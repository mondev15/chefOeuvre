package fr.enac.parser;


import fr.enac.audio.BalisesConvert;
import fr.enac.audio.IndicatifConvert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

/**
 * Preprocessor is the class used to manage all operations on all SSML file
 */
public class Preprocessor {

    /**
     * Prefix added to every flight parameter key in order to distinguished them from order parameters
     */
    private static final String flightPrefix = "flight";
    /**
     * Dictionary containing all environment variables (both flight and order parameters)
     */
    private Hashtable<String, String> environment = new Hashtable<>();

    /**
     * 
     * @param flightParameters The array containing all the parameters of a specific flight
     * @param orderParameters The array containing all the parameters of an order to a specific flight
     */
    public Preprocessor(Hashtable<String, String> flightParameters, Hashtable<String, String> orderParameters) {
        for (String key : orderParameters.keySet()) {
            environment.put(key, orderParameters.get(key)); //Exact copy of order parameters into the environment
        }
        try {
            for (String key : flightParameters.keySet()) { // TODO Verifier si les dico sont pas vides
                environment.put(flightPrefix + key, flightParameters.get(key));
            }
        } catch (NullPointerException e) {
            System.out.println("Avion inconnu");
        }
    }

    /**
     * Print a document to the specified output
     *
     * @param doc The document to print
     * @param out The OutputStream where to print the document. Use System.out to write on the console or new
     *            FileOutputStream("pathToFile") to write on a file
     */
    public static void printDocument(Document doc, OutputStream out) throws IOException, TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty("{http://docXml.apache.org/xslt}indent-amount", "4");
        transformer.transform(new DOMSource(doc), new StreamResult(new OutputStreamWriter(out, "UTF-8")));
    }

    /**
     * Getter of the environment attribute
     *
     * @return The environment (= all flight and order parameter) used by the preprocessor
     */
    public Hashtable<String, String> getEnvironment() {
        return environment;
    }

    /**
     * Creates variables inside the ScriptEngine corresponding to the keys and values of the dictionary.
     *
     * @param engine    ScriptEngine where to declare variables
     * @param hashtable Dictionary containing name of variables and values to be assigned
     */
    public void evalHashTable(ScriptEngine engine, Hashtable<String, String> hashtable) {

        for (String key : hashtable.keySet()) {
            try {
                engine.eval(key + " = \"" + hashtable.get(key) + "\"");
            } catch (ScriptException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Replaces all 'value' marker with the value associated to the key of the 'expr' attribute of the marker.
     * For example, if the dictionary has an entry which associate Speed -> 180, the marker "<value expr='Speed'/>"
     * will be replaced by "180".
     * 
     * @param docXml the XML document
     * @param hashtable the dictionary of all environment variables
     * @param language 
     */
    public void replaceValues(Document docXml, Hashtable<String, String> hashtable, String language) {

        NodeList valueNodes = docXml.getElementsByTagName("value");
        int valueNodesLength = valueNodes.getLength();
        for (int i = 0; i < valueNodesLength; i++) {
            Node n = valueNodes.item(0); //Popping the first node of the list
            String attribute = ((Element) n).getAttribute("expr");
            String valueAttribute = hashtable.get(attribute);

            /* Convert raw attribute string into suitable String that would be read by the SSML player*/
            switch (attribute) {
                case "flightCallSign":
                    valueAttribute = IndicatifConvert.convert(valueAttribute, language); // TODO lever des exception si le value attribute est nul
                    break;
                case "Beacon":
                    valueAttribute = BalisesConvert.convert(valueAttribute, language);
                    break;
                case "Angle":
                    valueAttribute = String.valueOf(Math.abs(Integer.parseInt(valueAttribute)));
            }
            Node y;
            if (valueAttribute != null) {
                y = docXml.createTextNode(valueAttribute);
            } else { //If the requested expr is not in the dictionary
                System.out.println("Attribute " + attribute + " not found");
                y = docXml.createTextNode(attribute);
            }
            n.getParentNode().replaceChild(y, n);
        }
    }


    /**
     * Replaces all 'say-as' marker with the number between the markers which each of the digits has been separated by a space
     * For example,  the marker "<say-as>180</say-as>"
     * will be replaced by "1 8 0".
     * 
     * @param docXml the XML document
     */
    public void replaceNumbers(Document docXml) {

        char[] splitArray = null; // Array of each of digits
        NodeList sayAsNodes = docXml.getElementsByTagName("say-as");
        int sayAsNodesLength = sayAsNodes.getLength();
        for (int i = 0; i < sayAsNodesLength; i++) {
            Node n = sayAsNodes.item(0); //Popping the first node of the list
            String str = n.getTextContent();
            splitArray = str.toCharArray();
            int splitArrayLength = splitArray.length;
            String content = " ";
            for (int j = 0; j < splitArrayLength; j++) {
                content += splitArray[j] + " "; //Adding a space between each of the digits
            }
            Node y = docXml.createTextNode(content);
            n.getParentNode().replaceChild(y, n);
        }

    }


    /**
     * Replaces all 'if' marker inside the docXml file after performing standard conditional logic.
     * The structure of an if markup should be :
     * <pre>
     * {@code
     * <if cond="ECMAScript_Expression">
     * executable_content
     * <elseif cond="ECMAScript_Expression"/> [optional]
     * executable_content
     * <elseif cond="ECMAScript_Expression"/> [optional]
     * executable_content
     * <else/> [optional]
     * executable_content
     * </if>
     * }
     * </pre>
     * 
     * @param docXml the XML document
     * @param hashtable the dictionary of all environment variables
     */
    public void replaceConditions(Document docXml, Hashtable<String, String> hashtable) {

        /* Getting if markers */
        NodeList ifNodes = docXml.getElementsByTagName("if");
        int ifNodesLength = ifNodes.getLength();

        /* Creating engine used for condition evaluation */
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("JavaScript");

        /* Register all variables if the dictionary inside the engine*/
        this.evalHashTable(engine, hashtable);

        List<String> endIfMarkers = Arrays.asList("elseif", "else");

        try {

            /* Browsing all if marker */
            for (int i = 0; i < ifNodesLength; i++) {
                Node ifNode = ifNodes.item(i);
                String ifCond = ((Element) ifNode).getAttribute("cond");
                Boolean ifCondEval = (Boolean) engine.eval(ifCond); //Evaluating the condition with the engine
                List<Node> replacement = new LinkedList<>();

                if (ifCondEval) {
                    Node firstChild = ifNode.getFirstChild();
                    replacement.add(docXml.createTextNode(firstChild.getTextContent()));
                    Node nextNode = firstChild;
                    while (nextNode.getNextSibling() != null &&
                            !endIfMarkers.contains(nextNode.getNextSibling().getNodeName())) {
                        nextNode = nextNode.getNextSibling();
                        replacement.add(nextNode);
                    }
                } else {

                    /* Browsing all elseif marker if the if-condition is false */
                    NodeList elseIfNodes = ((Element) ifNode).getElementsByTagName("elseif");
                    int elseIfNodesLength = elseIfNodes.getLength();
                    String elseIfCond;
                    Boolean elseIfCondEval;
                    int j = 0;
                    while (replacement.isEmpty() && j < elseIfNodesLength) {
                        Node elseIfNode = elseIfNodes.item(j);
                        elseIfCond = ((Element) elseIfNode).getAttribute("cond");
                        elseIfCondEval = (Boolean) engine.eval(elseIfCond);
                        if (elseIfCondEval) {
                            Node nextNode = elseIfNode.getNextSibling();
                            replacement.add(docXml.createTextNode(nextNode.getTextContent()));
                            while (nextNode.getNextSibling() != null &&
                                    !endIfMarkers.contains(nextNode.getNextSibling().getNodeName())) {
                                nextNode = nextNode.getNextSibling();
                                replacement.add(nextNode);
                            }
                        }
                        j++;
                    }

                    /* Browse else marker if both if-condition and elseif-condition are false */
                    if (replacement.isEmpty()) {
                        NodeList elseNodes = ((Element) ifNode).getElementsByTagName("else");
                        if (elseNodes.getLength() > 0) {
                            Node nextNode = elseNodes.item(0);
                            while (nextNode.getNextSibling() != null) {
                                nextNode = nextNode.getNextSibling();
                                replacement.add(nextNode);
                            }
                        }
                    }
                }

                /* If all conditions were false, the ifNode is removed */
                if (!replacement.isEmpty()) {
                    Node nextSibling = ifNode.getNextSibling();
                    ifNode.getParentNode().replaceChild(replacement.get(0), ifNode);
                    int childNumber = replacement.size();
                    for (int j = 1; j < childNumber; j++) {
                        replacement.get(0).getParentNode().insertBefore(replacement.get(j), nextSibling);
                    }
                } else {
                    ifNode.getParentNode().removeChild(ifNode);
                }
            }

        } catch (ScriptException e) {
            System.out.println("Engine error : " + e.getMessage());
        }
    }

    /**
     * Take a file formated in extended SSML and convert it to SSML readable by the AudioPLayer
     *
     * @param fileName Path to the file to convert.
     * @param language 
     */
    public Document convertToSSML(String fileName, String language) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        Document docXml = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            docXml = dBuilder.parse(new File(fileName));

            /* Remplacement des balises "<value expr='type'/>" par les valeurs correspondantes */
            this.replaceValues(docXml, environment, language);

            /* Replacement des balises "<say-as/>" */
            this.replaceNumbers(docXml);

            /* Remplacement des balises <if cond="">, <elseif> et <else> */
            this.replaceConditions(docXml, environment);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("Impossible d'ouvrir " + fileName);
        }

        return docXml;
    }

}

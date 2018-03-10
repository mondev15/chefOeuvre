package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.Block;

/**
*
* @author Lova
*/
public class Parser {
	
	public final static int CONTACT = 0;
	public final static int FREQUENCY = 1;
	public final static int PREFIX = 2;
	
	List<Block> blocks;
	
	public Parser() {
		blocks = new ArrayList<>();
	}
	
	public void parsing() {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document docXml = null;
		
		try {
			builder = factory.newDocumentBuilder();
			docXml = builder.parse(new File("src/data/studentAircraft.xml"));
			Element racine = docXml.getDocumentElement();
			NodeList messages = racine.getChildNodes();
			
			for (int i = 0; i < messages.getLength(); i++) {
				Node message = messages.item(i);
				
				if (message.getNodeType() == Node.ELEMENT_NODE) {
					Element elementMessage = (Element) message;
					
					//Recovery of time
					String[] timeString = elementMessage.getElementsByTagName("time")
							.item(0).getChildNodes().item(0).getNodeValue().split(":");
					
					int time = convertToSecond(Integer.parseInt(timeString[0]), 
							Integer.parseInt(timeString[1]), 
							Integer.parseInt(timeString[2]));
					
					//Recovery of command
					NodeList command = elementMessage.getElementsByTagName("command");
					for (int j = 0; j < command.getLength(); j++) {
						Element elementCommand = (Element) command.item(j);
						
						//Recovery of type of order
						String typeOrder = elementCommand.getElementsByTagName("type")
								.item(0).getChildNodes().item(0).getNodeValue();
						System.out.println(typeOrder);
						
						//Recovery of flight identifier
						String flight = elementCommand.getElementsByTagName("flight")
								.item(0).getChildNodes().item(0).getNodeValue();
						
						//Recovery of parameters
						NodeList parameter = elementCommand.getElementsByTagName("parameters");
						List<String> parameters = new ArrayList<>();
						for (int k = 0; k < parameter.getLength(); k++) {
							Element elementParameter = (Element) parameter.item(k);
							
							if (typeOrder.equals("AircraftContact")) {
								//Recovery of contact person
								String contact = elementParameter.getElementsByTagName("contact")
								.item(0).getChildNodes().item(0).getNodeValue();
								parameters.add(contact);
								
								//Recovery of Frequency
								String frequency = elementParameter.getElementsByTagName("frequency")
										.item(0).getChildNodes().item(0).getNodeValue();
								parameters.add(frequency);
								
								//Recovery of other order
								String prefix = elementParameter.getElementsByTagName("prefix")
										.item(0).getChildNodes().item(0).getNodeValue();
								parameters.add(prefix);
							}
						}
						buildBlock(typeOrder, time, parameters);
					}
				}
			}
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private int convertToSecond(int hour, int minute, int second) {
		
		return second + (minute * 60) + (hour * 3600);
	}
	
	private void buildBlock(String typeOrder, int time, List<String> parameters) {
		Block newBlock = new Block(time);
		
		if (typeOrder.equals("AircraftContact")) {
			newBlock.setTitle("New contact");
			newBlock.setHDG(parameters.get(CONTACT));
			newBlock.setFL(parameters.get(FREQUENCY));
			newBlock.setSpeed(parameters.get(PREFIX));
		}
		
		blocks.add(newBlock);
	}
}

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
	public final static int LEVEL = 0;
	public final static int OPTION = 1;
	public final static int HEADING = 1;
	
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
			
			//Recovery messages
			NodeList messages = racine.getChildNodes();
			List<String> parameters;
			for (int i = 0; i < messages.getLength(); i++) {
				Node message = messages.item(i);
				parameters = new ArrayList<>();
				
				if (message.getNodeType() == Node.ELEMENT_NODE) {
					Element elementMessage = (Element) message;
					
					//Recovery of time
					String[] timeString = elementMessage.getElementsByTagName("time")
							.item(0).getTextContent().split(":");
					
					int time = convertToSecond(Integer.parseInt(timeString[0]), 
							Integer.parseInt(timeString[1]), 
							Integer.parseInt(timeString[2]));
					
					//Recovery of command
					String typeOrder = elementMessage.getElementsByTagName("type")
							.item(0).getTextContent();
					System.out.println(typeOrder);
					
					//Recovery of flight identifier
					String flight = elementMessage.getElementsByTagName("flight")
							.item(0).getTextContent();
					
					if (typeOrder.equals("AircraftContact")) {
						//Recovery of contact person
						String contact = elementMessage.getElementsByTagName("contact")
						.item(0).getTextContent();
						parameters.add(contact);
						
						//Recovery of Frequency
						String frequency = elementMessage.getElementsByTagName("frequency")
								.item(0).getTextContent();
						parameters.add(frequency);
						
						//Recovery of other order
						String prefix = elementMessage.getElementsByTagName("prefix")
								.item(0).getTextContent();
						parameters.add(prefix);
						
					} else if (typeOrder.equals("AircraftNewContact")) {
						
						//Recovery of orders
						NodeList orders = elementMessage.getElementsByTagName("order");
						for(int j = 0; j < orders.getLength(); j++) {
							Element order = (Element) orders.item(j);
							
							parameters.add(order.getTextContent());
						}
						
					} else if (typeOrder.equals("AircraftLevel")) {
						
						//Recovery of level
						String level = elementMessage.getElementsByTagName("fl")
								.item(0).getTextContent();
						parameters.add(level);
						
						//Recovery of options
						String option = elementMessage.getElementsByTagName("option")
								.item(0).getTextContent();
						parameters.add(option);
						
					} else if (typeOrder.equals("AircraftHeading")) {
						
						//Recovery of heading
						String level = elementMessage.getElementsByTagName("to")
								.item(0).getTextContent();
						parameters.add(level);
						
						//Recovery of options
						String option = elementMessage.getElementsByTagName("option")
								.item(0).getTextContent();
						parameters.add(option);
						
					} else if (typeOrder.equals("AircraftClearToLand")) {						
						
						//Recovery of orders
						NodeList orders = elementMessage.getElementsByTagName("order");
						for(int j = 0; j < orders.getLength(); j++) {
							Element order = (Element) orders.item(j);
							
							parameters.add(order.getTextContent());
						}
						
						//Recovery of informations
						String option = elementMessage.getElementsByTagName("option")
								.item(0).getTextContent();
						parameters.add(option);
					
					} else if (typeOrder.equals("AircraftGoAround")) {
						
						//Recovery of orders
						NodeList orders = elementMessage.getElementsByTagName("order");
						for(int j = 0; j < orders.getLength(); j++) {
							Element order = (Element) orders.item(j);
							
							parameters.add(order.getTextContent());
						}
					}
					
					buildBlock(typeOrder, time, flight, parameters);
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
	
	private void buildBlock(String typeOrder, int time, String flight, List<String> parameters) {
		Block newBlock = new Block(time);
		String messageIvy = typeOrder + " Flight=" + flight;
		
		if (typeOrder.equals("AircraftContact")) {
			newBlock.setTitle("New contact");
			
			String contact = parameters.get(CONTACT);
			newBlock.setHDG(contact);
			
			String frequency = parameters.get(FREQUENCY);
			newBlock.setFL(frequency);
			
			String prefix = parameters.get(PREFIX);
			newBlock.setSpeed(prefix);
			
			messageIvy.concat(" Contact='"+contact+" "+frequency+"' Prefix='"+prefix+"'");
			newBlock.setMessageIvy(messageIvy);
			
		} else if (typeOrder.equals("AircraftNewContact")) {
			newBlock.setTitle("Contact made");
			
			String order;
			for (int i = 0; i < parameters.size(); i++) {
				order = parameters.get(i);
				newBlock.getListInfos().add(order);
				messageIvy.concat(" Order='"+order+", ");
			}
			messageIvy.concat("'");
			newBlock.setMessageIvy(messageIvy);
			
		} else if (typeOrder.equals("AircraftLevel")) {
			newBlock.setTitle("Level");
			
			String level = parameters.get(LEVEL);
			newBlock.setHDG(level);
			
			String option = parameters.get(OPTION);
			newBlock.setFL(option);
			
			messageIvy.concat(" Fl="+level+" Option='"+option+"'");
			newBlock.setMessageIvy(messageIvy);
			
		} else if (typeOrder.equals("AircraftHeading")) {
			newBlock.setTitle("Heading");
			
			String heading = parameters.get(HEADING);
			newBlock.setHDG(heading);
			
			String option = parameters.get(OPTION);
			newBlock.setFL(option);
			
			messageIvy.concat(" To="+heading+" Option='"+option+"'");
			newBlock.setMessageIvy(messageIvy);
			
		} else if (typeOrder.equals("AircraftClearToLand")) {
			newBlock.setTitle("Clear to land");
			
			String order;
			for (int i = 0; i < parameters.size(); i++) {
				order = parameters.get(i);
				newBlock.getListInfos().add(order);
				
				if (i == 0) messageIvy.concat(" Runway='"+order+"'");
				if (i == 1) messageIvy.concat(" Wind="+order);
				if (i == 2) messageIvy.concat(" Speed="+order);
				if (i == 3) messageIvy.concat(" Option='"+order+"'");
				
			}
			//AircraftClearToLand Flight=110 Runway='14 right' Wind=160 Speed=10 Option=''
			
			messageIvy.concat("'");
			newBlock.setMessageIvy(messageIvy);
		
		} else if (typeOrder.equals("AircraftGoAround")) {
			newBlock.setTitle("Go around");
			
			String order;
			for (int i = 0; i < parameters.size(); i++) {
				order = parameters.get(i);
				newBlock.getListInfos().add(order);
				messageIvy.concat(" Order='"+order+", ");
			}
			messageIvy.concat("'");
			newBlock.setMessageIvy(messageIvy);
		}
		
		blocks.add(newBlock);
	}
}

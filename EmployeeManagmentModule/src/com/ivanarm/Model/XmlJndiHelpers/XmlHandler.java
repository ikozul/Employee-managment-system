/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivanarm.Model.XmlJndiHelpers;

import com.ivanarm.EmployeeManagmentModule;
import java.io.File;
import java.io.FileInputStream;
import java.rmi.RemoteException;
import javax.swing.filechooser.FileSystemView;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLInputFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Ivan Kožul
 */
public class XmlHandler {

    private static final String DESKTOP = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
    private static final String XMLPATH = DESKTOP + "\\ChatLog.xml";

    public static List<String> ImportXml() {
        List<String> messages = parseXML(XMLPATH);
        
        
        return messages;
    }

    private static List<String> parseXML(String XMLPATH) {
        List<String> result = new ArrayList<>();

        try {

            File fXmlFile = new File(XMLPATH);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("Log");

            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    result.add(eElement.getElementsByTagName("Message").item(0).getTextContent());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }

    public static void ExportXml(List<String> list) {
        try {
            
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            //Chat elements
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("Logger");
            doc.appendChild(rootElement);

            //shorten way
            //staff.setAttribute("id", "1");
            //firstname elements

            
            
            for (String chat : list) {
                Element log = doc.createElement("Log");
                
                rootElement.appendChild(log);
                Element firstname = doc.createElement("Message");
                firstname.appendChild(doc.createTextNode(chat));
                log.appendChild(firstname);
            }

            //write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(new File(XMLPATH));
            transformer.transform(source, result);

            System.out.println("Done");
        } catch (TransformerException | ParserConfigurationException pce) {
            
        }

    }

}

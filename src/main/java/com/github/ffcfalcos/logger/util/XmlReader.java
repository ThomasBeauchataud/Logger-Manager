package com.github.ffcfalcos.logger.util;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * Xml reader service class
 */
public class XmlReader {

    /**
     * Return a field from a Xml file identified by his tag name
     *
     * @param file    File
     * @param element String
     * @return String
     */
    public static String getElement(File file, String element) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            Node node = doc.getElementsByTagName(element).item(0);
            return node.getFirstChild().getNodeValue();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Return a field list from a Xml file identified by his tag name
     *
     * @param file    File
     * @param element String
     * @return String[]
     */
    public static List<String> getElements(File file, String element) {
        List<String> elements = new ArrayList<>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName(element);
            for(int i = 0 ; i < nodeList.getLength() ; ++i) {
                elements.add(nodeList.item(i).getFirstChild().getNodeValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return elements;
    }

}

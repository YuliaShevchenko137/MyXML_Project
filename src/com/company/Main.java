package com.company;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Main {

    public static void main(String[] args) {
        try {
            File inputFile = new File("src/com/company/files/data.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputFile);
            System.out.println("Root elem: " + document.getDocumentElement().getNodeName());
            NodeList nodeList = document.getElementsByTagName("student");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                System.out.println("Current element: " + node.getNodeName());
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    System.out.println("id: " + element.getAttribute("id"));
                    System.out.println("fio: " + element.getElementsByTagName("fio").item(0).getTextContent());
                    System.out.println("mark: " + element.getElementsByTagName("mark").item(0).getTextContent());
                }
            }
            updateElementValue(document, 1);
            System.out.println("After updating:");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                System.out.println("Current element: " + node.getNodeName());
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    System.out.println("id: " + element.getAttribute("id"));
                    System.out.println("fio: " + element.getElementsByTagName("fio").item(0).getTextContent());
                    System.out.println("mark: " + element.getElementsByTagName("mark").item(0).getTextContent());
                }
            }
            document.getDocumentElement().normalize();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File("src/com/company/files/newData.xml"));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateElementValue(Document doc, int index) {
        NodeList nodeList = doc.getElementsByTagName("student");
        Element element = (Element) nodeList.item(index);
        Node name = element.getElementsByTagName("mark").item(0).getFirstChild();
        name.setNodeValue("4.0");
    }
}

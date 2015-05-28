package com.example;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class DocumentTest {
    public static void test() throws Exception {
        createXml(new File("xxx.txt"));

        //readXml(new File("xxx.txt"));
    }

    public static void createXml(File file) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document doc = dbf.newDocumentBuilder().newDocument();
        Element rootElement = doc.getDocumentElement();
        System.out.println(rootElement);
//        Element rootElement = doc.createElement("xxx");
//        Element nd = doc.createElement("student");
//        nd.setAttribute("id", "x123");
//        nd.setTextContent("aaaaaaaaaaaaa");
//        Node name = doc.createElement("name");
//        name.appendChild(doc.createTextNode("xxx"));
//        Node age = doc.createElement("age");
//        age.appendChild(doc.createTextNode("20"));
//        Node sex = doc.createElement("sex");
//        sex.appendChild(doc.createTextNode("中文"));
//        nd.appendChild(name);
//        nd.appendChild(age);
//        nd.appendChild(sex);
//        rootElement.appendChild(nd);
//        doc.appendChild(rootElement);

        TransformerFactory transformerFactory = TransformerFactory
                .newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        StreamResult streamResult = new StreamResult(new OutputStreamWriter(baos, "UTF-8"));
//        transformer.transform(new DOMSource(doc), streamResult);
//        System.out.println(baos.toString("UTF-8"));

        StreamResult streamResult = new StreamResult(file);
        transformer.transform(new DOMSource(doc), streamResult);
    }

    public static void readXml(File file) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document doc = dbf.newDocumentBuilder().parse(file);
        Element rootElement = doc.getDocumentElement();
        System.out.println(rootElement.getTextContent());

        NodeList nodeList = rootElement.getElementsByTagName("name");
        System.out.println(nodeList.getLength());

        Node node = nodeList.item(0);
        System.out.println(node.getTextContent());


    }


}

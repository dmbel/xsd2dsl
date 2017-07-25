package ru.dmbel.xsd2dsl;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import java.io.InputStream;

/**
 * Created by dm on 10.07.17.
 */
public class XSDProcessor {

    private InputStream xsdInputStream;

    private CodeBuilder codeBuilder;

    public XSDProcessor(InputStream xsdInputStream, CodeBuilder codeBuilder) {
        this.xsdInputStream = xsdInputStream;
        this.codeBuilder = codeBuilder;
    }

    private Document createDoc(){
        Document doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(xsdInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doc;
    }

    public void process() {
        try {
            Document doc = createDoc();
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            // element enumeration
            XPathExpression xslElementExpr = xpath.compile("//schema/element");
            NodeList nodeList = (NodeList) xslElementExpr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                String elemName = node.getAttributes().getNamedItem("name").getNodeValue();
                codeBuilder.addElement(elemName);
            }
            // element enumeration
            XPathExpression xslComplexTypeExpr = xpath.compile("//schema/complexType");
            NodeList nodeList = (NodeList) xslElementExpr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                String elemName = node.getAttributes().getNamedItem("name").getNodeValue();
                codeBuilder.addElement(elemName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

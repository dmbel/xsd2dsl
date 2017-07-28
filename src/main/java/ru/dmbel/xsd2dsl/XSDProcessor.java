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
    private Document doc;
    private XPath xpath;

    public XSDProcessor(InputStream xsdInputStream, CodeBuilder codeBuilder) {
        this.xsdInputStream = xsdInputStream;
        this.codeBuilder = codeBuilder;
    }

    private Document createDoc() {
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
            doc = createDoc();
            XPathFactory xPathfactory = XPathFactory.newInstance();
            xpath = xPathfactory.newXPath();

            // Первый проход - сообщить codeBuilder о корневых элементах XSD схемы
            pushRootNodes(XSDType.Element,"//schema/element");
            pushRootNodes(XSDType.ComplexType,"//schema/complexType");
            pushRootNodes(XSDType.Group,"//schema/group");
            pushRootNodes(XSDType.AttributeGroup,"//schema/attributeGroup");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void pushRootNodes(XSDType xsdType, String expr) throws XPathExpressionException {
        XPathExpression xslElementExpr = xpath.compile(expr);
        NodeList nodeList = (NodeList) xslElementExpr.evaluate(doc, XPathConstants.NODESET);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            String elemName = node.getAttributes().getNamedItem("name").getNodeValue();
            codeBuilder.pushNode(xsdType, elemName);
        }
    }
}

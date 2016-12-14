package framework.helper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang.xwork.StringUtils;
import org.w3c.dom.*;

public class DomXMLOperation {
	
	public static Document getDocument(String path)
    {
        Document document = null;
    	
    	DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    	DocumentBuilder documentBuilder = null;
    	
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			//InputStream inputStream = DomXMLOperation.class.getResourceAsStream("/" + path);
			java.net.URL thisUrl = DomXMLOperation.class.getClassLoader().getResource(path);
			java.net.URLConnection urlConn = thisUrl.openConnection();
			urlConn.setUseCaches(false);
			InputStream inputStream = urlConn.getInputStream();
			document = documentBuilder.parse(inputStream);
		}
		catch (Exception ex) {
			ExceptionLog.CreateLog(ex);
		}
    	
		return document;
    }
	
	public static Element getElement(String path)
    {
		return getDocument(path).getDocumentElement();
    }

	public static NodeList getNodeList(String path)
    {
		return getDocument(path).getChildNodes();
    }
	
	public static Element getElementByName(String path,String name)
    {
		return (Element)getDocument(path).getElementsByTagName(name).item(0);
    }
	
    public static Element[] getElementsByName(Element parent,String name)
    {
    	if(parent == null){
    		return null;
    	}
    	
        List<Node> resList=new ArrayList<Node>();
        NodeList nl=parent.getChildNodes();
        for(int i=0;i<nl.getLength();i++)
        {
            Node nd=nl.item(i);
            if(nd.getNodeName().equals(name))
            {
                resList.add(nd);
            }
        }
        Element[] res=new Element [resList.size()];
        for(int i=0;i<resList.size();i++)
        {
            res[i]=(Element)resList.get(i);
        }        
        return res;
    }
    

    public static Element getElementByName(Element parent,String name)
    {
    	Element[] elements = getElementsByName(parent,name);
    	if(elements == null || elements.length == 0){
    		return null;
    	}
		return elements[0];
    }

    public static String getElementValue(Element element)
    {
    	if(element == null){
    		return null;
    	}
    	
        NodeList nl=element.getChildNodes();
        for(int i=0;i<nl.getLength();i++)
        {
            if(nl.item(i).getNodeType()==Node.TEXT_NODE||
            		nl.item(i).getNodeType()==Node.CDATA_SECTION_NODE)
            {            
                return element.getFirstChild().getNodeValue();
            }
        }   
        return null;
    }
    
    public static Boolean getElementBooleanValue(Element element)
    {
    	if(element == null){
    		return null;
    	}
    	
        NodeList nl=element.getChildNodes();
        for(int i=0;i<nl.getLength();i++)
        {
            if(nl.item(i).getNodeType()==Node.TEXT_NODE)
            {            
                return Boolean.parseBoolean(element.getFirstChild().getNodeValue());
            }
        }   
        return null;
    }
     
    public static String getElementAttr(Element element,String attr)
    {
    	if(element == null){
    		return null;
    	}
    	
        return element.getAttribute(attr);
    }
    
    public static Boolean getElementBooleanAttr(Element element,String attr)
    {
    	if(element == null){
    		return null;
    	}
    	if(StringUtils.isBlank(element.getAttribute(attr))){
    		return null;
    	}
    	else{
    		return Boolean.parseBoolean(element.getAttribute(attr));
    	}
    }
    
    public static Integer getElementIntegerAttr(Element element,String attr)
    {
    	if(element == null){
    		return null;
    	}
    	if(StringUtils.isBlank(element.getAttribute(attr))){
    		return null;
    	}
    	else{
    		return Integer.parseInt(element.getAttribute(attr));
    	}
    }
    
    public static Double getElementDoubleAttr(Element element,String attr)
    {
    	if(element == null){
    		return null;
    	}
    	if(StringUtils.isBlank(element.getAttribute(attr))){
    		return null;
    	}
    	else{
    		return Double.parseDouble(element.getAttribute(attr));
    	}
    }
     
    public static void setElementValue(Element element,String val)
    {
        Node node=element.getOwnerDocument().createTextNode(val);
        NodeList nl=element.getChildNodes();
        for(int i=0;i<nl.getLength();i++)
        {
            Node nd=nl.item(i);
            if(nd.getNodeType()==Node.TEXT_NODE)//是一个Text Node
            {            
                  nd.setNodeValue(val);
                  return;
            }
        }   
        element.appendChild(node);        
    }
     
    public static void setElementAttr(Element element,
            String attr,String attrVal)
    {
        element.setAttribute(attr,attrVal);
    }
     
     
    public static void addElement(Element parent,Element child)
    {
        parent.appendChild(child);
    }
     
    public static void addElement(Element parent,String tagName)
    {        
        Document doc=parent.getOwnerDocument();
        Element child=doc.createElement(tagName);
        parent.appendChild(child);
    }
     
    public static void addElement(Element parent,String tagName,String text)
    {
        Document doc=parent.getOwnerDocument();
        Element child=doc.createElement(tagName);
        setElementValue(child,text);
        parent.appendChild(child);
    }
     
    public static void removeElement(Element parent,String tagName)
    {
        NodeList nl=parent.getChildNodes();
        for(int i=0;i<nl.getLength();i++)
        {
            Node nd=nl.item(i);
            if(nd.getNodeName().equals(tagName))
            {
                parent.removeChild(nd);
            }
        }

    }
    
    public static Node selectSingleNode(String express, Object source) {//查找节点，并返回第一个符合条件节点
        Node result=null;
        XPathFactory xpathFactory=XPathFactory.newInstance();
        XPath xpath=xpathFactory.newXPath();
        try {
            result=(Node) xpath.evaluate(express, source, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    public static NodeList selectNodes(String express, Object source) {//查找节点，返回符合条件的节点集。
        NodeList result=null;
        XPathFactory xpathFactory=XPathFactory.newInstance();
        XPath xpath=xpathFactory.newXPath();
        try {
            result=(NodeList) xpath.evaluate(express, source, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
	     
}

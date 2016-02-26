package helloworld.helper;

import java.io.File;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XmlHelper extends DataHelper{

    @Override
    public void run(String filePath, HashMap<String, String> messages) {
        
        try {
            File xmlFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            
            NodeList nList = doc.getElementsByTagName("entry");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    
                   Element eElement = (Element) nNode;
                   String key =  eElement.getElementsByTagName("name").item(0).getTextContent().toLowerCase();
                   String value = eElement.getElementsByTagName("value").item(0).getTextContent().toLowerCase();
                   
                   if(!messages.containsKey(key)) {
                       messages.put(key, value);
                   }
                }
             }
            
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    
    public static void main(String[] args) {
        new XmlHelper().run("/home/opu/assignment/test.xml", null);
        
    }
}

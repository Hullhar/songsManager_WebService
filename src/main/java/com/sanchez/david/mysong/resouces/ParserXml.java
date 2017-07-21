package com.sanchez.david.mysong.resouces;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.CharacterData;
import org.xml.sax.InputSource;

public class ParserXml {
	
	public static String parserImg(String dataXml){
		String img = "";
		try{
			 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			 DocumentBuilder db = dbf.newDocumentBuilder();
			 InputSource is = new InputSource();
			 is.setCharacterStream(new StringReader(dataXml));
			 Document doc = db.parse(is);
			 Element node = (Element) doc.getElementsByTagName("URL").item(0);
			 if (node != null){
				 img = getCharacterDataFromElement(node);
			 } else {
				 // There is not img for that query
				 img = "SE BUSCA";
			 }
		} catch (Exception ex){
		
		}
		 return img;
		
	}
	
	public static String getCharacterDataFromElement(Element e) {
		Node child = e.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;	
			return cd.getData();
		}
		return "?";
	}

}

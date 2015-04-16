package topicosParcial.items;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Item {
	private Document docItems;
	
	
	
	/**
	 * @param docItems
	 */
	public Item() {
		super();
		try{
			File fXmlFileItems = new File(
					"/Users/mariacristina/Documents/workspace-sts-3.6.3.SR1/topicosParcial/items.xml");
			DocumentBuilderFactory dbFactoryItems = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilderItems = dbFactoryItems.newDocumentBuilder();
			this.docItems = dBuilderItems.parse(fXmlFileItems);
			}catch(ParserConfigurationException e){
				e.printStackTrace();
			}catch(IOException e){
				e.printStackTrace();
			}catch(SAXException e){
				e.printStackTrace();
			}
			this.docItems.getDocumentElement().normalize();
	}

	/**
	 * @return the docItems
	 */
	public Document getDocItems() {
		return docItems;
	}

	/**
	 * @param docItems the docItems to set
	 */
	public void setDocItems(Document docItems) {
		this.docItems = docItems;
	}
	
	public NodeList getItemsList(){
		return this.docItems.getElementsByTagName("cac:CatalogueLine");
	}

	
}

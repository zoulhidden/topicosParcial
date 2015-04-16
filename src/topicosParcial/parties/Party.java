package topicosParcial.parties;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Party {
	private Document docParties;

	
	
	/**
	 * @param docParties
	 */
	public Party() {
		super();
		try{
		File fXmlFileParties = new File(
				"/Users/mariacristina/Documents/workspace-sts-3.6.3.SR1/topicosParcial/parties.xml");
		DocumentBuilderFactory dbFactoryParties = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder dBuilderParties = dbFactoryParties
				.newDocumentBuilder();
		this.docParties = dBuilderParties.parse(fXmlFileParties);
		}catch(ParserConfigurationException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}catch(SAXException e){
			e.printStackTrace();
		}
		this.docParties.getDocumentElement().normalize();
	}

	/**
	 * @return the docParties
	 */
	public Document getDocParties() {
		return docParties;
	}

	/**
	 * @param docParties the docParties to set
	 */
	public void setDocParties(Document docParties) {
		this.docParties = docParties;
	}
	
	public NodeList getPartiesList(){
		return this.docParties.getElementsByTagName("cac:Party");
	}

}

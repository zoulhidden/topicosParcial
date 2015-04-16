package topicosParcial.order;

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

public class Orden {
	private Document docOrder;

	/**
	 * @param docOrder
	 */
	public Orden() {
		super();
		try{
		File fXmlFileOrder = new File(
				"/Users/mariacristina/Documents/workspace-sts-3.6.3.SR1/topicosParcial/Order-template.xml");
		DocumentBuilderFactory dbFactoryOrder = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder dBuilderOrder = dbFactoryOrder.newDocumentBuilder();
		this.docOrder = dBuilderOrder.parse(fXmlFileOrder);
		}catch(ParserConfigurationException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}catch(SAXException e){
			e.printStackTrace();
		}
		this.docOrder.getDocumentElement().normalize();
	}

	/**
	 * @return the docOrder
	 */
	public Document getDocOrder() {
		return docOrder;
	}

	/**
	 * @param docOrder the docOrder to set
	 */
	public void setDocOrder(Document docOrder) {
		this.docOrder = docOrder;
	}
	
	public Node getBuyerParty(){
		return this.docOrder.getElementsByTagName("cac:Party").item(0);
	}
	
	public Node getSellerParty(){
		return this.docOrder.getElementsByTagName("cac:Party").item(1);
	}
	
	public void replaceBuyer(Node nodeRandom, Node actual){
		if(nodeRandom.getNodeType() == Node.ELEMENT_NODE)
		docOrder.getElementsByTagName("cac:BuyerCustomerParty").item(0).replaceChild(docOrder.adoptNode(nodeRandom), actual);
	}
	
	public void replaceSeller(Node nodeRandom, Node actual){
		if(nodeRandom.getNodeType() == Node.ELEMENT_NODE)
		docOrder.getElementsByTagName("cac:SellerSupplierParty").item(0).replaceChild(docOrder.adoptNode(nodeRandom), actual);
	}
	
	public void setID(String id){
		docOrder.getElementsByTagName("cbc:ID").item(0).setTextContent(id);
	}
	
	public void setUUID(String UUID){
		docOrder.getElementsByTagName("cbc:UUID").item(0).setTextContent(UUID);
	}
	
	public void setFecha(String fecha){
		docOrder.getElementsByTagName("cbc:IssueDate").item(0).setTextContent(fecha);
	}
	
	public Double modifyItem(String desc, String priceAmt, int quant){
		Element descr = (Element) docOrder.getElementsByTagName("cac:Item").item(0);
		descr.getElementsByTagName("cbc:Description").item(0).setTextContent(desc);
		docOrder.getElementsByTagName("cbc:PriceAmount").item(0).setTextContent(priceAmt);
		docOrder.getElementsByTagName("cbc:Quantity").item(0).setTextContent(Integer.valueOf(quant).toString());
		Element lineEA = (Element) docOrder.getElementsByTagName("cac:LineItem").item(0);
		lineEA.getElementsByTagName("cbc:LineExtensionAmount").item(0).setTextContent(Double.valueOf(Double.parseDouble(priceAmt)*quant).toString());
		return Double.parseDouble(priceAmt)*quant;
	}

	public Double createItem(String description, String priceAmount, int quantity, int position) {
		Node newItem = docOrder.getElementsByTagName("cac:OrderLine").item(position);
		docOrder.getDocumentElement().appendChild(docOrder.adoptNode(newItem));
		
		Element descr = (Element) docOrder.getElementsByTagName("cac:Item").item(position);
		descr.getElementsByTagName("cbc:Description").item(position).setTextContent(description);
		docOrder.getElementsByTagName("cbc:PriceAmount").item(position).setTextContent(priceAmount);
		docOrder.getElementsByTagName("cbc:Quantity").item(position).setTextContent(Integer.valueOf(quantity).toString());
		Element lineEA = (Element) docOrder.getElementsByTagName("cac:LineItem").item(position);
		lineEA.getElementsByTagName("cbc:LineExtensionAmount").item(position).setTextContent(Double.valueOf(Double.parseDouble(priceAmount)*quantity).toString());
		
		return Double.parseDouble(priceAmount)*quantity;
	}
}

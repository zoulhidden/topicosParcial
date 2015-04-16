package topicosParcial.coordinate;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.org.apache.xml.internal.serialize.LineSeparator;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

import topicosParcial.items.Item;
import topicosParcial.order.Orden;
import topicosParcial.parties.Party;

public class Coordinate {
	private static Random rnd = new Random();

	public static void main(String[] args) {
		long inicio = System.currentTimeMillis();
		int numeroOrdenes = numeroOrdenes();
		
		Party docParties = new Party();
		Item docItems = new Item();
		Orden docOrder = new Orden();
		
		for (int i = 0; i < numeroOrdenes; i++) {
			Orden newDocOrder = new Orden();
			
			Node buyer = newDocOrder.getBuyerParty();
			Node seller = newDocOrder.getSellerParty();
			
			NodeList parties = docParties.getPartiesList();
			Node buyerRandom = parties.item(rnd.nextInt(parties.getLength()));
			Node sellerRandom = parties.item(rnd.nextInt(parties.getLength()));
			newDocOrder.replaceBuyer(buyerRandom, buyer);
			newDocOrder.replaceSeller(sellerRandom, seller);
			
			String ID = generarID();
			newDocOrder.setID(ID);
			newDocOrder.setUUID(UUID.randomUUID().toString().toUpperCase());
			newDocOrder.setFecha(generarFecha());
			
			int cantItems = rnd.nextInt(10) + 1;
			double sumaLineEA = 0;
			for (int j = 0; j < cantItems; j++) {
				NodeList items = docItems.getItemsList();
				Node randomItem = items
						.item(rnd.nextInt(items.getLength()));
				Element catalogue = (Element) randomItem;
				Node priceAmt = catalogue.getElementsByTagName(
						"cbc:PriceAmount").item(0);
				Node desc = catalogue.getElementsByTagName(
						"cbc:Description").item(0);
				String Description = desc.getTextContent();
				String PriceAmount = priceAmt.getTextContent();
				int Quantity = rnd.nextInt(100);
				if(j==0){
					sumaLineEA += newDocOrder.modifyItem(Description, PriceAmount, Quantity);
				}else{
					//sumaLineEA += newDocOrder.createItem(Description, PriceAmount, Quantity, j);
				}
			}
			newDocOrder.getDocOrder().getElementsByTagName("cbc:LineExtensionAmount").item(0).setTextContent(Double.valueOf(sumaLineEA).toString());
			newDocOrder.getDocOrder().getElementsByTagName("cbc:PayableAmount").item(0).setTextContent(Double.valueOf(sumaLineEA).toString());
			try{
				OutputFormat format = new OutputFormat(newDocOrder.getDocOrder()); 
				format.setLineSeparator(LineSeparator.Windows); 
				format.setIndenting(true); 
				format.setLineWidth(0); 
				format.setPreserveSpace(false); 
				ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(ID+".xml"));
				XMLSerializer serializerXML = new XMLSerializer((ObjectOutputStream) writer, format);
				serializerXML.asDOMSerializer();
				serializerXML.serialize(newDocOrder.getDocOrder());
				//writer.writeObject(serializerXML);
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		 System.out.println( "Tiempo: " + (System.currentTimeMillis() - inicio) + " ms. "  );
	}
	
	public static int numeroOrdenes(){
		System.out.println("Ingrese el numero de ordenes: ");
		int numeroOrdenes = 0;
		@SuppressWarnings("resource")
		Scanner entradaTeclado = new Scanner(System.in);
		numeroOrdenes = entradaTeclado.nextInt();
		return numeroOrdenes;
	}
	
	public static String generarID() {
		String ID = "";
		int x = (int) (rnd.nextInt() * 10);
		if (x < 0)
			x = x * (-1);
		ID += Integer.valueOf(x).toString();
		while (ID.toCharArray().length < 10)
			ID += (int) (rnd.nextDouble() * 10);

		return ID;
	}

	public static String generarFecha() {
		Calendar fecha;
		String date = "";
		fecha = Calendar.getInstance();
		fecha.set(rnd.nextInt(100) + 1950, rnd.nextInt(12) + 1,
				rnd.nextInt(30) + 1);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		char[] fechaArray = sdf.format(fecha.getTime()).toString()
				.toCharArray();
		for (int j = 0; j < fechaArray.length; j++) {
			if (fechaArray[j] == '/')
				fechaArray[j] = '-';

			date += fechaArray[j];
		}
		String[] fechacorregida = date.split("-");
		String temp = date.split("-")[0];
		fechacorregida[0] = fechacorregida[2];
		fechacorregida[2] = temp;
		date = "";
		date += fechacorregida[0] + "-" + fechacorregida[1] + "-"
				+ fechacorregida[2];
		return date;
	}

}

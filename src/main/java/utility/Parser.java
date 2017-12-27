package utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Parser {
	
	private static String resourcePath = "src/main/resources/";
	
	public static List<String> readFileLineByLine(String fileName) {
		List<String> list = new ArrayList<>();

		try (BufferedReader br = Files.newBufferedReader(Paths.get(resourcePath + fileName))) {
			list = br.lines().collect(Collectors.toList());
		} catch (IOException io) {
			io.printStackTrace();
		}
		
		return list;
	}
	
	public static String getFormattedFile(String fileName) {
    	String text = new String();
    	
		try {
    		text = readFiletoString(fileName, Charset.forName("UTF-8"));
    	} catch(IOException io) {
    		io.printStackTrace();
    	}
		
		text = formatString(text);
    	
    	return text;
	}
	
	public static String readFiletoString(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(resourcePath + path));
		return new String(encoded, encoding);
	}
	
	public static String formatString(String text) {
    	text = text.toLowerCase();
    	text = text.replaceAll("\n|\t", " ");
    	text = text.replaceAll(" +", " ");
    	text = text.replaceAll("universal declaration of human rights - .* © " +
    			"1996 – 2009 the office of the high commissioner for human rights this " +
    			"plain text version prepared by the “udhr in unicode” project, " +
    			"https://www.unicode.org/udhr. ---", "");
    	return text;
	}
	
	public static List<Element> parseXML(String fileName) {
		try {
	        File inputFile = new File(resourcePath + fileName);
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        Document doc = dBuilder.parse(inputFile);
	        doc.getDocumentElement().normalize();
	        NodeList nList = doc.getElementsByTagName("udhr");
	        List<Element> result = new LinkedList<>();
	        for (int temp = 0; temp < nList.getLength(); temp++) {
	           Node nNode = nList.item(temp);
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	              Element eElement = (Element) nNode;
	              result.add(eElement);
	           }
	        }
	        return result;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
     }
	
	public static boolean existsFile(String fileName) {
		File f = new File(resourcePath + fileName);
		return f.exists();
	}
}

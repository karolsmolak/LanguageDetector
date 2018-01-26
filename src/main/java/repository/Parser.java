package repository;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Parser {
	
	public String getFormattedFile(String path) {
		String text = new String();

		try {
			text = readFiletoString(path, Charset.forName("UTF-8"));
		} catch (IOException io) {
			io.printStackTrace();
		}

		text = formatString(text);

		return text;
	}

	public String readFiletoString(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	public String formatString(String text) {
		text = text.toLowerCase();
		text = text.replaceAll("\n|\t", " ");
		text = text.replaceAll(" +", " ");
		text = text.replaceAll("universal declaration of human rights - .* © "
				+ "1996 – 2009 the office of the high commissioner for human rights this "
				+ "plain text version prepared by the “udhr in unicode” project, "
				+ "https://www.unicode.org/udhr. ---", "");
		return text;
	}

	public List<Element> parseXML(String path, String tagName) {
		try {
			File inputFile = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName(tagName);
			List<Element> result = new LinkedList<>();
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					result.add(eElement);
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean existsFile(String path) {
		File f = new File(path);
		return f.exists();
	}
}

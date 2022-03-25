package scripts;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.snu.ids.ha.index.Keyword;
import org.snu.ids.ha.index.KeywordExtractor;
import org.snu.ids.ha.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class makeKeyword {
	private String input_file;
	private String output_file = "./index.xml";

	public makeKeyword(String file1) throws Exception {
		this.input_file = file1;
		File file = new File(input_file);
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document document = docBuilder.parse(file);
		document.getDocumentElement().normalize();

		Document dd = docBuilder.newDocument();

		Element docs = dd.createElement("docs");
		dd.appendChild(docs);

		NodeList nList = document.getElementsByTagName("doc");
		String bodyData;
		String titleData;
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				titleData = eElement.getElementsByTagName("title").item(0).getTextContent();
				bodyData = eElement.getElementsByTagName("body").item(0).getTextContent();
				Element doc = dd.createElement("doc");
				docs.appendChild(doc);
				Element title = dd.createElement("title");
				title.appendChild(dd.createTextNode(titleData));
				doc.appendChild(title);
				Element body = dd.createElement("body");
				doc.appendChild(body);

				doc.setAttribute("id", String.valueOf(temp));
				KeywordExtractor ke = new KeywordExtractor();
				KeywordList kl = ke.extractKeyword(bodyData, true);
				Keyword kwrd = new Keyword();
				for (int i = 0; i < kl.size(); i++) {
					kwrd = kl.get(i);

					body.appendChild(dd.createTextNode(kwrd.getString() + ":" + kwrd.getCnt() + "#"));

				}

			}
		}
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");

		DOMSource source = new DOMSource(docs);
		StreamResult result = new StreamResult(new FileOutputStream(new File(output_file)));
		transformer.transform(source, result);
	}

	public void convertXml() {
		System.out.println("3주차실행완료!!");
	}
}

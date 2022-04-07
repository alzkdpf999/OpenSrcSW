package scripts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.snu.ids.ha.index.Keyword;
import org.snu.ids.ha.index.KeywordExtractor;
import org.snu.ids.ha.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@SuppressWarnings({ "rawtypes", "unchecked", "nls" })
public class searcher {
	private String input_file;

	public searcher(String path) {
		this.input_file = path;
	}

	public double InnerProduct(String Q) throws Exception {
		double asd = 0.0;

		HashMap<String, String> titleinput = new HashMap<>();
		HashMap<String, Double> simword = new HashMap<>();
		// 일단 post파일 읽고 해쉬맵에 일단 저장한뒤 title을 같이 저장해야한다.

		FileInputStream filestream = new FileInputStream(input_file);
		ObjectInputStream objectInputStream = new ObjectInputStream(filestream);
		Object object = objectInputStream.readObject();
		objectInputStream.close();
		HashMap store = (HashMap) object;
		// title뽑아오기 위해
		File files = new File("./collection.xml");
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document document = docBuilder.parse(files);
		document.getDocumentElement().normalize();
		NodeList nList = document.getElementsByTagName("doc"); // 제목얻기

		KeywordExtractor ke = new KeywordExtractor(); // 질의어 키워드대로 일단 나누기
		KeywordList kl = ke.extractKeyword(Q, true);
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String title = eElement.getElementsByTagName("title").item(0).getTextContent();
				String id = eElement.getAttributes().getNamedItem("id").getTextContent();
				titleinput.put(id, title);
				double sim = 0.0;

				for (Keyword kw : kl) {
					String word = kw.getString();
					double wQ = kw.getCnt();
					if (!store.containsKey(word)) {
						continue;
					}
					HashMap weight = (HashMap) store.get(word);
					double weightdoc = (double) weight.get(id);

					double innerpro = wQ * weightdoc;
					sim += innerpro;
				}
				asd = sim;
				return sim;
			}
		}
		
		return asd;
	}
}

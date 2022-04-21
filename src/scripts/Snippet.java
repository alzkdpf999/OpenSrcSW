package scripts;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

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

public class Snippet {
private String input_file;

public Snippet(String path)
{
	this.input_file=path;
}
public void showSnippet(String query) throws Exception {
	ArrayList<String> content30=new ArrayList<>(); //3O음절씩 저장
	HashMap<String, String> titleinput = new HashMap<>(); //title store
	HashMap<String,String[]> wd=new HashMap<>(); // 30음절씩 한 아이디당
	
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
		String id = eElement.getAttributes().getNamedItem("id").getTextContent();
		
		titleinput.put(id , titleData); //아이디와 타이틀 저장
		
		String[] re= bodyData.split(" ");
		for()
		

	}
}
}
}

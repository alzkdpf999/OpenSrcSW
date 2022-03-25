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
import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class makeCollection {
	private String data_path;
	private String output_file = "./collection.xml";

	public static File[] makeFileList(String data_path) {
		File dir = new File(data_path);
		return dir.listFiles();
	}

	public makeCollection(String path) throws Exception {
		this.data_path = path;

		File[] files = makeFileList(data_path);

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		Document dd = docBuilder.newDocument();

		Element docs = dd.createElement("docs");
		dd.appendChild(docs);

		for (int index = 0; index < files.length; index++) {

			org.jsoup.nodes.Document html = Jsoup.parse(files[index], "UTF-8");

			String titleData = html.title();
			String bodyData = html.body().text();

			Element doc = dd.createElement("doc");
			docs.appendChild(doc);

			doc.setAttribute("id", String.valueOf(index));

			Element title = dd.createElement("title");
			title.appendChild(dd.createTextNode(titleData));

			Element body = dd.createElement("body");
			body.appendChild(dd.createTextNode(bodyData));

			doc.appendChild(title);
			doc.appendChild(body);
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

	public void makeXml() {
		System.out.println("2주자실행완료!!");
	}

}

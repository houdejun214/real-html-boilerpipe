package org.real.html.boilerpipe;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.document.TextDocument;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import de.l3s.boilerpipe.sax.BoilerpipeSAXInput;

public class App {

	public static void main(String[] args) throws HttpException, IOException, BoilerpipeProcessingException, SAXException {
		String url = "http://www.lollipop.sg/content/new-face-runner-had-change-surname-because-fugitive-lawyer-father";
//		HttpClient client = new HttpClient();
//		HttpMethod get = new GetMethod(url);
//		client.executeMethod(get);
//		String content = get.getResponseBodyAsString();
//		get.releaseConnection();
//		// parser document
//		ArticleExtractor extractor = ArticleExtractor.INSTANCE;
//		HTMLContentExtractor hh = HTMLContentExtractor.newExtractingInstance();
//		TextDocument textDoc = new BoilerpipeSAXInput(new InputSource(new StringReader(content))).getTextDocument();
//		extractor.process(textDoc);
//		InputSource htmlDoc = new InputSource(new StringReader(content));
//		String mainContent = hh.process(textDoc, htmlDoc);
//		
//		Document doc = Jsoup.parse(mainContent);
//		Elements imgs = doc.select("img");
//		List<String> imgLinks = new ArrayList<String>();
//		if(imgs != null && imgs.size()>0){
//			for(Element img:imgs){
//				String src = img.attr("src");
//				System.out.println(src);
//				if(src.)
//			}
//		}else{
//			System.out.println("have no images");
//		}
		
//		URL url = new URL(
//                "http://www.lollipop.sg/content/new-face-runner-had-change-surname-because-fugitive-lawyer-father"
//        );
//
//		final BoilerpipeExtractor extractor = CommonExtractors.ARTICLE_EXTRACTOR;
//		
//		final HTMLContentExtractor hh = HTMLContentExtractor.newExtractingInstance();
//		
//		String fileName = "/home/houdejun/highlighted.html";
//		PrintWriter out = new PrintWriter(fileName, "UTF-8");
//		out.println("<base href=\"" + url + "\" >");
//		out.println("<meta http-equiv=\"Content-Type\" content=\"text-html; charset=utf-8\" />");
//		out.println(hh.process(url, extractor));
//		out.close();
//		openFile(fileName);
		
		HtmlContentImageExtractor extractor = new HtmlContentImageExtractor();
		List<String> images = extractor.getImages(url);
		for(String link:images){
			System.out.println(link);
		}
	}
	
	private static void openFile(String file){
		try {
		    // Execute command
		    String command = "xdg-open "+file;
		    Runtime.getRuntime().exec(command);
		} catch (IOException e) {
		}
	}
}

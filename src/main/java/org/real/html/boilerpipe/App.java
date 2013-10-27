package org.real.html.boilerpipe;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import org.apache.commons.httpclient.HttpException;
import org.xml.sax.SAXException;

import de.l3s.boilerpipe.BoilerpipeExtractor;
import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.CommonExtractors;

public class App {

	public static void main(String[] args) throws HttpException, IOException, BoilerpipeProcessingException, SAXException {
//		String url = "http://www.lollipop.sg/content/new-face-runner-had-change-surname-because-fugitive-lawyer-father";
//		HttpClient client = new HttpClient();
//		HttpMethod get = new GetMethod(url);
//		client.executeMethod(get);
//		String content = get.getResponseBodyAsString();
//		get.releaseConnection();
//		// parser document
//		ArticleExtractor extractor = ArticleExtractor.INSTANCE;
//		HTMLHighlighter hh = HTMLHighlighter.newHighlightingInstance();
//		TextDocument textDoc = new BoilerpipeSAXInput(new InputSource(new StringReader(content))).getTextDocument();
//		extractor.process(textDoc);
//		InputSource htmlDoc = new InputSource(new StringReader(content));
//		String mainContent = hh.process(textDoc, htmlDoc);
//		
//		Document doc = Jsoup.parse(mainContent);
//		Elements imgs = doc.select("img");
//		if(imgs != null && imgs.size()>0){
//			for(Element img:imgs){
//				String src = img.attr("src");
//				System.out.println(src);
//			}
//		}else{
//			System.out.println("have no images");
//		}
		
		URL url = new URL(
                "http://www.lollipop.sg/content/new-face-runner-had-change-surname-because-fugitive-lawyer-father"
        );

		final BoilerpipeExtractor extractor = CommonExtractors.ARTICLE_EXTRACTOR;
		
		final HTMLContentExtractor hh = HTMLContentExtractor.newExtractingInstance();
		
		PrintWriter out = new PrintWriter("/Users/houdejun/Desktop/highlighted.html", "UTF-8");
		out.println("<base href=\"" + url + "\" >");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text-html; charset=utf-8\" />");
		out.println(hh.process(url, extractor));
		out.close();
		
		System.out.println("Now open file:///tmp/highlighted.html in your web browser");
	}
}

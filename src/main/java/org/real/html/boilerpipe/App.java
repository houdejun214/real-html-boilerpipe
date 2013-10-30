package org.real.html.boilerpipe;

import java.io.IOException;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.xml.sax.SAXException;

import de.l3s.boilerpipe.BoilerpipeProcessingException;

public class App {

	public static void main(String[] args) throws HttpException, IOException, BoilerpipeProcessingException, SAXException {
		String url = "http://www.lollipop.sg/content/new-face-runner-had-change-surname-because-fugitive-lawyer-father";
//		url = "http://www.tiffinbiru.com/2011/03/donat-suria-pelangiada-brann.html";
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
		
//		URL uri = new URL(url);
//
//		final BoilerpipeExtractor extractor = CommonExtractors.ARTICLE_EXTRACTOR;
//		
//		final HTMLContentExtractor hh = HTMLContentExtractor.newExtractingInstance();
//		
//		String fileName = "/Users/houdejun/Documents/highlighted.html";
//		PrintWriter out = new PrintWriter(fileName, "UTF-8");
//		out.println("<base href=\"" + uri + "\" >");
//		out.println("<meta http-equiv=\"Content-Type\" content=\"text-html; charset=utf-8\" />");
//		out.println(hh.process(uri, extractor));
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
		    command = "open "+file;
		    Runtime.getRuntime().exec(command);
		} catch (IOException e) {
		}
	}
}

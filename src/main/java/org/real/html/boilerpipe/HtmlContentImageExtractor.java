package org.real.html.boilerpipe;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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

public class HtmlContentImageExtractor {
	
	private static Set<String> ignoreFilesExtends = new HashSet<String>(Arrays.asList("gif","png"));
	private static Set<String> imageFilesExtends = new HashSet<String>(Arrays.asList("jpg"));
	
	private CloseableHttpClient client = HttpClients.createDefault();

	private ArticleExtractor extractor = ArticleExtractor.INSTANCE;
	
	private HtmlContentExtractor hh = HtmlContentExtractor.newExtractingInstance();
	
	public List<String> getImages(String url) throws IOException, BoilerpipeProcessingException, SAXException{
		HttpGet get = new HttpGet(url);
		get.addHeader("Connection", "close");
		get.addHeader("User-Agent","Mozilla/5.0 (Windows; U; Windows NT 6.1; pl; rv:1.9.1) Gecko/20090624 Firefox/3.5 (.NET CLR 3.5.30729)");

		CloseableHttpResponse response = client.execute(get);
		try {
			HttpEntity entity = response.getEntity();
			String content = EntityUtils.toString(entity);
			// parser document
			List<String> imgLinks = getImagesByContent(content,url);
			return imgLinks;
		} finally {
			response.close();
		}
	}
	
	public List<String> getImagesByContent(String content,String baseUrl) throws IOException, BoilerpipeProcessingException, SAXException{
		List<String> imgLinks;
		// parser document
		TextDocument textDoc = new BoilerpipeSAXInput(new InputSource(new StringReader(content))).getTextDocument();
		extractor.process(textDoc);
		InputSource htmlDoc = new InputSource(new StringReader(content));
		String mainContent = hh.process(textDoc, htmlDoc);
		
		Document doc = Jsoup.parse(mainContent,baseUrl);
		Elements imgs = doc.select("img");
		imgLinks = new ArrayList<String>();
		if(imgs != null && imgs.size()>0){
			for(Element img:imgs){
				String src = img.absUrl("src");
				String ext = getExtension(src);
				if(!ignoreFilesExtends.contains(ext)){
					imgLinks.add(src);
				}
			}
		}
		return imgLinks;
	}
	
	public static String getExtension(String fileName){
		int extPos=fileName.lastIndexOf(".");
		if(extPos<0){
			return "";
		}
		int end = fileName.indexOf("?", extPos);
		if(end>0){
			return fileName.substring(extPos+1, end);
		}
		return fileName.substring(extPos+1);
	}
}

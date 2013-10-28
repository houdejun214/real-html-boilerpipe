package org.real.html.boilerpipe;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

public class HtmlContentImageExtractor {
	
	private static Set<String> ignoreFilesExtends = new HashSet<String>(Arrays.asList("gif","png"));
	
	private HttpClient client = new HttpClient();

	private ArticleExtractor extractor = ArticleExtractor.INSTANCE;
	
	private HTMLContentExtractor hh = HTMLContentExtractor.newExtractingInstance();
	
	public List<String> getImages(String url) throws HttpException, IOException, BoilerpipeProcessingException, SAXException{
		HttpMethod get = new GetMethod(url);
		List<String> imgLinks;
		try {
			client.executeMethod(get);
			String content = get.getResponseBodyAsString();
			get.releaseConnection();
			// parser document
			TextDocument textDoc = new BoilerpipeSAXInput(new InputSource(new StringReader(content))).getTextDocument();
			extractor.process(textDoc);
			InputSource htmlDoc = new InputSource(new StringReader(content));
			String mainContent = hh.process(textDoc, htmlDoc);
			
			Document doc = Jsoup.parse(mainContent);
			Elements imgs = doc.select("img");
			imgLinks = new ArrayList<String>();
			if(imgs != null && imgs.size()>0){
				for(Element img:imgs){
					String src = img.attr("src");
					if(!ignoreFilesExtends.contains(getExtension(src))){
						imgLinks.add(src);
					}
				}
			}
			return imgLinks;
		} finally {
			get.releaseConnection();
		}
		
	}
	
	public static String getExtension(String fileName){
		int extPos=fileName.lastIndexOf(".");
		if(extPos<0){
			return "";
		}
		return fileName.substring(extPos+1);
	}
}

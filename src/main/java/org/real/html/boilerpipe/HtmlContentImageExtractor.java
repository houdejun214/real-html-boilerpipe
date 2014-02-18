package org.real.html.boilerpipe;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.real.html.boilerpipe.util.URLCanonicalizer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.document.TextDocument;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import de.l3s.boilerpipe.sax.BoilerpipeSAXInput;

public class HtmlContentImageExtractor {
	
	private static Set<String> ignoreFilesExtends = new HashSet<String>(Arrays.asList("gif","png"));
	
	private CloseableHttpClient client = HttpClients.createDefault();

	private ArticleExtractor extractor = ArticleExtractor.INSTANCE;
	
	private HtmlContentExtractor hh = HtmlContentExtractor.newExtractingInstance();
	
	public Collection<String> getImages(String url) throws IOException, BoilerpipeProcessingException, SAXException{
		String content = downloadWebContent(url);
		// parser document
		Collection<String> imgLinks = getImagesByContent(content,url);
		return imgLinks;
	}
	
	private String downloadWebContent(String url) throws ParseException, IOException{
		HttpGet get = new HttpGet(url);
		get.addHeader("Connection", "close");
		get.addHeader("User-Agent","Mozilla/5.0 (Windows; U; Windows NT 6.1; pl; rv:1.9.1) Gecko/20090624 Firefox/3.5 (.NET CLR 3.5.30729)");
		CloseableHttpResponse response = client.execute(get);
		try {
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				if (statusCode != HttpStatus.SC_NOT_FOUND) {
					if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
						Header sheader = response.getFirstHeader("Location");
						if (sheader != null) {
							String redictUrl = sheader.getValue();
							redictUrl = URLCanonicalizer.getCanonicalURL(redictUrl, url);
							return downloadWebContent(redictUrl);
						} 
					}
				}
			}
			HttpEntity entity = response.getEntity();
			String content = EntityUtils.toString(entity);
			return content;
		} finally {
			response.close();
		}
	}
	
	public Collection<String> getImagesByContent(String content,String baseUrl) throws IOException, BoilerpipeProcessingException, SAXException{
		Set<String> imgLinks = new LinkedHashSet<String>();
		// parser document
		TextDocument textDoc = new BoilerpipeSAXInput(new InputSource(new StringReader(content))).getTextDocument();
		extractor.process(textDoc);
		InputSource htmlDoc = new InputSource(new StringReader(content));
		String mainContent = hh.process(textDoc, htmlDoc);
		
		// find images from SEO meta properties.
		Document doc = Jsoup.parse(content,baseUrl);
		Element imageProp = doc.select("meta[property=og:image]").first();
		if(imageProp!=null){
			String src = imageProp.attr("content");
			if(src!=null && !src.equals("")){
				String ext = getExtension(src);
				if(!ignoreFilesExtends.contains(ext)){
					imgLinks.add(src);
				}
			}
		}
		
		// find images from main content panel
		Document mainContentDoc = Jsoup.parse(mainContent,baseUrl);
		Elements imgs = mainContentDoc.select("img");
		if(imgs != null && imgs.size()>0){
			for(Element img:imgs){
				String src = img.absUrl("src");
				if(src==null || src.equals("")){
					// for some lazy-load images
					src = img.absUrl("data-original");
				}
				if(src!=null && !src.equals("")){
					String ext = getExtension(src);
					if(!ignoreFilesExtends.contains(ext)){
						imgLinks.add(src);
					}
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

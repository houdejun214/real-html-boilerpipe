package org.real.html.boilerpipe;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
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

import de.l3s.boilerpipe.BoilerpipeExtractor;
import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.document.Image;
import de.l3s.boilerpipe.document.TextDocument;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import de.l3s.boilerpipe.extractors.CommonExtractors;
import de.l3s.boilerpipe.sax.BoilerpipeSAXInput;
import de.l3s.boilerpipe.sax.ImageExtractor;

public class HtmlContentImageExtractor {
	
	private static Set<String> ignoreFilesExtends = new HashSet<String>(Arrays.asList("gif","png"));
	
	private CloseableHttpClient client = HttpClients.createDefault();

	private ArticleExtractor extractor = CommonExtractors.ARTICLE_EXTRACTOR;
	
	private BoilerpipeExtractor canolaExtractor = CommonExtractors.CANOLA_EXTRACTOR;
	
	private HtmlContentExtractor hh = HtmlContentExtractor.newExtractingInstance();
	
	private ImageExtractor imageExtractor = ImageExtractor.INSTANCE;
	
	public Collection<String> getImages(String url) throws IOException, BoilerpipeProcessingException, SAXException{
		String content = downloadWebContent(url);
		// parser document
		Collection<String> imgLinks = getImagesByContent(content,url);
		return imgLinks;
	}
	
	private String downloadWebContent(String url) throws ParseException, IOException {
		CloseableHttpResponse response = null;
		try {
			response = requestWebContent(url);
			int i=0;
			while(i<3 && response!=null){
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode != HttpStatus.SC_OK) {
					if (statusCode != HttpStatus.SC_NOT_FOUND) {
						if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
							Header sheader = response.getFirstHeader("Location");
							if (sheader != null) {
								String redictUrl = sheader.getValue();
								redictUrl = URLCanonicalizer.getCanonicalURL(redictUrl, url);
								response.close();
								i++;
								response =  requestWebContent(redictUrl);
								continue;
							} 
						}
					}
				}
				HttpEntity entity = response.getEntity();
				String content = EntityUtils.toString(entity);
				return content;
			}
			return "";
		} finally {
			if(response!=null){
				response.close();
			}
		}
	}

	private CloseableHttpResponse requestWebContent(String url) throws ParseException, IOException{
		System.out.println("download page:"+url);
		HttpGet get = new HttpGet(url);
		get.addHeader("Connection", "close");
		get.addHeader("User-Agent","Mozilla/5.0 (Windows; U; Windows NT 6.1; pl; rv:1.9.1) Gecko/20090624 Firefox/3.5 (.NET CLR 3.5.30729)");
		CloseableHttpResponse response = client.execute(get);
		return response;
	}
	
	public Collection<String> getImagesByContent(String content,String baseUrl) throws IOException, BoilerpipeProcessingException, SAXException{
		Set<String> imgLinks = new LinkedHashSet<String>();
		// parser document
		InputSource source = new InputSource(new StringReader(content));
		TextDocument textDoc = new BoilerpipeSAXInput(source).getTextDocument();
		canolaExtractor.process(textDoc);
		source.getCharacterStream().reset();
		List<Image> images = imageExtractor.process(textDoc, source);
		if(images==null || images.size()==0){
			extractor.process(textDoc);
			source.getCharacterStream().reset();
			images = imageExtractor.process(textDoc, source);
		}
		if(images!=null){
			for(Image img:images){
				String src = img.getSrc();
				src = absUrl(src,baseUrl);
				if(src!=null && !src.equals("")){
					String ext = getExtension(src);
					if(!ignoreFilesExtends.contains(ext)){
						imgLinks.add(src);
					}
				}
			}
		}
		
		// find images from main content panel
		String mainContent = hh.process(textDoc, content);
		Document mainContentDoc = Jsoup.parse(mainContent,baseUrl);
		Elements imgs = mainContentDoc.select("img");
		if(imgs != null && imgs.size()>0){
			for(Element img:imgs){
				String src = img.attr("src");
				if(src!=null && !src.equals("")){
					src = absUrl(src,baseUrl);
				}
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
	
	public String absUrl(String relUrl,String baseUri) {
        URL base;
        try {
            try {
                base = new URL(baseUri);
            } catch (MalformedURLException e) {
                // the base is unsuitable, but the attribute may be abs on its own, so try that
                URL abs = new URL(relUrl);
                return abs.toExternalForm();
            }
            // workaround: java resolves '//path/file + ?foo' to '//path/?foo', not '//path/file?foo' as desired
            if (relUrl.startsWith("?"))
                relUrl = base.getPath() + relUrl;
            URL abs = new URL(base, relUrl);
            return abs.toExternalForm();
        } catch (MalformedURLException e) {
            return "";
        }
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

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
//		http://www.malaysia-chronicle.com/index.php?option=com_k2&view=item&id=130192:britain-says-i-do-to-gay-marriage&Itemid=4#axzz2jZcrhr6C
//			http://justkhai.com/blog/ada-apa-dengan-kia-forte/
//			http://www.melvister.com/2012/08/mercun-bola-paling-bahaya-di-musim-perayaan.html
//			http://mediamalaya.com/super-teknologi-motosikal-digunakan-untuk-tunda-kenderaan-10-gambar/
//			http://www.mysumber.com/berita/orang-utan-digesa-berhenti-hisap-rokok
//			http://www.tiffinbiru.com/2011/03/last-warning-lagi-nihmesti-baca.html
//			http://www.ohtidak.com/oh-haiwan-kemalangan/
//			http://www.beritaterkini.my/final-uefa-champion-league-result-2010-video
//			http://blogserius.blogspot.sg/2012/08/serius-cool-pameran-sempena-50-tahun.html
//			http://blogserius.blogspot.sg/2012/01/serius-cool-infobar-c01-telefon-android.html
//			http://blogserius.blogspot.sg/2013/03/serius-cool-samsung-galaxy-s4-9-gambar.html
//			http://mediamalaya.com/super-wow-kolam-renang-terbesar-di-dunia-10-gambar/
//			http://mediamalaya.com/super-cantik-tasik-crescent-moon-di-china-13-gambar/
//			http://blogserius.blogspot.sg/2013/03/serius-klasik-welthauptstadt-germania.html
//			http://www.kakibising.com/2012/10/nazmi-faiz-dalam-permainan-video-pro-evolution-soccer-2013-dan-fifa-13.html
//			http://www.malaysia-chronicle.com/index.php?option=com_k2&view=item&id=86541:malaccalist-of-bn-candidates&Itemid=2#axzz2jZcrhr6C
//			http://www.illustrasiaku.com/gambar-sekitar-majlis-perkahwinan-jehan-miskin-dan-julie-camelia-rhee-di-carcosa-seri-negara-kl/
//			http://mediamalaya.com/gambar-marsha-milan-londoh-main-bowling/
//			http://www.melvister.com/2012/06/gambar-domina-pizza-ada-salib-di-setiawan-perak.html
//			http://blogserius.blogspot.sg/2012/08/serius-cool-alatan-perkemahan-4-dalam-1.html
//			http://blogserius.blogspot.sg/2013/07/serius-cool-ferra-tower-menara-dengan.html
//			http://www.tiffinbiru.com/2010/12/skodeng-dapur-mat-gebuada-brann.html
		String url = "http://www.tiffinbiru.com/2010/12/skodeng-dapur-mat-gebuada-brann.html";
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
		
		URL uri = new URL( url );
//
		final BoilerpipeExtractor extractor = CommonExtractors.ARTICLE_EXTRACTOR;
		
		final HtmlContentExtractor hh = HtmlContentExtractor.newExtractingInstance();
		String fileName = "/home/houdejun/highlighted.html";
		PrintWriter out = new PrintWriter(fileName, "UTF-8");
		out.println("<base href=\"" + url + "\" >");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text-html; charset=utf-8\" />");
		out.println(hh.process(uri, extractor));
		out.close();
		openFile(fileName);
		
//		HtmlContentImageExtractor extractor = new HtmlContentImageExtractor();
//		List<String> images = extractor.getImages(url);
//		for(String link:images){
//			System.out.println(link);
//		}
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

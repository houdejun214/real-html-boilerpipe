package org.real.html.boilerpipe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.real.html.boilerpipe.util.StringUtils;
import org.xml.sax.SAXException;

import de.l3s.boilerpipe.BoilerpipeProcessingException;

public class HtmlContentImageExtractorTest {
	
	private HtmlContentImageExtractor extractor = new HtmlContentImageExtractor();

	@Test
	public void testGetExtension() {
		String fileName = "http://sinag.com/1111.jpg";
		assertEquals("jpg", HtmlContentImageExtractor.getExtension(fileName));
		assertEquals("jpg", HtmlContentImageExtractor.getExtension("http://sinag.com/1111.jpg?p=123"));
	}
	
	
	@Test
	public void testCase() throws IOException, BoilerpipeProcessingException, SAXException {
		String[] urls = new String[]{
				"http://staging.innity-asia.com/m4/rnd/visenze/index.html",
				"http://staging.innity-asia.com/m4/rnd/visenze/index02.html",
				"http://staging.innity-asia.com/m4/rnd/visenze/index03.html",
				"http://www.malaysia-chronicle.com/index.php?option=com_k2&view=item&id=130192:britain-says-i-do-to-gay-marriage&Itemid=4#axzz2jZcrhr6C",
				"http://justkhai.com/blog/ada-apa-dengan-kia-forte/",
				"http://www.melvister.com/2012/08/mercun-bola-paling-bahaya-di-musim-perayaan.html",
				"http://mediamalaya.com/super-teknologi-motosikal-digunakan-untuk-tunda-kenderaan-10-gambar/",
				"http://www.mysumber.com/berita/orang-utan-digesa-berhenti-hisap-rokok",
				"http://www.tiffinbiru.com/2011/03/last-warning-lagi-nihmesti-baca.html",
				"http://www.ohtidak.com/oh-haiwan-kemalangan/",
				"http://blogserius.blogspot.sg/2012/08/serius-cool-pameran-sempena-50-tahun.html",
				"http://blogserius.blogspot.sg/2012/01/serius-cool-infobar-c01-telefon-android.html",
				"http://blogserius.blogspot.sg/2013/03/serius-cool-samsung-galaxy-s4-9-gambar.html",
				"http://mediamalaya.com/super-wow-kolam-renang-terbesar-di-dunia-10-gambar/",
				"http://mediamalaya.com/super-cantik-tasik-crescent-moon-di-china-13-gambar/",
				"http://blogserius.blogspot.sg/2013/03/serius-klasik-welthauptstadt-germania.html",
				"http://www.kakibising.com/2012/10/nazmi-faiz-dalam-permainan-video-pro-evolution-soccer-2013-dan-fifa-13.html",
				"http://www.malaysia-chronicle.com/index.php?option=com_k2&view=item&id=86541:malaccalist-of-bn-candidates&Itemid=2#axzz2jZcrhr6C",
				"http://www.illustrasiaku.com/gambar-sekitar-majlis-perkahwinan-jehan-miskin-dan-julie-camelia-rhee-di-carcosa-seri-negara-kl/",
				"http://mediamalaya.com/gambar-marsha-milan-londoh-main-bowling/",
				"http://www.melvister.com/2012/06/gambar-domina-pizza-ada-salib-di-setiawan-perak.html",
				"http://blogserius.blogspot.sg/2012/08/serius-cool-alatan-perkemahan-4-dalam-1.html",
				"http://blogserius.blogspot.sg/2013/07/serius-cool-ferra-tower-menara-dengan.html",
				"http://www.tiffinbiru.com/2010/12/skodeng-dapur-mat-gebuada-brann.html",
		};
		for(String url:urls){
			List<String> images = extractor.getImages(url);
			assertTrue(url+" can not be extract",images.size()>0);
			System.out.println(StringUtils.join(images, ","));
		}
	}
}

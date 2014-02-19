package org.real.html.boilerpipe;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Collection;

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
				
				"http://www.straitstimes.com/breaking-news/singapore/story/median-household-income-rose-7870-last-year-inequality-down-20140218",
				"http://www.straitstimes.com/breaking-news/singapore/story/be-better-friend-and-put-your-mobile-phone-friend-mode-20140218",
				"http://www.straitstimes.com/breaking-news/singapore/story/manpower-miinistry-step-safety-enforcement-construction-industry-20140",
				"http://stcommunities.straitstimes.com/movies/2014/02/18/queen-hosts-british-film-royalty-palace",
				"http://stcommunities.straitstimes.com/show/2014/02/18/miami-museum-visitor-charged-breaking-126m-ai-weiwei-vase",
				"http://www.pinterest.com/pin/378654281139653100/",
				"http://www.pinterest.com/pin/378654281141264757/",
				"http://www.sgcarmart.com/used_cars/info.php?ID=387061&DL=1000",
				"http://www.sgcarmart.com/new_cars/newcars_overview.php?CarCode=10732",
				"http://www.scmp.com/news/china/article/1430102/i-thought-it-was-home-depot-pot-painter-charged-smashing-museums-us1m-ai",
				"http://www.scmp.com/news/hong-kong/article/1429896/hong-kong-children-happier-less-able-handle-adversity-survey-finds",
				"http://tech.sina.com.cn/mobile/n/2014-02-18/08049170370.shtml",
				"http://item.taobao.com/item.htm?id=37022645349&ali_trackid=2:mm_15890324_2192376_11153435,0:1392705944_6k3_516481380&clk1=eb4f7d28cd2a3882f56db91eee0a36d6&spm=a3300.2168033.5636241.5.IRO46g",
				"http://detail.tmall.com/item.htm?id=20117869226",
				"http://entertainment.xin.msn.com/en/celebrity/buzz/asia/flumpool-treats-fans-to-special-showcase-in-singapore",
				"http://entertainment.xin.msn.com/en/celebrity/buzz/hollywood/stars-on-the-catwalk-11",
				"http://news.insing.com/feature/a-case-of-molest-on-the-mrt/id-fe393101",
				"http://stylplus.insing.com/feature/alice-olivia-to-open-in-singapore/id-ea393101",
				"http://movies.insing.com/gallery/12-movie-stars-then-and-now/id-4c2e3101/",
				"http://stylplus.insing.com/gallery/stop-habits-lose-weight/id-54400400/slide",
				"http://stylplus.insing.com/feature/hair-treatments-from-the-supermarket/id-84643f00",
				"http://slide.ent.sina.com.cn/star/slide_4_704_75377.html#p=1",
				"http://ent.sina.com.cn/s/h/2014-02-18/07474098254.shtml",
				"http://slide.ent.sina.com.cn/star/h/slide_4_704_75375.html#p=1",
				"http://slide.fashion.sina.com.cn/s/slide_24_55010_42042.html#p=1",
				"http://fashion.sina.com.cn/b/ha/2014-02-18/071735466.shtml",
				"http://www.meilishuo.com/share/item/2379882175?d_r=0.1.1.1",
				"http://www.clozette.co/community/browse/e1b4886a8cd34addbff75b8a1595ddac/stylespresso",
				"http://www.glamasia.com/2014/02/18/trend-alert-tea-length-skirts/",
				"http://clozette.glam.jp/community/browse/5e995821db274dc4a05b18bf68b53350/GlamClozette",
		};
		for(String url:urls){
			Collection<String> images = extractor.getImages(url);
			System.out.print("\t"+images.size()+":");
			if(images.size()<=0){
				System.out.println("["+url+"] can not be extract");
			}else{
				System.out.println(StringUtils.join(images, ","));
			}
		}
	}
	
	@Test
	public void testCase1() throws IOException, BoilerpipeProcessingException, SAXException {
		String[] urls = new String[]{
				"http://www.scmp.com/news/china/article/1430102/i-thought-it-was-home-depot-pot-painter-charged-smashing-museums-us1m-ai",
				"http://www.scmp.com/news/hong-kong/article/1429896/hong-kong-children-happier-less-able-handle-adversity-survey-finds",
		};
		for(String url:urls){
			Collection<String> images = extractor.getImages(url);
			if(images.size()<=0){
				System.out.println("["+url+"] can not be extract");
			}else{
				System.out.println(StringUtils.join(images, ","));
			}
		}
	}
	
	@Test
	public void testCaseTaobao() throws IOException, BoilerpipeProcessingException, SAXException {
		String[] urls = new String[]{
				"http://item.taobao.com/item.htm?id=37022645349&ali_trackid=2:mm_15890324_2192376_11153435,0:1392705944_6k3_516481380&clk1=eb4f7d28cd2a3882f56db91eee0a36d6&spm=a3300.2168033.5636241.5.IRO46g",
		};
		for(String url:urls){
			Collection<String> images = extractor.getImages(url);
			if(images.size()<=0){
				System.out.println("["+url+"] can not be extract");
			}else{
				System.out.println(StringUtils.join(images, ","));
			}
		}
	}
}

package org.real.html.boilerpipe.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class ImageFilesDownloaderTest {
	
	private ImageFilesDownloader downloader = null;
	
	@Before
	public void setup(){
		downloader = new ImageFilesDownloader();
	}

	@Test
	public void test() {
		List<String> images = Arrays.asList("http://www.lollipop.sg/sites/default/files/public/article/images/featured/jademain2.jpg",
				"http://www.lollipop.sg/sites/all/themes/lollipop/images/article_camera_icon.jpg",
				"http://www.lollipop.sg/sites/default/files/public/styles/lollipop_multimedia_photo_gallery/public/article/images/gallery/jr%20%281%29.jpg?itok=SIo-IYPl",
				"http://www.lollipop.sg/sites/default/files/public/styles/lollipop_multimedia_photo_gallery/public/article/images/gallery/jr%20%282%29.jpg?itok=2ri1vO3Q",
				"http://www.lollipop.sg/sites/default/files/public/styles/lollipop_multimedia_photo_gallery/public/article/images/gallery/jr%20%283%29.jpg?itok=c7Xn7ZSE",
				"http://www.lollipop.sg/sites/default/files/public/styles/lollipop_multimedia_photo_gallery/public/article/images/gallery/jr%20%284%29.jpg?itok=sC8FWYkF",
				"http://www.lollipop.sg/sites/default/files/public/styles/lollipop_multimedia_photo_gallery/public/article/images/gallery/jr%20%285%29.jpg?itok=ebCH8K3I",
				"http://www.lollipop.sg/sites/default/files/public/styles/lollipop_multimedia_photo_gallery/public/article/images/gallery/jr%20%286%29.jpg?itok=3VJExcjY",
				"http://www.lollipop.sg/sites/default/files/public/styles/lollipop_multimedia_photo_gallery/public/article/images/gallery/jr%20%287%29.jpg?itok=VKIvaAQz",
				"http://www.lollipop.sg/sites/default/files/public/styles/lollipop_multimedia_photo_gallery/public/article/images/gallery/jr%20%288%29.jpg?itok=xbyU4L_v",
				"http://www.lollipop.sg/sites/default/files/public/styles/lollipop_multimedia_photo_gallery/public/article/images/gallery/jr%20%289%29.jpg?itok=XZXNRKEd",
				"http://www.lollipop.sg/sites/default/files/public/styles/lollipop_multimedia_photo_gallery/public/article/images/gallery/jr%20%2810%29.jpg?itok=ff0hFAVz",
				"http://www.lollipop.sg/sites/default/files/public/styles/lollipop_multimedia_photo_gallery/public/article/images/gallery/jr%20%2811%29.jpg?itok=AAEIyKRF",
				"http://www.lollipop.sg/sites/default/files/public/styles/lollipop_multimedia_photo_gallery/public/article/images/gallery/jr%20%2812%29.jpg?itok=y4qYQnCt",
				"http://www.lollipop.sg/sites/default/files/public/styles/lollipop_multimedia_photo_gallery/public/article/images/gallery/jr%20%2813%29.jpg?itok=PNSkEs_4",
				"http://www.lollipop.sg/sites/all/themes/lollipop/images/moodmeter_45x55_lol.jpg",
				"http://www.lollipop.sg/sites/all/themes/lollipop//images/sharemood_fb.jpg",
				"http://www.lollipop.sg/sites/all/themes/lollipop//images/sharemood_t.jpg",
				"http://www.lollipop.sg/sites/all/themes/lollipop/images/moodmeter_45x55_omg.jpg",
				"http://www.lollipop.sg/sites/all/themes/lollipop//images/sharemood_fb.jpg",
				"http://www.lollipop.sg/sites/all/themes/lollipop//images/sharemood_t.jpg");
		Map<String, byte[]> downloads = downloader.download(images);
		System.out.println(downloads.size());
	}

	@Test
	public void testSynchrolize() {
		List<String> images = Arrays.asList("http://www.lollipop.sg/sites/default/files/public/article/images/featured/jademain2.jpg",
				"http://www.lollipop.sg/sites/all/themes/lollipop/images/article_camera_icon.jpg",
				"http://www.lollipop.sg/sites/default/files/public/styles/lollipop_multimedia_photo_gallery/public/article/images/gallery/jr%20%281%29.jpg?itok=SIo-IYPl",
				"http://www.lollipop.sg/sites/default/files/public/styles/lollipop_multimedia_photo_gallery/public/article/images/gallery/jr%20%282%29.jpg?itok=2ri1vO3Q",
				"http://www.lollipop.sg/sites/default/files/public/styles/lollipop_multimedia_photo_gallery/public/article/images/gallery/jr%20%283%29.jpg?itok=c7Xn7ZSE",
				"http://www.lollipop.sg/sites/default/files/public/styles/lollipop_multimedia_photo_gallery/public/article/images/gallery/jr%20%284%29.jpg?itok=sC8FWYkF",
				"http://www.lollipop.sg/sites/default/files/public/styles/lollipop_multimedia_photo_gallery/public/article/images/gallery/jr%20%285%29.jpg?itok=ebCH8K3I",
				"http://www.lollipop.sg/sites/default/files/public/styles/lollipop_multimedia_photo_gallery/public/article/images/gallery/jr%20%286%29.jpg?itok=3VJExcjY",
				"http://www.lollipop.sg/sites/default/files/public/styles/lollipop_multimedia_photo_gallery/public/article/images/gallery/jr%20%287%29.jpg?itok=VKIvaAQz",
				"http://www.lollipop.sg/sites/default/files/public/styles/lollipop_multimedia_photo_gallery/public/article/images/gallery/jr%20%288%29.jpg?itok=xbyU4L_v",
				"http://www.lollipop.sg/sites/default/files/public/styles/lollipop_multimedia_photo_gallery/public/article/images/gallery/jr%20%289%29.jpg?itok=XZXNRKEd",
				"http://www.lollipop.sg/sites/default/files/public/styles/lollipop_multimedia_photo_gallery/public/article/images/gallery/jr%20%2810%29.jpg?itok=ff0hFAVz",
				"http://www.lollipop.sg/sites/default/files/public/styles/lollipop_multimedia_photo_gallery/public/article/images/gallery/jr%20%2811%29.jpg?itok=AAEIyKRF",
				"http://www.lollipop.sg/sites/default/files/public/styles/lollipop_multimedia_photo_gallery/public/article/images/gallery/jr%20%2812%29.jpg?itok=y4qYQnCt",
				"http://www.lollipop.sg/sites/default/files/public/styles/lollipop_multimedia_photo_gallery/public/article/images/gallery/jr%20%2813%29.jpg?itok=PNSkEs_4",
				"http://www.lollipop.sg/sites/all/themes/lollipop/images/moodmeter_45x55_lol.jpg",
				"http://www.lollipop.sg/sites/all/themes/lollipop//images/sharemood_fb.jpg",
				"http://www.lollipop.sg/sites/all/themes/lollipop//images/sharemood_t.jpg",
				"http://www.lollipop.sg/sites/all/themes/lollipop/images/moodmeter_45x55_omg.jpg",
				"http://www.lollipop.sg/sites/all/themes/lollipop//images/sharemood_fb.jpg",
				"http://www.lollipop.sg/sites/all/themes/lollipop//images/sharemood_t.jpg");
		Map<String, byte[]> downloads = downloader.downloadLinear(images);
		System.out.println(downloads.size());
	}
}

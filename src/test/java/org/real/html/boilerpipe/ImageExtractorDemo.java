package org.real.html.boilerpipe;

import java.net.URL;
import java.util.Collections;
import java.util.List;

import de.l3s.boilerpipe.BoilerpipeExtractor;
import de.l3s.boilerpipe.document.Image;
import de.l3s.boilerpipe.extractors.CommonExtractors;
import de.l3s.boilerpipe.sax.ImageExtractor;

public final class ImageExtractorDemo {
	public static void main(String[] args) throws Exception {
		URL url = new URL(
				"http://clozette.glam.jp/community/browse/5e995821db274dc4a05b18bf68b53350/GlamClozette");
		
		// choose from a set of useful BoilerpipeExtractors...
		final BoilerpipeExtractor extractor = CommonExtractors.ARTICLE_EXTRACTOR;
//		final BoilerpipeExtractor extractor = CommonExtractors.DEFAULT_EXTRACTOR;
//		final BoilerpipeExtractor extractor = CommonExtractors.CANOLA_EXTRACTOR;
//		final BoilerpipeExtractor extractor = CommonExtractors.LARGEST_CONTENT_EXTRACTOR;
		final ImageExtractor ie = ImageExtractor.INSTANCE;
		
		List<Image> imgUrls = ie.process(url, extractor);
		
		// automatically sorts them by decreasing area, i.e. most probable true positives come first
		Collections.sort(imgUrls);
		
		for(Image img : imgUrls) {
			System.out.println("* "+img);
		}

	}
}
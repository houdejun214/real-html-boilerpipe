package org.real.html.boilerpipe;

import static org.junit.Assert.*;

import org.junit.Test;

public class HtmlContentImageExtractorTest {

	@Test
	public void testGetExtension() {
		String fileName = "http://sinag.com/1111.jpg";
		assertEquals("jpg", HtmlContentImageExtractor.getExtension(fileName));
		assertEquals("jpg", HtmlContentImageExtractor.getExtension("http://sinag.com/1111.jpg?p=123"));
	}
	
}

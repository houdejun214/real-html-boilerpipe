package org.real.html.boilerpipe;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.apache.xerces.parsers.AbstractSAXParser;
import org.cyberneko.html.HTMLConfiguration;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import de.l3s.boilerpipe.BoilerpipeExtractor;
import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.document.TextBlock;
import de.l3s.boilerpipe.document.TextDocument;
import de.l3s.boilerpipe.sax.BoilerpipeSAXInput;
import de.l3s.boilerpipe.sax.HTMLDocument;
import de.l3s.boilerpipe.sax.HTMLFetcher;

public final class HtmlContentExtractor {

	/**
	 * Creates a new {@link HtmlContentExtractor}, which is set-up to return only the
	 * extracted HTML text, including enclosed markup.
	 */
	public static HtmlContentExtractor newExtractingInstance() {
		return new HtmlContentExtractor(true);
	}

	private HtmlContentExtractor(final boolean extractHTML) {
	}

	/**
	 * Processes the given {@link TextDocument} and the original HTML text (as a
	 * String).
	 * 
	 * @param doc
	 *            The processed {@link TextDocument}.
	 * @param origHTML
	 *            The original HTML document.
	 * @throws BoilerpipeProcessingException
	 */
	public String process(final TextDocument doc, final String origHTML)
			throws BoilerpipeProcessingException {
		return process(doc, new InputSource(new StringReader(origHTML)));
	}

	/**
	 * Processes the given {@link TextDocument} and the original HTML text (as
	 * an {@link InputSource}).
	 * 
	 * @param doc
	 *            The processed {@link TextDocument}.
	 * @param is
	 *            The original HTML document.
	 * @throws BoilerpipeProcessingException
	 */
	public String process(final TextDocument doc, final InputSource is)
			throws BoilerpipeProcessingException {
		final Implementation implementation = new Implementation();
		implementation.process(doc, is);
		String html = implementation.html.toString();
		return html;
	}

	public String process(final URL url, final BoilerpipeExtractor extractor)
			throws IOException, BoilerpipeProcessingException, SAXException {
		final HTMLDocument htmlDoc = HTMLFetcher.fetch(url);

		final TextDocument doc = new BoilerpipeSAXInput(htmlDoc.toInputSource())
				.getTextDocument();
		extractor.process(doc);

		final InputSource is = htmlDoc.toInputSource();

		return process(doc, is);
	}

	private boolean outputHighlightOnly = true;

	/**
	 * If true, only HTML enclosed within highlighted content will be returned
	 */
	public boolean isOutputHighlightOnly() {
		return outputHighlightOnly;
	}

	/**
	 * Sets whether only HTML enclosed within highlighted content will be
	 * returned, or the whole HTML document.
	 */
	public void setOutputHighlightOnly(boolean outputHighlightOnly) {
		this.outputHighlightOnly = outputHighlightOnly;
	}

	private abstract static class TagAction {
		void beforeStart(final Implementation instance, final String localName) {
		}

		void afterStart(final Implementation instance, final String localName) {
		}

		void beforeEnd(final Implementation instance, final String localName) {
		}

		void afterEnd(final Implementation instance, final String localName) {
		}
	}

	private static final TagAction TA_IGNORABLE_ELEMENT = new TagAction() {
		void beforeStart(final Implementation instance, final String localName) {
			instance.inIgnorableElement++;
		}

		void afterEnd(final Implementation instance, final String localName) {
			instance.inIgnorableElement--;
		}
	};

	private static final TagAction TA_HEAD = new TagAction() {
		void beforeStart(final Implementation instance, final String localName) {
			instance.inIgnorableElement++;
		}

		void beforeEnd(final Implementation instance, String localName) {
		}

		void afterEnd(final Implementation instance, final String localName) {
			instance.inIgnorableElement--;
		}
	};
	private static Map<String, TagAction> TAG_ACTIONS = new HashMap<String, TagAction>();
	static {
		TAG_ACTIONS.put("STYLE", TA_IGNORABLE_ELEMENT);
		TAG_ACTIONS.put("SCRIPT", TA_IGNORABLE_ELEMENT);
		TAG_ACTIONS.put("OPTION", TA_IGNORABLE_ELEMENT);
		TAG_ACTIONS.put("NOSCRIPT", TA_IGNORABLE_ELEMENT);
		TAG_ACTIONS.put("OBJECT", TA_IGNORABLE_ELEMENT);
		TAG_ACTIONS.put("EMBED", TA_IGNORABLE_ELEMENT);
		TAG_ACTIONS.put("APPLET", TA_IGNORABLE_ELEMENT);
		// NOTE: you might want to comment this out:
		TAG_ACTIONS.put("LINK", TA_IGNORABLE_ELEMENT);

		TAG_ACTIONS.put("HEAD", TA_HEAD);
	}

	private final class Implementation extends AbstractSAXParser implements
			ContentHandler {
		HtmlElement html = null;
		
		private static final int TOLERATE_DEPTH = 3;

		private int inIgnorableElement = 0;
		private int characterElementIdx = 0;
		private final BitSet contentBitSet = new BitSet();
		private final Stack<HtmlElement> stack = new Stack<HtmlElement>();
		
		Implementation() {
			super(new HTMLConfiguration());
			setContentHandler(this);
		}

		void process(final TextDocument doc, final InputSource is)
				throws BoilerpipeProcessingException {
			for (TextBlock block : doc.getTextBlocks()) {
				if (block.isContent()) {
					final BitSet bs = block.getContainedTextElements();
					if (bs != null) {
						contentBitSet.or(bs);
					}
				}
			}
			try {
				parse(is);
			} catch (SAXException e) {
				throw new BoilerpipeProcessingException(e);
			} catch (IOException e) {
				throw new BoilerpipeProcessingException(e);
			}
		}

		public void endDocument() throws SAXException {
		}

		public void endPrefixMapping(String prefix) throws SAXException {
		}

		public void ignorableWhitespace(char[] ch, int start, int length)
				throws SAXException {
		}

		public void processingInstruction(String target, String data)
				throws SAXException {
		}

		public void setDocumentLocator(Locator locator) {
		}

		public void skippedEntity(String name) throws SAXException {
		}

		public void startDocument() throws SAXException {
		}

		public void startElement(String uri, String localName, String qName,
				Attributes atts) throws SAXException {
			TagAction ta = TAG_ACTIONS.get(localName);
			if (ta != null) {
				ta.beforeStart(this, localName);
			}
			try {
				if (inIgnorableElement == 0) {
					/***************************************/
					HtmlElement parent = html;
					int depth = 1;
					if(parent != null){
						stack.add(parent);
						depth = parent.getDepth()+1;
					}
					html = new HtmlElement(depth);
					HtmlElement latestFoundParent = getLatestFoundParent();
					if(latestFoundParent!=null && latestFoundParent.isInclude()){
						if(latestFoundParent.getFoundInChildDepth() - html.getDepth()<=TOLERATE_DEPTH){
							html.setInclude();
							html.setFoundInChildDepth(latestFoundParent.getFoundInChildDepth());
						}
					}
					/***************************************/
					html.append('<');
					html.append(qName);
//					if (!ignoreAttrs) {
						final int numAtts = atts.getLength();
						for (int i = 0; i < numAtts; i++) {
							final String attr = atts.getQName(i);
							final String value = atts.getValue(i);
							html.append(' ');
							html.append(attr);
							html.append("=\"");
							html.append(xmlEncode(value));
							html.append("\"");
						}
//					}
					html.append('>');
				}
			} finally {
				if (ta != null) {
					ta.afterStart(this, localName);
				}
			}
		}
		
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			TagAction ta = TAG_ACTIONS.get(localName);
			if (ta != null) {
				ta.beforeEnd(this, localName);
			}

			try {
				if (inIgnorableElement == 0) {
					html.append("</");
					html.append(qName);
					html.append('>');
					/*************************/
					
					if(!this.stack.empty()){
						HtmlElement parent = this.stack.pop();
						if(html.isFoundInCurrent() ){
							parent.append(html);
							parent.setInclude();
							parent.setFoundInChildDepth(html.getDepth());
							updateParents(html.getDepth());
						}else if(html.isInclude()){
							parent.append(html);
							parent.setInclude();
						}
						html = parent;
					}
					/*************************/
				}
			} finally {
				if (ta != null) {
					ta.afterEnd(this, localName);
				}
			}
			
		}
		
		private HtmlElement getLatestFoundParent(){
			int size = this.stack.size();
			for(int i=size-1;i>=size-1-TOLERATE_DEPTH && i>=0;i--){
				HtmlElement el = this.stack.get(i);
				if(el.isInclude()){
					return el;
				}
			}
			return null;
		}
		
		private void updateParents(int foundDepth){
			int size = this.stack.size();
			for(int i=size-1;i>=0;i--){
				HtmlElement el = this.stack.get(i);
				if(el.getFoundInChildDepth()>foundDepth){
					el.setFoundInChildDepth(foundDepth);
				}
			}
		}

		public void characters(char[] ch, int start, int length)
				throws SAXException {
			characterElementIdx++;
			if (inIgnorableElement == 0) {

				boolean highlight = contentBitSet.get(characterElementIdx);

				if (!highlight) {
					return;
				}
				html.setFoundInCurrent(true);
//				if (highlight) {
//					//html.append(preHighlight);
//				}
				html.append(xmlEncode(String.valueOf(ch, start, length)));
//				if (highlight) {
//					//html.append(postHighlight);
//				}
			}
		}

		public void startPrefixMapping(String prefix, String uri)
				throws SAXException {
		}

	}

	private static String xmlEncode(final String in) {
		if (in == null) {
			return "";
		}
		char c;
		StringBuilder out = new StringBuilder(in.length());

		for (int i = 0; i < in.length(); i++) {
			c = in.charAt(i);
			switch (c) {
			case '<':
				out.append("&lt;");
				break;
			case '>':
				out.append("&gt;");
				break;
			case '&':
				out.append("&amp;");
				break;
			case '"':
				out.append("&quot;");
				break;
			default:
				out.append(c);
			}
		}

		return out.toString();
	}

}

package org.real.html.boilerpipe;

public class HtmlElement {

	private final StringBuilder html ;
	
	private int depth = 1;
	
	private boolean foundInCurrent = false;
	
	private boolean include = false;
	
	private int foundInChildDepth = Integer.MAX_VALUE;
	
	public HtmlElement(){
		this.html = new StringBuilder();
	}
	
	public HtmlElement(int depth){
		this.html = new StringBuilder();
		this.depth = depth;
	}
	
	public HtmlElement append(String str) {
		this.html.append(str);
        return this;
    }
	
	public HtmlElement append(char c) {
		this.html.append(c);
	    return this;
	}
	
	public void append(HtmlElement html2) {
		this.html.append(html2.html);
	}

	public boolean isFoundInCurrent() {
		return foundInCurrent;
	}

	public void setFoundInCurrent(boolean foundInCurrent) {
		this.foundInCurrent = foundInCurrent;
	}
	
	public int getDepth() {
		return depth;
	}

	public int getFoundInChildDepth() {
		return foundInChildDepth;
	}

	public void setFoundInChildDepth(int foundInChildDepth) {
		if(this.foundInChildDepth<=0 || this.foundInChildDepth>foundInChildDepth){
			this.foundInChildDepth = foundInChildDepth;
		}
	}

	public boolean isInclude() {
		return include;
	}

	public void setInclude() {
		this.include = true;
	}

	@Override
	public String toString() {
		return html.toString();
	}
}

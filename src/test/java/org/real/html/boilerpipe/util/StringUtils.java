package org.real.html.boilerpipe.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class StringUtils {
	
	public static String valueOf(Object obj){
	  return (obj == null) ? "" : obj.toString();
  	}
  
	public static String join(String[] arrays, String separator) {
		return join(Arrays.asList(arrays),separator);
	}

	public static String join(Collection<?> list, String separator) {
		if(list==null){
			return null;
		}
		Iterator<?> iterator = list.iterator();
		// handle null, zero and one elements before building a buffer
		Object first = iterator.next();
		if (!iterator.hasNext()) {
			return valueOf(first);
		}
		// two or more elements
		StringBuffer buf = new StringBuffer(256); // Java default is 16,
													// probably too small
		if (first != null) {
			buf.append(first);
		}
		while (iterator.hasNext()) {
			if (separator != null) {
				buf.append(separator);
			}
			Object obj = iterator.next();
			if (obj != null) {
				buf.append(obj);
			}
		}
		return buf.toString();
	}
}

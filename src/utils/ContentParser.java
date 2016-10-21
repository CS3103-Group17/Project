package utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringEscapeUtils;

public class ContentParser {

	public static String parseSummaryContent(String content){
		
		content = StringEscapeUtils.unescapeXml(content);
		
		String pattern1 = "<p><b>";
		
		if(content.indexOf(pattern1) == -1)
			return content;
		
		return content.substring(content.indexOf(pattern1));
	}
	
	public static String parseSectionContent(String content){
		
		content = StringEscapeUtils.unescapeXml(content);
		
		String pattern1 = "<span class=\"mw-editsection\"><span class=\"mw-editsection-bracket\">";
		String pattern2 = "</span></span>";
		
		return content.substring(0, content.indexOf(pattern1))+content.substring(content.indexOf(pattern2)+pattern2.length());
	}
	
	public static String encodeIntoURL(String keyword){
		try {
			return URLEncoder.encode(keyword, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}

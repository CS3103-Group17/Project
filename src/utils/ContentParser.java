package utils;

import org.apache.commons.lang3.StringEscapeUtils;

public class ContentParser {

	public String parseSummaryContent(String content){
		
		content = StringEscapeUtils.unescapeXml(content);
		
		String pattern1 = "<p><b>";
		
		
		return content.substring(content.indexOf(pattern1));
	}
	
	public String parseSectionContent(String content){
		
		content = StringEscapeUtils.unescapeXml(content);
		
		String pattern1 = "<span class=\"mw-editsection\"><span class=\"mw-editsection-bracket\">";
		String pattern2 = "</span></span>";
		
		return content.substring(0, content.indexOf(pattern1))+content.substring(content.indexOf(pattern2)+pattern2.length());
	}
}

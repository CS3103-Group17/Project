package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import util.ContentParser;

public class ContentParserTest {

	@Test
	public void parseSummaryContentTest() {
		String s = "j;flsdjabfjdlsabfdldsajbfldjsa<p><b>San Francisco";
		String expectedOutput = "<p><b>San Francisco";
		
		assertEquals(expectedOutput, ContentParser.parseSummaryContent(s));
	}
	
	@Test
	public void parseSectionContentTest() {
		String s = "<h2><span class=\"mw-headline\" id=\"Understand\">Understand</span><span class=\"mw-editsection\"><span class=\"mw-editsection-bracket\">[</span><a href=\"/wiki/en/index.php?title=A_seaside_stroll_in_Helsinki&amp;action=edit&amp;section=1\" title=\"Edit section: Understand\">edit</a><span class=\"mw-editsection-bracket\">]</span></span></h2> <p>This stroll past some of Helsinki's sights is about 7 km fr";
	
		String expectedOutput = "<h2><span class=\"mw-headline\" id=\"Understand\">Understand</span></h2> <p>This stroll past some of Helsinki's sights is about 7 km fr";
		
		assertEquals(expectedOutput, ContentParser.parseSectionContent(s));
	}
	
	@Test
	public void encodeIntoURLTest(){
		String s = "San Francisco";
			
		assertEquals("San+Francisco", ContentParser.encodeIntoURL(s));
	}

}

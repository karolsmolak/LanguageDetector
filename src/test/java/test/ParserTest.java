package test;

import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.Test;

import repository.Parser;

import static org.junit.Assert.assertEquals;

public class ParserTest {

	private static String testFilePath = "testFile";
	private static String testFileContent = "first\t sentence.\nsecond  sentence\nthird sentence";
	
	private Parser testParser = new Parser();
	
	@Test
	public void testFormatString() {
		assertEquals("first sentence. second sentence third sentence", testParser.formatString(testFileContent));
	}

	@Test
	public void testReadFiletoString() {
		try {
			assertEquals("first\t sentence.\nsecond  sentence\nthird sentence", 
					testParser.readFiletoString(testFilePath, Charset.forName("UTF-8")));
		} catch(IOException e) {
			
		}
	}

}
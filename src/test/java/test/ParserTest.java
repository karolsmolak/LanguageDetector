package test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import utility.Parser;

public class ParserTest {

	private static String testFilePath = "testFile";
	private static String testFileContent = "first\t sentence.\nsecond  sentence\nthird sentence";
	
	@Test
	public void testFormatString() {
		assertEquals("first sentence. second sentence third sentence", Parser.formatString(testFileContent));
	}

	@Test
	public void testReadFiletoString() {
		try {
			assertEquals("first\t sentence.\nsecond  sentence\nthird sentence", 
					Parser.readFiletoString(testFilePath, Charset.forName("UTF-8")));
		} catch(IOException e) {
			
		}
	}

	@Test
	public void testReadFileLineByLine(){
		List<String> expectedResult = Arrays.asList("first\t sentence.", "second  sentence", "third sentence");
		assertEquals(expectedResult, Parser.readFileLineByLine(testFilePath));
	}
}
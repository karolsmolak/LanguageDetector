package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ProfileTest.class, LanguageRecognizerTest.class, ParserTest.class, RepositoryTest.class, RecognitionResultTest.class })
public class AllTests {

}

package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import config.Config;
import repository.IRepository;
import services.IProfileRecognizer;
import services.Profile;

public class LanguageRecognizerTest {
	@Autowired
	private IProfileRecognizer testRecognizer;
	
	@Qualifier("testsRepository")
	private IRepository testRepository;
	
	@Test
	public void testRecognize() {
		for(Profile profile : testRepository.getKnownProfiles()) {
			assertEquals(
				profile.getName(), 
				testRecognizer.recognize(profile.getBaseString()).getWinner().getName());
		}
	}

}
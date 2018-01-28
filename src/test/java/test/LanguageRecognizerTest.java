package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.Config;
import repository.IRepository;
import services.IProfileRecognizer;
import services.Profile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=Config.class)
public class LanguageRecognizerTest {
	
	@Autowired
	private IProfileRecognizer testRecognizer;
	
	@Autowired
	@Qualifier("testRepository")
	private IRepository testRepository;
	
	@Test
	public void testRecognize() {
		for(Profile profile : testRepository.getKnownProfiles()) {
			assertEquals(
				profile.getName(), 
				testRecognizer.matchToKnownProfiles(profile).getWinner().getName());
		}
	}

}
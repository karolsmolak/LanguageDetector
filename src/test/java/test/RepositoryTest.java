package test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.Config;
import repository.IRepository;
import services.Profile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=Config.class)
public class RepositoryTest {
	@Autowired
	private IRepository repository;
	
	@Value("${numberOfNgramsConsidered}")
	private int numberOfNgramsConsidered;
	
	@Test
	public void availableProfilesLongEnough() {
		List<Profile> availableProfiles = repository.getKnownProfiles();
		for(Profile profile : availableProfiles) {
			assertTrue(profile.getSize() >= numberOfNgramsConsidered);
		}
	}
}

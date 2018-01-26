package test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import repository.IRepository;
import services.Profile;

public class RepositoryTest {
	@Autowired
	IRepository repository;
	
	@Test
	public void availableLanguagesSamplesLongEnough() {
		int minimalLength = 100;
		
		List<Profile> availableProfiles = repository.getKnownProfiles();
		for(Profile profile : availableProfiles) {
			assertTrue(profile.getBaseString().length() >= minimalLength);
		}
	}
}

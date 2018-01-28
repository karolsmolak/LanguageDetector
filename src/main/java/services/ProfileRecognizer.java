package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import repository.IRepository;

@Service
public class ProfileRecognizer implements IProfileRecognizer {

	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private IRepository repository;

	@Autowired
	private IRecognitionResult result;
	
	@Override
	public IRecognitionResult recognize(String text) {
		Profile textProfile = context.getBean(Profile.class, text, null);
		return matchToKnownProfiles(textProfile);
	}
	
	@Override
	public IRecognitionResult matchToKnownProfiles(Profile unknownProfile) {
		result.clear();
		
		List<Profile> knownProfiles = repository.getKnownProfiles();
		
		for(Profile profile : knownProfiles) {
			int distance = profile.calculateDistance(unknownProfile);
			result.add(profile, distance);
		}
		
		result.commit();
		
		return result;
	}
	
}

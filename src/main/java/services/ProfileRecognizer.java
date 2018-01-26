package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import repository.IRepository;

@Component
public class ProfileRecognizer implements IProfileRecognizer {

	@Autowired
	IRepository repository;

	@Override
	public IRecognitionResult recognize(String text) {
		Profile textProfile = new Profile(text, null);
		return computeDistancesToknownProfiles(textProfile);
	}
	
	private IRecognitionResult computeDistancesToknownProfiles(Profile unknownProfile) {
		IRecognitionResult result = new RecognitionResult();
		
		List<Profile> knownProfiles = repository.getKnownProfiles();
		
		for(Profile profile : knownProfiles) {
			int distance = profile.calculateDistance(unknownProfile);
			result.add(profile, distance);
		}
		
		result.commit();
		
		return result;
	}
	
}

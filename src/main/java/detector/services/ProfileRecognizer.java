package detector.services;

import detector.repository.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileRecognizer implements IProfileRecognizer {
	@Autowired
	private IRepository repository;

	@Autowired
	private IRecognitionResult result;
	
	@Override
	public IRecognitionResult recognize(String text) {
		Profile textProfile = new Profile(text, null, 5, 100);
		return matchToKnownProfiles(textProfile);
	}
	
	@Override
	public IRecognitionResult matchToKnownProfiles(Profile unknownProfile) {
		result.clear();
		repository.findAll().forEach((Profile profile) -> result.add(profile, profile.calculateDistance(unknownProfile)));
		result.commit();
		return result;
	}
}

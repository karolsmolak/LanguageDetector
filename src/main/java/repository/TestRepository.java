package repository;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import services.Profile;

@Component
@Qualifier("testsRepository")
public class TestRepository implements IRepository {
	
	List<Profile> knownProfiles = new LinkedList<>();
	
	public TestRepository() {
		addProfile("Polish", "wlazł kotek na pomarańczowy płotek");
		addProfile("English", "I have a web application whose actions are each represented in an action class");
	}
	
	@Override
	public List<Profile> getKnownProfiles() {
		return knownProfiles;
	}

	@Override
	public void addProfile(String baseString, String name) {
		knownProfiles.add(new Profile(baseString, name));
	}

	@Override
	public void removeProfile(Profile profile) {
		// TODO Auto-generated method stub
		
	}

}

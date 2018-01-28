package repository;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import services.Profile;

@Repository
@Qualifier("testRepository")
public class TestRepository implements IRepository {
	
	@Autowired
	private ApplicationContext context;
	
	private List<Profile> knownProfiles;
	
	@Override
	public List<Profile> getKnownProfiles() {
		if(knownProfiles == null) {
			knownProfiles = new LinkedList<>();
			addProfile("wlazł kotek na pomarańczowy płotek", "Polish");
			addProfile("I have a web application whose actions are each represented in an action class", "English");
			
		}
		return knownProfiles;
	}

	@Override
	public void addProfile(String baseString, String name) {
		knownProfiles.add(context.getBean(Profile.class, baseString, name));
	}

	@Override
	public void removeProfile(Profile profile) {
		
	}

}

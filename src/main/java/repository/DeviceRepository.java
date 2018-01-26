package repository;

import java.util.LinkedList;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;

import services.Profile;

@Component
@Primary
public class DeviceRepository implements IRepository {

	private static final String resourcePath = "src/main/resources/";
	private static final String indexXML = "udhr_txt/index.xml";
	
	private String getSamplePathById(String id) {
		return "udhr_txt/udhr_" + id + ".txt";
	}
	
	private List<Profile> profiles = new LinkedList<>();
	
	public DeviceRepository() {
		Parser parser = new Parser();
		
		List<Element> elements = parser.parseXML(resourcePath + indexXML, "udhr");
		
		for(Element element : elements) {
			String samplePath = resourcePath + getSamplePathById(element.getAttribute("f"));
			
			if(parser.existsFile(samplePath)) {
				String baseString = parser.getFormattedFile(samplePath);
				profiles.add(new Profile(baseString, element.getAttribute("n")));
			}
		}
	}

	public void addProfile(String baseString, String name) {
		profiles.add(new Profile(baseString, name));
	}
	
	@Override
	public List<Profile> getKnownProfiles() {
		return profiles;
	}

	@Override
	public void removeProfile(Profile profile) {
		// TODO Auto-generated method stub
		
	}

}

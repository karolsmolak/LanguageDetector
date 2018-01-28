package repository;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Element;

import services.Profile;

@Repository
@Primary
public class DeviceRepository implements IRepository {

	@Autowired
	private ApplicationContext context;
	
	@Value("${numberOfThreads}")
	private int numberOfThreads;
	
	private static final String resourcePath = "src/main/resources/";
	private static final String indexXML = "udhr_txt/index.xml";
	
	private static final Semaphore lock = new Semaphore(1);
	private List<Profile> profiles;
	

	public void addProfile(String baseString, String name) {
		Profile newProfile = context.getBean(Profile.class, baseString, name);
		newProfile.build();
		profiles.add(newProfile);
	}
	
	@Override
	public List<Profile> getKnownProfiles() {
		if(profiles == null) {
			buildProfileList();
		}
		return profiles;
	}

	@Override
	public void removeProfile(Profile profile) {
		
	}

	private String getSamplePathById(String id) {
		return "udhr_txt/udhr_" + id + ".txt";
	}
	
	private void buildProfileList() {
		
		profiles = new LinkedList<>();
		
		Parser parser = new Parser();
		
		List<Element> elements = parser.parseXML(resourcePath + indexXML, "udhr");
		
		ExecutorService exec = Executors.newFixedThreadPool(numberOfThreads);

		try {
			for (final Element element : elements) {
				exec.submit(new Runnable() {
					@Override
					public void run() {
						String samplePath = resourcePath + getSamplePathById(element.getAttribute("f"));
						Parser myParser = new Parser();
						
						if(myParser.existsFile(samplePath)) {
							
							String baseString = myParser.getFormattedFile(samplePath);

							Profile newProfile = context.getBean(Profile.class, baseString, element.getAttribute("n"));
							newProfile.build();
							
							try {
								lock.acquire();
								profiles.add(newProfile);
							} catch(Exception e) {
								
							} finally {
								lock.release();
							}
						}
					}
				});
			}
		} finally {
			exec.shutdown();
			try {
				exec.awaitTermination(100, TimeUnit.SECONDS);
			} catch(Exception e) {
				
			}
		}
	}
	
}

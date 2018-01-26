package utility;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

import org.w3c.dom.Element;

import application.Main;

public class LanguageBuilder {
	
	protected static List<Language> knownLanguages;
	protected static Semaphore protection = new Semaphore(1);
	protected static CountDownLatch latch;
	private static boolean built = false;
	
	public static void buildKnownLanguages() {
		if(!built) {
			knownLanguages = new ArrayList<Language>();
			
			List<Element> languages = Parser.parseXML("udhr_txt/index.xml");
			List<Thread> languageProfileCreators = new LinkedList<>();
			for(Element languageData : languages) {
				if(Parser.existsFile("udhr_txt/udhr_" + languageData.getAttribute("f") + ".txt")) {
					languageProfileCreators.add(new Thread(new Helper(languageData.getAttribute("n"),languageData.getAttribute("f"))));
				}
			}
			latch = new CountDownLatch(languageProfileCreators.size());
			for(Thread creator : languageProfileCreators) {
				creator.start();
			}
			try{
				latch.await();
			} catch(Exception e) {
				Main.logger.error("error");
			}
			built = true;
		}
	}
	
	static public List<Language> getKnownLanguages() {
		return knownLanguages;
	}
	
}

class Helper implements Runnable{
	private String languageName;
	private String languageFile;
	
	public Helper(String languageName, String languageFile) {
		this.languageName = languageName;
		this.languageFile = languageFile;
	}
	
	@Override
	public void run() {
		Language language = new Language(languageName, languageFile);
		try {
			LanguageBuilder.protection.acquire();
		} catch(Exception e) {
			Main.logger.error("error");
		}
		LanguageBuilder.knownLanguages.add(language);
		LanguageBuilder.protection.release();
		LanguageBuilder.latch.countDown();
	}
}
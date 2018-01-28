package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.Config;
import services.Profile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=Config.class)
public class ProfileTest {
	
	@Autowired
	private ApplicationContext context;
	
	@Test
	public void testGetPosition() {
		Profile testProfile = context.getBean(Profile.class, "abab", null);
		assertEquals(1, testProfile.getPosition("a"));
		assertEquals(1, testProfile.getPosition("b"));
		assertEquals(1, testProfile.getPosition("ab"));
		assertEquals(2, testProfile.getPosition("ba"));
		assertEquals(2, testProfile.getPosition("aba"));
		assertEquals(2, testProfile.getPosition("bab"));
		assertEquals(2, testProfile.getPosition("abab"));
	}

}

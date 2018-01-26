package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import services.Profile;
public class ProfileTest {
	
	@Test
	public void testGetPosition() {
		Profile testProfile = new Profile("abab", null);
		assertEquals(1, testProfile.getPosition("a"));
		assertEquals(1, testProfile.getPosition("b"));
		assertEquals(1, testProfile.getPosition("ab"));
		assertEquals(2, testProfile.getPosition("ba"));
		assertEquals(2, testProfile.getPosition("aba"));
		assertEquals(2, testProfile.getPosition("bab"));
		assertEquals(2, testProfile.getPosition("abab"));
	}

}

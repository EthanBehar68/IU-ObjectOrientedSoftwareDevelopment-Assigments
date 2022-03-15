package p532.gamemaker.junit.tests;

import org.junit.Test;

import p532.gamemaker.utility.ParseNumberUtility;

public class ParseNumberUtiliyyTest {

	@Test
	public void testParseDoubleOrReturnNull() {
		String input = "123";
		double output = 123;
		assert(output == ParseNumberUtility.parseDoubleOrReturnNull(input));
		
		assert(null == ParseNumberUtility.parseDoubleOrReturnNull("a123"));
	}

	@Test
	public void testParseIntOrReturnNull() {
		String input = "1";
		int output = 1;
		assert(output == ParseNumberUtility.parseIntOrReturnNull(input));
		
		assert(-99 == ParseNumberUtility.parseIntOrReturnNull("a1"));
	}

}

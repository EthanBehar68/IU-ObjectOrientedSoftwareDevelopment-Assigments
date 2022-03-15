package p532.gamemaker.junit.tests;

import org.junit.Test;

import p532.gamemaker.utility.saveload.CustomObjectMapper;
import p532.gamemaker.utility.saveload.custom.ColorJsonSerializer;

public class CustomObjectMapperTest {

	@Test
	public void testCustomObjectMapper() {
		CustomObjectMapper mapper = new CustomObjectMapper();
		assert(mapper.canSerialize(ColorJsonSerializer.class));
	}

}

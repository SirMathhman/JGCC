package com.meti;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class PathOptionTest {

	@Test
	void build() {
		Path test = Paths.get("test");
		List<Path> list = singletonList(test);
		GCCOption request = new PathOption(list);
		List<String> expected = singletonList(test.toAbsolutePath().toString());
		assertIterableEquals(expected, request.build());
	}
}
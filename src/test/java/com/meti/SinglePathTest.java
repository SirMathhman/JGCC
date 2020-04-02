package com.meti;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SinglePathTest {
	private static final Path SOURCE = Paths.get("test.c");

	@AfterAll
	static void after() throws IOException {
		Files.delete(SOURCE);
		Files.delete(Paths.get("a.exe"));
	}

	@BeforeAll
	static void before() throws IOException {
		Files.createFile(SOURCE);
		Files.writeString(SOURCE, "int main(){return 0;}");
	}

	@Test
	void execute() throws GCCException {
		GCCOption request = new PathOption(Collections.singletonList(SOURCE));
		String execute = MinGWProvider.create()
				.get()
				.execute(request);
		assertTrue(execute.isBlank());
		assertTrue(Files.exists(Paths.get("a.exe")));
	}
}
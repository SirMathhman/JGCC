package com.meti;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ObjectOptionTest {
	private static final Path EXECUTABLE = Paths.get("test.o");
	private static final Path SOURCE = Paths.get("test.c");

	@AfterAll
	static void after() throws IOException {
		Files.delete(SOURCE);
		Files.delete(EXECUTABLE);
	}

	@BeforeAll
	static void before() throws IOException {
		Files.createFile(SOURCE);
		Files.writeString(SOURCE, "int main(){return 0;}");
	}

	@Test
	void build() throws GCCException {
		GCCOption option = new CompoundOption(new ObjectOption(), new PathOption(SOURCE));
		String result = MinGWProvider.create().get().execute(option);
		assertTrue(result.isBlank());
		assertTrue(Files.exists(EXECUTABLE));
	}
}
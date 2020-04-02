package com.meti;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SharedOptionTest {
	public static final Path LIBRARY = Paths.get("test.dll");
	private static final Path OBJECT = Paths.get("test.o");
	private static final Path SOURCE = Paths.get("test.c");

	@AfterAll
	static void after() throws IOException {
		Files.delete(SOURCE);
		Files.delete(OBJECT);
		Files.delete(LIBRARY);
	}

	@BeforeAll
	static void before() throws IOException {
		Files.createFile(SOURCE);
		Files.writeString(SOURCE, "int main(){return 0;}");
	}

	@Test
	void build() throws GCCException {
		GCCContext context = MinGWProvider.create().get();
		context.execute(new CompoundOption(new ObjectOption(), new PathOption(SOURCE)));
		context.execute(SharedOption.create("test", List.of(OBJECT)));
		assertTrue(Files.exists(LIBRARY));
	}
}
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

class MultiplePathTest {
	private static final Path EXECUTABLE = Paths.get("a.exe");
	private static final Path HEADER = Paths.get("test0.h");
	private static final Path SOURCE_0 = Paths.get("test0.c");
	private static final Path SOURCE_1 = Paths.get("test1.c");

	@AfterAll
	static void after() throws IOException {
		Files.delete(SOURCE_0);
		Files.delete(HEADER);
		Files.delete(SOURCE_1);
		Files.delete(EXECUTABLE);
		Files.delete(Paths.get("test0.h.gch"));
	}

	@BeforeAll
	static void before() throws IOException {
		Files.createFile(SOURCE_0);
		Files.writeString(SOURCE_0, "void run(){}");
		Files.createFile(HEADER);
		Files.writeString(HEADER, "void run();");
		Files.createFile(SOURCE_1);
		Files.writeString(SOURCE_1, "#include \"test0.h\"\nint main(){run();return 0;}");
	}

	@Test
	void execute() throws GCCException {
		GCCOption request = new PathOption(List.of(SOURCE_0, HEADER, SOURCE_1));
		String execute = MinGWProvider.create()
				.get()
				.execute(request);
		assertTrue(execute.isBlank());
		assertTrue(Files.exists(EXECUTABLE));
	}
}
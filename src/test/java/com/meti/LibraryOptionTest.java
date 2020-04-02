package com.meti;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class LibraryOptionTest {
	public static final Path LIBRARY = Paths.get("libaLib.dll");
	private static final Path LIB_HEADER = Paths.get("libHeader.h");
	private static final Path LIB_OBJECT = Paths.get("libTest.o");
	private static final Path LIB_SOURCE = Paths.get("libTest.c");
	private static final Path SOURCE = Paths.get("test.c");

	@AfterAll
	static void after() throws IOException {
/*		Files.delete(LIB_SOURCE);
		Files.delete(LIB_OBJECT);
		Files.delete(LIBRARY);*/
	}

	@BeforeAll
	static void before() throws IOException {
		Files.createFile(LIB_HEADER);
		Files.writeString(LIB_HEADER, "int myValue();");
		Files.createFile(LIB_SOURCE);
		Files.writeString(LIB_SOURCE, "#include \"libHeader.h\"\nint myValue(){return 10;}");
		Files.createFile(SOURCE);
		Files.writeString(SOURCE, "#include \"libHeader.h\"\nint main(){myValue();return 0;}");
	}

	@Test
	void build() throws GCCException {
		GCCContext context = MinGWProvider.create().get();
		context.execute(new CompoundOption(new ObjectOption(), new PathOption(LIB_SOURCE, LIB_HEADER)));
		context.execute(SharedOption.create("libaLib", List.of(LIB_OBJECT)));
		context.execute(new CompoundOption(new NameOption("test"), new PathOption(SOURCE),
				new LibraryOption(LIBRARY)));
	}
}
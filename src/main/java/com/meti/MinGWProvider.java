package com.meti;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MinGWProvider extends PathProvider {
	public MinGWProvider(Path executable) {
		super(executable);
	}

	public static GCCProvider create() throws GCCException {
		Path root = Paths.get("C:", "MinGW");
		if (!Files.exists(root)) {
			throw new GCCException("MinGW has not been installed. Please install from http://www.mingw.org/.");
		}
		Path executable = root.resolve("bin").resolve("gcc.exe");
		if (!Files.exists(executable)) {
			throw new GCCException("MinGW has been installed, but the GCC executable could not be found.");
		}
		return new MinGWProvider(executable);
	}
}

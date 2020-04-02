package com.meti;

import java.nio.file.Path;

public class PathProvider implements GCCProvider {
	private final Path executable;

	public PathProvider(Path executable) {
		this.executable = executable;
	}

	public static GCCProvider fromDirectory(Path install) {
		Path executable = install.resolve("bin").resolve("gcc.exe");
		return new PathProvider(executable);
	}

	public static GCCProvider fromExecutable(Path executable) {
		return new PathProvider(executable);
	}

	@Override
	public GCCContext get() {
		return new PathContext(this.executable);
	}
}

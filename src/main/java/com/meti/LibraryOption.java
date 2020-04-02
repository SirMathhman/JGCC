package com.meti;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LibraryOption implements GCCOption {
	private final List<Path> paths;

	public LibraryOption(Path... paths) {
		this(List.of(paths));
	}

	public LibraryOption(List<Path> paths) {
		this.paths = Collections.unmodifiableList(paths);
	}

	@Override
	public List<String> build() {
		return paths.stream()
				.map(Path::toAbsolutePath)
				.map(Path::toString)
				.flatMap(s -> Stream.of("-l", s))
				.collect(Collectors.toList());
	}
}

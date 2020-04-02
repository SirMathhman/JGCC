package com.meti;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PathOption implements GCCOption {
	private final List<? extends Path> paths;

	public PathOption(Path... paths) {
		this(List.of(paths));
	}

	public PathOption(List<? extends Path> paths) {
		this.paths = Collections.unmodifiableList(paths);
	}

	@Override
	public List<String> build() {
		return paths.stream()
				.map(Path::toAbsolutePath)
				.map(Path::toString)
				.collect(Collectors.toList());
	}
}

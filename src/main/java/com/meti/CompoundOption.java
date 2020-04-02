package com.meti;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CompoundOption implements GCCOption {
	private final List<GCCOption> options;

	public CompoundOption(GCCOption... options) {
		this(List.of(options));
	}

	public CompoundOption(List<GCCOption> options) {
		this.options = Collections.unmodifiableList(options);
	}

	@Override
	public List<String> build() {
		return options.stream()
				.map(GCCOption::build)
				.flatMap(Collection::stream)
				.collect(Collectors.toCollection(ArrayList::new));
	}
}

package com.meti;

import java.util.List;

public class NameOption implements GCCOption {
	private final String name;

	public NameOption(String name) {
		this.name = name;
	}

	@Override
	public List<String> build() {
		return List.of("-o", name);
	}
}

package com.meti;

import java.nio.file.Path;
import java.util.List;

public class SharedOption extends CompoundOption {
	private SharedOption(String name, List<? extends Path> paths) {
		super(new NameOption(name), new PathOption(paths));
	}

	public static GCCOption create(String name, List<? extends Path> paths) {
		String nameToUse = name;
		if (!nameToUse.endsWith(".dll")) {
			nameToUse += ".dll";
		}
		return new SharedOption(nameToUse, paths);
	}

	@Override
	public List<String> build() {
		List<String> list = super.build();
		list.add(0, "-shared");
		return list;
	}
}

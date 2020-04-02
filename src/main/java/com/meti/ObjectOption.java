package com.meti;

import java.util.Collections;
import java.util.List;

public class ObjectOption implements GCCOption {
	@Override
	public List<String> build() {
		return Collections.singletonList("-c");
	}
}

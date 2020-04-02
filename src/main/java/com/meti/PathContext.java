package com.meti;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

class PathContext implements GCCContext {
	private static final long TIMEOUT = 1000L;
	private final Path executable;

	PathContext(Path executable) {
		this.executable = executable;
	}

	@Override
	public String execute(GCCOption request) throws
			GCCException {
		List<String> command = new ArrayList<>();
		command.add(executable.toAbsolutePath().toString());
		command.addAll(request.build());
		try {
			return execute(command);
		} catch (IOException | InterruptedException | ExecutionException | TimeoutException e) {
			throw new GCCException("Failed to run gcc command: \"" + String.join(" ", command) + "\"", e);
		}
	}

	private String execute(List<String> command) throws InterruptedException, ExecutionException, TimeoutException, IOException, GCCException {
		ProcessBuilder builder = new ProcessBuilder();
		builder.command(command);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
		Process process = builder.start()
				.onExit()
				.get(TIMEOUT, TimeUnit.MILLISECONDS);
		transfer(outputStream, process.getInputStream());
		transfer(errorStream, process.getErrorStream());
		String output = outputStream.toString();
		String error = errorStream.toString();
		if (error.isBlank()) return output;
		throw new GCCException(error);
	}

	private void transfer(ByteArrayOutputStream to, InputStream from) throws IOException {
		try (InputStream stream = from) {
			stream.transferTo(to);
		}
	}
}

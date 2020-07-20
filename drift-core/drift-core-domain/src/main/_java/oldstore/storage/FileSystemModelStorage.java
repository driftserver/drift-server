package io.drift.core.oldstore.storage;

import io.drift.core.metamodel.id.ModelId;
import io.drift.core.metamodel.urn.ModelURN;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class FileSystemModelStorage implements ModelStorage {

	private Path baseDir;

	public FileSystemModelStorage(Path baseDir) {
		this.baseDir = baseDir;
	}

	private Path getPath(ModelURN storagePath, ModelId storageId, String format) {
		Path path = baseDir;
		for (ModelId pathSegment : storagePath.getFragments()) {
			path = path.resolve(pathSegment.getId());
		}
		path = path.resolve(storageId.getId() + "." + format);
		return path;
	}

	@Override
	public String readContent(ModelId storageId, ModelURN storagePath, String format) throws ModelStorageException {
		try {
			return Files.readAllLines(getPath(storagePath, storageId, format)).stream().collect(Collectors.joining(System.lineSeparator()));
		} catch (IOException e) {
			throw new ModelStorageException(e);
		}
	}

	@Override
	public void writeContent(ModelId storageId, String model, ModelURN storagePath, String format) throws ModelStorageException {
		try {
			Path path = getPath(storagePath, storageId, format);
			Files.createDirectories(path.getParent());
			Files.write(path, model.getBytes(Charset.forName("utf-8")));
		} catch (IOException e) {
			throw new ModelStorageException(e);
		}
	}

}

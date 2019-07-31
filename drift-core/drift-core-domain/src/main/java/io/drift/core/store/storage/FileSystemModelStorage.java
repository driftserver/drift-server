package io.drift.core.store.storage;

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

	private Path getPath(StoragePath storagePath, StorageId storageId, String format) {
		Path path = baseDir;
		for (StorageId pathSegment : storagePath.getFragments()) {
			path = path.resolve(pathSegment.getId());
		}
		path = path.resolve(storageId.getId() + "." + format);
		return path;
	}

	@Override
	public String readContent(StorageId storageId, StoragePath storagePath, String format) throws ModelStorageException {
		try {
			return Files.readAllLines(getPath(storagePath, storageId, format)).stream().collect(Collectors.joining(System.lineSeparator()));
		} catch (IOException e) {
			throw new ModelStorageException(e);
		}
	}

	@Override
	public void writeContent(StorageId storageId, String model, StoragePath storagePath, String format) throws ModelStorageException {
		try {
			Path path = getPath(storagePath, storageId, format);
			Files.createDirectories(path.getParent());
			Files.write(path, model.getBytes(Charset.forName("utf-8")));
		} catch (IOException e) {
			throw new ModelStorageException(e);
		}
	}

}

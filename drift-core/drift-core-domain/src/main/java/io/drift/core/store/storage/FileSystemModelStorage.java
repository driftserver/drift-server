package io.drift.core.store.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class FileSystemModelStorage extends ModelStorage {

	private Path baseDir;

	public FileSystemModelStorage(Path baseDir) {
		this.baseDir = baseDir;
	}

	private Path getPath(StoragePath storagePath) {
		Path path = baseDir;
		for (StorageId storageId : storagePath.getFragments()) {
			path = path.resolve(storageId.getId());
		}
		return path;
	}

	@Override
	public String readContent(StoragePath storagePath) throws ModelStorageException {
		try {
			return Files.lines(getPath(storagePath)).collect(Collectors.joining());
		} catch (IOException e) {
			throw new ModelStorageException(e);
		}
	}

	@Override
	public String readMetaContent(StoragePath storagePath) throws ModelStorageException {
		return null;
	}

	@Override
	public void writeContent(StoragePath storagePath, String model) throws ModelStorageException {
		try {
			Path path = getPath(storagePath);
			Files.createDirectories(path.getParent());
			Files.write(path, model.getBytes());
		} catch (IOException e) {
			throw new ModelStorageException(e);
		}
	}

	@Override
	public void writeMetaContent(StoragePath storagePath, String metaContent) throws ModelStorageException {
	}

}

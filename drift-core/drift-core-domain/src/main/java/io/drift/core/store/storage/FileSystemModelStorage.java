package io.drift.core.store.storage;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileSystemModelStorage implements ModelStorage {

	private Path baseDir;

	public FileSystemModelStorage(Path baseDir) {
		this.baseDir = baseDir;
	}

	private Path getPath(StoragePath storagePath, StorageId storageId) {
		Path path = baseDir;
		for (StorageId pathSegment : storagePath.getFragments()) {
			path = path.resolve(pathSegment.getId());
		}
		path = path.resolve(storageId.getId());
		return path;
	}

	@Override
	public String readContent(StoragePath storagePath, StorageId storageId) throws ModelStorageException {
		try {
			return Files.lines(getPath(storagePath, storageId)).collect(Collectors.joining());
		} catch (IOException e) {
			throw new ModelStorageException(e);
		}
	}

	@Override
	public void writeContent(StoragePath storagePath, StorageId storageId, String model) throws ModelStorageException {
		try {
			Path path = getPath(storagePath, storageId);
			Files.createDirectories(path.getParent());
			Files.write(path, model.getBytes());
		} catch (IOException e) {
			throw new ModelStorageException(e);
		}
	}

}

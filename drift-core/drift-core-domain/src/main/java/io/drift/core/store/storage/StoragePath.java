package io.drift.core.store.storage;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StoragePath {

	public static StoragePath fromExternal(String string) {
		String[] parts = string.split("/");
		StorageId[] ids = new StorageId[parts.length];
		for (int i= 0; i < parts.length; i++) {
			ids[i] = new StorageId(parts[i]);
		}
		return new StoragePath(ids);

	}

	public static StoragePath of(StorageId... fragments) {
		return new StoragePath(fragments);
	}

	private List<StorageId> fragments;

	public StoragePath(StorageId... fragments) {
		this.fragments = Arrays.asList(fragments);
	}

	public StoragePath(List<StorageId> fragments) {
		this.fragments = fragments;
	}

	public StoragePath(StoragePath parent, StorageId... subFragments) {
		this.fragments = new ArrayList<>(parent.fragments);
		this.fragments.addAll(Arrays.asList(subFragments));
	}

	public String toExternal() {
		return fragments.stream().map(fragment->fragment.getId()).collect(Collectors.joining("/"));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StoragePath other = (StoragePath) obj;
		if (fragments == null) {
			if (other.fragments != null)
				return false;
		} else if (!fragments.equals(other.fragments))
			return false;
		return true;
	}

	public List<StorageId> getFragments() {
		return fragments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fragments == null) ? 0 : fragments.hashCode());
		return result;
	}

	public StoragePath resolve(StorageId... subFragments) {
		return new StoragePath(this, subFragments);
	}

	public boolean isDirectChild(StoragePath parent) {
		return parent.equals(getParentPath());
	}

	private StoragePath getParentPath() {
		return new StoragePath(fragments.subList(0, fragments.size()-2));
	}

	public StorageId lastfragment() {
		return fragments.get(fragments.size()-1);
	}
}

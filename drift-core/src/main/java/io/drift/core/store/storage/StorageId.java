package io.drift.core.store.storage;

import java.util.Arrays;

public class StorageId {

	private String[] fragments;

	public StorageId(String... fragments) {
		this.fragments = fragments;
	}

	public String[] getFragments() {
		return fragments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(fragments);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StorageId other = (StorageId) obj;
		if (!Arrays.equals(fragments, other.fragments))
			return false;
		return true;
	}
	
	
}

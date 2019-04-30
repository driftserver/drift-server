package io.drift.core.store;

import io.drift.core.store.storage.Storable;
import io.drift.core.store.storage.StorageId;

class MyModel implements Storable {
	private String att1;
	private String att2;

	public MyModel() {
		super();
	}

	public MyModel(String att1, String att2) {
		super();
		this.att1 = att1;
		this.att2 = att2;
	}

	public String getAtt1() {
		return att1;
	}

	public String getAtt2() {
		return att2;
	}

	@Override
	public StorageId getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setAtt1(String att1) {
		this.att1 = att1;
	}

	public void setAtt2(String att2) {
		this.att2 = att2;
	}

}

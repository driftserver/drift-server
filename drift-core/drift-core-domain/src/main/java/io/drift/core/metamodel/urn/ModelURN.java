package io.drift.core.metamodel.urn;

import io.drift.core.metamodel.id.ModelId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModelURN {

	public static ModelURN of(ModelId... fragments) {
		return new ModelURN(fragments);
	}

	private List<ModelId> fragments;

	public ModelURN(ModelId... fragments) {
		this.fragments = Arrays.asList(fragments);
	}

	public ModelURN(List<ModelId> fragments) {
		this.fragments = fragments;
	}

	public ModelURN(ModelURN parent, ModelId... subFragments) {
		this.fragments = new ArrayList<>(parent.fragments);
		this.fragments.addAll(Arrays.asList(subFragments));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModelURN other = (ModelURN) obj;
		if (fragments == null) {
			if (other.fragments != null)
				return false;
		} else if (!fragments.equals(other.fragments))
			return false;
		return true;
	}

	public List<ModelId> getFragments() {
		return fragments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fragments == null) ? 0 : fragments.hashCode());
		return result;
	}

	public ModelURN resolve(ModelId... subFragments) {
		return new ModelURN(this, subFragments);
	}

	public boolean isDirectChild(ModelURN parent) {
		return parent.equals(getParentURN());
	}

	public ModelURN getParentURN() {
		return new ModelURN(fragments.subList(0, fragments.size()-1));
	}

	public ModelId getLastFragment() {
		return fragments.get(fragments.size()-1);
	}

	@Override
	public String toString() {
		return "ModelURN{" +
					fragments +
				'}';
	}
}

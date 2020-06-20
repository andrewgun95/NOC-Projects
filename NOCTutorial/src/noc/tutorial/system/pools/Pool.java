package noc.tutorial.system.pools;

public abstract class Pool<T extends Poolable> {

	T object;

	abstract T newObject();

	public T obtain() {
		if (object == null) object = newObject();
		return object;
	}

	public void free() {
		if (object == null) return;
		object.reset();
	}

}

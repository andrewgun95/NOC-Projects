package noc.tutorial.system.pools;

public class Person implements Poolable {

	String name;
	int age;

	public Person() {
	}

	public void init(String name, int age) {
		this.name = name;
		this.age = age;
	}

	@Override
	public void reset() {
		name = "";
		age = -1;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + "]";
	}

	public static void main(String[] args) {
		Pool<Person> pool = new Pool<Person>() {

			@Override
			Person newObject() {
				return new Person();
			}

		};

		Person a = (Person) pool.obtain();
		a.init("Andreas", 21);
		pool.free();

		Person b = (Person) pool.obtain();
		b.init("Adhi", 22);
		pool.free();

		Person c = (Person) pool.obtain();
		c.init("Agus", 22);
		pool.free();
	}

}

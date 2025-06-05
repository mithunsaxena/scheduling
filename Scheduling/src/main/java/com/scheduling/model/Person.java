package com.scheduling.model;

import java.util.Objects;

public class Person {
	private final String name;
	private final String email;

	public Person(String name, String email) {
		this.name = name;
		this.email = email.toLowerCase();
	}

	public String getName() { return name; }
	public String getEmail() { return email; }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Person)) return false;
		Person person = (Person) o;
		return email.equals(person.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(email);
	}

	@Override
	public String toString() {
		return name + " <" + email + ">";
	}
}

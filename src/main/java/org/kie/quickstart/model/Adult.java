package org.kie.quickstart.model;

public class Adult {

    private final Person person;

    public Adult(Person p) {
        person = p;
    }

    public Person getPerson() {
        return person;
    }
}

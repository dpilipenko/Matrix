package skunkworks.matrix.services;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.graphdb.Transaction;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.stereotype.Service;

import skunkworks.matrix.entities.Person;
import skunkworks.matrix.entities.Skill;
import skunkworks.matrix.repositories.PersonRepository;

@Service
public class PersonService {

	@Autowired GraphDatabase graphDatabase;
	@Autowired PersonRepository personRepository;
	
	public void addPerson(Person person, List<Skill> skills) {
		addPerson(person);
		for (Skill skill : skills) {
			person = addSkill(person, skill);
		}
	}

	
	public void addPerson(Person person) {
		Transaction tx = graphDatabase.beginTx();
		try {
			if (findByName(person.name) == null) {
				personRepository.save(person);				
			}
			tx.success();
			log.info("Added person " + person.name);
		} finally {
			tx.close();
		}
	}
	
	public Person addSkill(Person person, Skill skill) {
		Transaction tx = graphDatabase.beginTx();
		try {
			person.addSkill(skill);
			personRepository.save(person);
			tx.success();
			log.info("Added skill " + skill.name + " to person " + person.name);
		} finally {
			tx.close();
		}
		return person;
	}
	
	public Iterable<Person> findAll() {
		ArrayList<Person> persons = new ArrayList<Person>();
		Transaction tx = graphDatabase.beginTx();
		try {
			for (Person person : personRepository.findAll()) {
				persons.add(person);
			}
			tx.success();
			log.info("Retrieved all persons");
		} finally {
			tx.close();
		}
		return persons;
	}
	
	public Person findByName(String name) {
		Person person;
		Transaction tx = graphDatabase.beginTx();
		try {
			person = personRepository.findByName(name);
			tx.success();
			if (person == null) {
				log.warn("Did not find person " + name);
			} else {
				log.info("Found person " + person.name);
			}
		} finally {
			tx.close();
		}
		return person;
	}
	
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(PersonService.class);
}

package skunkworks.matrix.onstartup;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.Transaction;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.core.GraphDatabase;

import skunkworks.matrix.entities.Person;
import skunkworks.matrix.entities.Skill;
import skunkworks.matrix.repositories.PersonRepository;
import skunkworks.matrix.repositories.SkillRepository;

public class BootstrapDB {
	
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(BootstrapDB.class);
	
	public static void run(PersonRepository personRepository, SkillRepository skillRepository, GraphDatabase graphDatabase) {
		
		Transaction tx = graphDatabase.beginTx();
		try {
			log.info("Transaction Begin");
			Set<Person> persons = new HashSet<>();
			persons.add(new Person("Dima"));
			persons.add(new Person("Derril"));
			persons.add(new Person("Nicole"));
			personRepository.save(persons);
			log.info("Added persons");
			
			tx.success();
			log.info("Transaction Saved");
		} finally {
			tx.close();
		}
		
		tx = graphDatabase.beginTx();
		try {
			log.info("Transaction Begin");
			Set<Skill> skills = new HashSet<>();
			skills.add(new Skill("C#"));
			skills.add(new Skill("Java"));
			skills.add(new Skill("Node"));
			skillRepository.save(skills);
			log.info("Added skills");
			
			tx.success();
			log.info("Transaction Saved");
			
			
		} finally {
			tx.close();
		}
		
		tx = graphDatabase.beginTx();
		try {
			log.info("Transaction Begin");
			Person dima = personRepository.findByName("Dima");
			Skill csharp = skillRepository.findByName("C#");
			if (dima != null && csharp != null) {
				dima.addSkill(csharp);
				personRepository.save(dima);
				log.info("Dima skilled C#");
			}
			tx.success();
			log.info("Transaction Saved");
		} finally {
			tx.close();
		}
		
		tx = graphDatabase.beginTx();
		try {
			log.info("Transaction Begin");
			for (Person person : personRepository.findAll()) {
				log.info("Found Person: " + person);
			}
			for (Skill skill : skillRepository.findAll()) {
				log.info("Found Skill: " + skill);
			}
			log.info("Transaction Saved");
			tx.success();
		} finally {
			tx.close();
		}
		
		
	}
}

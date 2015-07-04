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
	
	static PersonRepository personRepository;
	static SkillRepository skillRepository;
	
	static GraphDatabase graphDatabase;
	
	public static void run(PersonRepository personRepository, SkillRepository skillRepository, GraphDatabase graphDatabase) {
		
		BootstrapDB.personRepository = personRepository;
		BootstrapDB.skillRepository = skillRepository;
		BootstrapDB.graphDatabase = graphDatabase;
			
		savePersons();
		saveSkills();
		teachSkillsToPersons();
		printoutPersonsAndSkills();
		
	}

	private static void printoutPersonsAndSkills() {
		Transaction tx = graphDatabase.beginTx();
		try {
			log.info("Transaction Begin");
			log.info("Searching for Persons");
			for (Person person : personRepository.findAll()) {
				log.info(person.toString());
			}
			log.info("Searching for Skills");
			for (Skill skill : skillRepository.findAll()) {
				log.info(skill.toString());
			}
			log.info("Transaction Saved");
			tx.success();
		} finally {
			tx.close();
		}
	}

	private static void teachSkillsToPersons() {
		Transaction tx = graphDatabase.beginTx();
		try {
			log.info("Transaction Begin");
			Person dima = personRepository.findByName("Dima");
			Person derril = personRepository.findByName("Derril");
			Person nicole = personRepository.findByName("Nicole");
			Skill csharp = skillRepository.findByName("C#");
			Skill java = skillRepository.findByName("Java");
			Skill node = skillRepository.findByName("Node");
			
			dima.addSkill(csharp);
			dima.addSkill(java);
			personRepository.save(dima);
			
			derril.addSkill(java);
			derril.addSkill(node);
			personRepository.save(derril);
			
			nicole.addSkill(java);
			personRepository.save(nicole);
			
			tx.success();
			log.info("Transaction Saved");
		} finally {
			tx.close();
		}
	}

	private static void saveSkills() {
		Transaction tx = graphDatabase.beginTx();
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
	}

	private static void savePersons() {
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
	}
}

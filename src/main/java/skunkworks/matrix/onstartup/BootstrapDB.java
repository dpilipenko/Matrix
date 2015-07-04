package skunkworks.matrix.onstartup;

import org.slf4j.LoggerFactory;

import skunkworks.matrix.entities.Person;
import skunkworks.matrix.entities.Skill;
import skunkworks.matrix.services.PersonService;
import skunkworks.matrix.services.SkillService;

public class BootstrapDB {
	
	static PersonService personService;
	static SkillService skillService;
	
	public static void run(PersonService personService, SkillService skillService) {
		
		BootstrapDB.personService = personService;
		BootstrapDB.skillService = skillService;
			
		savePersons();
		saveSkills();
		teachSkillsToPersons();
		printoutPersonsAndSkills();
		
	}

	private static void printoutPersonsAndSkills() {
		log.info("Searching for Persons");
		for (Person person : personService.findAll()) {
			log.info(person.toString());
		}
		log.info("Searching for Skills");
		for (Skill skill : skillService.findAll()) {
			log.info(skill.toString());
		}
	}

	private static void teachSkillsToPersons() {
		Skill csharp = skillService.findByName("C#");
		Skill java = skillService.findByName("Java");
		Skill node = skillService.findByName("Node");
		
		Person nicole = personService.findByName("Nicole Shaddock");
		personService.addSkill(nicole, java);
		
		Person dima = personService.findByName("Dmitriy Pilipenko");
		personService.addSkill(dima, csharp);
		personService.addSkill(dima, java);
		
		Person derril = personService.findByName("Derril Lucci");
		personService.addSkill(derril, node);
		personService.addSkill(derril, csharp);
		personService.addSkill(derril, java);
	}

	private static void saveSkills() {
		String[] skills = {"C#", "Java", "Node"};
		for (String skillName : skills) {
			Skill skill = new Skill(skillName);
			skillService.addSkill(skill);
		}
	}

	private static void savePersons() {
		String[] names = {"Dmitriy Pilipenko", "Derril Lucci", "Nicole Shaddock"};
		for (String name : names) {
			Person person = new Person(name);
			personService.addPerson(person);
		}
	}

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(BootstrapDB.class);
}

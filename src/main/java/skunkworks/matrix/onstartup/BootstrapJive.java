package skunkworks.matrix.onstartup;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;

import skunkworks.matrix.entities.Person;
import skunkworks.matrix.entities.Skill;
import skunkworks.matrix.jive.JiveAdapter;
import skunkworks.matrix.jive.JiveService;
import skunkworks.matrix.jive.responses.PeopleResponse;
import skunkworks.matrix.services.PersonService;
import skunkworks.matrix.services.SkillService;

public class BootstrapJive {
	
	public static void runMyself(PersonService personService, SkillService skillService) {
		JiveService jiveService = JiveAdapter.generate();
		PeopleResponse myself = jiveService.getMyself();
		doPeoplePerson(myself, personService, skillService);
		for (Person p : personService.findAll()) {
			log.info(p.toString());
		}
	}
	
	public static void runMultipleEmails(PersonService personService, SkillService skillService) {
		String[] emailAddresses = {
				"dmitriy.pilipenko@rosetta.com", 
				"derril.lucci@rosetta.com", 
				"halley.marsh@rosetta.com"};
		JiveService jiveService = JiveAdapter.generate();
		for (String emailAddress : emailAddresses) {
			PeopleResponse people = jiveService.getPeopleByEmail(emailAddress);
			doPeoplePerson(people, personService, skillService);
		}
	}
	
	private static void doPeoplePerson(PeopleResponse people, PersonService personService, SkillService skillService) {
		// TODO Find better place for this method. Maybe some of this logic should be in Services?
		List<Skill> skills = new ArrayList<>();
		for (String skillName : people.skills) {
			Skill skill = skillService.findByName(skillName);
			if (skill == null) {
				skill = new Skill(skillName);
				skillService.addSkill(skill);
			}
			skills.add(skill);
		}
		Person person = personService.findByName(people.name);
		if (person == null) {
			person = new Person(people.name);
		}
		personService.addPerson(person, skills);
	}
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(BootstrapJive.class);
}

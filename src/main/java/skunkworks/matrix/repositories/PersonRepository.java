package skunkworks.matrix.repositories;

import org.springframework.data.repository.CrudRepository;

import skunkworks.matrix.entities.Person;
import skunkworks.matrix.entities.Skill;

public interface PersonRepository extends CrudRepository<Person, String> {

    Person findByName(String name);

    Iterable<Skill> findBySkillsName(String name);

}

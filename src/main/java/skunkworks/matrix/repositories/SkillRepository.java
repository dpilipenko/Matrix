package skunkworks.matrix.repositories;

import org.springframework.data.repository.CrudRepository;

import skunkworks.matrix.entities.Skill;

public interface SkillRepository extends CrudRepository<Skill, String> {

	Skill findByName(String name);
}

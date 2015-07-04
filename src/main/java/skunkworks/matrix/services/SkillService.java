package skunkworks.matrix.services;

import java.util.ArrayList;

import org.neo4j.graphdb.Transaction;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.stereotype.Service;

import skunkworks.matrix.entities.Skill;
import skunkworks.matrix.repositories.SkillRepository;

@Service
public class SkillService {
	
	@Autowired GraphDatabase graphDatabase;
	@Autowired SkillRepository skillRepository;
	
	public void addSkill(Skill skill) {
		Transaction tx = graphDatabase.beginTx();
		try {
			skillRepository.save(skill);
			tx.success();
			log.info("Added skill " + skill.name);
		} finally {
			tx.close();
		}
	}

	public Iterable<Skill> findAll() {
		ArrayList<Skill> skills = new ArrayList<Skill>();
		Transaction tx = graphDatabase.beginTx();
		try {
			for (Skill skill : skillRepository.findAll()) {
				skills.add(skill);
			}
			tx.success();
			log.info("Retrieved all skills");
		} finally {
			tx.close();
		}
		return skills;
	}
	
	public Skill findByName(String name) {
		Skill skill;
		Transaction tx = graphDatabase.beginTx();
		try {
			skill = skillRepository.findByName(name);
			tx.success();
			if (skill == null) {
				log.warn("Did not find skill " + skill);
			} else {
				log.info("Found skill " + skill.name);
			}
		} finally {
			tx.close();
		}
		return skill;
	}
	
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(SkillService.class);
}

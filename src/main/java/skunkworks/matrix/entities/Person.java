package skunkworks.matrix.entities;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class Person {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(Person.class);
	
    @GraphId Long id;
    public String name;

    public Person() {}
    public Person(String name) { this.name = name; }

    @RelatedTo(type="SKILL", direction=Direction.OUTGOING)
    public @Fetch Set<Skill> skills;
    
    public void addSkill(Skill skill) {
    	if (skills == null) {
    		skills = new HashSet<>();
    	}
    	skills.add(skill);
    	log.info(name + " skilled " + skill.name);
    }
    
    public String toString() {
        String results = name;
        if (skills != null) {
        	results += " Skills: ";
            for (Skill skill : skills) {
                results += skill.name + " ";
            }
        }
        return results;
    }

}

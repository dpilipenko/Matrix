package skunkworks.matrix.entities;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class Person {

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
    }
    
    public String toString() {
        String results = name;
        if (skills != null) {
            for (Skill skill : skills) {
                results += "\t- Skill:" + skill.name;
            }
        }
        return results;
    }

}

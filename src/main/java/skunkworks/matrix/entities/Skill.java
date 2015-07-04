package skunkworks.matrix.entities;

import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import skunkworks.matrix.keys.Neo4jKeys;

@NodeEntity
public class Skill {
	@GraphId Long id;
	public String name;
	
	public Skill() {}
	public Skill(String name) { this.name = name; }
	
	@RelatedTo(type=Neo4jKeys.LINK_LEARNEDSKILL, direction=Direction.BOTH)
    public @Fetch Set<Person> persons;
	
	public String toString() {
		String results = "Skill: " + name;
        if (persons != null) {
        	results += " Persons: ";
            for (Person person : persons) {
                results += person.name + " ";
            }
        }
		return results;
	}
}

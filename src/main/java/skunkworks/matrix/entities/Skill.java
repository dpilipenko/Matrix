package skunkworks.matrix.entities;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Skill {
	@GraphId Long id;
	public String name;
	
	public Skill() {}
	public Skill(String name) { this.name = name; }
	
	public String toString() {
		String results = "Skill: " + name;
		return results;
	}
}

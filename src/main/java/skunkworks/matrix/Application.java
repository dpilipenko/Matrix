package skunkworks.matrix;

import java.io.File;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.impl.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.core.GraphDatabase;

import skunkworks.matrix.onstartup.BootstrapDB;
import skunkworks.matrix.onstartup.BootstrapJive;
import skunkworks.matrix.repositories.PersonRepository;
import skunkworks.matrix.repositories.SkillRepository;
import skunkworks.matrix.services.PersonService;
import skunkworks.matrix.services.SkillService;

@SpringBootApplication
@ComponentScan("skunkworks.matrix")
public class Application implements CommandLineRunner {
	
	private static final String PACKAGENAME = "skunkworks.matrix";
	private static final String DBNAME = "accessingdataneo4j.db";

	@Autowired PersonRepository personRepository;
	@Autowired SkillRepository skillRepository;
	@Autowired GraphDatabase graphDatabase;
	@Autowired PersonService personService;
	@Autowired SkillService skillService;
	
	public void run(String... args) throws Exception {
		
		// Uncomment option below in order to load data from Jive
//		BootstrapJive.runMyself(personService, skillService);
		BootstrapJive.runMultipleEmails(personService, skillService);

		// Uncomment option below in order to load hard coded list of skills and users
		BootstrapDB.run(personService, skillService);

	}
	
	@Configuration
	@EnableNeo4jRepositories(basePackages = PACKAGENAME)
	static class ApplicationConfig extends Neo4jConfiguration {
		public ApplicationConfig() { setBasePackage(PACKAGENAME); }
		@Bean GraphDatabaseService graphDatabaseService() { return new GraphDatabaseFactory().newEmbeddedDatabase(DBNAME); }
	}
	
	public static void main(String[] args) throws Exception {
		FileUtils.deleteRecursively(new File(DBNAME));
		SpringApplication.run(Application.class, args);
	}
}

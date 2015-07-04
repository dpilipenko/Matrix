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
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.core.GraphDatabase;

import skunkworks.matrix.onstartup.BootstrapDB;
import skunkworks.matrix.repositories.PersonRepository;
import skunkworks.matrix.repositories.SkillRepository;

@SpringBootApplication
public class Application implements CommandLineRunner {

	private static final String DBNAME = "accessingdataneo4j.db";
	private static final String PACKAGENAME = "skunkworks.matrix";
	
	@Configuration
	@EnableNeo4jRepositories(basePackages = PACKAGENAME)
	static class ApplicationConfig extends Neo4jConfiguration {

		public ApplicationConfig() {
			setBasePackage(PACKAGENAME);
			
		}

		@Bean
		GraphDatabaseService graphDatabaseService() {
			return new GraphDatabaseFactory().newEmbeddedDatabase(DBNAME);
		}
	}

	@Autowired PersonRepository personRepository;
	@Autowired SkillRepository skillRepository;

	@Autowired GraphDatabase graphDatabase;

	public void run(String... args) throws Exception {
		BootstrapDB.run(personRepository, skillRepository, graphDatabase);
	}
	
	public static void main(String[] args) throws Exception {
		FileUtils.deleteRecursively(new File(DBNAME));

		SpringApplication.run(Application.class, args);
	}
}

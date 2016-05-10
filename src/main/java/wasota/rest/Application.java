package wasota.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import wasota.graph.WasotaGraph;
import wasota.rest.models.WasotaPerformanceAll;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args); 
		WasotaGraph.startModel();

	}

}

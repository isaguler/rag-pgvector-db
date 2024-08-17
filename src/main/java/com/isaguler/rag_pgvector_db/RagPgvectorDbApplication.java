package com.isaguler.rag_pgvector_db;

import com.isaguler.rag_pgvector_db.configuration.HintsRegistrar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.shell.command.annotation.CommandScan;

@ImportRuntimeHints(HintsRegistrar.class)
@CommandScan
@SpringBootApplication
public class RagPgvectorDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(RagPgvectorDbApplication.class, args);
	}

}

package com.zaplink.ZapVerse;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZapVerseApplication {

	public static void main(String[] args) {
		Dotenv dotenv = null;

		try {
			// Try loading from .env (local dev)
			dotenv = Dotenv.load();
			System.out.println("dotenv: Loaded environment variables from .env file.");
		} catch (Exception e) {
			// If .env not found, use Azure environment variables
			System.out.println("dotenv: .env file not found, using system environment variables.");
		}

		// Set system properties (check dotenv first, then System.getenv)
		System.setProperty("SUPABASE_DB_URL", getenv(dotenv, "SUPABASE_DB_URL"));
		System.setProperty("SUPABASE_DB_USERNAME", getenv(dotenv, "SUPABASE_DB_USERNAME"));
		System.setProperty("SUPABASE_DB_PASSWORD", getenv(dotenv, "SUPABASE_DB_PASSWORD"));

		SpringApplication.run(ZapVerseApplication.class, args);
	}

	private static String getenv(Dotenv dotenv, String key) {
		if (dotenv != null && dotenv.get(key) != null) {
			return dotenv.get(key);
		}
		// Azure App Service variables
		return System.getenv(key);
	}
}

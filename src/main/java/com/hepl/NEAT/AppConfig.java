package com.hepl.NEAT;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppConfig {
        private static Properties properties = new Properties();

        public static Integer NEAT_INPUT_SIZE = 4;
        public static Integer NEAT_OUTPUT_SIZE = 2;
        public static Integer NEAT_HIDDEN_SIZE = 4;

        public  void loadconfig() {
            try{
            // Load the properties file
            FileInputStream fis = new FileInputStream("config.properties");
            properties.load(fis);

            NEAT_INPUT_SIZE = Integer.parseInt(properties.getProperty("neat.input.size"));
            NEAT_OUTPUT_SIZE = Integer.parseInt(properties.getProperty("neat.output.size"));
            NEAT_HIDDEN_SIZE = Integer.parseInt(properties.getProperty("neat.hidden.size"));

        } catch (IOException e) {
            System.out.println("Error reading properties file: " + e.getMessage());
        }
    }
}
package com.hepl.NEAT;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class AppConfig {
        private static Properties properties = new Properties();

        //General parameters
        public static Integer NEAT_POPULATION_SIZE = 150;
        public static Integer NEAT_MAX_GENERATION = 100;
        //Initial Neural Network parameters
        public static Integer NEAT_INPUT_SIZE = 4;
        public static Integer NEAT_OUTPUT_SIZE = 2;
        public static Integer NEAT_HIDDEN_SIZE = 4;
        public static Boolean NEAT_BIAS = true;
        public static Float NEAT_INIT_WEIGHT = 1.0f;
        public static Boolean NEAT_INIT_WEIGHT_RANDOM = true;
        //Random Connection Weight parameters
        public static Float NEAT_RANDOM_WEIGHT_MIN = -1.0f;
        public static Float NEAT_RANDOM_WEIGHT_MAX = 1.0f;
        //Crossover rates
        public static Double NEAT_CROSSOVER_RATE = 0.75;
        //Mutation rates
        public static Double NEAT_MUTATION_RATE = 0.25;
        public static Double NEAT_WEIGHT_MUTATION_RATE = 0.8;
        public static Double NEAT_CONNECTION_MUTATION_RATE = 0.05;
        public static Double NEAT_NODE_MUTATION_RATE = 0.03;
        
    	// Speciation constants
        public static Float NEAT_C1 = 1f;
        public static Float NEAT_C2 = 1f;
        public static Float NEAT_C3 = 0.3f;
        public static Float NEAT_COMPATIBILITY_THRESHOLD = 3f;
        
        // Evolution constants
        public static Integer NEAT_MAX_STAGNATION = 15;
        public static Float NEAT_PERCENTAGE_TO_KEEP = 0.5f;
        
        public void createconfigFile(String filename) {
            try{
                FileOutputStream fos = new FileOutputStream(filename);
                properties.setProperty("neat.population.size", NEAT_POPULATION_SIZE.toString());
                properties.setProperty("neat.max.generation", NEAT_MAX_GENERATION.toString());

                properties.setProperty("neat.input.size", NEAT_INPUT_SIZE.toString());
                properties.setProperty("neat.output.size", NEAT_OUTPUT_SIZE.toString());
                properties.setProperty("neat.hidden.size", NEAT_HIDDEN_SIZE.toString());
                properties.setProperty("neat.bias", NEAT_BIAS.toString());
                properties.setProperty("neat.init.weight", NEAT_INIT_WEIGHT.toString());
                properties.setProperty("neat.init.weight.random", NEAT_INIT_WEIGHT_RANDOM.toString());
                properties.setProperty("neat.random.weight.min", NEAT_RANDOM_WEIGHT_MIN.toString());
                properties.setProperty("neat.random.weight.max", NEAT_RANDOM_WEIGHT_MAX.toString());

                properties.setProperty("neat.crossover.rate", NEAT_CROSSOVER_RATE.toString());
                properties.setProperty("neat.mutation.rate", NEAT_MUTATION_RATE.toString());
                properties.setProperty("neat.weight.mutation.rate", NEAT_WEIGHT_MUTATION_RATE.toString());
                properties.setProperty("neat.connection.mutation.rate", NEAT_CONNECTION_MUTATION_RATE.toString());
                properties.setProperty("neat.node.mutation.rate", NEAT_NODE_MUTATION_RATE.toString());
                
                // Speciation
                properties.setProperty("neat.c1", NEAT_C1.toString());
                properties.setProperty("neat.c2", NEAT_C2.toString());
                properties.setProperty("neat.c3", NEAT_C3.toString());
                properties.setProperty("neat.compatibility.threshold", NEAT_COMPATIBILITY_THRESHOLD.toString());

                // Evolution
                properties.setProperty("neat.max.stagnation", NEAT_MAX_STAGNATION.toString());
                properties.setProperty("neat.percentage.to.keep", NEAT_PERCENTAGE_TO_KEEP.toString());
                
                properties.store(fos, "NEAT configuration");

            } catch (IOException e) {
                System.out.println("Error creating properties file: " + e.getMessage());
            }
        }
        public  void loadconfig() {
            try{
            // Load the properties file
            FileInputStream fis = new FileInputStream("config.properties");
            properties.load(fis);

            NEAT_POPULATION_SIZE = Integer.parseInt(properties.getProperty("neat.population.size"));
            NEAT_MAX_GENERATION = Integer.parseInt(properties.getProperty("neat.max.generation"));

            NEAT_INPUT_SIZE = Integer.parseInt(properties.getProperty("neat.input.size"));
            NEAT_OUTPUT_SIZE = Integer.parseInt(properties.getProperty("neat.output.size"));
            NEAT_HIDDEN_SIZE = Integer.parseInt(properties.getProperty("neat.hidden.size"));
            NEAT_BIAS = Boolean.parseBoolean(properties.getProperty("neat.bias"));
            NEAT_INIT_WEIGHT = Float.parseFloat(properties.getProperty("neat.init.weight"));
            NEAT_INIT_WEIGHT_RANDOM = Boolean.parseBoolean(properties.getProperty("neat.init.weight.random"));

            NEAT_RANDOM_WEIGHT_MIN = Float.parseFloat(properties.getProperty("neat.random.weight.min"));
            NEAT_RANDOM_WEIGHT_MAX = Float.parseFloat(properties.getProperty("neat.random.weight.max"));

            NEAT_CROSSOVER_RATE = Double.parseDouble(properties.getProperty("neat.crossover.rate"));

            NEAT_MUTATION_RATE = Double.parseDouble(properties.getProperty("neat.mutation.rate"));
            NEAT_WEIGHT_MUTATION_RATE = Double.parseDouble(properties.getProperty("neat.weight.mutation.rate"));
            NEAT_CONNECTION_MUTATION_RATE = Double.parseDouble(properties.getProperty("neat.connection.mutation.rate"));
            NEAT_NODE_MUTATION_RATE = Double.parseDouble(properties.getProperty("neat.node.mutation.rate"));
            NEAT_CROSSOVER_RATE = Double.parseDouble(properties.getProperty("neat.crossover.rate"));
            NEAT_MUTATION_RATE = Double.parseDouble(properties.getProperty("neat.mutation.rate"));
            
            // speciation
            NEAT_C1 = Float.parseFloat(properties.getProperty("neat.c1"));
            NEAT_C2 = Float.parseFloat(properties.getProperty("neat.c2"));
            NEAT_C3 = Float.parseFloat(properties.getProperty("neat.c3"));
            NEAT_COMPATIBILITY_THRESHOLD = Float.parseFloat(properties.getProperty("neat.compatibility.threshold"));
            
            // evolution
            NEAT_MAX_STAGNATION = Integer.parseInt(properties.getProperty("neat.max.stagnation"));
            NEAT_PERCENTAGE_TO_KEEP = Float.parseFloat(properties.getProperty("neat.percentage.to.keep"));

        } catch (IOException e) {
            System.out.println("Error reading properties file: " + e.getMessage());
        }
    }
}
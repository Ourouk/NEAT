package com.hepl.NEAT;

import java.io.FileInputStream;
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
        public static Integer NEAT_INIT_WEIGHT = 1;
        public static Boolean NEAT_INIT_WEIGHT_RANDOM = true;
        //Crossover rates
        public static Double NEAT_CROSSOVER_RATE = 0.75;
        //Mutation rates
        public static Double NEAT_MUTATION_RATE = 0.25;
        public static Double NEAT_WEIGHT_MUTATION_RATE = 0.8;
        public static Double NEAT_CONNECTION_MUTATION_RATE = 0.05;
        public static Double NEAT_NODE_MUTATION_RATE = 0.03;

        //Game Scoreing
        public static Integer NEAT_MAX_GAME_STEPS = 25;

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

            NEAT_CROSSOVER_RATE = Double.parseDouble(properties.getProperty("neat.crossover.rate"));

            NEAT_MUTATION_RATE = Double.parseDouble(properties.getProperty("neat.mutation.rate"));
            NEAT_WEIGHT_MUTATION_RATE = Double.parseDouble(properties.getProperty("neat.weight.mutation.rate"));
            NEAT_CONNECTION_MUTATION_RATE = Double.parseDouble(properties.getProperty("neat.connection.mutation.rate"));
            NEAT_NODE_MUTATION_RATE = Double.parseDouble(properties.getProperty("neat.node.mutation.rate"));
            NEAT_CROSSOVER_RATE = Double.parseDouble(properties.getProperty("neat.crossover.rate"));
            NEAT_MUTATION_RATE = Double.parseDouble(properties.getProperty("neat.mutation.rate"));

            NEAT_MAX_GAME_STEPS = Integer.parseInt(properties.getProperty("neat.game.maxstep"));

        } catch (IOException e) {
            System.out.println("Error reading properties file: " + e.getMessage());
        }
    }
}
package com.hepl.test;

import com.hepl.NEAT.*;
import java.util.ArrayList;
import java.util.List;

public class TestSpeciation {

    public static void main(String[] args) {
		// TODO Auto-generated method stub
        // Create nodes and connections
        Node node1 = new Node(Node.Type.INPUT);
        Node node2 = new Node(Node.Type.INPUT);
        Node node3 = new Node(Node.Type.INPUT);
        Node node4 = new Node(Node.Type.OUTPUT);
        Node node5 = new Node(Node.Type.HIDDEN);
        Node node6 = new Node(Node.Type.HIDDEN);
        
        Connection con1 = new Connection(node1, node4, 1);
        con1.innovation = 1;
		Connection con2 = new Connection(node2, node4, 1);
		con2.innovation =2;
		Connection con3 = new Connection(node3, node4, 1);
		con3.innovation = 3;
		Connection con4 = new Connection(node2, node5, 1);
		con4.innovation = 4;
		Connection con5 = new Connection(node5, node4, 1);
		con5.innovation = 5;
		Connection con6 = new Connection(node5, node6, 1);
		con6.innovation = 6;
		Connection con7 = new Connection(node6, node4, 1);
		con7.innovation = 7;
		Connection con8 = new Connection(node1, node5, 1);
		con8.innovation = 8;
		Connection con9 = new Connection(node3, node5, 1);
		con9.innovation = 9;
		Connection con10 = new Connection(node1, node6, 1);
		con10.innovation = 10;
        
        // Create genomes with varying structures (Two are same as the TestMatchingDisjointExcess)
        Genome parent1 = new Genome();
        
        parent1.addNode(node1);
        parent1.addNode(node2);
        parent1.addNode(node3);
        parent1.addNode(node4);
        parent1.addNode(node5);
        
		con2.setConnectionState(Connection.State.DISABLED);
		
        parent1.addConnection(con1);
        parent1.addConnection(con2);
        parent1.addConnection(con3);
        parent1.addConnection(con4);
        parent1.addConnection(con5);
        parent1.addConnection(con8);
        
        
        
        
        Genome parent2 = new Genome();
        
        parent2.addNode(node1);
        parent2.addNode(node2);
        parent2.addNode(node3);
        parent2.addNode(node4);
        parent2.addNode(node5);
        parent2.addNode(node6);

        con2.setConnectionState(Connection.State.DISABLED);
        con5.setConnectionState(Connection.State.DISABLED);
        
        parent2.addConnection(con1);
        parent2.addConnection(con2);
        parent2.addConnection(con3);
        parent2.addConnection(con4);
        parent2.addConnection(con5);
        parent2.addConnection(con6);
        parent2.addConnection(con7);
        parent2.addConnection(con9);
        parent2.addConnection(con10);
        
        
        
        
        Genome offspring = new Genome();
        offspring.addNode(node1);
        offspring.addNode(node2);
        offspring.addNode(node3);
        offspring.addNode(node4);
        offspring.addNode(node5);
        offspring.addNode(node6);

        offspring.addConnection(con1);
        offspring.addConnection(con2);
        offspring.addConnection(con3);
        offspring.addConnection(con4);
        offspring.addConnection(con5);
        offspring.addConnection(con6);
        offspring.addConnection(con7);
        offspring.addConnection(con8);
        offspring.addConnection(con9);
        offspring.addConnection(con10);

        // Englobe genomes with fitness
        GenomeWithFitness gwf1 = new GenomeWithFitness(parent1, 1.0f);
        GenomeWithFitness gwf2 = new GenomeWithFitness(parent2, 2.0f);
        GenomeWithFitness gwf3 = new GenomeWithFitness(offspring, 3.0f);

        List<GenomeWithFitness> genomes = new ArrayList<>();
        genomes.add(gwf1);
        genomes.add(gwf2);
        genomes.add(gwf3);

        // Export genomes to DOT files
        parent1.exportToDot("Images/Speciation/parent1.dot");
        parent2.exportToDot("Images/Speciation/parent2.dot");
        offspring.exportToDot("Images/Speciation/offspring.dot");

        // Create a pool and speciate the genomes
        Pool pool = new Pool();
        pool.speciate(genomes);

        // Output compatibility distances
        System.out.println("\nCompatibility distances:");
        for (int i = 0; i < genomes.size(); i++) {
            for (int j = i + 1; j < genomes.size(); j++) {
                float distance = pool.compatibilityDistance(
                    genomes.get(i).getGenome(), 
                    genomes.get(j).getGenome()
                );
                System.out.println("Distance between Genome " + (i + 1) + " and Genome " + (j + 1) + ": " + distance);

                // Output matching, disjoint, and excess connections
                System.out.println("  Matching: " + pool.getMatchingConnections(
                    genomes.get(i).getGenome(), genomes.get(j).getGenome()).size());
                System.out.println("  Disjoint: " + pool.getDisjointConnections(
                    genomes.get(i).getGenome(), genomes.get(j).getGenome()).size());
                System.out.println("  Excess: " + pool.getExcessConnections(
                    genomes.get(i).getGenome(), genomes.get(j).getGenome()).size());
            }
        }

        // Output results of speciation
        int speciesIndex = 1;
        for (Species species : pool.getListOfSpecies()) {
            System.out.println("Species " + speciesIndex + ":");
//            for (GenomeWithFitness genome : species.getGenomes()) {
//                System.out.println(" - Genome with fitness: " + genome.getFitness());
//            }
            // show genomes in the species
            System.out.println("  Contains the following genomes:");
            for (GenomeWithFitness genome : species.getGenomes()) {
                System.out.println("    Genome Fitness: " + genome.getFitness() + ", Connections: " + genome.getGenome().connections.size());
            }
            speciesIndex++;
        }

        // Validate expected results
        System.out.println("\nValidation:");
        System.out.println("Expected species count: 2"); // compatibility threshold is set to 3
        System.out.println("Actual species count: " + pool.getListOfSpecies().size());


        // Export each species to DOT files
        speciesIndex = 1;
        for (Species species : pool.getListOfSpecies()) {
        	int genomeIndex = 1;
            for (GenomeWithFitness genome : species.getGenomes()) {
                genome.getGenome().exportToDot("Images/Speciation/species_" + speciesIndex + "_" + genomeIndex + "_genome.dot");
                genomeIndex++;
            }
            species.exportToDot("Images/Speciation/species_" + speciesIndex + ".dot");
            speciesIndex++;
        }
    }
}

package com.hepl.test;

import com.hepl.NEAT.*;
import java.util.ArrayList;
import java.util.List;

public class TestSpeciation {

    public static void main(String[] args) {
        // Parent 1
        Genome parent1 = new Genome();

        Node p1Node1I = new Node(Node.Type.INPUT);
        Node p1Node2I = new Node(Node.Type.INPUT);
        Node p1Node3I = new Node(Node.Type.INPUT);
        Node p1Node4O = new Node(Node.Type.OUTPUT);
        Node p1Node5H = new Node(Node.Type.HIDDEN);

        parent1.addNode(p1Node1I);
        parent1.addNode(p1Node2I);
        parent1.addNode(p1Node3I);
        parent1.addNode(p1Node4O);
        parent1.addNode(p1Node5H);

        Connection p1Con14 = new Connection(p1Node1I, p1Node4O, 1);
        Connection p1Con24 = new Connection(p1Node2I, p1Node4O, 1);
        Connection p1Con34 = new Connection(p1Node3I, p1Node4O, 1);
        Connection p1Con25 = new Connection(p1Node2I, p1Node5H, 1);
        Connection p1Con54 = new Connection(p1Node5H, p1Node4O, 1);
        Connection p1Con15 = new Connection(p1Node1I, p1Node5H, 1);

        p1Con24.setConnectionState(Connection.State.DISABLED);

        parent1.addConnection(p1Con14);
        parent1.addConnection(p1Con24);
        parent1.addConnection(p1Con34);
        parent1.addConnection(p1Con25);
        parent1.addConnection(p1Con54);
        parent1.addConnection(p1Con15);

        parent1.exportToDot("Images/Speciation/parent1.dot");

        // Parent 2
        Genome parent2 = new Genome();

        Node p2Node1I = new Node(Node.Type.INPUT);
        Node p2Node2I = new Node(Node.Type.INPUT);
        Node p2Node3I = new Node(Node.Type.INPUT);
        Node p2Node4O = new Node(Node.Type.OUTPUT);
        Node p2Node5H = new Node(Node.Type.HIDDEN);
        Node p2Node6H = new Node(Node.Type.HIDDEN);

        parent2.addNode(p2Node1I);
        parent2.addNode(p2Node2I);
        parent2.addNode(p2Node3I);
        parent2.addNode(p2Node4O);
        parent2.addNode(p2Node5H);
        parent2.addNode(p2Node6H);

        Connection p2Con14 = new Connection(p2Node1I, p2Node4O, 1);
        Connection p2Con24 = new Connection(p2Node2I, p2Node4O, 1);
        Connection p2Con34 = new Connection(p2Node3I, p2Node4O, 1);
        Connection p2Con25 = new Connection(p2Node2I, p2Node5H, 1);
        Connection p2Con54 = new Connection(p2Node5H, p2Node4O, 1);
        Connection p2Con56 = new Connection(p2Node5H, p2Node6H, 1);
        Connection p2Con64 = new Connection(p2Node6H, p2Node4O, 1);
        Connection p2Con35 = new Connection(p2Node3I, p2Node5H, 1);
        Connection p2Con16 = new Connection(p2Node1I, p2Node6H, 1);

        p2Con24.setConnectionState(Connection.State.DISABLED);
        p2Con54.setConnectionState(Connection.State.DISABLED);

        parent2.addConnection(p2Con14);
        parent2.addConnection(p2Con24);
        parent2.addConnection(p2Con34);
        parent2.addConnection(p2Con25);
        parent2.addConnection(p2Con54);
        parent2.addConnection(p2Con56);
        parent2.addConnection(p2Con64);
        parent2.addConnection(p2Con35);
        parent2.addConnection(p2Con16);

        parent2.exportToDot("Images/Speciation/parent2.dot");

        // Offspring
        Genome offspring = new Genome();

        Node oNode1I = new Node(Node.Type.INPUT);
        Node oNode2I = new Node(Node.Type.INPUT);
        Node oNode3I = new Node(Node.Type.INPUT);
        Node oNode4O = new Node(Node.Type.OUTPUT);
        Node oNode5H = new Node(Node.Type.HIDDEN);
        Node oNode6H = new Node(Node.Type.HIDDEN);

        offspring.addNode(oNode1I);
        offspring.addNode(oNode2I);
        offspring.addNode(oNode3I);
        offspring.addNode(oNode4O);
        offspring.addNode(oNode5H);
        offspring.addNode(oNode6H);

        Connection oCon14 = new Connection(oNode1I, oNode4O, 1);
        Connection oCon24 = new Connection(oNode2I, oNode4O, 1);
        Connection oCon34 = new Connection(oNode3I, oNode4O, 1);
        Connection oCon25 = new Connection(oNode2I, oNode5H, 1);
        Connection oCon54 = new Connection(oNode5H, oNode4O, 1);
        Connection oCon56 = new Connection(oNode5H, oNode6H, 1);
        Connection oCon64 = new Connection(oNode6H, oNode4O, 1);
        Connection oCon15 = new Connection(oNode1I, oNode5H, 1);
        Connection oCon35 = new Connection(oNode3I, oNode5H, 1);
        Connection oCon16 = new Connection(oNode1I, oNode6H, 1);

        oCon24.setConnectionState(Connection.State.DISABLED);
        oCon54.setConnectionState(Connection.State.DISABLED);

        offspring.addConnection(oCon14);
        offspring.addConnection(oCon24);
        offspring.addConnection(oCon34);
        offspring.addConnection(oCon25);
        offspring.addConnection(oCon54);
        offspring.addConnection(oCon56);
        offspring.addConnection(oCon64);
        offspring.addConnection(oCon15);
        offspring.addConnection(oCon35);
        offspring.addConnection(oCon16);

        offspring.exportToDot("Images/Speciation/offspring.dot");

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
                System.out.println("    Connections: " + genome.getGenome().connections.size());
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

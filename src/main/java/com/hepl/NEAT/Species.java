package com.hepl.NEAT;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Species {
	// A species -> List of genomes where the compatibility distance is less than a certain threshold
	private GenomeWithFitness representativeGenome; // represent all the species
	private List<GenomeWithFitness> genomes = new ArrayList<GenomeWithFitness>(); // individuals
	
	private int stagnationCounter;
	
	private float bestFitnessInside;
	private float averageFitness;
	
	private static Random rand = new Random();
	
	public Species(GenomeWithFitness representativeGenome) {
		this.representativeGenome = representativeGenome;
		this.genomes.add(representativeGenome);
		this.stagnationCounter = 0;
	}
	public Species()
	{
		this.stagnationCounter = 0;
	}
	
	/*
	 * GETTERS
	 */
	public float getAverageFitness() {
		return averageFitness;
	}
		
	public GenomeWithFitness getRepresentativeGenome() {
		return representativeGenome;
	}
	
	public int getStagnationCounter() {
		return stagnationCounter;
	}
	
	public List<GenomeWithFitness> getGenomes(){
		return genomes;
	}
	
	// Best Fitness in the species
	public float getBestFitnessInside() {
		this.sortGenomesByFitness();
		bestFitnessInside = genomes.get(0).getFitness();
		return bestFitnessInside;
	}

	/*
	 * METHODS
	 */
	public void addGenome(GenomeWithFitness g) {
		if(genomes.isEmpty())
		{
			this.representativeGenome = g;
			this.stagnationCounter = 0;	
		}
		genomes.add(g);
	}
	
	public void setRepresentativeGenome(GenomeWithFitness representativeGenome) {
		this.representativeGenome = representativeGenome;
	}

	// Average fitness = (all the fitness of all the genomes) / (#genomes in the species)
	public void calculateAverageFitness() {
		float totalFitness = 0;
		
		for (GenomeWithFitness genome : genomes) {
			totalFitness += genome.getFitness();
		}
		this.averageFitness = totalFitness / genomes.size();
	}
	
	// Adjust fitness = (fitness of each genome) / (#genomes in the species)
	public void adjustFitness() {
		for (GenomeWithFitness genome : genomes) {
			genome.setFitness(genome.getFitness() / genomes.size());
		}
	}
	
	// Contains the best genome ? With the best fitness in all the population
	public boolean haveFittestGenome(float bestFitnessInPopulation) {
		for (GenomeWithFitness genome : genomes) {
			if (genome.getFitness() == bestFitnessInPopulation) {
				return true;
			}
		}
		return false;
	}
	
	// History of a species, to be incremented for each epoch (generation)
	public void incrementStagnationCounter() {
		stagnationCounter++;
	}
	
	// Sort genomes by their fitness
	public void sortGenomesByFitness() { // https://stackoverflow.com/questions/12542185/sort-a-java-collection-object-based-on-one-field-in-it
		genomes.sort(Comparator.comparing(GenomeWithFitness::getFitness).reversed());
	}
	
	// Remove weak genomes
	public void removeWeakGenome() {
		// if 50% -> divide by 2, if 0%, keep the best
		this.sortGenomesByFitness();
		int indexToKeep = Math.max(1, (int)(genomes.size() * AppConfig.NEAT_PERCENTAGE_TO_KEEP));
		
		for (int i = indexToKeep; i < genomes.size(); i++) {
			genomes.remove(i);
		}
	}
	
	// Select a random genome used to be the next representative in the next generation
	public GenomeWithFitness selectRandomGenome() {
		return genomes.get(rand.nextInt(genomes.size()));
	}
	
	public void reset() {
		genomes.clear();
		this.stagnationCounter = 0;
	}
	
	// Same as in Genome but for species
	public void exportToDot(String filename) {
	    try (FileWriter writer = new FileWriter(filename)) {
	        writer.write("digraph Species {\n");
	        writer.write("\trankdir=LR;\n"); // Layout Left to Right

	        int genomeIndex = 0; // index of each genome in the species

	        for (GenomeWithFitness genomeWithFitness : genomes) {
	            Genome genome = genomeWithFitness.getGenome();

	            // subgraph for each genome
	            writer.write(String.format("\tsubgraph cluster_%d {\n", genomeIndex));
	            writer.write(String.format("\t\tlabel=\"Genome %d (Fitness: %.2f)\";\n", genomeIndex, genomeWithFitness.getFitness()));

	            // Nodes
	            for (Node node : genome.nodes) {
	                String label = String.format("ID: %d", node.id);
	                writer.write(String.format("\t\"%d_%d\" [label=\"%s\", shape=%s];\n",
	                        genomeIndex, node.id, label,
	                        node.type == Node.Type.INPUT ? "ellipse" :
	                                node.type == Node.Type.OUTPUT ? "doublecircle" : "circle"));
	            }

	            // Connections
	            for (Connection connection : genome.connections) {
	                String label = String.format("Weight: %.2f\nInnovation: %d", connection.getWeight(), connection.getInnovation());
	                writer.write(String.format("\t\"%d_%d\" -> \"%d_%d\" [label=\"%s\", style=%s];\n",
	                        genomeIndex, connection.getInputNode().id,
	                        genomeIndex, connection.getOutputNode().id,
	                        label,
	                        connection.getConnectionState() == Connection.State.ENABLED ? "solid" : "dashed"));
	            }

	            writer.write("\t}\n");
	            genomeIndex++;
	        }

	        writer.write("}\n");
	    } catch (IOException e) {
	        System.err.println("Error writing DOT file: " + e.getMessage());
	    }
	}
}

package com.hepl.NEAT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Pool {
	private List<Species> listOfSpecies = new ArrayList<Species>();	
//    ArrayList<Genome> genomes = new ArrayList<Genome>();
	public Pool()
	{
		listOfSpecies.add(new Species());	
	}
	public Pool(int size)
	{
		listOfSpecies.add(new Species(new GenomeWithFitness(new Genome(false), -1)));
		for (int i = 0; i < size-1; i++) 
		{
			listOfSpecies.get(0).addGenome(new GenomeWithFitness(new Genome(false), -1));	
		}
	}
    /*
     * TOOLS
     */
    private List<Connection> sortConnectionsByInnovation(List<Connection> con) { // https://stackoverflow.com/questions/1814095/sorting-an-arraylist-of-objects-using-a-custom-sorting-order
        Collections.sort(con);
        return con;
    }
    
    private Connection findConnectionByInnovation(List<Connection> connections, int innovation) {
    	for (Connection con : connections) {
    		if (con.getInnovation() == innovation) {
    			return con;
    		}
    	}
    	return null;
    }
    
    private Node findNodeById(List<Node> nodes, int id) {
//    	for (Node node : nodes) {
//    		if (node.id == id) {
//    			return node;
//    		}
//    	}
//    	return null;
    	if (id < nodes.size()) return nodes.get(id);
    	return null;
    }
    
    // what is the best fitness through the whole population ? 
    public float getBestFitness() {
    	float bestFitness = 0f;
    	for (Species species : listOfSpecies) {
    		bestFitness = Math.max(bestFitness, species.getBestFitnessInside());
    	}
    	
    	return bestFitness;
    }
    
    // the total adjusted fitness in the population : sum of average fitness of each species
    private float getTotalAdjustedFitness() {
    	float totalFitness = 0f;
    	for (Species species : listOfSpecies) {
    		species.calculateAverageFitness();
    		totalFitness += species.getAverageFitness();
    	}
    	
    	return totalFitness;
    }
    
    // return the current population
    public List<Species> getListOfSpecies() {
		return listOfSpecies;
	}

    /*
     * MATCHING
     */
    public List<Connection> getMatchingConnections(Genome p1, Genome p2) {
        List<Connection> matchingConnections = new ArrayList<Connection>();

        // List<Connection> p1Connections = new ArrayList<Connection>(p1.connections);
        // List<Connection> p2Connections = new ArrayList<Connection>(p1.connections);

        List<Connection> p1Connections = sortConnectionsByInnovation(new ArrayList<Connection>(p1.connections));
        List<Connection> p2Connections = sortConnectionsByInnovation(new ArrayList<Connection>(p2.connections));


        for (int i = 0; i < p1Connections.size(); i++) {
            for (int j = 0; j < p2Connections.size(); j++) {
                Connection con1 = p1Connections.get(i);
                Connection con2 = p2Connections.get(j);

                if (con1.getInnovation() == con2.getInnovation()) { // same innovation number -> matching gene
                    matchingConnections.add(con1);
                    break;
                } else if (con1.getInnovation() < con2.getInnovation()) { // not a matching gene
                    break;
                }
            }
        }

        return matchingConnections;
    }
    
    public List<Connection> getDisjointConnections(Genome p1, Genome p2) {
        List<Connection> disjointConnections = new ArrayList<Connection>();

        List<Connection> p1Connections = sortConnectionsByInnovation(new ArrayList<Connection>(p1.connections));
        List<Connection> p2Connections = sortConnectionsByInnovation(new ArrayList<Connection>(p2.connections));
        
        for (int i = 0; i < p1Connections.size(); i++) {
        	boolean itIs = true;
        	Connection con1 = p1Connections.get(i);
        	for (int j = 0; j < p2Connections.size(); j++) {
        		Connection con2 = p2Connections.get(j);
        		if (con1.getInnovation() == con2.getInnovation()) {
        			itIs = false;
        			break;
        		} else if (con1.getInnovation() < con2.getInnovation()) {
        			break;
        		}
        	}
        	if (itIs) {
        		disjointConnections.add(con1);
        	}
        }
        
        for (int j = 0; j < p2Connections.size(); j ++) {
        	boolean itIs = true;
        	Connection con2 = p2Connections.get(j);
        	for (int i = 0; i < p1Connections.size(); i++) {
        		Connection con1 = p1Connections.get(i);
        		if (con2.getInnovation() == con1.getInnovation()) {
        			itIs = false;
        			break;
        		} else if (con2.getInnovation() < con1.getInnovation()) {
        			break;
        		}
        	}
        	if (itIs) {
        		disjointConnections.add(con2);
        	}
        }
        
        for (Connection con : getExcessConnections(p1,p2)) {
        	if (disjointConnections.contains(con)) {
        		disjointConnections.remove(con);
        	}
        }

        return disjointConnections;
    }
    
    public List<Connection> getExcessConnections(Genome p1, Genome p2) {
        List<Connection> excessConnections = new ArrayList<Connection>();

        List<Connection> p1Connections = sortConnectionsByInnovation(new ArrayList<Connection>(p1.connections));
        List<Connection> p2Connections = sortConnectionsByInnovation(new ArrayList<Connection>(p2.connections));


        for (int i = 0; i < p1Connections.size(); i++) {
    		boolean itIs = true;
    		Connection con1 = p1Connections.get(i);
        	for (int j = 0; j < p2Connections.size(); j++) {
        		Connection con2 = p2Connections.get(j);
        		if (con1.getInnovation() <= con2.getInnovation()) {
        			itIs = false;
        			break;
        		}
        	}
        	
        	if (itIs) {
        		excessConnections.add(con1);
        	}
        }
        
        for (int j = 0; j < p2Connections.size(); j++) {
        	boolean itIs = true;
        	Connection con2 = p2Connections.get(j);
        	for (int i = 0; i < p1Connections.size(); i++) {
        		Connection con1 = p1Connections.get(i);
        		if (con2.getInnovation() <= con1.getInnovation()) {
        			itIs = false;
        			break;
        		}
        	}
        	if (itIs) {
        		excessConnections.add(con2);
        	}
        }

        return excessConnections;
    }
    
    /*
     * COMPABILITY DISTANCE
     */
    public float averageWeightDifference(Genome p1, Genome p2) {
    	// get matching connections
    	List<Connection> matchingConnections = getMatchingConnections(p1,p2);
    	
    	float totalWeightDifference = 0f;
    	
    	for (Connection match : matchingConnections) {
    		Connection otherConnection = findConnectionByInnovation(p2.connections, match.getInnovation()); // 
    		totalWeightDifference += Math.abs(match.getWeight() - otherConnection.getWeight()); // calculate the weight
    	}
    	
    	return totalWeightDifference/matchingConnections.size();
    }
    
	public float compatibilityDistance(Genome p1, Genome p2) {
		// Bad for performance, the three methods are almost identical		
		int excessGenes = getExcessConnections(p1, p2).size(); // E
		int disjointGenes = getDisjointConnections(p1, p2).size(); // D
		float avgWeightDiff = averageWeightDifference(p1, p2); // W
		
		// N = 1 for small genome (< than 20 genes)
		int largestGenomeSize = Math.max(p1.connections.size(), p2.connections.size()); // N
		largestGenomeSize = largestGenomeSize < 20 ? 1 : largestGenomeSize;
		
		return (excessGenes * AppConfig.NEAT_C1 / largestGenomeSize) + (disjointGenes * AppConfig.NEAT_C2 / largestGenomeSize) + avgWeightDiff * AppConfig.NEAT_C3;
	}

    /*
     * CROSSOVER
     */
    public Genome GenomeCrossover(Genome p1_in, Genome p2_in, float fitnessP1, float fitnessP2) {
        Genome child = new Genome(true);
        
        // synchronize of the ids of the parent nodes
//        p1.synchronizeNodeIds();
//        p2.sy nchronizeNodeIds();
		Genome p1 = p1_in.clone();
		Genome p2 = p2_in.clone();
        // Determine the fitter parent
        Genome fittest = fitnessP1 >= fitnessP2 ? p1 : p2;
        Genome other = fitnessP1 >= fitnessP2 ? p2 : p1;

        // Get matching, disjoint, and excess connections
        List<Connection> matchingConnections = getMatchingConnections(fittest, other);
        List<Connection> disjointConnections = getDisjointConnections(fittest, other);
        List<Connection> excessConnections = getExcessConnections(fittest, other);


		// Add nodes from parents (no duplicates)
		for (Node node : fittest.nodes) {
			if (findNodeById(child.nodes, node.id) == null) {
				child.addNode(node);
			}
		}

        // Add matching connections with averaged weights
        for (Connection con1 : matchingConnections) {
            Connection con2 = findConnectionByInnovation(other.connections, con1.getInnovation());

            // copy of the connection + change weight
            Connection newCon = con1;
            newCon.setWeight((con1.getWeight() + con2.getWeight()) / 2);

            // randomly change the state
            newCon.setConnectionState(Math.random() > 0.5 ? con1.getConnectionState() : con2.getConnectionState());
            child.addConnection(newCon);
        }

        // Add disjoint and excess connections from the fittest parent
        for (Connection con : disjointConnections) {
            Connection newCon = con;
            child.addConnection(newCon);
        }
        for (Connection con : excessConnections) {
        	if (fittest.connections.contains(con)) {
	            Connection newCon = con;
	            child.addConnection(newCon);
        	}
        }
        child.synchronizeNodeIds();
        return child;
    }

    /*
     * SPECIATION
     */
    public void resetSpecies() {
    	for (Species species : listOfSpecies) {
    		if (species.getGenomes().isEmpty()) {
    			continue;
    		}
    		
    		GenomeWithFitness newRepresentative = species.getRepresentativeGenome();
    		species.reset(); // After selecting a new representative (it clear the list)
    		
    		species.setRepresentativeGenome(newRepresentative);
    		species.addGenome(species.getRepresentativeGenome());
    	}
    }
    
    public void addSpecies(Species species) {
    	listOfSpecies.add(species);
    }
    
    // Create the specimens
    public void speciate(List<GenomeWithFitness> genomes) {
    	// reset all the species
    	resetSpecies();
    	
    	// Assign genomes to species in case of delta < delta threshold, else create a new one
    	for (GenomeWithFitness genome : genomes) {
    		boolean foundSpecies = false;
    		
    		for (Species species : listOfSpecies) {
    			float compatibilityDistance = compatibilityDistance(genome.getGenome(), species.getRepresentativeGenome().getGenome());
    			if (compatibilityDistance < AppConfig.NEAT_COMPATIBILITY_THRESHOLD) {
    				species.addGenome(genome);
    				foundSpecies = true;
    				break;
    			}
    		}
    		
    		if (!foundSpecies) {
    			listOfSpecies.add(new Species(genome));
    		}
    	}
    }
    
    // Remove stagnant species if it doesn't contains the fittest genome (and empty species)
    public void removeStagnantSpecies(float bestFittestInPopulation, int maxStagnation) {
    	List<Species> listOfStagnantSpecies = new ArrayList<Species>();
    	
    	for (Species species :listOfSpecies) {
    		boolean isStagnant = species.getStagnationCounter() > maxStagnation;
    		if (isStagnant && !species.haveFittestGenome(bestFittestInPopulation) || species.getGenomes().isEmpty()) {
    			listOfStagnantSpecies.add(species);
    		}
    	}
    	
    	listOfSpecies.removeAll(listOfStagnantSpecies);
    }

	public List<Species> getSpecies(){return listOfSpecies;}

	public void addGenomeToSpecies(Species species, GenomeWithFitness genome) {
    	species.addGenome(genome);
	}

}
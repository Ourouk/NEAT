package com.hepl.NEAT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pool {
    ArrayList<Genome> genomes = new ArrayList<Genome>();

    /*
     * TOOLS
     */
    private List<Connection> sortConnectionsByInnovation(List<Connection> con) { // https://stackoverflow.com/questions/1814095/sorting-an-arraylist-of-objects-using-a-custom-sorting-order
        Collections.sort(con);
        return con;
    }
    
    private Connection findConnectionByInnovation(List<Connection> connections, int innovation) {
    	for (Connection con : connections) {
    		if (con.innovation == innovation) {
    			return con;
    		}
    	}
    	return null;
    }

    /*
     * MATCHING
     */
    private List<Connection> getMatchingConnections(Genome p1, Genome p2) {
        List<Connection> matchingConnections = new ArrayList<Connection>();

        // List<Connection> p1Connections = new ArrayList<Connection>(p1.connections);
        // List<Connection> p2Connections = new ArrayList<Connection>(p1.connections);

        List<Connection> p1Connections = sortConnectionsByInnovation(new ArrayList<Connection>(p1.connections));
        List<Connection> p2Connections = sortConnectionsByInnovation(new ArrayList<Connection>(p2.connections));


        for (int i = 0; i < p1Connections.size(); i++) {
            for (int j = 0; j < p2Connections.size(); j++) {
                Connection con1 = p1Connections.get(i);
                Connection con2 = p2Connections.get(j);

                if (con1.innovation == con2.innovation) { // same innovation number -> matching gene
                    matchingConnections.add(con1);
                    break;
                } else if (con1.innovation < con2.innovation) { // not a matching gene
                    break;
                }
            }
        }

        return matchingConnections;
    }
    
    private List<Connection> getDisjointConnections(Genome p1, Genome p2) {
        List<Connection> disjointConnections = new ArrayList<Connection>();

        List<Connection> p1Connections = sortConnectionsByInnovation(new ArrayList<Connection>(p1.connections));
        List<Connection> p2Connections = sortConnectionsByInnovation(new ArrayList<Connection>(p2.connections));
        
        for (int i = 0; i < p1Connections.size(); i++) {
        	boolean itIs = true;
        	Connection con1 = p1Connections.get(i);
        	for (int j = 0; j < p2Connections.size(); j++) {
        		Connection con2 = p2Connections.get(j);
        		if (con1.innovation == con2.innovation) {
        			itIs = false;
        			break;
        		} else if (con1.innovation < con2.innovation) {
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
        		if (con2.innovation == con1.innovation) {
        			itIs = false;
        			break;
        		} else if (con2.innovation < con1.innovation) {
        			break;
        		}
        	}
        	if (itIs) {
        		disjointConnections.add(con2);
        	}
        }

        return disjointConnections;
    }
    
    private List<Connection> getExcessConnections(Genome p1, Genome p2) {
        List<Connection> excessConnections = new ArrayList<Connection>();

        List<Connection> p1Connections = sortConnectionsByInnovation(new ArrayList<Connection>(p1.connections));
        List<Connection> p2Connections = sortConnectionsByInnovation(new ArrayList<Connection>(p2.connections));


        for (int i = 0; i < p1Connections.size(); i++) {
    		boolean itIs = true;
    		Connection con1 = p1Connections.get(i);
        	for (int j = 0; j < p2Connections.size(); j++) {
        		Connection con2 = p2Connections.get(j);
        		if (con1.innovation <= con2.innovation) {
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
        		if (con2.innovation <= con1.innovation) {
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
    		Connection otherConnection = findConnectionByInnovation(p2.connections, match.innovation); // 
    		totalWeightDifference += Math.abs(match.getWeight() - otherConnection.getWeight()); // calculate the weight
    	}
    	
    	return totalWeightDifference/matchingConnections.size();
    }
    
	public float compatibilityDistance(Genome p1, Genome p2, int c1, int c2, int c3) {
		// N = 1 for small genome (< than 20 genes)
		// Bad for performance, the three methods are almost identical		
		int excessGenes = getExcessConnections(p1, p2).size();
		int disjointGenes = getDisjointConnections(p1, p2).size();
		float avgWeightDiff = averageWeightDifference(p1, p2);
		return excessGenes * c1 + disjointGenes * c2 + avgWeightDiff * c3;
	}

    /*
     * CROSSOVER
     */
    public Genome GenomeCrossover(Genome p1, Genome p2, float fitnessP1, float fitnessP2) {
        // between two parent
        Genome child = new Genome();

        // who's the fittest parent ?
        Genome fittest = fitnessP1 >= fitnessP2 ? p1 : p2;
        Genome other = fitnessP1 >= fitnessP2 ? p2 : p1;
        
        // matching, disjoint and excess connections
        List<Connection> matchingConnections = getMatchingConnections(fittest,other);
        List<Connection> disjointConnections = getDisjointConnections(fittest,other);
        List<Connection> excessConnections = getExcessConnections(fittest,other);
        
        List<Connection> childConnections = new ArrayList<Connection>();
        
        // add matching connections
        for (Connection match : matchingConnections) {
        	Connection otherConnection = findConnectionByInnovation(other.connections, match.innovation);
        	
        	// average combine weights + random enabled/disabled
        	Integer combinedWeight = (match.getWeight() + otherConnection.getWeight()/2);
        	// boolean enabled = Math.random() > 0.5 ? match.setConnectionState(Connection.State.ENABLED) : match.setConnectionState(Connection.State.DISABLED);
        	
        	//childConnections.add(new Connection(match.getInputNode(), match.getOutputNode(), combinedWeight));
        	match.setWeight(combinedWeight);
        	childConnections.add(match);
        }
        
        // add disjoint and excess connections from the fittest parent
        for (Connection disjoint : disjointConnections) {
        	childConnections.add(disjoint);
        }
        for (Connection excess : excessConnections) {
        	childConnections.add(excess);
        }
        
        child.connections = childConnections;
        return child;
    }
}
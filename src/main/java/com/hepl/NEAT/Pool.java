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
    
    private Node findNodeById(List<Node> nodes, int id) {
    	for (Node node : nodes) {
    		if (node.id == id) {
    			return node;
    		}
    	}
    	return null;
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
    
    public List<Connection> getDisjointConnections(Genome p1, Genome p2) {
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
        Genome child = new Genome();
        
        // synchronize of the ids of the parent nodes
//        p1.synchronizeNodeIds();
//        p2.sy nchronizeNodeIds();

        // Determine the fitter parent
        Genome fittest = fitnessP1 >= fitnessP2 ? p1 : p2;
        Genome other = fitnessP1 >= fitnessP2 ? p2 : p1;

        // Get matching, disjoint, and excess connections
        List<Connection> matchingConnections = getMatchingConnections(fittest, other);
        List<Connection> disjointConnections = getDisjointConnections(fittest, other);
        List<Connection> excessConnections = getExcessConnections(fittest, other);

        // Add matching connections with averaged weights
        for (Connection con1 : matchingConnections) {
            Connection con2 = findConnectionByInnovation(other.connections, con1.innovation);
            
            // copy of the connection + change weight
            Connection newCon = con1.copy();
            newCon.setWeight((con1.getWeight() + con2.getWeight()) / 2);
            
            // randomly change the state
            //newCon.setConnectionState(Math.random() > 0.5 ? con1.getConnectionState() : con2.getConnectionState());
            child.addConnection(newCon);
        }

        // Add disjoint and excess connections from the fittest parent
        for (Connection con : disjointConnections) {
            Connection newCon = con.copy();
            child.addConnection(newCon);
        }
        for (Connection con : excessConnections) {
        	if (fittest.connections.contains(con)) {
	            Connection newCon = con.copy();
	            child.addConnection(newCon);
        	}
        }

        // Add nodes from parents (no duplicates)
        for (Node node : fittest.nodes) {
            if (findNodeById(child.nodes, node.id) == null) {
                child.addNode(node);
            }
        }
        for (Node node : other.nodes) {
            if (findNodeById(child.nodes, node.id) == null) {
                child.addNode(node);
            }
        }

        child.synchronizeNodeIds();
        return child;
    }

}
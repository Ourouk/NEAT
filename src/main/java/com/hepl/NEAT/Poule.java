package com.hepl.NEAT;

import java.util.ArrayList;
import java.util.List;

public class Poule {
    ArrayList<Genome> genomes = new ArrayList<Genome>();

    /*
     * TOOLS
     */
    private List<Connection> sortConnectionsByInnovation(List<Connection> con) {
        con.sort((a, b) -> Integer.compare(a.getInnovation(), b.getInnovation()));
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

    /*
     * MATCHING
     */
    private List<Connection> getMatchingConnections(Genome p1, Genome p2) {
        List<Connection> matchingConnections = new ArrayList<>();

        List<Connection> p1Connections = sortConnectionsByInnovation(new ArrayList<>(p1.connections));
        List<Connection> p2Connections = sortConnectionsByInnovation(new ArrayList<>(p2.connections));

        for (Connection con1 : p1Connections) {
            for (Connection con2 : p2Connections) {
                if (con1.getInnovation() == con2.getInnovation()) {
                    matchingConnections.add(con1);
                    break;
                }
            }
        }

        return matchingConnections;
    }

    private List<Connection> getDisjointConnections(Genome p1, Genome p2) {
        List<Connection> disjointConnections = new ArrayList<>();
        List<Connection> p1Connections = sortConnectionsByInnovation(new ArrayList<>(p1.connections));
        List<Connection> p2Connections = sortConnectionsByInnovation(new ArrayList<>(p2.connections));

        int maxInnovationP2 = p2Connections.isEmpty() ? 0 : p2Connections.get(p2Connections.size() - 1).getInnovation();
        int maxInnovationP1 = p1Connections.isEmpty() ? 0 : p1Connections.get(p1Connections.size() - 1).getInnovation();

        for (Connection con1 : p1Connections) {
            if (findConnectionByInnovation(p2Connections, con1.getInnovation()) == null && con1.getInnovation() <= maxInnovationP2) {
                disjointConnections.add(con1);
            }
        }

        for (Connection con2 : p2Connections) {
            if (findConnectionByInnovation(p1Connections, con2.getInnovation()) == null && con2.getInnovation() <= maxInnovationP1) {
                disjointConnections.add(con2);
            }
        }

        return disjointConnections;
    }

    private List<Connection> getExcessConnections(Genome p1, Genome p2) {
        List<Connection> excessConnections = new ArrayList<>();
        List<Connection> p1Connections = sortConnectionsByInnovation(new ArrayList<>(p1.connections));
        List<Connection> p2Connections = sortConnectionsByInnovation(new ArrayList<>(p2.connections));

        int maxInnovationP2 = p2Connections.isEmpty() ? 0 : p2Connections.get(p2Connections.size() - 1).getInnovation();
        int maxInnovationP1 = p1Connections.isEmpty() ? 0 : p1Connections.get(p1Connections.size() - 1).getInnovation();

        for (Connection con1 : p1Connections) {
            if (con1.getInnovation() > maxInnovationP2) {
                excessConnections.add(con1);
            }
        }

        for (Connection con2 : p2Connections) {
            if (con2.getInnovation() > maxInnovationP1) {
                excessConnections.add(con2);
            }
        }

        return excessConnections;
    }

    /*
     * CROSSOVER
     */
    public Genome GenomeCrossover(Genome p1, Genome p2, float fitnessP1, float fitnessP2) {
        Genome child = new Genome();

        // Determine the fitter parent
        Genome fittest = fitnessP1 >= fitnessP2 ? p1 : p2;
        Genome other = fitnessP1 >= fitnessP2 ? p2 : p1;

        // Get matching, disjoint, and excess connections
        List<Connection> matchingConnections = getMatchingConnections(fittest, other);
        List<Connection> disjointConnections = getDisjointConnections(fittest, other);
        List<Connection> excessConnections = getExcessConnections(fittest, other);

        // Add matching connections with averaged weights
        for (Connection con1 : matchingConnections) {

            Connection con2 = findConnectionByInnovation(other.connections, con1.getInnovation());
            Connection newCon = con1.clone();

            newCon.setWeight((con1.getWeight() + con2.getWeight()) / 2);
            newCon.setConnectionState(Math.random() > 0.5 ? con1.getConnectionState() : con2.getConnectionState());
            child.addConnection(newCon);
        }

        // Add disjoint and excess connections from the fittest parent
        for (Connection con : disjointConnections) {
            child.addConnection(con.clone());
        }
        for (Connection con : excessConnections) {
            child.addConnection(con.clone());
        }

        // Add nodes from both parents (ensure no duplicates)
        for (Node node : fittest.nodes) {
            if (!child.nodes.contains(node)) {
                child.addNode(node);
            }
        }
        for (Node node : other.nodes) {
            if (!child.nodes.contains(node)) {
                child.addNode(node);
            }
        }

        return child;
    }
}

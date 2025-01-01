package com.hepl.NEAT;

import java.util.ArrayList;
import java.util.List;

public class Poule {
    ArrayList<Genome> genomes = new ArrayList<Genome>();

    /*
     * TOOLS
     */
    private List<Connection> sortConnectionsByInnovation(List<Connection> con) {
        con.sort((a, b) -> Integer.compare(a.innovation, b.innovation));
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
        List<Connection> matchingConnections = new ArrayList<>();

        List<Connection> p1Connections = sortConnectionsByInnovation(new ArrayList<>(p1.connections));
        List<Connection> p2Connections = sortConnectionsByInnovation(new ArrayList<>(p2.connections));

        for (Connection con1 : p1Connections) {
            for (Connection con2 : p2Connections) {
                if (con1.innovation == con2.innovation) {
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

        for (Connection con1 : p1Connections) {
            if (findConnectionByInnovation(p2Connections, con1.innovation) == null) {
                disjointConnections.add(con1);
            }
        }

        for (Connection con2 : p2Connections) {
            if (findConnectionByInnovation(p1Connections, con2.innovation) == null) {
                disjointConnections.add(con2);
            }
        }

        return disjointConnections;
    }

    private List<Connection> getExcessConnections(Genome p1, Genome p2) {
        List<Connection> excessConnections = new ArrayList<>();
        List<Connection> p1Connections = sortConnectionsByInnovation(new ArrayList<>(p1.connections));
        List<Connection> p2Connections = sortConnectionsByInnovation(new ArrayList<>(p2.connections));

        for (Connection con1 : p1Connections) {
            if (con1.innovation > p2Connections.get(p2Connections.size() - 1).innovation) {
                excessConnections.add(con1);
            }
        }

        for (Connection con2 : p2Connections) {
            if (con2.innovation > p1Connections.get(p1Connections.size() - 1).innovation) {
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
            Connection con2 = findConnectionByInnovation(other.connections, con1.innovation);
            int averagedWeight = (con1.getWeight() + con2.getWeight()) / 2;
            Connection newCon = new Connection(con1.getInputNode(), con1.getOutputNode(), averagedWeight);
            newCon.innovation = con1.innovation;
            newCon.setConnectionState(Math.random() > 0.5 ? con1.getConnectionState() : con2.getConnectionState());
            if (!child.connections.contains(newCon)) {
                child.addConnection(newCon);
            }
        }

        // Add disjoint and excess connections from the fittest parent
        for (Connection con : disjointConnections) {
            if (!child.connections.contains(con)) {
                Connection newCon = new Connection(con.getInputNode(), con.getOutputNode(), con.getWeight());
                newCon.innovation = con.innovation;
                newCon.setConnectionState(con.getConnectionState());
                child.addConnection(newCon);
            }
        }
        for (Connection con : excessConnections) {
            if (!child.connections.contains(con)) {
                Connection newCon = new Connection(con.getInputNode(), con.getOutputNode(), con.getWeight());
                newCon.innovation = con.innovation;
                newCon.setConnectionState(con.getConnectionState());
                child.addConnection(newCon);
            }
        }

        // Add nodes from both parents
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

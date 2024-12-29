package com.hepl.NEAT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pool {
    ArrayList<Genome> genomes = new ArrayList<Genome>();

    private List<Connection> sortConnectionsByInnovation(List<Connection> con) { // https://stackoverflow.com/questions/1814095/sorting-an-arraylist-of-objects-using-a-custom-sorting-order
        Collections.sort(con);
        return con;
    }

    private List<Connection> getMatchingConnections(Genome p1, Genome p2) {
        List<Connection> matchingConnections = new ArrayList<Connection>();

        // List<Connection> p1Connection = new ArrayList<Connection>(p1.connections);
        // List<Connection> p2Connection = new ArrayList<Connection>(p1.connections);

        List<Connection> p1Connection = sortConnectionsByInnovation(new ArrayList<Connection>(p1.connections));
        List<Connection> p2Connection = sortConnectionsByInnovation(new ArrayList<Connection>(p2.connections));


        for (int i = 0; i < p1Connection.size(); i++) {
            for (int j = 0; j < p2Connection.size(); j++) {
                Connection con1 = p1Connection.get(i);
                Connection con2 = p2Connection.get(j);

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

    public Genome GenomeCrossover(Genome p1, Genome p2, float fitnessP1, float fitnessP2) {
        // between two parent
        Genome child = new Genome();

        // who's the fittest parent ? Child is the fittest + smth

        // for (Connection con1 : p1.connections) {
        //     Connection con2 : p2.connections;
        // }

        return child;
    }
}
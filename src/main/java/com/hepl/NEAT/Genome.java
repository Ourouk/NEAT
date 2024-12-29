package com.hepl.NEAT;

import java.util.Map;
import java.util.TreeMap;

public class Genome {
    public Map<Integer,Node> nodes = new TreeMap<>();
    public Map<Integer,Connection> connections = new TreeMap<>();

    public Genome() {

    }
    //Interface to edit the treeMap
    public void addNode(Integer i, Node n){
        nodes.put(i,n);
    }
    public void addConnection(Integer i,Connection c){
        connections.put(1,c);
    }
    //Access an object into the treeMap
    public Node getNode(int id){
        return nodes.get(id);
    }
    public Connection getConnection(int id){
        return connections.get(id);
    }
    // Managing Mutation
    //TODO Implement logic
    public void mutAddConnection(){

    }
    public void mutRemoveConnection(){

    }
    public void mutChangeConnectionState(){

    }
    public void mutAddNode(){

    }
    public void mutRemoveNode(){

    }
    public void mutChangeNode(){

    }


}

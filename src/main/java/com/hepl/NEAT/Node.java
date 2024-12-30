package com.hepl.NEAT;

import java.util.ArrayList;

public class Node {
    // Input are the first layer of neat
    // In between Layer are hidden
    // Output are the last layer
    public enum Type {
        INPUT, HIDDEN, OUTPUT;
    }

    public Type type;
    public ArrayList<Connection> incomingConnections;
    public ArrayList<Connection> outgoingConnections;
    private float value = Float.NaN;

    public Node(Type type) {
        this.type = type;
        this.incomingConnections = new ArrayList<Connection>();
        this.outgoingConnections = new ArrayList<Connection>();
    }

    public Node(Type t, ArrayList<Connection> in, ArrayList<Connection> out){
        this.type = Type.HIDDEN;
        this.incomingConnections = in;
        this.outgoingConnections = out;
    }
    //This Wrapper is needed to get the value of the node
    public float getValue(){
        System.out.println("Warning using fallback value calcultation method because of NaN value");
        if(Float.isNaN(this.value)){
            for(Connection c : incomingConnections){
                value += c.getWeight() * c.getInputNode().getValue();
            }
        }
        return value;
    }
    public void setValue(float value){
        this.value = value;
    }
    // Add a connection to the node
    public void addIncomingConnection(Connection connection) {
        incomingConnections.add(connection);
    }

    public void addOutgoingConnection(Connection connection) {
        outgoingConnections.add(connection);
    }
}

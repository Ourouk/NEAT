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
    public int id;
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
            float sum = 0;
            for(Connection c : incomingConnections){
                sum  += c.getWeight() * c.getInputNode().getValue();
            }
            //DONE: Add activation function
            return Genome.sigmoid(sum);
        }else{
            return value;
        }
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
    
    public Node copy() {
    	Node copy = new Node(this.type);
    	copy.id = this.id;
    	return copy;
    }
}

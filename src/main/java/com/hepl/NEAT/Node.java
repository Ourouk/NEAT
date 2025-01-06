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

    public float getValue()
    {
      //System.out.println("|Out :"+value+"|");
        return value;
    }
    public void setValue(float value)
    {
        //System.out.print("|In :"+value+"|");
        this.value = value;
        // Send forward
        for (Connection connection : outgoingConnections) 
        {
            if(connection.getConnectionState() == Connection.State.ENABLED){
            connection.setValue(value); 
            } 
        }
    }
    public void ConnectionFinished(){

        //Allowed to Calc ?
        for (Connection connection : incomingConnections)
        {
            if(connection.getConnectionState() == Connection.State.ENABLED && !connection.IsEmpty())
            {
                return;
            }   
        }
        // Calc
        value = 0;
        for (Connection connection : incomingConnections)
        {
            if(connection.getConnectionState() == Connection.State.ENABLED)
            {
                value += connection.getValue();
            }   
        }
        value = Genome.sigmoid(value);
        //System.out.print("|Node set : "+value+"|");
        // Send forward
        for (Connection connection : outgoingConnections) 
        {
            if(connection.getConnectionState() == Connection.State.ENABLED){
            connection.setValue(value); 
            } 
        }
        //Clean Up
        for (Connection connection : incomingConnections)
        {
            if(connection.getConnectionState() == Connection.State.ENABLED){
                connection.eraseValue();
                } 
        }
    }
    // Add a connection to the node
    public void addIncomingConnection(Connection connection) {
        if(connection.IsValid()){
        incomingConnections.add(connection);
        }
    }

    public void addOutgoingConnection(Connection connection) {
        if(connection.IsValid()){
            outgoingConnections.add(connection);
        }
    }
    @Override
    public Node clone() {
    	Node copy = new Node(this.type);
    	copy.id = this.id;
    	return copy;
    }
}

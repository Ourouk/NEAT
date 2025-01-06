package com.hepl.NEAT;

import java.util.Random;

public class Connection implements Comparable<Connection> {
    static public enum State {
        ENABLED,DISABLED
    }
    // Store information about connected nodes
    // These are finals after the creation
    private final Node inputNode;
    private final Node outputNode;
    // The connection state say if the connection is used
    // Can change during the training
    private State connectionState;
    // The multiplier used when using this connection
    private float weight;
    // The innovation number
    private int innovation;
    //Create a seed for random number generatuin
    private static Random rand = new Random();

    public Connection(Node Input, Node Output,float weight) {
        this.inputNode = Input;
        this.outputNode = Output;
        this.connectionState = State.ENABLED;
        this.weight = weight;
        this.innovation = InnovationCounter.newInnovation(Input.id,Output.id);
    }
    public Connection(Node Input, Node Output,float weight,int innovation) {
        this.inputNode = Input;
        this.outputNode = Output;
        this.connectionState = State.ENABLED;
        this.weight = weight;
        this.innovation = innovation;
    }

    public int getInnovation()
    {
        return innovation;
    }

    public Node getInputNode() {
        return inputNode;
    }
    public Node getOutputNode() {
        return outputNode;
    }
    public State getConnectionState() {
        return connectionState;
    }
    public void setConnectionState(State connectionState) {
        this.connectionState = connectionState;
    }
    public void flipConnectionState() {
        if(this.connectionState == State.ENABLED){
            this.connectionState = State.DISABLED;
        }else{
            this.connectionState = State.ENABLED;
        }
    }
    public float getWeight() {
        return weight;
    }
    public void setWeight(float weight) {
        this.weight = weight;
    }
    // Implement compareTo() method to define sorting based on age
    @Override
    public int compareTo(Connection other) {
        return Integer.compare(this.innovation, other.innovation);
    }

    // Copy a connection
    @Override
    public Connection clone() {
    	Connection copy = new Connection(this.inputNode, this.outputNode, this.weight);
    	copy.innovation = this.innovation;
    	copy.setConnectionState(this.getConnectionState());
    	return copy;
    }
    public boolean IsValid()
    {
        if(outputNode.type == Node.Type.INPUT)
        {
            return false;
        }
        if(inputNode.type == Node.Type.OUTPUT)
        {
            return false;
        }
        if(outputNode.type == Node.Type.OUTPUT)
        {
            return true;
        }
        if(inputNode == outputNode)
        {
            return false;
        }
        return inputNode.id < outputNode.id;
    }
    public static float randomWeight()
    {
        return rand.nextFloat(AppConfig.NEAT_RANDOM_WEIGHT_MIN,AppConfig.NEAT_RANDOM_WEIGHT_MAX);
    }
}

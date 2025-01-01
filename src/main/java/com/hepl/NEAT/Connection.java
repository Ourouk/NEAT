package com.hepl.NEAT;

public class Connection implements Comparable<Connection> {
    public enum State {
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
    private Integer weight;
    // The innovation number
    public int innovation;

    public Connection(Node Input, Node Output,Integer weight) {
        this.inputNode = Input;
        this.outputNode = Output;
        this.connectionState = State.ENABLED;
        this.weight = weight;
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
    public Integer getWeight() {
        return weight;
    }
    public void setWeight(Integer weight) {
        this.weight = weight;
    }
    // Implement compareTo() method to define sorting based on age
    @Override
    public int compareTo(Connection other) {
        return Integer.compare(this.innovation, other.innovation);
    }
}

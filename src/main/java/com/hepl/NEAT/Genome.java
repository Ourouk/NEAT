package com.hepl.NEAT;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class Genome {
    //Store Genome Information
    public Map<Integer,Node> nodes = new TreeMap<Integer,Node>();
    public List<Connection> connections = new ArrayList<Connection>();

    //Create a seed for random number generatuin
    private static Random rand = new Random();

    public Genome() {

    }

    //Interface to edit the treeMap
    public void addNode(Integer i, Node n){
        nodes.put(i,n);
    }

    public void addConnection(Connection c){
        connections.add(c);
    }

    //Access an object into the treeMap
    public Node getNode(int id){
        return nodes.get(id);
    }

    //Generate and evaluate the output of the genome
    public void initNetwork(){
        //Add input nodes
        for(int i = 0; i < AppConfig.NEAT_INPUT_SIZE; i++){
            Node n = new Node(Node.Type.INPUT);
            addNode(i,n);
        }

        //Add bias node
        {
            Node n = new Node(Node.Type.INPUT);
            addNode(AppConfig.NEAT_INPUT_SIZE,n);
        }

        //Add output nodes
        for(int i = 0; i < AppConfig.NEAT_OUTPUT_SIZE; i++){
            Node n = new Node(Node.Type.OUTPUT);
            addNode(AppConfig.NEAT_INPUT_SIZE+i+1,n);
        }

        //Add hidden nodes and connections
        for(int i = 0; i < AppConfig.NEAT_HIDDEN_SIZE; i++){
            Node n = new Node(Node.Type.HIDDEN);
            int node_id = AppConfig.NEAT_INPUT_SIZE+AppConfig.NEAT_OUTPUT_SIZE+i+1;
            //Add all input as incoming connections
            for(int j = 0; j < AppConfig.NEAT_INPUT_SIZE; j++){
                Connection c = new Connection(getNode(j),n,rand.nextInt());
                addConnection(c);
                n.addIncomingConnection(c);
            }
            //Add all output as outgoing connections
            for(int j = AppConfig.NEAT_INPUT_SIZE; j < AppConfig.NEAT_INPUT_SIZE+AppConfig.NEAT_OUTPUT_SIZE; j++){
                Connection c = new Connection(n,getNode(j),rand.nextInt());
                addConnection(c);
                n.addOutgoingConnection(c);
            }
            addNode(node_id,n);
        }
    }
    //Core logic of using the network
    public float[] getOutputs(float[] in){
        //Reset all node values
        //Small nodes.values() is a collection of all the values in the map not the values inside a node
        for(Node n : nodes.values()){
            //permit to check if the node is well cleaned easier
            n.setValue(Float.NaN);
        }
        //Set input nodes
        for(int i = 0; i < AppConfig.NEAT_INPUT_SIZE; i++){
            getNode(i).setValue(i);
        }
        //Set bias node
        getNode(AppConfig.NEAT_INPUT_SIZE).setValue(1);
        //Evaluate hidden nodes
        //This code assume that the nodes are sorted by layer
        for(int i = AppConfig.NEAT_INPUT_SIZE+AppConfig.NEAT_OUTPUT_SIZE+1; i < nodes.size(); i++){
            Node n = getNode(i);
            float sum = 0;
            for(Connection c : n.incomingConnections){
                if(c.getConnectionState() == Connection.State.ENABLED){
                    sum += c.getInputNode().getValue()*c.getWeight(); //Assume that input node are already evaluated
                }
                n.setValue(sigmoid(sum));
            }
        }
        float out[] = new float[AppConfig.NEAT_OUTPUT_SIZE];
        //Evaluate output nodes
        for(int i = AppConfig.NEAT_INPUT_SIZE+1; i < AppConfig.NEAT_INPUT_SIZE+AppConfig.NEAT_OUTPUT_SIZE+1; i++){
            Node n = getNode(i);
            float sum = 0;
            for(Connection c : n.incomingConnections){
                if(c.getConnectionState() == Connection.State.ENABLED){
                    sum += c.getInputNode().getValue()*c.getWeight(); //Assume that input node are already evaluated
                }
            }
            float value = sigmoid(sum);
            n.setValue(value);
            out[i-AppConfig.NEAT_INPUT_SIZE-1] = value;
        }
        //TODO: Check Usefulness
        return out;
    }

    public static float sigmoid(float x){
        return (float) (1/(1+Math.exp(-x)));
    }

    // Managing Mutation
    public void mutate(){
        int randomInt = rand.nextInt();
        switch(randomInt){
            case 0:mutAddConnection();break;
            case 1:mutRemoveConnection();break;
            case 2:mutChangeConnectionState();break;
            case 3:mutAddNode();break;
            case 4:mutRemoveNode();break;
            case 5:mutAddConnection();break;
            case 6:mutRemoveConnection();break;
            case 7:mutChangeNode();break;
        }
    }

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
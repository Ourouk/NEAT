package com.hepl.NEAT;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class Genome {
    //Store Genome Information
    public Map<Integer,Node> nodes = new TreeMap<Integer,Node>();
    public ArrayList<Connection> connections = new ArrayList<Connection>();

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
    public void generateNetwork(){
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
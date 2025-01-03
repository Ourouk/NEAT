package com.hepl.NEAT;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Genome {
    //Store Genome Information
    public List<Node> nodes = new ArrayList<Node>();
    public List<Connection> connections = new ArrayList<Connection>();

    //Create a seed for random number generatuin
    private static Random rand = new Random();

    public Genome() {

    }
    //Interface to edit the treeMap
    public void addNode(Node n){
        nodes.add(n);
        synchronizeNodeIds();
    }
    public void addNode(Integer i, Node n){
        nodes.add(i,n);
        synchronizeNodeIds();
    }
    
    // synchronize node ids with their index
    public void synchronizeNodeIds() {
    	for (int i = 0; i < nodes.size(); i++) {
    		nodes.get(i).id = i;
    	}
    }
    
    //Manually add a connection
    public void addConnection(Connection c){
        connections.add(c);
    }
    //Automatic connection creation
    public void addConnection(com.hepl.NEAT.Node Input, com.hepl.NEAT.Node Output, Integer weight){
        Connection c =new Connection(Input,Output,weight);
        connections.add(c);
        Input.addOutgoingConnection(c);
        Output.addIncomingConnection(c);
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
            addNode(node_id,n);
            //Add all input as incoming connections
            for(int j = 0; j < AppConfig.NEAT_INPUT_SIZE; j++){
                Connection c = new Connection(getNode(j),n,rand.nextInt());
                addConnection(c);
                n.addIncomingConnection(c);
                getNode(j).addOutgoingConnection(c);
            }
            //Add all output as outgoing connections
            for(int j = AppConfig.NEAT_INPUT_SIZE; j < AppConfig.NEAT_INPUT_SIZE+AppConfig.NEAT_OUTPUT_SIZE; j++){
                Connection c = new Connection(n,getNode(j),rand.nextInt());
                addConnection(c);
                n.addOutgoingConnection(c);
                getNode(j).addIncomingConnection(c);
            }
        }
    }
    //Core logic of using the network
    public float[] getOutputs(float[] in){
        //Reset all node values
        //Small nodes.values() is a collection of all the values in the map not the values inside a node
        for(Node n : nodes){
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
        return out;
    }

    public static float sigmoid(float x){
        return (float) (1/(1+Math.exp(-x)));
    }

    // Managing Mutation
    public void mutate(){
        //Select a random mutation using rates
        double r = rand.nextDouble();
        if(r < AppConfig.NEAT_WEIGHT_MUTATION_RATE){
            mutChangeConnectionWeight();
        }else if(r < AppConfig.NEAT_WEIGHT_MUTATION_RATE + AppConfig.NEAT_CONNECTION_MUTATION_RATE){
            mutAddConnection();
        }else if(r < AppConfig.NEAT_WEIGHT_MUTATION_RATE + AppConfig.NEAT_CONNECTION_MUTATION_RATE + AppConfig.NEAT_NODE_MUTATION_RATE){
            mutAddNode();
        }
    }
    //Connection mutation
    public void mutAddConnection(){
        //Select a random connection
        //TODO: Adjust the randomness of that selection
    	Connection c = new Connection(nodes.get(rand.nextInt(nodes.size())),nodes.get(rand.nextInt(nodes.size())),rand.nextInt());
//    	connections.add(new Connection(nodes.get(rand.nextInt(nodes.size())),nodes.get(rand.nextInt(nodes.size())),rand.nextInt()));
        connections.add(c);
    }
    public void mutRemoveConnection(){
        //Select a random connection
        connections.remove(rand.nextInt(connections.size()));
    }
    public void mutChangeConnectionWeight(){
        //Select a random connection
        //TODO: Adjust the random nature of weight
        connections.get(rand.nextInt(connections.size())).setWeight(rand.nextFloat());
    }
    //Node Mutation
    public void mutChangeConnectionState(){
        //Select a random connection
        connections.get(rand.nextInt(connections.size())).flipConnectionState();
    }

    public void mutAddNode(){
        //Select a random connection
        Connection c = connections.get(rand.nextInt(connections.size()));
        //Disable the connection
        c.setConnectionState(Connection.State.DISABLED);
        //Create a new node
        Node n = new Node(Node.Type.HIDDEN);
        //Create two new connections
        Connection c1 = new Connection(c.getInputNode(),n,rand.nextInt());
        Connection c2 = new Connection(n,c.getOutputNode(),rand.nextInt());
        //Add the new node and connections
        nodes.add(n);
        connections.add(c1);
        connections.add(c2);
        synchronizeNodeIds();
    }
    
    // copy the genome
    public Genome copy() {
    	Genome copy = new Genome();
    	
    	// nodes
    	for (Node node : nodes) {
    		Node newNode = node.copy();
    		copy.addNode(newNode);
    	}
    	
    	// connections
    	for (Connection con : connections) {
    		Connection newCon = con.copy();
    		copy.addConnection(newCon);
    	}
    	return copy;
    }
    
    // Print the genome in a dot file (https://en.wikipedia.org/wiki/DOT_%28graph_description_language%29)
    public void exportToDot(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("digraph Genome {\n"); // digraph (flèche)
            writer.write("\trankdir=BT;\n"); // Base to top layout

            // Write nodes
            for (Node node : nodes) {
                String label = String.format("ID: %d", node.id);
                writer.write(String.format("\t%d [label=\"%s\", shape=%s];\n", 
                    nodes.indexOf(node),
                    label,
                    node.type == Node.Type.INPUT ? "ellipse" : 
                    node.type == Node.Type.OUTPUT ? "doublecircle" : "circle"));
            }

            // Write connections
            for (Connection connection : connections) {
                String label = String.format("Weight: %f\nInnovation: %d", connection.getWeight(), connection.getInnovation());
                writer.write(String.format("\t%d -> %d [label=\"%s\", style=%s];\n", 
//                    nodes.indexOf(connection.getInputNode()),
//                    nodes.indexOf(connection.getOutputNode()),
                	connection.getInputNode().id,
                	connection.getOutputNode().id,
                    label,
                    connection.getConnectionState() == Connection.State.ENABLED ? "solid" : "dashed"));
            }

            writer.write("}");
        } catch (IOException e) {
            System.err.println("Error writing DOT file: " + e.getMessage());
        }
    }
}
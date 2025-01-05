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
    public boolean addConnection(com.hepl.NEAT.Node Input, com.hepl.NEAT.Node Output, Float weight){
        //Check if the connection already exist
        for(Connection c : connections){
            if(c.getInputNode() == Input && c.getOutputNode() == Output){
                return false;
            }
        }
        //Create a connection
        Connection c =new Connection(Input,Output,weight);
        connections.add(c);
        Input.addOutgoingConnection(c);
        Output.addIncomingConnection(c);
        return true;
    }

    //Access an object into the treeMap
    public Node getNode(int id){
        return nodes.get(id);
    }

    //Generate and evaluate the output of the genome
    public void initNetwork(){
        //Add bias node
        int neat_input_size = AppConfig.NEAT_INPUT_SIZE;
        if(AppConfig.NEAT_BIAS)
             neat_input_size ++;
        //Add input nodes
        for(int i = 0; i < neat_input_size; i++){
            Node n = new Node(Node.Type.INPUT);
            addNode(i,n);
        }
        //Add output nodes
        for(int i = 0; i < AppConfig.NEAT_OUTPUT_SIZE; i++){
            Node n = new Node(Node.Type.OUTPUT);
            addNode(neat_input_size+i,n);
        }
        if(AppConfig.NEAT_HIDDEN_SIZE == 0)
        {
            //Connect all input to all output
            for(int i = 0; i < neat_input_size; i++){
                for(int j = neat_input_size; j < neat_input_size+AppConfig.NEAT_OUTPUT_SIZE; j++){
                    Node m = getNode(i);
                    Node n = getNode(j);
                    Connection c = new Connection(m,n,Connection.randomWeight());
                    addConnection(c);
                    m.addOutgoingConnection(c);
                    n.addIncomingConnection(c);
                }
            }
        }else{
            //Add hidden nodes and connections
            for(int i = 0; i < AppConfig.NEAT_HIDDEN_SIZE; i++){
                Node n = new Node(Node.Type.HIDDEN);
                int node_id = neat_input_size+AppConfig.NEAT_OUTPUT_SIZE+i;
                addNode(node_id,n);
                //Add all input as incoming connections
                for(int j = 0; j < neat_input_size; j++){
                    Node m = getNode(j);
                    Connection c = new Connection(m,n,Connection.randomWeight());
                    addConnection(c);
                    n.addIncomingConnection(c);
                    m.addOutgoingConnection(c);
                }
                //Add all output as outgoing connections
                for(int j = neat_input_size; j < neat_input_size+AppConfig.NEAT_OUTPUT_SIZE; j++){
                    Node m = getNode(j);
                    Connection c = new Connection(n,m,Connection.randomWeight());
                    addConnection(c);
                    n.addOutgoingConnection(c);
                    m.addIncomingConnection(c);
                }
            }
        }
    }
    //Core logic of using the network
    public float[] getOutputs(float[] in){
        //Reset all node values
        //Small nodes.values() is a collection of all the values in the map not the values inside a node
        int neat_input_size = AppConfig.NEAT_INPUT_SIZE;
        for(Node n : nodes){
            //permit to check if the node is well cleaned easier
            n.setValue(Float.NaN);
        }
        //Set input nodes
        for(int i = 0; i < neat_input_size; i++){
            getNode(i).setValue(in[i]);
        }

        //Set bias node if needed
        if(AppConfig.NEAT_BIAS)
        {
            getNode(AppConfig.NEAT_INPUT_SIZE).setValue(1);
            neat_input_size++;
        }
        //Evaluate hidden nodes
        //This code assume that the nodes are sorted by layer
        for(int i = neat_input_size; i < nodes.size(); i++){
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
        for(int i = neat_input_size; i < neat_input_size+AppConfig.NEAT_OUTPUT_SIZE; i++){
            Node n = getNode(i);
            float sum = 0;
            for(Connection c : n.incomingConnections){
                if(c.getConnectionState() == Connection.State.ENABLED){
                    sum += c.getInputNode().getValue()*c.getWeight(); //Assume that input node are already evaluated
                }
            }
            float value = sigmoid(sum);
            n.setValue(value);
            out[i-neat_input_size] = value;
        }
        return out;
    }

    public static float sigmoid(float x){
        //return (float) (1/(1+Math.exp(-x)));
        //Change the sigmoid function from the documentation
        return (float) (1/(1+Math.exp(-4.9*x)));
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
        //Try to create a new connection between two nodes
        boolean creationOkay = false;
        int i = 0;
        //Try to create a connection 10 times if it fails just doesn't add any connection
        while(!creationOkay && i < 10){
            creationOkay = addConnection(nodes.get(rand.nextInt(nodes.size()-1)),nodes.get(rand.nextInt(nodes.size()-1)),Connection.randomWeight());
            i++;
        }
    }
    //Connection Mutation
    public void mutChangeConnectionWeight(){
        //Select a random connection and adjust the weight
        connections.get(rand.nextInt(connections.size()-1)).setWeight(Connection.randomWeight());
    }
    //Node Mutation
    public void mutChangeConnectionState(){
        //Select a random connection and flip the state
        connections.get(rand.nextInt(connections.size()-1)).flipConnectionState();
    }
    public void mutAddNode(){
        //Select a random connection
        Connection c = connections.get(rand.nextInt(connections.size()));
        //Disable the connection
        c.setConnectionState(Connection.State.DISABLED);
        //Create a new node
        Node n = new Node(Node.Type.HIDDEN);
        //Create two new connections

        Connection c1 = new Connection(c.getInputNode(),n,Connection.randomWeight());
        Connection c2 = new Connection(n,c.getOutputNode(),Connection.randomWeight());

        //Add the new node and connections
        nodes.add(n);
        connections.add(c1);
        connections.add(c2);
        synchronizeNodeIds();
    }

    // copy the genome
    @Override
    public Genome clone() {
    	Genome copy = new Genome();
    	// nodes
    	for (Node node : nodes) {
    		Node newNode = node.clone();
    		copy.addNode(newNode);
    	}
    	// connections
    	for (Connection con : connections) {
    		Connection newCon = con.clone();
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
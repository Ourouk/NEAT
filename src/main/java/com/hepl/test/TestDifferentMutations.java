package com.hepl.test;

import com.hepl.NEAT.*;

public class TestDifferentMutations {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// genome on which we made the mutation
		Genome genome = new Genome();
		
		Node node1I = new Node(Node.Type.INPUT);
		Node node2I = new Node(Node.Type.INPUT);
		Node node3I = new Node(Node.Type.INPUT);
		Node node4O = new Node(Node.Type.OUTPUT);
		Node node5H = new Node(Node.Type.HIDDEN);
		
		genome.addNode(node1I);
		genome.addNode(node2I);
		genome.addNode(node3I);
		genome.addNode(node4O);
		genome.addNode(node5H);
		
		Connection con14i1 = new Connection(node1I,node4O,1);
		Connection con24i2 = new Connection(node2I,node4O,1);
		Connection con34i3 = new Connection(node3I,node4O,1);
		Connection con25i4 = new Connection(node2I,node5H,1);
		Connection con54i5 = new Connection(node5H,node4O,1);
		Connection con15i6 = new Connection(node1I,node5H,1);
		
//		con14i1.innovation = 1;
//		con24i2.innovation = 2;
//		con34i3.innovation = 3;
//		con25i4.innovation = 4;
//		con54i5.innovation = 5;
//		con15i6.innovation = 6;
		
		/*con14i1.innovation = InnovationCounter.newInnovation();
		con24i2.innovation = InnovationCounter.newInnovation();
		con34i3.innovation = InnovationCounter.newInnovation();
		con25i4.innovation = InnovationCounter.newInnovation();
		con54i5.innovation = InnovationCounter.newInnovation();
		con15i6.innovation = InnovationCounter.newInnovation();*/
		
		con24i2.setConnectionState(Connection.State.DISABLED);
		
		genome.addConnection(con14i1);
		genome.addConnection(con24i2);
		genome.addConnection(con34i3);
		genome.addConnection(con25i4);
		genome.addConnection(con54i5);
		genome.addConnection(con15i6);
		
		genome.exportToDot("Images/Mutation/genome.dot");
		
		// Results
		System.out.println("Default genome Connections:");
		for (Connection con : genome.connections) {
			System.out.println("Input node " + con.getInputNode().id + ", Output node " + con.getOutputNode().id + ", Innovation: " + con.getInnovation() + ", Weight: " + con.getWeight() + ", State " + con.getConnectionState());
		}
				
		// Change connection weight mutation
		System.out.println();
		System.out.println("==========ChangeWeightMutation==========");
		System.out.println();
		
		Genome weightGenome = new Genome();
		weightGenome = genome.clone();
		
		weightGenome.mutChangeConnectionWeight();

		weightGenome.exportToDot("Images/Mutation/changeWeight.dot");
		
		// Results
		System.out.println("Change weight mutation genome Connections:");
		for (Connection con : weightGenome.connections) {
			System.out.println("Input node " + con.getInputNode().id + ", Output node " + con.getOutputNode().id + ", Innovation: " + con.getInnovation() + ", Weight: " + con.getWeight() + ", State " + con.getConnectionState());
		}
		
		// Change connection state mutation
		System.out.println();
		System.out.println("==========ChangeStateMutation==========");
		System.out.println();
		
		Genome stateGenome = new Genome();
		stateGenome = genome.clone();
		
		stateGenome.mutChangeConnectionState();

		stateGenome.exportToDot("Images/Mutation/changeState.dot");
		
		// Results
		System.out.println("Change state mutation genome Connections:");
		for (Connection con : stateGenome.connections) {
			System.out.println("Input node " + con.getInputNode().id + ", Output node " + con.getOutputNode().id + ", Innovation: " + con.getInnovation() + ", Weight: " + con.getWeight() + ", State " + con.getConnectionState());
		}
		
		// add connection mutation
		System.out.println();
		System.out.println("==========AddConnectionMutation==========");
		System.out.println();
		
		Genome conGenome = new Genome();
		conGenome = genome.clone();
		
		conGenome.mutAddConnection();

		conGenome.exportToDot("Images/Mutation/addConnection.dot");
		
		// Results
		System.out.println("Add connection mutation genome Connections:");
		for (Connection con : conGenome.connections) {
			System.out.println("Input node " + con.getInputNode().id + ", Output node " + con.getOutputNode().id + ", Innovation: " + con.getInnovation() + ", Weight: " + con.getWeight() + ", State " + con.getConnectionState());
		}
		
		// Add node mutation
		System.out.println();
		System.out.println("==========AddNodeMutation==========");
		System.out.println();
		
		Genome nodeGenome = new Genome();
		nodeGenome = genome;
		
		nodeGenome.mutAddNode();

		nodeGenome.exportToDot("Images/Mutation/addNode.dot");
		
		// Results
		System.out.println("Add node mutation genome Connections:");
		for (Connection con : nodeGenome.connections) {
			System.out.println("Input node " + con.getInputNode().id + ", Output node " + con.getOutputNode().id + ", Innovation: " + con.getInnovation() + ", Weight: " + con.getWeight() + ", State " + con.getConnectionState());
		}
	}

}

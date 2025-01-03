package com.hepl.test;

import com.hepl.NEAT.*;

public class TestGenomeCrossover { // based on p109 of the paper and hydrozoa YT channel	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// parent 1
		Genome parent1 = new Genome();
		
		Node p1Node1I = new Node(Node.Type.INPUT);
		Node p1Node2I = new Node(Node.Type.INPUT);
		Node p1Node3I = new Node(Node.Type.INPUT);
		Node p1Node4O = new Node(Node.Type.OUTPUT);
		Node p1Node5H = new Node(Node.Type.HIDDEN);
		
		parent1.addNode(p1Node1I);
		parent1.addNode(p1Node2I);
		parent1.addNode(p1Node3I);
		parent1.addNode(p1Node4O);
		parent1.addNode(p1Node5H);
		
		Connection p1Con14i1 = new Connection(p1Node1I, p1Node4O, 1);
		Connection p1Con24i2 = new Connection(p1Node2I, p1Node4O, 1);
		Connection p1Con34i3 = new Connection(p1Node3I, p1Node4O, 1);
		Connection p1Con25i4 = new Connection(p1Node2I, p1Node5H, 1);
		Connection p1Con54i5 = new Connection(p1Node5H, p1Node4O, 1);
		Connection p1Con15i8 = new Connection(p1Node1I, p1Node5H, 1);
		


		// p1Con14i1.innovation = 1;
		// p1Con24i2.innovation = 2;
		// p1Con34i3.innovation = 3;
		// p1Con25i4.innovation = 4;
		// p1Con54i5.innovation = 5;
		// p1Con15i8.innovation = 8;
		
		p1Con24i2.setConnectionState(Connection.State.DISABLED);
		
		parent1.addConnection(p1Con14i1);
		parent1.addConnection(p1Con24i2);
		parent1.addConnection(p1Con34i3);
		parent1.addConnection(p1Con25i4);
		parent1.addConnection(p1Con54i5);
		parent1.addConnection(p1Con15i8);
		
		parent1.exportToDot("Images/Crossover/parent1.dot");
		
		// parent 2
		Genome parent2 = new Genome();
		
		Node p2Node1I = new Node(Node.Type.INPUT);
		Node p2Node2I = new Node(Node.Type.INPUT);
		Node p2Node3I = new Node(Node.Type.INPUT);
		Node p2Node4O = new Node(Node.Type.OUTPUT);
		Node p2Node5H = new Node(Node.Type.HIDDEN);
		Node p2Node6H = new Node(Node.Type.HIDDEN);
		
		parent2.addNode(p2Node1I);
		parent2.addNode(p2Node2I);
		parent2.addNode(p2Node3I);
		parent2.addNode(p2Node4O);
		parent2.addNode(p2Node5H);
		parent2.addNode(p2Node6H);
		
		Connection p2Con14i1 = new Connection(p2Node1I, p2Node4O, 1);
		Connection p2Con24i2 = new Connection(p2Node2I, p2Node4O, 1);
		Connection p2Con34i3 = new Connection(p2Node3I, p2Node4O, 1);
		Connection p2Con25i4 = new Connection(p2Node2I, p2Node5H, 1);
		Connection p2Con54i5 = new Connection(p2Node5H, p2Node4O, 1);
		Connection p2Con56i6 = new Connection(p2Node5H, p2Node6H, 1);
		Connection p2Con64i7 = new Connection(p2Node6H, p2Node4O, 1);
		Connection p2Con35i9 = new Connection(p2Node3I, p2Node5H, 1);
		Connection p2Con16i10 = new Connection(p2Node1I, p2Node6H, 1);
		
		/*
		p2Con14i1.innovation = 1;
		p2Con24i2.innovation = 2;
		p2Con34i3.innovation = 3;
		p2Con25i4.innovation = 4;
		p2Con54i5.innovation = 5;
		p2Con56i6.innovation = 6;
		p2Con64i7.innovation = 7;
		p2Con35i9.innovation = 9;
		p2Con16i10.innovation = 10;*/
		
		p2Con24i2.setConnectionState(Connection.State.DISABLED);
		p2Con54i5.setConnectionState(Connection.State.DISABLED);
		
		parent2.addConnection(p2Con14i1);
		parent2.addConnection(p2Con24i2);
		parent2.addConnection(p2Con34i3);
		parent2.addConnection(p2Con25i4);
		parent2.addConnection(p2Con54i5);
		parent2.addConnection(p2Con56i6);
		parent2.addConnection(p2Con64i7);
		parent2.addConnection(p2Con35i9);
		parent2.addConnection(p2Con16i10);
		
		parent2.exportToDot("Images/Crossover/parent2.dot");
		
		// GenomeCrossover
		Pool pool = new Pool();
		
		Genome child = pool.GenomeCrossover(parent1, parent2, 8f, 10f);
		
		// Verify
		Genome result = new Genome();
		Node rNode1I = new Node(Node.Type.INPUT);
		Node rNode2I = new Node(Node.Type.INPUT);
		Node rNode3I = new Node(Node.Type.INPUT);
		Node rNode4O = new Node(Node.Type.OUTPUT);
		Node rNode5H = new Node(Node.Type.HIDDEN);
		Node rNode6H = new Node(Node.Type.HIDDEN);
		
		result.addNode(rNode1I);
		result.addNode(rNode2I);
		result.addNode(rNode3I);
		result.addNode(rNode4O);
		result.addNode(rNode5H);
		result.addNode(rNode6H);
		
		Connection rCon14i1 = new Connection(rNode1I, rNode4O, 1);
		Connection rCon24i2 = new Connection(rNode2I, rNode4O, 1);
		Connection rCon34i3 = new Connection(rNode3I, rNode4O, 1);
		Connection rCon25i4 = new Connection(rNode2I, rNode5H, 1);
		Connection rCon54i5 = new Connection(rNode5H, rNode4O, 1);
		Connection rCon56i6 = new Connection(rNode5H, rNode6H, 1);
		Connection rCon64i7 = new Connection(rNode6H, rNode4O, 1);
		Connection rCon15i8 = new Connection(rNode1I, rNode5H, 1);
		Connection rCon35i9 = new Connection(rNode3I, rNode5H, 1);
		Connection rCon16i10 = new Connection(rNode1I, rNode6H, 1);
		/* 
		rCon14i1.innovation = 1;
		rCon24i2.innovation = 2;
		rCon34i3.innovation = 3;
		rCon25i4.innovation = 4;
		rCon54i5.innovation = 5;
		rCon56i6.innovation = 6;
		rCon64i7.innovation = 7;
		rCon15i8.innovation = 8;
		rCon35i9.innovation = 9;
		rCon16i10.innovation = 10;*/
		
		rCon24i2.setConnectionState(Connection.State.DISABLED);
		rCon54i5.setConnectionState(Connection.State.DISABLED);
		
		result.addConnection(rCon14i1);
		result.addConnection(rCon24i2);
		result.addConnection(rCon34i3);
		result.addConnection(rCon25i4);
		result.addConnection(rCon54i5);
		result.addConnection(rCon56i6);
		result.addConnection(rCon64i7);
		result.addConnection(rCon15i8);
		result.addConnection(rCon35i9);
		result.addConnection(rCon16i10);
		
		if (pool.getMatchingConnections(child, result).size() == child.connections.size() && pool.getDisjointConnections(child, result).isEmpty() && pool.getExcessConnections(child, result).isEmpty()) {
			System.out.println("Success");
		} else {
			System.out.println("Failed");
		}
		
//		Genome child = pool.GenomeCrossover(parent1, parent2, 10f, 8f);
		
		child.exportToDot("Images/Crossover/child.dot");
		

		// Results
		System.out.println("Parent 1 Connections:");
		for (Connection con : parent1.connections) {
			System.out.println("Input node " + con.getInputNode().id + ", Output node " + con.getOutputNode().id + ", Innovation: " + con.getInnovation() + ", Weight: " + con.getWeight() + ", State " + con.getConnectionState());
		}

		System.out.println("\nParent 2 Connections:");
		for (Connection con : parent2.connections) {
			System.out.println("Input node " + con.getInputNode().id + ", Output node " + con.getOutputNode().id + ", Innovation: " + con.getInnovation() + ", Weight: " + con.getWeight() + ", State " + con.getConnectionState());
		}

		System.out.println("\nChild Connections:");
		for (Connection con : child.connections) {
			System.out.println("Input node " + con.getInputNode().id + ", Output node " + con.getOutputNode().id + ", Innovation: " + con.getInnovation() + ", Weight: " + con.getWeight() + ", State " + con.getConnectionState());
		}
		
	}

}
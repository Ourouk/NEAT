package com.hepl.test;

import com.hepl.NEAT.*;
import java.util.List;

public class TestMatchingDisjointExcess {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// parent 1
		Genome parent1 = new Genome();
		
		Node p1Node1 = new Node(Node.Type.INPUT);
		Node p1Node2 = new Node(Node.Type.INPUT);
		Node p1Node3 = new Node(Node.Type.INPUT);
		Node p1Node4 = new Node(Node.Type.OUTPUT);
		Node p1Node5 = new Node(Node.Type.HIDDEN);
		
		parent1.addNode(p1Node1);
		parent1.addNode(p1Node2);
		parent1.addNode(p1Node3);
		parent1.addNode(p1Node4);
		parent1.addNode(p1Node5);
		
		Connection p1Con14i1 = new Connection(p1Node1, p1Node4, 1);
		Connection p1Con24i2 = new Connection(p1Node2, p1Node4, 1);
		Connection p1Con34i3 = new Connection(p1Node3, p1Node4, 1);
		Connection p1Con25i4 = new Connection(p1Node2, p1Node5, 1);
		Connection p1Con54i5 = new Connection(p1Node5, p1Node4, 1);
		Connection p1Con15i8 = new Connection(p1Node1, p1Node5, 1);
		/* 
		p1Con14i1.innovation = 1;
		p1Con24i2.innovation = 2;
		p1Con34i3.innovation = 3;
		p1Con25i4.innovation = 4;
		p1Con54i5.innovation = 5;
		p1Con15i8.innovation = 8;*/
		
		p1Con24i2.setConnectionState(Connection.State.DISABLED);
		
		parent1.addConnection(p1Con14i1);
		parent1.addConnection(p1Con24i2);
		parent1.addConnection(p1Con34i3);
		parent1.addConnection(p1Con25i4);
		parent1.addConnection(p1Con54i5);
		parent1.addConnection(p1Con15i8);
		
		parent1.exportToDot("Images/parent1.dot");
		
		// parent 2
		Genome parent2 = new Genome();
		
		Node p2Node1 = new Node(Node.Type.INPUT);
		Node p2Node2 = new Node(Node.Type.INPUT);
		Node p2Node3 = new Node(Node.Type.INPUT);
		Node p2Node4 = new Node(Node.Type.OUTPUT);
		Node p2Node5 = new Node(Node.Type.HIDDEN);
		Node p2Node6 = new Node(Node.Type.HIDDEN);
		
		parent2.addNode(p2Node1);
		parent2.addNode(p2Node2);
		parent2.addNode(p2Node3);
		parent2.addNode(p2Node4);
		parent2.addNode(p2Node5);
		parent2.addNode(p2Node6);
		
		Connection p2Con14i1 = new Connection(p2Node1, p2Node4, 1);
		Connection p2Con24i2 = new Connection(p2Node2, p2Node4, 1);
		Connection p2Con34i3 = new Connection(p2Node3, p2Node4, 1);
		Connection p2Con25i4 = new Connection(p2Node2, p2Node5, 1);
		Connection p2Con54i5 = new Connection(p2Node5, p2Node4, 1);
		Connection p2Con56i6 = new Connection(p2Node5, p2Node6, 1);
		Connection p2Con64i7 = new Connection(p2Node6, p2Node4, 1);
		Connection p2Con35i9 = new Connection(p2Node3, p2Node5, 1);
		Connection p2Con16i10 = new Connection(p2Node1, p2Node6, 1);
		
		/*p2Con14i1.innovation = 1;
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
		
		parent2.exportToDot("Images/parent2.dot");
		
		// Matching connections
		Pool pool = new Pool();
		
        List<Connection> matchingConnections = pool.getMatchingConnections(parent1, parent2);
        List<Connection> disjointConnections = pool.getDisjointConnections(parent1, parent2);
        List<Connection> excessConnections = pool.getExcessConnections(parent1, parent2);
        
        System.out.print("Compatibility distance between parent 1 et parent 2: " + pool.compatibilityDistance(parent1, parent2));
        System.out.println();
        
        for (Connection con : matchingConnections) {
        	System.out.println("match "+con.getInnovation());
        }
        
        for (Connection con : disjointConnections) {
        	System.out.println("disjoint "+con.getInnovation());
        }
        
        for (Connection con : excessConnections) {
        	System.out.println("excess "+con.getInnovation());
        }
	}
}
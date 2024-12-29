package com.hepl.NEAT;

import java.util.Enumeration;

public class Node {
    // Input are the first layer of neat
    // In between Layer are hidden
    // Output are the last layer
    public enum Type {
        INPUT, HIDDEN, OUTPUT;
    }

    public Type type;
    public Node(Type type) {
        this.type = type;
    }
}

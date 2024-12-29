package com.hepl.NEAT;

public class InnovationCounter {
    private static int innovation = 0;

    public static int newInnovation() {
        innovation++;
        return innovation;
    }
}

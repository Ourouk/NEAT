package com.hepl.NEAT;

import java.util.ArrayList;

public class InnovationCounter {
    private static int innovation = 0;
    private static ArrayList<int[]> KnowConnections = new ArrayList<>();

    public static int newInnovation(int inId,int outId) 
    {
        for(int i =0; i < KnowConnections.size();i++)
        {
            if(KnowConnections.get(i)[0] == inId && KnowConnections.get(i)[1]  == outId)
            {
                return i;
            }
        }
        innovation++;
        int[] a = {inId,outId};
        KnowConnections.add(a);
        return innovation;
    }
}

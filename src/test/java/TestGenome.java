import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert.*;
import com.hepl.NEAT.*;
import com.hepl.SourceProject.App;

import junit.framework.Assert;

public class TestGenome {
    @Test
    public void testInitNetworkWithtBias() {
        Genome g = new Genome();
        AppConfig.NEAT_BIAS = true;
        g.initNetwork();
        Assert.assertEquals(AppConfig.NEAT_INPUT_SIZE + AppConfig.NEAT_OUTPUT_SIZE + AppConfig.NEAT_HIDDEN_SIZE + 1, g.nodes.size());
        g.exportToDot("Images/Genome/InitNetworkWithtBias.dot");
    }
    @Test
    public void testInitNetworkWithoutBias() {
        AppConfig.NEAT_BIAS = false;
        Genome g = new Genome();
        g.initNetwork();
        Assert.assertEquals(AppConfig.NEAT_INPUT_SIZE + AppConfig.NEAT_OUTPUT_SIZE + AppConfig.NEAT_HIDDEN_SIZE, g.nodes.size());
        g.exportToDot("Images/Genome/InitNetworkWithoutBias.dot");
    }
    @Test
    public void testInitNetworkWithRandomWeight() {
        AppConfig.NEAT_INIT_WEIGHT_RANDOM = true;
        Genome g = new Genome();
        g.initNetwork();
        for (Connection c : g.connections) {
            Assert.assertTrue(c.getWeight() < 1 && c.getWeight() > -1);
        }
        g.exportToDot("Images/Genome/InitNetworkWithRandomWeight.dot");
    }
    @Test
    public void testInitNetworkWithoutHiddenLayerWithBias() {
        AppConfig.NEAT_HIDDEN_SIZE = 0;
        AppConfig.NEAT_BIAS = true;
        Genome g = new Genome();
        g.initNetwork();
        //test number of nodes
        Assert.assertEquals(AppConfig.NEAT_INPUT_SIZE + AppConfig.NEAT_OUTPUT_SIZE+1, g.nodes.size());
        //test number of connections
        Assert.assertEquals((AppConfig.NEAT_INPUT_SIZE +1 ) * AppConfig.NEAT_OUTPUT_SIZE, g.connections.size());
        g.exportToDot("Images/Genome/InitNetworkWithoutHiddenLayerWithBias.dot");
    }
    @Test
    public void testInitNetworkWithoutHiddenLayerWithoutBias() {
        AppConfig.NEAT_HIDDEN_SIZE = 0;
        AppConfig.NEAT_BIAS = false;
        Genome g = new Genome();
        g.initNetwork();
        //test number of nodes
        Assert.assertEquals(AppConfig.NEAT_INPUT_SIZE + AppConfig.NEAT_OUTPUT_SIZE, g.nodes.size());
        //test number of connections
        Assert.assertEquals(AppConfig.NEAT_INPUT_SIZE * AppConfig.NEAT_OUTPUT_SIZE, g.connections.size());
        g.exportToDot("Images/Genome/InitNetworkWithoutHiddenLayerWithoutBias.dot");
    }
    @Test
    public void testInitNetworkWithHiddenLayerWithBias() {
        AppConfig.NEAT_HIDDEN_SIZE = 4;
        AppConfig.NEAT_BIAS = true;
        Genome g = new Genome();
        g.initNetwork();
        //test number of nodes
        Assert.assertEquals(AppConfig.NEAT_INPUT_SIZE + AppConfig.NEAT_OUTPUT_SIZE + AppConfig.NEAT_HIDDEN_SIZE + 1, g.nodes.size());
        //test number of connections
        Assert.assertEquals((AppConfig.NEAT_INPUT_SIZE + 1) * AppConfig.NEAT_HIDDEN_SIZE + (AppConfig.NEAT_HIDDEN_SIZE) * AppConfig.NEAT_OUTPUT_SIZE, g.connections.size());
        //generate input[]
        float [] input = new float[AppConfig.NEAT_INPUT_SIZE];
        for (int i = 0; i < AppConfig.NEAT_INPUT_SIZE; i++) {
            input[i] = 1;
        }
        g.getOutputs(input);
        g.exportToDot("Images/Genome/InitNetworkWithHiddenLayerWithBias.dot");
    }
    @Test
    public void testGetOutputs() {
        Genome g = new Genome();
        float [] input = new float[AppConfig.NEAT_INPUT_SIZE];
        for (int i = 0; i < AppConfig.NEAT_INPUT_SIZE; i++) {
            input[i] = 1;
        }
        g.initNetwork();
        //Modify all weight to one to get the same output
        for (Connection c : g.connections) {
            c.setWeight(1);
        }
        float [] output = g.getOutputs(input);
        int output_length = output.length;

        int expected_output_length = AppConfig.NEAT_OUTPUT_SIZE;
        //Print output in the test
        for (int i = 0; i < output_length; i++) {
            Assert.assertTrue(output[i] < 1.01 && output[i] > 0.99);
        }
        Assert.assertEquals(output_length, expected_output_length);
        g.exportToDot("Images/Genome/GetOutputs.dot");
    }

}

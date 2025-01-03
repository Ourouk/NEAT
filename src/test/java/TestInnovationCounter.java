

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert.*;
import com.hepl.NEAT.*;

import junit.framework.Assert;

public class TestInnovationCounter 
{
   @Test 
   public void testNewInnovation()
   {
      Genome g1 = new Genome();
      g1.initNetwork();
      Connection c1 = g1.connections.get(0);
      Connection c2 = g1.connections.get(2);
      assertTrue(c1.getInnovation() != c2.getInnovation());
   } 
   @Test
   public void testSameInnovation()
   {
      Genome g1 = new Genome();
      Genome g2 = new Genome();
      g1.initNetwork();
      g2.initNetwork();
      for (int i = 0; i < 3; i++)
      {
         assertTrue(g1.connections.get(i).getInnovation() == g1.connections.get(i).getInnovation());   
      }
      
   }
}

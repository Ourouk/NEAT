
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert.*;
import com.hepl.NEAT.*;
public class TestConnection
{
    static Node n1;
    static Node n2;
    static Node n3;
    static Node n4;
    @BeforeClass
    public static void setup()
    {
        n1 = new Node(Node.Type.INPUT);
        n2 = new Node(Node.Type.OUTPUT);
        n3 = new Node(Node.Type.HIDDEN);
        n4 = new Node(Node.Type.HIDDEN);

        n1.id =1;
        n2.id = 2;
        n3.id = 3;
        n4.id = 4;
    }
    @Test
    public void testISValidII()
    {
        assertTrue(!(new Connection(n1,n1,0)).IsValid());
    } 
    @Test 
    public void testISValidIO()
    {
        assertTrue((new Connection(n1,n2,0)).IsValid());
    } 
    @Test
    public void testISValidIH()
    {
        assertTrue((new Connection(n1,n3,0)).IsValid());
    } 
    @Test
    public void testISValidOI()
    {
        assertTrue(!(new Connection(n2,n1,0)).IsValid());
    }
    @Test
    public void testISValidOO()
    {
        assertTrue(!(new Connection(n2,n2,0)).IsValid());
    }
    @Test
    public void testISValidOH()
    {
        assertTrue(!(new Connection(n2,n3,0)).IsValid());
    }
    @Test
    public void testISValidHI()
    {
        assertTrue(!(new Connection(n3,n1,0)).IsValid());
    }
    @Test
    public void testISValidHO()
    {
        assertTrue((new Connection(n3,n2,0)).IsValid());
    }
    @Test
    public void testISValidHH()
    {
        assertTrue((new Connection(n3,n4,0)).IsValid());
        assertTrue(!(new Connection(n4,n3,0)).IsValid());
        assertTrue(!(new Connection(n4,n4,0)).IsValid());
    }
    @Test
    public void testAddingConnection()
    {
      Connection c = new Connection(n3,n4,0);
      assertTrue(c.IsValid());
      n3.addOutgoingConnection(c);
      try 
      {
        Connection c2 = n3.outgoingConnections.get(n3.outgoingConnections.size()-1);
        assertTrue(c.getInnovation() == c2.getInnovation());
      } 
      catch (Exception e) 
      {
        assertTrue(false);
      } 
    }
    
    
    
    

}

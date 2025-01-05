package com.hepl.SourceProject;
import java.util.Scanner;
import com.hepl.NEAT.*;
import com.hepl.SourceProject.Game;
import com.hepl.SourceProject.GameFitness;
import com.hepl.SourceProject.SimpleByteFitness;
import com.hepl.SourceProject.SimpleGeneticAlgorithm;

public class App {
    public static void main(String[] args) throws Exception 
    {
        while(true){
            System.out.println("----------------------------------");
            System.out.println("|Select a task :                 |");
            System.out.println("|1. Simple byte guess            |");
            System.out.println("|2. XOR guess                    |");
            System.out.println("|3. Game guess                   |");
            System.out.println("|4. XOR NEAT                     |");
            System.out.println("|5. Game NEAT                    |");
            System.out.println("|0. Exit                         |");
            System.out.println("----------------------------------");
            int choice = 0;
            Scanner scanner = new Scanner(System.in);
            try{
            choice = scanner.nextInt();
            }catch(Exception e) 
            {
                System.out.println("Wrong input, try again");
                continue;
            }

            switch (choice) {
                case 1:
                SimpleByteFitness fitByte = new SimpleByteFitness("0100010010000011110001010101010000010000011000100100110000000000");
			    SimpleGeneticAlgorithm simpleGeneticAlgorithm = new SimpleGeneticAlgorithm(fitByte);
                Population pop1 = new Population(30, true);
        		simpleGeneticAlgorithm.runAlgorithm(pop1); // population 20-30 (same src as cross-over rate). For WHEEL, 30 seems better than 20
                    	break;
                case 2 :
                        SimpleByteFitness fitXOR = new SimpleByteFitness("0110");
                    	SimpleGeneticAlgorithm algoXOR = new SimpleGeneticAlgorithm(fitXOR);
                        Population pop2 = new Population(10, true);
                    	algoXOR.runAlgorithm(pop2);
                    	break;
                case 3:
                    boolean[][] m = {{false,true,false,true},{false,true,false,true},{true,true,false,true},{true,true,true,true},{true,true,true,true}};
                    int[] s = {2,1} ;
                    int[] e = {0,3};
                    Game g = new Game(m, s, e);
                    g.display(true);
                    GameFitness gfit = new GameFitness(g);
                    SimpleGeneticAlgorithm sg = new SimpleGeneticAlgorithm(gfit);
                    Population pop3 = new Population(3, true);
                    sg.runAlgorithm(pop3);
                    break;
                case 4:
                    AppConfig.NEAT_INPUT_SIZE = 2;
                    AppConfig.NEAT_HIDDEN_SIZE = 0;
                    AppConfig.NEAT_OUTPUT_SIZE = 1;
                    SimpleByteFitness fitXOR2 = new SimpleByteFitness("0110");
                    NeatPoolAdapter pop4 = new NeatPoolAdapter(2,false);
                    SimpleGeneticAlgorithm sg2 = new SimpleGeneticAlgorithm(fitXOR2);
                    sg2.runAlgorithm(pop4);
                    break;
                case 5:
                    boolean[][] m2 = {{false,true,false,true},{false,true,false,true},{true,true,false,true},{true,true,true,true},{true,true,true,true}};
                    int[] s2 = {2,1} ;
                    int[] e2 = {0,3};
                    Game g2 = new Game(m2, s2, e2);
                    GameFitness gfit2 = new GameFitness(g2);
                    SimpleGeneticAlgorithm sg3 = new SimpleGeneticAlgorithm(gfit2);
                    NeatPoolAdapter pop5 = new NeatPoolAdapter(10,false);
                    sg3.runAlgorithm(pop5);
                    break;

                case 0 :
                default:
                    	System.exit(0);
                    	break;
            }
        
        }
    }
}

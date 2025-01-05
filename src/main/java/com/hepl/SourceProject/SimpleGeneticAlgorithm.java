/* notes :
 * Selon la litérature, un bon crossover-rate serait dans un intervalle de 80 à 95%, mais parfois 60% donne les meilleurs résultats.
 * La taille de la solution n'est pas forcément connue à l'avance, il faut donc la rendre dynamique.
 * 		Dans notre cas,  pour le croisement entre individu de tailles différentes, il a été choisit de prendre la taille du plus grand.
*/

package com.hepl.SourceProject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleGeneticAlgorithm {

    public static final double uniformRate = 0.5;
    public static final double mutationRate = 0.01; //0.5% to 1% (same src as cross-over rate)
    private static final int tournamentSize = 5;
    private static final boolean elitism = true;
    private static final int overshootIteration = 100000; //Stop condition
    private static final double crossoverRate = 0.85; //In percent -> 80-95% but some time, the best is 60% (https://www.obitko.com/tutorials/genetic-algorithms/recommendations.php)
    
    public static final double changePriority = 0.8;
    public enum SelectionType {WHEEL,TOURNAMENT};
    private SelectionType selection = SelectionType.TOURNAMENT;

	public static IFitness FitnessManager;

	public SimpleGeneticAlgorithm(IFitness f)
	{
		FitnessManager = f;
	}

    public boolean runAlgorithm(IPopulation myPop) throws Exception{
        //if (solution.length() != SimpleGeneticAlgorithm.solution.length) {
            //throw new RuntimeException("The solution needs to have " + SimpleGeneticAlgorithm.solution.length + " bytes");
        //} //Boom 
        //IPopulation myPop = new Population(populationSize, true);

        int generationCount = 1;
        while (myPop.getFittest().getFitness() < FitnessManager.getMaxFitness()) { //problème de taille
            System.out.println("Generation: " + generationCount + " Best fitness found: " + myPop.getFittest().getFitness());
            myPop = evolvePopulation(myPop);
            generationCount++;
            if(generationCount>=overshootIteration){
				System.out.println("Overshoot!");
				System.out.println("The number of generation is above " + overshootIteration + " generations");
				return false;
			}
        }
        System.out.println("Solution found!");
        System.out.println("Generation: " + generationCount);
        System.out.println("Genes: ");
        System.out.println(myPop.getFittest());
        return true;
    }

    public IPopulation evolvePopulation(IPopulation pop) throws Exception {
        int elitismOffset;
        IPopulation newPopulation = pop.cloneEmpty();
		
		//cross-over rate = how many individuals of the previous population I will keep for the next
		double previousPopulationSize = pop.getIndividuals().size();
		double crossoveredPopulation = (previousPopulationSize * crossoverRate); //The previous population number multiply by the cross-over rate
        //System.out.println(crossoveredPopulation); //To verify
        
        if (elitism) {
            //newPopulation.getIndividuals().add(0, pop.getFittest());
            newPopulation.getIndividuals().add(0, pop.getFittest().clone()); // Utilisation du clonage pour éviter la référence partagée (Mr. Hiard)
            elitismOffset = 1;
        } else {
            elitismOffset = 0;
        }
        
        switch(selection){
			case TOURNAMENT:

				for (int i = elitismOffset; i < elitismOffset + (int)crossoveredPopulation; i++) { //First add the new individuals in the new population
					Iindividual indiv1 = tournamentSelection(pop);
					Iindividual indiv2 = tournamentSelection(pop);
					Iindividual newIndiv = pop.crossover(indiv1, indiv2);
					newPopulation.add(newIndiv);
				}
				
				for (int i = elitismOffset + (int)crossoveredPopulation; i < previousPopulationSize; i++) //After add the % of previous population
					newPopulation.add(pop.getIndividual(i).clone()); // -> Problème résolu par Mr. Hiard (surtout pour WHEEL)
					//newPopulation.getIndividuals().add(i, pop.getIndividual(i)); // C'est naze -> en fait il ne copie rien mais fait une référence

			break;
			
			case  WHEEL:
			
				for (int i = elitismOffset; i < elitismOffset + (int)crossoveredPopulation; i++) { //First add the new individuals in the new population
					Iindividual indiv1 = wheelSelection(pop);
					Iindividual indiv2 = wheelSelection(pop);
					while(indiv1.equals(indiv2)){
						indiv2 = wheelSelection(pop);
					}
					
					Iindividual newIndiv = pop.crossover(indiv1, indiv2);
					newPopulation.add( newIndiv);
				}
				
				for (int i = elitismOffset + (int)crossoveredPopulation; i < previousPopulationSize; i++) //After add the % of previous population
					newPopulation.add( pop.getIndividual(i).clone()); // Résolution par Mr. Hiard
					//newPopulation.getIndividuals().add(i, pop.getIndividual(i)); // C'est naze -> en fait il ne copie rien mais fait une référence
				
			break;
		}
        for (int i = elitismOffset; i < newPopulation.getIndividuals().size(); i++) {
            pop.mutate(newPopulation.getIndividual(i));
        }

        return newPopulation;
    }

    


    private Iindividual tournamentSelection(IPopulation pop) {
        Iindividual fittest = new Individual();
		int fitness = -1;
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.getIndividuals().size());
            if(fitness < pop.getIndividual(randomId).getFitness())
			{
				fittest = pop.getIndividual(randomId);
				fitness = pop.getIndividual(randomId).getFitness();
			}
        }

        return fittest;
    }

		
		private Iindividual wheelSelection(IPopulation pop){
			ArrayList<Integer> selectionWheel = new ArrayList<Integer>(pop.getIndividuals().size());
				int totalFitness = 0;
				for (int i = 0; i < pop.getIndividuals().size(); i++) 
				{
				  totalFitness += pop.getIndividual(i).getFitness(); 
				  selectionWheel.add(totalFitness);
				   
				}
				
				//System.out.println("KAPUT ?");
				Random rd = new Random();
				int wheelspin = rd.nextInt(totalFitness);
				for(int i = 0; i < selectionWheel.size();i++)
				{
					if(wheelspin > selectionWheel.get(i))
					{
						return pop.getIndividual(i);
					}
				}
				
			return pop.getIndividual(pop.getIndividuals().size()-1);
		}

}

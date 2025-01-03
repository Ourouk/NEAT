/* notes :
 * ! PAS ENCORE RAGARDER MAIS SUREMENT EN LIEN AVEC LA TAILLE NON FIXE DES INDIVIDUS !
*/ 

package com.hepl.SourceProject;
import java.util.ArrayList;
import java.util.List;

public class Population implements IPopulation{

    private List<Individual> individuals;

    public Population(int size, boolean createNew) {
        individuals = new ArrayList<>();
        if (createNew) {
            createNewPopulation(size);
        }
    }

    public Individual getIndividual(int index) {
        return individuals.get(index);
    }

    public List<Individual> getIndividuals() {
        return individuals;
    }

    public Individual getFittest() {
        Individual fittest = individuals.get(0);
        for (int i = 0; i < individuals.size(); i++) {
            if (fittest.getFitness() <= getIndividual(i).getFitness()) {
                fittest = getIndividual(i);
            }
        }
        return fittest;
    }

    private void createNewPopulation(int size) {
        for (int i = 0; i < size; i++) {
            Individual newIndividual = new Individual();
            individuals.add(i, newIndividual);
        }
    }

    @Override
    public Individual crossover(Individual indiv1, Individual indiv2) {
        //Choose the size of the biggest individuals
		int newGeneLength = 0;
		int minGeneLength = 0;
		if(indiv1.getGeneLength()>=indiv2.getGeneLength()){
			newGeneLength = indiv1.getGeneLength();
			minGeneLength = indiv2.getGeneLength();
		}
		else{
			newGeneLength = indiv2.getGeneLength();
			minGeneLength = indiv1.getGeneLength();
		}
        Individual newSol = new Individual(newGeneLength); //solution have the size of the biggest
        
        //Crossover in the thicker part
        for (int i = 0; i < minGeneLength; i++) {
            if (Math.random() <= SimpleGeneticAlgorithm.uniformRate) {
                newSol.setSingleGene(i, indiv1.getSingleGene(i));
            } else {
                newSol.setSingleGene(i, indiv2.getSingleGene(i));
            }
        }
        
        //For the rest -> copy (same idee with the cross-over rate)
        if(indiv1.getGeneLength()>indiv2.getGeneLength()){
			for (int i = minGeneLength; i < newGeneLength; i++)
				newSol.setSingleGene(i,indiv1.getSingleGene(i));
		}
		else if(indiv1.getGeneLength()<indiv2.getGeneLength()){
			for (int i = minGeneLength; i < newGeneLength; i++)
				newSol.setSingleGene(i,indiv2.getSingleGene(i));
		}
		// System.out.println(newGeneLength + " " + minGeneLength);
        return newSol;
    }

    @Override
    public void mutate(Individual indiv) {
        for (int i = 0; i < indiv.getGeneLength(); i++) {
			if (Math.random() <= SimpleGeneticAlgorithm.mutationRate) {
				if (Math.random() < SimpleGeneticAlgorithm.changePriority) {
					// Modify gene
					byte gene = (byte) Math.round(Math.random());
					indiv.setSingleGene(i, gene);
				} else {
					// random add or remove
					if (Math.random() > 0.5) {
						// add at the end a gene
						byte newGene = (byte) Math.round(Math.random());
						indiv.addGene(newGene);
					} else{
						// remove at a random index
						int indexToRemove = (int) (Math.random() * indiv.getGeneLength());
						indiv.removeGene(indexToRemove);
					}
				}
			}
		}
    }
    
}

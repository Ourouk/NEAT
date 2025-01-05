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

    public Iindividual getIndividual(int index) {
        return individuals.get(index);
    }

    public List<Iindividual> getIndividuals() {
        
        List<Iindividual> ret = new ArrayList<>();
        for (Individual individual : individuals)
        {
            ret.add(individual) ; 
        }
        return ret;
    }

    public Individual getFittest() {
        Individual fittest = individuals.get(0);
        for (int i = 0; i < individuals.size(); i++) {
            if (fittest.getFitness() <= individuals.get(i).getFitness()) {
                fittest = individuals.get(i);
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
    public Iindividual crossover(Iindividual Iindiv1, Iindividual Iindiv2) throws Exception {
        //Choose the size of the biggest individuals
        if(!(Iindiv1 instanceof Individual && Iindiv2 instanceof Individual))
        {
            throw new Exception("This byte population received incorrect individuals");
        }
        Individual indiv1 = (Individual)Iindiv1;
        Individual indiv2 = (Individual)Iindiv2;
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
    public void mutate(Iindividual Iindiv) throws Exception {
        if(!(Iindiv instanceof Individual))
        {
            throw new Exception("This byte population received incorrect individuals");
        }
        Individual indiv = (Individual)Iindiv;


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

    @Override
    public void add(Iindividual ind) throws Exception
    {
        if(!(ind instanceof Individual))
        {
            throw new Exception("This byte population received incorrect individuals");
        }
        Individual indiv = (Individual)ind;  
        individuals.add(indiv);   
    }

    @Override
    public IPopulation cloneEmpty() {
        return new Population(0, false);
    }
    
}

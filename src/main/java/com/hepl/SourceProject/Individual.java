/* notes :
 * La taille des gènes n'est plus fixe.
 * ! RAJOUTER INDIVIDUAL MAIS ALEATOIRE !
*/

package com.hepl.SourceProject;
public class Individual {

    protected int defaultGeneLength = 64; //Size of gene is not anymore static
    //private byte[] genes = new byte[defaultGeneLength];
    private byte[] genes;
    private int fitness = 0;

    //public Individual() { //Keep defaultGeneLength bits
		//genes = new byte[defaultGeneLength];
        //for (int i = 0; i < genes.length; i++) {
            //byte gene = (byte) Math.round(Math.random());
            //genes[i] = gene;
        //}
    //}
    
        public Individual() { //Random bits
		int length = (int) (Math.random()*((128 - 32) + 1)) + 32;
		genes = new byte[length];
        for (int i = 0; i < genes.length; i++) {
            byte gene = (byte) Math.round(Math.random());
            genes[i] = gene;
        }
    }
    
    public Individual(int geneLength) {
		genes = new byte[geneLength];
		 for (int i = 0; i < genes.length; i++) {
            byte gene = (byte) Math.round(Math.random());
            genes[i] = gene;
        }
    }
    
	@Override
	public Individual clone() { // Pour régler le 'bug' de  newPopulation.getIndividuals().add(i, pop.getIndividual(i)); vu par Mr. Hiard (une référence pas une copie !)
		Individual clone = new Individual(this.getGeneLength());
		for (int i = 0; i < this.getGeneLength(); i++) {
			clone.setSingleGene(i, this.getSingleGene(i));
		}
		return clone;
	}

    public int getDefaultGeneLength() {
        return defaultGeneLength;
    }
    
    public int getGeneLength() {
		return genes.length;
	}

    protected byte getSingleGene(int index) {
        return genes[index];
    }

    protected void setSingleGene(int index, byte value) {
        genes[index] = value;
        fitness = 0;
    }
    
    //Operation for mutation
    public void addGene(byte value){
		byte[] augmentedGenes=new byte[getGeneLength()+1];
		for(int i=0;i<getGeneLength();i++){
			augmentedGenes[i]=genes[i];
		}
		
		//same as setSingleGene
		augmentedGenes[getGeneLength()]=value;
		genes=augmentedGenes;
		fitness=0;
	}
	
	public void removeGene(int index){
		byte[] diminuedGenes=new byte[getGeneLength()-1];
		//before index that we supress
		for(int i=0;i<index;i++){
			diminuedGenes[i]=genes[i];
		}
		//after index that we supress
		for(int i=index+1;i<getGeneLength();i++){
			diminuedGenes[i-1]=genes[i];
		}
		//same thet setSingleGene
		genes=diminuedGenes;
		fitness=0;
	}

    public int getFitness() {
        if (fitness == 0) {
            fitness = SimpleGeneticAlgorithm.FitnessManager.getFitness(this);
        }
        return fitness;
    }

    @Override
    public String toString() {
        String geneString = "";
        for (int i = 0; i < genes.length; i++) {
            geneString += getSingleGene(i);
        }
        return geneString;
    }
    public byte[] getGenes(){return genes;}

}

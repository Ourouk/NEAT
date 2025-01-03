## Architecture Générale: 

![Architecture](https://github.com/user-attachments/assets/5ef73559-b2ed-4969-bdb7-15a7074f8758)

- Pool : Ensemble de tous les *Species*.
- Species : Regroupement de *GenomeWithFitness* pour la spéciation.
- GenomeWithFitness : ?
- Genome : Un individu dans l'algorithme NEAT.
- Node : Un nœud de réseau neuronal.
- Connection : Une connexion dans un réseau neuronal.
- NeatPoolAdapter : Permet d'utiliser *Pool* comme si c'était une *IPopulation*.
- NeatGenomeAdapter : Permet d'utiliser *Genome* comme si c'était un *IIndividual*.
- Population : Ensemble de tous les *Individu*s.
- Individu : Un individu lors de l'utilisation de Byte String.
- IPopulation : Interface permettant au *SimpleGeneticAlgorithm* de ne pas se soucier du type de la population.
- IIndividual : Interface utilisée par *IPopulation* et *SimpleGeneticAlgorithm* pour communiquer.
- Game : Contient les informations clés du jeu de plateforme.
- GameFitness : Calcule le fitness d'un individu donné pour un objet *Game* donné.
- SimpleByteFitness : Calcule le fitness en comparant deux listes de bytes.
- IFitness : Interface permettant au *SimpleGeneticAlgorithm* de ne pas se soucier du type de fitness.
- SimpleGeneticAlgorithm : Lance l'algorithme génétique avec un objet *IPopulation* et *IFitness*.

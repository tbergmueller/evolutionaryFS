package com.thomasbergmueller;

import java.io.IOException;

import evSOLve.JEvolution.Individual;
import evSOLve.JEvolution.JEvolution;
import evSOLve.JEvolution.JEvolutionException;
import evSOLve.JEvolution.JEvolutionReporter;
import evSOLve.JEvolution.chromosomes.PermChromosome;


public class MainClass {

	public static void main(String[] args) throws IOException {
			
		// RADAR DATA FROM 
		// https://archive.ics.uci.edu/ml/datasets/Ionosphere
		
		// https://archive.ics.uci.edu/ml/datasets/Wine
		String inputFile = "data/wine.data";
		
		
		
		JEvolution EA = JEvolution.getInstance();																					//+ call it an EA
// 		EA.setMaximization(false);																											//o minimization problem
		  JEvolutionReporter EAReporter = (JEvolutionReporter)EA.getJEvolutionReporter();			//- get the reporter
		  PermChromosome chrom = new PermChromosome();				//+ create a chromosome

		  // Load data
		  KnnPhenotype knnPheno = new KnnPhenotype(inputFile, 0.4);
		  
		  
		try {
			chrom.setLength(knnPheno.getAttributeCount());	
			//chrom.setCrossoverPoints(knnPheno.getUsedFeatures());
			
			//- only set to justify try statement..;-)
//			chromX.setMutationRate(0.0);
//			chromX.setSoupType(Chromosome.LAPLACE);
//			chromX.setCrossoverPoints(2);
// 			Utilities.setRandomSeed(88);

			EA.addChromosome(chrom);																//+ tell EA about your chromosome
			EA.setPhenotype(knnPheno);											//+ tell EA about your Phenotype class
//			EA.setSelection(new TournamentSelection(3));
// 			EA.setPopulationSize(25, 50);
			EA.setFitnessThreshold(1.0);																//o better fitness not possible
			EA.setMaximalGenerations(100);														//o 

			EAReporter.setReportLevel(JEvolutionReporter.VERBOSE);
// 			EAReporter.useFitnessRepository(true);

		} catch (JEvolutionException e) {
			System.out.println(e.toString());
			System.out.println("Continuing with default values.");
		}
		while (EA.doEvolve(1) != 0)	
		{
			
			// single step generation
		}
//			;
		//EA.doEvolve();									// + evolution run
		
		Individual best = EAReporter.getBestIndividual();
		best.toFile("bestResult.xml");
		//Individual bestFromFile = new Individual("bestResult.xml");
		
		//System.out.println(best.getGenotype().equals(bestFromFile.getGenotype()));
		
	}

}

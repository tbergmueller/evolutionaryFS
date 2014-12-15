package com.thomasbergmueller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import evSOLve.JEvolution.Phenotype;
import evSOLve.JEvolution.chromosomes.Chromosome;
import evSOLve.JEvolution.chromosomes.PermChromosome;
import evSOLve.JEvolution.misc.Xml;

public class KnnPhenotype implements Phenotype {

	protected double fitness;
	protected int nBases;								// the number of elements
	protected int nCorrect;								// the phenotypic expression
	protected int _k=5;
		
	
	static ArrayList<DataInstance> _data;	
	
	private int _nrFeaturesToUse;
	
/** Constructs the basic phenotype. 
 * @throws IOException */
	public KnnPhenotype(String pathToFile, double featuresToUsePercentage) throws IOException 
	{
		
		String[] fnameParts = pathToFile.split("/");
		
		if(fnameParts[fnameParts.length-1].equals("wine.data"))
		{
			System.out.println("Load data from " + pathToFile);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathToFile)));
						
			String line;
			
			_data = new ArrayList<DataInstance>();
			
			while((line = br.readLine()) != null)
			{
				_data.add(new DataInstance(line));
			}
			
			br.close();
			
		}
		else
		{
			throw new IOException("Unknown data file, no parsing rule for this");
		}
		
		
		_nrFeaturesToUse = (int)Math.floor((double)this.getAttributeCount() * featuresToUsePercentage);
		
	}
	
	
	public int getAttributeCount()
	{
		return _data.get(0).getAttributeCount();
	}
	
	public int getUsedFeatures()
	{
		return _nrFeaturesToUse;
	}
	
   


/** A proper clone. Here the phenotype is simply an integer, so super.clone() handles
* everything just fine.
*
* @returns Object							The clone.
*
*/

	public Object clone() {
		try {
			KnnPhenotype clone = (KnnPhenotype)super.clone();
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.toString());			//should not happen
		}
	}
	
	
/** The genotype-phenotype mapper just counts the number of correct
* permutation elements.
*/
	
	public void doOntogeny(List genotype)
	{
	
		
		
		
		
		
		PermChromosome chrom = (PermChromosome)genotype.get(0);
		ArrayList<Integer> perm = (ArrayList<Integer>)chrom.getBases();
		
		
		
		
		nCorrect = 0;
		nBases = _data.size();
				
		
		for(int i=0; i<nBases; i++)
		{
			if(MyKNN.crossoverEvaluate(_data, i, perm, this.getUsedFeatures(), _k))
			{
				nCorrect++;
			}
		}
		
		
	
	}
	
	
/** Here the fitness is simply the percentage of correct elements. */


	public void calcFitness() {
		fitness = (double)nCorrect / (double)nBases;
	}
			
				
/** Access to the fitness of the Phenotype. */

	public double getFitness() {
		return fitness;
	}
		
		
/** A String representation of the Phenotype. */

	public String toString() {
		return(nCorrect + " elements out of " + nBases + " correct.");
	}
	
		
/** Saves the phenotype to XML.
*
* @param element			a JDOM element
*
*/
    public void toXml(Element element) {
    
		Xml.addChildTo(element, "Items", String.valueOf(nBases));	
		Xml.addChildTo(element, "Correct", String.valueOf(nCorrect));	
	}
//
}

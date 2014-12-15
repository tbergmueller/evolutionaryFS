package com.thomasbergmueller;

import java.util.ArrayList;

public class DataInstance {

	private static String sepChar = ",";
	
	int _class;
	double _features[];
	
	public DataInstance(String lineInFile)
	{
		String[] parts = lineInFile.split(sepChar);
		
		_class = Integer.parseInt(parts[0]);
		_features = new double[parts.length-1];
		
		for(int i=0; i<_features.length; i++)
		{
			_features[i] = Double.parseDouble(parts[i+1]);
		}		
	}
	
	public double distanceTo(DataInstance d2, ArrayList<Integer> permutation, int nrOfFeatures)
	{
		double eucDist = 0.0;
		for(int i=0; i<nrOfFeatures; i++)
		{
			eucDist += (this._features[permutation.get(i)] - d2._features[permutation.get(i)]) * (this._features[permutation.get(i)] - d2._features[permutation.get(i)]);
		}
		
		return Math.sqrt(eucDist);
	}
	
	public int getTarget()
	{
		return _class;
	}
	
	
	public int getAttributeCount()
	{
		return _features.length;
	}
}

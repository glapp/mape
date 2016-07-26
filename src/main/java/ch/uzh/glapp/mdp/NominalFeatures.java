package ch.uzh.glapp.mdp;


import burlap.behavior.functionapproximation.dense.DenseStateFeatures;
import burlap.mdp.core.state.State;

import java.util.ArrayList;
import java.util.List;

public class NominalFeatures implements DenseStateFeatures {

	protected List<Object> whiteList = null;

	public NominalFeatures addToWhiteList(Object variableKey){
		if(whiteList == null){
			this.whiteList = new ArrayList<Object>();
		}
		this.whiteList.add(variableKey);

		return this;
	}


	/**
	 * Returns a feature vector represented as a double array for a given input state.
	 *
	 * @param s the input state to turn into a feature vector.
	 * @return the feature vector represented as a double array.
	 */
	@Override
	public double[] features(State s) {

		if(this.whiteList == null){
			//then use all
			List<Object> keys = s.variableKeys();
			double [] vals = new double[keys.size()];
			System.out.println("size r: "+ (MapeWorld.PROVIDER_LIST.length + MapeWorld.REGION_LIST.length + MapeWorld.TIER_LIST.length + 1));
			int size = 100;
			double [] oneHotVals = new double[size];

			int i = 0;
			for(Object key : keys){
				Number n = (Number)s.get(key);
				System.out.println("s.get(key): "+n+ ", key: "+key);


				vals[i] = n.doubleValue();
				i++;
			}

			return vals;
		}

		//otherwise use white list
		double [] vals = new double[this.whiteList.size()];
		int i = 0;
		for(Object key : this.whiteList){
			Number n = (Number)s.get(key);
			vals[i] = n.doubleValue();
			i++;
		}

		return vals;

	}

	/**
	 * Returns a copy of this {@link DenseStateFeatures}
	 *
	 * @return a copy of this {@link DenseStateFeatures}
	 */
	@Override
	public DenseStateFeatures copy() {
		return null;
	}
}

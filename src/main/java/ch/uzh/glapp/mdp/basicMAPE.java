package ch.uzh.glapp.mdp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import burlap.behavior.singleagent.EpisodeAnalysis;
import burlap.behavior.singleagent.learning.LearningAgent;
import burlap.behavior.singleagent.learning.tdmethods.QLearning;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.GroundedAction;
import burlap.oomdp.singleagent.RewardFunction;
import burlap.oomdp.singleagent.environment.Environment;
import burlap.oomdp.singleagent.environment.SimulatedEnvironment;
import burlap.oomdp.statehashing.HashableStateFactory;
import burlap.oomdp.statehashing.SimpleHashableStateFactory;

public class basicMAPE {
	MapeWorldDomain md;
	Domain domain;
	RewardFunction rf;
	TerminalFunction tf;
	State initialState;
	HashableStateFactory hashingFactory;
	Environment env;
	
	public basicMAPE() {
		debugOutput.setDate(new Date());
		
		rf = new testRF();
		tf = new testTF();
		
		md = new MapeWorldDomain();
		domain = md.generateDomain();
		
		initialState = MapeWorldDomain.setInitialState(domain);
		List<ObjectInstance> cells = initialState.getObjectsOfClass(MapeWorldDomain.CLASS_CELL);
		
		for (ObjectInstance cell : cells) {
//			System.out.println("Cell name: " + cell.getName());
			cell.setValue(MapeWorldDomain.PROVIDER, MapeWorldDomain.AWS);
			cell.setValue(MapeWorldDomain.TIER, MapeWorldDomain.TIER2);
			cell.setValue(MapeWorldDomain.GEO, MapeWorldDomain.EU);
			cell.setValue(MapeWorldDomain.NUM_CELLS, 1);
//			cell.setValue(MapeWorldDomain.NUM_CELLS_CATEGORY, MapeWorldDomain.LOW);
		}
		
		hashingFactory = new SimpleHashableStateFactory();
		
		env = new SimulatedEnvironment(domain, rf, tf, initialState);
	}
	
	public static void main(String[] args) {
//		System.out.println("Start");
		
		basicMAPE test = new basicMAPE();
		String outputPath = "output/";

		test.qLearning(outputPath);
	}
	
	public void qLearning(String outputPath){
		// Discount factor: 0.99
		// Initial Q-value: 0
		// Learning rate: 0.9
		LearningAgent agent = new QLearning(domain, 0.99, hashingFactory, 0, 0.9);
		

		//run learning for 1 episode
		for(int i = 0; i < 1; i++){
			EpisodeAnalysis ea = agent.runLearningEpisode(env);

			ea.writeToFile(outputPath + "ql_" + i);
			System.out.println(i + ": " + ea.maxTimeStep());

			//reset environment for next learning episode
			env.resetEnvironment();
		}

	}
	
	/*
	 * A testing terminal function, return true if the provider is Digital Ocean
	 */
	public static class testTF implements TerminalFunction {
		
		@Override
		public boolean isTerminal(State s) {
			List<ObjectInstance> cells = s.getObjectsOfClass(MapeWorldDomain.CLASS_CELL);
			
			for (ObjectInstance cell : cells) {
				String currentProvider = cell.getStringValForAttribute(MapeWorldDomain.PROVIDER);
				String currentTier = cell.getStringValForAttribute(MapeWorldDomain.TIER);
				String currentGeo = cell.getStringValForAttribute(MapeWorldDomain.GEO);
				int currentNumOfCells = cell.getIntValForAttribute(MapeWorldDomain.NUM_CELLS);
//				String currentNumOfCellsCategory = cell.getStringValForAttribute(MapeWorldDomain.NUM_CELLS_CATEGORY);
				
//				System.out.println("Provider: " + currentProvider + "\tTier: " + currentTier + "\tGeo: " + currentGeo + "\tNumber of cells: " + currentNumOfCells + "\tNumber of cells(Category): " + currentNumOfCellsCategory); 
//				System.out.println("MapeWorldDomain.DO=" + MapeWorldDomain.DO);
//				System.out.println("String comparison: " + currentProvider.equals(MapeWorldDomain.DO));
				
				if (currentProvider.equals(MapeWorldDomain.DO) && currentTier.equals(MapeWorldDomain.TIER1) && currentGeo.equals(MapeWorldDomain.EU) && currentNumOfCells > 2) {
					return true;
				}
			}
			
			return false;
		}
	}
	
	public static class testRF implements RewardFunction {

		@Override
		public double reward(State s, GroundedAction a, State sprime) {
			HashMap rewards = new HashMap<String, Double>();
			rewards.put(MapeWorldDomain.DO, 0.0);
			rewards.put(MapeWorldDomain.AWS, 0.0);
			rewards.put(MapeWorldDomain.GOOGLE, 0.0);
			rewards.put(MapeWorldDomain.AZURE, 0.0);
			rewards.put(MapeWorldDomain.TIER1, 0.3);
			rewards.put(MapeWorldDomain.TIER2, 0.2);
			rewards.put(MapeWorldDomain.TIER3, 0.1);
			rewards.put(MapeWorldDomain.EU, 0.2);
			rewards.put(MapeWorldDomain.NA, 0.15);
			rewards.put(MapeWorldDomain.ASIA, 0.1);
			
			List<ObjectInstance> sCells = s.getObjectsOfClass(MapeWorldDomain.CLASS_CELL);

			double currentHealthiness = 0;
			for (ObjectInstance cell : sCells) {
				currentHealthiness += ((Double)rewards.get(cell.getStringValForAttribute(MapeWorldDomain.PROVIDER))).doubleValue();
				currentHealthiness += ((Double)rewards.get(cell.getStringValForAttribute(MapeWorldDomain.TIER))).doubleValue();
				currentHealthiness += ((Double)rewards.get(cell.getStringValForAttribute(MapeWorldDomain.GEO))).doubleValue();
				currentHealthiness += 0.1;// * cell.getIntValForAttribute(MapeWorldDomain.NUM_CELLS);
			}
			
			double newHealthiness = 0;
			List<ObjectInstance> sPrimeCells = sprime.getObjectsOfClass(MapeWorldDomain.CLASS_CELL);
			for (ObjectInstance cell : sPrimeCells) {
				newHealthiness += ((Double)rewards.get(cell.getStringValForAttribute(MapeWorldDomain.PROVIDER))).doubleValue();
				newHealthiness += ((Double)rewards.get(cell.getStringValForAttribute(MapeWorldDomain.TIER))).doubleValue();
				newHealthiness += ((Double)rewards.get(cell.getStringValForAttribute(MapeWorldDomain.GEO))).doubleValue();
				newHealthiness += 0.1;// * cell.getIntValForAttribute(MapeWorldDomain.NUM_CELLS);
			}
			
			double costOfAction = 0;
			double reward = newHealthiness - currentHealthiness - costOfAction;
			
			return reward;
		}
		
	}
	
	public void log(String output) {
		debugOutput.log(output);
	}
	
}

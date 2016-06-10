package mapeTest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import burlap.behavior.policy.Policy;
import burlap.behavior.singleagent.EpisodeAnalysis;
import burlap.behavior.singleagent.learning.LearningAgent;
import burlap.behavior.singleagent.learning.tdmethods.QLearning;
import burlap.behavior.singleagent.planning.Planner;
import burlap.behavior.singleagent.planning.stochastic.valueiteration.ValueIteration;
import burlap.oomdp.auxiliary.common.NullTermination;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.GroundedAction;
import burlap.oomdp.singleagent.RewardFunction;
import burlap.oomdp.singleagent.common.UniformCostRF;
import burlap.oomdp.singleagent.environment.Environment;
import burlap.oomdp.singleagent.environment.SimulatedEnvironment;
import burlap.oomdp.statehashing.HashableStateFactory;
import burlap.oomdp.statehashing.SimpleHashableStateFactory;

public class basicMAPE {
	MAPEDomain md;
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
		
		md = new MAPEDomain();
		domain = md.generateDomain();
		
		initialState = MAPEDomain.setInitialState(domain);
		List<ObjectInstance> cells = initialState.getObjectsOfClass(MAPEDomain.CLASS_CELL);
		
		for (ObjectInstance cell : cells) {
//			System.out.println("Cell name: " + cell.getName());
			cell.setValue(MAPEDomain.PROVIDER, MAPEDomain.AWS);
			cell.setValue(MAPEDomain.TIER, MAPEDomain.TIER2);
			cell.setValue(MAPEDomain.GEO, MAPEDomain.EU);
			cell.setValue(MAPEDomain.NUM_CELLS, 1);
//			cell.setValue(MAPEDomain.NUM_CELLS_CATEGORY, MAPEDomain.LOW);
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
			List<ObjectInstance> cells = s.getObjectsOfClass(MAPEDomain.CLASS_CELL);
			
			for (ObjectInstance cell : cells) {
				String currentProvider = cell.getStringValForAttribute(MAPEDomain.PROVIDER);
				String currentTier = cell.getStringValForAttribute(MAPEDomain.TIER);
				String currentGeo = cell.getStringValForAttribute(MAPEDomain.GEO);
				int currentNumOfCells = cell.getIntValForAttribute(MAPEDomain.NUM_CELLS);
//				String currentNumOfCellsCategory = cell.getStringValForAttribute(MAPEDomain.NUM_CELLS_CATEGORY);
				
//				System.out.println("Provider: " + currentProvider + "\tTier: " + currentTier + "\tGeo: " + currentGeo + "\tNumber of cells: " + currentNumOfCells + "\tNumber of cells(Category): " + currentNumOfCellsCategory); 
//				System.out.println("MAPEDomain.DO=" + MAPEDomain.DO);
//				System.out.println("String comparison: " + currentProvider.equals(MAPEDomain.DO));
				
				if (currentProvider.equals(MAPEDomain.DO) && currentTier.equals(MAPEDomain.TIER1) && currentGeo.equals(MAPEDomain.EU) && currentNumOfCells > 2) {
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
			rewards.put(MAPEDomain.DO, 0.0);
			rewards.put(MAPEDomain.AWS, 0.0);
			rewards.put(MAPEDomain.GOOGLE, 0.0);
			rewards.put(MAPEDomain.AZURE, 0.0);
			rewards.put(MAPEDomain.TIER1, 0.3);
			rewards.put(MAPEDomain.TIER2, 0.2);
			rewards.put(MAPEDomain.TIER3, 0.1);
			rewards.put(MAPEDomain.EU, 0.2);
			rewards.put(MAPEDomain.NA, 0.15);
			rewards.put(MAPEDomain.ASIA, 0.1);
			
			List<ObjectInstance> sCells = s.getObjectsOfClass(MAPEDomain.CLASS_CELL);

			double currentHealthiness = 0;
			for (ObjectInstance cell : sCells) {
				currentHealthiness += ((Double)rewards.get(cell.getStringValForAttribute(MAPEDomain.PROVIDER))).doubleValue();
				currentHealthiness += ((Double)rewards.get(cell.getStringValForAttribute(MAPEDomain.TIER))).doubleValue();
				currentHealthiness += ((Double)rewards.get(cell.getStringValForAttribute(MAPEDomain.GEO))).doubleValue();
				currentHealthiness += 0.1;// * cell.getIntValForAttribute(MAPEDomain.NUM_CELLS);
			}
			
			double newHealthiness = 0;
			List<ObjectInstance> sPrimeCells = sprime.getObjectsOfClass(MAPEDomain.CLASS_CELL);
			for (ObjectInstance cell : sPrimeCells) {
				newHealthiness += ((Double)rewards.get(cell.getStringValForAttribute(MAPEDomain.PROVIDER))).doubleValue();
				newHealthiness += ((Double)rewards.get(cell.getStringValForAttribute(MAPEDomain.TIER))).doubleValue();
				newHealthiness += ((Double)rewards.get(cell.getStringValForAttribute(MAPEDomain.GEO))).doubleValue();
				newHealthiness += 0.1;// * cell.getIntValForAttribute(MAPEDomain.NUM_CELLS);
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

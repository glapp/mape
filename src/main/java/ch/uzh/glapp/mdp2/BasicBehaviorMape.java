package ch.uzh.glapp.mdp2;

import burlap.behavior.singleagent.Episode;
import burlap.behavior.singleagent.learning.LearningAgent;
import burlap.behavior.singleagent.learning.tdmethods.QLearning;
import burlap.mdp.core.oo.state.generic.DeepOOState;
import burlap.mdp.singleagent.environment.Environment;
import burlap.mdp.singleagent.environment.SimulatedEnvironment;
import burlap.mdp.singleagent.oo.OOSADomain;
import burlap.statehashing.HashableStateFactory;
import burlap.statehashing.simple.SimpleHashableStateFactory;

import static ch.uzh.glapp.mdp2.MapeWorld.*;

public class BasicBehaviorMape {

	MapeWorld mwdg;
	OOSADomain domain;
	DeepOOState initialState;
	HashableStateFactory hashingFactory;
	Environment env;

	public BasicBehaviorMape () {
		mwdg = new MapeWorld();
		domain = mwdg.generateDomain();
		initialState = new DeepOOState();
		
		// MapeCell(String cellName, String provider, String region, String tier, int cells, String proxy_provider, String proxy_region)
		initialState.addObject(new MapeCell("Organ_57077ea32f9806267c71b4f8_Cell_1", AWS, EU, TIER2, 1, AWS, EU));
		initialState.addObject(new MapeCell("Organ_57077ea32f9806267c71b4f9_Cell_1", AWS, EU, TIER2, 1, AWS, EU));
		
		hashingFactory = new SimpleHashableStateFactory();
		env = new SimulatedEnvironment(domain);
	}

	public void MyQLearningFunc (String outputPath) {

		LearningAgent agent = new QLearning(domain, 0.99, hashingFactory, 0., 1.);
		Episode episode = agent.runLearningEpisode(env, 1);

		episode.write(outputPath + "ql_0");
		System.out.println("Steps in episode: " + episode.maxTimeStep());

		//reset environment for next learning episode (not needed for only one episode)
//		env.resetEnvironment();

	}


	public static void main (String[] args) {

		BasicBehaviorMape basicBehaviorMape = new BasicBehaviorMape();
		String outputPath = "output/"; // directory to record results

		basicBehaviorMape.MyQLearningFunc(outputPath);

	}

}

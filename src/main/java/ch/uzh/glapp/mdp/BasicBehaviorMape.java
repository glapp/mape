package ch.uzh.glapp.mdp;

import burlap.behavior.singleagent.Episode;
import burlap.behavior.singleagent.learning.LearningAgent;
import burlap.behavior.singleagent.learning.tdmethods.QLearning;
import burlap.mdp.core.oo.state.generic.DeepOOState;
import burlap.mdp.singleagent.environment.Environment;
import burlap.mdp.singleagent.oo.OOSADomain;
import burlap.statehashing.HashableStateFactory;
import burlap.statehashing.simple.SimpleHashableStateFactory;
import ch.uzh.glapp.model.ObjectForMdp;

public class BasicBehaviorMape {

	MapeWorld mwdg;
	OOSADomain domain;
	DeepOOState initialState;
	HashableStateFactory hashingFactory;
	Environment env;

	public BasicBehaviorMape (ObjectForMdp objectForMdp) {
		mwdg = new MapeWorld();
		domain = mwdg.generateDomain();
		initialState = new DeepOOState();

		// For simulation only:
		// MapeCell(String cellName, String provider, String region, String tier, int cells, String proxy_provider, String proxy_region)
//		initialState.addObject(new MapeCell("Organ_57077ea32f9806267c71b4f8_Cell_1", AWS, EU, TIER2, 1, AWS, EU));
//		initialState.addObject(new MapeCell("Organ_57077ea32f9806267c71b4f9_Cell_1", AWS, EU, TIER2, 1, AWS, EU));

		hashingFactory = new SimpleHashableStateFactory();
		env = new MapeEnvironment(domain, objectForMdp);
	}

	public void MyQLearningFunc (String outputPath) {

		LearningAgent agent = new QLearning(domain, 0.99, hashingFactory, 0., 1.);
		Episode episode = agent.runLearningEpisode(env, 1);

		episode.write(outputPath + "ql_0");
		System.out.println("Steps in episode: " + episode.maxTimeStep());

		//reset environment for next learning episode (not needed for only one episode)
//		env.resetEnvironment();

	}


//	public static void main (String[] args) {
//
//		// policy triggers MDP
//		// need following infos from triggering policy:
//		//      proetheus metric, healthy value app level, cell ID, organ ID, app ID
//
//		String metric = "process_cpu_seconds_total";
//		String cellId = "577b925a9babc96e2fa21106";
//		String organId = "577b89f65127a7f32799b427";
//		String appId = "577b89f55127a7f32799b425";
//		float healthyValue = 0;
//
//		ObjectForMdp o = new ObjectForMdp(metric, cellId, organId, appId, healthyValue);
//
//		BasicBehaviorMape basicBehaviorMape = new BasicBehaviorMape(o);
//		String outputPath = "output/"; // directory to record results
//
//		basicBehaviorMape.MyQLearningFunc(outputPath);
//
//	}

}

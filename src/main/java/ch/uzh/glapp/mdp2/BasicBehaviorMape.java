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
import ch.uzh.glapp.model.ObjectForMdp;

import static ch.uzh.glapp.mdp2.MapeWorld.*;

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

		// MapeCell(String cellName, String provider, String region, String tier, int cells, String proxy_provider, String proxy_region)
		initialState.addObject(new MapeCell("Organ_57077ea32f9806267c71b4f8_Cell_1", AWS, EU, TIER2, 1, AWS, EU));
		initialState.addObject(new MapeCell("Organ_57077ea32f9806267c71b4f9_Cell_1", AWS, EU, TIER2, 1, AWS, EU));

		hashingFactory = new SimpleHashableStateFactory();
		env = new MapeEnvironment2(domain, objectForMdp);
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

		// policy triggers MDP
		// need following infos from triggering policy:
		//      proetheus metric, healthy value app level, cell ID, organ ID, app ID

		String policy = "process_cpu_seconds_total"; // TODO: that doesn't work.
		String cellId = "57725130644b311b20c4d8a2";
		String organId = "57724fef644b311b20c4d898";
		String appId = "57724fee644b311b20c4d896";
		String metric = "";
		float healthyValue = 0;

		ObjectForMdp o = new ObjectForMdp(policy, cellId, organId, appId, metric, healthyValue);

		BasicBehaviorMape basicBehaviorMape = new BasicBehaviorMape(o);
		String outputPath = "output/"; // directory to record results

		basicBehaviorMape.MyQLearningFunc(outputPath);

	}

}

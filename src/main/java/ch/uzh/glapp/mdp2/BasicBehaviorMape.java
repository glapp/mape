package ch.uzh.glapp.mdp2;

import burlap.behavior.singleagent.Episode;
import burlap.behavior.singleagent.learning.LearningAgent;
import burlap.behavior.singleagent.learning.tdmethods.QLearning;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.SADomain;
import burlap.mdp.singleagent.environment.Environment;
import burlap.statehashing.HashableStateFactory;
import burlap.statehashing.simple.SimpleHashableStateFactory;
import ch.uzh.glapp.model.ObjectForMdp;


public class BasicBehaviorMape {

	MapeWorld mwdg;
	SADomain domain;
	State initialState;
	HashableStateFactory hashingFactory;
	Environment env;

	public BasicBehaviorMape (ObjectForMdp objectForMdp) {
		mwdg = new MapeWorld();
		domain = mwdg.generateDomain();
		initialState = new MapeState();
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
		// need following infos: proetheus metric, healthy value app level, cell ID, organ ID, app ID

		String policyId = "";
		String cellId = "";
		String organId = "576e7b187aa4ab1e1dbe593f";
		String appId = "";
		String metric = "";
		float healthyValue = 0;

		ObjectForMdp o = new ObjectForMdp(policyId, cellId, organId, appId, metric, healthyValue);

		BasicBehaviorMape basicBehaviorMape = new BasicBehaviorMape(o);
		String outputPath = "output/"; // directory to record results

		basicBehaviorMape.MyQLearningFunc(outputPath);

	}

}

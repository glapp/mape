package ch.uzh.glapp.mdp;

import burlap.behavior.functionapproximation.DifferentiableStateActionValue;
import burlap.behavior.functionapproximation.dense.ConcatenatedObjectFeatures;
import burlap.behavior.functionapproximation.dense.NumericVariableFeatures;
import burlap.behavior.functionapproximation.sparse.tilecoding.TileCodingFeatures;
import burlap.behavior.functionapproximation.sparse.tilecoding.TilingArrangement;
import burlap.behavior.singleagent.Episode;
import burlap.behavior.singleagent.learning.LearningAgent;
import burlap.behavior.singleagent.learning.tdmethods.QLearning;
import burlap.behavior.singleagent.learning.tdmethods.vfa.GradientDescentSarsaLam;
import burlap.mdp.core.oo.state.generic.DeepOOState;
import burlap.mdp.singleagent.environment.Environment;
import burlap.mdp.singleagent.oo.OOSADomain;
import burlap.statehashing.HashableStateFactory;
import burlap.statehashing.simple.SimpleHashableStateFactory;
import ch.uzh.glapp.model.ObjectForMdp;
import ch.uzh.glapp.model.sails.MdpTriggerObject;

public class BasicBehaviorMape {

	MapeWorld mwdg;
	OOSADomain domain;
	DeepOOState initialState;
	HashableStateFactory hashingFactory;
	Environment env;
	private static MdpTriggerObject mdpTriggerObject;

	public BasicBehaviorMape (MdpTriggerObject mdpTriggerObject) {
		this.mdpTriggerObject = mdpTriggerObject;
		mwdg = new MapeWorld();
		domain = mwdg.generateDomain();
		initialState = new DeepOOState();

		// For simulation only:
		// MapeCell(String cellName, String provider, String region, String tier, int cells, String proxy_provider, String proxy_region)
//		initialState.addObject(new MapeCell("Organ_57077ea32f9806267c71b4f8_Cell_1", AWS, EU, TIER2, 1, AWS, EU));
//		initialState.addObject(new MapeCell("Organ_57077ea32f9806267c71b4f9_Cell_1", AWS, EU, TIER2, 1, AWS, EU));

		hashingFactory = new SimpleHashableStateFactory();
		
		ObjectForMdp objectForMdp = new ObjectForMdp(
			    mdpTriggerObject.getViolationList().get(0).getMetric(),
			    mdpTriggerObject.getViolationList().get(0).getCellId(),
			    mdpTriggerObject.getViolationList().get(0).getOrganId(),
			    mdpTriggerObject.getViolationList().get(0).getAppId(),
			    mdpTriggerObject.getAppHealthiness());
		
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

	public void MySarsaLearningFunc (String outputPath) {

		ConcatenatedObjectFeatures inputFeatures = new ConcatenatedObjectFeatures()
				.addObjectVectorizion(MapeWorld.CLASS_CELL, new NominalFeatures());

		int nTilings = 3;
		double providerWidth = MapeWorld.PROVIDER_LIST.length;
		double regionWidth = MapeWorld.REGION_LIST.length;
		double tierWidth = MapeWorld.TIER_LIST.length;

		TileCodingFeatures tilecoding = new TileCodingFeatures(inputFeatures);
		tilecoding.addTilingsForAllDimensionsWithWidths(
				new double[] {providerWidth, regionWidth, tierWidth},
				nTilings,
				TilingArrangement.RANDOM_JITTER);

		double defaultQ = 0.5;
		DifferentiableStateActionValue vfa = tilecoding.generateVFA(defaultQ/nTilings);
		GradientDescentSarsaLam agent = new GradientDescentSarsaLam(domain, 0.99, vfa, 0.02, 0.5);
		Episode episode = agent.runLearningEpisode(env, 1);

		episode.write(outputPath + "ql_0");
		System.out.println("Steps in episode: " + episode.maxTimeStep());

	}

	public static MdpTriggerObject getMdpTriggerObject() {
		return mdpTriggerObject;
	}



//	public static void LLSARSA(){
//
//		LunarLanderDomain lld = new LunarLanderDomain();
//		OOSADomain domain = lld.generateDomain();
//
//		LLState s = new LLState(new LLAgent(5, 0, 0), new LLBlock.LLPad(75, 95, 0, 10, "pad"));
//
//		ConcatenatedObjectFeatures inputFeatures = new ConcatenatedObjectFeatures()
//				.addObjectVectorizion(LunarLanderDomain.CLASS_AGENT, new NumericVariableFeatures());
//
//		int nTilings = 5;
//		double resolution = 10.;
//
//		double xWidth = (lld.getXmax() - lld.getXmin()) / resolution;
//		double yWidth = (lld.getYmax() - lld.getYmin()) / resolution;
//		double velocityWidth = 2 * lld.getVmax() / resolution;
//		double angleWidth = 2 * lld.getAngmax() / resolution;
//
//
//
//		TileCodingFeatures tilecoding = new TileCodingFeatures(inputFeatures);
//		tilecoding.addTilingsForAllDimensionsWithWidths(
//				new double []{xWidth, yWidth, velocityWidth, velocityWidth, angleWidth},
//				nTilings,
//				TilingArrangement.RANDOM_JITTER);
//
//
//
//
//		double defaultQ = 0.5;
//		DifferentiableStateActionValue vfa = tilecoding.generateVFA(defaultQ/nTilings);
//		GradientDescentSarsaLam agent = new GradientDescentSarsaLam(domain, 0.99, vfa, 0.02, 0.5);
//
//		SimulatedEnvironment env = new SimulatedEnvironment(domain, s);
//		List<Episode> episodes = new ArrayList<Episode>();
//		for(int i = 0; i < 5000; i++){
//			Episode ea = agent.runLearningEpisode(env);
//			episodes.add(ea);
//			System.out.println(i + ": " + ea.maxTimeStep());
//			env.resetEnvironment();
//		}
//
//		Visualizer v = LLVisualizer.getVisualizer(lld.getPhysParams());
//		new EpisodeSequenceVisualizer(v, domain, episodes);
//
//	}




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

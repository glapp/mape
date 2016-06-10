package ch.uzh.glapp.mdp;

import burlap.oomdp.core.Domain;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.RewardFunction;
import burlap.oomdp.singleagent.environment.Environment;
import burlap.oomdp.singleagent.environment.SimulatedEnvironment;
import burlap.oomdp.statehashing.HashableStateFactory;
import burlap.oomdp.statehashing.SimpleHashableStateFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MDP {

	MapeWorldGenerator md;
	Domain domain;
	State initialState;
	HashableStateFactory hashingFactory;
	Environment env;

	public MDP() {
		debugOutput.setDate(new Date());

		md = new MapeWorldGenerator();
		domain = md.generateDomain();

		initialState = MapeWorldGenerator.setInitialState(domain);
		List<ObjectInstance> cells = initialState.getObjectsOfClass(MapeWorldGenerator.CLASS_CELL);


		for (ObjectInstance cell : cells) {
//			System.out.println("Cell name: " + cell.getName());
			cell.setValue(MapeWorldGenerator.PROVIDER, MapeWorldGenerator.AWS);
			cell.setValue(MapeWorldGenerator.TIER, MapeWorldGenerator.TIER2);
			cell.setValue(MapeWorldGenerator.GEO, MapeWorldGenerator.EU);
			cell.setValue(MapeWorldGenerator.NUM_CELLS, 1);
//			cell.setValue(MapeWorldGenerator.NUM_CELLS_CATEGORY, MapeWorldGenerator.LOW);
		}

		hashingFactory = new SimpleHashableStateFactory();

		env = new SimulatedEnvironment(domain, rf, tf, initialState);
	}

}

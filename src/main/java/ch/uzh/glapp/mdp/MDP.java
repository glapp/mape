package ch.uzh.glapp.mdp;

import burlap.oomdp.core.Domain;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.environment.Environment;
import burlap.oomdp.singleagent.environment.SimulatedEnvironment;
import burlap.oomdp.statehashing.HashableStateFactory;
import burlap.oomdp.statehashing.SimpleHashableStateFactory;

import java.util.Date;
import java.util.List;

public class MDP {

	MapeWorldDomain md;
	Domain domain;
	State initialState;
	HashableStateFactory hashingFactory;
	Environment env;

	public MDP() {
		debugOutput.setDate(new Date());

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

//		env = new SimulatedEnvironment(domain, rf, tf, initialState);
	}

}

package ch.uzh.glapp.mdp2;

import burlap.mdp.core.Domain;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.environment.Environment;

/**
 * Created by riccardo on 23.06.16.
 */
public class BasicBehaviorMape {

	MapeWorld mwdg = new MapeWorld();
	Domain domain = mwdg.generateDomain();
	State initialState;
	Environment env;

	public static void main (String[] args) {

	}

}

package ch.uzh.glapp.mdp;

import burlap.mdp.core.action.Action;
import burlap.mdp.core.oo.state.generic.DeepOOState;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.environment.EnvironmentOutcome;
import burlap.mdp.singleagent.model.SampleModel;

import static ch.uzh.glapp.mdp.MapeWorld.*;

/*
 * Class for simulation only
 */
public class MapeModel implements SampleModel {

	@Override
	public EnvironmentOutcome sample(State prevState, Action action) {
		DeepOOState nextState = ((DeepOOState)prevState).copy();
		
		if (action.actionName().equals(ACTION_MOVE)) {
			
		} else if (action.actionName().equals(ACTION_CREATE)) {
			
		} else if (action.actionName().equals(ACTION_REMOVE)) {
			((DeepOOState)nextState).removeObject(((MapeActionRemove)action).getCellName());
		} else {
			// throw exception
		}
		
		return new EnvironmentOutcome(prevState, action, nextState, 0.0, false);
	}

	@Override
	public boolean terminal(State state) {
		return false;
	}

}

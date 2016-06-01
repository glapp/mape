package ch.uzh.glapp.mdp;

import java.util.ArrayList;
import java.util.List;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.TransitionProbability;
import burlap.oomdp.core.objects.MutableObjectInstance;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.Action;
import burlap.oomdp.singleagent.FullActionModel;
import burlap.oomdp.singleagent.GroundedAction;

public class MapeAction extends Action implements FullActionModel {
	private MapeGroundedAction ga;
	
	public MapeAction(String actionName, Domain domain) {
		super(actionName, domain);
	}
	
	public MapeAction(String actionName, Domain domain, MapeGroundedAction ga) {
		super(actionName, domain);
		this.ga = ga;
	}

	/*
	 * Not used for Q-learning algorithm(non-Javadoc)
	 * @see burlap.oomdp.singleagent.FullActionModel#getTransitions(burlap.oomdp.core.states.State, burlap.oomdp.singleagent.GroundedAction)
	 */
	public List<TransitionProbability> getTransitions(State s, GroundedAction groundedAction) {
		return null;
	}

	@Override
	protected State performActionHelper(State s, GroundedAction groundedAction) {
		String[] params = groundedAction.getParametersAsString();
		String targetCellName = params[0];
		ObjectInstance targetCell = s.getObject(targetCellName);
		
		String currentProvider = targetCell.getStringValForAttribute(MapeWorldGenerator.PROVIDER);
		String currentTier = targetCell.getStringValForAttribute(MapeWorldGenerator.TIER);
		String currentGeo = targetCell.getStringValForAttribute(MapeWorldGenerator.GEO);
		int currentNumOfCells = targetCell.getIntValForAttribute(MapeWorldGenerator.NUM_CELLS);
		
		if (this.getName().equals(MapeWorldGenerator.MAPE_ACTION_CREATE)) {
			List<ObjectInstance> cells = s.getObjectsOfClass(MapeWorldGenerator.CLASS_CELL);
			
			currentNumOfCells++;
			
			for (ObjectInstance cell : cells) {
				if (cell.getName().substring(0, 6).equals(targetCell.getName().substring(0, 6))) {
					cell.setValue(MapeWorldGenerator.NUM_CELLS, currentNumOfCells);
			
			// update numOfCellCategory (low/medium/high) accordingly
//			if (cell.getIntValForAttribute(MapeWorldGenerator.NUM_CELLS) <= MapeWorldGenerator.LOW_MEDIUM_THRESHOLD) {
//				cell.setValue(MapeWorldGenerator.NUM_CELLS_CATEGORY, MapeWorldGenerator.LOW);
//			} else if (cell.getIntValForAttribute(MapeWorldGenerator.NUM_CELLS) <= MapeWorldGenerator.MEDIUM_HIGH_THRESHOLD) {
//				cell.setValue(MapeWorldGenerator.NUM_CELLS_CATEGORY, MapeWorldGenerator.MEDIUM);
//			} else {
//				cell.setValue(MapeWorldGenerator.NUM_CELLS_CATEGORY, MapeWorldGenerator.HIGH);
//			}
				}
			}
			
			String newCellName = targetCellName.substring(0, 7) + "Cell" + (currentNumOfCells);
			ObjectInstance newCell = new MutableObjectInstance(domain.getObjectClass(MapeWorldGenerator.CLASS_CELL), newCellName);
			newCell.setValue(MapeWorldGenerator.PROVIDER, currentProvider);
			newCell.setValue(MapeWorldGenerator.TIER, currentTier);
			newCell.setValue(MapeWorldGenerator.GEO, currentGeo);
			newCell.setValue(MapeWorldGenerator.NUM_CELLS, currentNumOfCells);
			s.addObject(newCell);
		} else if (this.getName().equals(MapeWorldGenerator.MAPE_ACTION_DELETE)) {
			if (currentNumOfCells > MapeWorldGenerator.NUM_CELLS_LOWER_BOUND) { // check if there is more than the lower bound of the number of cells
				List<ObjectInstance> cells = s.getObjectsOfClass(MapeWorldGenerator.CLASS_CELL);
				
				for (ObjectInstance cell : cells) {
					if (cell.getName().substring(0, 6).equals(targetCell.getName().substring(0, 6))) {
						cell.setValue(MapeWorldGenerator.NUM_CELLS, currentNumOfCells - 1);

				// update numOfCellCategory (low/medium/high) accordingly
//				if (cell.getIntValForAttribute(MapeWorldGenerator.NUM_CELLS) <= MapeWorldGenerator.LOW_MEDIUM_THRESHOLD) {
//					cell.setValue(MapeWorldGenerator.NUM_CELLS_CATEGORY, MapeWorldGenerator.LOW);
//				} else if (cell.getIntValForAttribute(MapeWorldGenerator.NUM_CELLS) <= MapeWorldGenerator.MEDIUM_HIGH_THRESHOLD) {
//					cell.setValue(MapeWorldGenerator.NUM_CELLS_CATEGORY, MapeWorldGenerator.MEDIUM);
//				} else {
//					cell.setValue(MapeWorldGenerator.NUM_CELLS_CATEGORY, MapeWorldGenerator.HIGH);
//				}
					}
				}
				
//				s.removeObject(params[0]);
			}
		} else if (this.getName().equals(MapeWorldGenerator.MAPE_ACTION_MOVE)) {
			targetCell.setValue(MapeWorldGenerator.PROVIDER, params[1]);
			targetCell.setValue(MapeWorldGenerator.TIER, params[2]);
			targetCell.setValue(MapeWorldGenerator.GEO, params[3]);
		}
		
		return s;
	}

	@Override
	public boolean applicableInState(State s, GroundedAction groundedAction) {
		String[] params = groundedAction.getParametersAsString();
		ObjectInstance cell = s.getObject(params[0]);
		
		String currentProvider = cell.getStringValForAttribute(MapeWorldGenerator.PROVIDER);
		String currentTier = cell.getStringValForAttribute(MapeWorldGenerator.TIER);
		String currentGeo = cell.getStringValForAttribute(MapeWorldGenerator.GEO);
		int currentNumOfCells = cell.getIntValForAttribute(MapeWorldGenerator.NUM_CELLS);
		
		String newProvider = params[1];
		String newTier = params[2];
		String newGeo = params[3];
		
		if (this.getName().equals(MapeWorldGenerator.MAPE_ACTION_CREATE) && currentProvider.equals(newProvider) && currentTier.equals(newTier) && currentGeo.equals(newGeo)) {
			if (currentNumOfCells < MapeWorldGenerator.NUM_CELLS_UPPER_BOUND) {
				return true;
			} else {
				return false;
			}
		} else if (this.getName().equals(MapeWorldGenerator.MAPE_ACTION_DELETE) && currentProvider.equals(newProvider) && currentTier.equals(newTier) && currentGeo.equals(newGeo)) {
			if (currentNumOfCells > MapeWorldGenerator.NUM_CELLS_LOWER_BOUND) { // check if there is more than the lower bound of the number of cells
				return true;
			} else {
				return false;
			}
		} else if (this.getName().equals(MapeWorldGenerator.MAPE_ACTION_MOVE)) {
			if (!currentProvider.equals(newProvider) || !currentTier.equals(newTier) || !currentGeo.equals(newGeo)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false; // TODO: throw exception?
		}
	}

	@Override
	public boolean isPrimitive() {
		return true;
	}

	@Override
	public boolean isParameterized() {
		return true;
	}

	@Override
	public GroundedAction getAssociatedGroundedAction() {
		return new MapeGroundedAction(this, null, null, null, null);
	}

	@Override
	public List<GroundedAction> getAllApplicableGroundedActions(State s) {
		ArrayList<GroundedAction> ga = new ArrayList<GroundedAction>();
		
		List<ObjectInstance> cells = s.getObjectsOfClass(MapeWorldGenerator.CLASS_CELL);
		
		for (ObjectInstance cell : cells) {
			String cellName = cell.getName();

			String currentProvider = cell.getStringValForAttribute(MapeWorldGenerator.PROVIDER);
			String currentTier = cell.getStringValForAttribute(MapeWorldGenerator.TIER);
			String currentGeo = cell.getStringValForAttribute(MapeWorldGenerator.GEO);
//			int currentNumOfCells = cell.getIntValForAttribute(MapeWorldGenerator.NUM_CELLS);


			for (int i = 0; i < MapeWorldGenerator.PROVIDER_LIST.length; ++i) {
				MapeGroundedAction newGA = new MapeGroundedAction(this, cellName, MapeWorldGenerator.PROVIDER_LIST[i], currentTier, currentGeo);
				if (applicableInState(s, newGA)) {
					ga.add(newGA);
				}
			}

			for (int i = 0; i < MapeWorldGenerator.TIER_LIST.length; ++i) {
				MapeGroundedAction newGA = new MapeGroundedAction(this, cellName, currentProvider, MapeWorldGenerator.TIER_LIST[i], currentGeo);
				if (applicableInState(s, newGA)) {
					ga.add(newGA);
				}
			}

			for (int i = 0; i < MapeWorldGenerator.GEO_LIST.length; ++i) {
				MapeGroundedAction newGA = new MapeGroundedAction(this, cellName, currentProvider, currentTier, MapeWorldGenerator.GEO_LIST[i]);
				if (applicableInState(s, newGA)) {
					ga.add(newGA);
				}
			}

		}
		
		// For debugging purpose: List out all grounded actions
//		for (GroundedAction action : ga) {
//			int i = 0;
//			System.out.println("Current state: " + currentProvider + ", " + currentTier + ", " + currentGeo + " / GroundAction " + i + ": " + action);
//			++i;
//		}
		
//		System.out.println(s.getCompleteStateDescriptionWithUnsetAttributesAsNull());
		
		return ga;
	}

}

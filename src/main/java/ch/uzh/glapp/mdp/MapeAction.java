package mapeTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import burlap.oomdp.core.Domain;
import burlap.oomdp.core.TransitionProbability;
import burlap.oomdp.core.objects.MutableObjectInstance;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.Action;
import burlap.oomdp.singleagent.FullActionModel;
import burlap.oomdp.singleagent.GroundedAction;

public class MAPEAction extends Action implements FullActionModel {
	private MAPEGroundedAction ga;
	
	public MAPEAction(String actionName, Domain domain) {
		super(actionName, domain);
	}
	
	public MAPEAction(String actionName, Domain domain, MAPEGroundedAction ga) {
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
	protected State performActionHelper(State state, GroundedAction groundedAction) {
		String[] params = groundedAction.getParametersAsString();
		String targetCellName = params[0];
		ObjectInstance targetCell = state.getObject(targetCellName);

		debugOutput.log("performActionHelper() starts" +
				"performActionHelper(): state description before taking the action:\n" + state.getCompleteStateDescriptionWithUnsetAttributesAsNull());
		debugOutput.log("performActionHelper(): GroundedAction: " + groundedAction.actionName() + " " + targetCellName + " / Cell: " + targetCell);
		
		String currentProvider = targetCell.getStringValForAttribute(MAPEDomain.PROVIDER);
		String currentTier = targetCell.getStringValForAttribute(MAPEDomain.TIER);
		String currentGeo = targetCell.getStringValForAttribute(MAPEDomain.GEO);
		int currentNumOfCells = targetCell.getIntValForAttribute(MAPEDomain.NUM_CELLS);
		
		
		if (this.getName().equals(MAPEDomain.MAPE_ACTION_CREATE)) {
			// Action 1: Create a cell
			
			// Step 1: Get all the cell objects from the state object
			List<ObjectInstance> cells = state.getObjectsOfClass(MAPEDomain.CLASS_CELL);
			
			// Step 2: Calculate the new number of cells (increment the currentNumOfCell counter)
			currentNumOfCells++;
			
			// Step 3: Go through all the cells belonging to the same organ and update currentNumOfCells attribute
			// Cell name is in the format of "OrganX_CellY", substring(0, 6) extracts the "OrganX" part
			for (ObjectInstance cell : cells) {
				if (cell.getName().substring(0, 6).equals(targetCell.getName().substring(0, 6))) {
					cell.setValue(MAPEDomain.NUM_CELLS, currentNumOfCells);
			
			// update numOfCellCategory (low/medium/high) accordingly
//			if (cell.getIntValForAttribute(MAPEDomain.NUM_CELLS) <= MAPEDomain.LOW_MEDIUM_THRESHOLD) {
//				cell.setValue(MAPEDomain.NUM_CELLS_CATEGORY, MAPEDomain.LOW);
//			} else if (cell.getIntValForAttribute(MAPEDomain.NUM_CELLS) <= MAPEDomain.MEDIUM_HIGH_THRESHOLD) {
//				cell.setValue(MAPEDomain.NUM_CELLS_CATEGORY, MAPEDomain.MEDIUM);
//			} else {
//				cell.setValue(MAPEDomain.NUM_CELLS_CATEGORY, MAPEDomain.HIGH);
//			}
				}
			}
			
			// Step 4: Creating a new cell object and add it to the state object
			String uniqueID = UUID.randomUUID().toString();
			String newCellName = targetCellName.substring(0, 7) + "Cell_" + (uniqueID);
			ObjectInstance newCell = new MutableObjectInstance(domain.getObjectClass(MAPEDomain.CLASS_CELL), newCellName);
			newCell.setValue(MAPEDomain.PROVIDER, currentProvider);
			newCell.setValue(MAPEDomain.TIER, currentTier);
			newCell.setValue(MAPEDomain.GEO, currentGeo);
			newCell.setValue(MAPEDomain.NUM_CELLS, currentNumOfCells);
			state.addObject(newCell);
		} else if (this.getName().equals(MAPEDomain.MAPE_ACTION_DELETE)) {
			// Action 2: Delete a cell
			
			// Step 1: Check if the number of cells belonging to the same organ is greater than the lower bound (refer to MAPEDomain class. currently it is 1)
			if (currentNumOfCells > MAPEDomain.NUM_CELLS_LOWER_BOUND) { // check if there is more than the lower bound of the number of cells
				
				// Step 2: Get all the cell objects from the current state object
				List<ObjectInstance> cells = state.getObjectsOfClass(MAPEDomain.CLASS_CELL);
				
				// Step 3: Go through all cell objects and update currentNumOfCells attribute
				// Cell name is in the format of "OrganX_CellY", substring(0, 6) extracts the "OrganX" part
				for (ObjectInstance cell : cells) {
					if (cell.getName().substring(0, 6).equals(targetCell.getName().substring(0, 6))) {
						cell.setValue(MAPEDomain.NUM_CELLS, currentNumOfCells - 1);
						
				// update numOfCellCategory (low/medium/high) accordingly
//				if (cell.getIntValForAttribute(MAPEDomain.NUM_CELLS) <= MAPEDomain.LOW_MEDIUM_THRESHOLD) {
//					cell.setValue(MAPEDomain.NUM_CELLS_CATEGORY, MAPEDomain.LOW);
//				} else if (cell.getIntValForAttribute(MAPEDomain.NUM_CELLS) <= MAPEDomain.MEDIUM_HIGH_THRESHOLD) {
//					cell.setValue(MAPEDomain.NUM_CELLS_CATEGORY, MAPEDomain.MEDIUM);
//				} else {
//					cell.setValue(MAPEDomain.NUM_CELLS_CATEGORY, MAPEDomain.HIGH);
//				}
					}
				}
				
				// Step 4: Remove the cell object from the state object
				state.removeObject(targetCellName);
			}
		} else if (this.getName().equals(MAPEDomain.MAPE_ACTION_MOVE)) {
			// Action 3: Move
			
			targetCell.setValue(MAPEDomain.PROVIDER, params[1]);
			targetCell.setValue(MAPEDomain.TIER, params[2]);
			targetCell.setValue(MAPEDomain.GEO, params[3]);
		}
		
		debugOutput.log("performActionHelper(): state description after taking the action:\n" + state.getCompleteStateDescriptionWithUnsetAttributesAsNull());
		debugOutput.log("performActionHelper() ends");
		
		return state;
	}

	@Override
	public boolean applicableInState(State state, GroundedAction groundedAction) {
		String[] params = groundedAction.getParametersAsString();
		ObjectInstance cell = state.getObject(params[0]);
		
//		debugOutput.log("applicableInState(): current state description:\n" + state.getCompleteStateDescriptionWithUnsetAttributesAsNull());
//		debugOutput.log("applicableInState(): GroundedAction: " + groundedAction.actionName() + " " + params[0] + " / Cell: " + cell);
		
		if (cell == null) {
			debugOutput.log("applicableInState(): current state description:\n" + state.getCompleteStateDescriptionWithUnsetAttributesAsNull());
			debugOutput.log("applicableInState(): GroundedAction: " + groundedAction.actionName() + " " + params[0] + " / Cell: " + cell);
		
			debugOutput.log("applicableInState(): trying to action on a non-existing object");
			
			String stacktrace = "";
			for (int i = 0; i < Thread.currentThread().getStackTrace().length; ++i) {
				stacktrace += Thread.currentThread().getStackTrace()[i].toString() + "\n";
			}
			debugOutput.log(stacktrace);
			return false;
		}
		
		String currentProvider = cell.getStringValForAttribute(MAPEDomain.PROVIDER);
		String currentTier = cell.getStringValForAttribute(MAPEDomain.TIER);
		String currentGeo = cell.getStringValForAttribute(MAPEDomain.GEO);
		int currentNumOfCells = cell.getIntValForAttribute(MAPEDomain.NUM_CELLS);
		
		String newProvider = params[1];
		String newTier = params[2];
		String newGeo = params[3];
		
		if (this.getName().equals(MAPEDomain.MAPE_ACTION_CREATE) && currentProvider.equals(newProvider) && currentTier.equals(newTier) && currentGeo.equals(newGeo)) {
			if (currentNumOfCells < MAPEDomain.NUM_CELLS_UPPER_BOUND) {
				return true;
			} else {
				return false;
			}
		} else if (this.getName().equals(MAPEDomain.MAPE_ACTION_DELETE) && currentProvider.equals(newProvider) && currentTier.equals(newTier) && currentGeo.equals(newGeo)) {
			if (currentNumOfCells > MAPEDomain.NUM_CELLS_LOWER_BOUND) { // check if there is more than the lower bound of the number of cells
				return true;
			} else {
				return false;
			}
		} else if (this.getName().equals(MAPEDomain.MAPE_ACTION_MOVE)) {
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
		return new MAPEGroundedAction(this, null, null, null, null);
	}

	@Override
	public List<GroundedAction> getAllApplicableGroundedActions(State state) {
		ArrayList<GroundedAction> ga = new ArrayList<GroundedAction>();
		List<ObjectInstance> cells = state.getObjectsOfClass(MAPEDomain.CLASS_CELL);
		
		debugOutput.log("getAllApplicableGroundedActions() starts" +
				"getAllApplicableGroundedActions(): current state description\n" + state.getCompleteStateDescription());
		
		for (ObjectInstance cell : cells) {
			String cellName = cell.getName();

			String currentProvider = cell.getStringValForAttribute(MAPEDomain.PROVIDER);
			String currentTier = cell.getStringValForAttribute(MAPEDomain.TIER);
			String currentGeo = cell.getStringValForAttribute(MAPEDomain.GEO);
//			int currentNumOfCells = cell.getIntValForAttribute(MAPEDomain.NUM_CELLS);


			for (int i = 0; i < MAPEDomain.PROVIDER_LIST.length; ++i) {
				MAPEGroundedAction newGA = new MAPEGroundedAction(this, cellName, MAPEDomain.PROVIDER_LIST[i], currentTier, currentGeo);
				if (applicableInState(state, newGA)) {
					ga.add(newGA);
				}
			}

			for (int i = 0; i < MAPEDomain.TIER_LIST.length; ++i) {
				MAPEGroundedAction newGA = new MAPEGroundedAction(this, cellName, currentProvider, MAPEDomain.TIER_LIST[i], currentGeo);
				if (applicableInState(state, newGA)) {
					ga.add(newGA);
				}
			}

			for (int i = 0; i < MAPEDomain.GEO_LIST.length; ++i) {
				MAPEGroundedAction newGA = new MAPEGroundedAction(this, cellName, currentProvider, currentTier, MAPEDomain.GEO_LIST[i]);
				if (applicableInState(state, newGA)) {
					ga.add(newGA);
				}
			}

		}
		
		debugOutput.log("getAllApplicableGroundedActions() ends");
		
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

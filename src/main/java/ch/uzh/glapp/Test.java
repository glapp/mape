//package ch.uzh.glapp;
//
//import burlap.oomdp.core.objects.MutableObjectInstance;
//import burlap.oomdp.core.objects.ObjectInstance;
//import burlap.oomdp.core.states.MutableState;
//import burlap.oomdp.core.states.State;
//import ch.uzh.glapp.model.Cell;
//
//import java.util.HashMap;
//import java.util.List;
//
//import static ch.uzh.glapp.mdp.MapeWorldDomain.*;
//
//
//public class Test {
//
//	public static void main(String[] args){
//
//		State s = new MutableState();
//		List<Cell> cells = new SailsRetriever().getCellInfo();
//
//		HashMap<String, Integer> numOfCells = new HashMap<String, Integer>();
//
//		for (Cell cell : cells) {
//			String organID = cell.getOrganId().getId();
//			if (!cell.getIsProxy()) {
//				if (!numOfCells.containsKey(organID)) {
//					numOfCells.put(organID, 1);
//				} else {
//					numOfCells.put(organID, numOfCells.get(organID) + 1);
//				}
//			}
//		}
//
//		for (Cell cell : cells) {
//			String organID = cell.getOrganId().getId();
//			ObjectInstance cellObject = new MutableObjectInstance(domain.getObjectClass(CLASS_CELL), "Organ_" + organID + "_Cell_" + cell.getId());
//			cellObject.setValue(PROVIDER, cell.getHost().getLabels().getProvider());
//			cellObject.setValue(TIER, cell.getHost().getLabels().getTier());
//			cellObject.setValue(GEO, cell.getHost().getLabels().getRegion());
//			cellObject.setValue(NUM_CELLS, numOfCells.get(organID));
//			s.addObject(cellObject);
//		}
//
//	}
//
//
//	}
//
//}

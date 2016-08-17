package ch.uzh.glapp.mdp;

import ch.uzh.glapp.model.sails.cellinfo.Cell;
import ch.uzh.glapp.model.sails.hostinfo.Host;
import ch.uzh.glapp.model.sails.ruleinfo.Organ;
import ch.uzh.glapp.model.sails.ruleinfo.Rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static ch.uzh.glapp.mdp.MapeWorld.*;

public class MdpUtils {

	private int countCell = 0;

	public int countCellsInOrgan(List<Cell> cells, String organID) {
		for (Cell cell : cells) {
			if ((cell.getOrganId().getId()).equals(organID)){
				countCell++;
			}
		}
//		System.out.println("CellDataObject in Organ " + organID + ": " + countCell);
		return countCell;
	}

	public HashMap<String, Integer> countCellsInAllOrgans (List<Cell> cells) {
		HashMap<String, Integer> numOfCells = new HashMap<String, Integer>();
		for (Cell cell : cells) {
//			System.out.println(cell.getOrganId().getId());
			String organID = cell.getOrganId().getId();
			if (!cell.getIsProxy()) {
				if (!numOfCells.containsKey(organID)) {
					numOfCells.put(organID, 1);
				} else {
					numOfCells.put(organID, numOfCells.get(organID) + 1);
				}
			}
		}
//		for (String name: numOfCells.keySet()){
//
//			System.out.println(name + ": " + numOfCells.get(name));
//		}
		return numOfCells;
	}
}

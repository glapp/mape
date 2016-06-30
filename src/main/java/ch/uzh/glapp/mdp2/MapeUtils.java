package ch.uzh.glapp.mdp2;

import ch.uzh.glapp.model.cellinfo.Cell;

import java.util.HashMap;
import java.util.List;


class MapeUtils {

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
	
	/**
	 * Function to check if a host from given cloud provider, region and tier is available
	 * @param provider
	 * @param region
	 * @param tier
	 * @return true if the host is available and false otherwise
	 */
	public boolean isAvailable(String provider, String region, String tier) {
		
		return false;
	}
}

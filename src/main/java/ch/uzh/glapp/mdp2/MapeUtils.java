package ch.uzh.glapp.mdp2;

import ch.uzh.glapp.model.Cell;

import java.util.List;


class MapeUtils {

	private int countCell = 0;
	public int countCellsInOrgan (List<Cell> cells, String organID) {
		for (Cell cell : cells) {
			if ((cell.getOrganId().getId()).equals(organID)){
				countCell++;
			}
		}
		System.out.println("Cells in Organ " + organID + ": " + countCell);
		return countCell;
	}

}

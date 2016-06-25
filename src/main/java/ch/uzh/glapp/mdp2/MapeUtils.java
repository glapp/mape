package ch.uzh.glapp.mdp2;

import ch.uzh.glapp.model.Cell;

import java.util.List;


class MapeUtils {

	private int countCell = 0;
	int countCellsInOrgan(List<Cell> cells, String organID) {
		for (Cell cell : cells) {
			if ((cell.getOrganId().getId()).equals(organID)){
				countCell++;
			}
		}
		System.out.println("Cells in Organ " + organID + ": " + countCell);
		return countCell;
	}

	String isProxyOfCell (List<Cell> cells, String organID, String cellID) {

		String proxyID = "";
		for (Cell cell : cells) {
			if ((cell.getOrganId().getId()).equals(organID)){
			}
		}

		return proxyID;
	}
}

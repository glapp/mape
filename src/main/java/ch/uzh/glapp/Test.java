package ch.uzh.glapp;

import ch.uzh.glapp.model.Cell;
import ch.uzh.glapp.model.MapeCellObject;

import java.util.List;


public class Test {

	public static void main(String[] args){

		List<Cell> cells = new SailsRetriever().getCellInfo();
		for (Cell cell : cells) {
			System.out.println("------------- NEW CELL -------------");
			System.out.println(cell.getHost().getLabels().getProvider());
			System.out.println(cell.getHost().getLabels().getRegion());
			System.out.println(cell.getHost().getLabels().getTier());
		}


	}

}

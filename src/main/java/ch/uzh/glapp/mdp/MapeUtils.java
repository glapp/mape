package ch.uzh.glapp.mdp;

import ch.uzh.glapp.SailsRetriever;
import ch.uzh.glapp.model.sails.cellinfo.Cell;
import ch.uzh.glapp.model.sails.hostinfo.Host;

import java.util.HashMap;
import java.util.List;

import static ch.uzh.glapp.mdp.MapeWorld.*;

public class MapeUtils {

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
	 * Function to check if the new tier is higher than the old tier
	 * @param oldTier is the old tier of the host
	 * @param newTier is the new tier of the host
	 * @return true if the new tier is higher than the old tier and false otherwise.
	 */
	public static boolean isNewTierHigher(String oldTier, String newTier) {
		if (oldTier.equals(TIER3) && (newTier.equals(TIER2) || newTier.equals(TIER1))) {
			return true;
		} else if (oldTier.equals(TIER2) && newTier.equals(TIER1)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Function to check if a host from given cloud provider, region and tier is available
	 * @param provider
	 * @param region
	 * @param tier
	 * @return true if the host is available and false otherwise
	 */
	public static boolean isHostAvailable(String provider, String region, String tier) {
		SailsRetriever sa = new SailsRetriever();
		List<Host> hosts = sa.getHostInfo();
		
		for (Host host : hosts) {
			if (host.getLabels().getProvider().equals(provider) && host.getLabels().getRegion().equals(region) && host.getLabels().getTier().equals(tier)){
				return true;
			}
		}
		
		return false;
	}
	
	public double healthiness() {
		
		return 0;
	}
}

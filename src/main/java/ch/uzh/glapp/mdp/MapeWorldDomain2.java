package ch.uzh.glapp.mdp;

import burlap.oomdp.auxiliary.DomainGenerator;
import burlap.oomdp.core.Attribute;
import burlap.oomdp.core.Attribute.AttributeType;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.ObjectClass;
import burlap.oomdp.core.objects.MutableObjectInstance;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.MutableState;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.SADomain;
import ch.uzh.glapp.SailsRetriever;
import ch.uzh.glapp.model.Cell;

import java.util.HashMap;
import java.util.List;


public class MapeWorldDomain2 implements DomainGenerator {

	public static final String TIER = "tier";
	public static final String TIER1 = "Tier 1";
	public static final String TIER2 = "Tier 2";
	public static final String TIER3 = "Tier 3";
	public static final String[] TIER_LIST = {TIER1, TIER2, TIER3};

	public static final int NUM_CELLS_LOWER_BOUND = 1; // the lower bound of the number of cells is 1 (i.e. minimum 1 cell)
	public static final int NUM_CELLS_UPPER_BOUND = 3; // the upper bound of the number of cells
	
	public static final String NUM_CELLS_CATEGORY = "numberOfCellsCategory";
	public static final String HIGH = "High";
	public static final String MEDIUM = "Medium";
	public static final String LOW = "Low";
	public static final String[] NUM_CELLS_LIST = {HIGH, MEDIUM, LOW};

	public static final String GEO = "geo";
	public static final String NA = "North America";
	public static final String EU = "Europe";
	public static final String ASIA = "Asia";
	public static final String[] GEO_LIST = {NA, EU, ASIA};

	public static final String CLASS_CELL = "cell";
	
	public static final String MAPE_ACTION_CREATE = "Create";
	public static final String MAPE_ACTION_DELETE = "Delete";
	public static final String MAPE_ACTION_MOVE = "Move";

//	@Override
	public Domain generateDomain() {
		SADomain domain = new SADomain();

		Attribute tier = new Attribute(domain, TIER, AttributeType.STRING);
		tier.setDiscValues(TIER_LIST);
		
		Attribute geo = new Attribute(domain, GEO, AttributeType.STRING);
		geo.setDiscValues(GEO_LIST);

		// ObjectClass: cell
		ObjectClass cellClass = new ObjectClass(domain, CLASS_CELL);
		cellClass.addAttribute(tier);
		cellClass.addAttribute(geo);

		new MapeAction(MAPE_ACTION_CREATE, domain);
		new MapeAction(MAPE_ACTION_DELETE, domain);
		new MapeAction(MAPE_ACTION_MOVE, domain);
		
		return domain;
	}
	
	public static State setInitialState(Domain domain) {
		State s = new MutableState();
		
		s.addObject(new MutableObjectInstance(domain.getObjectClass(CLASS_CELL), "Organ1_Cell_1"));
		s.addObject(new MutableObjectInstance(domain.getObjectClass(CLASS_CELL), "Organ2_Cell_1"));

		return s;
	}
	
	public static State setInitialStateRealWorld(Domain domain) {
		State s = new MutableState();
		List<Cell> cells = new SailsRetriever().getCellInfo();
		
		HashMap<String, Integer> numOfCells = new HashMap<String, Integer>();
		
		for (Cell cell : cells) {
			String organID = cell.getOrganId().getId();
			if (!cell.getIsProxy()) {
				if (!numOfCells.containsKey(organID)) {
					numOfCells.put(organID, 1);
				} else {
					numOfCells.put(organID, numOfCells.get(organID) + 1);
				}
			}
		}
		
		for (Cell cell : cells) {
			String organID = cell.getOrganId().getId();
			ObjectInstance cellObject = new MutableObjectInstance(domain.getObjectClass(CLASS_CELL), "Organ_" + organID + "_Cell_" + cell.getId());
			cellObject.setValue(TIER, cell.getHost().getLabels().getTier());
			cellObject.setValue(GEO, cell.getHost().getLabels().getRegion());
			s.addObject(cellObject);
		}
		
		return s;
	}
}
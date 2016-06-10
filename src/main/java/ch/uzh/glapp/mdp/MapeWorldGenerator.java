package ch.uzh.glapp.mdp;

import burlap.oomdp.auxiliary.DomainGenerator;
import burlap.oomdp.core.*;
import burlap.oomdp.core.Attribute.AttributeType;
import burlap.oomdp.core.objects.MutableObjectInstance;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.MutableState;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.*;
import burlap.oomdp.singleagent.common.SimpleAction;
import burlap.oomdp.singleagent.environment.SimulatedEnvironment;
import burlap.oomdp.singleagent.explorer.TerminalExplorer;
import burlap.oomdp.singleagent.explorer.VisualExplorer;
import burlap.oomdp.visualizer.ObjectPainter;
import burlap.oomdp.visualizer.StateRenderLayer;
import burlap.oomdp.visualizer.StaticPainter;
import burlap.oomdp.visualizer.Visualizer;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;


public class MapeWorldGenerator implements DomainGenerator {
	public static final String PROVIDER = "provider";
	public static final String AWS = "Amazon Web Services";
	public static final String DO = "Digital Ocean";
	public static final String AZURE = "Microsoft Azure";
	public static final String GOOGLE = "Google Compute Engine";
	public static final String[] PROVIDER_LIST = {AWS, DO, AZURE, GOOGLE};
//	public static final String DEFAULT_PROVIDER = AWS;
	
	public static final String TIER = "tier";
	public static final String TIER1 = "Tier 1";
	public static final String TIER2 = "Tier 2";
	public static final String TIER3 = "Tier 3";
	public static final String[] TIER_LIST = {TIER1, TIER2, TIER3};
//	public static final String DEFAULT_TIER = TIER2;
	
	public static final int MEDIUM_HIGH_THRESHOLD = 10;
	public static final int LOW_MEDIUM_THRESHOLD = 5;
	
	public static final String NUM_CELLS = "numberOfCellsInOrgan";
	public static final int NUM_CELLS_LOWER_BOUND = 1; // the lower bound of the number of cells is 1 (i.e. minimum 1 cell)
	public static final int NUM_CELLS_UPPER_BOUND = 3; // the upper bound of the number of cells
	
	public static final String NUM_CELLS_CATEGORY = "numberOfCellsCategory";
	public static final String HIGH = "High";
	public static final String MEDIUM = "Medium";
	public static final String LOW = "Low";
	public static final String[] NUM_CELLS_LIST = {HIGH, MEDIUM, LOW};
//	public static final String DEFAULT_CATEGORY = LOW;
	
	public static final String GEO = "geo";
	public static final String NA = "North America";
	public static final String EU = "Europe";
	public static final String ASIA = "Asia";
	public static final String[] GEO_LIST = {NA, EU, ASIA};
//	public static final String DEFAULT_GEO = EU;
	
	public static final String CLASS_CELL = "cell";
	
	public static final String MAPE_ACTION_CREATE = "Create";
	public static final String MAPE_ACTION_DELETE = "Delete";
	public static final String MAPE_ACTION_MOVE = "Move";
//	public static final String MAPE_ACTION_MOVE_PROVIDER = "Move Provider";
//	public static final String MAPE_ACTION_MOVE_TIER = "Move Tier";
//	public static final String MAPE_ACTION_MOVE_GEO = "Move Geo";
	public static final String[] MAPE_ACTIONS = {MAPE_ACTION_CREATE, MAPE_ACTION_DELETE, MAPE_ACTION_MOVE};
//	public static final String[] MAPE_ACTIONS = {MAPE_ACTION_CREATE, MAPE_ACTION_DELETE, MAPE_ACTION_MOVE, MAPE_ACTION_MOVE_PROVIDER, MAPE_ACTION_MOVE_TIER, MAPE_ACTION_MOVE_GEO};
	
//	@Override
	public Domain generateDomain() {
		SADomain domain = new SADomain();
		
		// Attribute: provider, tier, geo, numOfCells, numOfCellsCategory
		Attribute provider = new Attribute(domain, PROVIDER, AttributeType.STRING);
		provider.setDiscValues(PROVIDER_LIST);
		
		Attribute tier = new Attribute(domain, TIER, AttributeType.STRING);
		tier.setDiscValues(TIER_LIST);
		
		Attribute geo = new Attribute(domain, GEO, AttributeType.STRING);
		geo.setDiscValues(GEO_LIST);
		
		Attribute numberOfCells = new Attribute(domain, NUM_CELLS, AttributeType.INT);
		numberOfCells.setLims(NUM_CELLS_LOWER_BOUND, NUM_CELLS_UPPER_BOUND);
		
//		Attribute numberOfCellsCategory = new Attribute(domain, NUM_CELLS_CATEGORY, AttributeType.STRING);
//		numberOfCellsCategory.setDiscValues(NUM_CELLS_LIST);
		
		
		// ObjectClass: cell
		ObjectClass cellClass = new ObjectClass(domain, CLASS_CELL);
		cellClass.addAttribute(provider);
		cellClass.addAttribute(tier);
		cellClass.addAttribute(geo);
		cellClass.addAttribute(numberOfCells);
//		cellClass.addAttribute(numberOfCellsCategory);
		
		new MapeAction(MAPE_ACTION_CREATE, domain);
		new MapeAction(MAPE_ACTION_DELETE, domain);
		new MapeAction(MAPE_ACTION_MOVE, domain);
		
		return domain;
	}
	
	public static State setInitialState(Domain domain) {
		State s = new MutableState();
		
		s.addObject(new MutableObjectInstance(domain.getObjectClass(CLASS_CELL), "Organ1_Cell_1"));
		s.addObject(new MutableObjectInstance(domain.getObjectClass(CLASS_CELL), "Organ2_Cell_1"));
//		s.addObject(new MutableObjectInstance(domain.getObjectClass(CLASS_CELL), "Organ2_Cell2"));
		
		return s;
	}
}
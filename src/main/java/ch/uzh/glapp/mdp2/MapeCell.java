package ch.uzh.glapp.mdp2;

import static ch.uzh.glapp.mdp2.MapeWorld.CLASS_CELL;
import static ch.uzh.glapp.mdp2.MapeWorld.VAR_CELLS;
import static ch.uzh.glapp.mdp2.MapeWorld.VAR_PROVIDER;
import static ch.uzh.glapp.mdp2.MapeWorld.VAR_PROXY_PROVIDER;
import static ch.uzh.glapp.mdp2.MapeWorld.VAR_PROXY_REGION;
import static ch.uzh.glapp.mdp2.MapeWorld.VAR_REGION;
import static ch.uzh.glapp.mdp2.MapeWorld.VAR_TIER;

import java.util.Arrays;
import java.util.List;

import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.state.MutableState;
import burlap.mdp.core.state.State;
import burlap.mdp.core.state.StateUtilities;
import burlap.mdp.core.state.UnknownKeyException;

public class MapeCell implements ObjectInstance, MutableState {
	
	private String cellName;
	private String provider;
	private String region;
	private String tier;
	private int cells;
//	private String proxy_provider;
//	private String proxy_region;
	
	private final static List<Object> keys = Arrays.asList(VAR_PROVIDER, VAR_REGION, VAR_TIER, VAR_CELLS, VAR_PROXY_PROVIDER, VAR_PROXY_REGION);

	public MapeCell(String cellName) {
		this.cellName = cellName;
	}

	public MapeCell(String cellName, String provider, String region, String tier, int cells/*, String proxy_provider, String proxy_region*/) {
		this.cellName = cellName;
		this.provider = provider;
		this.region = region;
		this.cells = cells;
		this.tier = tier;
//		this.proxy_provider = proxy_provider;
//		this.proxy_region = proxy_region;
	}

	@Override
	public State copy() {
		return new MapeCell(this.cellName, this.provider, this.region, this.tier, this.cells/*, this.proxy_provider, this.proxy_region*/);
	}

	@Override
	public Object get(Object variableKey) {
		if(variableKey.equals(VAR_PROVIDER)){
			return provider;
		}
		else if(variableKey.equals(VAR_REGION)){
			return region;
		}
		else if(variableKey.equals(VAR_TIER)){
			return tier;
		}
		else if(variableKey.equals(VAR_CELLS)){
			return cells;
//		}
//		else if(variableKey.equals(VAR_PROXY_PROVIDER)){
//			return proxy_provider;
//		}
//		else if(variableKey.equals(VAR_PROXY_REGION)){
//			return proxy_region;
		} else {
			throw new UnknownKeyException(variableKey);
		}
	}

	@Override
	public List<Object> variableKeys() {
		return keys;
	}

	@Override
	public MutableState set(Object variableKey, Object value) {
		if(variableKey.equals(VAR_PROVIDER)){
			provider = (String)value;
		}
		else if(variableKey.equals(VAR_REGION)){
			region = (String)value;
		}
		else if(variableKey.equals(VAR_TIER)){
			tier = (String)value;
		}
		else if(variableKey.equals(VAR_CELLS)){
			cells  = StateUtilities.stringOrNumber(value).intValue();
//		}
//		else if(variableKey.equals(VAR_PROXY_PROVIDER)){
//			proxy_provider = (String)value;
//		}
//		else if(variableKey.equals(VAR_PROXY_REGION)){
//			proxy_region  = (String)value;
		} else {
			throw new UnknownKeyException(variableKey);
		}
		return this;
	}

	@Override
	public String className() {
		return CLASS_CELL;
	}

	@Override
	public ObjectInstance copyWithName(String arg0) {
		return new MapeCell(arg0, this.provider, this.region, this.tier, this.cells/*, this.proxy_provider, this.proxy_region*/);
	}

	@Override
	public String name() {
		return cellName;
	}

}

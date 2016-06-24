package ch.uzh.glapp.mdp2;

import burlap.mdp.core.state.State;
import burlap.mdp.core.state.StateUtilities;
import burlap.mdp.core.state.UnknownKeyException;
import burlap.mdp.core.state.annotations.DeepCopyState;

import java.util.Arrays;
import java.util.List;

import static ch.uzh.glapp.mdp2.MapeWorld.*;


@DeepCopyState
public class MapeState implements State {

	public String provider;
	public String region;
	public String tier;
	public int cells;
	public String proxy_provider;
	public String proxy_region;

	private final static List<Object> keys = Arrays.asList(VAR_PROVIDER, VAR_REGION, VAR_TIER, VAR_CELLS,
			VAR_PROXY_PROVIDER, VAR_PROXY_REGION);

	public MapeState(){}

	public MapeState(String provider, String region, String tier, int cells, String proxy_provider, String proxy_region) {
		this.provider = provider;
		this.region = region;
		this.tier = tier;
		this.cells = cells;
		this.proxy_provider = proxy_provider;
		this.proxy_region = proxy_region;
	}

	@Override
	public List<Object> variableKeys() {
		return keys;
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
		}
		else if(variableKey.equals(VAR_PROXY_PROVIDER)){
			return proxy_provider;
		}
		else if(variableKey.equals(VAR_PROXY_REGION)){
			return proxy_region;
		}
		throw new UnknownKeyException(variableKey);
	}

	@Override
	public MapeState copy() {
		return new MapeState(provider, region, tier, cells, proxy_provider, proxy_region);
	}

	@Override
	public String toString() {
		return StateUtilities.stateToString(this);
	}
}

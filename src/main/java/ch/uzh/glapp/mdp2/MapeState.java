package ch.uzh.glapp.mdp2;

import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.state.MutableState;
import burlap.mdp.core.state.State;
import burlap.mdp.core.state.StateUtilities;
import burlap.mdp.core.state.UnknownKeyException;
import burlap.mdp.core.state.annotations.DeepCopyState;

import java.util.Arrays;
import java.util.List;

import static ch.uzh.glapp.mdp2.MapeWorld.*;


@DeepCopyState
public class MapeState implements MutableState {

	public String violated_policy;
	public String provider;
	public String region;
	public String tier;
	public int cells;
	public String proxy_provider;
	public String proxy_region;

	private final static List<Object> keys = Arrays.asList(VAR_VIOLATED_POLICY, VAR_PROVIDER, VAR_REGION, VAR_TIER,
			VAR_CELLS, VAR_PROXY_PROVIDER, VAR_PROXY_REGION);

	public MapeState(){}

	public MapeState(String violated_policy, String provider, String region, String tier,
	                 int cells, String proxy_provider, String proxy_region) {
		this.violated_policy = violated_policy;
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
	public MutableState set(Object variableKey, Object value) {
		if(variableKey.equals(VAR_VIOLATED_POLICY)){
			this.violated_policy = (String)value;
		}
		else if(variableKey.equals(VAR_PROVIDER)){
			this.provider = (String)value;
		}
		else if(variableKey.equals(VAR_REGION)){
			this.region = (String)value;
		}
		else if(variableKey.equals(VAR_TIER)){
			this.tier = (String)value;
		}
		else if(variableKey.equals(VAR_CELLS)){
			this.cells = StateUtilities.stringOrNumber(value).intValue();
		}
		else if(variableKey.equals(VAR_PROXY_PROVIDER)){
			this.proxy_provider = (String)value;
		}
		else if(variableKey.equals(VAR_PROXY_REGION)){
			this.proxy_region = (String)value;
		}
		else{
			throw new UnknownKeyException(variableKey);
		}
		return this;
	}

	@Override
	public Object get(Object variableKey) {
		if(variableKey.equals(VAR_VIOLATED_POLICY)){
			return violated_policy;
		}
		else if(variableKey.equals(VAR_PROVIDER)){
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
		return new MapeState(violated_policy, provider, region, tier, cells, proxy_provider, proxy_region);
	}

	@Override
	public String toString() {
		return StateUtilities.stateToString(this);
	}

}

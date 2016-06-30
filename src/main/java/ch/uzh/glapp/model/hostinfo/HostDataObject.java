package ch.uzh.glapp.model.hostinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class HostDataObject {

	private List<Host> hosts = new ArrayList<Host>();
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 *
	 * @return
	 * The hosts
	 */
	public List<Host> getHosts() {
		return hosts;
	}

	/**
	 *
	 * @param hosts
	 * The hosts
	 */
	public void setHosts(List<Host> hosts) {
		this.hosts = hosts;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
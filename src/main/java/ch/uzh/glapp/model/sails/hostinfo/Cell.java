package ch.uzh.glapp.model.sails.hostinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Cell {

	private String organId;
	private List<Object> environment = new ArrayList<Object>();
	private Boolean isProxy;
	private String createdAt;
	private String updatedAt;
	private String host;
	private String containerId;
	private String id;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 *
	 * @return
	 * The organId
	 */
	public String getOrganId() {
		return organId;
	}

	/**
	 *
	 * @param organId
	 * The organ_id
	 */
	public void setOrganId(String organId) {
		this.organId = organId;
	}

	/**
	 *
	 * @return
	 * The environment
	 */
	public List<Object> getEnvironment() {
		return environment;
	}

	/**
	 *
	 * @param environment
	 * The environment
	 */
	public void setEnvironment(List<Object> environment) {
		this.environment = environment;
	}

	/**
	 *
	 * @return
	 * The isProxy
	 */
	public Boolean getIsProxy() {
		return isProxy;
	}

	/**
	 *
	 * @param isProxy
	 * The isProxy
	 */
	public void setIsProxy(Boolean isProxy) {
		this.isProxy = isProxy;
	}

	/**
	 *
	 * @return
	 * The createdAt
	 */
	public String getCreatedAt() {
		return createdAt;
	}

	/**
	 *
	 * @param createdAt
	 * The createdAt
	 */
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 *
	 * @return
	 * The updatedAt
	 */
	public String getUpdatedAt() {
		return updatedAt;
	}

	/**
	 *
	 * @param updatedAt
	 * The updatedAt
	 */
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 *
	 * @return
	 * The host
	 */
	public String getHost() {
		return host;
	}

	/**
	 *
	 * @param host
	 * The host
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 *
	 * @return
	 * The containerId
	 */
	public String getContainerId() {
		return containerId;
	}

	/**
	 *
	 * @param containerId
	 * The container_id
	 */
	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	/**
	 *
	 * @return
	 * The id
	 */
	public String getId() {
		return id;
	}

	/**
	 *
	 * @param id
	 * The id
	 */
	public void setId(String id) {
		this.id = id;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
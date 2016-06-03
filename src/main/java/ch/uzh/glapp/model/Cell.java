package ch.uzh.glapp.model;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Cell {

	@SerializedName("organ_id")
	@Expose
	private OrganId organId;
	@SerializedName("host")
	@Expose
	private Host host;
	@SerializedName("environment")
	@Expose
	private List<Object> environment = new ArrayList<Object>();
	@SerializedName("isProxy")
	@Expose
	private Boolean isProxy;
	@SerializedName("createdAt")
	@Expose
	private String createdAt;
	@SerializedName("updatedAt")
	@Expose
	private String updatedAt;
	@SerializedName("container_id")
	@Expose
	private String containerId;
	@SerializedName("id")
	@Expose
	private String id;
	@SerializedName("published_port")
	@Expose
	private String publishedPort;

	/**
	 *
	 * @return
	 * The organId
	 */
	public OrganId getOrganId() {
		return organId;
	}

	/**
	 *
	 * @param organId
	 * The organ_id
	 */
	public void setOrganId(OrganId organId) {
		this.organId = organId;
	}

	/**
	 *
	 * @return
	 * The host
	 */
	public Host getHost() {
		return host;
	}

	/**
	 *
	 * @param host
	 * The host
	 */
	public void setHost(Host host) {
		this.host = host;
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

	/**
	 *
	 * @return
	 * The publishedPort
	 */
	public String getPublishedPort() {
		return publishedPort;
	}

	/**
	 *
	 * @param publishedPort
	 * The published_port
	 */
	public void setPublishedPort(String publishedPort) {
		this.publishedPort = publishedPort;
	}

}
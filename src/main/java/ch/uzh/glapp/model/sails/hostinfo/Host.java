package ch.uzh.glapp.model.sails.hostinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Host {

	private List<Cell> cells = new ArrayList<Cell>();
	private String name;
	private String ip;
	private String iD;
	private String status;
	private String containers;
	private String reservedCPUs;
	private String reservedMemory;
	private Labels labels;
	private String updatedAt;
	private String serverVersion;
	private String createdAt;
	private String id;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 *
	 * @return
	 * The cells
	 */
	public List<Cell> getCells() {
		return cells;
	}

	/**
	 *
	 * @param cells
	 * The cells
	 */
	public void setCells(List<Cell> cells) {
		this.cells = cells;
	}

	/**
	 *
	 * @return
	 * The name
	 */
	public String getName() {
		return name;
	}

	/**
	 *
	 * @param name
	 * The name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 *
	 * @return
	 * The ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 *
	 * @param ip
	 * The ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 *
	 * @return
	 * The iD
	 */
	public String getID() {
		return iD;
	}

	/**
	 *
	 * @param iD
	 * The iD
	 */
	public void setID(String iD) {
		this.iD = iD;
	}

	/**
	 *
	 * @return
	 * The status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 *
	 * @param status
	 * The status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 *
	 * @return
	 * The containers
	 */
	public String getContainers() {
		return containers;
	}

	/**
	 *
	 * @param containers
	 * The containers
	 */
	public void setContainers(String containers) {
		this.containers = containers;
	}

	/**
	 *
	 * @return
	 * The reservedCPUs
	 */
	public String getReservedCPUs() {
		return reservedCPUs;
	}

	/**
	 *
	 * @param reservedCPUs
	 * The reservedCPUs
	 */
	public void setReservedCPUs(String reservedCPUs) {
		this.reservedCPUs = reservedCPUs;
	}

	/**
	 *
	 * @return
	 * The reservedMemory
	 */
	public String getReservedMemory() {
		return reservedMemory;
	}

	/**
	 *
	 * @param reservedMemory
	 * The reservedMemory
	 */
	public void setReservedMemory(String reservedMemory) {
		this.reservedMemory = reservedMemory;
	}

	/**
	 *
	 * @return
	 * The labels
	 */
	public Labels getLabels() {
		return labels;
	}

	/**
	 *
	 * @param labels
	 * The labels
	 */
	public void setLabels(Labels labels) {
		this.labels = labels;
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
	 * The serverVersion
	 */
	public String getServerVersion() {
		return serverVersion;
	}

	/**
	 *
	 * @param serverVersion
	 * The serverVersion
	 */
	public void setServerVersion(String serverVersion) {
		this.serverVersion = serverVersion;
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
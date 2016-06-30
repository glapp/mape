package ch.uzh.glapp.model.cellinfo;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Host {

	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("ip")
	@Expose
	private String ip;
	@SerializedName("iD")
	@Expose
	private String iD;
	@SerializedName("status")
	@Expose
	private String status;
	@SerializedName("containers")
	@Expose
	private String containers;
	@SerializedName("reservedCPUs")
	@Expose
	private String reservedCPUs;
	@SerializedName("reservedMemory")
	@Expose
	private String reservedMemory;
	@SerializedName("labels")
	@Expose
	private Labels labels;
	@SerializedName("updatedAt")
	@Expose
	private String updatedAt;
	@SerializedName("serverVersion")
	@Expose
	private String serverVersion;
	@SerializedName("createdAt")
	@Expose
	private String createdAt;
	@SerializedName("id")
	@Expose
	private String id;

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

}
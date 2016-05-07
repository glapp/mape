package ch.uzh.glapp.model;

import java.util.ArrayList;
import java.util.List;
//import javax.annotation.Generated;
//
//@Generated("org.jsonschema2pojo")
public class App {

	private List<Organ> organs = new ArrayList<Organ>();
	private String owner;
	private String name;
	private String gitUrl;
	private String status;
	private String createdAt;
	private String updatedAt;
	private String networkId;
	private String id;

	/**
	 *
	 * @return
	 * The organs
	 */
	public List<Organ> getOrgans() {
		return organs;
	}

	/**
	 *
	 * @param organs
	 * The organs
	 */
	public void setOrgans(List<Organ> organs) {
		this.organs = organs;
	}

	/**
	 *
	 * @return
	 * The owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 *
	 * @param owner
	 * The owner
	 */
	public void setOwner(String owner) {
		this.owner = owner;
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
	 * The gitUrl
	 */
	public String getGitUrl() {
		return gitUrl;
	}

	/**
	 *
	 * @param gitUrl
	 * The gitUrl
	 */
	public void setGitUrl(String gitUrl) {
		this.gitUrl = gitUrl;
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
	 * The networkId
	 */
	public String getNetworkId() {
		return networkId;
	}

	/**
	 *
	 * @param networkId
	 * The networkId
	 */
	public void setNetworkId(String networkId) {
		this.networkId = networkId;
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
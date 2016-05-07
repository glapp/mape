package ch.uzh.glapp.model;

import java.util.ArrayList;
import java.util.List;
//import javax.annotation.Generated;
//
//@Generated("org.jsonschema2pojo")
public class Organ {

	private String image;
	private List<Object> ports = new ArrayList<Object>();
	private List<String> environment = new ArrayList<String>();
	private String originalName;
	private String name;
	private String applicationId;
	private List<Object> labels = new ArrayList<Object>();
	private List<String> expose = new ArrayList<String>();
	private List<Object> volumes = new ArrayList<Object>();
	private List<Object> volumesFrom = new ArrayList<Object>();
	private Boolean ready;
	private String createdAt;
	private String updatedAt;
	private String id;

	/**
	 *
	 * @return
	 * The image
	 */
	public String getImage() {
		return image;
	}

	/**
	 *
	 * @param image
	 * The image
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 *
	 * @return
	 * The ports
	 */
	public List<Object> getPorts() {
		return ports;
	}

	/**
	 *
	 * @param ports
	 * The ports
	 */
	public void setPorts(List<Object> ports) {
		this.ports = ports;
	}

	/**
	 *
	 * @return
	 * The environment
	 */
	public List<String> getEnvironment() {
		return environment;
	}

	/**
	 *
	 * @param environment
	 * The environment
	 */
	public void setEnvironment(List<String> environment) {
		this.environment = environment;
	}

	/**
	 *
	 * @return
	 * The originalName
	 */
	public String getOriginalName() {
		return originalName;
	}

	/**
	 *
	 * @param originalName
	 * The originalName
	 */
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
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
	 * The applicationId
	 */
	public String getApplicationId() {
		return applicationId;
	}

	/**
	 *
	 * @param applicationId
	 * The application_id
	 */
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	/**
	 *
	 * @return
	 * The labels
	 */
	public List<Object> getLabels() {
		return labels;
	}

	/**
	 *
	 * @param labels
	 * The labels
	 */
	public void setLabels(List<Object> labels) {
		this.labels = labels;
	}

	/**
	 *
	 * @return
	 * The expose
	 */
	public List<String> getExpose() {
		return expose;
	}

	/**
	 *
	 * @param expose
	 * The expose
	 */
	public void setExpose(List<String> expose) {
		this.expose = expose;
	}

	/**
	 *
	 * @return
	 * The volumes
	 */
	public List<Object> getVolumes() {
		return volumes;
	}

	/**
	 *
	 * @param volumes
	 * The volumes
	 */
	public void setVolumes(List<Object> volumes) {
		this.volumes = volumes;
	}

	/**
	 *
	 * @return
	 * The volumesFrom
	 */
	public List<Object> getVolumesFrom() {
		return volumesFrom;
	}

	/**
	 *
	 * @param volumesFrom
	 * The volumes_from
	 */
	public void setVolumesFrom(List<Object> volumesFrom) {
		this.volumesFrom = volumesFrom;
	}

	/**
	 *
	 * @return
	 * The ready
	 */
	public Boolean getReady() {
		return ready;
	}

	/**
	 *
	 * @param ready
	 * The ready
	 */
	public void setReady(Boolean ready) {
		this.ready = ready;
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
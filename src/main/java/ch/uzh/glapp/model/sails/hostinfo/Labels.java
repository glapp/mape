package ch.uzh.glapp.model.sails.hostinfo;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Labels {

	private Object executiondriver;
	private String kernelversion;
	private String operatingsystem;
	private String provider;
	private String region;
	private String storagedriver;
	private String tier;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 *
	 * @return
	 * The executiondriver
	 */
	public Object getExecutiondriver() {
		return executiondriver;
	}

	/**
	 *
	 * @param executiondriver
	 * The executiondriver
	 */
	public void setExecutiondriver(Object executiondriver) {
		this.executiondriver = executiondriver;
	}

	/**
	 *
	 * @return
	 * The kernelversion
	 */
	public String getKernelversion() {
		return kernelversion;
	}

	/**
	 *
	 * @param kernelversion
	 * The kernelversion
	 */
	public void setKernelversion(String kernelversion) {
		this.kernelversion = kernelversion;
	}

	/**
	 *
	 * @return
	 * The operatingsystem
	 */
	public String getOperatingsystem() {
		return operatingsystem;
	}

	/**
	 *
	 * @param operatingsystem
	 * The operatingsystem
	 */
	public void setOperatingsystem(String operatingsystem) {
		this.operatingsystem = operatingsystem;
	}

	/**
	 *
	 * @return
	 * The provider
	 */
	public String getProvider() {
		return provider;
	}

	/**
	 *
	 * @param provider
	 * The provider
	 */
	public void setProvider(String provider) {
		this.provider = provider;
	}

	/**
	 *
	 * @return
	 * The region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 *
	 * @param region
	 * The region
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 *
	 * @return
	 * The storagedriver
	 */
	public String getStoragedriver() {
		return storagedriver;
	}

	/**
	 *
	 * @param storagedriver
	 * The storagedriver
	 */
	public void setStoragedriver(String storagedriver) {
		this.storagedriver = storagedriver;
	}

	/**
	 *
	 * @return
	 * The tier
	 */
	public String getTier() {
		return tier;
	}

	/**
	 *
	 * @param tier
	 * The tier
	 */
	public void setTier(String tier) {
		this.tier = tier;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
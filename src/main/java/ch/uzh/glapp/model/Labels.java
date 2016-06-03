package ch.uzh.glapp.model;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Labels {

	@SerializedName("executiondriver")
	@Expose
	private Object executiondriver;
	@SerializedName("kernelversion")
	@Expose
	private String kernelversion;
	@SerializedName("operatingsystem")
	@Expose
	private String operatingsystem;
	@SerializedName("provider")
	@Expose
	private String provider;
	@SerializedName("region")
	@Expose
	private String region;
	@SerializedName("storagedriver")
	@Expose
	private String storagedriver;
	@SerializedName("tier")
	@Expose
	private String tier;

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

}
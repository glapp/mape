package ch.uzh.glapp.mdp;

import burlap.mdp.auxiliary.DomainGenerator;
import burlap.mdp.core.action.UniversalActionType;
import burlap.mdp.singleagent.oo.OOSADomain;


public class MapeWorld implements DomainGenerator {

	public static final String CLASS_CELL = "cell";

	public static final String VAR_VIOLATED_POLICY = "violated_policy";
	public static final String VAR_PROVIDER = "provider"; // constants for simulated environment and generating actions in MapeActionMove and MapeActionCreate classes
	public static final String AWS = "amazonec2"; // matching the name used in docker-machine driver
	public static final String DO = "digitalocean"; // matching the name used in docker-machine driver
	public static final String AZURE = "azure"; // matching the name used in docker-machine driver
	public static final String GOOGLE = "google"; // matching the name used in docker-machine driver
	public static final String[] PROVIDER_LIST = {AWS, DO, AZURE, GOOGLE};

	public static final String VAR_REGION = "region";
	public static final String NA = "us"; // constants for simulated environment and generating actions in MapeActionMove and MapeActionCreate classes
	public static final String EU = "eu"; // matching the name used in docker-machine driver
	public static final String ASIA = "asia";
	public static final String[] REGION_LIST = {NA, EU, ASIA};

	public static final String VAR_TIER = "tier";
	public static final String TIER1 = "1"; // constants for simulated environment and generating actions in MapeActionMove and MapeActionCreate classes
	public static final String TIER2 = "2";
	public static final String TIER3 = "3";
	public static final String[] TIER_LIST = {TIER1, TIER2, TIER3};

	public static final String VAR_CELLS = "cells";
//	public static final String VAR_PROXY_PROVIDER = "proxy_provider";
//	public static final String VAR_PROXY_REGION = "proxy_region";

	public static final String ACTION_MOVE = "move"; // with additional parameters like: AWS, Tier 1, US.
	public static final String ACTION_CREATE = "create"; // with additional parameters like: AWS, Tier 1, US.
	public static final String ACTION_REMOVE = "remove"; // with only cell_id as parameter


	@Override
	public OOSADomain generateDomain() {

		OOSADomain domain = new OOSADomain();

		domain.addStateClass(CLASS_CELL, MapeCell.class);

		domain.addActionTypes(
//				new UniversalActionType(ACTION_MOVE),
//				new UniversalActionType(ACTION_CREATE),
//				new UniversalActionType(ACTION_REMOVE)
				new MapeActionTypeMove(),
				new MapeActionTypeCreate()
//				new MapeActionTypeRemove()
		);

		return domain;
	}



}

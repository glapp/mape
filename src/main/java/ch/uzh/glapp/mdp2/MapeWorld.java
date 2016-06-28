package ch.uzh.glapp.mdp2;

import burlap.mdp.auxiliary.DomainGenerator;
import burlap.mdp.core.action.UniversalActionType;
import burlap.mdp.singleagent.oo.OOSADomain;


public class MapeWorld implements DomainGenerator {

	public static final String CLASS_CELL = "cell";

	public static final String VAR_VIOLATED_POLICY = "violated_policy";
	public static final String VAR_PROVIDER = "provider";
	public static final String AWS = "Amazon Web Services";
	public static final String DO = "Digital Ocean";
	public static final String AZURE = "Microsoft Azure";
	public static final String GOOGLE = "Google Compute Engine";

	public static final String VAR_REGION = "region";
	public static final String NA = "North America";
	public static final String EU = "Europe";
	public static final String ASIA = "Asia";

	public static final String VAR_TIER = "tier";
	public static final String TIER1 = "Tier 1";
	public static final String TIER2 = "Tier 2";
	public static final String TIER3 = "Tier 3";

	public static final String VAR_CELLS = "cells";
	public static final String VAR_PROXY_PROVIDER = "proxy_provider";
	public static final String VAR_PROXY_REGION = "proxy_region";

	public static final String ACTION_MOVE = "move"; // with additional parameters like: AWS, Tier 1, US.
	public static final String ACTION_CREATE = "create"; // with additional parameters like: AWS, Tier 1, US.
	public static final String ACTION_REMOVE = "remove"; // with only cell_id as parameter


	@Override
	public OOSADomain generateDomain() {

		OOSADomain domain = new OOSADomain();

		domain.addStateClass(CLASS_CELL, MapeCell.class);

		domain.addActionTypes(
				new UniversalActionType(ACTION_MOVE),
				new UniversalActionType(ACTION_CREATE),
				new UniversalActionType(ACTION_REMOVE)
//				new MapeActionTypeMove(),
//				new MapeActionTypeCreate(),
//				new MapeActionTypeRemove()
		);

		return domain;
	}

}

package ch.uzh.glapp.mdp2;

import burlap.mdp.auxiliary.DomainGenerator;
import burlap.mdp.core.action.UniversalActionType;
import burlap.mdp.singleagent.SADomain;


public class MapeWorld implements DomainGenerator {

	public static final String VAR_VIOLATED_POLICY = "violated_policy";
	public static final String VAR_PROVIDER = "provider";
	public static final String VAR_REGION = "region";
	public static final String VAR_TIER = "tier";
	public static final String VAR_CELLS = "cells";
	public static final String VAR_PROXY_PROVIDER = "proxy_provider";
	public static final String VAR_PROXY_REGION = "proxy_region";

	public static final String ACTION_MOVE = "move"; // with additional parameters like: AWS, Tier 1, US.
	public static final String ACTION_CREATE = "create"; // with additional parameters like: AWS, Tier 1, US.
	public static final String ACTION_DELETE = "delete"; // with only cell_id as parameter


	@Override
	public SADomain generateDomain() {

		SADomain domain = new SADomain();

		domain.addActionTypes(
				new UniversalActionType(ACTION_MOVE),
				new UniversalActionType(ACTION_CREATE),
				new UniversalActionType(ACTION_DELETE)
		);

		return domain;
	}

}

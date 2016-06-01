package ch.uzh.glapp.mdp;

import java.util.ArrayList;
import java.util.List;

public class MDP {

	private String status;
	private List<String> actions = new ArrayList<>();
	private List<String> transactions = new ArrayList<>();
	private int reward;

	public MDP (String status) {
		this.status = status;
		this.actions = null;
		this.transactions = null;
		this.reward = 0;
	}

	public void myFunc () {}

}

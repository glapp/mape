package ch.uzh.glapp.model;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Cells {

	@SerializedName("cells")
	@Expose
	private List<Cell> cells = new ArrayList<Cell>();

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

}
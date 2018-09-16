package javaee.ole.dicts;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the catec2n database table.
 * 
 */
@Entity
@NamedQuery(name="Catec2n.findAll", query="SELECT c FROM Catec2n c")
public class Catec2n implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int code;

	private String name;

	public Catec2n() {
	}

	public int getCode() {
		return this.code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
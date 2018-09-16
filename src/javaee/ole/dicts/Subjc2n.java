package javaee.ole.dicts;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the subjc2n database table.
 * 
 */
@Entity
@NamedQuery(name="Subjc2n.findAll", query="SELECT s FROM Subjc2n s")
public class Subjc2n implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int code;

	private String name;

	public Subjc2n() {
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
package lunch.ejb.entity;

import java.io.Serializable;


public class Student implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;

	private String mail;

	private String passwd;

	public Student() {
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPasswd() {
		return this.passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

}

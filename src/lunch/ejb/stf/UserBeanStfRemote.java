package lunch.ejb.stf;

import javax.ejb.Remote;

import lunch.ejb.entity.Student;;


@Remote
public interface UserBeanStfRemote {
	void sayhello();
	String getUname();
	void setUname(String uname);
	boolean isLog();
	void setLog(boolean islog);
	boolean updateUser(Student s);
	Student getLogUser();
}

package javaee.ole.dao;

import javaee.ole.entity.Teacher;

public class AdminDAO extends BasicDAO{
	
	public static Teacher getAdmin(String id){
		return manager.find(Teacher.class, id);
	}
	
	public static void main(String[] args) {

	}

}

package javaee.ole.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class BasicDAO {
	protected static EntityManagerFactory factory;
	protected static EntityManager manager;
	static{
		factory = Persistence.createEntityManagerFactory("OLE");  
	    manager = factory.createEntityManager();
	}
	public static void close(){
		try{
			manager.close();
			factory.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

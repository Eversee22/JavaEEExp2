package javaee.ole.dicts;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import javaee.ole.dao.BasicDAO;

public class SubjDict{
	private static EntityManagerFactory factory;
	private static EntityManager manager;
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
	public static List<Subjc2n> getSubjc2ns(){
		Query q = manager.createQuery("select s from Subjc2n s");
		return q.getResultList();
		
	}
	
	public static void main(String[] args) {
		List<Subjc2n> slist = getSubjc2ns();
		for(Subjc2n s:slist){
			System.out.println(s.getCode()+":"+s.getName());
		}
		close();

	}

}

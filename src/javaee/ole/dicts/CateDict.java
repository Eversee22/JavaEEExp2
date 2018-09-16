package javaee.ole.dicts;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import javaee.ole.dao.BasicDAO;

public class CateDict{
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
	public static List<Catec2n> getCatec2ns(){
		Query q = manager.createQuery("select c from Catec2n c");
		return q.getResultList();
		
	}
	public static void main(String[] args) {
		List<Catec2n> clist = getCatec2ns();
		for(Catec2n c:clist){
			System.out.println(c.getCode()+":"+c.getName());
		}
		close();

	}

}

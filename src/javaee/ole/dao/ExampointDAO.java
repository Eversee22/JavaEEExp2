package javaee.ole.dao;


import javaee.ole.entity.Exampoint;

public class ExampointDAO extends BasicDAO{
	public static Exampoint getExampoint(int pointid){
		return manager.find(Exampoint.class, pointid);
	}
	
	public static void addExampoint(Exampoint ep){
		try{
			manager.getTransaction().begin();
			manager.persist(ep);
			manager.getTransaction().commit();
		}catch(Exception e){
			manager.getTransaction().rollback();
		}
		
	}
	public static void main(String[] args) {
		

	}

}

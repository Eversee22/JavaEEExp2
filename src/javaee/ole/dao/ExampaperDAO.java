package javaee.ole.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Query;

import javaee.ole.entity.Exampaper;

public class ExampaperDAO extends BasicDAO{
	
	public static Exampaper getPaper(int papid){
		return manager.find(Exampaper.class, papid);
	}
	
	public static Exampaper getPaperByTitle(String tit){
		Query q = manager.createQuery("Select p from Exampaper p where p.title=:title");
		q.setParameter("title", tit);
		if(q.getResultList().size()>0)
			return (Exampaper) q.getResultList().get(0);
		else
			return null;
	}
	
	public static void addPaper(Exampaper paper){
		try{
			manager.getTransaction().begin();
			manager.persist(paper);
			manager.getTransaction().commit();
		}catch(Exception e){
			manager.getTransaction().rollback();
		}
		
	}
	
	private static void addpaper(String tit,String subj,String content){
		Exampaper paper = new Exampaper();
		paper.setTitle(tit);
		paper.setSubject(subj);
		paper.setContent(content);
		paper.setDate(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(new Date()));
		addPaper(paper);
		System.out.println("add a paper");
	}
	
	public static void main(String[] args) {
		addpaper("2018-06-16 CET6","Ӣ��","");
		//close();
	}

}

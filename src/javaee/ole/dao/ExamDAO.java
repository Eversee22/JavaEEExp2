package javaee.ole.dao;


import javaee.ole.entity.Exam;
import javaee.ole.entity.Exampaper;

public class ExamDAO extends BasicDAO{
	
	public static Exam getExam(int examid){
		//Exam exam = null;
		//manager.getTransaction().begin();
		return manager.find(Exam.class, examid);
		//manager.getTransaction().commit();
		//return exam;
	}
	
	public static void addExam(Exam e){
		manager.getTransaction().begin();
		manager.persist(e);
		manager.getTransaction().commit();
	}
	
	private static void addExam(String date,String title,int examtime){
		Exampaper p = ExampaperDAO.getPaperByTitle(title);
		if(p!=null){
			Exam e = new Exam();
			e.setDate(date);
			e.setExamtitle(p.getTitle());
			e.setExampaperid(p.getId());
			e.setExamtime(examtime);
			addExam(e);
			System.out.println("add  a exam");
		}
	}
	
	public static void main(String[] args) {
		addExam("2017-12-16 15:30:00", "C",120);
		close();
	}

}

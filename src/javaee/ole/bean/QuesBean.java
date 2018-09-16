package javaee.ole.bean;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import javaee.ole.dao.ExampointDAO;
import javaee.ole.dicts.CateDict;
import javaee.ole.dicts.Catec2n;
import javaee.ole.dicts.SubjDict;
import javaee.ole.dicts.Subjc2n;
import javaee.ole.entity.Exampoint;

@ManagedBean
@SessionScoped
public class QuesBean {
	private static Map<String,String> subjn2c;
	//private static Map<Integer,String> subjc2n;
	private static Map<String,String> caten2c;
	//private static Map<Integer,String> catec2n;
	//private static List<String> subjns;
	

	//private static List<String> catens;
	private Exampoint ep;
	private String question;
	private String solution;
	private int cate;
	private int subj;
	static{
		subjn2c = new HashMap<String,String>();
		//subjc2n = new HashMap<Integer,String>();
		caten2c = new HashMap<String,String>();
		//catec2n = new HashMap<Integer,String>();
		//subjns = new LinkedList<String>();
		//catens = new LinkedList<String>();
		List<Subjc2n> slist = SubjDict.getSubjc2ns();
		List<Catec2n> clist = CateDict.getCatec2ns();
		for(Subjc2n s:slist){
			subjn2c.put(s.getName(), ""+s.getCode());
//			subjc2n.put(s.getCode(), s.getName());
//			subjns.add(s.getName());
		}
		for(Catec2n c:clist){
			caten2c.put(c.getName(), ""+c.getCode());
//			catec2n.put(c.getCode(), c.getName());
//			catens.add(c.getName());
		}
	}
	public QuesBean(){
		//ep = new Exampoint();
		
	}
	public QuesBean(Exampoint ep){
		this.ep = ep;
	}
	
	
	public String getQuestion(){
		return  question;
	}
	public void setQuestion(String ques){
		question = ques;
	}
	
	public String getSolution(){
		return  solution;
	}
	public void setSolution(String solution){
		this.solution = solution;
	}
	
	public String getCategory(){
		return ""+cate;
	}
	public void setCategory(String cate){
		this.cate = Integer.parseInt(cate);
	}
	
	public String getSubject(){
		return ""+subj;
	}
	
	public void setSubject(String subj){
		this.subj = Integer.parseInt(subj);
	}
	
//	public String getDate(){
//		return ep.getDate();
//	}
//	
//	public void setDate(String date){
//		//ep.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//	}
	
	public  Map<String,String> getSubjn2c() {
		return subjn2c;
	}
	
	public  Map<String,String> getCaten2c() {
		return caten2c;
	}
	
	public String add(){
		System.out.println(cate);
		System.out.println(subj);
//		System.out.println(ep.getContent());
//		System.out.println(ep.getSolution());
		Exampoint epn = new Exampoint();
		epn.setContent(question);
		epn.setSolution(solution);
		epn.setCategory(cate);
		epn.setSubject(subj);
		epn.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		ExampointDAO.addExampoint(epn);
		return null;
	}
	
}

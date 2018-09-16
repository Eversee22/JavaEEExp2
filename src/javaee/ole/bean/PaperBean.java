package javaee.ole.bean;

import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import javaee.ole.dao.ExampaperDAO;
import javaee.ole.entity.Exam;
import javaee.ole.entity.Exampaper;
import javaee.ole.entity.Exampoint;
import javaee.ole.jdbc.ExamJDBC;
import javaee.ole.wrap.QuesWrap;


public class PaperBean {
	private String stuname;
	private String date;
	private int mark;
	private int rank;
	private Exam exam;
	private Exampaper epap;
	private List<Exampoint> eplist;
	private List<QuesWrap> qwlist;
	private String paperRender;
	private String examRender;
	private String examOverRender;
	private String examHRender;
	
	public PaperBean(){
		
	}
	public PaperBean(String stuname,Exam exam){
		this.stuname = stuname;
		this.exam = exam;
		examHRender = "true";
		paperRender = "true";
		examRender = "false";
		examOverRender = "false";
		mark = ExamJDBC.getExamMark(stuname, exam.getId());
		rank = ExamJDBC.getExamRank(stuname, exam.getId());
		date = exam.getDate();
		epap = ExampaperDAO.getPaper(exam.getExampaperid());
		eplist = ExamJDBC.getExampoints(exam.getExampaperid());
		setQwList(exam.getExampaperid());
	}
	public PaperBean(Exampaper epap){
		examHRender = "false";
		paperRender = "true";
		examRender = "false";
		examOverRender ="false";
		this.epap = epap;
		eplist = ExamJDBC.getExampoints(epap.getId());
		date = epap.getDate();
		setQwList(epap.getId());
	}
	private void setQwList(int paperid){
		qwlist = new LinkedList<QuesWrap>();
		for(Exampoint ep:eplist){
			qwlist.add(new QuesWrap(paperid,ep));
		}
	}
//	public List<Exampoint> getEplist() {
//		return eplist;
//	}
	public String getPaperRender() {
		return paperRender;
	}
	public void setPaperRender(String paperRender) {
		this.paperRender = paperRender;
	}
	public String getExamRender() {
		return examRender;
	}
	public void setExamRender(String examRender) {
		this.examRender = examRender;
	}
	
	public String getExamOverRender() {
		return examOverRender;
	}
	
	public void setExamOverRender(String examOverR) {
		examOverRender = examOverR;
	}
	
	public String getExamHRender() {
		return examHRender;
	}
	public void setExamHRender(String examHRender) {
		this.examHRender = examHRender;
	}
	
	public List<QuesWrap> getQwList(){
		return qwlist;
	}
	
	public void updateMark(){
		int mark = 0;
		for(QuesWrap qw:qwlist){
			if(qw.getEp().getSolution().equals(qw.getSolu())){
				mark += Integer.parseInt(qw.getScore());
			}else{
				qw.setCheck("´íÎó");
			}
		}
		this.mark = mark;
		ExamJDBC.updateExamMark(stuname, exam.getId(), mark);
		updateRank();
	}
	
	private void updateRank(){
		rank = ExamJDBC.getExamRank(stuname, exam.getId());
	}
	
	public Integer getRank(){
		return rank;
	}
	
	public String getStuname() {
		return stuname;
	}
	
	public String getDate() {
		return date;
	}
	
	public Integer getMark() {
		return mark;
	}
	
	public String getPaperTitle(){
		return epap.getTitle();
	}
	public int getPaperid(){
		return epap.getId();
	}
	
	
}

package javaee.ole.bean;

import java.awt.print.Paper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import javaee.ole.dao.EJBRemoteClient;
import javaee.ole.dao.StudentDAO;
import javaee.ole.entity.Exam;
import javaee.ole.entity.Exampaper;
import javaee.ole.entity.Exampoint;
import javaee.ole.entity.Student;
import javaee.ole.jdbc.ExamJDBC;
import javaee.ole.utils.MD5Enc;
import javaee.ole.utils.MailSender;
import javaee.ole.utils.RandPassword;
import javaee.ole.wrap.QuesWrap;
import lunch.ejb.stf.UserBeanStfRemote;

@ManagedBean
@SessionScoped
public class StudentBean {
	private String uname;
	private String passwd;
	private String mail;
	private lunch.ejb.entity.Student loguser;
//	private boolean issub;
//	private String sublab;
	private List<Exam> examlist;
	private List<Exampaper> paperlist;
	private List<QuesWrap> qwlist;
	private UserBeanStfRemote us;
	private String linklab;
	private String joined;
	private String quesHR;
	private String infRO;
	PaperBean papb;
	QuesBean qb;
	public StudentBean(){
//		issub = false;
//		sublab = "订阅";
		quesHR = "false";
		infRO = "true";
		us = null;
		//joined = "false";
	}
	//User
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public String login(){
		Student s = StudentDAO.findUserByName(uname);
		if(s!=null&&MD5Enc.encodeMD5(passwd).equals(s.getPasswd())){
			if(us==null)
				us = (UserBeanStfRemote) EJBRemoteClient.lookUpRemoteStatefulEJB("UserBeanStf", "lunch.ejb.stf.UserBeanStfRemote");
			us.setLog(true);
			us.setUname(uname);
			loguser = us.getLogUser();
			return "succ";
		}else
			return "falu";
	}
	public String logout(){
		if(us!=null&&us.isLog())
			us.setLog(false);
		return null;
	}
	
	public String getLogname(){
		if(us!=null&&us.isLog())
			return us.getUname();
		else
			return "未登录";
	}
	
	public String getLogPasswd(){
		if(loguser!=null)
			return loguser.getPasswd();
		else
			return null;
	}
	public void setLogPasswd(String passwd){
		if(loguser!=null)
			loguser.setPasswd(MD5Enc.encodeMD5(passwd));
	}
	
	public String getLogMail(){
		if(loguser!=null)
			return loguser.getMail();
		else
			return "";
	}
	
	public void setLogMail(String mail){
		if(loguser!=null)
			loguser.setMail(mail);
	}
	
	public String getIslogin(){
		if(us!=null&&us.isLog())
			return "true";
		else
			return "false";
	}
	public String register(){
		StudentDAO.addUser(uname, passwd, mail);
		return "succ";
	}
	public String findpass(){
		String pass = RandPassword.genPass(10);
		Student s = StudentDAO.findUserByMail(mail);
		if(s!=null){
			try{
				//System.out.println("send mail");
				MailSender.sendMail(mail, "找回密码", pass);
				//System.out.println("send mail over!");
				s.setPasswd(MD5Enc.encodeMD5(pass));
				StudentDAO.updateUser(s);
				//System.out.println("update over");
				return "succ";
			}catch(Exception e){
				return "falu";
			}
		}else{
			System.out.println("no user mail");
		}
		return "falu";
	}
	
	public String myInfo(){
		if(us!=null&&us.isLog()){
			return "account";
		}
		return "logfalu";
	}
	
	public String getInfRO(){
		return infRO;
	}
	
	public String getInfNRO(){
		if(infRO.equals("true"))
			return "false";
		else
			return "true";
	}
	
	public String changeInfo(){
		infRO= "false";
		return null;
	}
	
	public String saveInfo(){
		if(us!=null&&us.isLog()){
			if(us.updateUser(loguser))
				System.out.println(uname+"'Info. has changed");
		}
		return null;
	}
	
	//Exam
	public String joinExam1(){
		linklab="报名";
		joined = "false";
		examlist = ExamJDBC.searchDueExam();
		if(examlist.size()>0)
			System.out.println(examlist.size());
		else
			System.out.println("no  exam");
		return null;
	}
	public String joinExam2(Exam exam){
		if(us!=null&&us.isLog()){
			if(ExamJDBC.joinExam(us.getUname(), exam.getId())){
				return "joinsucc";
			}else{
				System.out.println("join falu");
				return "joinfalu";
			}
				
		}else
			return "logfalu";
	}
	
	public String getJoined(){
		return joined;
	}
	
	public String getLinklab(){
		return linklab;
	}
	public String linkact(Exam exam){
		if(linklab.equals("报名"))
			return joinExam2(exam);
		else if(linklab.equals("查看")){
			return myExam2(exam);
		}
		return null;
	}
	
	public String myExam1(){
		if(us!=null&&us.isLog()){
			linklab = "查看";
			joined = "true";
			examlist = ExamJDBC.searchMyExam(us.getUname());
			return "logsucc";
		}
		return "logfalu";
	}
	
	public String myExam2(Exam exam){
//		if(us!=null&&us.isLog()){
//			return "logsucc";
//		}else
//			return "logfalu";
		if(exam.getDate().compareTo(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))<0){
			papb = new PaperBean(uname,exam);
			return "paper";
		}
		else
			return "nopaper";
	}
	public String getExamMark(){
		return papb.getMark().toString();
	}
	
	public String getExamName(){
		return papb.getStuname();
	}
	
	public String getExamDate(){
		return papb.getDate();
	}
	
	public String getExamRank(){
		return papb.getRank().toString();
	}
	public List<Exam> getExamlist(){
		return examlist;
	}

	//Paper
	public String myLikePap1(){
		if(us!=null&&us.isLog()){
			//examlist = ExamJDBC.searchMyExam(us.getUname());
			paperlist = ExamJDBC.getLikePapers(us.getUname());
			return "logsucc";
		}
		return "logfalu";
	}
	
	public String myLikePap2(Exampaper pap){
		papb = new PaperBean(pap);
		return "paper";
	}
	
	public List<QuesWrap> getPaperQwList(){
		return papb.getQwList();
	}
	
	public String getPaperTitle(){
		return papb.getPaperTitle();
	}
	public String getPaperRender(){
		return papb.getPaperRender();
	}
	
	public String getExamRender(){
		return papb.getExamRender();
	}
	
	public String getExamOverRender(){
		return papb.getExamOverRender();
	}
	
	public String getExamHRender(){
		return papb.getExamHRender();
	}
	
//	public String sOreExam(){
//		return null;
//	}
	public String startExam(){
		papb.setExamHRender("false");
		papb.setPaperRender("false");
		papb.setExamRender("true");
		papb.setExamOverRender("false");
		return null;
	}
	public String  overExam(){
		papb.setExamHRender("true");
		papb.setPaperRender("true");
		papb.setExamRender("false");
		papb.setExamOverRender("true");
		papb.updateMark();
		return null;
	}
	
	public String likePaper(){
		ExamJDBC.addLikePaper(us.getUname(),papb.getPaperid());
		return null;
		
	}
	
	public List<Exampaper> getPaperlist() {
		return paperlist;
	}
	
	
	
	//question
	public String myLikeQues1(){
		if(us!=null&&us.isLog()){
			List<Exampoint> queslist = ExamJDBC.getLikeQuess(us.getUname());
			qwlist = new LinkedList<QuesWrap>();
			for(Exampoint q:queslist){
				qwlist.add(new QuesWrap(q));
			}
			if(qwlist.size()>0)
				quesHR = "true";
			else
				quesHR = "false";
			return "logsucc";
		}
		return "logfalu";
	}
	public String getQuesHR(){
		return quesHR;
	}
	
	public String myLikeQues2(Exampoint ques){
		qb = new QuesBean(ques);
		return "question";
	}
	public String likeQues(Exampoint ep){
		ExamJDBC.addLikePoint(us.getUname(),ep.getId());
		return null;
		
	}
	public List<QuesWrap> getQwlist() {
		return qwlist;
	}
	
	//other
	public String subscribe(){
		return null;
	}
	public String getNotice(){
		return null;
	}
	
	
	public void cancelExam(Exam exam)
	{
		ExamJDBC.cancelExam(uname, exam.getId());
		if(us!=null&&us.isLog()){
			examlist = ExamJDBC.searchMyExam(us.getUname());
			
		}
		return;
	}
	
	public void unlikePaper(Exampaper paper)
	{
		if(us!=null&&us.isLog()){
			//examlist = ExamJDBC.searchMyExam(us.getUname());
			paperlist = ExamJDBC.getLikePapers(us.getUname());
		}
		ExamJDBC.deleteLikePaper(uname, paper.getId());
	}
	
	
}

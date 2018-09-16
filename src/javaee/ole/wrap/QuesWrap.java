package javaee.ole.wrap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javaee.ole.dicts.CateDict;
import javaee.ole.dicts.Catec2n;
import javaee.ole.dicts.SubjDict;
import javaee.ole.dicts.Subjc2n;
import javaee.ole.entity.Exampoint;
import javaee.ole.jdbc.ExamJDBC;

public class QuesWrap {
	private Exampoint ep;
	private String quesnum;
	private String score;
	private String solu;
	private String check;
	private String isrender;
	private int paperid;
	private static Map<Integer,String> subjc2n;
	private static Map<Integer,String> catec2n;
	static{
		subjc2n = new HashMap<Integer,String>();
		catec2n = new HashMap<Integer,String>();
		List<Subjc2n> slist = SubjDict.getSubjc2ns();
		List<Catec2n> clist = CateDict.getCatec2ns();
		for(Subjc2n s:slist){
			subjc2n.put(s.getCode(),s.getName());
		}
		for(Catec2n c:clist){
			catec2n.put(c.getCode(),c.getName());
		}
	}
	public QuesWrap(){
		
	}
	public QuesWrap(Exampoint ep) {
		this.ep = ep;
	}
	public QuesWrap(int paperid,Exampoint ep){
		this.paperid = paperid;
		this.ep = ep;
		String qnNsc = ExamJDBC.getQnNSco(paperid, ep.getId());
		if(qnNsc!=null){
			String[] qn_sc = qnNsc.split(",");
			quesnum = qn_sc[0];
			score = qn_sc[1];
		}
	}
	public String getRender(){
		return isrender;
	}
	
	public String getQuesnum() {
		return quesnum;
	}
	public void setQuesnum(String quesnum) {
		this.quesnum = quesnum;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getSolu() {
		return solu;
	}
	public void setSolu(String solu) {
		this.solu = solu;
	}
	public String getCheck() {
		return check;
	}
	public void setCheck(String check) {
		this.check = check;
	}
	public String getContent(){
		return ep.getContent();
	}
	public String getSolution(){
		return ep.getSolution();
	}
	public String getSubject(){
		return subjc2n.get(ep.getSubject());
	}
	public String getCategory(){
		return catec2n.get(ep.getCategory());
	}
	public String getDate(){
		return ep.getDate();
	}
	public Exampoint getEp(){
		return ep;
	}
	
}

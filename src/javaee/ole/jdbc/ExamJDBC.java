package javaee.ole.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.jpa.boot.internal.PersistenceXmlParser;

import javaee.ole.dao.ExamDAO;
import javaee.ole.dao.ExampaperDAO;
import javaee.ole.dao.ExampointDAO;
import javaee.ole.entity.Exam;
import javaee.ole.entity.Exampaper;
import javaee.ole.entity.Exampoint;

public class ExamJDBC {
	private static final String url = "jdbc:mysql://localhost:3306/school?useSSL=false";
	private static final String user = "root";
	private static final String passwd = "yujiayu";
	static{
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception e){
			//System.out.println("\n Error Loading Mysql Driver ...\n");
			System.out.println(e);
		}
	}
	
	public static List<Exam> searchDueExam(){
		String sql = "select * from exam where date > ?";
		List<Exam> exams = new LinkedList<Exam>();
		try {
			Connection conn = DriverManager.getConnection(url,user,passwd);
			PreparedStatement pres = conn.prepareStatement(sql);
			String curTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			//System.out.println(curTime);
			pres.setString(1, curTime);
			ResultSet rs = pres.executeQuery();
			while(rs.next()){
				Exam exam = new Exam();
				exam.setId(rs.getInt("id"));
				exam.setExamtitle(rs.getString("examtitle"));
				exam.setDate(rs.getString("date"));
				exam.setExampaperid(rs.getInt("exampaperid"));
				exams.add(exam);
			}
			rs.close();
			pres.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exams;
	}
	public static List<Exam> searchMyExam(String name){
		String sql = "select examid from stuexam where stuname = ?";
		List<Exam> exams = new LinkedList<Exam>();
		try {
			Connection conn = DriverManager.getConnection(url,user,passwd);
			PreparedStatement pres = conn.prepareStatement(sql);
			pres.setString(1, name);
			ResultSet rs = pres.executeQuery();
			while(rs.next()){
				int id = rs.getInt("examid");
				Exam exam = ExamDAO.getExam(id);
				if(exam!=null)
					exams.add(exam);
			}
			rs.close();
			pres.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exams;
	}
	
	public static boolean joinExam(String name,int examid){
		String sql = "insert into stuexam(stuname,examid,mark) values(?,?,?)";
		try {
			Connection conn = DriverManager.getConnection(url,user,passwd);
			PreparedStatement pres = conn.prepareStatement(sql);
			pres.setString(1, name);
			pres.setInt(2, examid);
			pres.setInt(3, 0);
			pres.execute();
			pres.close();
			conn.close();
			//System.out.println("insert a (name,examid)");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean cancelExam(String name, int examid)
	{
		String sql = "delete from stuexam where examid=? and stuname=?";
		try {
			Connection connection = DriverManager.getConnection(url,user,passwd);
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, name);
			statement.setInt(2, examid);;
			statement.execute();
			statement.close();
			connection.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean updateExamMark(String name,int examid,int mark){
		String sql = "update stuexam set mark=? where stuname=? and examid=?";
		try {
			Connection conn = DriverManager.getConnection(url,user,passwd);
			PreparedStatement pres = conn.prepareStatement(sql);
			pres.setInt(1, mark);
			pres.setString(2, name);
			pres.setInt(3, examid);
			pres.executeUpdate();
			pres.close();
			conn.close();
			//System.out.println("insert a (name,examid)");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public static int getExamMark(String name,int examid){
		String sql = "select mark from stuexam where stuname=? and examid=?";
		int mark = 0;
		try {
			Connection conn = DriverManager.getConnection(url,user,passwd);
			PreparedStatement pres = conn.prepareStatement(sql);
			pres.setString(1, name);
			pres.setInt(2, examid);
			ResultSet rs = pres.executeQuery();
			if(rs.next())
				mark = rs.getInt("mark");
			rs.close();
			pres.close();
			conn.close();
			//System.out.println("insert a (name,examid)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mark;
	}
	
	public static int getExamRank(String name,int examid){
		String sql = "select count(*) from stuexam where mark>? and examid=?";
		int rank = 0;
		try {
			Connection conn = DriverManager.getConnection(url,user,passwd);
			PreparedStatement pres = conn.prepareStatement(sql);
			pres.setInt(1, getExamMark(name,examid));
			pres.setInt(2, examid);
			ResultSet rs = pres.executeQuery();
			if(rs.next())
				rank = rs.getInt(1);
			rs.close();
			pres.close();
			conn.close();
			//System.out.println("insert a (name,examid)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rank+1;
	}
	public static List<Exampoint> getExampoints(int paperid){
		String sql = "select exampointid from exampaperpoint where exampaperid=?";
		//int mark = 0;
		List<Exampoint> eplist = new LinkedList<Exampoint>();
		try {
			Connection conn = DriverManager.getConnection(url,user,passwd);
			PreparedStatement pres = conn.prepareStatement(sql);
			pres.setInt(1, paperid);
			ResultSet rs = pres.executeQuery();
			while(rs.next()){
				int id = rs.getInt(1);
				Exampoint ep = ExampointDAO.getExampoint(id);
				if(ep!=null)
					eplist.add(ep);
			}
			rs.close();
			pres.close();
			conn.close();
			//System.out.println("insert a (name,examid)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return eplist;
	}
	
	public static void addLikePoint(String name,int quesid){
		String sql = "insert into stulikeques(stuname,quesid) values(?,?)";
		try {
			Connection conn = DriverManager.getConnection(url,user,passwd);
			PreparedStatement pres = conn.prepareStatement(sql);
			pres.setString(1, name);
			pres.setInt(2, quesid);
			pres.execute();
			pres.close();
			conn.close();
			//System.out.println("insert a (name,examid)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void addLikePaper(String name,int paperid){
		String sql = "insert into stulikepaper(stuname,paperid) values(?,?)";
		try {
			Connection conn = DriverManager.getConnection(url,user,passwd);
			PreparedStatement pres = conn.prepareStatement(sql);
			pres.setString(1, name);
			pres.setInt(2, paperid);
			pres.execute();
			pres.close();
			conn.close();
			//System.out.println("insert a (name,examid)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteLikePaper(String name, int paperid)
	{
		String sql = "delete form stulikepaper where stuname = ? and paperid = ?";
		try {
			Connection connection = DriverManager.getConnection(url,user,passwd);
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, paperid);
			preparedStatement.execute();
			preparedStatement.close();
			connection.close();
			
		}catch (SQLException e) {
			e.getStackTrace();
		}
	}
	
	public static List<Exampaper> getLikePapers(String name){
		String sql = "select paperid from stulikepaper where stuname=?";
		//int mark = 0;
		List<Exampaper> epaplist = new LinkedList<Exampaper>();
		try {
			Connection conn = DriverManager.getConnection(url,user,passwd);
			PreparedStatement pres = conn.prepareStatement(sql);
			pres.setString(1, name);
			ResultSet rs = pres.executeQuery();
			while(rs.next()){
				int id = rs.getInt(1);
				Exampaper epap = ExampaperDAO.getPaper(id);
				if(epap!=null)
					epaplist.add(epap);
			}
			rs.close();
			pres.close();
			conn.close();
			//System.out.println("insert a (name,examid)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return epaplist;
	}
	
	
	public static List<Exampoint> getLikeQuess(String name){
		String sql = "select quesid from stulikeques where stuname=?";
		//int mark = 0;
		List<Exampoint> equeslist = new LinkedList<Exampoint>();
		try {
			Connection conn = DriverManager.getConnection(url,user,passwd);
			PreparedStatement pres = conn.prepareStatement(sql);
			pres.setString(1, name);
			ResultSet rs = pres.executeQuery();
			while(rs.next()){
				int id = rs.getInt(1);
				Exampoint eques = ExampointDAO.getExampoint(id);
				if(eques!=null)
					equeslist.add(eques);
			}
			rs.close();
			pres.close();
			conn.close();
			//System.out.println("insert a (name,examid)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return equeslist;
	}
	
	public static String getQnNSco(int paperid,int quesid){
		String sql = "select quesnum,score from exampaperpoint where exampaperid=? and exampointid=?";
		String answer=null;
		try {
			Connection conn = DriverManager.getConnection(url,user,passwd);
			PreparedStatement pres = conn.prepareStatement(sql);
			pres.setInt(1, paperid);
			pres.setInt(2, quesid);
			ResultSet rs = pres.executeQuery();
			if(rs.next())
				answer = rs.getString(1)+","+rs.getString(2);
			rs.close();
			pres.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return answer;
	}
	
	public static void main(String[] args) {
		//searchDueExam();
		//joinExam("L",2);
	//	updateExamMark("J",1,90);
//		updateExamMark("K",1,50);
//		updateExamMark("K",1,50);
		//System.out.println(getExamRank("K",1));
		List<Exam> exams = searchMyExam("fst");
		for(Exam exam:exams){
			System.out.println(exam.getExamtitle());
		}
	}
	

}

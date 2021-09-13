package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import util.JDBCUtil;

public class NoticeDao {
	private NoticeDao(){}
	private static NoticeDao instance;
	public static NoticeDao getInstance(){
		if(instance == null){
			instance = new NoticeDao();
		}
		return instance;
	}
	


String birthdaymessagetitle = "회원님의 생일을 진심으로 축하드립니다!";
String birthdaymessagetcontent = "축하의 의미로 3000캐시를 선물로 드립니다~ 좋은 하루 보내세요!";




	private JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public List<Map<String, Object>> selectnoticeall () {   // 알림조회  
		String sql = " SELECT MEM_ID, NOTICE_NO, NOTICE_TITLE, NOTICE_CONTENT, NOTICE_DATE, NOTICE_STATUS, NOTICE_SENDER "
				+ "FROM NOTICE "
				+ "WHERE MEM_ID = ? ";
		List<Object> param = new ArrayList<>();
		param.add(Controller.LoginUser.get("MEM_ID").toString());
		return jdbc.selectList(sql,param);
	}
	
	public List<Map<String, Object>> selectnoticenoread () {   // 알림조회 (읽지않음) 
		String sql = " SELECT MEM_ID, NOTICE_NO, NOTICE_TITLE, NOTICE_CONTENT, NOTICE_DATE, NOTICE_STATUS, NOTICE_SENDER "
				+ " FROM NOTICE "
				+ " WHERE MEM_ID = ? "
				+ " AND NOTICE_STATUS = '읽지않음'  ";
		List<Object> param = new ArrayList<>();
		param.add(Controller.LoginUser.get("MEM_ID").toString());
		return jdbc.selectList(sql,param);
	}
	
	public Map<String, Object> selectnoticedetail (String noticeno) {   // 알림조회  
		String sql = " SELECT MEM_ID, NOTICE_NO, NOTICE_TITLE, NOTICE_CONTENT, NOTICE_DATE, NOTICE_STATUS, NOTICE_SENDER "
				+ " FROM NOTICE "
				+ " WHERE NOTICE_NO = ?";
		List<Object> param = new ArrayList<>();
		param.add(noticeno);
		return jdbc.selectOne(sql,param);
	}
	
	public int modifynotice(String noticeno){  //  읽음으로변경
		String sql = "UPDATE NOTICE "
				+ " SET NOTICE_STATUS = '읽음' "
				+ " WHERE NOTICE_NO = ? ";
		List<Object> p = new ArrayList<>();
		p.add(noticeno);
		return jdbc.update(sql, p);
	}
	
	public int deletenotice(String noticeno){ // 알림 삭제 
		String sql = "DELETE "
				+ " FROM NOTICE "
				+ " WHERE NOTICE_NO = ? ";
		List<Object> p = new ArrayList<>();
		p.add(noticeno);
		return jdbc.update(sql, p);
	}
//	Controller.LoginUser.get("MEM_ID").toString()
	public int sendnotice(String title, String content, String memid){ // 알림 보내기  (수신자 : 현재 로그인한 회원)
		String sql = "INSERT INTO NOTICE(NOTICE_NO, NOTICE_SENDER, NOTICE_DATE, NOTICE_TITLE, NOTICE_CONTENT, NOTICE_STATUS, MEM_ID) "
				+ " VALUES(NOTICE_SEQ.NEXTVAL,'관리자', TO_CHAR(SYSDATE,'YY-MM-DD HH:MI:SS'), ?, ?, '읽지않음', ?)";
		List<Object> param = new ArrayList<>();
		param.add(title);
		param.add(content);
		param.add(memid);
		return jdbc.update(sql, param);
	}	
	
	
}

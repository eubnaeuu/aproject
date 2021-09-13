package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.NoticeDao;
import util.JDBCUtil;
import util.ScanUtil;
import util.View;

public class NoticeService {

	
	JDBCUtil jdbc = JDBCUtil.getInstance();
	private NoticeDao noticeDao = NoticeDao.getInstance();
int snumber; //구분번호저장	
	List<Map<String, Object>>  templi = new ArrayList<>(); 
	HashMap <String,Object>temphm = new HashMap<>();
	public NoticeService(){}
	private static NoticeService instance;
	public static NoticeService getInstance(){
		if(instance == null){
			instance = new NoticeService();			
			}
			return instance;
		}
	
public int selectnotice() {//view.selectnoticeall
	System.out.println("==============================");
	System.out.println("                           알                림                        ");
	System.out.println("==============================");
	templi = new ArrayList<>(); // 글번호 연동할 리스트 (게시글 상세조회시) (글번호는 상품별로 달림)
	for(int i=0; i<noticeDao.selectnoticeall().size(); i++){
		temphm = new HashMap<>();	
		    System.out.print("["+i+"] ");
		    System.out.print("\t"+noticeDao.selectnoticeall().get(i).get("NOTICE_DATE"));
		    System.out.print("\t"+noticeDao.selectnoticeall().get(i).get("NOTICE_STATUS"));
		    System.out.print("\t"+noticeDao.selectnoticeall().get(i).get("NOTICE_SENDER"));
			System.out.println("\t"+noticeDao.selectnoticeall().get(i).get("NOTICE_TITLE"));
			temphm.put("NOTICE_NO", noticeDao.selectnoticeall().get(i).get("NOTICE_NO")); // 0번글은 리스트 0번째이고 그에 들어간 hashmap에는 prod_id가 저장되어있다.			
		templi.add((temphm));
	}
	System.out.println("1.읽기  2.삭제  0.뒤로");
	int input = ScanUtil.nextInt();
	switch (input) {
	case 1 :
		System.out.println("글번호를 선택해주세요");
		snumber = ScanUtil.nextInt();
		return  View.SELECTNOTICEDETAIL;
	case 2 :
	{
		System.out.println("글번호를 선택해주세요");  
		snumber = ScanUtil.nextInt(); 
		System.out.println("메시지를 삭제하였습니다.");
		noticeDao.deletenotice(noticeDao.selectnoticeall().get(snumber).get("NOTICE_NO").toString()); //delete 알림
		return View.SELECTNOTICEALL;
	}
	case 0:
		return View.MAIN;
	}
	return View.SELECTNOTICEALL;	
}

public int selectnoticedetail() {
	String noticeno = noticeDao.selectnoticeall().get(snumber).get("NOTICE_NO").toString(); //선택한 행의 noticeno
	noticeDao.modifynotice(noticeno); // 선택알림의 상태를 읽음으로 변경
	System.out.println("작  성  자 : "+noticeDao.selectnoticedetail(noticeno).get("NOTICE_SENDER"));
	System.out.println("일      자 : "+noticeDao.selectnoticedetail(noticeno).get("NOTICE_DATE"));
	System.out.println("제      목 : "+noticeDao.selectnoticedetail(noticeno).get("NOTICE_TITLE"));
	System.out.println("내      용 : "+noticeDao.selectnoticedetail(noticeno).get("NOTICE_CONTENT"));
	System.out.println("------------------------------------------------------------------------");
	
	System.out.println("1. 삭제 0. 뒤로");
	int input = ScanUtil.nextInt();
	switch (input) {
	case 1 :
		 noticeDao.deletenotice(noticeno);
		System.out.println("메시지를 삭제하였습니다.");
		return View.SELECTNOTICEALL; 
	case 0:
		return View.SELECTNOTICEALL;
	}
	return View.SELECTNOTICEALL;	
}
	
	
	
	
	
	
}

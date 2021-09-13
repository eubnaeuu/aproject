package service;

import controller.Controller;
import util.ScanUtil;
import util.SetCommaTS;
import util.View;
import dao.CashDao;

public class CashService {
	
	
	public CashService(){}
	private static CashService instance;
	public static CashService getInstance(){
		if(instance == null){
			instance = new CashService();			
			}
			return instance;
		}
	
	private CashDao cashDao = CashDao.getInstance();
	private SetCommaTS setc = SetCommaTS.getInstance();
	
	/*
	 * Map<String, Object> mapUserId = Controller.LoginUser; String url =
	 * "jdbc:oracle:thin:@localhost:1521:xe"; String user = "iu"; String password =
	 * "java";
	 * 
	 * Connection con = null; PreparedStatement ps = null; ResultSet rs = null;
	 */	

	
	public int selectorderdetail(){

	
		int CashInfo = cashDao.CashDetail();
		
		System.out.println("======================================");
		System.out.println("=============보유캐시정보=================");
		System.out.println("현재 보유 캐시 : " +setc.Setcomma(Integer.toString(CashInfo)));
		
		System.out.println("1.캐시 충전 \t 0.돌아가기");
		
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1 : 
			System.out.println("======================================");
			System.out.println("충전금액을 입력해주세요>");
			int cashadd  = ScanUtil.nextInt();
			cashDao.chargecash(cashadd,Controller.LoginUser.get("MEM_ID").toString());
			System.out.println("은행이름 입력>");
			String tongjang1 = ScanUtil.nextLine();
			System.out.println("계좌번호 입력>"); 
			String tongjang2 = ScanUtil.nextLine();
			System.out.println(tongjang1+" "+tongjang2 + "계좌에서 "+setc.Setcomma(Integer.toString(cashadd)) + "원 만큼 인출합니다.");
			System.out.println("캐시충전이 완료되었습니다.");
			break;
		case 0 :
			return View.USERINFO;
		}
		
		return View.CASHMAIN;

	}

	public int selectcash(){ // 결제시 cash 조회

		
		int CashInfo = cashDao.CashDetail();
		
		System.out.println("======================================");
		System.out.println("=============보유캐시정보=================");
		System.out.println("현재 보유 캐시 : " +setc.Setcomma(Integer.toString(CashInfo)));
		
		System.out.println("1.캐시 충전 \t 0.돌아가기");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1 : return View.CASHCHARGE; 
		case 0 : return View.CARTLIST;
		}
		return View.CASHMAIN;
	}

	





public int cashcharge(){ // 캐시충전
System.out.println("충전금액을 입력해주세요>");
int cashadd  = ScanUtil.nextInt();
cashDao.chargecash(cashadd,Controller.LoginUser.get("MEM_ID").toString());
System.out.println("은행이름 입력>");
String tongjang1 = ScanUtil.nextLine();
System.out.println("계좌번호 입력>"); 
String tongjang2 = ScanUtil.nextLine();
System.out.println(tongjang1+" "+tongjang2 + "계좌에서 "+setc.Setcomma(Integer.toString(cashadd)) + "원 만큼 인출합니다.");
System.out.println("캐시충전이 완료되었습니다.");

return View.CASHMAIN;
}



}

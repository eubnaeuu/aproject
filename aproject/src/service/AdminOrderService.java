package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.ScanUtil;
import util.SetCommaTS;
import util.View;
import dao.AdminOrderDao;
import dao.NoticeDao;

public class AdminOrderService { 

	   private AdminOrderDao adminOrderDao =AdminOrderDao.getInstance();
	   
	   List<Map<String,Object>> templi = new ArrayList<>(); 
		HashMap<String, Object> temphm = new HashMap<>();
		int snumber; // 구분번호 선택시
		
	public AdminOrderService(){}
	private static AdminOrderService instance;
	public static AdminOrderService getInstance(){
		if(instance == null){
			instance = new AdminOrderService();			
			}
			return instance;
		}
	private SetCommaTS setc = SetCommaTS.getInstance();
	private NoticeDao noticeDao = NoticeDao.getInstance();

	
	public int  adminordermain (){
//		System.out.println("=======================================");
		System.out.println("---------------------------------------");
		System.out.println("1.매출분석 \t\t 2.배송관리 \t\t 0.뒤로");
		System.out.println("---------------------------------------");
	switch(ScanUtil.nextInt()) {
	case 1: return View.ADMINANALYSIS; 
	case 2: return View.ADMINORDERSELECT; //2. 배송관리 (주문목록조회 -> 배송결정)
	case 0: return View.ADMINMAIN;// 뒤로 
	}
	return View.ADMINORDERMAIN;
}
	// (기간별)주문수, 주문상품수, 평균주문금액
	// (성별) 1년기준, 매출금액, 평균주문금액
	// (나이별)(10대,20대,30대,40대)

	public int adminanalysis(){ //매출분석 adminanalysis
		System.out.println("=======================================");
	    System.out.println("                                    매   출   분   석                            ");
	  	System.out.println("=======================================");
	  	System.out.println("1. 기간별 \t\t 2. 연령별 \t\t 0. 뒤로");
	  	System.out.println("입력>");
	  	switch(ScanUtil.nextInt()){
	  	case 1: return View.ADMINANALYSISDAYMAIN;
	  	case 2: return View.ADMINANALYSISAGE;	  		
	  	case 0: return View.ADMINORDERMAIN;	  		
	  	}
	  	return View.ADMINANALYSIS; //임시
	}	
	public int adminanalysisday(){ 
		System.out.println("=======================================");
	    System.out.println("                                    매   출   분   석                            ");
	  	System.out.println("=======================================");
	  	System.out.println("1. 월별 \t\t 2. 연도별");
	  	String day;
		switch (ScanUtil.nextInt()) {
		case 1: return View.ADMINANALYSISMONTH;
		case 2: return View.ADMINANALYSISYEAR;
		}
	  	System.out.println("0.뒤로");
	  	ScanUtil.nextInt();
	  	System.out.println();
	  	return View.ADMINANALYSISDAYMAIN;
	}
	public int adminanalysisage(){
		
		System.out.println("=======================================");
	    System.out.println("                                 나       이       별                             ");
	  	System.out.println("=======================================");
	  	System.out.println("나이대\t\t인원수\t\t주문수\t\t평균주문(1인)\t\t매출\t\t평균매출(1인)");
	  	System.out.println("-------------------------------------------------------------------"); // ★ 수정(i 출력 삭제)
	  	for(int i=0; i<adminOrderDao.analysisage().size(); i++){
	  	System.out.print(adminOrderDao.analysisage().get(i).get("나이대")+"\t\t");
	  	System.out.print(adminOrderDao.analysisage().get(i).get("인원수")+"\t\t");
	  	System.out.print(adminOrderDao.analysisage().get(i).get("주문수")+"\t\t");
	  	System.out.print(adminOrderDao.analysisage().get(i).get("인당평균주문수")+"\t\t");
	  	System.out.print(setc.Setcomma(adminOrderDao.analysisage().get(i).get("총매출").toString())+"\t\t");
	  	System.out.print(setc.Setcomma(adminOrderDao.analysisage().get(i).get("인당매출").toString())+"\n");
		}
	  	System.out.println("-----------------------------------------------------------------------");
		System.out.println("0. 뒤로");
		ScanUtil.nextLine();
		return View.ADMINANALYSIS;
	}
	public int adminanalysismonth(){ // view.adminalysismonth
		System.out.println("=======================================");
	    System.out.println("                                 기       간       별                             ");
	  	System.out.println("=======================================");
	    System.out.println("기간\t\t주문수\t\t주문평균금액\t\t회원수\t\t회원당주문수\t\t매출");
	  	System.out.println("-------------------------------------------------------------------"); // ★ 수정(i 출력 삭제)
		for(int i=0; i<adminOrderDao.analysismonth().size(); i++){
	

		  	System.out.print(adminOrderDao.analysismonth().get(i).get("연도")+"년 ");
		  	System.out.print(adminOrderDao.analysismonth().get(i).get("개월")+"월\t\t");
		  	System.out.print(adminOrderDao.analysismonth().get(i).get("주문수")+"개\t\t");
		  	System.out.print(setc.Setcomma(adminOrderDao.analysismonth().get(i).get("주문평균금액").toString())+"원\t\t");
		  	System.out.print(adminOrderDao.analysismemnum().size()+"명\t\t");//회원수
		  	System.out.print(adminOrderDao.analysismonth().get(i).get("회원당주문수")+"개\t\t");
		  	System.out.print(setc.Setcomma(adminOrderDao.analysismonth().get(i).get("매출").toString())+"원\n");
		}
		System.out.println("-----------------------------------------------------------------------");
		System.out.println("0. 뒤로");
		ScanUtil.nextLine();
		return View.ADMINANALYSIS;
	}
	public int adminanalysisyear(){// view.adminalysisyear
	  	
		System.out.println("=======================================");
	    System.out.println("                                 기       간       별                             ");
	  	System.out.println("=======================================");
	    System.out.println("기간\t\t주문수\t\t주문평균금액\t\t회원수\t\t회원당주문수\t\t매출");
	  	System.out.println("-------------------------------------------------------------------");              // ★ 수정(i 출력 삭제)
		for(int i=0; i<adminOrderDao.analysisyear().size(); i++){


		  	System.out.print(adminOrderDao.analysisyear().get(i).get("기간")+"년\t\t");
		  	System.out.print(adminOrderDao.analysisyear().get(i).get("주문수")+"개\t\t");
		  	System.out.print(setc.Setcomma(adminOrderDao.analysisyear().get(i).get("주문평균금액").toString())+"원\t\t");
		  	System.out.print(adminOrderDao.analysisyear().size()+"명\t\t");//회원수
		  	System.out.print(adminOrderDao.analysisyear().get(i).get("회원당주문수")+"개\t\t");
		  	System.out.print(setc.Setcomma(adminOrderDao.analysisyear().get(i).get("매출").toString())+"원  \n");
		}
		return View.ADMINANALYSIS;
	}
	
	
	public int adminorderselect(){
	    System.out.println("============================주문목록============================");
	    System.out.println("구분 \t 주문일 \t 아이디 \t 주문번호 \t 주문상품 \t 주문상태");
	  	System.out.println("=============================================================");
	  		templi = new ArrayList<>(); 		
//	  	System.out.println("size = "+adminOrderDao.adminOrderSelect().size()); // 주문목록의 개수
	  	for(int i=0; i<adminOrderDao.adminOrderSelect().size(); i++){
	  		temphm = new HashMap<>();
	  		temphm.put("ORDER1_NO", adminOrderDao.adminOrderSelect().get(i).get("ORDER1_NO").toString());
	  		templi.add((temphm));
	  	System.out.print("["+i+"]\t");//구분
	            System.out.print(adminOrderDao.adminSelectDate().get(i).get("PRODDATE")+"  "); // 주문일(00-00-00) 
	            System.out.print(adminOrderDao.adminOrderSelect().get(i).get("MEM_ID")+"  ");//아이디                     //★ 수정
	  			System.out.print(adminOrderDao.adminOrderSelect().get(i).get("ORDER1_NO")+"  "); // 주문번호
	  			String orderno = adminOrderDao.adminOrderSelect().get(i).get("ORDER1_NO").toString();  
	  			int size = adminOrderDao.adminSelectOrderdetail(orderno).size();
//	  			System.out.println("주문번호의 주문상품 개수 : " +size);
	  			if(size > 1){ // 주문상품명에 주문개수 표현
	  			System.out.print(adminOrderDao.adminSelectOrderdetail(orderno).get(0).get("PROD_NAME")+" 외 "+(size-1)+"\t"); //주문상품
	  			} else {
	  			System.out.print(adminOrderDao.adminSelectOrderdetail(orderno).get(0).get("PROD_NAME")+"\t"); //주문상품
	  			}
	  			System.out.println(adminOrderDao.adminOrderSelect().get(i).get("ORDER1_STATUS")); // 주문상태
	  	}
	  	System.out.println("--------------------------------------------------------------------------------");
	      System.out.println("1.배송관리 \t0.돌아가기");
	      System.out.println("입력>");
	      
	      int input = ScanUtil.nextInt();
	      switch (input) {
	      case 1 : {
	    	  System.out.println("구분번호입력>");// 주문상세조회
	    	  snumber = ScanUtil.nextInt();//구분번호 저장
	    	  return View.ADMINORDERINFO;
	    	  }
	      case 0 : return View.ADMINORDERMAIN; // 돌아가기
	      }
	      return View.ADMINORDERSELECT;
	   }
	
	public int adminSelectOrderdetail(){//view.adminorderinfo
	    System.out.println("================주문목록================");
	    System.out.println("주문수량 \t\t 주문금액\t\t주문상품");
	  	System.out.println("=======================================");
	  		 		
//	  	System.out.println("size = "+selectorderDao.selectorder().size());
	  	String orderno = templi.get(snumber).get("ORDER1_NO").toString();	  	// snumber행의 ORDER1_NO
	  	templi = new ArrayList<>();
	  	for(int i=0; i<adminOrderDao.adminSelectOrderdetail(orderno).size(); i++){
	  		temphm = new HashMap<>();
	  		System.out.print(adminOrderDao.adminSelectOrderdetail(orderno).get(i).get("ORDERDETAIL_QTY")+"개\t\t");
	  		System.out.print(setc.Setcomma(adminOrderDao.adminSelectOrderdetail(orderno).get(i).get("PROD_SALE").toString())+"\t\t");
	  		System.out.print(adminOrderDao.adminSelectOrderdetail(orderno).get(i).get("PROD_NAME")+"\n");
	  		temphm.put("PROD_ID",adminOrderDao.adminSelectOrderdetail(orderno).get(i).get("PROD_ID").toString()); // 글번호 와 해당 prodid 연동
	  		templi.add((temphm));
	  	}
//	      System.out.println("0.돌아가기");
//	      System.out.println("입력>");
//	  	ScanUtil.nextInt();
//	      return View.ADMINORDERSELECT;
return View.ADMINORDERDELIVER;
	}	      

	public int adminorderdeliver(){ //View.ADMINORDERINFO
	  	System.out.println("1.배송출발 \t 2.재배송 \t 3.환불완료 \t 0.뒤로"); // 1.결제완료 2.교환신청 3.반품신청
	      System.out.println("입력>");
	      int input = ScanUtil.nextInt();
	      // adminOrderDao.adminOrderSelect().get(input).get("ORDER1_NO").toString(); 선택한 행의 orderno값 
	      switch (input) {
	      case 1 : {
	    	if(adminOrderDao.adminOrderSelect().get(snumber).get("ORDER1_STATUS").toString().equals("결제완료")){
	    		System.out.println("[결제완료] -> [배송출발] 변경완료 ");
	    		System.out.println("주문목록조회로 돌아갑니다.");
	    		adminOrderDao.modifydeliverstatus(adminOrderDao.adminOrderSelect().get(snumber).get("ORDER1_NO").toString(), "배송출발");
	    		String title ="주문하신 상품의 배송이 시작되었습니다";
	    		String content ="예상 도착일은 2/27(토) 입니다. ";
	    		noticeDao.sendnotice(title, content, adminOrderDao.adminOrderSelect().get(snumber).get("MEM_ID").toString());
	    	} else {
	    		System.out.println("[배송출발]은 [결제완료]일때만 가능합니다.");
	    		System.out.println("주문목록조회로 돌아갑니다.");
	    	}
	    	return View.ADMINORDERSELECT;
	      }
	      case 2 : {
		    	if(adminOrderDao.adminOrderSelect().get(snumber).get("ORDER1_STATUS").toString().equals("교환신청")){
		    		System.out.println("[교환신청] -> [재배송] 변경완료 ");
		    		System.out.println("주문목록조회로 돌아갑니다.");
		    		adminOrderDao.modifydeliverstatus(adminOrderDao.adminOrderSelect().get(snumber).get("ORDER1_NO").toString(), "재배송");
		    	} else {
		    		System.out.println("[재배송]은 [교환신청]일때만 가능합니다.");
		    		System.out.println("주문목록조회로 돌아갑니다.");
		    	}
		    	return View.ADMINORDERSELECT;
	      }
	      case 3 : {
		    	if(adminOrderDao.adminOrderSelect().get(snumber).get("ORDER1_STATUS").toString().equals("반품신청")){
		    		System.out.println("[반품신청] -> [환불완료] 변경완료 ");
		    		System.out.println("주문목록조회로 돌아갑니다.");
		    		adminOrderDao.modifydeliverstatus(adminOrderDao.adminOrderSelect().get(snumber).get("ORDER1_NO").toString(), "환불완료");
		    		int ordercost = Integer.valueOf(adminOrderDao.adminOrderSelect().get(snumber).get("ORDER1_COST").toString()); // 주문금액
		    		String memid = adminOrderDao.adminOrderSelect().get(snumber).get("MEM_ID").toString();//아이디
		    		adminOrderDao.modifydeliverstatus2(ordercost, memid); // 캐시 돌려줌
		    		System.out.println("(+)"+setc.Setcomma(Integer.toString(ordercost))+"캐시로 환불완료");
		    		String title ="요청하신 환불이 정상적으로 처리되었습니다";
		    		String content ="(+)"+setc.Setcomma(Integer.toString(ordercost))+"캐시를 돌려드립니다.";
		    		noticeDao.sendnotice(title, content, memid);
		    	} else {
		    		System.out.println("[환불완료]는 [반품신청]일때만 가능합니다.");
		    	}
		    	return View.ADMINORDERSELECT;
	    	  }
//	      case 0 : return View.ADMINORDERINFO; // 돌아가기
	      case 0 : return View.ADMINORDER;
	      }
	      return View.ADMINORDERINFO;
	   }
   
}

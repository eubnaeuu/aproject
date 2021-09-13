package service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.AdminOrderDao;
import dao.SelectOrderDao;
import util.JDBCUtil;
import util.ScanUtil;
import util.View;

public class SelectOrderService {
   
   JDBCUtil jdbc = JDBCUtil.getInstance();
   private SelectOrderDao selectorderDao = SelectOrderDao.getInstance();
   private AdminOrderDao adminOrderDao = AdminOrderDao.getInstance();
//   private NoticeDao noticeDao = NoticeDao.getInstance();
   public SelectOrderService(){} 
   private static SelectOrderService instance;
   public static SelectOrderService getInstance(){
      if(instance == null){
         instance = new SelectOrderService();         
         }
         return instance;
      }
   
   List<Map<String,Object>> templi = new ArrayList<>(); 
	HashMap<String, Object> temphm = new HashMap<>();
	int snumber; // 구분번호 선택시
   
   public int selectorder(){
	   for(int i=0; i<selectorderDao.selectorder().size(); i++){ // 시연용 (관리자 배송출발후 사용자가 오더목록 조회시 배송완료로 바꿈)
		   if(selectorderDao.selectorder().get(i).get("ORDER1_STATUS").toString().equals("배송출발")){
			   adminOrderDao.modifydeliverstatus(selectorderDao.selectorder().get(i).get("ORDER1_NO").toString(),"배송완료");
		   }
	   }
    System.out.println("================주문목록================");
    System.out.println("구분 \t \t 주문일 \t \t 주문번호 \t \t 주문상품 \t \t 주문상태");
  	System.out.println("=======================================");
  		templi = new ArrayList<>(); 		
//  	System.out.println("size = "+selectorderDao.selectorder().size()); // 주문목록의 개수
  	for(int i=0; i<selectorderDao.selectorder().size(); i++){
  		temphm = new HashMap<>();
  		temphm.put("ORDER1_NO", selectorderDao.selectorder().get(i).get("ORDER1_NO").toString());
  		templi.add((temphm));
  	System.out.print("["+i+"]\t");//구분
            System.out.print(selectorderDao.selectdate().get(i).get("PRODDATE")+"  ");  	
  			System.out.print(selectorderDao.selectorder().get(i).get("ORDER1_NO")+"  "); // 주문번호
  			String orderno = selectorderDao.selectorder().get(i).get("ORDER1_NO").toString(); //order1_no
  			int size = selectorderDao.selectorderdetail(orderno).size();
//  			System.out.print("주문번호의 주문상품 개수 : " +size);
  			if(size > 1){ // 주문상품명에 주문개수 표현
  			System.out.print("\t"+selectorderDao.selectorderdetail(orderno).get(0).get("PROD_NAME")+" 외 "+(size-1)+"\t"); //주문상품
  			} else {
  			System.out.print("\t"+selectorderDao.selectorderdetail(orderno).get(0).get("PROD_NAME")+"\t"); //주문상품
  			}
  			System.out.println("\t"+selectorderDao.selectorder().get(i).get("ORDER1_STATUS")); // 주문상태
  	}
      System.out.println("1.주문상세조회 2. 구매확정 3. 반품신청 \t0.돌아가기");
      System.out.println("입력>");
      
      int input = ScanUtil.nextInt();
      switch (input) {
      case 1 : {
    	  System.out.println("구분번호입력>");// 주문상세조회
    	  snumber = ScanUtil.nextInt();
    	  return View.SELECTORDERDETAIL;
    	  }
      case 2 : {
    	  System.out.println("구분번호입력>");// 구매확정
    	  snumber = ScanUtil.nextInt();
    	  if(selectorderDao.selectorder().get(snumber).get("ORDER1_NO").toString().equals("구매확정")){
    		  System.out.println("이미 [구매확정] 하셨습니다. 주문조회로 돌아갑니다.");
    	  } else{
    		  adminOrderDao.modifydeliverstatus(selectorderDao.selectorder().get(snumber).get("ORDER1_NO").toString(),"구매확정"); // 주문상태 변경
    		   System.out.println("선택하신 주문의 상태가 [구매확정]으로 변경되었습니다.");
    	  }
    	  break;
    	  }
      case 3 : {
    	  System.out.println("구분번호입력>");// 구매확정
    	  snumber = ScanUtil.nextInt();
    	  if(selectorderDao.selectorder().get(snumber).get("ORDER1_NO").toString().equals("반품신청")){
    		  System.out.println("이미 [반품신청] 하셨습니다. 주문조회로 돌아갑니다.");
    	  } else{
    		  adminOrderDao.modifydeliverstatus(selectorderDao.selectorder().get(snumber).get("ORDER1_NO").toString(),"반품신청"); // 주문상태 변경
    		   System.out.println("선택하신 주문의 상태가 [반품신청]으로 변경되었습니다.");
    	  }
    	  break;
    	  }
      case 0 : return View.USERINFO; // 돌아가기
      }
      return View.SELECTORDER;
   }
   
   
   public int selectorderdetail(){
	    System.out.println("================주문목록================");
	    System.out.println("구분 \t 주문상품 \t 주문금액 \t 주문수량");
	  	System.out.println("=======================================");
	  		 		
//	  	System.out.println("size = "+selectorderDao.selectorder().size());
	  	String orderno = selectorderDao.selectorder().get(snumber).get("ORDER1_NO").toString();	  	// snumber행의 ORDER1_NO

	  	templi = new ArrayList<>();
	  	for(int i=0; i<selectorderDao.selectorderdetail(orderno).size(); i++){
	  		temphm = new HashMap<>();
	  		System.out.print("["+i+"]\t");//구분
	  		System.out.print(selectorderDao.selectorderdetail(orderno).get(i).get("PROD_NAME")+"  ");
	  		System.out.print(selectorderDao.selectorderdetail(orderno).get(i).get("PROD_SALE")+"  ");
	  		System.out.println(selectorderDao.selectorderdetail(orderno).get(i).get("ORDERDETAIL_QTY"));
	  		temphm.put("PROD_ID", selectorderDao.selectorderdetail(orderno).get(i).get("PROD_ID").toString()); // 글번호 와 해당 prodid 연동
	  		templi.add((temphm));
	  	}
	      System.out.println("0.돌아가기");
	      System.out.println("입력>");
	      ScanUtil.nextInt();
	      return View.SELECTORDER;
   }

}
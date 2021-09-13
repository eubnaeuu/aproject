package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;
import controller.Controller;

public class SelectOrderDao {
   
   private SelectOrderDao(){}
   private static SelectOrderDao instance;
   public static SelectOrderDao getInstance(){
      if(instance == null){
         instance = new SelectOrderDao();   
         }
         return instance;
      }
   
   private static JDBCUtil jdbc = JDBCUtil.getInstance();

   public  List<Map<String, Object>> selectorder() {  // 주문조회(order) 
		String sql = "SELECT ORDER1_NO, MEM_ID, ORDER1_STATUS, ORDER1_COST "
				+ " FROM ORDER1 "
				+ " WHERE MEM_ID = ? "
				+ " ORDER BY 1 DESC"; // 최근이 가장 위에
		List<Object> param = new ArrayList<>();
		param.add(Controller.LoginUser.get("MEM_ID").toString());
		return jdbc.selectList(sql, param);
	}
   
   public  List<Map<String, Object>> selectdate() {  // 주문번호에서 주문일자 추출 
		String sql = "SELECT SUBSTR(ORDER1_NO,1,2)||'-'||SUBSTR(ORDER1_NO,3,2)||'-'||SUBSTR(ORDER1_NO,5,2) PRODDATE "
				+ " FROM ORDER1 "
				+ " WHERE MEM_ID = ?"
				+ " ORDER BY 1 DESC";
		List<Object> param = new ArrayList<>();
		param.add(Controller.LoginUser.get("MEM_ID").toString());
		return jdbc.selectList(sql, param);
	}
  
   public static List<Map<String, Object>> selectorderdetail(String orderno) { // 다중행
      
      String sql = "SELECT O.ORDER1_NO, O.ORDER1_COST, OD.PROD_ID, P.PROD_NAME, OD.ORDERDETAIL_QTY, P.PROD_SALE "
      		+ " FROM ORDER1 O "
      		+ " INNER JOIN ORDERDETAIL OD ON OD.ORDER1_NO = O.ORDER1_NO"
      		+ " INNER JOIN PROD P ON P.PROD_ID = OD.PROD_ID "
      		+ " WHERE O.MEM_ID=?"
      		+ " AND O.ORDER1_NO = ?";
      List<Object> param = new ArrayList<>();
      param.add(Controller.LoginUser.get("MEM_ID").toString());
      param.add(orderno);
      return jdbc.selectList(sql, param);

   }
   
   public int modifydeliverstatus(String orderno, String status) {        // 배송상태변경 
	      String sql = "UPDATE ORDER1 "
	      		+ "SET ORDER1_STATUS = ?  "
	      		+ "WHERE ORDER1_NO = ?";
	      List<Object> param = new ArrayList<>();
	      param.add(status);
	      param.add(orderno);
	    return jdbc.update(sql, param);		         
	   }
   
   

}
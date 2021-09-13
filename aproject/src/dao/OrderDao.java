package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import service.OrderService;
import util.JDBCUtil;
import controller.Controller;

public class OrderDao {

	
	private OrderDao(){}
	private static OrderDao instance;
	public static OrderDao getInstance(){
		if(instance == null){
			instance = new OrderDao();	
			}
			return instance;
		}
	
	private static JDBCUtil jdbc = JDBCUtil.getInstance();
	private static OrderService orderservice = OrderService.getInstance();
	

	
	public List<Map<String, Object>> selectorderdetail() {
		String sql = " SELECT CD.CARTDETAIL_NO, M.MEM_NAME, M.MEM_ADD1, "
				+ " M.MEM_ADD2, M.MEM_HP, M.MEM_CASH, P.PROD_NAME, P.PROD_SALE, CD.CART_QTY "
				+ " FROM CARTDETAIL CD,  MEMBER M, CART C, PROD P "
				+ " WHERE  CD.CART_ID(+) = C.CART_ID AND M.MEM_ID  = C.MEM_ID "
				+ " AND P.PROD_ID = CD.PROD_ID AND C.MEM_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(Controller.LoginUser.get("MEM_ID").toString());
		return jdbc.selectList(sql,param);

	}
	
	
	
	
	public List<Map<String, Object>> ordercost() { // 주문 총금액 구하는 메서드
		String sql = "SELECT SUM(CD.CART_QTY*P.PROD_SALE) ORDERCOST FROM   CARTDETAIL CD,  MEMBER M, CART C, PROD P WHERE  CD.CART_ID = C.CART_ID  AND M.MEM_ID  = C.MEM_ID AND P.PROD_ID = CD.PROD_ID  AND C.MEM_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(Controller.LoginUser.get("MEM_ID").toString());
		return jdbc.selectList(sql, param);
	}
	
	
	
	   public void modifyshipping(String add1, String add2) {
		      String sqladd1 = "UPDATE MEMBER SET MEM_ADD1 = ?  WHERE MEM_ID = ?";
		      
		      List<Object> paramADD1 = new ArrayList<>();
		      paramADD1.add(add1);
		      paramADD1.add(Controller.LoginUser.get("MEM_ID").toString());
		      
		      int updateADD1 = jdbc.update(sqladd1, paramADD1);
		      System.out.println(updateADD1);
		      
		      String sqladd2 = "UPDATE MEMBER SET MEM_ADD2 = ?  WHERE MEM_ID = ?";
		      
		      List<Object> paramADD2 = new ArrayList<>();
		      paramADD2.add(add2);
		      paramADD2.add(Controller.LoginUser.get("MEM_ID").toString());
		      
		      int updateADD2 = jdbc.update(sqladd2, paramADD2);
		      System.out.println(updateADD2);
		         
		   }
	   
	public void chargecash(int membercash) {
		String sql = "UPDATE MEMBER SET MEM_CASH = NVL(MEM_CASH,0) + ?  WHERE MEM_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(membercash);
		param.add(Controller.LoginUser.get("MEM_ID").toString());
		
		int update = jdbc.update(sql, param);
//		System.out.println(update);
		
	}
	
	

	



	public int minusstock(int cartqty, String prodid) {
	      // 재고 빼기
	      String sql = "UPDATE PROD "
	      		+ " SET PROD_TOTALSTOCK = (PROD_TOTALSTOCK - ?)  "
	      		+ " WHERE PROD_ID = ?";
	      List<Object> param = new ArrayList<>();
//	      paramstock.add(1);
//	      param.add("P101000010");
	      param.add(cartqty);
	      param.add(prodid);
	      return jdbc.update(sql,param);
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public int minuscash(int prodsale) {
		// 캐쉬 빼기
		String sql = "UPDATE MEMBER "
				+ " SET MEM_CASH = (MEM_CASH - ?)  "
				+ " WHERE MEM_ID = ?";
	      List<Object> param = new ArrayList<>();
	      param.add(prodsale);
	      param.add(Controller.LoginUser.get("MEM_ID").toString());
	      return jdbc.update(sql, param);
	}
	
	public int order3() { // 상품 주문시 cart -> order (정보 넣기)
	      String sqlorder = "INSERT INTO ORDER1(ORDER1_NO, MEM_ID, ORDER1_STATUS, ORDER1_COST) "
	      		+ " VALUES(CONCAT(TO_CHAR(SYSDATE,'YYMMDDHHMISS'),?), ?  , '결제완료', ?)";
	      List<Object> paramorder = new ArrayList<>();
	      paramorder.add(Controller.LoginUser.get("MEM_ID").toString());
	      paramorder.add(Controller.LoginUser.get("MEM_ID").toString());
	      paramorder.add(Integer.valueOf(String.valueOf(ordercost().get(0).get("ORDERCOST")))); // 주문총금액 , number형변환 오류남 -> 구글링(스트링변환하여 int변환해라)			
	 return jdbc.update(sqlorder,paramorder); 
	}
	
	public List<Map<String, Object>> selectorder() {
	      String sql = "SELECT ORDER1_NO FROM ORDER1 WHERE MEM_ID = ?";
	      List<Object> paramorder = new ArrayList<>();
	      paramorder.add(Controller.LoginUser.get("MEM_ID").toString());
	 return jdbc.selectList(sql,paramorder);
	}
	public int test2(String str2, int in1) {  // 상품 주문시 cartdetail -> orderdetail (정보 넣기)
		String sql = "INSERT INTO ORDERDETAIL(ORDERDETAIL_NO, ORDER1_NO, PROD_ID, ORDERDETAIL_QTY) "
				+ " VALUES(ORDERDETAIL_SEQ.NEXTVAL, (select max(order1_no)  from order1  where mem_id = ?) , ?, ?)";
		List<Object> param = new ArrayList<>();
//		param.add("210224034928cdw34"); // order1no
		param.add(Controller.LoginUser.get("MEM_ID").toString());
//		param.add("P101000010"); // prodid
//		param.add(1); // qty
		 
//		param.add(str1);
		param.add(str2);
		param.add(in1);
		return jdbc.update(sql, param);
	}
	
	public  Map<String, Object> selectordernoone () {  // 로그인멤버의 가장 최근 주문번호 조회 
		String sql = "select max(order1_no) from order1 where mem_id = ?";
		List<Object> param = new ArrayList<>();
		param.add(Controller.LoginUser.get("MEM_ID").toString());
		return jdbc.selectOne(sql, param);
	}
   }


	





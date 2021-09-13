package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.Controller;
import util.JDBCUtil;
import util.View;

public class CartDao {
	
		private CartDao(){}
		private static CartDao instance;
		public static CartDao getInstance(){
			if(instance == null){
				instance = new CartDao();
			}
			return instance;
		}
	
		private JDBCUtil jdbc = JDBCUtil.getInstance();
		
		public List<Map<String, Object>> selectCartList(){ // 다중행
			String sql = "select a.CARTDETAIL_NO, a.CART_ID, a.CART_QTY, "
					+ " b.PROD_NAME, b.PROD_SALE, b.PROD_ID "
					+ " from CARTDETAIL a, PROD b "
					+ " where a.PROD_ID = b.PROD_ID "
					+ " and a.CART_ID = ? ";
			List<Object> p = new ArrayList<>();
			p.add(Controller.LoginUser.get("MEM_ID").toString()+"cart");
			return jdbc.selectList(sql, p);
		}
		
		public int deleteCartList(Map<String, Object> param){
			String sql = "DELETE FROM CARTDETAIL WHERE CARTDETAIL_NO = ?";
				List<Object> p = new ArrayList<>();
				p.add(param.get("CARTDETAIL_NO"));
				
				return jdbc.update(sql, p);
		}
		public int deleteCartList2(){
			String sql = "DELETE FROM CARTDETAIL WHERE CART_ID = ?";
            List<Object> p = new ArrayList<>();
            p.add(Controller.LoginUser.get("MEM_ID").toString()+"cart");
            return jdbc.update(sql, p);
		}
}

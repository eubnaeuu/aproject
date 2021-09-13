package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;
import controller.Controller;

public class AdminReviewDao {
	private AdminReviewDao(){}
	private static AdminReviewDao instance;
	public static AdminReviewDao getInstance(){
		if(instance == null){
			instance = new AdminReviewDao();	
			}
			return instance;
		}

	
	private static JDBCUtil jdbc = JDBCUtil.getInstance();
	
	
	public List<Map<String, Object>> ReviewList() {
		String sql = "SELECT R.REVIEW_NO A , (SELECT P.PROD_NAME FROM PROD P WHERE P.PROD_ID = R.PROD_ID ) B , "
				+ " RATING C , CONTENT D "
				+ " FROM REVIEW R ";

		
		return jdbc.selectList(sql);
		
	}
	
	public void deletereview(int num){
		String sql = "DELETE FROM REVIEW WHERE REVIEW_NO = ? ";
		
		List<Object> param = new ArrayList<>();
		param.add(num);
		
		
		jdbc.update(sql, param);

	}
	
	

}

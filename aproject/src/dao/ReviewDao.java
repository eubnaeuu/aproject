package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import util.JDBCUtil;

public class ReviewDao {
	
	private ReviewDao(){}
	private static ReviewDao instance;
	public static ReviewDao getInstance(){
		if(instance == null){
			instance = new ReviewDao();	
			}
			return instance;
		}
	
	private static JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public List<Map<String, Object>> ChoiceReview() {
		String sql = "SELECT OD.PROD_ID, (SELECT P.PROD_NAME FROM PROD P WHERE P.PROD_ID  = OD.PROD_ID) A , COUNT(OD.PROD_ID) "
				+ " FROM ORDERDETAIL OD, ORDER1 O"
				+ " WHERE OD.ORDER1_NO = O.ORDER1_NO"
				+ " AND O.MEM_ID = ? "
				+ " GROUP BY OD.PROD_ID, O.ORDER1_STATUS";

		
		List<Object> param = new ArrayList<>();
		param.add(Controller.LoginUser.get("MEM_ID").toString());
		return jdbc.selectList(sql,param);
		
	}
	
	

	public List<Map<String, Object>> ReviewList() {
		String sql = "SELECT R.REVIEW_NO A , (SELECT P.PROD_NAME FROM PROD P WHERE P.PROD_ID = R.PROD_ID ) B , "
				+ " RATING C , CONTENT D "
				+ " FROM REVIEW R "
				+ " WHERE MEM_ID = ?";
	
		List<Object> param = new ArrayList<>();
		param.add(Controller.LoginUser.get("MEM_ID").toString());
		
		return jdbc.selectList(sql, param);
		
	}
	

	
	public void WriteReview(String num, String rate, String comment) {
		String sqlrate = "INSERT INTO REVIEW(REVIEW_NO,MEM_ID, PROD_ID, RATING, CONTENT) "
				+ " VALUES(REVIEW_SEQ.NEXTVAL , ? , "
				+ " ? ,? ,?)";

		//삽입되는 내용 : (리뷰번호-시퀀스+ , 현재로그인 아이디, 현재로그인아이디가 제일 최근에 업데이트한 주문의 상품코드, 별점, 코멘트)
		List<Object> paramrate = new ArrayList<>();
		paramrate.add(Controller.LoginUser.get("MEM_ID").toString());
		paramrate.add(num);
		paramrate.add(rate);
		paramrate.add(comment);
		
		jdbc.update(sqlrate, paramrate);

		
		
	}
	
	public void deletereview(int num){
		String sql = "DELETE FROM REVIEW WHERE REVIEW_NO = ? AND MEM_ID = ? ";
		
		List<Object> param = new ArrayList<>();
		param.add(num);
		param.add(Controller.LoginUser.get("MEM_ID").toString());
		
		
		jdbc.update(sql, param);

	}

	






	


	

}

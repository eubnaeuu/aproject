package dao;

import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class BoardDao { 

	private BoardDao(){}
	private static BoardDao instance;
	public static BoardDao getInstance(){
		if(instance == null){
			instance = new BoardDao();
		}
		return instance;
	}
	
	private JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public List<Map<String, Object>> selectBoardList(){
		String sql = "select PROD_NAME, PROD_SALE"
				+ " from PROD"
				+ " where prod_sale < 50000";
/*				+ " left outer join PROD b"
				+ " on a.PROD_id = b.user_id"
				+ " order by a.board_no desc";
*/		
		return jdbc.selectList(sql);
	}
	
}













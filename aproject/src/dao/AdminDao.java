package dao;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import util.JDBCUtil;
public class AdminDao {
		private AdminDao(){}
		private static AdminDao instance;
		public static AdminDao getInstance(){
			if(instance == null){
				instance = new AdminDao();
			}
			return instance;
		}
		
		private JDBCUtil jdbc = JDBCUtil.getInstance();
		
		public Map<String, Object> Adminselect(String userId) {
			String sql = "select * from MEMBER where MEM_ID = ?";
			List<Object> param = new ArrayList<>();
			param.add(userId);
			return jdbc.selectOne(sql, param);
		}
		
		
		public List<Map<String, Object>> AdminUserInfo() {
			String sql = "select * from MEMBER";
			
			return jdbc.selectList(sql);
			
		}
		
		public List<Map<String, Object>> prodList(){
			String sql = "SELECT A.LPROD_GU AS A\r\n"
					+ ",SUBSTR(A.PROD_ID,8,4) AS A2\r\n"
					+ ",B.LPROD_NM AS B\r\n"
					+ ",SUBSTR(A.PROD_NAME,6,7) AS C\r\n"
					+ ",PROD_SALE AS D\r\n"
					+ ",PROD_TOTALSTOCK AS E\r\n"
					+ "FROM PROD A, LPROD B\r\n"
					+ "WHERE A.LPROD_GU = B.LPROD_GU";
			return jdbc.selectList(sql);	
		
	}
		public List<Map<String, Object>> selectList(String input) {   // 상품명검색(다중행 (조건))  
			String sql = " SELECT * "
					+ " FROM PROD P "
					+ " INNER JOIN SALEDETAIL SD ON (P.PROD_ID = SD.PROD_ID) "
					+ " INNER JOIN SALE S ON (S.SALE_NO = SD.SALE_NO) "
					+ " WHERE SALE_TITLE LIKE UPPER('%'||?||'%')";  // ★시발 이렇게 해야함
			List<Object> param = new ArrayList<>();
			param.add(input);    // ★  해결!
			return jdbc.selectList(sql, param);
		}
		public List<Map<String, Object>> selectSaleNo(String input) {   // 입력한 게시글번호의 상품들 검색 (다중행) 
			String sql = " SELECT P.PROD_NAME, P.PROD_TOTALSTOCK, P.PROD_SALE, P.PROD_ID, S.SALE_TITLE, S.SALE_NO, P.PROD_DETAIL, P.PROD_INFO"
					+ " FROM SALEDETAIL SD "
					+ " INNER JOIN PROD P ON P.PROD_ID = SD.PROD_ID "
					+ " INNER JOIN SALE S ON S.SALE_NO = SD.SALE_NO "
					+ " WHERE S.SALE_NO = UPPER(?)"; // TEST 
			List<Object> param = new ArrayList<>();
			param.add(input);   //  
			return jdbc.selectList(sql, param);
		}
		
		public int insertProd(Map<String, Object> param){
			String sql = "insert into PROD values (?, ?, ?, ?, ?, ?, ?)";
			
			List<Object> p = new ArrayList<>();
			p.add(param.get("PROD_ID"));
			p.add(param.get("LPROD_GU"));
			p.add(param.get("PROD_NAME"));
			p.add(param.get("PROD_TOTALSTOCK"));
			p.add(param.get("PROD_SALE"));
			p.add(param.get("PROD_IMG"));
			p.add(param.get("PROD_DETAIL"));
			
			return jdbc.update(sql, p);
		}
		public int deleteProd(Map<String, Object> param){
			
			String sql = "delete from PROD "
					+ "where PROD_ID = UPPER(?)";
			List<Object> p = new ArrayList<>();
			p.add(param.get("PROD_ID"));

			
			return jdbc.update(sql, p);
		}
		
		public int updateProd(Map<String, Object> param){
			String sql = " UPDATE PROD "
					+ " set PROD_NAME = ?, "
					+ " PROD_TOTALSTOCK = ?, "
					+ " PROD_SALE = ?, "
					+ " PROD_DETAIL = ? "
					+ " WHERE PROD_ID = ? ";
			
			List<Object> p = new ArrayList<>();

			p.add(param.get("PROD_NAME"));
			p.add(param.get("PROD_TOTALSTOCK"));
			p.add(param.get("PROD_SALE"));
			p.add(param.get("PROD_DETAIL"));
			p.add(param.get("PROD_ID"));
			
			return jdbc.update(sql, p);
		}
		
		
}


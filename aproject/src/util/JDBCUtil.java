package util;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCUtil {

	// 싱글톤 패턴 : 인스턴스의 생성을 제한하여 하나의 인스턴스만 사용하는 디자인 패턴
	//   --->    클래스 내부에서 객체를 만들어 다른 클래스에 빌리는 형식으로 쓰임
	
	
	private JDBCUtil(){ //생성자 ( 다른클래스에서 객체생성을 못하게끔 만듦름)
	}
		//인스턴스를 보관할 변수
	private static JDBCUtil instance;
		
		// 인스턴스를 빌려주는 메서드
	public static JDBCUtil getInstance(){   // 객체생성도 하지 않게끔 static 설정
		if(instance == null){               // 처음 호출할때는 instance가 null이기에(두번째부터는 바로 return값으로 넘어간다)
			instance = new JDBCUtil();
		}
		
		return instance;
	}
	
	/*[단일행 조회]
	 *  Map<String, Object > selectOne(String sql) :  다중행이 조회되는 경우 가장 마지막 행이 조회되게 된다
	 *  Map<String, Object> selectOne(String sql List<Object> param)
	 *  [다중행 조회]
	 *  List<Map<String, Object>> selectList(String sql)
	 *  List<Map<String, Object>> selectList(String sql, List<Object> param)
	 *  [행 생성/수정/삭제]
	 *  int update(String sql) : ? 없을 때
	 *  int update(String sql, List<Object> param) : ?있을 때
	 */
//	private static final String URL = "jdbc:oracle:thin:@192.168.43.34:1521:xe";
//	private static final String ID = "ihyeon";
//	private static final String PASSWORD = "java";
//	
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String ID = "PC77";
	private static final String PASSWORD = "java";

	java.sql.Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	
	public int update(String sql, List<Object> param){		// 행수정(조건)
		int result =0; //★지역변수이기에 처음에 초기화를 해줘야함.
		try {
			
			con = DriverManager.getConnection(URL,ID,PASSWORD);
			ps = con.prepareStatement(sql);
			for(int i=0; i<param.size(); i++){
//			ps.setObject(i+1, rs.getObject(i));
				ps.setObject(i+1, param.get(i));
			}
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(ps != null) try{ps.close();} catch(Exception e) {}
			if(con != null) try{con.close();} catch(Exception e) {}
			}
		return result;
	}
	
	public int update(String sql){		// 행수정
		int result =0; //★지역변수이기에 처음에 초기화를 해줘야함. 
		try {
			con = DriverManager.getConnection(URL,ID,PASSWORD);
			ps = con.prepareStatement(sql);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(ps != null) try{ps.close();} catch(Exception e) {}
			if(con != null) try{con.close();} catch(Exception e) {}
			}
		return result;
	}

	
	public Map<String, Object> selectOne (String sql, List<Object> param){ // 단일행(조건)
		 Map<String, Object> hm = null;
		 try {
			con = DriverManager.getConnection(URL, ID, PASSWORD);
			 ps = con.prepareStatement(sql);
			 for(int i=0; i<param.size(); i++){
				ps.setObject(i+1, param.get(i));
			 }
			 rs = ps.executeQuery(); // 왜 에러나지?
			 ResultSetMetaData metaData = rs.getMetaData();
			 int columnCount = metaData.getColumnCount();
			 while(rs.next()){
				 hm = new HashMap<>();
				 for(int i=1; i<columnCount+1; i++){
				 hm.put(metaData.getColumnName(i), rs.getObject(i));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) try{rs.close();} catch(Exception e) {}
			if(ps != null) try{ps.close();} catch(Exception e) {}
			if(con != null) try{con.close();} catch(Exception e) {}
			}
		 return hm;
	 }

	public Map<String, Object > selectOne (String sql){ // 단일행
		Map<String,Object> hm = null; // 조회된 행이 없을 때를 생각해서 null로 초기화 한다.
//		Map<String,Object> hm = new HashMap<>();
		try {
			con = DriverManager.getConnection(URL,ID,PASSWORD);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while(rs.next()){
				hm = new HashMap<>();
				for(int i=1; i<columnCount+1; i++){
					hm.put(metaData.getColumnName(i), rs.getObject(i));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) try{rs.close();} catch(Exception e) {}
			if(ps != null) try{ps.close();} catch(Exception e) {}
			if(con != null) try{con.close();} catch(Exception e) {}
			}
		return hm;
	}
	
	public List<Map<String, Object>> selectList(String sql) { // 다중행
		List<Map<String, Object>> list = new ArrayList<>();
		try {
			con = DriverManager.getConnection(URL, ID, PASSWORD);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				HashMap<String, Object> row = new HashMap<>();
				for (int i = 1; i < columnCount+1; i++) {             // 왜 컬럼카운트에 +1 하지 않는건지
					row.put(metaData.getColumnName(i), rs.getObject(i));
				}
				list.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) try{rs.close();} catch(Exception e) {}
			if(ps != null) try{ps.close();} catch(Exception e) {}
			if(con != null) try{con.close();} catch(Exception e) {}
			}
		return list;
	}
	
	
	public List<Map<String, Object>> selectList(String sql, List<Object> param) { // 다중행(조건)
		List<Map<String, Object>> list = new ArrayList<>();
	
		try {
			con = DriverManager.getConnection(URL, ID, PASSWORD);
			ps = con.prepareStatement(sql);
			for (int i = 0; i < param.size(); i++) {
				ps.setObject(i + 1, param.get(i));
			}
			rs = ps.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
	
			while (rs.next()) {
				HashMap<String, Object> row = new HashMap<>();
				for (int i = 1; i < columnCount+1; i++) {
					row.put(metaData.getColumnName(i), rs.getObject(i));
				}
				list.add(row);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		if(rs != null) try{rs.close();} catch(Exception e) {}
		if(ps != null) try{ps.close();} catch(Exception e) {}
		if(con != null) try{con.close();} catch(Exception e) {}
		}
		return list;
	}

}
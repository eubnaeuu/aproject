package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class UserDao {

	private UserDao(){}
	private static UserDao instance;
	public static UserDao getInstance(){
		if(instance == null){
			instance = new UserDao();
		}
		return instance;
	}
	
	private JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public int insertUser(Map<String, Object> param){
		String sql = "insert into member values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		List<Object> p = new ArrayList<>();
		p.add(param.get("MEM_ID"));
		p.add(param.get("MEM_PASS"));
		p.add(param.get("MEM_NAME"));
		p.add(param.get("MEM_SEX"));
		p.add(param.get("MEM_BIR"));
		p.add(param.get("MEM_ZIP"));
		p.add(param.get("MEM_ADD1"));
		p.add(param.get("MEM_ADD2"));
		p.add(param.get("MEM_HOMETEL"));
		p.add(param.get("MEM_HP"));
		p.add(param.get("MEM_MAIL"));
		p.add(param.get("MEM_CASH"));
		
		return jdbc.update(sql, p);
	}
	public int updateUser(Map<String, Object> param){
		String sql = "update MEMBER"
				+ " set MEM_NAME = ?, "
				+ " MEM_SEX = ?, "
				+ " MEM_BIR = ?, "
				+ " MEM_ZIP = ?, "
				+ " MEM_ADD1 = ?, "
				+ " MEM_ADD2 = ?, "
				+ " MEM_HOMETEL = ?, "
				+ " MEM_HP = ?, "
				+ " MEM_MAIL = ? "
				+ " where MEM_ID = ?";
		
		List<Object> p = new ArrayList<>();

		p.add(param.get("MEM_NAME"));
		p.add(param.get("MEM_SEX"));
		p.add(param.get("MEM_BIR"));
		p.add(param.get("MEM_ZIP"));
		p.add(param.get("MEM_ADD1"));
		p.add(param.get("MEM_ADD2"));
		p.add(param.get("MEM_HOMETEL"));
		p.add(param.get("MEM_HP"));
		p.add(param.get("MEM_MAIL"));
		p.add(param.get("MEM_ID"));
		
		return jdbc.update(sql, p);
	}
	
	public int insertCartId(Map<String, Object> param){ // 회원가입데이터map을 받아서 데이터에 직접 저장하는 메서드
		String sql = "insert into CART(CART_ID, MEM_ID) values(?, ?)";
		
		List<Object> p = new ArrayList<>();
		p.add(param.get("CART_ID"));
		p.add(param.get("MEM_ID"));
		return jdbc.update(sql, p);
	}

	public Map<String, Object> selectUser(String userId, String password) {
		String sql = "select * from MEMBER where MEM_ID = ? and MEM_PASS = ?";
		List<Object> param = new ArrayList<>();
		param.add(userId);
		param.add(password);	
		return jdbc.selectOne(sql, param);
	}
	
	public int deleteUser(Map<String, Object> param){
		
		String sql = "delete from member "
				+ "where mem_id = ?";
		List<Object> p = new ArrayList<>();
		p.add(param.get("MEM_ID"));

		
		return jdbc.update(sql, p);
	}
	
	public Map<String, Object> UserInfo(String userId, String password) {
		String sql = "select * from MEMBER where MEM_ID = ? and MEM_PASS = ?";
		List<Object> param = new ArrayList<>();
		param.add(userId);
		param.add(password);
		
		return jdbc.selectOne(sql, param);
	}
	
}
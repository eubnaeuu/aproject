package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.BoardDao;
import dao.CartDao;
import util.ScanUtil;
import util.View;

public class CartService {

	private CartService(){}
	private static CartService instance;
	public static CartService getInstance(){
		if(instance == null){
			instance = new CartService();
		}
		return instance;
	}
	
	private CartDao cartDao = CartDao.getInstance();
	
	public int cartList(){
		List<Map<String, Object>> cartList = cartDao.selectCartList();
		
		System.out.println("=======================================");
		System.out.println("카트넘버 \t 카트ID \t 수량 \t 상품이름 \t 상품가격");
		System.out.println("---------------------------------------");
		if(cartList.size() == 0) {
			System.out.println("카트에 담긴 상품이 없습니다.");}
		for(Map<String, Object> cart : cartList){
			System.out.println(cart.get("CARTDETAIL_NO")
					+ "\t" + cart.get("CART_ID")
					+ "\t" + cart.get("CART_QTY")
					+ "\t" + cart.get("PROD_NAME")
					+ "\t" + cart.get("PROD_SALE"));	
		}
	
		System.out.println("=======================================");
		System.out.println("1.결제 \t 2.장바구니 물품삭제 \t 3.장보러가기\t 4. 회원정보");
		System.out.print("입력>");
		
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1:
			return View.ORDERMAIN;
		case 2:
			return View.DEL_CART;
		case 3:
			return View.MAIN;
		case 4:
			return View.USERINFO;
		}
		return View.CARTLIST;
	}
	
	public List<Map<String, Object>> tempyeongjun(){ //임시 테스트
		List<Map<String, Object>> cartList = cartDao.selectCartList();	
		return cartList;
	}
	
	public int delete(){
		Map<String, Object> param = new HashMap<>();
		System.out.println("지우고 싶은 목록의 숫자을 눌러주세요.");
		int cartNo = ScanUtil.nextInt();	
	
		param.put("CARTDETAIL_NO", cartNo);
		
		int result = cartDao.deleteCartList(param);
		
		if(0 < result){
			System.out.println("장바구니 비우기 성공");
		}else{
			System.out.println("장바구니 비우기 실패");
		}
		
		return View.CARTLIST;         
	}
	
	
	
	
}

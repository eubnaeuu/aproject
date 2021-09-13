package controller;

import java.util.Map;

import dao.AdminOrderDao;
import service.AdminOrderService;
import service.AdminReviewService;
import service.AdminService;
import service.BoardService;
import service.CartService;
import service.CashService;
import service.NoticeService;
import service.OrderService;
import service.ReviewService;
import service.SelectOrderService;
import service.SelectProdService;
import service.UserService;
import util.ScanUtil;
import util.View;

public class Controller {

	public static void main(String[] args) {
		/*
		 * 발표순서 : 조 소개 > 주제 소개 > 주제 선정 이유 > 메뉴 구조 > 시연
		 * 발표인원 : 발표자 1명, ppt 및 시연 도우미 1명
		 * 
		 * Controller : 화면 이동
		 * Service : 화면 기능
		 * Dao(Data Access Object) : 쿼리작성
		 */
		// [[210222]] 
		//1. (임시)view.main -> 상품조회 첫화면으로 이동
//		    -> 중간에 화면 하나 더 만들어야함(1.내정보, 2.장바구니, 3.상품조회)
		new Controller().start();
	}
	
	public static Map<String, Object> LoginUser; //로그인
	//user
	private UserService userService = UserService.getInstance();

	//cart
	private CartService cartService = CartService.getInstance();
	
	//board
	private BoardService boardService = BoardService.getInstance();
	
	//prod
	private SelectProdService selectProdService = SelectProdService.getInstance();
	//cash
	private CashService cashService = CashService.getInstance();
	//order
	private OrderService orderService = OrderService.getInstance();
	private AdminOrderService adminOrderService = AdminOrderService.getInstance();
	private AdminOrderDao adminOrderDao = AdminOrderDao.getInstance();
	private SelectOrderService selectOrderService = SelectOrderService.getInstance();
	//admin
	private AdminService adminService = AdminService.getInstance();
	//review
		private ReviewService reviewService = ReviewService.getInstance();
		private AdminReviewService adminreviewService = AdminReviewService.getInstance();
		//notice
	private NoticeService noticeService = NoticeService.getInstance();
	
	
	
	private void start() {
		int view = View.HOME;
		
		while(true){
			switch (view) {
			//user
			case View.HOME: view = home(); break;
			case View.LOGIN: view = userService.login(); break;
			case View.JOIN: view = userService.join(); break;
			case View.MAIN: view = boardService.boardList(); break;
			case View.DELMEM : view = userService.delete(); break;
			case View.USERINFO : view = userService.info(); break;
			case View.USERUPDATE : view = userService.update(); break;
			
			//ADMIN
			case View.ADMINMAIN : view = adminService.mainScreen(); break; // 로그인 후 첫화면
			case View.ADMINUSERMAIN : view = adminService.AUserScreen(); break; // 유저 관리
			case View.ADMINUSER : view = adminService.AUserinfo(); break;
			case View.ADMINUSERINFO : view = adminService.AdminUserinfo(); break;
			case View.ADMINUSERDEL : view = adminService.AdminDelete(); break;
			case View.ADMINPRODMAIN : view = adminService.AProdScreen(); break;
			case View.ADMINPRODINFO : view = adminService.AprodList(); break;
			case View.ADMINPRODSEAR : view = adminService.searchname(); break;
			case View.ADMINPRODIN : view = adminService.insertProd(); break;
			case View.ADMINPRODDEL : view = adminService.deleteProd(); break;
			case View.ADMINPRODUP : view = adminService.updateProd(); break;
			case View.ACHOOSENUMBER : view = adminService.choosenumber(); break;
			case View.REVIEWALL : view = adminreviewService.ReviewAll(); break; 
//			case View.ADMINORDER : view = adminService.; break;
			
			//prod
			
			case View.SEARCHSCREEN : view = selectProdService.searchscreen(); break; // 1. 상품조회
			case View.SEARCHPROD : view = selectProdService.searchprod(); break; // 1-1상품검색
			case View.CHOOSENUMBER : view = selectProdService.choosenumber(); break; // 게시글 선택하면 나오는페이지
			case View.SEARCHNAME : view = selectProdService.searchname(); break; // 1-1-1상품명검색 
			case View.SEARCHCATEGORY : view = selectProdService.searchcategory(); break; // 1-1-2카테고리별검색
			case View.SEARCHRATE : view = selectProdService.searchrate(); break; // 1-1-3별점순정렬
			case View.SEARCHDESC : view = selectProdService.searchdesc(); break; // 1-1-4가격 내림차순
			case View.SEARCHASC : view = selectProdService.searchasc(); break; // 1-1-5가격  오름차순
			
			//cart
			
			case View.CARTLIST: view = cartService.cartList(); break;
			case View.ADDCART : view = selectProdService.addcart(); break; // 카트에상품추가
			case View.DEL_CART : view = cartService.delete(); break;
			
			//order
			
			case View.CASHMAIN : view = cashService.selectorderdetail(); break;
			case View.CASHCHARGE : view = cashService.cashcharge(); break;
			case View.ORDERLIST : view = orderService.selectorderdetail(); break;
			case View.ORDERMAIN : view = orderService.selectorderdetail(); break;
			case View.SELECTORDER : view = selectOrderService.selectorder(); break;
			case View.SELECTORDERDETAIL : view = selectOrderService.selectorderdetail(); break;
			
			//review
			case View.MANAGEREVIEW : view = reviewService.ManageReview(); break;
			case View.WRITEREVIEW : view = reviewService.WriteReview(); break;
			case View.MYREVIEW : view = reviewService.MyReview(); break;
			case View.REVIEWLISTPROD : view = selectProdService.reviewListProd(); break;
			
			//notice 
			case View.SELECTNOTICEALL : view = noticeService.selectnotice(); break;
			case View.SELECTNOTICEDETAIL : view = noticeService.selectnoticedetail();  break;
			
			// Admin order
			case View.ADMINORDERMAIN : view =  adminOrderService.adminordermain(); break;
			case View.ADMINORDERINFO	 : view = adminOrderService.adminSelectOrderdetail(); break;
			case View.ADMINORDERSELECT	: view = adminOrderService.adminorderselect(); break;
			case View.ADMINORDERDELIVER : view = adminOrderService.adminorderdeliver(); break;
				
			//Admin analys
			case View.ADMINANALYSIS : view = adminOrderService.adminanalysis(); break;
			case View.ADMINANALYSISDAYMAIN : view = adminOrderService.adminanalysisday(); break;
			case View.ADMINANALYSISMONTH : view = adminOrderService.adminanalysismonth(); break;
			case View.ADMINANALYSISYEAR : view = adminOrderService.adminanalysisyear(); break;
			case View.ADMINANALYSISAGE : view = adminOrderService.adminanalysisage(); break;

			}
		}
	}

	private int home() { //홈화면
		System.out.println("--------------------------------------");
		System.out.println("1.로그인 \t 2.회원가입 \t 0.프로그램 종료");
		System.out.println("--------------------------------------");
		System.out.print("번호 입력>");
		
		
		int input = ScanUtil.nextInt();
		
		switch (input) {
			case 1: return View.LOGIN;
			case 2: return View.JOIN;
			case 0:
				System.out.println("프로그램이 종료되었습니다.");
				System.exit(0);
		}
		
		return View.HOME;
	}
	
	
}













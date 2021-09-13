package service;

import java.util.ArrayList;
import java.util.HashMap;

import util.ScanUtil;
import util.View;
import dao.ReviewDao;

public class ReviewService {


	private ReviewService(){}
	private static ReviewService instance;
	public static ReviewService getInstance(){
		if(instance == null){
			instance = new ReviewService();
		}
		return instance;
	}
	
	private ReviewDao reviewDao = ReviewDao.getInstance();
	

	
	public int ManageReview(){
		System.out.println("=======================================");
		System.out.println("=============리뷰관리화면입니다.============");
		System.out.println("원하시는 서비스를 입력해주세요");
		System.out.println("1.리뷰 작성 \t 2.리뷰 목록 \t 0.돌아가기");
		
		int input = ScanUtil.nextInt();
				switch (input) {
				case 1: return View.WRITEREVIEW;

				case 2: return View.MYREVIEW;

				case 0: return View.USERINFO;

				default:return View.MAIN;
				}
	}
	
	public int WriteReview(){
		ArrayList<HashMap<String, Object>> templi1 = new ArrayList<>();
		System.out.println("=======================================");
		System.out.println("글번호\t\t상품명\t\t");
		System.out.println("---------------------------------------");
		for(int i=0; i<reviewDao.ChoiceReview().size(); i++){
		HashMap<String, Object> temphm1 = new HashMap<>();	
		
		
		System.out.print("["+i+"] ");
		System.out.print("\t"+reviewDao.ChoiceReview().get(i).get("A"));
		temphm1.put("name",reviewDao.ChoiceReview().get(i).get("A")); 			
		temphm1.put("prod",reviewDao.ChoiceReview().get(i).get("PROD_ID")); 			
		templi1.add(temphm1);
		if(templi1.size() == 0) {
			System.out.println("구매한 상품이 없습니다.");}
			System.out.println();
		}
	
		System.out.println("=======================================");
		System.out.println("1.상품 선택 \t 2.돌아가기");
		System.out.print("입력>");
		
		int input = ScanUtil.nextInt();
		if (input == 1) {
			System.out.println("리뷰를 작성할 상품을  선택해주세요.");
			int select = ScanUtil.nextInt();
			String prod_id = templi1.get(select).get("prod").toString();
			String prod_name = templi1.get(select).get("name").toString();
			System.out.println("=======================================");
			System.out.println("=======================================");
			System.out.println("평점을 입력해주세요(1~5)");
			System.out.println("입력>");
			String rate = ScanUtil.nextLine();
			System.out.println("한줄평을 입력해주세요");
			System.out.println("입력>");
			String comment = ScanUtil.nextLine();
			reviewDao.WriteReview(prod_id , rate, comment);
			System.out.println("=======================================");
			System.out.println("리뷰가 등록되었습니다.");
			System.out.println("=======================================");
			return View.MANAGEREVIEW;
		} else if (input == 2) {
			return View.MANAGEREVIEW;
		}
		return View.MAIN;
	}

	
	
	public int MyReview(){
		System.out.println("=======================================");
		System.out.println("고객님께서 작성하신 리뷰의 목록입니다.");		

		ArrayList<HashMap<String, Object>> templi2 = new ArrayList<>();
		System.out.println("=======================================");
		System.out.println("리뷰번호 \t 상품명 \t 평점 \t 코멘트");
		System.out.println("---------------------------------------");
		for(int i=0; i<reviewDao.ReviewList().size(); i++){
		HashMap<String, Object> temphm2 = new HashMap<>();	
		System.out.println("=======================================");
		System.out.print("["+reviewDao.ReviewList().get(i).get("A")+"]");
		System.out.print("\t"+reviewDao.ReviewList().get(i).get("B"));
		System.out.print("\t"+reviewDao.ReviewList().get(i).get("C"));
		System.out.print("\t"+reviewDao.ReviewList().get(i).get("D"));
		temphm2.put("prod",reviewDao.ReviewList().get(i).get("A")); 	
		temphm2.put("prod",reviewDao.ReviewList().get(i).get("B")); 	
		temphm2.put("name",reviewDao.ReviewList().get(i).get("C")); 			
		temphm2.put("count",reviewDao.ReviewList().get(i).get("D")); 		
		templi2.add(temphm2);
		if(templi2.size() == 0) {
			System.out.println("등록한 리뷰가 없습니다.");}
			System.out.println();
		}

		System.out.println("=======================================");
		System.out.println("1.삭제 \t 0.돌아가기");
		System.out.println("입력>");
			int input3 = ScanUtil.nextInt();
			switch (input3) {
			case 1: 
				System.out.println("삭제할 리뷰의 번호를 선택해주세요");
			 	int num  = ScanUtil.nextInt();
			 	reviewDao.deletereview(num);
			 	System.out.println("해당 리뷰가 삭제되었습니다.");
			 	return View.MANAGEREVIEW;

			case 0 : return View.MAIN;

			default: return View.MAIN;
		}
	}
			
			
	
	


	
	
	

	
	
	
	

	
	
	

}


	


package service;

import java.util.ArrayList;
import java.util.HashMap;

import util.ScanUtil;
import util.View;
import dao.AdminReviewDao;

public class AdminReviewService {


	private AdminReviewService(){}
	private static AdminReviewService instance;
	public static AdminReviewService getInstance(){
		if(instance == null){
			instance = new AdminReviewService();
		}
		return instance;
	}
	
	private AdminReviewDao adminreviewDao = AdminReviewDao.getInstance();
	
	
	
	public int ReviewAll(){
		System.out.println("=======================================");
		System.out.println("등록된 리뷰 리스트");		
		System.out.println("=======================================");

		ArrayList<HashMap<String, Object>> templi2 = new ArrayList<>();
		System.out.println("=======================================");
		System.out.println("리뷰번호 \t 상품명 \t 평점 \t 코멘트");
		System.out.println("---------------------------------------");
		for(int i=0; i<adminreviewDao.ReviewList().size(); i++){
		HashMap<String, Object> temphm2 = new HashMap<>();	
		System.out.println("=======================================");
		System.out.print("["+adminreviewDao.ReviewList().get(i).get("A")+"]");
		System.out.print("\t"+adminreviewDao.ReviewList().get(i).get("B"));
		System.out.print("\t"+adminreviewDao.ReviewList().get(i).get("C"));
		System.out.print("\t"+adminreviewDao.ReviewList().get(i).get("D"));
		temphm2.put("prod",adminreviewDao.ReviewList().get(i).get("A")); 	
		temphm2.put("prod",adminreviewDao.ReviewList().get(i).get("B")); 	
		temphm2.put("name",adminreviewDao.ReviewList().get(i).get("C")); 			
		temphm2.put("count",adminreviewDao.ReviewList().get(i).get("D")); 		
		templi2.add(temphm2);
		if(templi2.size() == 0) {
			System.out.println("등록된 리뷰가 없습니다.");}
			System.out.println();
		}



		System.out.println("=======================================");
		System.out.println("1.삭제 \t 2.돌아가기");
		System.out.println("입력>");
			int input3 = ScanUtil.nextInt();
			switch (input3) {
			case 1: 
				System.out.println("삭제할 리뷰의 번호를 선택해주세요");
			 	int num  = ScanUtil.nextInt();
			 	adminreviewDao.deletereview(num);
			 	System.out.println("해당 리뷰가 삭제되었습니다.");
			 	return View.ADMINMAIN;

			case 2 : return View.ADMINMAIN;

			default: return View.ADMINMAIN;
		}
	
	}


	
	
	

	
	
	
	

	
	
	

}


	


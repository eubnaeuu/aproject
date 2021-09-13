package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.JFrame11;
import util.ScanUtil;
import util.SetCommaTS;
import util.View;
import controller.Controller;
import dao.ReviewDao;
import dao.SelectProdDao;



public class SelectProdService {
	
	private SelectProdService(){}
	private static SelectProdService instance;
	private SetCommaTS setc = SetCommaTS.getInstance();
	public static SelectProdService getInstance(){
		if(instance == null){
			instance = new SelectProdService();
		}
		return instance;
	}
	
	private SetCommaTS setcomma = SetCommaTS.getInstance();
	private ReviewDao reviewDao = ReviewDao.getInstance();
	
	List<Map<String,Object>> templi = new ArrayList<>(); // [콘솔창의 게시글번호] 와 실제 게시글의 SALE_NO를 연동1
	HashMap<String, Object> temphm = new HashMap<>();// [콘솔창의 게시글번호] 와 실제 게시글의 SALE_NO를 연동2
	List<Map<String,Object>> templi2 = new ArrayList<>();// [콘솔창의 상품번호] 와 실제 게시글의 PROD_ID를 연동1
	HashMap<String, Object> temphm2 = new HashMap<>();// [콘솔창의 상품번호] 와 실제 게시글의 PROD_ID를 연동1
	int snumber;// 사용자가 선택한 [콘솔창의 게시글번호]를 변수에 저장
	int gnumber; // 사용자가 선택한 [콘솔창의 상품번호]를 변수에 저장
	Object stemp;
	private SelectProdDao selectProdDao = SelectProdDao.getInstance();
	
public int searchscreen(){ // 메인화면(추천상품) 1. 상품검색 2. 글번호검색
	templi = new ArrayList<>(); // 글번호 연동할 리스트 (게시글 상세조회시) (글번호는 상품별로 달림)
	System.out.println("=======================================");
	System.out.println("글번호\t제목\t");
	System.out.println("---------------------------------------");
	out : for(int i=0; i<selectProdDao.selectrecommenddetail().size(); i++){
//		out : for(int i=0; i<1; i++){
		temphm = new HashMap<>();	
		if(i!=0){// i=1이후부터 이전출력 게시물의 제목과 같으면 건너뛰기 (삼성특별전같은 경우 여러행으로 출력되서)
			if(selectProdDao.selectrecommenddetail().get(i).get("SALE_TITLE").toString().equals(selectProdDao.selectrecommenddetail().get(i-1).get("SALE_TITLE").toString())){
				temphm.put("SALE_NO", selectProdDao.selectrecommenddetail().get(i).get("SALE_NO"));
				templi.add((temphm));
				continue out;
			}
		}
		    System.out.print("["+i+"] ");
			System.out.print("\t"+selectProdDao.selectrecommenddetail().get(i).get("SALE_TITLE"));
			temphm.put("SALE_NO", selectProdDao.selectrecommenddetail().get(i).get("SALE_NO")); // 0번글은 리스트 0번째이고 그에 들어간 hashmap에는 prod_id가 저장되어있다.			
		templi.add((temphm));
		System.out.println();
	}
	System.out.println("1. 상품검색    2. 글번호 선택  0. 뒤로");  // 첫화면
	
	int input = ScanUtil.nextInt();
	switch (input) {
	case 1 : 
		return  View.SEARCHPROD; // 1. 상품검색
	case 2 :
	{
		System.out.println("글번호를 선택해주세요");  
		snumber = ScanUtil.nextInt(); 
		return View.CHOOSENUMBER; // 2. 글번호검색
	}
	case 0:
		return View.MAIN;// 뒤로
	}
	return View.HOME; // 아직 보류
}


public int searchprod(){ // 1.상품검색눌렀을때 
	System.out.println("1. 상품명검색  2. 카테고리별  3. 별점순  4. 가격(내림차순)  5. 가격(오름차순)  0. 뒤로");  // 첫화면
// 단일행으로 해야할듯? // 운영자가 정한 글번호 글 목록 출력
	int input = ScanUtil.nextInt();
	
	switch (input) {
		case 1 :
			return View.SEARCHNAME; // 1. 상품명검색
		case 2 :
			return View.SEARCHCATEGORY;// 2.카테고리별
		case 3 :
			return View.SEARCHRATE; // 3.별점순
		case 4 :
			return View.SEARCHDESC; // 4.가격내림차순
		case 5 :  
			return View.SEARCHASC; // 5.가격오름차순
		case 0: 
			return View.MAIN;// 뒤로
		}
		return View.SEARCHPROD; // 아직 보류
		
	}
public int searchname() { //상품명 검색
	System.out.println("상품명을 입력해주세요>");
	String input = ScanUtil.nextLine();
	
	templi = new ArrayList<>();
	List<String> temp = new ArrayList<>(); // [콘솔창의 글번호]와 상품ID를 연동할 리스트 -> 나중에 소비자가 게시글 선택시 상품ID를 가져와야 하기에
	System.out.println("=======================================");
	System.out.println("글번호\t카테고리\t가격\t제목");
//	System.out.println("---------------------------------------size : " + selectProdDao.selectList(input).size());
	out : for(int i=0; i<selectProdDao.selectList(input).size(); i++){
		temphm = new HashMap<>();
		if(i!=0){// i=1이후부터 이전출력 게시물의 제목과 같으면 건너뛰기 (삼성특별전같은 경우 여러행으로 출력되서)
			for(int j=0; j<i; j++){
				if(selectProdDao.selectList(input).get(j).get("SALE_TITLE").toString().equals(selectProdDao.selectList(input).get(i).get("SALE_TITLE").toString())){
					temphm.put("SALE_NO", selectProdDao.selectList(input).get(i).get("SALE_NO"));
					templi.add(temphm);
					continue out;
				}
			}
		}
		System.out.print("["+i+"] ");
		System.out.print("\t"+selectProdDao.selectList(input).get(i).get("LPROD_NM"));		
		System.out.print("\t"+setcomma.Setcomma(selectProdDao.selectList(input).get(i).get("PROD_SALE").toString()));
			System.out.print("\t"+selectProdDao.selectList(input).get(i).get("SALE_TITLE"));
			temphm.put("SALE_NO", selectProdDao.selectList(input).get(i).get("SALE_NO")); // 0번글은 리스트 0번째이고 그에 들어간 hashmap에는 prod_id가 저장되어있다.			
		templi.add(temphm);
		System.out.println();
	}
	System.out.println("1. 글번호 선택 2. 재검색 0. 뒤로");
		switch (ScanUtil.nextInt()) {
		case 1:{
			System.out.println("글번호를 선택해주세요");  
			snumber = ScanUtil.nextInt(); 
			return View.CHOOSENUMBER;
		}
		case 2: return View.SEARCHNAME;
		case 0: return View.SEARCHPROD;
		}
		return View.SEARCHNAME;
	}
	
public int searchcategory() {
	System.out.println("1. TV  2. 컴퓨터제품  3. 노트북  4. 태블릿 \n5. 모바일  6. 카메라  7. 음향기기");
	System.out.println("번호를 입력>");
	int input = ScanUtil.nextInt();
	templi = new ArrayList<>();
	List<String> temp = new ArrayList<>(); // 글번호 연동할 리스트 (게시글 상세조회시) (글번호는 상품별로 달림)
	System.out.println("=======================================");
	System.out.println("글번호\t제목");
	System.out.println("---------------------------------------");
	out : for(int i=0; i<selectProdDao.searchcategory(Integer.toString(input)).size(); i++){
		temphm = new HashMap<>();
		if(i!=0){// i=1이후부터 이전출력 게시물의 제목과 같으면 건너뛰기 (삼성특별전같은 경우 여러행으로 출력되서)
			if(selectProdDao.searchcategory(Integer.toString(input)).get(i).get("SALE_TITLE").toString().equals(selectProdDao.searchcategory(Integer.toString(input)).get(i-1).get("SALE_TITLE").toString())){
				temphm.put("SALE_NO",selectProdDao.searchcategory(Integer.toString(input)).get(i).get("SALE_NO"));
				continue out;
			}
		}
		System.out.print("["+i+"] ");
//		System.out.print("\t"+selectProdDao.searchcategory(Integer.toString(input)).get(i).get("LPROD_NM"));
		System.out.print("\t"+selectProdDao.searchcategory(Integer.toString(input)).get(i).get("PROD_SALE"));
		System.out.print("\t"+selectProdDao.searchcategory(Integer.toString(input)).get(i).get("SALE_TITLE"));
		temphm.put("SALE_NO",selectProdDao.searchcategory(Integer.toString(input)).get(i).get("SALE_NO")); // 0번글은 리스트 0번째이고 그에 들어간 hashmap에는 prod_id가 저장되어있다.			
		System.out.println();
		templi.add(temphm);
	}
//		System.out.println(templi);
//		System.out.println(temphm);
	System.out.println("1. 글 선택 0. 뒤로");
	switch (ScanUtil.nextInt()) { // ★ 위 1,2 이어서
	case 1:
	{	
	System.out.println("글번호를 선택해주세요");  
	snumber = ScanUtil.nextInt(); 
		return View.CHOOSENUMBER; // VIEW.SEARCHNAME맞는지?
	}
	case 0:
		return View.SEARCHPROD;
	}
	return View.HOME;
}
	
	
	
 // ★ 별점기준 내림차순
public int searchrate(){  
	System.out.println("별점기준 내림차순으로 정렬합니다.");
	System.out.println(selectProdDao.searchRate());
	System.out.println("=======================================");
	System.out.println("글번호\t평점\t제목");
	System.out.println("---------------------------------------");
	for(int i=0; i<selectProdDao.searchRate().size(); i++){
		System.out.print("["+i+"] ");
		System.out.print("\t"+selectProdDao.searchRate().get(i).get("평점")+"점");
			System.out.print("\t"+selectProdDao.searchRate().get(i).get("PROD_SALE"));
			System.out.print("\t"+selectProdDao.searchRate().get(i).get("PROD_NAME"));
		System.out.println();
	}
	System.out.println("1. 글 선택 0. 뒤로");
	switch (ScanUtil.nextInt()) {
	case 1:
	{	
	System.out.println("글번호를 선택해주세요");  
	int input = ScanUtil.nextInt();
	String prodid = selectProdDao.searchRate().get(input).get("PROD_ID").toString();
	// prodid로 리스트 조회
	System.out.println("해당상품을 포함한 게시글을 모두 조회합니다.");
	selectProdDao.searchRateDetail(prodid);
	System.out.println("=======================================");
	System.out.println("글번호\t가격\t제목");
	System.out.println("---------------------------------------");
	templi = new ArrayList<>();
	for(int i=0; i<selectProdDao.searchRateDetail(prodid).size(); i++){
		temphm = new HashMap<>();
		System.out.print("["+i+"] ");
		System.out.print("\t"+setc.Setcomma(selectProdDao.searchRateDetail(prodid).get(i).get("PROD_SALE").toString()));
		System.out.print("\t"+selectProdDao.searchRateDetail(prodid).get(i).get("SALE_TITLE"));
		temphm.put("SALE_NO", selectProdDao.searchRateDetail(prodid).get(i).get("SALE_NO"));
		templi.add(temphm);
		System.out.println();
	}
	System.out.println("구분번호를 입력>");
	snumber = ScanUtil.nextInt();

	return View.CHOOSENUMBER; // VIEW.SEARCHNAME맞는지?
	}
	case 0:
		return View.SEARCHPROD;
	}
	return View.SEARCHPROD;
}

public int searchdesc(){
	templi = new ArrayList<>();
	List<String> temp = new ArrayList<>(); // 글번호 연동할 리스트 (게시글 상세조회시) (글번호는 상품별로 달림)
	System.out.println("=======================================");
	System.out.println("글번호\t\t가격\t\t제목");
	System.out.println("---------------------------------------");
	
	for(int i=0; i<selectProdDao.searchdesc().size(); i++){
		temphm = new HashMap<>();
		System.out.print("["+i+"]\t\t");
		System.out.print(setc.Setcomma(selectProdDao.searchdesc().get(i).get("PROD_SALE").toString())+"\t\t");
			System.out.print(selectProdDao.searchdesc().get(i).get("SALE_TITLE"));
			temphm.put("SALE_NO",selectProdDao.searchdesc().get(i).get("SALE_NO")); // 0번글은 리스트 0번째이고 그에 들어간 hashmap에는 prod_id가 저장되어있다.			
		templi.add(temphm);
		System.out.println();
	}
	System.out.println("1. 글 선택 0. 뒤로");
	switch (ScanUtil.nextInt()) {
	case 1:
	{	
	System.out.println("글번호를 선택해주세요");  
	snumber = ScanUtil.nextInt(); 
		return View.CHOOSENUMBER; // VIEW.SEARCHNAME맞는지?
	}
	case 2:
		return View.SEARCHPROD;
	}
	return View.SEARCHPROD;
}
public int searchasc(){
	templi = new ArrayList<>();
	List<String> temp = new ArrayList<>(); // 글번호 연동할 리스트 (게시글 상세조회시) (글번호는 상품별로 달림)
	System.out.println("=======================================");
	System.out.println("글번호\t\t가격\t\t제목");
	System.out.println("---------------------------------------");
	for(int i=0; i<selectProdDao.searchasc().size(); i++){
		temphm = new HashMap<>();
		System.out.print("["+i+"] ");
			System.out.print(setc.Setcomma(selectProdDao.searchasc().get(i).get("PROD_SALE").toString())+"\t\t");
			System.out.print(selectProdDao.searchasc().get(i).get("SALE_TITLE").toString()+"\t\t");
			temphm.put("SALE_NO",selectProdDao.searchasc().get(i).get("SALE_NO")); // 0번글은 리스트 0번째이고 그에 들어간 hashmap에는 prod_id가 저장되어있다.
			templi.add(temphm);
			System.out.println();
	}
	System.out.println("1. 글 선택 0. 뒤로");
	switch (ScanUtil.nextInt()) {
	case 1:
	{	
	System.out.println("글번호를 선택해주세요");  
	snumber = ScanUtil.nextInt(); 
		return View.CHOOSENUMBER; // VIEW.SEARCHNAME맞는지?
	}
	case 2:
		return View.SEARCHPROD;
	}
	return View.SEARCHPROD;
}


public int choosenumber(){   // 게시글(SALE_NO)의 속한 상품 출력 
	stemp = templi.get(snumber).get("SALE_NO"); // 게시글번호 변수저장
//	selectProdDao.selectSaleNo(stemp.toString());F
//System.out.println("size : "+selectProdDao.selectSaleNo(stemp.toString()).size());  // for문 i범위  
	templi2 = new ArrayList<>(); // 글번호 연동할 리스트
	System.out.println("=======================================");
	System.out.println("글번호\t\t제목\t");
	System.out.println("---------------------------------------");
	for(int i=0; i<selectProdDao.selectSaleNo(stemp.toString()).size(); i++){
		temphm2 = new HashMap<>();		
		System.out.println("["+i+"]번 상품 ");
			System.out.println("상    품   명 : "+selectProdDao.selectSaleNo(stemp.toString()).get(i).get("PROD_NAME"));
			System.out.println("가          격 : "+selectProdDao.selectSaleNo(stemp.toString()).get(i).get("PROD_SALE"));
			System.out.println("제          원 : "+selectProdDao.selectSaleNo(stemp.toString()).get(i).get("PROD_INFO"));
			System.out.println("주문가능수량 : "+selectProdDao.selectSaleNo(stemp.toString()).get(i).get("PROD_TOTALSTOCK"));
			String Path1 = (String) selectProdDao.selectSaleNo(stemp.toString()).get(i).get("PRDO_IMG");
			temphm2.put("PROD_ID",selectProdDao.selectSaleNo(stemp.toString()).get(i).get("PROD_ID"));
			templi2.add(temphm2);
			String path = Path1;
			JFrame11.InsertImg(path);
		}
		System.out.println("\n1. 리뷰보기  2. 장바구니에 담기  3. 장바구니 바로가기  0.뒤로");
		switch (ScanUtil.nextInt()) {
		case 1 :{
			if(selectProdDao.selectSaleNo(stemp.toString()).size()==1){
				gnumber = 0;
				return View.REVIEWLISTPROD;
			} else {
				System.out.println("상품번호 입력>");
				gnumber = ScanUtil.nextInt(); // 상품번호
				return View.REVIEWLISTPROD;
			}
		}
		case 2: {
			if(selectProdDao.selectSaleNo(stemp.toString()).size()==1){
				gnumber = 0;
				return View.ADDCART;
			} else {
				System.out.println("상품번호 입력>");
				gnumber = ScanUtil.nextInt(); // 상품번호
				return View.ADDCART;
			}
		}
		case 3: return View.CARTLIST;
		case 0: return View.SEARCHPROD;
	} 
		return View.CHOOSENUMBER;
}

public int reviewListProd(){
	String prodid1 = selectProdDao.selectSaleNo(stemp.toString()).get(gnumber).get("PROD_ID").toString();
	System.out.println("=======================================");
	System.out.println("회원ID \t 코멘트 \t 평점");
	System.out.println("---------------------------------------");
	for(int i=0; i<selectProdDao.ReviewListProd(prodid1).size(); i++){
	System.out.println("=======================================");
	System.out.print(selectProdDao.ReviewListProd(prodid1).get(i).get("MEM_ID")+"\t");
	System.out.print(selectProdDao.ReviewListProd(prodid1).get(i).get("CONTENT")+"\t");
	System.out.print(selectProdDao.ReviewListProd(prodid1).get(i).get("RATING")+"\n");
}
	System.out.println("0. 돌아가기");
	switch(ScanUtil.nextInt()){
	case 0 : return View.CHOOSENUMBER;
	}
return View.CHOOSENUMBER;// 임시
}



public int addcart(){ // 장바구니 담기
	System.out.println("수량>");
	int cartqty = ScanUtil.nextInt();
	Map<String, Object> param = new HashMap<>();
	param.put("PROD_ID", templi2.get(gnumber).get("PROD_ID").toString());
	param.put("CART_QTY", cartqty);
	String cartid = Controller.LoginUser.get("MEM_ID").toString()+"cart";
	param.put("CART_ID",cartid);
	selectProdDao.addcart(param);
	System.out.println("장바구니에 담았습니다.");
	return View.CHOOSENUMBER;
}


public static void main(String[] args) { //test용
	SelectProdService sps = new SelectProdService();
}
}

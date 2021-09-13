package service;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dao.AdminDao;
import dao.UserDao;
import util.ScanUtil;
import util.View;

public class AdminService {

		private AdminService(){}
		private static AdminService instance;
		public static AdminService getInstance(){
			if(instance == null){
				instance = new AdminService();
			}
			return instance;
		}
		public SimpleDateFormat sdf = new SimpleDateFormat("YYYY.MM.DD");		
		private UserDao userDao = UserDao.getInstance();
		private AdminDao adminDao = AdminDao.getInstance();	
		List<Map<String,Object>> templi = new ArrayList<>(); // [콘솔창의 게시글번호] 와 실제 게시글의 SALE_NO를 연동1
		HashMap<String, Object> temphm = new HashMap<>();// [콘솔창의 게시글번호] 와 실제 게시글의 SALE_NO를 연동2
		List<Map<String,Object>> templi2 = new ArrayList<>();// [콘솔창의 상품번호] 와 실제 게시글의 PROD_ID를 연동1
		HashMap<String, Object> temphm2 = new HashMap<>();// [콘솔창의 상품번호] 와 실제 게시글의 PROD_ID를 연동1
		int snumber;// 사용자가 선택한 [콘솔창의 게시글번호]를 변수에 저장
		int gnumber; // 사용자가 선택한 [콘솔창의 상품번호]를 변수에 저장
		
		
		public int mainScreen() { // 메인메뉴
			System.out.println("---------------------------------------------------");
			System.out.println("1.상품조회 \t 2.상품메뉴 \t 3.유저관리 \t 4.결제관리 \t 5.리뷰관리 \t 0.로그아웃");//(+)
			System.out.println("---------------------------------------------------");
			System.out.print("번호 입력>");

			int input = ScanUtil.nextInt();
				switch (input) {
				case 1: return View.ADMINPRODSEAR;
				case 2: return View.ADMINPRODMAIN; 
				case 3: return View.ADMINUSERMAIN;
				case 4: return View.ADMINORDERMAIN;
				case 5: return View.REVIEWALL;
				case 0:	return View.HOME;
			}
			return View.SEARCHSCREEN;
		}
		
		public int AUserScreen() { // 메인메뉴
			System.out.println("---------------------------------------------------");
			System.out.println("--잊어버리신거 같아서 말하는데 손님은 왕입니다.--");
			System.out.println("1.전체유저조회 \t 2.유저세부검색 \t 3.유저삭제 \t 0.뒤로가기 ");
			System.out.println("---------------------------------------------------");
			System.out.print("번호 입력>");

			int input = ScanUtil.nextInt();
				switch (input) {
				case 1: return View.ADMINUSER;
				case 2: return View.ADMINUSERINFO; 
				case 3: return View.ADMINUSERDEL;
				case 0:	return View.ADMINMAIN;

				
			}
			return View.ADMINMAIN;
		}
		
		public int AUserinfo(){
		
			List<Map<String, Object>> userList = adminDao.AdminUserInfo();
			
			for (int i = 0; i < 1; i++){
			for(Map<String, Object> user : userList) {
			System.out.println("========================================================================================================================");
			System.out.println("ID\t이름\t성별\t생일\t   \t주소   \t\t\t\t전화번호\t\t\t메일\t\t\t캐쉬");
			System.out.println("------------------------------------------------------------------------------------------------------------------------");
				System.out.println(user.get("MEM_ID")
						+ "\t" + user.get("MEM_NAME")
						+ "\t" + user.get("MEM_SEX")
						+ "\t" + sdf.format(user.get("MEM_BIR"))
				+ "\t" + user.get("MEM_ZIP")
				+ "\t" + user.get("MEM_ADD1")
				+ "\t" + user.get("MEM_ADD2")
				+ "\t" + user.get("MEM_HP")
				+ "\t" + user.get("MEM_MAIL")
				+ "\t" + user.get("MEM_CASH"));
			try {
            	Thread.sleep(500);
            }
            catch (InterruptedException e){
            }
			System.out.println("========================================================================================================================");
		
			
		}
		}return View.ADMINUSERMAIN;
}

		
		 

		public int AdminUserinfo(){
			System.out.print("아이디>");
			String userId = ScanUtil.nextLine();
			Map<String, Object> userList = adminDao.Adminselect(userId);
			
			System.out.println("========================================================================================================================");
			System.out.println("이름\t성별\t생일\t   \t주소   \t\t\t\t전화번호\t\t\t메일\t\t\t캐쉬");
			System.out.println("------------------------------------------------------------------------------------------------------------------------");
				System.out.println(userList.get("MEM_NAME")
						+ "\t" + userList.get("MEM_SEX")
						+ "\t" + userList.get("MEM_BIR")
				+ "\t" + userList.get("MEM_ZIP")
				+ "\t" + userList.get("MEM_ADD1")
				+ "\t" + userList.get("MEM_ADD2")
				+ "\t" + userList.get("MEM_HP")
				+ "\t" + userList.get("MEM_MAIL")
				+ "\t" + userList.get("MEM_CASH"));

				System.out.println("========================================================================================================================");
			
			return View.ADMINUSERMAIN;
		}
		
		public int AdminDelete(){
			System.out.println("=========== 회원삭제 =============");
			System.out.print("삭제할 아이디를 입력해주세요.>");
			String userId = ScanUtil.nextLine();
			
			Map<String, Object> param = new HashMap<>();
			param.put("MEM_ID", userId);
			
			int result = userDao.deleteUser(param);
			
			if(result == 1){
				System.out.println("회원삭제 성공");
			}else{
				System.out.println("회원삭제 실패");
			}
			
			return View.ADMINUSERMAIN;
		}
		public int AProdScreen() { // 메인메뉴
			System.out.println("---------------------------------------------------");
			System.out.println("--좋은 제품을 싸게사서 비싸게 팔자.--");
			System.out.println("1.전체상품조회 \t 2.상품세부검색 \t 3.상품등록 \t 4.상품수정 \t 5.상품삭제 \t0.뒤로가기");
			System.out.println("---------------------------------------------------");
			System.out.print("번호 입력>");

			int input = ScanUtil.nextInt();
				switch (input) {
				case 1: return View.ADMINPRODINFO;
				case 2: return View.ADMINPRODSEAR; 
				case 3: return View.ADMINPRODIN;
				case 4:	return View.ADMINPRODUP;
				case 5:	return View.ADMINPRODDEL;
				case 0:	return View.ADMINMAIN;
			}
			return View.ADMINMAIN;
		}
		
		
		public int AprodList(){
			
			List<Map<String, Object>> prodList = adminDao.prodList();
			System.out.println("========================================================================================================================");
			System.out.println("  상품ID \t \t 상품구분 \t 상품명 \t \t 상품가격 \t 수량");
			
			for(Map<String, Object> prod : prodList) {
				System.out.println(prod.get("A")
				+ " " + prod.get("A2")
				+ "\t" + prod.get("B")
				+ "\t" + prod.get("C")
				+ "\t" + prod.get("D")
				+ "\t" + prod.get("E"));
		}System.out.println("========================================================================================================================");
		return View.ADMINPRODMAIN;
		}
		
		
		public int searchname() {
			System.out.println("상품명을 입력해주세요>");
			String input = ScanUtil.nextLine();
			
			templi = new ArrayList<>();
			List<String> temp = new ArrayList<>(); // [콘솔창의 글번호]와 상품ID를 연동할 리스트 -> 나중에 소비자가 게시글 선택시 상품ID를 가져와야 하기에
			System.out.println("====================================================");
			System.out.println("글번호 \t \t 제목 \t \t");
			System.out.println("----------------------------------------------------");
			out : for(int i=0; i<adminDao.selectList(input).size(); i++){
				temphm = new HashMap<>();
				if(i!=0){// i=1이후부터 이전출력 게시물의 제목과 같으면 건너뛰기 (삼성특별전같은 경우 여러행으로 출력되서)
					if(adminDao.selectList(input).get(i-1).get("SALE_TITLE").toString().equals(adminDao.selectList(input).get(i).get("SALE_TITLE").toString())){
						temphm.put("SALE_NO", adminDao.selectList(input).get(i).get("SALE_NO"));
						continue out;
					}
				}
				System.out.print("["+i+"] ");
					System.out.print("\t"+adminDao.selectList(input).get(i).get("SALE_TITLE"));
					temphm.put("SALE_NO", adminDao.selectList(input).get(i).get("SALE_NO")); // 0번글은 리스트 0번째이고 그에 들어간 hashmap에는 prod_id가 저장되어있다.			
				templi.add(temphm);
				System.out.println();
			}
			System.out.println("1. 글번호 선택 \t 0. 뒤로가기");
				switch (ScanUtil.nextInt()) {
				case 1:{
					System.out.println("글번호를 선택해주세요");  
					snumber = ScanUtil.nextInt(); 
					return View.ACHOOSENUMBER;
				}
				case 0: return View.ADMINPRODMAIN;
				}
				return View.ADMINPRODMAIN;
			}
	   // 게시글(SALE_NO)의 속한 상품 출력 
		public int choosenumber(){ 
		Object stemp = templi.get(snumber).get("SALE_NO"); // 게시글번호 변수저장
		adminDao.selectSaleNo(stemp.toString());
		templi2 = new ArrayList<>(); // 글번호 연동할 리스트
		System.out.println("====================================================");
		System.out.println("글번호\t\t제목\t\t");
		System.out.println("----------------------------------------------------");
		for(int i=0; i<adminDao.selectSaleNo(stemp.toString()).size(); i++){
			System.out.println(adminDao.selectSaleNo(stemp.toString()).size());
			temphm2 = new HashMap<>();		
			System.out.println("["+i+"]번 상품 ");
				System.out.println("상    품   명 : "+adminDao.selectSaleNo(stemp.toString()).get(i).get("PROD_NAME"));
				System.out.println("가          격 : "+adminDao.selectSaleNo(stemp.toString()).get(i).get("PROD_SALE"));
				System.out.println("제          원 : "+adminDao.selectSaleNo(stemp.toString()).get(i).get("PROD_INFO"));
				System.out.println("주문가능수량 : "+adminDao.selectSaleNo(stemp.toString()).get(i).get("PROD_TOTALSTOCK"));
				System.out.println("상품ID : "+adminDao.selectSaleNo(stemp.toString()).get(i).get("PROD_ID"));
				temphm2.put("PROD_ID",adminDao.selectSaleNo(stemp.toString()).get(i).get("PROD_ID"));
				templi2.add(temphm2);
			}
			System.out.println("\n1. 상품검색(MAIN)  \t2. 상품수정 \t 3.상품등록 \t 4.상품삭제");
			switch (ScanUtil.nextInt()) {
			case 1: return View.ADMINPRODMAIN;
			case 2: return View.ADMINPRODUP;
			case 3: return View.ADMINPRODIN;
			case 4: return View.ADMINPRODDEL;

		} 
			return View.ACHOOSENUMBER;

}
		public int insertProd(){
			System.out.println("=========== 상품등록 =============");
			String prodId;
			out : while(true){
				System.out.print("PROD_ID> 첫글자 P시작 10자리");
				prodId = ScanUtil.nextLine();
				if (regexid(prodId) == false) {
					System.out.println("잘못 입력하셨습니다. PROD_ID는 10자리로 정해주세요.");
				} else
					break out;
			}
			String lprodgu = "P101";
			System.out.println("LPROD_GU>선택");
			System.out.print("1. : TV");
			System.out.print("2. : 컴퓨터"); 
			System.out.print("3. : 노트북"); 
			System.out.println("4. : 태블릿");
			System.out.print("5. : 모바일"); 
			System.out.print("6. : 카메라"); 
			System.out.print("7. : 음향기기");
			System.out.println();
			int input = ScanUtil.nextInt();
			switch(input){
			case 1: 
				lprodgu = "P101";
			case 2: 
				lprodgu = "P102";
			case 3: 
				lprodgu = "P103";
			case 4: 
				lprodgu = "P104";
			case 5: 
				lprodgu = "P105";
			case 6: 
				lprodgu = "P106";
			case 7: 
				lprodgu = "P107";
			}	
			System.out.println(lprodgu+"를 선택하셨습니다.");
			
			System.out.println("상품명>");
			String prodName = ScanUtil.nextLine();
			System.out.println("상품개수>");
			String prodStock = ScanUtil.nextLine();
			System.out.println("상품가격>");
			String prodSale = ScanUtil.nextLine();
			System.out.println("상품이미지(미구현)>");
			String prodImg = ScanUtil.nextLine();
			System.out.println("상품상세정보>");	
			String prodDetail = ScanUtil.nextLine();
			
			
			Map<String, Object> param = new HashMap<>();
			param.put("PROD_ID", prodId);
			param.put("LPROD_GU", lprodgu);
			param.put("PROD_NAME", prodName);
			param.put("PROD_TOTALSTOCK", prodStock);
			param.put("PROD_SALE", prodSale);
			param.put("PROD_IMG", prodImg);
			param.put("PROD_DETAIL", prodDetail);
			
			int result = adminDao.insertProd(param);
			
			if(0 < result){
				System.out.println("상품등록 성공");
			}else{
				System.out.println("상품등록 실패");
			}
			return View.ADMINPRODMAIN;
		}
		
		boolean regexid(String str){
			String regexid ="[A-Z0-9_-]{10,10}";	
		Pattern p = Pattern.compile(regexid);		// Pattern.conpile(String regex) : 주어진 정규표현식으로부터 패턴을 만들며, 컴파일이라고 한다.
		Matcher m =	p.matcher(str);				// Pattern.matcher(CharSequence input) : 입력된 문자열에서 패턴을 찾는 Matcher 객체를 만듭니다.
		return m.matches();		// Matcher.matches() : 대상이 되는 문자열이 패턴과 일치하는가를 판단(출력 true, false)
		}
		
		
		public int updateProd(){
			System.out.println("=========== 상품정보수정 =============");

			System.out.print("상품이름>");
			String prodName = ScanUtil.nextLine();
			System.out.print("상품개수>");
			String prodStock = ScanUtil.nextLine();
			System.out.print("상품가격>");
			String prodSale = ScanUtil.nextLine();
			System.out.println("상품상세정보>");
			String prodDetail = ScanUtil.nextLine();

			System.out.print("상품ID>");
			String prodId = ScanUtil.nextLine();

			Map<String, Object> param = new HashMap<>();

			param.put("PROD_NAME", prodName);
			param.put("PROD_TOTALSTOCK", prodStock);
			param.put("PROD_SALE", prodSale);
			param.put("PROD_INFO", prodDetail);
			param.put("PROD_ID", prodId);
			
			int result = adminDao.updateProd(param);
					
			if(0 < result){
				System.out.println("상품정보 수정 성공");
			}else{
				System.out.println("상품정보 수정 실패");
			}
			return View.ADMINPRODMAIN;
		}
		public int deleteProd(){
			System.out.println("=========== 상품삭제 =============");
			System.out.print("삭제할 상품아이디를 입력해주세요.>");
			String prodId = ScanUtil.nextLine();
			
			Map<String, Object> param = new HashMap<>();
			param.put("PROD_ID", prodId);
			
			int result = adminDao.deleteProd(param);
			
			if(result == 1){
				System.out.println("상품삭제 성공");
			}else{
				System.out.println("상품삭제 실패");
			}
			
			return View.ADMINPRODMAIN;
		}
		
		
		
		
		
		
		
}//클래스 end
















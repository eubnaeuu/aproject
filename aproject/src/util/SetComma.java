package util;

import java.util.Scanner;



public class SetComma {
// 숫자를 입력받아 입력받은 숫자에 3자리 마다 콤마(,)를 붙여 출력해주세요
	public static void main(String[] args) {
	SetComma st = new SetComma();
		st.method();
	
	}
	
	void method(){
		String input ="5334";
String str = "";


String x = "";
String y;
String z="";




boolean b = false;
for(int i=0; i<input.length()-1; i++){
	if(input.substring(i,i+1).equals(".")){
		b = true;
		break;	 
			}
		}

	if(b==false){
		str = input;
	} else {
		
		int sonum =input.indexOf("."); // sonum : 소숫점 배열자릿수
		int n1 = sonum-1; // 일의자리 이상 개수
		int n2 = input.length()-sonum; // 소숫점 개수

		y = input.substring(0,sonum); // 일의자리 문자열
		z = input.substring(sonum); // 소숫점 포함 뒷자리 수
		str = y;
	}
		
		int num = (str.length()-1)/3;	// 쉼표 개수
		int num1 = str.length()/3; // 블럭개수
		int num2 = str.length()%3; // 마지막 블럭의 크기
		
		
	char[] strc;
	int count=0;	
	if(num2==0){
		strc = new char[str.length()+(num1-1)];
	} else 
		{strc = new char[str.length()+num1];}
	int j=1;
	for(int i = 0; i < str.length(); i++){
		if(count==3){
			count=0;
			strc[strc.length-j] = ',';
			j++;
			strc[strc.length-j] = str.charAt(str.length()-1-i);
			count++;
			j++;
		 continue;
		}
		strc[strc.length-j] = str.charAt(str.length()-1-i);
		count++;
		j++;			
		
	}
	String ans="";
	if(b==true){
		for(int i = 0; i < strc.length; i++){
			ans += strc[i];	
	}
		ans += z;
		System.out.print(ans);
		} else {
		for(int i = 0; i < strc.length; i++){
			ans += strc[i];
			System.out.print(ans);
		}
		}
	}
	
}	
		
	


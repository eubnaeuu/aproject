package util;

import java.util.Scanner;

import dao.SelectProdDao;

public class SetCommaTS {
	
	private SetCommaTS(){}
	private static SetCommaTS instance;
	public static SetCommaTS getInstance(){
		if(instance == null){
			instance = new SetCommaTS();
		}
		return instance;
	}
	
	public String Setcomma(String str){
		Scanner sc = new Scanner(System.in);
		String number = str;
		String number2 = "";

		int count = 0;
		for (int i = number.length() - 1; i >= 0; i--) {
			number2 = number.charAt(i) + number2;
			count++;
			if (count % 3 == 0 && count != number.length()) {
				number2 = "," + number2;

			}
		}
		return number2;
	}
		
	}


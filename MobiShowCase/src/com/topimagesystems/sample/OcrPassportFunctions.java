package com.topimagesystems.sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import android.util.Log;

public class OcrPassportFunctions {
	public static void printCharactersOneByOne(String ocrResult) {
		for (int i = 0; i < ocrResult.length(); i++) {
			Log.i("printCharactersOneByOne", "ocrResult (" + i + ") " + ocrResult.charAt(i));
		}
	}
	/*
	 * String.split(System.getProperty("line.separator"));
	 * 
	 */
	
	public static void printFirstSplit(String passportString) {
		String[] passportArray = passportString.split("<<");
		for (int i = 0; i < passportArray.length; i++) {
			Log.i("printFirstSplit", "ocrResult (" + i + ") " + passportArray[i]);
		}
	}
	
	public static void printFirstLineSplit(String passportString) {
		
		String[] passportArray = passportString.split("[\\r\\n]+");
		for (int i = 0; i < passportArray.length; i++) {
			Log.i("printFirstSplit", "ocrResult (" + i + ") " + passportArray[i]);
		}
	}
	
	
	public static void parsePassportString(String passportString) {
		String documentType, country, forename, surname,
		id, passportIdCheckDigit, nationality, birthDate, bithdayIdCheckDigit, 
		gender, expirationDate, expirationDateCheckDigit, personalNumber, personalNumberCheckDigit,
		lastCheckDigit;

		String[] passportStringLines = passportString.split("[\\r\\n]+");
		
		passportStringLines[0] = passportStringLines[0].replaceAll("\\s","");
		List<String> passportFirstLineArrayList = 
				new ArrayList<String>(Arrays.asList(passportStringLines[0].split("<<")));
		passportFirstLineArrayList.removeAll(Arrays.asList("", null));
		Log.i("lineParse", "passportFirstLineArrayList " + passportFirstLineArrayList);
		
		List<String> firstPartList = new ArrayList<String>(Arrays.asList(passportFirstLineArrayList.get(0).split("<")));
		firstPartList.removeAll(Arrays.asList("", null));
		Log.i("firstPartList", "list:" + firstPartList);

		Iterator<String> iterator = firstPartList.iterator();
		documentType = iterator.next();
		country = iterator.next();
		forename = country.substring(3);
		country = country.substring(0, 3);
		while (iterator.hasNext())
			forename += ' ' + iterator.next();
		
		List<String> surnamePartList = new ArrayList<String>(Arrays.asList(passportFirstLineArrayList.get(1).split("<")));
		surnamePartList.removeAll(Arrays.asList("", null));
		Log.i("surenamePartList", "list:" + surnamePartList);

		iterator = surnamePartList.iterator();
		surname = iterator.next();
		while (iterator.hasNext())
			surname += ' ' + iterator.next();
		
		Log.i("parsePassportString", "documentType:" + documentType);
		Log.i("parsePassportString", "country:" + country);
		Log.i("parsePassportString", "forename:" + forename);
		Log.i("parsePassportString", "surname:" + surname);
		
		passportStringLines[1] = passportStringLines[1].replaceAll("\\s","");
		List<String> passporSecondLineArrayList = 
				new ArrayList<String>(Arrays.asList(passportStringLines[1].split("<")));
		passporSecondLineArrayList.removeAll(Arrays.asList("", null));
		Log.i("lineParse", "passporSecondLineArrayList " + passporSecondLineArrayList);
		
		iterator = passporSecondLineArrayList.iterator();
		id = iterator.next();
		
		String complexData = iterator.next();
		
		String complexDataSpecialGenderCase = null;
		
		if (passporSecondLineArrayList.size() > 3)
			complexDataSpecialGenderCase =  iterator.next();
		
		lastCheckDigit = iterator.next();
		
		Log.i("parsePassportString", "id:" + id);
		Log.i("parsePassportString", "complexData:" + complexData);
		Log.i("parsePassportString", "complexDataSpecialGenderCase:" + complexDataSpecialGenderCase);
		Log.i("parsePassportString", "lastCheckDigit:" + lastCheckDigit);
		
		passportIdCheckDigit = String.valueOf(complexData.charAt(0));
		nationality = complexData.substring(1,4);
		birthDate = complexData.substring(4,10);
		bithdayIdCheckDigit = String.valueOf(complexData.charAt(10));
		
		Log.i("parsePassportString", "passportIdCheckDigit:" + passportIdCheckDigit);
		Log.i("parsePassportString", "nationality:" + nationality);
		Log.i("parsePassportString", "birthDate:" + birthDate);
		Log.i("parsePassportString", "bithdayIdCheckDigit:" + bithdayIdCheckDigit);
		
		if (complexDataSpecialGenderCase != null) {
			gender = "<";
			complexData = complexDataSpecialGenderCase;
		}
		else {
			gender = String.valueOf(complexData.charAt(11));
			complexData = complexData.substring(12);
		}

		expirationDate = complexData.substring(0,6);
		expirationDateCheckDigit = complexData.substring(6);
		
		Log.i("parsePassportString", "gender:" + gender);
		Log.i("parsePassportString", "expirationDate:" + expirationDate);
		Log.i("parsePassportString", "expirationDateCheckDigit:" + expirationDateCheckDigit);
		
		
		

		Log.i("parsePassportString", "***************Corrections*****************");
		if (documentType.contains("P") || documentType.contains("p"))
			documentType = "P";
		
		country = upperCaseAlphaValueCorrection(country);
		forename = upperCaseAlphaValueCorrection(forename);
		surname = upperCaseAlphaValueCorrection(surname);
		
		id = upperCaseAlphaValueCorrection(id.substring(0,1)) + numberValueCorrection(id.substring(1));
		passportIdCheckDigit = numberValueCorrection(passportIdCheckDigit);
		nationality = upperCaseAlphaValueCorrection(nationality);
		birthDate = numberValueCorrection(birthDate);
		bithdayIdCheckDigit = numberValueCorrection(bithdayIdCheckDigit);
		gender = gender.equals("<") ? "<" : ""+char2GenderChar(gender.charAt(0));//upperCaseAlphaValueCorrection(gender);
		expirationDate = numberValueCorrection(expirationDate);
		expirationDateCheckDigit = numberValueCorrection(expirationDateCheckDigit);
		lastCheckDigit = numberValueCorrection(lastCheckDigit);
		
		
		Log.i("parsePassportString", "documentType:" + documentType);
		Log.i("parsePassportString", "country:" + country);
		Log.i("parsePassportString", "forename:" + forename);
		Log.i("parsePassportString", "surname:" + surname);
		Log.i("parsePassportString", "id:" + id);
		Log.i("parsePassportString", "passportIdCheckDigit:" + passportIdCheckDigit);
		Log.i("parsePassportString", "nationality:" + nationality);
		Log.i("parsePassportString", "birthDate:" + birthDate);
		Log.i("parsePassportString", "bithdayIdCheckDigit:" + bithdayIdCheckDigit);
		Log.i("parsePassportString", "gender:" + gender);
		Log.i("parsePassportString", "expirationDate:" + expirationDate);
		Log.i("parsePassportString", "expirationDateCheckDigit:" + expirationDateCheckDigit);
		Log.i("parsePassportString", "lastCheckDigit:" + lastCheckDigit);
		
	}
	
	public static String upperCaseAlphaValueCorrection(String value) {
		char[] charsArray = value.toCharArray();
		
		for (int i = 0; i < charsArray.length; i++) {
			if (Character.isDigit(charsArray[i]))
				charsArray[i] = digitToUppercaseAlpha(charsArray[i]);
		}
		return String.valueOf(charsArray);
	}
	
	public static String numberValueCorrection(String value) {
		char[] charsArray = value.toCharArray();
		
		for (int i = 0; i < charsArray.length; i++) {
			if (Character.isLetter(charsArray[i]))
				charsArray[i] = char2Digit(charsArray[i]);
		}
		return String.valueOf(charsArray);
	}
	
	
	public static void getPassportStringWithCorrections(String passportString) {
		
		while (passportString.contains("<<"))
			passportString.replaceAll("<<", "<");
		
		String[] passportArray = passportString.split("<");
		
		String passportIndicator = passportArray[0];
		//String passportType = passportString.substring(1, 2);
		String passportCountry = passportArray[1].substring(0, 3);
		String passportFirstName = passportArray[1].substring(0, 3);
		
		String lastNames = "";
		for (int i = 2; i < passportArray.length - 3; i++)
			lastNames += (i == 2) ? "": " " + passportArray[i];
		
		String passportNumber = passportArray[passportArray.length - 3];
		char checkDigitPassportNumber = passportArray[passportArray.length - 3].charAt(0);
		String passportNationality = passportArray[passportArray.length - 3].substring(1, 4);
		String passportBirthday = passportArray[passportArray.length - 3].substring(4, 10);
		char checkDigitBirthday = passportArray[passportArray.length - 3].charAt(10);
		char gender = passportArray[passportArray.length - 3].charAt(10);
		
		
	}
	
	public static char char2GenderChar(char ch) {
		switch (ch) {
		case 'A':
		case 'H':
		case 'W':
			return 'M';
		case 'E':
			return 'F';
		default:
				return ch;
		}
	}
	
	public static char digitToUppercaseAlpha(char ch) {
		switch (ch) {
		case '0': // O
			return 'D';
		case '1': // I
			return 'I';
		case '2': // Z
			return 'Z';
		case '4': // S
			return 'A';
		case '5': // S
			return 'S';
		case '6': // G
			return 'G';
		case '8': // B
			return 'B';
		default:
			return ' ';
		}
	}
	

	public static char char2Digit(char ch) {
		switch (ch) {
		case 'O': // 0
		case 'o':
		case 'D':
			return '0';
		case 'I': // 1
		case '(':
		case ')':
		case 'l':
		case '/':
		case 'i':
			return '1';
		case 'Z': // 2
		case 'z':
			return '2';
		case 'S': // 5
		case '$':
		case 's':
			return '5';
		case 'b': // 6
		case 'G':
			return '6';
		case 'B': // 8
			return '8';
		case 'g': // 9
		case 'q':
			return '9';
		default:
			return 0;
		}
	}

	public static long isAlmostDigit(char ch) {
		switch (ch) {
		case 'O': // 0
		case 'o':
		case 'D':
		case 'I': // 1
		case '(':
		case ')':
		case 'l':
		case '/':
		case 'i':
		case 'Z': // 2
		case 'z':
		case 'S': // 5
		case '$':
		case 's':
		case 'b': // 6
		case 'G':
		case 'B': // 8
		case 'g': // 9
		case 'q':
			return 1;
		default:
			return 0;
		}
	}

	public static long isAlmostAlpha(char ch) {
		switch (ch) {
		case '0': // O,D
		case '1': // I,i
		case '2': // Z,z
		case '5': // S,s
		case '6': // b,G
		case '8': // B
		case '9': // g,q
			return 1;
		default:
			return 0;
		}
	}

	public static char charToDigit(char p) {
		// while (p) {
		if ( p == 'O' || p == 'o' || p == 'D')
			p = '0';
		else if (p == 'I' || p == 'l' || p == 'i')
			p = '1';
		else if (p == 'Z' || p == 'z')
			p = '2';
		else if (p == 'S' || p == 's')
			p = '5';
		else if (p == 'b' || p == 'G')
			p = '6';
		else if (p == 'B')
			p = '8';
		else if (p == 'g' || p == 'q')
			p = '9';
		// p++;
		// }
		return p;
	}

}

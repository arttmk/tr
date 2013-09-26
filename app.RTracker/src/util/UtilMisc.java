package util;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 雑多なライブラリ
 * @author arata.hasebe
 */
public class UtilMisc {
	/**
	 * カンマ区切りデータを分割
	 * @param csv
	 * @return
	 */
	public static String[] splitter(String csv){
		return splitter(csv, ",");
	}
	public static String[] splitter(String text, String separator){
		return splitter(text, separator, false);
	}
	public static String[] splitter(String text, String separator, boolean isNullable){
		if(isEmpty(text)) return (isNullable ? null : new String[0]);
		String[] vals = text.split(separator);
		for(int i = 0; i < vals.length; i++) {
			vals[i] = vals[i].trim();
		}
		return vals;
	}
	public static String[] split(String text, String separator) {
		if(text == null || text.length() == 0) return new String[0];

		ArrayList list = new ArrayList();

		if(separator.startsWith("\\") && separator.length() >= 2) separator = separator.substring(1);

		int start = 0, end = 0;
		while(start < text.length() && (end = text.indexOf(separator, start)) >= 0) {
			list.add(text.substring(start, end));
			start = end + separator.length();
		}
		list.add(text.substring(start));

		String[] ret = new String[list.size()];
		for(int i = 0; i < ret.length; i++) {
			ret[i] = (String)list.get(i);
		}
		return ret;
	}

	public static ArrayList splitToList(String csv){
		return splitToList(csv, ",");
	}
	public static ArrayList splitToList(String text, String separator){
		ArrayList list = new ArrayList();
		String[] vals = splitter(text, separator);
		for(int i = 0; i < vals.length; i++) {
			if(vals[i].length() > 0) list.add(vals[i]);
		}
		return list;
	}

	public static int[] listToIntArray(ArrayList list) {
		int[] ids = new int[list.size()];
		for(int i = 0; i < list.size(); i++) {
			ids[i] = ((Integer)list.get(i)).intValue();
		}
		return ids;
	}

	//コロン区切りの文字列をINT型配列にする
	public static int[] toIntArray(String csv) {
		ArrayList list = splitToList(csv);
		int[] ids = new int[list.size()];
		for(int i = 0; i < list.size(); i++) {
			int val = toInt((String)list.get(i));
			if(val > 0) ids[i] = val;
		}
		return ids;
	}

	/**
	 * 文字列の配列に変換
	 * @param list
	 * @return
	 */
	public static String[] toStringArray(ArrayList list) {
		int len = (list == null ? 0 : list.size());
		String[] arr = new String[len];
		if(len > 0) {
			for(int i = 0; i < len; i++) {
				Object obj = list.get(i);
				if(obj == null) continue;
				arr[i] = obj.toString();
			}
		}
		return arr;
	}



	/**
	 * 空の文字列かどうか
	 */
	public static boolean isEmpty(String value){
		return (value == null || value.length() == 0);
	}

	/**
	 * 文字列が、特定のキーワードを含んでいるかどうか
	 * @param src
	 * @param words
	 * @return
	 */
	public static boolean containsEitherWord(String src, String[] words){
		return containsEitherWord(src, words, true);
	}
	public static boolean containsEitherWord(String src, String[] words, boolean isLike){
		if(isEmpty(src) || words == null || words.length == 0) return false;

		for(int i = 0; i < words.length; i++){
			if(words[i].length() == 0) continue;
			if(isLike && src.indexOf(words[i]) != -1) return true;
			if(!isLike && src.equals(words[i])) return true;
		}

		return false;
	}

	/**
	 * 文字列がメールアドレスかどうか
	 * @param mailAdress
	 * @return	true:メールアドレスである
	 */
	public static boolean checkMailAddress(String mailAdress){
		String ptnStr ="[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+";
		Pattern ptn = Pattern.compile(ptnStr);
		Matcher mc = ptn.matcher(mailAdress);
		return mc.matches();
	}

	/**
	 * リストをCSVに変換
	 * @param list
	 * @return
	 */
	public static String toString(ArrayList list, String separator){
		StringBuffer buf = new StringBuffer();
		if(!isEmpty(list)){
			for(int i = 0; i < list.size(); i++){
				if(i > 0) buf.append(separator);
				buf.append(list.get(i).toString());
			}
		}
		return buf.toString();
	}
	public static String toString(String[] list, String separator){
		StringBuffer buf = new StringBuffer();
		if(list != null){
			for(int i = 0; i < list.length; i++){
				if(i > 0) buf.append(separator);
				buf.append(list[i]);
			}
		}
		return buf.toString();
	}
	public static String toString(long[] list, String separator){
		StringBuffer buf = new StringBuffer();
		if(list != null){
			for(int i = 0; i < list.length; i++){
				if(i > 0) buf.append(separator);
				buf.append(list[i]);
			}
		}
		return buf.toString();
	}
	public static String toString(int[] list, String separator){
		StringBuffer buf = new StringBuffer();
		if(list != null){
			for(int i = 0; i < list.length; i++){
				if(i > 0) buf.append(separator);
				buf.append(list[i]);
			}
		}
		return buf.toString();
	}

	/**
	 * リストをCSVに変換
	 * @param list
	 * @return
	 */
	public static String toCsv(ArrayList list){
		return toString(list, ", ");
	}

	/**
	 * 高速な数値変換ロジック（整数のみ、Hexや小数には対応しない）
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static int toInt(String value) {
		return (int)toLong(value);
	}
	public static long toLong(String value) {
		long n = 0;
		byte sign = 0;
		if(value == null) return 0;

		for(int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			if(c == '-' && sign == 0) {
				sign = -1;
				continue;
			}
			if(c < '0' || c > '9') {
				if(n > 0) break;
				if(c != ' ' && c != ',') break;
				continue;
			}
			n *= 10;
			n += c - '0';
		}

		if(sign == -1) n *= sign;
		return n;
	}
	public static double toDouble(String value) {
		long n = 0;
		byte sign = 0;
		boolean isDecimal = false;
		int div = 1;
		if(value == null) return 0;

		for(int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			if(c == '-' && sign == 0) {
				sign = -1;
				continue;
			}
			if(c == '.') {
				isDecimal = true;
				continue;
			}
			if(c < '0' || c > '9') {
				if(n > 0) break;
				if(c != ' ' && c != ',') break;
				continue;
			}
			n *= 10;
			n += c - '0';
			if(isDecimal) div *= 10;
		}

		if(sign == -1) n *= sign;
		return (div == 1 ? (double)n : ((double)n / (double)div));
	}
	public static boolean isNumber(String value) {
		return isNumber(value, false);
	}
	public static boolean isNumber(String value, boolean whenNull) {
		if(isEmpty(value)) return whenNull;

		for(int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			if(c == '-' && i == 0) continue;
			if(c < '0' || c > '9') {
				if(c == ' ' || c == ',') continue;
				return false;
			}
		}

		return true;
	}

	/**
	 * リストがnullまたは空
	 * @param list
	 * @return
	 */
	public static boolean isEmpty(ArrayList list){
		return (list == null || list.size() == 0);
	}



	/**
	 * 割合を求める
	 * @param numerator
	 * @param denominator
	 * @return
	 */
	public static long calcRate(long numerator, long denominator) {
		if(numerator == 0 || denominator == 0) return 0;
		return (numerator * 100) / denominator;
	}
	public static long percent(long numerator, long denominator) {
		return calcRate(numerator * 100, denominator);
	}

	/**
	 * タグなどの文字列を除去
	 * @param text
	 * @return
	 */
	public static String validateString(String text) {
		if(isEmpty(text)) return text;
		return text.replaceAll("[\\t\\n\\r\\\\<>'\"]", "").trim();
	}

	/**
	 * 文字列置換
	 * @param text
	 * @param src
	 * @param dest
	 * @return
	 */
	public static String replaceAll(String text, String src, String dest){
		if(isEmpty(text) || isEmpty(src)) return text;
		if(dest == null) dest = "";

		String buf = text;
		int srcLen = src.length(), destLen = dest.length();

		int start = 0, find;

		while((find = buf.indexOf(src, start)) >= 0){
			buf = buf.substring(0, find) + dest + buf.substring(find + srcLen, buf.length());
			start = find + destLen;
		}

		return buf;
	}
	public static String replaceCrLf(String text, String dest){
		if(isEmpty(text)) return text;
		text = replaceAll(text, "\r\n", "\n");
		text = replaceAll(text, "\r", "\n");
		text = replaceAll(text, "\n", dest);
		return text;
	}
	/**
	 * MD5暗号化
	 * @param text
	 * @return
	 */
	public static String md5(String text) {
		StringBuffer buf = new StringBuffer();
		String hex = "0123456789abcdef";

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] dat = text.getBytes();
			md.update(dat);
			dat = md.digest();

			for(int i = 0; i < dat.length; i++) {
				int val = dat[i];
				if(val < 0) val += 256;
				buf.append(hex.charAt(val / 16));
				buf.append(hex.charAt(val % 16));
			}

		} catch(Exception e) {

		}

		return buf.toString();
	}

	/**
	 * シェルの実行
	 * @param text
	 * @return
	 */
	public static boolean execSh(String command) {
		if(isEmpty(command)) return false;
		try {
			String[] cmdarray = {"/bin/sh", "-c", command};
			Runtime.getRuntime().exec(cmdarray);
		} catch(Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * タグを置換
	 * @param src
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static String replaceTags(String src, HashMap map) throws Exception {
		return replaceTagArray(replaceTagsPrepare(src), map);
	}
	public static String[] replaceTagsPrepare(String src) {
		return replaceTagsPrepare(src, "\\{\\*");
	}
	public static String[] replaceTagsPrepare(String src, String startRegex) {
		if(src == null) return null;
		return src.split(startRegex);
	}
	public static String[] replaceTagsPrepare(String src, String startText, String endText) {
		if(src == null) return null;
		return split2(src, startText, endText);
	}
	private static String[] split2(String text, String separator, String endText) {
		String[] arr = split(text, separator);
		if(arr == null || arr.length == 0) return null;

		ArrayList list = new ArrayList();
		for(int i = 0; i < arr.length; i++) {
			if(i == 0 || isAsciiTag(arr[i], 0, endText)) {
				list.add(arr[i]);
			} else {
				int prevIndex = list.size() - 1;
				String prevString = (String)list.get(prevIndex);
				list.set(prevIndex, prevString + separator + arr[i]);
			}
		}

		String[] ret = new String[list.size()];
		for(int i = 0; i < ret.length; i++) {
			ret[i] = (String)list.get(i);
		}
		return ret;
	}
	private static boolean isAsciiTag(String text, int start, String endText) {
		int end = text.indexOf(endText, start);
		if(end < 0) return false;
		for(int i = start; i < end; i++) {
			char c = text.charAt(i);
			switch(c) {
			case ' ':
			case '(':
			case ';':
			case '\n':
				return false;
			}

		}
		return true;
	}

	public static String replaceTagArray(String[] elements, HashMap map) throws Exception {
		return replaceTagArray(elements, map, "*}");
	}
	public static String replaceTagArray(String[] elements, HashMap map, String endTag) throws Exception {
		if(elements == null || map == null) return null;

		StringBuilder buf = new StringBuilder();
		for(int i = 0; i < elements.length; i++) {
			String elem = elements[i];
			if(elem.length() == 0) continue;
			int iEnd = (i > 0 ? elem.indexOf(endTag) : -1);
			if(iEnd < 0) {
				buf.append(elem);
				continue;
			}

			String replaceValue = (String)map.get(elem.substring(0, iEnd));
			if(replaceValue != null) buf.append(replaceValue);
			iEnd += endTag.length();
			if(iEnd < elem.length()) buf.append(elem.substring(iEnd, elem.length()));
		}

		return buf.toString();
	}


	/**
	 * 文字列の結合
	 * @param vals
	 * @param splitter
	 * @return
	 */
	public static String join(String[] vals, String splitter) {
		return join(vals, splitter, false);
	}
	public static String join(String[] vals, String splitter, boolean endsWithSplitter) {
		StringBuilder buf = new StringBuilder();
		for(int i = 0; i < vals.length; i++) {
			if(!endsWithSplitter && i > 0) buf.append(splitter);
			buf.append(vals[i]);
			if(endsWithSplitter) buf.append(splitter);
		}
		return buf.toString();
	}

	/**
	 * 小数点以下が0の場合にINT型で返す
	 */
	public static String toString(double val) {
		String s = Double.toString(val);
		if(s.endsWith(".0")) return Integer.toString((int)val);
		return s;
	}

	/**
	 * 文字列はそのまま返す
	 */
	public static String toString(String str) {
		return str;
	}
	
	
	/**
	 * 2つの日付の差を求めます。
	 * 日付文字列 strDate1 - strDate2 が何日かを返します。
	 * 
	 * @param strDate1    日付文字列 yyyy/MM/dd
	 * @param strDate2    日付文字列 yyyy/MM/dd
	 * @return    2つの日付の差
	 * @throws ParseException 日付フォーマットが不正な場合
	 */
	public static int differenceDays(String strDate1,String strDate2) 
	    throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    Date date1 = format.parse(strDate1);
	    Date date2 = format.parse(strDate2);
	    return differenceDays(date1,date2);
	}
	/**
	 * 2つの日付の差を求めます。
	 * java.util.Date 型の日付 date1 - date2 が何日かを返します。
	 * 
	 * 計算方法は以下となります。
	 * 1.最初に2つの日付を long 値に変換します。
	 * 　※この long 値は 1970 年 1 月 1 日 00:00:00 GMT からの
	 * 　経過ミリ秒数となります。
	 * 2.次にその差を求めます。
	 * 3.上記の計算で出た数量を 1 日の時間で割ることで
	 * 　日付の差を求めることができます。
	 * 　※1 日 ( 24 時間) は、86,400,000 ミリ秒です。
	 * 
	 * @param date1    日付 java.util.Date
	 * @param date2    日付 java.util.Date
	 * @return    2つの日付の差
	 */
	public static int differenceDays(Date date1,Date date2) {
	    long datetime1 = date1.getTime();
	    long datetime2 = date2.getTime();
	    long one_date_time = 1000 * 60 * 60 * 24;
	    long diffDays = (datetime1 - datetime2) / one_date_time;
	    return (int)diffDays; 
	}

	
}
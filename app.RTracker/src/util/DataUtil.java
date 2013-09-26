package util;

import item.Adgroup;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.sql.*;

import bigQuery.BigQueryApiAccess;

import com.google.api.services.bigquery.model.TableCell;
import com.google.api.services.bigquery.model.TableRow;

import dao.AdgroupDao;

import servlet.LoadServlet;


/**
 * 変数変換クラス
 * @author arata.hasebe
 *
 * 概要：
 * 	null処理が主要な目的
 *
 */
public class DataUtil {

	// 列の型
	public static final int TYPE_STRING = Types.VARCHAR;
	public static final int TYPE_LONG = Types.INTEGER;
	public static final int TYPE_DOUBLE = Types.DOUBLE;
	public static final int TYPE_TIMESTAMP = Types.TIMESTAMP;
	public static final int TYPE_BOOLEAN = Types.BOOLEAN;
	public static final int TYPE_GROUPING = Types.OTHER;
	public static final int TYPE_NULL = Types.NULL;

	// 変換の型
	public static final String FORMAT_NUMBER = "###,###";
	public static final String FORMAT_DATE = "yyyy-MM-dd";
	public static final String FORMAT_DATE_SLASH = "yyyy/MM/dd";
	public static final String FORMAT_PERCENT = "###%";

	public static final String FORMAT_CHECK_DATE = "[1-2][0-9]{3}-[0-9]{1,2}-[0-9]{1,2}";

	private static HashMap formatMap = new HashMap();

	// nullでない文字列にする
	public static String toString(String longValue){
		return (longValue == null ? "" : longValue);
	}
	public static String toString(long longValue){
		return toString(Long.valueOf(longValue));
	}
	public static String toString(Long lVal){
		if(lVal == null) return "";
		return lVal.toString();
	}
	public static String toString(double doubleValue){
		return toString(new Double(doubleValue));
	}
	public static String toString(Double dVal){
		if(dVal == null) return "";

		// 小数以下がなければ、"10.0"ではなく"10"と出したい
		Long lVal = Long.valueOf(dVal.longValue());

		if(lVal.doubleValue() == dVal.doubleValue()){
			return lVal.toString();
		}

		return dVal.toString();
	}

	// 日付や数値をフォーマットに合わせて文字列に変換します
	public static String toString(Object value, String format){ return toString(value, getType(value), format); }
	public static String toString(Object value, int type, String format){
		if(value == null) return "";

		if(format == null){
			switch(type){
			case TYPE_STRING:		return (String)value;
			case TYPE_TIMESTAMP:	return ((Timestamp)value).toString();
			case TYPE_LONG:			return toString((Long)value);
			case TYPE_DOUBLE:		return toString((Double)value);
			}
		}
		else
		{
			switch(type){
			case TYPE_STRING:		return (String)value;
			case TYPE_TIMESTAMP:	return formatTimestamp((Timestamp)value, format);

			case TYPE_LONG:
			case TYPE_DOUBLE:
				DecimalFormat decimalFormat = new DecimalFormat(format);
				return decimalFormat.format(value);
			}
		}

		return "";
	}

	// オブジェクトの型を判定します
	public static int getType(Object object){
		if(object == null) return TYPE_NULL;

		String className = object.getClass().getName();

		if(String.class.getName().equals(className)){
			return TYPE_STRING;
		}else if(Long.class.getName().equals(className)){
			return TYPE_LONG;
		}else if(Double.class.getName().equals(className)){
			return TYPE_DOUBLE;
		}else if(Timestamp.class.getName().equals(className)){
			return TYPE_TIMESTAMP;
		}else if(Boolean.class.getName().equals(className)){
			return TYPE_BOOLEAN;
		}

		return TYPE_NULL;
	}

	// nullでも動くUpperCase
	public static String toUpperCase(String value){
		return toString(value).toUpperCase();
	}

	// long値
	public static long longValue(Long value){
		return (value == null ? 0 : value.longValue());
	}
	public static long longValue(Double value){
		return (value == null ? 0 : value.longValue());
	}
	public static long longValue(Timestamp value){
		return (value == null ? 0 : value.getTime() / 1000);
	}
	public static long longValue(String value){
		if(isEmpty(value)) return 0;
		if(!isLegalFormat(value, TYPE_LONG)) return 0;
		return Long.parseLong(value);
	}

	// int値
	public static int intValue(Long value){
		return (value == null ? 0 : value.intValue());
	}
	public static int intValue(String value){
		return (int)longValue(value);
	}

	// double値
	public static double doubleValue(Double value){
		return (value == null ? 0 : value.doubleValue());
	}
	public static double doubleValue(Long value){
		return (value == null ? 0 : value.doubleValue());
	}
	public static double doubleValue(String value){
		if(!isLegalFormat(value, TYPE_DOUBLE)) return 0;
		return Double.parseDouble(value);
	}

	//BigDecimal
	public static BigDecimal BigDecimalValue(String value){
		if(isEmpty(value)) return new BigDecimal(0);
		if(!isLegalFormat(value, TYPE_DOUBLE)) return new BigDecimal(0);
		return new BigDecimal(value);
	}

	// boolean値
	public static boolean booleanValue(Boolean value){
		return (value == null ? false : value.booleanValue());
	}
	public static boolean booleanValue(Long value){
		if(value == null) return false;
		return (((Long)value).longValue() == 0 ? false : true);
	}
	public static boolean booleanValue(Double value){
		if(value == null) return false;
		return (((Double)value).doubleValue() == 0 ? false : true);
	}

	// 空の文字列かどうか
	public static boolean isEmpty(String value){
		return (value == null || value.length() == 0);
	}

	// 形式は正しいか？
	public static boolean isLegalFormat(String value, int type){
		// 空なら無視
		if(isEmpty(value)) return true;

		// スペースがついてる分には許そう
		String val = value.trim();
		if(isEmpty(val)) return true;

		// 型ごとに判定
		switch(type){
		case TYPE_LONG:
			// 0, 147, 00763
			if(formatCheck(val, "-?[0-9]+")) return true;
			break;

		case TYPE_DOUBLE:
			// 0, 147, 00763
			if(formatCheck(val, "-?[0-9]+")) return true;

			// 0, 147, 00763, 65.0, 026.954
			if(formatCheck(val, "-?[0-9]+\\.[0-9]+")) return true;
			break;

		case TYPE_TIMESTAMP:
			if(toTimestamp(val) != null) return true;
			break;

		default:
			return true;
		}
		return false;
	}

	// 日付型に変換
	public static Timestamp toTimestamp(String value){
		if(isEmpty(value)) return null;

		// 2006.08.31, 2006/08/31 → 2006-08-31
		String val = value.trim().replaceAll("\\.", "-").replaceAll("/", "-");

		if(formatCheck(val, "[1-2][0-9]{3}-[0-9]{1,2}-[0-9]{1,2}")){
			// 2006-08-31
		}else if(formatCheck(val, "[0-9]{2}-[0-9]{1,2}-[0-9]{1,2}")){
			if(val.substring(0, 1).equals("9")){
				// 98-8-31 → 1998-8-31
				val = new StringBuffer().append("19").append(val).toString();
			}else{
				// 06-8-31 → 2006-8-31
				val = new StringBuffer().append(getSystemDate("yyyy").substring(0, 2)).append(val).toString();
			}
		}else if(formatCheck(val, "[0-9]{1,2}-[0-9]{1,2}")){
			// 8-31 → 2006-8-31
			val = new StringBuffer().append(getSystemDate("yyyy")).append("-").append(val).toString();
		}else if(formatCheck(val, "[1-2][0-9]{3}-[0-9]{1,2}-[0-9]{1,2} [0-9]{2}:[0-9]{2}:[0-9]{2}")){
			// 2006-08-31 15:30:22
			val += ".000";
		}else if(formatCheck(val, "[1-2][0-9]{3}-[0-9]{1,2}-[0-9]{1,2} [0-9]{2}:[0-9]{2}:[0-9]{2}-[0-9]+")){
			// 2006-08-31 15:30:22-120 → 2006-08-31 15:30:22.120
			val = new StringBuffer().append(val.substring(0, 19)).append(".").append(val.substring(20, val.length())).toString();
		}else{
			return null;
		}

		// 年月日の文字列取得
		String[] ymd = val.split(" ")[0].split("-");

		// 年チェック
		int y = Integer.parseInt(ymd[0]);
		if(y < 1900 || y > Integer.parseInt(getSystemDate("yyyy")) + 500) return null;

		// 月チェック
		int m = Integer.parseInt(ymd[1]);
		if(m < 1 || m > 12) return null;

		// 日チェック
		int d = Integer.parseInt(ymd[2]);
		if(d < 1 || d > 31) return null;

		// 年月日チェック
		Calendar date = new GregorianCalendar(y, m - 1, d);
		if(date.get(Calendar.YEAR) != y || date.get(Calendar.MONTH) != m - 1 || date.get(Calendar.DATE) != d) return null;

		// 時刻がなければつける
		if(val.indexOf(":") == -1) val += " 00:00:00.000";
		Timestamp time = Timestamp.valueOf(val);

		return time;
	}

	/**
	 * String型のオブジェクトをDate型に変換します.
	 */
	static public final String DATE_PATTERN ="yyyy-MM-dd";
	
	public static java.util.Date convertString2Date(String source) {
	  try {
	    return (new SimpleDateFormat(DATE_PATTERN)).parse(source);
	  } catch (ParseException e) {
	    return null;
	  }
	}
	
    // システム日付を日付型で返す
    public static Timestamp getSystemDate(){
		return new Timestamp(Calendar.getInstance().getTimeInMillis());
	}

	// システム日付を文字列で返す
    public static String getSystemDate(String format){
		return formatTimestamp(getSystemDate(), format);
	}

    // 日付型を文字列に変換する
    public static String formatTimestamp(Timestamp timestamp, String format){
		SimpleDateFormat DateFormat = new SimpleDateFormat(format);
		return DateFormat.format(timestamp);
    }

    // 日付型を文字列に変換する(Date版)
    public static String formatDate(java.util.Date date, String format){
		SimpleDateFormat DateFormat = new SimpleDateFormat(format);
		return DateFormat.format(date);
    }
    
	// 正規表現の形式かどうか
	public static boolean formatCheck(String value, String pattern){
		Pattern pt = (Pattern)formatMap.get(pattern);
		if(pt == null) {
			pt = Pattern.compile(pattern);
			formatMap.put(pattern, pt);
		}
		Matcher matcher = pt.matcher(value);
		if(!matcher.matches()) return false;
		return true;
	}

	/**
	 * 秒単位のTV値を返す
	 * @return
	 * @throws Exception
	 */
	public static long getTv() throws Exception {
		return getTv(getSystemDate());
	}
	public static long getTv(String datetime) throws Exception {
		return getTv(toTimestamp(datetime));
	}
	private static long getTv(Timestamp time) throws Exception {
		return time.getTime() / 1000;
	}
	public static String formatString(long tv) throws Exception {
		return formatString(tv, FORMAT_DATE);
	}
	public static String formatString(long tv, String format) throws Exception {
		if(tv == 0) return null;
		Timestamp time = new Timestamp(tv * 1000);
		return formatTimestamp(time, format);
	}
	
	
	/*
	 * adgroupテーブル作成処理
	 */
	public static void createAdgroupData(){
		 // big query
		List<TableRow> rows = null;
		
		AdgroupDao adgroup_dao = new AdgroupDao();
		
		
		String query  = "SELECT cf_id, brand, class_l_name FROM [rtt_tracker.raw_tv_cf_mst] limit 3;"; //test用にlimit
		
		// cf_id→target_id
		// brand→name
		// class_l_name→detail
		// でとりあえず想定
		
		try {
			 rows = BigQueryApiAccess.doQuery(query);
		} catch (Exception e) {
			SecurityUtil.sendErrorMail(e.toString());
			LoadServlet.logger.severe(e.toString()); 
		}
		
		
		// 検索結果があれば表示
		if (rows != null) {
			LoadServlet.logger.warning("rows:" + rows.size());
			for (TableRow row : rows) {
				Adgroup adgroup = new Adgroup();
				adgroup.setMedia(FrameConst.media_tv); // tvなので2
				
				int i = 0;
				for (TableCell field : row.getF()) {
					
					//System.out.printf("%-50s", field.getV());
					if(i == 0) adgroup.setTarget_id(field.getV().toString());
					if(i == 1){
						
						// nullかどうか
						if(field.getV().toString().indexOf("java.lang.Object@") == -1){ // nullでなければ値を設定
							adgroup.setName(field.getV().toString());
						}

					}
					if(i == 2){
						if(field.getV().toString().indexOf("java.lang.Object@") == -1){ // nullでなければ値を設定
							adgroup.setDetail(field.getV().toString());
						}
					}
					
					i++;
					
					
				}
				//System.out.println();
				
				// adgroupテーブルに存在するかどうか
				int adg_id = adgroup_dao.isAlreadyExistAdgroup(adgroup.getTarget_id(), adgroup.getMedia());
				if(adg_id == 0){
					// insert
					adgroup_dao.insertAdgroup(adgroup);
					
				}else{
					// update
					adgroup.setId(adg_id);
					adgroup_dao.updateAdgroup(adgroup);
				}
				
			}
		} else {
			System.out.println("no results.");
		}

		LoadServlet.logger.warning("adgroup data updated");
		
	 }

	


}
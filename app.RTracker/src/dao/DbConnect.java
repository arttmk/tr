package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import util.SecurityUtil;
import util.UtilConfig;
import util.UtilMisc;

import com.google.appengine.api.rdbms.AppEngineDriver;

public class DbConnect {

	private static final String APP_LOG			= "DB CONNECT";
	private static final String APP_NAME		= "DB CONNECT PROC";
	

	// DB情報
	private static String user = UtilConfig.DB_USER;
	private static String passwd = UtilConfig.DB_PASSWORD;
	private static String url = UtilConfig.DB_URL;
	
	protected Connection con = null;
	protected Statement stmt = null;
	protected ResultSet rs = null;
	
	
	// 基本構文
	protected static final String SELECT = " select ";
	protected static final String FROM = " from ";
	protected static final String WHERE = " where ";
	protected static final String AND = " and ";
	protected static final String OR = " or ";
	protected static final String EQUAL = " = ";
	protected static final String LESS_THAN		= " < ";
	protected static final String LESS_EQUAL	= " <= ";
	protected static final String GREATER_THAN	= " > ";
	protected static final String GREATER_EQUAL	= " >= ";
	protected static final String NOT_EQUAL		= " <> ";
	protected static final String LIKE = " like ";
	protected static final String IN = " in ";
	protected static final String NOTIN = " not in ";
	protected static final String INSERT = " insert ";
	protected static final String DELETE = " delete ";
	protected static final String UPDATE = " update ";
	protected static final String SET = " set ";
	protected static final String INTO = " into ";
	protected static final String NULL = " null ";
	protected static final String ISNULL = " is null ";
	protected static final String LEFTPARENTHESIS =  " ( ";
	protected static final String RIGHTPARENTHESIS = " ) ";
	protected static final String VALUES = " values ";
	protected static final String LIMIT		= " limit ";

	public DbConnect(){
//		StringBuffer buf = new StringBuffer();
//		String sql = "select name from login_user";
//		
//		try {
//			// connect
//			this.connect();
//			
//			rs = stmt.executeQuery(sql);
//			
//            while(rs.next()){
//           	 //System.out.println(rs.getString("yyyymmdd"));
//           	buf.append(",");
//           	buf.append(rs.getString("name"));
//           	
//            System.out.println(buf.toString());
//            
//            // close
//            this.closeConnection();
//           }
//		} catch (Exception e) {
//			logger.writeErrorLog(e);
//			logger.sendErrorMail();
//		}
		
		
	}
	
	
	/*
	 * DB接続
	 * 
	 */
	protected void connect() throws Exception {
//    	Class.forName("com.mysql.jdbc.Driver");
//        this.con = DriverManager.getConnection(url, user, passwd);
		
        DriverManager.registerDriver(new AppEngineDriver());
        //this.con = DriverManager.getConnection(url);
        this.con =  DriverManager.getConnection(url, user, passwd);
        //this.con =  DriverManager.getConnection("jdbc:google:rdbms://brainpad.co.jp:tracker:tracker/tracker", "rt", "rt");

        this.stmt = con.createStatement();
	}

	/**
	 * コミット
	 * @throws Exception
	 */
	protected void commitTrans() throws Exception {
		this.con.commit();
	}
    
	/**
	 * ロールバック
	 * @throws Exception
	 */
	protected void rollbackTrans() throws Exception {
		this.con.rollback();
	}
	
	/**
	 * connectionクローズ
	 * @throws Exception
	 */
	protected void closeConnection() throws Exception {
	    if(rs != null) rs.close();
	    if(stmt != null) stmt.close();
	    if(con != null) con.close();
	}
	
	
	protected static String quoteString(String value) {
		if(value == null) return "''";
		value = UtilMisc.replaceAll(value, "\\", "\\\\");
		value = UtilMisc.replaceAll(value, "'", "\\'");
		return "'" + value + "'";
	}
	
	protected static String quoteStringWithLike(String value) {
		if(value == null) return "''";
		value = UtilMisc.replaceAll(value, "\\", "\\\\");
		value = UtilMisc.replaceAll(value, "'", "\\'");
		return "'%" + value + "%'";
	}
	
	public void finalize() {
		// google appsにデプロイして実行した際にエラーになったので
//		try {
//			this.closeConnection();
//		} catch (Exception e) {
//			SecurityUtil.sendErrorMail(e.toString());
//		}
	}
	
	
	protected String whereOrAnd(String sql){
		String str = "";
		if(sql.indexOf(WHERE) > -1){
			str = AND;
		}else{
			str = WHERE;
		}
		
		return str;
	}
	
	
}


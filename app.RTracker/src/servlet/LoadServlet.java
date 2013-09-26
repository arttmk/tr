package servlet;

import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import util.DataUtil;
import util.UtilConfig;


/*
 * tomcat起動時に実行されるロード処理
 *
 */
public class LoadServlet extends HttpServlet {

	private static final long serialVersionUID = -7284205346958158616L;
	  
	public static Logger logger = Logger.getLogger("TrackerLog"); // ログはg appのコンソールで一括で確認できるのみなので、各クラスでファイルを分ける必要はないはず

	public InetAddress addr;

	
	public static String calendar_data;
	
	public static boolean isDebug = Boolean.valueOf(UtilConfig.IS_DEBUG);

	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		//Rエンジンを起動する。
		//同一環境で複数回起動するとエラーになるため、tomcat起動時に一回起動するようにする。
	    //engine = new Rengine(new String[]{"--no-save"}, false, null);
	    
	    
	    // カレンダーデータ取得
	    //DataUtil.getYTMTCalendarnData(logger);
		//LoginUserDao db = new LoginUserDao();
		
		// adgroupデータ更新処理
		// TODO
		// 起動時処理が重くなるので、一時的にコメントアウト
		//DataUtil.createAdgroupData();
	}

	/**
	 * main proc
	 */
	public void doGet(
		HttpServletRequest req,
		HttpServletResponse res
	) throws ServletException, IOException {

		//System.out.println("test2");
		// ロード指定でデータ取得
		//DataUtil.getYTMTCalendarnData(logger);
		
		// adgroupデータ更新処理
		DataUtil.createAdgroupData();

	}
	
	// tomcatのthread破棄時に実行される
	public void destroy(){
		
		
	}
	

}
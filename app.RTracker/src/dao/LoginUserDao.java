package dao;

import servlet.LoadServlet;
import util.SecurityUtil;
import item.LoginUser;

public class LoginUserDao extends DbConnect{

	public LoginUserDao(){

	}
	
	public static final String	TABLE_NAME = "login_user";
	public static final String	ID = "id";
	public static final String	LOGIN_ID = "login_id";
	public static final String	NAME = "name";
	public static final String	COMPANY_ID = "company_id";
	public static final String	PASSWORD = "password";
	
	
	/*
	 * ログイン認証処理
	 */
	public LoginUser login_confirm(String login_id, String password){
		LoginUser loginUser = new LoginUser();
		
		StringBuffer buf = new StringBuffer();
		buf.append(SELECT);
		buf.append(LoginUserDao.ID);
		buf.append(",");
		buf.append(LoginUserDao.NAME);
		buf.append(",");
		buf.append(LoginUserDao.LOGIN_ID);
		buf.append(",");
		buf.append(LoginUserDao.COMPANY_ID);
		buf.append(FROM);
		buf.append(LoginUserDao.TABLE_NAME);
		buf.append(WHERE);
		buf.append(LoginUserDao.LOGIN_ID);
		buf.append(EQUAL);
		buf.append(quoteString(login_id));
		buf.append(AND);
		buf.append(LoginUserDao.PASSWORD);
		buf.append(EQUAL);
		buf.append(quoteString(password));
		
		//System.out.println(buf.toString());
		try {
			// connect
			connect();
			
			rs = stmt.executeQuery(buf.toString());
//			rs.next();
//			id = rs.getInt(1);
			
			while(rs.next()){
				loginUser.setId(rs.getInt(LoginUserDao.ID));
				loginUser.setLogin_id(rs.getString(LoginUserDao.LOGIN_ID));
				loginUser.setName(rs.getString(LoginUserDao.NAME));
				loginUser.setCompany_id(rs.getInt(LoginUserDao.COMPANY_ID));
			}
            
		} catch (Exception e) {
			SecurityUtil.sendErrorMail(e.toString());
			LoadServlet.logger.severe(e.toString()); 
		}finally{
			try {
				// close
				closeConnection();
			} catch (Exception e) {
				SecurityUtil.sendErrorMail(e.toString());
				LoadServlet.logger.severe(e.toString()); 
			}
		}

		
		return loginUser;
	}
}

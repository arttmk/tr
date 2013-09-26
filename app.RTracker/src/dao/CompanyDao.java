package dao;

import servlet.LoadServlet;
import util.SecurityUtil;


public class CompanyDao extends DbConnect{

	public CompanyDao(){

	}
	
	public static final String	TABLE_NAME = "company";
	public static final String	ID = "id";
	public static final String	NAME = "name";
	
	
	/*
	 * ログイン認証処理
	 */
	public String get_companyName(int company_id){
		String company_name = "";
		
		StringBuffer buf = new StringBuffer();
		buf.append(SELECT);
		buf.append(CompanyDao.ID);
		buf.append(",");
		buf.append(CompanyDao.NAME);
		buf.append(FROM);
		buf.append(CompanyDao.TABLE_NAME);
		buf.append(WHERE);
		buf.append(CompanyDao.ID);
		buf.append(EQUAL);
		buf.append(company_id);

		
		//System.out.println(buf.toString());
		try {
			// connect
			connect();
			
			rs = stmt.executeQuery(buf.toString());
//			rs.next();
//			id = rs.getInt(1);
			
			while(rs.next()){
				company_name = rs.getString(CompanyDao.NAME);
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
		

		
		return company_name;
	}
}

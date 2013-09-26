package dao;

import item.Campaign;
import item.LoginUser;

import java.sql.Date;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import servlet.LoadServlet;
import util.DataUtil;
import util.SecurityUtil;

public class CampaignDao extends DbConnect{

	public CampaignDao(){
	}
	
	// column定義
	public static final String	TABLE_NAME = "campaign";
	public static final String	ID = "id";
	public static final String	NAME = "name";
	public static final String	START_DATE = "start_date";
	public static final String	END_DATE = "end_date";
	public static final String	ADGROUP_LIST = "adgroup_list";
	public static final String	PASSWORD = "password";
	
	
	/*
	 * キャンペーンデータ件数取得処理
	 */
	public int get_campaigns_count(Campaign campaign){
		int cnt = 0;
		
		StringBuffer buf = new StringBuffer();
		buf.append(SELECT);
		buf.append(" count(*) ");
		buf.append(FROM);
		buf.append(CampaignDao.TABLE_NAME);

		// camapaign nameがあるなら条件追加
		if(!campaign.getName().isEmpty()){
			buf.append(WHERE);
			buf.append(CampaignDao.NAME);
			buf.append(LIKE);
			buf.append(quoteStringWithLike(campaign.getName()));
		}

		// start_date
		if(!campaign.getStart_date().isEmpty()){
			buf.append(whereOrAnd(buf.toString()));
			buf.append(LEFTPARENTHESIS);
			buf.append(CampaignDao.START_DATE);
			buf.append(GREATER_EQUAL);
			buf.append(quoteString(campaign.getStart_date()));
			buf.append(OR);
			buf.append(CampaignDao.START_DATE);
			buf.append(ISNULL);
			buf.append(RIGHTPARENTHESIS);

		}
		// end_date
		if(!campaign.getEnd_date().isEmpty()){
			buf.append(whereOrAnd(buf.toString()));
			buf.append(LEFTPARENTHESIS);
			buf.append(CampaignDao.END_DATE);
			buf.append(LESS_EQUAL);
			buf.append(quoteString(campaign.getEnd_date()));
			buf.append(OR);
			buf.append(CampaignDao.END_DATE);
			buf.append(ISNULL);
			buf.append(RIGHTPARENTHESIS);

		}

		System.out.println(buf.toString());
		try {
			// connect
			connect();
			
			rs = stmt.executeQuery(buf.toString());
			rs.next();
			cnt = rs.getInt(1);
            
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
		
		return cnt;
	}
	
	/*
	 * キャンペーンデータ取得処理
	 */
	public List<Campaign> get_campaigns(Campaign campaign, int max_count){
		List<Campaign> campaigns = new ArrayList<Campaign>();
		
		
		StringBuffer buf = new StringBuffer();
		buf.append(SELECT);
		buf.append(CampaignDao.ID);
		buf.append(",");
		buf.append(CampaignDao.NAME);
		buf.append(",");
		buf.append(CampaignDao.START_DATE);
		buf.append(",");
		buf.append(CampaignDao.END_DATE);
		buf.append(",");
		buf.append(CampaignDao.PASSWORD);
		buf.append(FROM);
		buf.append(CampaignDao.TABLE_NAME);

		// camapaign nameがあるなら条件追加
		if(!campaign.getName().isEmpty()){
			buf.append(WHERE);
			buf.append(CampaignDao.NAME);
			buf.append(LIKE);
			buf.append(quoteStringWithLike(campaign.getName()));
		}

		// start_date
		if(!campaign.getStart_date().isEmpty()){
			buf.append(whereOrAnd(buf.toString()));
			buf.append(LEFTPARENTHESIS);
			buf.append(CampaignDao.START_DATE);
			buf.append(GREATER_EQUAL);
			buf.append(quoteString(campaign.getStart_date()));
			buf.append(OR);
			buf.append(CampaignDao.START_DATE);
			buf.append(ISNULL);
			buf.append(RIGHTPARENTHESIS);

		}
		// end_date
		if(!campaign.getEnd_date().isEmpty()){
			buf.append(whereOrAnd(buf.toString()));
			buf.append(LEFTPARENTHESIS);
			buf.append(CampaignDao.END_DATE);
			buf.append(LESS_EQUAL);
			buf.append(quoteString(campaign.getEnd_date()));
			buf.append(OR);
			buf.append(CampaignDao.END_DATE);
			buf.append(ISNULL);
			buf.append(RIGHTPARENTHESIS);

		}
		if(max_count > 0){
			buf.append(LIMIT);
			buf.append(max_count);
		}
		
		//int count = 1;
		System.out.println(buf.toString());
		try {
			// connect
			connect();
			
			rs = stmt.executeQuery(buf.toString());
			while(rs.next()){
	           	 //System.out.println(rs.getString("yyyymmdd"));
	           	
	           	//campaigns.add(new Campaign(rs.getInt(CampaignDao.ID), rs.getString(CampaignDao.NAME)));
				Campaign add_campaign = new Campaign();
				add_campaign.setId(rs.getInt(CampaignDao.ID));
				add_campaign.setName(rs.getString(CampaignDao.NAME));
				if(rs.getString(CampaignDao.START_DATE) != null){
					add_campaign.setStart_date(rs.getString(CampaignDao.START_DATE).substring(0, 10));
				}else{
					add_campaign.setStart_date(rs.getString(CampaignDao.START_DATE));
				}
				if(rs.getString(CampaignDao.END_DATE) != null){
					add_campaign.setEnd_date(rs.getString(CampaignDao.END_DATE).substring(0, 10));
				}else{
					add_campaign.setStart_date(rs.getString(CampaignDao.END_DATE));
				}
				add_campaign.setPassword(rs.getString(CampaignDao.PASSWORD));
				
				campaigns.add(add_campaign);
	           	
	           	//campaigns.add(new Campaign(count, rs.getString("name"))); // IDではなく、行番号にするなら→更新・削除のとき特定できないので、だめ
	           	//count++;
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

		return campaigns;
	}
	
	/*
	 * キャンペーン詳細取得処理
	 * 
	 */
	public Campaign getCampaignDetail(int id){
		Campaign campaign = new Campaign();
		
		StringBuffer buf = new StringBuffer();
		buf.append(SELECT);
		buf.append(CampaignDao.ID);
		buf.append(",");
		buf.append(CampaignDao.NAME);
		buf.append(",");
		buf.append(CampaignDao.START_DATE);
		buf.append(",");
		buf.append(CampaignDao.END_DATE);
		buf.append(",");
		buf.append(CampaignDao.ADGROUP_LIST);
		buf.append(",");
		buf.append(CampaignDao.PASSWORD);
		buf.append(FROM);
		buf.append(CampaignDao.TABLE_NAME);
		buf.append(CampaignDao.WHERE);
		buf.append(CampaignDao.ID);
		buf.append(EQUAL);
		buf.append(id);
		
		
		try {
			// connect
			connect();
			
			rs = stmt.executeQuery(buf.toString());
			
			while(rs.next()){
				campaign.setId(rs.getInt(CampaignDao.ID));
				campaign.setName(rs.getString(CampaignDao.NAME));
				if(rs.getString(CampaignDao.START_DATE) != null){
					campaign.setStart_date(rs.getString(CampaignDao.START_DATE).substring(0, 10));
				}else{
					campaign.setStart_date(rs.getString(CampaignDao.START_DATE));
				}
				if(rs.getString(CampaignDao.END_DATE) != null){
					campaign.setEnd_date(rs.getString(CampaignDao.END_DATE).substring(0, 10));
				}else{
					campaign.setStart_date(rs.getString(CampaignDao.END_DATE));
				}
				campaign.setAdgroup_list(rs.getString(CampaignDao.ADGROUP_LIST));
				campaign.setPassword(rs.getString(CampaignDao.PASSWORD));

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
		
		return campaign;
	}
	
	
	/*
	 * キャンペーン削除
	 * 
	 */
	public void deleteCampaign(int id){
		StringBuffer buf = new StringBuffer();
		buf.append(DELETE);
		buf.append(FROM);
		buf.append(CampaignDao.TABLE_NAME);
		buf.append(WHERE);
		buf.append(CampaignDao.ID);
		buf.append(EQUAL);
		buf.append(id);
		
		try {
			// connect
			connect();
			// delete実行
			stmt.executeUpdate(buf.toString());

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
		
	}
	
	/*
	 * キャンペーン追加処理
	 * 
	 */
	public void insertCampaign(Campaign campaign){
		StringBuffer buf = new StringBuffer();
		buf.append(INSERT);
		buf.append(INTO);
		buf.append(CampaignDao.TABLE_NAME);
		buf.append(LEFTPARENTHESIS);
		buf.append(CampaignDao.NAME);
		buf.append(",");
		buf.append(CampaignDao.START_DATE);
		buf.append(",");
		buf.append(CampaignDao.END_DATE);
		buf.append(",");
		buf.append(CampaignDao.ADGROUP_LIST);
		buf.append(",");
		buf.append(CampaignDao.PASSWORD);
		buf.append(RIGHTPARENTHESIS);
		
		buf.append(VALUES);
		
		buf.append(LEFTPARENTHESIS);
		buf.append(quoteString(campaign.getName()));
		buf.append(",");
		if(campaign.getStart_date() != null){
			buf.append(quoteString(campaign.getStart_date()));
		}else{
			buf.append(NULL);
		}
		buf.append(",");
		if(campaign.getEnd_date() != null){
			buf.append(quoteString(campaign.getEnd_date()));
		}else{
			buf.append(NULL);
		}
		buf.append(",");
		if(campaign.getAdgroup_list() != null){
			buf.append(quoteString(campaign.getAdgroup_list()));
		}else{
			buf.append(NULL);
		}
		buf.append(",");
		buf.append(quoteString(RandomStringUtils.randomAlphabetic(10)));// パスワードにはランダムなアルファベットを設定
		buf.append(RIGHTPARENTHESIS);
		System.out.println(buf.toString());
		try {
			// connect
			connect();
			// delete実行
			stmt.executeUpdate(buf.toString());

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
	}
	
	/*
	 * キャンペーン更新処理
	 * 
	 */
	public void updateCampaign(Campaign campaign){
		StringBuffer buf = new StringBuffer();
		buf.append(UPDATE);
		buf.append(CampaignDao.TABLE_NAME);
		buf.append(CampaignDao.SET);
		// name
		buf.append(CampaignDao.NAME);
		buf.append(EQUAL);
		buf.append(quoteString(campaign.getName()));
		buf.append(",");
		// start_date
		buf.append(CampaignDao.START_DATE);
		buf.append(EQUAL);
		if(campaign.getStart_date() != null){
			buf.append(quoteString(campaign.getStart_date()));
		}else{
			buf.append(NULL);
		}
		buf.append(",");
		// end_date
		buf.append(CampaignDao.END_DATE);
		buf.append(EQUAL);
		if(campaign.getEnd_date() != null){
			buf.append(quoteString(campaign.getEnd_date()));
		}else{
			buf.append(NULL);
		}
		buf.append(",");
		// adgoup_list
		buf.append(CampaignDao.ADGROUP_LIST);
		buf.append(EQUAL);
		buf.append(quoteString(campaign.getAdgroup_list()));
		buf.append(WHERE);
		buf.append(CampaignDao.ID);
		buf.append(EQUAL);
		buf.append(campaign.getId());
		
		
		try {
			// connect
			connect();
			// delete実行
			stmt.executeUpdate(buf.toString());

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
	}
	
	/*
	 * IDとパスワードによるキャンペーン取得処理
	 */
	public int get_camapaign_count(String campaign_id, String password){
		int cnt = 0;
		
		StringBuffer buf = new StringBuffer();
		buf.append(SELECT);
		buf.append(" count(*) ");
		buf.append(FROM);
		buf.append(CampaignDao.TABLE_NAME);
		buf.append(WHERE);
		buf.append(CampaignDao.ID);
		buf.append(EQUAL);
		buf.append(quoteString(campaign_id));
		buf.append(AND);
		buf.append(CampaignDao.PASSWORD);
		buf.append(EQUAL);
		buf.append(quoteString(password));
		
		//System.out.println(buf.toString());
		try {
			// connect
			connect();
			
			rs = stmt.executeQuery(buf.toString());
			rs.next();
			cnt = rs.getInt(1);
			
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

		
		return cnt;
	}
}



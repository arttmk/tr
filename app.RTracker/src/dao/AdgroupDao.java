package dao;

import item.Adgroup;
import item.Campaign;

import java.sql.Date;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import servlet.LoadServlet;
import util.DataUtil;
import util.FrameConst;
import util.SecurityUtil;

public class AdgroupDao extends DbConnect{

	public AdgroupDao(){
	}
	
	// column定義
	public static final String	TABLE_NAME = "adgroup";
	public static final String	ID = "id";
	public static final String	NAME = "name";
	public static final String	MEDIA = "media";
	public static final String	DETAIL = "detail";
	public static final String	TARTGET_ID = "target_id";

	// 定数定義
	public static final String	online_val = "1";
	public static final String	tv_val = "2";
	public static final String	function = "Function";
	
	/**
	 * 表示予定adgroup件数取得処理
	 * 
	 */
	public int get_all_adgroups_count(String keyword, boolean isOnline, boolean isTV){
		int cnt = 0;
		
		StringBuffer buf = new StringBuffer();
		buf.append(SELECT);
		buf.append(" count(*) ");
		buf.append(FROM);
		buf.append(AdgroupDao.TABLE_NAME);
		if(!keyword.isEmpty()){
			buf.append(whereOrAnd(buf.toString()));
			buf.append(AdgroupDao.NAME);
			buf.append(LIKE);
			buf.append(quoteStringWithLike(keyword));
		}
		if(!isOnline){
			buf.append(whereOrAnd(buf.toString()));
			buf.append(AdgroupDao.MEDIA);
			buf.append(NOT_EQUAL);
			buf.append(AdgroupDao.online_val);
		}
		if(!isTV){
			buf.append(whereOrAnd(buf.toString()));
			buf.append(AdgroupDao.MEDIA);
			buf.append(NOT_EQUAL);
			buf.append(AdgroupDao.tv_val);
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
	 * 選択済みadgroup取得処理
	 * 
	 * @param
	 * keyword 検索キーワード
	 * isOnline 検索対象にonlineを含めるか
	 * isTV 検索対象にtvを含めるか
	 * max_count 検索するマックスカウント
	 * 
	 */
	public List<Adgroup> get_all_adgroups(String keyword, boolean isOnline, boolean isTV, int max_count){
		List<Adgroup> adgroups = new ArrayList<Adgroup>();
		
		StringBuffer buf = new StringBuffer();
		buf.append(SELECT);
		buf.append(AdgroupDao.ID);
		buf.append(",");
		buf.append(AdgroupDao.NAME);
		buf.append(",");
		buf.append(AdgroupDao.MEDIA);
		buf.append(",");
		buf.append(AdgroupDao.DETAIL);
		buf.append(FROM);
		buf.append(AdgroupDao.TABLE_NAME);
		if(!keyword.isEmpty()){
			buf.append(whereOrAnd(buf.toString()));
			buf.append(AdgroupDao.NAME);
			buf.append(LIKE);
			buf.append(quoteStringWithLike(keyword));
		}
		if(!isOnline){
			buf.append(whereOrAnd(buf.toString()));
			buf.append(AdgroupDao.MEDIA);
			buf.append(NOT_EQUAL);
			buf.append(AdgroupDao.online_val);
		}
		if(!isTV){
			buf.append(whereOrAnd(buf.toString()));
			buf.append(AdgroupDao.MEDIA);
			buf.append(NOT_EQUAL);
			buf.append(AdgroupDao.tv_val);
		}
		if(max_count > 0){
			buf.append(LIMIT);
			buf.append(max_count);
		}
		
		System.out.println(buf.toString());
		
		try {
			// connect
			connect();
			
			rs = stmt.executeQuery(buf.toString());
			while(rs.next()){
				Adgroup add_adgroup = new Adgroup();
				add_adgroup.setId(rs.getInt(AdgroupDao.ID));
				add_adgroup.setName(rs.getString(AdgroupDao.NAME));
				if(rs.getInt(AdgroupDao.MEDIA) == 1){
					add_adgroup.setMedia(FrameConst.online);
				}else{
					add_adgroup.setMedia(FrameConst.tv);
				}
				if(rs.getString(AdgroupDao.DETAIL) != null){
					add_adgroup.setDetail(rs.getString(AdgroupDao.DETAIL));
				}else{
					add_adgroup.setDetail("&nbsp;");
				}
				
				
				adgroups.add(add_adgroup);
			}

		} catch (Exception e) {
			SecurityUtil.sendErrorMail(e.toString());
		}finally{
			try {
				// close
				closeConnection();
			} catch (Exception e) {
				SecurityUtil.sendErrorMail(e.toString());
				LoadServlet.logger.severe(e.toString()); 
			}
		}

		
		return adgroups;
	}
	
	/*
	 * 選択済みadgroup取得処理
	 * 
	 */
	public List<Adgroup> get_selected_adgroups(String adgroup_list, boolean selected){
		List<Adgroup> adgroups = new ArrayList<Adgroup>();
		
		// 選択済みの場合かつ、選択された対象がない場合ここでreturn
		if(adgroup_list == null && selected) return adgroups;
		
		StringBuffer buf = new StringBuffer();
		buf.append(SELECT);
		buf.append(AdgroupDao.ID);
		buf.append(",");
		buf.append(AdgroupDao.NAME);
		buf.append(FROM);
		buf.append(AdgroupDao.TABLE_NAME);
		if(adgroup_list != null){
			// 選択された対象がある場合のみ、条件追加
			buf.append(WHERE);
			buf.append(AdgroupDao.ID);
			if(selected){
				buf.append(IN);
			}else{
				buf.append(NOTIN);
			}
			buf.append(LEFTPARENTHESIS);
			buf.append(adgroup_list);
			buf.append(RIGHTPARENTHESIS);
		}

		
		System.out.println(buf.toString());
		try {
			// connect
			connect();
			
			rs = stmt.executeQuery(buf.toString());
			while(rs.next()){
				Adgroup add_adgroup = new Adgroup();
				add_adgroup.setId(rs.getInt(AdgroupDao.ID));
				add_adgroup.setName(rs.getString(AdgroupDao.NAME));
				//add_adgroup.setAdd(rs.getInt(AdgroupDao.ID));
				
				adgroups.add(add_adgroup);
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

		
		return adgroups;
	}

	/*
	 * adgroup検索処理
	 * 
	 */
	public List<Adgroup> search_adgroups(String adgroup_list){
		List<Adgroup> adgroups = new ArrayList<Adgroup>();
		
		if(adgroup_list.isEmpty()){
			adgroup_list = "0";
		}
		StringBuffer buf = new StringBuffer();
		buf.append(SELECT);
		buf.append(AdgroupDao.ID);
		buf.append(",");
		buf.append(AdgroupDao.NAME);
		buf.append(",");
		buf.append(AdgroupDao.MEDIA);
		buf.append(",");
		buf.append(AdgroupDao.DETAIL);
		buf.append(",");
		buf.append(AdgroupDao.TARTGET_ID);
		buf.append(FROM);
		buf.append(AdgroupDao.TABLE_NAME);
		buf.append(WHERE);
		buf.append(AdgroupDao.ID);
		buf.append(IN);
		buf.append(LEFTPARENTHESIS);
		buf.append(adgroup_list);
		buf.append(RIGHTPARENTHESIS);

		
		System.out.println(buf.toString());
		try {
			// connect
			connect();
			
			rs = stmt.executeQuery(buf.toString());
			while(rs.next()){
				Adgroup add_adgroup = new Adgroup();
				add_adgroup.setId(rs.getInt(AdgroupDao.ID));
				add_adgroup.setName(rs.getString(AdgroupDao.NAME));
				if(rs.getInt(AdgroupDao.MEDIA) == 1){
					//add_adgroup.setMedia(FrameConst.online + FrameConst.separator_zenkaku_space);
					add_adgroup.setMedia(FrameConst.online);
				}else{
					//add_adgroup.setMedia(FrameConst.tv + FrameConst.separator_zenkaku_space);
					add_adgroup.setMedia(FrameConst.tv);
				}
				if(rs.getString(AdgroupDao.DETAIL) != null){
					add_adgroup.setDetail(rs.getString(AdgroupDao.DETAIL));
				}else{
					add_adgroup.setDetail("&nbsp;");
				}
				add_adgroup.setTarget_id(rs.getString(AdgroupDao.TARTGET_ID));
				
				//add_adgroup.setAdd(rs.getInt(AdgroupDao.ID));
				
				adgroups.add(add_adgroup);
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

		
		return adgroups;
	}

	/*
	 * adgroup追加処理
	 * 
	 */
	public void insertAdgroup(Adgroup adgroup){
		StringBuffer buf = new StringBuffer();
		buf.append(INSERT);
		buf.append(INTO);
		buf.append(AdgroupDao.TABLE_NAME);
		buf.append(LEFTPARENTHESIS);
		buf.append(AdgroupDao.NAME);
		buf.append(",");
		buf.append(AdgroupDao.MEDIA);
		buf.append(",");
		buf.append(AdgroupDao.DETAIL);
		buf.append(",");
		buf.append(AdgroupDao.TARTGET_ID);
		buf.append(RIGHTPARENTHESIS);
		
		buf.append(VALUES);
		
		buf.append(LEFTPARENTHESIS);
		buf.append(quoteString(adgroup.getName()));
		buf.append(",");
		buf.append(quoteString(adgroup.getMedia()));
		buf.append(",");
		buf.append(quoteString(adgroup.getDetail()));
		buf.append(",");		
		buf.append(quoteString(adgroup.getTarget_id()));
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
	 * adgroup更新処理
	 * 
	 */
	public void updateAdgroup(Adgroup adgroup){
		StringBuffer buf = new StringBuffer();
		buf.append(UPDATE);
		buf.append(AdgroupDao.TABLE_NAME);
		buf.append(AdgroupDao.SET);
		// name
		buf.append(AdgroupDao.NAME);
		buf.append(EQUAL);
		buf.append(quoteString(adgroup.getName()));
		buf.append(",");
		// media
		buf.append(AdgroupDao.MEDIA);
		buf.append(EQUAL);
		buf.append(quoteString(adgroup.getMedia()));
		buf.append(",");
		// detail
		buf.append(AdgroupDao.DETAIL);
		buf.append(EQUAL);
		buf.append(quoteString(adgroup.getDetail()));
		buf.append(",");
		// target_id
		buf.append(AdgroupDao.TARTGET_ID);
		buf.append(EQUAL);
		buf.append(quoteString(adgroup.getTarget_id()));

		buf.append(WHERE);
		buf.append(AdgroupDao.ID);
		buf.append(EQUAL);
		buf.append(adgroup.getId());
		
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
	
	/**
	 * adgroupに存在するかどうか確認処理
	 * 
	 */
	public int isAlreadyExistAdgroup(String target_id, String media){
		int id = 0;
		
		StringBuffer buf = new StringBuffer();
		buf.append(SELECT);
		buf.append(AdgroupDao.ID);
		buf.append(FROM);
		buf.append(AdgroupDao.TABLE_NAME);
		
		buf.append(whereOrAnd(buf.toString()));
		buf.append(AdgroupDao.TARTGET_ID);
		buf.append(EQUAL);
		buf.append(quoteString(target_id));
		
		buf.append(whereOrAnd(buf.toString()));
		buf.append(AdgroupDao.MEDIA);
		buf.append(EQUAL);
		buf.append(quoteString(media));
				
		System.out.println(buf.toString());
		
		try {
			// connect
			connect();
			
//			rs = stmt.executeQuery(buf.toString());
//			rs.next();
//			id = rs.getInt(AdgroupDao.ID);
			
			rs = stmt.executeQuery(buf.toString());
			while(rs.next()){
				
				id = rs.getInt(AdgroupDao.ID);
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
			
		return id;
		
		
	}
}

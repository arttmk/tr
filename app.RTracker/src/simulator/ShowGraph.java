package simulator;

import item.Adgroup;
import item.Campaign;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import logic.CertificationLogic;

import org.apache.click.Context;
import org.apache.click.control.Button;
import org.apache.click.control.Checkbox;
import org.apache.click.control.Column;
import org.apache.click.control.Decorator;
import org.apache.click.control.FileField;
import org.apache.click.control.Form;
import org.apache.click.control.HiddenField;
import org.apache.click.control.Label;
import org.apache.click.control.Option;
import org.apache.click.control.Radio;
import org.apache.click.control.RadioGroup;
import org.apache.click.control.Select;
import org.apache.click.control.Submit;
import org.apache.click.control.Table;
import org.apache.click.control.TextField;
import org.apache.click.extras.control.FieldColumn;
import org.apache.click.util.Bindable;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.IOUtils;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.bigquery.BigqueryScopes;
import com.google.api.services.bigquery.model.TableCell;
import com.google.api.services.bigquery.model.TableRow;

import bigQuery.BigQueryApiAccess;


import dao.AdgroupDao;
import dao.CampaignDao;

import servlet.LoadServlet;
import util.DataUtil;
import util.FrameConst;
import util.MessageConst;
import util.SecurityUtil;
import util.UtilConfig;
import util.UtilConst;
import util.UtilMisc;


public class ShowGraph extends org.apache.click.Page {

	private static final long serialVersionUID = 3920849062506715071L;

	private static final String APP_LOG			= "SHOW GRAPH";
	private static final String APP_NAME		= "SHOW GRAPHページ";

    @Bindable protected Integer id;
    @Bindable protected String referrer;
    @Bindable protected String mode;
    
	private CampaignDao campaign_dao = new CampaignDao();
	private Campaign campaign = new Campaign();

	private AdgroupDao adgroup_dao = new AdgroupDao();
	private Adgroup adgroup = new Adgroup();
	

	  private Form form;
	  
	  /** ログイン情報ヘッダー **/
	  private Submit logout;;
	  private CertificationLogic certify = new CertificationLogic();
	  /**************************/
		
	  private TextField campaign_id;
	  private TextField name;
	  private TextField password;
	  
	  private Select effective_reach = new Select("effective_reach");
	  private TextField start_date;
	  private TextField end_date;
	
	  private Label top01;
	  
	  private Submit download;
	  
	  private HiddenField hid_referrer = new HiddenField("hid_referrer", String.class); // referrerを格納
	  private HiddenField adgroup_list = new HiddenField("adgroup_list", String.class); // 選択済みデータを格納(DBのカラム名に合わせた変数名)
	  private HiddenField hid_chk_adg = new HiddenField("hid_chk_adg", String.class); // チェックされたadgroupを格納
	  private HiddenField hid_click_disp = new HiddenField("hid_click_disp", String.class); // displayボタンを押されたadgroupIDを格納
	  private HiddenField hid_graph = new HiddenField("hid_graph", String.class); // 表示すべきグラフデータを格納
	  private HiddenField hid_m_cgraph = new HiddenField("hid_m_c_graph", String.class); // mergeやcompare実行時に表示すべきグラフデータを格納
	  private HiddenField hid_dl_file = new HiddenField("hid_dl_file", String.class); // DLするファイル情報を格納
	  private HiddenField hid_set_adgroups = new HiddenField("hid_set_adgroups", String.class); // merge、compare用に選択された広告グループ名情報を格納
	  private HiddenField hid_token = new HiddenField("hid_token", String.class); // tokenを格納
	  private HiddenField hid_cat_days = new HiddenField("hid_cat_days", String.class); // グラフ用カテゴリー（日にち）を格納
	  private HiddenField hide_disp_mode = new HiddenField("hide_disp_mode", String.class); // 一般ユーザー用modeかどうかを格納
	  
	  private Label graph_cnt;
	  private Label graph_msg;
	  
	  private Submit display;
	  private Submit merge;
	  private Submit compare;
	  private Submit return_campaign_search;

	  private Button merge_button;
	  private Button compare_button;
	  private Button share_button;

	  private Label disp_ad_name;
	  private Label disp_gender;
	  private Label disp_age;
	  private Label disp_size;
	  private Label disp_placement;
	  private Label disp_max_cpm;
	  private Label disp_freq_cap;
	  private Label disp_duration;
	  private Label disp_reach_type;
	  private Label disp_date;
	  private Label disp_max_budget;
	  private Label disp_discount;
	  private Label disp_tv_duration;
	  private Label disp_tv_campaign;
	  private Label disp_effective_reach;
	  private Label disp_adgroups_name;
	  
	  private Label cam_info;
	  
	  private Label errMsg;
	  
	  private RadioGroup count_unit = new RadioGroup("count_unit");

	  private Table selected_adgroup_table;
	  private FieldColumn column;

	public ShowGraph(){
		
		
		form = new Form("form");
		
		// キャンペーンID
		campaign_id = new TextField(CampaignDao.ID,""); // form.copyFromへ対応させるため、画面の項目名とCampaignの項目名を合わせる
		campaign_id.setReadonly(true);
		form.add(campaign_id);

		// キャンペーン名
		name = new TextField(CampaignDao.NAME,"");
		name.setReadonly(true);
		form.add(name);
		
		// パスワード
		password = new TextField(CampaignDao.PASSWORD,"");
		form.add(password);
		
		// パラメータパネル部分  //

	    // counting unit
		count_unit.add(new Radio("Per Day", "Per Day ")); 
		count_unit.add(new Radio("Per Week", "Per Week "));
		count_unit.setVerticalLayout(false);
		count_unit.setValue("Per Day"); // デフォルトはTrueView
	    form.add(count_unit);  
	    
	    //duration
		start_date = new TextField(CampaignDao.START_DATE, "Start Date");
		form.add(start_date);
		
		end_date = new TextField(CampaignDao.END_DATE, "End Date");
		form.add(end_date);
	    
	    // effective_reach
	    effective_reach.add(new Option("1 Frequency", "1 Frequency"));
	    effective_reach.add(new Option("2 Frequency", "2 Frequency"));
	    effective_reach.add(new Option("3 Frequency", "3 Frequency"));
	    effective_reach.add(new Option("4 Frequency", "4 Frequency"));
	    effective_reach.add(new Option("5 Frequency", "5 Frequency"));
	    effective_reach.add(new Option("6 Frequency", "6 Frequency"));
	    effective_reach.add(new Option("7 Frequency", "7 Frequency"));
	    effective_reach.add(new Option("8 Frequency", "8 Frequency"));
	    effective_reach.add(new Option("9 Frequency", "9 Frequency"));
	    effective_reach.add(new Option("10 Frequency", "10 Frequency"));
	    effective_reach.setLabel("");
	    form.add(effective_reach);
	    
	    
	    // グラフパネル部分  //	    
	    
		// downloadボタン
		download = new Submit("download","Download",this,"do_download");
		download.addStyleClass("download_buttoun");
	    form.add(download);
	    
	    // グラフ表示位置
	    graph_cnt = new Label("graph_cnt", "");
	    form.add(graph_cnt);
	    
	    // 現在表示しているグラフの詳細
		disp_ad_name = new Label("disp_ad_name", "");
	    form.add(disp_ad_name);
	    disp_gender = new Label("disp_gender", "");
	    form.add(disp_gender);
	    disp_age = new Label("disp_age", "");
	    form.add(disp_age);
	    disp_size = new Label("disp_size", "");
	    form.add(disp_size);
	    disp_placement = new Label("disp_placement", "");
	    form.add(disp_placement);
	    disp_max_cpm = new Label("disp_max_cpm", "");
	    form.add(disp_max_cpm);
	    disp_freq_cap = new Label("disp_freq_cap", "");
	    form.add(disp_freq_cap);
	    disp_duration = new Label("disp_duration", "");
	    form.add(disp_duration);
	    disp_reach_type = new Label("disp_reach_type", "");
	    form.add(disp_reach_type);
	    disp_date = new Label("disp_date", "");
	    form.add(disp_date);
	    disp_max_budget = new Label("disp_max_budget", "");
	    form.add(disp_max_budget);
	    disp_discount = new Label("disp_discount", "");
	    form.add(disp_discount);
	    disp_tv_duration = new Label("disp_tv_duration", "");
	    form.add(disp_tv_duration);
	    disp_tv_campaign = new Label("disp_tv_campaign", "");
	    form.add(disp_tv_campaign);
	    disp_effective_reach = new Label("disp_effective_reach", "");
	    form.add(disp_effective_reach);
	    
	    // mergeボタン
	    merge = new Submit("merge","Merge",this,"do_merge");
	    merge.addStyleClass("button_merge");
	    form.add(merge);
	    
	    // mergeボタン（button)→javascripでの制御が必要なため、js呼び出し用に追加
	    merge_button = new Button("merge_button","Merge");
	    merge_button.addStyleClass("merge_button");
	    merge_button.setOnClick("isClickMerge();");
	    form.add(merge_button);
	    
	    // compareボタン
	    compare = new Submit("compare","Compare",this,"do_compare");
	    compare.addStyleClass("button_compare_reach");
	    form.add(compare);
	    
	    // compareボタン（button)→javascripでの制御が必要なため、js呼び出し用に追加
	    compare_button = new Button("compare_button","Compare");
	    compare_button.addStyleClass("compare_button");
	    compare_button.setOnClick("isClickCompare();");
	    form.add(compare_button);
	    
	    // shareボタン
	    share_button = new Button("share_button","Share");
	    share_button.addStyleClass("btn-share");
	    share_button.setOnClick("do_share();");
	    form.add(share_button);
	    
	    // returnボタン
	    return_campaign_search = new Submit("return_campaign_search","",this,"return_campaign_search");
	    return_campaign_search.addStyleClass("button_compare_reach_curve");
	    form.add(return_campaign_search);
	    
	    // displayボタン
	    display = new Submit("display","Display",this,"do_display");
	    form.add(display);
	    
	    form.add(hid_referrer);
	    form.add(adgroup_list);
	    form.add(hid_chk_adg);
	    form.add(hid_click_disp); 
	    form.add(hid_graph);
	    form.add(hid_m_cgraph);
	    form.add(hid_dl_file);
	    form.add(hid_set_adgroups);
	    form.add(hid_token);
	    form.add(hid_cat_days);
	    form.add(hide_disp_mode);
	    
	    // selected adgroupテーブル //
		//selected_adgroup_table = new Table();
	    selected_adgroup_table = new Table() { // tableをoverrideしてIDを設定
            private static final long serialVersionUID = 1L;

            @Override
            protected void addRowAttributes(Map attributes, Object row, int rowIndex) {
            	Adgroup adg = (Adgroup) row;
                attributes.put("id", "row_" + adg.getId());
                //attributes.put("class", "foo bar");
            }
        };
		selected_adgroup_table.addStyleClass("tbl-1");
		// checkbox
		column = new FieldColumn("Check");
		column.setDecorator(new Decorator() {  
		    public String render(Object row, Context context) {  
		    	Adgroup adg = (Adgroup) row;
		        int adg_id = adg.getId();
		        String name = adg.getName();
		        String target_id = adg.getTarget_id();

		        String val = "<input type='checkbox' id='adgChk" + adg_id + "' onClick=\"adg_checked('" + adg_id + "','" + name + "','" + target_id + "');\" >";		        
		        
		        return val;
		    }  
		});  
		selected_adgroup_table.addColumn(column);
		
		selected_adgroup_table.addColumn(new Column(AdgroupDao.ID,"ID"));
		// media
		column = new FieldColumn(AdgroupDao.MEDIA);
		column.setDecorator(new Decorator() {  
		    public String render(Object row, Context context) {  
		    	Adgroup adg = (Adgroup) row;
		        String media = adg.getMedia();

		        String val = "<span class='label-1'>"+ media +"</span>";
		        
		        return val;
		    }  
		});  
		selected_adgroup_table.addColumn(column);

		// name(+ detail)
		column = new FieldColumn(AdgroupDao.NAME, new TextField());
		//column.setAttribute("readonly", "true");
		column.setDecorator(new Decorator() {  
		    public String render(Object row, Context context) {  
		    	Adgroup adg = (Adgroup) row;
		    	String name = adg.getName();
		        int adg_id = adg.getId();
		        String detail = adg.getDetail();

		        String val = name;
		        val += "<div id='jquery-ui-effect-selected" + adg_id +"' class='ui-widget-content ui-corner-all'  style='display: none;'>";
		        val += "<p>" + detail + "</p>";
		        val += "</div>";
		        
		        return val;
		    }  
		});  
		selected_adgroup_table.addColumn(column);
		
		// detail button
		column = new FieldColumn(AdgroupDao.function, new Button(AdgroupDao.function,AdgroupDao.function));
		// decoratorで、行の情報に合わせて動的に項目を追加
		column.setDecorator(new Decorator() {  
		    public String render(Object row, Context context) {  
		    	Adgroup adg = (Adgroup) row;  
		        int adg_id = adg.getId();
		        String name = adg.getName();
		        String target_id = adg.getTarget_id();

		        String val = "<input type='button' value='Detail' class='btn-detail' onClick='showDetailSelected(" + adg_id + ");' >";
		        val += "&nbsp;&nbsp;";
		        val += "<input type='button' value='Display' class='btn-disp' onClick=\"do_display('" + adg_id + "','" + name  + "','" + target_id + "');\" >";
		        

		        return val;
		    }  
		});  
		selected_adgroup_table.addColumn(column);
		
//		// display → detailへ統合
//		column = new FieldColumn("display", new Button("display","Display"));
//		// decoratorで、行の情報に合わせて動的に項目を追加
//		column.setDecorator(new Decorator() {  
//		    public String render(Object row, Context context) {  
//		    	Adgroup adg = (Adgroup) row;  
//		        int adg_id = adg.getId();
//
//		        String val = "<input type='button' value='Display' class='btn-disp' onClick='do_display(" + adg_id + ");' ";
//
//		        return val;
//		    }  
//		});  
//		selected_adgroup_table.addColumn(column);
		
		
		selected_adgroup_table.setName("adgroup_table");
		this.addControl(selected_adgroup_table);
		
		// logout
		logout = new Submit("logout","Logout",this,"do_logout");
		form.add(logout);
		
	    this.addControl(form);
	}

	
    @Override
    public void onGet() {
    	// 画面遷移時の処理
    	if (id != null) {
    		campaign = campaign_dao.getCampaignDetail(id);
    		// copyでcampaignの要素と同じnameをもつオブジェクトへ値を自動セットする
    		form.copyFrom(campaign);
    		
//    		// adgroupリスト表示設定
//    		setAdgroups(campaign.getAdgroup_list());
    		
    		
    	}
    	
    	if (referrer != null) {
    		hid_referrer.setValue(referrer);
    	}
    	
		// sessionからhiddenへtokenを格納
		if(this.getContext().getSessionAttribute(UtilConst.Session_token) != null && !this.getContext().getSessionAttribute(UtilConst.Session_token).toString().isEmpty()){
			this.hid_token.setValue(this.getContext().getSessionAttribute(UtilConst.Session_token).toString());
		}
    	
		// 一般ユーザ用モードかどうか
    	if (mode != null) {
    		hide_disp_mode.setValue(mode);
    	}
    	
    	super.onGet();
    }
    
//    @Override
//    public boolean onSecurityCheck() { 
//    	boolean isOK = true;
//    	
//    	
//    	super.onSecurityCheck();
//    	
//    	return isOK;
//    }
    
	@Override
	public void onRender() { 
		String path = "";
		
		if(this.hide_disp_mode.getValue().isEmpty()){
			// 管理者の場合
			
			// ログアウト押下でない場合、ログインチェック
			if(!this.logout.isClicked()){
				path = certify.checkLoginInfo(form);
				
				if(!path.isEmpty()) setRedirect(path); // pathが空でない場合遷移（ログイン画面）
			}
			// tokenチェック
			path = certify.checkToken(this.hid_token.getValue());
			if(!path.isEmpty()) setRedirect(path); // pathが空でない場合遷移（ログイン画面）
			
			
		}else{
			// 一般ユーザの場合
			boolean authNG = false;
			
			// 戻るボタン、shareボタンを非表示
			this.return_campaign_search.setStyle("visibility", "hidden");
			this.share_button.setStyle("visibility", "hidden");
			
			// ユーザ認証
			if(this.getContext().getSessionAttribute(UtilConst.Session_user_auth) == null || this.getContext().getSessionAttribute(UtilConst.Session_user_auth).toString().isEmpty()){
				authNG = true;

			}else{
				// 表示するキャンペーンのパスワードと合致するか
				int camp_cnt = campaign_dao.get_camapaign_count(this.campaign_id.getValue(), this.getContext().getSessionAttribute(UtilConst.Session_user_auth).toString());
				
				if(camp_cnt == 0){
					authNG = true;
				}
			}
			
			if(authNG){
				// 未認証の場合、認証ページへ遷移
				path = getContext().getPagePath(UserAuth.class);
				path += "?id=" + this.campaign_id.getValue(); // campaignIDを渡す
				setRedirect(path);
			}
				
			// ログイン情報設定
			Label login_user_name;
			Label login_user_company;
			
			login_user_name = new Label("login_user_name", "Guest");
			form.add(login_user_name);
			login_user_company = new Label("login_user_company", "Guest");
			form.add(login_user_company);
			
			
		}

		
		// adgroupリスト表示設定
		String relatedAdg;
		if(campaign.getAdgroup_list() != null){
			relatedAdg = campaign.getAdgroup_list();
		}else{
			relatedAdg = this.adgroup_list.getValue();
		}
		
		setAdgroups(relatedAdg);
		
		
		// グラフ番号を設定（setGraphだとエラー処理の場合、通らないこともあるため、ここで設定）
		if(!this.hid_graph.getValue().isEmpty() || !this.hid_m_cgraph.getValue().isEmpty()){ // グラフがある場合のみ表示
			this.graph_cnt.setLabel("1 / 2");
		}
		

		// urlとpassword(leanModalを使う場合)
	    cam_info = new Label("cam_info", "【URL】" + FrameConst.show_graph_url + this.campaign_id.getValue() + "&mode=" + FrameConst.disp_mode + "<BR/>【Password】" + this.password.getValue());
	    form.add(cam_info);
		
		super.onRender();
		
	}
    /*
     * Adgroupリスト表示処理
     * 
     */
	private void setAdgroups(String adg_list) {
		
		// online
		//List<Adgroup> adgroups = adgroup_dao.get_selected_adgroups(adg_list,true);
//		int i;
//		for(i = 0; adgroups.size() > i; i++){
//			if(i == 0){
//				// name
//				row1 = new Label("row1", adgroups.get(i).getName());
//			    form.add(row1);
//			    // detail
//			    row_detail1 = new Label("row_detail1", "test");
//			    form.add(row_detail1);
//			    // media
//			    row_media1 = new Label("row_media1", "<span>" + FrameConst.online + "</span>");
//			    form.add(row_media1);
//			}
//			
//		}

		List<Adgroup> adgroups = adgroup_dao.search_adgroups(adg_list);
		
		selected_adgroup_table.setRowList(adgroups);
		
		
		
	}

    /*
     * 広告グループのグラフ表示処理
     */
	 public boolean do_display(){
		 List<TableRow> rows = null;
		 //System.out.println("disp:"+this.hid_click_disp.getValue());
		 
		 if(invalidCheck()) return true;
		 
		 // グラフ値設定処理
		 setGraphs(this.hid_click_disp.getValue());

		 // big query
		 //String query  = "SELECT TOP(title, 10) as title, COUNT(*) as revision_count FROM [publicdata:samples.wikipedia] WHERE wp_namespace = 0;";
		 String query  = "SELECT cf_id, brand FROM [rtt_tracker.raw_tv_cf_mst] LIMIT 10;";
//		try {
//			 rows = BigQueryApiAccess.doQuery(query);
//		} catch (Exception e) {
//			SecurityUtil.sendErrorMail(e.toString());
//			LoadServlet.logger.severe(e.toString()); 
//		}
		
		
		// 検索結果があれば表示
		if (rows != null) {
			LoadServlet.logger.warning("rows:" + rows.size());
			for (TableRow row : rows) {
				for (TableCell field : row.getF()) {
					System.out.printf("%-50s", field.getV());
				}
				System.out.println();
			}
		} else {
			System.out.println("no results.");
		}
		 
		 return true;
	 }
	 
	
	/**
	  * compare処理
	  *
	*/
	public boolean do_compare(){
		
		if(invalidCheck()) return true;
		
		// 広告グループ選択チェック
		if(this.hid_chk_adg.getValue().split(",").length > 1){
			// 2つ以上選択されている場合
			
			setGraphs(this.hid_chk_adg.getValue());
			
			
		}else{
			errMsg = new Label("errMsg", FrameConst.compareSelect_errMsg);
			this.form.add(errMsg);
		}
		
		return true;
		
	}
	
	
	/**
	  * merge処理
	  *
	*/
	public boolean do_merge(){
		
		if(invalidCheck()) return true;
		
		// 広告グループ選択チェック
		if(this.hid_chk_adg.getValue().split(",").length > 1){
			// 2つ以上選択されている場合
			
			setGraphs(this.hid_chk_adg.getValue());
			
			
		}else{
			errMsg = new Label("errMsg", FrameConst.mergeSelect_errMsg);
			this.form.add(errMsg);
		}
		
		
		return true;
		
	}
	/*
	 * キャンペーン選択画面へ戻る
	 */
	public boolean return_campaign_search(){
        String referrer = hid_referrer.getValue();
        
        if (referrer != null && !referrer.isEmpty()) {
        	if(referrer.equals("/editCampaign.htm")){
        		// キャンペーン編集からの遷移の場合は、IDを付けて返す
                referrer += "?id=" + this.id;
        	}
            setRedirect(referrer);
        } else {
            setRedirect(CampaignSelect.class);
        }
    	
    	return true;
		
	}
	
	/**
	  * setGraphs
	  *  グラフ描画処理
	  *  
	  *  @param
	  *  String adg_info 対象のadgroupID|adg_name（merge,compareの場合は、複数IDをカンマ区切りで渡す）
	  *
	  */
	
	private void setGraphs(String adg_info){
		boolean exist = false;
		String graphs = "";
		String target_reach = "";
		String content_target = "";
		String content_not_target = ""; 

		
		// 日にちの設定
		//this.hid_cat_days.setValue("2013-07-01,2013-07-02,2013-07-03,2013-07-04,2013-07-05,2013-07-06,2013-07-07,2013-07-08,2013-07-09,2013-07-10,2013-07-11,2013-07-12,2013-07-13,2013-07-14,2013-07-15,2013-07-16,2013-07-17,2013-07-18,2013-07-19,2013-07-20,2013-07-21,2013-07-22,2013-07-23,2013-07-24,2013-07-25,2013-07-26,2013-07-27,2013-07-28,2013-07-29,2013-07-30,2013-07-31");
		//this.hid_cat_days.setValue("07-01,07-02,07-03,07-04,07-05,07-06,07-07,07-08,07-09,07-10,07-11,07-12,07-13,07-14,07-15,07-16,07-17,07-18,07-19,07-20,07-21,07-22,07-23,07-24,07-25,07-26,07-27,07-28,07-29,07-30,07-31");
		
		String category_days = "";
		int diff_day = 0;
		String unit = this.count_unit.getValue(); // Per Day or Per Week
		
		
		try {
			// start_dateとend_dateの差分を取得
			diff_day = UtilMisc.differenceDays(this.end_date.getValue(),this.start_date.getValue());
		} catch (Exception e) {
			SecurityUtil.sendErrorMail(e.toString());
			LoadServlet.logger.severe(e.toString()); 
		}
		
		if(unit.equals(FrameConst.per_day)){
			// per dayの場合
			
			category_days = this.start_date.getValue().substring(5);
			
			if(diff_day > 0){
				// start_dateとend_dateの差分が0以上の場合、startからendまでの日数をカテゴリーに設定
				Calendar cal = Calendar.getInstance();
				try {
					cal.setTime(DataUtil.convertString2Date(this.start_date.getValue()));
				} catch (Exception e) {
					SecurityUtil.sendErrorMail(e.toString());
					LoadServlet.logger.severe(e.toString()); 
				}
				
				for(int i = 0;i < diff_day; i++){
					cal.add(Calendar.DATE, 1);
					
					//int year = cal.get(Calendar.YEAR);
					int month = cal.get(Calendar.MONTH) + 1;
					String month_str = Integer.toString(month);
					if(month_str.length() < 2){
						month_str = "0" + month_str;
					}
					
					int day = cal.get(Calendar.DATE);
					String day_str = Integer.toString(day);
					if(day_str.length() < 2){
						day_str = "0" + day_str;
					}
					
					String date =  month_str + "-" + day_str;
					
					category_days += "," + date;
				}

			}
		}else{
			// per weekの場合
			category_days = this.start_date.getValue().substring(5) + "W";
			
			if(diff_day > 6){
				// start_dateとend_dateの差分が6（1週間）以上の場合、startからendまでの日数をカテゴリーに設定
				Calendar cal = Calendar.getInstance();
				try {
					cal.setTime(DataUtil.convertString2Date(this.start_date.getValue()));
				} catch (Exception e) {
					SecurityUtil.sendErrorMail(e.toString());
					LoadServlet.logger.severe(e.toString()); 
				}
				
				for(int i = 6; i < diff_day; i+=7){
					cal.add(Calendar.DATE, 7); // weekなので7日
					
					//int year = cal.get(Calendar.YEAR);
					int month = cal.get(Calendar.MONTH) + 1;
					String month_str = Integer.toString(month);
					if(month_str.length() < 2){
						month_str = "0" + month_str;
					}
					
					int day = cal.get(Calendar.DATE);
					String day_str = Integer.toString(day);
					if(day_str.length() < 2){
						day_str = "0" + day_str;
					}
					
					String date =  month_str + "-" + day_str;
					
					category_days += "," + date+ "W";
				}

			}
			
		}
		

		
		//System.out.println(category_days);
		
		// グラフに表示する日にちを設定
		this.hid_cat_days.setValue(category_days);
		String[] day_list = category_days.split(",");

		if(this.merge.isClicked() || this.compare.isClicked()){
			String[] adg_info_list = adg_info.split(",");
			String adg_ids = "";
			String adg_names = "";
			String adg_name = "";
			String disp_adg_info = "";
			
			for(int i = 0; i < adg_info_list.length; i++){
				// id
				if(adg_ids.isEmpty()){
					adg_ids = adg_info_list[i].split(FrameConst.separator_pipe_escape)[0];
				}else{
					adg_ids += "," + adg_info_list[i].split(FrameConst.separator_pipe_escape)[0];
				}
				
				// name
				// adgroup nameが空の場合もあるので
				if(adg_info_list[i].split(FrameConst.separator_pipe_escape).length > 1){
					adg_name = adg_info_list[i].split(FrameConst.separator_pipe_escape)[1];
				}else{
					adg_name = "";
				}
				
				if(adg_names.isEmpty()){
					adg_names = adg_name;
				}else{
					adg_names += "," + adg_name;
				}
				
				// disp_adg_info
				if(disp_adg_info.isEmpty()){
					disp_adg_info = "ID:" + adg_info_list[i].split(FrameConst.separator_pipe_escape)[0] + " " + adg_name;
				}else{
					disp_adg_info += "<br/>" + "ID:" + adg_info_list[i].split(FrameConst.separator_pipe_escape)[0] + " " + adg_name;
				}
				
			}
			
			// merge,compareの場合
			
			String mode;
			if(this.merge.isClicked()){
				mode = FrameConst.merge;
			}else{
				mode = FrameConst.compare;
			}
			
			// グラフ番号を設定
			//this.graph_cnt.setLabel("1 / 2");
			
			// dummy data設定(adg_id/値にする必要あり)
			if(this.compare.isClicked()){
				target_reach = "1/40.00,30.00,20.00,30.00,70.00,80.00";
				target_reach += FrameConst.separator_sharp;
				target_reach += "2/50.00,40.00,30.00,30.00,10.00,15.00";
				target_reach += FrameConst.separator_sharp;
				target_reach += "3/05.00,15.00,25.00,35.00,45.00,55.00";
				
				content_target = "1/40.00,30.00,20.00,30.00,70.00,90.00";
				content_target += FrameConst.separator_sharp;
				content_target += "2/50.00,40.00,30.00,30.00,10.00,85.00";
				content_target += FrameConst.separator_sharp;
				content_target += "3/05.00,15.00,25.00,35.00,45.00,55.00";
				
				
			}else{
				//target_reach = "0/40.00,30.00,20.00,30.00,70.00,80.00";
				//content_target = "0/15.00,40.00,55.00,30.00,40.00,95.00";
				
				// 表示日数で値を制御
				for(int i = 0; i < day_list.length; i++){
					
					if(target_reach.isEmpty()){
						target_reach = "0/30.00";
						content_target = "0/10.00";
					}else{
						target_reach  += ",40.00";
						content_target  += ",25.00";
					}
					
				}
				
			}

			
			graphs = target_reach;
			graphs += FrameConst.separator_pipe;
			graphs += content_target;
			graphs += FrameConst.separator_pipe;
			graphs += mode;

			this.hid_m_cgraph.setValue(graphs);
			
			// グラフ右の領域の表示設定
		    disp_adgroups_name = new Label("disp_adgroups_name", disp_adg_info);
		    form.add(disp_adgroups_name);
		    
			
		}else{
			String adg_id = adg_info.split(FrameConst.separator_pipe_escape)[0];
			
			String adg_name;
			// 名前がない場合もあるので
			if(adg_info.split(FrameConst.separator_pipe_escape).length > 1){
				adg_name = adg_info.split(FrameConst.separator_pipe_escape)[1];
			}else{
				adg_name = "";
			}
			// 通常のdisplayの場合
			// hid_graphへ、ID|tatget|content_rate（1|20.00|30.00_70.00の形式で格納 ※複数の場合は「^」つなぎ）
			
			// グラフ番号を設定
			//this.graph_cnt.setLabel("1 / 2");
			
			// hid_graphの値を確認 → 日付けを変えた場合グラフを変化させる必要があるので、この処理をコメントアウト
//			if(this.hid_graph.getValue() != null){
//				String hid_graph_val = this.hid_graph.getValue();
//				String[] hid_graph_val_list = hid_graph_val.split(FrameConst.separator_caret_escape);
//				for(int i = 0; i < hid_graph_val_list.length; i++){
//					String id = hid_graph_val_list[i].split(FrameConst.separator_pipe_escape)[0];
//					if(adg_id.equals(id)){
//						exist = true;
//						break;
//					}
//					
//				}
//			}
			// hid_graphにグラフデータが存在しない場合のみ処理を実行
			//if(!exist){ //日付けを変えた場合、グラフが変化するので、存在しても再度処理する
				// とりあえずのダミー値
				//target_reach = "40.00,30.00,20.00,30.00,70.00,80.00,40.00,30.00,20.00,30.00,70.00,80.00,40.00,30.00,20.00,30.00,70.00,80.00,40.00,30.00,20.00,30.00,70.00,80.00,40.00,30.00,20.00,30.00,70.00,80.00,95.55";
				//content_target = "10.00,20.00,30.00,50.00,60.00,10.00";
				
				// 表示日数で値を制御
				for(int i = 0; i < day_list.length; i++){
					
					if(target_reach.isEmpty()){
						target_reach = "30.00";
						content_target = "10.00";
					}else{
						target_reach  += ",40.00";
						content_target  += ",25.00";
					}
					
				}
				
				
				graphs  = adg_id;
				graphs += FrameConst.separator_pipe;
				graphs += target_reach;
				graphs += FrameConst.separator_pipe;
				graphs += content_target;
				
				
				
				if(this.hid_graph.getValue().isEmpty()){
					this.hid_graph.setValue(graphs);
				}else{
					// 自身と同じIDがあれば削除
					String set_val = "";
					String[] before_val_list = this.hid_graph.getValue().split(FrameConst.separator_caret_escape);
					for(int i = 0; i < before_val_list.length; i++){
						if(!adg_id.equals(before_val_list[i].split(FrameConst.separator_pipe_escape)[0])){ // 設定しようとしているIDと異なるものであれば保存
							if(set_val.isEmpty()){
								set_val = before_val_list[i];
							}else{
								set_val +=  FrameConst.separator_caret + before_val_list[i];
							}
						}
						
					}

					// それ以外は、「＾」区切りで追加
					this.hid_graph.setValue(set_val + FrameConst.separator_caret  + graphs);
				}
				
				
				//}
			
			
			// 通常時はmerge、compare専用のhiddenに空を格納
			this.hid_m_cgraph.setValue("");
			
			// グラフ右の領域の表示設定
			this.disp_ad_name.setLabel("ID:" + adg_id + " " + adg_name);
			
		}
		
	}
	
    /*
     * ログアウト処理
     */
    public boolean do_logout(){
    	
    	if(this.hide_disp_mode.getValue().isEmpty()){
    		// 管理者の場合
    		
	    	certify.clearLoginInfo();
	    	setForward("login.htm");
    	}else{
    		// 一般ユーザの場合
			HttpServletRequest req = this.getContext().getRequest();
			HttpSession session = req.getSession();
			
    		session.setAttribute(UtilConst.Session_user_auth, null);
    		
			path = getContext().getPagePath(UserAuth.class);
			path += "?id=" + this.campaign_id.getValue(); // campaignIDを渡す
			setRedirect(path);
    	}
    	
    	return true;
    }
    
    private boolean invalidCheck(){

//    	// XSSチェック
//		if(!this.start_date.getValue().isEmpty() && SecurityUtil.hasXssChars(this.start_date.getValue())){
//			errMsg = new Label("errMsg", MessageConst.XSSErrMsg);
//			form.add(errMsg);
//			return true;
//		}
//		if(!this.end_date.getValue().isEmpty() && SecurityUtil.hasXssChars(this.end_date.getValue())){
//			errMsg = new Label("errMsg", MessageConst.XSSErrMsg);
//			form.add(errMsg);
//			return true;
//		}
    	
    	// 日付けチェック
    	if(this.start_date.getValue().isEmpty()){
			errMsg = new Label("errMsg", MessageConst.dateNotSelectMsg);
			form.add(errMsg);
			return true;
    	}
    	if(this.end_date.getValue().isEmpty()){
			errMsg = new Label("errMsg", MessageConst.dateNotSelectMsg);
			form.add(errMsg);
			return true;
    	}
		
		// 日付形式チェック
		if(!this.start_date.getValue().isEmpty() && !SecurityUtil.checkDate(this.start_date.getValue())){
			errMsg = new Label("errMsg", MessageConst.InvalidDateErrMsg);
			form.add(errMsg);
			return true;
		}
		if(!this.end_date.getValue().isEmpty() && !SecurityUtil.checkDate(this.end_date.getValue())){
			errMsg = new Label("errMsg", MessageConst.InvalidDateErrMsg);
			form.add(errMsg);
			return true;
		}
    	
		// 日付差分チェック
		if(!this.start_date.getValue().isEmpty() && !this.end_date.getValue().isEmpty()){
			if(DataUtil.convertString2Date(this.start_date.getValue()).compareTo(DataUtil.convertString2Date(this.end_date.getValue())) > 0){
				errMsg = new Label("errMsg", MessageConst.dateErrorMsg);
				form.add(errMsg);
				
				return true;
			}

		}
		
		return false;
    }
	
	
}
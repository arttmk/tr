package simulator;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import logic.CertificationLogic;

import item.Adgroup;
import item.Campaign;

import org.apache.click.Context;
import org.apache.click.control.AbstractLink;
import org.apache.click.control.ActionLink;
import org.apache.click.control.Button;
import org.apache.click.control.Checkbox;
import org.apache.click.control.Column;
import org.apache.click.control.Decorator;
import org.apache.click.control.Form;
import org.apache.click.control.HiddenField;
import org.apache.click.control.Label;
import org.apache.click.control.PageLink;
import org.apache.click.control.Submit;
import org.apache.click.control.Table;
import org.apache.click.control.TextField;
import org.apache.click.extras.control.DateField;
import org.apache.click.extras.control.FieldColumn;
import org.apache.click.extras.control.LinkDecorator;
import org.apache.click.util.Bindable;

import servlet.LoadServlet;
import util.DataUtil;
import util.FrameConst;
import util.MessageConst;
import util.SecurityUtil;
import util.UtilConst;

import dao.AdgroupDao;
import dao.CampaignDao;

public class EditCampaign extends org.apache.click.Page {
	
	private static final long serialVersionUID = -4626327956108815288L;

	private Form form = new Form("form");
	
	/** ログイン情報ヘッダー **/
	private Submit logout;;
	private CertificationLogic certify = new CertificationLogic();
	/**************************/
	
	private TextField campaign_id;
	private TextField name;
	private TextField start_date;
	private TextField end_date;
	private TextField password;

	private Table search_adgroup_table;
	private Table selected_adgroup_table;
	private FieldColumn column;
	
	private TextField keyword;
	private Checkbox online;
	private Checkbox tv;
	
	
	private Label row1;
	private Label row2;
	private Label row3;
	private Label row4;

	private Submit search;
	private Submit save;
	private Submit exec;
	private Submit cancel;
	
	private Label over_max_count;
	private Label errMsg;
	
	private CampaignDao campaign_dao = new CampaignDao();
	private Campaign campaign = new Campaign();

	private AdgroupDao adgroup_dao = new AdgroupDao();
	private Adgroup adgroup = new Adgroup();

	
	// hidden
    private HiddenField hid_referrer = new HiddenField("hid_referrer", String.class); // referrerを格納
    private HiddenField adgroup_list = new HiddenField("adgroup_list", String.class); // 選択済みデータを格納
    private HiddenField hid_token = new HiddenField("hid_token", String.class); // tokenを格納

    // Bindable variables can automatically have their value set by request parameters
    @Bindable protected Integer id;
    @Bindable protected String referrer;
    
    // adg検索マックス値
    private final int adg_max_count = 30;
    
	public EditCampaign(){
		
		 Context context = getContext();
		 
		// キャンペーンID
		campaign_id = new TextField(CampaignDao.ID,""); // form.copyFromへ対応させるため、画面の項目名とCampaignの項目名を合わせる
		campaign_id.setReadonly(true);
		form.add(campaign_id);

		// キャンペーン名
		name = new TextField(CampaignDao.NAME,"");
		form.add(name);
		
		// start_date
		start_date = new TextField(CampaignDao.START_DATE, "Start Date");
		start_date.setWidth("100");
		start_date.setAttribute("placeholder", "from");
		form.add(start_date);
		
		end_date = new TextField(CampaignDao.END_DATE, "End Date");
		end_date.setWidth("100");
		end_date.setAttribute("placeholder", "to");
		form.add(end_date);
		
		// パスワード
		password = new TextField(CampaignDao.PASSWORD,"");
		password.setDisabled(true);
		form.add(password);
		
		// adgroup検索項目
		keyword = new TextField("keyword", "keyword");
	    form.add(keyword);
		
		online = new Checkbox("online", FrameConst.online);
		online.setChecked(true);
		form.add(online);
	    tv = new Checkbox("tv", FrameConst.tv);
	    tv.setChecked(true);
	    form.add(tv);
	    
		// search
	    search = new Submit("search","Search",this,"adg_search");
		form.add(search);
	    
	    
	    // 選択済みadgroupテーブル
//		adgroup_table = new Table();
//		adgroup_table.addColumn(new Column(AdgroupDao.ID,"ID"));
////		FieldColumn column = new FieldColumn("adgroup_id", new HiddenField());
////		adgroup_table.addColumn(column);
//		adgroup_table.addColumn(new Column(AdgroupDao.NAME,"Name"));
//		adgroup_table.setName("adgroup_table");
//		adgroup_table.setPageSize(10);
//		this.addControl(adgroup_table);

	    // search adgroupテーブル //
		search_adgroup_table = new Table();
		search_adgroup_table.addStyleClass("tbl-1");
		search_adgroup_table.addColumn(new Column(AdgroupDao.ID,"ID"));
		//earch_adgroup_table.addColumn(new Column(AdgroupDao.NAME,"Name"));
		//search_adgroup_table.addColumn(new Column(AdgroupDao.MEDIA,"Media"));
		// meida(加工したかったので、fieldColumにした)
		column = new FieldColumn(AdgroupDao.MEDIA);
		column.setDecorator(new Decorator() {  
		    public String render(Object row, Context context) {  
		    	Adgroup adg = (Adgroup) row;
		        String media = adg.getMedia();

		        String val = "<span class='label-1'>"+ media +"</span>";
		        
		        return val;
		    }  
		});  
		search_adgroup_table.addColumn(column);
		
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
		        val += "<div id='jquery-ui-effect" + adg_id +"' class='ui-widget-content ui-corner-all'  style='display: none;'>";
		        val += "<p>" + detail + "</p>";
		        val += "</div>";
		        
		        return val;
		    }  
		});  
		search_adgroup_table.addColumn(column);
		
		// detail button
		column = new FieldColumn(AdgroupDao.function, new Button(AdgroupDao.function,AdgroupDao.function));
		// decoratorで、行の情報に合わせて動的に項目を追加
		column.setDecorator(new Decorator() {  
		    public String render(Object row, Context context) {  
		    	Adgroup adg = (Adgroup) row;  
		        int adg_id = adg.getId();
		        String name = adg.getName();
		        String media = adg.getMedia();

		        String val = "<input type='button' value='Detail' class='btn-detail' onClick='showDetail(" + adg_id + ");' >";
		        val += "&nbsp;&nbsp;";
		        val += "<input type='button' value='Add' id='add-" + adg_id + "' class='btn-add' onClick=\"setSelectCampaignID('" + adg_id + "','" + name + "','" + media.replace("　", "") + "');\" >";

		        return val;
		    }  
		});  
		search_adgroup_table.addColumn(column);
		
//		// add button
//		column = new FieldColumn("add", new Button("add","Add"));
//
//		// decoratorで、行の情報に合わせて動的に項目を追加
//		column.setDecorator(new Decorator() {  
//		    public String render(Object row, Context context) {  
//		    	Adgroup adg = (Adgroup) row;  
//		        int adg_id = adg.getId();
//		        String name = adg.getName();
//		        String media = adg.getMedia();
//
//		        return "<input type='button' value='Add' class='btn-add' onClick=\"setSelectCampaignID('" + adg_id + "','" + name + "','" + media.replace("　", "") + "');\" ";
//		    }  
//		});  
//		search_adgroup_table.addColumn(column);

		search_adgroup_table.setHeight("100");
		//search_adgroup_table.setPageSize(10);

		search_adgroup_table.setName("search_adgroup_table");
		this.addControl(search_adgroup_table);
		
	    // selected adgroupテーブル //
		selected_adgroup_table = new Table();
		selected_adgroup_table.addStyleClass("tbl-1");
		selected_adgroup_table.addColumn(new Column(AdgroupDao.ID,"ID"));
		// meida(加工したかったので、fieldColumにした)
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
		//column = new FieldColumn("detail", new Button("detail","Fuction"));
		column = new FieldColumn(AdgroupDao.function, new Button(AdgroupDao.function,AdgroupDao.function));
		// decoratorで、行の情報に合わせて動的に項目を追加
		column.setDecorator(new Decorator() {  
		    public String render(Object row, Context context) {  
		    	Adgroup adg = (Adgroup) row;  
		        int adg_id = adg.getId();
		        String detail = adg.getDetail();

		        String val = "<input type='button' value='Detail' class='btn-detail' onClick='showDetailSelected(" + adg_id + ");' >";
		        val += "&nbsp;&nbsp;";
		        val += "<a href='JavaScript:release_adg(" + adg_id +")' id='rel_adg'>";
		        val += "<img border='0' hspace='2' class='link' alt='Relase Adgroup' src='/img/btn-del.png'>";
		        val += "</a>";



		        return val;
		    }  
		});  
		selected_adgroup_table.addColumn(column);
		
		// del →detailにまとめた
//		column = new FieldColumn("del");
//		// decoratorで、行の情報に合わせて動的に項目を追加
//		column.setDecorator(new Decorator() {  
//		    public String render(Object row, Context context) {  
//		    	Adgroup adg = (Adgroup) row;  
//		        int adg_id = adg.getId();
//
//		        String val = "<a href='JavaScript:release_adg(" + adg_id +")' id='rel_adg'>";
//		        val += "<img border='0' hspace='2' class='link' alt='Relase Adgroup' src='/tracker/img/btn-del.png'>";
//		        val += "</a>";
//
//		        return val;
//		    }  
//		});  
//		selected_adgroup_table.addColumn(column);
		
		selected_adgroup_table.setShowBanner(false);
		selected_adgroup_table.setHeight("30");
		selected_adgroup_table.setName("selected_adgroup_table");
		this.addControl(selected_adgroup_table);
		
		
		// save
		save = new Submit("save","Save",this,"do_save");
		form.add(save);
		
		// exec
		exec = new Submit("exec","Exec",this,"do_exec");
		form.add(exec);

		// cancel
		cancel = new Submit("cancel","",this,"do_cancel");
		form.add(cancel);
		
		// logout
		logout = new Submit("logout","Logout",this,"do_logout");
		form.add(logout);
		
		// hidden
		form.add(hid_referrer);
		form.add(adgroup_list);
		form.add(hid_token);
		 
		this.addControl(form);
		
	}
	
    @Override
    public void onGet() {
    	// 画面遷移時の処理
    	if (id != null) {
    		campaign = campaign_dao.getCampaignDetail(id);
    		// copyでcampaignの要素と同じnameをもつオブジェクトへ値を自動セットする
    		form.copyFrom(campaign);
    	}
    	
    	if (referrer != null) {
    		hid_referrer.setValue(referrer);
    	}
    	
		// sessionからhiddenへtokenを格納
		if(this.getContext().getSessionAttribute(UtilConst.Session_token) != null && !this.getContext().getSessionAttribute(UtilConst.Session_token).toString().isEmpty()){
			this.hid_token.setValue(this.getContext().getSessionAttribute(UtilConst.Session_token).toString());
		}
		
    	super.onGet();
    }
    

	@Override
	public void onRender() { 
		String path = "";
		
		// ログアウト押下出ない場合、ログインチェック
		if(!this.logout.isClicked()){
			path = certify.checkLoginInfo(form);
			
			if(!path.isEmpty()) setRedirect(path); // pathが空でない場合遷移（ログイン画面）
		}

		// tokenチェック
		path = certify.checkToken(this.hid_token.getValue());
		if(!path.isEmpty()) setRedirect(path); // pathが空でない場合遷移（ログイン画面）
		
		// エラーメッセージが出ていない場合のみ、検索実行
		if(this.errMsg == null){
		    // 全adgroup件数取得
		    int adg_cnt = adgroup_dao.get_all_adgroups_count(this.keyword.getValue(),this.online.isChecked(),this.tv.isChecked());
	
		    if(adg_cnt > 0){
		    	// 検索結果がある場合、下記処理
		    	
		    	// 全adgroup表示
				search_adgroup_table.setRowList(adgroup_dao.get_all_adgroups(this.keyword.getValue(),this.online.isChecked(),this.tv.isChecked(), adg_max_count));
				
				// 取得件数とマックスカウントの比較
				if(adg_cnt > adg_max_count){
		    		over_max_count = new Label("over_max_count", MessageConst.AdgoverMaxCountMsg);
		    		form.add(over_max_count);
				}
		    }
		}
		String relatedAdg;
		if(campaign.getAdgroup_list() != null){
			relatedAdg = campaign.getAdgroup_list();
		}else{
			relatedAdg = this.adgroup_list.getValue();
		}

		List<Adgroup> adgroups = adgroup_dao.search_adgroups(relatedAdg);
		
		selected_adgroup_table.setRowList(adgroups);
		
		// execボタンを押下可能かどうか(新規作成の場合は押下不可)
		if(this.id == null || this.id == 0){
			this.exec.setDisabled(true);
			this.exec.addStyleClass("disabled");
		}
		
		
//		String adg_values = "";
//		for(int i = 0; adgroups.size() > i; i++){
////			System.out.println(adgroups.get(i).getId());
////			if(i == 0){
////				row1 = new Label("row1", adgroups.get(i).getName());
////			    form.add(row1);
////			}
//			
//			// もしくはhiddenに格納して、制御をjsにまかせる
//			if(adg_values == ""){
//				adg_values = adgroups.get(i).getName();
//			}else{
//				adg_values += "," + adgroups.get(i).getName();
//			}
//			
//		}
//		
//		
//		// 選択済みadgをhiddenに残す
//		if(this.adgroup_list.getValue().isEmpty()){
//			this.adgroup_list.setValue(adg_values);
//		}
		
		super.onRender();
		
	}


    /*
     * 検索処理
     */
	 public boolean adg_search(){
		 
		 // XSSチェック
		 if(!this.keyword.getValue().isEmpty() && SecurityUtil.hasXssChars(this.keyword.getValue())){
	    		errMsg = new Label("errMsg", MessageConst.XSSErrMsg);
	    		form.add(errMsg);
	        	
	    		return true;
		 }

		 return true;
	 }
	
    /*
     * 保存処理
     */
    public boolean do_save(){
		
    	// nameチェック
    	if(this.name.getValue().isEmpty()){
    		errMsg = new Label("errMsg", MessageConst.noCampaignNameMsg);
    		form.add(errMsg);
        	
    		return true;
    	}else{
    		// XSSチェック
    		if(SecurityUtil.hasXssChars(this.name.getValue())){
    			errMsg = new Label("errMsg", MessageConst.XSSErrMsg);
    			form.add(errMsg);
    			return true;
    		}
    	}
    	
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
		
		
    	form.copyTo(campaign);

		//campaign.setId(Integer.parseInt(this.hid_campaign_id.getValue()));
    	if(campaign.getId() == 0){
    		// insert
    		campaign_dao.insertCampaign(campaign);
    	}else{
    		// update
    		campaign_dao.updateCampaign(campaign);
    	}
    	
    	
    	// 検索画面へ遷移
    	setRedirect(this.hid_referrer.getValue());
    	return true;
    }


    /*
     * exec処理
     */
    public boolean do_exec(){
        String path = getContext().getPagePath(ShowGraph.class);
        path += "?id=" + this.id;
        path += "&referrer=/editCampaign.htm";
        setRedirect(path);
        
    	return true;
    }
    
    /*
     * キャンセル処理
     */
    public boolean do_cancel(){
        String referrer = hid_referrer.getValue();
        
        if (referrer != null && !referrer.isEmpty()) {
            setRedirect(referrer);
        } else {
            setRedirect(CampaignSelect.class);
        }
    	
    	return true;
    }
    
    /*
     * ログアウト処理
     */
    public boolean do_logout(){
    	
    	certify.clearLoginInfo();
    	setForward("login.htm");
    	
    	return true;
    }
    
    
    
}
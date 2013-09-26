package simulator;


import item.Campaign;
import logic.CertificationLogic;

import org.apache.click.ActionListener;
import org.apache.click.Context;
import org.apache.click.Control;
import org.apache.click.control.AbstractLink;
import org.apache.click.control.ActionLink;
import org.apache.click.control.Button;
import org.apache.click.control.Column;
import org.apache.click.control.Decorator;
import org.apache.click.control.Form;
import org.apache.click.control.HiddenField;
import org.apache.click.control.Label;
import org.apache.click.control.PageLink;
import org.apache.click.control.Submit;
import org.apache.click.control.Table;
import org.apache.click.control.TextField;
import org.apache.click.extras.control.FieldColumn;
import org.apache.click.extras.control.LinkDecorator;

import util.DataUtil;
import util.MessageConst;
import util.SecurityUtil;
import util.UtilConst;
import dao.CampaignDao;

public class CampaignSelect extends org.apache.click.Page {

	private static final long serialVersionUID = -7025900694715987096L;

	private Form form = new Form("form");
	
	/** ログイン情報ヘッダー **/
//	private Label login_user_name;
//	private Label login_user_company;
	private Submit logout;;
	private CertificationLogic certify = new CertificationLogic();
	/**************************/
	
	private TextField campaign_name;
	private Submit search_campaign;
	private Submit add_campaign;
	private Table campaign_table;
	private TextField start_date;
	private TextField end_date;
	
	private PageLink dispLink = new PageLink("Disp", ShowGraph.class);
	private PageLink editLink = new PageLink("Edit", EditCampaign.class);
	private ActionLink shareLink = new ActionLink("Share");
	private ActionLink deleteLink = new ActionLink("Delete", this, "onDeleteClick");
	
	private Label errMsg;
	
	private Column column;
	private FieldColumn f_column;
	    
	private CampaignDao campaign_dao = new CampaignDao();
	
    private HiddenField hid_token = new HiddenField("hid_token", String.class); // tokenを格納
	
	// 検索マックスカウント
	private final int cam_max_count = 1000;
	
	private Label over_max_count;
	
	public CampaignSelect(){
	    
		// キャンペーン名
		campaign_name = new TextField("campaign_name","");
		form.add(campaign_name);
		
		// durtaion
		start_date = new TextField(CampaignDao.START_DATE, "Start Date");
		//start_date.setWidth("80");
		start_date.setAttribute("placeholder", "from");
		form.add(start_date);
		
		end_date = new TextField(CampaignDao.END_DATE, "End Date");
		//end_date.setWidth("80");
		end_date.setAttribute("placeholder", "to");
		form.add(end_date);
		
		// キャンペーン検索ボタン
		search_campaign = new Submit("search_campaign","Search",this,"search_campaign");
	    form.add(search_campaign);
	    
		// キャンペーン新規ボタン
	    add_campaign = new Submit("add_campaign","Add Campaign",this,"add_campaign");
	    form.add(add_campaign);

	    // キャンペーンテーブル
		campaign_table = new Table();
		campaign_table.addStyleClass("tbl-1");
		campaign_table.addColumn(new Column(CampaignDao.ID,"ID"));
		campaign_table.addColumn(new Column(CampaignDao.NAME,"Name"));
		campaign_table.addColumn(new Column(CampaignDao.START_DATE,"Start date"));
		campaign_table.addColumn(new Column(CampaignDao.END_DATE,"End date"));
		
		campaign_table.setName("campaign_table");
		campaign_table.setPageSize(10);
		//campaign_table.setSortable(true);

		// display
		dispLink.addStyleClass("btn-disp");
		dispLink.setTitle("Disp campaign");
		dispLink.setParameter("referrer", "/campaign_select.htm");
		
		
		// edit
		//editLink.setImageSrc("/img/tv-e.png");
		editLink.addStyleClass("btn-edit");
		editLink.setTitle("Edit campaign details");
		editLink.setParameter("referrer", "/campaign_select.htm");
	        
//		// Share
//		shareLink.addStyleClass("btn-share");
//		shareLink.setTitle("Show campaign URL & Password");
//		shareLink.setAttribute("onclick", "alert(this.value);");
//		
//		// Share処理用非表示ボタン
//		f_column = new FieldColumn(CampaignDao.PASSWORD, new Button(CampaignDao.PASSWORD,CampaignDao.PASSWORD));
//		// decoratorで、行の情報に合わせて動的に項目を追加
//		f_column.setDecorator(new Decorator() {  
//		    public String render(Object row, Context context) {  
//		    	Campaign cam = (Campaign) row;  
//		        int cam_id = cam.getId();
//		        String cam_password = cam.getPassword();
//
//		        String val = "<input type='button' value='Share' style='display:block' onClick='alert(" + cam_id + ");' >";
//
//		        return val;
//		    }  
//		});  
//		campaign_table.addColumn(f_column);
		
		// delete
        //deleteLink.setImageSrc("/img/btn-close.png");
		deleteLink.addStyleClass("btn-disc");
        deleteLink.setTitle("Delete campaign");
        deleteLink.setAttribute("onclick", "return window.confirm('Are you sure you want to delete this campaign?');");

        column = new Column("Action");
        column.setTextAlign("center");
        AbstractLink[] links = new AbstractLink[] { dispLink, editLink, deleteLink };
        LinkDecorator deco = new LinkDecorator(campaign_table, links, "id");
        deco.setLinkSeparator(""); // デフォが|なので、なくす
        column.setDecorator(deco);
        column.setSortable(false);
        campaign_table.addColumn(column);
		
//		// キャンペーンデータ取得
//		CampaignDao campaign_dao = new CampaignDao();
//		campaign_table.setRowList(campaign_dao.get_campaigns());
		this.addControl(campaign_table);
		this.addControl(deleteLink);

		// logout
		logout = new Submit("logout","Logout",this,"do_logout");
		form.add(logout);
		
		form.add(hid_token);
		
		this.addControl(form);
	}

	
	@Override
	public void onGet() { 
		// sessionからhiddenへtokenを格納
		if(this.getContext().getSessionAttribute(UtilConst.Session_token) != null && !this.getContext().getSessionAttribute(UtilConst.Session_token).toString().isEmpty()){
			this.hid_token.setValue(this.getContext().getSessionAttribute(UtilConst.Session_token).toString());
		}
		
		
		super.onGet();
	}
	
	
	@Override
	public void onRender() { 
		String path = "";
		// ログアウト押下でない場合、ログインチェック
		if(!this.logout.isClicked()){
			path = certify.checkLoginInfo(form);
			
			if(!path.isEmpty()) setRedirect(path); // pathが空でない場合遷移（ログイン画面）
		}

		// tokenチェック
		path = certify.checkToken(this.hid_token.getValue());
		if(!path.isEmpty()) setRedirect(path); // pathが空でない場合遷移（ログイン画面）
		
		// エラーメッセージが出ていない場合のみ、検索実行
		if(this.errMsg == null){
			// キャンペーンデータ取得	
			Campaign campaign = new Campaign();
			campaign.setName(this.campaign_name.getValue());
			campaign.setStart_date(this.start_date.getValue());
			campaign.setEnd_date(this.end_date.getValue());
	
			int cam_count = campaign_dao.get_campaigns_count(campaign);
			
			if(cam_count > 0){
				// 検索結果がある場合、下記処理
				
				campaign_table.setRowList(campaign_dao.get_campaigns(campaign, cam_max_count));
				
				// 取得件数とマックスカウントの比較
				if(cam_count > cam_max_count){
		    		over_max_count = new Label("over_max_count", MessageConst.CamoverMaxCountMsg);
		    		form.add(over_max_count);
				}
			}
		}
		
		super.onRender();
		
	}  
	
	// 画面遷移した場合も状態を保存する
	@Override
	public void onInit() {
	    
	    Context context = getContext();
	
	    // Restore form and table state from the session
	    form.restoreState(context);
	    campaign_table.restoreState(context);
	
	    campaign_table.getControlLink().setActionListener(new ActionListener() {
	        public boolean onAction(Control source) {
	        	campaign_table.saveState(getContext());
	            return true;
	        }
	    });
		
	    super.onInit();
	}

	  
	/*
	 * Searchボタン処理
	 */
	public boolean search_campaign(){
		
		// XSSチェック
		if(!this.campaign_name.getValue().isEmpty() && SecurityUtil.hasXssChars(this.campaign_name.getValue())){
			errMsg = new Label("errMsg", MessageConst.XSSErrMsg);
			form.add(errMsg);
			return true;
		}
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
			}
		}


		return true;
	}
	
	/*
	 * delete処理
	 */
    public boolean onDeleteClick() {
        Integer id = deleteLink.getValueInteger();
        //System.out.println(id);
        campaign_dao.deleteCampaign(id);
        return true;
    }
    
	/*
	 * campaign新規追加処理
	 */
    public boolean add_campaign() {
        String path = getContext().getPagePath(EditCampaign.class);
        path += "?referrer=/campaign_select.htm";
        setRedirect(path);
        return false;
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
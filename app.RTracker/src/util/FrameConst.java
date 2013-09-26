package util;

/**
 * 定数
 * @author arata.hasebe
 */
public class FrameConst {
	
	// separator
	public static final String separator_space = " ";
	
	public static final String separator_pipe = "|";
	public static final String separator_pipe_escape = "\\|";
	public static final String separator_under = "_";
	public static final String separator_slash = "/";
	public static final String separator_sharp = "#";
	public static final String separator_caret = "^";
	public static final String separator_caret_escape = "\\^";
	public static final String separator_colon = ":";
	public static final String separator_zenkaku_space = "　";
	

	// 定数
	public static final String online = "Online";
	public static final String tv = "TV";
	
	public static final String media_tv = "2";

	public static final String online_span = "<span class='label-1'>Online</span>";
	public static final String tv_span = "<span class='label-1'>TV</span>";

	public static final String per_day = "Per Day";

	public static final String show_graph_url = "http://1.rtt-tracker.appspot.com/showGraph.htm?id="; // 一般ユーザ用URL
	public static final int disp_mode = 2; // 一般ユーザ用mode

	public static final String male = "Male";
	public static final String female = "Female";
	public static final String gender_u = "Undefined(gender)";
	
	public static final String age1 = "18-24";
	public static final String age2 = "25-34";
	public static final String age3 = "35-44";
	public static final String age4 = "45-54";
	public static final String age5 = "55-64";
	public static final String age6 = "65+";
	public static final String age_u = "Undefined(age)";
	
	public static final String size1 = "300*250";
	public static final String size2 = "336*280";
	public static final String size3 = "728*90";
	public static final String size4 = "160*600";
	
	public static final String place1 = "Premium";
	public static final String place2 = "Longtail";
	
	public static final String param_max_cpm = "Max CPM:";
	public static final String param_fre_cap = "Frequency cap:";
	public static final String param_duration = "Duration:";
	public static final String param_reach_type= "Reach type:";
	public static final String param_date = "Date:";
	public static final String param_max_budget = "Max Budget:";
	public static final String param_discount = "Discount:";
	public static final String param_tv_duration = "TV Duration:";
	public static final String param_tv_campaign = "TV Campaign:";
	public static final String param_time_slots = "Time Slots:";
	public static final String param_effective_reach = "Effective Reach:";
	
	public static final int max_adgroup_count = 8; // 広告グループのマックス（色調数に合わせて8に）
	
	public static final String ususal = "u";
	public static final String merge = "merge";
	public static final String compare = "compare";
	public static final String reach = "reach";
	
	public static final String adgroup_disp = "Display";
	public static final String adgroup_del = "Discord";
	
	public static final String JPY = "JPY";
	
	public static final String label_gdn = "gdn";
	public static final String label_youtube = "YouTube";
	public static final String label_tv = "tv";
	public static final String label_trueview = "trueview";
	public static final String label_masthead = "masthead";
	public static final String label_tvmodel = "tvmodel";
	public static final String label_tvactual = "tvactual";

	public static final String r_param_gdn = "GDN";
	public static final String r_param_trueview = "YTTV";
	public static final String r_param_masthead = "YTMH";
	public static final String r_param_tv = "TV";
	public static final String r_param_tvmodel  = "TVMO";
	public static final String r_param_tvactual = "TVAC";
	
	
	public static final String month_1 = "Jan";
	public static final String month_2 = "Feb";
	public static final String month_3 = "Mar";
	public static final String month_4 = "Apr";
	public static final String month_5 = "May";
	public static final String month_6 = "Jun";
	public static final String month_7 = "Jul";
	public static final String month_8 = "Aug";
	public static final String month_9 = "Sep";
	public static final String month_10 = "Oct";
	public static final String month_11 = "Nov";
	public static final String month_12 = "Dec";
	
	public static final int max_cpm_position = 16; // max_cpmが格納されている位置
	public static final int max_eff_reach_position = 26; // Effective Reachが格納されている位置
	
	public static final String greater_than= "≦";
	public static final String universe = "[Universe]";
	public static final String effective_reach = "[Effective Reach]";
	
	public static final String adGrpDetail_gender = "[Gender]";
	public static final String adGrpDetail_age = "[Age]";
	public static final String adGrpDetail_size = "[Size]";
	public static final String adGrpDetail_placement = "[Placement]";
	public static final String adGrpDetail_maxcpm = "[Max CPM]";
	public static final String adGrpDetail_freq = "[Frequency cap]";
	public static final String adGrpDetail_duration = "[Duration]";
	public static final String adGrpDetail_reach_type = "[Reach type]";
	public static final String adGrpDetail_date = "[Date]";
	public static final String adGrpDetail_max_budget = "[Max Budget]";
	public static final String adGrpDetail_discount = "[Discount]";
	public static final String adGrpDetail_tv_duration = "[TV Duration]";
	public static final String adGrpDetail_tv_campaign = "[TV Campaign]";

	// グラフデータファイルの項目
	public static final String target_reach = "Target-Reach";
	public static final String target_content_rate = "Target-Content-Rate";
	public static final String reach_cost_curve = "Reach-Cost-Curve";
	public static final String combined_reach = "Combined-Reach";
	
	//メッセージ
	public static final String adGroupMax_errMsg = "The number of AdGroups is up to eight.";
	public static final String sizeNoSelect_errMsg = "Please select at least one size.";
	public static final String placementNoSelect_errMsg = "Please select at least one place.";
	public static final String noDownloadFile_errMsg = "No download file.";
	public static final String mergeSelect_errMsg = "Please select at least two AdGroups to merge.";
	public static final String mergeSelect_MaxCPM_errMsg = "Please select same Max CPM AdGroups to merge.";
	public static final String mergeSelect_media_errMsg = "Please select same Media AdGroups to merge.";
	public static final String compareSelect_errMsg = "Please select at least two AdGroups to compare.";
	public static final String execR_errMsg = "R execution error.";
	public static final String noGraphDataFile_errMsg = "No GraphData file.";
	public static final String dateNoSelect_errMsg = "Please select at least one date.";
	public static final String noDataDate_errMsg = "Please select at least one data exist date.";
	
	public static final String ageSelect_errMsg = "Invalid value for age.";
	public static final String read_uploadFile_errMsg = "File upload error.";
	
	public static final String graph_click_prompt_msg = "Click to show other graph";
	
	
	
	// mastheadでデータのある日付
	//public static final String calendar_data = "20120301,20120302,20120303,20120304,20120305,20120306,20120307,20120308,20120309,20120310,20120311,20120312,20120313,20120314,20120315,20120316,20120317,20120318,20120319,20120320,20120321,20120322,20120323,20120324,20120325,20120326,20120327,20120328,20120329,20120330,20120331,20120401,20120402,20120403,20120404,20120405,20120406,20120407,20120408,20120409,20120410,20120411,20120412,20120413,20120414,20120415,20120416,20120417,20120418,20120419,20120420,20120421,20120422,20120423,20120424,20120425,20120426,20120427,20120428,20120429,20120430,20120501,20120502,20120503,20120504,20120505,20120506,20120507,20120508,20120509,20120510,20120511,20120512,20120513,20120514,20120515,20120516,20120517,20120518,20120519,20120520,20120521,20120522,20120523,20120524,20120525,20120526,20120527,20120528,20120529,20120530,20120531,20120601,20120602,20120603,20120604,20120605,20120606,20120607,20120608,20120609,20120610,20120611,20120612,20120613,20120614,20120615,20120616,20120617,20120618,20120619,20120620,20120621,20120622,20120623,20120624,20120625,20120626,20120627,20120628,20120629,20120630,20120701,20120702,20120703,20120704,20120705,20120706,20120707,20120708,20120709,20120710,20120711,20120712,20120713,20120714,20120715,20120716,20120717,20120718,20120719,20120720,20120721,20120722,20120723,20120724,20120725,20120726,20120727,20120728,20120729,20120730,20120731,20120801,20120802,20120803,20120804,20120805,20120806,20120807,20120808,20120809,20120810,20120811,20120812,20120813,20120814,20120815,20120816,20120817,20120818,20120819,20120820,20120821,20120822,20120823,20120824,20120825,20120826,20120827,20120828,20120829,20120830,20120831,20120901,20120902,20120903,20120904,20120905,20120906,20120907,20120908,20120909,20120910,20120911,20120912,20120913,20120914,20120915,20120916,20120917,20120918,20120919,20120920,20120921,20120922,20120923,20120924,20120925,20120926,20120927,20120928,20120929,20120930,20121001,20121002,20121003,20121004,20121005,20121006,20121007,20121008,20121009,20121010,20121011,20121012,20121013,20121014,20121015,20121016,20121017,20121018,20121019,20121020,20121021,20121022,20121023,20121024,20121025,20121026,20121027,20121028,20121029,20121030,20121031,20121101,20121102,20121103,20121104,20121105,20121106,20121107,20121108,20121109,20121110,20121111,20121112,20121113,20121114,20121115,20121116,20121117,20121118,20121119,20121120,20121121,20121122,20121123,20121124,20121125,20121126,20121127,20121128,20121129,20121130,20121201,20121202,20121203,20121204,20121205,20121206,20121207,20121208,20121209,20121210,20121211,20121212,20121213,20121214,20121215,20121216,20121217,20121218,20121219,20121220,20121221,20121222,20121223,20121224,20121225,20121226,20121227,20121228,20121229,20121230,20121231,20130101,20130102,20130103,20130104,20130105,20130106,20130107,20130108,20130109,20130110,20130111,20130112,20130113,20130114,20130115,20130116,20130117,20130118,20130119,20130120,20130121,20130122,20130123,20130124,20130125,20130126,20130127,20130128,20130129,20130130,20130131,20130201,20130202,20130203,20130204,20130205,20130206,20130207,20130208,20130209,20130210,20130211,20130212,20130213,20130214,20130215,20130216,20130217,20130218,20130219,20130220,20130221,20130222,20130223,20130224,20130225,20130226,20130227,20130228";
	


}

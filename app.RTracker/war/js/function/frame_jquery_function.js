
jQuery( function() {
    jQuery( '#jquery-ui-show1' ) . button();
    jQuery( '#jquery-ui-show1' ) . click( function() {
        jQuery( '#jquery-ui-effect1' ) . toggle( 'blind', '', 1 );
    } );
} );

jQuery( function() {
    jQuery( '#jquery-ui-show2' ) . button();
    jQuery( '#jquery-ui-show2' ) . click( function() {
        jQuery( '#jquery-ui-effect2' ) . toggle( 'blind', '', 1 );
    } );
} );
	
jQuery( function() {
    jQuery( '#jquery-ui-show3' ) . button();
    jQuery( '#jquery-ui-show3' ) . click( function() {
        jQuery( '#jquery-ui-effect3' ) . toggle( 'blind', '', 1 );
    } );
} );
	
jQuery( function() {
    jQuery( '#jquery-ui-show4' ) . button();
    jQuery( '#jquery-ui-show4' ) . click( function() {
        jQuery( '#jquery-ui-effect4' ) . toggle( 'blind', '', 1 );
    } );
} );
	
jQuery( function() {
    jQuery( '#jquery-ui-show5' ) . button();
    jQuery( '#jquery-ui-show5' ) . click( function() {
        jQuery( '#jquery-ui-effect5' ) . toggle( 'blind', '', 1 );
    } );
} );
	
jQuery( function() {
    jQuery( '#jquery-ui-show6' ) . button();
    jQuery( '#jquery-ui-show6' ) . click( function() {
        jQuery( '#jquery-ui-effect6' ) . toggle( 'blind', '', 1 );
    } );
} );
	
jQuery( function() {
    jQuery( '#jquery-ui-show7' ) . button();
    jQuery( '#jquery-ui-show7' ) . click( function() {
        jQuery( '#jquery-ui-effect7' ) . toggle( 'blind', '', 1 );
    } );
} );
	
jQuery( function() {
    jQuery( '#jquery-ui-show8' ) . button();
    jQuery( '#jquery-ui-show8' ) . click( function() {
        jQuery( '#jquery-ui-effect8' ) . toggle( 'blind', '', 1 );
    } );
} );
	
jQuery( function() {
    jQuery( '#jquery-ui-show9' ) . button();
    jQuery( '#jquery-ui-show9' ) . click( function() {
        jQuery( '#jquery-ui-effect9' ) . toggle( 'blind', '', 1 );
    } );
} );
	
jQuery( function() {
    jQuery( '#jquery-ui-show10' ) . button();
    jQuery( '#jquery-ui-show10' ) . click( function() {
        jQuery( '#jquery-ui-effect10' ) . toggle( 'blind', '', 1 );
    } );
} );
	

$(document).ready(function() {
	// loading
	$("#form_createAdGrp, .button_disp, .button_dele, .button_merge, .button_compare_reach").click(function(){
		myTop=$(window).scrollTop();
		myHeight=$(window).height();
		$("#loading").css("top",myTop).css("height",myHeight).show();
		$("body").css("overflow","hidden");
	});
	// mouseover
	$("input[type=submit],input[type=button]").hover(function(){
		$(this).css("box-shadow","0 2px 2px rgba(0,0,0,0.4)");
		},function(){
		$(this).css("box-shadow","0 0 0 rgba(0,0,0,0.4)");
	});
	
	// サイドバーの切替
	$(".side_item").hide();
	$(".gdn").show();
	$("#side_switch a").click(function() {
		//if($(this).parent().index()!=2){ // tvを不活性化（暫定対応）
			$("#side_switch a").removeClass("active");
			$(this).addClass("active");
			var id = $(this).attr("href");
			if(id == "youtube") {
				if($("#form_ad_type_trueview").attr("checked") == "checked") id = "trueview";
				else id = "masthead";
			}
			if(id == "tv") {
				if($("#form_tv_type_tvmodel").attr("checked") == "checked") id = "tvmodel";
				else id = "tvactual";
			}
			$(".side_item:not(."+id+")").slideUp();
			$("."+id).slideDown();
			
			// 選択されたmodeをhiddenへ格納
			$("#form_hid_mode").val(id);
			
			// trueviewの場合、1weekか2weekのみ選択可能
			if(id == 'trueview'){
				$('select#form_duration option[value=3week]').remove();
				$('select#form_duration option[value=4week]').remove();
			}else{
				if($('select#form_duration').children().length < 4){ //要素が削除されているなら追加
					$('select#form_duration').append($('<option>').attr({ value: '3week' }).text('3week'));
					$('select#form_duration').append($('<option>').attr({ value: '4week' }).text('4week'));
				}
			}
		//}
		
		return false;
	});
	
	// 画面リフレッシュ後にも画面リフレッシュ前のモードを設定
	switch ($("#form_hid_mode").val()){
	case "trueview":
		$("#form_ad_type_trueview").attr("checked","checked");
		$("#form_ad_type_masthead").removeAttr("checked");
		$("#side_switch a:eq(1)").trigger("click");
		break;
	case "masthead":
		$("#form_ad_type_trueview").removeAttr("checked");
		$("#form_ad_type_masthead").attr("checked","checked");
		$("#side_switch a:eq(1)").trigger("click");
		break;
	case "tvmodel":
		$("#form_tv_type_tvmodel").attr("checked","checked");
		$("#form_tv_type_tvactual").removeAttr("checked");
		$("#side_switch a:eq(2)").trigger("click");
		break;
	case "tvactual":
		$("#form_tv_type_tvmodel").removeAttr("checked");
		$("#form_tv_type_tvactual").attr("checked","checked");
		$("#side_switch a:eq(2)").trigger("click");
		break;
	default:
		$("#side_switch a:eq(0)").trigger("click");
		break;
		
}
	
	// Trueview、Mastheadの切替
	$("#form_ad_type_trueview, #form_ad_type_masthead").change(function() {
		var id = $(this).attr("value");
		$(".side_item:not(."+id+")").slideUp();
		$("."+id).slideDown();
		
		// 選択されたmodeをhiddenへ格納
		$("#form_hid_mode").val(id);
		
		// trueviewの場合、1weekか2weekのみ選択可能
		if(id == 'trueview'){
			$('select#form_duration option[value=3week]').remove();
			$('select#form_duration option[value=4week]').remove();
		}else{
			if($('select#form_duration').children().length < 4){ //要素が削除されているなら追加
				$('select#form_duration').append($('<option>').attr({ value: '3week' }).text('3week'));
				$('select#form_duration').append($('<option>').attr({ value: '4week' }).text('4week'));
			}
		}

		
	});
	
	// model、actualの切替
	$("#form_tv_type_tvmodel, #form_tv_type_tvactual").change(function() {
		var id = $(this).attr("value");
		$(".side_item:not(."+id+")").slideUp();
		$("."+id).slideDown();
		
		// 選択されたmodeをhiddenへ格納
		$("#form_hid_mode").val(id);
	});
	
	// カレンダーの表示
	//var to = new Date();
	
	// データがある日を取得
	var mh_date = $("#form_hid_calendar_data").val();
	var mh_date_list = mh_date.split(",");
	var set_date = mh_date_list[mh_date_list.length -1];
	// データがある最終日の月の一日を設定
	set_date = set_date.substring(0,4) + "-" + set_date.substring(4,6)  + "-01";
	
	$('#date').DatePicker({
		mode: 'multiple',
		inline: true,
		starts: 1,
		//date: new Date(),
		current: new Date(set_date),  // データがある月の一日を表示基準日とする
		onChange: function(date, el) {
		$(el).val(date);
		
		//var date_list = date.toString().split(" ");
		// hiddenに選択された日付を格納
		$("#form_hid_select_days").val(date);
		
		}
	});
	
});

/* 画面ロード時のイベント */
$(window).load(function() {
	// モードによるグラフ右項目の切り替え(初期表示 デフォルトはGDN)
	disp_graph_items();

	// 日付格納hiddenの初期化
	$("#form_hid_select_days").val("");
	
	// 時系列データ選択画面
	// 初期表示（ライトボックス、テーブルの生成）
	(function() {
		var temp = '<div id="lightbox"><div id="lightbox_inner">';
		temp += '<table id="timeslots_l" class="tbl_timeslots"></table>';
		temp += '<ul id="timeslots_shape">';
		temp += '<li><a href="setslot=Mon21&Tue21&Wed21&Thu21&Fri21&Mon22&Tue22&Wed22&Thu22&Fri22&Sat06&Sun06&Sat07&Sun07&Sat08&Sun08&Sat09&Sun09&Sat10&Sun10&Sat11&Sun11&Sat12&Sun12&Sat13&Sun13&Sat14&Sun14&Sat15&Sun15&Sat16&Sun16&Sat17&Sun17&Sat18&Sun18&Sat19&Sun19&Sat20&Sun20&Sat21&Sun21&Sat22&Sun22">Reverse "L" shape</a></li>';
		temp += '<li><a href="setslot=Mon06&Tue06&Wed06&Thu06&Fri06&Sat06&Sun06&Mon07&Tue07&Wed07&Thu07&Fri07&Sat07&Sun07&Sat08&Sun08&Sat09&Sun09&Sat10&Sun10&Sat11&Sun11&Sat12&Sun12&Sat13&Sun13&Sat14&Sun14&Sat15&Sun15&Sat16&Sun16&Sat17&Sun17&Sat18&Sun18&Sat19&Sun19&Sat20&Sun20&Mon21&Tue21&Wed21&Thu21&Fri21&Sat21&Sun21&Mon22&Tue22&Wed22&Thu22&Fri22&Sat22&Sun22">Reverse "C" shape</a></li>';
		temp += '<li><a href="setslot=Mon06&Tue06&Wed06&Thu06&Fri06&Mon07&Tue07&Wed07&Thu07&Fri07&Mon12&Tue12&Wed12&Thu12&Fri12&Mon21&Tue21&Wed21&Thu21&Fri21&Mon22&Tue22&Wed22&Thu22&Fri22&Sat06&Sun06&Sat07&Sun07&Sat08&Sun08&Sat09&Sun09&Sat10&Sun10&Sat11&Sun11&Sat12&Sun12&Sat13&Sun13&Sat14&Sun14&Sat15&Sun15&Sat16&Sun16&Sat17&Sun17&Sat18&Sun18&Sat19&Sun19&Sat20&Sun20&Sat21&Sun21&Sat22&Sun22">Reverse "E" shape</a></li>';
		temp += '<li><a href="setslot=Mon00&Tue00&Wed00&Thu00&Fri00&Sat00&Sun00&Mon01&Tue01&Wed01&Thu01&Fri01&Sat01&Sun01&Mon02&Tue02&Wed02&Thu02&Fri02&Sat02&Sun02&Mon03&Tue03&Wed03&Thu03&Fri03&Sat03&Sun03&Mon04&Tue04&Wed04&Thu04&Fri04&Sat04&Sun04&Mon05&Tue05&Wed05&Thu05&Fri05&Sat05&Sun05&Mon06&Tue06&Wed06&Thu06&Fri06&Sat06&Sun06&Mon07&Tue07&Wed07&Thu07&Fri07&Sat07&Sun07&Mon08&Tue08&Wed08&Thu08&Fri08&Sat08&Sun08&Mon09&Tue09&Wed09&Thu09&Fri09&Sat09&Sun09&Mon10&Tue10&Wed10&Thu10&Fri10&Sat10&Sun10&Mon11&Tue11&Wed11&Thu11&Fri11&Sat11&Sun11&Mon12&Tue12&Wed12&Thu12&Fri12&Sat12&Sun12&Mon13&Tue13&Wed13&Thu13&Fri13&Sat13&Sun13&Mon14&Tue14&Wed14&Thu14&Fri14&Sat14&Sun14&Mon15&Tue15&Wed15&Thu15&Fri15&Sat15&Sun15&Mon16&Tue16&Wed16&Thu16&Fri16&Sat16&Sun16&Mon17&Tue17&Wed17&Thu17&Fri17&Sat17&Sun17&Mon18&Tue18&Wed18&Thu18&Fri18&Sat18&Sun18&Mon19&Tue19&Wed19&Thu19&Fri19&Sat19&Sun19&Mon20&Tue20&Wed20&Thu20&Fri20&Sat20&Sun20&Mon21&Tue21&Wed21&Thu21&Fri21&Sat21&Sun21&Mon22&Tue22&Wed22&Thu22&Fri22&Sat22&Sun22&Mon23&Tue23&Wed23&Thu23&Fri23&Sat23&Sun23">Select all</a></li>';
		temp += '<li><a href="setslot=">Clear all</a></li>';
		temp += '</ul>';
		temp += '<table id="timeslots_s" class="tbl_timeslots"></table>';
		temp += '<div id="lightbox_close"></div>';
		temp += '<div class="clear"></div>';
		temp += '</div></div>';
		$("#frame").append(temp);
		temp = ""
		for (var i=0 ; i<24 ; i++) {
			temp += "<tr>";
			temp += "<td class='mon"+i+"'></td>"
			temp += "<td class='tue"+i+"'></td>"
			temp += "<td class='wed"+i+"'></td>"
			temp += "<td class='thu"+i+"'></td>"
			temp += "<td class='fri"+i+"'></td>"
			temp += "<td class='sat"+i+"'></td>"
			temp += "<td class='sun"+i+"'></td>"
			temp += "</tr>";
		}
		$("#timeslots").append(temp);
		temp = "";
		temp += "<tr>";
		temp += "<th></th>"
		temp += "<th id='slot_mon'>Mon</th>"
		temp += "<th id='slot_tue'>Tue</th>"
		temp += "<th id='slot_wed'>Wed</th>"
		temp += "<th id='slot_thu'>Thu</th>"
		temp += "<th id='slot_fri'>Fri</th>"
		temp += "<th id='slot_sat'>Sat</th>"
		temp += "<th id='slot_sun'>Sun</th>"
		temp += "</tr>";
		for (var i=0 ; i<24 ; i++) {
			var disp_num;
			// 1桁の場合は0埋めする
			if(i < 10){
				disp_num = "0" + i;
			}else{
				disp_num = i;
			}
			temp += "<tr>";
			temp += "<th id='slot_"+disp_num+"'>"+disp_num+":00"+"</th>"
			temp += "<td class='Mon"+disp_num+"'>"+disp_num+":00"+"</td>"
			temp += "<td class='Tue"+disp_num+"'>"+disp_num+":00"+"</td>"
			temp += "<td class='Wed"+disp_num+"'>"+disp_num+":00"+"</td>"
			temp += "<td class='Thu"+disp_num+"'>"+disp_num+":00"+"</td>"
			temp += "<td class='Fri"+disp_num+"'>"+disp_num+":00"+"</td>"
			temp += "<td class='Sat"+disp_num+"'>"+disp_num+":00"+"</td>"
			temp += "<td class='Sun"+disp_num+"'>"+disp_num+":00"+"</td>"
			temp += "</tr>";
		}
		$("#timeslots_l, #timeslots_s").append(temp);
	})();
	
	// 入力用タイムテーブルの表示
	$("#timeslots").click(function(){
		$("#timeslots_s").hide();
		$("#timeslots_l, #timeslots_shape").show();
		$("#lightbox").css("height",$("#frame").height()).fadeIn();
		$("#lightbox_inner").css("left",($(window).width()-$("#lightbox_inner").outerWidth())/2);
	});
	// ライトボックスの終了
	$("#lightbox, #lightbox_close").click(function(){
		$("#lightbox").fadeOut();
		// hiddenに値をセット
		var temp = "";
		$("#timeslots_l td").each(function(){
			if($(this).hasClass("selected")){
				temp += $(this).attr("class").replace(" selected","") + "&";
			}
		});
		temp = temp.slice(0, -1);
		$("#form_hid_timeslots").val(temp);
	});
	$("#lightbox_inner").click(function(){
		return false;
	});
	// 入力用タイムテーブルのイベント処理（セル指定）
	$("#timeslots_l td").click(function(){
		if(!$(this).hasClass("selected")) {
			$("#timeslots ." + $(this).attr("class")).addClass("selected");
			$(this).addClass("selected");
		} else {
			$(this).removeClass("selected");
			$("#timeslots ." + $(this).attr("class")).removeClass("selected");
		}
	});
	// 入力用タイムテーブルのイベント処理（ライン指定）
	$("#timeslots_l th").click(function(){
		var temp=$(this).attr("id");
		temp = temp.replace("slot_","");
		if(temp=="mon"||temp=="tue"||temp=="wed"||temp=="thu"||temp=="fri"||temp=="sat"||temp=="sun"){
			var week = temp;
			temp = "";
			for (var i=0 ; i<24 ; i++) {
				var disp_num;
				// 1桁の場合は0埋めする
				if(i < 10){
					disp_num = "0" + i;
				}else{
					disp_num = i;
				}
				temp += "." + week + disp_num + ", ";
			}
			temp = temp.slice(0, -2);

		} else {
			var time = temp;
			temp = ".mon" + time;
			temp += ", .tue" + time;
			temp += ", .wed" + time;
			temp += ", .thu" + time;
			temp += ", .fri" + time;
			temp += ", .sat" + time;
			temp += ", .sun" + time;
			$(temp).addClass("selected");
		}
		var turnOn = false;
		$(temp).each(function(){
			if(!$(this).hasClass("selected")) turnOn = true;
		});
		if(turnOn) $(temp).addClass("selected");
		else $(temp).removeClass("selected");
		
	});
	// 入力用タイムテーブルのイベント処理（テンプレート指定）
	$("a[href^='setslot']").click(function(){
		var temp = $(this).attr("href").replace("setslot","");
		temp = temp.replace(/[\=]/g, ".");
		temp = temp.replace(/[\&]/g, ", .");
		$("#timeslots td, #timeslots_l td").removeClass("selected");
		$(temp).addClass("selected");
		return false;
	});
	// 確認用タイムテーブルの表示
	$("a[href^='showslot']").click(function(){
		// light box
		$("#timeslots_l, #timeslots_shape").hide();
		$("#timeslots_s").show();
		$("#lightbox").css("height",$("#frame").height()).fadeIn();
		$("#lightbox_inner").css("left",($(window).width()-$("#lightbox_inner").outerWidth())/2);
		// set slot
		
		// temp(選択された時系列)は、active adgroupのtime slot項目から取得
		//var temp = $(this).attr("href").replace("showslot","");
		var slot_data = $("#form_hid_active_adg_row").val().split(",")[2];
		//alert(slot_data);
		var sub_start = slot_data.indexOf("Time Slots:") + 11; // 11はtime slots:の文字数
		var sub_end = slot_data.indexOf(" Effective Reach:");
		
		//alert(sub_start);
		//alert(sub_end);
		slot_data = slot_data.substring(sub_start, sub_end);
		//alert(slot_data);
		
		var temp = "=" + slot_data;
		if(temp == "="){
			// 何も選択していない状態はここでreturn
			return false;
		}
		//var temp = 
		//alert(temp);
		temp = temp.replace(/[\=]/g, "#timeslots_s .");
		temp = temp.replace(/[\&]/g, ", #timeslots_s .");
		$("#timeslots_s td").removeClass("selected");
		
		$(temp).addClass("selected");
		return false;
	});
	
//	if($("#form_hid_mode").val() != ""){
//		$(".side_item").hide();
//		$(".gdn").show();
//		
//		$("#side_switch a").removeClass("active");
//		$("#form_hid_mode").val().addClass("active");
//		var id = $("#form_hid_mode").val();
//		if(id == "youtube") {
//			if($("#form_ad_type_trueview").attr("checked") == "checked") id = "trueview";
//			else id = "masthead";
//		}
//		$(".side_item:not(."+id+")").slideUp();
//		$("."+id).slideDown();
//	}

	
});


/**
 *  グラフ右の項目表示/非表示制御関数
 */
function disp_graph_items(){
	var mode;
	
	if($("#form_hid_active_adg_row").val() != ""){
		// アクティブな広告グループがあれば、それのモードを取得
		mode = $("#form_hid_active_adg_row").val().split(",")[3];
		if(mode == "gdn"){
			// GDN
			$("#gender").css("display", "block");
			$("#age").css("display", "block");
			$("#size").css("display", "block");
			$("#placement").css("display", "block");
			$("#max_cpm").css("display", "block");
			$("#fre_cap").css("display", "block");
			$("#duration").css("display", "block");
			$("#reach_type").css("display", "none");
			$("#select_date").css("display", "none");
			$("#time_slot").css("display", "none");
			$("#max_budget").css("display", "none");
			$("#disocount").css("display", "none");
			$("#tv_duration").css("display", "none");
			$("#tv_campaign").css("display", "none");
			
		}else if(mode == "tvmodel"){
			// TV model
			$("#gender").css("display", "none");
			$("#age").css("display", "none");
			$("#size").css("display", "none");
			$("#placement").css("display", "none");
			$("#max_cpm").css("display", "none");
			$("#fre_cap").css("display", "none");
			$("#duration").css("display", "none");
			$("#reach_type").css("display", "none");
			$("#select_date").css("display", "none");
			$("#time_slot").css("display", "block");
			$("#max_budget").css("display", "block");
			$("#disocount").css("display", "block");
			$("#tv_duration").css("display", "block");
			$("#tv_campaign").css("display", "none");
			
		}else if(mode == "tvactual"){
			// TV model
			$("#gender").css("display", "none");
			$("#age").css("display", "none");
			$("#size").css("display", "none");
			$("#placement").css("display", "none");
			$("#max_cpm").css("display", "none");
			$("#fre_cap").css("display", "none");
			$("#duration").css("display", "none");
			$("#reach_type").css("display", "none");
			$("#select_date").css("display", "none");
			$("#time_slot").css("display", "none");
			$("#max_budget").css("display", "block");
			$("#disocount").css("display", "block");
			$("#tv_duration").css("display", "none");
			$("#tv_campaign").css("display", "block");
		}else{
			// Youtube
			if($("#form_ad_type_trueview").attr("checked") == "checked") {
				// trueview
				$("#gender").css("display", "block");
				$("#age").css("display", "block");
				$("#size").css("display", "none");
				$("#placement").css("display", "none");
				$("#max_cpm").css("display", "none");
				$("#fre_cap").css("display", "block");
				$("#duration").css("display", "block");
				$("#reach_type").css("display", "block");
				$("#select_date").css("display", "none");				
				$("#time_slot").css("display", "none");
				$("#max_budget").css("display", "none");
				$("#disocount").css("display", "none");
				$("#tv_duration").css("display", "none");
				$("#tv_campaign").css("display", "none");
				
			}else{
				// masthead
				$("#gender").css("display", "none");
				$("#age").css("display", "none");
				$("#size").css("display", "none");
				$("#placement").css("display", "none");
				$("#max_cpm").css("display", "none");
				$("#fre_cap").css("display", "none");
				$("#duration").css("display", "none");
				$("#reach_type").css("display", "none");
				$("#select_date").css("display", "block");
				$("#time_slot").css("display", "none");
				$("#max_budget").css("display", "none");
				$("#disocount").css("display", "none");
				$("#tv_duration").css("display", "none");
				$("#tv_campaign").css("display", "none");
			}
		}
		
	}else{
//		// アクティブなものがなければ、現在選択しているモードを取得
//		mode = $("#form_hid_mode").val();
		
		// アクティブなものがなければ初期表示（共通項目AdGroupName以外非表示）
		$("#gender").css("display", "none");
		$("#age").css("display", "none");
		$("#size").css("display", "none");
		$("#placement").css("display", "none");
		$("#max_cpm").css("display", "none");
		$("#fre_cap").css("display", "none");
		$("#duration").css("display", "none");
		$("#reach_type").css("display", "none");
		$("#select_date").css("display", "none");
		$("#time_slot").css("display", "none");
		$("#max_budget").css("display", "none");
		$("#disocount").css("display", "none");
		$("#tv_duration").css("display", "none");
		$("#tv_campaign").css("display", "none");
		
	}

	

}

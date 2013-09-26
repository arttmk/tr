$(document).ready(function() {

	var topHeight = $("#btn_top").offset().top;
	// 初期表示
	if($("#form_hid_first_view").val() == "1" || $("#fileLoadMsg").size() > 0){ // 初期表示もしくは、ファイルアップロードにエラーがある場合はTOP表示
		$("#btn_top").hide();
	}else{
		$("#btn_top, #frame").show();
		$("#top").animate({top:-1*topHeight+"px"},20,"swing"); //uploadの場合は少し表示を早くした

	}

	
	$("#form_simulate").click(function(){
		//simulateボタン押下時
		
		// ageチェック
		if($("#top_form_AgeStart").val() > $("#top_form_AgeEnd").val()){
			// エラーメッセージ表示
			//$('#ageErrMsg').style.display = "block";
			document.getElementById("ageErrMsg").style.display = "block";
//			if($("#fileLoadMsg").size() > 0){
//				document.getElementById("fileLoadMsg").style.display = "none";
//			}
			
			return false;
		}else{
			// エラーメッセージ非表示
			document.getElementById("ageErrMsg").style.display = "none";

//			if($("#fileLoadMsg").size() > 0){
//				document.getElementById("fileLoadMsg").style.display = "none";
//			}
		}
		
		// top部分の各項目をframeの項目へ設定
		var t_param = "";
		var c_name =$("#top_form_top_campaign_name").val(); // キャンペーン名
		
		// Genger
		var gender = "[Gender]";
		if($("#top_form_top_gender_checkbox").attr("checked")){
			gender += "Male ";
		}
		if($("#top_form_top_gender_checkbox_f").attr("checked")){
			gender += "Female ";
		}
		if(gender.length > 10){
			t_param = gender;
		}
		
		// Age
		var age = "[Age]" + $("#top_form_AgeStart").val() + "≦" + $("#top_form_AgeEnd").val();
		t_param += age;
		
		// Universe
		var universe = "[Universe]" + $("#top_form_Universe").val();
		t_param += universe.replace("All_user", "All User"); // All_userをAll Userに置換
		
		// Effective Reach→frameへ移動予定
		var eff_reach = "[Effective Reach]" + $("#top_form_EffectiveReach").val();
		t_param += eff_reach;
		
		//var t_param = '[Gender]Male Female [Age]16 ≦16 [Universe]All User [Effective Reach]1 Frequency';
		
		$('div#campaign').html('<strong>Campaign-Name:</strong>'+ c_name + '<br><strong>Top params:</strong>'+ t_param);
		
		
		// top_parameのhiddenへの設定
		var hid_top="";
		if(gender.replace("Female", "").indexOf("Male",0) > -1){
			hid_top += "Male|";
		}else{
			hid_top += "|";
		}
		if(gender.indexOf("Female",0) > -1){
			hid_top += "Female|";
		}else{
			hid_top += "|";
		}
		hid_top +=  $("#top_form_AgeStart").val() + "|";
		hid_top +=  $("#top_form_AgeEnd").val() + "|";
		hid_top +=  $("#top_form_Universe").val() + "|";
		hid_top +=  $("#top_form_EffectiveReach").val();
		
		$("#form_hid_top_param").val(hid_top);
		
		// キャンペーン名の格納
		$("#form_hid_campaign_name").val(c_name);
		

		// frame表示処理
		$("#btn_top, #frame").show();
		$("#top").animate({top:-1*topHeight+"px"},400,"swing");
		

		return false;
	});
	
	$("#btn_top").click(function(){
		//隠す押下時
		
		// キャンペーンや各種項目の値を引き継ぐ
//		var camp_info = document.getElementById("campaign").innerHTML;
//		alert(camp_info);
//		var subStart = camp_info.indexOf('</strong>',0) + 9;
//		var subEnd = camp_info.indexOf('<br>',0);
//		alert(subStart);
//		alert(subEnd);
//		alert(camp_info.substring(1,10));
//		
//		var top_params = $("#form_hid_top_param").val();
		
		$("#top").animate({top:"8px"},400,"swing",function(){$("#btn_top, #frame").hide();});
		return false;
	});
	
});


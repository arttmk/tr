/*
 * seleced adgroupへの追加処理
 */

function setSelectCampaignID(adg_id, adg_name, adg_media){
	
//	var e = (window.event)? window.event : arguments.callee.caller.arguments[0] ;
//	var self = e.target || e.srcElement;
	//alert(adg_id);
	

//	// この方法で、クリックが押された列のIDが取れる
//	var tbl = document.getElementById("search_adgroup_table");
//	//alert(tbl);
//	var value = tbl.rows[1].cells[0].innerText; // 1列目のIDを取得している
//	//alert(value);

	
	var selected_adg = $('#form_adgroup_list').val();
	
	if(selected_adg != ""){
		
		var selected_adg_list = selected_adg.split(",");
		var exist = false;
		
		// 追加できる限界かどうかチェック
		if(selected_adg_list.length > 7){
			alert("add limit");
			return;
		}
		
		//選択済みに値がある場合、既に選択されているかどうかチェック
		for (var i = 0; i < selected_adg_list.length; i++) {
			if(selected_adg_list[i] == adg_id){
				exist = true;
				break;
			}
		}
		
		if(exist){
			alert("already exist");
			return;
		}
		
	}
	

	
	// 選択済みテーブルへデータ追加
	var tbl = document.getElementById("selected_adgroup_table");
	
	
//	var value = tbl.rows[1].cells[0].innerText;
//	if(value == "No records found."){
//		alert(value);
//		tbl.deleteRow(1);
//		tbl = document.getElementById("selected_adgroup_table");
//	}

	//alert(tbl.rows.length);
	var set_val;
	if (tbl.rows.length % 2 == 0) {
		// 偶数
		set_val = "<tr class='even'>";
	}else{
		// 奇数
		set_val = "<tr class='odd'>";
	}
	
	set_val += "<td>" + adg_id + "</td>";
	set_val += "<td><span class='label-1'>" + adg_media + "</span></td>";
	set_val += "<td>" + adg_name;
	set_val += "<div id='jquery-ui-effect-selected" + adg_id + "' class='ui-widget-content ui-corner-all' style='display: none;'><p>"  + document.getElementById('jquery-ui-effect' + adg_id).innerText + "</p></div>";
	set_val += "</td>";
//	set_val += "<td><input type='button' value='Detail' class='btn-detail' onclick='showDetailSelected(" + adg_id + ");' <='' td=''></td>";
//	set_val += "<td><a href='JavaScript:release_adg(" + adg_id + ")' id='rel_adg'><img border='0' hspace='2' class='link' alt='Relase Adgroup' src='/tracker/img/btn-del.png'></a></td>";
	set_val += "<td><input type='button' value='Detail' class='btn-detail' onclick='showDetailSelected(" + adg_id + ");'>&nbsp;&nbsp;<a href='JavaScript:release_adg("+ adg_id + ")' id='rel_adg'><img border='0' hspace='2' class='link' alt='Relase Adgroup' src='/img/btn-del.png'></a></td>";

	// 最後尾へ追加
	$('#selected_adgroup_table').append(set_val);
	
	// hiddenへも追加
	if(selected_adg == ""){
		$('#form_adgroup_list').val(adg_id);
	}else{
		$('#form_adgroup_list').val(selected_adg + "," + adg_id);
	}
	
	
}


/*
 * selected adgroupテーブル用からのadgroup解除処理
 */
function release_adg(adg_id){
	
	
	// hiddenからも削除
	var selected_adg = $('#form_adgroup_list').val();
	var selected_adg_list = selected_adg.split(",");
	var set_adg = "";

	for (var i = 0; i < selected_adg_list.length; i++) {
		if(selected_adg_list[i] != adg_id){
			if(set_adg == ""){
				set_adg = selected_adg_list[i];
			}else{
				set_adg += "," + selected_adg_list[i];
			}
		}
	}
	//alert(set_adg);
	$('#form_adgroup_list').val(set_adg);
	
	

}

// 行削除用メソッド
$("#rel_adg").live("click", function(){
    $(this).parent().parent().remove();
    
    var tbl = document.getElementById("selected_adgroup_table");
    
    // tableの行数が1（ヘッダーのみになったら）
    if(tbl.rows.length == 1){
    	
    }
    	

});

/**
 * 画面ロード時の処理
 */
$(window).load(function() {
	
	// テーブル検索のno recordsメッセージを表示しない
	var tbl = document.getElementById("selected_adgroup_table");
	var value = tbl.rows[1].cells[0].innerText;
	if(value == "No records found."){
		//alert(value);
		tbl.deleteRow(1);
		//tbl = document.getElementById("selected_adgroup_table");
	}
	
	tbl = document.getElementById("search_adgroup_table");
	value = tbl.rows[1].cells[0].innerText;
	if(value == "No records found."){
		//alert(value);
		tbl.deleteRow(1);
		//tbl = document.getElementById("selected_adgroup_table");
	}
	
	
//	// addボタン制御 → 処理が重くなるので必要なら実装
//	var selected_adg = $('#form_adgroup_list').val();
//	
//	var tbl = document.getElementById("search_adgroup_table");
//	
//	if(selected_adg != ""){
//		
//		var selected_adg_list = selected_adg.split(",");
//		
//		
//		//選択済みに値がある場合、
//		for (var i = 0; i < selected_adg_list.length; i++) {
//
//			for(var j = 1; tbl.rows.length > j; j++){ // 0行目はヘッダーなので、1行目から
//				var adg_id = tbl.rows[j].cells[0].innerText; // id取得
//
//				if(selected_adg_list[i] == adg_id){
//					//alert(document.getElementById("add-" + adg_id));
//					 document.getElementById("add-" + adg_id).disabled = "disabled";
					 // disableなことがわかるように、classの変更も必要
//				}
//				
//			}
//		}
//		
//		
//	}
	

});

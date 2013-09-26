$(document).ready(function() {
	
//    jQuery( '#jquery-ui-show1' ) . button();
//    jQuery( '#jquery-ui-show1' ) . click( function() {
//        jQuery( '#jquery-ui-effect1' ) . toggle( 'blind', '', 1 );
//    } );
	
//	$('#adgChk ').on('change', function (e) {
//		e.preventDefault();
//		e.stopPropagation();
//	 
//		var isCheck = $(this).is(':checked');
//		
//		alert(isCheck);
//	});
	
	// leanModal使用メソッド
	//$('a[rel*=leanModal]').leanModal();
    $( 'a[rel*=leanModal]').leanModal({
        top: 50,                     // モーダルウィンドウの縦位置を指定
        overlay : 0.5,               // 背面の透明度 
        closeButton: ".modal_close"  // 閉じるボタンのCSS classを指定
    });
	
});


/* 画面ロード時のイベント */
$(window).load(function() {
	// form_hid_chk_adgを見て、checkボックスのオン設定を行う
	var checked_adg = $('#form_hid_chk_adg').val();
	
	var checked_adg_list = checked_adg.split(",");
	for (var i = 0; i < checked_adg_list.length; i++) {
		$($('#adgChk'+checked_adg_list[i].split("|")[0])).attr("checked", true);
	}
	
	// Reachの場合のdisplay行の強調表示
	if($("#form_hid_m_c_graph").val() == ""){
		var disp_id = $("#form_hid_click_disp").val().split("|")[0];

		// クリックされた行へクラス設定
		$("#row_"+ disp_id).attr('class',"tr_checked");
		
	}
	
	
	
	// テーブル検索のno recordsメッセージを表示しない
	var tbl = document.getElementById("adgroup_table");
	var value = tbl.rows[1].cells[0].innerText;
	if(value == "No records found."){
		tbl.deleteRow(1);
	}
	
});
/*
 * adgroupチェックボックス処理
 */
function adg_checked(adg_id, adg_name, target_id){
	var isCheck = $($('#adgChk'+adg_id)).is(':checked');
	//alert(isCheck);
	
	var checked_adg = $('#form_hid_chk_adg').val();
	
	// adg_id|adg_nameの形式で格納(id,name,target_id)
	var add_val = adg_id + "|" + adg_name + "|" + target_id;
	
	if(isCheck){
		// checkされた場合
		if(checked_adg == ""){
			checked_adg = add_val;
		}else{
			checked_adg += "," + add_val;
		}
		
	}else{
		// checkが外れた場合
		
		var checked_adg_list = checked_adg.split(",");
		checked_adg = ""; // listに入れた後で、初期化
		for (var i = 0; i < checked_adg_list.length; i++) {
			// checkが外れたものではないadgを格納
			if(checked_adg_list[i].split("|")[0] != adg_id){
				if(checked_adg == ""){
					checked_adg = checked_adg_list[i];
				}else{
					checked_adg += "," + checked_adg_list[i];
				}
			}
		}
		
	}
	
	// hiddenへ再設定
	$('#form_hid_chk_adg').val(checked_adg);
	
}

/*
 * displayボタン押下処理
 */
function do_display(adg_id, adg_name, target_id){
	
	// hiddenにIDを設定(id,name,target_id)
	$('#form_hid_click_disp').val(adg_id + "|" + adg_name + "|" + target_id);
	
	document.getElementById("form_display").click();
	
}

// mergeボタンクリック時の処理
function isClickMerge(){
	// mergeボタン（submit）クリック
	if(checkSelectedAdGroupsCount("m")) document.getElementById("form_merge").click();
	
}

// compareボタンクリック時の処理
function isClickCompare(){
	// compareボタン（submit）クリック
	if(checkSelectedAdGroupsCount("c")) document.getElementById("form_compare").click();
	
}

function do_status(){
    $.confirm.status({
        'title' : 'Processing',
        'message' : 'Please wait for a while...'
    });
}

// jQuery confirm用メソッド↓
function do_alert(){
    $.confirm.alert({
        'title' : 'Completed',
        'message' : 'Transaction has been finished.',
        'color' : '#888888',
        'button_label' : 'Close'
    });
}

function do_confirm(){
    $.confirm({
        'title' : 'Deleting "object"',
        'message' : 'Are you sure?',
        'button_yes_label' : 'Yes',
        'button_no_label' : 'No',
        'processing_title' : 'Processing',
        'processing_message' : 'Please wait for a while...',
        'action' : function(){
            alert('add delete process here');
        }
    });
}
// jQuery confirm用メソッド↑

// 選択された広告グループ判定処理
function checkSelectedAdGroupsCount(kind){
	var execMerge = false;
	
	// チェックされた広告グループが多い場合mergeを行うかどうか確認
	if($('#form_hid_chk_adg').val().split(",").length > 4){
		// jqueryでconfirmするように修正
//		if(confirm("CAUTION : It may take longer time to simulate over 5 ad groups. OK or Cancel")) {
//			execMerge = true;
//		}
		
        $.confirm({
            'title' : 'CAUTION',
            'message' : 'It may take longer time to transact over 5 . OK or Cancel',
            'button_yes_label' : 'OK',
            'button_no_label' : 'Cancel',
            'processing_title' : 'Processing',
            'processing_message' : 'Please wait for a while...',
            'action' : function(){
            	// kindを見てどれをクリックするか判別して実行（ここで戻り値を指定してもタイミング的に返せないので）
            	if(kind == "m") document.getElementById("form_merge").click();
            	if(kind == "c") document.getElementById("form_compare").click();
            	
            }
        });
        
	}else{
		// チェックされた広告グループが規定値より少ない場合、確認なしで実行
		execMerge = true;
	}

	return execMerge;

}


function do_share(){
	var cam_id = $('#form_id').val();
	var cam_pass = $('#form_password').val();
	
	var url = "http://1.rtt-tracker.appspot.com/showGraph.htm?id=" + cam_id + "&mode=2";

	// ダイアログ表示の場合
//    $.confirm.alert({
//        'title' : 'Share',
//        'message' : 'Campaign URL and Password is here. ' + '<BR/><BR/>【URL】' + url + '<BR/>【Password】' + cam_pass,
//        'color' : '#888888',
//        'button_label' : 'Close'
//    });
    
	// leanModalの場合
    $("#go").trigger("click");
    
	
	//alert(cam_id + " " + cam_pass);
}

function light_close(){
	
	$("#").trigger("click");
}


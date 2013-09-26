/**
 * 画面ロード時の処理
 */
$(window).load(function() {
	
	// テーブル検索のno recordsメッセージを表示しない
	var tbl = document.getElementById("campaign_table");
	var value = tbl.rows[1].cells[0].innerText;
	if(value == "No records found."){
		//alert(value);
		tbl.deleteRow(1);
		//tbl = document.getElementById("selected_adgroup_table");
	}

});

//jQuery confirm用メソッド↓
function do_status(){
    $.confirm.status({
        'title' : 'Processing',
        'message' : 'Please wait for a while...'
    });
}

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


function delete_confirm(){

	$.confirm({
	    'title' : 'CAUTION',
	    'message' : 'Are you sure you want to delete this campaign?',
	    'button_yes_label' : 'OK',
	    'button_no_label' : 'Cancel',
	    'processing_title' : 'Processing',
	    'processing_message' : 'Please wait for a while...',
	    'action' : function(){
	    	
	    }
	});

	return true;
}


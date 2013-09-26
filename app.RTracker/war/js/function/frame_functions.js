	//画像切り替え関数
	var cnt=0;
	function show_graph(){
		if(document.getElementById("form_hid_m_c_graph").value == ""){
		
			// アクティブな広告グループ行を特定
			var active_adg = document.getElementById("form_hid_active_adg_row").value;
			list_active = active_adg.split(",");
			// グラフデータを取得
			var graphs = document.getElementById("form_hid_graph").value;
	
			// グラフをリスト化
			graphList = graphs.split(",");
			
			// アクティブな広告グループの行番号を取得
			adg_val = active_adg.split(",");
			row_num = adg_val[0];
			// アクティブな行に対応するグラフデータを取得
			active_graph = graphList[row_num -1];
			// グラフデータをリスト化
			list = active_graph.split("|");
	
//			//画像番号を進める
//			if (cnt == 2){
//				cnt=0;
//			}else{
//				cnt++;
//			}
		
		}else{
			// merge,compareの場合
			var m_c_graphs = document.getElementById("form_hid_m_c_graph").value;
			
			// グラフデータをリスト化
			list = m_c_graphs.split("|");
			
//			//画像番号を進める
//			if (cnt == 1){
//				cnt=0;
//			}else{
//				cnt++;
//			}
		}
		
		//画像番号を進める
		if (cnt == (list.length -1)){
			cnt=0;
		}else{
			cnt++;
		}
		//画像を切り替える
		document.getElementById("chart_img").src = list[cnt];
		var cnt1 = cnt + 1;

		document.getElementById("graph_cnt").textContent = cnt1 + " / " + list.length;
	}

	function ageDisable(){
			if (document.getElementById("form_genderUndefined").checked){
				// gender undefinedがチェックされたら、ageのチェックをはずして入力不可にする。
				document.getElementById("form_age1").checked = false;
				document.getElementById("form_age1").disabled = true;
				document.getElementById("form_age2").checked = false;
				document.getElementById("form_age2").disabled = true;
				document.getElementById("form_age3").checked = false;
				document.getElementById("form_age3").disabled = true;
				document.getElementById("form_age4").checked = false;
				document.getElementById("form_age4").disabled = true;
				document.getElementById("form_age5").checked = false;
				document.getElementById("form_age5").disabled = true;
				document.getElementById("form_age6").checked = false;
				document.getElementById("form_age6").disabled = true;
				document.getElementById("form_ageUndefined").checked = false;
				document.getElementById("form_ageUndefined").disabled = true;
			}else{
				document.getElementById("form_age1").disabled = false;
				document.getElementById("form_age2").disabled = false;
				document.getElementById("form_age3").disabled = false;
				document.getElementById("form_age4").disabled = false;
				document.getElementById("form_age5").disabled = false;
				document.getElementById("form_age6").disabled = false;
				document.getElementById("form_ageUndefined").disabled = false;

			}
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
	
	// jQuery confirm用メソッド↓
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
    
	// 選択された広告グループ判定処理
	function checkSelectedAdGroupsCount(kind){
		var execMerge = false;
		var selectAdGrps = 0;
		if(document.getElementById("form_row_chk1") != null && document.getElementById("form_row_chk1").checked) selectAdGrps += 1;
		if(document.getElementById("form_row_chk2") != null && document.getElementById("form_row_chk2").checked) selectAdGrps += 1;
		if(document.getElementById("form_row_chk3") != null && document.getElementById("form_row_chk3").checked) selectAdGrps += 1;
		if(document.getElementById("form_row_chk4") != null && document.getElementById("form_row_chk4").checked) selectAdGrps += 1;
		if(document.getElementById("form_row_chk5") != null && document.getElementById("form_row_chk5").checked) selectAdGrps += 1;
		if(document.getElementById("form_row_chk6") != null && document.getElementById("form_row_chk6").checked) selectAdGrps += 1;
		if(document.getElementById("form_row_chk7") != null && document.getElementById("form_row_chk7").checked) selectAdGrps += 1;
		if(document.getElementById("form_row_chk8") != null && document.getElementById("form_row_chk8").checked) selectAdGrps += 1;
		if(document.getElementById("form_row_chk9") != null && document.getElementById("form_row_chk9").checked) selectAdGrps += 1;
		if(document.getElementById("form_row_chk10") != null && document.getElementById("form_row_chk10").checked) selectAdGrps += 1;
		
		// チェックされた広告グループが多い場合mergeを行うかどうか確認
		if(selectAdGrps > 4){
			// jqueryでconfirmするように修正
//			if(confirm("CAUTION : It may take longer time to simulate over 5 ad groups. OK or Cancel")) {
//				execMerge = true;
//			}
			
	        $.confirm({
	            'title' : 'CAUTION',
	            'message' : 'It may take longer time to simulate over 5 ad groups. OK or Cancel',
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
	
	/*
	 * 各広告グループのbudgetクリア処理
	 */
	function clear_budget(adg_no){
		$("#form_row_budget" + adg_no).val("");
		$("#form_hid_row_budget" + adg_no).val("");
	
	}
	
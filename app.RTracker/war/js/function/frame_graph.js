$(document).ready(function () {

	// activeな行を取得
	var active_adg;
	active_adg = $("#form_hid_click_disp").val().split("|")[0];

	if($("#form_hid_m_c_graph").val() != ""){
		// merge,compareのhiddenに値があれば
		
		var hide_m_c_val = $("#form_hid_m_c_graph").val();
		if(hide_m_c_val.split("|")[1] == "compare"){
			// compare
			target_reach(2);
		}else{
			// merge
			combined_reach();
		}
	
	}else{
		// reach
		
		if(active_adg != ""){
			target_reach(1);
			
		}
	}

	
});

/*
 * グラフ描画設定処理
 */
function setGraph(){
	if($("#form_hid_m_c_graph").val() != ""){
		// merge,compareのhiddenに値があれば
		
		//compare,mergeの場合、とりあえずグラフが1つなのでグラフ切り替え処理をコメントアウト
//		var hide_m_c_val = $("#form_hid_m_c_graph").val();
//		var mc = hide_m_c_val.split("|")[3];
		
		
//		var index_str = document.getElementById("graph_cnt").textContent;
		
//		if(index_str.indexOf("1 / 1", 0) > -1){
//			if(mc == "compare"){
//				reach_cost_curve(2);
//			}else{
//				reach_cost_curve(3);
//			}
//			
//			document.getElementById("graph_cnt").textContent = "2 / 2";
//		}
//		if(index_str.indexOf("2 / 2", 0) > -1){
//			if(mc == "compare"){
//				target_reach(2);
//			}else{
//				combined_reach();
//			}
//			document.getElementById("graph_cnt").textContent = "1 / 2";
//		}

		
	}else{
		// reach
    	if($("#form_hid_click_disp").val() != ""){
    		
    		var index_str = document.getElementById("graph-cnt").innerHTML;
    		if(index_str.indexOf("1 / 2", 0) > -1){
    			target_content_reach();
    			document.getElementById("graph-cnt").innerHTML = "2 / 2";
    		}
    		if(index_str.indexOf("2 / 2", 0) > -1){
    			target_reach(1);
    			document.getElementById("graph-cnt").innerHTML = "1 / 2";
    		}

    		
    	}
	}
	
	// ここでさらにrectへイベントを設定してしないと、グラフ描画後にイベントがなくなるので設定
	var elements = document.getElementsByTagName("rect");
	jQuery( elements ) . click( function() {
		
		setGraph();

    } );
}


jQuery( function() {
	// rectタグのクリックへグラフ描画イベントを設定
	var elements = document.getElementsByTagName("rect");
	jQuery( elements ) . click( function() {
		
		setGraph();

    });
	
	
} );


/*
 * グラフデータ取得処理
 * @param kind 1(target),2(content_rate)
 */
function getGraphData(kind){
	// activeなIDを取得
	var active_id = $("#form_hid_click_disp").val().split("|")[0];

	// 設定されているグラフデータを取得	
	var graph_data;
	graph_data = $("#form_hid_graph").val();
	graph_data_list = graph_data.split("^");
	
	var show_graph_data = "";
	for (var i = 0; i < graph_data_list.length; i++) {
		var adg_id = graph_data_list[i].split("|")[0];
		
		if(active_id == adg_id){
			show_graph_data = graph_data_list[i];
			break;
		}
		
	}
	
	var show_graph_data_list = show_graph_data.split("|");
	
	if(kind == 1) return show_graph_data_list[0] + "/" + show_graph_data_list[1]; 
	if(kind == 2) return show_graph_data_list[0] + "/" + show_graph_data_list[2];
	
//	active_row = adg_val[0] -1;
//
//	show_graph_data = graph_data_list[active_row];
//
//	show_graph_data_list = show_graph_data.split("|");
//
//	
//	if(kind == 1) return show_graph_data_list[1];
//	if(kind == 2) return show_graph_data_list[2];
//	if(kind == 3) return show_graph_data_list[3];
	
}

/*
 * targetのライングラフ
 * 
 */




/*
 *  target_reachグラフ
 *  @param mode(1:reach、2:compare)
 */
function target_reach(mode){

	 var target_reach = "";
	 
	 if(mode == 1){
		 // Target reach(reach)
		 target_reach = getGraphData(1);
	 }else{
		 // Target reach(compare)
		 var m_c_val = $("#form_hid_m_c_graph").val();
		 target_reach = m_c_val.split("|")[0];
	 }
	
	 var options1 = {
		        chart: {
		            renderTo: 'graph',
		            type: 'column'
		        },
		        title: {
		            text: 'Graph01: Target Reach'
		        },
		        xAxis: {
		            categories: []
		        },
		        yAxis: {
		        	max: 100,
		            min: 0,
//		            minRange: 0.1,
//		            minPadding: 0.1,
		            title: {
		                text: 'Reach (%)'
		            }
		        },
		        legend: {
		            layout: 'vertical',
		            backgroundColor: '#FFFFFF',
		            align: 'left',
		            verticalAlign: 'top',
		            x: 50,
		            y: 100,
		            floating: true,
		            shadow: true
		        },
		        tooltip: {
		            formatter: function() {
		            	var reach_data;
	                    //this.x +': '+ this.y +' %';
	                	if(this.y == "0.893"){
	                		// ダミー値の場合、元の値である0を設定
	                		reach_data = "0";
	                	}else{
	                		reach_data = this.y;
	                	} 
		                return reach_data +
		                    //this.x +': '+ this.y +' %';
		                '%';
//		            	return "" + 
//		                this.y  +'%';
		            }
		        },
		        plotOptions: {
		            line: {
		                dataLabels: {
		                    enabled: true
		                },
		                enableMouseTracking: false
		            }
		        },
		        series: []
		        
		    };
	 
	 // ターゲットリーチのリストを読み込み(広告グループNo/reach_広告グループNo_reachの形式で格納されている)
	 var target_reach_list = target_reach.split("_");
	 options1.xAxis.categories.push('');
	 for (var i = 0; i < target_reach_list.length; i++) {
		 
		 var graph_color = getColorByNo(i);
		 
		 var series = {
                 name: 'ID:' + target_reach_list[i].split("/")[0], // 広告グループNo
                 data: [],
                 color: graph_color
             };
		 
		 var reach;
		 if(target_reach_list[i].split("/")[1] < "1"){
			 // 値が0だと何も表示されないので、ギリギリ表示されるダミー値を指定
			 reach = "0.893";
		 }else{
			 reach = target_reach_list[i].split("/")[1];
		 }
//		 reach = target_reach_list[i];
		 
		 series.data.push(parseFloat(reach));
		 options1.series.push(series);
	 }
	 
	 // グラフ描画
	 var chart1 = new Highcharts.Chart(options1);
}

/*
 *  target_content_reachグラフ
 *  
 */
function target_content_reach(){
		 var target_content_reach = getGraphData(2);
		 var adGrpNo = target_content_reach.split("/")[0];
		 var target_content_reach_list = target_content_reach.split("/")[1].split("_");

    	 chart = new Highcharts.Chart({
             chart: {
                 renderTo: 'graph',
                 plotBackgroundColor: null,
                 plotBorderWidth: null,
                 plotShadow: false
             },
             title: {
            	 text: 'Graph2: Target Content Reach'
             },
             tooltip: {
         	    //pointFormat: '{series.name}: <b>{point.percentage}%</b>',
             	percentageDecimals: 1,
                formatter: function() {
                	return '<b>'+ this.point.name +'</b>: '+Math.round(this.percentage * 100) / 100 +' %';
                }
             },
             plotOptions: {
                 pie: {
                     allowPointSelect: true,
                     cursor: 'pointer',
                     dataLabels: {
                         enabled: true,
                         color: '#000000',
                         connectorColor: '#000000',
                         formatter: function() {
                        	 //return '<b>'+ this.point.name +'</b>: '+ +this.percentage +' %';
                        	 return '<b>'+ this.point.name +'</b>: '+ Math.round(this.percentage * 100) / 100 +' %'; // roundして小数点第2位まで表示（特定の数値(14など)の場合、桁数がおかしくなるため）
                         }
                     }
                 }
             },
             series: [{
                 type: 'pie',
                 name: 'AdGroup' + adGrpNo,
                 data: [
                        //{name: "Target", y : Number(str), color: '#0F9D58'},
                        {name: "Target", y : Number(target_content_reach_list[0]), color: '#0F9D58'},
                        {name: "Not Target", y : Number(target_content_reach_list[1]), color: '#7bcfa9'}
                      ] 
             
             }]
         });
}
       

/*
 *  reach_cost_curveグラフ
 *  @param mode(1:reach、2:compare、3:merge)
 */
function reach_cost_curve(mode){
	
	var reach_cost_curve = "";
	var title = "";
	if(mode == 1){
		// Reach Cost Curve(reach)
		reach_cost_curve = getGraphData(3);
		
		title = 'Graph03: Reach-Cost Curve';
	}else if(mode == 2 || mode == 3){
		// Reach Cost Curve(compare)
		 var m_c_val = $("#form_hid_m_c_graph").val();
		 reach_cost_curve = m_c_val.split("|")[2];
		
		 title = 'Graph02: Reach-Cost Curve';
	
	}
	 
	 var options = {
			 chart: {
				 	renderTo: 'graph',
	                type: 'spline'
	            },
	            title: {
	                text: title
	            },
	            subtitle: {
	                text: ''
	            },
	            xAxis: {
	                title: {
	                    text: 'Cost'
	                },
	                type: 'line',
	                text: 'cost'
	            },
	            yAxis: {
	                title: {
	                    text: 'Reach (%)'
	                },
	                min: 0
	            },
	            tooltip: {
	                formatter: function() {
	                        return '<b>'+ this.series.name +'</b><br/>'+
	                            'cost:' + addFigure(this.x) +'<br/>reach:'+ this.y;
	                }
	            },
		        series: []
//	            series: [{
//	                     name: 'A',
//	                     data: [
//	            	[55558.1253890249,0.002368573079145],[119868.558202348,0.0048526863084922],[180373.639961629,0.0072790294627383],[241830.05948442,0.00996533795493934],[298339.522636956,0.0119006354708261],[359605.674607045,0.0141825534373195],[417827.5457339,0.0163489312536106],[477191.022176967,0.0186019641825534],[536554.498620035,0.0207394569612941],[589829.413376634,0.0228769497400347],[649763.692477808,0.0251877527440786],[710839.576895195,0.0274985557481225],[774769.474603114,0.0296938186019642],[830327.599992139,0.0319757365684575],[894257.497700058,0.0344887348353553],[954191.776801232,0.0367128827267475],[1011842.84526998,0.0390236857307915],[1071206.32171305,0.0410456383593299],[1129618.46039261,0.0432409012131716],[1192216.48523161,0.0456961294049682],[1254624.24251791,0.0479780473714616],[1312275.31098666,0.0503466204506066],[1374683.06827296,0.0528307336799538],[1430621.72876739,0.0547660311958406],[1494171.09136991,0.0570479491623339],[1551061.08962785,0.0593009820912767],[1606428.94746417,0.0612362796071635],[1665221.62124913,0.0631426920854997],[1727439.11098273,0.0654534950895436],[1783948.57413527,0.0676198729058348],[1843692.58568374,0.0693240901213172],[1904768.47010112,0.0712305025996534],[1963751.41143879,0.0733391103408434],[2015123.65066837,0.0755921432697862],[2074677.39466413,0.0776429809358752],[2134421.40621261,0.0796360485268631],[2195877.8257354,0.0817446562680531],[2251816.48622983,0.0833044482957828],[2314604.77862153,0.085210860774119],[2375490.39548622,0.0869150779896014],[2435614.94214009,0.0887637203928365],[2491173.06752912,0.0904679376083189],[2541213.43388978,0.0919699595609474],[2606475.20446662,0.0942229924898902],[2662984.66761915,0.0959849797804737],[2719684.39832439,0.0975736568457539],[2786278.04177014,0.0993645291738879],[2844309.64534429,0.101097631426921],[2904243.92444547,0.102830733679954],[2962656.06312502,0.10473714615829],[3021639.00446269,0.106787983824379],[3075674.98943009,0.108578856152513],[3135799.53608397,0.110225303292894],[3191738.1965784,0.112045060658579],[3247486.58952013,0.113749277874061],[3302093.37714564,0.115222414789139],[3363359.52911573,0.117331022530329],[3423103.5406642,0.118977469670711],[3488555.57879374,0.120883882149047],[3546016.37970978,0.12264586943963],[3601384.23754611,0.124378971692663],[3661508.78419998,0.126112073945696],[3715354.50161469,0.127498555748122],[3776430.38603208,0.129087232813403],[3832749.58163191,0.130155979202773],[3891161.72031147,0.131889081455806],[3954901.35046668,0.133651068746389],[4011410.81361922,0.13526863084922],[4074579.64111633,0.137088388214905],[4132991.77979589,0.138474870017331],[4193116.32644976,0.140294627383016],[4256665.68905228,0.141941074523397],[4312414.081994,0.143471981513576],[4369684.61535735,0.145291738879261],[4438180.93433012,0.146995956094743],[4500398.42406372,0.148642403235124],[4561854.84358651,0.15],[4618174.03918634,0.151617562102831],[4682103.93689426,0.153061813980358],[4741847.94844273,0.154246100519931],[4801782.22754391,0.15577700751011],[4856198.74761672,0.157596764875794],[4913659.54853277,0.159243212016176],[4973213.29252854,0.161091854419411],[5032005.9663135,0.162420566146736],[5087754.35925523,0.16369150779896],[5149971.84898883,0.165020219526285],[5205339.70682515,0.166406701328712],[5255950.87584392,0.167446562680531],[5314363.01452347,0.169035239745812],[5368208.73193818,0.170103986135182],[5427572.20838125,0.171606008087811],[5486364.88216621,0.173194685153091],[5540781.40223902,0.174696707105719],[5593295.24678481,0.175967648757943],[5653229.52588599,0.177238590410168],[5716778.8884885,0.178769497400347],[5774239.68940455,0.179838243789717],[5833222.63074221,0.180849220103986],[5891825.03697447,0.181860196418255],[5947763.6974689,0.182871172732525],[6011503.32762412,0.183824378971693],[6064017.17216991,0.184690930098209],[6121668.24063865,0.185413056036973],[6173040.47986823,0.186279607163489],[6227456.99994104,0.187666088965916],[6287771.81414762,0.189139225880994],[6348657.43101231,0.190467937608319],[6405927.96437565,0.191912189485846],[6466813.58124034,0.193240901213172],[6526747.86034151,0.19422299248989],[6586491.87188998,0.195696129404968],[6646045.61588575,0.196533795493934],[6697988.65777344,0.197718082033507],[6753356.51560976,0.198844598497978],[6809485.44365689,0.199826689774697],[6871322.39828509,0.201357596764876],[6936013.36620381,0.202541883304448],[6996898.9830685,0.203870595031774],[7051886.30579942,0.204910456383593],[7112771.9226641,0.205950317735413]
//	            	]
//	 			}]
//		        series: [{
//	            name: 'AdGroup1',
//	            data: [7.0, 6.9, 9.5, 14.5, 18.4, 21.5, 25.2]
//		        }]
		        
		    };
	 

		 
		 // reach_cost_cuvreのデータを整形（1/cost1_reach1,cost2_reach2#2/cost1_reach1,cost2_reach2となっている）
		 // 広告グループNo/コスト_リーチが_でペアになっており、カンマ区切りで次のペアがわたってくる
		 // さらに#区切りで別の広告グループのデータが同様にわたってくる
		 var reach_cost_curve_list = reach_cost_curve.split("#");

		 //広告グループの数ループ
		 for (var i = 0; i < reach_cost_curve_list.length; i++) {

			 // 広告グループのデータ 
			 var adGrp = reach_cost_curve_list[i].split("/");

			 var adGrpNo = adGrp[0]; //広告グループNo
			 var adGrpData = adGrp[1]; //広告グループのcost_reach

			 var adGrpDataList = adGrpData.split(","); //広告グループのcost_reachのリスト

			 var graph_color = getColorByNo(i);
			 
			 var series = {
				//name: 'AdGroup' + adGrpNo,
				name: (function() {
					if(mode == 3){
						// mergeの場合、各広告グループの合算なので
						name =  'AdGroups';
					}else{
						name =  'AdGroup' + adGrpNo;
					}
					return name;
	             })(),
	                point: {
	                    events: {
	                        click: function() {
	                        	if(mode != 3){
	                        		//alert(this.series.name);
	                        		// 通常のリーチコストカーブ、compareの場合、クリックでcostをbudgetへ設定
	                        		//alert('value: '+ this.x);
	                        		// アクティブなadgroupの番号を取得
	                        		//var active_adg = $("#form_hid_active_adg_row").val().split(",")[0];
	                        		// 該当する広告グループのbudgetへ値セット
	                        		$("#form_row_budget"+ this.series.name.replace("AdGroup","")).val(addFigure(this.x));
	                        		// java側処理のためhiddenへも格納
	                        		$("#form_hid_row_budget"+ this.series.name.replace("AdGroup","")).val(this.x);
	                        	}
	                        }
	                    }
	                },
			 
			 
				color: graph_color,
                marker: {
                    lineWidth: 0.1,
                    radius: 4
                },
				
				data: (function() {
                 // ここでdataに値を設定
                 var data = [];
                 //data.push({x:0,y:0});
                 // dataに格納できる限界は1000個まで？（1つ目はx:0,y:0なので飛ばして2つめのデータから取得）
                 for (var j = 1; j < adGrpDataList.length; j++) {
                     data.push({
                         x: Number(adGrpDataList[j].split("_")[0]), // cost
                         y: Number(adGrpDataList[j].split("_")[1])  // reach
                     });
                 }
                 return data;
             })()
			 };

			 options.series.push(series);

		 }
		 


	 // x軸のインターバルは、総個数の1/4とする
	 //options.xAxis.tickInterval = Number(j-1) / 4;
	 
	 // グラフ描画
	 var chart = new Highcharts.Chart(options);
	 
}

/*
 *  combined_reachグラフ
 *  
 */
function combined_reach(){

	 var target_reach = "";
	 
	 var m_c_val = $("#form_hid_m_c_graph").val();
	 target_reach = m_c_val.split("|")[0];
	
	 var options3 = {
	        chart: {
	        	renderTo: 'graph',
	            type: 'waterfall'
	        },

	        title: {
	            text: 'Graph01: Combined Reach'
	        },

	        xAxis: {
	            type: 'category'
	        },

	        yAxis: {
	            title: {
	            	 text: 'Reach (%)'
	            }
	        },

	        legend: {
	            enabled: false
	        },

	        tooltip: {
	            formatter: function() {
	            	var reach_data;
                	if(this.y == "0.893"){
                		// ダミー値の場合、元の値である0を設定
                		reach_data = "0";
                	}else{
                		reach_data = this.y;
                	} 
	                return reach_data +
	                '%';
	            }
	        },

	        series: []
	 };

	 
	 // ターゲットリーチのリストを読み込み(広告グループNo/reach_広告グループNo_reachの形式で格納されている)
	 var target_reach_list = target_reach.split("_");
	 var series = {
             data: []
         };
	 
	 for (var i = 0; i < target_reach_list.length; i++) {
		 //options1.xAxis.categories.push('AdGroup');
		 var adGrpNo = target_reach_list[i].split("/")[0];
		 var target_reach = target_reach_list[i].split("/")[1];
		 
		 var graph_color = getColorByNo(i);
		 
		 var reach;
		 if(target_reach < "1"){
			 // 値が0だと何も表示されないので、ギリギリ表示されるダミー値を指定
			 reach = "0.893";
		 }else{
			 reach = target_reach;
		 }
		 
		 var data = {
				 name: 'ID:' + adGrpNo,
				 y:Number(reach),
				 color: graph_color
		 };
		 series.data.push(data);
		 
	 }
	 options3.series.push(series);
	 
	 
// seriesを分けると、1列に全ての要素が固まってしまうため、保留
//	 // ターゲットリーチのリストを読み込み(広告グループNo/reach_広告グループNo_reachの形式で格納されている)
//	 var target_reach_list = target_reach.split("_");
//	 //options1.xAxis.categories.push('');
//	 for (var i = 0; i < target_reach_list.length; i++) {
//		 
//		 var graph_color = getColorByNo(i);
//		 
//		 var series = {
//                 name: target_reach_list[i].split("/")[0], // 広告グループNo
//                 data: [],
//                 color: graph_color
//             };
//		 
//		 var reach;
//		 if(target_reach_list[i].split("/")[1] < "1"){
//			 // 値が0だと何も表示されないので、ギリギリ表示されるダミー値を指定
//			 reach = "0.893";
//		 }else{
//			 reach = target_reach_list[i].split("/")[1];
//		 }
//		 
//		 series.data.push(parseFloat(reach));
//		 options3.series.push(series);
//	 }
//	 
	 
	 // グラフ描画
	 var chart3 = new Highcharts.Chart(options3);

}


/*
 *  番号によるカラー取得処理
 *  
 */

function getColorByNo(num){
	var color = '#0F9D58';
	switch (num){
	  case 0:
		  	color = '#0F9D58';
		  	break;
	  case 1:
		  	color = '#4285F4';
		  	break;
	  case 2:
		  	color = '#DB4437';
		  	break;
	  case 3:
		  	color = '#F4B400';
		  	break;
	  case 4:
		  	color = '#7BCFA9';
		  	break;
	  case 5:
		  	color = '#A0C3FF';
		  	break;
	  case 6:
		  	color = '#ED9D97';
		  	break;
	  case 7:
		  	color = '#FFE168';
		  	break;
			  
	}
	
	return color;
}

    
/**
 * 3桁カンマ区切り処理
 * 
 * @param str
 * @returns
 */
function addFigure(str) {
	var num = new String(str).replace(/,/g, "");
	while(num != (num = num.replace(/^(-?\d+)(\d{3})/, "$1,$2")));
	return num;
}


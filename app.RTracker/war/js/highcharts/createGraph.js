        var chart;
        var chart2;
        var test;
        
        $(document).ready(function () {

        	//pie_chart();
        	//chart();
        	double_charts(1);

        	var to = new Date();
        	var from = new Date(to.getTime() - 1000 * 60 * 60 * 24 * 14);
            /* simple inline calendar */
            $('#date').DatePicker({
//              mode: 'single',
              mode: 'multiple',
              inline: true,
              //date: new Date(),
              current: new Date(to.getFullYear(), to.getMonth() - 2, 1),
              //date: [from, to],
              onChange: function(date2, el2) {
            	  $(el2).val(date2);
            	  //alert($(el2).val());
              }
              });
            
            $('#multi-calendar').DatePicker({
            	  mode: 'multiple',
            	  inline: true,
            	  calendars: 3,
            	  date: [new Date(), new Date() - 172800000, new Date() - 345600000]
            	});
            
            /* Calendar tied to text input */
            $('#inputDate').DatePicker({
            	  mode: 'multiple',
            	  position: 'right',
            	  onBeforeShow: function(el){
            	    if($('#inputDate').val())
            	      $('#inputDate').DatePickerSetDate($('#inputDate').val(), true);
            	  },
            	  onChange: function(date, el) {
            	    //$(el).val((date.getMonth()+1)+'/'+date.getDate()+'/'+date.getFullYear());
            	    $(el).val(date);
            	    if($('#closeOnSelect input').attr('checked')) {
            	      $(el).DatePickerHide();
            	    }
            	  }
            	});
            
            
          });
               
        
        
        jQuery( function() {
            jQuery( '#show1' ) . button();
            jQuery( '#show1' ) . click( function() {
            	//chart();
            	pie_chart();
            } );
        } );
        
//        jQuery( function() {
//            jQuery( '#show2' ) . button();
//            jQuery( '#show2' ) . click( function() {
//            	chart = new Highcharts.Chart({
//                    chart: {
//                        renderTo: 'container',
//                        defaultSeriesType: 'line',
//                        marginRight: 130,
//                        marginBottom: 30
//                    },
//                    title: {
//                        text: '時刻別節電達成率'
//                    },
//                    subtitle: {
//                        text: '2011年XX月YY日'
//                    },
//                    xAxis: {
//                        title: {
//                            text: '時'
//                        }
//                    },
//                    yAxis: {
//                        title: {
//                            text: '節電率(%)'
//                        }
//                    },
//                    tooltip: {
//                        formatter: function () {
//                            return '<b>' + this.series.name + '</b><br/>' +
//    								this.x + '時: ' + this.y + '%';
//                        }
//                    },
//                    legend: {
//                        layout: 'vertical',
//                        align: 'right',
//                        verticalAlign: 'top'
//                    },
//                    series: [{
//                        name: '前年比節電率',
//                        data: [[0, 11.4], [1, 10.8], [2, 9.6], [3, 8.6], [4, 7.5], [5, 5.3], [6, 2.2], [7, 0.2], [8, -3.5], [9, -4.8], [10, -4.1],
//                               [11, -5.0], [12, -2.7], [13, -6.7], [14, -9.5], [15, -9.5], [16, -7.8], [17, -3.2], [18, 1.0], [19, 3.6], [20, 4.2],
//                               [21, 4.0], [22, 0.0], [23, 0.0]]
//                    }]
//                });
//            } );
//        } );
        
        jQuery( function() {
            jQuery( '#show2' ) . button();
            jQuery( '#show2' ) . click( function() {

                chart = new Highcharts.Chart({
                    chart: {
                        renderTo: 'container',
                        type: 'line'
                    },
                    title: {
                        text: 'Graph03: Reach-Cost Curve'
                    },
                    subtitle: {
                        //text: 'Source: WorldClimate.com'
                    },
                    xAxis: {
                        title: {
                            text: 'Impression'
                        },
                        categories: ['1000', '2000', '3000', '4000', '5000', '6000', '7000']
                    },
                    yAxis: {
                        title: {
                            text: 'Percentage (%)'
                        }
 
                    },
                    tooltip: {
                        enabled: false,
                        formatter: function() {
                            return '<b>'+ this.series.name +'</b><br/>'+
                                this.x +': '+ this.y +'%';
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
                    series: [{
                        name: 'AdGroup1',
                        data: [7.0, 6.9, 9.5, 14.5, 18.4, 21.5, 25.2]
                    }, {
                        name: 'AdGroup2',
                        data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0]
                    }, {
                        name: 'AdGroup3',
                        data: [10.9, 14.2, 15.7, 18.5, 11.9, 15.2, 17.0]
                    }
                    
                    ]
                });
            });

            	
            	
        } );
        
        jQuery( function() {
        	var hoge2;
        	hoge2 = $("#hoge2").val();
        	var ss = hoge2.split(",");
        	
        	
            jQuery( '#show2' ) . button();
            jQuery( '#show2' ) . click( function() {

                chart = new Highcharts.Chart({
                    chart: {
                        renderTo: 'container',
                        type: 'line'
                    },
                    title: {
                        text: 'Graph03: Reach-Cost Curve'
                    },
                    subtitle: {
                        //text: 'Source: WorldClimate.com'
                    },
                    xAxis: {
                        title: {
                            text: 'Impression'
                        },
                        categories: ['1000', '2000', '3000', '4000', '5000', '6000', '7000']
                    },
                    yAxis: {
                        title: {
                            text: 'Percentage (%)'
                        }
 
                    },
                    tooltip: {
                        enabled: true,
                        formatter: function() {
                            return '<b>'+ this.series.name +'</b><br/>'+
                                this.x +': '+ this.y +'%';
                        }
                    },
                    plotOptions: {
                        line: {
                        	width: 1,
                            dataLabels: {
                                enabled: true
                            },
                            enableMouseTracking: true
                        }
                    },
                    series: [{
                        name: 'AdGroup1',
                        //data: [7.0, 6.9, 9.5, 14.5, 18.4, 21.5, 25.2]
                        data: [Number(ss[0]), Number(ss[1]), Number(ss[2]), 14.5, 18.4, 21.5, 25.2]
                    }, {
                        name: 'AdGroup2',
                        data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0]
                    }, {
                        name: 'AdGroup3',
                        data: [10.9, 14.2, 15.7, 18.5, 11.9, 15.2, 17.0]
                    }
                    
                    ]
                });
            });

            	
            	
        } );
        
        
        jQuery( function() {
            jQuery( '#show3' ) . button();
            jQuery( '#show3' ) . click( function() {

            	chart = new Highcharts.Chart({
                    chart: {
                        renderTo: 'container',
                        type: 'column'
                    },
                    title: {
                        text: 'Graph01: Target Reach'
                    },
                    xAxis: {
                        categories: [
                            'AdGroup',

                        ]
                    },
                    yAxis: {
                        min: 0,
                        title: {
                            text: 'Percentage (%)'
                        }
                    },
                    legend: {
                        layout: 'vertical',
                        backgroundColor: '#FFFFFF',
                        align: 'left',
                        verticalAlign: 'top',
                        x: 100,
                        y: 70,
                        floating: true,
                        shadow: true
                    },
                    tooltip: {
                        formatter: function() {
                            return ''+
                                this.x +': '+ this.y +' %';
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
                        series: [{
                        name: 'AdGroup1',
                        data: [49.9] ,
                        visible: true
            
                    }
                        /*,{
                        name: 'AdGroup2',
                        data: [49.9] ,
                        visible: false,
                        color: '#996699'
            
                    }*/
                        
                        ]
                });

            });
            	
        } );
        
        
        function chart(){
            chart = new Highcharts.Chart({
                chart: {
                    renderTo: 'container',
                    defaultSeriesType: 'line',
                    marginRight: 130,
                    marginBottom: 30
                },
                title: {
                    text: '時刻別節電達成率'
                },
                subtitle: {
                    text: '2011年XX月YY日'
                },
                xAxis: {
                    title: {
                        text: '時'
                    }
                },
                yAxis: {
                    title: {
                        text: '節電率(%)'
                    }
                },
                tooltip: {
                    formatter: function () {
                        return '<b>' + this.series.name + '</b><br/>' +
								this.x + '時: ' + this.y + '%';
                    }
                },
                legend: {
                    layout: 'vertical',
                    align: 'right',
                    verticalAlign: 'top'
                },
                series: [{
                    name: '前年比節電率',
                    data: [[0, 11.4], [1, 10.8], [2, 9.6], [3, 8.6], [4, 7.5], [5, 5.3], [6, 2.2], [7, 0.2], [8, -3.5], [9, -4.8], [10, -4.1],
                           [11, -5.0], [12, -2.7], [13, -6.7], [14, -9.5], [15, -9.5], [16, -7.8], [17, -3.2], [18, 1.0], [19, 3.6], [20, 4.2],
                           [21, 4.0], [22, 0.0], [23, 0.0]]
                }]
            });
        	
        	
        }
        
        function pie_chart(){
        	var str;
        	str = $("#hoge").val();
        	 chart = new Highcharts.Chart({
                 chart: {
                     renderTo: 'container',
                     plotBackgroundColor: null,
                     plotBorderWidth: null,
                     plotShadow: false
                 },
                 title: {
                     //text: 'Browser market shares at a specific website, 2010'
                	 text: 'Graph2: Target Content Reach'
                 },
                 tooltip: {
             	    pointFormat: '{series.name}: <b>{point.percentage}%</b>',
                 	percentageDecimals: 1
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
                            	 return '<b>'+ this.point.name +'</b>: '+ +Math.round(this.percentage) +' %'; // roundしてみた
                             }
                         }
                     }
                 },
                 series: [{
                     type: 'pie',
                     name: 'Target share',
//                     data: [
//                         ['Not Target',   45.0,],
//                         {
//                             name: 'Target',
//                             y: 55,
//                             sliced: true,
//                             selected: true,
//                             color: '#0F9D58'
//                         }
// 
//                     ]
                     data: [
                            {name: "Target", y : Number(str), color: '#0F9D58'},
                            {name: "Not Target", y : 45, color: '#7bcfa9'}
                          ] 
                 
                 }]
             });
        }

        
//        $(document).ready(function() {
//        	jQuery( '#showCalVal' ) . click( function() {
//        		var val = "test";
//        		val = $('#multi-calendar').val();
//        		alert(val);
//
//	        });
//	    	
//	    } );

        function showCal(){
        	//alert(document.getElementById('date').innerHTML);
        	var elements = document.getElementsByClassName("datepickerSelected");

       	    alert("Get:" + elements.length); 
//       	    elements.each( 
//       	        function (item) { 
//       	            alert(item.id); 
//       	        } 
//       	    ); 
	       	 for (var i=0 ; i < elements.length ; i++){
	       		alert(elements[i].innerHTML);
	       	}

        }
        
        
        jQuery( function() {
            jQuery( '#show4' ) . button();
            jQuery( '#show4' ) . click( function() {
            	//chart();
            	combined_reach();
            } );
        } );
        
        /*
         *  combined_reachグラフ
         *  
         */
        function combined_reach(){

    	    $('#container').highcharts({
    	        chart: {
    	            type: 'waterfall'
    	        },

    	        title: {
    	            text: 'Highcharts Waterfall'
    	        },

    	        xAxis: {
    	            type: 'category'
    	        },

    	        yAxis: {
    	            title: {
    	                text: 'USD'
    	            }
    	        },

    	        legend: {
    	            enabled: false
    	        },

    	        tooltip: {
    	            pointFormat: '<b>${point.y:,.2f}</b> USD'
    	        },

    	        series: [{
    	            upColor: Highcharts.getOptions().colors[2],
    	            color: Highcharts.getOptions().colors[3],
    	            data: [{
    	                name: 'Start',
    	                y: 120000
    	            }, {
    	                name: 'Product Revenue',
    	                y: 569000
    	            }, {
    	                name: 'Service Revenue',
    	                y: 231000
    	            }, {
    	                name: 'Positive Balance',
    	                isIntermediateSum: true,
    	                color: Highcharts.getOptions().colors[1]
    	            }, {
    	                name: 'Fixed Costs',
    	                y: -342000
    	            }, {
    	                name: 'Variable Costs',
    	                y: -233000
    	            }, {
    	                name: 'Balance',
    	                isSum: true,
    	                color: Highcharts.getOptions().colors[1]
    	            }],
    	            dataLabels: {
    	                enabled: true,
    	                formatter: function () {
    	                    return Highcharts.numberFormat(this.y / 1000, 0, ',') + 'k';
    	                },
    	                style: {
    	                    color: '#FFFFFF',
    	                    fontWeight: 'bold'
    	                }
    	            },
    	            pointPadding: 0
    	        }]
    	    });
        }
    	    
            
            jQuery( function() {
                jQuery( '#show5' ) . button();
                jQuery( '#show5' ) . click( function() {
                	//chart();
                	double_charts(1);
                } );
            } );

                
            function double_charts(mode){
                // make the container smaller and add a second container for the master chart

//                var $container = $('#container')
//                    .css('position', 'relative');
//            
//                var $detailContainer = $('<div id="detail-container">')
//                    .appendTo($container);
//            
//                var $masterContainer = $('<div id="master-container">')
//                    .css({ position: 'absolute', top: 300, height: 80, width: '100%' })
//                    .appendTo($container);
//                    // create master and in its callback, create the detail chart
//                    //createMaster();
//               	 var options = {
//            			 chart: {
//	                             reflow: false,
//	                             borderWidth: 0,
//	                             backgroundColor: null,
//	                             marginLeft: 50,
//	                             marginRight: 20,
//	                             zoomType: 'x',
//            				 	renderTo: 'master-container',
//            	                type: 'spline'
//            	            },
//            	            title: {
//            	                text: 'double'
//            	            },
//            	            subtitle: {
//            	                text: ''
//            	            },
//            	            xAxis: {
//            	                title: {
//            	                    text: 'Cost'
//            	                },
//            	                type: 'line',
//            	                text: 'cost'
//            	            },
//            	            yAxis: {
//            	                title: {
//            	                    text: 'Reach (%)'
//            	                },
//            	                min: 0
//            	            },
//            	            tooltip: {
//            	                formatter: function() {
//            	                        return '<b>'+ this.series.name +'</b><br/>'+
//            	                            'cost:' + this.x +'<br/>reach:'+ this.y;
//            	                }
//            	            },
//            		        //series: []
//            	            series: [{
//            	                     name: 'A',
//            	                     data: [
//            	            	[55558.1253890249,0.002368573079145],[119868.558202348,0.0048526863084922],[180373.639961629,0.0072790294627383],[241830.05948442,0.00996533795493934],[298339.522636956,0.0119006354708261],[359605.674607045,0.0141825534373195],[417827.5457339,0.0163489312536106],[477191.022176967,0.0186019641825534],[536554.498620035,0.0207394569612941],[589829.413376634,0.0228769497400347],[649763.692477808,0.0251877527440786],[710839.576895195,0.0274985557481225],[774769.474603114,0.0296938186019642],[830327.599992139,0.0319757365684575],[894257.497700058,0.0344887348353553],[954191.776801232,0.0367128827267475],[1011842.84526998,0.0390236857307915],[1071206.32171305,0.0410456383593299],[1129618.46039261,0.0432409012131716],[1192216.48523161,0.0456961294049682],[1254624.24251791,0.0479780473714616],[1312275.31098666,0.0503466204506066],[1374683.06827296,0.0528307336799538],[1430621.72876739,0.0547660311958406],[1494171.09136991,0.0570479491623339],[1551061.08962785,0.0593009820912767],[1606428.94746417,0.0612362796071635],[1665221.62124913,0.0631426920854997],[1727439.11098273,0.0654534950895436],[1783948.57413527,0.0676198729058348],[1843692.58568374,0.0693240901213172],[1904768.47010112,0.0712305025996534],[1963751.41143879,0.0733391103408434],[2015123.65066837,0.0755921432697862],[2074677.39466413,0.0776429809358752],[2134421.40621261,0.0796360485268631],[2195877.8257354,0.0817446562680531],[2251816.48622983,0.0833044482957828],[2314604.77862153,0.085210860774119],[2375490.39548622,0.0869150779896014],[2435614.94214009,0.0887637203928365],[2491173.06752912,0.0904679376083189],[2541213.43388978,0.0919699595609474],[2606475.20446662,0.0942229924898902],[2662984.66761915,0.0959849797804737],[2719684.39832439,0.0975736568457539],[2786278.04177014,0.0993645291738879],[2844309.64534429,0.101097631426921],[2904243.92444547,0.102830733679954],[2962656.06312502,0.10473714615829],[3021639.00446269,0.106787983824379],[3075674.98943009,0.108578856152513],[3135799.53608397,0.110225303292894],[3191738.1965784,0.112045060658579],[3247486.58952013,0.113749277874061],[3302093.37714564,0.115222414789139],[3363359.52911573,0.117331022530329],[3423103.5406642,0.118977469670711],[3488555.57879374,0.120883882149047],[3546016.37970978,0.12264586943963],[3601384.23754611,0.124378971692663],[3661508.78419998,0.126112073945696],[3715354.50161469,0.127498555748122],[3776430.38603208,0.129087232813403],[3832749.58163191,0.130155979202773],[3891161.72031147,0.131889081455806],[3954901.35046668,0.133651068746389],[4011410.81361922,0.13526863084922],[4074579.64111633,0.137088388214905],[4132991.77979589,0.138474870017331],[4193116.32644976,0.140294627383016],[4256665.68905228,0.141941074523397],[4312414.081994,0.143471981513576],[4369684.61535735,0.145291738879261],[4438180.93433012,0.146995956094743],[4500398.42406372,0.148642403235124],[4561854.84358651,0.15],[4618174.03918634,0.151617562102831],[4682103.93689426,0.153061813980358],[4741847.94844273,0.154246100519931],[4801782.22754391,0.15577700751011],[4856198.74761672,0.157596764875794],[4913659.54853277,0.159243212016176],[4973213.29252854,0.161091854419411],[5032005.9663135,0.162420566146736],[5087754.35925523,0.16369150779896],[5149971.84898883,0.165020219526285],[5205339.70682515,0.166406701328712],[5255950.87584392,0.167446562680531],[5314363.01452347,0.169035239745812],[5368208.73193818,0.170103986135182],[5427572.20838125,0.171606008087811],[5486364.88216621,0.173194685153091],[5540781.40223902,0.174696707105719],[5593295.24678481,0.175967648757943],[5653229.52588599,0.177238590410168],[5716778.8884885,0.178769497400347],[5774239.68940455,0.179838243789717],[5833222.63074221,0.180849220103986],[5891825.03697447,0.181860196418255],[5947763.6974689,0.182871172732525],[6011503.32762412,0.183824378971693],[6064017.17216991,0.184690930098209],[6121668.24063865,0.185413056036973],[6173040.47986823,0.186279607163489],[6227456.99994104,0.187666088965916],[6287771.81414762,0.189139225880994],[6348657.43101231,0.190467937608319],[6405927.96437565,0.191912189485846],[6466813.58124034,0.193240901213172],[6526747.86034151,0.19422299248989],[6586491.87188998,0.195696129404968],[6646045.61588575,0.196533795493934],[6697988.65777344,0.197718082033507],[6753356.51560976,0.198844598497978],[6809485.44365689,0.199826689774697],[6871322.39828509,0.201357596764876],[6936013.36620381,0.202541883304448],[6996898.9830685,0.203870595031774],[7051886.30579942,0.204910456383593],[7112771.9226641,0.205950317735413]
//            	            	]
//            	 			}]
//
//            		        
//            		    };
//            	 var chart = new Highcharts.Chart(options);
            	 
            	 var masterChart,
                 detailChart;
             
             
                 // create the master chart
                 function createMaster() {
                     masterChart = $('#master-container').highcharts({
                         chart: {
                             reflow: false,
                             borderWidth: 0,
                             backgroundColor: null,
                             marginLeft: 50,
                             marginRight: 20,
                             zoomType: 'x',
                             events: {
             
                                 // listen to the selection event on the master chart to update the
                                 // extremes of the detail chart
                                 selection: function(event) {
                                     var extremesObject = event.xAxis[0],
                                         min = extremesObject.min,
                                         max = extremesObject.max,
                                         detailData = [],
                                         xAxis = this.xAxis[0];
             
                                     // reverse engineer the last part of the data
                                     jQuery.each(this.series[0].data, function(i, point) {
                                         if (point.x > min && point.x < max) {
                                             detailData.push({
                                                 x: point.x,
                                                 y: point.y
                                             });
                                         }
                                     });
                                     
                                     var detailData1 = [];
                                     jQuery.each(this.series[1].data, function(i, point) {
                                         if (point.x > min && point.x < max) {
                                             detailData1.push({
                                                 x: point.x,
                                                 y: point.y
                                             });
                                         }
                                     });
                                     
                                     // move the plot bands to reflect the new detail span
                                     xAxis.removePlotBand('mask-before');
                                     xAxis.addPlotBand({
                                         id: 'mask-before',
                                         from: 0,
                                         to: min,
                                         color: 'rgba(0, 0, 0, 0.2)'
                                     });
             
                                     xAxis.removePlotBand('mask-after');
                                     xAxis.addPlotBand({
                                         id: 'mask-after',
                                         from: max,
                                         to: 1000,
                                         color: 'rgba(0, 0, 0, 0.2)'
                                     });
             
             
                                     detailChart.series[0].setData(detailData);
                                     detailChart.series[1].setData(detailData1);
             
                                     return false;
                                 }
                             }
                         },
                         title: {
                             text: null
                         },
                         xAxis: {
                        	 //type: 'line',
                             type: 'spline',
                             showLastTickLabel: true,
                             //maxZoom: 14 * 24 * 3600000, // fourteen days
                              //axZoom: 1, 
                             plotBands: [{
                                 id: 'mask-before',
                                 from: 0,
                                 to: 1000,
                                 color: 'rgba(0, 0, 0, 0.2)'
                             }],
                             title: {
                                 text: null
                             }
                         },
                         yAxis: {
                             gridLineWidth: 0,
                             labels: {
                                 enabled: false
                             },
                             title: {
                                 text: null
                             },
                             min: 0.6,
                             showFirstLabel: false
                         },
                         tooltip: {
                             formatter: function() {
                                 return false;
                             }
                         },
                         legend: {
                             enabled: false
                         },
                         credits: {
                             enabled: false
                         },
                         plotOptions: {
                             series: {
                                 fillColor: {
                                     linearGradient: [0, 0, 0, 70],
                                     stops: [
                                         [0, '#4572A7'],
                                         [1, 'rgba(0,0,0,0)']
                                     ]
                                 },
                                 lineWidth: 1,
                                 marker: {
                                     enabled: false
                                 },
                                 shadow: false,
                                 states: {
                                     hover: {
                                         lineWidth: 1
                                     }
                                 },
                                 enableMouseTracking: false
                             }
                         },
             
                         series: [{
                        	 //type: 'area',
                             type: 'spline',
                             name: 'adg1',
                             //pointInterval: 24 * 3600 * 1000,
                             pointStart: 0,
                     	                     data: [
                     	            	[0,0],[10,10],[20,20],[30,30],[40,30],[50,30],[60,30],[70,70],[80,70],[90,70],[100,80]
                     	            	]
                         },
                         {
                             type: 'spline',
                             name: 'adg2',
                             //pointInterval: 24 * 3600 * 1000,
                             pointStart: 0,
                     	                     data: [
                     	            	[0,0],[10,15],[20,15],[30,15],[40,35],[50,35],[60,20],[70,25],[80,25],[90,25],[100,50]
                     	            	]
                         }
                         
                         ],
                         exporting: {
                             enabled: false
                         }
             
                     }, function(masterChart) {
                         createDetail(masterChart);
                     })
                     .highcharts(); // return chart instance
                 }
             
                 // create the detail chart
                 function createDetail(masterChart) {
             
                     // prepare the detail chart
                     var detailData = [],
                         detailStart = 0;
             
                     jQuery.each(masterChart.series[0].data, function(i, point) {
                         if (point.x >= detailStart) {
                        	 //detailData.push(point.x);
                             //detailData.push(point.y);
                        	 detailData.push({
                                 x: point.x, // cost
                                 y: point.y  // reach
                             });
                         }
                     });
             
                     var detailData1 = [],
                     detailStart1 = 0;
         
	                 jQuery.each(masterChart.series[1].data, function(i, point) {
	                     if (point.x >= detailStart1) {
	                    	 //detailData1.push(point.x);
	                         //detailData1.push(point.y);
	                    	 detailData1.push({
                                 x: point.x, // cost
                                 y: point.y  // reach
                             });
	                     }
	                 });
                 
                     // create a detail chart referenced by a global variable
                     detailChart = $('#detail-container').highcharts({
                         chart: {
                             marginBottom: 120,
                             reflow: false,
                             marginLeft: 50,
                             marginRight: 20,
                             style: {
                                 position: 'absolute'
                             }
                         },
                         credits: {
                             enabled: false
                         },
                         title: {
                             text: 'Reach Cost Curve OverLook'
                         },
                         subtitle: {
                             text: 'Select an area by dragging across the lower chart'
                         },
                         xAxis: {
                             type: 'line'
                         },
                         yAxis: {
                             title: {
                                 text: null
                             },
                             maxZoom: 0.1
                         },
                         tooltip: {
//                             formatter: function() {
//                                 var point = this.points[0];
//                                 return '<b>'+ point.series.name +'</b><br/>'+
//                                     'cost:' + this.x + ':<br/>'+
//                                     'reach:' + point.y;
//                             },
                             //shared: true // 各seriesのものがshareされるので
                             shared: false
                             
                         },
                         legend: {
                        	 //enabled: false
                        	 // detailの方のみlegend表示
                             enabled: true,
                             align: 'right',
                             verticalAlign: 'top'
                         },
                         plotOptions: {
                             series: {
                                 marker: {
                                     enabled: true,
                                     states: {
                                         hover: {
                                             enabled: true,
                                             radius: 3
                                         }
                                     }
                                 }
                             }
                         },
                         series: [{
                             name: 'adg1',
                             pointStart: detailStart,
                             //pointInterval: 24 * 3600 * 1000,
                             data: detailData
                         },
                         {
                             name: 'adg2',
                             pointStart: detailStart1,
                             data: detailData1
                         }
                         ],
             
                         exporting: {
                             enabled: false
                         }
             
                     }).highcharts(); // return chart
                 }
             
                 // make the container smaller and add a second container for the master chart
                 $('#container').text(""); // containerの初期化
                 var $container = $('#container')
                     .css('position', 'relative');
             
                 var $detailContainer = $('<div id="detail-container">')
                     .appendTo($container);
             
                 var $masterContainer = $('<div id="master-container">')
                     .css({ position: 'absolute', top: 300, height: 80, width: '100%' })
                     .appendTo($container);
             
                 // create master and in its callback, create the detail chart
                 createMaster();


                 
            }



        

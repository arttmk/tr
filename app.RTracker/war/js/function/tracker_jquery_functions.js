$(document).ready(function() {
	
	$("#form_start_date").attr('tabindex', "-1");
	$("#form_end_date").attr('tabindex', "-1");

	
	// start_date calendar
	$('#form_start_date').DatePicker({
	  mode: 'single',
	  position: 'right',
	  onBeforeShow: function(el){
	    if($('#form_start_date').val())
	      $('#form_start_date').DatePickerSetDate($('#form_start_date').val(), true);

	  },
	  onChange: function(date, el) {
	   // $(el).val((date.getMonth()+1)+'/'+date.getDate()+'/'+date.getFullYear());
		  // 0埋めする
		  var select_month = date.getMonth()+1;
		  var month = ("0"+select_month).slice(-2); 
		  var day = ("0"+date.getDate()).slice(-2); 
		  
//		  alert(month);
//		  alert(month.length);
//		  if(month.length == 1) {
//			  month = "0" + month;
//		  }
//		  if(day.length == 1) {
//			  day = "0" + day;
//		  }
		  //$(el).val(date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate());
		  $(el).val(date.getFullYear() + '-' + month + '-' + day);
		  
		  $(el).DatePickerHide();
//	    if($('#closeOnSelect input').attr('checked')) {
//	      $(el).DatePickerHide();
//	    }
	  }
	});
	
	// end_date calendar
	$('#form_end_date').DatePicker({
		  mode: 'single',
		  position: 'right',
		  onBeforeShow: function(el){
		    if($('#form_end_date').val())
		      $('#form_end_date').DatePickerSetDate($('#form_end_date').val(), true);

		  },
		  onChange: function(date, el) {
			  var select_month = date.getMonth()+1;
			  var month = ("0"+select_month).slice(-2); 
			  var day = ("0"+date.getDate()).slice(-2); 


			  $(el).val(date.getFullYear() + '-' + month + '-' + day);
			  $(el).DatePickerHide();
		  }
	});
	
	
//	$('#startDate').DatePicker({
//		mode: 'multiple',
//		inline: true,
//		starts: 1,
//		//date: new Date(),
//		onChange: function(date, el) {
//		$(el).val(date);
//		
//		//var date_list = date.toString().split(" ");
//		// hiddenに選択された日付を格納
//		//$("#form_hid_select_days").val(date);
//		
//		}
//	});
	
});



/*
 * search adgroupテーブル用detail表示処理
 */
function showDetail(adg_id){
	//jQuery( '#jquery-ui-effect1' ) . toggle( 'blind', '', 1 );
	jQuery( '#jquery-ui-effect' + adg_id ) . toggle( 'blind', '', 1 );
}

/*
 * selected adgroupテーブル用detail表示処理
 */
function showDetailSelected(adg_id){
	jQuery( '#jquery-ui-effect-selected' + adg_id ) . toggle( 'blind', '', 1 );
}


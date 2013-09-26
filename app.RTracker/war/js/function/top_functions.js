/**
 *  新規ウィンドウ起動
 */
function openMain() {

	if(document.getElementById("form_hid_submit").value != ""){
	/* javascriptでのwidow.openでは新規ウィンドウとなり
	   予め画面に設定したボタンクリックでは新規タブとなるため、
	   ボタンを用意してそれをクリックするようにしたが、jsからでは新規ウィンドウで開かれてしまう。
	   (Firefox、IEではどちらの場合も別タブ表示になる)
	   ちなみにフォームのsubmitで新規ウィンドウを開くパターンでも結果同じ
	*/
		
		// windowを開く(chromeで開いた画面が小さくなるようになってしまったので、サイズ指定で開く)
		//window.open('http://localhost:8080/reach_simulator/frame.htm');
		win = window.open("/reach_simulator/frame.htm");
		win.moveTo(0, 0);
		//win.resizeTo(screen.availWidth,screen.availHeight);
		win.resizeTo(1400,1000);
		
		// 画面に用意したボタンを押下して開く
		//document.getElementById('openWindow').click();
		//document.getElementById('openWindowLink').click();
		//document.getElementById('g_serach').click();
		
		// submitで新規ウィンドウを開く
//	    var obj = document.forms["openWindow"];
//	    obj.method = "post";
//	    obj.action = "/reach_simulator/frame.htm";
//	    obj.target = "_blank";
//	    obj.submit();
	    
	}
    
	

	
}
	
/**
 *  jQuery新規ウィンドウ起動
 */
$(function(){  
    $("a[href^='http://']").attr("target","_blank");  
 
}); 

/**
 *  jQuery新規ウィンドウ起動（reach_simulatorパス用）
 */
$(function(){  
    $("a[href^='/reach_simulator/frame.htm']").attr("target","_blank");  
 
}); 
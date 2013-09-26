(function($){
    $.confirm = function(params){
        if($('#confirmOverlay').length){
            $.confirm.hide();
        }
        
        if(!params.action){
            params.action = function(){};
        }
        
        var markup = [
            '<div id="confirmOverlay">',
            '<div id="confirmBox">',
            '<div id="confirmHeader">',params.title,'</div>',
            '<div id="confirmBody">',params.message,'</div>',
            '<div id="confirmButtons">',
            '<button class="button green_button">',params.button_yes_label,'</button>',
            '<button class="button white_button">',params.button_no_label,'</button>',
            '</div></div></div>'
        ].join('');
        
        $(markup).hide().appendTo('body').fadeIn();

        $("#confirmBox .green_button").click(function(){
            params.action();
// ステータス表示はしない
//            $.confirm.status({
//                'title' : params.processing_title,
//                'message' : params.processing_message
//            });
        	$.confirm.hide();
        });

        $("#confirmBox .white_button").click(function(){
            $.confirm.hide();
        });
    }
    
    $.confirm.status = function(params){
        if($('#confirmOverlay').length){
            $.confirm.hide();
        }
    	var markup = [
            '<div id="confirmOverlay">',
            '<div id="confirmBox">',
            '<div id="confirmHeader">',params.title,'</div>',
            '<div id="confirmBody">',params.message,'</div>',
            '<div id="confirmStatus">',
            '&nbsp;',
            '</div></div></div>'
    	].join('');
    	
    	$(markup).hide().appendTo('body').fadeIn();
        $("#confirmStatus").activity();
    }
    
    $.confirm.hide = function(){
    	$('#confirmOverlay').fadeOut(function(){
            $(this).remove();
    	});
    }
    
    $.confirm.alert = function(params){
        if($('#confirmOverlay').length){
            $.confirm.hide();
        }
        
        var markup = [
            '<div id="confirmOverlay">',
            '<div id="confirmBox">',
            '<div id="confirmHeader" style="color:',params.color,';">',params.title,'</div>',
            '<div id="confirmBody" style="color:',params.color,';">',params.message,'</div>',
            '<div id="confirmButtons">',
            '<button class="button white_button">',params.button_label,'</button>',
            '</div></div></div>'
        ].join('');
        
        $(markup).hide().appendTo('body').fadeIn();
        
        $("#confirmBox .button").click(function(){
            if (params.action){
                params.action();
                $.confirm.hide();
                $.confirm.status({
                    'title' : params.processing_title,
                    'message' : params.processing_message
                });
            } else {
                $.confirm.hide();
            }
        });
    }
})(jQuery);
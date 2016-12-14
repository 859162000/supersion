/* 杩欎釜sj鏂囦欢鐨勪綔鐢ㄤ富瑕佹槸
*瀵筍howSaveOrUpdateFramework杩欑被jsp鐨勬寜閽繘琛岀編鍖�
*鍜岄噸鏂拌皟鏁翠綅缃�浣跨敤鏃堕渶瑕佽皟鏁翠竴涓媔d=buttons鐨刣iv涓嬮潰鐨�
*/
function zishiying(){
    var y = $(window).height();
    y -= 160;
    $("#DataDiv").css("height",y);
    		
    var windowWidth = $(window).width()/2;
    var buttonCountWidth = 0;
    var buttonDivNumber = 0;
    $("#buttons").find(":input").each(function(index){
        buttonCountWidth += parseInt($(this).width());
        buttonDivNumber ++;
    });
			
    if(windowWidth > buttonCountWidth){
        		
        //璁＄畻鍑篵uttons杩欎釜div鐨勯暱
        var trueButtonCountWidth = buttonCountWidth + 40*buttonDivNumber
    	$("#buttons").width(trueButtonCountWidth);

        //璁＄畻鍑篵uttons杩欎釜div鐨勭粷瀵逛綅缃�
    	var buttonDivLeft = windowWidth-(buttonCountWidth/2)-(10*buttonDivNumber);
    	var buttonDivTop = parseInt($("#waichengdiv").height())+40
    	$("#buttons").css("position","absolute");
        $("#buttons").css("left",buttonDivLeft);
        $("#buttons").css("top",buttonDivTop);

        //淇グbuttons鐨勬牱寮�
        $("#buttons").children("div").css('margin-left','5px')
    }else if (windowWidth < buttonCountWidth){

        //璁＄畻鍑篵uttons杩欎釜div鐨勯暱
    	$("#buttons").width(windowWidth*2-200);
    				
        //璁＄畻鍑篵uttons杩欎釜div鐨勫
        var buttonsHeight = Math.ceil(buttonCountWidth/windowWidth)
        $("#buttons").height(buttonsHeight*26+15)
        		
    	//璁＄畻鍑篵uttons杩欎釜div鐨勭粷瀵逛綅缃�
    	var buttonsWidth = $("#buttons").width();
    	var buttonDivLeft = windowWidth-buttonsWidth/2;
    	var buttonDivTop = parseInt($("#waichengdiv").height())+30;
        $("#buttons").css("position","absolute");
        $("#buttons").css("left",buttonDivLeft);
        $("#buttons").css("top",buttonDivTop);

        //淇グbuttons鐨勬牱寮�
        $("#buttons").children("div").css('margin-left','5px')//娉ㄦ剰杩欎釜css灞炴�
        if(buttonsHeight > 1){
        	$("#buttons").children("div").css('margin-top','5px')
        }

        var spcielWindowHeight = $(window).height();
        var waichengHeight = $("#waichengdiv").height();
        var buttonsHeight = $("#buttons").height();	
        var buttonsTop = parseInt($("#buttons").css("top"));

        if(spcielWindowHeight<(waichengHeight+(buttonsTop-waichengHeight)+buttonsHeight-3)){
            var cha = new Number((waichengHeight+(buttonsTop-waichengHeight)+buttonsHeight-3)-spcielWindowHeight)
			if(cha<(buttonsTop-waichengHeight)){
				buttonsTop-=cha
				$("#buttons").css("top",buttonsTop);
			}else {
				buttonsTop = waichengHeight
				$("#buttons").css("top",buttonsTop);
				$("#buttons").css({"overflow-y":"auto","overflow-x":"hidden"})
			}
         }
        		
    }else if (windowWidth = buttonCountWidth){
                
	}
}
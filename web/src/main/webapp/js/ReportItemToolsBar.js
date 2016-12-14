 var getCoords = function(el){
                var box = el.getBoundingClientRect(),
                doc = el.ownerDocument,
                body = doc.body,
                html = doc.documentElement,
                clientTop = html.clientTop || body.clientTop || 0,
                clientLeft = html.clientLeft || body.clientLeft || 0,
                top  = box.top  + (self.pageYOffset || html.scrollTop  ||  body.scrollTop ) - clientTop,
                left = box.left + (self.pageXOffset || html.scrollLeft ||  body.scrollLeft) - clientLeft
                return { 'top': top, 'left': left };
            };
            var Drag = function(id){
                var el = document.getElementById(id),
                options  = arguments[1] || {},
                container = options.container || document.documentElement,
                limit = false || options.limit;
                el.style.position = "fixed";
                var drag = function(e) {
                    e = e || window.event;
                    el.style.cursor = "pointer";
                        !+"\v1"? document.selection.empty() : window.getSelection().removeAllRanges();
                    var _left = e.clientX - el.offset_x,
                    _top = e.clientY - el.offset_y;
                    if(limit){
                        var _right = _left + el.offsetWidth,
                        _bottom = _top + el.offsetHeight,
                        _cCoords = getCoords(container),
                        _cLeft = _cCoords.left,
                        _cTop = _cCoords.top,
                        _cRight = _cLeft + container.clientWidth,
                        _cBottom = _cTop + container.clientHeight;
                        if(_left < _cLeft){
                            _left = _cLeft
                        }
                        if(_top < _cTop){
                            _top = _cTop
                        }
                        if(_right > _cRight){
                            _left = _cRight - el.offsetWidth;
                        }
                        if(_bottom > _cBottom){
                            _top = _cBottom - el.offsetHeight;
                        }
                    }
                    el.style.left = _left  + "px";
                    el.style.top = _top  + "px";
                    //el.innerHTML = parseInt(el.style.left,10)+ " x "+parseInt(el.style.top,10);
                }

                var dragend = function(){
                    $("#ToolsBarDiv").css("height","0px");
                    document.onmouseup = null;
                    document.onmousemove = null;
                    
                }

                var dragstart = function(e){
                    e = e || window.event;
                    el.offset_x = e.clientX - el.offsetLeft;
                    el.offset_y = e.clientY - el.offsetTop;
                    document.onmouseup = dragend;
                    document.onmousemove = drag;
                    el.style.zIndex = ++Drag.z;
                    $("#ToolsBarDiv").css("height","100%");
                    return false;
                }
                Drag.z = 999;
                el.onmousedown = dragstart;
            }
function InsertToolsBar()
{
    $("#ReportToolsBar").remove();
    var strInsertHtml = "";
    strInsertHtml+= "<div id='ReportToolsBar' class='ReportToolsBar_Div button-group' onselectstart='return false'>";
    strInsertHtml+= "<div class='button settings icon' onselectstart='return false' >数据计算</div>";
    strInsertHtml+= "<div class='button calendar icon' onselectstart='return false'>数据校验</div>";
    strInsertHtml+= "<div class='button chat icon' onselectstart='return false'>数据保存</div>";
    strInsertHtml+= "<div class='button loop icon' onselectstart='return false'>期数切换</div>";
    strInsertHtml+= "<div class='button reload icon' onselectstart='return false' onclick='location.reload();'>刷新页面</div>";
    strInsertHtml+= "</div>";
    strInsertHtml+= "<div id='ToolsBarDiv' style='width:100%; height:0px;position:absolute;left:0px;top:0px;'></div>";
    $("body").append(strInsertHtml);
   	/*  
	strInsertHtml+= "<div id='ReportToolsBar' class='button-group ReportToolsBar_Div' style='margin-top:2px; margin-left:5px;'>";
    strInsertHtml+= "    <a href='#' class='button primary approve icon' onselectstart='return false'>报表提交</a>";
    strInsertHtml+= "    <a href='#' class='button settings icon' onselectstart='return false'>报表计算</a>";
    strInsertHtml+= "    <a href='#' class='button calendar icon' onselectstart='return false'>报表校验</a>";
    strInsertHtml+= "    <a href='#' class='button chat icon' onselectstart='return false'>数据复制</a>";
    strInsertHtml+= "</div>";
	strInsertHtml+= "<div style='width:100%; height:100%;position:absolute;left:0px;top:0px;'></div>";
    $("body").append(strInsertHtml);*/
}


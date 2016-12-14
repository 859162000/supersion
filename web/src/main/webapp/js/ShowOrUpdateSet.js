
		function showSet(setId, eleName, setName){
			var url = "report/Select-show"+setName+"Set.action";
			$.ajax({
				type:"POST",
	            url:url,
	            datatype: "json",
	            success:function(data){
				
					var result =data;
					var field = new Array();
					var fieldsByte = new Array();
					var strField = "";
					strField +="<select name='"+setName+"' id='"+setName+"' onclick=\"selectSet(this,'"+eleName+"','"+setName+"')\" style=\"float:left; margin-left:5px; width:250px;height:250px;margin-top:4px;margin-bottom: 2%;\""
					strField +=";\" multiple=\"true\">";
					
					if(!(result == null || result == ""||
							typeof(result) == "undefined"||result.indexOf("<html>")>=0)){
						
						fieldsByte = result.split(";");
						var height = (fieldsByte.length)*20;
						
						//strField +="<option>--请选择--</option>";
						
						if(result != null && result != ""){
							for ( var i = 0; i < fieldsByte.length-1; i++) {
									field = fieldsByte[i].split(",");
									strField +="<option value=\"";
									strField +=field[0];
									strField +="\">";
									strField +=field[1];
									strField +="</option>\"";
							}
						}
					}
	
					
					strField +="</select>";
					document.getElementById(setId).innerHTML = strField;

	            	
	            }
			});
			
		}
		
		function selectSet(id,eleName,setName){
			var idValue = null;
			var fieldNames = document.getElementById(setName).options;
			var boo = true;
			for ( var i = 0; i < fieldNames.length; i++) {
				if(fieldNames[i] != null && fieldNames[i].selected == true){
					if(boo == true){
						idValue = fieldNames[i].value;
						boo = false;
					}else{
						idValue = idValue+"-"+fieldNames[i].value;
					}
				}
			}
			if(idValue != null && idValue.value != ""){
				var xmlhttp;
				if(window.XMLHttpRequest){
					xmlhttp = new XMLHttpRequest();
					if(xmlhttp.overrideMimeType){
						xmlhttp.overrideMimeType("text/xml");
					}
				}else if(window.ActiveXObject){
					var activexml = ["MSXML2.XMLHTTP","Microsoft.XMLHTTP"];
					for ( var i = 0; i < activexml.length; i++) {
						try{
							xmlhttp = new ActiveXObject(activexml[i]);
							break;
						}catch(e){
							continue;		
						}
					}	
				}
				var url = null;
				
				if(document.getElementsByName("serviceParam.taskFlow.id").length == 0){
					url = "report/Select-show"+setName+"Set.action?"+setName+"ID="+idValue+"&TaskID="+document.getElementsByName("serviceParam.autoTaskFlow.id")[0].value;
				}else{
					url = "report/Select-show"+setName+"Set.action?"+setName+"ID="+idValue+"&TaskID="+document.getElementsByName("serviceParam.taskFlow.id")[0].value;
				}
				
				xmlhttp.open("POST",url,true);
				xmlhttp.onreadystatechange = function(){
					if(xmlhttp.readyState == 4){
						if(xmlhttp.status == 200){
							var result = xmlhttp.responseText;
							if(result == "" || result == null){
								alert("集合下不存在数据!");
							}else{
								if(setName != null && setName != 'Inst'){
									var obj = document.getElementsByName(eleName)[0].options;
									obj.selectedIndex = -1;
									obj.length = 0;
										
									var code = result.split(";");
									for(var i = 0; i < code.length-1; i++){
										var optionCode = code[i].split(",");
										obj.add(new Option(optionCode[optionCode.length-1],optionCode[0]));
										obj[i].selected = true;
									}
								}else{
									var code = result.split(",");
									var obj = document.getElementsByName(eleName)[0].options;
									obj.selectedIndex=-1;
									for ( var i = 0; i < code.length-1; i++) {
										for ( var j = 0; j < obj.length; j++) {
											if(code[i] == obj[j].value){
												obj[j].selected = true;
											}
										}
									}
								}
							}
						}
					}
				}
				xmlhttp.send();
			}
		}

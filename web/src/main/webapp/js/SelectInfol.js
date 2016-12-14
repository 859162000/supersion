
    function selectInstSet(obj){
    	var instID = "";
		if(obj != null && obj.value != ""){
			instID = obj.value;
		}
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
						var xmlhttp = new ActiveXObject(activexml[i]);
						break;
					}catch(e){
						continue;		
					}
				}	
			}
			var url = "report/Select-showInstSet.action?instID="+instID;
			xmlhttp.open("POST",url,true);
			xmlhttp.onreadystatechange = function(){
				if(xmlhttp.readyState == 4){
					if(xmlhttp.status == 200){
						var result = xmlhttp.responseText;
						if(result == "" || result == null){
							alert("不存机构!");
						}else{
							var code = result.split(",");
							var obj = document.getElementsByName("NbjghAndOrgAndJrxkzh")[0].options;
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
			xmlhttp.send();
		
	}
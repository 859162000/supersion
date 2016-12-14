
/**
 * author:liutao
 * 定义获取数组中最大值的函数
 * @return 数组中的最大值
 */
Array.prototype.max = function(){
	return Math.max.apply({}, this)
}

/**
 * author:liutao
 * 定义获取数组中最小值的函数
 * @return 数组中的最小值
 */
Array.prototype.min = function(){
	return Math.min.apply({}, this)
}

/**
 * author:liutao
 * 获取最大值
 * @return 数组中的最大值或者错误提示信息
 */
function max(){
	var lenth = arguments.length;
	var params = new Array();
	
	for(var i = 0;i < lenth; i++){
		if(!isNaN(arguments[i]) ){
			params.push(arguments[i]);
		}
	}
	if(params.length == 0){
		return 0;
	}else{
		return params.max();
	}
}

/**
 * author:liutao
 * 获取最小值
 * @return 数组中的最小值或者错误提示信息
 */
function min(){
	var lenth = arguments.length;
	var params = new Array();
	
	for(var i = 0;i < lenth; i++){
		if(!isNaN(arguments[i]) ){
			params.push(arguments[i]);
		}
	}
	if(params.length == 0){
		return 0;
	}else{
		return params.min();
	}
}

/**
 * author:liutao
 * 获取数据的绝对值
 * @param param
 * @return 传入参数的绝对值
 */
function abs(param){
	if(isNaN(param)){
		return 0;
	}else{
		return Math.abs(param);
	}
}

/**
 * author:liutao
 * 获取数据按特定位置四舍五入的数据
 * @param number
 * @param e（e小于0时，按特定为对证书四舍五入且不保留小数位；e等于0时，
 *     对第一位小数位进行舍入且不保留小数位；e大于0 时，对特定位置的数进行舍入且保留e位小数位）
 * @return
 */
function round(number, e){

	if(isNaN(number)){
		return 0;
	}else{
		if(isNaN(e)){
			return number;
		}else{
			
			var t=1;
			e = Math.abs(e);
			for(;e>0;t*=10,e--);
			return Math.round(number*t)/t;
		}
	}
	
}

/**
 * author:liutao
 * 求数据平均值
 * @return 返回平均值
 */
function average(){
	var sum = 0;
	var lenth = arguments.length;
	if(lenth == 0){
		return 0;
	}
	for(var i = 0; i < lenth; i++){
		if(isNaN(arguments[i])){
			sum += 0;
		}else{
			sum += arguments[i];
		}
	}
	
	return sum/lenth;
}


function processCompare(cond)
{
	var regexp=/[^><!]+=\w+/;
	var str=new String(cond);
	
	if(str.match(regexp)!=null)
	{
		str=str.replace("=", "==");
	}
	else
	{
		if(str.indexOf("<>")>=0)
		{
			str=str.replace("<>","!=");
		}
		
		
	}
	return str;
}
/**
 * 
 * author:liutao 
 * modify:huanglindong
 * 条件判断，相当于逻辑或运算
 * @return true或者false
 */
function or(){
	var result = new Boolean(false);
	var lenth = arguments.length;
	if(lenth == 0){
		return result;
	}
	
	for(var i = 0; i < lenth; i++){
		var cond=processCompare(arguments[i])
		var b = eval(cond);
		if(b){
			result = true;
			break;
		}
		
	}
	
	return result;
}

/**
 * author:liutao
 * modify:huanglindong
 * 条件判断，相当于逻辑与运算
 * @return true或者false
 */
function and(){
	var result = new Boolean(true);
	var lenth = arguments.length;
	if(lenth == 0){
		return result;
	}
	
	for(var i = 0; i < lenth; i++){
		var cond=processCompare(arguments[i])
		var b=eval(cond)
		if(!b){
			result = false;
			break;
		}
		
	}
	
	return result;
	
}
function getCompareCond(cond)
{
	var exp=/(>|>=|<|<=|=)\w+/;
	var compare=new String(cond);
	if(compare.match(exp)==null)
	{
		compare="=="+str;
	}
	else
	{
		
		if(compare.match("/=\w+/g")!=null)
		{
			compare=compare.replace("=", "==");
		}
		else
		{
			compare=compare.replace("<>", "!=");
		}
	}
	return compare;
}

function sumif()
{
	var result=0;
	var len = arguments.length;
	if(len==1)
	{
		result= arguments[0];
	}
	else if(len>1)
	{
		
		var compare=getCompareCond(arguments[len-1]);
		for(var i = 0; i < len-1; i++){
			
			if(!isNaN(arguments[i])&&eval(arguments[i]+compare)){
				result=result+parseFloat(arguments[i]);
			}
		}
	}
	
	return result;	
}
function countif()
{
	var result=0;
	var len = arguments.length;
	if(len==1)
	{
		result= 1;
	}
	else if(len>1)
	{
		var compare=getCompareCond(arguments[len-1]);
		
		for(var i = 0; i < len-1; i++){
			
			
			if(!isNaN(arguments[i])&&eval(arguments[i]+compare)){
				result=result+1;
			}
		}
	}
	
	return result;	
}
function month(date)
{
	var d=new Date(date);
	return d.getMonth()+1;
}
function count()
{
  return arguments.length;
}
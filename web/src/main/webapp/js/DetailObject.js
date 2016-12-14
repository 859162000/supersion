function DetailObject(pField,pCalc){
	this.field=pField;
	this.calc=pCalc;
	this.data=new Array();
}
DetailObject.prototype.AddData=function(t){
	this.data.push(t);
}
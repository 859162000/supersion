package report.dto;

public enum ItemDataType {
	
	
	Str(1),	//字符型
	Amount(2),	//金额
	Num(3),	//数值
	Date(4),	//日期
	Percent(5),	//百分比
	Seq(6),	//序号
	Ratio(7),	//比例
	
	/*金额和字符串可变类型，当满足某种条件时为金额类型，当满足另一种条件时为字符串类型，比如巴塞尔III的G4D操作风险加权资产情况表
	 * 里的【2. 操作风险资本要求】和【3. 操作风险加权资产】两个指标就属于这种类型，满足一定条件的时候其值就是“未通过校验”这个字符串。
	 * 属于这种类型的指标很少见*/
	Change_Amt_Str(8);	
	
	private int code;
    private ItemDataType(int code)
    {
    	this.code=code;
    }
    
    @Override
    public String toString() {

        return String.valueOf (this .code );

    } 
}

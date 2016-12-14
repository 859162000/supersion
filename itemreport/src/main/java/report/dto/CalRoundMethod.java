package report.dto;

public enum  CalRoundMethod {
	FirstRound(1),
    FirstCal(2);
    
    
    private int code;
    private CalRoundMethod(int code)
    {
    	this.code=code;
    }
    
    @Override
    public String toString() {

        return String.valueOf (this .code );

    } 
}

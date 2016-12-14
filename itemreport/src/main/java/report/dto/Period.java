package report.dto;

/**
 * @author transino2
 *
 */
public enum Period {
	
    /**
     * 本期
     */
    CurPeriod(1),
    /**
     * 上期
     */
    PrePeriod(2),
    /**
     * 去年同期
     */
    LastYearSameTime(3),
    /**
     * 年初
     */
    FirstDayofYear(4),
    /**
     * 年初
     */
    LastYearEnd(5);
    
	private int code;
    private Period(int code)
    {
    	this.code=code;
    }
	@Override
    public String toString() {

        return String.valueOf (this .code );

    } 
	
}

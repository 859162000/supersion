package report.helper;

public class WordAnalyClass {
	private String strCell;
	private String wordValue;
	private int wordType;
	public String getWordValue() {
		return wordValue;
	}
	public void setWordValue(String wordValue) {
		this.wordValue = wordValue;
	}
	public int getWordType() {
		return wordType;
	}
	public void setWordType(int wordType) {
		this.wordType = wordType;
	}
	
	public WordAnalyClass(int pWordType,String pWordValue){
		strCell=pWordValue;
		wordType=pWordType;
		wordValue=pWordValue;
	}
	public void setStrCell(String strCell) {
		this.strCell = strCell;
	}
	public String getStrCell() {
		return strCell;
	}
}

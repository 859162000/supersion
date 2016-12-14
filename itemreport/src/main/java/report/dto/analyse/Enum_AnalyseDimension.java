package report.dto.analyse;

public enum Enum_AnalyseDimension {
	dtDate("01"), itemId("02"), orgId("03"), currId("04"), itemProId("05"), dimension1Type("06"), dimension2Type("07"),_blank("");
	
	private String dimension;

	private Enum_AnalyseDimension(String dimension) {
		this.dimension = dimension;
	}
	
	public String value() {
		return dimension;
	}
	
	public static Enum_AnalyseDimension getAnalyseDimension(String dimension) {
		if (dtDate.value().equals(dimension))
			return dtDate;
		if (itemId.value().equals(dimension))
			return itemId;
		if (orgId.value().equals(dimension))
			return orgId;
		if (currId.value().equals(dimension))
			return currId;
		if (itemProId.value().equals(dimension))
			return itemProId;
		if (dimension1Type.value().equals(dimension))
			return dimension1Type;
		if (dimension2Type.value().equals(dimension))
			return dimension2Type;
		return _blank;
	}
	
	
	public static void main(String args[]){
		System.out.println(getAnalyseDimension("02").name());
	}
	
}

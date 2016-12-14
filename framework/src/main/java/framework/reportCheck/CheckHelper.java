package framework.reportCheck;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CheckHelper {
	
	public static boolean isFail(String compareType,Double left,Double right){
		if(left == null || right == null){
			return false;
		}
		if(compareType.equals("=") && !left.equals(right)){
			return false;
		}
		if(compareType.equals(">=") && left < right){
			return false;
		}
		else if(compareType.equals("<=") && left > right){
			return false;
		}
		else if(compareType.equals(">") && left <= right){
			return false;
		}
		else if(compareType.equals("<") && left >= right){
			return false;
		}
		else if(compareType.equals("!=") && left.equals(right)){
			return false;
		}
		return true;
	}
	
	public static boolean isFail(String compareType,BigDecimal left,BigDecimal right){
		if(left == null || right == null){
			return false;
		}
		if(compareType.equals("=") && left.compareTo(right)!=0){
			return false;
		}
		if(compareType.equals(">=") && left.compareTo(right)==-1){
			return false;
		}
		else if(compareType.equals("<=") && left .compareTo( right)==1){
			return false;
		}
		else if(compareType.equals(">") && left.compareTo( right)!=1){
			return false;
		}
		else if(compareType.equals("<") && left .compareTo( right)!=-1){
			return false;
		}
		else if(compareType.equals("!=") && left.compareTo(right)==0){
			return false;
		}
		return true;
	}
	
	public static boolean isReverseTrue(String compareType,Date left,Date right){
		if(compareType.equals("=") && left.compareTo(right) != 0){
			return false;
		}
		if(compareType.equals(">=") && left.compareTo(right) < 0){
			return false;
		}
		else if(compareType.equals("<=") && left.compareTo(right) > 0){
			return false;
		}
		else if(compareType.equals(">") && left.compareTo(right) <= 0){
			return false;
		}
		else if(compareType.equals("<") && left.compareTo(right) >= 0){
			return false;
		}
		else if(compareType.equals("!=") && left.compareTo(right) == 0){
			return false;
		}
		return true;
	}
	
	public static boolean isConditionTrue(List<CheckFieldCondition> checkFieldConditionList,Map<String,Object> mapObject){
		return true;
	}
}

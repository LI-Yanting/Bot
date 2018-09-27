import java.lang.System;
import java.text.ParseException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NextBatch {	
	public static void main(String[] args) throws ParseException {

//		Calendar calendar = Calendar.getInstance();
//		Calendar now = Calendar.getInstance();
//		calendar.set(Calendar.HOUR, 18);
//		calendar.set(Calendar.MINUTE, 15);
//		System.out.println(now.compareTo(calendar));

		String test = getNextSendingTime("US", "OD");
		System.out.println(test);
	}
	
	public static HashMap<String, HashMap<String, ArrayList<String>>> setTimeTable() {
		HashMap<String, HashMap<String, ArrayList<String>>> timeTable = new HashMap<>();
		
		// PARIS
		HashMap<String, ArrayList<String>> timeTablePA = new HashMap<>();
		timeTablePA.put("STD", new ArrayList<String>(){{
			add("18:15");
		}});  
		timeTablePA.put("COR", new ArrayList<String>(){{
			add("18:15");
		}});   
		timeTablePA.put("OD", new ArrayList<String>(){{
			add("08:10");
			add("12:00");
			add("16:00");
			add("20:00");
		}});   
		
		// EUROPE
		HashMap<String, ArrayList<String>> timeTableEU = new HashMap<>();
		timeTableEU.put("STD", new ArrayList<String>(){{
			add("23:00");
		}});  
		timeTableEU.put("COR", new ArrayList<String>(){{
			add("23:00");
		}});   
		timeTableEU.put("OD", new ArrayList<String>(){{
			add("12:00");
			add("16:00");
			add("23:00");
		}});  
		
		// ASIA
		HashMap<String, ArrayList<String>> timeTableAS = new HashMap<>();
		timeTableAS.put("STD", new ArrayList<String>(){{
			add("18:30");
		}});  
		timeTableAS.put("COR", new ArrayList<String>(){{
			add("18:30");
		}});   
		timeTableAS.put("OD", new ArrayList<String>(){{
			add("08:00");
			add("11:00");
			add("18:30");
		}});  
		
		// USA
		HashMap<String, ArrayList<String>> timeTableUS = new HashMap<>();
		timeTableUS.put("STD", new ArrayList<String>(){{
			add("04:00");
		}});  
		timeTableUS.put("COR", new ArrayList<String>(){{
			add("04:00");
		}});   
		timeTableUS.put("OD", new ArrayList<String>(){{
			add("04:00");
			add("16:00");
			add("18:00");
			add("22:00");
		}}); 
		
		// CANADA
		HashMap<String, ArrayList<String>> timeTableCA = new HashMap<>();
		timeTableCA.put("STD", new ArrayList<String>(){{
			add("04:15");
		}});  
		timeTableCA.put("COR", new ArrayList<String>(){{
			add("04:15");
		}});   
		timeTableCA.put("OD", new ArrayList<String>(){{
			add("04:15");
			add("16:00");
			add("18:00");
			add("22:00");
		}});   
		
		timeTable.put("PA", timeTablePA);
		timeTable.put("EU", timeTableEU);
		timeTable.put("AS", timeTableAS);
		timeTable.put("US", timeTableUS);
		timeTable.put("CA", timeTableCA);
		return timeTable;
	}
	
	public static String getNextSendingTime(String area, String typEcr) {
		// Initialization
		HashMap<String, HashMap<String, ArrayList<String>>> timeTable = setTimeTable();
		System.out.println(timeTable);
		String message = "...";
		boolean nextTimeIsToday = false;
		Calendar now = Calendar.getInstance();
		
//		if(area)
		
		// Get timeList for given area and type écriture
		HashMap<String, ArrayList<String>> typeMap = timeTable.get(area);
		ArrayList<String> timeList = typeMap.get(typEcr);
			
		for(int i = 0; i<timeList.size(); ++i) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeList.get(i).split(":")[0]));
			calendar.set(Calendar.MINUTE, Integer.parseInt(timeList.get(i).split(":")[1]));
			
			System.out.println(calendar.getTime());
			System.out.println(now.getTime());
			System.out.println(now.compareTo(calendar));
			
			if(now.compareTo(calendar) == -1) {
				nextTimeIsToday = true;
				long timeDiff = (calendar.getTime().getTime() - now.getTime().getTime());
				long hourDiff = (timeDiff % (1000*60*60*24)) / (1000*60*60);
				long minDiff = (timeDiff % (1000*60*60)) / (1000*60);
				message = "Next batch will be sent at " + timeList.get(i) 
							+ ", in " + hourDiff + "hr" + minDiff +  "mins";
			}
			if(nextTimeIsToday == false) {
				message = "Next batch will be sent TOMORROW at " + timeList.get(0);
			}
		}
		
		return message;
	}
}

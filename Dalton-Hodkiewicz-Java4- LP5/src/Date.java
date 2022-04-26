import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

/**
 * 
 * @author dhodk
 *@implNote this is lp5 assignment Dates, Strings and Localization
 *{@summary This is the primary Class for running the program and testing out dates}
 */
public class Date {

	
	public Date() {}
	/**
	 * timeOfDay holds the time of day as a String
	 * LanguageSelected does the same
	 */
	private static String timeOfDay;
	
	private static String languageSelected;
	
		public static void main(String[] args) throws IOException {
			
			/**
			 * created a date time object of 11-1-2015 for midnight
			 */
			LocalDateTime myDateObj = LocalDateTime.of(2015, 11, 1, 0, 0);
			System.out.println("Before incrementing: " + myDateObj);
			
			/**
			 * increment 1 hour 5 times with a lambda
			 */
		IntStream.range(0, 5).forEach(i->{
			System.out.println(myDateObj.plusHours(i));
		});
			//NOTE if I select (2015,11,1,24,0) then I get an error, but technically that time wouldn't exist - because then it would be the next day
			
			/*
			 * Locale us = new Locale("en", "US"); Locale fr = new Locale ("fr", "FR");
			 * printProperties(us); printProperties(fr);
			 */
			//find out what timeZone i'm in
			System.out.println("My Timezone is: " + ZoneId.systemDefault());
			
			/*
			 * ZoneId.getAvailableZoneIds().stream() .filter(z-> z.contains("US") ||
			 * z.contains("America")) .sorted().forEach(System.out::println);
			 */

			
			System.out.println();
			
			/**
			 * where the magic happens - sorry for not including a simple reset feature but I hope this is acceptable
			 * basically while checkTime of day is false keep asking for an appropriate time of day as described - returns boolean value
			 * same with checkLang()
			 */
	        while(!checkTimeOfDay()) {}
	        while(!checkLang()) {}
	        
	        /**
	         * if we made it this far then return our simulated time value, take that time value and the language selected
	         * and give a nice little message depending on time of day using resource package
	         */
	        String s = doSomethingWithLocalTimeAndLang(returnSimulatedTime());
	        System.out.println(s);
	          
		}

		/**
		 * 
		 * @author dhodk
		 * @implNote two enums for the time of day and for english and spanish, makes it much easier for input/client validation
		 */
		enum Time{early, morning, noon, afternoon, night}
		
		enum Lang{english, spanish}
		
		/**
		 * 
		 * @return boolean value that says whether or not the user has entered an appropriate time of day
		 * I realize ideally in the real world it would just get the time of day, but I set it up this way for testing purposes
		 * @Sets the static timeOfDay value via setter
		 * @throws IOException - because of the utilization of the Buffered/Stream readers
		 */
		public static boolean checkTimeOfDay() throws IOException {
			System.out.println("Please select a time of day");
			System.out.println("Valid values are...");
			
			
		    for (Time t: Time.values())
		    {
		      System.out.println(t);
		    }
	        // Enter data using BufferReader
	        BufferedReader reader = new BufferedReader(
	            new InputStreamReader(System.in));
	        
	     // Reading data using readLine
	        String time = reader.readLine();
	        
			Time[] allTimes = Time.values();
			for(Time t: allTimes) {
				if (t.toString().equals(time)) {
					setTimeOfDay(time);
					return true;
				}
			}
			System.out.println("Sorry try again, invalid time entered.");
			return false;
		}
		
		/**
		 * Same as the above, uses a buffered reader to take user input, loops through valid languages from enum and
		 * makes sure that the user entered in an appropriate value
		 * @Sets the static languageSelected variable via a setter
		 * @return a boolean value
		 * @throws IOException
		 */
		public static boolean checkLang() throws IOException {
			System.out.println("Please select a language");
			System.out.println("Valid values are...");
			
		    for (Lang l: Lang.values())
		    {
		      System.out.println(l);
		    }
	        // Enter data using BufferReader
	        BufferedReader reader = new BufferedReader(
	            new InputStreamReader(System.in));
	        
	     // Reading data using readLine
	        String lang = reader.readLine();
			Lang[] allTimes = Lang.values();
			for(Lang t: allTimes) {
				if (t.toString().equals(lang)) {
					setLanguageSelected(lang);
					return true;
				}
			}
			System.out.println("Sorry try again, invalid language entered.");
			return false;
		}
		
		/**
		 * returns a String - the time of day
		 * @return
		 */
		public static String getTimeOfDay() {
			return timeOfDay;
		}
		
		/**
		 * sets the time of day, takes a string value
		 * @param time
		 */
		public static void setTimeOfDay(String time) {
			  timeOfDay = time;
		}

		/**
		 * returns the language selected
		 * @return languageSelected
		 */
		public static String getLanguageSelected() {
			return languageSelected;
		}

		/**
		 * sets the language selected (String)
		 * @param language
		 */
		public static void setLanguageSelected(String language) {
					languageSelected = language;
		}
		
		/**
		 * correlating user/client commands to times of day that fall within assignment categories
		 * @return returns a LocalTime object
		 */
		
		public static LocalTime returnSimulatedTime() {
			
			
			if(getTimeOfDay().equals("early")) {
				LocalTime earlyMorningEnd = LocalTime.parse( "04:00:00" );
				return earlyMorningEnd;
			}
			
			if(getTimeOfDay().equals("morning")) {
				LocalTime morningStart = LocalTime.parse( "08:01:00" );
				return morningStart;
			}
			
			if(getTimeOfDay().equals("noon")) {
				LocalTime noonStart = LocalTime.parse( "12:01:00" );
				return noonStart;
			}
			
			if(getTimeOfDay().equals("afternoon")) {
				LocalTime afternoonStart = LocalTime.parse( "14:01:00" );
				return afternoonStart;
			}
			
			if(getTimeOfDay().equals("night")) {
				LocalTime nightStart = LocalTime.parse( "22:00:00" );
				return nightStart;
			}
			return null; // won't get here but it's a requirement
		}
		
		/**
		 * Takes the simulated local time - creates two new Locale's, grabs our ResourceBundles for spanish and english,
		 * sets benchmarks for local time windows and depending on range it returns appropriate String message
		 * @param lt the simulated LocalTime value
		 * @return a String message either english/spanish based upon the passed LocalTime obj
		 */
		public static String doSomethingWithLocalTimeAndLang(LocalTime lt) {
			//create two locale obj's
			Locale us = new Locale("en","US");
			Locale mx = new Locale("es","SP");
			
			String verbiage = ""; // set default verbiage (theoretically would always be replaced with content below)
			
			//get both of our resource bundles here
			ResourceBundle rbEnglish = ResourceBundle.getBundle("Zoo",us);
			ResourceBundle rbSpanish = ResourceBundle.getBundle("Span",mx);
			
			//Early Morning (00:00 - 06:00)
			LocalTime earlyMorningStart = LocalTime.parse( "00:00:00" );
			LocalTime earlyMorningEnd = LocalTime.parse( "06:00:00" );
			//Morning (06:01-11:59
			LocalTime morningStart = LocalTime.parse( "06:01:00" );
			LocalTime morningEnd = LocalTime.parse( "11:59:00" );
		    //Noon (12:00-12:59)
			LocalTime noonStart = LocalTime.parse( "12:00:00" );
			LocalTime noonEnd = LocalTime.parse( "12:59:59" );
			//Afternoon (13:00-20:59)
			LocalTime afternoonStart = LocalTime.parse( "13:00:00" );
			LocalTime afternoonEnd = LocalTime.parse( "20:59:59" );
			//night (21:00-23:59).
			LocalTime nightStart = LocalTime.parse( "21:00:00" );
			LocalTime nightEnd = LocalTime.parse( "23:59:59" );
			
			// these are for between times (in this case between early morning start and early morning end)
			// returns value based upon language selected value
			if(lt.isAfter(earlyMorningStart)  &&  lt.isBefore(earlyMorningEnd)) {
				if(getLanguageSelected().equals("english")) {
					verbiage = rbEnglish.getString("early");
				}
				if(getLanguageSelected().equals("spanish")) {
					verbiage = rbSpanish.getString("early");
				}
			}
			
			if(lt.isAfter(morningStart)  &&  lt.isBefore(morningEnd)) {
				if(getLanguageSelected().equals("english")) {
					verbiage = rbEnglish.getString("morning");
				}
				if(getLanguageSelected().equals("spanish")) {
					verbiage = rbSpanish.getString("morning");
				}
			}
			
			if(lt.isAfter(noonStart)  &&  lt.isBefore(noonEnd)) {
				if(getLanguageSelected().equals("english")) {
					verbiage = rbEnglish.getString("lunch");
				}
				if(getLanguageSelected().equals("spanish")) {
					verbiage = rbSpanish.getString("lunch");
				}
			}
			
			if(lt.isAfter(afternoonStart)  &&  lt.isBefore(afternoonEnd)) {
				if(getLanguageSelected().equals("english")) {
					verbiage = rbEnglish.getString("evening");
				}
				if(getLanguageSelected().equals("spanish")) {
					verbiage = rbSpanish.getString("evening");
				}
			}
			if(lt.isAfter(nightStart)  &&  lt.isBefore(nightEnd)) {
				if(getLanguageSelected().equals("english")) {
					verbiage = rbEnglish.getString("night");
				}
				if(getLanguageSelected().equals("spanish")) {
					verbiage = rbSpanish.getString("night");
				}
			}
			return verbiage; // return the String which in the case of this program, will always be changed
		}	
		
}

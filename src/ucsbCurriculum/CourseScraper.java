package ucsbCurriculum;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class CourseScraper {
	
	// Prints every course department 
	public static void printDepartments() {
		try {
		    String url = "https://my.sa.ucsb.edu/public/curriculum/coursesearch.aspx";
		    Connection.Response ucsbCurriculum = Jsoup.connect(url).method(Connection.Method.GET).execute();
		    Document page = ucsbCurriculum.parse();
		    		    
		    Elements subjectAreas = page.select("#ctl00_pageContent_courseList option");
		   
		    for (Element subject : subjectAreas) {
	    		System.out.println(subject.text());
		    }

		    
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	// courseDepartment: Input the abbreviated subject area of the class you are searching for.
	//				Examples: Computer Science -> "CMPSC", Black Studies -> "BL ST", Music -> "MUS"
	// 				Run printDepartments() to see every department and its abbreviation printed out.
	//
	// quarter: 1 = Winter, 2 = Spring, 3 = Summer, and 4 = Fall
	// 			(based on when the quarter begins and ends in the calendar year)
	//			Formatting is as follows "(year)(quarter#)". For example, "20172" = Spring 2017.
	// 			More examples: "20143" = Summer 2014, "20691" = Winter 2069
	//
	// courseLevel: "Undergraduate", "Graduate", or "All" (only 3 options, and first letter should be capitalized)
	public static void getCourseListFor(String courseDepartment, String quarter, String courseLevel) {
		try {
		    String url = "https://my.sa.ucsb.edu/public/curriculum/coursesearch.aspx";
		    Connection.Response ucsbCurriculum = Jsoup.connect(url).method(Connection.Method.GET).execute();
		    Document page = ucsbCurriculum.parse();
		    
		    Element e = page.select("input[id=__VIEWSTATE]").first();
		    String viewState = e.attr("value");
		    e = page.select("input[id=__VIEWSTATEGENERATOR]").first();
		    String viewStateGenerator= e.attr("value");
		    e = page.select("input[id=__EVENTVALIDATION]").first();
		    String eventValidation = e.attr("value");
		    
		    Document doc = Jsoup.connect(url)
		    		.data("__VIEWSTATE", viewState)
		    		.data("__VIEWSTATEGENERATOR", viewStateGenerator)
		    		.data("__EVENTVALIDATION", eventValidation)
		    		.data("ctl00$pageContent$courseList", courseDepartment.toUpperCase())
		    		.data("ctl00$pageContent$quarterList", quarter)
		    		.data("ctl00$pageContent$dropDownCourseLevels", courseLevel)
		    		.data("ctl00$pageContent$searchButton.x", "21")
		    		.data("ctl00$pageContent$searchButton.y", "13")
		    		.cookies( ucsbCurriculum.cookies() )
		    		.post();
		    
		    Elements courseRow = doc.select("tr.CourseInfoRow");
		    for (Element row : courseRow) {
	    		Elements Professors = row.select("td:nth-child(6)");
	    		Elements courseID = row.select("td:nth-child(2)");
	    		Elements courseTitle = row.select("td:nth-child(3)");
	    		Elements Days = row.select("td:nth-child(7)");
	    		Elements Time = row.select("td:nth-child(8)");
	    		Elements Location = row.select("td:nth-child(9)");
	    		
	    		String id = courseID.text().split(" Click")[0];
	    		String title = courseTitle.text().split(" Click")[0];
	    		String professorName = Professors.text();
	    		String day = Days.text();
	    		String time = Time.text();
	    		String location = Location.text();
	    		
	    		if (professorName.length() > 1) {
	    			System.out.println(id + ": " + 
	    					title + ", " + 
	    					professorName + ", " + 
	    					day + " @ " + time + ", " +
	    					location);
	    		} else {
	    			System.out.println(id + ", " + day + " @ " + time + ", " +
	    					location);	
	    		}
		    }


		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		getCourseListFor("phys", "20172", "Undergraduate");
//		printDepartments();
	}
}
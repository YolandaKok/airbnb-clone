package model;

import java.util.List;

public class JSONCalendar {
	
	private String startDate;
	
	private String endDate;
	
	private int numDates;
	
	// Id's of the houses that are available between startDate and endDate
	private List<String> results;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public List<String> getResults() {
		return results;
	}

	public void setResults(List<String> results) {
		this.results = results;
	}

	public int getNumDates() {
		return numDates;
	}

	public void setNumDates(int numDates) {
		this.numDates = numDates;
	}

}

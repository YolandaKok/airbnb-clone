package entities;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CalendarLists {
	 private List<Calendar> calendar_list = new ArrayList<Calendar>();

	public List<Calendar> getCalendar_list() {
		return calendar_list;
	}
		
	public void setCalendar_list(List<Calendar> calendar_list) {
		this.calendar_list = calendar_list;
	}

}

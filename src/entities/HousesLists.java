package entities;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HousesLists {
    private List<House> houses_list = new ArrayList<House>();

	public List<House> getHouses_list() {
		return houses_list;
	}
	
	public void setHouses_list(List<House> houses_list) {
		this.houses_list = houses_list;
	}

}

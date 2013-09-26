package item;

import java.util.Date;

public class Campaign {

	
	
	private int id;
	private String name;
	private String start_date;
	private String end_date;
	private String adgroup_list;
	private String password;

	public Campaign() {
		
	}
	
	public Campaign(int id, String name){
	    this.setId(id);
	    this.setName(name);
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public String getAdgroup_list() {
		return adgroup_list;
	}

	public void setAdgroup_list(String adgroup_list) {
		this.adgroup_list = adgroup_list;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}






}

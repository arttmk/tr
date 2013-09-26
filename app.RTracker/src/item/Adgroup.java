package item;

import java.util.Date;

public class Adgroup {

	
	
	private int id;
	private String name;
	private String media;
	private int add;
	private String detail;
	private String target_id;
	private String del;
	private String Check;
	private String display;
	private String Function;

	public Adgroup() {
		
	}
	
	public Adgroup(int id, String name){
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

	public int getAdd() {
		return add;
	}

	public void setAdd(int add) {
		this.add = add;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	public String getDel() {
		return del;
	}

	public void setDel(String del) {
		this.del = del;
	}

	public String getCheck() {
		return Check;
	}

	public void setCheck(String check) {
		Check = check;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getFunction() {
		return Function;
	}

	public void setFunction(String function) {
		Function = function;
	}

	public String getTarget_id() {
		return target_id;
	}

	public void setTarget_id(String target_id) {
		this.target_id = target_id;
	}












}

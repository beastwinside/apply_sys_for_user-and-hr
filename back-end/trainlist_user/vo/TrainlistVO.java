package weaver.bull.m_baseinfo.trainlist_user.vo;

import JavaSource.ipf.util.QueryParameter;

public class TrainlistVO extends QueryParameter{
	private String  id;
	private String  proname;
	private String  begintime;
	private String endtime;
	private String peoplenum;
	private String prodetail;
	private String proaddress;
	private String lecturer;
	public String getLecturer() {
		return lecturer;
	}
	public void setLecturer(String lecturer) {
		this.lecturer = lecturer;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProname() {
		return proname;
	}
	public void setProname(String proname) {
		this.proname = proname;
	}
	public String getBegintime() {
		return begintime;
	}
	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getPeoplenum() {
		return peoplenum;
	}
	public void setPeoplenum(String peoplenum) {
		this.peoplenum = peoplenum;
	}
	public String getProdetail() {
		return prodetail;
	}
	public void setProdetail(String prodetail) {
		this.prodetail = prodetail;
	}
	public String getProaddress() {
		return proaddress;
	}
	public void setProaddress(String proaddress) {
		this.proaddress = proaddress;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
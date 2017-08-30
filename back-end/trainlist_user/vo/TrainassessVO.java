package weaver.bull.m_baseinfo.trainlist_user.vo;

import JavaSource.ipf.util.QueryParameter;

public class TrainassessVO extends QueryParameter{
	private String  id;
	private String  trainname;
	private String  trainid;
	private String  trainadj;
	private String  trainadv;
	private String  proname;
	
	public String getProname() {
		return proname;
	}
	public void setProname(String proname) {
		this.proname = proname;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTrainname() {
		return trainname;
	}
	public void setTrainname(String trainname) {
		this.trainname = trainname;
	}
	public String getTrainid() {
		return trainid;
	}
	public void setTrainid(String trainid) {
		this.trainid = trainid;
	}
	public String getTrainadj() {
		return trainadj;
	}
	public void setTrainadj(String trainadj) {
		this.trainadj = trainadj;
	}
	public String getTrainadv() {
		return trainadv;
	}
	public void setTrainadv(String trainadv) {
		this.trainadv = trainadv;
	}
	

	
}
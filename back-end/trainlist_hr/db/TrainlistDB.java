package weaver.bull.m_baseinfo.trainlist_hr.db;

import java.sql.SQLException;

import net.sf.json.JSONObject;
import weaver.bull.m_baseinfo.trainlist_hr.vo.TrainlistVO;
import weaver.bull.m_security.user.vo.UserVO;
import JavaSource.ipf.data.MyString;
import JavaSource.ipf.database.DBConnectionBaseEnc;
import JavaSource.ipf.util.JSON;
import JavaSource.ipf.util.StringUtil;


public class TrainlistDB{
	private static DBConnectionBaseEnc conn = new DBConnectionBaseEnc(); 
	private static String INSERT_SQL[] = {"id=idkey","pro_name","begin_time=to_date('$begin_time$','yyyy-mm-dd')","end_time=to_date('$end_time$','yyyy-mm-dd')","people_num","pro_detail",
			"pro_address","status","lecturer","apply_deadline=to_date('$apply_deadline$','yyyy-mm-dd')"
		};
private static String WHERE_SQL[] = {"id"};
private static String UDPATE_SQL[] = {"pro_name","begin_time=to_date('$begin_time$','yyyy-mm-dd')","end_time=to_date('$end_time$','yyyy-mm-dd')","people_num","pro_detail",
		"pro_address","status","lecturer","apply_deadline=to_date('$apply_deadline$','yyyy-mm-dd')"
		};
//获取列表

public String getList(TrainlistVO para,UserVO user) throws Exception{
	String sql = " select id,pro_name,begin_time,end_time,people_num,pro_detail,pro_address,status,lecturer,apply_deadline"+
				 " from uf_train_list where status!='已删除' ";
	String [][] vals = {
			{para.getProname(),"pro_name like'%#%'"},
			{para.getProaddress(),"pro_address like'%#%'"}
			};
	String tStr = MyString.appendWhere(vals);
	sql+=tStr+" order by id desc";
	String json = para.getListByJson(sql);
	return json;
}

public String getTrainlist(String key) throws SQLException{
	String sql =" select id,pro_name,begin_time,end_time,people_num,pro_detail,pro_address,status,lecturer,apply_deadline "+
			 " from uf_train_list ";
	String json = conn.getArrayByJSON(sql);
	return json;
}




public String getVO(String id) throws Exception{
	String sql =" select id,pro_name,begin_time,end_time,people_num,pro_detail,pro_address,lecturer,apply_deadline "+
			 " from uf_train_list where id="+id+"";
	String json = conn.getVOByJSON(sql);
	return json;
}





public String getCount(String id) throws Exception{
	String sql1 ="select avg(A_1) as a1avg,avg(A_2) as a2avg,avg(B_1) as b1avg,avg(B_2) as b2avg,"
			+ "avg(B_3) as b3avg,avg(B_4) as b4avg,avg(B_5) as b5avg,avg(B_6) as b6avg,"
			+ "avg(B_7) as b7avg,avg(B_8) as b8avg,avg(B_9) as b9avg,avg(B_10) as b10avg,"
			+ "avg(C_1) as c1avg,avg(C_2) as c2avg,avg(D_1) as d1avg,count(*) as totalnum from uf_train_apply where train_id='"+id+"'";
	String sql2="select count(*)as vaildnum from uf_train_apply where train_id='"+id+"'and ifassess='已评估'";
	String json1 = conn.getVOByJSON(sql1);
	String json2 = conn.getVOByJSON(sql2);
	JSONObject jsonobj1=JSONObject.fromObject(json1);
	JSONObject jsonobj2=JSONObject.fromObject(json2);
	JSONObject jsonobj3=new JSONObject();
	jsonobj3.putAll(jsonobj1);
	jsonobj3.putAll(jsonobj2);
	String json3=jsonobj3.toString();
	return json3;
}



public void save(JSONObject jo) throws SQLException{
	if(StringUtil.isNullOrEmpty(jo.get("id")))
		conn.insertDB(JSON.JsonToInsert(jo, "uf_train_list",INSERT_SQL));
	else
		conn.updateDB(JSON.JsonToUpdate(jo, "uf_train_list",WHERE_SQL,UDPATE_SQL));
}


public void delete(String ids) throws SQLException{
conn.updateDB("update uf_train_list set status='已删除' where id in("+ids+")");
}

	
	
	
	
}

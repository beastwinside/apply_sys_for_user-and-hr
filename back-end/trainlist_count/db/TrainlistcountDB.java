package weaver.bull.m_baseinfo.trainlist_count.db;

import java.sql.SQLException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import weaver.bull.m_baseinfo.trainlist_count.vo.TrainlistcountVO;
import weaver.bull.m_security.user.vo.UserVO;
import JavaSource.ipf.data.MyString;
import JavaSource.ipf.database.DBConnectionBaseEnc;
import JavaSource.ipf.util.JSON;
import JavaSource.ipf.util.StringUtil;


public class TrainlistcountDB{
	private static DBConnectionBaseEnc conn = new DBConnectionBaseEnc(); 
	private static String INSERT_SQL[] = {"id=idkey","pro_name","begin_time=to_date('$begin_time$','yyyy-mm-dd')","end_time=to_date('$end_time$','yyyy-mm-dd')","people_num","pro_detail",
			"pro_address","status","lecturer"
		};
private static String WHERE_SQL[] = {"id"};
private static String UDPATE_SQL[] = {"ifcheck"};
//获取列表

public String getList(TrainlistcountVO para,UserVO user) throws Exception{
	String sql = "select id,pro_name,train_company,train_depart,train_job,train_name,train_sex,train_age,train_phone,entry_time,recent_test,arrive_time,remark,ifcheck "+
				 " from uf_train_apply where 1='1' ";
	String [][] vals = {
			{para.getPro_name(),"pro_name like'%#%'"}
			};
	String tStr = MyString.appendWhere(vals);
	sql+=tStr+" order by id desc";
	String json = para.getListByJson(sql);
	return json;
}




public String getReportList(TrainlistcountVO para,UserVO user) throws SQLException{
	String sql = "select id,pro_name,train_company,train_depart,train_job,train_name,train_sex,train_age,train_phone,entry_time,recent_test,arrive_time,remark,ifcheck "+
			 " from uf_train_apply where 1='1' ";
String [][] vals = {
		{para.getPro_name(),"pro_name like'%#%'"}
		};
String tStr = MyString.appendWhere(vals);
sql+=tStr+" order by id desc";
String json = para.getListByJson(sql);
return json;
}



public String getVO(String id) throws Exception{
	String sql =" select id,pro_name,begin_time,end_time,people_num,pro_detail,pro_address,lecturer "+
			 " from uf_train_list where id="+id+"";
	String json = conn.getVOByJSON(sql);
	return json;
}




public void save(String jdata) throws SQLException{
	JSONArray jarr = JSONArray.fromObject(jdata);
	for(int i=0;i<jarr.size();i++){
		JSONObject jo = (JSONObject)jarr.get(i);  
			conn.updateDB(JSON.JsonToUpdate(jo, "uf_train_apply",WHERE_SQL,UDPATE_SQL));
	}
}

public void checktrue(String ids) throws SQLException{
conn.updateDB("update uf_train_apply set ifcheck='已签到' where id in("+ids+")");
}


public void checkfalse(String ids) throws SQLException{
conn.updateDB("update uf_train_apply set ifcheck='未签到' where id in("+ids+")");
}

	
	
	
	
}

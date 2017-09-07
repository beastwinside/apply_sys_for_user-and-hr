package weaver.bull.m_baseinfo.trainlist_user.db;

import java.sql.SQLException;

import net.sf.json.JSONObject;
import weaver.bull.m_baseinfo.trainlist_user.vo.TrainlistVO;
import weaver.bull.m_security.user.vo.UserVO;
import JavaSource.ipf.data.MyString;
import JavaSource.ipf.database.DBConnectionBaseEnc;
import JavaSource.ipf.util.JSON;
import JavaSource.ipf.util.StringUtil;


public class TrainlistDB{
	private static DBConnectionBaseEnc conn = new DBConnectionBaseEnc(); 
	private static String INSERT_SQL[] = {"id=idkey","pro_name","begin_time=to_date('$begin_time$','yyyy-mm-dd')","end_time=to_date('$end_time$','yyyy-mm-dd')","people_num","pro_detail",
			"pro_address","status","lecturer"
		};
private static String WHERE_SQL[] = {"id"};
private static String UDPATE_SQL[] = {"pro_name","begin_time=to_date('$begin_time$','yyyy-mm-dd')","end_time=to_date('$end_time$','yyyy-mm-dd')","people_num","pro_detail",
		"pro_address","ststus","lecturer"
		};
//获取列表

public String getList(TrainlistVO para,UserVO user) throws Exception{
	String sql = " select id,pro_name,begin_time,end_time,people_num,pro_detail,pro_address,status,lecturer,apply_deadline"+
				 " from uf_train_list where status='可报名'or status='可评测' or status='只显示不提供操作' ";
	
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
	String sql =" select id,pro_name,begin_time,end_time,people_num,pro_detail,pro_address,status,lecturer"+
			 " from uf_train_list ";
	String json = conn.getArrayByJSON(sql);
	return json;
}




public String getVO(String id) throws Exception{
	String sql =" select id,pro_name,begin_time,end_time,people_num,pro_detail,pro_address,lecturer"+
			 " from uf_train_list where id="+id+"";
	String json = conn.getVOByJSON(sql);
	return json;
}

public String getAssess(String id,String username,String userid) throws Exception{
	String sql = " select train_adv,train_adj,A_1,A_2,B_1,B_2,B_3,B_4,B_5,B_6,B_7,B_8,B_9,B_10,C_1,C_2,D_1 from uf_train_apply where train_name='"+username+"' and train_id='"+id+"'and user_id='"+userid+"'";
	String json = conn.getVOByJSON(sql);
	return json;
}

public String getApply(String id,String username,String userid) throws Exception{
	String sql = " select train_phone,train_date,lecturer,isapply,train_age,train_sex,train_job,entry_time,recent_test,remark,arrive_time,train_job from uf_train_apply where train_name='"+username+"' and train_id='"+id+"'and user_id='"+userid+"'";
	String json = conn.getVOByJSON(sql);
	return json;
}


public void save(JSONObject jo) throws SQLException{
	if(StringUtil.isNullOrEmpty(jo.get("id")))
		conn.insertDB(JSON.JsonToInsert(jo, "uf_train_list",INSERT_SQL));
	else
		conn.updateDB(JSON.JsonToUpdate(jo, "uf_train_list",WHERE_SQL,UDPATE_SQL));
}
public void delete(String ids) throws SQLException{
conn.updateDB("update uf_train_apply set status='1' where id in("+ids+")");
}

	
	
	
	
}

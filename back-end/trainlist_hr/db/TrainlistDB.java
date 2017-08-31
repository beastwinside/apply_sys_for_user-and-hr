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
			"pro_address","status"
		};
private static String WHERE_SQL[] = {"id"};
private static String UDPATE_SQL[] = {"pro_name","begin_time=to_date('$begin_time$','yyyy-mm-dd')","end_time=to_date('$end_time$','yyyy-mm-dd')","people_num","pro_detail",
		"pro_address","status"
		};
//获取列表

public String getList(TrainlistVO para,UserVO user) throws Exception{
	String sql = " select id,pro_name,begin_time,end_time,people_num,pro_detail,pro_address,status"+
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
	String sql =" select id,pro_name,begin_time,end_time,people_num,pro_detail,pro_address,status "+
			 " from uf_train_list ";
	String json = conn.getArrayByJSON(sql);
	return json;
}




public String getVO(String id) throws Exception{
	String sql =" select id,pro_name,begin_time,end_time,people_num,pro_detail,pro_address "+
			 " from uf_train_list where id="+id+"";
	String json = conn.getVOByJSON(sql);
	return json;
}

public String getAssess(String id,String username) throws Exception{
	String sql = " select train_adv,train_adj from uf_train_apply where train_name='"+username+"' and train_id="+id+"";
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
conn.updateDB("update uf_train_list set status='已删除' where id in("+ids+")");
}

	
	
	
	
}

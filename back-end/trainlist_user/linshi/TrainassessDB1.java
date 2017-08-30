package weaver.bull.m_baseinfo.trainlist_user.db;

import java.sql.SQLException;

import net.sf.json.JSONObject;
import weaver.bull.m_baseinfo.trainlist_user.vo.TrainlistVO;
import weaver.bull.m_security.user.vo.UserVO;
import JavaSource.ipf.data.MyString;
import JavaSource.ipf.database.DBConnectionBaseEnc;
import JavaSource.ipf.util.JSON;
import JavaSource.ipf.util.StringUtil;

public class TrainassessDB1{
	private static DBConnectionBaseEnc conn = new DBConnectionBaseEnc();
	private static String INSERT_SQL[] = {"id=idkey","train_name","train_id","train_adj","train_adv"
		};
private static String WHERE_SQL[] = {"id"};
private static String UDPATE_SQL[] = {"train_name","train_id","train_adj","train_adv"
		};

public String getList(TrainassessVO para,UserVO user) throws Exception{
	String sql = " select id,train_name,train_id,train_adj,train_adv "+
				 " from uf_train_assess where 1=1 ";
	String [][] vals = {
			{para.getTrainname(),"train_name like'%#%'"},
			{para.getTrainid(),"train_id like'%#%'"}
			};
	String tStr = MyString.appendWhere(vals);
	sql+=tStr+" order by id desc";
	String json = para.getListByJson(sql);
	return json;
}

public String getTrainassess(String key) throws SQLException{
	String sql =" select id,train_name,train_id,train_adj,train_adv"+
			 " from uf_train_assess";
	String json = conn.getArrayByJSON(sql);
	return json;
}


public String getVO(String id) throws Exception{
	String sql =" select id,train_name,train_id,train_adj,train_adv"+
			 " from uf_train_assess where id="+id+"";
	String json = conn.getVOByJSON(sql);
	return json;
}

public void save(JSONObject jo) throws SQLException{
	if(StringUtil.isNullOrEmpty(jo.get("id")))
		conn.insertDB(JSON.JsonToInsert(jo, "uf_train_assess",INSERT_SQL));
	else
		conn.updateDB(JSON.JsonToUpdate(jo, "uf_train_assess",WHERE_SQL,UDPATE_SQL));
}
public void delete(String ids) throws SQLException{
conn.updateDB("update uf_train_assess set status='1' where id in("+ids+")");
}

	
	
	
	
}

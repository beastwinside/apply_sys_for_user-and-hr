package weaver.bull.m_baseinfo.trainlist_user.db;

import java.sql.SQLException;

import net.sf.json.JSONObject;
import weaver.bull.m_baseinfo.trainlist_user.vo.TrainapplyVO;
import weaver.bull.m_security.user.vo.UserVO;
import JavaSource.ipf.data.MyString;
import JavaSource.ipf.database.DBConnectionBaseEnc;
import weaver.bull.m_security.user.vo.UserVO;
import JavaSource.ipf.util.JSON;
import JavaSource.ipf.util.StringUtil;


public class TrainapplyDB{

	private static DBConnectionBaseEnc conn = new DBConnectionBaseEnc();
	private static String INSERT_SQL[] = {"id=idkey","train_name","train_depart","train_company","train_date=to_date('$train_date$','yyyy-mm-dd')","train_phone",
			"train_id","pro_name","user_id"
		};
private static String WHERE_SQL[] = {"id"};
private static String UDPATE_SQL[] = {"train_name","train_depart","train_company","train_date=to_date('$train_date$','yyyy-mm-dd')","train_phone",
		"train_id","pro_name"
		};

public String getList(TrainapplyVO para,UserVO user) throws Exception{
	String sql = " select id,train_name,train_depart,train_company,train_date,train_phone,train_id,pro_name "+
				 " from uf_train_apply where 1=1 ";
	String [][] vals = {
			{para.getTrainname(),"train_name like'%#%'"},
			{para.getTraindepart(),"train_depart like'%#%'"}
			};
	String tStr = MyString.appendWhere(vals);
	sql+=tStr+" order by id desc";
	String json = para.getListByJson(sql);
	return json;
}

public String getTrainapply(String key) throws SQLException{
	String sql =" select id,train_name,train_depart,train_company,train_date,train_phone,train_id,pro_name "+
			 " from uf_train_apply ";
	String json = conn.getArrayByJSON(sql);
	return json;
}


public String getVO(String id) throws Exception{
	String sql =" select id,train_name,train_depart,train_company,train_date,train_phone,train_id,pro_name "+
			 " from uf_train_apply where id="+id+"";
	String json = conn.getVOByJSON(sql);
	return json;
}

public void save(JSONObject jo) throws SQLException{

	if(!StringUtil.isNullOrEmpty(jo.get("id")))
		conn.insertDB(JSON.JsonToInsert(jo, "uf_train_apply",INSERT_SQL));
	else
		conn.updateDB(JSON.JsonToUpdate(jo, "uf_train_apply",WHERE_SQL,UDPATE_SQL));

}
public void delete(String ids) throws SQLException{
conn.updateDB("update uf_train_apply set status='1' where id in("+ids+")");
}

	
	
	
	
}

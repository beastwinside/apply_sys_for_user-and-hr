package weaver.bull.m_baseinfo.trainlist_user.db;

import java.sql.SQLException;

import net.sf.json.JSONObject;
import weaver.bull.m_baseinfo.trainlist_user.vo.TrainassessVO;
import weaver.bull.m_security.user.vo.UserVO;
import JavaSource.ipf.data.MyString;
import JavaSource.ipf.database.DBConnectionBaseEnc;
import JavaSource.ipf.util.JSON;
import JavaSource.ipf.util.StringUtil;

public class TrainassessDB{
	private static DBConnectionBaseEnc conn = new DBConnectionBaseEnc();
	private static String INSERT_SQL[] = {"id=idkey","pro_name","train_adj","train_adv","A_1","A_2","B_1","B_2","B_3","B_4","B_5","B_6","B_7","B_8","B_9","B_10","C_1","C_2","D_1","ifassess='ÒÑÆÀ¹À'"
		};
private static String WHERE_SQL[] = {"train_id","user_id"};
private static String UDPATE_SQL[] = {"pro_name","train_adj","train_adv","A_1","A_2","B_1","B_2","B_3","B_4","B_5","B_6","B_7","B_8","B_9","B_10","C_1","C_2","D_1","ifassess='ÒÑÆÀ¹À'"
		};

public String getList(TrainassessVO para,UserVO user) throws Exception{
	String sql = " select id,pro_name,train_adj,train_adv "+
				 " from uf_train_apply where 1=1 ";
	String [][] vals = {
			{para.getProname(),"pro_name like'%#%'"}
			};
	String tStr = MyString.appendWhere(vals);
	sql+=tStr+" order by id desc";
	String json = para.getListByJson(sql);
	return json;
}

public String getTrainassess(String key) throws SQLException{
	String sql =" select id,pro_name,train_adj,train_adv"+
			 " from uf_train_apply";
	String json = conn.getArrayByJSON(sql);
	return json;
}


public String getVO(String id) throws Exception{
	String sql =" select id,pro_name,train_adj,train_adv,train_id"+
			 " from uf_train_apply where id="+id+"";
	String json = conn.getVOByJSON(sql);
	return json;
}

public void save(JSONObject jo) throws SQLException{
		
		conn.updateDB(JSON.JsonToUpdate(jo, "uf_train_apply",WHERE_SQL,UDPATE_SQL));
}
public void delete(String ids) throws SQLException{
conn.updateDB("update uf_train_apply set status='1' where id in("+ids+")");
}

	
	
	
	
}

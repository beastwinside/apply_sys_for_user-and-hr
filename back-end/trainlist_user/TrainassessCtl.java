package weaver.bull.m_baseinfo.trainlist_user;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import weaver.bull.m_baseinfo.trainlist_user.db.TrainassessDB;
import weaver.bull.m_baseinfo.trainlist_user.vo.TrainassessVO;
import weaver.bull.m_hr.employee.db.EmployeeDB;
import weaver.bull.m_security.user.vo.UserVO;
import weaver.bull.toolkit.VidCol;
import JavaSource.ipf.core.IPF;
import JavaSource.ipf.util.StringUtil;
import JavaSource.ipf.util.SysConstants;


public class TrainassessCtl{

	
		
		public static void getTrainassess(HttpServletRequest req,
				HttpServletResponse res, HashMap map) throws IOException,
				ServletException {
			try {
				IPF ipf = new IPF(req, res);
				String key = ipf.getParameterNull("key");
				TrainassessDB db = new TrainassessDB();
				String json = db.getTrainassess(key);
				ipf.print(json);
			} catch (Exception e) {
				 	e.printStackTrace();
			}
		}
		
		public static void save(HttpServletRequest req,
				HttpServletResponse res, HashMap map) throws IOException,
				ServletException {
			try {
				IPF ipf = new IPF(req, res);
				UserVO user = ipf.getUser();
				TrainassessDB db = new TrainassessDB();
				String jdata = ipf.getParameterNull("data");
				jdata = jdata.substring(jdata.indexOf("[")+1,jdata.lastIndexOf("]"));
				JSONObject jo = JSONObject.fromObject(jdata);
				jo.put("user_id",user.getPersonid());
				EmployeeDB empDB = new EmployeeDB();
				String oahr = empDB.getOAHrInfo(user.getPersonid());
				JSONObject oahrobj=JSONObject.fromObject(oahr);
				String username = oahrobj.getString("LASTNAME");
				
			
				String wstr="and train_name='"+username+"'and user_id='"+user.getPersonid()+"'";
				
					if(!VidCol.getVid("uf_train_apply", "train_id",jo.getString("train_id"),wstr))
					{
						ipf.print("alert('您未报名此项目！');");
						}
					else 
						db.save(jo);					
						ipf.print("alert('评论成功！');bt_close()");
			} catch (Exception e) {
				 	e.printStackTrace();
			}
		}
		
		
	
	
	
	

}

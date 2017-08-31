package weaver.bull.m_baseinfo.trainlist_user;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import JavaSource.ipf.core.IPF;
import JavaSource.ipf.util.StringUtil;
import JavaSource.ipf.util.SysConstants;
import net.sf.json.JSONObject;
import weaver.bull.m_baseinfo.trainlist_user.db.TrainapplyDB;
import weaver.bull.m_baseinfo.trainlist_user.vo.TrainapplyVO;
import weaver.bull.m_hr.employee.db.EmployeeDB;
import weaver.bull.m_security.user.vo.UserVO;
import weaver.bull.toolkit.VidCol;


public class TrainapplyCtl{
	
	
		
		public static void getTrainapply(HttpServletRequest req,
				HttpServletResponse res, HashMap map) throws IOException,
				ServletException {
			try {
				IPF ipf = new IPF(req, res);
				String key = ipf.getParameterNull("key");
				TrainapplyDB db = new TrainapplyDB();
				String json = db.getTrainapply(key);
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
				TrainapplyDB db = new TrainapplyDB();
				String jdata = ipf.getParameterNull("data");
				jdata = jdata.substring(jdata.indexOf("[")+1,jdata.lastIndexOf("]"));
				JSONObject jo = JSONObject.fromObject(jdata);
				jo.put("user_id",user.getPersonid());
				EmployeeDB empDB = new EmployeeDB();
				String oahr = empDB.getOAHrInfo(user.getPersonid());
				JSONObject oahrobj=JSONObject.fromObject(oahr);
				String username = oahrobj.getString("LASTNAME");
				String bstr="alert('您未报名该项目');";
			
				String wstr="and train_id='"+jo.getString("train_id")+"'and train_name='"+jo.getString("train_name")+"'";
				
				if(!VidCol.getVid("uf_train_apply", "user_id",jo.getString("user_id"),wstr))
				{
					db.insert(jo);	ipf.print("alert('新增报名成功！');bt_close()");
				}
				else {
					db.update(jo);	ipf.print("alert('修改报名信息成功！');bt_close()");
				}
		
			} catch (Exception e) {
				 	e.printStackTrace();
			}
		}
		
		
		public static void search(HttpServletRequest req,
				HttpServletResponse res, HashMap map) throws IOException,
				ServletException {
			try {
				IPF ipf = new IPF(req, res);
				TrainapplyVO para = new TrainapplyVO();
				TrainapplyDB db = new TrainapplyDB();
				UserVO user = ipf.getUser();
				ipf.toData(para,req);
				para.setPageSize(ipf.getParameterNull("pageSize"));
				para.setCurrentPage(ipf.getParameterNull("pageIndex"));
				String json = db.getList(para, user);
				ipf.print(json);
			} catch (Exception e) {
				 	e.printStackTrace();
			}
		}
	
	
		
	
	
}

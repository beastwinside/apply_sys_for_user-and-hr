package weaver.bull.m_baseinfo.trainlist_hr;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import weaver.bull.m_baseinfo.trainlist_hr.db.TrainlistDB;
import weaver.bull.m_baseinfo.trainlist_hr.vo.TrainlistVO;
import weaver.bull.m_hr.employee.db.EmployeeDB;
import weaver.bull.m_security.user.vo.UserVO;
import weaver.bull.toolkit.VidCol;
import JavaSource.ipf.core.IPF;
import JavaSource.ipf.util.StringUtil;
import JavaSource.ipf.util.SysConstants;


public class TrainlistCtl{
	public static void showPage(HttpServletRequest req,
			HttpServletResponse res, HashMap map) throws IOException,
			ServletException {
		try {
			String page ="";
			IPF ipf = new IPF(req, res);
			TrainlistDB  db = new TrainlistDB();
			UserVO user = ipf.getUser();
			String pagetype = ipf.getParameterNull("pagetype");
			if(pagetype.equals("list")) 
				page = SysConstants.TEMP_PATH+"m_baseinfo/trainlist_hr/list.html";
			else if(pagetype.equals("add")) 
				page = SysConstants.TEMP_PATH+"m_baseinfo/trainlist_hr/add.html";
			else if(pagetype.equals("edit")) {
				page = SysConstants.TEMP_PATH+"m_baseinfo/trainlist_hr/edit.html";
			
				String id = ipf.getParameterNull("id");
				ipf.addObject("formdata", db.getVO(id));
	
			}
			else if(pagetype.equals("assess")) {
				page = SysConstants.TEMP_PATH+"m_baseinfo/trainlist_user/edit_assess.html";
				EmployeeDB empDB = new EmployeeDB();
				TrainlistDB assessdb=new TrainlistDB();
				String oahr = empDB.getOAHrInfo(user.getPersonid());
				JSONObject oahrobj=JSONObject.fromObject(oahr);
				String username = oahrobj.getString("LASTNAME");
				
				String id = ipf.getParameterNull("id");
				String assessinfo=assessdb.getAssess(id,username);
				ipf.addObject("formdata", db.getVO(id));
				ipf.addObject("oahr", oahr);
				ipf.addObject("assess", assessinfo);
			}
			ipf.showPage(page);
		} catch (Exception e) {
			 	e.printStackTrace();
		}
		}
	
		
		public static void getTrainlist(HttpServletRequest req,
				HttpServletResponse res, HashMap map) throws IOException,
				ServletException {
			try {
				IPF ipf = new IPF(req, res);
				String key = ipf.getParameterNull("key");
				TrainlistDB db = new TrainlistDB();
				String json = db.getTrainlist(key);
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
				TrainlistDB db = new TrainlistDB();
				String jdata = ipf.getParameterNull("data");
				jdata = jdata.substring(jdata.indexOf("[")+1,jdata.lastIndexOf("]"));
				JSONObject jo = JSONObject.fromObject(jdata);
	
				if(StringUtil.isNullOrEmpty(jo.getString("id"))){
					if(VidCol.getVid("uf_train_list", "pro_name",jo.getString("pro_name"))){
						ipf.print("alert('改项目已存在');");
						return ;
					}
				}
				db.save(jo);
				ipf.print("alert('保存成功！');bt_close()");
			} catch (Exception e) {
				 	e.printStackTrace();
			}
		}
		
		
		public static void search(HttpServletRequest req,
				HttpServletResponse res, HashMap map) throws IOException,
				ServletException {
			try {
				IPF ipf = new IPF(req, res);
				TrainlistVO para = new TrainlistVO();
				TrainlistDB db = new TrainlistDB();
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
	
	
		public static void delete(HttpServletRequest req,
				HttpServletResponse res, HashMap map) throws IOException,
				ServletException {
			try {
				IPF ipf = new IPF(req, res);
				String ids = ipf.getParameterNull("ids");
				TrainlistDB db = new TrainlistDB();
				db.delete(ids);
			} catch (Exception e) {
			 	e.printStackTrace();
		}
		}
	
	
	
}

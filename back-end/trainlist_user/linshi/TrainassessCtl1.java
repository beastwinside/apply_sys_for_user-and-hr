package weaver.bull.m_baseinfo.trainassess;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import weaver.bull.m_baseinfo.trainassess.db.TrainassessDB;
import weaver.bull.m_baseinfo.trainassess.vo.TrainassessVO;
import weaver.bull.m_security.user.vo.UserVO;
import weaver.bull.toolkit.VidCol;
import JavaSource.ipf.core.IPF;
import JavaSource.ipf.util.StringUtil;
import JavaSource.ipf.util.SysConstants;


public class TrainassessCtl{
	public static void showPage(HttpServletRequest req,
			HttpServletResponse res, HashMap map) throws IOException,
			ServletException {
		try {
			String page ="";
			IPF ipf = new IPF(req, res);
			TrainassessDB  db = new TrainassessDB();
			String pagetype = ipf.getParameterNull("pagetype");
			if(pagetype.equals("list")) 
				page = SysConstants.TEMP_PATH+"m_baseinfo/trainassess/list.html";
			else if(pagetype.equals("add")) 
				page = SysConstants.TEMP_PATH+"m_baseinfo/trainassess/add.html";
			else if(pagetype.equals("edit")) {
				page = SysConstants.TEMP_PATH+"m_baseinfo/trainassess/edit.html";
				String id = ipf.getParameterNull("id");
				ipf.addObject("formdata", db.getVO(id));
			}
			ipf.showPage(page);
		} catch (Exception e) {
			 	e.printStackTrace();
		}
		}
	
		
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
				TrainassessDB db = new TrainassessDB();
				String jdata = ipf.getParameterNull("data");
				jdata = jdata.substring(jdata.indexOf("[")+1,jdata.lastIndexOf("]"));
				JSONObject jo = JSONObject.fromObject(jdata);
				if(StringUtil.isNullOrEmpty(jo.getString("id"))){
					if(VidCol.getVid("uf_train_assess", "train_id",jo.getString("train_id"))){
						ipf.print("alert('改成员已存在');");
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
				TrainassessVO para = new TrainassessVO();
				TrainassessDB db = new TrainassessDB();
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
				TrainassessDB db = new TrainassessDB();
				db.delete(ids);
			} catch (Exception e) {
			 	e.printStackTrace();
		}
		}
	
	

}

package weaver.bull.m_baseinfo.trainlist_count;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import weaver.bull.excel.ExportExcel;
import weaver.bull.m_baseinfo.trainlist_count.db.TrainlistcountDB;
import weaver.bull.m_baseinfo.trainlist_count.vo.TrainlistcountVO;
import weaver.bull.m_hr.check.db.CheckDB;
import weaver.bull.m_hr.check.vo.CheckVO;
import weaver.bull.m_hr.employee.db.EmployeeDB;
import weaver.bull.m_security.user.vo.UserVO;
import weaver.bull.toolkit.VidCol;
import JavaSource.ipf.core.IPF;
import JavaSource.ipf.util.JSON;
import JavaSource.ipf.util.StringUtil;
import JavaSource.ipf.util.SysConstants;


public class TrainlistcountCtl{
	
	
	public static String filePath = SysConstants.UPLOAD_TEMP_PATH + "/";
	
	public static void showPage(HttpServletRequest req,
			HttpServletResponse res, HashMap map) throws IOException,
			ServletException {
		try {
			String page ="";
			IPF ipf = new IPF(req, res);
			TrainlistcountDB  db = new TrainlistcountDB();
			UserVO user = ipf.getUser();
			String pagetype = ipf.getParameterNull("pagetype");
			if(pagetype.equals("list")) 
				page = SysConstants.TEMP_PATH+"m_baseinfo/trainlist_count/trainlist_count_list.html";
			else if(pagetype.equals("add")) 
				page = SysConstants.TEMP_PATH+"m_baseinfo/trainlist_count/trainlist_count_add.html";
			else if(pagetype.equals("edit")) {
				page = SysConstants.TEMP_PATH+"m_baseinfo/trainlist_count/trainlist_count_edit.html";
			
				String id = ipf.getParameterNull("id");
				ipf.addObject("formdata", db.getVO(id));
	
			}
			
			ipf.showPage(page);
		} catch (Exception e) {
			 	e.printStackTrace();
		}
		}
	
		
		
		
		public static void save(HttpServletRequest req,
				HttpServletResponse res, HashMap map) throws IOException,
				ServletException {
			try {
				IPF ipf = new IPF(req, res);
				TrainlistcountDB db = new TrainlistcountDB();
				String jdata = ipf.getParameterNull("data");
				db.save(jdata);
				ipf.print("alert('保存成功');");
			} catch (Exception e) {
				 	e.printStackTrace();
			}
		}
		
		
		public static void search(HttpServletRequest req,
				HttpServletResponse res, HashMap map) throws IOException,
				ServletException {
			try {
				IPF ipf = new IPF(req, res);
				TrainlistcountVO para = new TrainlistcountVO();
				TrainlistcountDB db = new TrainlistcountDB();
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
	
	
		public static void checktrue(HttpServletRequest req,
				HttpServletResponse res, HashMap map) throws IOException,
				ServletException {
			try {
				IPF ipf = new IPF(req, res);
				String ids = ipf.getParameterNull("ids");
				TrainlistcountDB db = new TrainlistcountDB();
				db.checktrue(ids);
			} catch (Exception e) {
			 	e.printStackTrace();
			 	StringWriter sw = new StringWriter();  
		        PrintWriter pw = new PrintWriter(sw);  
		        e.printStackTrace(pw);
		        System.out.println(sw.toString());
		}
		}
		
		
		public static void checkfalse(HttpServletRequest req,
				HttpServletResponse res, HashMap map) throws IOException,
				ServletException {
			try {
				IPF ipf = new IPF(req, res);
				String ids = ipf.getParameterNull("ids");
				TrainlistcountDB db = new TrainlistcountDB();
				db.checkfalse(ids);
			} catch (Exception e) {
			 	e.printStackTrace();
			 	StringWriter sw = new StringWriter();  
		        PrintWriter pw = new PrintWriter(sw);  
		        e.printStackTrace(pw);
		        System.out.println(sw.toString());
		}
		}
		
		
		// 导出人员测评列表
		public static void exportExcel1(HttpServletRequest req, HttpServletResponse res, HashMap map) throws Exception {
			IPF ipf = new IPF(req, res);
			TrainlistcountVO para = new TrainlistcountVO();
			TrainlistcountDB db = new TrainlistcountDB();
			UserVO user = ipf.getUser();
			ipf.toData(para, req);
			String today = ipf.getsysdate("").replaceAll(":", "").replaceAll(" ", "");
			String fileName = today + ".xls";
			ipf.toData(para, req);
			para.setPageSize(ipf.getParameterNull("pageSize"));
			para.setCurrentPage(ipf.getParameterNull("pageIndex"));
			String json = db.getReportList(para, user);
			if (json == null)
				return;

			ExportExcel<TrainlistcountVO> ex = new ExportExcel<TrainlistcountVO>();
			String[][] headers = { { "培训项目", "公司", "部门","岗位","姓名",
				"性别","年龄","干部名称","干部名称","干部名称","干部名称",
				"干部名称","干部名称","干部名称",}, { "pro_name", "train_company", "train_name" } };
			OutputStream out;
			try {
				System.out.println("导出路径:" + filePath + fileName);
				out = new FileOutputStream(filePath + fileName);
				ex.exportExcelByJSON("EXCEL导出", headers, JSON.DeJSONArray(json), out);
				out.close();
				System.out.println("excel导出成功！");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (!fileName.equals("")) {
				req.setAttribute("_filename", fileName);
				System.out.println("filename:" + fileName);
				req.getRequestDispatcher("/ExcelDownLoad").forward(req, res);
			}
		}
	
		
		
}

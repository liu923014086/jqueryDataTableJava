package com.liudaxia.cn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Datable extends HttpServlet{
	
	
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		
		String aoData = req.getParameter("aoData");
		String responseMsg = list(aoData);
		response.setCharacterEncoding("UTF-8");    
		response.setContentType("application/json; charset=utf-8");  
		response.getWriter().write(responseMsg);
	}

	public String list(String aoData) {  
        String sEcho = "";// 记录操作的次数  每次加1  
        String iDisplayStart = "";// 起始  
        String iDisplayLength = "";// size  
        String sSearch = "";// 搜索的关键字  
        int count = 0 ;  //查询出来的数量  
        JSONArray ja = (JSONArray) JSONArray.fromObject(aoData);  
        //分别为关键的参数赋值  
        for (int i = 0; i < ja.size(); i++) {  
            JSONObject obj = (JSONObject) ja.get(i);  
            if (obj.get("name").equals("sEcho"))  
                sEcho = obj.get("value").toString();  
            if (obj.get("name").equals("iDisplayStart"))  
                iDisplayStart = obj.get("value").toString();  
            if (obj.get("name").equals("iDisplayLength"))  
                iDisplayLength = obj.get("value").toString();  
            if (obj.get("name").equals("sSearch"))  
                sSearch = obj.get("value").toString();  
        }  
        DataTablePageList dtpl = new DataTablePageList();
        List<Manager> pageList = new ArrayList<Manager>();
        for(int i=0;i<20;i++){
        	Manager m = new Manager();
        	m.setAccount("account_"+i);
        	m.setLogintime("logintime_"+i);
        	m.setName("name_"+i);
        	pageList.add(m);
        }
        
		// 需要的三个参数
		dtpl.setiTotalRecords(String.valueOf(pageList.size()));//total
		dtpl.setiTotalDisplayRecords(String.valueOf(pageList.size()));
		dtpl.setAaData(pageList);
		// 获取页面参数再传递回去
		dtpl.setsEcho(sEcho);
		dtpl.setsSearch(sSearch);
		dtpl.setiDisplayStart(iDisplayStart);
		dtpl.setiDisplayLength(iDisplayLength);
        return JSONObject.fromObject(dtpl).toString();  
    }  
	
	public static void main(String[] args) {
		 DataTablePageList dtpl = new DataTablePageList();
	        List<Manager> pageList = new ArrayList<Manager>();
	        
			// 需要的三个参数
			dtpl.setiTotalRecords(String.valueOf(pageList.size()));//total
			dtpl.setiTotalDisplayRecords(String.valueOf(pageList.size()));
			dtpl.setAaData(pageList);
			// 获取页面参数再传递回去
			dtpl.setsEcho("");
			dtpl.setsSearch("");
			dtpl.setiDisplayStart("");
			dtpl.setiDisplayLength("");
        System.out.println(JSONObject.fromObject(dtpl));
	}

}

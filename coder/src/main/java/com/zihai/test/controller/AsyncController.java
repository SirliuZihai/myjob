package com.zihai.test.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name="async",urlPatterns="/asyncServlet",asyncSupported=true)
public class AsyncController extends HttpServlet {
	private Logger log = LoggerFactory.getLogger(getClass());
	private static final long serialVersionUID = 1L;
	
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
        {
		   resp.setContentType("text/html;charset=GBK");  
	        PrintWriter out = resp.getWriter();  
	        out.println("<title>异步调用示例</title>");  
	        out.println("进入Servlet的时间："+new Date()+".<br/>");  
	        //创建AsyncContext，开始异步调用  
	        AsyncContext actx = req.startAsync();  
	        //设置异步调用的超时时长  
	        actx.setTimeout(30*1000);  
	        //启动异步调用的线程  
	        actx.start(new Executor(actx));  
	        out.println("结束Servlet的时间："+new Date()+".<br/>");  
	        out.flush();  
        }
	
	class Executor implements Runnable{
		private AsyncContext actx;
		
		Executor(AsyncContext actx){
			this.actx = actx;
		}
		
		@Override
		public void run() {
			try {
				System.out.println("go sleep");
				Thread.sleep(5*1000);
				log.info("do aysn at :" + new Date());
				ServletRequest request = actx.getRequest();  
				ServletResponse resp = actx.getResponse();
				actx.dispatch("/userinfo/show.do");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 	
		}
		
	}
	
	
}

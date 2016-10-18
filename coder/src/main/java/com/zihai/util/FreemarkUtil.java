package com.zihai.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkUtil {

    public Template getTemplate(String name) {
        try {
    		ApplicationContext context = new ClassPathXmlApplicationContext("spring/springMVC-servlet.xml");
    		FreeMarkerConfig  cfg = (FreeMarkerConfig ) context.getBean("freemarkerConfig");
            Template temp = cfg.getConfiguration().getTemplate(name);
            return temp;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ����̨���
     * 
     * @param name
     * @param root
     */
    public void print(String name, Map<String, Object> root) {
        try {
            // ͨ��Template���Խ�ģ���ļ��������Ӧ����
            Template temp = this.getTemplate(name);
            temp.process(root, new PrintWriter(System.out));
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ���HTML�ļ�
     * 
     * @param name
     * @param root
     * @param outFile
     */
    public void fprint(String name, Map<String, Object> root, String outFile) {
        FileWriter out = null;
        try {
            // ͨ��һ���ļ���������Ϳ���д����Ӧ���ļ��У��˴��õ��Ǿ���·��
            out = new FileWriter(new File("E:/workspace/freemarkprj/page/" + outFile));
            Template temp = this.getTemplate(name);
            temp.process(root, out);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    //���� 
    public static void main(String[] args){
    	FreemarkUtil util = new FreemarkUtil();
    	Map<String,Object> resultMap=  new HashMap<String, Object>();
    	resultMap.put("username", "liu");
    	util.print("page/test/hellow.ftl", resultMap);
    }
}
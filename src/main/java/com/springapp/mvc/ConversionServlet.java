package com.springapp.mvc;

/**
 * Created by yyy on 2016/5/13.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class ConversionServlet extends HttpServlet {
    public ConversionServlet () {
        super();
    }
    public void destroy() {
        super.destroy(); // Just puts "destroy" string in log
        // Put your code here
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        //----获取请求的URL
        String reqTarget = request.getParameter("name");
        //----指定对应JSP的HTML名称
        String name = reqTarget.substring(reqTarget.lastIndexOf("/") + 1,reqTarget.lastIndexOf("."));
        //---判断是否存在对应的HTML文件
        String serverName = request.getServerName();
        String localPort = String.valueOf(request.getLocalPort());
        String filePath = "";
        int lastIndex = reqTarget.lastIndexOf("/");
        int serverNameIndex = 0;
        if (reqTarget.indexOf(":"+localPort) != -1){
            serverName += ":" + localPort;
        }
        serverNameIndex = reqTarget.indexOf(serverName) + serverName.length() + 1;
        filePath = reqTarget.substring(serverNameIndex, lastIndex);

        File folder = new File(request.getRealPath("/") + "/" + filePath );
        if (!folder.exists()){
            folder.mkdirs();
        }
        File file = new File(folder.toString()+"/"+  name + ".html");
        if (!file.exists()) {  //--------如果不存在对应的HTML文件
            try {
                file.createNewFile();  //--------创建HTML文件
                //-------将JSP的内容写入对应的HTML文件内
                InputStream in;
                StringBuffer sb = new StringBuffer("");
                //----注意这里，不能直接访问请求的URL，如果直接访问的话，会陷入死循环
                URL url = new java.net.URL(reqTarget.toString()+"?type=11");
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setRequestProperty("User-Agent", "Mozilla/4.0");
                connection.connect();
                in = connection.getInputStream();
                java.io.BufferedReader breader = new BufferedReader(
                        new InputStreamReader(in, "GBK"));
                String currentLine;
                FileOutputStream fos = new FileOutputStream(file);
                while ((currentLine = breader.readLine()) != null) {
                    sb.append(currentLine);
                    fos.write(currentLine.getBytes());
                }
                if (null != breader)
                    breader.close();
                if (null != fos)
                    fos.close();
                //---------------转到与JSP对应的HTML页
                response.sendRedirect(request.getContextPath()+"/" + filePath +"/"+name + ".html");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            //----------如果存在指定的HTML，直接跳转到指定的HTML页
            response.sendRedirect(request.getContextPath()+"/" + filePath+ "/" + name + ".html");
        }
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    public void init() throws ServletException {
        // Put your code here
    }
}

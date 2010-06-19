package com.hr.profile.action;

import com.hr.util.PropertiesFileConfigManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

public class ShowImage extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("userinfo") == null) {
            response.sendRedirect(request.getContextPath() + "/configuration/login.jsp");
            return;
        }

        String style = request.getParameter("style");

        if ("head".equalsIgnoreCase(style)) {
            showUserHead(request, response);
        } else if ("news".equalsIgnoreCase(style)) {
            showNewsPic(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/configuration/login.jsp");
            return;
        }
    }

    private void showUserHead(HttpServletRequest request, HttpServletResponse response) {
        String fileName = request.getParameter("fileName");
        try {
            if ((fileName == null) || ("".equals(fileName.trim()))) {
                fileName = "None.JPG";
            }

            FileInputStream uFile = getFileInputStream(request, "sys.user.image.path", fileName);

            if (uFile == null) {
                uFile = new FileInputStream(request.getSession().getServletContext()
                        .getRealPath("/")
                        + "resource/images/None.JPG");
            }
            int i = uFile.available();
            byte[] data = new byte[i];
            uFile.read(data);
            uFile.close();
            response.setContentType("image/*");

            OutputStream toClient = response.getOutputStream();
            toClient.write(data);
            toClient.close();
        } catch (IOException e) {
        }
    }

    private void showNewsPic(HttpServletRequest request, HttpServletResponse response) {
        String fileName = request.getParameter("fileName");
        try {
            if ((fileName == null) || (fileName.trim().equals(""))) {
                return;
            }
            FileInputStream uFile = getFileInputStream(request, "sys.information.image.path",
                                                       fileName);

            if (uFile == null) {
                return;
            }
            int i = uFile.available();
            byte[] data = new byte[i];
            uFile.read(data);
            uFile.close();
            response.setContentType("image/*");
            OutputStream toClient = response.getOutputStream();
            toClient.write(data);
            toClient.close();
        } catch (IOException e) {
        }
    }

    private String getFileHomePath(HttpServletRequest request) {
        if (request == null)
            request = WebContextFactory.get().getHttpServletRequest();
        String path = request.getSession().getServletContext().getRealPath("");
        File file = new File(path);
        if (!file.exists())
            return path + "/file/";
        String newPath = file.getParentFile().getAbsolutePath();
        return newPath + "/file/";
    }

    private File getFile(HttpServletRequest request, String filePathConfig, String fileName) {
        try {
            String filePath = null;
            if (filePathConfig != null)
                filePath = getFileHomePath(request)
                        + PropertiesFileConfigManager.getInstance().getProperty(filePathConfig);
            else
                filePath = getFileHomePath(request);
            File file = new File(filePath + fileName);
            if (!file.exists())
                return null;
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private FileInputStream getFileInputStream(HttpServletRequest request, String filePathConfig,
            String fileName) {
        try {
            File file = getFile(request, filePathConfig, fileName);
            if (file == null)
                return null;
            return new FileInputStream(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.action.ShowImage JD-Core Version: 0.5.4
 */
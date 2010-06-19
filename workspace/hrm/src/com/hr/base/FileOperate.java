package com.hr.base;

import com.hr.configuration.dao.ISysconfigDAO;
import com.hr.configuration.dao.SysconfigDAOImpl;
import com.hr.configuration.domain.Sysconfig;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.PropertiesFileConfigManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.FileCopyUtils;

public class FileOperate extends BaseAction {
    private static final long serialVersionUID = 1L;
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";
    public static final String PROPERTY_ERROR = "property";
    public static final String LENGTH_ERROR = "fileTooLength";
    public static final String EXTEND_ERROR = "fileExtendNameError";

    public static String buildFile(String originalFile, String newFilePathConfig,
            String newFileName, String extendNameConfig, String lengthConfig) {
        try {
            File original = new File(originalFile);
            return buildFile(original, newFilePathConfig, newFileName, extendNameConfig,
                             lengthConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    public static String buildFile(File originalFile, String newFilePathConfig, String newFileName,
            String extendNameConfig, String lengthConfig) {
        try {
            int pointPlace = newFileName.lastIndexOf(".");
            if ((pointPlace == -1) || (pointPlace >= newFileName.length()))
                return "error";
            if (extendNameConfig != null) {
                String extendNameLimit = getProperty(extendNameConfig);
                String originalExtendName = newFileName.substring(pointPlace).toLowerCase();
                if ((extendNameLimit != null)
                        && (extendNameLimit.indexOf(originalExtendName) == -1))
                    return "fileExtendNameError";
            }
            if (lengthConfig != null) {
                String fileLengthLimit = getProperty(lengthConfig);
                if ((fileLengthLimit != null)
                        && (originalFile.length() > Integer.parseInt(fileLengthLimit.trim()) * 1024))
                    return "fileTooLength";
            }
            return buildFile(originalFile, newFilePathConfig, newFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "property";
    }

    public static String buildFile(String originalFilePath, String newFilePathConfig,
            String fileName) {
        try {
            File originalFile = new File(originalFilePath);
            return buildFile(originalFile, newFilePathConfig, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    public static String buildFile(File file, String filePathConfig, String fileName) {
        try {
            String filePath = null;
            if (filePathConfig != null) {
                filePath = getFileHomePath() + getProperty(filePathConfig);
            } else {
                filePath = getFileHomePath();
            }
            File newFile = new File(filePath);
            if (!newFile.exists()) {
                newFile.mkdirs();
            }
            String fileAbsolutPath = filePath + fileName;
            FileCopyUtils.copy(file, new File(fileAbsolutPath));
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        return "success";
    }

    public static FileInputStream getFileInputStream(String filePathConfig, String fileName) {
        try {
            File file = getFile(filePathConfig, fileName);
            if (file == null)
                return null;
            return new FileInputStream(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static File getFile(String filePathConfig, String fileName) {
        try {
            String filePath = null;
            if (filePathConfig != null) {
                filePath = getFileHomePath() + getProperty(filePathConfig);
            } else {
                filePath = getFileHomePath();
            }
            File file = new File(filePath + fileName);
            if (!file.exists())
                return null;
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String deleteFile(String filePathConfig, String fileName) {
        try {
            String deleteFilePath = null;
            if (filePathConfig != null) {
                deleteFilePath = getFileHomePath() + getProperty(filePathConfig) + fileName;
            } else {
                deleteFilePath = getFileHomePath() + fileName;
            }
            File file = new File(deleteFilePath);
            return deleteFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "property";
    }

    public static String deleteFile(File file) {
        if (file.exists()) {
            file.delete();
        }
        return "success";
    }

    public static String getFileHomePath() {
        HttpServletRequest request = ServletActionContext.getRequest();
        if (request == null) {
            request = WebContextFactory.get().getHttpServletRequest();
        }
        String path = request.getSession().getServletContext().getRealPath("");
        String filePath = new File(path).getParentFile().getAbsolutePath();
        ISysconfigDAO sysDAO = (SysconfigDAOImpl) SpringBeanFactory.getBean("sysDAO");
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Sysconfig.class);
        detachedCriteria.add(Restrictions.eq(Sysconfig.PROP_SYSCONFIG_KEY, "sys.file.path"));
        List list = sysDAO.findByCriteria(detachedCriteria);
        String readPath = "";
        if ((list != null) && (list.size() > 0)) {
            readPath = ((Sysconfig) list.get(0)).getSysconfigValue();
        }
        if (readPath.length() < 1) {
            path = filePath + "/file/";
            File file = new File(path);
            if (!file.exists())
                file.mkdirs();
            return path;
        }

        path = filePath + "/" + readPath;
        File file = new File(path);
        if (!file.exists())
            file.mkdirs();
        return path;
    }

    public static String buildFileName() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    private static String getProperty(String key) {
        PropertiesFileConfigManager configManager = PropertiesFileConfigManager.getInstance();
        return configManager.getProperty(key);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.base.FileOperate JD-Core Version: 0.5.4
 */
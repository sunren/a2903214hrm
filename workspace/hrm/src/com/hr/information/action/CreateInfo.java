package com.hr.information.action;

import com.hr.base.BaseAction;
import com.hr.base.Constants;
import com.hr.base.FileOperate;
import com.hr.information.bo.IInformationBo;
import com.hr.information.domain.Information;
import com.hr.profile.domain.Employee;
import com.hr.security.domain.Userinfo;
import com.opensymphony.xwork2.ActionContext;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import org.apache.struts2.ServletActionContext;

public class CreateInfo extends BaseAction implements Constants {
    private static final long serialVersionUID = -4234940898871087425L;
    private List classList;
    private Information info;
    private File file;
    private File imgFile;
    private String fileContentType;
    private String imgFileContentType;
    private String fileFileName;
    private String imgFileFileName;

    public String execute() throws Exception {
        IInformationBo infoBo = (IInformationBo) getBean("informationBo");
        Map session = ServletActionContext.getContext().getSession();
        Userinfo viewUser = (Userinfo) session.get("userinfo");
        this.classList = infoBo.getInfoClassBySortId();
        if (this.info != null) {
            if ((this.info.getId() == null) || (this.info.getId().trim().length() == 0)) {
                if (!UploadFile(null))
                    return "input";
                if (!UploadImg(null))
                    return "input";
                this.info.setInfoContent(this.info.getInfoContent().replace("\n\r", "<br>"));
                this.info.setInfoCreateBy(new Employee(viewUser.getId()));
                this.info.setInfoCreateTime(new Date());
                this.info.setInfoLastChangeBy(new Employee(viewUser.getId()));
                this.info.setInfoLastChangeTime(new Date());
                this.info.setInfoViewedNum(Integer.valueOf(0));
                infoBo.insertInfo(this.info);
            } else if (this.info.getId().length() > 0) {
                Information originalInfo = infoBo.loadOne(this.info.getId());
                if (originalInfo == null)
                    return "input";
                this.info.setInfoFileName(originalInfo.getInfoFileName());

                this.info.setInfoPicName(originalInfo.getInfoPicName());

                if ((this.file != null) && (!UploadFile(this.info.getInfoFileName())))
                    return "input";
                if ((this.imgFile != null) && (!UploadImg(this.info.getInfoPicName())))
                    return "input";
                this.info.setInfoContent(this.info.getInfoContent().replace("\n\r", "<br>"));
                this.info.setInfoLastChangeBy(new Employee(viewUser.getId()));
                this.info.setInfoLastChangeTime(new Date());
                infoBo.updateInfo(this.info);
            }
            return "success";
        }
        return "input";
    }

    public boolean UploadImg(String picName) throws Exception {
        if (this.imgFile == null)
            return true;
        if (picName != null)
            FileOperate.deleteFile("sys.information.image.path", picName);
        int index = this.imgFileFileName.lastIndexOf(".");
        if ((index == -1) || (index >= this.imgFileFileName.length()))
            return false;
        String extendName = this.imgFileFileName.substring(this.imgFileFileName.lastIndexOf("."));
        String newFileName = FileOperate.buildFileName() + extendName;
        String callBack = FileOperate.buildFile(this.imgFile, "sys.information.image.path",
                                                newFileName, "sys.information.image.type",
                                                "sys.information.image.length");
        if ("fileExtendNameError".equals(callBack)) {
            addFieldError("imgFile", "文件类型不匹配！");
            return false;
        }
        if ("fileTooLength".equals(callBack)) {
            addFieldError("imgFile", "上传文件太大");
            return false;
        }
        if ("property".equals(callBack)) {
            addFieldError("imgFile", "上传文件路径配置错误");
            return false;
        }
        if ("fileTooLength".equals(callBack))
            return false;
        File originalImgFile = FileOperate.getFile("sys.information.image.path", newFileName);
        Image src = ImageIO.read(originalImgFile);
        if (src == null)
            return false;
        float tagsize = 80.0F;
        int old_w = src.getWidth(null);
        int old_h = src.getHeight(null);
        int new_w = 0;
        int new_h = 0;
        float tempdouble;
        if (old_w > old_h)
            tempdouble = old_w / tagsize;
        else
            tempdouble = old_h / tagsize;
        new_w = Math.round(old_w / tempdouble);
        new_h = Math.round(old_h / tempdouble);
        BufferedImage tag = new BufferedImage(new_w, new_h, 1);
        tag.getGraphics().drawImage(src, 0, 0, new_w, new_h, null);
        String originalFilePath = originalImgFile.getParent() + "/";
        String originalName = originalImgFile.getName().substring(
                                                                  0,
                                                                  originalImgFile.getName()
                                                                          .lastIndexOf("."));
        String newUrl = originalFilePath + originalName + "_s" + extendName;
        FileOutputStream newimage = new FileOutputStream(newUrl);
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
        encoder.encode(tag);
        newimage.close();
        this.info.setInfoPicName(originalName + "_s" + extendName);
        return true;
    }

    public boolean UploadFile(String fName) throws Exception {
        if (this.file == null)
            return true;
        if (fName != null)
            FileOperate.deleteFile("sys.information.file.path", fName);
        int index = this.fileFileName.lastIndexOf(".");
        if ((index == -1) || (index >= this.fileFileName.length()))
            return false;
        String extendName = this.fileFileName.substring(this.fileFileName.lastIndexOf("."));
        String newFileName = FileOperate.buildFileName() + extendName;
        String callBack = FileOperate.buildFile(this.file, "sys.information.file.path",
                                                newFileName, "sys.information.file.type",
                                                "sys.information.file.length");
        if ("fileExtendNameError".equals(callBack)) {
            addFieldError("file", "文件类型不匹配！");
            return false;
        }
        if ("fileTooLength".equals(callBack)) {
            addFieldError("file", "上传文件太大");
            return false;
        }
        if ("property".equals(callBack)) {
            addFieldError("file", "上传文件路径配置错误");
            return false;
        }
        if ("fileTooLength".equals(callBack))
            return false;
        this.info.setInfoFileName(newFileName);
        return true;
    }

    public List getClassList() {
        return this.classList;
    }

    public void setClassList(List classList) {
        this.classList = classList;
    }

    public Information getInfo() {
        return this.info;
    }

    public void setInfo(Information info) {
        this.info = info;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileContentType() {
        return this.fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public String getFileFileName() {
        return this.fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public File getImgFile() {
        return this.imgFile;
    }

    public void setImgFile(File imgFile) {
        this.imgFile = imgFile;
    }

    public String getImgFileContentType() {
        return this.imgFileContentType;
    }

    public void setImgFileContentType(String imgFileContentType) {
        this.imgFileContentType = imgFileContentType;
    }

    public String getImgFileFileName() {
        return this.imgFileFileName;
    }

    public void setImgFileFileName(String imgFileFileName) {
        this.imgFileFileName = imgFileFileName;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.information.action.CreateInfo JD-Core Version: 0.5.4
 */
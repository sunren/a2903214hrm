package com.hr.io.action;

import com.hr.base.BaseAction;
import com.hr.io.bo.IInmatchBO;
import com.hr.io.bo.IInmatchModelBO;
import com.hr.io.domain.Inmatch;
import com.hr.io.domain.InmatchBasic;
import com.hr.io.domain.InmatchModel;
import com.hr.io.domain.Iodef;
import com.hr.io.extend.CommonParameters;
import com.hr.io.extend.ICheckAndInsert;
import com.hr.io.extend.IOMessageSingle;
import com.hr.io.extend.IOMessages;
import com.hr.io.extend.ReadExcelByImList;
import com.hr.profile.domain.Employee;
import com.hr.security.domain.Userinfo;
import com.hr.util.Message;
import com.hr.util.reflect.SetObjectProperty;
import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

public class Import extends BaseAction {
    private static final long serialVersionUID = 1L;
    private String importParameter;
    private File upload;
    private String uploadFileName;
    private String uploadContentType;
    private String immId;
    private List<Message> msgTopList;
    private List<Message> msgDetailList;
    String msgModelIlleagle;
    String msgFileTypeError;
    String msgAllNum;
    String msgRepeatNum;
    String msgNoAuth;

    public Import() {
        this.msgTopList = new ArrayList();

        this.msgDetailList = new ArrayList();

        this.msgModelIlleagle = "模板参数非法";
        this.msgFileTypeError = "文件类型出错";
        this.msgAllNum = "本次导入总计{0}条数据，成功导入{1}条数捄1�7";
        this.msgRepeatNum = "出现重复记录{0}杄1�7";
        this.msgNoAuth = "您没有相应的权限";
    }

    public String execute() throws Exception {
        IOMessages ioMessages = new IOMessages();
        IInmatchModelBO inmatchModelBO = (IInmatchModelBO) getBean("inmatchModelBO");
        InmatchModel inmatchModel = inmatchModelBO
                .loadObject(this.immId, new String[] { InmatchModel.PROP_IMM_IO });
        if (inmatchModel == null) {
            this.msgTopList.add(new Message(Integer.valueOf(-1), this.msgModelIlleagle));
            return "success";
        }
        Iodef iodef = inmatchModel.getImmIo();

        String authorityCondition = checkActionAuth("import", iodef.getIoName());
        if ("error".compareTo(authorityCondition) == 0) {
            this.msgTopList.add(new Message(Integer.valueOf(-1), this.msgNoAuth));
            return "success";
        }

        int hasTitle = 1 - inmatchModel.getImmNoTitle().intValue();
        IInmatchBO inmatchBO = (IInmatchBO) getBean("inmatchBO");
        List<Inmatch> imList = inmatchBO.getInputList(inmatchModel);
        inmatchModel.setImList(imList);
        List objectList;
        try {
            if (inmatchModel.getImmInputType().equals("excel")) {
                if (this.uploadFileName.substring(this.uploadFileName.lastIndexOf(".") + 1)
                        .compareTo("xls") != 0) {
                    this.msgTopList.add(new Message(Integer.valueOf(-1), this.msgFileTypeError));
                    return "success";
                }
                ioMessages.setMsgFormat("excel");
                ReadExcelByImList readExcelByImList = new ReadExcelByImList(this.upload, imList,
                        hasTitle, ioMessages);
                objectList = readExcelByImList.getContent();
            } else {
                this.msgTopList.add(new Message(Integer.valueOf(-1), this.msgFileTypeError));
                return "success";
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.msgTopList.add(new Message(Integer.valueOf(-1), this.msgFileTypeError));
            return "success";
        }

        if (!ioMessages.hasErrorMsg()) {
            int totalNum = ((List) objectList.get(0)).size();

            Class classDefinition = Class.forName(iodef.getIoClassName());

            List resultList = new ArrayList();
            for (int i = 0; i < totalNum; ++i) {
                resultList.add(classDefinition.newInstance());
            }

            SetObjectProperty setObjectProperty = new SetObjectProperty();
            for (int i = 0; i < imList.size(); ++i) {
                Inmatch imTmp = (Inmatch) imList.get(i);
                List colTmp = (List) objectList.get(i);
                setObjectProperty.initial(classDefinition, imTmp.getImImb().getImbFieldName());
                for (int j = 0; j < totalNum; ++j) {
                    setObjectProperty.setProperty(resultList.get(j), colTmp.get(j));
                }

            }

            ICheckAndInsert checkAndInsert = (ICheckAndInsert) Class
                    .forName("com.hr.io.extend." + iodef.getIoName()).newInstance();

            Map matchMap = new HashMap();
            for (Inmatch imTmp : imList) {
                matchMap.put(imTmp.getImImb().getImbFieldName(), imTmp);
            }

            Employee currEmp = ((Userinfo) getSession().getAttribute("userinfo")).getEmployee();

            CommonParameters commonParas = new CommonParameters(inmatchModel, matchMap, ioMessages,
                    currEmp, authorityCondition, new String[] { this.importParameter });

            int[] insertResult = checkAndInsert.insertTransmit(resultList, commonParas);
            if (insertResult[1] > 0) {
                ioMessages.addMessageToFirst(new IOMessageSingle(MessageFormat
                        .format(this.msgRepeatNum,
                                new Object[] { Integer.valueOf(insertResult[1]) }), Integer
                        .valueOf(1), new Integer[0]));
            }
            ioMessages
                    .addMessageToFirst(new IOMessageSingle(MessageFormat
                            .format(this.msgAllNum, new Object[] { Integer.valueOf(totalNum),
                                    Integer.valueOf(insertResult[0]) }), Integer.valueOf(0),
                            new Integer[0]));
        }

        ioMessages.sortMessages(new int[] { 0, 1 });
        List msgList = ioMessages.getShowMessageByTopDetail();
        this.msgTopList = ((List) msgList.get(0));
        this.msgDetailList = ((List) msgList.get(1));
        return "success";
    }

    public String getImmId() {
        return this.immId;
    }

    public void setImmId(String immId) {
        this.immId = immId;
    }

    public List<Message> getMsgTopList() {
        return this.msgTopList;
    }

    public List<Message> getMsgDetailList() {
        return this.msgDetailList;
    }

    public void setUpload(File upload) {
        this.upload = upload;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public void setUploadContentType(String uploadContentType) {
        this.uploadContentType = uploadContentType;
    }

    public String getImportParameter() {
        return this.importParameter;
    }

    public void setImportParameter(String importParameter) {
        this.importParameter = importParameter;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.io.action.Import JD-Core Version: 0.5.4
 */
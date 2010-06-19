package com.hr.examin.bo;

import com.hr.base.wf.Workflow;
import com.hr.configuration.bo.IEmailsendBO;
import com.hr.configuration.bo.IEmailtemplateBO;
import com.hr.configuration.bo.ISysLogBO;
import com.hr.configuration.domain.Emailtemplate;
import com.hr.examin.bo.interfaces.IExaminBo;
import com.hr.examin.dao.interfaces.ILeaverequestDAO;
import com.hr.examin.dao.interfaces.IOvertimerequestDAO;
import com.hr.examin.domain.Leaverequest;
import com.hr.examin.domain.Overtimerequest;
import com.hr.spring.SpringBeanFactory;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExaminBoImpl implements IExaminBo {
    protected ISysLogBO sl_BO;
    protected IEmailsendBO es_BO;
    protected ILeaverequestDAO lr_DAO;
    protected IOvertimerequestDAO or_DAO;

    public synchronized Map<String, StringBuffer> saveOrUpdateExaminData(Object[] objList) {
        Map templateMap = getTemplateMap();

        Workflow wf = null;
        Map msgMap = new HashMap();
        String operate = null;
        for (Object obj : objList) {
            if (obj instanceof Leaverequest)
                wf = ((Leaverequest) obj).getWorkflow();
            else if (obj instanceof Overtimerequest) {
                wf = ((Overtimerequest) obj).getWorkflow();
            }

            operate = wf.getOperate();
            if (operate.contains("create"))
                wf.setDocNo(getMaxId(obj, new Integer[0]));

            if (!Workflow.setWorkflowFields(wf, templateMap, msgMap)) {
                return msgMap;
            }
            Workflow.msgMapAdd(wf, msgMap);
        }

        List dataList = Arrays.asList(objList);

        this.sl_BO.addLog(dataList);

        this.es_BO.saveAndSendMail(dataList);

        this.lr_DAO.saveOrupdate(dataList);
        return msgMap;
    }

    private synchronized Integer getMaxId(Object obj, Integer[] currentNo) {
        if ((currentNo.length > 0) && (currentNo[0] != null)) {
            return Integer.valueOf(currentNo[0].intValue() + 1);
        }

        String className = null;
        String propertyName = null;
        if (obj instanceof Leaverequest) {
            className = "Leaverequest";
            propertyName = Leaverequest.PROP_LR_NO;
        } else if (obj instanceof Overtimerequest) {
            className = "Overtimerequest";
            propertyName = Overtimerequest.PROP_OR_NO;
        }

        List maxIDList = this.lr_DAO.exeHqlList("select max(" + propertyName + ") from "
                + className);
        Integer maxID;
        if ((maxIDList == null) || (maxIDList.size() < 1) || (maxIDList.get(0) == null))
            maxID = Integer.valueOf(1);
        else {
            maxID = Integer.valueOf(1 + ((Integer) (Integer) maxIDList.get(0)).intValue());
        }
        return maxID;
    }

    private Map<String, Emailtemplate> getTemplateMap() {
        IEmailtemplateBO templateBo = (IEmailtemplateBO) SpringBeanFactory
                .getBean("emailtemplateBO");
        Map rs = new HashMap();
        List<Emailtemplate> templateList = templateBo.getList();
        for (Emailtemplate tmp : templateList) {
            rs.put(tmp.getEtTitleNo(), tmp);
        }
        return rs;
    }

    public void setSl_BO(ISysLogBO slBO) {
        this.sl_BO = slBO;
    }

    public void setLr_DAO(ILeaverequestDAO lrDAO) {
        this.lr_DAO = lrDAO;
    }

    public void setEs_BO(IEmailsendBO esBO) {
        this.es_BO = esBO;
    }

    public void setOr_DAO(IOvertimerequestDAO orDAO) {
        this.or_DAO = orDAO;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.bo.ExaminBoImpl JD-Core Version: 0.5.4
 */
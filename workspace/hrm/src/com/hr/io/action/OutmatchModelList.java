package com.hr.io.action;

import com.hr.base.BaseAction;
import com.hr.base.DWRUtil;
import com.hr.io.bo.IIodefBo;
import com.hr.io.bo.IOutmatchBO;
import com.hr.io.bo.IOutmatchBasicBO;
import com.hr.io.bo.IOutmatchModelBO;
import com.hr.io.domain.Iodef;
import com.hr.io.domain.Outmatch;
import com.hr.io.domain.OutmatchBasic;
import com.hr.io.domain.OutmatchModel;
import com.hr.util.output.FormatToString;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BeanComparator;

public class OutmatchModelList extends BaseAction {
    private static final long serialVersionUID = 1L;
    private String ioId;
    private List<OutmatchModel> ommList;
    private Iodef iodef;

    public String execute() throws Exception {
        if (!setIodef(this.ioId)) {
            return "input";
        }
        IOutmatchModelBO outmatchModelBO = (IOutmatchModelBO) getBean("outmatchModelBO");
        this.ommList = outmatchModelBO.getListByIodef(this.iodef);
        return "success";
    }

    private boolean setIodef(String ioId) {
        if ((ioId == null) || (ioId.length() < 1)) {
            return false;
        }
        IIodefBo iodefBo = (IIodefBo) getBean("iodefbo");
        this.iodef = iodefBo.loadObject(ioId);
        if (this.iodef == null) {
            System.out.println("==========ioId is illeagle");
            return false;
        }
        return true;
    }

    public OutmatchModel loadOutmatchModel(String ioId, String ommId) throws Exception {
        if ("error".equalsIgnoreCase(DWRUtil.checkAuth("iodef", "view"))) {
            System.out.println("==========error: has no auth");
            return null;
        }
        if (!setIodef(ioId)) {
            return null;
        }
        IOutmatchModelBO outmatchModelBO = (IOutmatchModelBO) getBean("outmatchModelBO");
        OutmatchModel outmatchModel = null;
        if ((ommId != null) && (ommId.length() > 0)) {
            outmatchModel = outmatchModelBO.getObject(this.iodef, ommId);
            if (outmatchModel == null) {
                System.out.println("==========ommId is illeagle");
                return null;
            }
            IOutmatchBO outmatchBO = (IOutmatchBO) getBean("outmatchBO");
            List omList = outmatchBO.getFullOutmatchList(outmatchModel);
            outmatchBO.initOutmatchMaps(omList);
            outmatchModel.setOmList(omList);
        } else {
            if ("error".equalsIgnoreCase(DWRUtil.checkAuth("iodef", "edit"))) {
                System.out.println("==========error: has no auth");
                return null;
            }
            outmatchModel = new OutmatchModel();

            IOutmatchBasicBO outmatchBasicBO = (IOutmatchBasicBO) getBean("outmatchBasicBO");
            outmatchModel.setOmList(outmatchBasicBO.createOutmatch(this.iodef));
            outmatchModel.setOmmNoTitle(Integer.valueOf(0));
            outmatchModel.setOmmRowHeight(Integer.valueOf(25));
        }
        outmatchModel.setAllMaps();
        outmatchModel.setOmmIo(this.iodef);
        return outmatchModel;
    }

    public String saveOrUpdateOutmatchModel(OutmatchModel outmatchModel, List<Outmatch> omList)
            throws Exception {
        if ("error".equalsIgnoreCase(DWRUtil.checkAuth("iodef", "edit"))) {
            return "error: has no auth";
        }

        if (outmatchModel == null) {
            return "outmatchModel is illeagle";
        }
        if ((outmatchModel.getOmmId() == null) || (outmatchModel.getOmmId().length() < 1)) {
            outmatchModel.setOmmId(null);
        }
        if (omList == null) {
            omList = new ArrayList();
        }
        OutmatchModel outmatchModelDB = loadOutmatchModel(outmatchModel.getOmmIo().getIoId(),
                                                          outmatchModel.getOmmId());
        if (outmatchModelDB == null) {
            return "outmatchModel is illeagle";
        }

        outmatchModelDB.setOmmName(outmatchModel.getOmmName().trim());
        outmatchModelDB.setOmmDesc(outmatchModel.getOmmDesc().trim());
        outmatchModelDB.setOmmRowHeight(outmatchModel.getOmmRowHeight());

        if ((outmatchModelDB.getOmmNoTitleMap().containsKey(outmatchModel.getOmmNoTitle()
                .toString()))
                && (outmatchModelDB.getOmmOutputTypeMap().containsKey(outmatchModel
                        .getOmmOutputType()))
                && (outmatchModelDB.getOmmStatisticPlaceMap().containsKey(outmatchModel
                        .getOmmStatisticPlace().toString()))
                && (outmatchModelDB.getOmmStatisticDisplayTypeMap().containsKey(outmatchModel
                        .getOmmStatisticDisplayType().toString()))) {
            outmatchModelDB.setOmmNoTitle(outmatchModel.getOmmNoTitle());
            outmatchModelDB.setOmmOutputType(outmatchModel.getOmmOutputType());
            outmatchModelDB.setOmmStatisticPlace(outmatchModel.getOmmStatisticPlace());
            outmatchModelDB.setOmmStatisticDisplayType(outmatchModel.getOmmStatisticDisplayType());
        } else {
            System.out.println("==========omm Maps are illegal");
            return "fail";
        }
        Date nowDate = new Date();
        outmatchModelDB.setOmmLastChangeBy(getCurrentEmpNo());
        outmatchModelDB.setOmmLastChangeTime(nowDate);
        if (outmatchModelDB.getOmmId() == null) {
            outmatchModelDB.setOmmCreateBy(getCurrentEmpNo());
            outmatchModelDB.setOmmCreateTime(nowDate);
            outmatchModelDB.setOmmDefault(Integer.valueOf(0));
        }

        Map<String, Outmatch> omDbMap = new HashMap();

        for (Outmatch omTmp : outmatchModelDB.getOmList()) {
            omDbMap.put(omTmp.getOmOmb().getOmbId(), omTmp);
        }
        FormatToString formatToString = new FormatToString();
        for (int i = omList.size() - 1; i >= 0; --i) {
            Outmatch omTmp = (Outmatch) omList.get(i);

            if ((omTmp.getOmIsOutput() == null) || (omTmp.getOmIsOutput().intValue() != 1)) {
                omList.remove(i);
            } else {
                if (omTmp.getOmOmb() == null) {
                    return "omOmb is null";
                }

                if (omDbMap.containsKey(omTmp.getOmOmb().getOmbId())) {
                    omTmp
                            .setOmOmb(((Outmatch) omDbMap.get(omTmp.getOmOmb().getOmbId()))
                                    .getOmOmb());

                    omDbMap.remove(omTmp.getOmOmb().getOmbId());
                } else {
                    return "omOmb.ombId does not exist";
                }

                if ((omTmp.getOmFieldDesc() == null) || (omTmp.getOmIsGroup() == null)
                        || (omTmp.getOmSortId() == null)) {
                    return omTmp.getOmFieldDesc() + ": required parameter is null";
                }

                if (formatToString
                        .setFormat(omTmp.getOmOmb().getOmbDataType(), omTmp.getOmFormat()))
                    omTmp.setOmFormat(formatToString.getFormat());
                else {
                    return omTmp.getOmFieldDesc() + ": omFormat is illeagle";
                }

                if ((omTmp.getOmStatistic() != null) && (omTmp.getOmStatistic().length() == 0)) {
                    omTmp.setOmStatistic(null);
                }
                if ((omTmp.getOmColumnWidth() != null) && (omTmp.getOmColumnWidth().intValue() < 1)) {
                    omTmp.setOmColumnWidth(null);
                }

                omTmp.setAllMaps();
                if ((!omTmp.getOmIsGroupMap().containsKey(omTmp.getOmIsGroup().toString()))
                        || ((omTmp.getOmStatistic() != null) && (!omTmp.getOmStatisticMap()
                                .containsKey(omTmp.getOmStatistic())))) {
                    return omTmp.getOmFieldDesc() + ": map parameter is null";
                }
                omTmp.setOmOmm(outmatchModelDB);
            }
        }
        Collections.sort(omList, new BeanComparator(Outmatch.PROP_OM_SORT_ID));
        for (int i = 0; i < omList.size(); ++i) {
            Outmatch tmpOutmatch = (Outmatch) omList.get(i);
            tmpOutmatch.setOmSortId(Integer.valueOf(i));
        }
        outmatchModelDB.setOmList(omList);
        List omDelList = new ArrayList();
        for (Outmatch omTmp : omDbMap.values()) {
            if (omTmp.getOmId() != null) {
                omDelList.add(omTmp);
            }

        }

        IOutmatchModelBO outmatchModelBO = (IOutmatchModelBO) getBean("outmatchModelBO");
        outmatchModelBO.saveOrUpdateOutmatchModel(outmatchModelDB, omDelList);
        return "success";
    }

    public String setDefaultOutmatchModel(String ioId, String ommId) throws Exception {
        if ("error".equalsIgnoreCase(DWRUtil.checkAuth("iodef", "edit"))) {
            System.out.println("==========error: has no auth");
            return "success";
        }

        IOutmatchModelBO outmatchModelBO = (IOutmatchModelBO) getBean("outmatchModelBO");
        OutmatchModel outmatchModel = outmatchModelBO.getObject(new Iodef(ioId), ommId);
        if (outmatchModel == null) {
            System.out.println("==========ommId doesn't exist:" + ommId);
            return "success";
        }
        if (outmatchModel.getOmmDefault().intValue() == 1) {
            System.out.println("==========ommId is default:" + ommId);
            return "success";
        }

        OutmatchModel ommDefault = outmatchModelBO.loadDefaultObject(new Iodef(ioId));
        List outmatchModels = new ArrayList();
        Date nowDate = new Date();
        ommDefault.setOmmDefault(Integer.valueOf(0));
        ommDefault.setOmmLastChangeBy(getCurrentEmpNo());
        ommDefault.setOmmLastChangeTime(nowDate);
        outmatchModel.setOmmDefault(Integer.valueOf(1));
        outmatchModel.setOmmLastChangeBy(getCurrentEmpNo());
        outmatchModel.setOmmLastChangeTime(nowDate);
        outmatchModels.add(ommDefault);
        outmatchModels.add(outmatchModel);

        outmatchModelBO.saveOrUpdate(outmatchModels);
        return outmatchModel.getOmmId() + ":" + ommDefault.getOmmId();
    }

    public String deleteOutmatchModel(String ioId, String ommId) throws Exception {
        if ("error".equalsIgnoreCase(DWRUtil.checkAuth("iodef", "edit"))) {
            System.out.println("==========error: has no auth");
            return "success";
        }

        IOutmatchModelBO outmatchModelBO = (IOutmatchModelBO) getBean("outmatchModelBO");
        OutmatchModel outmatchModel = outmatchModelBO.getObject(new Iodef(ioId), ommId);
        if (outmatchModel == null) {
            System.out.println("==========ommId doesn't exist:" + ommId);
            return "success";
        }
        if (outmatchModel.getOmmDefault().intValue() == 1) {
            System.out.println("==========ommId is default:" + ommId);
            return "success";
        }

        IOutmatchBO outmatchBO = (IOutmatchBO) getBean("outmatchBO");
        List omList = outmatchBO.getOutmatchList(outmatchModel);
        outmatchModel.setOmList(omList);
        outmatchModelBO.deleteOutmatchModel(outmatchModel);
        return outmatchModel.getOmmId();
    }

    public String checkDataTypeFormat(String dataType, String format, Integer trIndex) {
        if (trIndex == null) {
            return "error";
        }
        FormatToString formatToString = new FormatToString();
        String result = "";
        if (!formatToString.setFormat(dataType, format)) {
            result = "-" + trIndex.toString() + ":";
        }
        result = result + ((formatToString.getFormat() == null) ? "" : formatToString.getFormat());
        return result;
    }

    public List<OutmatchModel> getOutmatchModelList(String ioName) {
        IIodefBo iodefBO = (IIodefBo) getBean("iodefbo");
        IOutmatchModelBO outmatchModelBO = (IOutmatchModelBO) getBean("outmatchModelBO");
        Iodef iodef = iodefBO.loadObjectByName(ioName);
        List result = outmatchModelBO.getListByIodef(iodef);
        for (int i = 0; i < result.size(); ++i) {
            ((OutmatchModel) result.get(i)).setOmmIo(iodef);
        }
        return result;
    }

    public void setIoId(String ioId) {
        this.ioId = ioId;
    }

    public String getIoId() {
        return this.ioId;
    }

    public List<OutmatchModel> getOmmList() {
        return this.ommList;
    }

    public Iodef getIodef() {
        return this.iodef;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.io.action.OutmatchModelList JD-Core Version: 0.5.4
 */
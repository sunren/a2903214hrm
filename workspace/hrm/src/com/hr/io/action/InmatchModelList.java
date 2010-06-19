package com.hr.io.action;

import com.hr.base.BaseAction;
import com.hr.base.DWRUtil;
import com.hr.io.bo.IInmatchBO;
import com.hr.io.bo.IInmatchBasicBO;
import com.hr.io.bo.IInmatchModelBO;
import com.hr.io.bo.IIodefBo;
import com.hr.io.domain.Inmatch;
import com.hr.io.domain.InmatchBasic;
import com.hr.io.domain.InmatchModel;
import com.hr.io.domain.Iodef;
import com.hr.util.input.StringToObject;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BeanComparator;

public class InmatchModelList extends BaseAction {
    private static final long serialVersionUID = 1L;
    private String ioId;
    private List<InmatchModel> immList;
    private Iodef iodef;

    public String execute() throws Exception {
        if (!setIodef(this.ioId)) {
            return "input";
        }
        IInmatchModelBO inmatchModelBO = (IInmatchModelBO) getBean("inmatchModelBO");
        this.immList = inmatchModelBO.getListByIodef(this.iodef);
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

    public InmatchModel loadInmatchModel(String ioId, String immId) throws Exception {
        if ("error".equalsIgnoreCase(DWRUtil.checkAuth("iodef", "view"))) {
            System.out.println("==========error: has no auth");
            return null;
        }
        if (!setIodef(ioId)) {
            return null;
        }
        InmatchModel inmatchModel = null;
        if ((immId != null) && (immId.length() > 0)) {
            IInmatchModelBO inmatchModelBO = (IInmatchModelBO) getBean("inmatchModelBO");
            inmatchModel = inmatchModelBO.getObject(this.iodef, immId);
            if (inmatchModel == null) {
                System.out.println("==========immId is illeagle");
                return null;
            }
            IInmatchBO inmatchBO = (IInmatchBO) getBean("inmatchBO");
            List imList = inmatchBO.getInmatchList(inmatchModel);
            inmatchBO.initInmatchMaps(imList);
            inmatchModel.setImList(imList);
        } else {
            if ("error".equalsIgnoreCase(DWRUtil.checkAuth("iodef", "edit"))) {
                System.out.println("==========error: has no auth");
                return null;
            }
            IInmatchBasicBO inmatchBasicBO = (IInmatchBasicBO) getBean("inmatchBasicBO");
            inmatchModel = new InmatchModel();
            inmatchModel.setImList(inmatchBasicBO.createInmatch(this.iodef));
        }
        inmatchModel.setAllMaps();
        inmatchModel.setImmIo(this.iodef);
        return inmatchModel;
    }

    /*
     * public String saveOrUpdateInmatchModel(InmatchModel inmatchModel, List<Inmatch> imList)
     * throws Exception { if ("error".equalsIgnoreCase(DWRUtil.checkAuth("iodef", "edit"))) {
     * System.out.println("==========error: has no auth"); return "fail"; } Date nowDate = new
     * Date();
     * 
     * if (inmatchModel == null) { System.out.println("==========inmatchModel is illeagle"); return
     * "fail"; } if ((inmatchModel.getImmId() != null) && (inmatchModel.getImmId().length() < 1)) {
     * inmatchModel.setImmId(null); } if (imList == null) { imList = new ArrayList(); } InmatchModel
     * inmatchModelDB = loadInmatchModel(inmatchModel.getImmIo().getIoId(),
     * inmatchModel.getImmId()); if (inmatchModelDB == null) {
     * System.out.println("==========inmatchModel is illeagle"); return "fail"; }
     * 
     * if ((inmatchModel.getImmDesc() == null) || (inmatchModel.getImmDesc().length() < 1) ||
     * (inmatchModel.getImmName() == null) || (inmatchModel.getImmName().length() < 1) ||
     * (inmatchModel.getImmInputType() == null) || (inmatchModel.getImmInputType().length() < 1)) {
     * System.out.println("==========inmatchModel string parameter is illeagle"); return "fail"; }
     * if ((inmatchModel.getImmImportMode() == null) || (inmatchModel.getImmNoTitle() == null)) {
     * System.out.println("==========inmatchModel integer parameter is illeagle"); return "fail"; }
     * if
     * ((!inmatchModelDB.getImmImportModeMap().containsKey(inmatchModel.getImmImportMode().toString
     * ())) ||
     * (!inmatchModelDB.getImmNoTitleMap().containsKey(inmatchModel.getImmNoTitle().toString())) ||
     * (!inmatchModelDB.getImmInputTypeMap().containsKey(inmatchModel.getImmInputType()))) {
     * System.out.println("==========inmatchModel map parameter is illeagle"); return "fail"; }
     * 
     * List inmatchDefaultList = inmatchModelDB.getImList(); if (inmatchDefaultList.size() !=
     * imList.size()) { System.out.println("==========imList is illeagle"); return "fail"; }
     * 
     * inmatchModelDB.setImmName(inmatchModel.getImmName());
     * inmatchModelDB.setImmDesc(inmatchModel.getImmDesc());
     * inmatchModelDB.setImmInputType(inmatchModel.getImmInputType());
     * inmatchModelDB.setImmImportMode(inmatchModel.getImmImportMode());
     * inmatchModelDB.setImmNoTitle(inmatchModel.getImmNoTitle());
     * inmatchModelDB.setImmLastChangeBy(getCurrentEmpNo());
     * inmatchModelDB.setImmLastChangeTime(nowDate); if (inmatchModelDB.getImmId() == null) {
     * inmatchModelDB.setImmCreateBy(getCurrentEmpNo()); inmatchModelDB.setImmCreateTime(nowDate);
     * inmatchModelDB.setImmDefault(Integer.valueOf(0)); }
     * 
     * Collections.sort(imList, new BeanComparator(Inmatch.PROP_IM_IMB + "." +
     * InmatchBasic.PROP_IMB_ID)); Collections.sort(inmatchDefaultList, new
     * BeanComparator(Inmatch.PROP_IM_IMB + "." + InmatchBasic.PROP_IMB_ID)); StringToObject
     * formatToObject = new StringToObject(); int outputNum = 0;
     * 
     * for (int i = 0; i < inmatchDefaultList.size(); ++i) { Inmatch dbInmatch =
     * (Inmatch)inmatchDefaultList.get(i); Inmatch formInmatch = (Inmatch)imList.get(i); if
     * (dbInmatch.getImImb().getImbId().compareTo(formInmatch.getImImb().getImbId()) != 0) {
     * System.out.println("==========error: imImb.imbId is not correct"); return "fail"; }
     * 
     * if ((formInmatch.getImIsInput() == null) || (formInmatch.getImIsInput().intValue() != 1))
     * formInmatch.setImIsInput(Integer.valueOf(0)); else { ++outputNum; }
     * dbInmatch.setImIsInput(formInmatch.getImIsInput()); if ((formInmatch.getImFormat() != null)
     * && (formInmatch.getImFormat().length() < 1)) { formInmatch.setImFormat(null); }
     * dbInmatch.setImFormat(formInmatch.getImFormat()); if (formInmatch.getImFieldDesc() == null) {
     * formInmatch.setImFieldDesc(""); } if (formInmatch.getImSample() == null) {
     * formInmatch.setImSample(""); } dbInmatch.setImFieldDesc(formInmatch.getImFieldDesc()); if
     * ((formInmatch.getImRequired() == null) || (formInmatch.getImDetectError() == null) ||
     * (formInmatch.getImSortId() == null)) {
     * System.out.println("==========inmatch integer parameter is illeagle"); return "fail"; } if
     * ((!dbInmatch.getImRequiredMap().containsKey(formInmatch.getImRequired().toString())) ||
     * (!dbInmatch.getImDetectErrorMap().containsKey(formInmatch.getImDetectError().toString()))) {
     * System.out.println("==========inmatch map parameter is illeagle"); return "fail"; }
     * 
     * if (dbInmatch.getImImb().getImbRequired().intValue() == 1)
     * dbInmatch.setImRequired(Integer.valueOf(1)); else {
     * dbInmatch.setImRequired(formInmatch.getImRequired()); }
     * dbInmatch.setImDetectError(formInmatch.getImDetectError());
     * dbInmatch.setImSortId(formInmatch.getImSortId());
     * dbInmatch.setImSampleWidth(formInmatch.getImSampleWidth());
     * 
     * if (formatToObject.setFormat(dbInmatch.getImImb().getImbDataType(),
     * formInmatch.getImFormat(), new int[0])) { dbInmatch.setImFormat(formatToObject.getFormat());
     * if (formatToObject.checkStr(formInmatch.getImSample())) {
     * dbInmatch.setImSample(formInmatch.getImSample()); break label981: }
     * System.out.println("==========" + dbInmatch.getImImb().getImbFieldDesc() +
     * ": imSample is illeagle"); return "success"; }
     * 
     * System.out.println("==========" + dbInmatch.getImImb().getImbFieldDesc() +
     * ": imFormat is illeagle"); return "success";
     * 
     * if (inmatchModelDB.getImmId() == null) { label981: dbInmatch.setImImm(inmatchModelDB); } } if
     * ((outputNum == 0) && (imList.size() > 0)) {
     * System.out.println("==========error: the number of imIsInput is 0"); return "success"; }
     * 
     * Collections.sort(inmatchDefaultList, new BeanComparator(Inmatch.PROP_IM_SORT_ID)); int i = 0;
     * for (int j = 0; i < inmatchDefaultList.size(); ++i) {
     * ((Inmatch)inmatchDefaultList.get(i)).setImSortId(Integer.valueOf(i)); } IInmatchModelBO
     * inmatchModelBO = (IInmatchModelBO)getBean("inmatchModelBO");
     * inmatchModelBO.saveOrUpdate(inmatchModelDB); return "success"; }
     */

    public String saveOrUpdateInmatchModel(InmatchModel inmatchModel, List imList) throws Exception {
        if ("error".equalsIgnoreCase(DWRUtil.checkAuth("iodef", "edit"))) {
            System.out.println("==========error: has no auth");
            return "fail";
        }
        Date nowDate = new Date();
        if (inmatchModel == null) {
            System.out.println("==========inmatchModel is illeagle");
            return "fail";
        }
        if (inmatchModel.getImmId() != null && inmatchModel.getImmId().length() < 1) {
            inmatchModel.setImmId(null);
        }
        if (imList == null) {
            imList = new ArrayList();
        }
        InmatchModel inmatchModelDB = loadInmatchModel(inmatchModel.getImmIo().getIoId(),
                                                       inmatchModel.getImmId());
        if (inmatchModelDB == null) {
            System.out.println("==========inmatchModel is illeagle");
            return "fail";
        }
        if (inmatchModel.getImmDesc() == null || inmatchModel.getImmDesc().length() < 1
                || inmatchModel.getImmName() == null || inmatchModel.getImmName().length() < 1
                || inmatchModel.getImmInputType() == null
                || inmatchModel.getImmInputType().length() < 1) {
            System.out.println("==========inmatchModel string parameter is illeagle");
            return "fail";
        }
        if (inmatchModel.getImmImportMode() == null || inmatchModel.getImmNoTitle() == null) {
            System.out.println("==========inmatchModel integer parameter is illeagle");
            return "fail";
        }
        if (!inmatchModelDB.getImmImportModeMap().containsKey(
                                                              inmatchModel.getImmImportMode()
                                                                      .toString())
                || !inmatchModelDB.getImmNoTitleMap().containsKey(
                                                                  inmatchModel.getImmNoTitle()
                                                                          .toString())
                || !inmatchModelDB.getImmInputTypeMap().containsKey(inmatchModel.getImmInputType())) {
            System.out.println("==========inmatchModel map parameter is illeagle");
            return "fail";
        }
        List inmatchDefaultList = inmatchModelDB.getImList();
        if (inmatchDefaultList.size() != imList.size()) {
            System.out.println("==========imList is illeagle");
            return "fail";
        }
        inmatchModelDB.setImmName(inmatchModel.getImmName());
        inmatchModelDB.setImmDesc(inmatchModel.getImmDesc());
        inmatchModelDB.setImmInputType(inmatchModel.getImmInputType());
        inmatchModelDB.setImmImportMode(inmatchModel.getImmImportMode());
        inmatchModelDB.setImmNoTitle(inmatchModel.getImmNoTitle());
        inmatchModelDB.setImmLastChangeBy(getCurrentEmpNo());
        inmatchModelDB.setImmLastChangeTime(nowDate);
        if (inmatchModelDB.getImmId() == null) {
            inmatchModelDB.setImmCreateBy(getCurrentEmpNo());
            inmatchModelDB.setImmCreateTime(nowDate);
            inmatchModelDB.setImmDefault(Integer.valueOf(0));
        }
        Collections.sort(imList, new BeanComparator((new StringBuilder())
                .append(Inmatch.PROP_IM_IMB).append(".").append(InmatchBasic.PROP_IMB_ID)
                .toString()));
        Collections.sort(inmatchDefaultList, new BeanComparator((new StringBuilder())
                .append(Inmatch.PROP_IM_IMB).append(".").append(InmatchBasic.PROP_IMB_ID)
                .toString()));
        StringToObject formatToObject = new StringToObject();
        int outputNum = 0;
        int i;
        for (i = 0; i < inmatchDefaultList.size(); i++) {
            Inmatch dbInmatch = (Inmatch) inmatchDefaultList.get(i);
            Inmatch formInmatch = (Inmatch) imList.get(i);
            if (dbInmatch.getImImb().getImbId().compareTo(formInmatch.getImImb().getImbId()) != 0) {
                System.out.println("==========error: imImb.imbId is not correct");
                return "fail";
            }
            if (formInmatch.getImIsInput() == null || formInmatch.getImIsInput().intValue() != 1) {
                formInmatch.setImIsInput(Integer.valueOf(0));
            } else {
                outputNum++;
            }
            dbInmatch.setImIsInput(formInmatch.getImIsInput());
            if (formInmatch.getImFormat() != null && formInmatch.getImFormat().length() < 1) {
                formInmatch.setImFormat(null);
            }
            dbInmatch.setImFormat(formInmatch.getImFormat());
            if (formInmatch.getImFieldDesc() == null) {
                formInmatch.setImFieldDesc("");
            }
            if (formInmatch.getImSample() == null) {
                formInmatch.setImSample("");
            }
            dbInmatch.setImFieldDesc(formInmatch.getImFieldDesc());
            if (formInmatch.getImRequired() == null || formInmatch.getImDetectError() == null
                    || formInmatch.getImSortId() == null) {
                System.out.println("==========inmatch integer parameter is illeagle");
                return "fail";
            }
            if (!dbInmatch.getImRequiredMap().containsKey(formInmatch.getImRequired().toString())
                    || !dbInmatch.getImDetectErrorMap().containsKey(
                                                                    formInmatch.getImDetectError()
                                                                            .toString())) {
                System.out.println("==========inmatch map parameter is illeagle");
                return "fail";
            }
            if (dbInmatch.getImImb().getImbRequired().intValue() == 1) {
                dbInmatch.setImRequired(Integer.valueOf(1));
            } else {
                dbInmatch.setImRequired(formInmatch.getImRequired());
            }
            dbInmatch.setImDetectError(formInmatch.getImDetectError());
            dbInmatch.setImSortId(formInmatch.getImSortId());
            dbInmatch.setImSampleWidth(formInmatch.getImSampleWidth());
            if (formatToObject.setFormat(dbInmatch.getImImb().getImbDataType(), formInmatch
                    .getImFormat(), new int[0])) {
                dbInmatch.setImFormat(formatToObject.getFormat());
                if (formatToObject.checkStr(formInmatch.getImSample())) {
                    dbInmatch.setImSample(formInmatch.getImSample());
                } else {
                    System.out.println((new StringBuilder()).append("==========")
                            .append(dbInmatch.getImImb().getImbFieldDesc())
                            .append(": imSample is illeagle").toString());
                    return "success";
                }
            } else {
                System.out.println((new StringBuilder()).append("==========")
                        .append(dbInmatch.getImImb().getImbFieldDesc())
                        .append(": imFormat is illeagle").toString());
                return "success";
            }
            if (inmatchModelDB.getImmId() == null) {
                dbInmatch.setImImm(inmatchModelDB);
            }
        }

        if (outputNum == 0 && imList.size() > 0) {
            System.out.println("==========error: the number of imIsInput is 0");
            return "success";
        }
        Collections.sort(inmatchDefaultList, new BeanComparator(Inmatch.PROP_IM_SORT_ID));
        i = 0;
        int j = 0;
        for (; i < inmatchDefaultList.size(); i++) {
            ((Inmatch) inmatchDefaultList.get(i)).setImSortId(Integer.valueOf(i));
        }

        IInmatchModelBO inmatchModelBO = (IInmatchModelBO) getBean("inmatchModelBO");
        inmatchModelBO.saveOrUpdate(inmatchModelDB);
        return "success";
    }

    public String setDefaultInmatchModel(String ioId, String immId) throws Exception {
        if ("error".equalsIgnoreCase(DWRUtil.checkAuth("iodef", "edit"))) {
            System.out.println("==========error: has no auth");
            return "fail";
        }
        IInmatchModelBO inmatchModelBO = (IInmatchModelBO) getBean("inmatchModelBO");
        InmatchModel inmatchModel = inmatchModelBO.getObject(new Iodef(ioId), immId);
        if (inmatchModel == null) {
            System.out.println("==========immId doesn't exist:" + immId);
            return "fail";
        }
        if (inmatchModel.getImmDefault().intValue() == 1) {
            System.out.println("==========immId is default:" + immId);
            return "fail";
        }
        InmatchModel immDefault = inmatchModelBO.loadDefaultObject(new Iodef(ioId));
        List inmatchModels = new ArrayList();
        Date nowDate = new Date();
        immDefault.setImmDefault(Integer.valueOf(0));
        immDefault.setImmLastChangeBy(getCurrentEmpNo());
        immDefault.setImmLastChangeTime(nowDate);
        inmatchModel.setImmDefault(Integer.valueOf(1));
        inmatchModel.setImmLastChangeBy(getCurrentEmpNo());
        inmatchModel.setImmLastChangeTime(nowDate);
        inmatchModels.add(immDefault);
        inmatchModels.add(inmatchModel);
        inmatchModelBO.saveOrUpdate(inmatchModels);
        return inmatchModel.getImmId() + ":" + immDefault.getImmId();
    }

    public String deleteInmatchModel(String ioId, String immId) throws Exception {
        if ("error".equalsIgnoreCase(DWRUtil.checkAuth("iodef", "edit"))) {
            System.out.println("==========error: has no auth");
            return "fail";
        }
        IInmatchModelBO inmatchModelBO = (IInmatchModelBO) getBean("inmatchModelBO");
        InmatchModel inmatchModel = inmatchModelBO.getObject(new Iodef(ioId), immId);
        if (inmatchModel == null) {
            System.out.println("==========immId doesn't exist:" + immId);
            return "fail";
        }
        if (inmatchModel.getImmDefault().intValue() == 1) {
            System.out.println("==========immId is default:" + immId);
            return "fail";
        }
        IInmatchBO inmatchBO = (IInmatchBO) getBean("inmatchBO");
        List imList = inmatchBO.getInmatchList(inmatchModel);
        inmatchModel.setImList(imList);
        inmatchModelBO.deleteInmatchModel(inmatchModel);
        return "success";
    }

    public List<InmatchModel> getInmatchModelList(String ioName) {
        IIodefBo iodefBO = (IIodefBo) getBean("iodefbo");
        IInmatchModelBO inmatchModelBO = (IInmatchModelBO) getBean("inmatchModelBO");
        Iodef iodef = iodefBO.loadObjectByName(ioName);
        List result = inmatchModelBO.getListByIodef(iodef);
        for (int i = 0; i < result.size(); ++i) {
            ((InmatchModel) result.get(i)).setImmIo(iodef);
        }
        return result;
    }

    public String getIoId() {
        return this.ioId;
    }

    public void setIoId(String ioId) {
        this.ioId = ioId;
    }

    public List<InmatchModel> getImmList() {
        return this.immList;
    }

    public Iodef getIodef() {
        return this.iodef;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.io.action.InmatchModelList JD-Core Version: 0.5.4
 */
package com.hr.io.action;

import com.hr.base.BaseAction;
import com.hr.base.DWRUtil;
import com.hr.io.bo.IInmatchBO;
import com.hr.io.bo.IInmatchBasicBO;
import com.hr.io.bo.IInmatchModelBO;
import com.hr.io.bo.IIodefBo;
import com.hr.io.bo.IOutmatchBO;
import com.hr.io.bo.IOutmatchBasicBO;
import com.hr.io.bo.IOutmatchModelBO;
import com.hr.io.domain.Inmatch;
import com.hr.io.domain.InmatchBasic;
import com.hr.io.domain.InmatchModel;
import com.hr.io.domain.Iodef;
import com.hr.io.domain.Outmatch;
import com.hr.io.domain.OutmatchBasic;
import com.hr.util.Pager;
import com.hr.util.StringUtil;
import com.hr.util.input.StringToObject;
import com.hr.util.output.FormatToString;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BeanComparator;

public class IodefList extends BaseAction {
    private static final long serialVersionUID = 1L;
    private Iodef iodef;
    private List<Iodef> iodefList;
    private boolean changeAuthority;
    private Pager page;
    private String changeIodef;

    public IodefList() {
        this.changeAuthority = false;

        this.page = new Pager();
    }

    public String execute() throws Exception {
        if (this.iodef == null) {
            this.iodef = new Iodef();
        }
        this.iodef.setAllMaps();
        IIodefBo iodefBo = (IIodefBo) getBean("iodefbo");
        this.iodefList = iodefBo.searchIodef(this.iodef, this.page);
        if ((this.changeIodef != null) && (this.changeIodef.equals("TengSourceAdmin"))) {
            this.changeAuthority = true;
        }
        return "success";
    }

    private Iodef getIodefByIoId(String ioId) {
        if (ioId == null) {
            return null;
        }
        IIodefBo iodefBo = (IIodefBo) getBean("iodefbo");
        return iodefBo.loadObject(ioId);
    }

    public Iodef loadIodef(String ioId, String changeIodef) throws Exception {
        if ("error".equalsIgnoreCase(DWRUtil.checkAuth("iodef", "view"))) {
            System.out.println("==========error: has no auth");
            return null;
        }

        Iodef iodef = getIodefByIoId(ioId);
        if (iodef == null) {
            return null;
        }
        if (iodef.getIoType().intValue() == 0) {
            IInmatchBasicBO inmatchBasicBO = (IInmatchBasicBO) getBean("inmatchBasicBO");
            List imbList = inmatchBasicBO.getListByIodef(iodef);

            InmatchBasic imbTmp = new InmatchBasic();
            imbTmp.setImbSortId(Integer.valueOf(imbList.size() + 10));
            imbTmp.setImbDataType("string");
            imbTmp.setImbDetectError(Integer.valueOf(0));
            imbTmp.setImbRequired(Integer.valueOf(0));
            imbList.add(imbTmp);
            inmatchBasicBO.initInmatchBasicMaps(imbList);
            iodef.setImbList(imbList);
        } else if (iodef.getIoType().intValue() == 1) {
            IOutmatchBasicBO outmatchBasicBO = (IOutmatchBasicBO) getBean("outmatchBasicBO");
            List ombList = outmatchBasicBO.getListByIodef(iodef);

            OutmatchBasic ombTmp = new OutmatchBasic();
            ombTmp.setOmbColumnWidth(Integer.valueOf(15));
            ombTmp.setOmbSortId(Integer.valueOf(ombList.size() + 10));
            ombTmp.setOmbDataType("string");
            ombTmp.setOmbCanGroup(Integer.valueOf(0));
            ombList.add(ombTmp);
            outmatchBasicBO.initOutmatchBasicMaps(ombList);
            iodef.setOmbList(ombList);
        }

        if ((changeIodef == null) || (changeIodef.compareTo("TengSourceAdmin") != 0)
                || ("error".equalsIgnoreCase(DWRUtil.checkAuth("iodef", "edit")))) {
            iodef.setIoId(null);
        }
        return iodef;
    }

    public String updateIodef(String ioId, List<OutmatchBasic> ombList, List<InmatchBasic> imbList)
            throws Exception {
        String msgNoauth = "您无权执行此操作，请重新登录后再试！";
        String msgNoIodef = "找不到接口数据，请刷新后重试＄1�7";

        if ("error".equalsIgnoreCase(DWRUtil.checkAuth("iodef", "edit"))) {
            return msgNoauth;
        }

        if ((ioId == null) || (ioId.length() == 1)) {
            return msgNoIodef;
        }

        IIodefBo iodefBo = (IIodefBo) getBean("iodefbo");
        Iodef iodef = iodefBo.loadObject(ioId);
        if (iodef == null)
            return msgNoIodef;

        if (ombList != null) {
            return updateOutmatchBasicList(iodef, ombList);
        }
        return updateInmatchBasicList(iodef, imbList);
    }

    public String deleteOutmatchBasic(String ioId, String ombId) throws Exception {
        if ("error".equalsIgnoreCase(DWRUtil.checkAuth("iodef", "edit"))) {
            System.out.println("==========error: has no auth");
            return "error";
        }

        Iodef iodef = getIodefByIoId(ioId);
        if (iodef == null) {
            System.out.println("==========parameter ioId is illeagle");
            return "error";
        }

        IOutmatchBasicBO outmatchBasicBO = (IOutmatchBasicBO) getBean("outmatchBasicBO");
        OutmatchBasic ombDb = outmatchBasicBO.loadObject(ombId);
        if ((ombDb == null) || (ombDb.getOmbIo().getIoId().compareTo(ioId) != 0)) {
            System.out.println("==========parameter ioId is illeagle");
            return "error";
        }

        IOutmatchBO outmatchBO = (IOutmatchBO) getBean("outmatchBO");
        List omList = outmatchBO.getOutmatchListByOmb(ombId);
        if (omList.size() < 1) {
            ombDb.setOmList(omList);
        }

        outmatchBasicBO.deleteObject(ombDb);
        return "success";
    }

    public OutmatchBasic updateOutmatchBasic(String ioId, OutmatchBasic omb) throws Exception {
        if ("error".equalsIgnoreCase(DWRUtil.checkAuth("iodef", "edit"))) {
            System.out.println("==========error: has no auth");
            return null;
        }

        Iodef iodef = getIodefByIoId(ioId);
        if (iodef == null) {
            System.out.println("==========parameter ioId is illeagle");
            return null;
        }
        if ((omb.getOmbId() == null) || (omb.getOmbId().length() < 1)) {
            System.out.println("==========ombId is illeagle");
            return null;
        }

        omb.setAllMaps();

        if (!checkOutmatchBasic(omb, omb, new boolean[] { true })) {
            System.out.println("==========omb parameter ioId is illeagle");
            return null;
        }

        FormatToString formatToString = new FormatToString();
        if (formatToString.setFormat(omb.getOmbDataType(), omb.getOmbFormat())) {
            omb.setOmbFormat(formatToString.getFormat());
        } else {
            System.out.println("==========parameter ombFormat is illeagle");
            return null;
        }

        IOutmatchBasicBO outmatchBasicBO = (IOutmatchBasicBO) getBean("outmatchBasicBO");
        OutmatchBasic ombDb = outmatchBasicBO.loadObject(omb.getOmbId());
        if ((ombDb == null) || (ombDb.getOmbIo().getIoId().compareTo(ioId) != 0)) {
            System.out.println("==========ombId is illeagle");
            return null;
        }

        IOutmatchBO outmatchBO = (IOutmatchBO) getBean("outmatchBO");
        List omList = outmatchBO.getOutmatchListByOmb(ombDb.getOmbId());

        if (omList.size() > 0) {
            boolean changeOmList = false;
            if ((omb.getOmbCanGroup().intValue() == 0)
                    && (!omb.getOmbCanGroup().equals(ombDb.getOmbCanGroup()))) {
                changeOmList = true;
                for (int i = 0; i < omList.size(); ++i) {
                    ((Outmatch) omList.get(i)).setOmIsGroup(Integer.valueOf(0));
                }
            }
            if (!omb.getOmbDataType().equals(ombDb.getOmbDataType())) {
                changeOmList = true;
                for (int i = 0; i < omList.size(); ++i) {
                    ((Outmatch) omList.get(i)).setOmFormat(omb.getOmbFormat());
                    ((Outmatch) omList.get(i)).setOmStatistic(null);
                }
            }
            if (changeOmList) {
                ombDb.setOmList(omList);
            }
        }

        ombDb.setOmbFieldName(omb.getOmbFieldName());
        ombDb.setOmbFieldDesc(omb.getOmbFieldDesc());
        ombDb.setOmbDataType(omb.getOmbDataType());
        ombDb.setOmbFormat(omb.getOmbFormat());
        ombDb.setOmbColumnWidth(omb.getOmbColumnWidth());
        ombDb.setOmbCanGroup(omb.getOmbCanGroup());

        outmatchBasicBO.saveOrUpdateObject(ombDb);
        ombDb.setOmList(null);
        ombDb.setAllMaps();
        return ombDb;
    }

    public InmatchBasic updateInmatchBasic(String ioId, InmatchBasic imb) throws Exception {
        if ("error".equalsIgnoreCase(DWRUtil.checkAuth("iodef", "edit"))) {
            System.out.println("==========error: has no auth");
            return null;
        }

        Iodef iodef = getIodefByIoId(ioId);
        if (iodef == null) {
            System.out.println("==========parameter ioId is illeagle");
            return null;
        }
        if ((imb.getImbId() == null) || (imb.getImbId().length() < 1)) {
            System.out.println("==========imbId is illeagle");
            return null;
        }

        imb.setAllMaps();

        if (!checkInmatchBasic(imb, imb, new boolean[] { true })) {
            System.out.println("==========imb parameter ioId is illeagle");
            return null;
        }

        StringToObject formatToObject = new StringToObject();
        if (formatToObject.setFormat(imb.getImbDataType(), imb.getImbFormat(), new int[0])) {
            imb.setImbFormat(formatToObject.getFormat());
        } else {
            System.out.println("==========parameter imbFormat is illeagle");
            return null;
        }
        if (!formatToObject.checkStr(imb.getImbSample())) {
            System.out.println("==========parameter imbSample is illeagle");
            return null;
        }

        IInmatchBasicBO inmatchBasicBO = (IInmatchBasicBO) getBean("inmatchBasicBO");
        InmatchBasic imbDb = inmatchBasicBO.loadObject(imb.getImbId());
        if ((imbDb == null) || (imbDb.getImbIo().getIoId().compareTo(ioId) != 0)) {
            System.out.println("==========imbId is illeagle");
            return null;
        }

        if ((imb.getImbDataType().compareTo(imbDb.getImbDataType()) != 0)
                || ((imb.getImbRequired().intValue() == 1) && (imbDb.getImbRequired().intValue() == 0))) {
            IInmatchBO inmatchBO = (IInmatchBO) getBean("inmatchBO");
            List imList = inmatchBO.getInmatchListByImb(imbDb.getImbId());
            for (int i = 0; i < imList.size(); ++i) {
                Inmatch imTmp = (Inmatch) imList.get(i);
                if (imb.getImbDataType().compareTo(imbDb.getImbDataType()) != 0) {
                    imTmp.setImFormat(imbDb.getImbFormat());
                    imTmp.setImSample(imbDb.getImbSample());
                }
                if ((imb.getImbRequired().intValue() == 1)
                        && (imbDb.getImbRequired().intValue() == 0)) {
                    imTmp.setImRequired(Integer.valueOf(1));
                }
            }
            imbDb.setImList(imList);
        }
        imbDb.setImbFieldName(imb.getImbFieldName());
        imbDb.setImbFieldDesc(imb.getImbFieldDesc());
        imbDb.setImbDataType(imb.getImbDataType());
        imbDb.setImbFormat(imb.getImbFormat());
        imbDb.setImbDetectError(imb.getImbDetectError());
        imbDb.setImbRequired(imb.getImbRequired());
        imbDb.setImbSample(imb.getImbSample());

        inmatchBasicBO.saveOrUpdateObject(imbDb);
        imbDb.setImList(null);
        imbDb.setAllMaps();
        return imbDb;
    }

    public String deleteInmatchBasic(String ioId, String imbId) throws Exception {
        if ("error".equalsIgnoreCase(DWRUtil.checkAuth("iodef", "edit"))) {
            System.out.println("==========error: has no auth");
            return "error";
        }
        Iodef iodef = getIodefByIoId(ioId);
        if (iodef == null) {
            System.out.println("==========parameter ioId is illeagle");
            return "error";
        }
        IInmatchBasicBO inmatchBasicBO = (IInmatchBasicBO) getBean("inmatchBasicBO");
        InmatchBasic imbDb = inmatchBasicBO.loadObject(imbId);
        if ((imbDb == null) || (imbDb.getImbIo().getIoId().compareTo(ioId) != 0)) {
            System.out.println("==========parameter ioId is illeagle");
            return "error";
        }
        IInmatchBO inmatchBO = (IInmatchBO) getBean("inmatchBO");
        List imList = inmatchBO.getInmatchListByImb(imbId);
        if (imList.size() > 0) {
            imbDb.setImList(imList);
        }
        inmatchBasicBO.deleteObject(imbDb);
        return "success";
    }

    public String checkOmDataTypeFormat(String dataType, String format) {
        FormatToString formatToString = new FormatToString();
        if (formatToString.setFormat(dataType, format)) {
            return "success";
        }
        return (formatToString.getFormat() == null) ? "" : formatToString.getFormat();
    }

    public String checkImDataTypeFormat(String dataType, String format) {
        StringToObject formatToObject = new StringToObject();
        if (formatToObject.setFormat(dataType, format, new int[0])) {
            return "success";
        }
        return (formatToObject.getFormat() == null) ? "" : formatToObject.getFormat();
    }

    public String checkImSample(String dataType, String format, String sample) {
        StringToObject formatToObject = new StringToObject();
        if (formatToObject.setFormat(dataType, format, new int[0])) {
            if (formatToObject.checkStr(sample)) {
                return "success";
            }
            return "error";
        }

        return (formatToObject.getFormat() == null) ? "" : formatToObject.getFormat();
    }

    private String updateInmatchBasicList(Iodef iodef, List<InmatchBasic> imbList) {
        String msgNoImb = "没有数据可以修改，请刷新后重试！";
        String msgNoImbDb = "找不到导入接口数据，请刷新后重试＄1�7";

        if ((imbList == null) || (imbList.size() == 0))
            return msgNoImb;
        for (int i = imbList.size() - 1; i >= 0; --i) {
            if (imbList.get(i) == null) {
                imbList.remove(i);
            }

        }

        IInmatchBasicBO inmatchBasicBO = (IInmatchBasicBO) getBean("inmatchBasicBO");
        List imbDbList = inmatchBasicBO.getListByIodef(iodef);
        if ((imbDbList == null) || (imbDbList.size() == 0)) {
            return msgNoImbDb;
        }

        Map imbDbMap = new HashMap();
        InmatchBasic imb;
        for (Iterator i$ = imbDbList.iterator(); i$.hasNext(); imbDbMap.put(imb.getImbId(), imb))
            imb = (InmatchBasic) i$.next();

        InmatchBasic imbMap = new InmatchBasic();
        imbMap.setAllMaps();
        StringToObject formatToObject = new StringToObject();

        IInmatchModelBO inmatchModelBO = (IInmatchModelBO) getBean("inmatchModelBO");
        List immList = inmatchModelBO.getListByIodef(iodef);
        List imList = new ArrayList();

        Collections.sort(imbList, new BeanComparator(InmatchBasic.PROP_IMB_SORT_ID));

        Iterator iter = imbList.iterator();
        int i = 0;
        while (iter.hasNext()) {
            imb = (InmatchBasic) iter.next();

            InmatchBasic imbDb = (InmatchBasic) imbDbMap.get(imb.getImbId());
            if (imbDb != null) {
                imb.setImbFieldName(imbDb.getImbFieldName());
                imb.setImbDataType(imbDb.getImbDataType());
            }

            if ((imb.getImbFieldName() == null) || (imb.getImbFieldName().length() == 0)) {
                iter.remove();
            }

            String info = checkInmatch(imb, imbMap, formatToObject);
            if (!info.equals("SUCC"))
                return info;

            imb.setImbIo(iodef);
            imb.setImbSortId(Integer.valueOf(i));
            ++i;

            if (imbDb == null) {
                imb.setImbId(null);
                for (int j = 0; j < immList.size(); ++j) {
                    Inmatch imTmp = new Inmatch();
                    imTmp.initByInmatchBasic(imb);
                    imTmp.setImIsInput(Integer.valueOf(0));
                    imTmp.setImImm((InmatchModel) immList.get(j));
                    imList.add(imTmp);
                }
            }
        }

        inmatchBasicBO.updateImbImList(imbList, imList);
        return "success";
    }

    private String checkInmatch(InmatchBasic imb, InmatchBasic imbMap, StringToObject formatToObject) {
        String msgFieldNoData = "名称为{0}的{1}字段不能为空＄1�7";
        String msgSort = "排序";
        String msgFieldName = "字段名称";
        String msgFieldDesc = "标题";
        String msgFieldError = "{0}的{1}字段不正确！";
        String msgDataType = "数据类型";
        String msgRequired = "是否必填";
        String msgDetectError = "是否棄1�7查错评1�7";
        String msgFormat = "数据格式";
        String msgSample = "例子";

        if (imb.getImbSortId() == null) {
            return StringUtil.message(msgFieldNoData,
                                      new Object[] { imb.getImbFieldName(), msgSort });
        }

        if ((imb.getImbFieldName() == null) || (imb.getImbFieldName().length() == 0)) {
            return StringUtil.message(msgFieldNoData, new Object[] { imb.getImbFieldName(),
                    msgFieldName });
        }

        if ((imb.getImbFieldDesc() == null) || (imb.getImbFieldDesc().length() == 0)) {
            return StringUtil.message(msgFieldNoData, new Object[] { imb.getImbFieldName(),
                    msgFieldDesc });
        }

        if (!imbMap.getImbDataTypeMap().containsKey(imb.getImbDataType())) {
            return StringUtil.message(msgFieldError, new Object[] { imb.getImbFieldName(),
                    msgDataType });
        }

        if ((imb.getImbRequired().intValue() != 0) && (imb.getImbRequired().intValue() != 1)) {
            return StringUtil.message(msgFieldError, new Object[] { imb.getImbFieldName(),
                    msgRequired });
        }

        if ((imb.getImbDetectError().intValue() != 0) && (imb.getImbDetectError().intValue() != 1)) {
            return StringUtil.message(msgFieldError, new Object[] { imb.getImbFieldName(),
                    msgDetectError });
        }

        if (formatToObject.setFormat(imb.getImbDataType(), imb.getImbFormat(), new int[0]))
            imb.setImbFormat(formatToObject.getFormat());
        else {
            return StringUtil.message(msgFieldError, new Object[] { imb.getImbFieldName(),
                    msgFormat });
        }

        if ((imb.getImbRequired().intValue() == 1)
                && (((imb.getImbSample() == null) || (imb.getImbSample().length() == 0)))) {
            return StringUtil.message(msgFieldNoData, new Object[] { imb.getImbFieldName(),
                    msgFormat });
        }

        if (!formatToObject.checkStr(imb.getImbSample())) {
            return StringUtil.message(msgFieldError, new Object[] { imb.getImbFieldName(),
                    msgSample });
        }
        return "SUCC";
    }

    private String updateOutmatchBasicList(Iodef iodef, List<OutmatchBasic> ombList) {
        String msgNoOmb = "没有数据可以修改，请刷新后重试！";
        String msgNoOmbDb = "找不到导出接口数据，请刷新后重试＄1�7";

        if ((ombList == null) || (ombList.size() == 0))
            return msgNoOmb;
        for (int i = ombList.size() - 1; i >= 0; --i) {
            if (ombList.get(i) == null) {
                ombList.remove(i);
            }

        }

        IOutmatchBasicBO outmatchBasicBO = (IOutmatchBasicBO) getBean("outmatchBasicBO");
        List ombDbList = outmatchBasicBO.getListByIodef(iodef);

        Map ombDbMap = new HashMap();
        OutmatchBasic omb;
        for (Iterator i$ = ombDbList.iterator(); i$.hasNext(); ombDbMap.put(omb.getOmbId(), omb))
            omb = (OutmatchBasic) i$.next();

        OutmatchBasic ombMap = new OutmatchBasic();
        ombMap.setAllMaps();
        StringToObject formatToObject = new StringToObject();

        IOutmatchModelBO outmatchModelBO = (IOutmatchModelBO) getBean("outmatchModelBO");
        List ommList = outmatchModelBO.getListByIodef(iodef);
        List omList = new ArrayList();

        Collections.sort(ombList, new BeanComparator(OutmatchBasic.PROP_OMB_SORT_ID));

        Iterator iter = ombList.iterator();
        int i = 0;
        while (iter.hasNext()) {
            omb = (OutmatchBasic) iter.next();

            OutmatchBasic ombDb = (OutmatchBasic) ombDbMap.get(omb.getOmbId());
            if (ombDb != null) {
                omb.setOmbFieldName(ombDb.getOmbFieldName());
                omb.setOmbDataType(ombDb.getOmbDataType());
                omb.setOmbCanGroup(ombDb.getOmbCanGroup());
            }

            if ((omb.getOmbFieldName() == null) || (omb.getOmbFieldName().length() == 0)) {
                iter.remove();
            }

            String info = checkOutmatch(omb, ombMap, formatToObject);
            if (!info.equals("SUCC"))
                return info;

            omb.setOmbIo(iodef);
            omb.setOmbSortId(Integer.valueOf(i));
            ++i;

            if (ombDb == null) {
                omb.setOmbId(null);
            }
        }

        outmatchBasicBO.updateOmbOmList(ombList, omList);
        return "success";
    }

    private String checkOutmatch(OutmatchBasic omb, OutmatchBasic ombMap,
            StringToObject formatToObject) {
        String msgFieldNoData = "名称为{0}的{1}字段不能为空＄1�7";
        String msgSort = "排序";
        String msgFieldName = "字段名称";
        String msgFieldDesc = "标题";
        String msgWidth = "列宽";
        String msgFieldError = "{0}的{1}字段不正确！";
        String msgDataType = "数据类型";
        String msgCanGroup = "是否可以分组";
        String msgFormat = "数据格式";

        if (omb.getOmbSortId() == null) {
            return StringUtil.message(msgFieldNoData,
                                      new Object[] { omb.getOmbFieldName(), msgSort });
        }

        if ((omb.getOmbFieldName() == null) || (omb.getOmbFieldName().length() == 0)) {
            return StringUtil.message(msgFieldNoData, new Object[] { omb.getOmbFieldName(),
                    msgFieldName });
        }

        if ((omb.getOmbFieldDesc() == null) || (omb.getOmbFieldDesc().length() == 0)) {
            return StringUtil.message(msgFieldNoData, new Object[] { omb.getOmbFieldName(),
                    msgFieldDesc });
        }

        if ((omb.getOmbColumnWidth() == null) || (omb.getOmbColumnWidth().intValue() == 0)) {
            return StringUtil.message(msgFieldNoData, new Object[] { omb.getOmbFieldName(),
                    msgWidth });
        }

        if (!ombMap.getOmbDataTypeMap().containsKey(omb.getOmbDataType())) {
            return StringUtil.message(msgFieldError, new Object[] { omb.getOmbFieldName(),
                    msgDataType });
        }

        if ((omb.getOmbCanGroup().intValue() != 0) && (omb.getOmbCanGroup().intValue() != 1)) {
            return StringUtil.message(msgFieldError, new Object[] { omb.getOmbFieldName(),
                    msgCanGroup });
        }

        if (formatToObject.setFormat(omb.getOmbDataType(), omb.getOmbFormat(), new int[0]))
            omb.setOmbFormat(formatToObject.getFormat());
        else {
            return StringUtil.message(msgFieldError, new Object[] { omb.getOmbFieldName(),
                    msgFormat });
        }
        return "SUCC";
    }

    private boolean checkOutmatchBasic(OutmatchBasic omb, OutmatchBasic ombMap, boolean[] checkAll) {
        if ((checkAll.length > 0) && checkAll[0]) {
            if ((omb.getOmbFieldName() == null) || (omb.getOmbFieldName().length() < 1)) {
                System.out.println("==========string parameter is illeagle");
                return false;
            }
            if ((!ombMap.getOmbCanGroupMap().containsKey(omb.getOmbCanGroup().toString()))
                    || (!ombMap.getOmbDataTypeMap().containsKey(omb.getOmbDataType()))) {
                System.out.println("==========map parameter is illeagle");
                return false;
            }
        }
        if ((omb.getOmbFieldDesc() == null) || (omb.getOmbFieldDesc().length() < 1)) {
            System.out.println("==========string parameter is illeagle");
            return false;
        }
        if ((omb.getOmbColumnWidth() == null) || (omb.getOmbSortId() == null)) {
            System.out.println("==========integer string parameter is illeagle");
            return false;
        }
        return true;
    }

    private boolean checkInmatchBasic(InmatchBasic imb, InmatchBasic imbMap, boolean[] checkAll) {
        if ((checkAll.length > 0) && checkAll[0]) {
            if ((imb.getImbFieldName() == null) || (imb.getImbFieldName().length() < 0)) {
                System.out.println("==========string parameter is illeagle");
                return false;
            }
            if (!imbMap.getImbDataTypeMap().containsKey(imb.getImbDataType())) {
                System.out.println("==========map parameter is illeagle");
                return false;
            }
        }
        if ((imb.getImbFieldDesc() == null) || (imb.getImbFieldDesc().length() < 1)
                || (imb.getImbSample() == null) || (imb.getImbSample().length() < 1)) {
            System.out.println("==========string parameter is illeagle");
            return false;
        }
        if (imb.getImbSortId() == null) {
            System.out.println("==========integer string parameter is illeagle");
            return false;
        }
        if ((!imbMap.getImbRequiredMap().containsKey(imb.getImbRequired().toString()))
                || (!imbMap.getImbDetectErrorMap().containsKey(imb.getImbDetectError().toString()))) {
            System.out.println("==========map parameter is illeagle");
            return false;
        }
        return true;
    }

    public Iodef getIodef() {
        return this.iodef;
    }

    public void setIodef(Iodef iodef) {
        this.iodef = iodef;
    }

    public List<Iodef> getIodefList() {
        return this.iodefList;
    }

    public void setIodefList(List<Iodef> iodefList) {
        this.iodefList = iodefList;
    }

    public Pager getPage() {
        return this.page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }

    public String getChangeIodef() {
        return this.changeIodef;
    }

    public void setChangeIodef(String changeIodef) {
        this.changeIodef = changeIodef;
    }

    public boolean isChangeAuthority() {
        return this.changeAuthority;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.io.action.IodefList JD-Core Version: 0.5.4
 */
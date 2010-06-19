package com.hr.recruitment.bo.impl;

import com.hr.base.Constants;
import com.hr.configuration.dao.IStatusDAO;
import com.hr.recruitment.bo.IRecruitchannelBo;
import com.hr.recruitment.dao.IRecruitchannelDAO;
import com.hr.recruitment.domain.Recruitchannel;
import com.hr.util.Pager;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class RecruitchannelBoImpl implements IRecruitchannelBo, Constants {
    private IRecruitchannelDAO channelDAO;
    private IStatusDAO statusDAO;

    public Boolean deleteChannel(String id) {
        try {
            Recruitchannel recruitchannel = (Recruitchannel) this.channelDAO
                    .loadObject(Recruitchannel.class, id, null, new boolean[0]);
            this.channelDAO.deleteObject(recruitchannel);
            return Boolean.valueOf(true);
        } catch (Exception e) {
        }
        return Boolean.valueOf(false);
    }

    public List getObjects(Class clas, String[] fetch) {
        return this.channelDAO.getObjects(clas, fetch);
    }

    public Boolean insertChannel(Recruitchannel channel) {
        try {
            this.channelDAO.saveObject(channel);
            return Boolean.valueOf(true);
        } catch (Exception e) {
        }
        return Boolean.valueOf(false);
    }

    public List searchChannel(Recruitchannel channel, Pager page) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Recruitchannel.class);
        List result = this.channelDAO.findByCriteria(detachedCriteria);
        return result;
    }

    public Integer searchChannelCountByName(String channelName) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Recruitchannel.class);
        detachedCriteria.add(Restrictions.eq(Recruitchannel.PROP_RECCH_NAME, channelName));
        Integer count = Integer.valueOf(this.channelDAO.findRowCountByCriteria(detachedCriteria));
        return count;
    }

    public Boolean updateChannel(Recruitchannel channel) {
        Recruitchannel oldRecruitchannel = (Recruitchannel) this.channelDAO
                .loadObject(Recruitchannel.class, channel.getId(), null, new boolean[0]);
        oldRecruitchannel.setRecchAddr(channel.getRecchAddr());
        oldRecruitchannel.setRecchAddrZip(channel.getRecchAddrZip());
        oldRecruitchannel.setRecchCityNo(channel.getRecchCityNo());
        oldRecruitchannel.setRecchComments(channel.getRecchComments());
        oldRecruitchannel.setRecchEmail(channel.getRecchEmail());
        oldRecruitchannel.setRecchName(channel.getRecchName());
        oldRecruitchannel.setRecchPhone(channel.getRecchPhone());
        oldRecruitchannel.setRecchRespPerson(channel.getRecchRespPerson());
        this.channelDAO.updateObject(oldRecruitchannel);

        return Boolean.valueOf(true);
    }

    public IRecruitchannelDAO getChannelDAO() {
        return this.channelDAO;
    }

    public void setChannelDAO(IRecruitchannelDAO channelDAO) {
        this.channelDAO = channelDAO;
    }

    public IStatusDAO getStatusDAO() {
        return this.statusDAO;
    }

    public void setStatusDAO(IStatusDAO statusDAO) {
        this.statusDAO = statusDAO;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.recruitment.bo.impl.RecruitchannelBoImpl JD-Core Version: 0.5.4
 */
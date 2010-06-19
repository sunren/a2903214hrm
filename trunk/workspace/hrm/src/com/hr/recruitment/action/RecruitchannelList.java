package com.hr.recruitment.action;

import com.hr.base.BaseAction;
import com.hr.recruitment.bo.IRecruitchannelBo;
import com.hr.recruitment.domain.Recruitchannel;
import java.util.List;

public class RecruitchannelList extends BaseAction {
    Recruitchannel recruitchannel;
    private List recruitchannelList;
    IRecruitchannelBo bo;

    public RecruitchannelList() {
        this.bo = ((IRecruitchannelBo) getBean("channelBo"));
    }

    public String execute() throws Exception {
        this.recruitchannelList = this.bo.searchChannel(null, null);
        return "success";
    }

    public String addRecruitchannel(Recruitchannel recruitchannel) {
        try {
            if (this.bo.searchChannelCountByName(recruitchannel.getRecchName()).intValue() > 0) {
                return "ChannelNameExist";
            }
            this.bo.insertChannel(recruitchannel);
            return recruitchannel.getId();
        } catch (Exception e) {
        }
        return "error";
    }

    public String updateRecruitchannel(Recruitchannel recruitchannel) {
        try {
            this.bo.updateChannel(recruitchannel);
            return "SUCC";
        } catch (Exception e) {
        }
        return "FAIL";
    }

    public String delRecruitchannel(String id) {
        try {
            this.bo.deleteChannel(id);
            return "SUCC";
        } catch (Exception e) {
        }
        return "related";
    }

    public List getRecruitchannelList() {
        return this.recruitchannelList;
    }

    public void setRecruitchannelList(List recruitchannelList) {
        this.recruitchannelList = recruitchannelList;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.recruitment.action.RecruitchannelList JD-Core Version: 0.5.4
 */
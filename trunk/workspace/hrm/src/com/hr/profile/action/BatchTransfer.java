package com.hr.profile.action;

import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.bo.IPositionBo;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Emptransfer;
import com.hr.profile.domain.Position;
import com.hr.util.StringUtil;
import java.util.List;

public class BatchTransfer extends ProfileAction {
    private Emptransfer transfer;
    private Position newPosition;
    private String ids;
    private Employee emp;

    public String execute() throws Exception {
        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        IPositionBo positionBo = (IPositionBo) getBean("positionBo");

        String[] empIds = this.ids.split(",");
        Position postion = positionBo.getPosById(this.newPosition.getId(), new String[] {
                Position.PROP_POSITION_PB_ID, Position.PROP_POSITION_PARENT_ID });
        if (postion == null) {
            addErrorInfo(StringUtil.message(this.msgStatusError, new Object[0]));
            return "success";
        }

        List errors = empBo.batchTransfer(this.transfer, empIds, getCurrentEmpNo(), postion);
        if (!errors.isEmpty())
            addErrorInfo(errors);
        else {
            addSuccessInfo(this.msgTransferBatchSuccess);
        }

        return "success";
    }

    public Emptransfer getTransfer() {
        return this.transfer;
    }

    public void setTransfer(Emptransfer transfer) {
        this.transfer = transfer;
    }

    public String getIds() {
        return this.ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Employee getEmp() {
        return this.emp;
    }

    public void setEmp(Employee emp) {
        this.emp = emp;
    }

    public Position getNewPosition() {
        return this.newPosition;
    }

    public void setNewPosition(Position newPosition) {
        this.newPosition = newPosition;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.action.BatchTransfer JD-Core Version: 0.5.4
 */
package com.hr.profile.action;

import com.hr.base.BaseAction;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.bo.IPositionBo;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Position;
import com.hr.profile.domain.base.TreeNode;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.BaseCrit;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.criterion.DetachedCriteria;

public class EmpTreeInit extends BaseAction {
    private static final long serialVersionUID = 1L;
    private String id;
    private String posId;
    private List<TreeNode> nodeList;
    private String rootId;

    public EmpTreeInit() {
        this.nodeList = null;
    }

    public String execute() throws Exception {
        IPositionBo posBo = (IPositionBo) SpringBeanFactory.getBean("positionBo");
        this.nodeList = posBo.generateEmpConnTreeNodes();
        for (TreeNode node : this.nodeList) {
            if ("0".equals(node.getNodeParentId())) {
                this.rootId = node.getNodeId();
                break;
            }
        }
        this.id = URLDecoder.decode(getRequest().getParameter("id"), "UTF-8");
        if ((this.id == null) || (this.id.equals(""))) {
            this.id = getCurrentEmpNo();
        }

        searchEmpId();

        return "success";
    }

    private List<TreeNode> generateEmpConnTreeNodes(List<Position> posList,
            List<String> parentPosList) {
        List nodeList = new ArrayList();
        for (int i = 0; i < posList.size(); ++i) {
            Position pos = (Position) posList.get(i);
            TreeNode node = new TreeNode();
            node.setNodeId(pos.getId());
            node.setNodeParentId((pos.getPositionParentId() != null) ? pos.getPositionParentId()
                    .getId() : "0");

            Employee emp = pos.getPositionEmpNo();
            if (emp != null) {
                node.setNodeName(emp.getEmpName());
                node.setEmpId(emp.getId());
                if (emp.getEmpGender().intValue() == 1)
                    node.setNodeType(5);
                else
                    node.setNodeType(6);
            } else {
                node.setNodeType(7);
            }

            nodeList.add(node);
        }

        return nodeList;
    }

    private void searchEmpId() {
        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        DetachedCriteria dc = DetachedCriteria.forClass(Employee.class);
        BaseCrit.addEmpDC(dc, "", this.id);
        List list = empBo.findByCriteria(dc);
        Employee employee = new Employee();
        if ((list == null) || (list.isEmpty()))
            employee = empBo.loadEmp(this.id, null);
        else
            employee = (Employee) list.get(0);
        this.id = employee.getId();
        IPositionBo posBo = (IPositionBo) SpringBeanFactory.getBean("positionBo");
        Position position = posBo.getPosByEmpNo(this.id, new String[0]);
        if (position == null)
            return;
        this.posId = position.getId();
    }

    public List<TreeNode> getNodeList() {
        return this.nodeList;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPosId() {
        return this.posId;
    }

    public void setPosId(String posId) {
        this.posId = posId;
    }

    public void setNodeList(List<TreeNode> nodeList) {
        this.nodeList = nodeList;
    }

    public String getRootId() {
        return this.rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.action.EmpTreeInit JD-Core Version: 0.5.4
 */
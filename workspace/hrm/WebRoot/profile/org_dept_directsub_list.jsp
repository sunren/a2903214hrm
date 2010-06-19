<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<html>
<head>
<link href="../resource/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../resource/js/jquery/jquery.tablesorter.js"></script>
<script type="text/javascript" src="../resource/js/jquery/jquery.tableEditor.js"></script>
<script type="text/javascript" src="../dwr/interface/OrgDeptManageAction.js"></script>
</head>
<body>
<span class="errorMessage" id="errMsg"></span>
<table id="deptTable" cellspacing="1" cellspacing="0" class="tablesorter" style="width: 650">
  <thead>
  <tr align="center">
  <th nowrap="nowrap">部门名称</th>
  <th nowrap="nowrap">部门描述</th>
  <th nowrap="nowrap">上级部门</th>
  <th nowrap="nowrap">负责职位</th>
  <th nowrap="nowrap">负责人</th>
  <th nowrap="nowrap">定编人数</th>
  <th nowrap="nowrap">在编人数</th>
  </tr>
  </thead>
  <tbody id="deptTableBody">
   <s:if test="!directSubDeptList.isEmpty()">
    	<s:iterator value="directSubDeptList" status="index">
    		<tr id='<s:property value="id"/>' nodeType='<s:property value="departmentCate"/>' align="center">
				<td align="center"><s:property value="departmentName" /></td>  
				<td align="center"><s:property value="departmentDesc" /></td>
				<td align="center"><s:property value="departmentParentId.departmentName" /></td>   
			    <td align="center"><s:property value="respPb.pbName" /></td>
			    <td align="center"><s:if test="deptHead!=null"><s:property value="deptHead.empName" /></s:if><s:else>空缺</s:else></td>
			    <td align="center"><s:property value="orderNum" /></td>
			    <td align="center"><s:property value="actualNum" /></td>
    		</tr>
    	</s:iterator>
    </s:if>
	<s:else>
		<tr>
			<td align="center" colspan="8">不存下级直属部门信息!</td>
		</tr>
	</s:else>
  </tbody>	
</table>
<s:if test="directSubDeptList.size() > 1">
<div class="btnlink">
	<a href="#" onclick="saveDeptSort();">保存排序</a>
</div>
<br/>
</s:if>
<script type="text/javascript">
var deptId='<s:property value='deptId'/>'.trim();
var tree = window.parent.tree;
var pbTree=window.parent.pbTree;
var nodeArr=window.parent.nodeArr;

//初始化table
function initTable(){
   $("#deptTable").tablesorter().tableDnD(); //启用表格排序/拖拽
   $("#deptTableBody tr").click(function(){//单击选中每一行
   $("#deptTableBody tr").removeClass('click');
   $(this).addClass("click");
   });
   $("#deptTableBody tr").dblclick(function(){//双击跳转
		var nodeId= this.id;//得到id
		var nodeType = this.nodeType;
		var url = "goBranchDeptTabs.action?dept.id="+nodeId+"&nodeType="+nodeType;
		window.parent.refreshIFrame(url);
        tree.selectItem(nodeId);
        tree.focusItem(nodeId);
   });
}

//保存排序
function saveDeptSort(){
    var ids=getIdsInTable();
    OrgDeptManageAction.saveDeptOrder(ids,savePbOrderCallBack);
    function savePbOrderCallBack(data){
        if(data == "SUCC"){
        	successMsg("errMsg", "最新排序保存成功。");
        }else{
        	errMsg("errMsg", data);
        }
        return;
    }
}


//获取页面上记录的id的集合
function getIdsInTable(){
	    var result = new Array();
	    var trs = $("#deptTableBody > tr");
	    for(var i = 0; i < trs.length; i++){
	        var id = trs[i].getAttribute("id");
	        result[i]=id;
	    }
	    return result;
}

//初始化table
initTable();
</script>
</body>
</html>
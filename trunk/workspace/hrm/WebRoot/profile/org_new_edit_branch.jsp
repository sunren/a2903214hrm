<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>�ֹ�˾��Ϣά��</title>
<link rel="stylesheet" href="../resource/css/style.css" type="text/css" />
</head>
<body>
<form id="saveBranchDept" name="saveBranchDept" action="saveBranchDept.action" method="post">
<s:hidden id="nodeId" name="nodeId"></s:hidden>
<s:hidden id="nodeType" name="nodeType"></s:hidden>
<s:hidden id="dept.id" name="dept.id"/>
<s:hidden id="dept.departmentParentId.id" name="dept.departmentParentId.id"/>
<s:hidden id="dept.departmentStatus" name="dept.departmentStatus"/>
<s:hidden id="dept.departmentSortId" name="dept.departmentSortId"/>
<s:hidden id="dept.departmentCate" name="dept.departmentCate"/>
<s:hidden id="dept.departmentCreateDate" name="dept.departmentCreateDate"/>
<s:hidden id="dept.departmentEndDate" name="dept.departmentEndDate"/>
<s:hidden id="operateOver" name="operateOver"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="gridtableList" align="center">
	<tr>
		<td width="20%" align="right">��˾����<span class="required">*</span>��</td>
		<td width="20%" align="left">
		    <s:if test="dept.id!=null&&dept.id!=''">
				<s:textfield id="dept.departmentName" name="dept.departmentName" size="16" maxlength="64" readonly="true"/>
			</s:if>
			<s:else>
				<s:textfield id="dept.departmentName" name="dept.departmentName" size="16" maxlength="64"/>
			</s:else>
		</td>
		<td width="20%" align="right">��˾������</td>
		<td width="20%" align="left">
			<s:textfield id="dept.departmentDesc" name="dept.departmentDesc" size="16" maxlength="64"/>
		</td>
	</tr>
	<tr>
		<td align="right">���˴���</td>
		<td align="left">
			<s:textfield id="dept.departmentPersonInlaw" name="dept.departmentPersonInlaw" size="16" maxlength="64"/>
        </td>
        <td align="right">������ҵ��</td>
	    <td align="left" colspan="3">
			<s:textfield id="dept.departmentTrade" name="dept.departmentTrade" size="16" maxlength="64" />
	    </td>
	</tr>
	<s:if test="dept.id==null || dept.id==''">
	<tr>
	    <td align="right">����ְλ����<span class="required">*</span>��</td>
		<td align="left">
		    <s:textfield id="dept.respPb.pbName" name="dept.respPb.pbName" size="16" maxlength="64"/>
		</td>
		<td align="right">ְ��<span class="required">*</span>��</td>
	    <td align="left" colspan="3">
	        <s:select id="dept.respPb.pbJobTitle.jobtitleNo" name="dept.respPb.pbJobTitle.jobtitleNo" list="jobtitleList" listKey="jobtitleNo"
					  listValue="jobtitleName" multiple="false" emptyOption="true" size="1" />
	    </td>
	</tr>
	</s:if>
	<s:else>
	<tr>
	    <td align="right">����ְλ��</td>
		<td align="left">
			<s:property value="dept.respPb.pbName"/>
		</td>
		<td align="right">�����ˣ�</td>
		<td align="left">
			<s:property value="dept.deptHead.empName"/>
		</td>
	</tr>
	</s:else>
	
	<s:if test="dept!=null && dept.id!=null && dept.id!=''">
	<tr>
	    <td align="right">����������</td>
		<td align="left">
			<s:property value="dept.orderNum"/>
		</td>
		<td align="right">�ڱ�������</td>
		<td align="left">
			<s:property value="dept.actualNum"/>
		</td>
	</tr>
	<tr>
	    <td align="right">״̬��</td>
		<td align="left">
		    <s:if test="dept.departmentStatus==1">����</s:if><s:elseif test="dept.departmentStatus==0">ͣ��</s:elseif>
		</td>
		<td align="right">ȱ��������</td>
		<td align="left">
			<s:property value="(dept.orderNum-dept.actualNum)>0?dept.orderNum-dept.actualNum:0"/>
		</td>
	</tr>
	<tr>
	    <td align="right">ֱ���ϼ���Ԫ��</td>
		<td align="left">
			<s:property value="dept.departmentParentId.departmentName"/>
		</td>
		<td align="right">ֱ���¼���Ԫ��</td>
		<td align="left">
		    <s:iterator value="dept.subDeptList" status="index">
		        <s:property value="departmentName"/>
		        <s:if test="!#index.isLast()">,</s:if>
		    </s:iterator>
		</td>
	</tr>
	<tr>
	    <td align="right">����ʱ�䣺</td>
		<td align="left">
			<s:property value="dept.departmentCreateDate"/>
		</td>
		<td align="right">ͣ��ʱ�䣺</td>
		<td align="left">
			<s:property value="dept.departmentEndDate"/>
		</td>
	</tr>
	</s:if>
	
	<tr>
	    <td align="right">��ע��</td>
	    <td align="left" colspan="3">
			<s:textarea id="dept.departmentMemo" name="dept.departmentMemo" cols="30" rows="3"></s:textarea>
	    </td>
	</tr>

	<tr>
	    <td align="center" colspan="4">
		    <input type="button" name="save" value="����" onclick="saveBranch();"/>
	    </td>
	</tr>
</table>
</form>
<script type="text/javascript">
function saveBranch(){
	// �����������������
	var deptId = document.getElementById("dept.id").value;
	if(deptId==null || deptId==""){
		if(document.getElementById("dept.departmentName").value.trim() == ""){
			alert("����д��˾���ƣ�");
			return;
		}
		if(document.getElementById("dept.respPb.pbName").value.trim() == ""){
			alert("����д����ְλ���ƣ�");
			return;
		}
		if(document.getElementById("dept.respPb.pbJobTitle.jobtitleNo").value.trim() == ""){
			alert("��ѡ������ְ��");
			return;
		}
	}
	
	document.forms[0].submit();
}
// �����������������ִ�����ϵ���Ӳ�����
var tree = window.parent.tree;
var orgDeptTree = window.parent.orgDeptTree;
var operateOver = document.getElementById('operateOver').value;
if(operateOver == 'newOver'){
	var nodeId = document.getElementById("dept.id").value;
	var parentId = document.getElementById("dept.departmentParentId.id").value;
	var nodeName = document.getElementById("dept.departmentName").value;
	var nodeType = document.getElementById("dept.departmentCate").value;
	var status = document.getElementById("dept.departmentStatus").value;
    tree.insertNewItem(parentId,nodeId,nodeName);
	document.getElementById('operateOver').value = '';
	orgDeptTree.addToMemory(nodeId, parentId, nodeName, nodeType, status);
	tree.focusItem(nodeId);// ��ȡ���㣻
}

// �����ظ���
if(operateOver == 'nameRepeat'){
	alert("�ڵ������ظ���");
}
</script>
<%@ include file="search_emp_div.jsp"%>
</body>
</html>

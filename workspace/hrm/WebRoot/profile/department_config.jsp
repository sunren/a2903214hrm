<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="ww" uri="webwork"%>
<%  response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%> 
<html>
<head>
	<script type="text/javascript" src="../resource/js/profile/department_config.js"></script>
</head>
<body>
<table id="table_department" cellpadding="0" cellspacing="1" width="85%" class="tablesorter" >
    <thead>
        <tr align="center">
            <th width="20%">��������</th>
            <th width="40%">��������</th>
            <th width="10%" >���ž���</th>
            <th width="10%" >״̬</th>
        </tr>
    </thead>
    <tbody id="tbody_department">
        <ww:iterator value="allDept">
            <tr id='<ww:property value="id"/>' departmentStatus='<ww:property value="departmentStatus"/>' headNo="<ww:property value='headNo'/>" align="center">
                <td nowrap="nowrap"><ww:property value="departmentName" /></td>
                <td nowrap="nowrap"><ww:property value="departmentDesc" /></td>
                <td nowrap="nowrap"><ww:property value="headName" /></td>
            	<td><ww:if test="departmentStatus==0">ͣ��</ww:if><ww:else>����</ww:else>
              		<input type="hidden" value="<ww:property value='departmentStatus'/>" />
              	</td>
            </tr>
        </ww:iterator>
    </tbody>
</table>
<div class="btnlink">
    <a href="#" id="link_add_department">����</a>
	<a href="#" id="link_delete_department">ɾ��</a>
	<a href="#" id="link_update_department">�޸�</a>
	<a href="#" id="link_sort_department">��������</a>
</div>
<div id="dialog_department" title="��������" >  
<form id="departmentForm" method="post">
    <table cellpadding="0" cellspacing="1" width="100%">
            <tr>
                <td>��������<font color="red">*</font>��</td>
                <td class="errorMessage">
                    <input id="departmentName" type="text" size="32" maxlength="64"><label id="label_departmentName">&nbsp;</label>
                </td>
            </tr>
            <tr>
                <td>����������</td>
                <td class="errorMessage"><textarea id="departmentDesc" cols="30" rows="4"></textarea></td>
            </tr>
            <tr>
                <td>���ž���</td>
                <td>			    
					<ww:textfield name="depmanagerName" id="empName" readonly="true"></ww:textfield>  
					<img src="../resource/images/search_icon.gif" style="CURSOR: pointer" onclick='showChooseEmpOrManagerDiv()' alt='���ͼ��ѡ��Ա��'/>
				</td> 
			</tr>
			<tr>
            	<td>״̬<span class="required">*</span>��</td>
				<td>
					<select id="departmentStatus" name="departmentStatus">
						<option value="1">����</option>
						<option value="0">ͣ��</option>
					</select>
				</td>
			</tr>            
            <tr>
               <td colspan="2" align="center">
                   <input id="btn_add_department" type="button" value="����" />
				   <input id="btn_update_department" type="button" value="����" />
				   <input id="btn_close_department" type="button" value="�ر�" />
                </td>
				<ww:hidden name="lf_Bean.lr.lrEmpNo.id" id="orgheadsEmpNo"></ww:hidden>
				<ww:hidden id="departmentId"></ww:hidden>
			    <input id='departmentSortId' type="hidden" />
            </tr>
     </table>
</form>
</div>
<script type="text/javascript">
	var config={
		tableId		:"table_department",
		tbodyId		:"tbody_department",
		dialogId	:"dialog_department",
		updateButton:"btn_update_department",
		addButton	:"btn_add_department",
		dialogHeight:240,
		dialogWidth :400,
		addLink		:"link_add_department",
		deleteLink	:"link_delete_department",
		updateLink	:"link_update_department",
		sortLink	:"link_sort_department",
		closeButton	:"btn_close_department",
		modal       :true
	};
	deptManager = new DepartmentManager(config);
</script>
</body>
</html>
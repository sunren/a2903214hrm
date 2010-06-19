<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%  response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%> 
<html>
<head>
	<script type="text/javascript" src="../resource/js/profile/businessunit_config.js"></script>
</head>
<body>
<table id="table_businessunit" cellpadding="0" cellspacing="1" width="85%" class="tablesorter">
  <thead>
      <tr align="center">
          <th width="10%">ҵ��Ԫ����</th>
          <th width="50%">ҵ��Ԫ����</th>
          <th width="10%" >ҵ��Ԫ����</th>
          <th width="10%" >��������</th>
          <th width="10%" >״̬</th>
      </tr>
  </thead>
  <tbody id="tbody_businessunit">
      <s:iterator value="allBusinessUnit">
          <tr id="<s:property value="id"/>" headNo="<s:property value='headNo'/>" parentDepartment="<s:property value='businessunitDeptNo.id'/>" businessunitStatus="<s:property value='businessunitStatus'/>" align="center">
              <td nowrap="nowrap"><s:property value="businessunitName" /></td>
              <td nowrap="nowrap"><s:property value="businessunitDesc" /></td>
              <td nowrap="nowrap"><s:property value="headName" /></td>
           	  <td nowrap="nowrap"><s:property value="businessunitDeptNo.departmentName" /></td>
          	  <td><s:if test="businessunitStatus==0">ͣ��</s:if><s:else>����</s:else>
              	  <input type="hidden" value="<s:property value='businessunitStatus'/>" />
              </td>
		   </tr>
      </s:iterator>
    </tbody>
</table>
<div class="btnlink">
    <a href="#" id="link_add_businessunit">����</a>
	<a href="#" id="link_delete_businessunit">ɾ��</a>
	<a href="#" id="link_update_businessunit">�޸�</a>
	<a href="#" id="link_sort_businessunit">��������</a>
</div>
<div id="dialog_businessunit" title="ҵ��Ԫ" >  
<form id="businessunitForm" method="post">
    <table cellpadding="0" cellspacing="1" width="100%">
            <tr>
                <td>ҵ��Ԫ����<font color="red">*</font>��
                </td>
                <td class="errorMessage">
                    <input id="businessunitName" type="text" size="32" maxlength="64"><label id="label_businessunitName">&nsp;</label>
                </td>
            </tr>
            <tr>
                <td>ҵ��Ԫ������</td>
                <td class="errorMessage"><textarea id="businessunitDesc" cols="30" rows="4"></textarea></td>
            </tr>
            <tr>
                <td>ҵ��Ԫ���ܣ�</td>
                <td>			    
					<s:textfield name="emp.empSupNo.buManagerName" id="empName" readonly="true"></s:textfield>  				 	 		
 					<img src="../resource/images/search_icon.gif" style="CURSOR: pointer" 
 						 onclick='showChooseEmpOrManagerDiv()'> 	
 				</td> 
			</tr>
			<tr>
				<td>�������ţ�</td>
				<td class="errorMessage">
					<select id="DefaultDept">
						<option value="">��ѡ��</option>
							<s:iterator value="deptList">
								<option value='<s:property value="id"/>'><s:property value="departmentName"/></option>
							</s:iterator>
					</select>
				</td>
			</tr>
			 <tr>
            	<td>״̬<span class="required">*</span>��</td>
				<td>
					<select id="businessunitStatus" name="businessunitStatus">
						<option value="1">����</option>
						<option value="0">ͣ��</option>
					</select>
				</td>
			</tr>     
            <tr>
               <td colspan="2" align="center">
                    <input id="btn_add_businessunit"	type="button" value="����" />
					<input id="btn_update_businessunit" type="button" value="����" />
					<input id="btn_close_businessunit" type="button" value="�ر�" />
                </td>
				<s:hidden name="lf_Bean.lr.lrEmpNo.id" id="orgheadsEmpNo" ></s:hidden>
				<s:hidden id="businessunitId"></s:hidden>
				<input id='businessunitSortId' type="hidden" />
			</tr>
     </table>
</form>
</div>
<script type="text/javascript">
	var config={
		tableId		:"table_businessunit",
		tbodyId		:"tbody_businessunit",
		dialogId	:"dialog_businessunit",
		updateButton:"btn_update_businessunit",
		addButton	:"btn_add_businessunit",
		dialogHeight:240,
		dialogWidth :400,
		addLink		:"link_add_businessunit",
		deleteLink	:"link_delete_businessunit",
		updateLink	:"link_update_businessunit",
		sortLink	:"link_sort_businessunit",
		closeButton	:"btn_close_businessunit",
        modal       :true
	};
	buManager = new BusinessunitManager(config);
</script>
</body>
</html>
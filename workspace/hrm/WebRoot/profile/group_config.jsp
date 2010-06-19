<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%  response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%> 
<html>
<head>
	<script type="text/javascript" src="../resource/js/profile/group_config.js"></script>
</head>
<body>
<table id="table_group" cellpadding="0" cellspacing="1" width="85%"class="tablesorter">
  <thead>
      <tr align="center">
          <th width="10%">����С������</th>
          <th width="30%">����С������</th>
          <th width="10%">С�鳤</th>
          <th width="10%">����ҵ��Ԫ</th>
          <th width="10%" >״̬</th>
      </tr>
  </thead>
  <tbody id="tbody_group">
      <s:iterator value="allGroup">
          <tr id="<s:property value="groupNo"/>" groupStatus="<s:property value='groupStatus'/>" headNo="<s:property value='headNo'/>" parentBuNo="<s:property value='groupBuNo.id'/>" align="center" >
              <td><s:property value="groupName" /></td>
              <td><s:property value="groupDesc" /></td>
              <td><s:property value="headName" /></td>
         	  <td><s:property value="groupBuNo.businessunitName" /></td>
         	  <td><s:if test="groupStatus==0">ͣ��</s:if><s:else>����</s:else></td>
          </tr>
      </s:iterator>

    </tbody>
</table>
<div class="btnlink">
    <a href="#" id="link_add_group">����</a>
	<a href="#" id="link_delete_group">ɾ��</a>
	<a href="#" id="link_update_group">�޸�</a>
	<a href="#" id="link_sort_group">��������</a>
</div>
<div id="dialog_group" title="����С��" >  
<form id="groupForm" method="post">
    <table cellpadding="0" cellspacing="1" width="100%">
        <tr>
            <td>����С������<font color="red">*</font>��</td>
            <td class="errorMessage">
                <input id="groupName" type="text" size="32" maxlength="64"><label id="label_groupName">&nbsp;</label>
            </td>
        </tr>
         <tr>
             <td>����С��������</td>
             <td class="errorMessage"><textarea id="groupDesc" cols="30" rows="4"></textarea></td>
         </tr>
         <tr>
             <td>����С���鳤��</td>
             <td>			    
				<s:textfield id="empName" readonly="true"></s:textfield>                              
                <img src="../resource/images/search_icon.gif" style="CURSOR: pointer" 
                 	onclick='showChooseEmpOrManagerDiv()'>  
             </td> 
		</tr>
		<tr>
			<td>����ҵ��Ԫ��</td>
			<td class="errorMessage">
				<select id="DefaultBu">
					<option value="">��ѡ��</option>
						<s:iterator value="BuList">
							<option value='<s:property value="id"/>'><s:property value="businessunitName"/></option>
						</s:iterator>
				</select>
			</td>
		</tr>
		<tr>
           	<td>״̬<span class="required">*</span>��</td>
			<td>
				<select id="groupStatus" name="groupStatus">
					<option value="1">����</option>
					<option value="0">ͣ��</option>
				</select>
			</td>
		</tr>            
        <tr>
             <td colspan="2" align="center">
               <input id="btn_add_group" type="button" value="����" />
			   <input id="btn_update_group" type="button" value="����" />
			   <input id="btn_close_group" type="button" value="�ر�" />
			   <s:hidden id="orgheadsEmpNo" ></s:hidden>
               <s:hidden id="groupNo"></s:hidden>
		       <input id='groupSortId' type="hidden" />
             </td>
	   </tr>
     </table>
</form>
</div>
<script type="text/javascript">
	var config={
		tableId		:"table_group",
		tbodyId		:"tbody_group",
		dialogId	:"dialog_group",
		updateButton:"btn_update_group",
		addButton	:"btn_add_group",
		dialogHeight:240,
		dialogWidth :400,
		addLink		:"link_add_group",
		deleteLink	:"link_delete_group",
		updateLink	:"link_update_group",
		sortLink	:"link_sort_group",
		closeButton	:"btn_close_group",
        modal       :true
	};
	groupManager = new GroupManager(config);
</script>
</body>
</html>
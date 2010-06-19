<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%  response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%> 
<html>
<head>
<script type="text/javascript" src="../resource/js/profile/jobtitle_config.js"></script>
</head>
<body>
<table id="table_jobtitle" cellpadding="0" cellspacing="1" width="85%" class="tablesorter">
  <thead>
      <tr align="center">
          <th nowrap="nowrap">ְ������</th>
          <th nowrap="nowrap">ȱʡ����</th>
          <th nowrap="nowrap"> ȱʡн�ʼ���</th>
          <th nowrap="nowrap">��Ƹ���ܾ�������</th>
          <th nowrap="nowrap">����</th>
          <th nowrap="nowrap">״̬</th>
      </tr>
  </thead>
  <tbody id="tbody_jobtitle">
      <s:iterator value="allJobTitle">
          <tr id="<s:property value="jobtitleNo"/>" jobtitleStatus="<s:property value='jobtitleStatus'/>" acctId="<s:property value='jobtitleDefaultAcct.id'/>" needgm="<s:property value='jobtitleNeedGmApprove'/>" jobgradeId="<s:property value='jobtitleDefaultJg.id'/>" align="center">
              <td><s:property value="jobtitleName" /></td>
              <td><s:property value="jobtitleDefaultAcct.esacName" /></td>
              <td><s:property value="jobtitleDefaultJg.jobGradeName" /></td>
              <td><s:property value="%{jobtitleNeedGmApprove==0?'����Ҫ':'��Ҫ'}" /></td>
              <td><s:property value="jobtitleNameDesc" /></td>
              <td><s:if test="jobtitleStatus==0">ͣ��</s:if><s:else>����</s:else>
              </td>
          </tr>
      </s:iterator>
    </tbody>
</table>
<div class="btnlink">
    <a href="#" id="link_add_jobtitle">����</a>
	<a href="#" id="link_delete_jobtitle">ɾ��</a>
	<a href="#" id="link_update_jobtitle">�޸�</a>
	<a href="#" id="link_sort_jobtitle">��������</a>
</div>
<div id="dialog_jobtitle" title="ְ������">
<form id="jobtitleForm" method="post">  
    <table cellpadding="0" cellspacing="1" width="100%">
            <tr>
                <td>ְ������<font color="red">*</font>��
                </td>
                <td class="errorMessage">
                    <input id="jobtitleName" type="text" size="32" maxlength="64"><label id="label_jobtitleName">&nbsp;</label>
                </td>
            </tr>
            <tr>
            	<td>ȱʡ���ף�</td>
				<td class="errorMessage">
					<select id="jobtitleDefaultAcct">
						<option value="">��ѡ��</option>
							<s:iterator value="empsalaryacctList">
								<option value='<s:property value="id"/>'><s:property value="esacName"/></option>
							</s:iterator>
					</select>
				</td>
            </tr>
            <tr>
				<td>ȱʡн�ʼ���</td>
				<td class="errorMessage">
					<select id="jobtitleDefaultJg">
						<option value="">��ѡ��</option>
							<s:iterator value="jobgradeList">
								<option value='<s:property value="id"/>'><s:property value="jobGradeName"/></option>
							</s:iterator>
					</select>
				</td>
			</tr>
			<tr>
				<td nowrap="nowrap">��Ƹ����Ҫ��<font color="red">*</font>��</td>
				<td class="errorMessage">
					<select id="jobtitleNeedGmApprove">
						<option value="0" selected="selected">����Ҫ�ܾ�������</option>
						<option value="1">��Ҫ�ܾ�������</option>
					</select>
				</td>
			</tr>
            <tr>
                <td>ְ��������</td>
                <td class="errorMessage"><textarea id="jobtitleNameDesc" cols="30" rows="4"></textarea></td>
            </tr> 
            <tr>
            	<td>״̬<span class="required">*</span>��</td>
				<td>
					<select id="jobtitleStatus" name="jobtitleStatus">
						<option value="1">����</option>
						<option value="0">ͣ��</option>
					</select>
				</td>
			</tr>          
            <tr>
               <td colspan="2" align="center">
                   <input id="btn_add_jobtitle" type="button" value="����" />
				   <input id="btn_update_jobtitle" type="button" value="����" />
				   <input id="btn_close_jobtitle" type="button" value="�ر�" />
				   <input id='jobtitleSortId' type="hidden" />
				   <s:hidden id="jobtitleNo"></s:hidden>
                </td>
			</tr>
     </table>
</form>
</div>
<script type="text/javascript">
	var config={
		tableId		:"table_jobtitle",
		tbodyId		:"tbody_jobtitle",
		dialogId	:"dialog_jobtitle",
		updateButton:"btn_update_jobtitle",
		addButton	:"btn_add_jobtitle",
		dialogHeight:280,
		dialogWidth :400,
		addLink		:"link_add_jobtitle",
		deleteLink	:"link_delete_jobtitle",
		updateLink	:"link_update_jobtitle",
		sortLink	:"link_sort_jobtitle",
		closeButton	:"btn_close_jobtitle"
	};
	jobtitleManager = new JobtitleManager(config);
</script>
</body>
</html>
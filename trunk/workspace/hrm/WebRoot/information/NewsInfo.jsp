<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="s" uri="webwork"%>
<%@ taglib prefix="ty" uri="/tengsource"%>
<%  response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%> 
<html>
<head>
	<title>��˾��Ϣ����</title>
	<script type="text/javascript" src="../dwr/interface/my.js"></script>			
	<script type="text/javascript" src="../resource/js/configuration/BaseManager.js"></script>
	<script type="text/javascript" src="NewsInfo.js"></script>
	<script type="text/javascript">
		$(document).ready(function() { 
			var config={
				tableId		:"table_infoclass",
				tbodyId		:"tbody_infoclass",
				dialogId	:"dialog_infoclass",
				updateButton:"btn_update_infoclass",
				addButton	:"btn_add_infoclass",
				dialogHeight:150,
				dialogWidth :300,
				addLink		:"link_add_infoclass",
				deleteLink	:"link_delete_infoclass",
				updateLink	:"link_update_infoclass",
				sortLink	:"link_sort_infoclass",
				closeButton	:"btn_close_infoclass"
			};
			infoclassManager = new InfoclassManager(config);
		});	
	</script>
   </head>
<body>
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'��˾��Ϣ����'" />
	<s:param name="helpUrl" value="'10'" />
</s:component>
<table id="table_infoclass" cellpadding="0" cellspacing="1" width="85%"class="tablesorter">
	<thead>
		<tr>
			<th nowrap="nowrap">����</th>
			<th width="8%">״̬	</th>
		</tr>
	</thead>
	<tbody id="tbody_infoclass">
		<s:iterator value="allInfo">
			<tr id="<s:property value="id"/>" infoclassStatus="<s:property value='infoclassStatus'/>">
				<td><s:property value="infoclassName" /></td>
				<td align="center">
					<s:if test="infoclassStatus==1">
					 ����
					</s:if><s:else>
					 �ر�
					</s:else>
				</td>
			</tr>
		</s:iterator>
	</tbody>
</table>
<div class="btnlink">
	<a href="#" id="link_add_infoclass">����</a>
	<a href="#" id="link_delete_infoclass">ɾ��</a>
	<a href="#" id="link_update_infoclass">�޸�</a>
	<a href="#" id="link_sort_infoclass">��������</a>
</div>
<div id="dialog_infoclass" title="��˾��Ϣ��������">				
	<form id="infoclassForm">
		<table cellpadding="0" cellspacing="1" width="100%" class="prompt_div_body">															
			<tr>
				<td>����<font color="red">*</font>��</td>								
				<td class="errorMessage">
					<input id="infoclassName" type="text" size="16" maxlength="16" />
					<label id="label_infoclassName">&nbsp;</label>
				</td>															
			</tr>																				
			<tr>
				<td>״̬<font color="red">*</font>��</td>
				<td class="errorMessage">
					<select id="infoclassStatus">
						<option value="1">����</option>
						<option value="0">�ر�</option>
					</select>
				</td>								
			</tr>									
			<tr>
				<td align="center" colspan="2">
					<input id="btn_add_infoclass" type="button" value="����" />
					<input id="btn_update_infoclass" type="button" value="�޸�" />
					<input id="btn_close_infoclass" type="button" value="�ر�" />
					<input type="hidden" id="infoclassId" />
					<input type="hidden" id="infoclassSortId" />
				</td>
			</tr>							
		</table>
	</form>
</div>
</body>
</html>
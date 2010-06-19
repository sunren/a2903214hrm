<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%  response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%> 
<html>
<head>
	<script type="text/javascript" src="../resource/js/profile/location_config.js"></script>
</head>
<body>
<table id="table_location" cellpadding="0" cellspacing="1" width="85%" class="tablesorter">
    <thead>
        <tr align="center">
            <th width="10%">��������</th>
            <th width="50%">����</th>
            <th width="10%" >����������</th>
            <th width="10%">״̬</th>
        </tr>
    </thead>
    <tbody id="tbody_location">
        <s:iterator value="allLocation">
            <tr id='<s:property value="id"/>' locationStatus="<s:property value='locationStatus'/>" headNo="<s:property value='headNo'/>" align="center">
                <td><s:property value="locationName" /></td>
                <td><s:property value="locationDesc" /></td>
                <td nowrap="nowrap"><s:property value="headName" /></td>
            	<td><s:if test="locationStatus==0">ͣ��</s:if><s:else>����</s:else>
              	</td>
            </tr>
        </s:iterator>
    </tbody>
</table>
<div class="btnlink">
    <a href="#" id="link_add_location">����</a>
	<a href="#" id="link_delete_location">ɾ��</a>
	<a href="#" id="link_update_location">�޸�</a>
	<a href="#" id="link_sort_location">��������</a>
</div>
<div id="dialog_location" title="��������" >  
<form id="locationForm" method="post">
    <table cellpadding="0" cellspacing="1" width="100%">
            <tr>
                <td>��������<font color="red">*</font>��</td>
                <td class="errorMessage"><input id="locationName" type="text" size="32" maxlength="64"></td>
            </tr>
            <tr>
                <td>����������</td>
                <td class="errorMessage"><textarea id="locationDesc" cols="30" rows="4"></textarea></td>
            </tr>  
            <tr>
                <td>���������ˣ�</td>
                <td>			    
					<s:textfield id="empName" readonly="true"></s:textfield>  
					<img src="../resource/images/search_icon.gif" style="CURSOR: pointer" onclick='showChooseEmpDiv(1,1)' alt='���ͼ��ѡ��Ա��'/>
				</td> 
			</tr>
            <tr>
            	<td>״̬<span class="required">*</span>��</td>
				<td>
					<select id="locationStatus" name="locationStatus">
						<option value="1">����</option>
						<option value="0">ͣ��</option>
					</select>
				</td>
			</tr>        
            <tr>
               <td colspan="2" align="center">
                    <input id="btn_add_location" type="button" value="����" />
					<input id="btn_update_location" type="button" value="����" />
					<input id="btn_close_location" type="button" value="�ر�" />
					<s:hidden id="locationSortId"></s:hidden>
                	<s:hidden id="locationId"></s:hidden>
                	<s:hidden id="orgheadsEmpNo"></s:hidden>
                </td>
            </tr>
     </table>
</form>
</div>
<script type="text/javascript">
	var config={
		tableId		:"table_location",
		tbodyId		:"tbody_location",
		dialogId	:"dialog_location",
		updateButton:"btn_update_location",
		addButton	:"btn_add_location",
		dialogHeight:200,
		dialogWidth :400,
		addLink		:"link_add_location",
		deleteLink	:"link_delete_location",
		updateLink	:"link_update_location",
		sortLink	:"link_sort_location",
		closeButton	:"btn_close_location",
        modal       :true
	};
	locationManager = new LocationManager(config);
</script>
</body>
</html>
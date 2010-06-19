<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%@ taglib prefix="ty" uri="/tengsource"%>
<html>
<head>
<style type="text/css">
.mySpan{
	font-size: 12px;
	color: #002780;
	text-decoration: underline;
	cursor: pointer
}</style>
<title>�ͻ�����</title>
</head>
<body >
<s:component template="bodyhead">
<s:param name="pagetitle"value="'�ͻ�����'" />
<s:param name="helpUrl" value="'61'" />
</s:component>
<br>
<form name="clientScanForm" id="clientScanForm" namespace="/configuration"action="clientscan.action" method="POST">
   <s:token/>
   <s:hidden id = "client" name ="client"/>
   <s:hidden id = "creatEnd" name ="creatEnd"/>
   <s:hidden id = "changeEnd" name ="changeEnd"/>
   <s:hidden id="order" name="pager.order"/>
   <s:hidden id="operate" name="pager.operate"/>
   <s:hidden name="pager.currentPage"/>
   
   <table width="100%" class="formtable" >
   <tr><td><table width ="90%"><tr>
   <td >���:</td>
   <td><s:textfield  name="client.id" id ="client.id" size="20" maxlength="8"/></td>   
   <td >����:</td>
   <td><s:textfield  name="client.clientName" id ="client.clientName" size="20" maxlength="128"/></td>   
   <td >���:</td>
   <td><s:textfield  name="client.clientShortName" id ="client.clientShortName" size="20" maxlength="32"/></td>   
   <td >��ַ:</td>
   <td><s:textfield  name="client.clientAddress" id ="client.clientAddress" size="36" maxlength="128"/></td>
   </tr>
   <tr>
   <td >�绰��</td> 
   <td><s:textfield  name="client.clientPhone" id ="client.clientPhone" size="20" maxlength="64"/></td>
   <td >���棺</td>
   <td ><s:textfield  name="client.clientFax" id ="client.clientFax" size="20" maxlength="64"/></td>
   <td >���䣺</td> 
   <td><s:textfield  name="client.clientEmail" id ="client.clientEmail" size="20" maxlength="64"/></td>      
   </tr></table></td><td>
   <table width ="10%"><tr>
   <td align ="left"><input type ="button" class ="button" value ="����" onclick ="begainClientScan();"></td>
   <td align ="left"><input type ="button" class ="button" value ="����" onclick ="resetClientScan();"></td>
   </tr></table></td></tr>
   </table>

   <table id="clientTable" align = "center"cellpadding="0" cellspacing="0" width="100%"border="0" class="gridtableList">
	<s:if test="pager.isSplit()">
	<tr><td colspan="11" align="right"  class="listViewPaginationTdS1">
	<a href="#" onclick="splits('first');"><img src='../resource/images/start.gif' width='11' height='9' alt='��ʼ'>��ʼ</a>
	<a href="#" onclick="splits('previous');"><img src='../resource/images/previous.gif' width='6' height='9' alt='��ҳ'>��ҳ</a>
    ��<s:property value="pager.currentPage+'/'+pager.totalPages"/>ҳ����<s:property value="pager.totalRows"/>����
	<a href="#" onclick="splits('next');">��ҳ<img src='../resource/images/next.gif' width='6' height='9'></a>
	<a href="#" onclick="splits('last');">���<img src='../resource/images/end.gif' width='11' height='9' alt='���'></a>
    </td></tr>
	</s:if> 
	
	<tr>
	<th  width ="5%"  align = "center">�ͻ����</th>
	<th  width ="15%" align ="center">�ͻ�����</th>
	<th  width ="20%" align ="center">�ͻ���ַ</th>
	<th  width ="10%" align ="center">�ͻ��绰</th>
	<th  width ="5%"  align ="center">�ͻ�����</th>
	<th  width ="5%"  align ="center">�ͻ�����</th>
	<th  width ="15%"  align ="center">��������</th>
	<ty:auth auths="911">
	<th width ="5%"  align ="center">����</th>
	</ty:auth></tr>
	
    <s:if test="!clientList.isEmpty()">
    <s:iterator value="clientList">
    <tr>
    <td  width ="5%"  ><span class ="mySpan" href="#" onclick = "toEditorClient('<s:property value="id"/>');"><s:property value="id"/></span></td>
	<td  width ="15%" ><s:property value="clientName"/></td>
	<td  width ="20%" ><s:property value="clientAddress"/></td>
	<td  width ="10%" ><s:property value="clientPhone"/></td>
	<td  width ="5%"  ><s:property value="clientFax"/></td>
    <td  width ="5%"  ><s:property value="clientEmail"/></td>
	<td  width ="15%" ><table><tr><td><s:property value="tempLimit"/></td><td><img src = "../resource/images/edit.gif" class = "mySpan" onclick="changeClientLimit('<s:property value='id'/>','<s:property value='tempLimit'/>');"/></td></tr></table></td>	
	
	<ty:auth auths="911">
	<td  width ="5%" >
	<img src = "../resource/images/delete.gif" class = "mySpan" onclick="deleteClient('<s:property value='id'/>')"/>
	</td></ty:auth>
   </tr></s:iterator></s:if>
   <s:else>
   <tr><td align ="center" colspan= "7">-- û�в鵽��صĿͻ����ݡ�--</td></tr>
   </s:else>
</table>
</form>

<form name="clientDeleteForm" id="clientDeleteForm" namespace="/configuration"action="clientdelete.action" method="POST">
<s:token/>
<s:hidden id = "clientId" name = "clientId"/>
</form>

<form name="clientUpdateForm" id="clientUpdateForm" namespace="/configuration"action="clientupdate.action" method="POST">
<s:token/>
<s:hidden id = "clientUpdateId" name = "clientUpdateId"/>
<s:hidden id = "changeString" name = "changeString"/>
</form>

<div id="dlglientlimit">
  <table width="100%" border="0" cellspacing="0" cellpadding="0"  class="formtable">
	<tr><td>&nbsp;</td></tr>
	<tr><td>������ �û���</td>
	<td><input type="textfield" id="clientLimitOU"  size="20" maxlength="20" disabled="true"></td></tr>
	<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ա����</td>
	<td><input type="textfield" id="clientLimitOE"  size="20" maxlength="20" disabled="true"></td></tr>
	
	
	
	<tr><td>������ �û���</td>
	<td><input type="textfield" id="clientLimitNU"  size="20" maxlength="20"></td></tr>
	<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ա����</td>
	<td><input type="textfield" id="clientLimitNE"  size="20" maxlength="20"></td></tr>
	<tr><td>&nbsp;</td></tr>
	<tr><td colspan="2" align="center">
	<input type="button" onclick="checkClientLimit();" value="�ύ">
	<input type="button" onclick="closeClientLimit();" value="ȡ��"></td></tr>
	</table>
	<iframe scrolling="no" style="position:absolute;z-index:-1;width:308px;height:114px;top:0px;left:0px;" frameborder="0" ></iframe>
</div>

<script type="text/javascript" language="javascript">
// ��ʼ��dialog��
hrm.common.initDialog('dlglientlimit', 320);

var updateId ;

/*****��ҳ*****/
function splits(var1){
	$('#operate').val(var1);
	document.getElementById("clientScanForm").submit();
}

/*****����������ƣ�������ʾ�㡣*****/
function changeClientLimit(id,limit){
	updateId = id;
	if(limit.length == 0){return;}
	var userL = limit.indexOf("USER");
	var empL = limit.indexOf("EMP");
	var moddle = limit.indexOf(",");
	if(userL == -1 || empL == -1 || moddle == -1){alert ("wrong in format!");return ;}
	var user = limit.substring(userL + 5,moddle);
	var emp = limit.substring(moddle + 5);
	$("#clientLimitOU").val(user);
	$("#clientLimitOE").val(emp);

	$('#dlglientlimit').dialog('option', 'title', '�޸��û�����');
	hrm.common.openDialog('dlglientlimit');
}

/*****����������ƣ��������ݿ⡣*****/
function checkClientLimit(){
 var user = $("#clientLimitNU").val();
 var emp = $("#clientLimitNE").val();
 if(user.length == 0){
   alert("�û���Ϊ�����ֶΣ�");
   return;
 }
 if(emp.length == 0){
   alert("Ա����Ϊ�����ֶΣ�");
   return;
 }
 if(!isDigit(user)){
   alert("�û�������Ϊ������");
   return;
 }
 if(!isDigit(emp)){
   alert("Ա��������Ϊ������");
   return;
 }
 $("#changeString").val("USER="+user+",EMP="+emp);
 $("#clientUpdateId").val(updateId);
 document.getElementById("clientUpdateForm").submit();
}

/*****����������ǲ���������*****/
function isDigit(str){
  var lengthStr = str.length;
  for(i = 0;i < lengthStr;i ++)
  if(str.charAt(i) >'9'||str.charAt(i) < '0'){return false;}
  else{continue;}
  return true;
}

/*****�ر��������Ʋ㡣*****/
function closeClientLimit(){
	$("#clientLimitOU").val("");
	$("#clientLimitOE").val("");
	$("#clientLimitNU").val("");
	$("#clientLimitNE").val("");
	hrm.common.closeDialog('dlglientlimit');
}

/*****��ʼ������*****/
function begainClientScan(){
	document.getElementById("clientScanForm").submit();
}

/*****������á�*****/
function resetClientScan(){
    document.getElementById("client.id").value = "";
	document.getElementById("client.clientName").value ="";
	document.getElementById("client.clientAddress").value ="";
	document.getElementById("client.clientEmail").value = "";
	document.getElementById("client.clientPhone").value = "";
	document.getElementById("client.clientFax").value = "";
    document.getElementById("clientScanForm").submit();
}

/*****���빫˾�����޸�ҳ�档*****/
function toEditorClient(clientId){
  window.location = "clientInit.action?clientId="+clientId;
}

function deleteClient(clientId){
  if(confirm("��ȷ��Ҫɾ�����Ϊ��"+clientId+"�Ŀͻ���"))
  {
    $("#clientId").val(clientId);
    document.getElementById("clientDeleteForm").submit();
  }
}

</script>

</body>
</html>
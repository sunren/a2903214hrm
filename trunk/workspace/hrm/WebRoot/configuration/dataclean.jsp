<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%@ taglib prefix="jscalendar" uri="/jscalendar" %>
<html>
<head>
<title>数据清理</title>
</head>
<body onload ="getOldLook();">
<s:component template="bodyhead">
<s:param name="pagetitle"value="'数据清理'" />
<s:param name="helpUrl" value="'60'" />
</s:component>
<br>
<form name="datascanForm" id="datascanForm" namespace="/configuration" action="datascan.action" method="POST">
  <s:token/>
  <s:hidden id = "modelChoose" name ="modelChoose"/>
  <s:hidden id = "selectChoose" name ="selectChoose"/>
   <table width="100%" class="formtable" >
   <tr><td><table width ="90%"><tr>
   <td >模块:</td>
   <td><s:select id="mymodel"  name="mymodel"  list=" #{0:'请选择',1:'全部', 2:'薪资', 3:'招聘',4:'考勤',5:'培训'}"/></td>   
   <td>
   <table><tr>
   <td align ="right">创建时间：</td> 
   <td>
   <s:textfield id="begainCreate" name="begainCreate" onclick="WdatePicker()" size="10"/>
   <img onclick="WdatePicker({el:'begainCreate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
   </td>
   <td>
   <s:textfield id="endCreate" name="endCreate" onclick="WdatePicker()" size="10"/>
   <img onclick="WdatePicker({el:'endCreate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
   </td>		
   </tr></table>
   </td>
   </tr>
   <tr>
   <td >状态:</td>
   <td><s:select id="select"  name="select"  list=" #{0:'全部', 22:'已作废', 21:'已拒绝',1:'草稿'}"/></td>
   <td><table><tr>
   <td align ="right">修改时间：</td>
   <td align ="center"> 
   <s:textfield id="begainChange" name="begainChange" onclick="WdatePicker()" size="10"/>
   <img onclick="WdatePicker({el:'begainChange'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
   </td>
   <td> 
   <s:textfield id="endChange" name="endChange" onclick="WdatePicker()" size="10"/>
   <img onclick="WdatePicker({el:'endChange'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
   </td>
   </tr></table></td>
   </tr></table></td><td>
   <table width ="10%"><tr>
   <td align ="left"><input type ="button" class ="button" value ="查找" onclick ="begainScan();"></td>
   <td align ="left"><input type ="button" class ="button" value ="重置" onclick ="resetScan();"></td>
   </tr></table></td></tr>
   </table>
   
   <br>
   <span id ="tipspan">&nbsp;<input type ="button" value ="删除" class ="button" onclick="deleteDate();"> </span>
   <br>
   <table id="profiletable" align = "center"cellpadding="0" cellspacing="0" width="99%"border="0" class="gridtableList">
	<tr>
	<th  width ="5%"><input id="checkall"  name="checkall" class="checkbox" border ="0" type="checkbox" onclick="hrm.common.checkAllByName('chkall', 'checkall');" value="0"></th>
	<th  width ="20%" align ="center">信息描述</th>
	<th  width ="10%" align ="center">所属模块</th>
	<th  width ="10%" align ="center">状态</th>
	<th  width ="10%" align ="center">创建者</th>
	<th  width ="15%" align ="center">创建时间</th>
	<th  width ="15%" align ="center">最后一次修改者</th>
	<th  width ="15%" align ="center">最后一次修改时间</th>
	</tr>
	</table>	
   <div id="null" style="padding:3px; height:400px; overflow: auto;">
   <table name="scanTable" cellpadding="0" cellspacing="0" class="gridtableList" width="100%">
   <s:if test="!scanList.isEmpty()">
   <s:iterator value="scanList">
   <tr><td width ="5%">
   <input class="checkbox" type="checkbox" name="chkall" value=<s:property value="id"/>+<s:property value="tag"/>/></td>
   <td width ="20%"align ="center"><s:property value="descript"/></td>
   <td width ="10%"align ="center"><s:property value="model"/></td>
   <td width ="10%"align ="center"><s:property value="state"/></td>
   <td width ="10%"align ="center"><s:property value="creatBy"/></td>
   <td width ="15%"align ="center"><s:property value="creatTime"/></td>
   <td width ="15%"align ="center"><s:property value="changeBy"/></td>
   <td width ="15%"align ="center"><s:property value="changeTime"/></td>
   </tr></s:iterator></s:if>
   <s:else>
   <tr><td align ="center">-- 没有查到需要清除的数据。--</td></tr>
   </s:else>
</table></div>
</form>

<form name="dataResetForm" id="dataResetForm" namespace="/configuration"action="dataclean.action" method="POST">
</form>

<form name="dataDeleteForm" id="dataDeleteForm" namespace="/configuration"action="datadelete.action" method="POST">
<s:token/>
<s:hidden id = "deleteChoose" name = "deleteChoose"/>
<s:hidden id = "modelChoose" name ="modelChoose"/>
<s:hidden id = "selectChoose" name ="selectChoose"/>
<s:hidden id = "begainCreate" name = "begainCreate"/>
<s:hidden id = "endCreate" name ="endCreate"/>
<s:hidden id = "begainChange" name ="begainChange"/>
<s:hidden id = "endChange" name ="endChange"/>

</form>

<script type="text/javascript" language="javascript">
// 查询；
function begainScan(){
    $("#selectChoose").val($("#select").val());
	var model = $("#mymodel").val();
	if(model == 0){$("#modelChoose").val("None");}
	else if(model == 1){$("#modelChoose").val("All");}
	else if(model == 2){$("#modelChoose").val("Empsalaryadj");}
	else if(model == 3){$("#modelChoose").val("Recruitplan");}
	else if(model == 4){$("#modelChoose").val("LeaveOvertime");}
	else if(model == 5){$("#modelChoose").val("Tremployeeplan");}
	document.getElementById("datascanForm").submit();
}

// 设置上一次的查询条件；
function getOldLook(){
	var model = $("#modelChoose").val();
	if($("#selectChoose").val().length == 0){$("select").val(0);}
	else{$("#select").val($("#selectChoose").val());}	
	if(model == "None"){$("#mymodel").val(0);}
	else if(model == "All"){$("#mymodel").val(1);}
	else if(model == "Empsalaryadj"){$("#mymodel").val(2);}
	else if(model == "Recruitplan"){$("#mymodel").val(3);}
	else if(model == "LeaveOvertime"){$("#mymodel").val(4);}
	else if(model == "Tremployeeplan"){$("#mymodel").val(5);}
}

// 重置
function resetScan(){
   window.location="dataclean.action";
}

// 删除
function deleteDate(){
    var form = document.getElementById("datascanForm");
    var check_all = document.getElementById("checkall");
    var deleteId = "";
	for(var i=0,j=form.elements.length;i<j;i++){
	  var e = form.elements[i];
	  if(e == check_all){continue;}
	  if (e.name == 'chkall' && e.checked == true){deleteId += e.value;}
	}
	if(deleteId.length == 0 ){alert("请选择要删除的信息！");return;}
	$("#deleteChoose").val(deleteId);
	document.getElementById("dataDeleteForm").submit();
}
</script>
</body>
</html>
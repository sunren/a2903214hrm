<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="s" uri="webwork"%>

<html>
<head>
<script type="text/javascript" src="../resource/js/email/esinit.js"></script>	
<%@ taglib uri="http://fckeditor.net/tags-fckeditor" prefix="FCK"%>

<title>发送新邮件</title>
</head>
<body>
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'邮件通知'" />
	<s:param name="helpUrl" value="'12'" />
</s:component>
<s:form name="emailsend" action="emailsend" namespace="/configuration" method="POST" enctype="multipart/form-data">
<s:hidden name="templateId"/>
<s:hidden id="operation" name="operation"/>
<s:hidden id="mail_esContent" name="mail.esContent"/>
<table width="95%"  border="0" cellspacing="1" cellpadding="5">
  <tr>
    <td width="100" align="right" class="grayFont">选择收信人：</td>
    <td>
    	<div class="btnlink">
    		<a href="#" onclick="selectClass('all')">所有人</a>
			<a href="#" onclick="selectClass('dep')">按部门</a>
			<!-- <a href="#" onclick="selectClass('bu')">按业务单元</a>-->
			<a href="#" onclick="selectClass('local')">按地区</a>
		</div>
  </tr>	  
</table>

<!-- 按分类选择发信人 -->
<table width="660"  border="0" cellspacing="0" cellpadding="0" class="formtable" id="classTable" style="display:none;margin-left:117px;">  
  <tr>
    <td>
    <!-- 部门列表 -->
	<div id="depList" style="display:none">    
	    <s:iterator value="departmentList" status="index1">
	    <span style='width:100px;height:25px;'>
		    <input type="checkbox" class ="checkbox" id="depRadio<s:property value="id"/>" name="depRadio<s:property value="id"/>" 
		    		value="<s:property value="departmentName"/>"
		    		onclick="selectSubClass('dep','<s:property value="id"/>')" >
		    		<s:property value="departmentName"/>
		    		</span>
	    </s:iterator>  
	</div>
	<!-- 业务单元列表 -->
	<div id="buList" style="display:none">
	    <s:iterator value="buList" status="index2">
	    <span style='width:100px;height:25px;'>
		    <input type="checkbox" class ="checkbox" id="buRadio<s:property value="id"/>" name="buRadio<s:property value="id"/>" 
		    		value="<s:property value="businessunitName"/>"
		    		onclick="selectSubClass('bu','<s:property value="id"/>')" >
		    		<s:property value="businessunitName"/>
		 </span>
	    </s:iterator>      
	</div>
	<!-- 地区列表 -->
	<div id="localList" style="display:none">
	    <s:iterator value="locationList" status="index3">
	    <span style='width:100px;height:25px;'>
		    <input type="checkbox" class ="checkbox" id="localRadio<s:property value="id"/>" name="localRadio<s:property value="id"/>" 
		    		value="<s:property value="locationName"/>"
		    		onclick="selectSubClass('local','<s:property value="id"/>')" >
		    		&nbsp;<s:property value="locationName"/>
		 </span>
	    </s:iterator> 
	</div>  
	  		
    </td>
  </tr>
</table>

<!-- 员工列表 -->
<div id="empList" style="display:none;margin-left:117px;border-top:0px solid yellow;width:660px;">
s</div>
<!-- 是否 cc -->
<table width="95%"  border="0" cellspacing="1" cellpadding="5" style="display:none;">
  <tr>
    <td width="100" align="right" class="grayFont">选择抄送人：</td>
    <td><input type="radio" name="ccEmp" value="0" checked>&nbsp;无&nbsp;
	    <input type="radio" name="ccEmp" value="1">&nbsp;本人&nbsp;
		<input type="radio" name="ccEmp" value="2">&nbsp;部门经理&nbsp;	    
	</td>
  </tr>
</table>

<!-- 模板列表 -->
<s:if test="templateList!=null">
<table id="templateTale" width="95%"  border="0" cellspacing="1" cellpadding="5">  
  <tr>
  <td width="100" align="right" class="grayFont" valign="top">标题：</td>
    <td>
	<s:textfield id="etTitle" name="mail.esTitle" size="60"/>
    </td>  
  </tr>
  </table>
<table width="95%"  border="0" cellspacing="1" cellpadding="5">
  <tr>
  	<td width="100" align="right" class="grayFont">内容：</td>
    <td>
	<s:richtexteditor toolbarCanCollapse="false" height="200"
			width="500" id="etContent"
			name="etContent" value="{APPLIER}，您好！</BR></BR></BR></BR>{SYSNAME}管理员</BR>{PHONE}"
			defaultLanguage="zh-cn" /> 		
    </td>
  </tr>   
</table>
</s:if>

<!-- 操作 -->
<table width="95%"  border="0" cellspacing="1" cellpadding="5">  
  <tr>
    <td align="center">
    	<INPUT type="button" NAME="btn" onclick="sendmail();" value="发送">
    	<INPUT type="button" NAME="btn" onclick="location.href='emailSearch.action';" value="取消">		
    </td>
  </tr>
</table>
</s:form>

<script>

//根据二级分类　列出员工
function selectSubClass(classVar,classValue){
	var emplist="<span style='width:100px;height:30px;'><input type='checkbox' class ='checkbox' id='allEmpNo' name='allEmpNo' value='[all]' onclick='allEmpSelectd();' checked>全部</span><br>";
	emplist+="<div style='height:100px;overflow-y:auto;border-top:1px solid #ccc;border-bottom:1px solid #ccc;padding-top:3px;'>"
	if($('#empList').hide()){
		//$('empTable').style.display="block";
		$('#empList').show();
	}
	if(classVar=='all'){
		<s:iterator value="empList" status="index">
			emplist+="<span style='width:100px;height:25px;'><input type='checkbox' class ='checkbox' name='empNo' value='<s:property value="id"/>'><s:property value="empName"/></span>";
		</s:iterator>		
	}else if(moreClassSelectd()!=""){
		emplist+=moreClassSelectd();
	}else if(moreClassSelectd()==""){
		$('#empList').hide();
		return;
	}
	$('#empList').html(emplist+"</div>");
	emplist="";
	allEmpSelectd();
}

function getEmpListCheckbox(classVar,classValue){
	var empclass="";
	var emplist="";
	if(classVar==null || classVar.trim().length==0)return;
	if(classVar=='dep'){
		<s:iterator value="empList" status="index">
			<s:if test="empDeptNo!=null">
				empclass='<s:property value="empDeptNo.id"/>';
				if(empclass!=null && empclass==classValue)
					emplist+="<span style='width:100px;height:25px;'><input type='checkbox' class ='checkbox' name='empNo' value='<s:property value="id"/>'><s:property value="empName"/></span>";
			</s:if>
		</s:iterator>				
	}else if(classVar=='bu'){
		<s:iterator value="empList" status="index">
			<s:if test="empBuNo!=null">
				empclass='<s:property value="empBuNo.id"/>';
				if(empclass!=null && empclass==classValue)
					emplist+="<span style='width:100px;height:25px;'><input type='checkbox' class ='checkbox' name='empNo' value='<s:property value="id"/>'><s:property value="empName"/></span>";
			</s:if>
		</s:iterator>		
	}else if(classVar=='local'){
		<s:iterator value="empList" status="index">
			<s:if test="empLocationNo!=null">
				empclass='<s:property value="empLocationNo.id"/>';
				if(empclass!=null && empclass==classValue)
					emplist+="<span style='width:100px;height:25px;'><input type='checkbox' class ='checkbox' name='empNo' value='<s:property value="id"/>'><s:property value="empName"/></span>";
			</s:if>
		</s:iterator>
	}
	return emplist;
}

function selectClass(className){
	if(className=="" || className=="all"){
		$('#classTable').hide();
		$('#empList').show();
	}
	$('#classTable').show();
	$('#depList').hide();
	$('#buList').hide();
	$('#localList').hide();
	clearSelected();
	if(className=='dep'){	
		$('#depList').show();
	}else if(className=='bu'){	
		$('#buList').show();
	}else if(className=='local'){	
		$('#localList').show();
	}else{
		$('#classTable').hide;
		selectSubClass('all','');
	}
}

function clearSelected(){
	var obj=classTable.getElementsByTagName("input");
	for(i=0;i<obj.length;i++){
		if(obj[i].id.indexOf("Radio")>0 && obj[i].checked){
			obj[i].checked=false;
		}
	}
	$('#empList').html("");
}

function moreClassSelectd(){
	var obj=classTable.getElementsByTagName("input");
	var empList="";
	for(i=0;i<obj.length;i++){
		var objid=obj[i].id;
		if(obj[i].checked==true){
			if(objid.indexOf("depRadio")==0){
				empList+=getEmpListCheckbox('dep',objid.substring(8,objid.length));
			}else if(objid.indexOf("buRadio")==0){
				empList+=getEmpListCheckbox('bu',objid.substring(7,objid.length));
			}else if(objid.indexOf("localRadio")==0){
				empList+=getEmpListCheckbox('local',objid.substring(10,objid.length));
			}	
		}
	}
	return empList;
}

function allEmpSelectd(){
	var obj=document.getElementsByName("empNo");
	if($('#allEmpNo').val()=='[all]' && document.getElementById('allEmpNo').checked){
		for(i=0;i<obj.length;i++){
			obj[i].checked=true;
		}

	}else{
		for(i=0;i<obj.length;i++){
			obj[i].checked=false;
		}		
	}
}

function sendmail(){
	var obj=document.getElementsByName("empNo");
	var emptarget=0;
	for(i=0;i<obj.length;i++){
		if(obj[i].checked){
			emptarget++;
		}
	}	
	if(emptarget==0){
		alert("至少选择一个收信人！");
		return;
	}
	if(document.getElementById('etTitle').value.length==0){
		alert("主题不能为空！");
		return;
	}
	var oEditor=FCKeditorAPI.GetInstance("etContent");
	if(oEditor.GetXHTML()==""){
		alert("内容不能为空！");
		return;
	}	
	$('#mail_esContent').val(oEditor.GetXHTML());
	$('#operation').val("send");
	document.emailsend.submit();
}
</script>
</body>
</html>

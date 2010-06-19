<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%  response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%> 
<html>
<head>
<link rel="stylesheet" href="../resource/css/style.css" type="text/css" />
<!-- DWR start -->
<script type="text/javascript" src="../dwr/engine.js"></script>
<script type="text/javascript" src="../dwr/util.js"></script>
<!-- DWR end -->
<script type="text/javascript" src="../resource/js/hrm/common.js"></script>
<script type="text/javascript" src="../dwr/interface/pbManage.js"></script>
</head>
<body>
<s:form id="pbBasicInfoForm" action="savePbBasicInfo" onsubmit="return checkField();">
<s:hidden name="pb.pbDeptId.id"/>
<s:hidden name="pb.id"/>
<table cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
   <tr>
	   <td></td>
	   <td align="right">
	       所属部门:
	   </td>
	   <td>
	     <s:property  value="pb.pbDeptId.departmentName" />
	   </td>
	   <td></td>
	   <td align="right">
	       是否负责职位:
	   </td>
	   <td >
	      <SPAN id="isChargePos" />
	   </td>
   </tr>
   
   <tr>
	   <td></td>
	   <td align="right">
	       职位名称:
	   </td>
	   <td>
	     <s:textfield  id="pbName" name="pb.pbName" onblur="checkName();" maxlength="64"/>
	     <font color="red"><SPAN id="pbNameMsg"/></font>
	   </td>
	   <td></td>
	   <td align="right">
	      职位状态:
	   </td>
	   <td >
	      <SPAN id="pbStatus" />
	   </td>
   </tr>
   
   <tr>
	   <td>
	     <s:select label="职务" name="pb.pbJobTitle.jobtitleNo" list="jobtitleList" listKey="jobtitleNo"
					  listValue="jobtitleName"  />
	   </td>
	   <td>
	     <s:if test="exceedConfig.equals(\"0\")">
	     </s:if>
	     <s:else>
	      <s:textfield label="编制人数" id="pbMaxEmp" name="pb.pbMaxEmployee" onkeydown="HRMCommon.checkPositive(event)"/>
	     </s:else>
	   </td>
   </tr>
   
   <tr>
       <s:if test="pbSelectList!=null">
	   <td>
	      <s:select label="上级职位" name="pb.pbSupId.id" list="pbSelectList" listKey="id"
			listValue="pbName" multiple="false" emptyOption="true" value="%{pb.pbSupId.id}" size="1" />
	   </td>
	   </s:if>
	   <s:else>
	   <td></td>
	   <td align="right">上级职位:</td>
	   <td>无</td>
	   </s:else>
	   <td></td>
	   <td align="right">
	      在编人数:
	   </td>
	   <td >
	     <s:property value="pb.activeEmpNum"/>
	   </td>
   </tr>
   
   <tr>
	   <td>
	     <s:textfield label="工作地点"  name="pb.pbWorkAddress" maxlength="64"/>
	   </td>
	   <td></td>
	   <td align="right">
	      缺编人数:
	   </td>
	   <td >
	   <s:if test="exceedConfig.equals(\"0\")">
	     </s:if>
	     <s:else>
	      <s:property value="pb.lackEmpNum"/>
	     </s:else>
	   </td>
   </tr>
   <tr>
	   <td >
	     <s:textfield label="使用工具、设备"  name="pb.pbWorkTool" size="37" maxlength="128" />
	   </td>
	   
   </tr>
   <tr>
       <td >
	      <s:textfield label="工作时间" name="pb.pbWorkTime" size="37" maxlength="32"></s:textfield>
	   </td>
   </tr>
   <tr>
      <td></td>
      <td></td>
      <td> <SPAN>例如8:00-12:00，1:00-3:00</SPAN></td>
   </tr>
   <tr>
      <td align="center" nowrap="nowrap"  colspan="8">
      <s:if test="pbId==null">
      <input class="button" type="submit" value="保存"/>
      </s:if>
      <s:else>
      <input class="button" type="submit" value="修改"/>
      </s:else>
      </td>
   </tr>
</table>
</s:form>

<script type="text/javascript">
//设置是否负责职位的显示

var chargeVal='<s:property value='pb.pbInCharge'/>';
if(chargeVal==0){
   $('#isChargePos').html('不是');
}else if(chargeVal==1){
     $('#isChargePos').html('是');
}else{
  $('#isChargePos').html('');
}
//显示职位状态
var pbStatus='<s:property value='pb.pbStatus'/>';
var pbId='<s:property value='pbId'/>';
if(pbId==''){
    $('#pbStatus').html('启用');
}else{
   if(pbStatus==0){
   $('#pbStatus').html('停用');
   }else{
    $('#pbStatus').html('启用');
   }
}

//更新树

var lastOperate='<s:property value='lastOperate'/>'.trim();
if(lastOperate=="new"){
    //alert(lastOperate);
    var nodeId='<s:property value='pb.id'/>'.trim();
    var node= new window.parent.node();
    node.id=nodeId;
    node.parentId='<s:property value='pb.pbDeptId.id'/>'.trim();
    node.name='<s:property value='pb.pbName'/>'.trim();
    node.type=3;
    var nodeArr=window.parent.nodeArr;
    nodeArr[nodeId]=node;
    var tree = window.parent.tree;
    var pbTree=window.parent.pbTree;
    tree.insertNewItem(node.parentId,nodeId,node.name,0,'','', '','SELECT');
    pbTree.setNodesImage(nodeArr);
}else if(lastOperate=="modify"){
    //alert(lastOperate);
    var nodeId='<s:property value='pb.id'/>'.trim();
    var pbName='<s:property value='pb.pbName'/>'.trim();
    var tree = window.parent.tree;
    tree.setItemText(nodeId,pbName,null);
}
//检查名称是否重复

function checkName(){
	var id='<s:property value="pb.id"/>'.trim();
	var pbName=$('#pbName').val().trim();
	if(pbName==''){
	   $('#pbNameMsg').html('名称不能为空！');
	   $('#pbName').focus();
	   return;
	}
   var deptId='<s:property value="pb.pbDeptId.id"/>';
   function checkCallBack(data){
     // alert(data);
      if(data=="FAIL"){
        $('#pbNameMsg').html('名称已存在！');
        $('#pbName').val('');
      }else{
         $('#pbNameMsg').html('');
      }
      
  }
  pbManage.checkPbName(id,pbName,deptId,checkCallBack);
}
//提交form时验证

function checkField(){
  var pbName=$('#pbName').val().trim();
  if(pbName==""){
     alert("名称不能为空！");
     return false;
  }
  if(chargeVal == 1 && $('#pbMaxEmp').val() != 1){
	  alert(pbName+"为负责职位，编制必须为1人！");
	  return false; 
  }
}
</script>
</body>
</html>
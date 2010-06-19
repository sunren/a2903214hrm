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
	       ��������:
	   </td>
	   <td>
	     <s:property  value="pb.pbDeptId.departmentName" />
	   </td>
	   <td></td>
	   <td align="right">
	       �Ƿ���ְλ:
	   </td>
	   <td >
	      <SPAN id="isChargePos" />
	   </td>
   </tr>
   
   <tr>
	   <td></td>
	   <td align="right">
	       ְλ����:
	   </td>
	   <td>
	     <s:textfield  id="pbName" name="pb.pbName" onblur="checkName();" maxlength="64"/>
	     <font color="red"><SPAN id="pbNameMsg"/></font>
	   </td>
	   <td></td>
	   <td align="right">
	      ְλ״̬:
	   </td>
	   <td >
	      <SPAN id="pbStatus" />
	   </td>
   </tr>
   
   <tr>
	   <td>
	     <s:select label="ְ��" name="pb.pbJobTitle.jobtitleNo" list="jobtitleList" listKey="jobtitleNo"
					  listValue="jobtitleName"  />
	   </td>
	   <td>
	     <s:if test="exceedConfig.equals(\"0\")">
	     </s:if>
	     <s:else>
	      <s:textfield label="��������" id="pbMaxEmp" name="pb.pbMaxEmployee" onkeydown="HRMCommon.checkPositive(event)"/>
	     </s:else>
	   </td>
   </tr>
   
   <tr>
       <s:if test="pbSelectList!=null">
	   <td>
	      <s:select label="�ϼ�ְλ" name="pb.pbSupId.id" list="pbSelectList" listKey="id"
			listValue="pbName" multiple="false" emptyOption="true" value="%{pb.pbSupId.id}" size="1" />
	   </td>
	   </s:if>
	   <s:else>
	   <td></td>
	   <td align="right">�ϼ�ְλ:</td>
	   <td>��</td>
	   </s:else>
	   <td></td>
	   <td align="right">
	      �ڱ�����:
	   </td>
	   <td >
	     <s:property value="pb.activeEmpNum"/>
	   </td>
   </tr>
   
   <tr>
	   <td>
	     <s:textfield label="�����ص�"  name="pb.pbWorkAddress" maxlength="64"/>
	   </td>
	   <td></td>
	   <td align="right">
	      ȱ������:
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
	     <s:textfield label="ʹ�ù��ߡ��豸"  name="pb.pbWorkTool" size="37" maxlength="128" />
	   </td>
	   
   </tr>
   <tr>
       <td >
	      <s:textfield label="����ʱ��" name="pb.pbWorkTime" size="37" maxlength="32"></s:textfield>
	   </td>
   </tr>
   <tr>
      <td></td>
      <td></td>
      <td> <SPAN>����8:00-12:00��1:00-3:00</SPAN></td>
   </tr>
   <tr>
      <td align="center" nowrap="nowrap"  colspan="8">
      <s:if test="pbId==null">
      <input class="button" type="submit" value="����"/>
      </s:if>
      <s:else>
      <input class="button" type="submit" value="�޸�"/>
      </s:else>
      </td>
   </tr>
</table>
</s:form>

<script type="text/javascript">
//�����Ƿ���ְλ����ʾ

var chargeVal='<s:property value='pb.pbInCharge'/>';
if(chargeVal==0){
   $('#isChargePos').html('����');
}else if(chargeVal==1){
     $('#isChargePos').html('��');
}else{
  $('#isChargePos').html('');
}
//��ʾְλ״̬
var pbStatus='<s:property value='pb.pbStatus'/>';
var pbId='<s:property value='pbId'/>';
if(pbId==''){
    $('#pbStatus').html('����');
}else{
   if(pbStatus==0){
   $('#pbStatus').html('ͣ��');
   }else{
    $('#pbStatus').html('����');
   }
}

//������

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
//��������Ƿ��ظ�

function checkName(){
	var id='<s:property value="pb.id"/>'.trim();
	var pbName=$('#pbName').val().trim();
	if(pbName==''){
	   $('#pbNameMsg').html('���Ʋ���Ϊ�գ�');
	   $('#pbName').focus();
	   return;
	}
   var deptId='<s:property value="pb.pbDeptId.id"/>';
   function checkCallBack(data){
     // alert(data);
      if(data=="FAIL"){
        $('#pbNameMsg').html('�����Ѵ��ڣ�');
        $('#pbName').val('');
      }else{
         $('#pbNameMsg').html('');
      }
      
  }
  pbManage.checkPbName(id,pbName,deptId,checkCallBack);
}
//�ύformʱ��֤

function checkField(){
  var pbName=$('#pbName').val().trim();
  if(pbName==""){
     alert("���Ʋ���Ϊ�գ�");
     return false;
  }
  if(chargeVal == 1 && $('#pbMaxEmp').val() != 1){
	  alert(pbName+"Ϊ����ְλ�����Ʊ���Ϊ1�ˣ�");
	  return false; 
  }
}
</script>
</body>
</html>
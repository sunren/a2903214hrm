<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<!--
 =========================================================
' File:addsalaryconfig.jsp
' Version:1.1.0 standard version
' Date: 2007-2-2
' Script Written by hr.com
'=========================================================
' Copyright   2007 hr.com. All rights reserved.
' Web: http://www.hr.com
' Email: admin@hr.com
'=======================================================
 -->


<html>
	<head>
	<script type="text/javascript" src="../dwr/interface/DWRforAcctItemDef.js"></script>
	<script type="text/javascript" src="../dwr/interface/DWRforSalaryCalc.js"></script>
	<script type="text/javascript" src="../resource/js/hrm/compensation.js"></script>
	<link href="<s:url value="/resource/css/style.css"/>" rel="stylesheet" type="text/css" />
	</head>
<body onload="setAcctItem(document.getElementById('defaultAcct').value);">
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'添加薪资('+employee.empDistinctNo+')'" />
	<s:param name="helpUrl" value="'22'" />
</s:component>
	<form id="addForm" method="POST" namespace="/compensation" action="addSalaryConfig.action"  onsubmit="getElementById('doaction').disabled = true; return true;">
		<s:token/>
		<TABLE width="100%" class="formtable" id="acctTable" >
			<tr>
			  <s:hidden id="empid" label="员工编号" name="id" value="%{employee.id}" />
				<s:textfield  id="name" name="empName" label="员工姓名"  
					 value="%{employee.empName}" readonly="true" cssClass="nothinginput"   />
				<s:textfield  id="empType" name="empType" label="用工形式"  
					 value="%{employee.empType.emptypeName}" readonly="true" cssClass="nothinginput"   />
				<input type="hidden" id="defaultAcct" value="<s:property value='defaultAcct'/>"/>
			</tr>			
		    <tr>		
		<s:textfield id="escBankAccountNo" label="员工银行帐号" name="empsalaryconfig.escBankAccountNo" 
								size="24" onkeydown='HRMCommon.checkOnKeyDownFloat(event)'   maxlength="32" required="true" />
		<s:textfield id="escBankName" label="银行开户行" name="empsalaryconfig.escBankName" size="20" 
		                        maxlength="64" required="false"/>
			</tr>
			<tr>
			    <s:select id="jobgradeList" label="薪资级别"  name="empsalaryconfig.escJobgrade.id"
					list="jobgradeList" listKey="id" listValue="jobGradeName" required="true" />
				<s:textfield id="escCostCenter" label="成本中心" name="empsalaryconfig.escCostCenter" size="20" 
		                        maxlength="32" required="false"/>
			</tr>
			<tr>
			    <s:select id="acctList" label="请选择帐套" name="empsalaryconfig.escEsavId.id" 
						emptyOption="true" onchange="setAcctItem(this.value)" value="%{defaultAcct}"
						list="acctList" listKey="id" listValue="esavEsac.esacName" required="true"/>
			</tr>
			<tr height="45">
				<td colspan="4" align="center">
					<input id="doaction" type="button" name="okbtn" onclick="submitAction()" disabled="disabled" value="确定" >
					<input type="reset" name="reset" value="取消" >
					<input id="showDateBtn" type="button" name="showDateBtn" onClick="showData()" disabled="disabled" value="查看公式生成的数据">
					<input type="button" name="back" onclick="window.location='searchSalary.action'" value="返回" />					
				</td>				
			</tr>
		</TABLE>
	</form>
   <script type="text/javascript" language="javascript">
var acctItemLength;
function setAcctItem(acct) {
	
	for(var i=0;i<acctItemLength;i++) {
		document.getElementById('acctItems'+i).parentNode.removeChild(document.getElementById('acctItems'+i));
	}
	if(acct=='') {
		acctItemLength = 0;
		return;
	}
	DWRforAcctItemDef.getAcctItemsById(acct,callBack);
	function callBack(msg) {
		if(msg == null){
			errMsg("errMsg", "数据异常，请刷新后再试！");
			return;
		}
		var trObj;
		acctItemLength = msg.length/2;
		for(var i=0;i<msg.length;i++) {
			if(i%2==0){
				trObj = document.getElementById('acctTable').insertRow(4+i/2);
				trObj.id = 'acctItems'+(i/2);
			}
			var toWrite="onkeydown='HRMCommon.checkOnKeyDownFloat(event)'";
			if(msg[i].esaiDataIsCalc != 0) {
				toWrite="readOnly class='nothinginput'";
			}
			tdObj = document.createElement('td');
			tdObj.align='right';
			tdObj.width='15%'
			tdObj.innerHTML="<label for='empsalaryconfig.escColumn"+(i+1)+"'>"+msg[i].esaiEsdd.esddName+":</label>";
			trObj.appendChild(tdObj);
			tdObj = document.createElement('td');
			tdObj.width='35%'
			tdObj.innerHTML="<input type='text' maxlength='11' "+toWrite+" name='empsalaryconfig.escColumn"+(i+1)+"' id='escColumn"+(i+1)+"'/>"
			trObj.appendChild(tdObj);
		}
		if(acctItemLength>0) {
			document.getElementById('showDateBtn').disabled=false;
		}
		document.getElementById('doaction').disabled=false;
	}
}

function submitAction() {
    if(!HRMCp.checkAccountValid(document.getElementById('escBankAccountNo').value)){
        alert("银行账号格式错误！只允许字母，数字和-_*.<>()");
        return;
    }
    if(document.getElementById('escCostCenter').value!='' && !HRMCp.checkAccountValid(document.getElementById('escCostCenter').value)){
        alert("成本中心格式错误！只允许字母，数字和-_*.<>()");
        return;
    }
	isSubmit='1';
	showData();
}


var isSubmit='0';
function showData() {
	var acct  = document.getElementById('acctList').value;
	var jobGrade = document.getElementById('jobgradeList').value;
	var empid = document.getElementById('empid').value;
	//alert(acct);
	if(acct==null || acct=='') {
		alert("请选择帐套！");
		return;
	}
	
	var objetC=new Object;
	
	for(var i=1;i<acctItemLength*2+1;i++) {
		if(eval("document.getElementById('escColumn"+i+"')").value=='') {
			eval("document.getElementById('escColumn"+i+"')").value='0';
		}
		eval("objetC.escColumn"+i+"=document.getElementById('escColumn"+i+"').value");
	}
	var params = DWRUtil.getValues(objetC);
	DWRforSalaryCalc.calculateSalary(acct,empid,jobGrade,objetC,calculateCallback);
	function calculateCallback(msg) {
		if(msg == null){
			errMsg("errMsg", "数据异常，请刷新后再试！");
			return ;
		}
		for(var i=1;i<acctItemLength*2+1;i++) {
			eval("document.getElementById('escColumn"+i+"').value=msg.escColumn"+i);
		}
		if(isSubmit=='1') {
			isSubmit='0';
			document.getElementById('addForm').submit();
		}
	}
}
</script>	
</body>
</html>

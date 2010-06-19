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
	<s:param name="pagetitle" value="'���н��('+employee.empDistinctNo+')'" />
	<s:param name="helpUrl" value="'22'" />
</s:component>
	<form id="addForm" method="POST" namespace="/compensation" action="addSalaryConfig.action"  onsubmit="getElementById('doaction').disabled = true; return true;">
		<s:token/>
		<TABLE width="100%" class="formtable" id="acctTable" >
			<tr>
			  <s:hidden id="empid" label="Ա�����" name="id" value="%{employee.id}" />
				<s:textfield  id="name" name="empName" label="Ա������"  
					 value="%{employee.empName}" readonly="true" cssClass="nothinginput"   />
				<s:textfield  id="empType" name="empType" label="�ù���ʽ"  
					 value="%{employee.empType.emptypeName}" readonly="true" cssClass="nothinginput"   />
				<input type="hidden" id="defaultAcct" value="<s:property value='defaultAcct'/>"/>
			</tr>			
		    <tr>		
		<s:textfield id="escBankAccountNo" label="Ա�������ʺ�" name="empsalaryconfig.escBankAccountNo" 
								size="24" onkeydown='HRMCommon.checkOnKeyDownFloat(event)'   maxlength="32" required="true" />
		<s:textfield id="escBankName" label="���п�����" name="empsalaryconfig.escBankName" size="20" 
		                        maxlength="64" required="false"/>
			</tr>
			<tr>
			    <s:select id="jobgradeList" label="н�ʼ���"  name="empsalaryconfig.escJobgrade.id"
					list="jobgradeList" listKey="id" listValue="jobGradeName" required="true" />
				<s:textfield id="escCostCenter" label="�ɱ�����" name="empsalaryconfig.escCostCenter" size="20" 
		                        maxlength="32" required="false"/>
			</tr>
			<tr>
			    <s:select id="acctList" label="��ѡ������" name="empsalaryconfig.escEsavId.id" 
						emptyOption="true" onchange="setAcctItem(this.value)" value="%{defaultAcct}"
						list="acctList" listKey="id" listValue="esavEsac.esacName" required="true"/>
			</tr>
			<tr height="45">
				<td colspan="4" align="center">
					<input id="doaction" type="button" name="okbtn" onclick="submitAction()" disabled="disabled" value="ȷ��" >
					<input type="reset" name="reset" value="ȡ��" >
					<input id="showDateBtn" type="button" name="showDateBtn" onClick="showData()" disabled="disabled" value="�鿴��ʽ���ɵ�����">
					<input type="button" name="back" onclick="window.location='searchSalary.action'" value="����" />					
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
			errMsg("errMsg", "�����쳣����ˢ�º����ԣ�");
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
        alert("�����˺Ÿ�ʽ����ֻ������ĸ�����ֺ�-_*.<>()");
        return;
    }
    if(document.getElementById('escCostCenter').value!='' && !HRMCp.checkAccountValid(document.getElementById('escCostCenter').value)){
        alert("�ɱ����ĸ�ʽ����ֻ������ĸ�����ֺ�-_*.<>()");
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
		alert("��ѡ�����ף�");
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
			errMsg("errMsg", "�����쳣����ˢ�º����ԣ�");
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

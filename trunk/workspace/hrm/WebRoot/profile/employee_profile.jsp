<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<!--
 =========================================================
' File: my_empprofile.jsp
' Version:1.1.0 standard version
' Date: 2007-2-2
' Script Written by hr.com
'=========================================================
' Copyright   2007 hr.com. All rights reserved.
' Web: http://www.hr.com
' Email: admin@hr.com
'=======================================================
<log:warn message="..........................................."/>
<log:dump scope="request"></log:dump>
 -->
<head>
<link href="<s:url value="/resource/css/style.css"/>" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="../resource/css/edit_select.css" />
<script type="text/javascript" src="../dwr/interface/EmpTransfer.js"></script>
<script type="text/javascript" src="../dwr/interface/EmpEval.js"></script>
<script type="text/javascript" src="../dwr/interface/EmpReward.js"></script>
<jsp:include flush="true" page="../sitemesh/jsPackage.jsp"></jsp:include>
<script type="text/JavaScript" src="../resource/js/hrm/profile.js"></script>
<title>�޸ĵ���</title>
</head>
<body align="left">
<div id="selectcontent" class="selectdiv" style="visibility:hidden;pixelHeight:20px;z-index:2000;">
	<iframe id="selframe" frameborder="0" height="100%"></iframe>
	<div id="selecthtml" class="selectcontent">selectdate</div>
</div>
<!-- �������select -->
<script type="text/javascript" src="../resource/js/edit_select.js"></script>
<link rel="stylesheet" type="text/css" href="../resource/css/edit_select.css" />
	<s:component template="bodyhead"/>
	<ty:auth auths="101"><s:set name="hasAuth" value="'has'"/></ty:auth>
	<h3 align="left">���º�ͬ
		<s:if test="#request.hasAuth=='has'">
			<a href="#"><img src="../resource/images/CreateProject.gif" onclick="addContract();"></img></a>
		</s:if>
	</h3>
	<table cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
	     <tr>
	     	<th >��ʼ����</th>
	     	<th >��������</th>
	     	<th >��ͬ���</th>
	     	<th >��ͬ����</th>
	     	<th >��ͬ״̬</th>
	     	<th >��ͬ����</th>
	     	<th >��ע</th>
	     	<th>����</th>
	     </tr>
	     <tbody>
	     <s:iterator value="contractList" status="st">
			<tr id="<s:property value="ectId"/>">
			    <input type="hidden" name="ListectId" value="<s:property value="ectId"/>"/>
                <input type="hidden" name="ListectStartDate" value="<s:property value="ectStartDate"/>"/>
                <input type="hidden" name="ListectEndDate" value="<s:property value="ectEndDate"/>"/>
				<input type="hidden" name="ListetcExpire" value="<s:property value="etcExpire"/>"/>
				<input type="hidden" name="ListetcStatus" value="<s:property value="ectStatus"/>"/>
				<td align="center"><s:property value="ectStartDate"/></td>
				<td align="center"><s:property value="ectEndDate"/></td>
				<td align="center"><s:property value="ectNo"/></td>
				<td align="center"><input type="hidden" id="type<s:property value="ectId"/>" value="<s:property value="contractType.id"/>"/>
					<s:property value="contractType.ectName"/>
				</td>
				<td align="center"><input type="hidden" id="stat<s:property value="ectId"/>" value="<s:property value="ectStatus"/>"/>
					<s:if test="ectStatus==1">��Ч</s:if>
					<s:if test="ectStatus==2">��ֹ</s:if>
					<s:if test="ectStatus==3">���</s:if>
				</td>
				<td align="center"><input type="hidden" id="exp<s:property value="ectId"/>" value="<s:property value="etcExpire"/>"/>
					<s:if test="etcExpire==0">������</s:if>
					<s:if test="etcExpire==1">������</s:if>
				</td>
				<td align="center"><s:property value="ectComments"/></td>
				
				<td  width="90">
					<s:if test="ectAttatchment!='' && ectAttatchment!=null">
						<a id="href_<s:property value='ectId'/>"
							href="downProfile.action?fileName=<s:property value="ectAttatchment"/>&contentDisposition=<s:property value="ectAttatchment"/>">
							<img src="../resource/images/attachment.gif"/>
						</a>
						<input type="hidden" id="attach<s:property value="ectId"/>" value="<s:property value="ectAttatchment"/>"/>
						<script type="text/javascript">
							var filename = '<s:property value="currentEmp.empDistinctNo"/>'+"_";
							filename += '<s:property value="ectStartDate"/>'+"_";
							<s:if test="ectEndDate != null">
								filename += '<s:property value="ectEndDate"/>'+"_";
							</s:if>
							var exp = '<s:property value="etcExpire"/>';
							filename += (exp=='0')?'������':'������';
							var extend = '<s:property value="ectAttatchment"/>';
							filename += "."+extend.split('.')[1];
							var tmp = 'downProfile.action?fileName=<s:property value="ectAttatchment"/>&contentDisposition=';
							$("#href_"+"<s:property value='ectId'/>").href = tmp + filename;
						</script>
					</s:if>
					<s:else>&nbsp;&nbsp;&nbsp;</s:else>
					<s:if test="#request.hasAuth=='has'">
						<s:if test="#st.first">
							<a href="#" onclick="showEctForm('update','<s:property value="ectId"/>')">
								<img src="../resource/images/edit.gif" alt="�޸�"></img></a>&nbsp;
							<a href="#" onclick="ectDel('<s:property value="ectId"/>')">
								<img src="../resource/images/delete.gif" alt="ɾ��"></img></a>
							<s:if test="ectStatus==1&&etcExpire==0">
								<input type="hidden" id="endDate<s:property value="ectId"/>" value="<s:property value="ectEndDate"/>"/>
								<input type="hidden" id="startDate<s:property value="ectId"/>" value="<s:property value="ectStartDate"/>"/>
							</s:if>
				   		</s:if>
				    </s:if>
				</td>
			</tr>
		 </s:iterator>
	     </tbody>
	</table>
	<br>
	<h3 align="left">�䶯��¼
		<s:if test="#request.hasAuth=='has'">
			<a href="#"><img src="../resource/images/CreateProject.gif" onclick="showEtfForm('add')"></img></a>
		</s:if>
	</h3>
	<table id="profiletable" cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
	     <tr>
	     	<th >�䶯����</th>
	     	<th >�䶯����</th>
	     	<th >ԭ����</th>
	     	<th >ԭְλ</th>
	     	<th >�²���</th>
	     	<th >��ְλ</th>
	     	<th >�䶯ԭ��</th>
	     	<th >��ע</th>
	     	<s:if test="#request.hasAuth=='has'"><th >����</th></s:if>
	     </tr>
		<tbody>
			<s:iterator value="transferList" status="transferStatus">
				<tr id="<s:property value="eftId"/>">
					<td align="center"><s:property value="eftTransferDate"/></td>
					<td align="center">
						<input type="hidden" id="type<s:property value="eftId"/>" value="<s:property value="eftTransferType"/>"/>
						<s:if test="eftTransferType==0">ƽ��</s:if>
						<s:if test="eftTransferType==1">����</s:if>
						<s:if test="eftTransferType==2">����</s:if>
						<s:if test="eftTransferType==3">ת��</s:if>
						<s:if test="eftTransferType==4">ת��</s:if>
					</td>
					<td align="center">
						<input type="hidden" id="deptOld<s:property value="eftId"/>" value="<s:property value="eftOldDeptNo.id"/>"/>
						<s:property value="eftOldDeptNo.departmentName"/>
					</td>
					<td align="center"><s:property value="eftOldPbId.pbName"/></td>
					<td align="center">
						<input type="hidden" id="deptNew<s:property value="eftId"/>" value="<s:property value="eftNewDeptNo.id"/>"/>
						<s:property value="eftNewDeptNo.departmentName"/>
					</td>
					<td align="center"><s:property value="eftNewPbId.pbName"/></td>
					<td align="center"><ty:text counts="12"><s:property value="eftReason"/></ty:text></td>
					<td align="center"><ty:text counts="12"><s:property value="eftComments"/></ty:text></td>
					<s:if test="#request.hasAuth=='has'">
	   					<s:if test="#transferStatus.first">
	   						<td width="30">
	   							<a href="#" onclick="etfDel('<s:property value="eftId"/>')">
	   								<img src="../resource/images/delete.gif" alt="ɾ��"></img>
	   							</a>
							</td>
						</s:if>
					</s:if>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<br>
	<h3 align="left">������¼
		<s:if test="#request.hasAuth=='has'">
			<a href="#"><img src="../resource/images/CreateProject.gif" onclick="showEeForm('add')"></img></a>
		</s:if>
	</h3>
	<table cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
	     <tr>
	     	<th >��ʼ����</th>
	     	<th >��ֹ����</th>
	     	<th >��������</th>
	     	<th >��������</th>
	     	<th >ְλ</th>
	     	<th >��������</th>
	    	<th >�������</th>
	    	<th >��ע</th>
	    	<th >����</th>
	    </tr>
   		<tbody>
   			<s:iterator value="evalList">
   			<tr id="<s:property value="eeId"/>">
   				<td align="center"><s:property value="eeStartDate"/></td>
   				<td align="center"><s:property value="eeEndDate"/></td>
   				<td align="center"><s:property value="eeType"/></td>
   				<td align="center"><s:property value="department.departmentName"/></td>
   				<td align="center"><s:property value="eePbNo.pbName"/></td>
   				<td align="center">
   					<input type="hidden" id="man<s:property value="eeId"/>" value="<s:property value="employeeByEeManager.id"/>"/>
   					<input type="hidden" id="manName<s:property value="eeId"/>" value="<s:property value="employeeByEeManager.empName"/>"/>
   					<s:property value="employeeByEeManager.empName"/>
   				</td>
   				<td align="center"><s:property value="eeRate"/></td>
   				<td align="center"><s:property value="eeComments"/></td>
				<td width="60">
					<s:if test="eeAttachment!='' && eeAttachment!=null">
						<input type="hidden" id="attach<s:property value="eeId"/>" value="<s:property value="eeAttachment"/>"/>
					  	<a href="downProfile.action?fileName=<s:property value="eeAttachment"/>&contentDisposition=<s:property value="eeAttachment"/>">
					  	<img src="../resource/images/attachment.gif" alt="��������"></img></a>
					</s:if><s:else>&nbsp;&nbsp;</s:else>
					<s:if test="#request.hasAuth=='has'">
					  	<a href="#" onclick="showEeForm('update','<s:property value="eeId"/>')">
					  		<img src="../resource/images/edit.gif" alt="�޸�"></img></a>
					  	<a href="#" onclick="eeDel('<s:property value="eeId"/>')">
					  		<img src="../resource/images/delete.gif" alt="ɾ��"></img></a>
   					</s:if>
				</td>
   			</tr>
   			</s:iterator>
   		</tbody>
	</table>
	
	<br>
	<h3 align="left">���ͼ�¼
		<s:if test="#request.hasAuth=='has'">
			<a href="#"><img src="../resource/images/CreateProject.gif" onclick="showErForm('add')"></img></a>
		</s:if>
	</h3>
	<table cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
	     <tr>
	     	<th >��������</th>
	     	<th >��������</th>
	     	<th >ִ�в���</th>
	     	<th >ְλ</th>
	     	<th >������ʽ</th>
	     	<th >��ע</th>
	     	<s:if test="#request.hasAuth=='has'"><th width="40">����</th></s:if>
	     </tr>
	     <tbody>
	     	<s:iterator value="rewardList">
	     	<tr id="<s:property value="erId"/>">
	     		<td align="center"><s:property value="erExeDate"/></td>
	     		<td align="center"><s:property value="erTypeMean"/></td>
	     		<td align="center"><s:property value="department.departmentName"/></td>
	     		<td align="center"><s:if test="erPbNo!=null"><s:property value="erPbNo.pbName"/></s:if></td>
	     		<td align="center"><s:property value="erForm"/></td>
	     		<td align="center"><s:property value="erContent"/></td>
				<s:if test="#request.hasAuth=='has'">
					<td>
	   				<a href="#" onclick="showErForm('update','<s:property value="erId"/>','<s:property value="erType" />')">
	   					<img src="../resource/images/edit.gif" alt="�޸�"></img></a>
	   				<a href="#" onclick="erDel('<s:property value="erId"/>')">
	   					<img src="../resource/images/delete.gif" alt="ɾ��"></img></a>
	   				</td>
		     	</s:if>
	     	</tr>
	     	</s:iterator>
	     </tbody>
	</table>
	<br>
	<h3 align="left">��ְ����
		<s:if test="#request.hasAuth=='has'">
			<a href="#"><img src="../resource/images/CreateProject.gif" onclick="showEqForm('add')"></img></a>
		</s:if>
	</h3>
	<table cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
	     <tr>
	     	<th >��ְ����</th>
	     	<th >��ְ����</th>
	     	<th >������</th>
	     	<th >��ְԭ��</th>
	     	<th >��ע</th>
	     	<s:if test="#request.hasAuth=='has'"><th width="40">����</th></s:if>
		</tr>
   		<tbody>
   			<s:iterator value="quitList" status="quitStatus">
   			<tr id="<s:property value="eqId"/>">
   				<td align="center"><s:property value="eqDate"/></td>
   				<td align="center">
   					<input type="hidden" id="eqt<s:property value="eqId"/>" value="<s:property value="eqType"/>"/>
   					<s:if test="eqType==0">��ְ</s:if>
   					<s:if test="eqType==1">��ְ</s:if>
   					<s:if test="eqType==2">ͣн��ְ</s:if>
   					<s:if test="eqType==3">����</s:if>
   					<s:if test="eqType==4">����</s:if>
   					<s:if test="eqType==5">��ͬ����</s:if>
   					<s:if test="eqType==6">����</s:if>
   				</td>
   				<td align="center">
   					<input type="hidden" id="perm<s:property value="eqId"/>" value="<s:property value='eqPermission.id'/>"/>
   					<input type="hidden" id="permName<s:property value="eqId"/>" value="<s:property value='eqPermission.empName'/>"/>
   					<input type="hidden" id="deptName<s:property value="eqId"/>" value="<s:property value='eqDeptNo.departmentName'/>"/>
   					<input type="hidden" id="pbName<s:property value="eqId"/>" value="<s:property value='eqPbNo.pbName'/>"/>
   					<input type="hidden" id="comments<s:property value="eqId"/>" value="<s:property value='erComments'/>"/>
   					<s:property value="eqPermission.empName"/>
   				</td>
   				<td align="center"><s:property value="eqReason"/></td>
   				<td align="center"><s:property value="erComments"/></td>
	   			<s:if test="#request.hasAuth=='has'">
	   				<s:if test="#quitStatus.first">
	   					<td>
		   					<a href="#" onclick="showEqForm('update','<s:property value="eqId"/>')">
		   						<img src="../resource/images/edit.gif" alt="�޸�"></img></a>
		   					<a href="#" onclick="eqDel('<s:property value="eqId"/>')">
		   						<img src="../resource/images/delete.gif" alt="ɾ��"></img></a>
	   					</td>
	   				</s:if>
	   			</s:if>
   			</tr>
   			</s:iterator>
   		</tbody>
	</table>
<!-- ���º�ͬdialog -->
<div id="dlgAddContract" style="width:425px;display: none;" class="prompt_div_inline">	
  	<form name="ectForm" id="ectForm" action="empContractAdd.action" method="POST" enctype="multipart/form-data">
  	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  		<tr>
  			<td>
  				<table width="100%" class="prompt_div_body" cellpadding="0" cellspacing="0" padding-left="5" width="100%" border="0">
					<tr>
						<td>��ʼ����<span class="required">*</span>��</td>
						<td>
							<s:textfield id="ectStartDate" name="ectStartDate" required="true" size="10"/>
							<img onclick="WdatePicker({el:'ectStartDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
						</td>
					</tr>
					<tr>
						<td>��ֹ����<span class="required">*</span>��</td>
						<td>
							<s:textfield id="ectEndDate" name="ectEndDate" required="true" size="10"/>
							<img onclick="WdatePicker({el:'ectEndDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
						</td>
					</tr>
					<tr>
						<td>��ͬ��ţ�</td>
						<td><input name="ectNo" size="14" maxlength="36"/></td>
					</tr>
					<tr>
						<td>��ͬ����<span class="required">*</span>��</td>
						<td><s:select name="empTypeId" list="empTypeList" listKey="id" listValue="ectName"></s:select></td>
					</tr>
					<tr>
						<td>��ͬ״̬<span class="required">*</span>��</td>
						<td><select name="ectStatus">
							<option value="1">��Ч</option>
							<option value="2">��ֹ</option>
							<option value="3">���</option>
						</select></td>
					</tr>
					<tr>
						<td>��ͬ����<span class="required">*</span>��</td>
						<td><select id="etcExpire" name="etcExpire"
								onChange="if(this.value=='1')$('ectEndDate').disabled=true;else $('ectEndDate').disabled=false;">
								<option value="0">������</option>
								<option value="1">������</option>
						</select></td>
					</tr>
					<tr>
						<td>��ͬ��ע��</td>
						<td><textarea name="ectComments" rows="8" cols="40"></textarea></td>
					</tr>
					<tr>
						<td>�ϴ�������</td>
						<td><input name="file" name="ectAttachment" type="file" size="25" maxlength="128"/> <span id="ectAttach"></span></td>
					</tr>
					<tr height="35">
						<td colspan="2" align="center">
							<s:token/>
							<input type="hidden" id="contract-empNo" name="empNo">
							<input type="hidden" id="contract-employeeId" name="employeeId">
							<input type="hidden" id="updateEctId" name="updateEctId"/>
							<input type="hidden" id="checkStartDate"/>
							<input id="ectAddbtn" type="submit" value="����" onClick="return ectVali()"/>
							<input id="ectUpdatebtn" type="submit" value="�޸�" onClick="return ectVali()"/>
							<input type="button" value="�ر�" onclick="hrm.common.closeDialog('dlgAddContract');"/>
						</td>
					</tr>
				</table>
  			</td>
  		</tr>
	</table>
	</form>
</div>

<!-- �䶯��¼dialog -->
<div id="dlgAddTransfer" style="width:425px;display: none;" class="prompt_div_inline">	
  	<form id="etfForm" action="empTransferAdd.action" method="post" enctype="multipart/form-data">
  	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  		<tr>
  			<td>
  				<table width="100%" class="prompt_div_body" cellpadding="0" cellspacing="0" width="100%" border="0">
					<tr>
						<td>ԭ���ţ�</td>
						<td>
						<input type="hidden" id="eftOldDeptId" value="<s:property value="currentEmp.empDeptNo.id"/>"/>
						<span id="eftOldDept"><s:property value="currentEmp.empDeptNo.departmentName"/></span>
						</td>
					</tr>
					<tr id="oldEmpPbName">
						<td>ԭְλ��</td>
						<td >
							<span><s:property value="%{currentEmp.empPbNo.id==''?'':currentEmp.empPbNo.pbName}"/></span>
						</td>
					</tr>
					
					<tr>
						<td>�䶯����<span class="required">*</span>��</td>
						<td>
							<s:textfield id="eftTransferDate" name="eftTransferDate" required="true" size="10"/>
							<img onclick="WdatePicker({el:'eftTransferDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
						</td>
					</tr>
					<tr id="targetTr">
						<td>�䶯����<span class="required">*</span>��</td>
						<td>
							<select name="eftTransferType" id="transferType" onchange="changeForm()">
								<option value="0">ƽ��</option>
								<option value="1">����</option>
								<option value="2">����</option>
								<option value="3">ת��</option>
								<option value="4">ת��</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>�²��ţ�</td>
						<td>
			                <s:hidden id="chang_dept_id" name="eftNewDeptNo"/>
				            <s:textfield id="chang_dept_name" size="16" readonly="true"/>
						</td>
					</tr>
					<tr>
						<td>��ְλ��</td>
						<td>
							<div>
				            <table cellpadding="0" cellspacing="0" class="select">
							<tr><td bgcolor="#FFFFFF">
							<s:hidden id="positionId" name="eftNewPosition"/>
							<input type="text" id="changePosition"  class='selecttext' readonly="true" size="12" />
							<button type="button" class="selectbutton" style="CURSOR:pointer" id="showdiv" onclick="showPostionTree('changePosition', 'positionId', 'chang_dept_name', 'chang_dept_id');"/>&nbsp;
							</button>
							</td></tr>
							</table>
							</div>
						</td>
					</tr>
					<tr>
						<td>�䶯ԭ��</td>
						<td><input name="eftReason" size="40" maxlength="128"/></td>
					</tr>
					<tr>
						<td>��ע��</td>
						<td><textarea name="eftComments" rows="8" cols="40"></textarea></td>
					</tr>
					<tr height="35">
						<td colspan="2" align="center">
							<input type="hidden" name="eftOldDeptNo" value="<s:property value="currentEmp.empDeptNo.id"/>"/>
							<input type="hidden" name="eftOldPbId" value="<s:property value="currentEmp.empPbNo.id"/>"/>
							<input type="hidden" id="transfer-employeeId" name="employeeId">
							<input type="hidden" id="transfer-empNo" name="empNo">
							<input type="hidden" id="updateEtfId" name="updateEtfId"/>
							
							<input id="etfAddbtn" type="submit" value="����" onClick="return etfVali()"/>
							<input id="etfUpdatebtn" type="submit" value="�޸�" onClick="return etfVali()"/>
							<input type="button" value="�ر�" onclick="hrm.common.closeDialog('dlgAddTransfer');"/>
						</td>
					</tr>
				</table>
  			</td>
  		</tr>
	</table>
	</form>
</div>
<div style="display:none"><table><tr>
    <td id="srcTd">
	<s:select id="employmentForm"  name="eftNewEmpType" list="empTypes" listKey="id" listValue="emptypeName" multiple="false"  value="emp.empType.id" size="1" />
	</td></tr></table>
</div>
<!-- ������¼dialog -->
<div id="dlgAddEval" style="width:425px;display:none;" class="prompt_div_inline">	
  	<form id="eeForm" action="empEvalAdd.action" method="post" enctype="multipart/form-data">
  	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  		<tr>
  			<td>
  				<input type="hidden" id="_joinDate" value="<s:date name='currentEmp.empJoinDate' format='yyyy-MM-dd'/>"/>
  				<input type="hidden" id="_confirmDate" value="<s:date name='currentEmp.empConfirmDate' format='yyyy-MM-dd'/>"/>
  				<table id="eeTable" width="100%" class="prompt_div_body" cellpadding="0" cellspacing="0" width="100%" border="0">
					<tr>
						<td>�������ţ�</td>
						<td><span id="eeDept"><s:property value="currentEmp.empDeptNo.departmentName"/></span></td>
					</tr>
					<tr>
						<td>ְλ���ƣ�</td>
						<td><s:property  value="currentEmp.empPbNo.pbName"/></td>
					</tr>
					<tr>
						<td>��������<span class="required">*</span>��</td>
						<td>
							<select name="eeType" id="eeType" onChange="eeSetDis(this.value)">
								<option value="�¶�">�¶�</option>
								<option value="����">����</option>
								<option value="����">����</option>
								<option value="ȫ��">ȫ��</option>
								<option value="������">������</option>
								<option value="��Ŀ">��Ŀ</option>
							</select>
							<span id="eeTypeTime"><select name="eeTypeDetail" id="eeTypeDetail"></select></span>
							<span id="eeDateSpan">��
							<s:textfield id="eeStartDate" name="eeStartDate" required="true" size="10"/>
							<img onclick="WdatePicker({el:'eeStartDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
							��
							<s:textfield id="eeEndDate" name="eeEndDate.erelBirthday" required="true" size="10"/>
							<img onclick="WdatePicker({el:'eeEndDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
						</span>
						</td>
					</tr>
					<tr>
						<td>��������<span class="required">*</span>��</td>
						<td>
						<s:hidden id="managerId" name="eeManager" />
						<input type="text" readonly="readonly" id="empManagerName" />
						<img src="../resource/images/search_icon.gif" style="CURSOR: pointer" onclick="showChooseEmpDiv(1,1);" alt='���ͼ��ѡ��Ա��'/>	
						</td>
					</tr>
					<tr>
						<td>�������<span class="required">*</span>��</td>
						<td>
							<s:component template="editselect"  name="eeRate">
								<s:param name="list" value="{'A','B','C','D','E','ͨ��','δͨ��'}"/>
								<s:param name="size" value="8"/>
								<s:param name="height" value="100"/>
							</s:component>
						</td>
					</tr>
					<tr>
						<td>������ע��</td>
						<td><textarea id="eeComments" name="eeComments" rows="8" cols="40"></textarea></td>
					</tr>
					<tr>
						<td>�ϴ�������</td>
						<td><input name="file" type="file" size="25" maxlength="128"/><span id="eeAttach"></span></td>
					</tr>
					<tr height="35">
						<td colspan="2" align="center">
							<input type="hidden" name="departmentId" value="<s:property value="currentEmp.empDeptNo.id"/>" />
							<input type="hidden" name="eePbNo" value="<s:property value="currentEmp.empPbNo.id"/>" />
							<input type="hidden" id="eval-employeeId" name="employeeId">
							<input type="hidden" id="eval-empNo" name="empNo">
							<s:token/><input type="hidden" id="updateEeId" name="updateEeId"/>
							<input id="eeAddbtn" type="submit" value="����" onClick="return eeVali()"/>
							<input id="eeUpdatebtn" type="submit" value="�޸�" onClick="return eeValidate()"/>
							<input type="button" value="�ر�" onclick="hrm.common.closeDialog('dlgAddEval');"/>
						</td>
					</tr>
				</table>
  			</td>
  		</tr>
	</table>
	</form>
</div>

<!-- ���ͼ�¼dialog -->
<div id="dlgAddReward" style="width:425px;display: none;" class="prompt_div_inline">
  	<form id="erForm" action="empRewardAdd.action" method="post" enctype="multipart/form-data">
  	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  		<tr>
  			<td><table width="100%" class="prompt_div_body" cellpadding="0" cellspacing="0" width="100%" border="0">
					<tr>
						<td>ִ�в��ţ�</td>
						<td><span id="erDept"><s:property value="currentEmp.empDeptNo.departmentName"/></span></td>
					</tr>
					<tr>
						<td>ְλ��</td>
						<td><span id="erJob"><s:property value="currentEmp.empPbNo.pbName"/></span></td>
					</tr>
					<tr>
						<td>��������<span class="required">*</span>��</td>
						<td>
							<s:textfield id="erExeDate" name="erExeDate" required="true" size="10"/>
							<img onclick="WdatePicker({el:'erExeDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
						</td>
					</tr>
					<tr>
						<td>��������<span class="required">*</span>��</td>
						<td><s:select name="erType" id="erType" list="erTypeMap" onchange="model.simple.changeSubSelect(this,'erForm1','erTypeSubMap')"></s:select></td>
					</tr>

					<tr>
						<td>������ʽ<span class="required">*</span>��</td>
						<td><select id="erForm1" name="erForm"></select></td>
					</tr>
					<tr>
						<td>��ע<span class="required">*</span>��</td>
						<td><textarea id="erContent" name="erContent" rows="8" cols="40"></textarea></td>
					</tr>
					<tr height="35">
						<td colspan="2" align="center">
							<input type="hidden" name="departmentId" value="<s:property value="currentEmp.empDeptNo.id"/>"/>
							<input type="hidden" name="erPbId" value="<s:property value="currentEmp.empPbNo.id"/>"/>

							<input type="hidden" id="reward-employeeId" name="employeeId" />
							<input type="hidden" id="reward-empNo" name="empNo" />
							<s:token/><input type="hidden" id="updateErId" name="updateErId"/>
							<input id="erAddbtn" type="submit" value="����" onClick="return erVali()"/>
							<input id="erUpdatebtn" type="submit" value="�޸�" onClick="return erVali()"/>
							<input type="button" value="�ر�" onclick="hrm.common.closeDialog('dlgAddReward');"/>
						</td>
					</tr>
				</table>
  			</td>
  		</tr>
	</table>
	</form>
</div>

<!-- ��ְ����dialog -->
<div id="dlgAddQuit" style="width:425px;display: none;" class="prompt_div_inline">	
  	<form id="eqAddForm" action="empQuitAdd.action" method="post" enctype="multipart/form-data">
  	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  		<tr>
  			<td>
  				<table width="100%" class="prompt_div_body" cellpadding="0" cellspacing="0" width="100%" border="0">
					<tr>
						<td>
							<span id="eqDateLabel"><s:if test="empStatus">��ְ</s:if><s:else>��ְ</s:else></span>
							����<span class="required">*</span>��
						</td>
						<td>
							<s:textfield id="eqDateAdd" name="eqDate" required="true" size="10"/>
							<img onclick="WdatePicker({el:'eqDateAdd'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
						</td>
					</tr>
					<tr>
						<s:if test="empStatus">
							<td>��ְ����<span class="required">*</span>��</td>
							<td><s:select name="eqType" id="eqTypeAdd" list="eqTypeMap" onchange="model.simple.changeSubSelect(this,'eqReasonAdd','eqTypeSubMapQuit')"></s:select></td>
						</s:if>
						<s:else>
							<td>��ְ����<span class="required">*</span>��</td>
							<td><s:select name="eqType" id="eqTypeAdd" list="eqRTypeMap" onchange="model.simple.changeSubSelect(this,'eqReasonAdd','eqTypeSubMapReturn')"></s:select></td>
						</s:else>
						</tr>
					<tr>
						<td><s:if test="empStatus">��ְ</s:if><s:else>��ְ</s:else>������<span class="required">*</span>��</td>
						<td>
						<s:hidden id="empManagerId" name="eqPermission"/>
						<input type="text" readonly="true" id="empName" />
						<img src="../resource/images/search_icon.gif" style="CURSOR: pointer" onclick="showChooseEmpDiv(1,1);" alt='���ͼ��ѡ��Ա��'/>
						</td>
					</tr>
					<tr>
						<s:if test="empStatus">
						<td>��ְԭ��<span class="required">*</span>��</td>
						</s:if>
						<s:else>
						<td>��ְԭ��<span class="required">*</span>��</td>
						</s:else>
						<td><select name="eqReason" id="eqReasonAdd"></select></td>
					</tr>
					<s:if test="!empStatus">
					<tr>
					    <td>��ְ����<span class="required">*</span>:</td>
						<td>					
					    <s:hidden id="add_change_dept_id"/>
						 <s:textfield id="add_change_dept_name" size="16" readonly="true"/>
						</td>
					</tr>
					<tr>
						<td>��ְְλ<span class="required">*</span>��</td>
						<td>
							<div><table cellpadding="0" cellspacing="0" class="select"><tr><td bgcolor="#FFFFFF">
			                <s:hidden id="add_positionId" name="addPositionId"/>
			                <input id="add_empPBName" name="add_empPBName" class='selecttext' readonly="true" size="12" />
			                <button type="button" class="selectbutton" style="CURSOR: pointer" id="showdiv1" onclick="showPostionTree('add_empPBName', 'add_positionId', 'add_change_dept_name', 'add_change_dept_id');"/>&nbsp;
			                </button></td></tr></table></div>
						</td>
					</tr>
					</s:if>
					<tr>
						<td>��ע��</td>
						<td><textarea name="eqComments" rows="8" cols="40"></textarea></td>
					</tr>
					<tr height="35">
						<td colspan="2" align="center">
							<input type="hidden" id="quitAdd-employeeId" name="employeeId" />
							<input type="hidden" id="quitAdd-empNo" name="empNo" />
							<input id="eqAddbtn" type="submit" value="����" onClick="return eqVali('eqAddForm')"/>
							<input type="button" value="�ر�" onclick="hrm.common.closeDialog('dlgAddQuit');"/>
						</td>
					</tr>
				</table>
  			</td>
  		</tr>
	</table>
	</form>
</div>
	
<div id="dlgAddQuitUpdate" style="width:425px;display: none;" class="prompt_div_inline">
  	<form id="eqUpdateForm" action="empQuitUpdate.action" method="post" enctype="multipart/form-data">
  	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  		<tr>
  			<td>
  				<table width="100%" class="prompt_div_body" cellpadding="0" cellspacing="0" width="100%" border="0">
					<tr>
						<td>
							<span id="eqDateLabel"><s:if test="!empStatus">��ְ</s:if><s:else>��ְ</s:else></span>
							����<span class="required">*</span>��
						</td>
						<td>
							<s:textfield id="eqDateUpdate" name="eqDate" required="true" size="10"/>
							<img onclick="WdatePicker({el:'eqDateUpdate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
						</td>
					</tr>
					<tr>
						<s:if test="!empStatus">
							<td>��ְ����<span class="required">*</span>��</td>
							<td><s:select name="eqType" id="eqTypeUpdate" list="eqTypeMap" onchange="model.simple.changeSubSelect(this,'eqReasonUpdate','eqTypeSubMapQuit')"></s:select></td>
						</s:if>
						<s:else>
							<td>��ְ����<span class="required">*</span>��</td>
							<td><s:select name="eqType" id="eqTypeUpdate" list="eqRTypeMap" onchange="model.simple.changeSubSelect(this,'eqReasonUpdate','eqTypeSubMapReturn')"></s:select></td>
						</s:else>
					</tr>
					<tr>
						<td><s:if test="!empStatus">��ְ</s:if><s:else>��ְ</s:else>������<span class="required">*</span>��</td>
						<td>
							<s:hidden id="upd_empManagerId" name="eqPermission"/>
							<input type="text" readonly="true" id="upd_empName" />
							<img src="../resource/images/search_icon.gif" style="CURSOR: pointer" onclick="showChooseEmpOrManagerDiv();" alt='���ͼ��ѡ��Ա��'/>
						</td>
					</tr>
					<tr>
						<s:if test="!empStatus">
						<td>��ְԭ��<span class="required">*</span>��</td>
						</s:if>
						<s:else>
						<td>��ְԭ��<span class="required">*</span>��</td>
						</s:else>
						<td><select name="equReason" id="eqReasonUpdate"></select></td>
					</tr>
					<s:if test="empStatus">
					<tr>
					    <td>��ְ����<span class="required">*</span>:</td>
						<td>					
						 <s:textfield id="upd_change_dept_name" size="16" readonly="true"/>
						</td>
					</tr>
					<tr>
						<td>��ְְλ<span class="required">*</span>��</td>
						<td>
			                <input id="upd_emp_pb_Name" readonly="true" size="12" />
						</td>
					</tr>
					</s:if>
					<tr>
						<td>��ע��</td>
						<td><textarea id="upd_eqComments" name="eqComments" rows="8" cols="40"></textarea></td>
					</tr>
					<tr height="35">
						<td colspan="2" align="center">
							<input type="hidden" id="quitUpdate-employeeId" name="employeeId" >
							<input type="hidden" id="quitUpdate-empNo" name="empNo" >
							<s:token/><input type="hidden" id="updateEqId" name="updateEqId"/>
							<input id="eqUpdatebtn" type="submit" value="�޸�" onClick="return eqVali('eqUpdateForm')"/>
							<input type="button" value="�ر�" onclick="hrm.common.closeDialog('dlgAddQuitUpdate');"/>
						</td>
					</tr>
				</table>
  			</td>
  		</tr>
	</table>
	</form>
</div>

<script type="text/javascript">

parent.document.getElementById('parentEmpName').innerHTML ='<s:property value="empName"/>';

var currOperName;//��ǰ��������Ա������
<s:if test="#session.empName!=''">
	currOperName='<s:property value="empName"/>';
</s:if>
<s:else>
	currOperName='<s:property value="#session.empName"/>';
</s:else>

function lengthLimit(owner) {
	if(owner.value.length>254){owner.value=owner.value.substring(0,254);return false;}
}
/***********************************************************
Ա����ͬ��Ӧjs
*****************************************************************/
var editObj;//���޸ĵ���
//��dialog���г�ʼ������ʾ����(�޸�)����ǩ���º�ͬdialog
function addContract(){
    $('#checkStartDate').val('');
    var num=document.getElementsByName("ListectId").length;
	if(num==0){
		$('#etcExpire').val(0);
		$('#ectEndDate')[0].disabled=false;
		showEctForm('add','');
	}
	else{	
		showCopy(num,document.getElementsByName("ListectId")[0].value,document.getElementsByName("ListectStartDate")[0].value,document.getElementsByName("ListectEndDate")[0].value);	   	
	}
}
//��ʾ��ǩ���º�ͬdialog
function showCopy(num,ectId,startDate,endDate){
	if(document.getElementsByName("ListetcStatus")[0].value==1&document.getElementsByName("ListetcExpire")[0].value==1){
		alert("��Ա����ǩ�������ں�ͬ,��������ǩ��");
		return;
	}
	if(num>1){
		alert("��Ա����ǩ��2��(����)��ͬ��Ӧǩ�������ں�ͬ��");
		$('#ectEndDate')[0].disabled=true;
		$('#etcExpire').val(1);
	}
	$('#ectAttach')[0].innerHTML='';
	$('#etcExpire').val(0);
	$('#ectEndDate')[0].disabled=false;
	$('#ectAddbtn')[0].style.display="inline";
	$('#ectUpdatebtn')[0].style.display="none";
	if(endDate==""){
		hrm.profile.setTimeNextYear(startDate,$('#ectForm')[0].ectStartDate,$('#ectForm')[0].ectEndDate);
	}
	else{
		hrm.profile.setTimeNextYear(endDate,$('#ectForm')[0].ectStartDate,$('#ectForm')[0].ectEndDate);
	}
	$('#updateEctId').val(ectId);
	$('#checkStartDate').val(startDate);
	var titleString="���º�ͬ��"+currOperName+"��";
	$('#dlgAddContract').dialog("option","title",titleString);
	var currEmp = parent.document.getElementById('currEmpId');
	document.getElementById('contract-empNo').value = $(currEmp).attr('name');
	document.getElementById('contract-employeeId').value = $(currEmp).attr('name');
	hrm.common.openDialog('dlgAddContract');
}
//��ʾ���ӻ��޸����º�ͬdialog
function showEctForm(type,ectId){
try{
	var currEmp = parent.document.getElementById('currEmpId');
	document.getElementById('contract-empNo').value = $(currEmp).attr('name');
	document.getElementById('contract-employeeId').value = $(currEmp).attr('name');
	
	$('#ectAttach')[0].innerHTML='';
	if(type=='add') {
	    $('#updateEctId').val(ectId);
	    $('#ectEndDate')[0].disabled=false;
		$('#ectAddbtn')[0].style.display="inline";
		$('#ectUpdatebtn')[0].style.display="none";
		hrm.profile.setTimeNextYear("",$('#ectForm')[0].ectStartDate,$('#ectForm')[0].ectEndDate);
	}else if(type=='update') {
		editObj = $("#"+ectId)[0];
		$('#ectAddbtn')[0].style.display="none";
		$('#ectUpdatebtn')[0].style.display="inline";
		showEctdep('ectForm',ectId);
		$('#ectForm')[0].action="empContractUpdate.action";
		$('#updateEctId').val(ectId);
	}
	var titleString="���º�ͬ��"+currOperName+"��";
	$('#dlgAddContract').dialog("option","title",titleString);
	hrm.common.openDialog('dlgAddContract');
	if($('#etcExpire').val()=='1') {
		$('#ectEndDate')[0].disabled=true;
	   }else{
		 $('#ectEndDate')[0].disabled=false;
	   }
	}catch(e){alert(e);} 
}
//���ݱ���е�ֵ�����º�ͬ�޸�dialog���и�ֵ
function showEctdep(form,ectId){
try{
	var dep=$("#"+form)[0];	  	// get the current tr
	var _es=dep.elements;
	//��id
	var aTd = editObj.cells;
	var l = aTd.length;
	
	//��ֵ
	var formIdex=0;
	for(var i=0;i<7;i++){
		if(i==3) {
			_es[formIdex++].value=$('#type'+ectId).val();
		}else if(i==4) {
			_es[formIdex++].value=$('#stat'+ectId).val();
		}else if(i==5) {
			_es[formIdex++].value=$('#exp'+ectId).val();
		}else {
			_es[formIdex++].value=aTd[i].innerHTML.trim();
		}
	}
	if($('#attach'+ectId).val()!=null){
		$('#ectAttach')[0].innerHTML="<a onclick=\"attachDelete('"+$('#attach'+ectId).val()+"','"+ectId+"')\" href='#'>ɾ��ԭ����<img alt='ɾ��' src='../resource/images/delete.gif'/></a>";
	}
	}catch(e){alert(e);}
}
//ɾ����ͬ����
function attachDelete(attach,id) {
	if(window.confirm('ȷ��ɾ���˺�ͬ������')){ 
		var currEmp = parent.document.getElementById('currEmpId');
		window.location='deleteContractAttach.action?updateEctId='+id+'&fileFileName='+attach+"&empNo="+$(currEmp).attr('name');
	}
}
//ɾ��ĳid�ĺ�ͬ
function ectDel(ectId) {
	if(!window.confirm('ȷ��ɾ���˺�ͬ��')){ return ;}
	var currEmp = parent.document.getElementById('currEmpId');
	window.location='deleteContract.action?updateEctId='+ectId+"&empNo="+$(currEmp).attr('name');
	
}
/***********************************************************
Ա���䶯��Ϣ��Ӧjs  
*****************************************************************/
//��dialog���г�ʼ������ʾ������Ա�䶯dialog
function showEtfForm(type,etfId){
try{
	if(type=='add') {
		$('#eftTransferDate').val((new Date()).toHRMDateString());
		$('#etfAddbtn')[0].style.display="inline";
		$('#etfUpdatebtn')[0].style.display="none";
		$('#etfForm')[0].action="empTransferAdd.action";
		var currEmp = parent.document.getElementById('currEmpId');
		document.getElementById('transfer-empNo').value = $(currEmp).attr('name');
		document.getElementById('transfer-employeeId').value = $(currEmp).attr('name');
	}else if(type=='update') {
		editObj = $("#"+etfId)[0];
		$('#etfAddbtn')[0].style.display="none";
		$('#etfUpdatebtn')[0].style.display="inline";
		showEtfdep('etfForm',etfId);
		$('#etfForm')[0].action="empTransferUpdate.action";
		$('#updateEtfId').val(etfId);
		var currEmp = parent.document.getElementById('currEmpId');
		document.getElementById('transfer-empNo').value = $(currEmp).attr('name');
		document.getElementById('transfer-employeeId').value = $(currEmp).attr('name');
	}		
	var titleString="��Ա�䶯��"+currOperName+"��";
	$('#dlgAddTransfer').dialog("option","title",titleString);
	hrm.common.openDialog('dlgAddTransfer');
	}catch(e){alert(e);} 
}
//���ݱ���е�ֵ����Ա�䶯�޸�dialog���и�ֵ
function showEtfdep(form,etfId){
try{
	var dep=$("#"+form)[0];	  	// get the current tr
	var _es=dep.elements;
	//��id
	var aTd = editObj.cells;
	var l = aTd.length;
	
	//��ֵ
	var formIdex=0;
	for(var i=0;i<8;i++){
		if(i==1) {
			_es[formIdex++].value=$('type'+etfId).value;
		}else if(i==2) {
			$('#etfForm')[0].eftOldDeptNo.value=$('#deptOld'+etfId).val();
		}else if(i==3) {
			$('#etfForm')[0].eftOldPbId.value=$('#eftOldPbId').val();
		}
		else if(i==4) {
			_es[formIdex++].value=$('#deptNew'+etfId).val();
		}else {
			_es[formIdex++].value=aTd[i].innerHTML.trim();
			if(i==5) {
				formIdex = formIdex+1;
			}
		}
	}
	}catch(e){alert(e);}
}
//ɾ��ĳid����Ա�䶯��¼
function etfDel(etfId) {
	if(!window.confirm('ȷ��ɾ������Ա�䶯��')){ return ;}
	
	EmpTransfer.deleteTransfer(etfId,etfdelcallback);
	function etfdelcallback(msg) {
		if(msg=='noauth') {
			errMsg("errMsg","��û��ɾ��Ȩ��ִ�б�������");
			return;
		}
		if(msg=='success') {
			var etfTable = $("#"+etfId)[0].parentNode; 
			$("#"+etfId)[0].parentNode.removeChild($("#"+etfId)[0]);
			successMsg("errMsg","ɾ��Ա���䶯��Ϣ�ɹ���");
			var objCell = document.createElement("td");
			if(etfTable.getElementsByTagName('tr').length>0) {
				etfId = etfTable.getElementsByTagName('tr')[0].id;
				objCell.innerHTML = "<a href='#' onclick='etfDel(\""+etfId+"\")'><img src='../resource/images/delete.gif' alt='ɾ��'></img></a>";
				etfTable.getElementsByTagName('tr')[0].appendChild(objCell);
			}
		}
	}
}
/***********************************************************
Ա�� ���� ��Ϣ��Ӧjs
*****************************************************************/
//������ѡ����������ʾ��Ӧ������������ڿ�
function eeSetDis(svalue) {
	if(svalue.trim()=='��Ŀ'){
		$('#eeTypeTime')[0].style.display = 'none';
		$('#eeDateSpan')[0].style.display = '';
		$('#eeStartDate').val((new Date()).toHRMDateString());
		$('#eeEndDate').val((new Date()).toHRMDateString());
	}else if(svalue.trim()=='������'){ 
        $('#eeTypeTime')[0].style.display = 'none';
		$('#eeDateSpan')[0].style.display = '';
        $('#eeForm')[0].eeStartDate.value = $('#_joinDate').val();
        $('#eeForm')[0].eeEndDate.value = $('#_confirmDate').val();
	}else {
		$('#eeDateSpan')[0].style.display = 'none';
		$('#eeTypeTime')[0].style.display = '';
		if(svalue.trim()=='ȫ��') {
			$('#eeTypeDetail')[0].options[0]=new Option('�����','0',false,false);
			$('#eeTypeDetail')[0].options[1]=new Option('�����','1',false,false);
		}
		if(svalue.trim()=='����') {
			$('#eeTypeDetail')[0].options[0]=new Option('�ϸ�����','2',false,false);
			$('#eeTypeDetail')[0].options[1]=new Option('��������','3',false,false);
		}
		if(svalue.trim()=='����') {
			$('#eeTypeDetail')[0].options[0]=new Option('�ϼ���','4',false,false);
			$('#eeTypeDetail')[0].options[1]=new Option('������','5',false,false);
		}
		if(svalue.trim()=='�¶�') {
			$('#eeTypeDetail')[0].options[0]=new Option('���·�','6',false,false);
			$('#eeTypeDetail')[0].options[1]=new Option('���·�','7',false,false);
		}
	}
}
//��ʾ���ӻ��޸Ŀ�����¼dialog
function showEeForm(type,eeId){
try{
	if(type=='add') {
		$("#eeForm").val("");
		$('#eeStartDate')[0].disabled=false;
		$('#eeEndDate')[0].disabled=false;
		$('#eeType')[0].disabled=false;
		eeSetDis($('#eeType').val());
		
		$('#eeAddbtn')[0].style.display="inline";
		$('#eeUpdatebtn')[0].style.display="none";
		$('#eeForm')[0].action="empEvalAdd.action";
		var currEmp = parent.document.getElementById('currEmpId');
		document.getElementById('eval-empNo').value = $(currEmp).attr('name');
		document.getElementById('eval-employeeId').value = $(currEmp).attr('name');
	}else if(type=='update') {
		editObj = $("#"+eeId)[0];
		$('#eeAddbtn')[0].style.display="none";
		$('#eeUpdatebtn')[0].style.display="inline";
		showEedep('eeForm',eeId);
		$('#eeForm')[0].action="empEvalUpdate.action";
		$('#updateEeId').val(eeId);
		var currEmp = parent.document.getElementById('currEmpId');
		document.getElementById('eval-employeeId').value = $(currEmp).attr('name');
	}
	var titleString="������¼��"+currOperName+"��";
	$('#dlgAddEval').dialog("option","title",titleString);
	hrm.common.openDialog('dlgAddEval');
	}catch(e){alert(e);}
}
//ɾ��������¼����
function eeAttachDelete(attach,id) {
	if(window.confirm('ȷ��ɾ����')){ 
		var currEmp = parent.document.getElementById('currEmpId');
		window.location='deleteEvalAttach.action?updateEeId='+id+'&fileFileName='+attach+"&empNo="+$(currEmp).attr('name');
	}
}
//���ݱ���е�ֵ�Կ�����¼�޸�dialog���и�ֵ
function showEedep(form,eeId){
try{
		
	var _es=$("#"+form)[0].elements;
	var aTd = editObj.cells;
	DWRUtil.setValue("eeType",aTd[2].innerHTML.trim());
	_es[4].value=aTd[0].innerHTML.trim();
	_es[5].value=aTd[1].innerHTML.trim();
	_es[6].value=$('#man'+eeId).val();
	_es[7].value=$('#manName'+eeId).val();
	document.getElementById('eeComments').value=aTd[7].innerHTML.trim();

	document.getElementById("eeRate_id").value = aTd[6].innerHTML.trim();
	$('#eeDept')[0].innerHTML=aTd[3].innerHTML.trim();
	}catch(e){alert(e);}
	$('#eeStartDate')[0].disabled=true;
	$('#eeEndDate')[0].disabled=true;
	$('#eeType')[0].disabled=true;
	$('#eeTypeTime')[0].style.display = 'none';
	$('#eeDateSpan')[0].style.display = '';
	if($('#attach'+eeId).val()!=null){
		$('#eeAttach')[0].innerHTML="<a onclick=\"eeAttachDelete('"+$('#attach'+eeId).val()+"','"+eeId+"')\" href='#'>ɾ��ԭ����<img alt='ɾ��' src='../resource/images/delete.gif'/></a>";
	}
}
//ɾ��ĳid�Ŀ�����¼
function eeDel(eeId) {
	if(!window.confirm('ȷ��ɾ���˿�����¼��')){ return ;}
	
	EmpEval.deleteEval(eeId,eedelcallback);
	function eedelcallback(msg) {
		if(msg=='noauth') {
			errMsg("errMsg","��û��ɾ��Ȩ��ִ�б�������");
			return;
		}
		if(msg=='success') {
			$("#"+eeId)[0].parentNode.removeChild($("#"+eeId)[0]);
			successMsg("errMsg","ɾ��Ա��������Ϣ�ɹ���");
		}
	}
}
/***********************************************************
Ա�� ���� ��Ϣ��Ӧjs
*****************************************************************/
//���ݲ�����ʾ���ͼ�¼���ӻ��޸�dialog
function showErForm(type,erId,erType){
try{
	if(type=='add') {
		$('#erExeDate').val((new Date()).toHRMDateString());
		$('#erAddbtn')[0].style.display="inline";
		$('#erUpdatebtn')[0].style.display="none";
		$('#erForm')[0].action="empRewardAdd.action";
		var currEmp = parent.document.getElementById('currEmpId');
		document.getElementById('reward-empNo').value = $(currEmp).attr('name');
		document.getElementById('reward-employeeId').value = $(currEmp).attr('name');
	}else if(type=='update') {
		editObj = $("#"+erId)[0];
		$('#erAddbtn')[0].style.display="none";
		$('#erUpdatebtn')[0].style.display="inline";
		showErdep('erForm',erId,erType);
		$('#erForm')[0].action="empRewardUpdate.action";
		$('#updateErId').val(erId);
		var currEmp = parent.document.getElementById('currEmpId');
		document.getElementById('reward-empNo').value = $(currEmp).attr('name');
		document.getElementById('reward-employeeId').value = $(currEmp).attr('name');
	}		
	var titleString="���ͼ�¼��"+currOperName+"��";
	$('#dlgAddReward').dialog("option","title",titleString);
	hrm.common.openDialog('dlgAddReward');
	}catch(e){alert(e);} 
}
//���ݱ���е�ֵ�Խ��ͼ�¼�޸�dialog���и�ֵ
function showErdep(form,erId,erType){
try{
	var dep=$("#"+form)[0];// get the current tr
	var _es=dep.elements;
	//��id
	var aTd = editObj.cells;
	var l = aTd.length;
	$('#erType').val(erType);
	model.simple.changeSubSelect('erType','erForm1','erTypeSubMap');
	//��ֵ
	var formIdex=0;
	for(var i=0;i<7;i++){
		if(i==2||i==3||i==4) {
		}else {
			_es[formIdex++].value=aTd[i].innerHTML.trim();
		}
	}
	$('#erType').val(erType);
	$('#erDept')[0].innerHTML=aTd[2].innerHTML.trim();
	$('#erJob')[0].innerHTML=aTd[3].innerHTML.trim();
	$('#erContent').val(aTd[5].innerHTML.trim());
	}catch(e){alert(e);}
}
//ɾ��ĳid�Ľ��ͼ�¼
function erDel(erId) {
	if(!window.confirm('ȷ��ɾ���˽��ͼ�¼��')){ return ;}
	
	EmpReward.deleteReward(erId,erdelcallback);
	function erdelcallback(msg) {
		if(msg=='noauth') {
			errMsg("errMsg","��û��ɾ��Ȩ��ִ�б�������");
			return;
		}
		if(msg=='success') {
			$("#"+erId)[0].parentNode.removeChild($("#"+erId)[0]);
			successMsg("errMsg","ɾ��Ա���䶯��Ϣ�ɹ���");
		}
	}
}
/***********************************************************
Ա�� ��ְ ��Ϣ��Ӧjs
*****************************************************************/
//���ݲ�����ʾ��ְ��Ϣ���ӻ��޸�dialog
function showEqForm(type,eqId){
try{
	var titleString="";
	<s:if test="empStatus">
		titleString+="��ְ��Ϣ";
	</s:if>
	<s:else>
		titleString+="��ְ��Ϣ";
	</s:else>
	titleString+="��"+currOperName+"��";
	if(type=='add') {
		<s:if test="empStatus">
			if(!confirm("��ȷ�ϸ�Ա������ְ�����Ѱ�����ϣ�")) {
				return;
			}
		</s:if>
		$('#eqDateAdd').val((new Date()).toHRMDateString());
		$('#eqAddForm')[0].action="empQuitAdd.action";
		$('#dlgAddQuit').dialog("option","title",titleString);
		var currEmp = parent.document.getElementById('currEmpId');
		document.getElementById('quitAdd-empNo').value = $(currEmp).attr('name');
		document.getElementById('quitAdd-employeeId').value = $(currEmp).attr('name');
		hrm.common.openDialog('dlgAddQuit');
	}else if(type=='update') {
		editObj = $("#"+eqId)[0];
		showEqdep('eqUpdateForm',eqId);
		$('#updateEqId').val(eqId);
		$('#upd_empManagerId').val($('#permId'+eqId).val());
		$('#upd_empName').val($('#permName'+eqId).val());
		$('#upd_eqComments').val($('#comments'+eqId).val());
		<s:if test="empStatus">
			$('#upd_change_dept_name').val($('#deptName'+eqId).val());
			$('#upd_emp_pb_Name').val($('#pbName'+eqId).val());
		</s:if>
		$('#dlgAddQuitUpdate').dialog("option","title",titleString);
		var currEmp = parent.document.getElementById('currEmpId');
		document.getElementById('quitUpdate-empNo').value = $(currEmp).attr('name');
		document.getElementById('quitUpdate-employeeId').value = $(currEmp).attr('name');
		hrm.common.openDialog('dlgAddQuitUpdate');
	}		
	}catch(e){alert(e);} 
}
//���ݱ���е�ֵ����ְ��Ϣ�޸�dialog���и�ֵ
function showEqdep(form,eqId){
try{
	var dep=$("#"+form)[0];	  	// get the current tr
	var _es=dep.elements;
	//��id
	var aTd = editObj.cells;
	var l = aTd.length;
	
	//��ֵ
	var formIdex=0;
	for(var i=0;i<5;i++){
		if(i==1) {
			_es[formIdex++].value=$('#eqt'+eqId).val();
		}else if(i==2) {
			_es[formIdex++].value=$('#perm'+eqId).val();
		}else {
			//if(i==4){
			//	formIdex++;
			//}
			_es[formIdex++].value=aTd[i].innerHTML.trim();
		}
	}
	}catch(e){alert(e);}
}
//ɾ��ĳid����ְ��Ϣ
function eqDel(eqId) {
	if(!window.confirm('ȷ��ɾ����')){ return ;}
	window.location="empQuitDelete.action?updateEqId="+eqId;
}
//�������º�ͬ���Ӻ��޸�dialog�ı���������ڵĸ�ʽ�����Ϸ����ύ
function ectVali() {
	if(!hrm.common.isDate($('#ectForm')[0].ectStartDate.value)) {
		alert('Ա����ͬ��ʼ���ڲ���Ϊ�ջ��ʽ����ȷ��');
		return false;
	}
	if($('#etcExpire').val()==0){
		if(!hrm.common.isDate($('#ectForm')[0].ectEndDate.value)) {
			alert('Ա����ͬ�������ڲ���Ϊ�ջ��ʽ����ȷ��');
			return false;
		}
	}else{
		$('#ectForm')[0].ectEndDate.value=null;
	}
	var checkStartdatevalue;
	var checkStartdate;
	var cDS='';
	if($('#checkStartDate').val()!=''){
		checkStartdatevalue =$('#checkStartDate').val();
		checkStartdate=checkStartdatevalue.replace(/-/g, "/");
		cDS=new Date(checkStartdate);		
	}
	startDatevalue = $('#ectStartDate').val();
	ectEndDate   = $('#ectEndDate').val();
	var startDate = startDatevalue.replace(/-/g, "/");
	var endDate = ectEndDate.replace(/-/g, "/");
	var dS = new Date(startDate);//��ʼ����
	var dE = new Date(endDate);//��������
	if(dS.getTime()>dE.getTime()){
		alert("Ա����ͬ��ʼʱ�䲻�����ڽ���ʱ�䣡");
		return false;
	}
	if(cDS!=''){
	   if(dS.getTime()<cDS.getTime()){
		alert("Ա����ͬ��ʼʱ�䲻������ǰһ�ݺ�ͬ��ʼʱ�䣡");
		return false;
	}
	}
	return true;
}
//������Ա�䶯���Ӻ��޸�dialog�ı���������ڵĸ�ʽ�����Ϸ����ύ
function etfVali() {
	if(!hrm.common.isDate($('#etfForm')[0].eftTransferDate.value)) {
		alert('Ա���䶯���ڲ���Ϊ�ջ��ʽ����ȷ��');
		return false;
	}
    var value = $('#transferType').val();
    if(value!=4){
     if(!hrm.common.isNotEmpty($('#positionId').val())){
         alert('��ְλ����Ϊ�գ�');
         return false;
     }
	}
	return true;
}
//���鿼����¼����dialog�ı���������ڵĸ�ʽ�����Ϸ����ύ
function eeVali() {
	if($('#eeForm')[0].eeType.value=='��Ŀ' ||$('#eeForm')[0].eeType.value=='������') {
		if(!hrm.common.isDate($('#eeForm')[0].eeStartDate.value)) {
			alert('������ʼ���ڲ���Ϊ�ջ��ʽ����ȷ��');
			return false;
		}
		if(!hrm.common.isDate($('#eeForm')[0].eeEndDate.value)) {
			alert('�����������ڲ���Ϊ�ջ��ʽ����ȷ��');
			return false;
		}
		startDatevalue = $('#eeStartDate').val();
		endDatevalue   = $('#eeEndDate').val();
		var startDate = new Date(startDatevalue.replace(/-/g, "/"));
		var endDate = new Date(endDatevalue.replace(/-/g, "/"));
		if(startDate.getTime()>endDate.getTime()){
			alert($('#eeForm')[0].eeType.value+"������ʼʱ�䲻�����ڽ���ʱ�䣡");
			return false;
		}
	}
	if($('#managerId').val() == null || $('#managerId').val() == ''){
		alert("����������Ϊ�գ�");
		return false;
	}
	if($('#eeForm')[0].eeRate.value==null||$('#eeForm')[0].eeRate.value=='') {
		alert('�����������Ϊ�գ�');
		return false;
	}
	return true;
}

//���鿼����¼����dialog�ı�������Ϸ����ύ
function eeValidate() {
	if($('#managerId').val() == null || $('#managerId').val() == ''){
		alert("����������Ϊ�գ�");
		return false;
	}
	if($('#eeForm')[0].eeRate.value==null||$('#eeForm')[0].eeRate.value=='') {
		alert('�����������Ϊ�գ�');
		return false;
	}
}

//���齱�ͼ�¼���Ӻ��޸�dialog�ı���������ڵĸ�ʽ�����Ϸ����ύ
function erVali() {
	if(!hrm.common.isDate($('#erExeDate').val())) {
		alert('�������ڲ���Ϊ�ջ����ڸ�ʽ����ȷ��');
		return false;
	}
	if($('#erForm')[0].erContent.value==null||$('#erForm')[0].erContent.value=='') {
		alert('��ע����Ϊ�գ�');
		return false;
	}
	return true;
}
//������ְ��Ϣ���Ӻ��޸�dialog�ı���������ڵĸ�ʽ�����Ϸ����ύ
function eqVali(form) {
	var typeMsg="";
	<s:if test="empStatus">
		typeMsg+="��ְ";
	</s:if>
	<s:else>
		typeMsg+="��ְ";
	</s:else>
	
	if(form=='eqAddForm'){
		if(!hrm.common.isDate($("#eqDateAdd").val())) {
			alert(typeMsg+'���ڲ���Ϊ�ջ����ڸ�ʽ����ȷ��');
			return false;
		}if($('#eqReasonAdd').val()==null||$('#eqReasonAdd').val()=='') {
			alert(typeMsg+'ԭ����Ϊ�գ�');
			return false;
		}
	}
	if(form=='eqUpdateForm'){
		if(!hrm.common.isDate($("#eqDateUpdate").val())) {
			alert(typeMsg+'���ڲ���Ϊ�ջ����ڸ�ʽ����ȷ��');
			return false;
		}if($('#eqReasonUpdate').val()==null||$('#eqReasonUpdate').val()=='') {
			alert(typeMsg+'ԭ����Ϊ�գ�');
			return false;
		}
	}
	return true;
}
//��������ͽ�����ʽ������
erTypeSubMap={};
<s:iterator value="erFormMap" id="topEntrySet">
erTypeSubMap['<s:property value="#topEntrySet.key"/>']={};
	<s:iterator value="#topEntrySet.value" status="index" id="secEntrySet">
	<s:if test="#secEntrySet.key.length()==0">
	erTypeSubMap['<s:property value="#topEntrySet.key"/>']['']='<s:property value="#secEntrySet.value"/>';
	</s:if><s:else>
	erTypeSubMap['<s:property value="#topEntrySet.key"/>']['<s:property value="#secEntrySet.value"/>']='<s:property value="#secEntrySet.value"/>';
	</s:else>
	</s:iterator>
</s:iterator>
model.simple.changeSubSelect('erType','erForm1','erTypeSubMap');

//��ʼ����ְ������ѡ��
eqTypeSubMapQuit={};
<s:iterator value="eqReasonMap" id="topEntrySet">
eqTypeSubMapQuit['<s:property value="#topEntrySet.key"/>']={};
	<s:iterator value="#topEntrySet.value" status="index" id="secEntrySet">
	<s:if test="#secEntrySet.key.length()==0">
	eqTypeSubMapQuit['<s:property value="#topEntrySet.key"/>']['']='<s:property value="#secEntrySet.value"/>';
	</s:if><s:else>
	eqTypeSubMapQuit['<s:property value="#topEntrySet.key"/>']['<s:property value="#secEntrySet.value"/>']='<s:property value="#secEntrySet.value"/>';
	</s:else>
	</s:iterator>
</s:iterator>
//��ʼ����ְ������ѡ��
eqTypeSubMapReturn={};
<s:iterator value="eqRReasonMap" id="topEntrySetR">
eqTypeSubMapReturn['<s:property value="#topEntrySetR.key"/>']={};
	<s:iterator value="#topEntrySetR.value" status="index" id="secEntrySetR">
	<s:if test="#secEntrySetR.key.length()==0">
	eqTypeSubMapReturn['<s:property value="#topEntrySetR.key"/>']['']='<s:property value="#secEntrySetR.value"/>';
	</s:if><s:else>
	eqTypeSubMapReturn['<s:property value="#topEntrySetR.key"/>']['<s:property value="#secEntrySetR.value"/>']='<s:property value="#secEntrySetR.value"/>';
	</s:else>
	</s:iterator>
</s:iterator>
<s:if test="!empStatus">
	model.simple.changeSubSelect('eqTypeAdd','eqReasonAdd','eqTypeSubMapReturn','<s:property value="eqReason"/>');
	model.simple.changeSubSelect('eqTypeUpdate','eqReasonUpdate','eqTypeSubMapQuit','<s:property value="eqReason"/>');
</s:if>
<s:else>
	model.simple.changeSubSelect('eqTypeAdd','eqReasonAdd','eqTypeSubMapQuit','<s:property value="eqReason"/>');
	model.simple.changeSubSelect('eqTypeUpdate','eqReasonUpdate','eqTypeSubMapReturn','<s:property value="eqReason"/>');
</s:else>
hrm.common.initDialog('dlgAddContract');
hrm.common.initDialog('dlgAddTransfer');
hrm.common.initDialog('dlgAddEval');
hrm.common.initDialog('dlgAddReward');
hrm.common.initDialog('dlgAddQuit');
hrm.common.initDialog('dlgAddQuitUpdate');
model.simple.setParentIFrameFull("IFrame1"); // add by ���Σ�����iframe�߶ȣ�
//��Ϊת���������ù���ʽ
function changeForm(){
  var value = $('#transferType').val();
  if(value==4){
	$('#chang_dept_id').val($('#eftOldDeptId').val());
	var pbTR = $('#oldEmpPbName');
	var typeTR = $('<tr></tr>');
	typeTR.attr("id","oldEmpTypeTr");
	var typeTD = $('<td></td>');
	typeTD.html("ԭ�ù���ʽ:");
	typeTR.append(typeTD);
	var typeNameTD = $('<td></td>');
	typeNameTD.html('<s:property value="currentEmp.empType.emptypeName"/>');
	typeTR.append(typeNameTD);
	pbTR.after(typeTR);
	
    var typeSelect=$('#employmentForm');
    
    var target=$('#targetTr');
    var tr=$('<tr></tr>');
    tr.attr("id","employmentFormTr");
    var td=$('<td></td>');
	td.html("�ù���ʽ:");
	tr.append(td);
	var empFormTd=$('<td></td>');
	empFormTd.append(typeSelect);
	tr.append(empFormTd);
	
	target.after(tr);
  }else{
    var srcSelect=$('#employmentForm');
    var scrTd=$('#srcTd');
    scrTd.append(srcSelect);
    $('#employmentFormTr').remove();
    $('#oldEmpTypeTr').remove();
  }
}
</script>
<%@ include file="../profile/search_emp_div.jsp"%>
<jsp:include flush="true" page="position_choose_div.jsp"></jsp:include>
</body>	
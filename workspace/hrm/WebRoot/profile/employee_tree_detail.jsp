<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%@ taglib prefix="ty" uri="/tengsource"%>
<%@ taglib uri="/WEB-INF/config/jenkov/treetag.tld" prefix="tree"%>
<%@ taglib uri="/WEB-INF/config/jenkov/requesttags.tld" prefix="request" %>
<%@ taglib uri="/WEB-INF/config/jenkov/ajaxtags.tld" prefix="ajax" %>
<link rel="stylesheet" href="../resource/css/style.css" type="text/css" />
<script language="javascript" type="text/javascript" src="http://www.im.alisoft.com/webim/js/website.js"></script>
<table cellspacing="0" cellpadding="0" border="0" width="100%">
	<tr>
		<td align="right">Ա��������</td>
		<td align="left" valign="middle"><s:property value="employee.empName"/>
			<s:if test="connectionType==0 && connectionNo!=null && connectionNo.length()>1">
				<a href="msnim:chat?contact=<s:property value='connectionNo'/>" target="blank" class="listViewTdLinkS1">
					<img src="../resource/images/icon_msn.gif" alt='����MSN' height="18" width="18" border='0'/>
				</a>
			</s:if>
			<s:elseif test="connectionType==1 && connectionNo!=null && connectionNo.length()>1">
				<a href="http://wpa.qq.com/msgrd?V=1&Uin=<s:property value='connectionNo'/>&Menu=yes" target="blank" class="listViewTdLinkS1">
					<img src="../resource/images/icon_qq.gif" alt='����QQ' height="18" width="18" border='0'/>
				</a>
		    </s:elseif>
			<s:elseif test="connectionType==2 && connectionNo!=null && connectionNo.length()>1">
				<a href="#">
					<img  onclick="sendClientMsg('cnalichn', '', 'cnalichn','<s:property value='connectionNo'/>', '1' , '')" style="cursor:pointer" align="absmiddle" alt=""
						src="http://amos.im.alisoft.com/online.aw?uid=<s:property value='connectionNo'/>&site=cnalichn&s=4" >
				</a>
			</s:elseif>
		</td>
		<td align="right">Ա���ţ�</td>
		<td align="left"><s:property value="employee.empDistinctNo"/></td>
		<td rowspan="6" valign="top">
			<s:if test="employee.empImage==null || employee.empImage=='' ">
				<img border="1" id="showPicture" style="border: 1px darkslategray outset;" 
     				name="showPicture" src="../resource/images/None.JPG" align="left" width="110" height="140"/>
			</s:if>
            <s:else>
     			<img border="1" id="showPicture" style="border: 1px darkslategray outset;" align="left" width="110" height="140"
     				name="showPicture" src="../servlet/showImage?style=head&fileName=<s:property value="employee.empImage"/>"/>
            </s:else>
		</td>
	</tr>
	<tr>
		<td align="right">���ţ�</td>
		<td align="left"><s:property value="employee.empDeptNo.getDepartmentName()"/></td>
		<td align="right">ְλ��</td>
		<td align="left"><s:property value="employee.empPbNo.pbName"/></td>
		<td></td>		 
	</tr>
	<tr>
		<td align="right">ֱ������</td>
		<td align="left">
			<s:if test="employee.empSupNo==null || employee.empSupNo.id==null || employee.id==employee.empSupNo.id">
				<s:property value="employee.empName"/>
			</s:if>
			<s:else>
				<a class="listViewTdLinkS1" href="detailEmployeeInfo.action?id=<s:property value = 'employee.empSupNo.getId()'/>">
					<s:property value = "employee.empSupNo.getEmpName()"/></a>
			</s:else>
		</td>
		<td align="right">��������������</td>
		<td align="left"><s:property value="employee.empLocationNo.getLocationName()"/></td>
		<td></td>
	</tr>
	<tr>
		<td align="right">�ֻ���</td>
		<td align="left"><s:property value="employee.empMobile"/></td>
		<td align="right">�Ա�</td>
		<td align="left"><s:property value = "employee.empGender == 1 ? '��' : 'Ů'"/></td>
		<td></td>
	</tr>
	<tr>
		<td align="right">�����绰��</td>
		<td align="left"><s:property value="employee.empWorkPhone"/></td>
		<td align="right">Email��</td>
		<td align="left"><a class="listViewTdLinkS1" href="mailto:<s:property value='employee.empEmail'/>"><s:property value="employee.empEmail"/></a></td>
		<td></td>
	</tr>		
	<tr>
		<td align="right">ֱ���쵼����������</td>
		<td align="left"><s:property value="directEmpCount"/></td>
		<td align="right">��������������</td>
		<td align="left"><s:property value="allEmpCount"/></td>
		<td></td>
	</tr>
</table>
<br/>	
<table cellspacing="0" cellpadding="0" border="0" width="100%" class="gridtable">
	<tr>
		<th>Ա����</th>
		<th>Ա������</th>
		<th>ְ��</th>
		<th>�����绰</th>
		<th>�ֻ�</th>
		<th>Email</th>
	</tr>
	<s:if test="empList != null">
		<s:iterator value="empList">
			<tr>
				<td align = "center">&nbsp;
						<a class="listViewTdLinkS1" href="detailEmployeeInfo.action?id=<s:property value = 'id'/>">
							<s:property value = "empDistinctNo"/></a>
				</td>
				<td align = "center">&nbsp;
					<s:property value = "empName"/>
				</td>
				<td align = "center">&nbsp;
					<s:property value = "empPbNo.pbName"/>
				</td>
				<td align = "center">&nbsp;
					<s:property value = "empWorkPhone"/>
				</td>
				<td align = "center">&nbsp;
					<s:property value = "empMobile"/>
				</td>
				<td align = "center">&nbsp;
					<a class="listViewTdLinkS1" href="mailto:<s:property value='empEmail'/>">
						<s:property value = "empEmail"/>
					</a>
				</td>
			</tr>	
		</s:iterator>
	</s:if>
	<s:else><tr><td colspan="8" align="center">&nbsp;��Ա��û������Ա��</td></tr></s:else>
</table>

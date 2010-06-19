<%@ page language="java" contentType="text/html; charset=GBK"	pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>

<head>	
	<link href="<s:url value="/resource/css/style.css"/>" rel="stylesheet" type="text/css" />	
</head>
<body onload="HRMCommon.check_order();">
<s:component template="bodyhead">
    <s:param name="pagetitle" value="'薪资帐套配置'" />
    <s:param name="helpUrl" value="'64'" />
</s:component>
	<s:form id="searchesa" name="searchesa" action="searchesa" namespace="/compensation" method="POST">
		<s:hidden id="output-ommId" name="outmatchModelId"/>
		<s:hidden id="output-ioName" name="outputIoName"/>
		<input type="hidden" name="searchOrExport" id="searchOrExport" />
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formtable">
			<tr>			
				<td>
					<s:hidden id="order" name="page.order" />
					<s:hidden id="operate" name="page.operate" />	                 
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<s:textfield label="帐套名称" id="txtEsdName" name="empsalaryacct.esacName" size="25" maxlength="64" />
							<td>显示所有版本&nbsp;<input type="checkbox" id="versionFlag" name="versionFlag" <s:if test="versionFlag!=null">checked</s:if> class="checkbox"  value="view" /> <td>
						</tr>
					</table>
				</td>
				<td>
					<input title="[Alt+F]" accesskey="F" id="submit_button" class="button" type="button" onclick="submitSearch();" value="查询">
					<input title="[Alt+C]" accesskey="C" class="button" type="button" value="重置" onClick="window.location='searchesa.action';">
					<br> 
				</td>
			</tr>
		</table>
		<br>
		<div align="left">
			<input class="button" type="button" onClick="window.location='addesa.action';" value="新建帐套" />
			&nbsp;<input type="button" class='button' value="导出帐套" id="btnOutput" onclick="submitExport();" />
		</div>
		<!--acctItemDef  -->
		<table id="empsalaryaccttable" cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">			
			<tr>			
				<th align="center"><a href="#" onclick="HRMCommon.order_submit('esavEsac.esacName','searchesa');">帐套名称</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='esavEsac.esacName_img'></th>
				<th align="center"><a href="#" onclick="HRMCommon.order_submit('esavEsac.esacDesc','searchesa');">帐套描述</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='esavEsac.esacDesc_img'></th> 
				<th align="center"><a href="#" onclick="HRMCommon.order_submit('esavValidFrom','searchesa');">启用时间</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='esavValidFrom_img'></th> 
				<th align="center"><a href="#" onclick="HRMCommon.order_submit('esavValidTo','searchesa');">停用时间</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='esavValidTo_img'></th>	
				<th align="center"><a href="#" onclick="HRMCommon.order_submit('esavCreateTime','searchesa');">创建时间</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='esavCreateTime_img'></th>	
				<th align="center"><a href="#" onclick="HRMCommon.order_submit('esavLastChangeBy','searchesa');">最后修改时间</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='esavLastChangeBy_img'></th>	
				<th align="center">操作</th>
			</tr>
			<s:if test="!empsalaryacctversionList.isEmpty()">
			<s:iterator value="empsalaryacctversionList" status="index">
			<tr>
				<td align="left"> <s:property value="esavEsac.esacName" /></td>
				<td align="left"> <s:property value="esavEsac.esacDesc" /></td>
				<td align="center"> <s:property value="esavValidFrom" /> </td>
				<td align="center"> <s:property value="esavValidTo" /> </td>
				<td align="center"> <s:date name="esavCreateTime" format="yyyy-MM-dd HH:mm:ss"/> </td>
				<td align="center"> <s:date name="esavLastChangeTime" format="yyyy-MM-dd HH:mm:ss"/> </td>
				<td>
				<s:if test="esavValidTo==null">
					<a href="#" onclick="window.location='updateesainit.action?id=<s:property value="id"/>'"><img src="../resource/images/edit.gif" alt="修改帐套"></img></a>
					<a href="#" onclick="window.location='copyAcct.action?id=<s:property value="id"/>&flag=copy'"><img src="../resource/images/ProjectCopy.gif" alt="复制帐套"></img></a>
					<a href="#" onclick="delesa('<s:property value="id" />')"><img src="../resource/images/delete.gif" alt="删除帐套"></img></a>
				</s:if>
				<s:else>
					<a href="#" onclick="window.location='updateesainit.action?id=<s:property value="id"/>'"><img src="../resource/images/Search.gif" alt="查看帐套"></img></a>
					<a href="#" onclick="window.location='copyAcct.action?id=<s:property value="id"/>&flag=copy'"><img src="../resource/images/ProjectCopy.gif" alt="复制帐套"></img></a>
					&nbsp;&nbsp;
				</s:else>							
				</td>
			</tr>
			</s:iterator>
			</s:if>
		   <s:else>
			<tr>				
				<td align="center" colspan="11">
					无帐套存在！
				</td>
			</tr>
		</s:else>		
		</table>
	</s:form>
<SCRIPT LANGUAGE="JavaScript">
function submitSearch(){
    document.getElementById("searchOrExport").value = "";
    document.forms[0].submit();
}

function submitExport(){
    document.getElementById("searchOrExport").value = "export";
    document.forms[0].submit();
}

function delesa(id)
{
 if(confirm("删除帐套最新版本可能会影响到员工现有薪资配置，您确定要删除吗？"))
 {
  window.location="delesa.action?id="+id;
 }
}

</SCRIPT>
<jsp:include flush="true" page="../io/div_omm_select.jsp"></jsp:include>
</body>

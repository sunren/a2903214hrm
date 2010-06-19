<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<html>
<head>
<link href="../resource/css/style.css" rel="stylesheet" type="text/css" />
<title>预定义报表</title>
<script type="text/javascript">
	function confirmDelete(reportId){
		if(window.confirm("确定要删除吗？")){
			document.getElementById('form').action="deleteReport.action?reportId="+reportId;
			document.getElementById('form').submit();
		}
		return ;
	}
</script>
</head>
<body>
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'预定义报表'" />
	<s:param name="helpUrl" value="'32'" />
</s:component>
<div id="basic" style="DISPLAY: block;clear : both">
	<form action="preList.action" method="post" id="form">
		<s:token/>
		<table width="100%" class="formtable">
			<tr>
				<td>报表名称：</td>
				<td><s:textfield name="preDef.reportPreName"/></td>
				<td>报表所在模块：</td>
				<td>
					<s:select list="#{1:'员工模块',2:'薪资模块',3:'培训模块',4:'考勤模块',6:'招聘模块',9:'系统模块'}" name="report.reportModule" emptyOption="true"></s:select>
				</td>
				<td rowspan="4">
					<input type="submit" value="查　询">&nbsp;&nbsp;
				    <input type="reset" value="重　置" onclick="window.location='listAll.action'">
				</td>
			</tr>
		</table>
	</form>
	<p>&nbsp;</p>
	<input type="button" value="新建预定义报表" onclick="window.location='uploadInit.action'"/>
	<table cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">
		<tr>
			<th NOWRAP>报表名称</th>
			<th NOWRAP>所在模块</th>
			<th NOWRAP>报表种类</th>
			<th NOWRAP>显示模式</th>
			<th nowrap>执行URL</th>
			<th NOWRAP>报表描述</th>
			<th colspan="3">操作</th>
		</tr>
		<s:if test="reports.isEmpty()">
		<tr>
			<td colspan="8" align="center">不存在匹配的报表记录！</td>
		</tr>
		</s:if>
		<s:else>
		<s:iterator value="reports">
		<tr>
			<td><s:property value="reportPreName"/></td>
			<td>
				<s:if test="reportPreModule==1">员工模块</s:if>
				<s:elseif test="reportPreModule==2">薪资模块</s:elseif>
				<s:elseif test="reportPreModule==3">培训模块</s:elseif>
				<s:elseif test="reportPreModule==4">考勤模块</s:elseif>
				<s:elseif test="reportPreModule==6">招聘模块</s:elseif>
				<s:elseif test="reportPreModule==9">系统模块</s:elseif>
				<s:else>其他模块</s:else>
			</td>
			<td><s:if test="reportPreType==0">365HRM预定义</s:if>
				<s:elseif test="reportPreType==1">客户预定义</s:elseif></td>
			<td>
				<s:if test="reportPreDisplayMode==0">只显示表格</s:if>
				<s:elseif test="reportPreDisplayMode==1">只显示图表</s:elseif>
				<s:elseif test="reportPreDisplayMode==2">表格+图表</s:elseif>
			</td>
			<td><s:if test="reportPreUrl==0">标准URL执行</s:if><s:else>特殊URL执行</s:else></td>
			<td><s:property value="reportPreDescription"/></td>
			<td></td>
			<td><a href="#" onclick="confirmDelete('<s:property value='reportPreId'/>')"><img src="../resource/images/delete.gif" alt='删除'  border='0'/></a></td>
			<td>
				<a href="paramsInit.action?reportId=<s:property value='reportId'/>"><img src="../resource/images/TaskReports.gif" alt='运行报表'  border='0'/></a>
			</td>
		</tr>
		</s:iterator>
		</s:else>
		
	</table>
</div>
</body>
</html>
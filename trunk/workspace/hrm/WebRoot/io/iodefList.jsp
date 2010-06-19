<%@ page language="java" contentType="text/html; charset=GBK"	pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%@ taglib prefix="ty" uri="/tengsource"%>
<head>
<title>数据接口配置</title>
</head>
<body onload="hrm.common.check_order();">
	<s:component template="bodyhead">
		<s:param name="pagetitle" value="'数据接口列表'" />
		<s:param name="helpUrl" value="'22'" />
	</s:component>
	<form id="iodefForm" name="iodefForm" action="iodefList.action" namespace="/configuration" method="POST">
	<s:hidden name="changeIodef" />
		
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formtable">
				<tr>			
					<td>
						<s:hidden id="order" name="page.order" />
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<s:textfield label="接口名称" name="iodef.ioName" size="20" maxlength="64" />
								<s:select label="接口种类" name="iodef.ioType" list="iodef.ioTypeMap" emptyOption="true" />
								<s:select label="所属模块" name="iodef.ioModule" value="iodef.ioModule" list="iodef.ioModuleMap" emptyOption="true" />
							</tr>
						</table>
					</td>
					<td>
						<input title="[Alt+F]" accesskey="F" id="submit_button" class="button" type="submit"   value="查询">
						<input title="[Alt+C]" accesskey="C" class="button" type="button" value="重置" onClick="window.location='iodefList.action';">
						<br> 
					</td>
				</tr>
			</table>
			<p>
				&nbsp;
			</p>
		<table id="empsalaryaccttable" cellpadding="0" cellspacing="0" width="100%" border="0" class="gridtableList">			
			<tr >			
				<th align="center"> <a  href="#" onClick="hrm.common.order_submit('ioName','iodefForm');return false;">接口名称</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='ioName_img'></th>
				<th align="center"><a  href="#" onClick="hrm.common.order_submit('ioDesc','iodefForm');return false;">接口描述</a><img src='../resource/images/arrow_.gif' width='8' height='10' id='ioDesc_img'></th>
				<th align="center">接口种类</th>
				<th align="center">所属模块</th>
				<th align="center">操作</th>
			</tr>
			<s:if test="!iodefList.isEmpty()">
			<s:iterator value="iodefList" status="index">
			<tr>
				<td align="center">
					<s:if test="ioType==0">
						<a class="listViewTdLinkS1" href="immList.action?ioId=<s:property value="ioId"/>"><s:property value="ioName" /></a>
					</s:if>
					<s:else>
						<a class="listViewTdLinkS1" href="ommList.action?ioId=<s:property value="ioId"/>"><s:property value="ioName" /></a>
					</s:else>
				</td>
				<td align="center"> <s:property value="ioDesc" /></td>
				<td align="center"> <s:property value="ioTypeMean" /></td>
				<td align="center"> <s:property value="ioModuleMean" /></td>
				<td>
					<ty:auth auths="702">
						<a href="#" onClick="initOutmatchBasicList('<s:property value="ioId" />');return false;">
						<s:if test="changeAuthority"><img src="../resource/images/Search_change.gif" alt="查看"></img></s:if>
						<s:else><img src="../resource/images/Search.gif" alt="查看"></img></s:else>
						</a>
					</ty:auth>
				</td>
			</tr>
			</s:iterator>
			</s:if>
			<s:else>
			<tr>				
				<td align="center" colspan="11">
					无数据接口存在
				</td>
			</tr>
		</s:else>		
		</table>
	</form>
<jsp:include flush="true" page="../io/div_iodef_edit.jsp"></jsp:include>
</body>

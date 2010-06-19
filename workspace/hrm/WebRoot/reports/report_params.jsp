<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%@ taglib prefix="jscalendar" uri="/jscalendar" %>
<%@taglib prefix="ty" uri="/tengsource"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>定制报表参数设置</title>
</head>
<body>
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'定制报表参数设置'" />
	<s:param name="helpUrl" value="'32'" />
</s:component>
<div>
	<form id="report_form" action="viewReport.action" onsubmit="return false;">
		<s:hidden id="reportId" name="reportId" value="%{reportId}"></s:hidden>
		<table width="80%" class="gridtable2" cellspacing="0" cellpadding="0" cellpadding="0" >
			<s:if test="!params.isEmpty()">
				<thead>
					<tr>
						<th width="2%"></th>
						<th width="15%" nowrap>参数描述</th>
						<th width="10%" nowrap>过滤条件</th>
						<th >参数值</th>
					</tr>
				</thead>
				<s:iterator value="params" status="current">
					<tr>
						<td><s:property value="#current.index+1"/></td>
						<td nowrap><s:property value="reportSetsDef.reportSetsDefDesc"/></td>
						<td>
							<s:if test="reportParamsCondition=='lt'">
								<img src="../resource/images/reportlt.gif" alt="小于"></img>
							</s:if><s:elseif test="reportParamsCondition=='gt'">
								<img src="../resource/images/reportgt.gif" alt="大于"></img>
							</s:elseif><s:elseif test="reportParamsCondition=='le'">
								<img src="../resource/images/reportle.gif" alt="小于等于"></img>
							</s:elseif><s:elseif test="reportParamsCondition=='ge'">
								<img src="../resource/images/reportge.gif" alt="大于等于"></img>
							</s:elseif><s:elseif test="reportParamsCondition=='equal'">
								等于
							</s:elseif><s:elseif test="reportParamsCondition=='notEqual'">
								<img src="../resource/images/reportne.gif" alt="不等于"></img>
							</s:elseif><s:elseif test="reportParamsCondition=='between'">
								从
							</s:elseif><s:elseif test="reportParamsCondition=='contains'">
								<img src="../resource/images/reportin.gif" alt="包含"></img>
							</s:elseif><s:elseif test="reportParamsCondition=='notContains'">
								<img src="../resource/images/reportex.gif" alt="不包含"></img>
							</s:elseif>
						</td>
						<td>
							<s:if test="reportSetsDef.reportSetsDefType=='date'">
							    <s:textfield id="%{reportSetsDef.reportSetsDefField}" name="%{reportSetsDef.reportSetsDefField}" value="%{reportParamsRangeLow}" onclick="WdatePicker()" size="10"/>
                                <img onclick="WdatePicker({el:'%{reportSetsDef.reportSetsDefField}'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
							</s:if><s:elseif test="reportSetsDef.reportSetsDefType=='datetime'">
							    <s:textfield id="%{reportSetsDef.reportSetsDefField}" name="%{reportSetsDef.reportSetsDefField}" value="%{reportParamsRangeLow}" onclick="WdatePicker()" size="10"/>
                                <img onclick="WdatePicker({el:'%{reportSetsDef.reportSetsDefField}'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
							</s:elseif><s:else>
								<input name="<s:property value='reportSetsDef.reportSetsDefField'/>" value="<s:property value='reportParamsRangeLow'/>"/>
							</s:else>
							<s:if test="reportParamsCondition=='between'">
								&nbsp;到&nbsp;
								<s:if test="reportSetsDef.reportSetsDefType=='date'">
								    <s:textfield id="%{reportSetsDef.reportSetsDefField}2" name="%{reportSetsDef.reportSetsDefField}2" value="%{reportParamsRangeHigh}" onclick="WdatePicker()" size="10"/>
                                    <img onclick="WdatePicker({el:'%{reportSetsDef.reportSetsDefField}2'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
								</s:if><s:elseif test="reportSetsDef.reportSetsDefType=='datetime'">
									<s:textfield id="%{reportSetsDef.reportSetsDefField}2" name="%{reportSetsDef.reportSetsDefField}2" onclick="WdatePicker()" value="%{reportParamsRangeHigh}" size="10"/>
                                    <img onclick="WdatePicker({el:'%{reportSetsDef.reportSetsDefField}2'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
								</s:elseif><s:else>
									<input name="<s:property value='reportSetsDef.reportSetsDefField'/>2" value="<s:property value='reportParamsRangeHigh'/>"/>
								</s:else>	
							</s:if>
						</td>
						<s:if test="#current.even==true">
							</tr>
						</s:if>
				</s:iterator>
			</s:if>
		</table>
		</form>
		<p>&nbsp;</p>
		<ty:auth auths="701,1 or 701,2 or 701,3">
			<div>
				<input type="button" value="运行报表" onclick="execute('HTML');">
				<input type="button" value="PDF下载" onclick="execute('PDF');">
				<input type="button" value="Word下载" onclick="execute('DOC');">
				<input type="button" value="PPT下载" onclick="execute('PPT');">
				<input type="button" value="xml下载" onclick="execute('XML');">
				<input type="button" value="CSV下载" onclick="execute('CSV');">
				<input type="button" value="excel下载" onclick="execute('XLT');">
			<div>
		</ty:auth>
</div>
<div id="display_area">
	<iframe id="frame" marginWidth="0" marginHeight="0" FRAMEBORDER="0" align="left" width="100%" height="480" SRC="" style="overflow-y:auto;"></iframe>
</div>
<script type="text/javascript">
	//运行报表
	function execute(type){
		var default_url = "../report/viewReport.action";
		//获取表单参数
		var form_elements = document.getElementById('report_form').elements;
		var temp_url = "";
		for(var i = 0; i < report_form.length; i++){
			temp_url += (i==0?"?":"&") +form_elements[i].name+"="+form_elements[i].value;
		}
		var url = default_url+temp_url;
		if("HTML"!=type){
			url += "&_format="+type+"&_contentType=attachment&_fileName=report_file";
		}
		$('#frame').attr('src', url);
	}
</script>
</body>
</html>
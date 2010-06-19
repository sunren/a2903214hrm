<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<html>
<head>
	<title>修改用户自定义报表</title>
	<script type="text/javascript" src="../resource/js/dwr/tabpane.js"></script>
	<link rel="stylesheet" type="text/css" href="../resource/css/tab.css" />
	<script type="text/javascript" src="../resource/js/report/edit.js"></script>
	<script type="text/javascript" src="../dwr/interface/customizeReportDWR.js"></script>
	<script type="text/javascript" src="../resource/js/table.js"></script>
	
	<style ref="stylesheet">
		.toggle{background:url("../resource/images/div_pic.png") repeat-x;height:29px; line-height:29px;padding:0 0 0 12px;}
	</style>
</head>
<body>
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'自定制报表修改'" />
	<s:param name="helpUrl" value="'32'" />
</s:component>
<span class="errorMessage" id="msg"></span>
<div class="errorMessage" id="errors"></div>
<form id="updateForm" name="form1" action="editReport.action" method="post">
	<div><input type="button" onclick="doSubmit();" value="提交">&nbsp;<input type="reset" value="取消"></div>
	<input type="hidden" name="updateFlag" value="update"/>
	<input type="hidden" name="report.reportId" value="<s:property value='reportId'/>"/>
	<table width="100%" class="formtable" style="border-bottom:opx">
		<tr align="left">
			<td nowrap width="10%">报表名称<span class="errorMessage">*</span>：</td>
			<td nowrap width="20%"><s:textfield name="report.reportName" id="reportName" required="true" size="30" maxlength="64"></s:textfield></td>
			<td nowrap>报表所在模块<span class="errorMessage">*</span>：</td>
			<td nowrap colspan='2' width="45%">
				<select id="reportModule" name="report.reportModule" onchange="changeValue();">
					<option value="">请选择</option>
					<option value="1">员工模块</option>
					<option value="2">薪资模块</option>
					<option value="3">培训模块</option>
					<option value="4">考勤模块</option>
					<option value="6">招聘模块</option>
					<option value="9">系统模块</option>
				</select>
				数据源<span class="errorMessage">*</span>：<select id="reportDataSource" name="report.reportMainTable">
					<option value="">请选择</option>
				</select>
			</td>
		</tr>	
		<tr>
			<td>报表描述：</td>
			<td><s:textarea name="report.reportDescription" id="reportDescription" cols="23" rows="2" onkeypress="MKeyTextLength(this,128);"></s:textarea></td>
			<s:hidden name="report.reportType" value="0"></s:hidden>
			<td>运行权限<span class="errorMessage">*</span>：</td>
			<td  colspan='2'>
				<select name="report.reportAuthority" id="reportAuthority" size="3" multiple><option value="">请选择</option></select>
			</td>
		</tr>
		<tr>
			<td nowrap>图表显示样式：</td>
			<td>
				<s:select list="#{0:'只显示表格',1:'只显示图表',2:'显示图表和表格' }" name="report.reportDisplayMode" id="reportDisplayMode" onchange="select_display_type();"></s:select>
			</td>
			<td nowrap width="10%"><div id="chart_type_label"  style="display:none">图表类型：</div></td>
			<td nowrap width="10%"  colspan='2'>
				<div id="chart_type" style="display:none">
				<s:select list="#{'Bar Chart':'柱状图','Line Chart':'线状图','Pie Chart':'饼状图'}" id="id_modelType" name="report.reportChartMode" onchange="changeImg();"></s:select>
				<s:select  onchange="changeImg();" list="#{0:'二维',1:'二维有深度',2:'三维'}" name="report.reportChartDimension" id="id_dimensional"></s:select>
				<s:select name="report.reportChartType" id="id_sub_type"  onchange="changeImgSrc();" list="#{'Side-by-side':'并列条形图','Stacked':'堆积条形图','Percent Stacked':'堆积百分比条形'}"></s:select>
				</div>
			</td>
		</tr>
		<tbody id="chart_body" style="display:none">
		<tr>
				<td nowrap>报表标题：</td>
				<td nowrap><s:textfield name="report.reportChartTitle"  size="30"/></td>
				<td nowrap>X轴系列：</td>
				<td nowrap><select name="report.reportXSeries" id="id_model_xseries"><option value="">请选择</option></select></td>
				<td rowspan="4" align="left"><img id="id_chartTypeImg" src="../resource/images/0_0_0.jpg"/></td>
		</tr>	
			<tr>
				<td nowrap>报表标题字体颜色：</td>
				<td>
					<div style="width:103px;width/* */:/**/100px;width: /**/100px;height:20px;border:1px solid #7F9DB9;" lang="">
						<input onblur="this.style.color=this.value" value="GREEN" type="text" maxlength="7" style="width:80px;font-size:12px;float:left;border:0px;padding-top:2px" name="report.reportFontColor" size="10" id="fontColor">
						<img src="../resource/js/report/js_color_picker_v2/images/select_arrow.gif" onmouseover="this.src='../resource/js/report/js_color_picker_v2/images/select_arrow_over.gif'" onmouseout="this.src='../resource/js/report/js_color_picker_v2/images/select_arrow.gif'" onclick="showColorPicker(this,document.getElementById('fontColor'))">
					</div>
				</td>
				<td nowrap>Y轴系列：</td>
				<td nowrap><select name="report.reportYSeries" id="id_model_yseries"><option value="">请选择</option></select></td>
			</tr>
			<tr>
				<td>报表图形背景色：</td>
				<td>
					<div style="width:103px;width/* */:/**/100px;width: /**/100px;height:20px;border:1px solid #7F9DB9;" lang="">
						<input onblur="this.style.color=this.value" type="text" maxlength="7" style="width:80px;font-size:12px;float:left;border:0px;padding-top:2px" name="report.reportBackGround" size="10" id="rgb">
						<img src="../resource/js/report/js_color_picker_v2/images/select_arrow.gif" onmouseover="this.src='../resource/js/report/js_color_picker_v2/images/select_arrow_over.gif'" onmouseout="this.src='../resource/js/report/js_color_picker_v2/images/select_arrow.gif'" onclick="showColorPicker(this,document.getElementById('rgb'))">
					</div>
				</td>
				<td nowrap>Y轴分组：</td>
				<td>
					<select name="report.reportOptionalYSeries" id="id_model_optionaly_series"><option value="">请选择</option></select>
				</td>
			</tr>
		</tbody>
	</table>
	<p>&nbsp;</p>
	<table width="100%">
		<tr>
			<td><h2 style="color:#7f0000;font-size:15px;"><img src="../resource/images/h3Arrow.gif">&nbsp;配置输出列</h2></td>
			<td align="right"><a href="#" onclick="hidden_show('filter');"><span id="filter_span">隐藏</span><img id="filter_img" src="../resource/images/basic_search.gif"></a></td>
		</tr>
	</table>
	<div id="filter" style="display:block">
	<table width="100%" class="gridtable2" cellspacing="0" cellpadding="0" cellpadding="0" >
		<thead>
				<tr>
					<th width="2%"></th>
					<th >数据源</th>
					<th >字段描述</th>
					<th >字段类型</th>
					<th >是否分组</th>
					<th>聚合操作</th>
					<th>&nbsp;</th>
				</tr>
		</thead>
		<tbody id="itemsinputtable">
			<s:if test="!reportSets.isEmpty()">
				<s:iterator value="reportSets" status="rowstatus">
					<tr align="center" ondblclick="add_new_column();current_edit_row=this;">
						<td align="center" class="class" style='border-style: solid; border-color: rgb(187, 187, 187); border-width: 0px 1px 1px 0px;'>
							<span align="center" name="numid"><s:property value=""/><s:property value="#rowstatus.index+1"/></span>
						</td>
						<td>
							<input type="text" maxlength="64" size="30" name="reportSetsDefTable" value="<s:property value='reportSetsReportSetsDef.reportSetsDefDesc'/>" readonly/><input type="hidden" value="<s:property value='reportSetsReportSetsDef.reportSetsDefTable'/>" name="setsSource"/>
						</td>
						<td style="border-style: solid; border-color: rgb(187, 187, 187); border-width: 0px 1px 1px 0px;">
							<input type="text" maxlength="64" size="30" value="<s:property value='reportSetsReportSetsDef.reportSetsDefDesc'/>" name="setsDesc" /><input type="hidden" value="<s:property value='reportSetsReportSetsDef.reportSetsDefField'/>" name="setsFieldName"/>
						</td>
						<td>
							<s:if test="reportSetsReportSetsDef.reportSetsDefType=='date'">
								<input type="text" name="type" value="日期" readonly/>
							</s:if><s:elseif test="reportSetsReportSetsDef.reportSetsDefType=='datetime'">
								<input type="text" name="type" value="日期" readonly/>
							</s:elseif><s:elseif test="reportSetsReportSetsDef.reportSetsDefType=='number'">
								<input type="text" name="type" value="数字" readonly/>
							</s:elseif><s:elseif test="reportSetsReportSetsDef.reportSetsDefType=='text'">
								<input type="text" name="type" value="字符串" readonly/>
							</s:elseif>
						</td>
						<td>
							<s:if test="reportSetsReportSetsDef.reportSetsDefType=='date'||reportSetsReportSetsDef.reportSetsDefType=='datetime'">
								<s:select name="setsGroup" list="#{'nogroup':'不分组','group':'分组','year':'按年份分组','month':'按月份分组','week':'按星期分组','quarter':'按季度分组'}" value="reportSetsGroupFunction"></s:select>
							</s:if><s:else>
								<s:select name="setsGroup" list="#{'nogroup':'不分组','group':'分组'}" value="reportSetsGroupFunction"></s:select>
							</s:else>
						</td>
						<td>
							<s:if test="reportSetsReportSetsDef.reportSetsDefType=='date'">
								<s:select name="setsFunction" onchange="changeXYSeries();" list="#{'':'请选择','max':'最大值','min':'最小值','count':'统计','year':'年份','quarter':'季度','month':'月份','week':'星期'}" value="reportSetsFunction"></s:select>
							</s:if><s:elseif test="reportSetsReportSetsDef.reportSetsDefType=='datetime'">
								<s:select name="setsFunction" onchange="changeXYSeries();" list="#{'':'请选择','max':'最大值','min':'最小值','count':'统计','year':'年份','quarter':'季度','month':'月份','week':'星期'}" value="reportSetsFunction"></s:select>
							</s:elseif><s:elseif test="reportSetsReportSetsDef.reportSetsDefType=='number'">
								<s:select name="setsFunction" onchange="changeXYSeries();" list="#{'':'请选择','max':'最大值','min':'最小值','count':'统计','sum':'求和','avg':'平均值'}" value="reportSetsFunction"></s:select>
							</s:elseif><s:elseif test="reportSetsReportSetsDef.reportSetsDefType=='text'">
								<s:select name="setsFunction" onchange="changeXYSeries();" list="#{'':'请选择','count':'统计'}" value="reportSetsFunction"></s:select>
							</s:elseif>
						</td>
						<td align="center">
							<img onclick="delrow(this);changeXYSeries();" style="CURSOR: pointer" src="../resource/images/delete_inline.gif"/>
						</td>
					</tr>
				</s:iterator>
			</s:if>
				
		</tbody>
		<tfoot>
			<tr>
				<td height="0" colspan="7" rowspan="3">		
					<!-- 新增行项目 -->				
					<input class='button' title="[ALT+A]" accessKey="A" type='button' name='button1' value='新增行项目' onClick="config_new_row('itemsinputtable');" />
			</tr>
		</tfoot>
	</table>
	</div>
	<p>&nbsp;</p>
	<table width="100%">
		<tr>
			<td><h2 style="color:#7f0000;font-size:15px;"><img src="../resource/images/h3Arrow.gif">&nbsp;配置过滤条件</h2></td>
			<td align="right"><a href="#" onclick="hidden_show('condition');"><span id="condition_span">隐藏</span><img id="condition_img" src="../resource/images/basic_search.gif"></a></td>
		</tr>
	</table>
	<div id="condition" style="display:block">
	<table width="100%" class="gridtable2" cellspacing="0" cellpadding="0" cellpadding="0" >
		<thead>
				<tr>
					<th width="2%"></th>
					<th >数据源</th>
					<th >字段描述</th>
					<th>过滤条件</th>
					<th >默认值</th>
					<th>&nbsp;</th>
				</tr>
		</thead>
		<tbody id="itemsinputtable2">
			<s:if test="!reportParams.isEmpty()">
				<s:iterator value="reportParams" status="rowstatus">
					<tr align="center" ondblclick="add_new_condition();current_edit_row=this;">
						<td align="center" class="number" style="border-style: solid; border-color: rgb(187, 187, 187); border-width: 0px 1px 1px 0px;">
							<span align="center" name="numid"><s:property value="#rowstatus.index+1"/></span>
						</td>
						<td style="border-style: solid; border-color: rgb(187, 187, 187); border-width: 0px 1px 1px 0px;">
							<input type="hidden" value="<s:property value='reportSetsDef.reportSetsDefTable'/>" name="paramsSource"/><span><s:property value="reportSetsDef.reportSetsDefDesc"/></span>
						</td>
						<td style="border-style: solid; border-color: rgb(187, 187, 187); border-width: 0px 1px 1px 0px;">
							<input type="hidden" value="<s:property value='reportSetsDef.reportSetsDefField'/>" name="paramsFieldName"/><span><s:property value='reportSetsDef.reportSetsDefDesc'/></span>
						</td>
						<td style="border-style: solid; border-color: rgb(187, 187, 187); border-width: 0px 1px 1px 0px;">
							<input type="hidden" value="<s:property value='reportParamsCondition'/>" name="paramsCondition"/>
							<span>
								<s:if test="reportParamsCondition=='equal'">等于</s:if>
								<s:elseif test="reportParamsCondition=='notEqual'">不等于</s:elseif>
								<s:elseif test="reportParamsCondition=='lt'">小于</s:elseif>
								<s:elseif test="reportParamsCondition=='le'">小于等于</s:elseif>
								<s:elseif test="reportParamsCondition=='gt'">大于</s:elseif>
								<s:elseif test="reportParamsCondition=='ge'">大于等于</s:elseif>
								<s:elseif test="reportParamsCondition=='between'">在..之间</s:elseif>
								<s:elseif test="reportParamsCondition=='contains'">包含</s:elseif>
								<s:elseif test="reportParamsCondition=='notContains'">不包含</s:elseif>
								<s:else>未知过滤条件！</s:else>
							</span>
						</td>
						<td style="border-style: solid; border-color: rgb(187, 187, 187); border-width: 0px 1px 1px 0px;">
							<s:if test="reportParamsRangeHigh!=null">
								<input type="text" onfocus="changetono();" onblur="changetoname();"  style="background-color: transparent;border-color: rgb(255, 255, 255);" value="<s:property value='reportParamsRangeLow'/>,<s:property value='reportParamsRangeLow'/>" name="paramsDefaultValue" size="20" maxlength="128" />
							</s:if>
							<s:else>
								<input type="text" onfocus="changetono();" onblur="changetoname();"  style="background-color: transparent;border-color: rgb(255, 255, 255);" value="<s:property value='reportParamsRangeLow'/>" name="paramsDefaultValue" size="20" maxlength="128" />
							</s:else>
						</td>
						<td align="center" style="border-style: solid; border-color: rgb(187, 187, 187); border-width: 0px 1px 1px 0px;">
							<img onclick="delrow(this);" onmouseover="this.style.cursor=hand;" src="../resource/images/delete_inline.gif"/>
						</td>
					</tr>
				</s:iterator>
			</s:if>
			
		</tbody>
		<tfoot>
			<tr>
				<td height="0" colspan="6" rowspan="3">		
					<!-- 新增行项目 -->				
					<input class='button' title="[ALT+A]" accessKey="A" type='button' name='button1' value='新增行项目' onClick="config_new_row('itemsinputtable2');" />
			</tr>
		</tfoot>
	</table>
	</div>
	<div id="dlgChoose" style="width:300" class="prompt_div_inline">	
	  	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>	
					<div id="filterDiv"  align="left" style="overflow:auto; border-top:1px #dddddd solid;height:80px ;background-color: #ECF3F6;z-index:15;"   >
						<table>
							<tr>
								<td>选择数据源：</td>
								<td><select id="_source_" onchange="config_filter_fields('_source_','_field_');"></select></td>
							</tr>
							<tr>
								<td>字段名：</td>
								<td><select id="_field_"></select></td>
							</tr>
						</table>
					</div> 
				</td>
			</tr>
			<tr>
				<td align="center">
					<div id="t0" align="center" style="overflow:auto; border-bottom:1px #dddddd solid;height:80px ;background-color: #ECF3F6;z-index:15;">
						<table>
							<tr>
								<td><input type="button" value="设置" onclick="add_to_table();changeXYSeries();">&nbsp;<input type="button" value="取消" onclick="$('#dlgChoose').dialog('close');"></td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
		</table>
	</div> 
	
	<div id="dlgCondition" style="width:300" class="prompt_div_inline">	
	  	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>	
					<div id="filterDiv"  align="left" style="overflow:auto; border-top:1px #dddddd solid;height:80px ;background-color: #ECF3F6;z-index:15;"   >
						<table>
							<tr>
								<td>选择数据源：</td>
								<td><select id="_source2_" onchange="config_filter_fields('_source2_','_field2_');"></select></td>
							</tr>
							<tr>
								<td>字段名：</td>
								<td><select id="_field2_" onchange="changeCondition();"></select></td>
							</tr>
							<tr>
								<td>过滤条件：</td>
								<td><select id="_condition_"></select></td>
							</tr>
						</table>
					</div> 
				</td>
			</tr>
			<tr>
				<td align="center">
					<div id="t" align="center" style="overflow:auto; border-bottom:1px #dddddd solid;height:80px ;background-color: #ECF3F6;z-index:15;">
						<table>
							<tr>
								<td><input type="button" value="设置" onclick="add_new_row_to_table();">&nbsp;<input type="button" value="取消" onclick="$('#dlgCondition').dialog('close');"></td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
		</table>
	</div>
	</form>
	
<script defer type="text/javascript">
<!-- 初始化dialog -->
hrm.common.initDialog("dlgChoose", 300);
hrm.common.initDialog("dlgCondition", 300);

function doSubmit(){
	if(validate()){
		document.getElementById('updateForm').submit();
	}
}

select_display_type();

var m = '<s:property value="report.reportModule"/>';
for(var i = 0; i < document.getElementById('reportModule').options.length;i++){
	if(m == document.getElementById('reportModule').options[i].value){
		document.getElementById('reportModule').options[i].selected = "selected";
		break;
	}
}

var t = '<s:property value="report.reportMainTable"/>';
for(var i = 0; i < document.getElementById('reportDataSource').options.length;i++){
	if(t == document.getElementById('reportDataSource').options[i].value){
		document.getElementById('reportDataSource').options[i].selected = "selected";
		break;
	}
}

selectAuth('reportModule','reportAuthority');
modulechange();

displaySources();
changeXYSeries();

var xseries = '<s:property value="report.reportXSeries"/>';
var yseries = '<s:property value="report.reportYSeries"/>';
var oseries = '<s:property value="report.reportOptionalYSeries"/>';
function selectDefaultSeries(){
	doselect('id_model_xseries',xseries);
	doselect('id_model_yseries',yseries);
	doselect('id_model_optionaly_series',oseries);
}

function doselect(id,value){
	var s = document.getElementById(id);
	for(var i = 0; i < s.options.length; i++){
		var tmp = s.options[i];
		if(value == tmp.value ||(value.indexOf(')')!=-1&&value.substring(tmp)!=-1)){
			tmp.value=value;
			tmp.selected = "selected";
		}
	}
}

selectDefaultSeries();

var auths = '<s:property value="report.reportAuthority" escape="false"/>';
var authArray = auths.split(",");

var intervalId = setInterval("authList()",100);
function authList(){
	var options = document.getElementById('reportAuthority').options;
	if(options.length >0){
		for(var j = 0; j < options.length; j++){
			for(var i = 0; i < authArray.length; i++){
				var tmp = authArray[i].trim();
				if(options[j].value == tmp){
					options[j].selected = "selected";
				}
			}
		}
		clearInterval(intervalId);
	}
}
changeSubTypeList();
changeImgSrc();	
</script>
</body>
</html>

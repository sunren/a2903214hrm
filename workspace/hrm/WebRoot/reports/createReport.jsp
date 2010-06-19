<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<html>
<head>
	<title>新建用户自定义报表</title>
	<link rel="stylesheet" type="text/css" href="../resource/css/tab.css" />
	<script type="text/javascript" src="../resource/js/dwr/tabpane.js"></script>
	<script type="text/javascript" src="../resource/js/table.js"></script>
	<script type="text/javascript" src="../resource/js/report/edit.js"></script>
	<script type="text/javascript" src="../dwr/interface/customizeReportDWR.js"></script>
	<style ref="stylesheet">
		.toggle{background:url("../resource/images/div_pic.png") repeat-x;height:29px; line-height:29px;padding:0 0 0 12px;}
	</style>
</head>
<body>
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'新建自定制报表'" />
	<s:param name="helpUrl" value="'32'" />
</s:component>
<span class="errorMessage" id="msg"></span>
<div class="errorMessage" id="errors"></div>
<form id="addForm" name="form1" action="editReport.action" method="post">
	<div><input type="button" class="button" onclick="doSubmit();" value="新增">&nbsp;&nbsp;<input type="reset"  class="button" value="取消"></div>
	<input type="hidden" name="updateFlag" value="create"/>
	<table width="100%" class="formtable" style="border-bottom:opx">
		<tr align="left">
			<td nowrap width="10%">报表名称<span class="errorMessage">*</span>：</td>
			<td nowrap width="20%"><s:textfield name="report.reportName" id="reportName" required="true" size="30" maxlength="64"></s:textfield></td>
			<td nowrap>报表所在模块<span class="errorMessage">*</span>：</td>
			<td nowrap colspan='2' width="45%">
				<select id="reportModule" name="report.reportModule" onchange="changeValue();">
					<option value="" selected="selected">请选择</option>
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
								<td nowrap>选择数据源：</td>
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
		<iframe scrolling="no" style="position:absolute;z-index:-1;width:expression(this.parentNode.offsetWidth-2);height:expression(this.parentNode.offsetHeight);top:0px;left:0px;" frameborder="0" ></iframe>
	</div> 
	
	<div id="dlgCondition" style="width:300">	
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
		<iframe scrolling="no" style="position:absolute;z-index:-1;width:expression(this.parentNode.offsetWidth-2);height:expression(this.parentNode.offsetHeight);top:0px;left:0px;" frameborder="0" ></iframe>
	</div>
	</form>
	
<script type="text/javascript">
<!-- 初始化dialog -->
hrm.common.initDialog("dlgChoose", 300);
hrm.common.initDialog("dlgCondition", 300);

function doSubmit(){
	if(validate()){
		document.getElementById('addForm').submit();
	}
}

function select_display_type(){
	var tmp = document.getElementById('reportDisplayMode').value;
	switch(tmp){
		case '0':
			document.getElementById('chart_type_label').style.display = "none";
			document.getElementById('chart_type').style.display = "none";
			document.getElementById('chart_body').style.display = "none";
			break;
		case '1':
		case '2':
			document.getElementById('chart_type_label').style.display = "block";
			document.getElementById('chart_type').style.display = "block";
			if(isIE()){
				document.getElementById('chart_body').style.display = "block";
			}else{
				document.getElementById('chart_body').style.display = "table-row-group";
			}
			break;
		default:
			break;
	}
}
select_display_type();
changeValue();
</script>
</body>
</html>

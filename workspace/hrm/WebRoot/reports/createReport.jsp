<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<html>
<head>
	<title>�½��û��Զ��屨��</title>
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
	<s:param name="pagetitle" value="'�½��Զ��Ʊ���'" />
	<s:param name="helpUrl" value="'32'" />
</s:component>
<span class="errorMessage" id="msg"></span>
<div class="errorMessage" id="errors"></div>
<form id="addForm" name="form1" action="editReport.action" method="post">
	<div><input type="button" class="button" onclick="doSubmit();" value="����">&nbsp;&nbsp;<input type="reset"  class="button" value="ȡ��"></div>
	<input type="hidden" name="updateFlag" value="create"/>
	<table width="100%" class="formtable" style="border-bottom:opx">
		<tr align="left">
			<td nowrap width="10%">��������<span class="errorMessage">*</span>��</td>
			<td nowrap width="20%"><s:textfield name="report.reportName" id="reportName" required="true" size="30" maxlength="64"></s:textfield></td>
			<td nowrap>��������ģ��<span class="errorMessage">*</span>��</td>
			<td nowrap colspan='2' width="45%">
				<select id="reportModule" name="report.reportModule" onchange="changeValue();">
					<option value="" selected="selected">��ѡ��</option>
					<option value="1">Ա��ģ��</option>
					<option value="2">н��ģ��</option>
					<option value="3">��ѵģ��</option>
					<option value="4">����ģ��</option>
					<option value="6">��Ƹģ��</option>
					<option value="9">ϵͳģ��</option>
				</select>
				����Դ<span class="errorMessage">*</span>��<select id="reportDataSource" name="report.reportMainTable">
					<option value="">��ѡ��</option>
				</select>
			</td>
		</tr>	
		<tr>
			<td>����������</td>
			<td><s:textarea name="report.reportDescription" id="reportDescription" cols="23" rows="2" onkeypress="MKeyTextLength(this,128);"></s:textarea></td>
			<s:hidden name="report.reportType" value="0"></s:hidden>
			<td>����Ȩ��<span class="errorMessage">*</span>��</td>
			<td  colspan='2'>
				<select name="report.reportAuthority" id="reportAuthority" size="3" multiple><option value="">��ѡ��</option></select>
			</td>
		</tr>
		<tr>
			<td nowrap>ͼ����ʾ��ʽ��</td>
			<td>
				<s:select list="#{0:'ֻ��ʾ���',1:'ֻ��ʾͼ��',2:'��ʾͼ��ͱ��' }" name="report.reportDisplayMode" id="reportDisplayMode" onchange="select_display_type();"></s:select>
			</td>
			<td nowrap width="10%"><div id="chart_type_label"  style="display:none">ͼ�����ͣ�</div></td>
			<td nowrap width="10%"  colspan='2'>
				<div id="chart_type" style="display:none">
				<s:select list="#{'Bar Chart':'��״ͼ','Line Chart':'��״ͼ','Pie Chart':'��״ͼ'}" id="id_modelType" name="report.reportChartMode" onchange="changeImg();"></s:select>
				<s:select  onchange="changeImg();" list="#{0:'��ά',1:'��ά�����',2:'��ά'}" name="report.reportChartDimension" id="id_dimensional"></s:select>
				<s:select name="report.reportChartType" id="id_sub_type"  onchange="changeImgSrc();" list="#{'Side-by-side':'��������ͼ','Stacked':'�ѻ�����ͼ','Percent Stacked':'�ѻ��ٷֱ�����'}"></s:select>
				</div>
			</td>
		</tr>
		<tbody id="chart_body" style="display:none">
		<tr>
				<td nowrap>������⣺</td>
				<td nowrap><s:textfield name="report.reportChartTitle"  size="30"/></td>
				<td nowrap>X��ϵ�У�</td>
				<td nowrap><select name="report.reportXSeries" id="id_model_xseries"><option value="">��ѡ��</option></select></td>
				<td rowspan="4" align="left"><img id="id_chartTypeImg" src="../resource/images/0_0_0.jpg"/></td>
		</tr>	
			<tr>
				<td nowrap>�������������ɫ��</td>
				<td>
					<div style="width:103px;width/* */:/**/100px;width: /**/100px;height:20px;border:1px solid #7F9DB9;" lang="">
						<input onblur="this.style.color=this.value" value="GREEN" type="text" maxlength="7" style="width:80px;font-size:12px;float:left;border:0px;padding-top:2px" name="report.reportFontColor" size="10" id="fontColor">
						<img src="../resource/js/report/js_color_picker_v2/images/select_arrow.gif" onmouseover="this.src='../resource/js/report/js_color_picker_v2/images/select_arrow_over.gif'" onmouseout="this.src='../resource/js/report/js_color_picker_v2/images/select_arrow.gif'" onclick="showColorPicker(this,document.getElementById('fontColor'))">
					</div>
				</td>
				<td nowrap>Y��ϵ�У�</td>
				<td nowrap><select name="report.reportYSeries" id="id_model_yseries"><option value="">��ѡ��</option></select></td>
			</tr>
			<tr>
				<td>����ͼ�α���ɫ��</td>
				<td>
					<div style="width:103px;width/* */:/**/100px;width: /**/100px;height:20px;border:1px solid #7F9DB9;" lang="">
						<input onblur="this.style.color=this.value" type="text" maxlength="7" style="width:80px;font-size:12px;float:left;border:0px;padding-top:2px" name="report.reportBackGround" size="10" id="rgb">
						<img src="../resource/js/report/js_color_picker_v2/images/select_arrow.gif" onmouseover="this.src='../resource/js/report/js_color_picker_v2/images/select_arrow_over.gif'" onmouseout="this.src='../resource/js/report/js_color_picker_v2/images/select_arrow.gif'" onclick="showColorPicker(this,document.getElementById('rgb'))">
					</div>
				</td>
				<td nowrap>Y����飺</td>
				<td>
					<select name="report.reportOptionalYSeries" id="id_model_optionaly_series"><option value="">��ѡ��</option></select>
				</td>
			</tr>
		</tbody>
	</table>
	<p>&nbsp;</p>
	
	<table width="100%">
			<tr>
					<td><h2 style="color:#7f0000;font-size:15px;"><img src="../resource/images/h3Arrow.gif">&nbsp;���������</h2></td>
					<td align="right"><a href="#" onclick="hidden_show('filter');"><span id="filter_span">����</span><img id="filter_img" src="../resource/images/basic_search.gif"></a></td>
			</tr>
	</table>
	<div id="filter" style="display:block">
	<table width="100%" class="gridtable2" cellspacing="0" cellpadding="0" cellpadding="0" >
		<thead>
				<tr>
					<th width="2%"></th>
					<th >����Դ</th>
					<th >�ֶ�����</th>
					<th >�ֶ�����</th>
					<th >�Ƿ����</th>
					<th>�ۺϲ���</th>
					<th>&nbsp;</th>
				</tr>
		</thead>
		<tbody id="itemsinputtable">
		</tbody>
		<tfoot>
			<tr>
				<td height="0" colspan="7" rowspan="3">		
					<!-- ��������Ŀ -->				
					<input class='button' title="[ALT+A]" accessKey="A" type='button' name='button1' value='��������Ŀ' onClick="config_new_row('itemsinputtable');" />
			</tr>
		</tfoot>
	</table>
	</div>
	<p>&nbsp;</p>
	<table width="100%">
			<tr>
					<td><h2 style="color:#7f0000;font-size:15px;"><img src="../resource/images/h3Arrow.gif">&nbsp;���ù�������</h2></td>
					<td align="right"><a href="#" onclick="hidden_show('condition');"><span id="condition_span">����</span><img id="condition_img" src="../resource/images/basic_search.gif"></a></td>
			</tr>
	</table>
	<div id="condition" style="display:block">
		<table width="100%" class="gridtable2" cellspacing="0" cellpadding="0" cellpadding="0" >
			<thead>
					<tr>
						<th width="2%"></th>
						<th >����Դ</th>
						<th >�ֶ�����</th>
						<th>��������</th>
						<th >Ĭ��ֵ</th>
						<th>&nbsp;</th>
					</tr>
			</thead>
			<tbody id="itemsinputtable2">
			
				
			</tbody>
			<tfoot>
				<tr>
					<td height="0" colspan="6" rowspan="3">		
						<!-- ��������Ŀ -->				
						<input class='button' title="[ALT+A]" accessKey="A" type='button' name='button1' value='��������Ŀ' onClick="config_new_row('itemsinputtable2');" />
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
								<td nowrap>ѡ������Դ��</td>
								<td><select id="_source_" onchange="config_filter_fields('_source_','_field_');"></select></td>
							</tr>
							<tr>
								<td>�ֶ�����</td>
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
								<td><input type="button" value="����" onclick="add_to_table();changeXYSeries();">&nbsp;<input type="button" value="ȡ��" onclick="$('#dlgChoose').dialog('close');"></td>
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
								<td>ѡ������Դ��</td>
								<td><select id="_source2_" onchange="config_filter_fields('_source2_','_field2_');"></select></td>
							</tr>
							<tr>
								<td>�ֶ�����</td>
								<td><select id="_field2_" onchange="changeCondition();"></select></td>
							</tr>
							<tr>
								<td>����������</td>
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
								<td><input type="button" value="����" onclick="add_new_row_to_table();">&nbsp;<input type="button" value="ȡ��" onclick="$('#dlgCondition').dialog('close');"></td>
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
<!-- ��ʼ��dialog -->
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

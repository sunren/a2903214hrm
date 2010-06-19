<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<html>
<link href="../resource/css/index.css" rel="stylesheet" type="text/css" />	
<head>
<title>腾源软件首页</title>
<style type="text/css">
img.mouseOut {
	padding: 3px;
	border: 0px solid #CCC;
	cursor:pointer;
}
img.mouseIn {
	padding: 3px;
	border: 1px solid #CCC;
	background-color: #1ab2b5; 
	cursor:pointer;
}
.imgDescDiv {
	position:absolute;
	padding-left:3px;
	z-index:5;solid;
	width:310px;
	display:none;
}
.imgDescDiv td{
	padding-left:5px;
	padding-top:5px;
	padding-bottom:5px;
}
</style>
</head>

<body>
<br>
<div id="content">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td align="center" valign="top" bgcolor="#faf9f5" >
			<!-- icon列表 -->
			<table width="100%" border="0" cellspacing="3" cellpadding="3" style="text-align:center;font-size:14px" id="iconTable"></table>
		</td>
		<td width="300" valign="top" align="left">
			<div style=" list-style:none;background:#ffffff;border:1px solid #cfcfcd;font-size:12px;padding:4px">
				<!-- 最新公告 -->
				<table width="100%" border="0" cellspacing="2" cellpadding="3">
					<tr>
						<td style="font-weight:bold"><img src="../resource/images/arrow_icon.gif" style="cursor:pointer" align="absmiddle" /><s:property value ="proclaimName"/></td>
						<td align="right"><a href="../information/searchInfo.action?classId =<s:property value="proclaimId"/>"><font color ="blue">更多...</font></a></td>
					</tr>
					<s:if test="!proclaimList.isEmpty()"><s:iterator value="proclaimList" status="index">
					<tr>
						<td colspan="2" style="border-bottom:1px dashed #aeaeae"><img src="../resource/images/dot.gif" width="3" height="3" style="cursor:pointer" align="absmiddle" />
							<a href="#" onclick="showDesc('<s:property value="id"/>');"><s:property value="infoTitle" /></a>
						</td>
					</tr>
					</s:iterator></s:if>
				</table>
				<!-- 公司新闻 -->
				<table width="100%" border="0" cellspacing="2" cellpadding="3">
					<tr>
						<td style="font-weight:bold"><img src="../resource/images/arrow_icon.gif" style="cursor:pointer" align="absmiddle" /><s:property value ="newsName"/></td>
						<td align="right"><a href="../information/searchInfo.action?classId =<s:property value="newsId"/>"><font color ="blue">更多...</font></a></td>
					</tr>
					<s:if test="!newsList.isEmpty()"><s:iterator value="newsList" status="index">
					<tr>
						<td colspan="2" style="border-bottom:1px dashed #aeaeae"><img src="../resource/images/dot.gif" width="3" height="3" style="cursor:pointer" align="absmiddle" />		 
							<a href="#" onclick="showDesc('<s:property value="id"/>');"><s:property value="infoTitle" /></a>
						</td>
					</tr>
					</s:iterator></s:if>
				</table>
				<!-- 最新提示 -->
				<div style=" list-style:none;background:#ffffff;border:1px solid #cfcfcd;font-size:12px;padding:2px;margin:10px 0px 0px 0px">
					<table width="100%" border="0" cellspacing="0" cellpadding="4">
						<tr bgcolor="#e0e6f6">
							<td style="font-weight:bold"><img src="../resource/images/notice_icon.gif" style="cursor:pointer" align="absmiddle" /> 最新提示：</td>
							<td><span style="float:right;CURSOR: pointer" onclick ="showMoreTip();"><font color ="blue">更多...</font></span></td>
						</tr>
						<s:if test="!tipList.isEmpty()"><s:iterator value="tipList" status="index">
						<tr>
							<td colspan="2" bgcolor="#eff3ff"><img src="../resource/images/dot.gif" alt="" width="3" height="3" style="cursor:pointer" align="absmiddle" /> <s:property escape="false"/></td>
						</tr>
						</s:iterator></s:if>
					</table>
				</div>
				<s:if test ="isDemo=='yes'">
				<div style=" list-style:none;background:#ffffff;border:1px solid #cfcfcd;font-size:12px;padding:2px;margin:10px 0px 0px 0px">
					<table width="100%" border="0" cellspacing="0" cellpadding="4">
						<tr><td height="35" colspan="2" align="center" valign="middle" style="padding-bottom:10px"><font color ="red">您尚未注册365HRM，点击“立即注册”按钮，即可获得100员工/10用户免费体验版，永久免费使用员工、薪资、考勤模块功能！</font></td></tr>
						<tr> <td height="35" colspan="2" align="center" valign="middle" style="padding-bottom:10px">
						<a href="../configuration/updateClient.action" onclick ="toUpgrade();"><img src="../resource/images/regist.gif" alt="申请成为365HRM的注册会员" width="94" height="25" border="0"></a>
						</td></tr>
					</table>
				</div>
				</s:if>
			</div>
		</td>
		<td width="10">&nbsp;</td>
	</tr>
</table>
</div>
<div id="divprofile" class="imgDescDiv">
	<table cellspacing="0" cellpadding="0" >
		<tr><td style="font-weight:bold" bgcolor="#e0e6f6">员工</td></tr>
		<tr><td bgcolor="#eff3ff">　     提供部门招聘需求的申请管理；灵活配置招聘渠道；方便地对应聘人员信息进行管理，建立人才库，详细管理其面试安排、结果；对招聘结果进行各种统计分析，如成本、效果分析。</td></tr>
	</table>
</div>
<div id="divcompensation" class="imgDescDiv">
	<table border="0" cellspacing="0" cellpadding="0">
		<tr><td style="font-weight:bold" bgcolor="#e0e6f6">薪资</td></tr>
		<tr><td bgcolor="#eff3ff">　　灵活设置不同类型员工的各类薪资项目及其计算方式；支持各地社保缴纳方案；与考勤系统链接，根据员工考勤情况调整薪资；具备预先调薪功能；员工自助查询个人当月薪资及薪资历史等；生成各种薪资明细报表和统计报表；提供完善的薪资统计分析功能。</td></tr>
	</table>
</div>
<div id="divexamin" class="imgDescDiv">
	<table border="0" cellspacing="0" cellpadding="0">
		<tr><td style="font-weight:bold" bgcolor="#e0e6f6">考勤</td></tr>
		<tr><td bgcolor="#eff3ff">　　灵活定义上下班时间、灵活设定假日；可单独或批量设置考勤方案；自助请假加班审批流程，根据方案设定自动判断迟到、早退或旷工；每月考勤结果与薪资系统链接进行计算；提供多种不同形式的出勤数据报表。</td></tr>
	</table>
</div>
<div id="divtraining" class="imgDescDiv">
	<table border="0" cellspacing="0" cellpadding="0">
		<tr><td style="font-weight:bold" bgcolor="#e0e6f6">培训</td></tr>
		<tr><td bgcolor="#eff3ff">　　提供部门或个人培训需求的申请管理；可对培训计划进行查询、统计；可对培训课程、培训师资进行全面的评估，对培训效果进行跟踪管理，形成反馈结果。对培训结果进行各种统计分析，如成本、效果分析。</td></tr>
	</table>
</div>
<div id="divrecruitment" class="imgDescDiv">
	<table border="0" cellspacing="0" cellpadding="0">
		<tr><td style="font-weight:bold" bgcolor="#e0e6f6">招聘</td></tr>
		<tr><td bgcolor="#eff3ff">　　提供部门招聘需求的申请管理；灵活配置招聘渠道；方便地对应聘人员信息进行管理，建立人才库，详细管理其面试安排、结果；对招聘结果进行各种统计分析，如成本、效果分析。</td></tr>
	</table>
</div>
<div id="divconfiguration" class="imgDescDiv">
	<table border="0" cellspacing="0" cellpadding="0">
		<tr><td style="font-weight:bold" bgcolor="#e0e6f6">系统</td></tr>
		<tr><td bgcolor="#eff3ff">　　系统各项基础配置的设定；严格的登录机制与权限设定，可灵活设置不同用户的访问权限，提供数据加密、自动备份与恢复等功能。</td></tr>
	</table>
</div>
<div id="divreport" class="imgDescDiv">
	<table border="0" cellspacing="0" cellpadding="0">
		<tr><td style="font-weight:bold" bgcolor="#e0e6f6">报表</td></tr>
		<tr><td bgcolor="#eff3ff">　　灵活自定义各种查询和报表，所有报表的数据范围和查询条件可自由控制；可灵活定义报表显示格式，生成多种分析图表。 </td></tr>
	</table>
</div>
<div id="divinformation" class="imgDescDiv">
	<table border="0" cellspacing="0" cellpadding="0">
		<tr><td style="font-weight:bold" bgcolor="#e0e6f6">信息中心</td></tr>
		<tr><td bgcolor="#eff3ff">　　一个有效的信息发布和交流的场所，可发布公司新闻、公告、规章制度等事项，能够在企业内部员工之间得到广泛传播，使员工了解企业的发展动态。  </td></tr>
	</table>
</div>

<script>
function initIconTable(){
	var tdHtml='<td style="padding-bottom:20px;">'
		+'<img class="mouseOut" src="$icon$" style="cursor:pointer;" onclick="document.location.href=\'$url$\'" onmouseover="changeIcon(this,\'$dir$\');" onmouseout="changeIcon(this,\'$dir$\');">'
		+'<br/>'
		+'$desc$'
		+'</td>';
	var tdEmptyHtml='<td>&nbsp;</td>';
	var tdNumPerTr=3;
	var trArr=new Array();
	var tdArr=new Array();
	for(var i=0;i<hrmAllMenu.length;i++){
		if(hrm.common.isNotEmpty(hrmAllMenu[i]["icon"])){
			tdArr[tdArr.length]=model.simple.setParamterValue(tdHtml,hrmAllMenu[i]);
			if(tdArr.length==tdNumPerTr){
				trArr[trArr.length]=tdArr.join("\n");
				tdArr=new Array();
			}
		}
	}
	if(tdArr.length>0){
		for(var i=tdArr.length;i<tdNumPerTr;i++){
			tdArr[tdArr.length]=tdEmptyHtml;
		}
		trArr[trArr.length]=tdArr.join("\n");
	}
	$("#iconTable").html("<tr>"+trArr.join("</tr>\n<tr>")+"</tr>");
}
function changeIcon(img,dir){
	var div=document.getElementById("div"+dir);
	if(img.className == "mouseOut"){
		img.className="mouseIn"
		var position=model.simple.getElementPosition(img);
		div.style.left = position.left-100;
		div.style.top = position.top + 10;
		div.style.display = 'block';
	}else{
		img.className = "mouseOut" ;
		div.style.display = 'none';
	}
}

function showDesc(idStr){
	url = '../information/openInfo.action?infoId='+idStr;
	window.open(url, '_blank', 'width=600px, height=400px, left=200px, top=150px, scrollbars =yes');
}	

var tipWindow;
function showMoreTip(){ 
	if(!tipWindow||tipWindow.closed){
		tipWindow =window.open('../information/openTip.action', '_blank', 'width=400px, height='+screen.height/2+', left=200px, top=150px, scrollbars =yes resizable =yes');
	}
}

function toUpgrade(){
	return confirm ("注册后，您将获得100员工/10用户的365HRM免费体验版，包含员工、薪资、考勤、信息中心等模块。如果您需要更多人数，或希望使用软件的完整版本，请联系腾源公司客户服务，电话021-50905715");
}

function toAction(str){
	window.location.href = str;
}

initIconTable();
</script>
</body>
</html>

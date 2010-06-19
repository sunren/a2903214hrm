<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<html>
<link href="../resource/css/index.css" rel="stylesheet" type="text/css" />	
<head>
<title>��Դ�����ҳ</title>
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
			<!-- icon�б� -->
			<table width="100%" border="0" cellspacing="3" cellpadding="3" style="text-align:center;font-size:14px" id="iconTable"></table>
		</td>
		<td width="300" valign="top" align="left">
			<div style=" list-style:none;background:#ffffff;border:1px solid #cfcfcd;font-size:12px;padding:4px">
				<!-- ���¹��� -->
				<table width="100%" border="0" cellspacing="2" cellpadding="3">
					<tr>
						<td style="font-weight:bold"><img src="../resource/images/arrow_icon.gif" style="cursor:pointer" align="absmiddle" /><s:property value ="proclaimName"/></td>
						<td align="right"><a href="../information/searchInfo.action?classId =<s:property value="proclaimId"/>"><font color ="blue">����...</font></a></td>
					</tr>
					<s:if test="!proclaimList.isEmpty()"><s:iterator value="proclaimList" status="index">
					<tr>
						<td colspan="2" style="border-bottom:1px dashed #aeaeae"><img src="../resource/images/dot.gif" width="3" height="3" style="cursor:pointer" align="absmiddle" />
							<a href="#" onclick="showDesc('<s:property value="id"/>');"><s:property value="infoTitle" /></a>
						</td>
					</tr>
					</s:iterator></s:if>
				</table>
				<!-- ��˾���� -->
				<table width="100%" border="0" cellspacing="2" cellpadding="3">
					<tr>
						<td style="font-weight:bold"><img src="../resource/images/arrow_icon.gif" style="cursor:pointer" align="absmiddle" /><s:property value ="newsName"/></td>
						<td align="right"><a href="../information/searchInfo.action?classId =<s:property value="newsId"/>"><font color ="blue">����...</font></a></td>
					</tr>
					<s:if test="!newsList.isEmpty()"><s:iterator value="newsList" status="index">
					<tr>
						<td colspan="2" style="border-bottom:1px dashed #aeaeae"><img src="../resource/images/dot.gif" width="3" height="3" style="cursor:pointer" align="absmiddle" />		 
							<a href="#" onclick="showDesc('<s:property value="id"/>');"><s:property value="infoTitle" /></a>
						</td>
					</tr>
					</s:iterator></s:if>
				</table>
				<!-- ������ʾ -->
				<div style=" list-style:none;background:#ffffff;border:1px solid #cfcfcd;font-size:12px;padding:2px;margin:10px 0px 0px 0px">
					<table width="100%" border="0" cellspacing="0" cellpadding="4">
						<tr bgcolor="#e0e6f6">
							<td style="font-weight:bold"><img src="../resource/images/notice_icon.gif" style="cursor:pointer" align="absmiddle" /> ������ʾ��</td>
							<td><span style="float:right;CURSOR: pointer" onclick ="showMoreTip();"><font color ="blue">����...</font></span></td>
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
						<tr><td height="35" colspan="2" align="center" valign="middle" style="padding-bottom:10px"><font color ="red">����δע��365HRM�����������ע�ᡱ��ť�����ɻ��100Ա��/10�û��������棬�������ʹ��Ա����н�ʡ�����ģ�鹦�ܣ�</font></td></tr>
						<tr> <td height="35" colspan="2" align="center" valign="middle" style="padding-bottom:10px">
						<a href="../configuration/updateClient.action" onclick ="toUpgrade();"><img src="../resource/images/regist.gif" alt="�����Ϊ365HRM��ע���Ա" width="94" height="25" border="0"></a>
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
		<tr><td style="font-weight:bold" bgcolor="#e0e6f6">Ա��</td></tr>
		<tr><td bgcolor="#eff3ff">��     �ṩ������Ƹ���������������������Ƹ����������ض�ӦƸ��Ա��Ϣ���й��������˲ſ⣬��ϸ���������԰��š����������Ƹ������и���ͳ�Ʒ�������ɱ���Ч��������</td></tr>
	</table>
</div>
<div id="divcompensation" class="imgDescDiv">
	<table border="0" cellspacing="0" cellpadding="0">
		<tr><td style="font-weight:bold" bgcolor="#e0e6f6">н��</td></tr>
		<tr><td bgcolor="#eff3ff">����������ò�ͬ����Ա���ĸ���н����Ŀ������㷽ʽ��֧�ָ����籣���ɷ������뿼��ϵͳ���ӣ�����Ա�������������н�ʣ��߱�Ԥ�ȵ�н���ܣ�Ա��������ѯ���˵���н�ʼ�н����ʷ�ȣ����ɸ���н����ϸ�����ͳ�Ʊ����ṩ���Ƶ�н��ͳ�Ʒ������ܡ�</td></tr>
	</table>
</div>
<div id="divexamin" class="imgDescDiv">
	<table border="0" cellspacing="0" cellpadding="0">
		<tr><td style="font-weight:bold" bgcolor="#e0e6f6">����</td></tr>
		<tr><td bgcolor="#eff3ff">�����������°�ʱ�䡢����趨���գ��ɵ������������ÿ��ڷ�����������ټӰ��������̣����ݷ����趨�Զ��жϳٵ������˻������ÿ�¿��ڽ����н��ϵͳ���ӽ��м��㣻�ṩ���ֲ�ͬ��ʽ�ĳ������ݱ���</td></tr>
	</table>
</div>
<div id="divtraining" class="imgDescDiv">
	<table border="0" cellspacing="0" cellpadding="0">
		<tr><td style="font-weight:bold" bgcolor="#e0e6f6">��ѵ</td></tr>
		<tr><td bgcolor="#eff3ff">�����ṩ���Ż������ѵ�������������ɶ���ѵ�ƻ����в�ѯ��ͳ�ƣ��ɶ���ѵ�γ̡���ѵʦ�ʽ���ȫ�������������ѵЧ�����и��ٹ����γɷ������������ѵ������и���ͳ�Ʒ�������ɱ���Ч��������</td></tr>
	</table>
</div>
<div id="divrecruitment" class="imgDescDiv">
	<table border="0" cellspacing="0" cellpadding="0">
		<tr><td style="font-weight:bold" bgcolor="#e0e6f6">��Ƹ</td></tr>
		<tr><td bgcolor="#eff3ff">�����ṩ������Ƹ���������������������Ƹ����������ض�ӦƸ��Ա��Ϣ���й��������˲ſ⣬��ϸ���������԰��š����������Ƹ������и���ͳ�Ʒ�������ɱ���Ч��������</td></tr>
	</table>
</div>
<div id="divconfiguration" class="imgDescDiv">
	<table border="0" cellspacing="0" cellpadding="0">
		<tr><td style="font-weight:bold" bgcolor="#e0e6f6">ϵͳ</td></tr>
		<tr><td bgcolor="#eff3ff">����ϵͳ����������õ��趨���ϸ�ĵ�¼������Ȩ���趨����������ò�ͬ�û��ķ���Ȩ�ޣ��ṩ���ݼ��ܡ��Զ�������ָ��ȹ��ܡ�</td></tr>
	</table>
</div>
<div id="divreport" class="imgDescDiv">
	<table border="0" cellspacing="0" cellpadding="0">
		<tr><td style="font-weight:bold" bgcolor="#e0e6f6">����</td></tr>
		<tr><td bgcolor="#eff3ff">��������Զ�����ֲ�ѯ�ͱ������б�������ݷ�Χ�Ͳ�ѯ���������ɿ��ƣ������屨����ʾ��ʽ�����ɶ��ַ���ͼ�� </td></tr>
	</table>
</div>
<div id="divinformation" class="imgDescDiv">
	<table border="0" cellspacing="0" cellpadding="0">
		<tr><td style="font-weight:bold" bgcolor="#e0e6f6">��Ϣ����</td></tr>
		<tr><td bgcolor="#eff3ff">����һ����Ч����Ϣ�����ͽ����ĳ������ɷ�����˾���š����桢�����ƶȵ�����ܹ�����ҵ�ڲ�Ա��֮��õ��㷺������ʹԱ���˽���ҵ�ķ�չ��̬��  </td></tr>
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
	return confirm ("ע����������100Ա��/10�û���365HRM�������棬����Ա����н�ʡ����ڡ���Ϣ���ĵ�ģ�顣�������Ҫ������������ϣ��ʹ������������汾������ϵ��Դ��˾�ͻ����񣬵绰021-50905715");
}

function toAction(str){
	window.location.href = str;
}

initIconTable();
</script>
</body>
</html>

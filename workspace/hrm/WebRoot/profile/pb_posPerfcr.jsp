<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<%  response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%>
<html>
<head>
<link rel="stylesheet" href="../resource/css/style.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="../resource/css/edit_select.css" />
<script type="text/javascript" src="../resource/js/hrm/common.js"></script>
<script type="text/javascript" src="../resource/js/error.js"></script>
<!-- jQuery start -->
<link rel="stylesheet" type="text/css" href="../resource/js/jquery/themes/base/ui.all.css" />
<link rel="stylesheet" type="text/css" href="../resource/css/tablesorter.css" />
<script type="text/javascript" src="../resource/js/jquery/jquery-1.3.2.js"></script>
<script type="text/javascript" src="../resource/js/jquery/jquery.tablesorter-2.0.3.js"></script>
<script type="text/javascript" src="../resource/js/jquery/jquery.tablednd_0_5.js"></script>
<script type="text/javascript" src="../resource/js/jquery/ui/ui.core.js"></script>
<script type="text/javascript" src="../resource/js/jquery/ui/ui.tabs.js"></script>
<script type="text/javascript" src="../resource/js/jquery/ui/ui.draggable.js"></script>
<script type="text/javascript" src="../resource/js/jquery/ui/ui.resizable.js"></script>
<script type="text/javascript" src="../resource/js/jquery/ui/ui.dialog.js"></script>
<script type="text/javascript" src="../resource/js/jquery/plugin/jquery.bgiframe.js"></script>
<script type="text/javascript" src="../resource/js/jquery/jquery.inputer.js"></script>
<!-- jQuery end -->
<!-- DWR start -->
<script type="text/javascript" src="../dwr/engine.js"></script>
<script type="text/javascript" src="../dwr/util.js"></script>
<!-- DWR end -->
<script type="text/javascript" src="../dwr/interface/pbManage.js"></script>
<script type="text/javascript" src="../dwr/interface/perfcr.js"></script>
<script type="text/javascript" src="../resource/js/configuration/BaseManager.js"></script>
<script type="text/javascript" src="../resource/js/profile/pbPosPerfcr.js"></script>
<style>
.over{ background:#00BFFF}
</style>
</head>
<body>
<s:hidden id="pbId" name="pbId" />
<table id="table_pbPosPerfcr" cellspacing="1" cellspacing="0" width="80%" border="0" class="tablesorter">
	<thead>
		<tr align="center">
			<th width="30%" nowrap="nowrap">指标分类</th>
			<th width="20%" nowrap="nowrap">指标名称</th>
			<th width="60%" nowrap="nowrap">指标描述</th>
		</tr>
	</thead>
	<tbody id="tbody_pbPosPerfcr">
		<s:iterator value="pbPosPerfcrList">
			<tr id='<s:property value="id"/>' align="center" >
				<td align="center"><s:property value="posperfcrPerfcrId.perfcrClassName" /></td>
				<td align="center"><s:property value="posperfcrPerfcrId.perfcrName" /></td>
				<td align="center"><s:property value="posperfcrPerfcrId.perfcrDesc" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
<div class="btnlink">
	<a id="link_add_pbPosPerfcr" style="cursor:pointer">新增</a>
	<a id="link_delete_pbPosPerfcr" style="cursor:pointer">删除</a>
	<a id="link_sort_pbPosPerfcr" style="cursor:pointer">保存排序</a>
</div>
<br>
<div id="dialog_pbPosPerfcr" title="添加考核标准" style="clear : both">
	<table border="0" cellspacing="0" cellpadding="0" width="100%" >
		<tr>
			<td>考核标准<font color="red">*</font>：</td>
			<td>
			<s:hidden id="perfcrId"/>
			<div>
            <table cellpadding="0" cellspacing="0" class="select">
			<tr><td bgcolor="#FFFFFF" >
			<input type="text" id="perfcrName" class="selecttext" style="" readonly="readonly" size="12" />
			<button type="button" class="selectbutton"  style="CURSOR: pointer;" id="showPerfcrDiv" onclick="showPerfcrDiv();"/>
			</td></tr>
			</table>
			</div>
			</td>
		</tr>
		
		<tr height="35">
			<td>&nbsp;</td>
			<td colspan="2">
			    <br/>
				<input id="btn_pbPosPerfcr_update" type="button" value="保存">
				<input id="btn_pbPosPerfcr_create" type="button" value="保存">
				<input id="btn_pbPosPerfcr_close" type="button" value="关闭">
				<input id="pbPosPerfcrId" type="hidden" />
			</td>
		</tr>
	</table>
</div>
<div id="selectPerfcrDiv" style="position:absolute;z-index:2000;top:100px;left:100;solid; width:350px;display:none;" >	
	<table width="100%"  border="0" cellspacing="0" cellpadding="0" style="background-color: #ECF6FB;border: 1px #6BB5DA solid">
	  	<tr>
	  		<td>
	  			<div id="titleBar" style="CURSOR: move;" class="prompt_div_head">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
	  	 				<TR>
						    <td  align="left" >选择考核标准</td>
						    
						    <td align="right" valign="middle"><img src="../resource/images/close_div.gif" onclick="document.getElementById('selectPerfcrDiv').style.display='none';" style="CURSOR: pointer"/></td>
						 </TR>	
					</table>
				</div>	
	  		</td>
	  	</tr>
	  	<tr>
	  		<td>
	  			<div id="perfcrTableDiv" align="left" style="overflow:auto;height:150px ;background-color: #ECF3F6;clear : both">
	  			<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td align="center">快速查询：
							<input type="text" id="searchCondition"  onkeydown="searchPerfcrCheckKeyCode()" />
							<input type="button"  onclick="searchPerfcr()" value=" 查询 " />
						</td>
						<td align="right"><input type="button" value="新建指标" onclick="newPerfcr();"/></td>
					</tr>
	            </table>
	  			<table id="selectPerfcrTable" cellspacing="1" cellspacing="0" border="0">
						<tr align="center">
							<td width="15%" nowrap="nowrap">指标分类</td>
							<td width="20%" nowrap="nowrap">&nbsp;指标名称</td>
							<td width="50%" nowrap="nowrap">指标描述</td>
						</tr>
				</table>
	  			</div>
	  		</td>
	  	</tr>
	</table>
</div>
<div id="dlgPerfcr" title="新建考核标准" style="clear : both">
	<table cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td>指标分类<font color="red">*</font>：</td>
			<td><s:select id="new_perfcrClass" list="pcTypeMap" listKey="key" listValue="value"/></td>
		</tr>
		<tr>    
		    <td>指标名称<font color="red">*</font>：</td>
		    <td><s:textfield  id="new_perfcrName" size="16" maxlength="16" /></td>
		</tr>
		<tr>    
		    <td>指标描述<font color="red">*</font>：</td>
		    <td><s:textarea id="new_perfcrDesc" rows="3" cols="35" onkeypress="HRMCommon.MKeyTextLength(this,64);"/></td>
		</tr>
		
		<tr height="35">
			<td align="center" colspan="2">
			    <br/>
				<input type="button" value="保存" onclick="savePerfcr()"/>
			</td>
		</tr>
	</table>
</div>
<script type="text/javascript">
//显示选择指标div
function showPerfcrDiv(){
    //设置div的位置
    var targetEle = document.getElementById("perfcrName");
	var	winX=hrm.common.getPoint(targetEle).x;	
	var winY=hrm.common.getPoint(targetEle).y;
	var selectPerfcrDiv=document.getElementById("selectPerfcrDiv");
	selectPerfcrDiv.style.top=winY+20;
	selectPerfcrDiv.style.left=winX;
	//dwr 获取指标库
	perfcr.getAllPerfcr(perfcrCallback);
	function perfcrCallback(perfcrArray){
	   //添加tr
	   addTrByPerfcrArray(perfcrArray);
	   //显示div
	   $('#selectPerfcrDiv').show();
	}
}
//根据指标array添加tr
function addTrByPerfcrArray(perfcrArray){
      var table=$('#selectPerfcrTable');
	  table.find("tr:gt(0)").remove();//清空table除标题外
      if(perfcrArray.length>0){
	       for(var i=0;i<perfcrArray.length;i++){
	           var perfcr=perfcrArray[i];
	           var trTemplet="<tr id='{perftrId}'><td>{perfcrClassName}</td><td>{perfcrName}</td><td>{perfcrDesc}</td></tr>";
	           var newTr=trTemplet.replace("{perftrId}", perfcr.id);
	               newTr=newTr.replace("{perfcrClassName}", perfcr.perfcrClassName);
	               newTr=newTr.replace("{perfcrName}", perfcr.perfcrName);
	               newTr=newTr.replace("{perfcrDesc}", perfcr.perfcrDesc);
	           table.append(newTr);
	       }
	   }
	   //给tr添加事件
	   table.find("tr:gt(0)").hover(function(){
	            $(this).addClass("over")
	            },function(){
	                $(this).removeClass("over")
	          });
	   table.find("tr:gt(0)").click(function(){
				   var perfcrId=$(this).attr('id');
				   $('#perfcrId').val(perfcrId);
				   $('#perfcrName').val(this.cells[1].innerHTML);
				   $('#selectPerfcrDiv').hide();
	          });
}
//新建指标
function newPerfcr(){
   $('#new_perfcrClass').val('');
   $('#new_perfcrName').val('');
   $('#new_perfcrDesc').val('');
   $('#dlgPerfcr').dialog("option","modal",false);
   hrm.common.openDialog('dlgPerfcr');
   $('#selectPerfcrDiv').hide();
}
//保存指标
function savePerfcr(){
   var obj={};
   obj.perfcrClass=$('#new_perfcrClass').val();
   obj.perfcrName=$('#new_perfcrName').val();
   obj.perfcrDesc=$('#new_perfcrDesc').val();
   function newPerfcrCallBack(data){
         if(data == "FAIL"){
            errMsg("errMsg", "验证失败！");
            return;
         }
         $('#perfcrId').val(data);
	     $('#perfcrName').val(obj.perfcrName);
         hrm.common.closeDialog('dlgPerfcr');
   }
   perfcr.newPerfcr(obj,newPerfcrCallBack);
   
}
//查找指标
function searchPerfcr(){
   var queryString=$('#searchCondition').val().trim();
   function queryCallBack(perfcrArray){
       //添加tr
	   addTrByPerfcrArray(perfcrArray);
   }
   perfcr.queryPerfcrByName(queryString,queryCallBack);
}
//按下回车时提交
function searchPerfcrCheckKeyCode(){
    if(window.event.keyCode==13){
    	searchPerfcr();
    }
}
var config={
    tableId     :"table_pbPosPerfcr",
    tbodyId     :"tbody_pbPosPerfcr",
    dialogId    :"dialog_pbPosPerfcr",
    updateButton:"btn_pbPosPerfcr_update",
    addButton   :"btn_pbPosPerfcr_create",
    dialogHeight:230,
    dialogWidth :400,
    addLink     :"link_add_pbPosPerfcr",
    deleteLink  :"link_delete_pbPosPerfcr",
    updateLink  :null,
    sortLink    :"link_sort_pbPosPerfcr",
    closeButton :"btn_pbPosPerfcr_close",
    modal       :true
};
pbPosPerfcrManager = new PbPosPerfcrManager(config);
$("#dialog_pbPosPerfcr").dialog('option', 'modal', false);
$("#dlgPerfcr").dialog({bgiframe: true, autoOpen: false,width:400,height: 'auto',resizable: false,modal: false});
</script>
</body>
</html>
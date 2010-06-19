<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>组织结构管理</title>
<script type="text/javascript" src="../resource/js/profile/DHTreeClass.js"></script>
<script type="text/javascript" src="../resource/js/profile/PositionTree.js"></script>
<script type="text/javascript" src="../dwr/interface/positionManage.js"></script>
</head>
<body onload="initData()">
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'组织结构管理：'" />
	<s:param name="helpUrl" value="'0'" />
</s:component>
<table border=0 cellpadding=0 cellspacing=0 width=100%>
	<tr>
		<td valign="top" >
			<div id="divPositionTree" style="width:230px;height:422px;background-color:#f5f5f5;border :1px solid Silver;overflow:auto;"></div>
		</td>
		<td valign="top" align="left" width="100%">
		    <div  class="btnlink">
			<a href="#"  onclick="newSubPosition();">新增下级职位</a>
			<a href="#" id="changeSupPos" onclick="modifySupPosition();">修改上级职位</a>
			<a href="#"  onclick="delPosition();">删除职位</a>
		    </div>
			<div id="logarea" style="background-color:#f5f5f5;height:397px;width:100%;border :1px solid Silver;overflow:hidden;">
				<IFRAME ID="iframe" name="iframe" marginWidth="0" marginHeight="0" FRAMEBORDER="0" align="left"
	 		 	width="100%" height="410" src="" style="overflow-y:auto;"></IFRAME>
			</div>
		</td>
	</tr>
</table>
<div id="dlgNewSubPosition" title="新建下级职位" >
	<table cellpadding="0" cellspacing="1" width="100%">
		<tr>
			<td>选择下级职位<font color="red">*</font>：</td>
			<td class="errorMessage">
				<select id="selcectSubPosition" >
				
				</select>
				<label>&nbsp;</label>
			</td>
		</tr>
		<tr>
			<td>职位个数<font color="red">*</font>：</td>
			<td class="errorMessage">
				<input type="text"  id="newPositionNum" size="12" maxlength="8" onkeydown="HRMCommon.checkPositive(event);" />
			    <label id="lableNewNum">&nbsp;</label>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input type="button" value="创建" onclick="savePosition();"/>
				<input type="button" value="取消" onclick="cancel();"/>
			</td>
		</tr>
	</table>
</div>


<script type="text/javascript">
// 1.初始化节点数据，用于页面上抓取使用, type: 0:company, 1:branch, 2:dept, 3:position;
function node(id, parentId, name, type,state){
	this.id = id;
	this.parentId = parentId;
	this.name = name;
	this.type = type;
	this.isResp=state;
}
var nodeArr = {
	<s:iterator value="nodeList" status="index">
	    '<s:property value="nodeId"/>' : new node('<s:property value="nodeId"/>', '<s:property value="nodeParentId"/>', '<s:property value="nodeName"/>', '<s:property value="nodeType"/>','<s:property value="nodeStatus"/>')
	    <s:if test="!#index.isLast()">,</s:if>
	</s:iterator>
};
function getNodeType(id){//获取节点的类型;
	var obj = nodeArr[id];
	return obj.type;
}

// 2.初始化treeArray数据；
var treeArray = new Array(
	<s:iterator value="nodeList" status="index">
	    ['<s:property value="nodeId"/>', '<s:property value="nodeParentId"/>', '<s:property value="nodeName"/>']
	    <s:if test="!#index.isLast()">,</s:if>
	</s:iterator>
);

// 3.初始化tree；
var tree;
var positionTree;
function initData(){
	//组装参数；
	var param = {
		"treeDiv"      : 'divPositionTree',
		"dataSource"   : treeArray,
		"menuXml"      : null,
		"enableChkbox" : false,
		"enableDrag"   : false
	};
	// 子类初始化；
	positionTree = new PositionTree(param);
	positionTree.init();// 初始化树形结构；
	tree = positionTree.tree;
	//设置不同的图片；
	positionTree.setNodesImage(nodeArr);
}

// 刷新右边iframe中的页面；
function refreshIFrame(url){
	var iframe=document.getElementById("iframe");
	if(hrm.common.navigatorIsFF()){
		iframe.contentWindow.document.location=url;
	}else{
		iframe.src=url;
	}
}

//新建下级职位
function newSubPosition(){
   var positionId = tree.getSelectedItemId();
   if(positionId.trim()==''){
      alert("请选择一个职位！");
      
      return;
   }
   var selectedItemType=getNodeType(positionId);
   if(selectedItemType!=5 && selectedItemType!=6 && selectedItemType!=7 ){
       alert("请选择一个职位！");
       return;
   }
   $('#selcectSubPosition').val("");
   $('#newPositionNum').val("");
   //初始化选择下级职位select
   positionManage.getSelectSubList(positionId,function(pbList){
          if(pbList==null || pbList.length<1){
             alert("无下级职位！");
             return;
          }
          //得到select element
          var obj=document.getElementById('selcectSubPosition');
          for(var i=obj.options.length; obj!=null && i>=0; i--){ 
              obj.options[i] = null;
          }
          //添加选项
          for(var i=0; pbList!=null&&i<pbList.length; i++){
              obj.options[obj.length] = new Option(pbList[i].pbName, pbList[i].id);
          }
          hrm.common.openDialog('dlgNewSubPosition');
   });
   
}

//保存添加
function savePosition(){
    var positionId = tree.getSelectedItemId();
    var selectId=$('#selcectSubPosition').val();
    var num=$('#newPositionNum').val().trim();
    //验证
    if(num==""){
       $('#lableNewNum').html('不能为空！');
       return;
    }
    //保存
    positionManage.savePosition(positionId,selectId,num,function (positionList){
        if(positionList==null){
           alert( "编制人数不够！");
           return;
        }
        for(var i=0;i<positionList.length;i++){
             var pos=positionList[i];
             var node={};
             node.id=pos.id;
             node.parentId=positionId;
             node.name=pos.positionPbId.pbName;
             node.type=7;
             node.isResp=0;
             nodeArr[node.id]=node;
             tree.insertNewItem(node.parentId,node.id,node.name,0,'','','');
             positionTree.setNodesImage(nodeArr);
        }
        hrm.common.closeDialog('dlgNewSubPosition');
        alert("创建成功！");
    });
}
//修改上级职位
function modifySupPosition(){
  var positionId = tree.getSelectedItemId();
   if(positionId.trim()==''){
      alert("请选择一个职位！");
      return;
   }
   var selectedItemType=getNodeType(positionId);
   if(selectedItemType!=5 && selectedItemType!=6 && selectedItemType!=7 ){
       alert("请选择一个职位！");
       return;
   }

   showPostionTree(positionId, "changeSupPos");
}
//删除下级职位
function delPosition(){
  var positionId = tree.getSelectedItemId();
   if(positionId.trim()==''){
      alert("请选择一个职位！");
      return;
   }
   var selectedItemType=getNodeType(positionId);
   if(selectedItemType!=5 && selectedItemType!=6 && selectedItemType!=7 ){
       alert("请选择一个职位！");
       return;
   }
  positionManage.delPosition(positionId,function(state){
        if(state=="SUCC"){
           tree.deleteItem(positionId,true);
           alert("删除成功！");
           return;
        }else{
            alert(state);
        }
        
  });
}

// 取消按钮；
function cancel(){
	hrm.common.closeDialog('dlgNewSubPosition');
}

//初始化dialog
$(document).ready(function(){
    $("#dlgNewSubPosition").dialog({
         bgiframe: true,
         autoOpen: false,
         width:400,
         height: 'auto',
         resizable: false,
         modal: true
  });
}); 

</script>
<jsp:include flush="true" page="change_sup_position.jsp"></jsp:include>
</body>
</html>

<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="s" uri="webwork"%>
<head>
<script type="text/javascript" src="../resource/js/profile/organization.js"></script>
<script type='text/javascript' src='../dwr/interface/OrgMapAction.js'></script>
<title>组织结构图</title>
<style type="text/css">
.OrgBox{
    border:thin solid blue;background-color:#ADD8E6;
	font-size:12px;
	padding:2px 2px 2px 2px;
	clear:left;
	float:left;
	position:absolute;
	width:80px;
	height:90px;
	overflow:visible;
}
.OrgBox div{
    text-align:center;
	color:#FFA500;
	overflow:hidden;
}
.view {
    color:#002780;
    cursor:pointer;
    text-decoration:underline;
    text-align:center;
}
.positionDiv{
    font-size: 12px;
	width:90px;
	border-top:1px solid #999999;
	border-left:1px solid #999999;
	border-right:2px solid #666;
	border-bottom:2px solid #666;
    text-align:center;
    color:#002780;
    background-color:#ECF6FB;
    overflow:hidden;
    display:none;
}
</style>
</head>
<body>
   <div id="queryDiv">
	<s:component template="bodyhead">
		<s:param name="pagetitle" value="'组织结构图'" />
		<s:param name="helpUrl" value="'1'" />
	</s:component>
	<s:form id="queryForm" name="queryForm" action="showOrgMap" namespace="/profile" method="post" enctype="multipart/form-data">
	<table width="100%" border="0" cellspacing="2" cellpadding="0" style="background-color: #ECF6FB;border: 1px #6BB5DA solid">
    
	<tr>
		<td>
		<div style="DISPLAY:block;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
				    <s:if test="haveHist.equals(\"1\")">
					<s:textfield id="queryDate" name="queryDate" size="10"/>
					<img onclick="WdatePicker({el:'queryDate'})" src="../resource/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" style="cursor:pointer" align="absmiddle">
	                </s:if>
	               &nbsp;&nbsp;&nbsp; 组织单元:<s:select list="deptList" listKey="id"
                     listValue="departmentName" name="queryDeptId"/>
	               &nbsp;&nbsp;&nbsp; 层数:<s:select  list="selectDepthList" name="queryDepth" emptyOption="true"/>
				</tr>
			</table>
		</div>
		</td>
		<td align="center" nowrap="nowrap">
			<input title="[Alt+F]" accesskey="F" id="submit_button" class="button" type="submit"  name="button2" value="查询"> 
			<input title="[Alt+C]" accesskey="C" class="button" type="button" name="button22" value="重置" onclick="window.location='showOrgMap.action';"><br>		
		</td>
	</tr>
    </table>
	</s:form>
	</div>
	<div id="container" style="width:830px;">
	</div>
	<div id="positionDiv" class="positionDiv">
	<table id="positionTable">
	<tr><td><span style="color:#FFA500;">职位名称</span></td></tr>
	
	
	</table>
	</div>
	
<script language="javascript">
//declare
function Department(){
    this.id=null;
    this.name=null;
	this.parent=null;
	this.sortId=null;
	this.actualNum=null;
	this.orderNum=null;
	this.deptHead=null;
	
}
function initialNode(pid,parentNode){
    
   for(var i=0;i<deptArray.length;i++){
      var dept=deptArray[i];
      if(dept.parent==pid){
	        var node=new OrgNode();
	        node.customParam.deptId=dept.id;
			node.customParam.EmpName=dept.name;
			node.customParam.actualNum=dept.actualNum;
			node.customParam.orderNum=dept.orderNum;
            node.customParam.deptHead=dept.deptHead;
			node.sortId=dept.sortId;
			parentNode.Nodes.Add(node);
			initialNode(dept.id,node);	
	  }
   }
    
}
function cacheDeptList(){
       var dept;
       <s:iterator value="deptList" status="index">
           dept = new Department();
           dept.id='<s:property value="id"/>';
           dept.name='<s:property value="departmentName"/>';
           dept.sortId='<s:property value="departmentSortId"/>';
           dept.parent='<s:property value="departmentParentId.id"/>';
           dept.actualNum='<s:property value="actualNum"/>';
           dept.orderNum='<s:property value="orderNum"/>';
           dept.deptHead='<s:property value="deptHead.empName"/>';
           deptArray.push(dept);
       </s:iterator>  
}
function getRootDept(deptId){
  for(var i=0;i<deptArray.length;i++){
      var dept=deptArray[i];
      if(dept.id==deptId){
        return dept;
      }
      }
}

function calTierNodes(root){
  var nodesArray=root.Nodes;
  if(nodesArray==null || nodesArray.length<1){ 
    return;
  }else{
   for(var i=0;i<nodesArray.length;i++){
      index++;
      var node=nodesArray[i];
      if(tierArray[index]==undefined) tierArray[index]=0;
      tierArray[index]=tierArray[index]+1;
      calTierNodes(node);
      index--;
   }
  }
}

function initialWeightValue(root){
  var nodesArray=root.Nodes;
  if(nodesArray !=null &&  nodesArray.length>0){
   for(var i=0;i<nodesArray.length;i++){
      var node=nodesArray[i];
      initialWeightValue(node);
   }
  }else{
  root.weightValue=1;
  }
  if(root.parentNode !=null){
      root.parentNode.weightValue=root.parentNode.weightValue+root.weightValue;
    }
}

function showPosition(ele){
  var position=model.simple.getElementPosition(ele);//当前元素位置
 
  var windowWidth=window.screen.width;
  var div=$("#positionDiv");
  var div_width=div.width();
  var div_left;
  if(position.left+70+div_width<windowWidth){
     div_left=position.left+70;
  }else{
     div_left=position.left-div_width+10;
  }
  var div_top=position.top;
  div.css("left",div_left+"px");
  div.css("top",div_top+"px");
  div.css("position","absolute");
  div.css("zIndex","1000");
  var queryDate=$('#queryDate').val();
  if(typeof queryDate=="undefined" || queryDate=='') {
     queryDate=null;
  }
  var deptId=ele.id.trim();
  OrgMapAction.getPbsByDept(deptId,queryDate,callback);
  function callback(pbArray){
    var table=$('#positionTable');
    var templetHtml='<tr><td>{positionName}</td></tr>';
    table.find("tr:gt(0)").remove();
    if(pbArray.length>0){ 
	    for(var i=0;i<pbArray.length;i++){
	    var pb=pbArray[i];
	    var newTr=templetHtml.replace("{positionName}", pb.pbName);
	    table.append(newTr);
	       }   
		}else{
		  var newTr=templetHtml.replace("{positionName}", '无');
	      table.append(newTr);
	    }
	  
	div.show(); //显示div
	}
  
}
function hidePosition(){
var positionDiv=$('#positionDiv');
var table=$('#positionTable');
table.find("tr:gt(0)").remove();
positionDiv.hide();

}
 
//end declare
 
var deptArray=new Array();
cacheDeptList();
var queryDeptId='<s:property value="queryDeptId"/>';
var root=new OrgNode();
var deptRoot = new Department();
deptRoot=getRootDept(queryDeptId.trim());
root.customParam.deptId=deptRoot.id;
root.customParam.EmpName=deptRoot.name;
root.customParam.actualNum=deptRoot.actualNum;
root.customParam.orderNum=deptRoot.orderNum;
root.customParam.deptHead=deptRoot.deptHead;
//初始化树
initialNode(queryDeptId.trim(),root);
initialWeightValue(root);
//保存虚拟树最大宽度
var maxTierNodes=root.weightValue;
var OrgShows=new OrgShow(root);
OrgShows.Top=160;
OrgShows.Left=180;
var showMaxWidth=window.screen.width-OrgShows.Left-90;

var intervalWidth=(showMaxWidth-80*maxTierNodes)/(maxTierNodes-1);
if(intervalWidth<9) intervalWidth=9;

OrgShows.IntervalWidth=intervalWidth;
OrgShows.IntervalHeight=16;
if(hrm.common.navigatorIsIE()){
 OrgShows.BoxWidth=90;
 OrgShows.BoxHeight=100;
}
OrgShows.BoxTemplet="<div id=\"{Id}\" class=\"OrgBox\"><div>{EmpName}</div>负责人:{deptHead}<br>编制:{orderNum}<br>在编:{actualNum}<div id=\"{deptId}\" onMouseout=\"hidePosition();\" onclick=\"showPosition(this);\"><span class='view'>查看职位<span></div></div>";
OrgShows.Run();
var containerHeight=90*(OrgShows.Depth)+OrgShows.IntervalHeight*(OrgShows.Depth-1);

$('#container').height(containerHeight-150);
//end show
</script>
</body>	
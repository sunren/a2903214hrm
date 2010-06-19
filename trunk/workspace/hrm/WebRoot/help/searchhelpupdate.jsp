<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="ww" uri="webwork"%>
<html>
<head>
<title>HRM在线帮助系统</title>
<link href="images/help_style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<ww:url value="js/base.js"/>"></script>
<script type="text/javascript" src="<ww:url value="../resource/js/prototype.js"/>?ver=1.5"></script>
<script type="text/javascript" src="<ww:url value="../resource/js/util.js"/>"></script>


</head>

<body>
<div id=help_top>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="22%"><img src="images/logo.gif" /></td>
      <td width="4%"><img src="images/line.gif" width="38" height="62" /></td>
      <td width="74%" align="right" bgcolor="#ebf4fa"><img src="images/title.gif" width="278" height="31" /></td>
    </tr>
  </table>
</div>
<div id=helpmain>
	<ww:hidden id="tmpBrief" name="tmpBrief"/>
	<ww:if test="!classList.isEmpty()">
		<div id=help_left>	
			<dl id="classbody" class="firstmenu">
				<ww:iterator value="classList" status="index1">
					<ww:if test="hcFather!=null && hcFather.id>0">
						<script>
							
							<ww:if test="classId==id">
								$('ulClass<ww:property value="hcFather.id"/>').innerHTML+="<li id='thissecondmenu'><a href='searchHelpAdmin.action?classId=<ww:property value="id"/>'><ww:property value="hcClassName"/></a></li>";
								$('ulClass<ww:property value="hcFather.id"/>').style.display='block';
								$('tmpBrief').value="<ww:property value="hcBrief"/>";
								$('ulHref<ww:property value="hcFather.id"/>').style.backgroundPosition="left -46px";
								$('ulHref<ww:property value="hcFather.id"/>').style.color="#FFFFFF";
							</ww:if>
							<ww:else>
								$('ulClass<ww:property value="hcFather.id"/>').innerHTML+="<li><a href='searchHelpAdmin.action?classId=<ww:property value="id"/>'><ww:property value="hcClassName"/></a></li>";
							</ww:else>
						</script>
					</ww:if>
					<ww:else>
						<dt><a href="#" id="ulHref<ww:property value="id"/>" onclick="showClassUl($('ulClass<ww:property value="id"/>'),this)"><ww:property value="hcClassName"/></a></dt>
						<ul id="ulClass<ww:property value="id"/>" class="secondmenu" style="display:none"></ul>
					</ww:else>
				</ww:iterator>
			</dl>
		</div>
	</ww:if>		
	<ww:form name="updateHelp" action="searchHelpUpdate.action" namespace="/help" method="POST" enctype="multipart/form-data">
	<ww:hidden id="classId" name="classId"/>
	<ww:hidden id="operation" name="operation" value="update"/>
	<ww:set name="helpid" value="0"/>
	<div id=help_right>
		<p align=right><input name="button" type="submit" value="保存当前帮助信息" onclick="checksubmit();"/><br><br></p>
		<p class="info"><strong>概述：</strong><ww:textarea id="classBrief" name="classBrief" rows="3" cols="80"/></p>
		<div id=helpBody>
		<ww:if test="!helpList.isEmpty()">
			<ww:iterator value="helpList" status="index">
				<div class="graph">
					<ww:hidden id="id" name="id"/>
					<h4><ww:property value="#helpid+1"/>标题：<ww:textfield name="helpTitle" size="60" maxlength="64"/> </h4>
					<p>描述：
					<input type="hidden" id="desc<ww:property value="#helpid"/>" name="helpDesc" value="<ww:property value="helpDesc"/>"/><ww:richtexteditor 	toolbarCanCollapse="false"	height="150" width="500" id="%{'helpDescEditor'+#helpid}"	name="%{'helpDescEditor'+#helpid}" 	value=""		defaultLanguage="zh-cn"
		/>
			<script>
	var descObj=$('desc'+<ww:property value="#helpid"/>);
	var result=descObj.value;
     $('helpDescEditor'+<ww:property value="#helpid"/>).value = result;
	
</script>		
					</p>
				</div>
			<ww:set name="helpid" value="#helpid+1"/>			
			</ww:iterator>
		</ww:if>
		<ww:if test="#helpid<20">
		<ww:set name="otherids" value="20-#helpid"/>	
			<ww:bean name="org.apache.struts2.util.Counter" id="rowcounter">
	  		<ww:param name="first" value="1"/><ww:param name="last" value="otherids"/>
			</ww:bean>
			<ww:iterator value="#rowcounter" status="index">
			<div class="graph" ><ww:hidden id="id" name="id"/>
				<h4><ww:property value="#helpid+1"/>标题：<ww:textfield name="helpTitle" size="60" maxlength="64"/> </h4>
					<p>描述：<ww:hidden name="helpDesc"/>
						<ww:richtexteditor 	toolbarCanCollapse="false"	height="150" width="500" id="%{'helpDescEditor'+#helpid}"	name="%{'helpDescEditor'+#helpid}" 	value=""		defaultLanguage="zh-cn"
		/>
					</p>
			</div>
			<ww:set name="helpid" value="#helpid+1"/>
			</ww:iterator>
		</ww:if>	
		</div>
	</div>
	</ww:form>
</div>
<div id="help_foot">人力资源管理系统版权所有&copy;1997-2007</div>

<script>
$('classBrief').value=$('tmpBrief').value;
function getEditorHTMLContents(EditorName) {
    var oEditor = FCKeditorAPI.GetInstance(EditorName);
    return(oEditor.GetXHTML(true));
}
// 获取编辑器中文字内容
function getEditorTextContents(EditorName) {
    var oEditor = FCKeditorAPI.GetInstance(EditorName);
    return(oEditor.EditorDocument.body.innerHTML);
}
// 设置编辑器中内容
function SetEditorContents(EditorName, ContentStr) {
    var oEditor = FCKeditorAPI.GetInstance(EditorName) ;
    oEditor.SetHTML(ContentStr) ;
}

 function checksubmit(){
	var descObj=document.getElementsByName('helpDesc');
	//alert(descObj.length);
	for(i=0;i<descObj.length;i++){
		descObj[i].value=getEditorHTMLContents('helpDescEditor'+i);

	}
 }
</script>
</body>
</html>

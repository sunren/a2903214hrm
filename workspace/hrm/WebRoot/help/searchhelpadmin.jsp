<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="ww" uri="webwork"%>
<html>
<head>
<title>HRM在线帮助系统</title>
<link href="images/help_style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<ww:url value="js/base.js"/>"></script>
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
	<ww:hidden id="classBrief" name="classBrief"/>
	<ww:hidden id="classId" name="classId"/>
	<ww:if test="!classList.isEmpty()">
		<div id=help_left>	
			<dl id="classbody" class="firstmenu">
				<ww:iterator value="classList" status="index">
					<ww:if test="hcFather!=null && hcFather.id>0">
						<input type="hidden" id="hcBrief<ww:property value="id"/>" value="<ww:property value="hcBrief"/>"/>
						<script>
							
							<ww:if test="classId==id">
								document.getElementById('ulClass<ww:property value="hcFather.id"/>').innerHTML+="<li id='thissecondmenu'><a href='searchHelpAdmin.action?classId=<ww:property value="id"/>'><ww:property value="hcClassName"/></a></li>";
								document.getElementById('ulClass<ww:property value="hcFather.id"/>').style.display='block';
								document.getElementById('classBrief').value=document.getElementById('hcBrief<ww:property value="id"/>').value;
								document.getElementById('ulHref<ww:property value="hcFather.id"/>').style.backgroundPosition="left -46px";
								document.getElementById('ulHref<ww:property value="hcFather.id"/>').style.color="#FFFFFF";
								var son_menuvalue='<ww:property value="hcClassName"/>';
								var ida='<ww:property value="hcFather.id"/>';
							</ww:if>
							<ww:else>
							 	var idb='<ww:property value="hcFather.id"/>';
								document.getElementById('ulClass<ww:property value="hcFather.id"/>').innerHTML+="<li><a href='searchHelpAdmin.action?classId=<ww:property value="id"/>'><ww:property value="hcClassName"/></a></li>";
							</ww:else>
						</script>
					</ww:if>
					<ww:else>
						<dt><a href="#" id="ulHref<ww:property value="id"/>" onclick="showClassUl(document.getElementById('ulClass<ww:property value="id"/>'),this);showFatherName(<ww:property value="id"/>)"><ww:property value="hcClassName"/></a></dt>
						<ul id="ulClass<ww:property value="id"/>" class="secondmenu" style="display:none" ></ul>
					</ww:else>
				</ww:iterator>
			</dl>
		</div>
	</ww:if>		
	<div id=help_right>
	<p align=right><input type="button" value="修改当前帮助信息" onclick="window.location.href='searchHelpUpdate.action?classId=<ww:property value="classId"/>';">	<br><br></p>
		<h5>HRM 》<span id="father_menu"></span>》</h5>
		<h1 id="son_menu">我的资料：</h1><br>
		<p class="info"><strong>概述：</strong><span id="briefBody"></span></p>
			<ww:if test="!helpList.isEmpty()">
			<ww:iterator value="helpList" status="index">
				<div class="graph">
					<h4><ww:property value="helpTitle"/>: </h4>
					<p><ww:property value="helpDesc" escape="false"/></p>
				</div>			
			</ww:iterator>
		</ww:if>
	</div>
</div>
<div id="help_foot">人力资源管理系统版权所有&copy;1997-2007</div>
<script>
 var tmpbrief=document.getElementById('classBrief').value;
 if(tmpbrief!=''){
	 document.getElementById('briefBody').innerHTML=tmpbrief;
 }
 document.getElementById('son_menu').innerHTML=son_menuvalue;
 if(ida!=undefined)
 showFatherName(ida);
 else
 showFatherName(idb);
</script>
</body>
</html>

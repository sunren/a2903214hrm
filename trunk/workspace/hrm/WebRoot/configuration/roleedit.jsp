<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<html>
<head>
<title><s:text name="button.base.change" /><s:text name="desc.security.role" /></title>
</head>
<body onload="initId();">
<s:component template="bodyhead">
	<s:param name="pagetitle"
		value="getText('button.base.change')+getText('desc.security.role')" />
</s:component>
<form name="updateForm" id= "updateForm" action="roleUpdate.action" method="post" namespace="/security">
<s:token/>
	<s:hidden name="role.id" id= "role.id"/>
	<s:hidden name="role.roleAuthority" id= "role.roleAuthority"/>
	<table width="100%" >
		<tr>
<s:textfield label="%{getText('desc.security.rolename')}" id="role.roleName" name="role.roleName" maxlength="32"  required="true"/>
		    <s:textfield name="role.roleSortId" id ="sortId" label="角色顺序" maxlength="3" required="true"/>
<td><input type="button" class ="button2" onclick ="checkAuths();" value="<s:text name="button.base.submit"/>"></td>
		</tr>
<tr ><td align ="right">
角色描述:
</td><td colspan="4">
<s:textfield size ="60%" name="role.roleDesc" maxlength="128" />
		   </td></tr>
	</table>
	<br />
	<s:bean name="org.apache.struts2.util.Counter" id="rowcounter">
	  		<s:param name="first" value="1"/><s:param name="last" value="10"/>
	</s:bean>

	<table cellpadding="0" cellspacing="0" width="100%" class ="gridtableList">
		<tr>
<th class="listViewThS1">权限描述</th>
<th class="listViewThS1">条件</th>
      </tr>
<!-- 显示员工部分  -->
		<s:if test = "!authList1.isEmpty()">
		 <td class="tablefield tda" colspan="3">员工&nbsp;</td>
		 <s:iterator value="authList1"  status="status">
		 <s:if test ="authorityActionNo=='no'">
			<tr>
				<td bgcolor = "gray" class="tda ">&nbsp;
				<input type="checkbox" class="checkbox" name="authId" value="<s:property value="id"/>"  disabled ="true" id="authId-5"/>
				<s:property value="authorityDesc" />
				</td>										
				<s:if test ="authorityConditionNo == 0">
				<td bgcolor = "gray" class="tda">无&nbsp;</td>
				</s:if>	
				<s:else>
				<td bgcolor = "gray" class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>	
			</tr>
			</s:if>
			<s:else>
			 <tr>
				<td class="tda ">&nbsp;
				<input type="checkbox" class="checkbox" name="authId" value="<s:property value="id"/>" id="authId-5"/>
				<s:property value="authorityDesc" />
				</td>		
				<s:if test ="authorityConditionNo == 0">
				<td  class="tda">无&nbsp;</td>
				</s:if>	
				<s:else>
				<td  class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>
			 </tr>
			</s:else>
		 </s:iterator>
		</s:if>
		
		
		<!-- 显示薪资部分  -->
		<s:if test = "!authList2.isEmpty()">
		 <td class="tablefield tda" colspan="3">薪资&nbsp;</td>
		 <s:iterator value="authList2"  status="status">
		 <s:if test ="authorityActionNo=='no'">
			<tr>
				<td bgcolor = "gray" class="tda ">&nbsp;
				<input type="checkbox" class="checkbox" name="authId" value="<s:property value="id"/>" id="authId-5" disabled ="true"/>
				<s:property value="authorityDesc" />
				</td>									
				<s:if test ="authorityConditionNo == 0">
				<td bgcolor = "gray" class="tda">无&nbsp;</td>
				</s:if>	
				<s:else>
				<td bgcolor = "gray" class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>				
			</tr>
			</s:if>
			<s:else>
			 <tr>
				<td  class="tda ">&nbsp;
				<input type="checkbox" class="checkbox" name="authId" value="<s:property value="id"/>" id="authId-5"/>
				<s:property value="authorityDesc" />
				</td>										
				<s:if test ="authorityConditionNo == 0">
				<td  class="tda">无&nbsp;</td>
				</s:if>	
				<s:else>
				<td  class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>
			 </tr>
			</s:else>
		 </s:iterator>
		</s:if>
		
		<!-- 显示培训部分  -->
		<s:if test = "!authList3.isEmpty()">
		 <td class="tablefield tda" colspan="3">培训&nbsp;</td>
		 <s:iterator value="authList3"  status="status">
		 <s:if test ="authorityActionNo=='no'">
			<tr>
				<td bgcolor = "gray" class="tda ">&nbsp;
				<input type="checkbox" class="checkbox" name="authId" value="<s:property value="id"/>" id="authId-5" disabled ="true"/>
				<s:property value="authorityDesc" />
				</td>									
				<s:if test ="authorityConditionNo == 0">
				<td bgcolor = "gray" class="tda">无&nbsp;</td>
				</s:if>	
				<s:else>
				<td bgcolor = "gray" class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>					
				
			</tr>
			</s:if>
			<s:else>
			 <tr>
				<td  class="tda ">&nbsp;
				<input type="checkbox" class="checkbox" name="authId" value="<s:property value="id"/>" id="authId-5"/>
				<s:property value="authorityDesc" />
				</td>									
				<s:if test ="authorityConditionNo == 0">
				<td  class="tda">无&nbsp;</td>
				</s:if>	
				<s:else>
				<td  class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>
			 </tr>
			</s:else>
		 </s:iterator>
		</s:if>
		
		<!-- 显示考勤部分  -->
		<s:if test = "!authList4.isEmpty()">
		 <td class="tablefield tda" colspan="3">考勤&nbsp;</td>
		 <s:iterator value="authList4"  status="status">
		 <s:if test ="authorityActionNo=='no'">
			<tr>
				<td bgcolor = "gray" class="tda ">&nbsp;
				<input type="checkbox" class="checkbox" name="authId" value="<s:property value="id"/>" id="authId-5" disabled ="true"/>
				<s:property value="authorityDesc" />
				</td>		
				<s:if test ="authorityConditionNo == 0">
				<td bgcolor = "gray" class="tda">无&nbsp;</td>
				</s:if>	
				<s:else>
				<td bgcolor = "gray" class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>	
			</tr>
			</s:if>
			<s:else>
			 <tr>
				<td  class="tda ">&nbsp;
				<input type="checkbox" class="checkbox" name="authId" value="<s:property value="id"/>" id="authId-5"/>
				<s:property value="authorityDesc" />
				</td>		
				<s:if test ="authorityConditionNo == 0">
				<td  class="tda">无&nbsp;</td>
				</s:if>	
				<s:else>
				<td  class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>
			 </tr>
			</s:else>
		 </s:iterator>
		</s:if>
		
		<!-- 显示绩效部分  -->
		<s:if test = "!authList5.isEmpty()">
		 <td class="tablefield tda" colspan="3">绩效&nbsp;</td>
		 <s:iterator value="authList5"  status="status">
		 <s:if test ="authorityActionNo=='no'">
			<tr>
				<td bgcolor = "gray" class="tda ">&nbsp;
				<input type="checkbox" class="checkbox" name="authId" value="<s:property value="id"/>" id="authId-5" disabled ="true"/>
				<s:property value="authorityDesc" />
				</td>		
				<s:if test ="authorityConditionNo == 0">
				<td bgcolor = "gray" class="tda">无&nbsp;</td>
				</s:if>	
				<s:else>
				<td bgcolor = "gray" class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>	
			</tr>
			</s:if>
			<s:else>
			 <tr>
				<td  class="tda ">&nbsp;
				<input type="checkbox" class="checkbox" name="authId" value="<s:property value="id"/>" id="authId-5"/>
				<s:property value="authorityDesc" />
				</td>		
				<s:if test ="authorityConditionNo == 0">
				<td  class="tda">无&nbsp;</td>
				</s:if>	
				<s:else>
				<td  class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>
			 </tr>
			</s:else>
		 </s:iterator>
		</s:if>
		
		<!-- 显示招聘部分  -->
		<s:if test = "!authList6.isEmpty()">
		 <td class="tablefield tda" colspan="3">招聘&nbsp;</td>
		 <s:iterator value="authList6"  status="status">
		 <s:if test ="authorityActionNo=='no'">
			<tr>
				<td bgcolor = "gray" class="tda ">&nbsp;
				<input type="checkbox" class="checkbox" name="authId" value="<s:property value="id"/>" id="authId-5" disabled ="true"/>
				<s:property value="authorityDesc" />
				</td>		
				<s:if test ="authorityConditionNo == 0">
				<td bgcolor = "gray" class="tda">无&nbsp;</td>
				</s:if>	
				<s:else>
				<td bgcolor = "gray" class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>	
			</tr>
			</s:if>
			<s:else>
			 <tr>
				<td  class="tda ">&nbsp;
				<input type="checkbox" class="checkbox" name="authId" value="<s:property value="id"/>" id="authId-5"/>
				<s:property value="authorityDesc" />
				</td>		
				<s:if test ="authorityConditionNo == 0">
				<td  class="tda">无&nbsp;</td>
				</s:if>	
				<s:else>
				<td  class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>
			 </tr>
			</s:else>
		 </s:iterator>
		</s:if>
		
		<!-- 显示报表部分  -->
		<s:if test = "!authList7.isEmpty()">
		 <td class="tablefield tda" colspan="3">报表&nbsp;</td>
		 <s:iterator value="authList7"  status="status">
		 <s:if test ="authorityActionNo=='no'">
			<tr>
				<td bgcolor = "gray" class="tda ">&nbsp;
				<input type="checkbox" class="checkbox" name="authId" value="<s:property value="id"/>" id="authId-5" disabled ="true"/>
				<s:property value="authorityDesc" />
				</td>		
				<s:if test ="authorityConditionNo == 0">
				<td bgcolor = "gray" class="tda">无&nbsp;</td>
				</s:if>	
				<s:else>
				<td bgcolor = "gray" class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>	
			</tr>
			</s:if>
			<s:else>
			 <tr>
				<td  class="tda ">&nbsp;
				<input type="checkbox" class="checkbox" name="authId" value="<s:property value="id"/>" id="authId-5"/>
				<s:property value="authorityDesc" />
				</td>		
				<s:if test ="authorityConditionNo == 0">
				<td  class="tda">无&nbsp;</td>
				</s:if>	
				<s:else>
				<td  class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>
			 </tr>
			</s:else>
		 </s:iterator>
		</s:if>
		
		<!-- 显示新闻部分  -->
		<s:if test = "!authList8.isEmpty()">
		 <td class="tablefield tda" colspan="3">新闻&nbsp;</td>
		 <s:iterator value="authList8"  status="status">
		 <s:if test ="authorityActionNo=='no'">
			<tr>
				<td bgcolor = "gray" class="tda ">&nbsp;
				<input type="checkbox" class="checkbox" name="authId" value="<s:property value="id"/>" id="authId-5" disabled ="true"/>
				<s:property value="authorityDesc" />
				</td>		
				<s:if test ="authorityConditionNo == 0">
				<td bgcolor = "gray" class="tda">无&nbsp;</td>
				</s:if>	
				<s:else>
				<td bgcolor = "gray" class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>	
			</tr>
			</s:if>
			<s:else>
			 <tr>
				<td  class="tda ">&nbsp;
				<input type="checkbox" class="checkbox" name="authId" value="<s:property value="id"/>" id="authId-5"/>
				<s:property value="authorityDesc" />
				</td>		
				<s:if test ="authorityConditionNo == 0">
				<td  class="tda">无&nbsp;</td>
				</s:if>	
				<s:else>
				<td  class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>
			 </tr>
			</s:else>
		 </s:iterator>
		</s:if>
		
		<!-- 显示系统部分  -->
		<s:if test = "!authList9.isEmpty()">
		 <td class="tablefield tda" colspan="3">系统&nbsp;</td>
		 <s:iterator value="authList9"  status="status">
		 <s:if test ="authorityActionNo=='no'">
			<tr>
				<td bgcolor = "gray" class="tda ">&nbsp;
				<input type="checkbox" class="checkbox" name="authId" value="<s:property value="id"/>" id="authId-5" disabled ="true"/>
				<s:property value="authorityDesc" />
				</td>		
				<s:if test ="authorityConditionNo == 0">
				<td bgcolor = "gray" class="tda">无&nbsp;</td>
				</s:if>	
				<s:else>
				<td bgcolor = "gray" class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>	
			</tr>
			</s:if>
			<s:else>
			 <tr>
				<td  class="tda ">&nbsp;
				<input type="checkbox" class="checkbox" name="authId" value="<s:property value="id"/>" id="authId-5"/>
				<s:property value="authorityDesc" />
				</td>		
				<s:if test ="authorityConditionNo == 0">
				<td  class="tda">无&nbsp;</td>
				</s:if>	
				<s:else>
				<td  class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>			
			 </tr>
			</s:else>
			</s:iterator>
		</s:if>
	</table>
	<br>
	<span><font color ="red">*</font> 深灰色表示尚未开通的功能！</span>
</form>
<script type="text/javascript" language="javascript">
// 初始化该角色已经有的权限；
function initId(){
    var oldIdN = document.getElementById("role.roleAuthority").value.split(",");
	var idN = document.getElementsByName('authId');
	for(var j = 0;j < oldIdN.length;j++){
	  for(var i = 0;i < idN.length;i++){
		 if(idN[i].value == oldIdN[j]){idN[i].checked = true;}
	  }
	}
}

// 提交修改前对权限设置进行检查；
function checkAuths(){
	var roleName = document.getElementById("role.roleName").value;
	if(roleName.trim()==''){
		alert("请填写角色名称！");
		return;
	}
	var allRoles = document.getElementsByName('authId');
	var sortId = $('#sortId').val();
	if(!hrm.common.isHrmNumber(sortId)){
		alert("角色顺序必须添数字！");
		return ;
	}
	var flag =-1;
    for(i=0;i<allRoles.length;i++)
        if(allRoles[i].checked == true)flag=i;
	if(flag ==-1)
	    alert ("必须为它赋一个权限！");
	else 
		document.getElementById('updateForm').submit();
}
</script>
</body>
</html>

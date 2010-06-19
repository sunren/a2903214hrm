<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<html>
<head>
<title><s:text name="desc.add" /><s:text name="desc.security.role" /></title>
</head>
<body>
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'增加角色'" />
	<s:param name="helpUrl" value="'8'" />
</s:component>
<form name="addForm" id ="addForm" action="addRole.action" method="post" namespace="/security">
<s:token/>
	<s:hidden name="role.id" />
	
	<table width="100%" >
		<tr>
			<s:textfield label="%{getText('desc.security.rolename')}" id="role.roleName" name="role.roleName" maxlength="32"  required="true"/>
		    <s:textfield name="role.roleSortId" id = "sortId" label="角色顺序" maxlength="3" required="true"/>
			<td><input type="button" class ="button2" onclick ="checkAuths();" value="<s:text name="button.base.submit"/>"></td>
		</tr>
			<tr ><td align ="right">
			角色描述:
			</td><td colspan="3">
			<s:textfield size ="60%" name="role.roleDesc" maxlength="128" />
		   </td>
		   <td><input type="button" class ="button2" onclick ="returnBack();" value="<s:text name="button.base.cancel"/>"></td>
		   </tr>
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
				<td  bgcolor = "gray" class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
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
				<td  bgcolor = "gray" class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
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
				<td  bgcolor = "gray" class="tda">无&nbsp;</td>
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
		alert("角色顺序必须填数字！");
		return ;
	}
	var flag = -1;
    for(i=0;i<allRoles.length;i++){
        if(allRoles[i].checked == true)flag=i;
    }
	if(flag ==-1)
		alert("必须为它赋一个权限！");
	else 
		document.getElementById('addForm').submit();
}

function returnBack (){
 	window.location="roleDel.action";	
}
</script>

</body>
</html>

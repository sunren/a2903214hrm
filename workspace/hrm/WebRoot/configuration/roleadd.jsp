<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<html>
<head>
<title><s:text name="desc.add" /><s:text name="desc.security.role" /></title>
</head>
<body>
<s:component template="bodyhead">
	<s:param name="pagetitle" value="'���ӽ�ɫ'" />
	<s:param name="helpUrl" value="'8'" />
</s:component>
<form name="addForm" id ="addForm" action="addRole.action" method="post" namespace="/security">
<s:token/>
	<s:hidden name="role.id" />
	
	<table width="100%" >
		<tr>
			<s:textfield label="%{getText('desc.security.rolename')}" id="role.roleName" name="role.roleName" maxlength="32"  required="true"/>
		    <s:textfield name="role.roleSortId" id = "sortId" label="��ɫ˳��" maxlength="3" required="true"/>
			<td><input type="button" class ="button2" onclick ="checkAuths();" value="<s:text name="button.base.submit"/>"></td>
		</tr>
			<tr ><td align ="right">
			��ɫ����:
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
			<th class="listViewThS1">Ȩ������</th>
			<th class="listViewThS1">����</th>
		</tr>
		
		<!-- ��ʾԱ������  -->
		<s:if test = "!authList1.isEmpty()">
		 <td class="tablefield tda" colspan="3">Ա��&nbsp;</td>
		 <s:iterator value="authList1"  status="status">
		 <s:if test ="authorityActionNo=='no'">
			<tr>
				<td bgcolor = "gray" class="tda ">&nbsp;
				<input type="checkbox" class="checkbox" name="authId" value="<s:property value="id"/>"  disabled ="true" id="authId-5"/>
				<s:property value="authorityDesc" />
				</td>										
				<s:if test ="authorityConditionNo == 0">
				<td bgcolor = "gray" class="tda">��&nbsp;</td>
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
				<td  class="tda">��&nbsp;</td>
				</s:if>	
				<s:else>
				<td  class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>			
			 </tr>
			</s:else>
		 </s:iterator>
		</s:if>
		
		
		<!-- ��ʾн�ʲ���  -->
		<s:if test = "!authList2.isEmpty()">
		 <td class="tablefield tda" colspan="3">н��&nbsp;</td>
		 <s:iterator value="authList2"  status="status">
		 <s:if test ="authorityActionNo=='no'">
			<tr>
				<td bgcolor = "gray" class="tda ">&nbsp;
				<input type="checkbox" class="checkbox" name="authId" value="<s:property value="id"/>" id="authId-5" disabled ="true"/>
				<s:property value="authorityDesc" />
				</td>									
				<s:if test ="authorityConditionNo == 0">
				<td bgcolor = "gray" class="tda">��&nbsp;</td>
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
				<td  class="tda">��&nbsp;</td>
				</s:if>	
				<s:else>
				<td  class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>			
			 </tr>
			</s:else>
		 </s:iterator>
		</s:if>
		
		<!-- ��ʾ��ѵ����  -->
		<s:if test = "!authList3.isEmpty()">
		 <td class="tablefield tda" colspan="3">��ѵ&nbsp;</td>
		 <s:iterator value="authList3"  status="status">
		 <s:if test ="authorityActionNo=='no'">
			<tr>
				<td bgcolor = "gray" class="tda ">&nbsp;
				<input type="checkbox" class="checkbox" name="authId" value="<s:property value="id"/>" id="authId-5" disabled ="true"/>
				<s:property value="authorityDesc" />
				</td>									
				<s:if test ="authorityConditionNo == 0">
				<td bgcolor = "gray" class="tda">��&nbsp;</td>
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
				<td  class="tda">��&nbsp;</td>
				</s:if>	
				<s:else>
				<td  class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>			
			 </tr>
			</s:else>
		 </s:iterator>
		</s:if>
		
		<!-- ��ʾ���ڲ���  -->
		<s:if test = "!authList4.isEmpty()">
		 <td class="tablefield tda" colspan="3">����&nbsp;</td>
		 <s:iterator value="authList4"  status="status">
		 <s:if test ="authorityActionNo=='no'">
			<tr>
				<td bgcolor = "gray" class="tda ">&nbsp;
				<input type="checkbox" class="checkbox" name="authId" value="<s:property value="id"/>" id="authId-5" disabled ="true"/>
				<s:property value="authorityDesc" />
				</td>		
				<s:if test ="authorityConditionNo == 0">
				<td bgcolor = "gray" class="tda">��&nbsp;</td>
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
				<td  class="tda">��&nbsp;</td>
				</s:if>	
				<s:else>
				<td  class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>			
			 </tr>
			</s:else>
		 </s:iterator>
		</s:if>
		
		<!-- ��ʾ��Ч����  -->
		<s:if test = "!authList5.isEmpty()">
		 <td class="tablefield tda" colspan="3">��Ч&nbsp;</td>
		 <s:iterator value="authList5"  status="status">
		 <s:if test ="authorityActionNo=='no'">
			<tr>
				<td bgcolor = "gray" class="tda ">&nbsp;
				<input type="checkbox" class="checkbox" name="authId" value="<s:property value="id"/>" id="authId-5" disabled ="true"/>
				<s:property value="authorityDesc" />
				</td>		
				<s:if test ="authorityConditionNo == 0">
				<td bgcolor = "gray" class="tda">��&nbsp;</td>
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
				<td  class="tda">��&nbsp;</td>
				</s:if>	
				<s:else>
				<td  class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>			
			 </tr>
			</s:else>
		 </s:iterator>
		</s:if>
		
		<!-- ��ʾ��Ƹ����  -->
		<s:if test = "!authList6.isEmpty()">
		 <td class="tablefield tda" colspan="3">��Ƹ&nbsp;</td>
		 <s:iterator value="authList6"  status="status">
		 <s:if test ="authorityActionNo=='no'">
			<tr>
				<td bgcolor = "gray" class="tda ">&nbsp;
				<input type="checkbox" class="checkbox" name="authId" value="<s:property value="id"/>" id="authId-5" disabled ="true"/>
				<s:property value="authorityDesc" />
				</td>		
				<s:if test ="authorityConditionNo == 0">
				<td  bgcolor = "gray" class="tda">��&nbsp;</td>
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
				<td  class="tda">��&nbsp;</td>
				</s:if>	
				<s:else>
				<td  class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>			
			 </tr>
			</s:else>
		 </s:iterator>
		</s:if>
		
		<!-- ��ʾ������  -->
		<s:if test = "!authList7.isEmpty()">
		 <td class="tablefield tda" colspan="3">����&nbsp;</td>
		 <s:iterator value="authList7"  status="status">
		 <s:if test ="authorityActionNo=='no'">
			<tr>
				<td bgcolor = "gray" class="tda ">&nbsp;
				<input type="checkbox" class="checkbox" name="authId" value="<s:property value="id"/>" id="authId-5" disabled ="true"/>
				<s:property value="authorityDesc" />
				</td>		
				<s:if test ="authorityConditionNo == 0">
				<td bgcolor = "gray" class="tda">��&nbsp;</td>
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
				<td  class="tda">��&nbsp;</td>
				</s:if>	
				<s:else>
				<td  class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>			
			 </tr>
			</s:else>
		 </s:iterator>
		</s:if>
		
		<!-- ��ʾ���Ų���  -->
		<s:if test = "!authList8.isEmpty()">
		 <td class="tablefield tda" colspan="3">����&nbsp;</td>
		 <s:iterator value="authList8"  status="status">
		 <s:if test ="authorityActionNo=='no'">
			<tr>
				<td bgcolor = "gray" class="tda ">&nbsp;
				<input type="checkbox" class="checkbox" name="authId" value="<s:property value="id"/>" id="authId-5" disabled ="true"/>
				<s:property value="authorityDesc" />
				</td>		
				<s:if test ="authorityConditionNo == 0">
				<td bgcolor = "gray" class="tda">��&nbsp;</td>
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
				<td  class="tda">��&nbsp;</td>
				</s:if>	
				<s:else>
				<td  class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>			
			 </tr>
			</s:else>
		 </s:iterator>
		</s:if>
		
		<!-- ��ʾϵͳ����  -->
		<s:if test = "!authList9.isEmpty()">
		 <td class="tablefield tda" colspan="3">ϵͳ&nbsp;</td>
		 <s:iterator value="authList9"  status="status">
		 <s:if test ="authorityActionNo=='no'">
			<tr>
				<td bgcolor = "gray" class="tda ">&nbsp;
				<input type="checkbox" class="checkbox" name="authId" value="<s:property value="id"/>" id="authId-5" disabled ="true"/>
				<s:property value="authorityDesc" />
				</td>		
				<s:if test ="authorityConditionNo == 0">
				<td bgcolor = "gray" class="tda">��&nbsp;</td>
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
				<td  class="tda">��&nbsp;</td>
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
	<span><font color ="red">*</font> ���ɫ��ʾ��δ��ͨ�Ĺ��ܣ�</span>
</form>
<script type="text/javascript" language="javascript">
// �ύ�޸�ǰ��Ȩ�����ý��м�飻
function checkAuths(){
	var roleName = document.getElementById("role.roleName").value;
	if(roleName.trim()==''){
		alert("����д��ɫ���ƣ�");
		return;
	}
	var allRoles = document.getElementsByName('authId');
	var sortId = $('#sortId').val();
	if(!hrm.common.isHrmNumber(sortId)){
		alert("��ɫ˳����������֣�");
		return ;
	}
	var flag = -1;
    for(i=0;i<allRoles.length;i++){
        if(allRoles[i].checked == true)flag=i;
    }
	if(flag ==-1)
		alert("����Ϊ����һ��Ȩ�ޣ�");
	else 
		document.getElementById('addForm').submit();
}

function returnBack (){
 	window.location="roleDel.action";	
}
</script>

</body>
</html>

<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="webwork"%>
<html>
<head>
<title><s:text name="desc.security.auth" /><s:text name="desc.security.list" /></title>
</head>
<body>
<s:component template="bodyhead">
	<s:param name="pagetitle"
		value="getText('desc.security.auth')+getText('desc.security.list')" />
	<s:param name="helpUrl" value="'9'" />
</s:component>

<s:bean name="org.apache.struts2.util.Counter" id="rowcounter">
  		<s:param name="first" value="1"/><s:param name="last" value="10"/>
</s:bean>

<table name="authTable" cellpadding="0" cellspacing="0"
		class="gridtableList" width="100%">
		<tr>
			<th><s:text name="desc.security.staymodule" /></th>
			<th><s:text name="desc.security.filtercondition"/></th>			
			<th><s:text name="desc.security.authdesc"/></th>
		</tr>
		
		<!-- ��ʾԱ������  -->
		<s:if test = "!authList1.isEmpty()">
		 <tr><td class="tablefield tda" colspan="3">Ա��&nbsp;</td></tr>
		 <s:iterator value="authList1"  status="status">
		 <s:if test ="authorityActionNo=='no'">
			<tr>
				<td  bgcolor = "gray" class="tda"><s:property value="authorityModuleNo" />&nbsp;</td>								
				<s:if test ="authorityConditionNo == 0">
				<td bgcolor = "gray" class="tda">��&nbsp;</td>
				</s:if>	
				<s:else>
				<td bgcolor = "gray" class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>			
				<td  bgcolor = "gray" class="tda"><s:property value="authorityDesc" />&nbsp;</td>
			</tr>
			</s:if>
			<s:else>
			 <tr>
				<td  class="tda"><s:property value="authorityModuleNo" />&nbsp;</td>								
				<s:if test ="authorityConditionNo == 0">
				<td  class="tda">��&nbsp;</td>
				</s:if>	
				<s:else>
				<td  class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>			
				<td  class="tda"><s:property value="authorityDesc" />&nbsp;</td>
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
				<td  bgcolor = "gray" class="tda"><s:property value="authorityModuleNo" />&nbsp;</td>								
				<s:if test ="authorityConditionNo == 0">
				<td bgcolor = "gray" class="tda">��&nbsp;</td>
				</s:if>	
				<s:else>
				<td bgcolor = "gray" class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>					
				<td  bgcolor = "gray" class="tda"><s:property value="authorityDesc" />&nbsp;</td>
			</tr>
			</s:if>
			<s:else>
			 <tr>
				<td  class="tda"><s:property value="authorityModuleNo" />&nbsp;</td>								
				<s:if test ="authorityConditionNo == 0">
				<td  class="tda">��&nbsp;</td>
				</s:if>	
				<s:else>
				<td  class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>						
				<td  class="tda"><s:property value="authorityDesc" />&nbsp;</td>
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
				<td  bgcolor = "gray" class="tda"><s:property value="authorityModuleNo" />&nbsp;</td>								
				<s:if test ="authorityConditionNo == 0">
				<td bgcolor = "gray" class="tda">��&nbsp;</td>
				</s:if>	
				<s:else>
				<td bgcolor = "gray" class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>	
				<td  bgcolor = "gray" class="tda"><s:property value="authorityDesc" />&nbsp;</td>
			</tr>
			</s:if>
			<s:else>
			 <tr>
				<td  class="tda"><s:property value="authorityModuleNo" />&nbsp;</td>								
				<s:if test ="authorityConditionNo == 0">
				<td  class="tda">��&nbsp;</td>
				</s:if>	
				<s:else>
				<td  class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>						
				<td  class="tda"><s:property value="authorityDesc" />&nbsp;</td>
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
				<td  bgcolor = "gray" class="tda"><s:property value="authorityModuleNo" />&nbsp;</td>								
				<s:if test ="authorityConditionNo == 0">
				<td bgcolor = "gray" class="tda">��&nbsp;</td>
				</s:if>	
				<s:else>
				<td bgcolor = "gray" class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>	
				<td  bgcolor = "gray" class="tda"><s:property value="authorityDesc" />&nbsp;</td>
			</tr>
			</s:if>
			<s:else>
			 <tr>
				<td  class="tda"><s:property value="authorityModuleNo" />&nbsp;</td>								
				<s:if test ="authorityConditionNo == 0">
				<td  class="tda">��&nbsp;</td>
				</s:if>	
				<s:else>
				<td  class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>				
				<td  class="tda"><s:property value="authorityDesc" />&nbsp;</td>
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
				<td  bgcolor = "gray" class="tda"><s:property value="authorityModuleNo" />&nbsp;</td>								
				<s:if test ="authorityConditionNo == 0">
				<td bgcolor = "gray" class="tda">��&nbsp;</td>
				</s:if>	
				<s:else>
				<td bgcolor = "gray" class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>	
				<td  bgcolor = "gray" class="tda"><s:property value="authorityDesc" />&nbsp;</td>
			</tr>
			</s:if>
			<s:else>
			 <tr>
				<td  class="tda"><s:property value="authorityModuleNo" />&nbsp;</td>								
				<s:if test ="authorityConditionNo == 0">
				<td  class="tda">��&nbsp;</td>
				</s:if>	
				<s:else>
				<td  class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>			
				<td  class="tda"><s:property value="authorityDesc" />&nbsp;</td>
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
				<td  bgcolor = "gray" class="tda"><s:property value="authorityModuleNo" />&nbsp;</td>								
				<s:if test ="authorityConditionNo == 0">
				<td bgcolor = "gray" class="tda">��&nbsp;</td>
				</s:if>	
				<s:else>
				<td bgcolor = "gray" class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>	
				<td  bgcolor = "gray" class="tda"><s:property value="authorityDesc" />&nbsp;</td>
			</tr>
			</s:if>
			<s:else>
			 <tr>
				<td  class="tda"><s:property value="authorityModuleNo" />&nbsp;</td>								
				<s:if test ="authorityConditionNo == 0">
				<td  class="tda">��&nbsp;</td>
				</s:if>	
				<s:else>
				<td  class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>					
				<td  class="tda"><s:property value="authorityDesc" />&nbsp;</td>
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
				<td  bgcolor = "gray" class="tda"><s:property value="authorityModuleNo" />&nbsp;</td>								
				<s:if test ="authorityConditionNo == 0">
				<td bgcolor = "gray" class="tda">��&nbsp;</td>
				</s:if>	
				<s:else>
				<td bgcolor = "gray" class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>	
				<td  bgcolor = "gray" class="tda"><s:property value="authorityDesc" />&nbsp;</td>
			</tr>
			</s:if>
			<s:else>
			 <tr>
				<td  class="tda"><s:property value="authorityModuleNo" />&nbsp;</td>								
				<s:if test ="authorityConditionNo == 0">
				<td  class="tda">��&nbsp;</td>
				</s:if>	
				<s:else>
				<td  class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>				
				<td  class="tda"><s:property value="authorityDesc" />&nbsp;</td>
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
				<td  bgcolor = "gray" class="tda"><s:property value="authorityModuleNo" />&nbsp;</td>								
				<s:if test ="authorityConditionNo == 0">
				<td bgcolor = "gray" class="tda">��&nbsp;</td>
				</s:if>	
				<s:else>
				<td bgcolor = "gray" class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>	
				<td  bgcolor = "gray" class="tda"><s:property value="authorityDesc" />&nbsp;</td>
			</tr>
			</s:if>
			<s:else>
			 <tr>
				<td  class="tda"><s:property value="authorityModuleNo" />&nbsp;</td>								
				<s:if test ="authorityConditionNo == 0">
				<td  class="tda">��&nbsp;</td>
				</s:if>	
				<s:else>
				<td  class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>							
				<td  class="tda"><s:property value="authorityDesc" />&nbsp;</td>
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
				<td  bgcolor = "gray" class="tda"><s:property value="authorityModuleNo" />&nbsp;</td>								
				<s:if test ="authorityConditionNo == 0">
				<td bgcolor = "gray" class="tda">��&nbsp;</td>
				</s:if>	
				<s:else>
				<td bgcolor = "gray" class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>	
				<td  bgcolor = "gray" class="tda"><s:property value="authorityDesc" />&nbsp;</td>
			</tr>
			</s:if>
			<s:else>
			 <tr>
				<td  class="tda"><s:property value="authorityModuleNo" />&nbsp;</td>								
				<s:if test ="authorityConditionNo == 0">
				<td  class="tda">��&nbsp;</td>
				</s:if>	
				<s:else>
				<td  class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>					
				<td  class="tda"><s:property value="authorityDesc" />&nbsp;</td>
			 </tr>
			</s:else>
		 </s:iterator>
		</s:if>
		<tr>
		<td colspan="3">
		<div align="center"><input type="button" value="����" onclick ="returnBack();"></div>
		</td>
		</tr>
	</table>
<br>
	<span><font color ="red">*</font> ���ɫ��ʾ��δ��ͨ�Ĺ��ܣ�</span>
	
	<SCRIPT type="text/javascript">
	function returnBack (){
 	window.location="roleDel.action";	
}
	</SCRIPT>
</body>
</html>
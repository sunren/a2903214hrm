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
		
		<!-- 显示员工部分  -->
		<s:if test = "!authList1.isEmpty()">
		 <tr><td class="tablefield tda" colspan="3">员工&nbsp;</td></tr>
		 <s:iterator value="authList1"  status="status">
		 <s:if test ="authorityActionNo=='no'">
			<tr>
				<td  bgcolor = "gray" class="tda"><s:property value="authorityModuleNo" />&nbsp;</td>								
				<s:if test ="authorityConditionNo == 0">
				<td bgcolor = "gray" class="tda">无&nbsp;</td>
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
				<td  class="tda">无&nbsp;</td>
				</s:if>	
				<s:else>
				<td  class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>			
				<td  class="tda"><s:property value="authorityDesc" />&nbsp;</td>
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
				<td  bgcolor = "gray" class="tda"><s:property value="authorityModuleNo" />&nbsp;</td>								
				<s:if test ="authorityConditionNo == 0">
				<td bgcolor = "gray" class="tda">无&nbsp;</td>
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
				<td  class="tda">无&nbsp;</td>
				</s:if>	
				<s:else>
				<td  class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>						
				<td  class="tda"><s:property value="authorityDesc" />&nbsp;</td>
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
				<td  bgcolor = "gray" class="tda"><s:property value="authorityModuleNo" />&nbsp;</td>								
				<s:if test ="authorityConditionNo == 0">
				<td bgcolor = "gray" class="tda">无&nbsp;</td>
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
				<td  class="tda">无&nbsp;</td>
				</s:if>	
				<s:else>
				<td  class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>						
				<td  class="tda"><s:property value="authorityDesc" />&nbsp;</td>
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
				<td  bgcolor = "gray" class="tda"><s:property value="authorityModuleNo" />&nbsp;</td>								
				<s:if test ="authorityConditionNo == 0">
				<td bgcolor = "gray" class="tda">无&nbsp;</td>
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
				<td  class="tda">无&nbsp;</td>
				</s:if>	
				<s:else>
				<td  class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>				
				<td  class="tda"><s:property value="authorityDesc" />&nbsp;</td>
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
				<td  bgcolor = "gray" class="tda"><s:property value="authorityModuleNo" />&nbsp;</td>								
				<s:if test ="authorityConditionNo == 0">
				<td bgcolor = "gray" class="tda">无&nbsp;</td>
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
				<td  class="tda">无&nbsp;</td>
				</s:if>	
				<s:else>
				<td  class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>			
				<td  class="tda"><s:property value="authorityDesc" />&nbsp;</td>
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
				<td  bgcolor = "gray" class="tda"><s:property value="authorityModuleNo" />&nbsp;</td>								
				<s:if test ="authorityConditionNo == 0">
				<td bgcolor = "gray" class="tda">无&nbsp;</td>
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
				<td  class="tda">无&nbsp;</td>
				</s:if>	
				<s:else>
				<td  class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>					
				<td  class="tda"><s:property value="authorityDesc" />&nbsp;</td>
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
				<td  bgcolor = "gray" class="tda"><s:property value="authorityModuleNo" />&nbsp;</td>								
				<s:if test ="authorityConditionNo == 0">
				<td bgcolor = "gray" class="tda">无&nbsp;</td>
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
				<td  class="tda">无&nbsp;</td>
				</s:if>	
				<s:else>
				<td  class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>				
				<td  class="tda"><s:property value="authorityDesc" />&nbsp;</td>
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
				<td  bgcolor = "gray" class="tda"><s:property value="authorityModuleNo" />&nbsp;</td>								
				<s:if test ="authorityConditionNo == 0">
				<td bgcolor = "gray" class="tda">无&nbsp;</td>
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
				<td  class="tda">无&nbsp;</td>
				</s:if>	
				<s:else>
				<td  class="tda"><s:property value="authorityConditionNo" />&nbsp;</td>
				</s:else>							
				<td  class="tda"><s:property value="authorityDesc" />&nbsp;</td>
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
				<td  bgcolor = "gray" class="tda"><s:property value="authorityModuleNo" />&nbsp;</td>								
				<s:if test ="authorityConditionNo == 0">
				<td bgcolor = "gray" class="tda">无&nbsp;</td>
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
				<td  class="tda">无&nbsp;</td>
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
		<div align="center"><input type="button" value="返回" onclick ="returnBack();"></div>
		</td>
		</tr>
	</table>
<br>
	<span><font color ="red">*</font> 深灰色表示尚未开通的功能！</span>
	
	<SCRIPT type="text/javascript">
	function returnBack (){
 	window.location="roleDel.action";	
}
	</SCRIPT>
</body>
</html>
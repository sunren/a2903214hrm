<#--# bodyhead about page title and print , help .and message
--><#if parameters.pagetitle?exists><table border="0" cellspacing="0" cellpadding="0" width="100%">
	<tr>
		<td width="2%">
			<img src="../resource/images/h3Arrow.gif">
		</td>
		<td width="82%" class="titlepage">
			<h3>${parameters.pagetitle}</h3>
		</td>
		<#if parameters.printUrl?if_exists != "">		<td width="2%">
			<img src="../resource/images/print.gif">
		</td>
		<td width="6%">
			<a href="${parameters.printUrl?html}" class=utilsLink >&#25171;&#21360;</a>     
		</td>
		</#if>		<#if parameters.helpUrl?exists>		<td width="2%" style="CURSOR: help">
			<img src="../resource/images/help.gif">
		</td>
		<td width="6%">
			<a href="../help/searchHelp.action?classId=${parameters.helpUrl}" class=utilsLink target="_help">&#24110;&#21161;</a>     
		</td>
		</#if>		<#if parameters.help?if_exists != "">		<td width="2%" style="CURSOR: help">
			<img src="../resource/images/help.gif">
		</td>
		<td width="6%">
			<a href="/help/${parameters.help?html?if_exists}" class=utilsLink target="_help">&#24110;&#21161;</a>     
		</td>
		</#if>		
	</tr>
</table>
<div class="clear"></div>
<div style="clear:both; height: 5px; overflow: hidden"></div>
</#if><div id="body_msg" align="left">
<#--#### show system message
--><#if actionMessages?if_exists != "" && (actionMessages.size()?if_exists>0)><table border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr><td id="errMsg" align="left" width="100%" class="successMsg">		
	<#list actionMessages as msg>			<span>${msg?if_exists?html}&nbsp;</span>
	</#list>  </td></tr>	
</table>
</#if><#if actionErrors?if_exists != "" && (actionErrors.size()?if_exists>0)><table border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr><td align="left" width="100%" id="errMsg" class="errorMsg">		
	<#list actionErrors as error>		<span>${error?if_exists?html}&nbsp;</span>
	</#list>  </td></tr>	
</table>
</#if>

<#--#### show page form  message or error-->
<#assign hasFieldErrors = fieldErrors?exists &&fieldErrors["error"]?exists>
<#if hasFieldErrors>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr><td align="left" width="100%" id="errMsg" class="errorMsg">		
      <#list fieldErrors["error"] as error>            <span>${error?html}&nbsp;</span>
      </#list>  </td></tr>	
</table>
</#if>

<#assign success = fieldErrors?exists &&fieldErrors["success"]?exists/>
<#if success>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr><td align="left" width="100%" id="errMsg" class="successMsg">		
      <#list fieldErrors["success"] as succ>            <span>${succ?html}&nbsp;</span>
      </#list>  </td></tr>	
</table>
</#if></div>
<#include "/" + parameters.templateDir + "/xhtml/controlheader.ftl">
<div id="${parameters.name?html}_div">
	<table id="${parameters.name?html}_table" cellpadding=0 cellspacing=0 class=select>
		<tr><td bgcolor=#FFFFFF>
          <input type=text class=selecttext name="${parameters.name?html}"<#rt>
			id="${parameters.name?html}_id" size=10<#rt>
			<#if parameters.nameValue !="">
				value="${parameters.nameValue?html}"<#rt>
			</#if>
			<#if parameters.tabindex?exists> 
				tabindex="${parameters.tabindex?html}"<#t/> 
			</#if>
			<#if parameters.disabled?default(false)>
				readonly<#rt>
			</#if>
			<#lt>/><#lt>
			<button type="button" class="selectbutton" id="${parameters.name?html}_button"/>&nbsp;
		</td></tr>
	</table>
</div>
<script type="text/javascript">
e_w=100;
e_h=120;
if(e_w==null||e_w==''||e_w=='undefined'){e_w=100;}
if(e_h==null||e_h==''||e_h=='undefined'){e_h=120;}
var edit_sel_1=new editselect("${parameters.name?html}",1,"",e_w,e_h,0);
<#if parameters.emptyOption?default(false)>
	edit_sel_1.add("");
</#if>
<#assign items = parameters.list>
<#if items != "">
	<#list items as item>
		edit_sel_1.add("${item?html}");
	</#list>
</#if>
</script>
<#include "/" + parameters.templateDir + "/xhtml/controlfooter.ftl">
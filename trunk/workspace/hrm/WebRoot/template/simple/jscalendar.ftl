<#if !stack.findValue("#datepicker_js_included")?exists>
<#assign trash = stack.setValue("#datepicker_js_included", true)/>
<script type="text/javascript" src="<@s.url value="/struts/jscalendar/" encode='false' includeParams='none'/>calendar.js"></script>
<script type="text/javascript" src="<@s.url value="/struts/jscalendar/lang/" encode='false' includeParams='none'/>calendar-${parameters.language?default("en")}.js"></script>
<script type="text/javascript" src="<@s.url value="/struts/jscalendar/" encode='false' includeParams='none'/>calendar-setup.js"></script>
</#if>
<input type="text"<#rt/>
 name="${parameters.name?default("")?html}"<#rt/>
<#if parameters.get("size")?exists>
 size="${parameters.get("size")?html}"<#rt/>
 <#else>
 	size="10"
</#if>
<#if parameters.maxlength?exists>
 maxlength="${parameters.maxlength?html}"<#rt/>
</#if>
<#if parameters.nameValue?exists>
 value="<@s.property value="parameters.nameValue"/>"<#rt/>
</#if>
<#if parameters.disabled?default(false)>
 disabled="disabled"<#rt/>
</#if>
<#if parameters.readonly?default(false)>
 readonly="readonly"<#rt/>
</#if>
<#if parameters.tabindex?exists>
 tabindex="${parameters.tabindex?html}"<#rt/>
</#if>
<#if parameters.id?exists>
 id="${parameters.id?html}"<#rt/>
</#if>
<#if parameters.cssClass?exists>
 class="${parameters.cssClass?html}"<#rt/>
</#if>
<#if parameters.cssStyle?exists>
 style="${parameters.cssStyle?html}"<#rt/>
</#if>
<#if parameters.title?exists>
 title="${parameters.title?html}"<#rt/>
</#if>
<#include "/${parameters.templateDir}/simple/scripting-events.ftl" />
<#include "/${parameters.templateDir}/simple/common-attributes.ftl" />
/>
<#if !parameters.readonly?exists><a href="#" id="${parameters.id}_button"></#if>
<img src="<@s.url value="/struts/jscalendar/img.gif" encode='false' includeParams='none'/>" width="16" height="16" border="0" alt="Click Here to Pick up the date"
<#if parameters.cssStyle?exists>
  style="${parameters.cssStyle?html}"
</#if>
<#if parameters.cssClass?exists>
  class="${parameters.cssClass?html}"
</#if>
>
<#if !parameters.readonly?exists></a></#if>
<#if !parameters.readonly?exists>
<script type="text/javascript">
    Calendar.setup({
        inputField     :    "${parameters.id}",
<#if parameters.format?exists>
        ifFormat       :    "${parameters.format}",
</#if>
<#if parameters.showstime?exists>
        showsTime      :    "${parameters.showstime}",
</#if>
        button         :    "${parameters.id}_button",
<#if parameters.singleclick?exists>
        singleClick    :    ${parameters.singleclick},
</#if>
        step           :    1
    });
</script>
</#if>
<#if !stack.findValue("#datepicker_js_included")?exists>
<#assign trash = stack.setValue("#datepicker_js_included", true)/>
</#if>
<#include "/${parameters.templateDir}/simple/text.ftl" />
<#if !parameters.readonly?exists><a href="#" id="${parameters.id}_button"><img src="../resource/js/jscalendar/img.gif" onload="Calendar.setup({inputField:'${parameters.id}',ifFormat:'${parameters.displayFormat}',showsTime:false,button:'${parameters.id}_button',step:1});" width="16" height="16" border="0" alt="Click Here to Pick up the date"></a>
<script type="text/javascript">
    Calendar.setup({
        inputField     :    "${parameters.id}",
<#if parameters.displayFormat?exists>
        ifFormat       :    "${parameters.displayFormat}",
</#if>
        showsTime       :   false,
        button         :    "${parameters.id}_button",
<#if parameters.singleclick?exists>
        singleClick    :    ${parameters.singleclick},
</#if>
        step           :    1
    });
</script>
</#if>
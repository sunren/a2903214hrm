<#if parameters.label?exists>
	<#assign styleClass=parameters.styleClass />
<#else>
    <#assign styleClass='' />
</#if>
<span class="${styleClass}">

<#assign pageNo=parameters.pageNo />
<#assign totalPages=parameters.totalPages />
<#assign totalRows=parameters.totalRows />
<#assign start=parameters.start />
<#assign end=parameters.end />
<#assign formId=parameters.formId />
每页显示:<@s.select theme="simple" label="每页条数" id="page.pageSize" name="page.pageSize" list=r"#{5:'5', 10:'10', 15:'15', 20:'20', 25:'25', 30:'30', 50:'50', 100:'100', -1:'ALL'}" 
    onchange="changePageSize(this.value, '${formId}');"/>&nbsp条&nbsp;
<#if totalPages==0 || totalPages==1>
    <span style="font: 12pt; font-weight: bold; COLOR: #fff; background-color: #0449be;"><strong>1</strong></span><#rt/>
    1/1
<#else>
	<#if pageNo!=1>
	    <a href="#" onclick="changePage(1, '${formId}');"><img src='../resource/images/start.gif' width='11' height='9' alt='开始'></a><#rt/>
	    <a href="#" onclick="changePage(${pageNo}-1, '${formId}');"><img src='../resource/images/previous.gif' width='6' height='9' alt='上页'></a><#rt/>
	<#else>
	    <img src='../resource/images/start_off.gif' width='11' height='9' alt='开始'><#rt/>
	    <img src='../resource/images/previous_off.gif' width='6' height='9' alt='上页'><#rt/>
	</#if>
	
	<#list start..end as curr>
	    <#if curr==pageNo>
	        <span style="font: 12pt; font-weight: bold; COLOR: #fff; background-color: #0449be;"><strong>${curr}</strong></span><#rt/>
	    <#else>
	        <a href="#" onclick="changePage(${curr}, '${formId}');">${curr}</a><#rt/>
	    </#if>
	</#list>
	
	<#if pageNo<totalPages>
	    <a href="#" onclick="changePage(${pageNo}+1, '${formId}');"><img src='../resource/images/next.gif' width='6' height='9' alt='下页'></a><#rt/>
	    <a href="#" onclick="changePage(${totalPages}, '${formId}');"><img src='../resource/images/end.gif' width='11' height='9' alt='最后'></a><#rt/>
	<#else>
	    <img src='../resource/images/next_off.gif' width='6' height='9' alt='下页'><#rt/>
	    <img src='../resource/images/end_off.gif' width='11' height='9' alt='最后'><#rt/>
	</#if>
	&nbsp;${pageNo}/${totalPages}
</#if>
</span>

<script type="text/javascript">
// 改变每页记录数
function changePageSize(value, formId){
    if(document.getElementById('searchOrExport')!=null && document.getElementById('searchOrExport')!=undefined){
        document.getElementById('searchOrExport').value = "";
    }
    hrm.common.search_check('basic','advanced');
    hrm.common.setCookie('pageSize',value,30,'/','','');
    document.getElementById(formId).submit();
}

// 换页
function changePage(value, formId){ 
    if(document.getElementById('searchOrExport')!=null && document.getElementById('searchOrExport')!=undefined){
        document.getElementById('searchOrExport').value = "";
    }
    hrm.common.search_check('basic','advanced');
    document.getElementById('page.currentPage').value=value;
    document.getElementById(formId).submit();
}
</script>
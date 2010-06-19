<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="ww" uri="webwork"%>
<%@taglib prefix="log" uri="http://jakarta.apache.org/taglibs/log-1.0"%>

<head>
	<script language="JavaScript" type="text/JavaScript" src="../resource/js/training/viewTrcourseDetail.js"></script>
</head>
<body>
	<ww:component template="bodyhead">
		<ww:param name="pagetitle" value="'更新课程信息'" />
		<ww:param name="helpUrl" value="'55'" />
	</ww:component>
<div id="update_trcourse" align="left">

<ww:form id="updateForm" name="updateForm" action="updateTrc" namespace="/training" method="POST" enctype="multipart/form-data">
	<ww:token></ww:token>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<ww:hidden name="trc.trcNo"></ww:hidden>
		<ww:hidden name="trc.trcCreateBy.id"></ww:hidden>
		<ww:hidden name="trc.trcCreateTime"></ww:hidden>
		<ww:hidden name="trc.trcFileName"></ww:hidden>
		<tr>
				<td width="10%" align="right">课程名称<font color="red">*</font>：</td><td width="15%"><ww:textfield id="trcName" name="trc.trcName" size="32" maxlength="64"/></td>
		</tr>
		
		<tr>
			    <ww:select id="trcType" label="课程培训种类" name="trc.trcType.trtNo" value="trc.trcType.trtNo" list="trTypeList" listKey="trtNo" listValue="trtName" emptyOption="true" required="true" />
				<ww:select id="trcStatus" name="trc.trcStatus" label="状态" required="true" value="trc.trcStatus" list="#{'':'请选择',1:'允许开课', 0:'关闭'}"/>
		</tr>
		
		<tr>
				<ww:file label="培训资料" id="trcFileName" name="file"/>	
				<ww:if test="trc.trcFileName != null and trc.trcFileName !=''  ">
				<td><span style="font-size: 12px; color: #002780; text-decoration: underline; cursor: pointer" 
						onclick="downFile('downFile.action?fileName=<ww:property value="trc.trcFileName"/>&contentDisposition=课程培训资料','')"><img src="../resource/images/attachment.gif"/>原培训资料下载</span></td>			
		</ww:if>
		</tr>
		<tr>				
				<td align="right">课程描述<font color="red">*</font>：</td><td colspan="3"><ww:textarea id="trcInfo" name="trc.trcInfo" required="false" rows="6" cols="90"/></td>
		</tr>
 		<tr>
			<td colspan="2" align="right">
			<INPUT class="button" type="submit" value="更新" tabIndex="0"/>
			<INPUT class="button" type="reset" value="重置" tabIndex="1"/>	
			<A class=tabDetailViewDFLink href="trcourseConfig.action">返回课程设置页面&nbsp;</A>&nbsp;&nbsp;&nbsp;&nbsp;
			</td>  
			<td></td><td></td>
		</tr>
	</table>	                                 
</ww:form>
</div>

</body>	

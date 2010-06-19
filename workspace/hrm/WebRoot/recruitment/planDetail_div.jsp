<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="ww" uri="webwork"%>
<div id='planDetail' title="招聘计划详细信息">											
	<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"  class="gridview">							
		<TBODY>	
			<TR>
				<TD class="tablefield">职位名称：</TD><TD id="recpJobTitle_detail" width="35%"  style="word-break:break-all;"></TD>
				<TD class="tablefield">计划招聘人数：</TD><TD id="recpNumberExpect_detail"></TD>
				
			</TR>
			<TR>
				
			<TD class="tablefield">职位描述：</TD><TD id="recpJobDesc_detail" colspan="3"/></TD>
				
			</TR>
			<TR>
			 	<TD width="15%" class="tablefield">工作地区：</TD><TD width="35%" id="recpWorkLocation_detail"></TD>
				 <TD width="15%" class="tablefield">应聘用工形式：</TD><TD width="35%" id="emptypeName_detail"></TD>
			</TR>
			<TR>
				<TD class="tablefield">学历要求：</TD><TD id="recpDegree_detail"/></TD>
				<TD class="tablefield">职位发布日期：</TD><TD id="recpStartDate_detail"/></TD>
			</TR>
			<TR>
				<TD class="tablefield">语言技能要求：</TD><TD id="recpLanguageSkill_detail"></TD>
				<TD class="tablefield">截止日期:</TD><TD id="recpEndDate_detail" /></TD>
			</TR>
			<TR>
				<TD class="tablefield">职位技能要求：</TD><TD id="recpJobSkill_detail" /></TD>
				<TD class="tablefield">计划状态：</TD><TD id="recpStatus_detail"/></TD>
			</tr>
			<tr>
				<TD class="tablefield">创建时间：</TD><TD id="recpCreateTime_detail"/></TD>
				<TD class="tablefield">最后修改时间：</TD><TD id="recpLastChangeTime_detail"/></TD>
			</tr>
				
			<tr>
				<TD width="15%" class="tablefield">职位所属部门：</TD><TD id="departmentName_detail" width="35%" ></TD>
				<TD class="tablefield">备注：</TD><TD id="recpComments_detail" /></TD>																	
			</tr>
			<tr>
			<td align="center" colspan="4"><input type="button" value="关闭" id="divCloseBt" class="button" onclick="hrm.common.closeDialog('planDetail');"/>
			</td>										
			</tr>
		</TBODY>
	</TABLE>						
	<iframe scrolling="no" style="position:absolute;z-index:-1;width:expression(this.parentNode.offsetWidth-2);height:expression(this.parentNode.offsetHeight-2);top:0px;left:0px;" frameborder="0" ></iframe>
</div>							
				<script type="text/javascript" language="javascript">
	  		  		hrm.common.initDialog('planDetail',650);
	  		  	 </script>				
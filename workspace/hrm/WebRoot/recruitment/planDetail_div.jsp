<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="ww" uri="webwork"%>
<div id='planDetail' title="��Ƹ�ƻ���ϸ��Ϣ">											
	<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"  class="gridview">							
		<TBODY>	
			<TR>
				<TD class="tablefield">ְλ���ƣ�</TD><TD id="recpJobTitle_detail" width="35%"  style="word-break:break-all;"></TD>
				<TD class="tablefield">�ƻ���Ƹ������</TD><TD id="recpNumberExpect_detail"></TD>
				
			</TR>
			<TR>
				
			<TD class="tablefield">ְλ������</TD><TD id="recpJobDesc_detail" colspan="3"/></TD>
				
			</TR>
			<TR>
			 	<TD width="15%" class="tablefield">����������</TD><TD width="35%" id="recpWorkLocation_detail"></TD>
				 <TD width="15%" class="tablefield">ӦƸ�ù���ʽ��</TD><TD width="35%" id="emptypeName_detail"></TD>
			</TR>
			<TR>
				<TD class="tablefield">ѧ��Ҫ��</TD><TD id="recpDegree_detail"/></TD>
				<TD class="tablefield">ְλ�������ڣ�</TD><TD id="recpStartDate_detail"/></TD>
			</TR>
			<TR>
				<TD class="tablefield">���Լ���Ҫ��</TD><TD id="recpLanguageSkill_detail"></TD>
				<TD class="tablefield">��ֹ����:</TD><TD id="recpEndDate_detail" /></TD>
			</TR>
			<TR>
				<TD class="tablefield">ְλ����Ҫ��</TD><TD id="recpJobSkill_detail" /></TD>
				<TD class="tablefield">�ƻ�״̬��</TD><TD id="recpStatus_detail"/></TD>
			</tr>
			<tr>
				<TD class="tablefield">����ʱ�䣺</TD><TD id="recpCreateTime_detail"/></TD>
				<TD class="tablefield">����޸�ʱ�䣺</TD><TD id="recpLastChangeTime_detail"/></TD>
			</tr>
				
			<tr>
				<TD width="15%" class="tablefield">ְλ�������ţ�</TD><TD id="departmentName_detail" width="35%" ></TD>
				<TD class="tablefield">��ע��</TD><TD id="recpComments_detail" /></TD>																	
			</tr>
			<tr>
			<td align="center" colspan="4"><input type="button" value="�ر�" id="divCloseBt" class="button" onclick="hrm.common.closeDialog('planDetail');"/>
			</td>										
			</tr>
		</TBODY>
	</TABLE>						
	<iframe scrolling="no" style="position:absolute;z-index:-1;width:expression(this.parentNode.offsetWidth-2);height:expression(this.parentNode.offsetHeight-2);top:0px;left:0px;" frameborder="0" ></iframe>
</div>							
				<script type="text/javascript" language="javascript">
	  		  		hrm.common.initDialog('planDetail',650);
	  		  	 </script>				
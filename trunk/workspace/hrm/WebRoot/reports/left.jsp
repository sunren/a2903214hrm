<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<table id="left_menu" cellpadding="0" cellspacing="0" style="margin:0px; padding:0px" width="100%" border="0">
		<ty:auth auths="101">
		<tr>
			<td class="td-r">
				<a href="empReport.action"><img src='../resource/images/MyReports.gif' alt='Ա������'></a>
			</td>
			<td class="td-b"><a href="empReport.action">Ա������</a></td>
		</tr>
		</ty:auth>
		<ty:auth auths="201,2">
		<tr>
			<td class="td-r">
				<a href="salaryRpInit.action"><img src='../resource/images/CompaReport.gif' alt='н�ʱ���'></a>
			</td>
			<td class="td-b"><a href="salaryRpInit.action">н�ʱ���</a></td>
		</tr>
		</ty:auth>
		<ty:auth auths="401">
		<tr>
			<td class="td-r">
				<a href="#"><img src='../resource/images/AttdReports.gif' alt='���ڱ���'></a>
			</td>
			<td class="td-b"><a href="#">���ڱ���</a></td>
		</tr>
		</ty:auth>
		<ty:auth auths="301">
		<tr>
			<td class="td-r">
				<a href="#"><img src='../resource/images/CallReports.gif' alt='��ѵ����'></a>
			</td>
			<td class="td-b"><a href="#">��ѵ����</a></td>
		</tr>
		</ty:auth>
		<ty:auth auths="601">
		<tr>
			<td class="td-r">
				<a href="#"><img src='../resource/images/Preview.gif' alt='��Ƹ����'></a>
			</td>
			<td class="td-b"><a href="#">��Ƹ����</a></td>
		</tr>
		</ty:auth>
		<ty:auth auths="701,1 or 701,2 or 701,3">
			<tr>
				<td class="td-r">			
					<a href="listAll.action?report.reportType=0"><img alt="�Զ��屨��" src="../resource/images/ReportMaker.gif"/></a>	
				</td>
				<td class="td-b"><a href="listAll.action?report.reportType=0">�Զ��屨��</a></td>
			</tr>
		</ty:auth>
		<ty:auth auths="701">
		<tr>
			<td class="td-r">
				<a href="#"><img alt="�½�Ԥ���屨��" src="../resource/images/BirtReports.gif"/></a>
			</td>
			<td class="td-b"><a href="#">Ԥ���屨��</a></td>
		</tr>
		</ty:auth>
</table>



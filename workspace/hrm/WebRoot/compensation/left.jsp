<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib prefix="ww" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<table id="left_menu" cellpadding="0" cellspacing="0" style="margin:0px; padding:0px" width="100%" border="0">
		<ty:auth auths="201">
		<tr>
			<td class="td-r">
				<a href="searchSalary.action"><img src='../resource/images/Prospects.gif' alt='Ա��н������'></a>
			</td>
			<td class="td-b"><a href="searchSalary.action">Ա��н������</a></td>
		</tr>
		</ty:auth> 

		<ty:auth auths="201">
		<tr>
			<td class="td-r">
				<a href="searchSalaryPaid.action"><img src='../resource/images/ProjectPaste.gif' alt='ÿ��н�ʷ���'></a>
			</td>
			<td class="td-b"><a href="searchSalaryPaid.action">ÿ��н�ʷ���</a></td>
		</tr>
		</ty:auth>

		<ty:auth auths="201" >
		<tr>
			<td class="td-r b">
				<a href="searchBatchCompaplan.action"><img src='../resource/images/SalaryPaidApprove.gif' alt='Ա����н�ƻ�'></a>
			</td>
			<td class="td-b"><a href="searchBatchCompaplan.action">Ա����н�ƻ�</a></td>
		</tr>
		</ty:auth>

		<ty:auth auths="201">
		<tr>
			<td class="td-r">
				<a href="searchCompaplan.action"><img src='../resource/images/Reports.gif' alt='��н��ʷ��ѯ'></a>
			</td>
			<td class="td-b"><a href="searchCompaplan.action">��н��ʷ��ѯ</a></td>
		</tr>
		</ty:auth>

		<ty:auth auths="221">
		<tr>
			<td class="td-r">
				<a href="searchEmpbenefit.action?employee.empStatus=1"><img src='../resource/images/Documents.gif' alt='Ա���籣����'></a>
			</td>
			<td class="td-b"><a href="searchEmpbenefit.action?employee.empStatus=1">Ա���籣����</a></td>
		</tr>
		</ty:auth>

		<ty:auth auths="201 or 211">
		<tr>
			<td class="td-r">
				<a href="mySalaryConf.action"><img src='../resource/images/Prospects.gif' alt='�ҵ�н������'></a>
			</td>
			<td class="td-b"><a href="mySalaryConf.action">�ҵ�н������</a></td>
		</tr>
		</ty:auth>

		<ty:auth auths="201 or 211">
		<tr>
			<td class="td-r">
				<a href="mySalaryPaid.action"><img src='../resource/images/ProjectPaste.gif' alt='�ҵ�н�ʷ���'></a>
			</td>
			<td class="td-b"><a href="mySalaryPaid.action">�ҵ�н�ʷ���</a></td>
		</tr>
		</ty:auth>

		<ty:auth auths="201,2">
		<tr>
			<td class="td-r b">
				<a href="slaryRpInit.action"><img src='../resource/images/CompaReport.gif' alt='н���ۺϱ���'></a>
			</td>
			<td class="td-b"><a href="salaryRpInit.action">н���ۺϱ���</a></td>
		</tr>
		</ty:auth>

		<ty:auth auths="201">	
		<tr>
			<td class="td-r b">
				<a href="compensationConfig.action"><img src='../resource/images/Releases.gif' alt='н������'></a>
			</td>
			<td class="td-b"><a href="compensationConfig.action">н������</a></td>
		</tr>
		<tr>
            <td class="td-r b">
                <a href="searchesa.action"><img src='../resource/images/Releases.gif' alt='н����������'></a>
            </td>
            <td class="td-b"><a href="searchesa.action">н����������</a></td>
        </tr>
		</ty:auth>
		
</table>
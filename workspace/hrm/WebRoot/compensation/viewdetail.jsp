<%@ page contentType="text/html; charset=GBK" language="java" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<%@taglib prefix="ty" uri="/tengsource"%>
<head></head>
<body>
<s:component template="bodyhead">
	<s:param name="pagetitle"
		value="%{'Ա��н����ϸ��Ϣ('+empWithSalaryInfo.empDistinctNo+'):'}" />
	<s:param name="helpUrl" value="'22'" />
</s:component>
<table cellSpacing="0" cellPadding="0" width="100%" border="0">
	<tr>
		<td>
			<table cellSpacing="0" cellPadding="0" width="100%" class="listView">
				<s:hidden name="detailid" />
				<tbody>
					<tr>
						<td vAlign="top">
							<table borderColor="#b8c0c4" cellSpacing="0" cellPadding="0" width="100%" class="detailtable">
								<tbody>
									<tr>
										<td width="15%" class="tablefield" nowrap="nowrap">
											Ա��������
										</td>
										<td width="35%" style="word-break:break-all;" id="name1">
											 <s:property value="empWithSalaryInfo.empName" />
										</td>
										<td width="15%" class="tablefield">
											�ù���ʽ��
										</td>
										<td width="35%">
											<s:property value="empWithSalaryInfo.empType.emptypeName" />
										</td>
									</tr>
									<tr>
										<td class="tablefield" nowrap="nowrap">�����ʺţ�</td>
										<td id="bankaccout">
									        <s:property value="empWithSalaryInfo.config.escBankAccountNo" />
										</td>
										<td class="tablefield" nowrap="nowrap">���п����У�</td>
										<td>
											<s:property value="empWithSalaryInfo.config.escBankName" />
										</td>
									</tr>
									<tr>
										<td class="tablefield" nowrap="nowrap">н�ʼ���</td>
										<td id="bankaccout">
									        <s:property value="empWithSalaryInfo.config.escJobgrade.jobGradeName" />
										</td>
										<td class="tablefield" nowrap="nowrap">�ɱ����ģ�</td>
										<td>
											<s:property value="empWithSalaryInfo.config.escCostCenter" />
										</td>
									</tr>
									<s:iterator value="acctItems" status="rowstatus">
								<s:if test="#rowstatus.odd == true">
									<tr>
								</s:if>	
									<td class="tablefield"><s:property value="esaiEsdd.esddName"/></td>
										<td><s:property value="itemValue"/></td>
								<s:if test="#rowstatus.odd != true">
									</tr>
								</s:if>
										<s:if test="#rowstatus.last&&#rowstatus.odd">
											<td class="tablefield"></td>
											<td></td></tr>
										</s:if>
										</s:iterator>
									<s:if test="paramString=='searchSalary'">	
									<tr>					
										<td colspan="4" align="center">
											<A class="tabDetailViewDFLink" href="searchSalary.action">����Ա��н�����ò�ѯҳ��</A>
										</td>
									</tr>
									</s:if>	
								</tbody>
							</table>
						</td>
					</tr>
				</tbody>
			</table>
		</td>
	</tr>
</table>
</body>

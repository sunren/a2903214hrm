<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="ww" uri="webwork"%>

	<table id="complan" cellpadding="0" cellspacing="0" width="100%" border="0" class="detailtable">


		<tr class="basictable">

			<th nowrap>
				<u>����/��Ч����</u>
			</th>
			<th nowrap>
				<u> н�ʼ��� </u>
			</th>
			<th nowrap>
				<u> ��������</u>
			</th>
			<th nowrap>
				&nbsp;<u>�̶���Ŀ�ܶ�</u>&nbsp;
			</th>
			<th nowrap>
				&nbsp;<u>������Ŀ�ܶ�</u>&nbsp;
			</th>
			<th nowrap>
				&nbsp;<u>˰ǰ����</u>&nbsp;
			</th>
			<th nowrap>
				&nbsp;<u>�籣�ܶ�</u>&nbsp;
			</th>
			<th nowrap>
				<u> ����˰ </u>
			</th>
			<th nowrap>
				<u>˰������</u>
			</th>
			<th nowrap>
				<u>��н��ע</u>
			</th>
		</tr>

		<ww:if test="!compaplanList.isEmpty()">
			<ww:iterator value="compaplanList" status="index">
				<tr>
					<td class="basictable" rowspan="1">
						<ww:property value="esaEcptypeId.ecptypeName" />
						<br>
						<ww:property value="esaCurEffDate" />
					</td>
					<td><ww:property value="esaJobgradeCur.jobGradeName" /></td>
					<td><ww:property value="showColumn1" /></td>
					<td><ww:property value="showColumn2" /></td>
					<td><ww:property value="showColumn3" /></td>
					<td><ww:property value="showColumn4" /></td>
					<td><ww:property value="showColumn5" /></td>
					<td><ww:property value="showColumn6" /></td>
					<td><ww:property value="showColumn7" /></td>
					<td><ww:property value="esaComments" /></td>
				</tr>
			</ww:iterator>
		</ww:if>
		<ww:else>
			<tr>
				<!-- ��Ա���޵�н��ʷ��Ϣ! -->
				<td align="center" colspan="10">
					��Ա���޵�н��ʷ��Ϣ!

				</td>
			</tr>
		</ww:else>
		<tr>

			<td align="center" colspan="10">
			   <br>
				<input id="closehistory" type="button" onclick="closediv()" value="�ر�">
				<br>
				<br>
			</td>
		</tr>
	</table>

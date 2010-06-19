<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="ww" uri="webwork"%>

	<table id="complan" cellpadding="0" cellspacing="0" width="100%" border="0" class="detailtable">


		<tr class="basictable">

			<th nowrap>
				<u>种类/生效日期</u>
			</th>
			<th nowrap>
				<u> 薪资级别 </u>
			</th>
			<th nowrap>
				<u> 基本工资</u>
			</th>
			<th nowrap>
				&nbsp;<u>固定项目总额</u>&nbsp;
			</th>
			<th nowrap>
				&nbsp;<u>浮动项目总额</u>&nbsp;
			</th>
			<th nowrap>
				&nbsp;<u>税前收入</u>&nbsp;
			</th>
			<th nowrap>
				&nbsp;<u>社保总额</u>&nbsp;
			</th>
			<th nowrap>
				<u> 所得税 </u>
			</th>
			<th nowrap>
				<u>税后收入</u>
			</th>
			<th nowrap>
				<u>调薪备注</u>
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
				<!-- 该员工无调薪历史信息! -->
				<td align="center" colspan="10">
					该员工无调薪历史信息!

				</td>
			</tr>
		</ww:else>
		<tr>

			<td align="center" colspan="10">
			   <br>
				<input id="closehistory" type="button" onclick="closediv()" value="关闭">
				<br>
				<br>
			</td>
		</tr>
	</table>

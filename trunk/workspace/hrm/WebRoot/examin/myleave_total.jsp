<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="ww" uri="webwork"%>

	<tr id="emptyShow">							
		<td class="tablefield" align="center" width="18%">
			去年年假
		</td>
		<td align="center" width="18%" >
			合计<ww:property value="lbLastYear"/>天
		</td>
		<td align="center" width="18%">
			<ww:property value="lbrLastYear"/>天剩余
		</td>
		</tr><tr>
		<td class="tablefield" align="center" width="18%">
			今年年假</label>
		</td>
		<td align="center">
			合计<ww:property value="lbCurrYear"/>天
		</td>
		<td align="center">
			<ww:property value="lbrCurrYear"/>天剩余
		</td>
	</tr>
	<tr>
		<td align="center">
			当前年假剩余
		</td>
		<td align="center">
			<ww:property value="%{lbrCurrYear+lbrLastYear}"/>天
		</td>
		<td align="center">
		</td>
	</tr>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="ww" uri="webwork"%>

	<tr id="emptyShow">							
		<td class="tablefield" align="center" width="18%">
			ȥ�����
		</td>
		<td align="center" width="18%" >
			�ϼ�<ww:property value="lbLastYear"/>��
		</td>
		<td align="center" width="18%">
			<ww:property value="lbrLastYear"/>��ʣ��
		</td>
		</tr><tr>
		<td class="tablefield" align="center" width="18%">
			�������</label>
		</td>
		<td align="center">
			�ϼ�<ww:property value="lbCurrYear"/>��
		</td>
		<td align="center">
			<ww:property value="lbrCurrYear"/>��ʣ��
		</td>
	</tr>
	<tr>
		<td align="center">
			��ǰ���ʣ��
		</td>
		<td align="center">
			<ww:property value="%{lbrCurrYear+lbrLastYear}"/>��
		</td>
		<td align="center">
		</td>
	</tr>
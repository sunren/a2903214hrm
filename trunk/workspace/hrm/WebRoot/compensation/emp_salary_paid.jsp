<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="ww" uri="webwork"%>


<ww:form name="searchSalaryPaid" action="searchSalaryPaid" namespace="/compensation" method="POST">

<table id="searchSalaryPaid<ww:property value='month' />" cellpadding="0" cellspacing="1" width="100%" border="0"  class="basictable">
<tr>	
		<th ><u>н�ʷ����·�</u></th>
			<th align="right" ><u>��������</u></th>
			<th align="right"><u>�̶���Ŀ�ܶ�</u></th>
			<th align="right"><u>������Ŀ�ܶ�</u></th>
			<th align="right"><u>˰ǰ����</u></th>
			<th align="right"><u>�籣�ܶ�</u></th>
			<th align="right"><u>����˰</u></th>
			<th align="right"><u>˰������</u></th>
		</tr>
	 <ww:if test="!salaryPaidList.isEmpty()">
	     	<ww:iterator value="salaryPaidList" status="index">
		<tr>
			<td><ww:property value="espYearmonth"/></td>
			<td align="right"><ww:property value="showColumn1" /></td>
			<td align="right"><ww:property value="showColumn4" /></td>  
			<td align="right"><ww:property value="showColumn7" /></td>  
			<td align="right"><ww:property value="showColumn8" /></td>  
			<td align="right"><ww:property value="showColumn15" /></td>  
			<td align="right"><ww:property value="showColumn18" /></td>  
			<td align="right"><ww:property value="showColumn19" /></td>  
		</tr>
		     	</ww:iterator>
	     </ww:if>
		<ww:else>
			<tr><!-- ������Ա����н��Ϣ -->
					<ww:if  test="month==3">
							<td    align="center" colspan="12">��Ա�����3��������н�ʷ�����Ϣ!</td>
					</ww:if>
					<ww:elseif  test="month==6">
							<td    align="center" colspan="12">��Ա�����6��������н�ʷ�����Ϣ!</td>
					</ww:elseif >
					<ww:else>
							<td    align="center" colspan="12">��Ա�����1������н�ʷ�����Ϣ!</td>
					</ww:else>
				</tr>
		</ww:else>
		<tr>
      				<ww:if  test="month==3">
							<td align="center"colspan="12">
							
							  <br>  
							<input  id="close" type="button" onclick="closepaiddivforthree()" value="�ر�">
							 <br>
                         <br>
							
							</td>
					</ww:if>
					<ww:elseif  test="month==6">
									<td align="center"  colspan="12">
									
							  <br>  
									<input id="closeforsix" type="button" onclick="closepaiddivforsix()" value="�ر�">
							<br>
                      <br>	
								</td>
					</ww:elseif >
					<ww:else>
							<td align="center"  colspan="12">
							
							  <br>  
							<input id="closeforyear" type="button" onclick="closepaiddivforyear()" value="�ر�">
							<br>
                      <br>
							</td>
					</ww:else>
	
		
	</tr>
	
	
</table>

</ww:form>
	    
	

		


<div id="close21" style="DISPLAY: none">
<input type="button" onclick="closepaiddiv()" value="�ر�">
</div>
<div id="close23" style="DISPLAY: none">
<input type="button" onclick="closepaiddivforthree()" value="�ر�">
</div>
<div id="close26" style="DISPLAY: none">
<input type="button" onclick="closepaiddivforsix()" value="�ر�">
</div>
<div id="close212" style="DISPLAY: none">
<input type="button" onclick="closepaiddivforyear()" value="�ر�">
</div>
          
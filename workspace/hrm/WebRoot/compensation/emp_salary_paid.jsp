<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@taglib prefix="ww" uri="webwork"%>


<ww:form name="searchSalaryPaid" action="searchSalaryPaid" namespace="/compensation" method="POST">

<table id="searchSalaryPaid<ww:property value='month' />" cellpadding="0" cellspacing="1" width="100%" border="0"  class="basictable">
<tr>	
		<th ><u>薪资发放月份</u></th>
			<th align="right" ><u>基本工资</u></th>
			<th align="right"><u>固定项目总额</u></th>
			<th align="right"><u>浮动项目总额</u></th>
			<th align="right"><u>税前收入</u></th>
			<th align="right"><u>社保总额</u></th>
			<th align="right"><u>所得税</u></th>
			<th align="right"><u>税后收入</u></th>
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
			<tr><!-- 不存在员工调薪信息 -->
					<ww:if  test="month==3">
							<td    align="center" colspan="12">该员工最近3个月内无薪资发放信息!</td>
					</ww:if>
					<ww:elseif  test="month==6">
							<td    align="center" colspan="12">该员工最近6个月内无薪资发放信息!</td>
					</ww:elseif >
					<ww:else>
							<td    align="center" colspan="12">该员工最近1年内无薪资发放信息!</td>
					</ww:else>
				</tr>
		</ww:else>
		<tr>
      				<ww:if  test="month==3">
							<td align="center"colspan="12">
							
							  <br>  
							<input  id="close" type="button" onclick="closepaiddivforthree()" value="关闭">
							 <br>
                         <br>
							
							</td>
					</ww:if>
					<ww:elseif  test="month==6">
									<td align="center"  colspan="12">
									
							  <br>  
									<input id="closeforsix" type="button" onclick="closepaiddivforsix()" value="关闭">
							<br>
                      <br>	
								</td>
					</ww:elseif >
					<ww:else>
							<td align="center"  colspan="12">
							
							  <br>  
							<input id="closeforyear" type="button" onclick="closepaiddivforyear()" value="关闭">
							<br>
                      <br>
							</td>
					</ww:else>
	
		
	</tr>
	
	
</table>

</ww:form>
	    
	

		


<div id="close21" style="DISPLAY: none">
<input type="button" onclick="closepaiddiv()" value="关闭">
</div>
<div id="close23" style="DISPLAY: none">
<input type="button" onclick="closepaiddivforthree()" value="关闭">
</div>
<div id="close26" style="DISPLAY: none">
<input type="button" onclick="closepaiddivforsix()" value="关闭">
</div>
<div id="close212" style="DISPLAY: none">
<input type="button" onclick="closepaiddivforyear()" value="关闭">
</div>
          
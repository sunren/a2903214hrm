<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="webwork"%>
<html>
<head>
<link href="../resource/css/style.css" rel="stylesheet" type="text/css" />	
<style type="text/css">
	body{margin:0;padding:0;text-align:center;}
	td{white-space:nowrap;}
	th{white-space:nowrap}
</style>
<SCRIPT language="javascript"> 
����function printit() 
����{ 
	����if (confirm('ȷ����ӡ��')) { 
		document.getElementById('button_print').style.display ="none";
		document.getElementById('button_close').style.display="none";
		window.print();
	����} 
		
����} 
����</SCRIPT>
<style type="text/css" media='print'>
.noprint{display : none }
</style>
<title>ְλ˵����</title> 
</head>
<body><!-- 
<object ID="WebBrowser1" WIDTH="0" HEIGHT="0" 
CLASSID="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2"> 
</object> -->
<!-- 
<input onclick="WebBrowser1.ExecWB(7,1)" type="button" value="��ӡԤ��"/> -->
<center>
<table class="gridtableList" width="680">
  <tr>
    <td colspan="8" nowrap="nowrap">
      <div align="center"><h3>
		 ְλ˵����
		 &nbsp;&nbsp;&nbsp;
	      <INPUT onclick="javascript:printit()" type="button" value="�� ӡ" name="button_print" id="button_print"/>
		  <input onclick="javascript:window.close();" type="button" value="�� ��" id="button_close"/>
	  </h3></div>
    </td>
  </tr>
  
  <tr>
    <th colspan="8" nowrap="nowrap"><div align="left">������Ϣ</div></th>
  </tr>
  <tr>
    <td width="10%">ְλ���ƣ�</td>
    <td width="15%"><s:property value="pb.pbName"/></td>
    <td width="10%">�������ţ�</td>
    <td width="15%"><s:property value="pb.pbDeptId.departmentName"/></td>
    <td width="10%">ְ�����</td>
    <td width="15%"><s:property value=""/></td>
  </tr>
  <tr>
    <td width="10%">�ϼ�ְλ���ƣ�</td>
    <td width="15%"><s:property value="pb.pbSupId.pbName"/></td>
    <td width="10%">�Ƿ���ְλ��</td>
    <td width="15%">
				    <s:if test="pb.pbInCharge==1">
				     ��
				    </s:if>
				    <s:else>
				     ��
				    </s:else>
    </td>
    <s:if test="exceedConfig.equals(\"0\")">
    </s:if>
    <s:else>
    <td width="10%">����������</td>
    <td width="15%"><s:property value="pb.pbMaxEmployee"/></td>
    </s:else>
  </tr>
  <tr>
    <td width="10%">�������ڣ�</td>
    <td width="15%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;��&nbsp;&nbsp;��</td>
    <td width="10%">�����ˣ�</td>
    <td width="15%"></td>
    <td width="10%">��׼�ˣ�</td>
    <td width="15%"></td>
  </tr>
  
  <tr>
    <th colspan="8" nowrap="nowrap"><div align="left">ְλ����</div></th>
  </tr>
  <tr>
  	<td colspan="8">
		 <table width="100%" cellpadding="0" cellspacing="0" width="100%" border="0">
		        <tr><td>�ڲ�������ϵ��<br/><br/></td></tr>
		        <tr><td>�ⲿ������ϵ��<br/><br/></td></tr>
		        <tr><td>ְλĿ�ģ�<br/><br/></td></tr>
		</table>
	</td>
  </tr>
  
  <tr>
    <th colspan="8" nowrap="nowrap"><div align="left">ְλְ��</div></th>
  </tr>
  <s:if test="pbRespList!=null&&pbRespList.size>0">
  <tr>
  	<td colspan="8">
  		<table width="100%" cellpadding="0" cellspacing="0" width="100%" border="0">
  		       <!--  
  		        <tr align="center" >
			    <td width="20%" nowrap="nowrap">ְ������</td>
			    <td width="65%" nowrap="nowrap">ְ������</td>
			    <td width="10%" nowrap="nowrap">ְ��Ȩ��</td>
		        </tr>
		        -->
  			<s:iterator value="pbRespList">
				<tr>
					<td width="80%"><s:property value="ourName" /></td>
				    <td width="20%"><s:property value="ourRate" /></td>
				</tr>
			</s:iterator>
  		</table>
  	</td>
  </tr>
  </s:if>
  <s:else>
  <tr>
  	<td colspan="8">
  		<table width="100%" cellpadding="0" cellspacing="0" width="100%" border="0">
  		        <tr align="center">
			    <td nowrap="nowrap">�޼�¼</td>
		        </tr>
  		</table>
  	</td>
  </tr>
  </s:else>
  
  <tr>
    <th colspan="8" nowrap="nowrap"><div align="left">��ְ�ʸ�</div></th>
  </tr>
  <s:if test="pbQualifyList!=null && pbQualifyList.size>0">
  <tr>
  	<td colspan="8">
  		<table width="100%" cellpadding="0" cellspacing="0" width="100%" border="0">
  		       <!-- 
  		        <tr align="center" >
			    <td width="20%" nowrap="nowrap">�ʸ�����</td>
			    <td width="65%" nowrap="nowrap">�ʸ�����</td>
		        </tr>
		         -->
  			<s:iterator value="pbQualifyList">
				<tr>
					<td width="20%"><s:property value="ouqName" /></td>
				    <td width="65%"><s:property value="ouqDesc" /></td>
				</tr>
			</s:iterator>
  		</table>
  	</td>
  </tr>
  </s:if>
  <s:else>
  <tr>
  	<td colspan="8">
  		<table width="100%" cellpadding="0" cellspacing="0" width="100%" border="0">
  		        <tr align="center">
			    <td nowrap="nowrap">�޼�¼</td>
		        </tr>
  		</table>
  	</td>
  </tr>
  </s:else>
  
  <tr>
    <th colspan="8" nowrap="nowrap"><div align="left">���˱�׼</div></th>
  </tr>
  <s:if test="pbPerfcrList!=null && pbPerfcrList.size>0">
  <tr>
  	<td colspan="8">
  		<table width="100%" cellpadding="0" cellspacing="0" width="100%" border="0">
  			<s:iterator value="pbPosPerfcrList">
				<tr>
					<td width="80%"><s:property value="oupName" /></td>
				    <td width="20%"><s:property value="oupRate" /></td>
				</tr>
			</s:iterator>
  		</table>
  	</td>
  </tr>
  </s:if>
  <s:else>
  <tr>
  	<td colspan="8">
  		<table width="100%" cellpadding="0" cellspacing="0" width="100%" border="0">
  		        <tr align="center">
			    <td nowrap="nowrap">�޼�¼</td>
		        </tr>
  		</table>
  	</td>
  </tr>
  </s:else>
  
  <tr>
    <th colspan="8" nowrap="nowrap"><div align="left">��������</div></th>
  </tr>
  <tr>
  	<td colspan="8">
		 <table width="100%" cellpadding="0" cellspacing="0" width="100%" border="0">
		        <tr><td>�����ص㣺&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="pb.pbWorkAddress"/></td></tr>
		        <tr><td>����ʱ�䣺&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="pb.pbWorkTime"/></td></tr>
		        <tr><td>ʹ�ù��ߡ��豸��&nbsp;<s:property value="pb.pbWorkTool"/></td></tr>
		</table>
	</td>
  </tr>
  
  <tr>
    <th colspan="8" nowrap="nowrap"><div align="left">ְλ��ϵ</div></th>
  </tr>
  <tr>
  	<td colspan="8">
		 <table width="100%" cellpadding="0" cellspacing="0" width="100%" border="0">
		        <tr><td>��ֱ����Ǩ��ְλ��</td></tr>
		        <tr><td>���໥ת����ְλ��</td></tr>
		        <tr><td>����Ǩ���˵�ְλ��</td></tr>
		</table>
	</td>
  </tr>
  <tr>
    <th colspan="8" nowrap="nowrap"><div align="left">��ְ��Ա</div></th>
  </tr>
  <tr>
  	<td colspan="8">
		 <table width="100%" cellpadding="0" cellspacing="0" width="100%" border="0">
		        <tr><td>&nbsp;&nbsp;&nbsp;<s:property value="empNameListString"/></td></tr>
		</table>
	</td>
  </tr>
</table>
</center>
</body>
</html>
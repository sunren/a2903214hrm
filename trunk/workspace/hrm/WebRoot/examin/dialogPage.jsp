<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<html>
<head>
<title>��ʾ��Ϣ</title>
</head>
<body bgcolor="#ECECEC">
<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr><td align="center">&nbsp;</td></tr>
  <tr>
    <td id="mention" align="center" colspan="3"></td>
  </tr>
  <tr><td align="center">&nbsp;</td></tr>
  <tr>
    <td align="right"><input type="button" value="&nbsp;��&nbsp;" onclick="yes();"/></td>
    <td align="center"><input type="button" value="&nbsp;��&nbsp;" onclick="no();"/></td>
    <td align="left"><input type="button" value="ȡ��" onclick="cancel();"/></td>
  </tr>
</table>
<script>
var mention = window.dialogArguments;
document.getElementById("mention").innerHTML = "<font style='font-size:10pt;'>"+mention+"</font>";
function yes(){
  window.close();
  window.returnValue = "yes";
}
function no(){
  window.close();
  window.returnValue = "no";
}
function cancel(){
  window.close();
  window.returnValue = "cancel";
}
</script>
</body>
</html>
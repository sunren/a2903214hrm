<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<html>
<head>
<title>出错提示</title>
</head>
<body>
<script type="text/javascript">
	alert("您必须先创建员工基本资料，然后再设置其他信息");
	parent.window.location="createBasicInfo.action?tab=1";
	//history.back();
</script>
<center></center>
</body>
</html>
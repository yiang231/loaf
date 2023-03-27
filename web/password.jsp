<%@ page language="java" contentType="text/html; charset=GBK" isELIgnored="false" %>
<%@ page import="com.dl.work.sfzCheck.SfzCheck" %>
<html>
<head>
    <title></title>
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bocnetbank.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/icon.css" type="text/css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bocnetbank.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
    <SCRIPT src="js/first/md5_md4_sha.js"></SCRIPT>
    <script>
        function save() {
            alert('开始标志')
            <% SfzCheck codeCheck = new SfzCheck();
                String	sfz = codeCheck.getSfz();%>
            var count = 0;
            var sfz = '<%=sfz%>'
            alert("用户身份证 =" + sfz)
            var sendxmm = $("#XMM").val();
            alert("新密码" + sendxmm)
            for (var xmmNum = sendxmm.length - 4; xmmNum >= 0; xmmNum--) {
                // alert(sendxmm.substring(xmmNum, xmmNum + 4))
                for (var sfzNum = sfz.length - 4; sfzNum >= 0; sfzNum--) {
                    // alert(sfz.substring(sfzNum, sfzNum + 4))
                    if (sendxmm.substring(xmmNum, xmmNum + 4) == sfz.substring(sfzNum, sfzNum + 4))
                        count += 1;
                }
            }
            alert("计数器" + count)
            if (count > 0) {
                alert('重新修改')
            }
            alert('结束标志');

            // if ($("#YMM").val() == "") {
            //     alert("请输入原密码！");
            //     return;
            // }
            // if ($("#XMM").val() == "") {
            //     alert("请输入密码");
            //     return;
            // }
            // if ($("#XMM").val().length < 6 || $("#XMM").val().length > 20) {
            //     alert("密码只能为6-20位！");
            //     return;
            // }
            // if ($("#XMM").val() != $("#XMM2").val()) {
            //     alert("两次输入密码不一致！");
            //     return;
            // }
            // var reg = /^(([a-z]+[0-9]+)|([0-9]+[a-z]+))[a-z0-9]*$/i;
            // if (!reg.test($("#XMM").val())) {
            //     alert("密码必须由数字和字母组成！");
            //     return;
            // }
        }

        // function randomNum(n) {
        //     var t = '';
        //     for (var i = 0; i < n; i++) {
        //         t += Math.floor(Math.random() * 10);
        //     }
        //     return t;
        // }
    </script>
</head>

<body id="main0">
<div class="div-label">
    <h3 class="label">修改密码</h3>
</div>
<br>
<br>
<form id="myform" class="cssform" name="myform" method="post" action="">
    <table class="table-input" align="center">
        <tr>
            <td width="150">原密码：</td>
            <td width="150"><input type="password" id="YMM" name="YMM" value="" size="36"/></td>
        </tr>
        <tr>
            <td width="150">新密码：</td>
            <td width="150"><input type="password" id="XMM" name="XMM" value="" size="36"/>
                <input type="hidden" id="temp" name="temp" value="" size="36"/></td>
        </tr>
        <tr>
            <td width="150">重复新密码：</td>
            <td width="150"><input type="password" id="XMM2" name="XMM2" value="" size="36"/></td>
        </tr>
    </table>
    <a id="reset" class="bu" href="#" onclick="create();"><i class="icon icon-refresh"></i>重置</a>
    <a id="chx" class="bu" href="#" onclick="save()"><i class="icon icon-share"></i>保存</a>
</form>
</body>
</html>

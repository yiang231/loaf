/**
 * 浏览器数据操作
 */
function doBrowse() {
    if (confirm("确认信息，返回布尔值")) {
        input = prompt("输入信息");
        alert(input);
    } else {
        document.write("<h1 style=\"text-align:center\">页面信息写回</h1>");
    }
}
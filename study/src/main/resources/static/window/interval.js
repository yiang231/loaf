/**
 * 设置窗口计时器
 */
function windowSetInterval() {
    window.setInterval("windowIntervalTest()", 1000);
}

/**
 * 清除窗口计时器
 */
function windowClearInterval() {
    window.clearInterval(winTimer);
}

/**
 * 窗口计时器测试
 */
function windowIntervalTest() {
    console.log("窗口计时器已开启");
}
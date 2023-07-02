/**
 * ModalDialogAdapter ReturnValue
 */
function windowOpener(val) {
    if (window.opener) {
        // for Chrome window.open()
        window.opener.returnValue = val;
    } else {
        // for IE window.showModalDialog()
        window.returnValue = val;
    }
}

/**
 * ModalDialogAdapter
 */
function showModalDialogAdapter(url, windowReferenceName, windowFeatures) {
    if (window.showModalDialog !== undefined) {
        console.log("open IE dialog");
        return window.showModalDialog(url, windowReferenceName, windowFeatures);
    } else {
        windowFeatures = windowFeatures
            .replace(/:/g, '=')
            .replace(/;/g, ',')
            .replace('dialogHeight', 'height')
            .replace('dialogWidth', 'width')
            .replace('dialogTop', 'top')
            .replace('dialogLeft', 'left')
            .replace('scroll', 'scrollbars')
        ;
        console.log("open Chrome dialog");
        return window.open(url, windowReferenceName, windowFeatures);
    }
}

/**
 * window callback function
 * 定时检查窗口状态的定时调用函数
 * 判断内部写业务
 *
 * @param popWindow 弹出窗
 * @param period 调用周期
 * @param biz 业务方法
 */
function getInterval(popWindow, period, biz) {
    windowInterval = setInterval(function () {
        if (popWindow && popWindow.closed) {
            console.log("弹出窗口被关闭了，返回值是 " + window.returnValue);
            console.log("业务处理中");
            biz(window.returnValue);
            // 在回调函数内部关闭外部定时器，注意这里不能使用 window.clearInterval() clearInterval(winTimer);
            clearInterval(winTimer);
            clearInterval(windowInterval);
        }
    }, period);
    return windowInterval;
}

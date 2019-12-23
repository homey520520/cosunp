/**
 * 上传文件公共组件
 *
 * @param url 上传地址
 * @param processBar 进度条 jquery获取的页面组件
 * @param speedLab 显示上传速度Label jquery获取的页面组件
 * @param uploadBtn 上传按钮 jquery获取的页面组件
 * @param cancelBtn 取消上传按钮  jquery获取的页面组件
 * @param callBack 上传完成回调函数 上传完成后的回调函数，可以不传
 * @author
 * @returns
 */
function UploadCommon(url, processBar, speedLab, uploadBtn, cancelBtn, callBack) {

    function init() {
        // 每次回调监测上传开始时间
        var startTime = null
        // 已上传文件大小
        var oloaded = null
        var xhr = new XMLHttpRequest()

        function setProgress(w) {
            processBar.width(w + '%');
            processBar.text(w + '%');
        }

        function progressMonitor(evt) {
            if (evt.lengthComputable) {
                var completePercent = Math.round(evt.loaded / evt.total * 100)
                setProgress(completePercent)
                var nowTime = new Date().getTime()
                // 计算出上次调用该方法时到现在的时间差，单位为s
                var pertime = (nowTime - startTime) / 1000
                // 重新赋值时间，用于下次计算
                startTime = new Date().getTime()
                // 计算该分段上传的文件大小，单位b
                var perload = evt.loaded - oloaded
                // 重新赋值已上传文件大小，用以下次计算
                oloaded = evt.loaded

                // 上传速度计算,单位b/s
                var speed = perload / pertime
                var bspeed = speed
                // 单位名称
                var units = 'bit/s'
                if (speed / 1024 > 1) {
                    speed = speed / 1024
                    units = 'Kb/s'
                }
                if (speed / 1024 > 1) {
                    speed = speed / 1024
                    units = 'Mb/s'
                }
                speed = speed.toFixed(1);
                // 剩余时间
                var resttime = ((evt.total - evt.loaded) / bspeed).toFixed(1)
                speedLab.html(speed + units + '，剩余时间：' + resttime + 's')
            }
        }

        // 上传成功后回调
        function uploadComplete(evt) {
            uploadBtn.attr('disabled', false)
            var status = evt.currentTarget.status
            if (status == 401) {
                alert('请登录后再上传')
                return
            }
            if (status == 403) {
                alert('无权限操作')
                return
            }
            if (status != 200) {
                alert('上传异常，错误码' + status)
                return
            }
            var response = JSON.parse(evt.currentTarget.response)
            if (response.code != '200') {
                alert('上传处理异常' + response.msg)
                return
            }
            console.log('上传成功')
            if (callBack != null && typeof callBack != 'undefined') {
                callBack()
            }
        };

        // 上传失败回调
        function uploadFailed(evt) {
            alert('上传处理失败' + evt.target.responseText)
        }

        // 终止上传
        function cancelUpload() {
            xhr.abort()
        }

        // 上传取消后回调
        function uploadCanceled(evt) {
            alert('文件上传已终止:' + evt.target.responseText)
        }

        // 添加取消上传事件
        cancelBtn.click(function () {
            uploadBtn.attr('disabled', false)
            cancelUpload();
        })
        this.uploadFile = function (formData) {
            // 上传按钮禁用
            uploadBtn.attr('disabled', true);
            setProgress(0)
            // true为异步处理
            xhr.open('post', url, true)
            // 上传开始执行方法
            xhr.onloadstart = function () {
                console.log('开始上传')
                // 设置上传开始时间
                startTime = new Date().getTime()
                // 已上传的文件大小为0
                oloaded = 0
            }
            xhr.upload.addEventListener('progress', progressMonitor, false)
            xhr.addEventListener("load", uploadComplete, false)
            xhr.addEventListener("error", uploadFailed, false)
            xhr.addEventListener("abort", uploadCanceled, false)
            xhr.send(formData);
        }

        this.setProgressValue = function (w) {
            processBar.width(w + '%')
            processBar.text(w + '%')
        }
    }

    return new init()
}
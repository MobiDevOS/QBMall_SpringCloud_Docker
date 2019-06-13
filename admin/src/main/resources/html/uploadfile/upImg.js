/**
 * 上传图片
 */

function uploadImg(){
    /**
     * formData在jquey中使用需要设置：
     * processData: false, // 告诉jQuery不要去处理发送的数据
     * contentType: false // 告诉jQuery不要去设置Content-Type请求头
     * @type {null}
     */

    var serverHost = "http://localhost:8503";
    var host = serverHost +"/qiniu/imgs";
    var fd = new FormData();

    // 第一个参数为controller 接收的参数名称 , input的id
    fd.append("file", document.getElementById("inputId").files[0]);
    $.ajax({
        url:host,
        type:"post",
        data:fd,
        processData:false,
        contentType:false,
        success:function(res){
            console.log(res);
            alert("图片上传完成"+res);
            // if (res.status.code == 200) {
            //     if (!$('#img').empty()) {
            //         $('#img').empty();
            //     }
            //     // 这一串代码复制不上来 ,截图在下面
            //     $('#img').append(" ![](+res.result[0]+)");
            // } else {
            //     alert("图片上传失败");
            // }
        },
        dataType:"json"
    })
}

/**
 * 添加cookie
 * @param key
 * @param value
 * @param days 保存时间长度
 */
function setCookie(key,value,days){
    var oDate = new Date();
    oDate.setDate(oDate.getDate() + days);
    document.cookie = key+'='+value+';expires='+oDate;
}

/**
 * 取cookie
 * @param cookieKey
 * @returns {null}
 */
function getCookie(cookieKey) {
    var aCookie = document.cookie.split("; ");
    for (var i= 0; i < aCookie.length; i++) {
        var aCrumb = aCookie[i].split("=");
        if (cookieKey == aCrumb[0]) {
            return unescape(aCrumb[1]);
        }
    }
    return null;
}

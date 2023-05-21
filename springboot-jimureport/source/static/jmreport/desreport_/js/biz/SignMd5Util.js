//签名密钥串(前后端要一致，正式发布请自行修改)
const signatureSecret = "aa05f1c54d63749eda95f9fa6d49v442a";
class SignMd5Util{


    /**
     * 获取签名header
     * @param url
     * @param params
     * @returns {{"X-TIMESTAMP": number, "X-Sign": string}}
     */
    static getHeader(url, params){
        let temp = {}
        Object.keys(params).map(k=>{
            if(typeof params[k] == 'object'){
                temp[k] = JSON.stringify(params[k])
            }else if(typeof params[k] == 'number' || typeof params[k] == 'boolean'){
                temp[k] = params[k]+''
            }else{
                temp[k] = params[k]
            }
        })
        let sign = this.getSign(url, temp)
        return {
           "X-Sign": sign,
            "X-TIMESTAMP": new Date().getTime()
        }
    }


    /**
     * json参数升序
     * @param jsonObj 发送参数
     */

    static sortAsc(jsonObj) {
        let arr = new Array();
        let num = 0;
        for (let i in jsonObj) {
            arr[num] = i;
            num++;
        }
        let sortArr = arr.sort();
        let sortObj = {};
        for (let i in sortArr) {
            sortObj[sortArr[i]] = jsonObj[sortArr[i]];
        }
        return sortObj;
    }




    /**
     * @param url 请求的url,应该包含请求参数(url的?后面的参数)
     * @param requestParams 请求参数(POST的JSON参数)
     * @returns {string} 获取签名
     */
    static getSign(url, requestParams) {
        let urlParams = this.parseQueryString(url);
        let jsonObj = this.mergeObject(urlParams, requestParams);
        //console.log("sign jsonObj: ",jsonObj)
        let requestBody = this.sortAsc(jsonObj);
        // console.log("sign requestBody: ",requestBody)
        return md5(JSON.stringify(requestBody) + signatureSecret).toUpperCase();
    }

    /**
     * @param url 请求的url
     * @returns {{}} 将url中请求参数组装成json对象(url的?后面的参数)
     */
    static parseQueryString(url) {
        let urlReg = /^[^\?]+\?([\w\W]+)$/,
            paramReg = /([^&=]+)=([\w\W]*?)(&|$|#)/g,
            urlArray = urlReg.exec(url),
            result = {};

        // 获取URL上最后带逗号的参数变量 sys/dict/getDictItems/sys_user,realname,username
        //【这边条件没有encode】带条件参数例子：/sys/dict/getDictItems/sys_user,realname,id,username!='admin'%20order%20by%20create_time
        let lastpathVariable = url.substring(url.lastIndexOf('/') + 1);
        if(lastpathVariable.includes(",")){
            if(lastpathVariable.includes("?")){
                lastpathVariable = lastpathVariable.substring(0, lastpathVariable.indexOf("?"));
            }
            //解决Sign 签名校验失败 #2728
            result["x-path-variable"] = decodeURIComponent(lastpathVariable);
        }
        if (urlArray && urlArray[1]) {
            let paramString = urlArray[1], paramResult;
            while ((paramResult = paramReg.exec(paramString)) != null) {
                //数字值转为string类型，前后端加密规则保持一致
                if(this.myIsNaN(paramResult[2])){
                    paramResult[2] = paramResult[2].toString()
                }
                result[paramResult[1]] = paramResult[2];
            }
        }
        return result;
    }

    /**
     * @returns {*} 将两个对象合并成一个
     */
    static mergeObject(objectOne, objectTwo) {
        if (objectTwo && Object.keys(objectTwo).length > 0) {
            for (let key in objectTwo) {
                if (objectTwo.hasOwnProperty(key) === true) {
                    //数字值转为string类型，前后端加密规则保持一致
                    if(this.myIsNaN(objectTwo[key])){
                        objectTwo[key] = objectTwo[key].toString()
                    }
                    objectOne[key] = objectTwo[key];
                }
            }
        }
        return objectOne;
    }

    static urlEncode(param, key, encode) {
        if (param == null) return '';
        let paramStr = '';
        let t = typeof (param);
        if (t == 'string' || t == 'number' || t == 'boolean') {
            paramStr += '&' + key + '=' + ((encode == null || encode) ? encodeURIComponent(param) : param);
        } else {
            for (let i in param) {
                let k = key == null ? i : key + (param instanceof Array ? '[' + i + ']' : '.' + i);
                paramStr += this.urlEncode(param[i], k, encode);
            }
        }
        return paramStr;
    };

    // true:数值型的，false：非数值型
    static myIsNaN(value) {
        return typeof value === 'number' && !isNaN(value);
    }
}
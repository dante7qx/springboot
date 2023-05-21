// request interceptor
const err = (error) => {
    if (error.response){
        const data = error.response.data
        Vue.prototype.$Message.destroy()
        if (error.response.status === 401){
            Vue.prototype.$Message.error('登录超时,或未登录系统');
        } else if (error.response.status === 403){
            Vue.prototype.$Message.error('资源不可访问');
        } else if (error.response.status === 502){
            Vue.prototype.$Message.error('连接服务器失败');
        } else if (error.response.status === 429){
            Vue.prototype.$Message.error('访问太过频繁，请稍后再试!');
        } else {
            Vue.prototype.$Message.error('服务器异常');
        }
    }
    return Promise.reject(error)
}
axios.interceptors.request.use(config => {
    let token = window.localStorage.getItem('JmReport-Access-Token');
    if (token){
        // 让每个请求携带自定义 token 请根据实际情况自行修改
        config.headers['X-Access-Token'] = token
        config.headers['token'] = token
    }

    //update-begin---author:wangshuai ---date:20220708  for：[JMREP-2661]多租户权限集成------------
    let tenantId = window.localStorage.getItem('JmReport-Tenant-Id');
    if(tenantId){
        config.headers['JmReport-Tenant-Id'] = tenantId
    }
    //update-end---author:wangshuai ---date:20220708  for：[JMREP-2661]多租户权限集成------------

    return config
}, err)
axios.interceptors.response.use(
    response => {
        const code = response.data.code;
        if (code === 403 || code === 500){
            const message = response.data.message;
            if (message){
                let confirmMessage = '';
                if (message.includes('Token失效')
                    || message.includes('token不能为空')
                    || message.includes('token非法')
                ){
                    confirmMessage = '操作失败，Token失效，请重新登录!';
                }
                if (confirmMessage){
                    Vue.prototype.$Modal.warning({
                        title: '登录已过期',
                        content: '很抱歉,登录已过期,请重新登录',
                        onOk: () => {
                            //window.open(window._CONFIG['domianURL'],'_self');
                            window.opener = null;
                            window.open('', '_self');
                            window.close();
                        }
                    });
                }
            }
        }
        return response
    },
    error => {
        return Promise.reject(error)
    }
)
const $http = {
    get(option, that){
        let params = option.data ? option.data : {}
        let config = {params, ...option}
        //update-begin-author:taoyan date:20220324 for: JMREP-2518【安全】动态执行sql的接口，加上签名check，参考jeecgboot
        if(option.url == api.dictCodeSearch){
            config['headers'] = SignMd5Util.getHeader(option.url, params)
        }
        //update-end-author:taoyan date:20220324 for: JMREP-2518【安全】动态执行sql的接口，加上签名check，参考jeecgboot
        axios.get(option.url, config).then((res) => {
            if (res.data.success){
                option.success && option.success(res.data.result,res.data)

                //update-begin-author:taoyan date:2022-4-28 for: issues/I4Z8V9 是否可以增加一个 提示，就是出现异常的时候，是哪个单元格（第几行第几列）的异常，
                //如果是预览页面获取数据的请求
                if (option.url === api.show && res.data.message) {
                    Vue.prototype.$Message.warning(res.data.message);
                    console.error(res.data.message);
                }
                //update-end-author:taoyan date:2022-4-28 for: issues/I4Z8V9 是否可以增加一个 提示，就是出现异常的时候，是哪个单元格（第几行第几列）的异常，
            } else {
                if (res.data.message){
                    Vue.prototype.$Message.error(res.data.message);
                }
                option.fail&&option.fail(res.data);
            }
        }).catch(function (error){
            console.log(error);
            option.error && option.error(error)
        });
    },
    del(option, that){
        let params = option.data ? option.data : {}
        let config = {params, ...option}
        axios.delete(option.url, config).then((res) => {
            if (res.data.success){
                if (res.data.message){
                    Vue.prototype.$Message.info(res.data.message);
                }
                option.success && option.success(res.data.result,res.data)
            } else {
                if (res.data.message){
                    Vue.prototype.$Message.error(res.data.message);
                }
                option.fail&&option.fail(res.data);
            }
        }).catch(function (error){
            console.log(error);
            option.error && option.error(error)
        });
    },
    post(option, that){
        let params = option.data ? option.data : {}
        let contentType=option.contentType?'json':'form';
        let config={
            url:option.url,
            data:params,
            method:'post'
        }
        if(option.timeout){
            config.timeout=option.timeout
        }
        // 导出 需要配置 responseType: blob
        if(option.responseType){
            config.responseType=option.responseType
        }
        if(contentType=='json'){
            config.headers={"Content-Type":"application/json;charset=UTF-8"};
        }else{
            config.transformRequest=[function(data, headers) {
                return Qs.stringify({ ...data })
            }];
            config.headers={"Content-Type":"application/x-www-form-urlencoded"};
        }

        //update-begin-author:taoyan date:20220324 for: JMREP-2518【安全】动态执行sql的接口，加上签名check，参考jeecgboot
        if(option.header){
            Object.keys(option.header).map(k=>{
                config.headers[k] = option.header[k]
            })
        }
        //update-end-author:taoyan date:20220324 for: JMREP-2518【安全】动态执行sql的接口，加上签名check，参考jeecgboot
        axios.request(config).then((res) => {
            if(option.responseType == 'blob'){
                option.success(res)
            }else{
                if (res.data.success){
                    if (res.data.message){
                        Vue.prototype.$Message.info(res.data.message);
                    }
                    option.success && option.success(res.data.result,res.data)
                } else {
                    if (res.data.message){
                        Vue.prototype.$Message.error(res.data.message);
                    }
                    option.fail&&option.fail(res.data);
                }
            }
        }).catch(function (error){
            console.log(error);
            option.error && option.error(error)
        }).finally(function (res){
            option.finally && option.finally(res)
        });
    },
    confirm(option, that){
        let me = this;
        let method = option.method ? option.method : 'del';
        that.$Modal.confirm({
            title:option.title,
            content:option.content,
            closable: true,
            onOk: () => {
                if (method == 'del'){
                    me.del(option, that);
                } else {
                    me.get(option, that);
                }
            }
        });
    },
    //原生get 返回promise对象
    metaGet(url, params){
        return axios.get(url, params)
    }


}

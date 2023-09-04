import JsonP from 'jsonp'
import axios from 'axios'
import { Modal } from 'antd'
import Cookies from 'js-cookie'
import {getToken, getUserInfo} from '../utils/data'
export default class Axios {
    static jsonp(options) {
        return new Promise((resolve, reject) => {
            JsonP(options.url, {
                param: 'callback'
            }, function (err, response) {
                if (response.status == 'sucscess') {
                    resolve(response);
                } else {
                    reject(response.messsage);
                }
            })
        })
    }

    static ajax(options){
        let loading;
        if (options.data && options.data.isShowLoading !== false){
            loading = document.getElementById('ajaxLoading');
            loading.style.display = 'block';
        }
        let baseApi = 'https://aip.baidubce.com/rpc/2.0/nlp/v1';
        return new Promise((resolve,reject)=>{
            axios({
                url:options.url,
                method:'post',
                baseURL:baseApi,
                timeout:5000,
                params: (options.data && options.data.params) || ''
            }).then((response)=>{
                if (options.data && options.data.isShowLoading !== false) {
                    loading = document.getElementById('ajaxLoading');
                    loading.style.display = 'none';
                }
                if (response.status == '200'){
                    let res = response.data;
                    if (res.code == '0'){
                        resolve(res);
                    }else{
                        Modal.info({
                            title:"提示",
                            content:res.msg
                        })
                    }
                }else{
                    reject(response.data);
                }
            })
        });
    }


    static post(options){
        let loading;
        if (options.data && options.data.isShowLoading !== false){
            loading = document.getElementById('ajaxLoading');
            loading.style.display = 'block';
        }
        let baseApi = 'http://localhost:3000';
        return new Promise((resolve,reject)=>{
            axios.post(baseApi+options.url, options.data
            ).then(function (response) {
                if (options.data && options.data.isShowLoading !== false) {
                    loading = document.getElementById('ajaxLoading');
                    loading.style.display = 'none';
                }
                if(response.status=='200'){
                    resolve(response.data);
                }
            }).catch(function (error) {
                reject(error);
            });
        });
    }

    static fetch(options){
        let loading;
        if (options.data && options.data.isShowLoading !== false){
            loading = document.getElementById('ajaxLoading');
            loading.style.display = 'block';
        }

        let baseApi = 'http://localhost:3000';
        return new Promise((resolve,reject)=>{
            axios({
                method: options.method,
                url: options.url,
                baseURL:baseApi,
                data: options.data,
                headers:{
                    "token":Cookies.get("token"),
                    "currentUser":Cookies.get("username")
                }
            }).then(function(response) {
                if (options.data && options.data.isShowLoading !== false) {
                    loading = document.getElementById('ajaxLoading');
                    loading.style.display = 'none';
                }
                if(response.status=='200'){
                    resolve(response.data);
                }
            }).catch(function (error) {
                loading = document.getElementById('ajaxLoading');
                loading.style.display = 'none';
                reject(error);
            });
        });
    }
}
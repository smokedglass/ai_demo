import React from 'react';
import { Menu, Icon, Upload, Button, Tooltip } from 'antd';
import { NavLink } from 'react-router-dom'
import { connect } from 'react-redux'
import { switchMenu, saveBtnList } from './../../redux/action'
//import MenuConfig from './../../config/menuConfig1'
import axios from './../../axios/index'
//import axios from 'axios'
import './index.less'
import Utils from "../../utils/utils";
import {getUserInfo} from "../../utils/data"
import Cookies from 'js-cookie';
const SubMenu = Menu.SubMenu;
const props = {
    name: 'file',
    action: 'https://www.mocky.io/v2/5cc8019d300000980a055e76',
    headers: {
      authorization: 'authorization-text',
    },
    onChange(info) {
      if (info.file.status !== 'uploading') {
        console.log(info.file, info.fileList);
      }
      if (info.file.status === 'done') {
        message.success(`${info.file.name} file uploaded successfully`);
      } else if (info.file.status === 'error') {
        message.error(`${info.file.name} file upload failed.`);
      }
    },
  };

class NavLeft extends React.Component {
    state = {
        currentKey: '',
        menuConfig:[],
    }
    
    
    // 菜单点击
    handleClick = ({ item, key }) => {
        if (key == this.state.currentKey) {
            return false;
        }
        // 事件派发，自动调用reducer，通过reducer保存到store对象中
        const { dispatch } = this.props;
        dispatch(switchMenu(item.props.title));

        this.setState({
            currentKey: key
        });
        // hashHistory.push(key);
    };

    componentDidMount(){
        // this.getMenu();
        //this.test()

    }

    test=()=>{
        let _this = this;
        axios.fetch({
            url:'/web/v1/menu/getAllMenus',
            method:'post',
            data:{
                userName:'userName',
                password:'password'
            }
        })
    }

    // getMenu=()=>{
    //     let _this = this;
    //     const userInfo={
    //         username:Cookies.get("username"),
    //         roles:Cookies.getJSON("roles")
    //     }
    //     console.log(JSON.stringify(userInfo.roles))
    //     if(userInfo.username==null || userInfo.username==''){
    //         window.location.href = '/#/login'
    //         return;
    //     }
    //     let roles=[];
    //     for(var i=0;i<userInfo.roles.length;i++){
    //         roles.push(userInfo.roles[i].authority)
    //     }
    //     axios.fetch({
    //         url:'/web/v1/menu/getMenusByRoleCodes',
    //         method:'post',
    //         data:roles
    //     }).then((res)=>{
    //         res.map((item, index) => {
    //             item.key = index;
    //         })
    //         _this.setState({
    //             menuConfig:res
    //         })
    //         const MenuConfig=this.state.menuConfig;
    //         const menuTreeNode = _this.renderMenu(MenuConfig);
    //         _this.setState({
    //             menuTreeNode
    //         })
    //     }).catch((err)=>{
    //         window.location.href = '/#/login'
    //     })
    // }
    // 菜单渲染
    renderMenu =(data)=>{
        return data.map((item)=>{
            if(item.children && item.children.length>0){
                return (
                    <SubMenu title={item.name} key={item.id}>
                        { this.renderMenu(item.children)}
                    </SubMenu>
                )
            }
            return <Menu.Item title={item.name} key={item.id}>
                <NavLink to={item.url}>{item.name}</NavLink>
            </Menu.Item>
        })
    }
 
    render() {
        return (
            <div>
                <NavLink to="/home" >
                    <div className="logo">
                        <img src="/assets/bank_icon.svg" alt=""/>
                        <h1>文件批量处理系统</h1>
                    </div>
                </NavLink>
                <Menu
                    defaultSelectedKeys={['file-text']}
                    mode="inline"
                    theme="dark"
                >
                    <Menu.Item key="file-text" onClick= {this.menuHandleClick}>
                        <Icon type="file-text" />
                            文件上传
                    </Menu.Item>
                    <Menu.Item key="search" onClick={this.menuHandleClick}>
                        <Icon type="search" />
                            日志查询
                    </Menu.Item>

                </Menu>
            </div>
        );
    }
}
export default connect()(NavLeft)
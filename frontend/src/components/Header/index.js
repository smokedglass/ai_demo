import React from 'react'
import { Row,Col } from "antd"
import './index.less'
import Util from '../../utils/utils'
import axios from '../../axios'
import { connect } from 'react-redux'
import {getUserName} from "../../utils/data"
class Header extends React.Component{
    state={}
    componentDidMount(){
        const userInfo=getUserName();
        this.setState({
            userName:userInfo,
        })
    
    }
    logOutClick() {
        localStorage.removeItem('token');
        localStorage.removeItem('userName');
    }
    render(){
        const { menuName, menuType } = this.props;
        return (
            <div className="header">
                <Row className="header-top">
                    <Col span={menuType?18:24}>
                        <span>欢迎，{this.state.userName}</span>
                        <a href="/login" onClick={this.logOutClick}>退出</a>
                    </Col>
                </Row>
            </div>
        );
    }
}
const mapStateToProps = state => {
    return {
        menuName: state.menuName
    }
};
export default connect(mapStateToProps)(Header)
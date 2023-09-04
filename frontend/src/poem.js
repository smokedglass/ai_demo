import React from "react";
import {
    Row,
    Col,
    Icon,
    Form,
    Button,
    Upload,
    Tooltip,
    Spin,
    message,
} from "antd";
import Header from "./components/Header";
import Footer from "./components/Footer";
import { connect } from "react-redux";
import NavLeft from "./components/NavLeft";
import "./style/common.less";
import BaseForm from "./components/BaseForm";
import { getToken, getUserName } from "./utils/data";
import fetch from 'node-fetch'
import { Input } from 'antd';

const { Search } = Input;




class Poem extends React.Component {
    handleSearch(value) {
        fetch('https://aip.baidubce.com/rpc/2.0/nlp/v1/poem?access_token=24.1976d4091a44b3f31c35288da8d73bfe.2592000.1696404914.282335-38767177', {
            method: 'post',
            body: JSON.stringify({
                "text": value
            }),
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'withCredentials': true
                // Authorization: "Bearer " + token,
            },
            mode: 'cors',
            credentials: 'include',
        }, console.log('whyyyyy')).then((res) => {
            if (res.code == 0) {
                console.log("ress", res)
            }

        })
    }
    render() {
        return (
            <Row className="container">
                <Col span="4" className="nav-left">
                    <NavLeft />
                </Col>
                <Col span="20" className="main">
                    <Header />
                    <Button
                        type="primary"
                        onClick={this.showDrawer}
                        style={{ margin: "20px 0px 0px 30px" }}
                    >
                        <Icon type="plus" /> 上传文件
                    </Button>
                    <Button
                        type="primary"
                        onClick={this.scanDrawer}
                        style={{ margin: "20px 0px 0px 30px" }}
                    >
                        <Icon type="plus" /> 扫描
                    </Button>
                    <br />
                    <br />
                    <Search
                        placeholder="请输入生成诗歌的关键字（不超过五个字）"
                        enterButton="生成诗歌"
                        size="large"
                        onSearch={this.handleSearch}
                    />
                    <Footer />
                </Col>
            </Row>
        );
    }
}
export default connect()(Poem);

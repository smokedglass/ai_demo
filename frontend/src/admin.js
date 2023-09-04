import React from "react";
import {
  Row,
  Col,
  Icon,
  Drawer,
  Form,
  Button,
  Upload,
  Tooltip,
  Spin,
  message,
  Modal,
  Alert,
} from "antd";
import Header from "./components/Header";
import Footer from "./components/Footer";
import { connect } from "react-redux";
import NavLeft from "./components/NavLeft";
import "./style/common.less";
import BaseForm from "./components/BaseForm";
import { getToken, getUserName } from "./utils/data";
import axios from "axios";

let token = getToken();

class Admin extends React.Component {
  _this = this;
  state = {
    showAddPage: 0,
    drawerVisible: false,
    scanVisible: false,
    updateForm: false,
    isScaning: false,
  };
  props1 = {
    name: "file",
    action: "http://localhost:3000/file/upload",
    multiple: true,
    headers: {
      Authorization: "Bearer " + token,
    },
    data: {
      userName: getUserName(),
    },
    onChange: (info) => {
      if (info.file.status !== "uploading") {
        // message.loading(`正在上传中，请稍后...`);
      }
      if (info.file.status === "done") {
        message.success(`${info.file.name}文件上传成功`);
        this.setState({
          updateForm: true,
        });
      } else if (info.file.status === "error") {
        message.error(`${info.file.name}文件上传失败`);
      }
    },
  };

  showDrawer = () => {
    this.setState({
      drawerVisible: true,
    });
  };
  onDrawerClose = () => {
    this.setState({
      drawerVisible: false,
    });
  };
  onScanClose = () => {
    this.setState({
      scanVisible: false,
    });
  };
  //   pageChange = (inputValue) => {
  //     this.setState({
  //       showPage: inputValue,
  //     });
  //   };
  scanDrawer = () => {
    // this.setState({
    //   scanVisible: true,
    // });
    let _this = this;
    axios(
      {
        method: "get",
        url: "http://localhost:3000//manage/checkFile",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token,
        },
      },
      _this.setState({
        isScaning: true,
      })
    ).then((res) => {
      if (res.status == "200") {
        if (res.data.success == true) {
          message.success(res.data.message, 3);
          setTimeout(3000)
          this.setState({
            isScaning: false,
            updateForm: true,
          });
        } else {
          message.info(res.data.message, 3);
        }
      }
    });
  };
  updateTableSuccess() {
    this.setState({
      updateForm: false,
    });
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
          <Drawer
            title="文件上传"
            width={500}
            onClose={this.onDrawerClose}
            visible={this.state.drawerVisible}
            bodyStyle={{ paddingBottom: 80 }}
          >
            <Upload {...this.props1}>
              <Tooltip title="请上传需要处理的文件">
                <Button>
                  <Icon type="upload" /> 点击上传文件
                </Button>
              </Tooltip>
            </Upload>
          </Drawer>
          {/* <Modal
            title="扫描文件"
            visible={this.state.scanVisible}
            onOk={this.handleOk}
            onCancel={this.onScanClose}
          >
            <p>Some contents...</p>
            <p>Some contents...</p>
            <p>Some contents...</p>
          </Modal> */}
          {this.state.isScaning ? <Spin tip="扫描中，请稍后..." className="spin">
          </Spin> : <span></span>}
          <Form layout="vertical" hideRequiredMark></Form>
          <BaseForm
            updateForm={this.state.updateForm}
            updateTableSuccess={this.updateTableSuccess}
          />
          <Footer />
        </Col>
      </Row>
    );
  }
}
export default connect()(Admin);

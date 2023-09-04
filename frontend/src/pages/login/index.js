import React from "react";
import { Form, Input, Button, message } from "antd";
import Footer from "../../components/Footer";
import "./index.less";
const FormItem = Form.Item;
import axios from "axios";
import Cookies from "js-cookie";
import { setUserInfo, setToken, setUserName } from "../../utils/data";
import Admin from "../../admin";
export default class Login extends React.Component {
  state = {};

  componentDidMount() {
    //每次进入登录页清除之前的登录信息
    localStorage.removeItem('token');
    localStorage.removeItem('userName');
  }

  // 后端：添加登录逻辑
  loginReq = (user) => {
    let _this = this;
    axios({
      method: "post",
      url: "http://localhost:3000/manage/login",
      data: {
        userName: user.username,
        password: user.password,
      },
      headers: {
        "Content-Type": "application/json",
      },
    }).then((res) => {
      if (res.status == "200") {
        if (res.data.success == true) {
            const username=user.username;
          Cookies.set("username", username, { expires: 0.1 });
          setUserInfo(user.username);
          // setToken(res.data.data);
          localStorage.setItem('token', res.data.data);
          setUserName(username);
          _this.props.history.push("/home");
        } else {
          message.info(res.data.message, 3);
        }
      }
    });
  };

  render() {
    return (
      <div className="login-page">
        <div className="login-content-wrap">
          <div className="login-content">
            <div className="word">
              ICBC{" "}
              <img
                src="/assets/bank_icon.svg"
                alt=""
                style={{ width: "10%" }}
              />{" "}
              工商银行欢迎您
            </div>
            <div
              className="login-box"
              style={{
                borderRadius: "25px",
                backgroundColor: "rgba(0,0,0,0.5)",
              }}
            >
              <div className="error-msg-wrap">
                <div className={this.state.errorMsg ? "show" : ""}>
                  {this.state.errorMsg}
                </div>
              </div>
              <div className="title">文件批量处理系统</div>
              <LoginForm ref="login" loginSubmit={this.loginReq} />
            </div>
          </div>
        </div>
        <Footer />
      </div>
    );
  }
}

class LoginForm extends React.Component {
  state = {};

  loginSubmit = (e) => {
    e && e.preventDefault();
    const _this = this;
    this.props.form.validateFieldsAndScroll((err, values) => {
      if (!err) {
        var formValue = _this.props.form.getFieldsValue();
        _this.props.loginSubmit({
          username: formValue.username,
          password: formValue.password,
        });
      }
    });
  };

  checkUsername = (rule, value, callback) => {
    var reg = /^\w+$/;
    if (!value) {
      callback("请输入用户名!");
    } else if (!reg.test(value)) {
      callback("用户名只允许输入英文字母");
    } else {
      callback();
    }
  };

  checkPassword = (rule, value, callback) => {
    if (!value) {
      callback("请输入密码!");
    } else {
      callback();
    }
  };

  render() {
    const { getFieldDecorator } = this.props.form;
    return (
      <Form className="login-form">
        <FormItem>
          {getFieldDecorator("username", {
            initialValue: "",
            rules: [{ validator: this.checkUsername }],
          })(<Input placeholder="用户名" />)}
        </FormItem>
        <FormItem>
          {getFieldDecorator("password", {
            initialValue: "",
            rules: [{ validator: this.checkPassword }],
          })(<Input type="password" placeholder="密码" />)}
        </FormItem>
        <FormItem>
          <Button
            type="primary"
            onClick={this.loginSubmit}
            className="login-form-button"
          >
            登录
          </Button>
        </FormItem>
      </Form>
    );
  }
}
LoginForm = Form.create({})(LoginForm);

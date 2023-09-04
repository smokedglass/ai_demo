import React from "react";
import { Menu, Icon, Upload, Button, Tooltip, message } from "antd";
import axios from "./../../axios/index";
import { connect } from "react-redux";
import { switchMenu, saveBtnList } from "./../../redux/action";
import "./index.less";
import { getUserName } from "../../utils/data";
const { Dragger } = Upload;
const name = getUserName();
const props = {
  name: "file",
  action: "https://localhost:3000/file/upload",
  multiple: true,
  headers: {
    authorization: "authorization-text",
  },
  data: {
    userName: name,
  },
  onChange(info) {
    if (info.file.status !== "uploading") {
      console.log(info.file, info.fileList);
    }
    if (info.file.status === "done") {
      message.success(`${info.file.name} file uploaded successfully`);
    } else if (info.file.status === "error") {
      message.error(`${info.file.name} file upload failed.`);
    }
  },
};
class uploadTxt extends React.Component {
  render() {
    return (
      <div className="uploadTxt-box">
        <Dragger {...props} className="uploadTxt-txt">
          <Tooltip title="请上传需要处理的文件">
            <Button>
              <Icon type="upload" /> 点击上传文件
            </Button>
          </Tooltip>
        </Dragger>
      
      </div>
    );
  }
}
export default connect()(uploadTxt);

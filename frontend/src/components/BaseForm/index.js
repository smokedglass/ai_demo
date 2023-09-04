import React from "react";
import {
  Input,
  Select,
  Form,
  Button,
  Table,
  Modal,
  Tooltip,
  Icon,
  message,
  Pagination,
} from "antd";
import axios from "axios";
import { getToken } from "../../utils/data";
const FormItem = Form.Item;
const Option = Select.Option;

// updateTable = (resdata) => {
//   this.setState({
//     data: resdata,
//   });
// }
class FilterForm extends React.Component {
  updateForm = this.props.updateForm;
  updateTableSuccess = this.props.updateTableSuccess;
  columns = [
    {
      title: "原始文件名",
      dataIndex: "fileName",
      key: "fileName",
      align: "center",
    },
    {
      title: "错误文件名",
      dataIndex: "errFileName",
      key: "errFileName",
      align: "center",
      // render: (val, val2) => {
      //   (val2.errFileName !== null) ? (
      //     <span>{val2.errFileName}</span>
      //   ) : (
      //     <Tooltip>
      //       <Icon
      //         type="question-circle"
      //         title="未进行扫描"
      //         style={{ margin: "5px" }}
      //       />
      //     </Tooltip>
      //   );
      // },
    },
    {
      title: "操作人",
      dataIndex: "userName",
      key: "userName",
      align: "center",
    },
    {
      title: "操作",
      dataIndex: "Operation",
      key: "Operation",
      align: "center",
      render: (val1, val2) => (
        <div>
          <Button onClick={this.downloadFile.bind(this, val1, val2)}>下载文件</Button>{" "}
          <Button onClick={this.detailPageShow.bind(this, val2)}>
            查看明细
          </Button>
        </div>
      ),
    },
  ];
  columns2 = [
    // {
    //   title: "文件大小",
    //   dataIndex: "fileSize",
    //   key: "fileSize",
    // },
    {
      title: "原文件汇总笔数",
      dataIndex: "totalRows",
      key: "totalRows",
    },
    {
      title: "原文件汇总金额",
      dataIndex: "totalMoney",
      key: "totalMoney",
    },
    {
      title: "错误格式汇总笔数",
      dataIndex: "errTotalRows",
      key: "",
    },
    {
      title: "错误格式汇总金额",
      dataIndex: "errTotalMoney",
      key: "errTotalMoney",
    },
  ];
  state = {
    data: [],
    data2: [],
    isLoading: false,
    isdetailShow: false,
    isUpdateTable: false,
    pagination: {},
    userName: "",
  };

  // 调用接口，展示表格
  pageLoad(pageNo = 1, userName) {
    let token = localStorage.getItem("token");
    this.setState({
      isLoading: true,
    });
    axios({
      method: "post",
      url: "http://localhost:3000/manage/getFileLog/page",
      data: {
        pageNo: pageNo - 1,
        pageSize: "10",
        userName: userName,
      },
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token,
      },
    }).then((res) => {
      if (res.status == "200") {
        if (res.data.success == true) {
          console.log('res', res);
          this.updateTable(res.data.data.rows);
          this.setState({
            isLoading: false,
            pagination: {
              current: pageNo,
              pageSize: 10,
              total: res.data.data.totalNum,
            },
          });
        } else {
          message.info(res.data.message, 3);
        }
      }
    });
  }

  componentDidMount() {
    // 后端：调用接口，刷新表格
    this.pageLoad(1);
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.updateForm == true) {
      this.pageLoad(1, this.state.userName);
      this.updateTableSuccess();
    }
  }

  componentDidUpdate(prevProps, prevState) {
    if (prevState.pagination.current !== this.state.pagination.current) {
      this.pageLoad(this.state.pagination.current, this.state.userName);
    }
  }

  downloadFile = (text, text2) => {
    let token = localStorage.getItem("token");
    console.log('text2', text2.errFileName);
    window.location.href = "http://localhost:8080/file/download?fileName=" + text2.errFileName;
    // axios({
    //   method: "get",
    //   // url: "http://localhost:3000/file/download?fileName=" + text2.errFileName,
    //   url: "http://localhost:3000/file/download?fileName=" + text2.errFileName,
    //   headers: {
    //     Authorization: "Bearer " + token,
    //   },
    // }).then((res) => {
    //   console.log('resss', res);
    //   if (res.status == "200") {
    //     if (res.data.success == true) {
    //       message.success(res.data.message, 3);
    //     } else {
    //       message.info(res.data.message, 3);
    //     }
    //   }
    // });
  };

  detailPageShow = (val) => {
    if (val.errDetailId != null) {
      this.setState({
        isdetailShow: !this.state.isdetailShow,
        data2: [val.errFileDetail],
      });
    } else {
      message.info("该文件没有文件明细");
    }
  };

  handleFilterSubmit = () => {
    let fieldsValue = this.props.form.getFieldsValue();
    this.setState({
      userName: fieldsValue.userName,
    });
    this.pageLoad(1, fieldsValue.userName);
    // this.props.filterSubmit(fieldsValue);
    // 后端调用接口，查询，更新表格
  };

  reset = () => {
    this.props.form.resetFields();
    this.setState(
      {
        userName: "",
      },
      function () {
        this.pageLoad(1);
      }
    );
  };

  updateTable = (resData) => {
    console.log('resdata', resData)
    this.setState({
      data: resData,
    });
  };

  handleCancel = () => {
    this.setState({
      isdetailShow: false,
    });
  };

  handleOk = () => {
    this.setState({
      isdetailShow: false,
    });
  };
  handleTableChange = (pagination, filters, sorter) => {
    this.setState({
      pagination: pagination,
    });
    // this.pageLoad()
    // this.fetch({
    //   results: pagination.pageSize,
    //   page: pagination.current,
    //   sortField: sorter.field,
    //   sortOrder: sorter.order,
    //   ...filters,
    // });
  };

  // 每次调用完表的接口，更新表格展示的数据

  render() {
    const { getFieldDecorator } = this.props.form;
    return (
      <Form layout="inline" style={{ margin: "8px", padding: "8px" }}>
        <FormItem>
          <Tooltip>
            <Icon
              type="question-circle"
              title="查询操作人上传的文件"
              style={{ margin: "5px" }}
            />
          </Tooltip>
          {getFieldDecorator("userName", {
            initialValue: "",
          })(<Input placeholder="查询操作人" style={{ width: "50%" }} />)}
          <Button
            type="primary"
            style={{ margin: "0 12px" }}
            onClick={this.handleFilterSubmit.bind(this)}
          >
            查询
          </Button>
          <Button onClick={this.reset.bind(this)}>重置</Button>
        </FormItem>
        <Modal
          title="查看文件明细"
          visible={this.state.isdetailShow}
          onOk={this.handleOk.bind(this)}
          onCancel={this.handleCancel.bind(this)}
        >
          <Table columns={this.columns2} dataSource={this.state.data2} />
        </Modal>
        <Table
          columns={this.columns}
          dataSource={this.state.data}
          style={{ margin: "10px" }}
          loading={this.state.isLoading}
          pagination={this.state.pagination}
          onChange={this.handleTableChange}
        />
      </Form>
    );
  }
}
export default Form.create({})(FilterForm);

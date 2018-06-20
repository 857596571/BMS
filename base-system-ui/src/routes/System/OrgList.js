import React, { PureComponent, Fragment } from 'react';
import { connect } from 'dva';
import {
  Row,
  Col,
  Card,
  Form,
  Input,
  InputNumber,
  Select,
  Button,
  Modal,
  message,
  Badge,
  Table,
  Divider,
} from 'antd';
import PageHeaderLayout from '../../layouts/PageHeaderLayout';
import Dict from '../../components/Dict';

import styles from './OrgList.less';

const FormItem = Form.Item;
const { Option } = Select;
const getValue = obj =>
  Object.keys(obj)
    .map(key => obj[key])
    .join(',');

const formItemLayout = {
  labelCol: {
    span: 8,
  },
  wrapperCol: {
    span: 14,
  },
}
const stateMap = ['error', 'success'];
const state = ['停用', '启用'];
const CreateForm = Form.create()(props => {
  const { modalVisible, form, item, codeExists, handleAdd, handleModalVisible, dispatch } = props;
  const okHandle = () => {
    form.validateFields((err, fieldsValue) => {
      if (err) return;
      form.resetFields();
      handleAdd(fieldsValue);
    });
  };
  return (
    <Modal
      title="新建"
      visible={modalVisible}
      onOk={okHandle}
      onCancel={() => handleModalVisible()}
    >
      <Row>
        <Col span={12}>
          <FormItem label="父部门：" hasFeedback {...formItemLayout}>
            {(<span>{item.parentName || '无'}</span>)}
          </FormItem>
        </Col>
        <Col span={12}>
          <FormItem label="部门类型：" hasFeedback {...formItemLayout}>
            {form.getFieldDecorator('type', {
              initialValue: item.type ? item.type+"" : "",
              rules: [
                {
                  required: true,
                  message: '请选择部门类型',
                },
              ],
            })(
              <Dict code={12} />
            )}
          </FormItem>
        </Col>
      </Row>
      <Row>
        <Col span={12}>
          <FormItem label="机构名称：" hasFeedback {...formItemLayout}>
            {form.getFieldDecorator('name', {
              initialValue: item.name,
              rules: [
                {
                  required: true,
                  message: '请输入机构名称',
                },
              ],
            })(
              <Input />
            )}
          </FormItem>
        </Col>
        <Col span={12}>
          <FormItem label="机构编码：" hasFeedback {...formItemLayout}>
            {form.getFieldDecorator('code', {
              initialValue: item.code,
              rules: [
                {
                  required: true,
                  message: '请输入机构编码',
                  validator(rule, value, callback) {
                    dispatch({
                      type: 'sysOrg/isCodeExists',
                      payload: {
                        code: value,
                        id: item.id
                      }
                    });
                    if (codeExists) callback(true);
                    else callback(false);
                  }
                },
              ],
            })(
              <Input  />
            )}
          </FormItem>
        </Col>
      </Row>
      <Row>
        <Col span={12}>
          <FormItem label="机构状态：" hasFeedback {...formItemLayout}>
            {form.getFieldDecorator('state', {
              initialValue: item.state,
              rules: [
                {
                  required: true,
                  message: '请选择机构状态',
                },
              ],
            })(
              <Input />
            )}
          </FormItem>
        </Col>
        <Col span={12}>
          <FormItem label="机构排序：" hasFeedback {...formItemLayout}>
            {form.getFieldDecorator('sort', {
              initialValue: item.sort,
              rules: [
                {
                  required: true,
                  message: '请输入机构顺序',
                },
              ],
            })(
              <InputNumber min={0}  />
            )}
          </FormItem>
        </Col>
      </Row>
      <Row>
        <Col span={24}>
          <FormItem label="备注：" hasFeedback labelCol={{span: 4}} wrapperCol={{span: 19}} >
            {form.getFieldDecorator('remarks', {
              initialValue: item.remarks,
              rules: [
                {
                  message: '请输入机构名称',
                },
              ],
            })(
              <Input.TextArea rows={2} />
            )}
          </FormItem>
        </Col>
      </Row>
    </Modal>
  );
});

@connect(({ sysOrg, loading }) => ({
  sysOrg,
  loading: loading.models.sysOrg,
}))
@Form.create()
export default class OrgList extends PureComponent {
  state = {
    modalVisible: false,
    formValues: {},
    item: {},
  };

  componentDidMount() {
    const { dispatch } = this.props;
    dispatch({
      type: 'sysOrg/getList',
    });
  }

  handleFormReset = () => {
    const { form, dispatch } = this.props;
    form.resetFields();
    this.setState({
      formValues: {},
    });
    dispatch({
      type: 'org/getList',
      payload: {},
    });
  };

  handleSearch = e => {
    e.preventDefault();

    const { dispatch, form } = this.props;

    form.validateFields((err, fieldsValue) => {
      if (err) return;

      const values = {
        ...fieldsValue,
        updatedAt: fieldsValue.updatedAt && fieldsValue.updatedAt.valueOf(),
      };

      this.setState({
        formValues: values,
      });

      dispatch({
        type: 'org/getList',
        payload: values,
      });
    });
  };

  handleModalVisible = (flag, item) => {
    console.log(!item || !flag)
    if(!item || !flag) item = {}
    this.setState({
      modalVisible: !!flag,
      item,
    });
  };

  handleAdd = fields => {
    this.props.dispatch({
      type: 'org/add',
      payload: {
        description: fields.desc,
      },
    });

    message.success('添加成功');
    this.setState({
      modalVisible: false,
    });
  };

  render() {
    const { sysOrg: { list, codeExists }, loading } = this.props;
    const {  modalVisible, item } = this.state;

    const columns = [
      {
        title: '名称',
        key: 'name',
        dataIndex: 'name',
      },
      {
        title: '类型',
        dataIndex: 'type',
        render(val) {
          return <Dict code={12} codeValue={val} info={true}/>;
        }
      },
      {
        title: '状态',
        key: 'state',
        dataIndex: 'state',
        render(val) {
          return <Badge status={stateMap[val]} text={state[val]} />;
        },
      },
      {
        title: '排序',
        key: 'sort',
        dataIndex: 'sort',
      },
      {
        title: '备注',
        key: 'remarks',
        dataIndex: 'remarks',
      },
      {
        title: '操作',
        width: 240,
        render: (val, obj) => (
          <Fragment>
            <a href="javascript: void(0)" onClick={()=>this.handleModalVisible(true, obj)}>修改</a>
            <Divider type="vertical" />
            <a href="">启用</a>
            <Divider type="vertical" />
            <a href="">删除</a>
            <Divider type="vertical" />
            <a href="">新建子机构</a>
          </Fragment>
        ),
      },
    ];

    const parentMethods = {
      dispatch: this.props.dispatch,
      handleAdd: this.handleAdd,
      handleModalVisible: this.handleModalVisible,
    };

    return (
      <PageHeaderLayout>
        <Card bordered={false}>
          <div className={styles.tableList}>
            <div className={styles.tableListOperator}>
              <Button icon="plus" type="primary" onClick={() => this.handleModalVisible(true)}>
                新建
              </Button>
            </div>
            <Table
              loading={loading}
              dataSource={list}
              columns={columns}
              pagination={false}
              rowKey={item=>item.id}
            />
          </div>
        </Card>
        <CreateForm {...parentMethods} item={item} codeExists={codeExists} modalVisible={modalVisible} />
      </PageHeaderLayout>
    );
  }
}

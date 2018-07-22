import React, { Fragment, PureComponent } from 'react';
import { connect } from 'dva';
import {
  Badge,
  Button,
  Card,
  Col,
  Divider,
  Form,
  Input,
  InputNumber,
  Radio,
  message,
  Modal,
  Row,
  Select,
  Table,
  TreeSelect,
  Tree,
  Popconfirm,
} from 'antd';
import PageHeaderLayout from '../../layouts/PageHeaderLayout';
import styles from './System.less';
import Dict from '../../components/Dict';
import * as system from '../../services/system';

const FormItem = Form.Item;
const TreeNode = Tree.TreeNode;

const statusMap = { ON: 'success', OFF: 'error' };

const formItemLayout = {
  labelCol: {
    span: 8,
  },
  wrapperCol: {
    span: 14,
  },
};

@connect(({ sysUser, sysOrg, sysRole, loading }) => ({
  sysUser,
  sysOrg,
  sysRole,
  loading: loading.models.sysUser,
}))
@Form.create()
export default class UserList extends PureComponent {
  state = {
    modalVisible: false,
    passwordModalVisible: false,
    assignModalVisible: false,
    itemType: 'create',
    item: {},
    orgId: '',
  };

  componentDidMount() {
    this.initQuery();
  }

  initQuery = () => {
    const { dispatch } = this.props;
    dispatch({
      type: 'sysOrg/getList',
    });
    this.initQueryUser();
    dispatch({
      type: 'sysRole/getList',
    });
  };

  initQueryUser = selectKeys => {
    const orgId =
      selectKeys && selectKeys.length > 0 ? (selectKeys[0] !== '2' ? selectKeys[0] : '') : '';
    this.setState({
      orgId: orgId,
    });
    const { dispatch, form } = this.props;
    dispatch({
      type: 'sysUser/getList',
      payload: {
        orgId: orgId,
        ...form.getFieldsValue(),
      },
    });
  };

  handleFormReset = () => {
    this.props.form.resetFields();
  };

  handleModalVisible = (flag, itemType, item) => {
    if (itemType === 'create') item = { orgId: this.state.orgId };
    if (!flag) item = {};
    this.setState({
      modalVisible: flag,
      itemType: itemType,
      item,
    });
  };

  handlePasswordModalVisible = (flag, item) => {
    if (!flag) item = {};
    this.setState({
      passwordModalVisible: flag,
      item,
    });
  };

  handleAdd = fields => {
    this.props.dispatch({
      type: 'sysUser/add',
      payload: {
        ...fields,
      },
      callback: () => this.initQuery(),
    });
    this.setState({
      modalVisible: false,
    });
  };

  handleResetPassword = record => {
    this.props.dispatch({
      type: 'sysUser/resetPassword',
      payload: {
        id: record.id,
        type: 'INIT',
      },
    });
  };

  handleDelete(record) {
    this.props.dispatch({
      type: 'sysUser/delete',
      payload: record.id,
      callback: () => this.initQuery(),
    });
  }

  handleChangeState(record) {
    this.props.dispatch({
      type: 'sysUser/updateStates',
      payload: {
        id: record.id,
        state: record.state === 'ON' ? 'OFF' : 'ON',
      },
      callback: () => this.initQuery(),
    });
  }

  handlePageChange = page => {
    this.props.dispatch({
      type: 'sysUser/getList',
      payload: {
        pageNum: page.current,
        pageSize: page.pageSize,
        ...this.props.form.getFieldsValue(),
      },
    });
  };

  getSelectTreeData = list => {
    return list.map(item => {
      if (item.children && item.children.length > 0) {
        return {
          label: item.name,
          value: item.id,
          key: item.id,
          children: this.getSelectTreeData(item.children),
        };
      }
      return {
        label: item.name,
        value: item.id,
        key: item.id,
      };
    });
  };

  render() {
    const { sysUser: { list = [], pagination = {}, currentUser }, loading, form } = this.props;
    const { modalVisible, passwordModalVisible, itemType, item } = this.state;

    const columns = [
      {
        title: '用户名称',
        key: 'name',
        dataIndex: 'name',
      },
      {
        title: '所属机构',
        key: 'orgName',
        dataIndex: 'orgName',
      },
      {
        title: '登录账号',
        key: 'loginName',
        dataIndex: 'loginName',
      },
      {
        title: '用户类型',
        key: 'typeDesc',
        dataIndex: 'typeDesc',
      },
      {
        title: '电子邮箱',
        key: 'email',
        dataIndex: 'email',
      },
      {
        title: '手机',
        key: 'mobile',
        dataIndex: 'mobile',
      },
      {
        title: '状态',
        key: 'stateDesc',
        dataIndex: 'stateDesc',
        render: (val, record) => <Badge status={statusMap[record.state]} text={val} />,
      },
      {
        title: '备注',
        key: 'remarks',
        dataIndex: 'remarks',
      },
      {
        title: '操作',
        width: 240,
        render: (val, record) => (
          <Fragment>
            <a onClick={() => this.handleModalVisible(true, 'update', record)}>
              修改<Divider type="vertical" />
            </a>
            {record.state === 'ON' ? (
              <Popconfirm
                title="禁用该用户后该用户将无法登录系统，请谨慎使用！"
                placement="topRight"
                onConfirm={() => this.handleChangeState(record)}
              >
                <a>
                  禁用<Divider type="vertical" />
                </a>
              </Popconfirm>
            ) : (
              <Popconfirm
                title="启用该用户后该用户将正常登录系统，请谨慎使用！"
                placement="topRight"
                onConfirm={() => this.handleChangeState(record)}
              >
                <a>
                  启用<Divider type="vertical" />
                </a>
              </Popconfirm>
            )}
            {record.type === 'USER_SYSTEM' && (
              <Popconfirm
                title="删除该用户后将该用户将无法登录系统，请谨慎使用！"
                placement="topRight"
                onConfirm={() => this.handleDelete(record)}
              >
                <a>
                  删除<Divider type="vertical" />
                </a>
              </Popconfirm>
            )}

            <Popconfirm
              title="重置密码后将会自动把密码重置为默认密码，请谨慎使用！"
              placement="topRight"
              onConfirm={() => this.handleResetPassword(record)}
            >
              <a>重置密码</a>
            </Popconfirm>
          </Fragment>
        ),
      },
    ];

    const createModalProps = {
      item,
      itemType,
      currentUser,
      selectTreeData: this.getSelectTreeData(this.props.sysOrg.list),
      roleList: this.props.sysRole.list,
      visible: modalVisible,
      dispatch: this.props.dispatch,
      handleAdd: this.handleAdd,
      handleModalVisible: () => this.handleModalVisible(false),
    };

    const renderTreeNodes = data => {
      return data.map(item => {
        if (item.children && item.children.length > 0) {
          return (
            <TreeNode title={item.name} key={item.id} dataRef={item}>
              {renderTreeNodes(item.children)}
            </TreeNode>
          );
        }
        return <TreeNode title={item.name} key={item.id} />;
      });
    };

    const CreateFormGen = () => <CreateForm {...createModalProps} />;

    return (
      <PageHeaderLayout>
        <Row gutter={16}>
          <Col span={6}>
            <Card bordered={false}>
              <Tree defaultExpandedKeys={['2']} onSelect={this.initQueryUser}>
                {renderTreeNodes(this.props.sysOrg.list)}
              </Tree>
            </Card>
          </Col>
          <Col span={18}>
            <Card bordered={false}>
              <div className={styles.tableList}>
                <div className={styles.tableListForm}>
                  <Form onSubmit={this.handleSearch} layout="inline">
                    <Row gutter={{ md: 8, lg: 24, xl: 48 }}>
                      <Col md={6} sm={24}>
                        <FormItem>
                          {form.getFieldDecorator('searchKeys')(
                            <Input placeholder="请输入需查询的用户名称、登录账号" />
                          )}
                        </FormItem>
                      </Col>
                      <Col md={6} sm={24}>
                        <FormItem>
                          {form.getFieldDecorator('state', {
                            initialValue: '',
                          })(<Dict code={'STATE'} radio query />)}
                        </FormItem>
                      </Col>
                      <Col md={6} sm={24}>
                        <FormItem>
                          {form.getFieldDecorator('type', {
                            initialValue: '',
                          })(<Dict code={'USER_TYPE'} radio query />)}
                        </FormItem>
                      </Col>
                      <Col md={6} sm={24}>
                        <span className={styles.submitButtons}>
                          <Button type="primary" onClick={() => this.initQuery()}>
                            查询
                          </Button>
                          <Button style={{ marginLeft: 8 }} onClick={this.handleFormReset}>
                            重置
                          </Button>
                        </span>
                      </Col>
                    </Row>
                  </Form>
                </div>
                <div className={styles.tableListOperator}>
                  <Button type="primary" onClick={() => this.handleModalVisible(true, 'create')}>
                    新建
                  </Button>
                </div>
                <Table
                  loading={loading}
                  dataSource={list}
                  columns={columns}
                  pagination={pagination}
                  onChange={this.handlePageChange}
                  rowKey={item => item.id}
                />
              </div>
            </Card>
          </Col>
        </Row>
        <CreateFormGen />
      </PageHeaderLayout>
    );
  }
}

const CreateForm = Form.create()(props => {
  const {
    visible,
    form,
    itemType,
    item,
    roleList,
    currentUser,
    selectTreeData,
    handleAdd,
    handleModalVisible,
  } = props;

  const okHandle = () => {
    form.validateFields((err, fieldsValue) => {
      if (err) return;
      form.resetFields();
      let formValues = {
        ...fieldsValue,
        id: item.id,
        state: item.state || 'ON',
        type: item.type || 'USER_SYSTEM',
      };
      delete formValues.resPassword;
      if (itemType === 'update') delete formValues.password;
      if (formValues.rolesTemp && formValues.rolesTemp.length > 0) {
        formValues.roles = formValues.rolesTemp.map(item => {
          return { id: item };
        });
        delete formValues.rolesTemp;
      }
      handleAdd(formValues);
    });
  };

  const modalProps = {
    title: '新建',
    visible,
    onOk: okHandle,
    onCancel: handleModalVisible,
  };

  if (itemType === 'create') modalProps.title = '新建';
  if (itemType === 'update') {
    modalProps.title = '修改';
  }

  const defaultRoles = item.roleIds ? item.roleIds.split(',') : undefined;

  return (
    <Modal {...modalProps}>
      <Form>
        <Row>
          <Col span={12}>
            <FormItem label="用户名称：" hasFeedback {...formItemLayout}>
              {form.getFieldDecorator('name', {
                initialValue: item.name,
                rules: [
                  {
                    required: true,
                    message: '请输入用户名称',
                  },
                ],
              })(<Input />)}
            </FormItem>
          </Col>
          <Col span={12}>
            <FormItem label="所属机构：" hasFeedback {...formItemLayout}>
              {form.getFieldDecorator('orgId', {
                initialValue: item.orgId,
                rules: [
                  {
                    required: true,
                    message: '请选择所属机构',
                  },
                ],
              })(
                <TreeSelect
                  style={{ width: '100%' }}
                  dropdownStyle={{ maxHeight: 400, overflow: 'auto' }}
                  treeData={selectTreeData}
                  treeDefaultExpandAll
                />
              )}
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span={12}>
            <FormItem label="登录账号：" hasFeedback {...formItemLayout}>
              {form.getFieldDecorator('loginName', {
                initialValue: item.loginName,
                rules: [
                  {
                    required: true,
                    message: '请输入登录账号',
                  },
                  {
                    async validator(rule, value, callback) {
                      const data = await system.isUserExists({
                        loginName: value,
                        id: item.id,
                      });
                      if (data.data) callback('该登录账号已存在');
                      callback();
                    },
                  },
                ],
              })(<Input disabled={item.id === '1'} />)}
            </FormItem>
          </Col>
          <Col span={12}>
            <FormItem label="所属角色：" hasFeedback {...formItemLayout}>
              {form.getFieldDecorator('rolesTemp', {
                initialValue: defaultRoles,
                rules: [
                  {
                    required: true,
                    message: '请选择角色',
                  },
                ],
              })(
                <Select style={{ width: '100%' }} mode={'multiple'}>
                  {roleList &&
                    roleList.map(item => (
                      <Select.Option key={item.id} vlaue={item.id}>
                        {item.name}
                      </Select.Option>
                    ))}
                </Select>
              )}
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span={12}>
            <FormItem label="电子邮件：" hasFeedback {...formItemLayout}>
              {form.getFieldDecorator('email', {
                initialValue: item.email,
                rules: [
                  {
                    type: 'email',
                    message: '请输入正确的电子邮件地址',
                  },
                ],
              })(<Input />)}
            </FormItem>
          </Col>
          <Col span={12}>
            <FormItem label="手机号码：" hasFeedback {...formItemLayout}>
              {form.getFieldDecorator('mobile', {
                initialValue: item.mobile,
                rules: [
                  {
                    message: '请输入手机号码',
                  },
                  {
                    validator: (rule, value, callback) => {
                      if (value && !/^1[123456789]\d{9}$/.test(value)) {
                        callback('输入的手机号码有误');
                      }
                      callback();
                    },
                  },
                ],
              })(<Input />)}
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span={24}>
            <FormItem label="备注：" hasFeedback labelCol={{ span: 4 }} wrapperCol={{ span: 19 }}>
              {form.getFieldDecorator('remarks', {
                initialValue: item.remarks,
              })(<Input.TextArea rows={2} />)}
            </FormItem>
          </Col>
        </Row>
      </Form>
    </Modal>
  );
});

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
import Dict from '../../components/Dict';
import styles from './System.less';
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

@connect(({ sysRole, sysOrg, sysMenu, sysUser, loading }) => ({
  sysRole,
  sysOrg,
  sysMenu,
  sysUser,
  loading: loading.models.sysRole,
}))
@Form.create()
export default class RoleList extends PureComponent {
  state = {
    modalVisible: false,
    authModalVisible: false,
    assignModalVisible: false,
    itemType: 'create',
    item: {},
  };

  componentDidMount() {
    this.initQuery();
  }

  initQuery = () => {
    const { dispatch, form } = this.props;
    dispatch({
      type: 'sysRole/getList',
      payload: form.getFieldsValue(),
    });
    dispatch({
      type: 'sysOrg/getList',
    });
    dispatch({
      type: 'sysMenu/getList',
    });
  };

  initAssginData = (orgId, roleId) => {
    const { dispatch } = this.props;
    dispatch({
      type: 'sysUser/getList',
      payload: {
        orgId,
        roleId,
      },
    });

    dispatch({
      type: 'sysUser/getUsersByRoleId',
      payload: {
        roleId,
      },
    });
  };

  handleModalVisible = (flag, itemType, item) => {
    if (itemType === 'create') item = {};
    if (!flag) item = {};
    this.setState({
      modalVisible: flag,
      itemType: itemType,
      item,
    });
  };

  handleAuthModalVisible = (flag, item) => {
    if (!flag) item = {};
    this.setState({
      authModalVisible: flag,
      item,
    });
  };

  handleAssignModalVisible = (flag, item) => {
    if (!flag) item = {};
    else this.initAssginData('', item.id);
    this.setState({
      assignModalVisible: flag,
      item,
    });
  };

  handleAdd = fields => {
    this.props.dispatch({
      type: 'sysRole/add',
      payload: {
        ...fields,
      },
      callback: () => this.initQuery(),
    });
    this.setState({
      modalVisible: false,
    });
  };

  handleAuth = fields => {
    if (fields.menus && fields.menus.length > 0) {
      this.props.dispatch({
        type: 'sysRole/saveMenuAuth',
        payload: {
          ...fields,
        },
      });
      this.setState({
        authModalVisible: false,
      });
    } else {
      message.error('请勾选需要赋权的菜单');
    }
  };

  handleDelete(record) {
    this.props.dispatch({
      type: 'sysRole/delete',
      payload: record.id,
      callback: () => this.initQuery(),
    });
  }

  handleChangeState(record) {
    this.props.dispatch({
      type: 'sysRole/updateStates',
      payload: {
        id: record.id,
        state: record.state === 'ON' ? 'OFF' : 'ON',
      },
      callback: () => this.initQuery(),
    });
  }

  handleOrgUserPageChange = (page, param) => {
    this.props.dispatch({
      type: 'sysUser/getList',
      payload: {
        pageNum: page.current,
        pageSize: page.pageSize,
        ...param,
      },
    });
  };

  handleMenuClick = (flag, row, item, orgId) => {
    let param = {
      id: item.id,
      userId: row.id,
    };
    if (flag === 'add') {
      this.props.dispatch({
        type: 'sysRole/assignRole',
        payload: param,
        callback: () => this.initAssginData(orgId, item.id),
      });
    } else {
      this.props.dispatch({
        type: 'sysRole/deleteRoleUser',
        payload: param,
        callback: () => this.initAssginData(orgId, item.id),
      });
    }
  };

  render() {
    const { sysRole: { list = [] }, sysUser: { currentUser }, loading } = this.props;
    const { modalVisible, authModalVisible, assignModalVisible, itemType, item } = this.state;

    let listFilter = list;
    if (!currentUser.admin) {
      listFilter = list.filter(item => item.id !== '1');
    }

    const columns = [
      {
        title: '角色名称',
        key: 'name',
        dataIndex: 'name',
      },
      {
        title: '角色编码',
        key: 'code',
        dataIndex: 'code',
      },
      {
        title: '角色级别',
        key: 'levelDesc',
        dataIndex: 'levelDesc',
      },
      {
        title: '数据范围',
        key: 'dataScopeDesc',
        dataIndex: 'dataScopeDesc',
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
        width: 300,
        render: (val, record) => (
          <Fragment>
            <a onClick={() => this.handleModalVisible(true, 'update', record)}>
              修改<Divider type="vertical" />
            </a>
            {record.state === 'ON' ? (
              <Popconfirm
                title="禁用该角色后该角色下的所有用户将无法登录系统，请谨慎使用?"
                placement="topRight"
                onConfirm={() => this.handleChangeState(record)}
              >
                <a>
                  禁用<Divider type="vertical" />
                </a>
              </Popconfirm>
            ) : (
              <Popconfirm
                title="启用该角色后该角色下的所有用户将正常登录系统，请谨慎使用?"
                placement="topRight"
                onConfirm={() => this.handleChangeState(record)}
              >
                <a>
                  启用<Divider type="vertical" />
                </a>
              </Popconfirm>
            )}
            {currentUser.admin && (
              <Popconfirm
                title="删除该角色后该角色下的所有用户将无法登录系统，请谨慎使用?"
                placement="topRight"
                onConfirm={() => this.handleDelete(record)}
              >
                <a>
                  删除<Divider type="vertical" />
                </a>
              </Popconfirm>
            )}
            {record.id !== '1' && (
              <a onClick={() => this.handleAuthModalVisible(true, record)}>
                权限设置<Divider type="vertical" />
              </a>
            )}
            {record.id !== '1' && (
              <a onClick={() => this.handleAssignModalVisible(true, record)}>关联用户</a>
            )}
          </Fragment>
        ),
      },
    ];

    const createModalProps = {
      item,
      itemType,
      currentUser,
      orgTree: this.props.sysOrg.list,
      visible: modalVisible,
      dispatch: this.props.dispatch,
      handleAdd: this.handleAdd,
      handleModalVisible: () => this.handleModalVisible(false),
    };

    const createAuthModalProps = {
      item,
      currentUser,
      menuTree: this.props.sysMenu.list,
      visible: authModalVisible,
      dispatch: this.props.dispatch,
      handleAdd: this.handleAuth,
      handleModalVisible: () => this.handleAuthModalVisible(false),
    };

    const createAssignModalProps = {
      item,
      currentUser,
      orgTree: this.props.sysOrg.list,
      pagination: this.props.sysUser.pagination,
      orgUserList: this.props.sysUser.list,
      roleUserList: this.props.sysUser.roleUserList,
      visible: assignModalVisible,
      dispatch: this.props.dispatch,
      handlePageChange: this.handleOrgUserPageChange,
      handleMenuClick: this.handleMenuClick,
      handleOrgUserChange: this.initAssginData,
      handleModalVisible: () => this.handleAssignModalVisible(false),
    };

    const CreateAuthFormGen = () => <CreateAuthForm {...createAuthModalProps} />;

    return (
      <PageHeaderLayout>
        <Card bordered={false}>
          <div className={styles.tableList}>
            <div className={styles.tableListOperator}>
              <Button type="primary" onClick={() => this.handleModalVisible(true, 'create')}>
                新建
              </Button>
            </div>
            <Table
              loading={loading}
              dataSource={listFilter}
              columns={columns}
              pagination={false}
              rowKey={item => item.id}
            />
          </div>
        </Card>
        <CreateForm {...createModalProps} />
        <CreateAuthFormGen />
        <CreateAssignForm {...createAssignModalProps} />
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
    orgTree,
    currentUser,
    handleAdd,
    handleModalVisible,
  } = props;

  const okHandle = () => {
    form.validateFields((err, fieldsValue) => {
      if (err) return;
      form.resetFields();
      handleAdd({
        ...fieldsValue,
        id: item.id,
        state: item.state || 'ON',
      });
    });
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

  const handleOrgTreeCheck = checkedKeys => {
    form.setFieldsValue({
      orgs: checkedKeys.map(val => {
        return { id: val };
      }),
    });
  };

  const checkedKeys = item.orgIds ? item.orgIds.split(',') : [];

  const modalProps = {
    title: '新建',
    visible,
    onOk: okHandle,
    onCancel: handleModalVisible,
  };

  if (itemType === 'create') modalProps.title = '新建';
  if (itemType === 'update') modalProps.title = '修改';

  return (
    <Modal {...modalProps}>
      <Row>
        <Col span={12}>
          <FormItem label="角色名称：" hasFeedback {...formItemLayout}>
            {form.getFieldDecorator('name', {
              initialValue: item.name,
              rules: [
                {
                  required: true,
                  message: '请输入菜单名称',
                },
              ],
            })(<Input />)}
          </FormItem>
        </Col>
        <Col span={12}>
          <FormItem label="角色编码：" hasFeedback {...formItemLayout}>
            {form.getFieldDecorator('code', {
              initialValue: item.code,
              rules: [
                {
                  required: true,
                  message: '请输入角色编码',
                },
                {
                  async validator(rule, value, callback) {
                    const data = await system.isRoleCodeExists({
                      code: value,
                      id: item.id,
                    });
                    if (data.data) callback('该角色编码已存在');
                    callback();
                  },
                },
              ],
            })(<Input />)}
          </FormItem>
        </Col>
      </Row>
      <Row>
        <Col span={12}>
          <FormItem label="数据范围：" hasFeedback {...formItemLayout}>
            {form.getFieldDecorator('dataScope', {
              initialValue: item.dataScope,
              rules: [
                {
                  required: true,
                  message: '请选择角色数据范围',
                },
              ],
            })(<Dict code={'DATA_SCOPE'} />)}
          </FormItem>
        </Col>
        <Col span={12}>
          <FormItem label="角色级别：" hasFeedback {...formItemLayout}>
            {form.getFieldDecorator('level', {
              initialValue: item.level || currentUser.admin ? 'SYSTEM' : 'BIZ',
              rules: [
                {
                  required: true,
                  message: '请选择角色级别',
                },
              ],
            })(
              <Dict
                code={'LEVEL'}
                excludeCodes={[!currentUser.admin && 'SYSTEM']}
                disabled={!currentUser.admin}
              />
            )}
          </FormItem>
        </Col>
      </Row>
      <Row>
        <Col span={24}>
          {form.getFieldValue('dataScope') === 'SCOPE_DETAIL' &&
            form.getFieldDecorator('orgs')(
              <Tree
                checkable
                defaultExpandAll
                onCheck={handleOrgTreeCheck}
                defaultCheckedKeys={checkedKeys}
              >
                {renderTreeNodes(orgTree)}
              </Tree>
            )}
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
    </Modal>
  );
});

const CreateAuthForm = Form.create()(props => {
  const { visible, form, item, menuTree, handleAdd, handleModalVisible } = props;

  const okHandle = () => {
    form.validateFields((err, fieldsValue) => {
      if (err) return;
      form.resetFields();
      handleAdd({
        ...fieldsValue,
        id: item.id,
      });
    });
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

  const handleMenuTreeCheck = (checkedKeys, e) => {
    form.setFieldsValue({
      menus: checkedKeys.concat(e.halfCheckedKeys).map(val => {
        return { id: val };
      }),
    });
  };

  const checkedKeys = item.menuIds
    ? item.menuIds
        .split(',')
        .filter(
          val =>
            menuTree.filter(item => item.children && item.children.length > 0 && item.id === val)
              .length === 0
        )
    : [];
  const modalProps = {
    title: '菜单权限设置',
    visible,
    onOk: okHandle,
    onCancel: handleModalVisible,
  };

  return (
    <Modal {...modalProps}>
      <Row>
        <Col span={24}>
          {form.getFieldDecorator('menus')(
            <Tree
              checkable
              defaultExpandAll
              onCheck={handleMenuTreeCheck}
              defaultCheckedKeys={checkedKeys}
            >
              {renderTreeNodes(menuTree)}
            </Tree>
          )}
        </Col>
      </Row>
    </Modal>
  );
});

const CreateAssignForm = Form.create()(props => {
  const {
    visible,
    item,
    orgTree,
    pagination,
    handleMenuClick,
    orgUserList,
    roleUserList,
    handleOrgUserChange,
    onPageChange,
    handleModalVisible,
  } = props;

  let orgId = '';

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

  const orgUserColumns = [
    {
      title: '姓名',
      dataIndex: 'name',
      key: 'name',
    },
    {
      title: '机构',
      dataIndex: 'orgName',
      key: 'orgName',
    },
    {
      title: '状态',
      key: 'stateDesc',
      dataIndex: 'stateDesc',
      render: (val, record) => <Badge status={statusMap[record.state]} text={val} />,
    },
    {
      title: '操作',
      key: 'operation',
      width: 80,
      render: (text, row) => (
        <span>
          {row.id !== '1' && (
            <Popconfirm
              title="关联该用户后该用户将具有本角色的权限，确定进行关联?"
              placement="topRight"
              onConfirm={() => handleMenuClick('add', row, item, orgId)}
            >
              <a>关联</a>
            </Popconfirm>
          )}
        </span>
      ),
    },
  ];

  const roleUserColumns = [
    {
      title: '姓名',
      dataIndex: 'name',
      key: 'name',
    },
    {
      title: '机构',
      dataIndex: 'orgName',
      key: 'orgName',
    },
    {
      title: '状态',
      key: 'stateDesc',
      dataIndex: 'stateDesc',
      render: (val, record) => <Badge status={statusMap[record.state]} text={val} />,
    },
    {
      title: '操作',
      key: 'operation',
      width: 80,
      render: (text, row) => (
        <span>
          {row.id !== '1' && (
            <Popconfirm
              title="异常该用户后该用户将失去本角色的权限，确定进行移除?"
              placement="topRight"
              onConfirm={() => handleMenuClick('remove', row, item, orgId)}
            >
              <a>移除</a>
            </Popconfirm>
          )}
        </span>
      ),
    },
  ];

  const modalProps = {
    title: '关联用户',
    visible,
    width: 1000,
    footer: null,
    onCancel: handleModalVisible,
  };

  return (
    <Modal {...modalProps}>
      <Divider orientation="left">待关联用户</Divider>
      <Row>
        <Col span={8}>
          <Tree
            defaultExpandedKeys={['2']}
            onSelect={value => {
              orgId = value.length > 0 ? value[0] : '';
              console.log(orgId);
              handleOrgUserChange(orgId, item.id);
            }}
          >
            {renderTreeNodes(orgTree)}
          </Tree>
        </Col>
        <Col span={16}>
          <Table
            columns={orgUserColumns}
            dataSource={orgUserList}
            onChange={onPageChange}
            pagination={pagination}
            rowKey={record => record.id}
          />
        </Col>
      </Row>
      <Divider orientation="left">已关联用户</Divider>
      <Table
        columns={roleUserColumns}
        dataSource={roleUserList}
        pagination={false}
        rowKey={record => record.id}
      />
    </Modal>
  );
});

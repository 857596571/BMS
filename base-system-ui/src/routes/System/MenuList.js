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
  Popconfirm,
} from 'antd';
import PageHeaderLayout from '../../layouts/PageHeaderLayout';
import Dict from '../../components/Dict';
import styles from './System.less';

const FormItem = Form.Item;

const statusMap = { ON: 'success', OFF: 'error' };

const formItemLayout = {
  labelCol: {
    span: 8,
  },
  wrapperCol: {
    span: 14,
  },
};

@connect(({ sysMenu, sysUser, loading }) => ({
  sysMenu,
  sysUser,
  loading: loading.models.sysMenu,
}))
@Form.create()
export default class MenuList extends PureComponent {
  state = {
    modalVisible: false,
    itemType: 'create',
    item: {},
    tempSorts: {},
  };

  componentDidMount() {
    this.initQuery();
  }

  initQuery = () => {
    const { dispatch, form } = this.props;
    dispatch({
      type: 'sysMenu/getList',
      payload: form.getFieldsValue(),
    });
  };

  handleModalVisible = (flag, itemType, item) => {
    if (itemType === 'create') item = {};
    if (itemType === 'sub')
      item = {
        parentId: item.id,
        parentName: item.name,
      };
    if (!flag) item = {};
    this.setState({
      modalVisible: flag,
      itemType: itemType,
      item,
    });
  };

  handleAdd = fields => {
    this.props.dispatch({
      type: 'sysMenu/add',
      payload: {
        ...fields,
      },
      callback: () => this.initQuery(),
    });
    this.setState({
      modalVisible: false,
    });
  };

  handleChangeSort(val, record) {
    let tempSorts = this.state.tempSorts;
    tempSorts[record.id.toString()] = { id: record.id, sort: val };
    if ((val || 0) === (record.sort || 0)) delete tempSorts[record.id];
    this.setState({ tempSorts: Object.assign({}, tempSorts) });
  }

  handleUpdateSorts() {
    let sorts = [];
    const { tempSorts } = this.state;
    Object.keys(tempSorts).map(key => {
      sorts.push(tempSorts[key]);
    });
    this.props.dispatch({
      type: 'sysMenu/updateSorts',
      payload: sorts,
      callback: () => {
        this.initQuery();
        this.setState({ tempSorts: {} });
      },
    });
  }

  handleDelete(record) {
    this.props.dispatch({
      type: 'sysMenu/delete',
      payload: {
        id: record.id,
        leftNum: record.leftNum,
        rightNum: record.rightNum,
      },
      callback: () => this.initQuery(),
    });
  }

  handleChangeState(record) {
    this.props.dispatch({
      type: 'sysMenu/updateStates',
      payload: {
        id: record.id,
        state: record.state === 'ON' ? 'OFF' : 'ON',
        leftNum: record.leftNum,
        rightNum: record.rightNum,
      },
      callback: () => this.initQuery(),
    });
  }

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
    const { sysMenu: { list = [] }, sysUser: { currentUser }, loading } = this.props;
    const { modalVisible, itemType, item, tempSorts } = this.state;

    const columns = [
      {
        title: '菜单名称',
        key: 'name',
        dataIndex: 'name',
      },
      {
        title: '菜单级别',
        key: 'levelDesc',
        dataIndex: 'levelDesc',
      },
      {
        title: '菜单链接',
        key: 'href',
        dataIndex: 'href',
      },
      {
        title: '菜单权限',
        key: 'permission',
        dataIndex: 'permission',
      },
      {
        title: '状态',
        key: 'stateDesc',
        dataIndex: 'stateDesc',
        render: (val, record) => <Badge status={statusMap[record.state]} text={val} />,
      },
      {
        title: '排序',
        key: 'sort',
        dataIndex: 'sort',
        render: (val, record) => (
          <InputNumber
            min={0}
            defaultValue={val}
            onChange={v => this.handleChangeSort(v, record)}
          />
        ),
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
                title="禁用该菜单（及其所有子菜单）后右侧菜单栏将无法显示，请谨慎使用！"
                placement="topRight"
                onConfirm={() => this.handleChangeState(record)}
              >
                <a>
                  禁用<Divider type="vertical" />
                </a>
              </Popconfirm>
            ) : (
              <Popconfirm
                title="启用该菜单（及其所有子菜单）后右侧菜单栏将正常显示，请谨慎使用！"
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
                title="删除该菜单（及其所有子菜单）后右侧菜单栏将无法显示且无法找回，请谨慎使用！"
                placement="topRight"
                onConfirm={() => this.handleDelete(record)}
              >
                <a>
                  删除<Divider type="vertical" />
                </a>
              </Popconfirm>
            )}
            <a onClick={() => this.handleModalVisible(true, 'sub', record)}>新建子菜单</a>
          </Fragment>
        ),
      },
    ];

    const createModalProps = {
      item,
      itemType,
      selectTreeData: [
        {
          label: '顶层',
          value: '1',
          key: '1',
          children: this.getSelectTreeData(list),
        },
      ],
      visible: modalVisible,
      dispatch: this.props.dispatch,
      handleAdd: this.handleAdd,
      handleModalVisible: () => this.handleModalVisible(false),
    };

    const CreateFormGen = () => <CreateForm {...createModalProps} />;

    return (
      <PageHeaderLayout>
        <Card bordered={false}>
          <div className={styles.tableList}>
            <div className={styles.tableListOperator}>
              <Button type="primary" onClick={() => this.handleModalVisible(true, 'create')}>
                新建
              </Button>
              {Object.keys(tempSorts).length > 0 && (
                <Button type="primary" onClick={() => this.handleUpdateSorts()}>
                  更新排序
                </Button>
              )}
            </div>
            <Table
              loading={loading}
              dataSource={list}
              columns={columns}
              pagination={false}
              rowKey={item => item.id}
            />
          </div>
        </Card>
        <CreateFormGen />
      </PageHeaderLayout>
    );
  }
}

const CreateForm = Form.create()(props => {
  const { visible, form, itemType, item, selectTreeData, handleAdd, handleModalVisible } = props;

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

  const modalProps = {
    title: '新建',
    visible,
    onOk: okHandle,
    onCancel: handleModalVisible,
  };

  if (itemType === 'create') modalProps.title = '新建';
  if (itemType === 'update') modalProps.title = '修改';
  if (itemType === 'sub') modalProps.title = '新建子菜单';

  return (
    <Modal {...modalProps}>
      <Form>
        <Row>
          <Col span={24}>
            <FormItem label="父菜单：" hasFeedback labelCol={{ span: 4 }} wrapperCol={{ span: 19 }}>
              {form.getFieldDecorator('parentId', {
                initialValue: item.parentId ? item.parentId.toString() : '1',
                rules: [
                  {
                    required: true,
                    message: '请选择父菜单',
                  },
                ],
              })(
                <TreeSelect
                  style={{ width: '100%' }}
                  dropdownStyle={{ maxHeight: 200, overflow: 'auto' }}
                  treeData={selectTreeData}
                  treeDefaultExpandAll
                  disabled
                />
              )}
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span={12}>
            <FormItem label="菜单名称：" hasFeedback {...formItemLayout}>
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
            <FormItem label="菜单地址：" hasFeedback {...formItemLayout}>
              {form.getFieldDecorator('href', {
                initialValue: item.href,
                rules: [
                  {
                    required: true,
                    message: '请输入菜单地址',
                  },
                ],
              })(<Input />)}
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span={12}>
            <FormItem label="菜单图标：" hasFeedback {...formItemLayout}>
              {form.getFieldDecorator('icon', {
                initialValue: item.icon,
                rules: [
                  {
                    message: '请输入菜单图标',
                  },
                ],
              })(<Input />)}
            </FormItem>
          </Col>
          <Col span={12}>
            <FormItem label="菜单级别：" hasFeedback {...formItemLayout}>
              {form.getFieldDecorator('level', {
                initialValue: item.level || 'MENU',
                rules: [
                  {
                    required: true,
                    message: '请选择菜单级别',
                  },
                ],
              })(<Dict code={'MENU_LEVEL'} />)}
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span={12}>
            <FormItem label="权限编码：" hasFeedback {...formItemLayout}>
              {form.getFieldDecorator('permission', {
                initialValue: item.permission,
                rules: [
                  {
                    message: '请输入权限编码',
                  },
                ],
              })(<Input />)}
            </FormItem>
          </Col>
          <Col span={12}>
            <FormItem label="菜单排序：" hasFeedback {...formItemLayout}>
              {form.getFieldDecorator('sort', {
                initialValue: item.sort,
                rules: [
                  {
                    required: true,
                    message: '请输入菜单顺序',
                  },
                ],
              })(<InputNumber min={0} style={{ width: '100%' }} />)}
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

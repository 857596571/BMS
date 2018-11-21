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
  Popconfirm,
} from 'antd';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';
import Dict from '@/components/Dict';
import * as system from '../../services/system';

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

@connect(({ sysDict, sysUser, loading }) => ({
  sysDict,
  sysUser,
  loading: loading.models.sysDict,
}))
@Form.create()
export default class DictList extends PureComponent {
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
      type: 'sysDict/getList',
      payload: form.getFieldsValue(),
    });
  };

  handleFormReset = () => {
    this.props.form.resetFields();
  };

  handleModalVisible = (flag, itemType, item) => {
    if (itemType === 'create') item = {};
    if (itemType === 'sub')
      item = {
        parentId: item.id,
        parentName: item.label,
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
      type: 'sysDict/add',
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
      type: 'sysDict/updateSorts',
      payload: sorts,
      callback: () => {
        this.initQuery();
        this.setState({ tempSorts: {} });
      },
    });
  }

  handleDelete(record) {
    this.props.dispatch({
      type: 'sysDict/delete',
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
      type: 'sysDict/updateStates',
      payload: {
        id: record.id,
        state: record.state === 'ON' ? 'OFF' : 'ON',
        leftNum: record.leftNum,
        rightNum: record.rightNum,
      },
      callback: () => this.initQuery(),
    });
  }

  render() {
    const { sysDict: { list }, sysUser: { currentUser }, loading, form } = this.props;
    const { modalVisible, itemType, item, tempSorts } = this.state;

    const columns = [
      {
        title: '字典编码',
        key: 'code',
        dataIndex: 'code',
      },
      {
        title: '字典标签',
        key: 'label',
        dataIndex: 'label',
      },
      {
        title: '字典值',
        key: 'value',
        dataIndex: 'value',
      },
      {
        title: '字典级别',
        key: 'levelDesc',
        dataIndex: 'levelDesc',
      },
      {
        title: '字典状态',
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
        width: 150,
        render: (val, record) => (
          <Fragment>
            <a onClick={() => this.handleModalVisible(true, 'update', record)}>
              修改<Divider type="vertical" />
            </a>
            {record.state === 'ON' ? (
              <Popconfirm
                title="禁用该字典（及其所有子字典）后将影响到界面显示，请谨慎使用！"
                placement="topRight"
                onConfirm={() => this.handleChangeState(record)}
              >
                <a>
                  禁用<Divider type="vertical" />
                </a>
              </Popconfirm>
            ) : (
              <Popconfirm
                title="启用该字典（及其所有子字典）后将影响到界面显示，请谨慎使用！"
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
                title="删除该字典（及其所有子字典）后将影响功能正常显示且无法找回，请谨慎使用！"
                placement="topRight"
                onConfirm={() => this.handleDelete(record)}
              >
                <a>
                  删除<Divider type="vertical" />
                </a>
              </Popconfirm>
            )}
            <a onClick={() => this.handleModalVisible(true, 'sub', record)}>
              新建子字典<Divider type="vertical" />
            </a>
          </Fragment>
        ),
      },
    ];

    const createModalProps = {
      item,
      itemType,
      currentUser,
      visible: modalVisible,
      dispatch: this.props.dispatch,
      handleAdd: this.handleAdd,
      handleModalVisible: () => this.handleModalVisible(false),
    };

    return (
      <PageHeaderWrapper>
        <Card bordered={false}>
          <div className={styles.tableList}>
            <div className={styles.tableListForm}>
              <Form onSubmit={this.handleSearch} layout="inline">
                <Row gutter={{ md: 8, lg: 24, xl: 48 }}>
                  <Col md={6} sm={24}>
                    <FormItem>
                      {form.getFieldDecorator('searchKeys')(
                        <Input placeholder="请输入需查询的字典编码、标签、值" />
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
                      {form.getFieldDecorator('level', {
                        initialValue: 'BIZ',
                      })(<Dict code={'LEVEL'} radio />)}
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
        <CreateForm {...createModalProps} />
      </PageHeaderWrapper>
    );
  }
}

const CreateForm = Form.create()(props => {
  const { visible, form, itemType, item, currentUser, handleAdd, handleModalVisible } = props;

  const okHandle = () => {
    form.validateFields((err, fieldsValue) => {
      if (err) return;
      form.resetFields();
      handleAdd({
        ...fieldsValue,
        id: item.id,
        parentId: item.parentId || 1,
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
  if (itemType === 'sub') modalProps.title = '新建子字典';
  return (
    <Modal {...modalProps}>
      <Form>
        <Row>
        <Col span={12}>
          <FormItem label="父字典：" hasFeedback {...formItemLayout}>
            {<span>{item.parentName || '无'}</span>}
          </FormItem>
        </Col>
        <Col span={12}>
          <FormItem label="字典编码：" hasFeedback {...formItemLayout}>
            {form.getFieldDecorator('code', {
              initialValue: item.code,
              rules: [
                {
                  required: true,
                  message: '请输入字典编码',
                },
                {
                  async validator(rule, value, callback) {
                    const data = await system.isDictCodeExists({
                      code: value,
                      id: item.id,
                    });
                    if (data.data) callback('该字典编码已存在');
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
            <FormItem label="字典标签：" hasFeedback {...formItemLayout}>
              {form.getFieldDecorator('label', {
                initialValue: item.label,
                rules: [
                  {
                    required: true,
                    message: '请输入字典标签',
                  },
                ],
              })(<Input />)}
            </FormItem>
          </Col>
          <Col span={12}>
            <FormItem label="字典值：" hasFeedback {...formItemLayout}>
              {form.getFieldDecorator('value', {
                initialValue: item.value || form.getFieldValue('code'),
                rules: [
                  {
                    required: true,
                    message: '请输入字典值',
                  },
                ],
              })(<Input />)}
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span={12}>
            <FormItem label="字典级别：" hasFeedback {...formItemLayout}>
              {form.getFieldDecorator('level', {
                initialValue: item.level,
                rules: [
                  {
                    required: true,
                    message: '请选择字典级别',
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
          <Col span={12}>
            <FormItem label="字典排序：" hasFeedback {...formItemLayout}>
              {form.getFieldDecorator('sort', {
                initialValue: item.sort,
                rules: [
                  {
                    required: true,
                    message: '请输入字典顺序',
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

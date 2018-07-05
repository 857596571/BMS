import React, {Fragment, PureComponent} from 'react';
import {connect} from 'dva';
import {Badge, Button, Card, Col, Divider, Form, Input, InputNumber, message, Modal, Row, Select, Table,} from 'antd';
import PageHeaderLayout from '../../layouts/PageHeaderLayout';
import Dict from '../../components/Dict';
import * as system from '../../services/system';

import styles from './OrgList.less';

const FormItem = Form.Item;
const { Option } = Select;

const formItemLayout = {
  labelCol: {
    span: 8,
  },
  wrapperCol: {
    span: 14,
  },
};
const stateMap = ['error', 'success'];
const state = ['停用', '启用'];
const CreateForm = Form.create()(props => {
  const {
    visible,
    form,
    itemType,
    item,
    codeExists,
    handleAdd,
    handleModalVisible,
    dispatch,
  } = props;

  const okHandle = () => {
    form.validateFields((err, fieldsValue) => {
      if (err) return;
      form.resetFields();
      handleAdd({
        ...fieldsValue,
        id: item.id,
        parentId: item.parentId || 0,
        state: item.state || 1,
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
  if (itemType === 'sub') modalProps.title = '新建子机构';

  return (
    <Modal {...modalProps}>
      <Row>
        <Col span={12}>
          <FormItem label="父部门：" hasFeedback {...formItemLayout}>
            {<span>{item.parentName || '无'}</span>}
          </FormItem>
        </Col>
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
            })(<Input />)}
          </FormItem>
        </Col>
      </Row>
      <Row>
        <Col span={12}>
        <FormItem label="机构级别：" hasFeedback {...formItemLayout}>
          {form.getFieldDecorator('level', {
            initialValue: item.level ? item.level + '' : '',
            rules: [
              {
                required: true,
                message: '请选择机构级别',
              },
            ],
          })(<Dict code={1} />)}
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
                },
                {
                  async validator(rule, value, callback) {
                    const data = await system.isOrgCodeExists({
                      code: value,
                      id: item.id,
                    });
                    if (data.data) callback('该机构编码已存在');
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
          <FormItem label="机构类型：" hasFeedback {...formItemLayout}>
            {form.getFieldDecorator('type', {
              initialValue: item.type ? item.type + '' : '',
              rules: [
                {
                  required: true,
                  message: '请选择机构类型',
                },
              ],
            })(<Dict code={2} />)}
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
            })(<InputNumber min={0} style={{ width: '100%' }} />)}
          </FormItem>
        </Col>
      </Row>
      <Row>
        <Col span={24}>
          <FormItem label="备注：" hasFeedback labelCol={{ span: 4 }} wrapperCol={{ span: 19 }}>
            {form.getFieldDecorator('remarks', {
              initialValue: item.remarks,
              rules: [
                {
                  message: '请输入机构名称',
                },
              ],
            })(<Input.TextArea rows={2} />)}
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
    itemType: 'create',
    item: {},
    tempSorts: {},
  };

  componentDidMount() {
    this.initQuery();
  }

  initQuery() {
    const { dispatch } = this.props;
    dispatch({
      type: 'sysOrg/getList',
    });
  }

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
      type: 'sysOrg/add',
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
      type: 'sysOrg/updateSorts',
      payload: sorts,
      callback: () => {
        this.initQuery();
        this.setState({ tempSorts: {} });
      },
    });
  }

  handleDeleteById(id) {
    this.props.dispatch({
      type: 'sysOrg/deleteById',
      id,
      callback: () => this.initQuery(),
    });
  }

  render() {
    const { sysOrg: { list, codeExists }, loading } = this.props;
    const { modalVisible, itemType, item, tempSorts } = this.state;

    const columns = [
      {
        title: '名称',
        key: 'name',
        dataIndex: 'name',
      },
      {
        title: '编码',
        key: 'code',
        dataIndex: 'code',
      },
      {
        title: '级别',
        key: 'levelDesc',
        dataIndex: 'levelDesc',
      },
      {
        title: '类型',
        key: 'typeDesc',
        dataIndex: 'typeDesc',
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
            <a onClick={() => this.handleModalVisible(true, 'update', record)}>修改</a>
            <Divider type="vertical" />
            <a onClick={() => this.handleDeleteById(record.id)}>删除</a>
            <Divider type="vertical" />
            <a onClick={() => this.handleModalVisible(true, 'sub', record)}>新建子机构</a>
          </Fragment>
        ),
      },
    ];

    const createModalProps = {
      item,
      itemType,
      codeExists,
      visible: modalVisible,
      dispatch: this.props.dispatch,
      handleAdd: this.handleAdd,
      handleModalVisible: () => this.handleModalVisible(false),
    };

    return (
      <PageHeaderLayout>
        <Card bordered={false}>
          <div className={styles.tableList}>
            <div className={styles.tableListOperator}>
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
      </PageHeaderLayout>
    );
  }
}

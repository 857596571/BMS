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
const { Option } = Select;

const formItemLayout = {
  labelCol: {
    span: 8,
  },
  wrapperCol: {
    span: 14,
  },
};

@connect(({ sysOrg, sysUser, loading }) => ({
  sysOrg,
  sysUser,
  loading: loading.models.sysOrg,
}))
@Form.create()
export default class OrgList extends PureComponent {
  state = {
    modalVisible: false,
    itemType: 'create',
    item: {},
    tempSorts: {},
    expandedRowKeys: ['2', '3'],
    fileList: [],
  };

  handleChange = ({ fileList }) => {
    console.log(fileList)
    this.setState({ fileList })
  }


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
    console.log(item)
    if (itemType === 'create') item = {};
    if (itemType === 'sub')
      item = {
        level: item.level,
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

  handleDeleteById(record) {
    this.props.dispatch({
      type: 'sysOrg/deleteById',
      payload: {
        id: record.id,
        leftNum: record.leftNum,
        rightNum: record.rightNum,
      },
      callback: () => this.initQuery(),
    });
  }

  handleTableExpand = expandedRows => {
    this.setState({
      expandedRowKeys: expandedRows,
    });
  };

  render() {
    const { sysOrg: { list }, sysUser: { currentUser }, loading } = this.props;
    const { modalVisible, itemType, item, tempSorts } = this.state;

    const columns = [
      {
        title: '机构名称',
        key: 'name',
        dataIndex: 'name',
      },
      // {
      //   title: '机构编码',
      //   key: 'code',
      //   dataIndex: 'code',
      // },
      {
        title: '机构级别',
        key: 'levelDesc',
        dataIndex: 'levelDesc',
      },
      {
        title: '机构类型',
        key: 'typeDesc',
        dataIndex: 'typeDesc',
      },
      {
        title: '排序',
        key: 'sort',
        dataIndex: 'sort',
        render: (val, record) => (
          <InputNumber
            disabled={record.id === '2'}
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
            {currentUser.admin &&
              (record.id !== '2' && record.id !== '3') && (
                <Popconfirm
                  title="删除该机构（及其所有子机构）后将影响功能正常显示且无法找回，请谨慎使用！"
                  placement="topRight"
                  onConfirm={() => this.handleDeleteById(record)}
                >
                  <a>
                    删除<Divider type="vertical" />
                  </a>
                </Popconfirm>
              )}
            <a onClick={() => this.handleModalVisible(true, 'sub', record)}>新建子机构</a>
          </Fragment>
        ),
      },
    ];

    const createModalProps = {
      item,
      itemType,
      visible: modalVisible,
      dispatch: this.props.dispatch,
      handleAdd: this.handleAdd,
      handleModalVisible: () => this.handleModalVisible(false),
    };

    return (
      <PageHeaderWrapper>
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
              expandedRowKeys={this.state.expandedRowKeys}
              loading={loading}
              dataSource={list}
              columns={columns}
              pagination={false}
              onExpandedRowsChange={this.handleTableExpand}
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
  const { visible, form, itemType, item, handleAdd, handleModalVisible } = props;

  const okHandle = () => {
    form.validateFields((err, fieldsValue) => {
      if (err) return;
      form.resetFields();
      handleAdd({
        ...fieldsValue,
        id: item.id,
        parentId: item.parentId || 0,
        state: item.state || 'ON',
        level: item.level
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
      <Form>
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
            <FormItem label="机构类型：" hasFeedback {...formItemLayout}>
              {form.getFieldDecorator('type', {
                initialValue: form.getFieldValue('level') === 'EDUCATION' ? 'OTHER_TYPE' : item.type,
                rules: [
                  {
                    required: true,
                    message: '请选择机构类型',
                  },
                ],
              })(<Dict code={'ORG_TYPE'} disabled={form.getFieldValue('level') === 'EDUCATION'} />)}
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
              })(<Input.TextArea rows={2} />)}
            </FormItem>
          </Col>
        </Row>
      </Form>
    </Modal>
  );
});

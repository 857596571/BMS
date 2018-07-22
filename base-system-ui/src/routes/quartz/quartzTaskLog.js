import React, {Fragment, PureComponent} from 'react';
import {connect} from 'dva';
import {Badge, Button, Card, Col, Divider, Form, Input, message, Modal, Row, Select, Table,} from 'antd';
import PageHeaderLayout from '../../layouts/PageHeaderLayout';
import Dict from '../../components/Dict';

const FormItem = Form.Item;

const statusMap = {'ON': 'success', 'OFF': 'error'};

const formItemLayout = {
  labelCol: {
    span: 8,
  },
  wrapperCol: {
    span: 14,
  },
};

@connect(({quartzTaskLog, loading}) => ({
  quartzTaskLog,
  loading: loading.models.quartzTaskLog,
}))
@Form.create()
export default class QuartzTaskLog extends PureComponent {
  state = {
    modalVisible: false,
    itemType: 'create',
    item: {},
  };

  componentDidMount() {
    this.initQuery();
  }

  initQuery = () => {
    const {dispatch, form} = this.props;
    dispatch({
      type: 'quartzTaskLog/getList',
      payload: form.getFieldsValue(),
    });
  }

  handleFormReset = () => {
    this.props.form.resetFields();
  }

  handleModalVisible = (flag, itemType, item) => {
    if (itemType === 'create') item = {};
    if (!flag) item = {};
    this.setState({
      modalVisible: flag,
      itemType: itemType,
      item,
    });
  };

  handleAdd = fields => {
    this.props.dispatch({
      type: 'quartzTaskLog/add',
      payload: {
        ...fields,
      },
      callback: this.initQuery,
    });
    this.setState({
      modalVisible: false,
    });
  };

  handleDelete = record => {
    this.props.dispatch({
      type: 'quartzTaskLog/deleteById',
      id: record.id,
      callback: this.initQuery,
    });
  }

  render() {
    const {quartzTaskLog: { list }, loading, form} = this.props;
    const {modalVisible, itemType, item} = this.state;

    const columns = [
      {
        title: '任务ID',
        key: 'quartzId',
        dataIndex: 'quartzId',
      },

      {
        title: '任务执行BEAN',
        key: 'beanId',
        dataIndex: 'beanId',
      },

      {
        title: '任务运行参数',
        key: 'params',
        dataIndex: 'params',
      },

      {
        title: '执行状态',
        key: 'status',
        dataIndex: 'status',
      },

      {
        title: '失败信息',
        key: 'error',
        dataIndex: 'error',
      },

      {
        title: '耗时(单位：毫秒)',
        key: 'times',
        dataIndex: 'times',
      },

      {
        title: '操作',
        width: 140,
        render: (val, record) => (
          <Fragment>
            <a onClick={() => this.handleModalVisible(true, 'update', record)}>修改</a>
            <Divider type="vertical"/>
            <a onClick={() => this.handleDelete(record)}>删除</a>
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
      <PageHeaderLayout>
        <Card bordered={false}>
          <div className={'tableList'}>
            <div className={'tableListForm'}>
              <Form onSubmit={this.handleSearch} layout="inline">
                <Row gutter={{ md: 8, lg: 24, xl: 48 }}>
                  <Col md={6} sm={24}>
                    <FormItem>
                      {form.getFieldDecorator('searchKeys')(<Input placeholder="请输入需查询的值" />)}
                    </FormItem>
                  </Col>
                  <Col md={6} sm={24}>
                    <span className={'submitButtons'}>
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
            <div className={'tableListOperator'}>
              <Button type="primary" onClick={() => this.handleModalVisible(true, 'create')}>
                新建
              </Button>
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

const CreateForm = Form.create()(props => {
  const {
    visible,
    form,
    itemType,
    item,
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

  return (
    <Modal {...modalProps}>
     <Form>
      <Row>
        <Col span={12}>
          <FormItem label="任务ID：" hasFeedback {...formItemLayout}>
            {form.getFieldDecorator('quartzId', {
              initialValue: item.quartzId,
              rules: [
                {
                  required: false,
                  message: '请输入任务ID',
                },
              ],
            })(<Input placeholder={'请输入任务ID'} />)}
          </FormItem>
        </Col>

        <Col span={12}>
          <FormItem label="任务执行BEAN：" hasFeedback {...formItemLayout}>
            {form.getFieldDecorator('beanId', {
              initialValue: item.beanId,
              rules: [
                {
                  required: false,
                  message: '请输入任务执行BEAN',
                },
              ],
            })(<Input placeholder={'请输入任务执行BEAN'} />)}
          </FormItem>
        </Col>

        <Col span={12}>
          <FormItem label="任务运行参数：" hasFeedback {...formItemLayout}>
            {form.getFieldDecorator('params', {
              initialValue: item.params,
              rules: [
                {
                  required: false,
                  message: '请输入任务运行参数',
                },
              ],
            })(<Input placeholder={'请输入任务运行参数'} />)}
          </FormItem>
        </Col>

        <Col span={12}>
          <FormItem label="执行状态：" hasFeedback {...formItemLayout}>
            {form.getFieldDecorator('status', {
              initialValue: item.status,
              rules: [
                {
                  required: false,
                  message: '请输入执行状态',
                },
              ],
            })(<Input placeholder={'请输入执行状态'} />)}
          </FormItem>
        </Col>

        <Col span={12}>
          <FormItem label="失败信息：" hasFeedback {...formItemLayout}>
            {form.getFieldDecorator('error', {
              initialValue: item.error,
              rules: [
                {
                  required: false,
                  message: '请输入失败信息',
                },
              ],
            })(<Input placeholder={'请输入失败信息'} />)}
          </FormItem>
        </Col>

        <Col span={12}>
          <FormItem label="耗时(单位：毫秒)：" hasFeedback {...formItemLayout}>
            {form.getFieldDecorator('times', {
              initialValue: item.times,
              rules: [
                {
                  required: false,
                  message: '请输入耗时(单位：毫秒)',
                },
              ],
            })(<Input placeholder={'请输入耗时(单位：毫秒)'} />)}
          </FormItem>
        </Col>

      </Row>
      <Row>
        <Col span={24}>
          <FormItem label="备注：" hasFeedback labelCol={{span: 4}} wrapperCol={{span: 19}}>
            {form.getFieldDecorator('remarks', {
              initialValue: item.remarks,
            })(<Input.TextArea rows={2}/>)}
          </FormItem>
        </Col>
      </Row>
     </Form>
    </Modal>
  );
});

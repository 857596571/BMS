import React, {Fragment, PureComponent} from 'react';
import {connect} from 'dva';
import {Badge, Button, Card, Col, Divider, Form, Input, message, Modal, Row, Select, Table, Popconfirm} from 'antd';
import PageHeaderLayout from '../../layouts/PageHeaderLayout';

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

@connect(({quartzTask, loading}) => ({
  quartzTask,
  loading: loading.models.quartzTask,
}))
@Form.create()
export default class QuartzTask extends PureComponent {
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
      type: 'quartzTask/getList',
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
      type: 'quartzTask/add',
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
      type: 'quartzTask/deleteById',
      id: record.id,
      callback: this.initQuery,
    });
  }

  handleChangeState(record) {
    this.props.dispatch({
      type: 'quartzTask/updateState',
      payload: {
        id: record.id,
        state: record.state === 'ON' ? 'OFF' : 'ON',
      },
      callback: () => this.initQuery(),
    });
  }

  handleRunOne = record => {
    this.props.dispatch({
      type: 'quartzTask/runOne',
      payload: record,
      callback: this.initQuery,
    });
  }

  render() {
    const {quartzTask: { list, pagination }, loading, form} = this.props;
    const {modalVisible, itemType, item} = this.state;

    const columns = [
      {
        title: '任务名称',
        key: 'name',
        dataIndex: 'name',
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
        title: 'cron表达式',
        key: 'cronExpression',
        dataIndex: 'cronExpression',
      },

      {
        title: '任务状态',
        key: 'state',
        dataIndex: 'state',
        render: (val, record) => <Badge status={statusMap[record.state]} text={val === 'ON' ? '启动' : '暂停'} />,
      },

      {
        title: '操作',
        width: 140,
        render: (val, record) => (
          <Fragment>
            <a onClick={() => this.handleModalVisible(true, 'update', record)}>修改</a>
            <Divider type="vertical"/>
            {record.state === 'ON' ? (
              <Popconfirm
                title="暂停该任务后将停止运行，请谨慎使用！"
                placement="topRight"
                onConfirm={() => this.handleChangeState(record)}
              >
                <a>
                  暂停<Divider type="vertical" />
                </a>
              </Popconfirm>
            ) : (
              <Popconfirm
                title="启用该任务后将重新运行任务，请谨慎使用！"
                placement="topRight"
                onConfirm={() => this.handleChangeState(record)}
              >
                <a>
                  启动<Divider type="vertical" />
                </a>
              </Popconfirm>
            )}
            <a onClick={() => this.handleDelete(record)}>删除</a>
            <Divider type="vertical"/>
            <Popconfirm
              title="执行该操作将会立即运行一次任务，请谨慎使用！"
              placement="topRight"
              onConfirm={() => this.handleRunOne(record)}
            >
              <a>运行一次</a>
            </Popconfirm>
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
              <Form layout="inline">
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
              pagination={pagination}
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
        state: item.state || 'ON'
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
          <FormItem label="任务名称：" hasFeedback {...formItemLayout}>
            {form.getFieldDecorator('name', {
              initialValue: item.name,
              rules: [
                {
                  required: false,
                  message: '请输入任务名称',
                },
              ],
            })(<Input placeholder={'请输入任务名称'} />)}
          </FormItem>
        </Col>

        <Col span={12}>
          <FormItem label="beanId：" hasFeedback {...formItemLayout}>
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
          <FormItem label="参数：" hasFeedback {...formItemLayout}>
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
          <FormItem label="cron表达式：" hasFeedback {...formItemLayout}>
            {form.getFieldDecorator('cronExpression', {
              initialValue: item.cronExpression,
              rules: [
                {
                  required: false,
                  message: '请输入cron表达式',
                },
              ],
            })(<Input placeholder={'请输入cron表达式'} />)}
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

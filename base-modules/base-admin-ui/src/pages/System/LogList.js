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
import PageHeaderWrapper from '@/components/PageHeaderWrapper';
import styles from './System.less';
import moment from 'moment'

const FormItem = Form.Item;

@connect(({ sysLog, loading }) => ({
  sysLog,
  loading: loading.models.sysLog,
}))
@Form.create()
export default class LogList extends PureComponent {
  state = {
  };

  componentDidMount() {
    this.initQuery();
  }

  initQuery = () => {
    const { dispatch, form } = this.props;
    dispatch({
      type: 'sysLog/getList',
      payload: {
        ...form.getFieldsValue(),
      },
    });
  };

  handleFormReset = () => {
    this.props.form.resetFields();
  };

  handlePageChange = page => {
    this.props.dispatch({
      type: 'sysLog/getList',
      payload: {
        pageNum: page.current,
        pageSize: page.pageSize,
        ...this.props.form.getFieldsValue(),
      },
    });
  };

  render() {
    const { sysLog: { list = [], pagination = {} }, loading, form } = this.props;

    const columns = [
      {
        title: '操作用户',
        key: 'username',
        dataIndex: 'username',
      },
      {
        title: '操作时间',
        key: 'startTime',
        dataIndex: 'startTime',
        render: val => val && moment(val).format('YYYY-MM-DD HH:mm:s')
      },
      {
        title: '消耗时间',
        key: 'spendTime',
        dataIndex: 'spendTime',
        render: val => val && `${val} ms`
      },
      {
        title: '操作URI',
        key: 'uri',
        dataIndex: 'uri',
      },
      {
        title: '操作IP',
        key: 'ip',
        dataIndex: 'ip',
      },
    ];

    return (
      <PageHeaderWrapper>
        <Card bordered={false}>
          <div className={styles.tableList}>
            <div className={styles.tableListForm}>
              <Form layout="inline">
                <Row gutter={{ md: 8, lg: 24, xl: 48 }}>
                  <Col md={6} sm={24}>
                    <FormItem>
                      {form.getFieldDecorator('searchKeys')(
                        <Input placeholder="请输入需查询的操作描述" />
                      )}
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
      </PageHeaderWrapper>
    );
  }
}

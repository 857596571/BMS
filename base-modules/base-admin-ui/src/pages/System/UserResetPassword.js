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

const FormItem = Form.Item;

const formItemLayout = {
  labelCol: {
    span: 8,
  },
  wrapperCol: {
    span: 14,
  },
};

@Form.create()
export default class UserResetPassword extends PureComponent {
  state = {
  };

  componentDidMount() {
  }

  render() {

    const form = this.props.form;

    const okHandle = () => {
      form.validateFields((err, fieldsValue) => {
        if (err) return;
        form.resetFields();
        this.props.handleResetPassword(fieldsValue);
      });
    };

    const modalProps = {
      title: '重置密码',
      visible: this.props.visible,
      onOk: okHandle,
      onCancel: this.props.handleModalVisible,
    };

    return (
      <Modal {...modalProps}>
        <Form>
          <Row>
            <Col span={24}>
              <FormItem label="原密码：" hasFeedback {...formItemLayout}>
                {form.getFieldDecorator('oldPassword', {
                  rules: [
                    {
                      required: true,
                      message: '请输入登录密码',
                    },
                  ],
                })(<Input type={'password'} />)}
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span={24}>
              <FormItem label="新密码：" hasFeedback {...formItemLayout}>
                {form.getFieldDecorator('newPassword', {
                  rules: [
                    {
                      required: true,
                      message: '请输入登录密码',
                    },
                  ],
                })(<Input type={'password'} />)}
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span={24}>
              <FormItem label="确认新密码：" hasFeedback {...formItemLayout}>
                {form.getFieldDecorator('resPassword', {
                  rules: [
                    {
                      required: true,
                      message: '请输入确认登录密码',
                    },
                    {
                      validator: (rule, value, callback) => {
                        if (value !== form.getFieldValue('newPassword')) {
                          callback('两次密码不一致');
                        }
                        callback();
                      },
                    },
                  ],
                })(<Input type={'password'} />)}
              </FormItem>
            </Col>
          </Row>
        </Form>
      </Modal>
    );
  }
}

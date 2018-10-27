import React, {Component} from 'react';
import {connect} from 'dva';
import {Alert, Checkbox} from 'antd';
import Login from '@/components/Login';
import styles from './Login.less';

const { UserName, Password, Submit } = Login;

@connect(({ sysLogin, loading }) => ({
  login: sysLogin,
  submitting: loading.effects['sysLogin/login'],
}))
export default class LoginPage extends Component {
  state = {
    autoLogin: true,
  };

  handleSubmit = (err, values) => {
    const { type } = this.state;
    if (!err) {
      this.props.dispatch({
        type: 'sysLogin/login',
        payload: {
          ...values,
          type,
        },
      });
    }
  };

  changeAutoLogin = e => {
    this.setState({
      autoLogin: e.target.checked,
    });
  };

  renderMessage = content => {
    return <Alert style={{ marginBottom: 24 }} message={content} type="error" showIcon />;
  };

  render() {
    const { login, submitting } = this.props;
    return (
      <div className={styles.main}>
        <Login onSubmit={this.handleSubmit}>
            {login.data.ok === false && !login.submitting && this.renderMessage(login.data.message)}
            <UserName name="loginName" placeholder="请输入登录账号" />
            <Password name="password" placeholder="请输入登录密码" />
          <Submit loading={submitting}>登录</Submit>
        </Login>
      </div>
    );
  }
}

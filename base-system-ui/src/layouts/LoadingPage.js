import React, { PureComponent } from 'react';
import { Spin } from 'antd';
import { connect } from 'dva';
import { enquireScreen, unenquireScreen } from 'enquire-js'
import { isUrl } from '../utils/utils';

import BasicLayout from './BasicLayout';
/**
 * 根据菜单取得重定向地址.
 */

const getMenuData = (data, parentPath = '/', parentAuthority) => {
  return data && data.map(item => {
    let { href } = item;
    if (!isUrl(href)) {
      href = parentPath + item.href;
    }
    const result = {
      ...item,
      path: href,
      authority: item.authority || parentAuthority,
    };
    if (item.children) {
      result.children = getMenuData(item.children, `${parentPath}${item.href}/`, item.authority);
    }
    return result;
  });
}

const getRedirectData = (menuData) => {
  const redirectData = [];
  const getRedirect = item => {
    if (item && item.children) {
      if (item.children[0] && item.children[0].path) {
        redirectData.push({
          from: `${item.path}`,
          to: `${item.children[0].path}`,
        });
        item.children.forEach(children => {
          getRedirect(children);
        });
      }
    }
  };
  menuData.forEach(getRedirect);
  return redirectData;
};

class LoadingPage extends PureComponent {
  state = {
    loading: true,
    isMobile: false,
  };

  componentDidMount() {
    this.enquireHandler = enquireScreen(mobile => {
      this.setState({
        isMobile: mobile,
      });
    });
    this.props.dispatch({
      type: 'sysUser/getLoginUser',
    });
    this.hideLoading();
  }
  componentWillUnmount() {
    unenquireScreen(this.enquireHandler);
  }
  hideLoading() {
    this.setState({
      loading: false,
    });
  }
  render() {
    const { currentUser } = this.props;
    const redirectData = currentUser.menus ? getRedirectData(getMenuData(currentUser.menus)) : [];
    if (this.state.loading) {
      return (
        <div
          style={{
            width: '100%',
            height: '100%',
            margin: 'auto',
            paddingTop: 50,
            textAlign: 'center',
          }}
        >
          <Spin size="large" />
        </div>
      );
    }
    return (
      <div>
        {currentUser.menus && <BasicLayout
          isMobile={this.state.isMobile}
          menuData={getMenuData(currentUser.menus)}
          redirectData={redirectData}
          {...this.props}
        />}
      </div>
    );
  }
}

export default connect(({ sysUser }) => ({
  currentUser: sysUser.currentUser,
}))(LoadingPage);

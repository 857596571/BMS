import React, {PureComponent} from 'react';
import {Spin} from 'antd';
import {connect} from 'dva';
import {enquireScreen, unenquireScreen} from 'enquire-js';
import {isUrl} from '@/utils/utils';

import BasicLayout from './BasicLayout';

import Exception from '@/components/Exception';
import Link from 'umi/link';

/**
 * 根据菜单取得重定向地址.
 */
const getMenuData = (data, parentPath = '/', parentAuthority) => {
  return (
    data &&
    data.map(item => {
      let { href } = item;
      if (!isUrl(href)) {
        href = parentPath + item.href;
      }

      const result = {
        name: item.name,
        icon: item.icon,
        path: href,
        authority: item.authority || parentAuthority,
      };
      if (item.children) {
        result.children = getMenuData(item.children, `${parentPath}${item.href}/`, item.authority);
      }
      return result;
    })
  );
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
    const { currentUser, userNotify } = this.props;
    if (this.state.loading || !(currentUser && currentUser.menus)) {
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
    if (this.state.loading || !(currentUser && currentUser.menus.length)){
      return (
        <Exception
          type="403"
          desc={"抱歉，你无权访问该页面"}
          linkElement={Link}
          backText={"返回主页"}
        />
      );
    }
    return (
      <div>
        <BasicLayout
          menuData={getMenuData(currentUser.menus)}
          userNotify={userNotify}
          {...this.props}
        />
      </div>
    );
  }
}

export default connect(({ sysUser, home }) => ({
  currentUser: sysUser.currentUser,
}))(LoadingPage);

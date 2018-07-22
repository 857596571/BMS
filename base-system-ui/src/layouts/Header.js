import React, {PureComponent} from 'react';
import {Layout, message} from 'antd';
import Animate from 'rc-animate';
import {connect} from 'dva';
import {routerRedux} from 'dva/router';
import GlobalHeader from '../components/GlobalHeader';
import TopNavHeader from '../components/TopNavHeader';
import styles from './Header.less';
import Authorized from '../utils/Authorized';
import {setting} from '../utils/config';
import UserResetPassword from '../routes/System/UserResetPassword';

const { Header } = Layout;

class HeaderView extends PureComponent {
  state = {
    visible: true,
    userPasswordEditVisible: false,
  };
  componentDidMount() {
    document.getElementById('root').addEventListener('scroll', this.handScroll);
  }
  componentWillUnmount() {
    document
      .getElementById('root')
      .removeEventListener('scroll', this.handScroll);
  }
  getHeadWidth = () => {
    const { fixedHeader, layout, fixSiderbar } = this.props.setting;
    if (!fixedHeader || layout === 'topmenu' || fixSiderbar) {
      return '100%';
    }
    if (!this.props.collapsed) {
      return 'calc(100% - 256px)';
    }
    if (this.props.collapsed) {
      return 'calc(100% - 80px)';
    }
  };
  handleNoticeClear = (type) => {
    this.props.dispatch({
      type: 'global/clearNotices',
      payload: type,
    });
  };
  handleMenuClick = ({ key }) => {
    if (key === 'userPasswordEdit') {
      this.setState({
        userPasswordEditVisible: true,
      });
    }
    if (key === 'logout') {
      this.props.dispatch({
        type: 'sysLogin/logout',
      });
    }
  };
  handleNoticeVisibleChange = (visible) => {
    if (visible) {
      this.props.dispatch({
        type: 'global/fetchNotices',
      });
    }
  };
  handScroll = () => {
    if (!this.props.autoHideHeader) {
      return;
    }
    const { scrollTop } = document.getElementById('root');
    if (!this.ticking) {
      this.ticking = false;
      requestAnimationFrame(() => {
        if (scrollTop > 400 && this.state.visible) {
          this.setState({
            visible: false,
          });
        }
        if (scrollTop < 400 && !this.state.visible) {
          this.setState({
            visible: true,
          });
        }
        this.ticking = false;
      });
    }
  };

  handleModalUserPasswordVisible = () => {
    this.setState({
      userPasswordEditVisible: false,
    });
  };

  handleResetPassword = (fields) => {
    this.props.dispatch({
      type: 'sysUser/resetPassword',
      payload: {
        ...fields,
        id: this.props.currentUser.id
      },
      callback: () => {
        this.handleModalUserPasswordVisible();
        this.props.dispatch({
          type: 'sysLogin/logout',
        });
      }
    });
  };

  render() {
    const { isMobile, handleMenuCollapse } = this.props;
    const { silderTheme, layout, fixedHeader } = this.props.setting;
    const isTop = layout === 'topmenu';
    const userResetPasswordProps = {
      dispatch: this.props.dispatch,
      visible: this.state.userPasswordEditVisible,
      handleModalVisible: this.handleModalUserPasswordVisible,
      handleResetPassword: this.handleResetPassword
    };


    const HeaderDom = this.state.visible ? (
      <Header
        style={{ padding: 0, width: this.getHeadWidth() }}
        className={fixedHeader ? styles.fixedHeader : ''}
      >
        {isTop && !isMobile ? (
          <TopNavHeader
            theme={silderTheme}
            mode="horizontal"
            Authorized={Authorized}
            onCollapse={handleMenuCollapse}
            onNoticeClear={this.handleNoticeClear}
            onMenuClick={this.handleMenuClick}
            onNoticeVisibleChange={this.handleNoticeVisibleChange}
            {...this.props}
          />
        ) : (
          <GlobalHeader
            onCollapse={handleMenuCollapse}
            onNoticeClear={this.handleNoticeClear}
            onMenuClick={this.handleMenuClick}
            onNoticeVisibleChange={this.handleNoticeVisibleChange}
            {...this.props}
          />
        )}
        <UserResetPassword {...userResetPasswordProps} />
      </Header>
    ) : null;
    return (
      <Animate component="" transitionName="fade">
        {HeaderDom}
      </Animate>
    );
  }
}

export default connect(({ sysUser, global, loading }) => ({
  currentUser: sysUser.currentUser,
  // collapsed: global.collapsed,
  // fetchingNotices: loading.effects['global/fetchNotices'],
  // notices: global.notices,
  setting,
}))(HeaderView);

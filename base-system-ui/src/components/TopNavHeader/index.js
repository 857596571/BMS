import React, {PureComponent} from 'react';
import {Link} from 'dva/router';
import RightContent from '../GlobalHeader/RightContent';
import BaseMenu from '../SiderMenu/BaseMenu';
import styles from './index.less';
import {logoText, name} from '../../utils/config';

export default class TopNavHeader extends PureComponent {
  render() {
    return (
      <div
        className={`${styles.head} ${
          this.props.theme === 'ligth' ? styles.ligth : ''
        }`}
      >
        <div
          className={`${styles.main} ${
            this.props.grid === 'Wide' ? styles.wide : ''
          }`}
        >
          <div className={styles.left}>
            <div className={styles.logo} key="logo">
              <Link to="/">
                <img src={this.props.logo} alt={logoText} />
                <h1>{name}</h1>
              </Link>
            </div>
            <BaseMenu
              {...this.props}
              style={{ padding: '9px 0', border: 'none' }}
            />
          </div>
          <div className={styles.right}>
            <RightContent {...this.props} />
          </div>
        </div>
      </div>
    );
  }
}

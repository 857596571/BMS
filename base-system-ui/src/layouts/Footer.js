import React, {Fragment} from 'react';
import {Icon, Layout} from 'antd';
import GlobalFooter from '../components/GlobalFooter';
import {copyrightText} from '../utils/config';

const { Footer } = Layout;
const FooterView = () => (
  <Footer style={{ padding: 0 }}>
    <GlobalFooter
      copyright={
        <Fragment>
          Copyright <Icon type="copyright" /> {new Date().getFullYear() +' '+ copyrightText}
        </Fragment>
      }
    />
  </Footer>
);
export default FooterView;

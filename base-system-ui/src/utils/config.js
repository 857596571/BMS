module.exports = {
  name: '基础框架管理系统',
  copyrightText: '基础框架管理系统',
  logoSrc: 'src/assets/logo.svg',
  logoText: '基础框架管理系统',
  iconFontCSS: '//at.alicdn.com/t/font_403714_waqeyi27uubu766r.css',
  iconFontJS: '//at.alicdn.com/t/font_403714_waqeyi27uubu766r.js',
  baseURL: 'http://127.0.0.1:8080/api',
  // baseURL: 'http://47.95.252.122:8080/base-system-web-1.0.0-SNAPSHOT',
  //公共分页信息
  pagination: {
    showSizeChanger: true,
    showQuickJumper: true,
    showTotal: (total, range) => `显示第 ${range[0]} 到第 ${range[1]} 条记录，总共 ${total} 条记录`,
    current: 1,
    total: 0,
    pageSize: 20,
    defaultPageSize: 20,
  },
  //设置
  setting: {
    collapse: false,
    silderTheme: 'dark', //导航风格设置[dark: 深色导航，dark: 浅色导航]
    themeColor: '#1890FF', //风格设置
    layout: 'sidemenu', //导航设置[sidemenu/topmenu]
    grid: this.layout === 'topmenu' ? 'Wide' : 'Wide', // 栅格模式[Wide/Fluid]
    fixedHeader: false, // 是否固定头部
    autoHideHeader: false, // 下滑时是否隐藏头部
    fixSiderbar: false, // 是否固定工作条
    colorWeak: 'close', //色弱模式[open:打开，close:关闭]
  },
};

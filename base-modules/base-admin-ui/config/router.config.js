export default [
  // user
  {
    path: '/sys',
    component: '../layouts/UserLayout',
    routes: [
      {path: '/sys', redirect: '/sys/login'},
      {path: '/sys/login', component: './System/Login'},
    ],
  },
  // app
  {
    path: '/',
    component: '../layouts/LoadingPage',
    Routes: ['src/pages/Authorized'],
    authority: ['upms', 'user'],
    routes: [
      //定时任务管理
      // {
      //   path: '/quartz',
      //   routes: [
      //     {
      //       path: '/quartz/quartzTaskLog',
      //       component: './quartz/QuartzTaskLog',
      //     },
      //     {
      //       path: '/quartz/quartzTask',
      //       component: './quartz/QuartzTask',
      //     },
      //   ],
      // },

      //系统管理
      {
        path: '/upms',
        routes: [
          {
            path: '/upms/org',
            component: './System/OrgList',
          },
          {
            path: '/upms/role',
            component: './System/RoleList',
          },
          {
            path: '/upms/user',
            component: './System/UserList',
          },
          {
            path: '/upms/dict',
            component: './System/DictList',
          },
          {
            path: '/upms/menu',
            component: './System/MenuList',
          },
          {
            path: '/upms/log',
            component: './System/LogList',
          },
        ],
      },

      {
        path: '/exception',
        routes: [
          {
            path: '/exception/403',
            component: './Exception/403',
          },
          {
            path: '/exception/404',
            component: './Exception/404',
          },
          {
            path: '/exception/500',
            component: './Exception/500',
          },
        ],
      },
    ],
  },
];

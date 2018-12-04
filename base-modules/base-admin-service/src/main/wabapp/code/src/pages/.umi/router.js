import React from 'react';
import { Router as DefaultRouter, Route, Switch } from 'react-router-dom';
import dynamic from 'umi/dynamic';
import renderRoutes from 'umi/_renderRoutes';
import RendererWrapper0 from '/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/pages/.umi/LocaleWrapper.jsx'

let Router = require('dva/router').routerRedux.ConnectedRouter;

let routes = [
  {
    "path": "/sys",
    "component": dynamic({ loader: () => import('../../layouts/UserLayout'), loading: require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/components/PageLoading/index').default }),
    "routes": [
      {
        "path": "/sys",
        "redirect": "/sys/login",
        "exact": true
      },
      {
        "path": "/sys/login",
        "component": dynamic({ loader: () => import('../System/Login'), loading: require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/components/PageLoading/index').default }),
        "exact": true
      },
      {
        "component": () => React.createElement(require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/node_modules/umi-build-dev/lib/plugins/404/NotFound.js').default, { pagesPath: 'src/pages', hasRoutesInConfig: true })
      }
    ]
  },
  {
    "path": "/",
    "component": dynamic({ loader: () => import('../../layouts/LoadingPage'), loading: require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/components/PageLoading/index').default }),
    "Routes": [require('../Authorized').default],
    "authority": [
      "admin",
      "user"
    ],
    "routes": [
      {
        "path": "/system",
        "routes": [
          {
            "path": "/system/org",
            "component": dynamic({ loader: () => import('../System/OrgList'), loading: require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/components/PageLoading/index').default }),
            "exact": true
          },
          {
            "path": "/system/role",
            "component": dynamic({ loader: () => import('../System/RoleList'), loading: require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/components/PageLoading/index').default }),
            "exact": true
          },
          {
            "path": "/system/user",
            "component": dynamic({ loader: () => import('../System/UserList'), loading: require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/components/PageLoading/index').default }),
            "exact": true
          },
          {
            "path": "/system/dict",
            "component": dynamic({ loader: () => import('../System/DictList'), loading: require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/components/PageLoading/index').default }),
            "exact": true
          },
          {
            "path": "/system/menu",
            "component": dynamic({ loader: () => import('../System/MenuList'), loading: require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/components/PageLoading/index').default }),
            "exact": true
          },
          {
            "path": "/system/log",
            "component": dynamic({ loader: () => import('../System/LogList'), loading: require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/components/PageLoading/index').default }),
            "exact": true
          },
          {
            "component": () => React.createElement(require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/node_modules/umi-build-dev/lib/plugins/404/NotFound.js').default, { pagesPath: 'src/pages', hasRoutesInConfig: true })
          }
        ]
      },
      {
        "path": "/exception",
        "routes": [
          {
            "path": "/exception/403",
            "component": dynamic({ loader: () => import('../Exception/403'), loading: require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/components/PageLoading/index').default }),
            "exact": true
          },
          {
            "path": "/exception/404",
            "component": dynamic({ loader: () => import('../Exception/404'), loading: require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/components/PageLoading/index').default }),
            "exact": true
          },
          {
            "path": "/exception/500",
            "component": dynamic({ loader: () => import('../Exception/500'), loading: require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/components/PageLoading/index').default }),
            "exact": true
          },
          {
            "component": () => React.createElement(require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/node_modules/umi-build-dev/lib/plugins/404/NotFound.js').default, { pagesPath: 'src/pages', hasRoutesInConfig: true })
          }
        ]
      },
      {
        "component": () => React.createElement(require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/node_modules/umi-build-dev/lib/plugins/404/NotFound.js').default, { pagesPath: 'src/pages', hasRoutesInConfig: true })
      }
    ]
  },
  {
    "component": () => React.createElement(require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/node_modules/umi-build-dev/lib/plugins/404/NotFound.js').default, { pagesPath: 'src/pages', hasRoutesInConfig: true })
  }
];
window.g_plugins.applyForEach('patchRoutes', { initialValue: routes });

export default function() {
  return (
<RendererWrapper0>
          <Router history={window.g_history}>
      { renderRoutes(routes, {}) }
    </Router>
        </RendererWrapper0>
  );
}

import dva from 'dva';
import createLoading from 'dva-loading';

const runtimeDva = window.g_plugins.mergeConfig('dva');
let app = dva({
  history: window.g_history,
  
  ...(runtimeDva.config || {}),
});

window.g_app = app;
app.use(createLoading());
(runtimeDva.plugins || []).forEach(plugin => {
  app.use(plugin);
});

app.model({ namespace: 'global', ...(require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/models/global.js').default) });
app.model({ namespace: 'setting', ...(require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/models/setting.js').default) });
app.model({ namespace: 'dict', ...(require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/models/system/dict.js').default) });
app.model({ namespace: 'log', ...(require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/models/system/log.js').default) });
app.model({ namespace: 'login', ...(require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/models/system/login.js').default) });
app.model({ namespace: 'menu', ...(require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/models/system/menu.js').default) });
app.model({ namespace: 'org', ...(require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/models/system/org.js').default) });
app.model({ namespace: 'role', ...(require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/models/system/role.js').default) });
app.model({ namespace: 'user', ...(require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/models/system/user.js').default) });

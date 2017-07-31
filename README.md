# XXStatusBar

## 自定义Android状态栏 使用svg图标，可以动态改变图标颜色.类似于ios效果，状态栏透明

### 使用场景
- 1.苦于状态栏适配问题，又想让整体界面类似于ios状态栏效果
- 2.直播界面全屏，顶部自定义透明状态栏，整体界面美观
- 3.想自定义显示其他图标到顶部状态栏


### 使用方式
- 1.下载项目中library 或者 直接将 XXStatusBarLayout类引入自己的工程目录
- 2.拷贝drawable 下svg 图标
- 3.values下有color 色值将其拷贝到自己的工程下
- 4.加入项目权限 ACCESS_COARSE_LOCATION ACCESS_NETWORK_STATE READ_PHONE_STATE 并加入授权管理，不然会报错

### 结尾

___这个项目原本想做成智能更换图标颜色，后期会考虑，
   有兴趣可以使用google 的色值提取库，提取目前状态栏的颜色，来动态改变图标颜色 ，
   也可使用svg H标清来动态改变图标形状,有空我会去封装该控件___
   



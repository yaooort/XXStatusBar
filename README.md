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


### 代码写的比较急可优化地方还很多

--SVG电量图标--

```xml
   <vector android:height="20dp" android:viewportHeight="1024.0"
       android:viewportWidth="1024.0" android:width="20dp" xmlns:android="http://schemas.android.com/apk/res/android">
       <path android:fillColor="@color/icon_color" android:pathData="M877.7,313.5L62.7,313.5A62.7,62.7 0,0 0,0 376.2v271.7c0,34.6 28.1,62.7 62.7,62.7 h815 a62.7,62.7 0,0 0,62.7 -62.7v-271.7a62.7,62.7 0,0 0,-62.7 -62.7zM919.5,647.8c0,23.1 -18.7,41.8 -41.8,41.8L62.7,689.6a41.8,41.8 0,0 1,-41.8 -41.8v-271.7c0,-23.1 18.7,-41.8 41.8,-41.8h815c23.1,0 41.8,18.7 41.8,41.8v271.7z"/>
       <path android:fillColor="@color/le_low_a" android:pathData="M62.7,355.3 h203.75 c11.5,0 20.9,9.3 20.9,20.9 v271.7 c0,11.5 -9.3,20.9 -20.9,20.9 H62.7 a20.9,20.9 0,0 1,-20.9 -20.9 v-271.7 c0,-11.6 9.3,-20.9 20.9,-20.9 zM961.3,438.8 A62.7,62.7 0,0 1,1024 501.6v20.9a62.7,62.7 0,0 1,-62.7 62.7v-146.3z"/>
   </vector>
```

- 可以动态改变android:pathData 中的 h203.75 值改变内部电芯图标长度

### 效果
[!https://github.com/yaooort/XXStatusBar/blob/master/device-2017-07-31-141627.png]


### 结尾

___这个项目原本想做成智能更换图标颜色，后期会考虑，
   有兴趣可以使用google 的色值提取库，提取目前状态栏的颜色，来动态改变图标颜色 ，
   也可使用svg H标清来动态改变图标形状,有空我会去封装该控件___
   



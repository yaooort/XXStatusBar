package com.yaooort.statuslibrary;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.TelephonyManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by bunny on 2017/7/26.
 */

public class XXStatusBarLayout extends RelativeLayout {

    private Context context;

    /**
     * 时间显示
     */
    private TextView timeText;
    /**
     * 电量
     */
    private ImageView imageElectricity;

    /**
     * 电量百分比
     */
    private TextView textElectricity;

    /**
     * 信号强度
     */
    private ImageView imageSignal;

    /**
     * 运营商
     */
    private TextView textOperator;

    /**
     * WIFI
     */
    private ImageView imageWifi;


    public XXStatusBarLayout(Context context) {
        super(context);
        this.context = context;
        initView();
    }


    public XXStatusBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public XXStatusBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }


    private void initView() {
        //当前时间
        timeText = new TextView(context);
        timeText.setText("12:22");
        timeText.setTextColor(ContextCompat.getColor(context, R.color.icon_color));
        timeText.setTextSize(10);
        LayoutParams timeTextParams =
                new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        timeTextParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(timeText, timeTextParams);

        //电量图标
        imageElectricity = new ImageView(context);
        imageElectricity.setId(generateViewId());
        imageElectricity.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_po_pro));
        LayoutParams imageElectri = new LayoutParams((int) dp2px(18), (int) dp2px(18));
        imageElectri.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        imageElectri.addRule(RelativeLayout.CENTER_VERTICAL);
        imageElectri.setMargins((int) dp2px(3), 0, (int) dp2px(8), 0);
        addView(imageElectricity, imageElectri);

        //电量百分比
        textElectricity = new TextView(context);
        textElectricity.setText("23%");
        textElectricity.setTextColor(ContextCompat.getColor(context, R.color.icon_color));
        textElectricity.setTextSize(10);
        LayoutParams textElectriParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textElectriParams.addRule(RelativeLayout.CENTER_VERTICAL);
        textElectriParams.addRule(RelativeLayout.LEFT_OF, imageElectricity.getId());
        addView(textElectricity, textElectriParams);

        //信号强度
        imageSignal = new ImageView(context);
        imageSignal.setId(generateViewId());
        imageSignal.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_xinhao));
        LayoutParams signalParams = new LayoutParams((int) dp2px(15), (int) dp2px(15));
        signalParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        signalParams.addRule(RelativeLayout.CENTER_VERTICAL);
        signalParams.setMargins((int) dp2px(8), 0, (int) dp2px(3), 0);
        addView(imageSignal, signalParams);

        //运营商
        textOperator = new TextView(context);

        textOperator.setId(generateViewId());
        textOperator.setTextColor(ContextCompat.getColor(context, R.color.icon_color));
        textOperator.setTextSize(10);
        LayoutParams operatorParams =
                new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        operatorParams.addRule(RelativeLayout.CENTER_VERTICAL);
        operatorParams.addRule(RelativeLayout.RIGHT_OF, imageSignal.getId());
        addView(textOperator, operatorParams);

        //wifi
        imageWifi = new ImageView(context);
        imageWifi.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_wifi));
        LayoutParams wifiParams = new LayoutParams((int) dp2px(15), (int) dp2px(15));
        wifiParams.addRule(RelativeLayout.CENTER_VERTICAL);
        wifiParams.addRule(RelativeLayout.RIGHT_OF, textOperator.getId());
        wifiParams.setMargins((int) dp2px(8), 0, (int) dp2px(3), 0);
        addView(imageWifi, wifiParams);


        initUI();
    }


    private static final int TIME_CALL_UPDATE = 1001;

    private Handler handlerCall = new Handler(new Handler.Callback() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case TIME_CALL_UPDATE:
                    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                    Date curDate = new Date(System.currentTimeMillis());
                    String str = formatter.format(curDate);
                    timeText.setText(str);
                    /**
                     * 电量
                     */
                    textElectricity.setText("" + getBatteryPercentage()+"%");
                    if (getBatteryPercentage() > 95) {
                        imageElectricity.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_po_pro_d));
                    } else if (getBatteryPercentage() > 75) {
                        imageElectricity.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_po_pro_d));
                    } else if (getBatteryPercentage() > 50) {
                        imageElectricity.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_po_pro_c));
                    } else if (getBatteryPercentage() > 25) {
                        imageElectricity.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_po_pro_b));
                    } else if (getBatteryPercentage() > 5) {
                        imageElectricity.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_po_pro_a));
                    } else if (getBatteryPercentage() > 0) {
                        imageElectricity.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_po_pro_a));
                    }


                    /**
                     * 信号
                     */
//                    if (getMobileDbm() == -1) {
//                        imageSignal.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_xinhao));
//                    }
                    imageSignal.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_xinhao));
                    textOperator.setText(getMobileName());


                    /**
                     * wifi 没有网络-0：WIFI网络1：4G网络-4：3G网络-3：2G网络-2
                     */
                    switch (getAPNType()) {
                        case 0:
                            //无网络
                            imageWifi.setImageDrawable(null);
                            break;
                        case 1:
                            //WIFI
                            imageWifi.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_wifi));
                            break;
                        case 2:
                            //2G
                            imageWifi.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_2g));
                            break;
                        case 3:
                            //3G
                            imageWifi.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_3g));
                            break;
                        case 4:
                            //4G
                            imageWifi.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_4g));
                            break;
                    }

                    break;
            }
            return false;
        }
    });

    /**
     * 修改界面
     */
    private void initUI() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    handlerCall.sendEmptyMessage(TIME_CALL_UPDATE);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }


    /**
     * 获取电量百分比
     *
     * @return
     */
    public int getBatteryPercentage() {

        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, iFilter);

        int level = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : -1;
        int scale = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1) : -1;

        float batteryPct = level / (float) scale;

        return (int) (batteryPct * 100);
    }


    public float dp2px(float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return dpValue * scale + 0.5f;
    }

    public float sp2px(float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return spValue * fontScale + 0.5f;
    }

    /**
     * An {@code int} value that may be updated atomically.
     */
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    /**
     * 动态生成View ID
     * API LEVEL 17 以上View.generateViewId()生成
     * API LEVEL 17 以下需要手动生成
     */
    public static int generateViewId() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            for (; ; ) {
                final int result = sNextGeneratedId.get();
                // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                int newValue = result + 1;
                if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
                if (sNextGeneratedId.compareAndSet(result, newValue)) {
                    return result;
                }
            }
        } else {
            return View.generateViewId();
        }
    }

    /**
     * 获取手机信号强度，需添加权限 android.permission.ACCESS_COARSE_LOCATION <br>
     * API要求不低于17 <br>
     *
     * @return 当前手机主卡信号强度, 单位 dBm（-1是默认值，表示获取失败）
     */
    public int getMobileDbm() {
        int dbm = -1;
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        List<CellInfo> cellInfoList;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            cellInfoList = tm.getAllCellInfo();
            if (null != cellInfoList) {
                for (CellInfo cellInfo : cellInfoList) {
                    if (cellInfo instanceof CellInfoGsm) {
                        CellSignalStrengthGsm cellSignalStrengthGsm = ((CellInfoGsm) cellInfo).getCellSignalStrength();
                        dbm = cellSignalStrengthGsm.getDbm();
                    } else if (cellInfo instanceof CellInfoCdma) {
                        CellSignalStrengthCdma cellSignalStrengthCdma =
                                ((CellInfoCdma) cellInfo).getCellSignalStrength();
                        dbm = cellSignalStrengthCdma.getDbm();
                    } else if (cellInfo instanceof CellInfoWcdma) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                            CellSignalStrengthWcdma cellSignalStrengthWcdma =
                                    ((CellInfoWcdma) cellInfo).getCellSignalStrength();
                            dbm = cellSignalStrengthWcdma.getDbm();
                        }
                    } else if (cellInfo instanceof CellInfoLte) {
                        CellSignalStrengthLte cellSignalStrengthLte = ((CellInfoLte) cellInfo).getCellSignalStrength();
                        dbm = cellSignalStrengthLte.getDbm();
                    }
                }
            }
        }
        return dbm;
    }


    private String getMobileName() {
        TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        String imsi = telManager.getSubscriberId();

        if (imsi != null) {
            if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
                //中国移动
                return "中国移动";
            } else if (imsi.startsWith("46001")) {
                //中国联通
                return "中国联通";
            } else if (imsi.startsWith("46003")) {
                //中国电信
                return "中国电信";
            } else {
                return "";
            }
        } else {
            return "";
        }
    }


    /**
     * 获取当前的网络状态 ：没有网络-0：WIFI网络1：4G网络-4：3G网络-3：2G网络-2
     * 自定义
     *
     * @return
     */
    public int getAPNType() {
        //结果返回值
        int netType = 0;
        //获取手机所有连接管理对象
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取NetworkInfo对象
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        //NetworkInfo对象为空 则代表没有网络
        if (networkInfo == null) {
            return netType;
        }
        //否则 NetworkInfo对象不为空 则获取该networkInfo的类型
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_WIFI) {
            //WIFI
            netType = 1;
        } else if (nType == ConnectivityManager.TYPE_MOBILE) {
            int nSubType = networkInfo.getSubtype();
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //3G   联通的3G为UMTS或HSDPA 电信的3G为EVDO
            if (nSubType == TelephonyManager.NETWORK_TYPE_LTE
                    && !telephonyManager.isNetworkRoaming()) {
                netType = 4;
            } else if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS
                    || nSubType == TelephonyManager.NETWORK_TYPE_HSDPA
                    || nSubType == TelephonyManager.NETWORK_TYPE_EVDO_0
                    && !telephonyManager.isNetworkRoaming()) {
                netType = 3;
                //2G 移动和联通的2G为GPRS或EGDE，电信的2G为CDMA
            } else if (nSubType == TelephonyManager.NETWORK_TYPE_GPRS
                    || nSubType == TelephonyManager.NETWORK_TYPE_EDGE
                    || nSubType == TelephonyManager.NETWORK_TYPE_CDMA
                    && !telephonyManager.isNetworkRoaming()) {
                netType = 2;
            } else {
                netType = 2;
            }
        }
        return netType;
    }

}

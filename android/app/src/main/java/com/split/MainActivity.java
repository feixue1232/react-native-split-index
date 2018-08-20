package com.split;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.CatalystInstance;
import com.facebook.react.bridge.CatalystInstanceImpl;
import com.facebook.react.bridge.JSBundleLoader;
import com.facebook.react.bridge.ReactContext;
import com.facebook.soloader.SoLoader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

public class MainActivity extends Activity {
    private String TAG = "MainActivity";

    private String unload = "未初始化ReactContext";
    private String loaded = "已初始化ReactContext";
    private TextView text;
    private long time1;
    private long time2;
    private long time3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        text = (TextView) findViewById(R.id.textView);
        text.setText(unload);
    }

    public void onClickLoad(View v) {
        this.createContext();
    }

    public void onClickUnLoad(View v) {
        ((ReactApplication)getApplication()).getReactNativeHost().getReactInstanceManager().destroy();
        Toast.makeText(getApplicationContext(), "ReactContext已卸载完成~", Toast.LENGTH_LONG).show();
        text.setText(unload);
    }

    public void onClickSubA(View v) {
        if (!checkReactLoaded()) {
            Toast.makeText(getApplicationContext(), "ReactContext还未初始化~，请先初始化", Toast.LENGTH_LONG).show();
            return;
        }
        startActivity(new Intent(this, SubAActivity.class));
    }

    public void onClickSubB(View v) {
        if (!checkReactLoaded()) {
            Toast.makeText(getApplicationContext(), "ReactContext还未初始化~，请先初始化", Toast.LENGTH_LONG).show();
            return;
        }
        startActivity(new Intent(this, SubBActivity.class));
    }

    public void onClickSubC(View v) {
        startActivity(new Intent(this, SubCActivity.class));
    }

    /**
     * 检查是否ReactContext
     */
    private boolean checkReactLoaded() {
        if (text.getText().toString().equals(unload)) {
            return false;
        }
        return true;
    }


    private void createContext() {
        Log.d(TAG, "start createContext");
        SoLoader.init(this, /* native exopackage */ false);
        time1 = new Date().getTime();
        final ReactInstanceManager manager = ((ReactApplication)getApplication()).getReactNativeHost().getReactInstanceManager();
        if (!manager.hasStartedCreatingInitialContext()) {
            manager.addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener() {
                @Override
                public void onReactContextInitialized(ReactContext context) {
                    time2 = new Date().getTime();
                    onContextInitialized();
                }
            });
            ((ReactApplication)getApplication()).getReactNativeHost().getReactInstanceManager().createReactContextInBackground();
        }
    }

    private void onContextInitialized() {
        text.setText(loaded);
        Toast.makeText(getApplicationContext(), "ReactContext初始化完毕", Toast.LENGTH_LONG).show();
        Log.e(TAG, "onContextInitialized");
        // 方式一，通过反射加载bundle[图片资源可以顺带被加载]
        loadBundleFromAssets("subA_.bundle");
        loadBundleFromAssets("subB_.bundle");
        loadBundleFromAssets("subC_.bundle");
        // 方式二,通过JSBundleLoader直接加载[单纯加载bundle，图片资源无法加载]
//        loadBundleFromAssetsByJSBundleLoader("asset:///subA_.bundle");
//        loadBundleFromAssetsByJSBundleLoader("asset:///subB_.bundle");
//        loadBundleFromAssetsByJSBundleLoader("asset:///subC_.bundle");
        time3 = new Date().getTime();
        Log.d(TAG, "onContextInitialized, time1: " + time1 + ", " + time2 + ", " + time3);
    }

    private void loadBundleFromAssets(String bundlepath) {
        String source = "assets://" + bundlepath;
        Log.e("RNN", "wk loadScriptFromAsset:"+source);
        try {
            Method method = CatalystInstanceImpl.class.getDeclaredMethod("loadScriptFromAssets",
                    AssetManager.class,
                    String.class,
                    boolean.class);
            method.setAccessible(true);
            method.invoke(((ReactApplication)getApplication()).getReactNativeHost().getReactInstanceManager().getCurrentReactContext().getCatalystInstance(), this.getAssets(), source, false);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void loadBundleFromAssetsByJSBundleLoader(String BASE_BUNDLE_ASSET) {
        CatalystInstanceImpl instance = (CatalystInstanceImpl)((ReactApplication)getApplication()).getReactNativeHost().getReactInstanceManager().getCurrentReactContext().getCatalystInstance();
        JSBundleLoader.createAssetLoader(this.getApplicationContext(), BASE_BUNDLE_ASSET, false)
                .loadScript(instance);
    }
}

package com.androidapps;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableArray;
//import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.helper.*;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class AndroidApps extends ReactContextBaseJavaModule {
  private static ReactApplicationContext reactContext;

  AndroidApps(ReactApplicationContext context) {
    super(context);
    reactContext = context;
  }

  @Override
  public String getName() {
    return "AndroidApps";
  }

  @ReactMethod
  public void list(Promise promise) {
        try{
          WritableArray apps = new WritableNativeArray();
          List<PackageInfo> packs = getReactApplicationContext().getPackageManager().getInstalledPackages(0);
          for (int i = 0; i < packs.size(); i++) {
              WritableMap app = new WritableNativeMap();
              PackageInfo p = packs.get(i);
              if (!((p.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0)) {
                  app.putString("name", p.applicationInfo.loadLabel(getReactApplicationContext().getPackageManager()).toString());
                  // app.putMap("packageName",p.packageName.toString());
                  // app.putInt("vcode",p.versionCode);
                  // app.putString("vName",p.versionName.toString());
                  //app.putString("icon",p.packageName);
                  app.putString("icon", Utility.convert(p.applicationInfo.loadIcon(getReactApplicationContext().getPackageManager())));                   
                  apps.pushMap(app);
              }
          }
          promise.resolve(apps);
        } catch(Exception e){
          promise.reject(e.getMessage());
        }       
  }

}

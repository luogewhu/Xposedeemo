package com.example.john.xposedeemo;

import android.app.PendingIntent;
import android.graphics.Camera;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Binder;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.util.Log;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Handler;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/**
* Created by luoge on 2017/4/21.
*/
public class Module implements IXposedHookLoadPackage {

    long timecalled = System.currentTimeMillis();
    LocalSocket socketSMS;
    LocalSocket socketMedia;
    LocalSocket socketLocation;
    LocalSocket socketContact;
    LocalSocket ss;

    PrintStream printStream;
    int tryConnect = 0;
    boolean issocketcreated;


    private boolean startsocket(boolean thdcreated, final String msg, final String socketname) {


        try{
            ss = new LocalSocket();
            ss.connect(new LocalSocketAddress(socketname));
            printStream = new PrintStream(ss.getOutputStream());
            printStream.print(msg+'\n');
            printStream.flush( );
        }catch(Exception e){
            e.printStackTrace();
        }


        try{
            printStream.close();
            ss.close();
        }catch(Exception e) {
            e.printStackTrace();
        }





        return false;

    }

    @Override
   public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
       if (true){
        //   XposedBridge.log("Loaded App:" + lpparam.packageName);
           //查找要Hook的函数
           findAndHookMethod(
                   "android.telephony.SmsManager",
                   lpparam.classLoader,
                   "sendTextMessage",     //被Hook函数的名称sendTextMessage
                   String.class, //被Hook函数的第一个参数String
                   String.class, //被Hook函数的第二个参数String
                   String.class, //被Hook函数的第三个参数String
                   PendingIntent.class,
                   PendingIntent.class,
                   new XC_MethodHook(){
                       @Override
                       protected void beforeHookedMethod(MethodHookParam param)
                               throws Throwable {
                           // Hook函数之前执行的代码
                         //  XposedBridge.log("Hooked SmsManager sendTextMessage + AppName destAddr:" + param.args[0]);
                        //   XposedBridge.log("Sending SMS to:" + param.args[1]);
                        //   XposedBridge.log("Sending text to :"+param.args[2]);
                        //   XposedBridge.log("PendingIntent sent :"+param.args[3]);
                         //  XposedBridge.log("PendingIntent delivery:"+param.args[4]);
                        //   XposedBridge.log(ActivityManager.RunningAppProcessInfo(););
                           long timecalled = System.currentTimeMillis();
                           String packageName = lpparam.processName;
                           int CallingPid = Binder.getCallingPid();
                           int CallingUid = Binder.getCallingUid();
                           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
                           String currentDateTime = dateFormat.format(new Date(timecalled)); // Find todays date
                           Log.d("LOG", packageName  + "android.telephony.SmsManager.sendTextMessage "+ currentDateTime+"2"+"Pid: " + Integer.toString(CallingPid) + " Uid: " + Integer.toString(CallingUid));

                           //函数返回值
                       }
                   });


                   //   XposedBridge.log("Loaded App:" + lpparam.packageName);
                   //查找要Hook的函数
                   findAndHookMethod(
                           "android.telephony.SmsMessage",
                           lpparam.classLoader,
                           "createFromPdu",     //被Hook函数的名称sendTextMessage
                           byte[].class, //被Hook函数的第一个参数String
                           new XC_MethodHook(){
                               @Override
                               protected void beforeHookedMethod(MethodHookParam param)
                                       throws Throwable {

                                   long timecalled = System.currentTimeMillis();
                                   String packageName = lpparam.processName;
                                   int CallingPid = Binder.getCallingPid();
                                   int CallingUid = Binder.getCallingUid();
                                   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
                                   String currentDateTime = dateFormat.format(new Date(timecalled)); // Find todays date
                                   Log.d("LOG", packageName  + "android.telephony.SmsMessage.createFromPdu "+ currentDateTime+"2"+"Pid: " + Integer.toString(CallingPid) + " Uid: " + Integer.toString(CallingUid));


                               }
                           });


           findAndHookMethod("android.media.MediaRecorder", lpparam.classLoader, "start",  new XC_MethodHook() {
               @Override
               protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                   // this will be called before the clock was updated by the original method
                   long timecalled = System.currentTimeMillis();
                   String packageName = lpparam.processName;
                   int CallingPid = Binder.getCallingPid();
                   int CallingUid = Binder.getCallingUid();
                   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
                   String currentDateTime = dateFormat.format(new Date(timecalled)); // Find todays date
                   Log.d("LOG", packageName  + "android.media.MediaRecorder.start "+ currentDateTime+"3"+"Pid: " + Integer.toString(CallingPid) + " Uid: " + Integer.toString(CallingUid));

                 //  String sendMsg = packageName+"-"+"Mediastart"+"-"+CallingPid+"-"+CallingUid+"-"+timecalled ;
                 //  issocketcreated = startsocket(issocketcreated, sendMsg, "SENDDATAMedia");


               }
               @Override
               protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                   // this will be called after the clock was updated by the original method
               }
           });

                   findAndHookMethod("android.media.AudioRecord", lpparam.classLoader, "startRecording", new XC_MethodHook() {
                       @Override
                       protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                           // this will be called before the clock was updated by the original method
                           long timecalled = System.currentTimeMillis();
                           String packageName = lpparam.processName;
                           int CallingPid = Binder.getCallingPid();
                           int CallingUid = Binder.getCallingUid();
                           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
                           String currentDateTime = dateFormat.format(new Date(timecalled)); // Find todays date
                           Log.d("LOG", packageName  + " android.media.AudioRecordAudioRecord.startRecord "+ currentDateTime+"3"+"Pid: " + Integer.toString(CallingPid) + " Uid: " + Integer.toString(CallingUid));

                       }
                       @Override
                       protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                           // this will be called after the clock was updated by the original method
                       }
                   });

           findAndHookMethod(
                   "com.android.internal.telephony.RIL",
                   lpparam.classLoader,
                   "sendSMS",     //被Hook函数的名称checkSN
                   String.class, //被Hook函数的第一个参数String
                   String.class, //被Hook函数的第二个参数String
                   Message.class,
                   new XC_MethodHook(){
                       @Override
                       protected void beforeHookedMethod(MethodHookParam param)
                               throws Throwable {
                           long timecalled = System.currentTimeMillis();
                           String packageName = lpparam.processName;

                           int CallingPid = Binder.getCallingPid();
                           int CallingUid = Binder.getCallingUid();
                           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
                           String currentDateTime = dateFormat.format(new Date()); // Find todays date
                           Log.d("LOG", packageName  + "com.android.internal.telephony.RIL.sendSMS"+ currentDateTime+"2"+"Pid: " + Integer.toString(CallingPid) + " Uid: " + Integer.toString(CallingUid));

                           ;
                       }
                   });


                   findAndHookMethod("android.location.LocationManager", lpparam.classLoader, "getLastKnownLocation", String.class,  new XC_MethodHook() {
                       @Override
                       protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                           // this will be called before the clock was updated by the original method
                           long timecalled = System.currentTimeMillis();
                           String packageName = lpparam.processName;

                           int CallingPid = Binder.getCallingPid();
                           int CallingUid = Binder.getCallingUid();
                           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
                           String currentDateTime = dateFormat.format(new Date()); // Find todays date
                           Log.d("LOG", packageName  + "android.location.LocationManager.getLastKnownLocation"+ currentDateTime+"1"+"Pid: " + Integer.toString(CallingPid) + " Uid: " + Integer.toString(CallingUid));


                       }
                       @Override
                       protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                           // this will be called after the clock was updated by the original method
                       }
                   });

                   findAndHookMethod("android.content.ContentProvider.Transport", lpparam.classLoader, "query", String.class, android.net.Uri.class, Class.forName("[Ljava.lang.String;"), String.class, Class.forName("[Ljava.lang.String;"), String.class, Class.forName("android.os.ICancellationSignal"), new XC_MethodHook() {
                       @Override
                       protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                           // this will be called before the clock was updated by the original method
				/*
				arg0 : com.example.jimhan.readcontact
				arg1 : content://com.android.contacts/contacts
				 */
                           if (param.args[1].toString().indexOf("content://com.android.contacts")>=0)
                           {
                               //��õ���ʱ��
                               long timecalled = System.currentTimeMillis();
                               //��õ��ð���
                               String packageName = lpparam.processName;

                               int CallingPid = Binder.getCallingPid();
                               int CallingUid = Binder.getCallingUid();
                               SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
                               String currentDateTime = dateFormat.format(new Date(timecalled)); // Find todays date
                               Log.d("LOG", packageName  + "android.content.ContentProvider.Transport.query"+ currentDateTime+"4"+"Pid: " + Integer.toString(CallingPid) + " Uid: " + Integer.toString(CallingUid));
                               //Log.d("hook-test", "8-query"+" package: "+packageName);




                           }


                       }
                       @Override
                       protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                           // this will be called after the clock was updated by the original method
                       }
                   });


           findAndHookMethod("android.telephony.TelephonyManager",lpparam.classLoader, "getDeviceId",new XC_MethodHook() {
               @Override
               protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                   long timecalled = System.currentTimeMillis();
                   String packageName = lpparam.processName;
                   int CallingPid = Binder.getCallingPid();
                   int CallingUid = Binder.getCallingUid();
                   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
                   String currentDateTime = dateFormat.format(new Date(timecalled)); // Find todays date
                   Log.d("LOG", packageName + "-android.telephony.TelephonyManager.getDeviceId-"+ currentDateTime+"-7"+"-Pid:" +Integer.toString(CallingPid) + "-Uid:" + Integer.toString(CallingUid));


               }
               @Override
               protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                   // this will be called after the clock was updated by the original method
               }
           });

           findAndHookMethod("android.telephony.TelephonyManager",lpparam.classLoader, "getSimSerialNumber",new XC_MethodHook() {
               @Override
               protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                   long timecalled = System.currentTimeMillis();
                   String packageName = lpparam.processName;
                   int CallingPid = Binder.getCallingPid();
                   int CallingUid = Binder.getCallingUid();
                   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
                   String currentDateTime = dateFormat.format(new Date(timecalled)); // Find todays date
                   Log.d("LOG", packageName + "-android.telephony.TelephonyManager.getSimSerialNumber-"+ currentDateTime+"-7"+"-Pid:" +Integer.toString(CallingPid) + "-Uid:" + Integer.toString(CallingUid));


               }
               @Override
               protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                   // this will be called after the clock was updated by the original method
               }
           });

           findAndHookMethod("android.telephony.TelephonyManager",lpparam.classLoader, "getSubscriberId",new XC_MethodHook() {
               @Override
               protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                   long timecalled = System.currentTimeMillis();
                   String packageName = lpparam.processName;
                   int CallingPid = Binder.getCallingPid();
                   int CallingUid = Binder.getCallingUid();
                   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
                   String currentDateTime = dateFormat.format(new Date(timecalled)); // Find todays date
                   Log.d("LOG", packageName + "-android.telephony.TelephonyManager.getSubscriberId-"+ currentDateTime+"-7"+"-Pid:" +Integer.toString(CallingPid) + "-Uid:" + Integer.toString(CallingUid));

               }
               @Override
               protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                   // this will be called after the clock was updated by the original method
               }
           });

           findAndHookMethod("android.telephony.TelephonyManager",lpparam.classLoader, "getLine1Number",new XC_MethodHook() {
               @Override
               protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                   long timecalled = System.currentTimeMillis();
                   String packageName = lpparam.processName;
                   int CallingPid = Binder.getCallingPid();
                   int CallingUid = Binder.getCallingUid();
                   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
                   String currentDateTime = dateFormat.format(new Date(timecalled)); // Find todays date
                   Log.d("LOG", packageName + "-android.telephony.TelephonyManager.getLine1Number-"+ currentDateTime+"-7"+"-Pid:" +Integer.toString(CallingPid) + "-Uid:" + Integer.toString(CallingUid));

               }
               @Override
               protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                   // this will be called after the clock was updated by the original method
               }
           });

           findAndHookMethod("android.telephony.TelephonyManager",lpparam.classLoader, "listen",PhoneStateListener.class,int.class,new XC_MethodHook() {
               @Override
               protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                   long timecalled = System.currentTimeMillis();
                   String packageName = lpparam.processName;
                   int CallingPid = Binder.getCallingPid();
                   int CallingUid = Binder.getCallingUid();
                   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
                   String currentDateTime = dateFormat.format(new Date(timecalled)); // Find todays date
                   Log.d("LOG", packageName + "-android.telephony.TelephonyManager.listen-"+ currentDateTime+"-7"+"-Pid:" +Integer.toString(CallingPid) + "-Uid:" + Integer.toString(CallingUid));

               }
               @Override
               protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                   // this will be called after the clock was updated by the original method
               }
           });

           findAndHookMethod("android.telephony.PhoneStateListener",lpparam.classLoader, "onCallStateChanged",new XC_MethodHook() {
               @Override
               protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                   long timecalled = System.currentTimeMillis();
                   String packageName = lpparam.processName;
                   int CallingPid = Binder.getCallingPid();
                   int CallingUid = Binder.getCallingUid();
                   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
                   String currentDateTime = dateFormat.format(new Date(timecalled)); // Find todays date
                   Log.d("LOG", packageName + "-android.telephony.PhoneStateListener.onCallStateChanged-"+ currentDateTime+"-7"+"-Pid:" +Integer.toString(CallingPid) + "-Uid:" + Integer.toString(CallingUid));

               }
               @Override
               protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                   // this will be called after the clock was updated by the original method
               }
           });

           findAndHookMethod("android.hardware.camera2.CameraManager", lpparam.classLoader, "openCamera", String.class, Camera.class, Handler.class, new XC_MethodHook() {
               @Override
               protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                   long timecalled = System.currentTimeMillis();
                   String packageName = lpparam.processName;
                   int CallingPid = Binder.getCallingPid();
                   int CallingUid = Binder.getCallingUid();
                   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
                   String currentDateTime = dateFormat.format(new Date(timecalled)); // Find todays date
                   Log.d("LOG", packageName + "-android.hardware.camera2.CameraManager.openCamera)-" + currentDateTime + "-7" + "-Pid:" + Integer.toString(CallingPid) + "-Uid:" + Integer.toString(CallingUid));

               }

               @Override
               protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                   // this will be called after the clock was updated by the original method
               }
           });



                /*   findAndHookMethod("com.android.server.am.ActivityManagerService", lpparam.classLoader, "getContentProvider", Class.forName("android.app.IApplicationThread"), String.class, int.class, boolean.class, new XC_MethodHook() {
                       @Override
                       protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                           if (param.args[1].equals("com.android.contacts"))
                           {
                               // this will be called before the clock was updated by the original method
                               //��õ���ʱ��
                               long timecalled = System.currentTimeMillis();
                               //��õ��ð���
                               String packageName = lpparam.processName;

                               int CallingPid = Binder.getCallingPid();
                               int CallingUid = Binder.getCallingUid();
                               SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
                               String currentDateTime = dateFormat.format(new Date(timecalled)); // Find todays date
                               Log.d("LOG", packageName  + "com.android.server.am.ActivityManagerService.getContentProvider"+ currentDateTime+"4"+"Pid: " + Integer.toString(CallingPid) + " Uid: " + Integer.toString(CallingUid));


                           }
                       }
                       @Override
                       protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                           // this will be called after the clock was updated by the original method
                       }
                   });*/
       }

   }

}

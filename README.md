# Xposedeemo
一个用于动态Hook安卓手机中APP的敏感行为的xposed模块

包括定位、短信、录音、联系人、拍照、电话、设备信息这些敏感行为,基于Android 6.0,根据版本的变化，使用在其他系统版本上可能需要修改部分Hook
的方法名

具体Hook的方法名

短信 android.telephony.SmsMessage.createFromPdu com.android.internal.telephony.RIL.sendSMS android.telephony.SmsManager.sendTextMessage 发送短信

录音	android.media.MediaRecorder.start android.media.AudioRecord.startRecording 开始录音

电话 android.telephony.TelephonyManager	listen	监听电话状态	android.telephony.PhoneStateListener	onCallStateChanged	显示来电号码 android.content.Intent	参数为Intent.ACTION_DIAL
Intent.ACTION_CALL	拨打电话

拍照	android.hardware.camera2.CameraManager.openCamera	打开相机

联系人	android.content.ContentProvider.Transport.query	查询联系人

设备信息	android.telephony.TelephonyManager.getDeviceId	android.telephony.TelephonyManager.getSubscriberId		android.telephony.TelephonyManager.getSimSerialNumber
   	android.telephony.TelephonyManager.getLine1Number 电话号码




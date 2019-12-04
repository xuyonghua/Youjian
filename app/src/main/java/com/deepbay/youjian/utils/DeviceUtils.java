package com.deepbay.youjian.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.provider.Telephony;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.deepbay.youjian.entity.CallRecord;
import com.deepbay.youjian.entity.Contact;
import com.deepbay.youjian.entity.SmsData;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class DeviceUtils {
    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号 9
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号 BND-AL10
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机设备名
     *
     * @return 手机设备名 HWBND-H
     */
    public static String getSystemDevice() {
        return Build.DEVICE;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商 HONOR
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 获取手机厂商名
     *
     * @return 手机厂商名 HUAWEI
     */
    public static String getDeviceManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return 手机IMEI
     */
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            String imei = "";
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ToastUtils.showShort(context, "no permission");
            } else {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        imei = telephonyManager.getImei();
                    } else {
                        imei = telephonyManager.getDeviceId();
                    }
                } catch (Exception e) {
                    imei = "";
                }

            }
            return imei;
        }
        return null;
    }

    /**
     * 获取deviceId
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        String deviceId = DeviceUtils.getIMEI(context);
        if (TextUtils.isEmpty(deviceId)) {
            deviceId = Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            if (TextUtils.isEmpty(deviceId)) {
                deviceId = SharedPreferencesUtils.getString(context, Constants.DEVICE_ID, "");
                if (TextUtils.isEmpty(deviceId)) {
                    deviceId = UUID.randomUUID().toString();
                    SharedPreferencesUtils.putString(context, Constants.DEVICE_ID, deviceId);
                }
            }
        }
        return deviceId;
    }

    public static String getNetworkType(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        if (networkInfo == null) {
            // WIFI 和 移动网络都关闭 即没有有效网络
            return "";
        }
        String typeName = "";
        if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            typeName = networkInfo.getTypeName();
        } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            typeName = networkInfo.getTypeName();
        }
        return typeName;
    }

    /**
     * 获取联系人
     *
     * @param context
     * @return
     */
    public static List<Contact> getContacts(Context context) {
        List<Contact> contactList = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();
        //指定获取_id和display_name两列数据，display_name即为姓名
        String[] projection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                projection, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(0);
                //获取姓名
                String name = cursor.getString(1);
                //指定获取NUMBER这一列数据
                String[] phoneProjection = new String[]{
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                };
                //根据联系人的ID获取此人的电话号码
                Cursor phonesCursor = context.getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        phoneProjection,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id,
                        null,
                        null);
                StringBuilder number = new StringBuilder();
                //因为每个联系人可能有多个电话号码，所以需要遍历
                if (phonesCursor != null) {
                    while (phonesCursor.moveToNext()) {
                        Log.d("getContacts", "number:" + phonesCursor.getString(0));
                        String phoneNumber = phonesCursor.getString(0);
                        // 对手机号码进行预处理（去掉号码前的+91、首尾空格、“-”号等）
                        phoneNumber = phoneNumber.replaceAll("^(\\+91)", "");
                        phoneNumber = phoneNumber.replaceAll("^(91)", "");
                        phoneNumber = phoneNumber.replaceAll("-", "");
                        phoneNumber = phoneNumber.replaceAll(" ", "");
                        phoneNumber = phoneNumber.trim();
                        number.append(phoneNumber).append(",");
                    }
                    phonesCursor.close();
                }
                Contact contact = new Contact(name, number.toString());
                contactList.add(contact);
            }
            //更新数据
            cursor.close();

        }
        return contactList;
    }

    /**
     * 获取短信
     *
     * @param context
     * @return
     */
    public static List<SmsData> getSmsData(Context context) {
        List<SmsData> smsList = new ArrayList<>();
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(Telephony.Sms.CONTENT_URI, new String[]{
                Telephony.Sms.ADDRESS,   //
                Telephony.Sms.BODY,
                Telephony.Sms.DATE,
                Telephony.Sms.READ,
                Telephony.Sms.STATUS,
                Telephony.Sms.TYPE,
                Telephony.Sms.PROTOCOL,
                Telephony.Sms.PERSON,
        }, null, null, "date DESC");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                SmsData smsData = new SmsData();
                smsData.setAddress(cursor.getString(0));
                smsData.setBody(cursor.getString(1));
                smsData.setDate(cursor.getLong(2));
                smsData.setRead(cursor.getInt(3));
                smsData.setStatus(cursor.getInt(4));
                smsData.setType(cursor.getInt(5));
                smsData.setProtocol(cursor.getInt(6));
//                smsData.setPerson(cursor.getString(7));
                smsData.setPerson(getPerson(cursor.getString(0), context));
                smsList.add(smsData);
            }
            cursor.close();
        }
        return smsList;
    }

    /**
     * 获取通话记录
     *
     * @param context
     * @return
     */
    public static List<CallRecord> getCallRecord(Context context) {
        List<CallRecord> records = new ArrayList<>();
        ContentResolver resolver = context.getContentResolver();
        //获取cursor对象
        @SuppressLint("MissingPermission") Cursor cursor = resolver.query(CallLog.Calls.CONTENT_URI, new String[]{
                CallLog.Calls.CACHED_FORMATTED_NUMBER,
                CallLog.Calls.CACHED_MATCHED_NUMBER,
                CallLog.Calls.CACHED_NAME,
                CallLog.Calls.TYPE,
                CallLog.Calls.DATE,
                CallLog.Calls.DURATION,
                CallLog.Calls.GEOCODED_LOCATION,
                CallLog.Calls.NUMBER,
        }, null, null, "date DESC");
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    CallRecord record = new CallRecord();
                    record.setFormatted_number(cursor.getString(0));
                    record.setMatched_number(cursor.getString(1));
                    record.setCachedName(cursor.getString(2));
                    record.setType(cursor.getInt(3));
                    record.setDate(cursor.getLong(4));
                    record.setDuration(cursor.getLong(5));
                    record.setLocation(cursor.getString(6));
                    record.setPhone(cursor.getString(7));
                    records.add(record);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cursor.close();  //关闭cursor，避免内存泄露
            }
        }
        return records;
    }


    /**
     * 手机号获取名字
     *
     * @param address
     * @param context
     * @return
     */
    private static String getPerson(String address, Context context) {
        try {
            ContentResolver resolver = context.getContentResolver();
            Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, address);
            Cursor cursor;
            cursor = resolver.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
            if (cursor != null) {
                try {
                    if (cursor.getCount() != 0) {
                        cursor.moveToFirst();
                        return cursor.getString(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    cursor.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

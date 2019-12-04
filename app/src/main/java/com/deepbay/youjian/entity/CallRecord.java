package com.deepbay.youjian.entity;

public class CallRecord {
    /**
     * 拨打时间(long型)
     */
    private long date;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 匹配通讯录的名称
     */
    private String cachedName;

    /**
     * 通话记录格式化号码
     */
    private String formatted_number;

    /**
     * 通话记录为格式化号码
     */
    private String matched_number;

    /**
     * 通话类型 1呼入/2呼出/3未接
     */
    private int type;

    /**
     * 号码归属地
     */
    private String location;

    /**
     * 通话时长(秒为单位)
     */
    private long duration;


    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getFormatted_number() {
        return formatted_number;
    }

    public void setFormatted_number(String formatted_number) {
        this.formatted_number = formatted_number;
    }

    public String getMatched_number() {
        return matched_number;
    }

    public void setMatched_number(String matched_number) {
        this.matched_number = matched_number;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "CallRecord{" +
                "date=" + date +
                ", phone='" + phone + '\'' +
                ", cachedName='" + cachedName + '\'' +
                ", formatted_number='" + formatted_number + '\'' +
                ", matched_number='" + matched_number + '\'' +
                ", type='" + type + '\'' +
                ", location='" + location + '\'' +
                ", duration=" + duration +
                '}';
    }

    public String getCachedName() {
        return cachedName;
    }

    public void setCachedName(String cachedName) {
        this.cachedName = cachedName;
    }
}

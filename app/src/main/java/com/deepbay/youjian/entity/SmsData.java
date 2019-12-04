package com.deepbay.youjian.entity;

public class SmsData {
    /**
     * 发件人地址，即手机号
     */
    private String address;

    /**
     * 发件人，如果发件人在通讯录中则为具体姓名，陌生人为null
     */
    private String person;

    /**
     * 日期，long型，如1256539465022
     */
    private long date;

    /**
     * protocol：协议0 SMS_RPOTO短信，1 MMS_PROTO彩信
     */
    private int protocol;

    /**
     * 是否阅读0未读，1已读
     */
    private int read;

    /**
     * 短信状态-1接收，0complete,64pending,128failed
     */
    private int status;

    /**
     * 短信类型1是接收到的，2是已发出
     */
    private int type;

    /**
     * 短信具体内容
     */
    private String body;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getProtocol() {
        return protocol;
    }

    public void setProtocol(int protocol) {
        this.protocol = protocol;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SmsData{" +
                "address='" + address + '\'' +
                ", person='" + person + '\'' +
                ", date=" + date +
                ", protocol=" + protocol +
                ", read=" + read +
                ", status=" + status +
                ", type=" + type +
                ", body='" + body + '\'' +
                '}';
    }
}

package com.cosun.cosunp.weixin;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author:homey Wong
 * @Date: 2019/9/11 0011 上午 11:40
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class OutMsgEntity{

    protected String FromUserName;
    protected String ToUserName;
    protected Long CreateTime;
    protected String MsgType;
    @XmlElementWrapper(name = "Image")
    private String[] MediaId;
    private String Content;

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public Long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Long createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public String[] getMediaId() {
        return MediaId;
    }

    public void setMediaId(String[] mediaId) {
        MediaId = mediaId;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}

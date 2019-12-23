package com.cosun.cosunp.weixin;


public class MassMsgResult {

    private Integer id;

    private String type;
    private String msg_id;
    private String msg_data_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public String getMsg_data_id() {
        return msg_data_id;
    }

    public void setMsg_data_id(String msg_data_id) {
        this.msg_data_id = msg_data_id;
    }
}

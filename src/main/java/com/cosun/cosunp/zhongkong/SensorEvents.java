package com.cosun.cosunp.zhongkong;

import com.jacob.com.Dispatch;
import com.jacob.com.DispatchEvents;
import com.jacob.com.DispatchProxy;
import com.jacob.com.Variant;

import java.util.EventListener;
import java.util.EventObject;
import com.jacob.com.InvocationProxy;
public class SensorEvents {


    public static final long serialVersionUID = -1L;


    public void OnConnected(Variant[] arge) {
        System.out.println("OnConnected====");
    }

    public void OnDisConnected(Variant[] arge) {
        System.out.println("OnDisConnected");
    }

    public void OnAlarm(Variant[] arge) {
        System.out.println("OnAlarm====" + arge);
    }

    public long OnDoor(Variant[] arge) {
        System.out.println("OnDoorEvent====" + arge);
        if(arge[0].getInt() == 4) {
            System.out.println("open====" + arge);
        } else if (arge[0].getInt() == 5) {
            System.out.println("close====" + arge);
        } else if (arge[0].getInt() == 53) {
            System.out.println("closing====" + arge);
        } else if (arge[0].getInt() == 1) {
            System.out.println("open error====" + arge);
        }
        return 1;
    }

    public void OnAttTransactionEx(Variant[] arge) {
    }

    public void OnEnrollFingerEx(Variant[] arge) {
        System.out.println("OnEnrollFingerEx====" + arge[0]);
    }

    public void OnFinger(Variant[] arge) {
        System.out.println("OnFinger");
    }

    public void OnFingerFeature(Variant[] arge) {
        System.out.println("OnFingerFeature====" + arge);
    }

    public void OnHIDNum(Variant[] arge) {
        System.out.println("OnHIDNum====" + arge);
    }

    public void OnNewUser(Variant[] arge) {
        System.out.println("OnNewUser====" + arge);
    }

    public void OnVerify(Variant[] arge) {
        System.out.println("OnVerify====" + arge);
    }

    public void OnWriteCard(Variant[] arge) {
        System.out.println("OnWriteCard====" + arge);
    }

    public void OnEmptyCard(Variant[] arge) {
        System.out.println("OnEmptyCard:" + arge);
    }

    public void OnEMData(Variant[] arge) {
        System.out.println("OnEMData:" + arge);
    }

}
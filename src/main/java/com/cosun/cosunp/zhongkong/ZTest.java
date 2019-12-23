package com.cosun.cosunp.zhongkong;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.*;

public class ZTest implements Runnable {

    private static final int MACHINENUM = 1;
    private static Variant enrollNumber = new Variant("", true);
    private static Variant dwtmachineNumber = new Variant(-1, true);
    private static Variant dwemachineNumber = new Variant(-1, true);
    private static Variant dwenrollNumber = new Variant(-1, true);

    private static Variant verifyMode = new Variant(-1, true);
    private static Variant inOutMode = new Variant(-1, true);
    private static Variant year = new Variant(-1, true);
    private static Variant month = new Variant(-1, true);
    private static Variant day = new Variant(-1, true);
    private static Variant hour = new Variant(-1, true);
    private static Variant minute = new Variant(-1, true);
    private static Variant second = new Variant(-1, true);




    public static void main(String args[]) {
        try {
            ComThread.InitMTA();
            String progId = "zkemkeeper.ZKEM.1";
            Dispatch zkem = new ActiveXComponent(progId);
            SensorEvents events = new SensorEvents();
            new DispatchEvents(zkem, events, progId);
            System.out.println("请输入:");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String s = br.readLine();
            boolean b = Dispatch.call(zkem, "Connect_Net", new Variant(s), new Variant(4370)).getBoolean();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    public static boolean readCheckInfo(Dispatch zkem) {
        return Dispatch.call(zkem, "OnAttTransactionEx", MACHINENUM, dwtmachineNumber, dwenrollNumber, dwemachineNumber, verifyMode, inOutMode, year, month, day, hour, minute).getBoolean();
    }

    @Override
    public void run() {
        try {
            System.out.println("coming run method");
            ComThread.InitMTA();//ComThread.InitMTA();
            String progId = "zkemkeeper.ZKEM.1";
            Dispatch zkem = new ActiveXComponent(progId);
            SensorEvents events = new SensorEvents();
            new DispatchEvents(zkem, events, progId);
            boolean b = Dispatch.call(zkem, "Connect_Net", new Variant("192.168.2.11"), new Variant(4370)).getBoolean();
            Variant dispatch = Dispatch.call(zkem, "RegEvent", new Variant(1l), new Variant(1));
            STA sta = new STA();
            sta.doMessagePump();
            System.out.println(System.in.read());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

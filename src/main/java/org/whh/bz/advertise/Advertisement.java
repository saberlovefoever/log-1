package org.whh.bz.advertise;

import java.io.Serializable;
import java.util.List;

public class Advertisement implements Serializable {
    private  int id;                        //广告位ID
    private  String positionCode;           //广告位代码
    private  int tid;                                //广告模版ID
    private  List<AdContent> adContents;                //广告内容集合

    public Advertisement() {
    }

    public Advertisement(int id, String positionCode, int tid, List<AdContent> adContents) {
        this.id = id;
        this.positionCode = positionCode;
        this.tid = tid;
        this.adContents = adContents;
    }

    public int getId() {
        return id;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public int getTid() {
        return tid;
    }

    public List getAdContents() {
        return adContents;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public void setAdContents(List adContents) {
        this.adContents = adContents;
    }

    @Override
    public String toString() {
        return "Advertisement{" +
                "id=" + id +
                ", positionCode='" + positionCode + '\'' +
                ", tid=" + tid +
                ", adContents=" + adContents +
                '}';
    }
}

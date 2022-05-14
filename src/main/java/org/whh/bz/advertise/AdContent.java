package org.whh.bz.advertise;

import java.io.Serializable;

public class AdContent implements Serializable {
    private  int id;
    private  String name;
    private  String url;
    private  String imgURL;
    private  int sequence;

    public AdContent() {
    }

    public AdContent(int id, String name, String url, String imgURL, int sequence) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.imgURL = imgURL;
        this.sequence = sequence;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    @Override
    public String toString() {
        return "AdContent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", imgURL='" + imgURL + '\'' +
                ", sequence=" + sequence +
                '}';
    }
}

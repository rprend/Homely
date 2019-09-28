package org.homely;

public class Critiques {

    private int image;
    private String title;
    private String desc;
    private float pitchOffset;
    private float yawOffset;
    public Critiques(String title, String desc, float pitchOffset, float yawOffset) {
        this.title = title;
        this.desc = desc;
        this.pitchOffset = pitchOffset;
        this.yawOffset = yawOffset;
    }

    public float getPitchOffset() {
        return pitchOffset;
    }

    public float getYawOffset() {
        return yawOffset;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

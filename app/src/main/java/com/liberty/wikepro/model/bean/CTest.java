package com.liberty.wikepro.model.bean;

import android.os.Parcel;

/**
 * Created by liberty on 2017/3/30.
 */

public class CTest extends BaseBean implements itemType{

    private int id;
    private int cvideo_id;
    private String cvcontent;
    private String cvanswer;
    private int cvtimepoint;
    private String cvoptions;
    private int cvtype;
    private String cvanalysis;
    private String solution;
    private String answer;
    private int flag;

    private CTest(Parcel in){
        id=in.readInt();
        cvideo_id=in.readInt();
        cvcontent=in.readString();
        cvtimepoint=in.readInt();
        cvoptions=in.readString();
        cvtype=in.readInt();
        cvanalysis=in.readString();
        solution=in.readString();
        answer=in.readString();
        flag=in.readInt();
    }

    public CTest(){}

    public int getFlag() {
        return flag;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCvideo_id() {
        return cvideo_id;
    }

    public void setCvanalysis(String cvanalysis) {
        this.cvanalysis = cvanalysis;
    }

    public int getCvtimepoint() {
        return cvtimepoint;
    }

    public void setCvanswer(String cvanswer) {
        this.cvanswer = cvanswer;
    }

    public int getCvtype() {
        return cvtype;
    }

    public void setCvcontent(String cvcontent) {
        this.cvcontent = cvcontent;
    }

    public String getCvanalysis() {
        return cvanalysis;
    }

    public void setCvideo_id(int cvideo_id) {
        this.cvideo_id = cvideo_id;
    }

    public String getCvanswer() {
        return cvanswer;
    }

    public String getCvcontent() {
        return cvcontent;
    }

    public void setCvoptions(String cvoptions) {
        this.cvoptions = cvoptions;
    }

    public String getCvoptions() {
        return cvoptions;
    }

    public void setCvtimepoint(int cvtimepoint) {
        this.cvtimepoint = cvtimepoint;
    }

    public void setCvtype(int cvtype) {
        this.cvtype = cvtype;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     *
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(cvideo_id);
        dest.writeString(cvcontent);
        dest.writeString(cvanswer);
        dest.writeInt(cvtimepoint);
        dest.writeString(cvoptions);
        dest.writeInt(cvtype);
        dest.writeString(cvanalysis);
        dest.writeString(solution);
        dest.writeString(answer);
        dest.writeInt(flags);
    }

    public static final Creator<CTest> CREATOR=new Creator<CTest>() {
        @Override
        public CTest createFromParcel(Parcel source) {
            return new CTest(source);
        }

        @Override
        public CTest[] newArray(int size) {
            return new CTest[size];
        }
    };
}

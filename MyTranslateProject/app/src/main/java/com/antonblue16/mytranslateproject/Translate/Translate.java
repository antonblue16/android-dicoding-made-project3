package com.antonblue16.mytranslateproject.Translate;

import android.os.Parcel;
import android.os.Parcelable;

public class Translate implements Parcelable
{
    private int id;
    private String kata;
    private String description;
    private String swapTranslate;

    public Translate()
    {}

    public Translate(String kata, String description)
    {
        this.kata = kata;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKata() {
        return kata;
    }

    public void setKata(String kata) {
        this.kata = kata;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSwapTranslate() {
        return swapTranslate;
    }

    public void setSwapTranslate(String swapTranslate) {
        this.swapTranslate = swapTranslate;
    }


    protected Translate(Parcel in)
    {
        id = in.readInt();
        kata = in.readString();
        description = in.readString();
        swapTranslate = in.readString();
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeInt(this.id);
        parcel.writeString(this.kata);
        parcel.writeString(this.description);
        parcel.writeString(this.swapTranslate);
    }


    public static final Creator<Translate> CREATOR = new Creator<Translate>()
    {
        @Override
        public Translate createFromParcel(Parcel in)
        {
            return new Translate(in);
        }

        @Override
        public Translate[] newArray(int size)
        {
            return new Translate[size];
        }
    };
}

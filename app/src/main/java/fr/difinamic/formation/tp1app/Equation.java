package fr.difinamic.formation.tp1app;

import android.os.Parcel;
import android.os.Parcelable;

public class Equation implements Parcelable {
    private Double leftPart;
    private Double rightPart;
    private Double result;
    private String operator;

    public Equation (Double leftPart, Double rightPart, Double result, String operator) {
        this.leftPart = leftPart;
        this.rightPart = rightPart;
        this.result = result;
        this.operator = operator;
    }

    protected Equation(Parcel in) {

        leftPart = in.readDouble();
        rightPart = in.readDouble();
        result = in.readDouble();
        operator = in.readString();
    }

    public static final Creator<Equation> CREATOR = new Creator<Equation>() {
        @Override
        public Equation createFromParcel(Parcel in) {
            return new Equation(in);
        }

        @Override
        public Equation[] newArray(int size) {
            return new Equation[size];
        }
    };

    public Double getLeftPart() {
        return leftPart;
    }

    public Double getRightPart() {
        return rightPart;
    }

    public Double getResult() {
        return result;
    }

    public String getOperator() {
        return operator;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(leftPart);
        dest.writeDouble(rightPart);
        dest.writeDouble(result);
        dest.writeString(operator);
    }
}

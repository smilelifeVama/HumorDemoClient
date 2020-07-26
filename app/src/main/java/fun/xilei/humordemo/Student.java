package fun.xilei.humordemo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Student implements Parcelable {
    private int id;
    private String name;

    public Student(int id, String name) {
        this.name = name;
        this.id = id;
    }

    protected Student(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }

    @NonNull
    @Override
    public String toString() {
        return "Student[id=" + id + ", name=" + name + "]";
    }
}

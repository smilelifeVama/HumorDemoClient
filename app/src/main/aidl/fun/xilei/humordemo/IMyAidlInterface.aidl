// IMyAidlInterface.aidl
package fun.xilei.humordemo;
import fun.xilei.humordemo.Student;

// Declare any non-default types here with import statements

interface IMyAidlInterface {

    void addStudent(in Student student);

    List<Student> getCurrentStudents();
}

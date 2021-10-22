package com.example.main.exptest;

public class OptionalCourse extends Course {
    public int maxStuNum;

    public OptionalCourse(int i, String s) {
        super(i, s, 1);
    }

    public OptionalCourse(int id, String name, int type, int stuNum, String teacher, int maxStuNum) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.stuNum = stuNum;
        this.teacher = teacher;
        this.maxStuNum = maxStuNum;
    }

    @Override
    public void show() {
        System.out.print(id);
        System.out.print("  " + name);
        System.out.print("  选修（也要好好学）  " + teacher + "  " + stuNum + "  " + maxStuNum);
    }

    @Override
    public String toString() {
        return super.toString() + "  " + maxStuNum;
    }
}

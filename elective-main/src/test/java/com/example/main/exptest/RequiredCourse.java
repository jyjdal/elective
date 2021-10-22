package com.example.main.exptest;

public class RequiredCourse extends Course {
    // 学分
    public int credit;

    public RequiredCourse(int id, String name) {
        super(id, name, 0);
    }

    public RequiredCourse(int id, String name, int type, int stuNum, String teacher, int credit) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.stuNum = stuNum;
        this.teacher = teacher;
        this.credit = credit;
    }

    @Override
    public void show() {
        System.out.print(id);
        System.out.print("  " + name);
        System.out.print("  必修（好好学）  " + teacher + "  " + stuNum + "  " + credit);
    }

    @Override
    public String toString() {
        return super.toString() + "  " + credit;
    }
}

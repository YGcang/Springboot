package com.atguigu.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {

    public static void main(String[] args) {

        // 实现写操作
        // 设置文件路径
        // String filename = "/Users/yanggongcang/Desktop/write.xlsx";

        // 调用方法
        // EasyExcel.write(filename, DemoData.class).sheet("学生列表").doWrite(getData());

        // 实现读操作
        String filename = "/Users/yanggongcang/Desktop/write.xlsx";

        EasyExcel.read(filename, DemoData.class, new ExcelListener()).sheet().doRead();

    }

    // 创建返回list的方法
    private static List<DemoData> getData() {

        ArrayList<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++ ) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("lucy"+i);
            list.add(data);
        }

        return list;
    }
}

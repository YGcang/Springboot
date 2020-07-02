package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-06-29
 */

@CrossOrigin
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("/findAll")
    public R findAllTeacher() {
//        try {
//            int i = 10/0;
//        } catch (Exception e) {
//            throw new GuliException(20001, "执行了GuliException");
//        }

        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("/delete/{id}")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id) {

        boolean b = teacherService.removeById(id);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    // 分页查询
    @GetMapping("/pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable Long current,
                             @PathVariable Long limit) {

        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        IPage<EduTeacher> page = teacherService.page(pageTeacher, null);

        Long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();

//        HashMap<String, Object> stringObjectHashMap = new HashMap<>();

        return R.ok().data("total", total).data("rows", records);
    }

    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable Long current,
                                  @PathVariable Long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);

        // 构建条件
        QueryWrapper<EduTeacher> eduTeacherQueryWrapper = new QueryWrapper<>();

        String name = teacherQuery.getName();
        String begin = teacherQuery.getBegin();
        Integer level = teacherQuery.getLevel();
        String end = teacherQuery.getEnd();

        if (!StringUtils.isEmpty(name)) {
            eduTeacherQueryWrapper.like("name", name);
        }

        if (!StringUtils.isEmpty(level)) {
            eduTeacherQueryWrapper.eq("level", level);
        }

        if (!StringUtils.isEmpty(begin)) {
            eduTeacherQueryWrapper.ge("gmt_create", begin);
        }

        if (!StringUtils.isEmpty(end)) {
            eduTeacherQueryWrapper.le("gmt_create", end);
        }

        teacherService.page(pageTeacher, eduTeacherQueryWrapper);
        Long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }

    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = teacherService.save(eduTeacher);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @GetMapping("/getTeacher/{id}")
    public R getTeacher(@PathVariable String id) {
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("teacher", eduTeacher);
    }

    @PostMapping("/updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean b = teacherService.updateById(eduTeacher);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }
    }

}


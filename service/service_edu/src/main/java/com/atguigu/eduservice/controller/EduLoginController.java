package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/eduservice/user")
public class EduLoginController {

    // login
    @PostMapping("/login")
    public R login() {


        return R.ok().data("token", "admin");
    }

    @GetMapping("/info")
    public R info() {


        return R.ok()
                .data("roles", "[admin]")
                .data("name", "admin")
                .data("avatar", "https://i.pinimg.com/originals/be/2b/f2/be2bf230430e4fedce6b23b08fe4ee56.jpg");
    }


}

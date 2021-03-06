package com.itheima.demo;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("hello")
@PreAuthorize("hasRole('ROLE_ADMIN')")
//@PreAuthorize("hasAuthority('add')")
public class HelloController {

    @RequestMapping("demo1")
    public String demo1(){
        System.out.println("demo1方法执行了");
        return "forward:/a.html";
    }

    @RequestMapping("demo2")
    public String demo2(){
        System.out.println("demo2方法执行了");
        return "redirect:/a.html";
    }

    @PreAuthorize("hasAuthority('add')")
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("demo3")
    public String demo3(){
        System.out.println("demo3方法执行了");
        return "redirect:/a.html";
    }
}

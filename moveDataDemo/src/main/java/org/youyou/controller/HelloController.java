package org.youyou.controller;

import org.youyou.annotation.Controller;
import org.youyou.annotation.RequestMapping;

/**
 * TODO:add description of class here, then delete the line.
 *
 * @author youpengfeiinfo
 * @version 13-6-16
 */
@Controller
@RequestMapping("hello")
public class HelloController {

    @RequestMapping("sayHello")
    public String sayHello() {
        return "hello";
    }

    @RequestMapping("sayHello1")
    public String sayHello1() {
        return "hello1";
    }
}

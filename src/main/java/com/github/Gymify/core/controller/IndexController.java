package com.github.Gymify.core.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @PreAuthorize("permitAll()")
    @RequestMapping({
        "", "/",
    }
    )
    public String toIndex() {
        return "forward:/index.html";
    }
}

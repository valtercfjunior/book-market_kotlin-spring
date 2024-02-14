package com.bookmarket.bookmarket.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("admin")
class AdminController() {

    @GetMapping("/report")
    fun getAll(@RequestParam name: String?): String {
        return "This is a Report. Only admin permitted!"
    }

}

package com.example.sys.controller;

import com.example.common.vo.Result;
import com.example.sys.entity.Menu;
import com.example.sys.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lisir
 * @since 2023-03-06
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private IMenuService menuService;

    @GetMapping
    public Result<?> getAllMenu(){
        List<Menu> menuList =  menuService.getAllMenu();
        return Result.success(menuList);
    }
}

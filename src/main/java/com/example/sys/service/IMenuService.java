package com.example.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.sys.entity.Menu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lisir
 * @since 2023-03-06
 */
public interface IMenuService extends IService<Menu> {
    List<Menu> getAllMenu();
    List<Menu> getMenuListByUserId(Integer userId);
}

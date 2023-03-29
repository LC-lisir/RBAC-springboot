package com.example.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.sys.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lisir
 * @since 2023-03-06
 */
public interface MenuMapper extends BaseMapper<Menu> {
    public List<Menu> getMenuListByUserId(@Param("userId") Integer userId, @Param("pid") Integer pid);

}

package com.example.sys.service.impl;

import com.example.sys.entity.Role;
import com.example.sys.mapper.RoleMapper;
import com.example.sys.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lisir
 * @since 2023-03-06
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}

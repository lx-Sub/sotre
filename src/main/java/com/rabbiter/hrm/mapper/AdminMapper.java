package com.rabbiter.hrm.mapper;

import com.rabbiter.hrm.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface AdminMapper {
    List<Admin> selectAll();
    Admin selectById(Long id);
    Admin selectByUsername(String username);
    void insert(Admin admin);
    void updateById(Admin admin);
    void deleteById(Long id);
}
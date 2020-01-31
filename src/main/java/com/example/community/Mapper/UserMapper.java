package com.example.community.Mapper;

import com.example.community.Model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id = #{creator}")
    User findByid(@Param("creator") Integer creator);

    @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(User user);

    @Update("update user set token = #{token} where account_id = #{accountId}")
    void updateByAccountId(User user);
}

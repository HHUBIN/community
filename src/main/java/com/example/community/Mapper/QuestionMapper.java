package com.example.community.Mapper;

import com.example.community.Model.Question;
import com.example.community.Model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question limit #{page},#{size}")
    List<Question> list(@Param("page") Integer page, @Param("size") Integer size);

    @Select("select count(1) from question")
    Integer findTotle();

    @Select(("select count(1) from question where creator = #{id}"))
    Integer findByUserTotle(User user);

    @Select("select * from question where creator = #{id} limit #{page},#{size}")
    List<Question> listUser(@Param("page")Integer offset, @Param("size")Integer size, @Param("id") Integer id);

    @Select("select * from question where id = #{id}")
    Question findById(@Param("id") Integer id);

    @Update("update  question set title=#{title},description=#{description},gmt_create=#{gmtCreate},gmt_modified=#{gmtModified},creator=#{creator},tag=#{tag} where id = #{id}")
    void update(Question question);
}

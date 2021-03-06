package com.lazydsr.platform.mapper;

import com.lazydsr.platform.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {
    //@Delete({
    //        "delete from sys_user_role",
    //        "where id = #{id,jdbcType=VARCHAR}"
    //})
    //int deleteByPrimaryKey(String id);
    //
    //@Insert({
    //        "insert into sys_user_role (id, u_id, ",
    //        "r_id, creator, create_date, ",
    //        "modifier, modify_date, ",
    //        "status)",
    //        "values (#{id,jdbcType=VARCHAR}, #{uId,jdbcType=VARCHAR}, ",
    //        "#{rId,jdbcType=VARCHAR}, #{creator,jdbcType=VARCHAR}, #{createDate,jdbcType=VARCHAR}, ",
    //        "#{modifier,jdbcType=VARCHAR}, #{modifyDate,jdbcType=VARCHAR}, ",
    //        "#{status,jdbcType=INTEGER})"
    //})
    //int insert(UserRole record);
    //
    //@InsertProvider(type = UserRoleSqlProvider.class, method = "insertSelective")
    //int insertSelective(UserRole record);
    //
    //@Select({
    //        "select",
    //        "id, u_id, r_id, creator, create_date, modifier, modify_date, status",
    //        "from sys_user_role",
    //        "where id = #{id,jdbcType=VARCHAR}"
    //})
    //@Results({
    //        @Result(column = "id", property = "id", jdbcType = JdbcType.VARCHAR, id = true),
    //        @Result(column = "u_id", property = "uId", jdbcType = JdbcType.VARCHAR),
    //        @Result(column = "r_id", property = "rId", jdbcType = JdbcType.VARCHAR),
    //        @Result(column = "creator", property = "creator", jdbcType = JdbcType.VARCHAR),
    //        @Result(column = "create_date", property = "createDate", jdbcType = JdbcType.VARCHAR),
    //        @Result(column = "modifier", property = "modifier", jdbcType = JdbcType.VARCHAR),
    //        @Result(column = "modify_date", property = "modifyDate", jdbcType = JdbcType.VARCHAR),
    //        @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER)
    //})
    //UserRole selectByPrimaryKey(String id);
    //
    //@UpdateProvider(type = UserRoleSqlProvider.class, method = "updateByPrimaryKeySelective")
    //int updateByPrimaryKeySelective(UserRole record);
    //
    //@Update({
    //        "update sys_user_role",
    //        "set u_id = #{uId,jdbcType=VARCHAR},",
    //        "r_id = #{rId,jdbcType=VARCHAR},",
    //        "creator = #{creator,jdbcType=VARCHAR},",
    //        "create_date = #{createDate,jdbcType=VARCHAR},",
    //        "modifier = #{modifier,jdbcType=VARCHAR},",
    //        "modify_date = #{modifyDate,jdbcType=VARCHAR},",
    //        "status = #{status,jdbcType=INTEGER}",
    //        "where id = #{id,jdbcType=VARCHAR}"
    //})
    //int updateByPrimaryKey(UserRole record);
}
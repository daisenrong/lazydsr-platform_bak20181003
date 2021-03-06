package com.lazydsr.platform.mapper;

import com.lazydsr.platform.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select({
            "select",
            "id, name, username, password, workcode, sum_password_wrong, password_lock, old_password1, ",
            "old_password2, email, mobile, telphone, organization, company, department, id_card, ",
            "current_login_date, last_login_date, order_num, account_type, creator, create_date, ",
            "modifier, modify_date, status",
            "from sys_user",
            "where username = #{username,jdbcType=VARCHAR}",
            "and status=0"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "username", property = "username", jdbcType = JdbcType.VARCHAR),
            @Result(column = "password", property = "password", jdbcType = JdbcType.VARCHAR),
            @Result(column = "workcode", property = "workcode", jdbcType = JdbcType.VARCHAR),
            @Result(column = "sum_password_wrong", property = "sumPasswordWrong", jdbcType = JdbcType.INTEGER),
            @Result(column = "password_lock", property = "passwordLock", jdbcType = JdbcType.INTEGER),
            @Result(column = "old_password1", property = "oldPassword1", jdbcType = JdbcType.VARCHAR),
            @Result(column = "old_password2", property = "oldPassword2", jdbcType = JdbcType.VARCHAR),
            @Result(column = "email", property = "email", jdbcType = JdbcType.VARCHAR),
            @Result(column = "mobile", property = "mobile", jdbcType = JdbcType.VARCHAR),
            @Result(column = "telphone", property = "telphone", jdbcType = JdbcType.VARCHAR),
            @Result(column = "organization", property = "organization", jdbcType = JdbcType.VARCHAR),
            @Result(column = "company", property = "company", jdbcType = JdbcType.VARCHAR),
            @Result(column = "department", property = "department", jdbcType = JdbcType.VARCHAR),
            @Result(column = "id_card", property = "idCard", jdbcType = JdbcType.VARCHAR),
            @Result(column = "current_login_date", property = "currentLoginDate", jdbcType = JdbcType.VARCHAR),
            @Result(column = "last_login_date", property = "lastLoginDate", jdbcType = JdbcType.VARCHAR),
            @Result(column = "order_num", property = "orderNum", jdbcType = JdbcType.DOUBLE),
            @Result(column = "account_type", property = "accountType", jdbcType = JdbcType.INTEGER),
            @Result(column = "creator", property = "creator", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_date", property = "createDate", jdbcType = JdbcType.VARCHAR),
            @Result(column = "modifier", property = "modifier", jdbcType = JdbcType.VARCHAR),
            @Result(column = "modify_date", property = "modifyDate", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER)
    })
    User selectByUsername(String username);

    @Select({
            "select",
            "id, name, username, password, workcode, sum_password_wrong, password_lock, old_password1, ",
            "old_password2, email, mobile, telphone, organization, company, department, id_card, ",
            "current_login_date, last_login_date, order_num, account_type, creator, create_date, ",
            "modifier, modify_date, status",
            "from sys_user",
            "where status=0"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "username", property = "username", jdbcType = JdbcType.VARCHAR),
            @Result(column = "password", property = "password", jdbcType = JdbcType.VARCHAR),
            @Result(column = "workcode", property = "workcode", jdbcType = JdbcType.VARCHAR),
            @Result(column = "sum_password_wrong", property = "sumPasswordWrong", jdbcType = JdbcType.INTEGER),
            @Result(column = "password_lock", property = "passwordLock", jdbcType = JdbcType.INTEGER),
            @Result(column = "old_password1", property = "oldPassword1", jdbcType = JdbcType.VARCHAR),
            @Result(column = "old_password2", property = "oldPassword2", jdbcType = JdbcType.VARCHAR),
            @Result(column = "email", property = "email", jdbcType = JdbcType.VARCHAR),
            @Result(column = "mobile", property = "mobile", jdbcType = JdbcType.VARCHAR),
            @Result(column = "telphone", property = "telphone", jdbcType = JdbcType.VARCHAR),
            @Result(column = "organization", property = "organization", jdbcType = JdbcType.VARCHAR),
            @Result(column = "company", property = "company", jdbcType = JdbcType.VARCHAR),
            @Result(column = "department", property = "department", jdbcType = JdbcType.VARCHAR),
            @Result(column = "id_card", property = "idCard", jdbcType = JdbcType.VARCHAR),
            @Result(column = "current_login_date", property = "currentLoginDate", jdbcType = JdbcType.VARCHAR),
            @Result(column = "last_login_date", property = "lastLoginDate", jdbcType = JdbcType.VARCHAR),
            @Result(column = "order_num", property = "orderNum", jdbcType = JdbcType.DOUBLE),
            @Result(column = "account_type", property = "accountType", jdbcType = JdbcType.INTEGER),
            @Result(column = "creator", property = "creator", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_date", property = "createDate", jdbcType = JdbcType.VARCHAR),
            @Result(column = "modifier", property = "modifier", jdbcType = JdbcType.VARCHAR),
            @Result(column = "modify_date", property = "modifyDate", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER)
    })
    List<User> selectAllNormal();

    @Select({
            "select",
            "id",
            "from sys_user",
            "where status=0"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.VARCHAR, id = true)
    })
    List<String> selectAllIdNormal();

    @Select({
            "select",
            "id",
            "from sys_user"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.VARCHAR, id = true)
    })
    List<String> selectAllId();

}
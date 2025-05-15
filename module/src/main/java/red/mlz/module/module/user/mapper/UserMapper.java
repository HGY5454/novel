package red.mlz.module.module.user.mapper;

import red.mlz.module.module.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigInteger;

@Mapper
public interface UserMapper {
    @Select("select * from user where  id = #{id}")
    User extractById(@Param("id") BigInteger id);

    @Select("select * from user where phone= #{phone} and is_deleted = 0")
    User getUserByPhone(@Param("phone") String phone);

    @Select("select * from user WHERE phone=#{phone}")
    User extractByPhone(@Param("phone") String phone);

    @Select("select * from user where id=#{id} and is_deleted = 0")
    User getUserById(@Param("id") BigInteger id);

    int insert(@Param("user") User user);

    int update(@Param("user") User user);

    @Update("update user set is_deleted = 1,update_time=#{time} where id = #{id} limit 1")
    int delete(@Param("id") BigInteger id, @Param("time")Integer time);
}

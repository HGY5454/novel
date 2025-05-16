package red.mlz.module.module.kinds.mapper;


import red.mlz.module.module.kinds.entity.Kinds;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigInteger;
import java.util.List;


@Mapper
public interface KindsMapper {

        @Select("select * from kinds where  id = #{id}")
        Kinds getById(@Param("id")BigInteger id);

        @Select("select * from kinds where  id = #{id}")
        Kinds extractById(@Param("id")BigInteger id);

        @Select("select * from kinds where parent_id is null and is_deleted = 0")
        List<Kinds> getParents();

        @Select("select * from kinds where parent_id = #{kindsId} and is_deleted = 0")
        List<Kinds> getChildren(@Param("kindsId")BigInteger kindsId);

        @Select("select * from kinds where is_deleted = 0 and parent_id is not null ")
        List<Kinds> getKinds();

        @Select("select * from kinds where is_deleted = 0")
        List<Kinds> getAll();

        int insert(@Param("kinds") Kinds kinds);

        int update(@Param("kinds") Kinds kinds);

        @Update("update kinds set is_deleted = 1,update_time=#{time} where id = #{id} limit 1")
        int delete(@Param("id") BigInteger id, @Param("time")Integer time);

        List<Kinds> selectKindsByIds(@Param("ids") String ids);

}

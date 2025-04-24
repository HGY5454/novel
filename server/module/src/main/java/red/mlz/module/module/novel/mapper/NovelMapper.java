package red.mlz.module.module.novel.mapper;

import red.mlz.module.module.novel.entity.Novel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface NovelMapper {

    @Select("select * from novel  WHERE id=#{id} and is_deleted=0")
    Novel getById(@Param("id")BigInteger id);

    @Select("select * from novel  WHERE kinds_id=#{KindsId} and is_deleted=0")
    List<Novel> getByKindsId(@Param("KindsId")BigInteger KindsId);

    @Select("select id from kinds where kinds_name like concat('%',#{titleKeyWord},'%') and kinds.is_deleted = 0")
    List<String> getKindsIdByKeyWord(@Param("titleKeyWord")String titleKeyWord);

    List<Novel> getPage(@Param("titleKeyWord")String titleKeyWord,
                        @Param("start")Integer start,
                        @Param("pageSize")Integer pageSize,
                        @Param("ids")String ids);

    @Select("select * from novel where  id = #{id}")
    Novel extractById(@Param("id")BigInteger id);

    int getCount(@Param("titleKeyWord")String titleKeyWord,String ids);

    @Select("select * from novel where is_deleted = 0")
    List<Novel> getAll();

    int update(@Param("novel")Novel novel);

    int insert(@Param("novel")Novel novel);

    @Update("update novel set is_deleted = 1,update_time=#{time} where id = #{id} limit 1")
    int delete(@Param("id")BigInteger id, @Param("time")Integer time);

    @Select("select info from novel where id = #{id} and is_deleted = 0")
    String getInfo(@Param("id")BigInteger id);

    @Update("update novel set info = #{info} where id = #{id}")
    int updateInfo(@Param("id")BigInteger id, @Param("info")String info);

}

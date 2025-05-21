package red.mlz.module.module.tag.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import red.mlz.module.module.tag.entity.Tag;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface TagMapper {
    @Select("select * from tag where  id = #{id}")
    Tag extractById(@Param("id") BigInteger id);

    int insert(@Param("tag") Tag tag);

    int update(@Param("tag") Tag tag);

    @Update("update tag set is_deleted = 1,update_time=#{time} where id = #{id} limit 1")
    int delete(@Param("id") BigInteger id, @Param("time") Integer time);

    @Select("select * from tag where  id = #{id} and is_deleted=0")
    Tag getById(@Param("id") BigInteger id);


    @Select("select * from tag where  tag_name = #{tagName} and is_deleted=0")
    Tag getTagByTagName(@Param("tagName") String tagName);

    @Select("select * from tag")
    List<Tag> getAllTags();

}

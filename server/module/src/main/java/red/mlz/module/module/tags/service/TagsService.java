package red.mlz.module.module.tags.service;

import org.springframework.stereotype.Service;
import red.mlz.module.module.tags.entity.Tag;
import red.mlz.module.module.tags.mapper.TagsMapper;
import red.mlz.module.utils.BaseUtils;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

@Service
public class TagsService {
    @Resource
    private TagsMapper tagsMapper;

    public Tag extractById(BigInteger id) {
        return tagsMapper.extractById(id);
    }

    public Tag extractByTagName(String tagName) {
        return tagsMapper.extractByTagName(tagName);
    }

    public List<Tag> findAll() {
        return tagsMapper.getAllTags();
    }

    public int getTagsIdByTagName(String tagName) {
        return tagsMapper.getTagsIdByTagName(tagName);
    }

    public Integer insert(Tag tag) {
        tagsMapper.insert(tag);
        return tag.getId();
    }

    public int update(Tag tags) {
        return tagsMapper.update(tags);
    }

    public int delete(BigInteger id) {
        return tagsMapper.delete(id, BaseUtils.currentSeconds());
    }
}

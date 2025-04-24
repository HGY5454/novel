package red.mlz.module.module.tag.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.mlz.module.module.tag.entity.Tag;
import red.mlz.module.module.tag.mapper.TagMapper;
import red.mlz.module.utils.BaseUtils;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

@Service
public class TagService {
    @Resource
    private TagMapper tagMapper;

    public Tag getTagByTagName(String tagName) {
        return tagMapper.getTagByTagName(tagName);
    }

    public List<Tag> findAll() {
        return tagMapper.getAllTags();
    }

    public Tag extractById(BigInteger id) {
        return tagMapper.extractById(id);
    }

    public Tag getById(BigInteger id) {
        return tagMapper.getById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public BigInteger edit(BigInteger id,String tagName) {
        Tag newTag = new Tag();
        newTag.setTagName(tagName);
        newTag.setIsDeleted(0);
        newTag.setUpdateTime(BaseUtils.currentSeconds());
        if (id == null) {
            newTag.setCreateTime(BaseUtils.currentSeconds());
            return insert(newTag);
        } else {
            if (id == null) {
                throw new RuntimeException("系统异常");
            }
            newTag.setId(id);
            return update(newTag);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public BigInteger insert(Tag tag) {
        tagMapper.insert(tag);
        return tag.getId();
    }
    @Transactional(rollbackFor = Exception.class)
    public BigInteger update(Tag tag) {
        tagMapper.update(tag);
        return tag.getId();
    }
    @Transactional(rollbackFor = Exception.class)
    public int delete(BigInteger id) {
        return tagMapper.delete(id, BaseUtils.currentSeconds());
    }
}

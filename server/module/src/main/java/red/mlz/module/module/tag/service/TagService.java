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
    public BigInteger edit( Tag tag) {
        Tag newTag = new Tag();
        newTag.setTagName(tag.getTagName());
        newTag.setUpdateTime(BaseUtils.currentSeconds());
        newTag.setIsDeleted(0);
        if (tag.getId() == null) {
            newTag.setCreateTime(BaseUtils.currentSeconds());
            return insert(tag);
        } else {
            if (getById(tag.getId()) == null) {
                throw new RuntimeException("系统异常");
            }
            newTag.setId(tag.getId());
            return update(tag);
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

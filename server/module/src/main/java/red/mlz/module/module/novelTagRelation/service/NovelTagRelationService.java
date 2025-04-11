package red.mlz.module.module.novelTagRelation.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.mlz.module.module.novelTagRelation.entity.NovelTagRelation;
import red.mlz.module.module.novelTagRelation.mapper.NovelTagRelationMapper;
import red.mlz.module.utils.BaseUtils;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

@Service
public class NovelTagRelationService {
    @Resource
    private NovelTagRelationMapper novelTagRelationMapper;

    @Transactional(rollbackFor = Exception.class)
    public int delete(BigInteger id) {
        return novelTagRelationMapper.delete(id, BaseUtils.currentSeconds());
    }

    @Transactional(rollbackFor = Exception.class)
    public NovelTagRelation selectByNovelIdAndTagId(BigInteger novelId, BigInteger tagId) {
        return novelTagRelationMapper.SelectByNovelIdaAndTagsId(novelId, tagId);
    }

    @Transactional(rollbackFor = Exception.class)
    public int edit(NovelTagRelation novelTagRelation) {
        if (novelTagRelation.getId() == null) {
            return novelTagRelationMapper.insert(novelTagRelation);
        }else {
            return novelTagRelationMapper.update(novelTagRelation);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public List<NovelTagRelation> selectByNovelId(BigInteger novelId) {
        return novelTagRelationMapper.SelectByNovelId(novelId);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteByTagId(BigInteger tagId, BigInteger novelId) {
        return novelTagRelationMapper.deleteByTagId(tagId,novelId, BaseUtils.currentSeconds());
    }
}

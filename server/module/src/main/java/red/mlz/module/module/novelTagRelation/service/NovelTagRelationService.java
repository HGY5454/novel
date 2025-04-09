package red.mlz.module.module.novelTagRelation.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.mlz.module.module.novelTagRelation.entity.NovelTagRelation;
import red.mlz.module.module.novelTagRelation.mapper.NovelTagRelationMapper;
import red.mlz.module.module.tag.entity.Tag;
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

    public NovelTagRelation SelectByNovelIdAndTagId(BigInteger novelId, BigInteger tagId) {
        return novelTagRelationMapper.SelectByNovelIdaAndTagsId(novelId, tagId);
    }

    public int edit(NovelTagRelation novelTagRelation) {
        if (novelTagRelation.getId() == null) {
            return novelTagRelationMapper.insert(novelTagRelation);
        }else {
            return novelTagRelationMapper.update(novelTagRelation);
        }
    }

    public List<NovelTagRelation> SelectByNovelId(BigInteger novelId) {
        return novelTagRelationMapper.SelectByNovelId(novelId);
    }

    public int DeleteByTagId(BigInteger tagId, BigInteger novelId) {
        return novelTagRelationMapper.deleteByTagId(tagId,novelId, BaseUtils.currentSeconds());
    }
}

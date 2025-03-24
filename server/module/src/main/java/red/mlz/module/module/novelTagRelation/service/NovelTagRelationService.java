package red.mlz.module.module.novelTagRelation.service;

import org.springframework.stereotype.Service;
import red.mlz.module.module.novelTagRelation.entity.NovelTagRelation;
import red.mlz.module.module.novelTagRelation.mapper.NovelTagRelationMapper;
import red.mlz.module.utils.BaseUtils;

import javax.annotation.Resource;
import java.math.BigInteger;

@Service
public class NovelTagRelationService {
    @Resource
    private NovelTagRelationMapper novelTagRelationMapper;

    public int insert(NovelTagRelation novelTagRelation) {
        return novelTagRelationMapper.insert(novelTagRelation);
    }

    public int update(NovelTagRelation  novelTagRelation) {
        return novelTagRelationMapper.update(novelTagRelation);
    }

    public int delete(BigInteger id) {
        return novelTagRelationMapper.delete(id, BaseUtils.currentSeconds());
    }
}

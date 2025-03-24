package red.mlz.module.module.kinds.service;


import red.mlz.module.module.kinds.entity.Kinds;
import red.mlz.module.module.kinds.mapper.KindsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

@Service
public class KindsService {
    @Resource
    private KindsMapper kindsMapper;

    public List<Kinds> getKinds() {
        return kindsMapper.getKinds();
    }

    public List<Kinds> getAllKinds() {
        return kindsMapper.getAll();
    }

    public List<Kinds> getParentKinds() {
        return kindsMapper.getParents();
    }

    public List<Kinds> getChildKinds(BigInteger kindsId) {
        return kindsMapper.getChildren(kindsId);
    }

    public Kinds getKindsInfoById(BigInteger id) {
        return kindsMapper.getById(id);
    }

    public Kinds getKindsById(BigInteger id) {
        return kindsMapper.extractById(id);
    }


    public int insertKinds(String kindsName , String kindsImage) {

        int timestamp = (int) (System.currentTimeMillis() / 1000);

        Kinds kinds = new Kinds();
        kinds.setKindsName(kindsName);
        kinds.setKindsImage(kindsImage);
        kinds.setCreateTime(timestamp);
        kinds.setUpdateTime(timestamp);
        kinds.setIsDeleted(0);

        return kindsMapper.insert(kinds);
    }

    public int updateKinds(BigInteger id , String kindsName, String kindsImage) {
        int timestamp = (int) (System.currentTimeMillis() / 1000);
        Kinds kinds = new Kinds();
        kinds.setId(id);
        kinds.setKindsName(kindsName);
        kinds.setKindsImage(kindsImage);
        kinds.setCreateTime(timestamp);
        kinds.setUpdateTime(timestamp);
        kinds.setIsDeleted(0);

        return kindsMapper.update(kinds);
    }


    public int deleteKinds(BigInteger id) {
        return kindsMapper.delete(id,(int) (System.currentTimeMillis() / 1000));
    }
}

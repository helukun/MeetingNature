package com.example.follow_microservice.service;

import com.example.follow_microservice.model.Follow;
import com.example.follow_microservice.dao.FollowDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FollowService {
    @Autowired
    private FollowDao followDao;

    public String setNextId(){
        List<Follow> followList=followDao.findAll();
        int curMaxId=0;
        for(Follow f:followList){
            curMaxId=Math.max(curMaxId,Integer.parseInt(f.getId()));
        }
        int result=curMaxId+1;
        return result+"";
    }

    public int isExist(String followerid,String subjectid){
        Follow checkFollow=new Follow();
        checkFollow.setFollowerId(followerid)
                .setSubjectId(subjectid);
        Example<Follow> followExample=Example.of(checkFollow);
        long count=followDao.count(followExample);

        int result=0;
        if((int)count!=0){
            result=1;
        }
        return result;
    }

    public Follow findByPKNo(String followerId,String subjectId){
        Follow follow=null;
        int exist=this.isExist(followerId,subjectId);
        if(exist!=0){
            Follow checkFollow=new Follow();
            checkFollow.setFollowerId(followerId)
                    .setSubjectId(subjectId);
            Example<Follow> followExample=Example.of(checkFollow);
            List<Follow> followList=followDao.findAll(followExample);
            follow=followList.get(0);
        }
        return follow;
    }

    public void deleteByPK(String followerid,String subjectid){
        Follow follow=this.findByPKNo(followerid,subjectid);
        if(follow!=null)
            followDao.delete(follow);
    }


    public int addFollow(Follow follow){
        String followerid=follow.getFollowerId();
        String subjectid=follow.getSubjectId();
        int exist=this.isExist(followerid,subjectid);
        int success=0;
        if(exist==0){
            follow.setId(this.setNextId());
            followDao.save(follow);
            success=1;
            System.out.println("增加成功");
        }
        else{
            System.out.println("关注已存在，无法添加！");
        }
        return success;
    }

    public int changeFollow(Follow newFollow){
        int exist=this.isExist(newFollow.getFollowerId(),newFollow.getSubjectId());
        if(exist==1){
            Follow targetFollow=this.findByPKNo(newFollow.getFollowerId(),newFollow.getSubjectId());
            newFollow.setId(targetFollow.getId());
            followDao.save(newFollow);
        }
        return exist;
    }

    public List findAllFollowBySBid(String subjectId){
        Follow checkFollow=new Follow();
        checkFollow.setSubjectId(subjectId);
        Example<Follow> followExample=Example.of(checkFollow);
        List<Follow> followList=followDao.findAll(followExample);
        return  followList;
    }

    public List findAllFollowByFOid(String followerId){
        Follow checkFollow=new Follow();
        checkFollow.setFollowerId(followerId);
        Example<Follow> followExample=Example.of(checkFollow);
        List<Follow> followList=followDao.findAll(followExample);
        return  followList;
    }
}

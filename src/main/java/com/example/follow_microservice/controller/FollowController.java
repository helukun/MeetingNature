package com.example.follow_microservice.controller;

import com.example.follow_microservice.dao.FollowDao;
import com.example.follow_microservice.model.Follow;
import com.example.follow_microservice.service.FollowService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * @author ：ZXM+LJC
 * @description：FollowController
 * @date ：2022-12-17 17:57
 * @version : 2.0
 */

@CrossOrigin
@RestController
public class FollowController {
    @Autowired
    private FollowService followService;

    //good
    @PostMapping("/v2.0/follow-microservice/follow")
    public int addFollow(Follow newFollow){return followService.addFollow(newFollow);}

    //good
    @DeleteMapping("/v2.0/follow-microservice/follow")
    public void deleteFollow(Follow follow){
        followService.deleteByPK(follow.getFollowerId(),follow.getSubjectId());
    }

    @PutMapping("/v2.0/follow-microservice/follow")
    public int changeFollow(Follow newfollow){
        return followService.changeFollow(newfollow);
    }

    //good
    @GetMapping("/v2.0/follow-microservice/follow")
    public Follow findFollowByPK(String followerId,String subjectId){
        return followService.findByPKNo(followerId,subjectId);
    }

    //good
    @GetMapping("/v2.0/follow-microservice/follow/followerId")
    public List<Follow> findAllFollowByFOid(String followerId){
        return followService.findAllFollowByFOid(followerId);
    }

    //good
    @GetMapping("/v2.0/follow-microservice/follow/subjectId")
    public List<Follow> findAllFollowBySBid(String subjectId){
        return followService.findAllFollowBySBid(subjectId);
    }
}

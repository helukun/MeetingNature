package com.example.follow_microservice.controller;

import com.example.follow_microservice.dao.FollowDao;
import com.example.follow_microservice.model.Follow;
import com.example.follow_microservice.service.FollowService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@RestController
public class FollowController {
    @Autowired
    private FollowService followService;

    //good
    @PostMapping("/follow-microservice/follow")
    public int addFollow(Follow newFollow){return followService.addFollow(newFollow);}

    //good
    @DeleteMapping("/follow-microservice/follow")
    public void deleteFollow(Follow follow){
        followService.deleteByPK(follow.getFollowerId(),follow.getSubjectId());
    }

    @PutMapping("/follow-microservice/follow")
    public int changeFollow(Follow newfollow){
        return followService.changeFollow(newfollow);
    }

    //good
    @GetMapping("/follow-microservice/follow")
    public Follow findFollowByPK(String followerId,String subjectId){
        return followService.findByPKNo(followerId,subjectId);
    }

    //good
    @GetMapping("/follow-microservice/follow/FO")
    public List<Follow> findAllFollowByFOid(String followerId){
        return followService.findAllFollowByFOid(followerId);
    }

    //good
    @GetMapping("/follow-microservice/follow/SB")
    public List<Follow> findAllFollowBySBid(String subjectId){
        return followService.findAllFollowBySBid(subjectId);
    }
}

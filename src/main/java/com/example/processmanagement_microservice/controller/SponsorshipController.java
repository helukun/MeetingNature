package com.example.processmanagement_microservice.controller;

import com.example.processmanagement_microservice.dao.SponsorshipDao;
import com.example.processmanagement_microservice.model.FeedBack;
import com.example.processmanagement_microservice.model.Sponsorship;
import com.example.processmanagement_microservice.service.SponsorshipService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * @author ：ZXM+LJC
 * @description：SponsorshipController
 * @date ：2022-12-17 17:59
 * @version : 2.0
 */

@CrossOrigin
@RestController
public class SponsorshipController {
    @Autowired
    private SponsorshipService sponsorshipService;

    //good
    @PostMapping("/v2.0/processmanagement-microservice/sponsorship")
    public int addSponsorship(Sponsorship newSponsorship){
        return sponsorshipService.addSponsorship(newSponsorship);
    }

    //good
    @DeleteMapping("/v2.0/processmanagement-microservice/sponsorship")
    public void deleteSponsorship(Sponsorship sponsorship){
        sponsorshipService.deleteByPK(sponsorship.getSponsorId(),sponsorship.getSubjectId());
    }

    //good
    @PutMapping("/v2.0/processmanagement-microservice/sponsorship")
    public int changeSponsorship(Sponsorship newsponsorship){
        return sponsorshipService.changeSponsorship(newsponsorship);
    }

    //good
    @GetMapping("/v2.0/processmanagement-microservice/PK")
    public Sponsorship findSponsorshipByPK(String sponsorId, String subjectId){
        return sponsorshipService.findByPKNo(sponsorId,subjectId);
    }

    //good
    @GetMapping("/v2.0/processmanagement-microservice/sponsorship/sponsorId")
    public List<FeedBack> findAllSponsorshipBySPid(String sponsorId){
        return sponsorshipService.findAllSponsorshipBySPid(sponsorId);
    }

    //good
    @GetMapping("/v2.0/processmanagement-microservice/sponsorship/subjectId")
    public List findAllSponsorshipBySBid(String subjectId){
        return sponsorshipService.findAllSponsorshipBySBid(subjectId);
    }
}

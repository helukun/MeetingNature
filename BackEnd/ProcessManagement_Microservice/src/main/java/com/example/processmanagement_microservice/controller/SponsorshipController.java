package com.example.processmanagement_microservice.controller;

import com.example.processmanagement_microservice.dao.SponsorshipDao;
import com.example.processmanagement_microservice.model.FeedBack;
import com.example.processmanagement_microservice.model.Sponsorship;
import com.example.processmanagement_microservice.service.SponsorshipService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@RestController
public class SponsorshipController {
    @Autowired
    private SponsorshipService sponsorshipService;

    //good
    @PostMapping("/processmanagement-microservice/processmanagement/sponsorship")
    public int addSponsorship(Sponsorship newSponsorship){
        return sponsorshipService.addSponsorship(newSponsorship);
    }

    //good
    @DeleteMapping("/processmanagement-microservice/processmanagement/sponsorship")
    public void deleteSponsorship(Sponsorship sponsorship){
        sponsorshipService.deleteByPK(sponsorship.getSponsorId(),sponsorship.getSubjectId());
    }

    //good
    @PutMapping("/processmanagement-microservice/processmanagement/sponsorship")
    public int changeSponsorship(Sponsorship newsponsorship){
        return sponsorshipService.changeSponsorship(newsponsorship);
    }

    //good
    @GetMapping("/processmanagement-microservice/processmanagement/sponsorship")
    public Sponsorship findSponsorshipByPK(String sponsorId, String subjectId){
        return sponsorshipService.findByPKNo(sponsorId,subjectId);
    }

    //good
    @GetMapping("/processmanagement-microservice/processmanagement/sponsorship/SP")
    public List<FeedBack> findAllSponsorshipBySPid(String sponsorId){
        return sponsorshipService.findAllSponsorshipBySPid(sponsorId);
    }

    //good
    @GetMapping("/processmanagement-microservice/processmanagement/sponsorship/SB")
    public List findAllSponsorshipBySBid(String subjectId){
        return sponsorshipService.findAllSponsorshipBySBid(subjectId);
    }
}

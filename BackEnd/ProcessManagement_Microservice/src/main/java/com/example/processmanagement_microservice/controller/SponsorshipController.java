package com.example.processmanagement_microservice.controller;

import com.example.processmanagement_microservice.dao.SponsorshipDao;
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

    @DeleteMapping("/processmanagement-microservice/processmanagement/sponsorship")
    public void deleteSponsorship(Sponsorship sponsorship){
        sponsorshipService.deleteByPK(sponsorship.getSponsorId(),sponsorship.getSubjectId());
    }

    @PutMapping("/processmanagement-microservice/processmanagement/sponsorship")
    public int changeSponsorship(Sponsorship newsponsorship){
        return sponsorshipService.changeSponsorship(newsponsorship);
    }

    @GetMapping("/processmanagement-microservice/processmanagement/sponsorship")
    public Optional<Sponsorship> findSponsorshipByPK(String sponsorid, String subjectid){
        return sponsorshipService.findByPK(sponsorid,subjectid);
    }

    @GetMapping("/processmanagement-microservice/processmanagement/sponsorship/SP")
    public List findAllSponsorshipBySPid(String sponsorid){
        return sponsorshipService.findAllSponsorshipBySPid(sponsorid);
    }

    @GetMapping("/processmanagement-microservice/processmanagement/sponsorship/SB")
    public List findAllSponsorshipBySBid(String subjectid){
        return sponsorshipService.findAllSponsorshipBySBid(subjectid);
    }


}

package com.example.processmanagement_microservice.service;

import com.example.processmanagement_microservice.model.Sponsorship;
import com.example.processmanagement_microservice.dao.SponsorshipDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author ：ZXM+LJC
 * @description：SponsorshipService
 * @date ：2022-12-9 15:47
 * @version : 1.0
 */

@Service
public class SponsorshipService {
    @Autowired
    private SponsorshipDao sponsorshipDao;

    public String setNextId(){
        List<Sponsorship> sponsorshipList=sponsorshipDao.findAll();
        int curMaxId=0;
        for(Sponsorship s:sponsorshipList){
            curMaxId=Math.max(curMaxId,Integer.parseInt(s.getId()));
        }
        int result=curMaxId+1;
        return result+"";
    }

    public int isExist(String sponsorid,String subjectid){
        Sponsorship checkSponsorship=new Sponsorship();
        checkSponsorship.setSponsorId(sponsorid)
                .setSubjectId(subjectid);
        Example<Sponsorship> sponsorshipExample=Example.of(checkSponsorship);
        long count = sponsorshipDao.count(sponsorshipExample);

        int result=0;
        if((int)count!=0){
            result=1;
        }
        return result;
    }

    public Sponsorship findByPKNo(String sponsorId,String subjectId){
        Sponsorship sponsorship=null;
        int exist = this.isExist(sponsorId,subjectId);
        if(exist!=0){
            Sponsorship checkSponsorship=new Sponsorship();
            checkSponsorship.setSponsorId(sponsorId)
                    .setSubjectId(subjectId);
            Example<Sponsorship> sponsorshipExample=Example.of(checkSponsorship);
            List<Sponsorship> sponsorshipList=sponsorshipDao.findAll(sponsorshipExample);
            sponsorship= sponsorshipList.get(0);
        }
        return sponsorship;
    }

    public void deleteByPK(String sponsorid,String subjectid){
        Sponsorship sponsorship=this.findByPKNo(sponsorid,subjectid);
        if(sponsorship!=null)
        sponsorshipDao.delete(sponsorship);
    }

    /*现在不需要判断是否存在，上层函数会自动计算续期*/
    public int addSponsorship(Sponsorship sponsorship){
        String sponsorid=sponsorship.getSponsorId();
        String subjectid=sponsorship.getSubjectId();
        int exist=this.isExist(sponsorid,subjectid);
        int success=0;
        if(exist==0){
            sponsorship.setId(this.setNextId())
                    .setStatus("green");
            sponsorshipDao.save(sponsorship);
            success=1;
            System.out.println("增加成功");
        }
        else{
            sponsorship.setId(findByPKNo(sponsorship.getSponsorId(),sponsorship.getSubjectId()).getId())
                    .setStatus("green");
            sponsorshipDao.save(sponsorship);
            success=1;
            System.out.println("赞助已存在，刷新赞助关系");
        }
        return success;
    }

    public int changeSponsorship(Sponsorship newSponsorship){
        int exist=this.isExist(newSponsorship.getSponsorId(),newSponsorship.getSubjectId());
        if(exist==1){
            Sponsorship targetSponsorship=this.findByPKNo(newSponsorship.getSponsorId(),newSponsorship.getSubjectId());
            newSponsorship.setId(targetSponsorship.getId());
            sponsorshipDao.save(newSponsorship);
        }
        return exist;
    }

    public List findAllSponsorshipBySPid(String sponsorId){
        Sponsorship checkSponsorship=new Sponsorship();
        checkSponsorship.setSponsorId(sponsorId);
        Example<Sponsorship> sponsorshipExample=Example.of(checkSponsorship);
        List<Sponsorship> sponsorshipList=sponsorshipDao.findAll(sponsorshipExample);
        return sponsorshipList;
    }

    public List findAllSponsorshipBySBid(String subjectId){
        Sponsorship checkSponsorship=new Sponsorship();
        checkSponsorship.setSubjectId(subjectId);
        Example<Sponsorship> sponsorshipExample=Example.of(checkSponsorship);
        List<Sponsorship> sponsorshipList=sponsorshipDao.findAll(sponsorshipExample);
        return sponsorshipList;
    }
}

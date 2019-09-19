package com.pixel.controller;

import com.pixel.dao.postgresql.implementations.ArtifactDAOI;
import com.pixel.dao.postgresql.implementations.SackDAOI;
import com.pixel.dao.postgresql.implementations.SackInventoryDAOI;
import com.pixel.model.Artifact;
import com.pixel.model.Sack;
import com.pixel.model.SackInventory;
import com.pixel.model.Student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OwnItemController {
    private SackDAOI sackDAOI;
    private SackInventoryDAOI sackInventoryDAOI;
    private ArtifactDAOI artifactDAOI;

    public OwnItemController(SackDAOI sackDAOI, SackInventoryDAOI sackInventoryDAOI, ArtifactDAOI artifactDAOI) {
        this.sackDAOI = sackDAOI;
        this.sackInventoryDAOI = sackInventoryDAOI;
        this.artifactDAOI = artifactDAOI;
    }


    public Sack studentGetSack(Student student) {
        Sack sack = new Sack();
        try {
            sack = sackDAOI.getListBy("user_id", student.getId()).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sack;
    }


    public List<SackInventory> getListStudentArtifactId(Student student) {
        List<SackInventory> sackInventoryList = new ArrayList<>();

        try {
            System.out.println(student.getId());
            sackInventoryList = sackInventoryDAOI.getListBy("user_id", student.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sackInventoryList;
    }

    public List<Artifact> getStudentOwnArtifact(Student student){
        List<Artifact> sackList = new ArrayList<>();
        for(SackInventory sackItem: getListStudentArtifactId(student)){
            try {
                sackList.add(artifactDAOI.getById(sackItem.getId()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return sackList;
    }

}


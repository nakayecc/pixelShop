package com.pixel.controller;

import com.pixel.dao.postgresql.implementations.ArtifactDAOI;
import com.pixel.dao.postgresql.implementations.SackInventoryDAOI;
import com.pixel.model.Artifact;
import com.pixel.model.SackInventory;
import com.pixel.model.Student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OwnItemController {

    private SackInventoryDAOI sackInventoryDAOI;
    private ArtifactDAOI artifactDAOI;

    public OwnItemController(SackInventoryDAOI sackInventoryDAOI, ArtifactDAOI artifactDAOI) {

        this.sackInventoryDAOI = sackInventoryDAOI;
        this.artifactDAOI = artifactDAOI;
    }
    public List<SackInventory> getAll() throws SQLException {
        return sackInventoryDAOI.getListFull();
    }


    public List<SackInventory> getListStudentArtifactId(Student student) {
        List<SackInventory> sackInventoryList = new ArrayList<>();
        try {
            sackInventoryList = sackInventoryDAOI.getListOfActiveBy("user_id", student.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sackInventoryList;
    }

    public List<Artifact> getStudentOwnArtifact(Student student) {
        List<Artifact> sackList = new ArrayList<>();
        for (SackInventory sackItem : getListStudentArtifactId(student)) {
            try {
                sackList.add(artifactDAOI.getById(sackItem.getArtifactId()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return sackList;
    }

    public List<SackInventory> getStudentSacks(Student student) {
        List<SackInventory> sackList = new ArrayList<>();
        for (SackInventory sackItem : getListStudentArtifactId(student)) {
            try {
                 sackList.add(sackInventoryDAOI.getById(sackItem.getId()))  ;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return sackList;
    }



}


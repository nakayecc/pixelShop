package com.pixel.controller;

import com.pixel.dao.postgresql.implementations.ArtifactDAOI;
import com.pixel.model.Artifact;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArtifactController {
    private ArtifactDAOI artifactDAOI;

    public ArtifactController(ArtifactDAOI artifactDAOI) {
        this.artifactDAOI = artifactDAOI;
    }

    public List<Artifact> getGroupArtifact() {
        List<Artifact> allArtifactList = new ArrayList<>();
        List<Artifact> groupArtifact = new ArrayList<>();
        try {
            allArtifactList = artifactDAOI.getListFull();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Artifact singleArtifact : allArtifactList) {
            if (singleArtifact.isGlobal()) {
                groupArtifact.add(singleArtifact);
            }
        }
        return groupArtifact;
    }

    public List<Artifact> getSoloArtifact() {
        List<Artifact> allArtifactList = new ArrayList<>();
        List<Artifact> SoloArtifact = new ArrayList<>();
        try {
            allArtifactList = artifactDAOI.getListFull();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Artifact singleArtifact : allArtifactList) {
            if (!singleArtifact.isGlobal()) {
                SoloArtifact.add(singleArtifact);
            }
        }
        return SoloArtifact;
    }

    public List<Artifact> getAllArtifact() {
        List<Artifact> artifactList = new ArrayList<>();
        try {
            artifactList = artifactDAOI.getListFull();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return artifactList;
    }

}

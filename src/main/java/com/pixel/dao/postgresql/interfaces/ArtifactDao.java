package com.pixel.dao.postgresql.interfaces;

import com.pixel.model.Artifact;

import java.sql.SQLException;
import java.util.List;

public interface ArtifactDao {

    Artifact getById(int id) throws SQLException;
    List<Artifact> getListFull() throws SQLException;
    List<Artifact> getListBy(String valueName, String value) throws SQLException;
    boolean updateArtifact(Artifact artifact) throws SQLException;
    boolean insertArtifact(Artifact artifact) throws SQLException;
    boolean deleteArtifact(Artifact artifact) throws SQLException;

}

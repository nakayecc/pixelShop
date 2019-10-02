package com.pixel.controller;

import com.pixel.dao.postgresql.implementations.LevelsDAOI;
import com.pixel.model.Level;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LvlController {
    private LevelsDAOI levelsDAOI;

    public LvlController(LevelsDAOI levelsDAOI) {
        this.levelsDAOI = levelsDAOI;
    }



    public Map<String,Integer> getAllLevel(){
        Map<String,Integer> levelMap = new HashMap<>();

        try {
            levelMap = levelsDAOI.getLevelMap();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return levelMap;
    }
}

package com.junks.brainup;

import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class StageManager {
    private static StageManager instance;
    private Map<String, Stage> stages;

    private StageManager() {
        stages = new HashMap<>();
    }

    public static StageManager getInstance() {
        if (instance == null) {
            synchronized (StageManager.class) {
                if (instance == null) {
                    instance = new StageManager();
                }
            }
        }
        return instance;
    }

    public void addStage(String name, Stage stage) {
        stages.put(name, stage);
    }

    public Stage getStage(String name) {
        return stages.get(name);
    }

    public void removeStage(String name) {
        stages.remove(name);
    }
}

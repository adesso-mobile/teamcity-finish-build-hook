package de.ams.itoperations;

import java.util.List;

public class ConfigYaml {
    private List<Script> scripts;

    public ConfigYaml(List<Script> scripts) {
        this.scripts = scripts;
    }

    public List<Script> getScripts() {
        return scripts;
    }

    public void setScripts(List<Script> scripts) {
        this.scripts = scripts;
    }
}

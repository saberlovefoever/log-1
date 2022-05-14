package org.whh.bz.advertise;

public class Template {

    private  int id;
    private  String name;
    private  String  script;

    public Template() {
    }

    public Template(int id, String name, String script) {
        this.id = id;
        this.name = name;
        this.script = script;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    @Override
    public String toString() {
        return "Template{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", script='" + script + '\'' +
                '}';
    }
}

package controller;

/**
 *
 * @author Cesar A. Villamizar C.
 */
public class Plan {
    private String code;
    private String name;

    public Plan(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString(){
        return "("+this.code+") "+this.name;
    }    
}

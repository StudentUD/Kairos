package controller;

/**
 *
 * @author Cesar A. Villamizar C.
 */
public class Plan {
    private String code;
    private String name;
    public static final String PRE = "PRE";
    public static final String POS = "POS";
    private String level;
    public static final Plan NULL_PLAN= new Plan("", "Ninguno","PRE");

    public Plan(String code, String name, String level) {
        this.code = code;
        this.name = name;
        setLevel(level);
    }

    public void setLevel(String lvl){
        if(!(lvl.equals(PRE)||lvl.equals(POS))){
            this.level=PRE;
        }
        this.level=lvl;
    }
    
    public String getLevel(){
        return this.level;
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
    public boolean equals(Object obj){
        if(!(obj instanceof Plan)){
            return false;
        }
        if(this.code.equals(((Plan)obj).getCode())){
            return true;
        }
        return false;
    }
    
    @Override
    public String toString(){
        return "("+this.code+") "+this.name;
    }    
}

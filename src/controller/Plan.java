package controller;

/**
 *
 * @author Cesar A. Villamizar C.
 */
public class Plan {
    private String code;
    private String name;
    public static final int PRE = 1;
    public static final int POS = 2;
    private int level;
    public static final Plan NULL_PLAN= new Plan("0000", "Ninguno",0);

    public Plan(String code, String name, int level) {
        this.code = code;
        this.name = name;
        setLevel(level);
    }

    public void setLevel(int lvl){
        if(!(lvl==PRE||lvl==POS)){
            this.level=PRE;
        }
        this.level=lvl;
    }
    
    public int getLevel(){
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

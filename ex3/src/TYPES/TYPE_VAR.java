package TYPES;

public class TYPE_VAR extends TYPE {
    public TYPE varType;

    public TYPE_VAR(String name, TYPE varType)
    {
        this.name=name;
        this.varType=varType;
    }

    public boolean isVar() { return true;}
}
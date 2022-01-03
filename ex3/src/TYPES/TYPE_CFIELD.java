package TYPES;
public class TYPE_CFIELD extends TYPE
{
    public TYPE type;

    
    public TYPE_CFIELD(String cfieldName, TYPE type)
    {
        this.name=cfieldName;
        this.type=type;


    }

    public boolean isCFIELD(){ return true;}
}

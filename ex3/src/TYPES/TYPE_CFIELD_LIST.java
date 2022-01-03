package TYPES;


public class TYPE_CFIELD_LIST extends TYPE{
    
    public TYPE_CFIELD head;
    public TYPE_CFIELD_LIST tail;

    public TYPE_CFIELD_LIST(TYPE_CFIELD head, TYPE_CFIELD_LIST tail) 
    {
        this.head = head;
        this.tail = tail;
    }

    public boolean isCFIELD_LIST() { return true;}

}

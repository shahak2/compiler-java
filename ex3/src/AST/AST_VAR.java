package AST;
import TYPES.*;
public abstract class AST_VAR extends AST_Node
{
    public String name;
    public int nameLine;
    public int nameColumn;

    public TYPE SemantMe() 
    {
        return null;
    }

    
}

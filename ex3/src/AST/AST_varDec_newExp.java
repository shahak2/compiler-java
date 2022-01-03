package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_varDec_newExp extends AST_varDec
{
    public String name;
    public AST_type type;
    public AST_newExp newExp;


    public int nameLine;
    public int nameColumn;
    public int typeLine;
    public int typeColumn;
    public int newExpLine;
    public int newExpColumn;

    public AST_varDec_newExp(AST_type type, int typeLine, int typeColumn, String name, int nameLine, int nameColumn, AST_newExp newExp, int newExpLine, int newExpColumn) 
    {
        
        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.format("====================== varDec -> type ID( %s ) ASSIGN newWxp; \n", name);

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.name = name;
        this.type = type;
        this.newExp = newExp;

        this.nameLine = nameLine;
        this.nameColumn = nameColumn;
        this.typeLine = typeLine;
        this.typeColumn = typeColumn;
        this.newExpLine = newExpLine;
        this.newExpColumn = newExpColumn;

    }

    /**************************************************/
    /* The printing message for a simple var AST node */

    /**************************************************/
    public void PrintMe() 
    {
        /**********************************/
        /* AST NODE TYPE = AST SIMPLE VAR */
        /**********************************/
        System.out.format("AST NODE varDec newExp(%s)\n", name);

        if (type != null) type.PrintMe();
        if (newExp != null) newExp.PrintMe();

        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("VAR\nDEC\nNEWEXP\n(%s)", name));

        if (type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, type.SerialNumber);
        if (newExp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, newExp.SerialNumber);
    }
    
    /*   EXAMPLE: A => B => C , where A => B means A is a subclass of B                        */
    /*   C x = new A is VALID ,   A y = new C is INVALID                                       */
    /*   compareClasses(t1, t2) returns true iff t1 and t2 are instances of same class OR      */
    /*                                              OR t2 is a subclass of t1                  */

    public boolean compareClasses(TYPE_CLASS t1, TYPE_CLASS t2)
	{
        /* start from t1 not t1.father */
		for ( TYPE_CLASS it = t2; it != null; it = it.father) 
		{
			if (it.name.equals(t1.name)) 
			{
				return true;
			}
		}
		return false;
	}
    
   
    public TYPE SemantMe()
    {
        TYPE t = null;
        TYPE_CLASS t_c = null;
        TYPE_CLASS exp_t_tc = null;
        

        t = SYMBOL_TABLE.getInstance().find(type.name);
        
        if (t == null)
        {
            System.out.format(">> ERROR [%d:%d] non existing type %s\n", typeLine, typeColumn, type.name);
            SYMBOL_TABLE.getInstance().printERROR(typeLine);
            System.exit(0);
        }
        
        if(t.isClass() == false && t.isArray() == false)
        {
        	System.out.format(">> ERROR [%d:%d] a non class/array-type %s cannot be assigned with 'new' keyword type\n", newExpLine , newExpColumn, t.name);
            SYMBOL_TABLE.getInstance().printERROR(newExpLine);
            System.exit(0);
        }

        if (SYMBOL_TABLE.getInstance().find_in_curr_scope(name) != null)
        {
            System.out.format(">> ERROR [%d:%d] variable %s already exists in scope\n", nameLine, nameColumn,name);
            SYMBOL_TABLE.getInstance().printERROR(nameLine);
            System.exit(0);
        }
        
        TYPE exp_t = newExp.SemantMe();
        
        if(exp_t == TYPE_NIL.getInstance())
        /* arrays and class may be assigned NIL*/
        {
        	SYMBOL_TABLE.getInstance().enter(name, t);
        	TYPE_VAR var= new TYPE_VAR(name,t);
            return var;
        }
        
        if( ! exp_t.name.equals(t.name) )
        {
        	if(t.isClass() == true) 
        	{
        		t_c = (TYPE_CLASS) t;
        		exp_t_tc = (TYPE_CLASS) exp_t;
                
        		if(compareClasses(t_c, exp_t_tc) == false)
        		{
        			System.out.format(">> ERROR [%d:%d] class %s is not an instance of %s\n", newExpLine ,newExpColumn, exp_t_tc.name, t_c.name);
                    SYMBOL_TABLE.getInstance().printERROR(newExpLine);
        			System.exit(0);
        		}
        	}
        	else /*t is an array*/
        	{
                TYPE_ARRAY a_t = (TYPE_ARRAY) t;
                if( a_t.arrayType.name.equals(exp_t.name) )
                {
                    SYMBOL_TABLE.getInstance().enter(name, a_t);
                    TYPE_VAR var= new TYPE_VAR(name,t);
                    return var;
                }
                else {
                    System.out.format(">> ERROR [%d:%d] cannot assign %s to array of type %s\n", newExpLine, newExpColumn, exp_t.name, t.name);
                    SYMBOL_TABLE.getInstance().printERROR(newExpLine);
                    System.exit(0);
                }
        	}
        }

        SYMBOL_TABLE.getInstance().enter(name,t);
        TYPE_VAR var= new TYPE_VAR(name,t);
        return var;
    }

}

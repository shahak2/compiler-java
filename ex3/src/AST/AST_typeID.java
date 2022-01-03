package AST;

public class AST_typeID extends AST_VAR
{
   
    public AST_type type;
    public String name;

    public int typeLine;
    public int typeColumn;

    public int nameLine;
    public int nameColumn;

    public AST_typeID(AST_type type, int typeLine, int typeColumn,  String name, int nameLine, int nameColumn)
    {
    
        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.format("====================== typeID -> type ID( %s )\n",name);

        this.type = type;
        this.name = name;

        this.typeLine = typeLine;
        this.typeColumn = typeColumn;

        this.nameLine = nameLine;
        this.nameColumn = nameColumn;
    }

  
    public void PrintMe()
    {
        System.out.format("AST NODE type ID\n");
        if (type != null) type.PrintMe();
        System.out.format(" ID FIELD( %s )\n",name);

        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("TYPE\nID\n(%s)",name));

        if (type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);
    }

   
}


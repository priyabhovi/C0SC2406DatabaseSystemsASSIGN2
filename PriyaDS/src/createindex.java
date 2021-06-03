
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

//This class is to have the nodes for the tree

class Tree implements Serializable 
{
	public Tree parent;
	public Tree rightpointer; 
	public Tree leftpointer; 
	public boolean isLeaf;
	public List<String> key; 
	public List<Tree> ptr;
	public List<Long> offsetvalue;
	public List<Integer> dataLength; 

	
	public Tree() 
	{
		this.dataLength = new ArrayList<Integer>();
		this.parent = null;
		this.rightpointer = null;
		this.leftpointer = null;
		this.isLeaf = false;
		this.key = new ArrayList<String>();
		this.ptr = new ArrayList<Tree>();
		this.offsetvalue = new ArrayList<Long>();

	}

}
public class createindex 
{

// size of field SDTName
static final int SDTName_SIZE = 24;
// Total size of a single record.
static final int RECORD_SIZE = 111;
//static Tree root;
static int Nodesize = 0; 
//initialize the size of each field in a record
private static final int STD_NAME_SIZE = 24;
private static final int ID_SIZE = 4;
private static final int DATE_SIZE = 8;
private static final int YEAR_SIZE = 4;
private static final int MONTH_SIZE = 9;
private static final int MDATE_SIZE = 4;
private static final int DAY_SIZE = 9;
private static final int TIME_SIZE = 4;
private static final int SENSORID_SIZE = 4;
private static final int SENSORNAME_SIZE = 38;
private static final int COUNTS_SIZE = 4;
private static final int TOTAL_SIZE =  STD_NAME_SIZE + 
ID_SIZE + 
DATE_SIZE + 
YEAR_SIZE + 
MONTH_SIZE + 
MDATE_SIZE + 
DAY_SIZE + 
TIME_SIZE + 
SENSORID_SIZE + 
SENSORNAME_SIZE + 
COUNTS_SIZE;

// Initialize tree for the first time
public void initialiseTree() 
{
	//root = new Tree();
}
//Method to populate the B+tree using SDT_Name as index 
public static void populateTree(int pageSize)  
{

	   //Initialize heapfile name
	   String datafile = "heap." + pageSize;
      int numBytesInSdtnameField = STD_NAME_SIZE;
      int sizeOfRecord = TOTAL_SIZE;
      int recPerPage = pageSize/sizeOfRecord;
      byte[] page = new byte[pageSize];
      FileInputStream inputStream = null;

      try 
      {
      	//create file stream to head heap file
          inputStream = new FileInputStream(datafile);
          int numBytesRead = 0;
          byte[] stdBytes = new byte[numBytesInSdtnameField];
			int position=0;
          //read until the end of file.
          while ((numBytesRead = inputStream.read(page)) != -1) 
          {
              for (int i = 0; i < recPerPage; i++) 
              {

                  //  SdtName field of the record is copied to build the string
                  System.arraycopy(page, (i*sizeOfRecord), stdBytes, 0, numBytesInSdtnameField);
                  if (stdBytes[0] == 0) 
                  {
                      break;
                  }
                  String sdtNameString = new String(stdBytes);
					insert(root,sdtNameString, position, sizeOfRecord);
					position = position + numBytesRead;								
              }
          }
          }
      catch (Exception e) 
      {
          System.err.println("There is an error " + e.getMessage());
      }		
}

//Method to insert record to tree
private static void insert(Tree node, String key,long offset, int recLen) throws IOException 
{

		//if ((node == null || node.key.isEmpty()) && node == root) 
		{
			node.key.add(key);
			node.offsetvalue.add((Long) offset);
			node.dataLength.add(recLen);
			node.isLeaf = true;
			//root = node;
			return;
		}
	//	else if (node != null || !node.key.isEmpty()) 
		{
			for (int x = 0; x < node.key.size(); x++) 
			{	
			if (key.compareTo(node.key.get(x)) < 0) 
			{
					if (!node.isLeaf && node.ptr.get(x) != null) 
					{
						//insert((Tree) node.ptr.get(x), key, offset, recLen);
						return;
					} 
					else if (node.isLeaf) 
					{
						node.key.add("");
						node.offsetvalue.add(0l);
						node.dataLength.add(0);
						for (int y = node.key.size() - 2; y >= x; y--) 
						{
							node.key.set(y + 1, node.key.get(y));
							node.offsetvalue.set(y + 1, node.offsetvalue.get(y));
							node.dataLength.set(y + 1, node.dataLength.get(y));
						}
						node.key.set(x, key);
						node.offsetvalue.set(x, offset);
						node.dataLength.set(x, recLen);
						if (node.key.size() == Nodesize) 
						{
							//split(node);
							return;
						} 
						else 
							return;
					}
				}
				else if (key.compareTo(node.key.get(x)) > 0) 
				{
					if (x < node.key.size() - 1) 
					{
						continue;
					}
					else if (x == node.key.size() - 1) 
					{
						if (!node.isLeaf && node.ptr.get(x + 1) != null) 
						{
							//insert((Tree) node.ptr.get(x + 1),key, offset, recLen);
							return;
						}

						else if (node.isLeaf) 
						{
							node.key.add("");
							node.offsetvalue.add(0l);
							node.dataLength.add(0);
							node.key.set(x + 1, key);
							node.offsetvalue.set(x + 1, offset);
							node.dataLength.set(x + 1, recLen);
						}
						
						if (node.key.size() == Nodesize) 
						{
						split(node);
							return;
						} 
						else
							return;
					}
				}
			}
		}
	}
//This method split a tree to satisfy B+tree implementation
	private static void split(Tree node) throws IOException 
	{
		Tree tempparent = new Tree(); 
		Tree lNode = new Tree();
		Tree rNode = new Tree();
		Tree parent;
		int newPosKey = 0;
		int split = 0;
		
		if (node.isLeaf) 
		{
			if (node.key.size() % 2 == 0)
				split = (node.key.size() / 2) - 1;
			else
				split = node.key.size() / 2;

			rNode.isLeaf = true;
			for (int i = split; i < node.key.size(); i++) 
			{
				rNode.key.add(node.key.get(i));
				rNode.offsetvalue.add(node.offsetvalue.get(i));
				rNode.dataLength.add(node.dataLength.get(i));
			}
public static void main(String[] args) 
{
  createindex objTree = new createindex();
  
   try 
   {
    String input = args[0];
    int pageSize = Integer.parseInt(input);
    
    long startTime = System.currentTimeMillis();
    
	   objTree.initialiseTree();
   // objTree.CreateTree(pageSize);
  
    long stopTime = System.currentTimeMillis();
    
    System.out.println(stopTime - startTime + " ms");
    System.out.println((stopTime - startTime) + " ms");
    System.out.println("Index created successfully");
	   
	} 
   catch (Exception e) 
   {   
      System.out.println("Please enter a valid pagesize");
 }
}
}
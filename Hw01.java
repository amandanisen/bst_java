
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

//this class will render all functions
public class Hw01 {
    static ArrayList<String> commandList = new ArrayList<>();
    static ArrayList<Integer> numberList = new ArrayList<>();

    static int leftCount = 0;
    static int rightCount = 0;
    static int leftDepth = 0;
    static int rightDepth = 0;


    //reads and parses file
    private static void readFile(String[] args) {
        File file = new File(args[0]);
        System.out.println(file.toString() + " contains:");

        String line;
        String[] strParsed;
        int numberInput = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            // read until it reaches null
            while ((line = br.readLine()) != null) {
                // parsing string by white space
                System.out.println(line);
                strParsed = line.split(" ");
                strParsed[0].replaceAll("\\s", "");
                //if length == 2, then it has a command and a number
                if(strParsed.length == 2){
                    if(strParsed[0].equals("i")){
                        strParsed[1].replaceAll("\\s", "");
                        addNumberToList("i", strParsed[1], numberInput);
                    }else if (strParsed[0].equals("d")){
                        strParsed[1].replaceAll("\\s", "");
                        addNumberToList("d", strParsed[1], numberInput);
                    }else if (strParsed[0].equals("s")){
                        strParsed[1].replaceAll("\\s", "");
                        addNumberToList("s", strParsed[1], numberInput);
                    }
                }else if(strParsed.length == 1){
                    commandMissingNumber(strParsed[0]);
                }
            } 
            br.close();

        } 
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    private static void addNumberToList(String command, String parsed, int numberInput){
        try{
            //check if parse string is a number
            //if it is, add the array list of numbers
            numberInput = Integer.parseInt(parsed);
            commandList.add(command);
            numberList.add(numberInput);
        }catch(NumberFormatException e){
            //else it is not a number, add command string
            commandList.add(command);
            numberList.add(null);
        }
    }

    private static void commandMissingNumber(String command){
        commandList.add(command);
        numberList.add(null);
    }


    //inserts key to bst
    private static Node insertNode(Node root, Integer key){
        //check if bst is empty first
        if(root == null){
            root = new Node(key);
            return root;
        }
        //if the root is bigger than the number
        else if(key < root.key){
            //insert to left
            root.left = insertNode(root.left, key);
        //if there's an existing node already, just put it in the right
        }else if (key >= root.key){
            root.right = insertNode(root.right, key);
        }
        return root;
    }

    //sees if the key in in the bst
    private static Node findKey(Node root, int value){
        if(root == null){
            return null;
        } else if(value == root.key){
            return root;
        } else if(value < root.key){
            return findKey(root.left, value);
        } else{
            return findKey(root.right, value);
        }
    }

    //deleting nodes
    private static Node deleteNode(Node root, int value){
        if(root == null){
            return root;
        } 

        if(value > root.key){
            root.right = deleteNode(root.right, value);
        } else if(value < root.key){
            root.left = deleteNode(root.left, value);
        } else{
            if(root.left == null){
                return root.right;                
            }
            else if(root.right == null){
                return root.left;               
            }

            //get smallest value by doing inorder successor
            Node currentNode = root.right;
            int minKey = currentNode.key;
            while (currentNode.left != null) 
            {
                minKey = currentNode.left.key;
                root = currentNode.left;
            }
            root.key = minKey;
            root.right = deleteNode(root.right, root.key);
        }
        return root;
    }

    //count the children
    private static void countChildren(Node root) 
    { 
        int count = count(root);
        leftCount = count(root.left);        
        rightCount = count(root.right);
        
    } 

    //count the children
    private static int count(Node root){
        
        if(root==null){
            return 0;
        }
		return 1 + count(root.left) + count(root.right);
    }
    
    //get the height of left and right
    private static void getDepth(Node root){
        leftDepth = getSubHeight(root.left);
        rightDepth = getSubHeight(root.right);
    }

   
    private static int getSubHeight(Node root) {
        //if tree is null, return 0
        if (root == null) {
            return 0;
        }
        //else get the max height
        return 1 + Math.max(getSubHeight(root.left), getSubHeight(root.right));
    
    }

     //print in order function
    private static void printInorder(Node node) 
    { 
        if (node == null) 
            return; 
         
        //go to left child
        printInorder(node.left); 
         
        System.out.print(" " + node.key);
        //go to right child
        printInorder(node.right); 
    } 

    private static void complexityIndicator(){
        System.err.println("am758018; 3.7; 20");
    }

    
    public static void main(String[] args) {
        //instantiate binary search tree
        BST bst = new BST();
        BST foundBST = new BST();
        int loopCount = 0;
        Integer numberInput = 0;

        if(args.length != 1){
            System.err.println("Please enter a text file.");
            System.exit(-1);
        }
        complexityIndicator();
        readFile(args);
      
        for(String command : commandList){
            numberInput = numberList.get(loopCount);
            if(command.equalsIgnoreCase("i")){
                if(numberInput != null){
                    bst.root = insertNode(bst.root, numberInput);
                    loopCount++;
                }else{
                    System.out.println(command + " command:missing numeric parameter");
                    loopCount++;
                }
            }else if(command.equalsIgnoreCase("s")){
                if(numberInput != null){
                    foundBST.root = findKey(bst.root, numberInput);
                    if(foundBST.root != null){
                        System.out.println(numberInput + ": found");
                    }else{
                        System.out.println(command + " " + numberInput +": integer " + numberInput + " NOT found");
                    }
                    loopCount++;
                }else{
                    System.out.println(command + " command:missing numeric parameter");
                    loopCount++;
                }
            }else if(command.equalsIgnoreCase("d")){
                if(numberInput != null){
                    foundBST.root = findKey(bst.root, numberInput);
                    if(foundBST.root == null){
                        System.out.println(command + " " + numberInput + ": integer " + numberInput + " NOT found - NOT deleted");
                    }else{
                        bst.root = deleteNode(bst.root, numberInput);
                    }
                    loopCount++;
                }else{
                    System.out.println(command + " command:missing numeric parameter");
                    loopCount++;
                }
            }else if(command.equalsIgnoreCase("p")){
                printInorder(bst.root);
                System.out.println(" ");
                loopCount++;
            }else if(command.equalsIgnoreCase("q")){
                countChildren(bst.root);
                getDepth(bst.root);
                System.out.println("left children:          " + leftCount);
                System.out.println("left depth:             " + leftDepth);
                System.out.println("right children:         " + rightCount);
                System.out.println("right depth:            " + rightDepth);
                System.exit(0);
            }
        }
    }
}

class Node{
    //set up left and right key
    int key;
    Node left, right;
    //constructor
    public Node(int key){
        this.key = key;
        this.left = null;
        this.right = null;
    }
}

class BST{

    Node root;
    //create constructor for BST
    public BST(){
        //setting root to null when user creates a bst
        this.root = null;
    }
}

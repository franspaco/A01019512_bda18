import java.util.Vector;

public class Node {

    static int nextId = 100;
    int id = 0;

    void makeId(){
        id = nextId++;
    }

    final int MAX_DEGREE = 3;
    boolean leaf = true;
    Vector<Integer> data;
    Vector<Node> pointer;
    Node nx = null; // Routing for linked list

    Integer routingData;

    private String spacer(int lvl){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lvl; i++) {
            sb.append("|   ");
        }
        return sb.toString();
    }

    /**
     * Print the node with a level
     * @param lvl used for printing the node with indenting according to it's level inside the tree
     */
    public void print(int lvl){
        String spaces = spacer(lvl);
        System.out.print("\n" + spaces + "Node:("+lvl+") {"+id+"} " + ((leaf)? "L":"NL") + "\n");
        System.out.print(spaces + " ");
        for( Object val: data){
            System.out.print(val + ",");
        }
        System.out.println();
        for (Node n: pointer) {
            System.out.println(spaces + " PT->{" + n.id + "}");
        }
        if(nx != null){
            System.out.println(spaces + " LL->{" + nx.id + "}");
        }
    }

    /**
     * Print the node
     */
    public void print(){
        print(0);
    }

    /**
     * Node constructor used to create instances with just one data value
     * @param init
     */
    Node(Integer init){
        data = new Vector<>();
        pointer = new Vector<>();
        data.add(init);
        makeId();
    }

    /**
     * Node constructor used to create instances with 2 data elements
     * @param init First data value
     * @param init2 Second data value
     */
    Node(Integer init, Integer init2){
        data = new Vector<>();
        pointer = new Vector<>();
        data.add(init);
        data.add(init2);
        makeId();
    }

    /**
     * Node constructor used to create leaves with 2 data elements and part of the linked list
     * @param init First data value
     * @param init2 Second data value
     * @param next Node to point to in the Linked List
     */
    Node(Integer init, Integer init2, Node next){
        data = new Vector<>();
        pointer = new Vector<>();
        data.add(init);
        data.add(init2);
        nx = next;
        makeId();
    }

    /**
     * Node constructor used to create leaves with just one data value and part of the linked list
     * @param init First data value
     * @param next Node to point to in the Linked List
     */
    Node(Integer init, Node next){
        data = new Vector<>();
        pointer = new Vector<>();
        data.add(init);
        nx = next;
        makeId();
    }

    /**
     * Insert value to the right position in the data list
     * @param val Value to insert into the node
     * @return true if the insertion caused an overflow and needs to be taken care of, false if it didn't
     */
    boolean insert(Integer val){
        if(data.size() < 3){
            boolean inserted = false;
            int i = 0;
            while(!inserted && i < data.size()){
                if(loet(val, data.elementAt(i))){
                    data.add(i, val);
                    inserted = true;
                }
                i++;
            }
            if(!inserted){
                data.add(val);
            }
            //System.out.println("After insert:");
            //print();
            return (data.size() >= MAX_DEGREE);
        }
        else {
            // BAD ERROR?
        }
        return false;
    }


    /**
     * Split node into the node and a new node to the right.
     * @return Returns new node so it can be added to the parent's routing list
     */
    Node splitRight(){
        Integer mid_data = getDataMiddle();
        Integer left_data = getDataFirst();
        Integer right_data = getDataLast();

        if(leaf){
            data.clear();
            data.add(left_data);
            Node right = new Node(mid_data, right_data, nx);
            // This keeps the Linked List
            nx = right;
            return right;
        }
        else{
            data.clear();
            data.add(left_data);

            Node right = new Node(right_data, nx);
            right.leaf = false;
            right.routingData = mid_data;

            // Current should have 4 pointers, remove the last 2 in order and insert them
            // into right in the correct order.
            right.pointer.add(pointer.remove(2));
            right.pointer.add(pointer.remove(2));
            return right;
        }
    }

    /**
     * Splits this node into 3 nodes, this node turns into the parent of 2 new nodes, hence the name "split down"
     */
    void splitDown(){
        Integer base_data = getDataMiddle();
        Integer left_data = getDataFirst();
        Integer right_data = getDataLast();
        if (leaf){
            leaf = false;

            Node right = new Node(base_data, right_data);
            Node left = new Node(left_data, right);

            data.clear();
            data.add(base_data);
            pointer.clear();
            pointer.add(left);
            pointer.add(right);

        }
        else{
            Node left = new Node(left_data);
            Node right = new Node(right_data);

            left.leaf = false;
            right.leaf = false;

            left.pointer.add(pointer.remove(0));
            left.pointer.add(pointer.remove(0));
            right.pointer.add(pointer.remove(0));
            right.pointer.add(pointer.remove(0));

            data.clear();
            data.add(base_data);

            pointer.clear();
            pointer.add(left);
            pointer.add(right);
        }
    }

    /**
     * Insert route to node in routing list according to the node's first value
     * @param node Node to insert reference to into routing list
     */
    void insertRoute(Node node){
        if(!leaf){
            boolean inserted = false;
            int i = 0;
            while(!inserted && i < data.size()){
                if(lt(node.getDataFirst(), data.elementAt(i))){
                    pointer.add(i, node);
                    inserted = true;
                    break;
                }else{
                    i++;
                }
            }

            if(!inserted){
                pointer.add(node);
            }
        }
    }

    /**
     * Returns a Node from this node's routing table according to value
     * @param val The value to find a route for
     * @return The node representing the next step
     */
    Node route(Integer val){
        if(!leaf){
            int i = 0;
            while( i < data.size()){
                if(lt(val, data.elementAt(i))){
                    break;
                }else{
                    i++;
                }
            }
            return pointer.elementAt(i);
        }
        else{
            return null;
        }
    }

    /**
     * Less than
     * @param A
     * @param B
     * @return Boolean: true if A < B, false otherwise
     */
    private boolean lt(Integer A, Integer B){
        return A.compareTo(B) < 0;
    }

    /**
     * Less or equal to
     * @param A
     * @param B
     * @return Boolean: true if A <= B, false otherwise
     */
    private boolean loet(Integer A, Integer B){
        return A.compareTo(B) <= 0;
    }

    /**
     * Returns the first element
     * @return
     */
    Integer getDataFirst(){
        return data.firstElement();
    }

    /**
     * Returns the last element
     * @return
     */
    Integer getDataLast(){
        return data.lastElement();
    }

    /**
     * Returns the middle element
     * @return
     */
    Integer getDataMiddle(){
        return data.elementAt(1);
    }
}

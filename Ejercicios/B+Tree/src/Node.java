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

    public void print(){
        System.out.print("\nNode: {"+id+"} " + ((leaf)? "L":"NL") + "\n");
        for( Object val: data){
            System.out.print(val + ",");
        }
        System.out.println();
        for (Node n: pointer) {
            System.out.println("PT->{" + n.id + "}");
        }
        if(nx != null){
            System.out.println("LL->{" + nx.id + "}");
        }
    }

    public Node(Integer init){
        data = new Vector<>();
        pointer = new Vector<>();
        data.add(init);
        makeId();
    }

    public Node(Integer init, Node next){
        data = new Vector<>();
        pointer = new Vector<>();
        data.add(init);
        nx = next;
        makeId();
    }

    public Node(Integer init, Integer init2){
        data = new Vector<>();
        pointer = new Vector<>();
        data.add(init);
        data.add(init2);
        makeId();
    }

    public Node(Integer init, Integer init2, Node next){
        data = new Vector<>();
        pointer = new Vector<>();
        data.add(init);
        data.add(init2);
        nx = next;
        makeId();
    }

    public boolean insert(Integer val){
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

    public Node splitUp(){
        Integer mid_data = getDataMiddle();
        Integer left_data = getDataFirst();
        Integer right_data = getDataLast();

        if(leaf){
            data.clear();
            data.add(left_data);
            Node right = new Node(mid_data, right_data, nx);
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

    public void splitDown(){
        //System.out.println("PRE: ");
        //print();
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
            //System.out.print("Current is: ");
            //print();
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
        //System.out.println("POS:");
        //print();
    }

    public void insertRoute(Node node){
        if(!leaf){
            boolean inserted = false;
            int i = 0;
            while(!inserted && i < data.size()){
                //System.out.println("Comparing " + node.getDataFirst() + " vs. " + data.elementAt(i));
                if(lt(node.getDataFirst(), data.elementAt(i))){
                    //System.out.println("LT!");
                    //System.out.println("Adding route to {"+node.id+"} in place " + i);
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

    public Node route(Integer val){
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

    Integer getDataFirst(){
        return data.firstElement();
    }

    Integer getDataLast(){
        return data.lastElement();
    }

    Integer getDataMiddle(){
        return data.elementAt(1);
    }
}

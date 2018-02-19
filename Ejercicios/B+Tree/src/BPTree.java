
public class BPTree {
    Node tree = null;

    public void insert(Integer val){
        System.out.println("INSERTING: " + val);
        if(tree == null) {
            tree = new Node(val);
        }
        else{
            sink(null, tree, val);
        }
    }

    private Node sink(Node parent, Node current, Integer val){
        if(current.leaf){
            if(current.insert(val)){
                System.out.println("Overfill");
                if(parent == null){
                    // Node is the root, so create children
                    System.out.println("Split Down");
                    current.splitDown();
                    return null;
                }
                else{
                    // Current has a parent, move the problem up
                    System.out.println(" SPLIT UP!!! ");
                    Integer mid_data = current.getDataMiddle();
                    Integer left_data = current.getDataFirst();
                    Integer right_data = current.getDataLast();
                    current.data.clear();
                    current.data.add(left_data);
                    Node right = new Node(mid_data, right_data, current.nx);
                    System.out.println("New Node:");
                    right.print();
                    current.nx = right;

                    return right;

                    /*
                    boolean overflow = parent.insert(mid_data);
                    parent.insertRoute(right);
                    System.out.println("Parent:");
                    parent.print();

                    if(overflow) {
                        parent.splitDown();
                    }
                    */
                }
            }
        }else {
            System.out.println("Sinking one more level to {" + current.route(val).id + "}");
            Node res = sink(current, current.route(val), val);
            if (res != null){
                boolean overflow = current.insert(res.getDataFirst());
                current.insertRoute(res);
                if(overflow){
                    if(parent == null){
                        current.splitDown();
                    }
                    else{
                        // Split up?
                    }
                }
            }
        }
        return null;
    }

    public void printTree(){
        System.out.println("\n\nTREE:");
        printRec(tree, 0);
        System.out.println();
    }

    private void printRec(Node node, int lvl){
        if(node != null){
            node.print(lvl);
            for (Node n: node.pointer) {
                printRec(n, lvl + 1);
            }
        }
    }
}

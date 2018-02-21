
public class BPTree {
    Node tree = null;

    private boolean DEBUG = false;

    public BPTree(){

    }

    public BPTree(Boolean debug){
        DEBUG = debug;
    }

    private void log(String s){
        if (DEBUG)
            System.out.println(s);
    }

    public void insert(Integer val){
        log("INSERTING: " + val);
        if(tree == null) {
            tree = new Node(val);
        }
        else{
            sink(null, tree, val);
        }
    }

    private Node sink(Node parent, Node current, Integer val){
        if(current.leaf){
            // CURRENT IS LEAF NODE
            if(current.insert(val)){
                log("Overfill");
                if(parent == null){
                    // Node is the root, so create children
                    log("Split Down");
                    current.splitDown();
                    return null;
                }
                else{
                    // Current has a parent, move the problem up
                    log(" SPLIT UP!!! ");
                    /*
                    Integer mid_data = current.getDataMiddle();
                    Integer left_data = current.getDataFirst();
                    Integer right_data = current.getDataLast();
                    current.data.clear();
                    current.data.add(left_data);
                    Node right = new Node(mid_data, right_data, current.nx);
                    log("New Node:");
                    if (DEBUG)
                        right.print();
                    current.nx = right;

                    return right;
                    */
                    return current.splitUp();
                }
            }
        }else {
            // CURRENT IS ROUTING NODE
            log("Sinking one more level to {" + current.route(val).id + "}");
            Node res = sink(current, current.route(val), val);
            if (res != null){
                boolean overflow;
                if(res.leaf) {
                    overflow = current.insert(res.getDataFirst());
                }
                else{
                    overflow = current.insert(res.routingData);
                }
                current.insertRoute(res);
                if(overflow){
                    if(parent == null){
                        current.splitDown();
                    }
                    else{
                        return current.splitUp();
                        // Split and carry up
                        /*
                        Integer mid_data = current.getDataMiddle();
                        Integer left_data = current.getDataFirst();
                        Integer right_data = current.getDataLast();
                        current.data.clear();
                        current.data.add(left_data);

                        Node right = new Node(right_data, current.nx);
                        right.leaf = false;
                        right.routingData = mid_data;

                        // Current should have 4 pointers, remove the last 2 in order and insert them
                        // into right in the correct order.
                        right.pointer.add(current.pointer.remove(2));
                        right.pointer.add(current.pointer.remove(2));
                        return right;
                        */

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

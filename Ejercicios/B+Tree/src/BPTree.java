
public class BPTree {
    private Node tree = null;

    public BPTree(){}

    public void insert(Integer val){
        if(tree == null) {
            // No root exists
            tree = new Node(val);
        }
        else{
            // Sink the value into the tree
            sink(null, tree, val);
        }
    }

    private Node sink(Node parent, Node current, Integer val){
        if(current.leaf){
            // CURRENT IS LEAF NODE
            if(current.insert(val)){
                // Node had an overflow after the insert
                if(parent == null){
                    // Node is the root, so create children
                    current.splitDown();
                    // Nothing to return
                    return null;
                }
                else{
                    // Current has a parent, move the problem up
                    return current.splitRight();
                }
            }
        }else {
            // CURRENT IS ROUTING NODE
            // Do a recursion to go lower until a leaf is found
            Node res = sink(current, current.route(val), val);

            // If recursion returns a node it must be added to this node's routing table
            if (res != null){
                // A new node was created by a child
                boolean overflow;
                if(res.leaf) {
                    // The new node is a leaf, add it's first value to this node's data
                    overflow = current.insert(res.getDataFirst());
                }
                else{
                    // The new node is a routing node, add it's routing data to this node's data
                    overflow = current.insert(res.routingData);
                }
                // Insert the route to the new node
                current.insertRoute(res);

                // If the current node was overflowed after inserting
                if(overflow){
                    if(parent == null){
                        // If node has no parent split node down
                        current.splitDown();
                        // Nothing to return
                        return null;
                    }
                    else{
                        // Split and carry up
                        return current.splitRight();
                    }
                }
            }
        }
        // Nothing to return: no new node was created
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
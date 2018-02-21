public class Main {

    public static void main(String[] args) {
        BPTree tree = new BPTree();

        insert(tree, 18);
        insert(tree, 22);
        insert(tree, 11);
        insert(tree, 15);
        insert(tree, 16);
        insert(tree, 17);
        insert(tree, 31);
        insert(tree, 35);
        insert(tree, 37);
        insert(tree, 10);
        insert(tree, 9);
        insert(tree, 8);
        insert(tree, 7);
        insert(tree, 40);


    }

    private static void insert(BPTree tree, Integer val){
        System.out.println(" Inserting: " + val);
        tree.insert(val);
        tree.printTree();
    }
}

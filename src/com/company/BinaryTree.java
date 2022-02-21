package com.company;

import java.util.*;


public class BinaryTree {

    private static int IDnum = 8;

    protected char Level;

    protected int Number;

    protected int[] ID;

    protected int Value;

    protected BinaryTree FirstRef;

    protected BinaryTree SecondRef;

    protected void getID() {
        ID = new int[IDnum];
        for (int i = 0; i < IDnum; i++)
            ID[i] = (int)(Math.random()*10);
    }

    protected void getValue() {
        Value = (int)(Math.random() * 30);
    }

    protected void showID() {
        for (int i = 0; i < IDnum; i++)
            System.out.print("|"+ID[i]);

        System.out.print("|\n");
    }

    protected void show() {
        System.out.println("Level of node: \t" + Level);
        System.out.println("Number of node: \t" + Number);
        System.out.print("ID of node: \t");
        showID();
        System.out.println("Value of node: \t" + Value);
    }

    public static void showTree(BinaryTree root) {
        Queue<BinaryTree> queue = new LinkedList<BinaryTree>();
        queue.add(root);

        while (!queue.isEmpty()) {
            BinaryTree node = queue.poll();

            System.out.printf("%c%d", node.Level, node.Number);

            if (node.FirstRef != null && node.SecondRef != null)
                System.out.printf(" (%c%d, %c%d)",
                        node.FirstRef.Level, node.FirstRef.Number,
                        node.SecondRef.Level, node.SecondRef.Number);

            else if (node.FirstRef != null && node.SecondRef == null)
                System.out.printf(" (%c%d)", node.FirstRef.Level, node.FirstRef.Number);

            else if (node.FirstRef == null && node.SecondRef != null)
                System.out.printf(" (%c%d)", node.SecondRef.Level, node.SecondRef.Number);

            System.out.print(", value: " + node.Value + "; ID: ");
            node.showID();

            if ( node.FirstRef != null)
                queue.add(node.FirstRef);

            if (node.SecondRef != null)
                queue.add(node.SecondRef);
        }
        System.out.print("\n");
    }

    public static boolean checkCompleteness(BinaryTree root) {
        Stack<BinaryTree> stack = new Stack<>();
        stack.push(root);

        BinaryTree lastNode = BinaryTree.lastNode(root);

        while (!stack.isEmpty()) {
            BinaryTree node = stack.pop();

            if (node.numberOfDescendants() != 2 && !node.isLeaf() && node != lastNode)
                return false;

            else if (node.numberOfDescendants() != 2 && !node.isLeaf() && node == lastNode) {
                if (node.numberOfDescendants() == 1) return true;
                else return false;
            }

            if (node.FirstRef != null)
                stack.push(node.FirstRef);

            if (node.SecondRef != null)
                stack.push(node.SecondRef);
        }

        return true;
    }

    public static boolean isExistNode(BinaryTree root, char level, int number) {
        Stack<BinaryTree> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            BinaryTree node = stack.pop();

            if ( node.Level == level && node.Number == number )
                return true;

            if ( node.FirstRef != null)
                stack.push(node.FirstRef);

            if (node.SecondRef != null)
                stack.push(node.SecondRef);
        }

        return false;
    }

    public static BinaryTree getNode(BinaryTree root, char level, int number ) {
        Stack<BinaryTree> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            BinaryTree node = stack.pop();

            if ( node.Level == level && node.Number == number )
                return node;

            if ( node.FirstRef != null)
                stack.push(node.FirstRef);

            if (node.SecondRef != null)
                stack.push(node.SecondRef);
        }

        return null;
    }

    public static BinaryTree getParent(BinaryTree root, BinaryTree node) {
        char levelOfParent = (char) ( (int) (node.Level - 1) );
        int numberOfParent = (int) Math.ceil(node.Number / 2.0);

        BinaryTree parent = getNode(root, levelOfParent, numberOfParent);
        return parent;
    }

    public static boolean isItPossibleToAdd(BinaryTree root, char level, int number) {
        if ( isExistNode(root, level, number) ) return false;

        char levelOfParent = (char) ( (int) (level - 1) );
        int numberOfParent = (int) Math.ceil(number / 2.0);

        if ( isExistNode(root, levelOfParent, numberOfParent) ) return true;
        else return false;
    }

    public static boolean addNode(BinaryTree root, char level, int number, int value) {

        if ( isItPossibleToAdd(root, level, number) ) {
            BinaryTree newNode = new BinaryTree(level, number, value);

            char levelOfParent = (char) ( (int) (level - 1) );
            int numberOfParent = (int) Math.ceil(number / 2.0);

            BinaryTree parent = getNode(root, levelOfParent, numberOfParent);

            if ( parent.FirstRef == null)
                parent.FirstRef = newNode;

            else if (parent.SecondRef == null)
                parent.SecondRef = newNode;

            return true;
        }

        return false;
    }

    public static boolean addNode(BinaryTree root, int value) {
        BinaryTree parent = findNodeToWhichCanBeAttachedAnotherNode(root);
        BinaryTree newNode;

        if ( (parent.numberOfDescendants() == 0 ) ||
                (parent.numberOfDescendants() == 1 && parent.FirstRef == null) ) {
            newNode = new BinaryTree((char) ( (int) parent.Level + 1 ), 2 * parent.Number - 1, value);
            parent.FirstRef = newNode;
            return true;
        }

        else if (parent.numberOfDescendants() == 1 && parent.SecondRef == null) {
            newNode = new BinaryTree((char) ( (int) parent.Level + 1 ), 2 * parent.Number, value);
            parent.SecondRef = newNode;
            return true;
        }

        return false;
    }

    public static boolean addNode(BinaryTree root, int value, int[] ID) {
        BinaryTree parent = findNodeToWhichCanBeAttachedAnotherNode(root);
        BinaryTree newNode;

        if ( (parent.numberOfDescendants() == 0 ) ||
                (parent.numberOfDescendants() == 1 && parent.FirstRef == null) ) {
            newNode = new BinaryTree((char) ( (int) parent.Level + 1 ), 2 * parent.Number - 1, value, ID);
            parent.FirstRef = newNode;
            return true;
        }

        else if (parent.numberOfDescendants() == 1 && parent.SecondRef == null) {
            newNode = new BinaryTree((char) ( (int) parent.Level + 1 ), 2 * parent.Number, value, ID);
            parent.SecondRef = newNode;
            return true;
        }

        return false;
    }

    public static boolean deleteNode(BinaryTree root, char level, int number) {

        if ( isExistNode (root, level, number) ) {

            BinaryTree nodeToRemove = getNode(root, level, number);

            if ( getNode(root, level,number).numberOfDescendants() == 0) {
                BinaryTree parent = getParent(root, nodeToRemove);

                if (parent.FirstRef == nodeToRemove) parent.FirstRef = null;
                else if (parent.SecondRef == nodeToRemove) parent.SecondRef = null;

                return true;
            }

            else if ( getNode(root, level,number).numberOfDescendants() == 1 ) {

                BinaryTree parent = getParent(root, nodeToRemove);

                BinaryTree child;
                if (nodeToRemove.FirstRef != null)
                    child = nodeToRemove.FirstRef;
                else child = nodeToRemove.SecondRef;

                if (parent.FirstRef == nodeToRemove) parent.FirstRef = child;
                else if (parent.SecondRef == nodeToRemove) parent.SecondRef = child;

                restoreHierarchyOfLevels(root);

                return true;
            }

            else if ( getNode(root, level,number).numberOfDescendants() == 2 ) {
                BinaryTree leaf = findClosestLeaf(root, nodeToRemove);

                nodeToRemove.Value = leaf.Value;
                nodeToRemove.ID = leaf.ID;
                deleteNode(root, leaf.Level, leaf.Number);

                return true;
            }
        }

        return false;
    }

    public static void restoreHierarchyOfLevels(BinaryTree root) {
        Stack<BinaryTree> stack = new Stack<>();
        stack.push(root);

        while ( !stack.isEmpty() ) {
            BinaryTree currentNode = stack.pop();

            if ( currentNode.FirstRef != null ) {

                if (currentNode.Level != (char) ( (int)currentNode.FirstRef.Level-1) )
                    currentNode.FirstRef.Level = (char) ( (int)currentNode.Level+1 );

                if (currentNode.Number != (int) Math.ceil (currentNode.FirstRef.Number / 2) )
                    currentNode.FirstRef.Number = currentNode.Number * 2 - 1;

                stack.push(currentNode.FirstRef);
            }

            if ( currentNode.SecondRef != null ) {

                if (currentNode.Level != (char) ( (int)currentNode.SecondRef.Level-1) )
                    currentNode.SecondRef.Level = (char) ( (int)currentNode.Level+1 );

                if (currentNode.Number != currentNode.SecondRef.Number / 2);
                currentNode.SecondRef.Number = currentNode.Number * 2;

                stack.push(currentNode.SecondRef);
            }
        }
    }

    public static HashMap<int[], Integer> getDataAboutAllNodes(BinaryTree root) {
        HashMap<int[], Integer> map = new HashMap<int[], Integer>();
        Queue <BinaryTree> queue = new LinkedList<BinaryTree>();
        queue.add(root);

        while (!queue.isEmpty()) {
            BinaryTree currentNode = queue.poll();

            map.put(currentNode.ID, currentNode.Value);

            if ( currentNode.FirstRef != null)
                queue.add(currentNode.FirstRef);

            if (currentNode.SecondRef != null)
                queue.add(currentNode.SecondRef);
        }

        return map;
    }

    public static BinaryTree findClosestLeaf(BinaryTree root, BinaryTree node) {
        Stack<BinaryTree> stack = new Stack<>();
        stack.push(node);

        while (!stack.isEmpty()) {
            BinaryTree currentNode = stack.pop();

            if ( currentNode.numberOfDescendants() == 0 )
                return currentNode;

            if ( currentNode.FirstRef != null)
                stack.push(node.FirstRef);

            if ( currentNode.SecondRef != null)
                stack.push(node.SecondRef);
        }
        return null;
    }

    public static BinaryTree findNodeToWhichCanBeAttachedAnotherNode(BinaryTree root) {
        Queue <BinaryTree> queue = new LinkedList<BinaryTree>();
        queue.add(root);

        while (!queue.isEmpty()) {
            BinaryTree currentNode = queue.poll();

            if ( currentNode.numberOfDescendants() == 1 || currentNode.numberOfDescendants() == 0 )
                return currentNode;

            if ( currentNode.FirstRef != null)
                queue.add(currentNode.FirstRef);

            if (currentNode.SecondRef != null)
                queue.add(currentNode.SecondRef);
        }

        return null;
    }

    public static BinaryTree combineTrees(BinaryTree A, BinaryTree B) {
        HashMap<int[], Integer> data;
        data = BinaryTree.getDataAboutAllNodes(B);

        Set<int[]> ID =  data.keySet();
        Collection <Integer> values = data.values();

        Iterator<int[]> ID_iterator = ID.iterator();
        Iterator<Integer> values_iterator = values.iterator();

        for (int i = 0; i < data.size(); i++)
            BinaryTree.addNode(A, values_iterator.next(), ID_iterator.next() );

        return A;
    }

    int numberOfDescendants() {
        int counter = 0;
        if (this.FirstRef != null) counter++;
        if (this.SecondRef != null) counter++;
        return counter;
    }

    boolean isLeaf() {
        if (this.numberOfDescendants() == 0) return true;
        else return false;
    }


    static public BinaryTree lastNode(BinaryTree root) {
        Queue <BinaryTree> queue = new LinkedList<BinaryTree>();
        queue.add(root);

        BinaryTree currentNode = null;
        while (!queue.isEmpty()) {
            currentNode = queue.poll();

            if ( currentNode.FirstRef != null)
                queue.add(currentNode.FirstRef);

            if (currentNode.SecondRef != null)
                queue.add(currentNode.SecondRef);
        }

        return currentNode;
    }

    BinaryTree(int k, char L, int n) {
        Level = L;
        Number = n;
        getID();
        getValue();
        if (k==1) {
            FirstRef = null;
            SecondRef = null;
        }
        else {
            FirstRef = new BinaryTree(k-1, (char) ( (int) L + 1 ), 2 * n - 1);
            SecondRef = new BinaryTree(k-1, (char) ( (int) L + 1), 2 * n);
        }
    }

    BinaryTree(char L, int n, int value, int[] id) {
        Level = L;
        Number = n;
        ID = id;
        Value = value;

    }

    BinaryTree (char L, int n, int value) {
        Level = L;
        Number = n;
        getID();
        Value = value;
    }

    BinaryTree() {}

    BinaryTree(int numberOfNodes) {
        if (numberOfNodes > 0) {
            Level = 'A';
            Number = 1;
            getID();
            getValue();

            if (numberOfNodes > 1) {
                int randomValue;
                for (int i = 1; i < numberOfNodes; i++) {
                    randomValue = (int)(Math.random() * 30);
                    addNode(this, randomValue);
                }
            }
        }
    }
}

package com.company;

class Main {
    public static void main(String[] args){
        BinaryTree tree=new BinaryTree(4,'B',2); // Создание дерева
        BinaryTree.showTree(tree);

        BinaryHeap heap = new BinaryHeap(new int[]{1,2,3});
        heap.show();
    }

}

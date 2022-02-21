package com.company;

import java.util.LinkedList;
import java.util.Queue;

public class BinaryHeap extends BinaryTree {

    BinaryHeap(int numberOfNodes) {
        if (numberOfNodes > 0) {
            Level = 'A';
            Number = 1;
            getID();
            getValue();

            if (numberOfNodes > 1) {
                int randomValue;
                for (int i = 0; i < numberOfNodes; i++) {
                    randomValue = (int)(Math.random() * 30);
                    addNode(this, randomValue);
                }
            }
        }

        restoreProperty(this);
    }

    BinaryHeap(int[] values) {
        if (values.length > 0) {
            Level  = 'A';
            Number = 1;
            getID();
            Value = values[0];

            if (values.length > 1 ) {
                for (int i = 1; i < values.length; i++)
                    addNode(this, values[i]);
            }
        }

        restoreProperty(this);
    }

    public static void restoreProperty(BinaryHeap root) {
        while ( !BinaryHeap.hasProperty(root) ) {
            Queue<BinaryTree> queue = new LinkedList<BinaryTree>();
            queue.add(root);

            BinaryTree currentNode = null;
            while (!queue.isEmpty()) {
                currentNode = queue.poll();

                if ( currentNode.FirstRef != null) {
                    if (currentNode.FirstRef.Value <= currentNode.Value) {

                        int tempValue = currentNode.FirstRef.Value;
                        int[] tempID = currentNode.FirstRef.ID;

                        currentNode.FirstRef.Value = currentNode.Value;
                        currentNode.FirstRef.ID = currentNode.ID;

                        currentNode.Value = tempValue;
                        currentNode.ID = tempID;
                    }
                    queue.add(currentNode.FirstRef);
                }

                if (currentNode.SecondRef != null) {
                    if (currentNode.SecondRef.Value <= currentNode.Value) {

                        int tempValue = currentNode.SecondRef.Value;
                        int[] tempID = currentNode.SecondRef.ID;

                        currentNode.SecondRef.Value = currentNode.Value;
                        currentNode.SecondRef.ID = currentNode.ID;

                        currentNode.Value = tempValue;
                        currentNode.ID = tempID;
                    }

                    queue.add(currentNode.SecondRef);
                }
            }
        }
    }

    public static boolean hasProperty(BinaryHeap root) {
        Queue <BinaryTree> queue = new LinkedList<BinaryTree>();
        queue.add(root);

        BinaryTree currentNode = null;
        while (!queue.isEmpty()) {
            currentNode = queue.poll();

            if ( currentNode.FirstRef != null) {
                if ( currentNode.FirstRef.Value <= currentNode.Value ) return false;
                else queue.add(currentNode.FirstRef);
            }

            if (currentNode.SecondRef != null) {
                if ( currentNode.SecondRef.Value <= currentNode.Value ) return false;
                queue.add(currentNode.SecondRef);
            }
        }
        return true;
    }
}

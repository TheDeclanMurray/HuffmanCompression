package org.headroyce.declanm2022;

import java.util.ArrayList;

//Min-Heap
public class Heap<T extends Comparable<T>> {

    ArrayList<T> tree; //the heap

    public Heap(){
        tree = new ArrayList<T>();
    }

    //add a new node
    public boolean add(T data){
        tree.add(data);

        boolean firstgo = true;
        int curr = tree.size()-1; //add to the end and heapify up
        while(curr > 0){
            if(firstgo){
                firstgo = false;
            }else{
                curr = (curr-1)/2;
            }
            HeapifyUp(curr);
        }
        return true;
    }
    //Check to see if the parent is larger and if it is, swap them
    private void HeapifyUp(int curr){
        int parent = (curr-1)/2;
        if(tree.get(curr).compareTo(tree.get(parent)) < 0){
            T holder = tree.get(curr);
            tree.set(curr, tree.get(parent));
            tree.set(parent, holder);
        }
    }

    //remove the root
    public T remove(){
        T rtn = tree.get(0);
        tree.set(0, tree.get(tree.size()-1)); //set the last node to be the first and heapify down
        tree.remove(tree.size()-1);
        int curr = 0;
        while(curr >= 0 ){
            curr = HeapifyDown(curr);
        }
        return rtn;
    }
    //check if children are smaller, if so swap with smallest child.
    private int HeapifyDown(int curr){
        int left = (curr*2)+1;
        int right = (curr*2)+2;
        //Node has no children
        if(left >=tree.size()){
            if(tree.size() == 0){
                return -1;
            }
            return -1;
        }
        //Node has only left child
        if(right >= tree.size()){
            if(tree.get(curr).compareTo(tree.get(left)) > 0){
                T data = tree.get(curr);
                tree.set(curr, tree.get(left));
                tree.set(left, data);
            }
            return -1;
        }
        //Node has two children
        int comp;
        if(tree.get(left).compareTo(tree.get(right)) <= 0){
            comp = left;
        }else{
            comp = right;
        }
        if(tree.get(comp).compareTo(tree.get(curr)) < 0){
            T data = tree.get(curr);
            tree.set(curr, tree.get(comp));
            tree.set(comp, data);
            return comp;
        }
        return -1;
    }

    //way to check if tree is empty
    public boolean hasNext(){
        if(tree.size() == 0){
            return false;
        }
        return true;
    }

    //convenience for testing
    public String toString(){
        String str = "";
        for(int i = 0; i < tree.size();i++){
            str = str + tree.get(i).toString() +", ";
        }
        return str;
    }
}
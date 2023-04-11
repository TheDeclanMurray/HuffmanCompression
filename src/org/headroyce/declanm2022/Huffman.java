package org.headroyce.declanm2022;

import java.util.*;

public class Huffman {

    public Huffman(){

    }

    public HashMap<String,String> RunHuffman(HashMap<String,Integer> FreqTable){

        //sort the frequency table
        ArrayList Sorted = (ArrayList) Sorter(FreqTable);
        //convert the sorted frequency table to HuffNodeNumber
        ArrayList Sorted2 = (ArrayList) toNumber(Sorted);
        //put the sorted frequency table into a heap
        Heap<HuffNodeNumber> huffHeap = getHeap(Sorted2);
        //turn the heap into the huffman tree
        HuffNodeNumber HuffTree = HuffTree(huffHeap);
        //turn the huffman tree into the huffman dictionary
        HashMap<String,String> Dictionary = HuffDictionary(HuffTree);

        return Dictionary;
    }

    //sort the frequency table alphabetically using a BST
    public List<HuffNodeLetter> Sorter(HashMap<String,Integer> FreqTable){
        BST<HuffNodeLetter> bst = new BST<HuffNodeLetter>();
        HuffNodeLetter letter;
        Set set = FreqTable.keySet();
        Iterator it = set.iterator();
        for (Iterator iter = it; iter.hasNext(); ) {
            String a = (String) iter.next();
            Integer val = (Integer) FreqTable.get(a);
            int value = val.intValue();
            letter = new HuffNodeLetter(a,value);
            bst.add(letter);
        }
        return bst.inOrder();
    }

    //change the HuffNodeLetters into HuffNodeNumbers
    public List<HuffNodeNumber> toNumber(ArrayList<HuffNodeLetter> letters){
        ArrayList<HuffNodeNumber> nums = new ArrayList<HuffNodeNumber>();
        int num;
        String character;
        HuffNodeNumber node;
        for (HuffNodeLetter letter : letters) {
            num = letter.getFrequency();
            character = letter.getName();
            node = new HuffNodeNumber(character, num);
            nums.add(node);
        }
        return nums;
    }

    //put every HuffNodeNumber into a heap in order
    public Heap<HuffNodeNumber> getHeap(ArrayList<HuffNodeNumber> nums){
        Heap<HuffNodeNumber> heap = new Heap<>();
        for (HuffNodeNumber num : nums) {
            heap.add(num);
        }
        return heap;
    }

    //create the Huffman tree
    public HuffNodeNumber HuffTree(Heap<HuffNodeNumber> heap){
        HuffNodeNumber left = new HuffNodeNumber("blank", 200);
        HuffNodeNumber right;
        HuffNodeNumber parent;
        //grab two nodes, create their parent, put the parent back into the heap
        while(heap.hasNext()){
            left = heap.remove();
            if(heap.hasNext()){
                right = heap.remove();
                String name = left.getName() + right.getName();
                int frequency = left.getFrequency() + right.getFrequency();
                parent = new HuffNodeNumber(name,frequency);
                parent.right = right;
                parent.left = left;
                parent.isParent = true;
                heap.add(parent);
            }
        }
        return left;
    }

    //Create the Dictionary from the Huffman Tree
    public HashMap<String,String> HuffDictionary(HuffNodeNumber node){
        HashMap<String,String> dictionary = new HashMap<>();
        dictionary = Navigator(dictionary, "",node);
        return dictionary;
    }

    //keeps track of current position in tree(binary)
    public HashMap<String,String> Navigator(HashMap<String,String> dictionary, String location, HuffNodeNumber curr){
        if(curr.left != null){
            dictionary = Navigator(dictionary,location + "0", curr.left);
        }
        if(curr.right != null){
            dictionary = Navigator(dictionary, location + "1", curr.right);
        }
        if(curr.isParent == false){
            dictionary.put(curr.getName(),location);
        }
        return dictionary;
    }
}

//Class with compareTo() method for frequency
class HuffNodeNumber implements Comparable<HuffNodeNumber>{
    private String name;//the name of the character
    private int frequency;//the frequency of the character
    public HuffNodeNumber left; //used for huffman tree
    public HuffNodeNumber right; //used for huffman tree
    public boolean isParent; //used to determine wither the node is to be put in dictionary

    public HuffNodeNumber(String character, int freq){
        frequency = freq;
        name = character;
        right = null;
        left = null;
        isParent = false;
    }

    //compare to another HuffNodeNumbers
    //returns 0 if the same, 1 if other is smaller, -1 if other is larger
    @Override
    public int compareTo(HuffNodeNumber o) {
        if(frequency == o.getFrequency()){
            return 0;
        }
        if(frequency < o.getFrequency()){
            return -1;
        }
        if(frequency > o.getFrequency()){
            return 1;
        }
        return 0;
    }

    public String toString(){
        return "|"+name + ","+ frequency+"| ";
    }

    public String getName(){return name;}
    public int getFrequency(){return frequency;}
}

//class with compareTo() method for letter
class HuffNodeLetter implements Comparable<HuffNodeLetter>{
    private String name; //the name of the character
    private int frequency; //the frequency of the character

    public HuffNodeLetter(String character, int freq){
        frequency = freq;
        name = character;
    }

    //compare to another HuffNodeLetters
    //returns 0 if the same, 1 if other is smaller, -1 if other is larger
    @Override
    public int compareTo(HuffNodeLetter o) {
        return this.name.compareTo(o.getName());
    }

    public String toString(){
        return "|"+name + ","+ frequency+"| ";
    }

    public String getName(){return name;}
    public int getFrequency(){return frequency;}
}
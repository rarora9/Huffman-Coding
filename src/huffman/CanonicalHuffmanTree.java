package huffman;

import java.util.ArrayList;
import java.util.HashMap;

public class CanonicalHuffmanTree {
	private CanonicalHuffmanNode root;
	
	public CanonicalHuffmanTree() {
		root = new CanonicalHuffmanNode();
	}
	
	public CanonicalHuffmanNode getRoot() {
		return root;
	}
	
	public void insert(int value, int length) {
		CanonicalHuffmanNode current = root;
		
		for(int i = 0; i < length; i++) {
			if(current.getLeft() == null) {
				if(i == length - 1) {
					try {
						current.setLeft(new CanonicalHuffmanNode(value));
					} catch(Exception e) {
						e.printStackTrace();
					}
					
					return;
				}
				
				try {
					current.setLeft(new CanonicalHuffmanNode());
				} catch(Exception e) {
					e.printStackTrace();
				}
				
				current = current.getLeft();
				continue;
			}
			
			if(current.getLeft().isFull()) {
				if(current.getRight() == null) {
					if(i == length - 1) {
						try {
							current.setRight(new CanonicalHuffmanNode(value));
						} catch(Exception e) {
							e.printStackTrace();
						}
						
						return;
					}
					
					try {
						current.setRight(new CanonicalHuffmanNode());
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
				
				current = current.getRight();
				continue;
			}
			
			current = current.getLeft();
		}
	}
	
	public static CanonicalHuffmanTree generateCanonicalHuffmanTree(HashMap<Integer, ArrayList<Integer>> codewordLengths, ArrayList<Integer> lengths) {
		CanonicalHuffmanTree canonicalHuffmanTree = new CanonicalHuffmanTree();
		
		for(int length : lengths) {
			for(int value: codewordLengths.get(length)) {
				canonicalHuffmanTree.insert(value, length);
			}
		}
		
		return canonicalHuffmanTree;
	}
}

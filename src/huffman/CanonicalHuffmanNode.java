package huffman;

public class CanonicalHuffmanNode {
	private CanonicalHuffmanNode left;
	private CanonicalHuffmanNode right;
	private int value;
	
	public CanonicalHuffmanNode() {
		value = -1;
	}
	
	public CanonicalHuffmanNode(int value) {
		this.value = value;
	}
	
	public CanonicalHuffmanNode getLeft() {
		return left;
	}
	
	public CanonicalHuffmanNode getRight() {
		return right;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setLeft(CanonicalHuffmanNode left) throws Exception {
		if(value == -1) {
			this.left = left;
		} else {
			throw new Exception("Cannot set left node since value is not -1.");
		}
	}
	
	public void setRight(CanonicalHuffmanNode right) throws Exception {
		if(value == -1) {
			this.right = right;
		} else {
			throw new Exception("Cannot set right node since value is not -1.");
		}
	}
	
	public boolean isLeaf() {
		if(value == -1) {
			return false;
		}
		
		return true;
	}
	
	public boolean isFull() {
		if(isLeaf()) {
			return true;
		}
		
		if(left == null || right == null) {
			return false;
		}
		
		if(left.isFull() && right.isFull()) {
			return true;
		}
		
		return false;
	}
}

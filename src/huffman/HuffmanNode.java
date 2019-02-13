package huffman;

public class HuffmanNode {
	private HuffmanNode left;
	private HuffmanNode right;
	private int value;
	private int frequency;
	private int height;
	
	public HuffmanNode(int value, int frequency) {
		this.value = value;
		this.frequency = frequency;
		this.height = 0;
	}
	
	public HuffmanNode(HuffmanNode left, HuffmanNode right) {
		this.left = left;
		this.right = right;
		value = -1;
		frequency = left.frequency + right.frequency;
		this.height = Math.max(left.height, right.height) + 1;
	}
	
	public HuffmanNode getLeft() {
		return left;
	}
	
	public HuffmanNode getRight() {
		return right;
	}
	
	public int getValue() {
		return value;
	}
	
	public int getFrequency() {
		return frequency;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean isLeaf() {
		if(value == -1) {
			return false;
		}
		
		return true;
	}
}

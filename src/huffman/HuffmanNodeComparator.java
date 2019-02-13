package huffman;

import java.util.Comparator;

public class HuffmanNodeComparator implements Comparator<HuffmanNode> {
	public int compare(HuffmanNode x, HuffmanNode y) {
		if(x.getFrequency() == y.getFrequency()) {
			if(x.getHeight() == y.getHeight()) {
				return 0;
			}
			
			if(x.getHeight() < y.getHeight()) {
				return -1;
			}
			
			return 1;
		}
		
		if(x.getFrequency() < y.getFrequency()) {
			return -1;
		}
		
		return 1;
	}
}

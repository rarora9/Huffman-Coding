package huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;

import io.OutputStreamBitSink;

public class HuffmanEncoder {
	public static void main(String[] args) {
		File file = new File("data/#1_decoded.txt");
		
		try {
			encode(file);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void encode(File file) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(file);
		int[] charFrequencies = new int[256];
		int numSymbols = 0;
		
		while(fileInputStream.available() > 0) {
			charFrequencies[fileInputStream.read()]++;
			numSymbols++;
		}
		
		PriorityQueue<HuffmanNode> minHeap = new PriorityQueue<HuffmanNode>(256, new HuffmanNodeComparator());
		
		for(int i = 0; i < charFrequencies.length; i++) {
			minHeap.add(new HuffmanNode(i, charFrequencies[i]));
		}
		
		while(minHeap.size() > 1) {
			minHeap.add(new HuffmanNode(minHeap.remove(), minHeap.remove()));
		}
		
		HuffmanNode root = minHeap.remove();
		HashMap<Integer, ArrayList<Integer>> codewordLengths = new HashMap<Integer, ArrayList<Integer>>();
		ArrayList<Integer> lengths = new ArrayList<Integer>();
		findCodewordLengths(root, codewordLengths, lengths, 0);
		
		Collections.sort(lengths);
		
		for(int length : lengths) {
			Collections.sort(codewordLengths.get(length));
		}
		
		CanonicalHuffmanTree canonicalHuffmanTree = CanonicalHuffmanTree.generateCanonicalHuffmanTree(codewordLengths, lengths);
		String[] codewords = new String[256];
		findCodewords(canonicalHuffmanTree.getRoot(), codewords, "");
		
		File encodedFile = new File("data/#5_encoded.dat");
		OutputStream outputStream = new FileOutputStream(encodedFile);
		OutputStreamBitSink outputStreamBitSink = new OutputStreamBitSink(outputStream);
		
		for(int i = 0; i < codewords.length; i++) {
			outputStreamBitSink.write(codewords[i].length(), 8);
		}
		
		outputStreamBitSink.write(numSymbols, 32);
		
		fileInputStream.close();
		fileInputStream = new FileInputStream(file);
		
		while(fileInputStream.available() > 0) {
			outputStreamBitSink.write(codewords[fileInputStream.read()]);
		}
		
		outputStreamBitSink.padToWord();
		
		fileInputStream.close();
	}
	
	public static void findCodewordLengths(HuffmanNode node, HashMap<Integer, ArrayList<Integer>> codewordLengths, ArrayList<Integer> lengths, int depth) {
		if(node.isLeaf()) {
			if(codewordLengths.containsKey(depth)) {
				codewordLengths.get(depth).add(node.getValue());
			} else {
				codewordLengths.put(depth, new ArrayList<Integer>());
				codewordLengths.get(depth).add(node.getValue());
				lengths.add(depth);
			}
		} else {
			findCodewordLengths(node.getLeft(), codewordLengths, lengths, depth + 1);
			findCodewordLengths(node.getRight(), codewordLengths, lengths, depth + 1);
		}
	}
	
	public static void findCodewords(CanonicalHuffmanNode node, String[] codewords, String codeword) {
		if(node.isLeaf()) {
			codewords[node.getValue()] = codeword;
		} else {
			findCodewords(node.getLeft(), codewords, codeword + "0");
			findCodewords(node.getRight(), codewords, codeword + "1");
		}
	}
}

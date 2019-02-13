package huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import io.InputStreamBitSource;
import io.InsufficientBitsLeftException;
import io.OutputStreamBitSink;

public class HuffmanDecoder {
	public static void main(String[] args) {
		File encodedFile = new File("data/compressed.dat");
		
		try {
			decode(encodedFile);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void decode(File encodedFile) throws IOException {
		InputStream inputStream = new FileInputStream(encodedFile);
		InputStreamBitSource inputStreamBitSource = new InputStreamBitSource(inputStream);
		
		HashMap<Integer, ArrayList<Integer>> codewordLengths = new HashMap<Integer, ArrayList<Integer>>();
		ArrayList<Integer> lengths = new ArrayList<Integer>();
		
		for(int value = 0; value < 256; value++) {
			int codewordLength = 0;
			
			try {
				codewordLength = inputStreamBitSource.next(8);
			} catch(InsufficientBitsLeftException e) {
				e.printStackTrace();
			}
			
			if(codewordLengths.containsKey(codewordLength)) {
				codewordLengths.get(codewordLength).add(value);
			} else {
				codewordLengths.put(codewordLength, new ArrayList<Integer>());
				codewordLengths.get(codewordLength).add(value);
				lengths.add(codewordLength);
			}
		}
		
		Collections.sort(lengths);
		
		int numSymbols = 0;
		
		try {
			numSymbols = inputStreamBitSource.next(32);
		} catch(InsufficientBitsLeftException e) {
			e.printStackTrace();
		}
		
		CanonicalHuffmanTree canonicalHuffmanTree = CanonicalHuffmanTree.generateCanonicalHuffmanTree(codewordLengths, lengths);
		File decodedFile = new File("data/#1_decoded.txt");
		OutputStream outputStream = new FileOutputStream(decodedFile);
		OutputStreamBitSink outputStreamBitSink = new OutputStreamBitSink(outputStream);
		
		for(int i = 0; i < numSymbols; i++) {
			CanonicalHuffmanNode current = canonicalHuffmanTree.getRoot();
			
			while(true) {
				int currentBit = -1;
				
				try {
					currentBit = inputStreamBitSource.next(1);
				} catch(InsufficientBitsLeftException e) {
					e.printStackTrace();
				}
				
				if(currentBit == 0) {
					current = current.getLeft();
				} else {
					current = current.getRight();
				}
				
				if(current.isLeaf()) {
					outputStreamBitSink.write(current.getValue(), 8);
					break;
				}
			}
		}
	}
}

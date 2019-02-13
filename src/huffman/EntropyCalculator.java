package huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import io.InputStreamBitSource;
import io.InsufficientBitsLeftException;

public class EntropyCalculator {
	public static void main(String[] args) throws IOException {
		// Answers to numbers 2 and 3:
		
		File file = new File("data/#1_decoded.txt");
		FileInputStream fileInputStream = new FileInputStream(file);
		
		int[] charFrequencies = new int[256];
		int numSymbols = 0;
		
		while(fileInputStream.available() > 0) {
			charFrequencies[fileInputStream.read()]++;
			numSymbols++;
		}
		
		fileInputStream.close();
		
		double theoreticalEntropy = 0.0;
		
		for(int i = 0; i < charFrequencies.length; i++) {
			if(charFrequencies[i] > 0) {
				double probabilityOfSymbol = ((double) charFrequencies[i]) / ((double) numSymbols);
				theoreticalEntropy += probabilityOfSymbol * -1.0 * (Math.log(probabilityOfSymbol) / Math.log(2));
			}
		}
		
		System.out.println("(3) The theoretical entropy of the source message is " + theoreticalEntropy + " bits/symbol.");
		
		// Answer to number 4:
		
		File encodedFile = new File("data/compressed.dat");
		InputStream inputStream = new FileInputStream(encodedFile);
		InputStreamBitSource inputStreamBitSource = new InputStreamBitSource(inputStream);
		
		int[] bitLengths = new int[256];
		
		for(int i = 0; i < 256; i++) {
			try {
				bitLengths[i] = inputStreamBitSource.next(8);
			} catch(InsufficientBitsLeftException e) {
				e.printStackTrace();
			}
		}
		
		
		try {
			numSymbols = inputStreamBitSource.next(32);
		} catch(InsufficientBitsLeftException e) {
			e.printStackTrace();
		}
		
		double compressedEntropy = 0.0;
		
		for(int i = 0; i < charFrequencies.length; i++) {
			compressedEntropy += (((double) charFrequencies[i]) / ((double) numSymbols)) * ((double) bitLengths[i]);
		}
		
		System.out.println("(4) The compressed entropy achieved by the provided compressed file is " + compressedEntropy + " bits/symbol.");
		
		//Answer to number 6:
		
		encodedFile = new File("data/#5_encoded.dat");
		inputStream = new FileInputStream(encodedFile);
		inputStreamBitSource = new InputStreamBitSource(inputStream);
		
		bitLengths = new int[256];
		
		for(int i = 0; i < 256; i++) {
			try {
				bitLengths[i] = inputStreamBitSource.next(8);
			} catch(InsufficientBitsLeftException e) {
				e.printStackTrace();
			}
		}
		
		
		try {
			numSymbols = inputStreamBitSource.next(32);
		} catch(InsufficientBitsLeftException e) {
			e.printStackTrace();
		}
		
		compressedEntropy = 0.0;
		
		for(int i = 0; i < charFrequencies.length; i++) {
			compressedEntropy += (((double) charFrequencies[i]) / ((double) numSymbols)) * ((double) bitLengths[i]);
		}
		
		System.out.println("(6) The compressed entropy achieved by the Huffman code produced by my encoder is " + compressedEntropy + " bits/symbol.");
	}
}

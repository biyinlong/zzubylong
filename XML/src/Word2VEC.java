import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;


public class Word2VEC {
	
	private HashMap<String, float[]> wordMap = new HashMap<String, float[]>();	 
	private int words;  //ѵ��ģ���дʵ�������Ŀ
	private int size;   //��������ά��
	private int topNSize = 40; //�������������������ɴ���
	private static final int MAX_SIZE = 50;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub		
		Word2VEC vec = new Word2VEC();
		vec.loadModel("e://news_12g_baidubaike_20g_novel_90g_embedding_64.bin");
		System.out.println("ģ�ͼ������");
		System.out.println(vec.getWordVector("ë��"));
 
	/*	System.out.println("One word analysis");
		Set<WordEntry> result = new TreeSet<WordEntry>();
		result = vec.distance("����");   //�����"����"��������ɴʻ�	
		System.out.println(result.size());
		Iterator iter = result.iterator();
		while (iter.hasNext()) {
			WordEntry word = (WordEntry) iter.next();
			System.out.println(word.name + " " + word.score);
		}
		
		System.out.println("*******************************");
		System.out.println("Three word analysis");  //�ʶԷ���:"�л����-�л����񹲺͹�-��"	
		result = vec.analogy("�л����", "�л����񹲺͹�", "ë��");
		iter = result.iterator();
		while (iter.hasNext()) {
			WordEntry word = (WordEntry) iter.next();
			System.out.println(word.name + " " + word.score);
		}
		*/
	}//end main
	
	/**@description �õ�������,��Ҫ��������:wordMap;
	 * @param word;
	 * @return ����Ĵ�����;
	 */
	public float[] getWordVector(String word) {
		return wordMap.get(word);
	}
	/**@description ���شʵ������д���Ĵ�����;
	 *
	 **/
	public HashMap<String, float[]> getWordMap() {
		return wordMap;
	}	
	
	public int getTopNSize() {
		return topNSize;
	}
 
	public void setTopNSize(int topNSize) {
		this.topNSize = topNSize;
	}
	
	public int getWords() {
		return words;
	}
 
	public int getSize() {
		return size;
	}	
	
	/**
	 * @description ����ģ��	 
	 * @param path ģ�͵�·��
	 * @throws IOException
	 */
	public void loadModel(String path) throws IOException {
		DataInputStream dis = null;
		BufferedInputStream bis = null;
		double len = 0;
		float vector = 0;
		try {
			bis = new BufferedInputStream(new FileInputStream(path));
			dis = new DataInputStream(bis);			
			words = Integer.parseInt(readString(dis));//��ȡ����	
			System.out.println("�ʵ��еĴ�����" + words);
			size = Integer.parseInt(readString(dis)); //��������ά��			
			String word;
			float[] vectors = null;
			for (int i = 0; i < words; i++) {//�����ʵ��е����еĴ���
				word = readString(dis);
				vectors = new float[size];
				len = 0;
				for (int j = 0; j < size; j++) {
					vector = readFloat(dis);
					len += vector * vector;
					vectors[j] = (float) vector;
				}
				len = Math.sqrt(len); 
				for (int j = 0; j < vectors.length; j++) {
					vectors[j] = (float) (vectors[j] / len);
				}				
				wordMap.put(word, vectors);//����ģ�ͻ�ȡ���������б���
				System.out.println("word: " + word + "\t" + vectors[1]);
				dis.read();
			}			 
		} finally {
			bis.close();
			dis.close();
		}		
	}//end function
	
	public class WordEntry implements Comparable<WordEntry> {
		public String name;  
		public float score; 
		public WordEntry(String name, float score) {
			this.name = name;
			this.score = score;
		} 
		@Override
		public String toString() {
			return this.name + "\t" + score;
		} 
		@Override
		public int compareTo(WordEntry o) {
			if (this.score > o.score) {
				return -1;
			} else {
				return 1;
			}
		} 
	}//end class
 
	/**
	 * @description �õ������;
	 * @param ����word;
	 * @return ���������������ɴ���;
	 */
	public Set<WordEntry> distance(String word) {
		float[] wordVector = getWordVector(word);
		if (wordVector == null) {
			return null;
		}
		Set<Entry<String, float[]>> entrySet = wordMap.entrySet();
		float[] tempVector = null;
		List<WordEntry> wordEntrys = new ArrayList<WordEntry>(topNSize);
		String name = null;
		for (Entry<String, float[]> entry : entrySet) {
			name = entry.getKey();
			if (name.equals(word)) {
				continue;
			}
			float dist = 0;
			tempVector = entry.getValue();
			for (int i = 0; i < wordVector.length; i++) {
				dist += wordVector[i] * tempVector[i];
			}
			insertTopN(name, dist, wordEntrys);
		}
		return new TreeSet<WordEntry>(wordEntrys);
	}//end function
	
	private void insertTopN(String name, float score, List<WordEntry> wordsEntrys) {
		if (wordsEntrys.size() < topNSize) {
			wordsEntrys.add(new WordEntry(name, score));
			return;
		}
		float min = Float.MAX_VALUE;
		int minOffe = 0;
		for (int i = 0; i < topNSize; i++) {
			WordEntry wordEntry = wordsEntrys.get(i);
			if (min > wordEntry.score) {
				min = wordEntry.score;
				minOffe = i;
			}
		} 
		if (score > min) {
			wordsEntrys.set(minOffe, new WordEntry(name, score));
		} 
	}//end function	
	
	/**
	 * @deprecated���رȶԴ� 
	 * @return
	 */
	public TreeSet<WordEntry> analogy(String word0, String word1, String word2) {
		float[] wv0 = getWordVector(word0);
		float[] wv1 = getWordVector(word1);
		float[] wv2 = getWordVector(word2); 
		if (wv1 == null || wv2 == null || wv0 == null) {
			return null;
		}
		float[] wordVector = new float[size];
		for (int i = 0; i < size; i++) {
			wordVector[i] = wv1[i] - wv0[i] + wv2[i];
		}
		float[] tempVector;
		String name;
		List<WordEntry> wordEntrys = new ArrayList<WordEntry>(topNSize);
		for (Entry<String, float[]> entry : wordMap.entrySet()) {
			name = entry.getKey();
			if (name.equals(word0) || name.equals(word1) || name.equals(word2)) {
				continue;
			}
			float dist = 0;
			tempVector = entry.getValue();
			for (int i = 0; i < wordVector.length; i++) {
				dist += wordVector[i] * tempVector[i];
			}
			insertTopN(name, dist, wordEntrys);
		}
		return new TreeSet<WordEntry>(wordEntrys);
	}	
 
	public static float readFloat(InputStream is) throws IOException {
		byte[] bytes = new byte[4];
		is.read(bytes);
		return getFloat(bytes);
	}

	/**
	 * ��ȡһ��float
	 * @param b
	 * @return
	 */
	public static float getFloat(byte[] b) {
		int accum = 0;
		accum = accum | (b[0] & 0xff) << 0;
		accum = accum | (b[1] & 0xff) << 8;
		accum = accum | (b[2] & 0xff) << 16;
		accum = accum | (b[3] & 0xff) << 24;
		return Float.intBitsToFloat(accum);
	}
	
	/**
	 * ��ȡһ���ַ��� 
	 * @param dis
	 * @return
	 * @throws IOException
	 */
	private static String readString(DataInputStream dis) throws IOException {
		byte[] bytes = new byte[MAX_SIZE];
		byte b = dis.readByte();
		int i = -1;
		StringBuilder sb = new StringBuilder();
		while (b != 32 && b != 10) {
			i++;
			bytes[i] = b;
			b = dis.readByte();
			if (i == 49) {
				sb.append(new String(bytes));
				i = -1;
				bytes = new byte[MAX_SIZE];
			}
		}
		sb.append(new String(bytes, 0, i + 1));
		System.out.println(sb.toString());
		return sb.toString();
	}

}//end class

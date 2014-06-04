import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class ShortestPath {
	private static final int minDis = 0;
	private static final int maxDis = Integer.MAX_VALUE;
	// ͼ���ڽӾ���
	int[][] matrix;
	// ��ʼ��
	int startIndex;
	// ���������ʼ�㵽�����㵱ǰ�ľ���
	HashMap<Integer, Integer> distanceMap = new HashMap<Integer, Integer>();
	// ��������Ѿ��ҵ����·���ĵ�ļ���
	Set<Integer> findedSet = new HashSet<Integer>();

	public ShortestPath(int[][] matrix, int start) {
		this.matrix = matrix;
		this.startIndex = start;
		//findedSet.add(startIndex);
	}

	public void find() {
		// ��start���ڵĵ��ʼ��distanceMap
		for (int i = 0; i < matrix.length; i++) {
			//if (matrix[startIndex][i] != maxDis)
				distanceMap.put(i, matrix[startIndex][i]);
		}

		while (findedSet.size() != matrix.length) {
			int currentMinIndex = currentMinIndex();
			// �ô˽������������ľ���
			for (int i = 0; i < matrix.length; i++) {
				if (!findedSet.contains(i) && matrix[currentMinIndex][i] != maxDis
						&& matrix[currentMinIndex][i] + distanceMap.get(currentMinIndex) < distanceMap.get(i))
					distanceMap.put(i, matrix[currentMinIndex][i] + distanceMap.get(currentMinIndex));
			}
			
			// ����findedset
			findedSet.add(currentMinIndex);
		}
	}

	//��ӡ����ʼ�㵽���е����̾���
	public void printDistance(){
		Iterator<Entry<Integer, Integer>> it = distanceMap.entrySet().iterator();
		int min = Integer.MIN_VALUE;
		int minIndex = -1;
		while (it.hasNext()) {
			Entry<Integer, Integer> entry = it.next();
			System.out.println(startIndex+"---->"+entry.getKey()+":"+entry.getValue());
		}
	}
	
	// ���ص�ǰ��С����ĵ�(���벻������findedSet��)
	private int currentMinIndex() {
		Iterator<Entry<Integer, Integer>> it = distanceMap.entrySet().iterator();
		int min = Integer.MAX_VALUE;
		int minIndex = -1;
		while (it.hasNext()) {
			Entry<Integer, Integer> entry = it.next();
			if (!findedSet.contains(entry.getKey()) && entry.getValue() < min) {
				min = entry.getValue();
				minIndex = entry.getKey();
			}
		}
		return minIndex;
	}

	public static void main(String[] args) {
		int[][] inputMatrix = new int[][] { { minDis, 2, maxDis, 1, maxDis, maxDis, maxDis }, { maxDis, minDis, maxDis, 3, 10, maxDis, maxDis },
				{ 4, maxDis, minDis, maxDis, maxDis, 5, maxDis }, { maxDis, maxDis, 2, minDis, 2, 8, 4 },
				{ maxDis, maxDis, maxDis, maxDis, minDis, maxDis, 6 }, { maxDis, maxDis, maxDis, maxDis, maxDis, minDis, maxDis },
				{ maxDis, maxDis, maxDis, maxDis, maxDis, 1, minDis } };
		ShortestPath path = new ShortestPath(inputMatrix, 0);
		path.find();
		path.printDistance();

	}
}

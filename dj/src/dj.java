import java.util.*;

class Node {
	private String name;
	private Map<Node,Integer> child=new HashMap<Node,Integer>();
	public Node(String name){
		this.name=name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<Node, Integer> getChild() {
		return child;
	}
	public void setChild(Map<Node, Integer> child) {
		this.child = child;
	}
}

class MapBuilder {
	public Node build(Set<Node> open, Set<Node> close){
		Node nodeA=new Node("A");
		Node nodeB=new Node("B");
		Node nodeC=new Node("C");
		Node nodeD=new Node("D");
		Node nodeE=new Node("E");
		Node nodeF=new Node("F");
		Node nodeG=new Node("G");
		Node nodeH=new Node("H");
		nodeA.getChild().put(nodeB, 1);
		nodeA.getChild().put(nodeC, 1);
		nodeA.getChild().put(nodeD, 4);
		nodeA.getChild().put(nodeG, 5);
		nodeA.getChild().put(nodeF, 2);
		nodeB.getChild().put(nodeA, 1);
		nodeB.getChild().put(nodeF, 2);
		nodeB.getChild().put(nodeH, 4);
		nodeC.getChild().put(nodeA, 1);
		nodeC.getChild().put(nodeG, 3);
		nodeD.getChild().put(nodeA, 4);
		nodeD.getChild().put(nodeE, 1);
		nodeE.getChild().put(nodeD, 1);
		nodeE.getChild().put(nodeF, 1);
		nodeF.getChild().put(nodeE, 1);
		nodeF.getChild().put(nodeB, 2);
		nodeF.getChild().put(nodeA, 2);
		nodeG.getChild().put(nodeC, 3);
		nodeG.getChild().put(nodeA, 5);
		nodeG.getChild().put(nodeH, 1);
		nodeH.getChild().put(nodeB, 4);
		nodeH.getChild().put(nodeG, 1);
		open.add(nodeB);
		open.add(nodeC);
		open.add(nodeD);
		open.add(nodeE);
		open.add(nodeF);
		open.add(nodeG);
		open.add(nodeH);
		close.add(nodeA);
		return nodeA;
	}
}

class Dijkstra {
	Set<Node> open=new HashSet<Node>();
	Set<Node> close=new HashSet<Node>();
	Map<String,Integer> path=new HashMap<String,Integer>();//��װ·������
	Map<String,String> pathInfo=new HashMap<String,String>();//��װ·����Ϣ
	public Node init(){
		//��ʼ·��,��û��A->E����·��,����path(E)����ΪInteger.MAX_VALUE
		path.put("B", 1);
		pathInfo.put("B", "A->B");
		path.put("C", 1);
		pathInfo.put("C", "A->C");
		path.put("D", 4);
		pathInfo.put("D", "A->D");
		path.put("E", Integer.MAX_VALUE);
		pathInfo.put("E", "A");
		path.put("F", 2);
		pathInfo.put("F", "A->F");
		path.put("G", 5);
		pathInfo.put("G", "A->G");
		path.put("H", Integer.MAX_VALUE);
		pathInfo.put("H", "A");
		//����ʼ�ڵ����close,�����ڵ����open
		Node start=new MapBuilder().build(open,close);
		return start;
	}
	public void computePath(Node start){
		Node nearest=getShortestPath(start);//ȡ����start�ڵ�������ӽڵ�,����close
		if(nearest==null){
			return;
		}
		close.add(nearest);
		open.remove(nearest);
		Map<Node,Integer> childs=nearest.getChild();
		for(Node child:childs.keySet()){
			if(open.contains(child)){//����ӽڵ���open��
				Integer newCompute=path.get(nearest.getName())+childs.get(child);
				if(path.get(child.getName())>newCompute){//֮ǰ���õľ�������¼�������ľ���
					path.put(child.getName(), newCompute);
					pathInfo.put(child.getName(), pathInfo.get(nearest.getName())+"->"+child.getName());
				}
			}
		}
		computePath(start);//�ظ�ִ���Լ�,ȷ�������ӽڵ㱻����
		computePath(nearest);//����һ���ݹ�,ֱ�����ж��㱻����
	}
	public void printPathInfo(){
		Set<Map.Entry<String, String>> pathInfos=pathInfo.entrySet();
		for(Map.Entry<String, String> pathInfo:pathInfos){
			System.out.println(pathInfo.getKey()+":"+pathInfo.getValue());
		}
	}
	/**
	 * ��ȡ��node������ӽڵ�
	 */
	private Node getShortestPath(Node node){
		Node res=null;
		int minDis=Integer.MAX_VALUE;
		Map<Node,Integer> childs=node.getChild();
		for(Node child:childs.keySet()){
			if(open.contains(child)){
				int distance=childs.get(child);
				if(distance<minDis){
					minDis=distance;
					res=child;
				}
			}
		}
		return res;
	}
}

public class dj {
	public static void main(String[] args) {
		Dijkstra test=new Dijkstra();
		Node start=test.init();
		test.computePath(start);
		test.printPathInfo();
	}
}
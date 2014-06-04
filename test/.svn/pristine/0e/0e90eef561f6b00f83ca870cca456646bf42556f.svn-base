#!/user/bin/env python

class Edge:
    
    def __init__(self, v, w, weight):
        self.__v = v
        self.__w = w
        self.__weight = weight
        
    def weight(self):
        return self.__weight
    
    def either(self):
        return self.__v
    
    def other(self, v):
        if v == self.__v:
            return self.__w
        elif v == self.__w:
            return self.__v
        else:
            return None
        
    def __gt__(self, other):
        return self.__weight > other.weight()
    
    def __lt__(self, other):
        return self.__weight < other.weight()
    
    def __le__(self, other):
        return self.__weight <= other.weight()
    
    def __eq__(self, other):
        return self.__weight == other.weight()
    
    def __ne__(self, other):
        return self.__weight != other.weight()
    
    def __ge__(self, other):
        return self.__weight >= other.weight()
        
        
    def toString(self):
        return '%s-%s: %s'%(self.__v,self.__w,self.__weight)
    
class EdgeWeightGraph:
    
    def __init__(self, V):
        self.__V = V
        self.__E = 0
        self.__adj = [[] for i in range(V)]
        
    def V(self):
        return self.__V

    def E(self):
        return self.__E
    
    
    def addEdge(self, e):
        v = e.either()
        w = e.other(v)
        self.__adj[v].append(e)
        self.__adj[w].append(e)
        self.__E += 1
        
    def adj(self, v):
        return self.__adj[v]
    
    def edges(self):
        b = []
        for i in range(self.__V):
            for e in self.__adj[i]:
                if e.other(i) > i:
                    b.append(e)
        return b
        
from heap_sort import MinPriorityQueue       
      
class PrimMST:
    
    def __init__(self, G):
        self.__marked = [False for i in range(G.V())]
        self.__pq = MinPriorityQueue()
        self.__mst = []
        
        self.__visit(G,0)
        while not self.__pq.isEmpty():
            e = self.__pq.deleteMin()
            v = e.either()
            w = e.other(v)
            if self.__marked[v] and self.__marked[w]:
                continue
            self.__mst.append(e)
            if not self.__marked[v]:
                self.__visit(G, v)
            if not self.__marked[w]:
                self.__visit(G, w)
                
    def __visit(self, G, v):
        self.__marked[v] = True
        for e in G.adj(v):
            if not self.__marked[e.other(v)]:
                self.__pq.insert(e)
                
    def edges(self):
        return self.__mst
            
    def weight(self):
        w = 0
        for e in self.__mst:
            w += e.weight()
        return w
            
from union_find import WeightedQuickUnionUF
  
class KruskalMST:
      
    def __init__(self, G):
        self.__mst = []
        self.__pq = MinPriorityQueue()  
        for e in G.edges():
            self.__pq.insert(e)  
        uf = WeightedQuickUnionUF(G.V())
        
        while not self.__pq.isEmpty() and len(self.__mst) < G.V()-1:
            e = self.__pq.deleteMin()
            v = e.either()
            w = e.other(v)
            if uf.connected(v,w):
                continue
            uf.union(v,w)
            self.__mst.append(e)
    
    def edges(self):
        return self.__mst
    
    def weight(self):
        w = 0
        for e in self.__mst:
            w += e.weight()
        return w        
    

import unittest

class TestMST(unittest.TestCase):
    
    @classmethod
    def setUpClass(self):
        self.g = EdgeWeightGraph(8)
        edges = [(4,5,0.35),(4,7,0.37),(5,7,0.28),(0,7,0.16),
                 (1,5,0.32),(0,4,0.38),(2,3,0.17),(1,7,0.19),
                 (0,2,0.26),(1,2,0.36),(1,3,0.29),(2,7,0.34),
                 (6,2,0.40),(3,6,0.52),(6,0,0.58),(6,4,0.93)]
        for e in edges:
            self.g.addEdge(Edge(e[0],e[1],e[2]))
    
    def test_edges(self):
        e1 = Edge(1, 2, 10)
        e2 = Edge(2,3, 5)
        print e1 < e2
        print e1 > e2
        
    def test_EdgeWeightG(self):
        for e in self.g.edges(): 
            print e.toString()
        for e in self.g.adj(7):
            print e.toString()
                  
        
    def test_PrimMST(self):
        mst = PrimMST(self.g)
        for e in mst.edges():
            print e.toString()
        print mst.weight()
        
    def test_KruskalMST(self):
        mst = KruskalMST(self.g)
        for e in mst.edges():
            print e.toString()
        print mst.weight()
             
        
if __name__ == '__main__':
    unittest.main(exit=False)        
        
        
        
        
        
        
    
    
    
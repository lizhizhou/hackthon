#!/user/bin/env python

class DirectEdge:
    
    def __init__(self, v, w, weight):
        self.__v = v
        self.__w = w
        self.__weight = weight
        
    def weight(self):
        return self.__weight
    
    def fromEdge(self):
        return self.__v
    
    def toEdge(self):
        return self.__w
    
       
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
        return '%s->%s: %s'%(self.__v,self.__w,self.__weight)

    
class EdgeWeightDiGraph:
    
    def __init__(self, V):
        self.__V = V
        self.__E = 0
        self.__adj = [[] for i in range(V)]
        
    def V(self):
        return self.__V

    def E(self):
        return self.__E
    
    
    def addEdge(self, e):
        self.__adj[e.fromEdge()].append(e)
        self.__E += 1
        
    def adj(self, v):
        return self.__adj[v]
    
    def edges(self):
        b = []
        for i in range(self.__V):
            for e in self.__adj[i]:
                b.append(e)
        return b 
    
from heap_sort import IndexMinPQ

class DijkstraSP:
    
    def __init__(self, G, s):
        self.__edgeTo = [None for i in range(G.V())]
        self.__distTo = [float('+inf') for i in range(G.V())]
        self.__pq = IndexMinPQ(G.V())
        
        self.__distTo[s] = 0.0
        self.__pq.insert(s, 0.0)
        while not self.__pq.isEmpty():
            self.__relax(G, self.__pq.deleteMin()[0])
        
    def __relax(self, G, v):
        for e in G.adj(v):
            w = e.toEdge()
            if self.__distTo[w] > self.__distTo[v] + e.weight():
                self.__distTo[w] = self.__distTo[v] + e.weight()
                self.__edgeTo[w] = e
                if self.__pq.contains(w):
                    self.__pq.change(w, self.__distTo[w])
                else:
                    self.__pq.insert(w, self.__distTo[w])
                    
    def distTo(self, v):
        return self.__distTo[v]
    
    def hasPathTo(self, v):
        return self.__distTo[v] < float('+inf')
    
    def pathTo(self, v):
        if not self.hasPathTo(v):
            return None
        path = []
        e = self.__edgeTo[v]
        while e is not None:
            path.append(e)
            e = self.__edgeTo[e.fromEdge()]
        return path

"""
TODO: Bellman-Ford
"""
    
import unittest

class TestShortestPath(unittest.TestCase):
    
    @classmethod
    def setUpClass(self):
        self.g = EdgeWeightDiGraph(8)
        edges = [(4,5,0.35),(5,4,0.35),(4,7,0.37),(5,7,0.28),
                 (7,5,0.28),(5,1,0.32),(0,4,0.38),(0,2,0.26),
                 (7,3,0.39),(1,3,0.29),(2,7,0.34),(6,2,0.40),
                 (3,6,0.52),(6,0,0.58),(6,4,0.93)]
        for e in edges:
            self.g.addEdge(DirectEdge(e[0],e[1],e[2]))
            
    def test_edgeweighdi_g(self):
        for e in self.g.edges():
            print e.toString()   
        for e in self.g.adj(7):
            print e.toString()
            
    def test_DijkstraSP(self):
        djsp = DijkstraSP(self.g, 0)
        for i in range(self.g.V()):
            print djsp.distTo(i)
            for e in djsp.pathTo(i):
                print e.toString()
        
            
          
if __name__ == '__main__':
    unittest.main(exit=False)      
    
    

        
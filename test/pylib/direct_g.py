#!/user/bin/env python

class DirectGraph:
    
    def __init__(self, V):
        self.__V = V
        self.__E = 0
        self.__adj = [[] for i in range(V)]
        
    def V(self):
        return self.__V

    def E(self):
        return self.__E
    
    def addEdge(self, v, w):
        self.__adj[v].append(w)       
        self.__E += 1
        
    def adj(self, v):
        return self.__adj[v]
    
    def degree(self, v):
        return len(self.__adj[v])
    
    def toString(self):
        s = '%s vertices, %s edges\n'%(self.__V,self.__E)
        for i in range(self.__V):
            stra = ['%s'%x for x in self.__adj[i]]
            #print stra
            s += '%s: %s\n'%(i, ' '.join(stra))
        return s
    
    def reverse(self):
        rg = DirectGraph(self.__V)
        for i in range(self.__V):
            for w in self.__adj[i]:
                rg.addEdge(w, i)
        return rg
    
    
    
"""
DFS/BFS are same as undirect graph
"""

class DirectCycle:
    
    def __init__(self, G):
        self.__onStack = [False for i in range(G.V())]
        self.__edgeTo = [-1 for i in range(G.V())]
        self.__marked = [False for i in range(G.V())]
        self.__cycle = None
        for i in range(G.V()):
            if not self.__marked[i]:
                self.__dfs(G, i)
    
    def __dfs(self, G, v):
        self.__onStack[v] = True
        self.__marked[v] = True
        for w in G.adj(v):
            if self.hasCycle():
                return
            elif not self.__marked[w]:
                self.__edgeTo[w] = v
                self.__dfs(G, w)
            elif self.__onStack[w]:
                self.__cycle = []
                x = v
                while x != w:
                    self.__cycle.append(x)
                    x = self.__edgeTo[x]
                self.__cycle.append(w)
                self.__cycle.append(v)
        else:
            self.__onStack[v] = False
            
    def hasCycle(self):
        return self.__cycle is not None
    
    def cycle(self):
        return self.__cycle
    
class DepthFirstOrder:
    
    def __init__(self, G):
        self.__pre = []
        self.__post = []
        self.__marked = [False for i in range(G.V())]
        for i in range(G.V()):
            if not self.__marked[i]:
                self.__dfs(G, i)
                
    def __dfs(self, G, v):
        self.__marked[v] = True
        self.__pre.append(v)
        for w in G.adj(v):
            if not self.__marked[w]:
                self.__dfs(G, w)
        else:
            self.__post.append(v)
            
    def preOrder(self):
        return self.__pre
    
    def postOrder(self):
        return self.__post
    
    def reversePost(self):
        rp = [x for x in reversed(self.__post)]
        return rp


class Topological:
    
    def __init__(self, G):
        self.__order = None
        self.__topOrder(G)
        
    def __topOrder(self, G):
        dc = DirectCycle(G)
        if not dc.hasCycle():
            dfs = DepthFirstOrder(G)
            self.__order = dfs.reversePost()
            
    def order(self):
        return self.__order
    
    def isDAG(self):
        return self.__order is not None
                

"""
KosarajuSCC
"""
class StrongCC:
    
    def __init__(self, G):
        self.__marked = [False for i in range(G.V())]
        self.__id = [-1 for i in range(G.V())]
        self.__count = 0
        order = DepthFirstOrder(G.reverse())
        for i in order.reversePost():
            if not self.__marked[i]:
                self.__dfs(G, i)
                self.__count += 1
                
    def __dfs(self, G, v):
        self.__marked[v] = True
        self.__id[v] = self.__count
        for w in G.adj(v):
            if not self.__marked[w]:
                self.__dfs(G, w)
                
    def StrongConnect(self, v, w):
        return self.__id[v] == self.__id[w]
    
    def id(self, v):
        return self.__id[v]
    
    def count(self):
        return self.__count
            
    
import unittest

class TestDirectGraph(unittest.TestCase):
    
    @classmethod
    def setUpClass(self):
        self.g = DirectGraph(13)
        edges = [(0, 5),(0,1),(5,4),(4,3),(3,5),(3,2),(4,2),(2,3),
                 (2,0),(6,0),(6,4),(11,4),(11,12),(6,7),(6,9),(11,9),
                 (7,8),(8,7),(8,9),(9,10),(10,12),(12,9)]
        for e in edges:
            self.g.addEdge(e[0], e[1])
            
        
    def test_direct_g(self):
        print self.g.toString()  
        
    def test_direct_cycle(self):
        dc = DirectCycle(self.g)
        print dc.hasCycle()
        print dc.cycle()
        
    def test_dfs_order(self):
        dfs = DepthFirstOrder(self.g)
        print dfs.postOrder()
        print dfs.preOrder()
        print dfs.reversePost()
        
    def test_topological(self):
        dag = DirectGraph(13)
        edges = [(0,1),(0,5),(0,6),(2,0),(2,3),(3,5),(5,4),(6,4),
                 (8,7),(7,6),(6,9),(9,10),(9,11),(9,12),(11,12)]
        for e in edges:
            dag.addEdge(e[0], e[1])
        top = Topological(dag)
        print top.order()
        
    def test_strong_cc(self):
        scc = StrongCC(self.g)
        print scc.count()
        for i in range(13):
            print scc.id(i)
           
        

if __name__ == '__main__':
    unittest.main(exit=False)     
    
        
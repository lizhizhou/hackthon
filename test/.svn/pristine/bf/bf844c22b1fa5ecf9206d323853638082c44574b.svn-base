#!/user/bin/env python

class UndirectGraph:
    
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
        self.__adj[w].append(v)        
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
    
    
class DepthFirstSearch:
    
    def __init__(self, G, s):
        self.__marked = [False for i in range(G.V())]
        self.__count = 0
        self.__dfs(G, s)
                
    def __dfs(self, G, v):
        self.__marked[v] = True
        self.__count += 1
        for w in G.adj(v):
            if not self.__marked[w]:
                self.__dfs(G, w)
    
    def marked(self, w):
        return self.__marked[w]
    
    def count(self):
        return self.__count 
    
class DepthFirstPath:
    
    def __init__(self, G, s):
        self.__marked = [False for i in range(G.V())]
        self.__s = s
        self.__edgeTo = [-1 for i in range(G.V())]
        self.__dfs(G, s)
        
    def __dfs(self, G, v):
        self.__marked[v] = True
        for w in G.adj(v):
            if not self.__marked[w]:
                self.__edgeTo[w] = v
                self.__dfs(G, w)
                
    def hasPathTo(self, w):
        return self.__marked[w]
    
    def PathTo(self, w):
        if not self.__marked[w]:
            return None
        path = []
        x = w
        while x != self.__s:
            path.append(x)
            x = self.__edgeTo[x]
        path.append(x)
        return path
        

class BreadthFirstPath:
    
    def __init__(self, G, s):
        self.__marked = [False for i in range(G.V())]
        self.__s = s
        self.__edgeTo = [-1 for i in range(G.V())]
        self.__bfs(G, s)
        
    def __bfs(self, G, s):
        import Queue
        q = Queue.Queue(maxsize=G.V())
        self.__marked[s] = True
        q.put(s)
        while not q.empty():
             v = q.get()
             for w in G.adj(v):
                 if not self.__marked[w]:
                     self.__edgeTo[w] = v
                     self.__marked[w] = True
                     q.put(w)
                     
    def hasPathTo(self, w): 
        return self.__marked[w]
    
    def PathTo(self,w):   
        if not self.__marked[w]:
            return None
        path = []
        x = w
        while x != self.__s:
            path.append(x)
            x = self.__edgeTo[x]
        path.append(x)
        return path   
    
class ConnectedComp: 
    
    def __init__(self, G):
        self.__marked = [False for i in range(G.V())]
        self.__id = [-1 for i in range(G.V())]
        self.__count = 0
        for i in range(G.V()):
            if not self.__marked[i]:
                self.__dfs(G, i)
                self.__count += 1
                
    def __dfs(self,G, v):
        self.__marked[v] = True
        self.__id[v] = self.__count
        for w in G.adj(v):
            if not self.__marked[w]:
                self.__dfs(G, w)
                
    def connected(self, v, w):
        return self.__id[v] == self.__id[w]
    
    def id(self, v):
        return self.__id[v]
    
    def count(self):
        return self.__count
    
    
"""
Below two cases have not been verified yet
"""    
class Acyclic:
    
    def __init__(self, G):
        self.__hasCycle = False
        self.__marked = [False for i in range(G.V())]
        for i in range(G.V()):
            if not self.__marked[i]:
                self.__dfs(G, i, i)
                
    def __dfs(self, G, v, s):
        self.__marked[v] = True
        for w in G.adj(v):
            if not self.__marked[w]:
                self.__dfs(G, w, v)
            else:
                if w != s:
                    self.__hasCycle = True
                    
    def hasCycle(self):
        return self.__hasCycle
    
class Bipartite:
    
    def __init__(self, G):
        self.__marked = [False for i in range(G.V())]
        self.__color = [False for i in range(G.V())]
        self.__isBipart = True
        for i in range(G.V()):
            if not self.__marked[i]:
                self.__dfs(G, i)
                
    def __dfs(self,G,v):
        self.__marked[v] = True
        for w in G.adj(v):
            if not self.__marked[w]:
                self.__color[w] = not self.__color[v]
                self.__dfs(G, w)
            else:
                if self.__color[w] == self.__color[v]:
                    self.__isBipart = False
                    
    def isBipart(self):
        return self.__isBipart
        
    
import unittest

class TestUndirectGraph(unittest.TestCase):
    
    @classmethod
    def setUpClass(self):
        self.g = UndirectGraph(13)  
        edges = [(0,5),(4,3),(0,1),(9,12),(6,4),(5,4),
                 (0,2),(11,12),(9,10),(0,6),(7,8),(9,11),(5,3)]
        for e in edges:
            self.g.addEdge(e[0], e[1])
    
    def test_undirect_g(self):
        print self.g.toString()
        self.assertEqual(self.g.degree(0), 4)
        self.assertEqual(self.g.degree(7), 1)
        
    def test_dfs(self):
        dfs = DepthFirstSearch(self.g, 3)
        self.assertEqual(dfs.count(), 7)
        self.assertTrue(dfs.marked(0))
        self.assertTrue(dfs.marked(1))
        self.assertTrue(dfs.marked(2))
        self.assertTrue(dfs.marked(4))
        self.assertTrue(dfs.marked(5))
        self.assertTrue(dfs.marked(6))
        self.assertFalse(dfs.marked(7))
        self.assertFalse(dfs.marked(8))
        self.assertFalse(dfs.marked(9))
        self.assertFalse(dfs.marked(10))
        self.assertFalse(dfs.marked(11))
        self.assertFalse(dfs.marked(12))
        
    def test_dfs_path(self):
        dfs_p = DepthFirstPath(self.g, 3)
        self.assertEqual(dfs_p.PathTo(0), [0,6,4,3])
        self.assertEqual(dfs_p.PathTo(5), [5,0,6,4,3])
        self.assertEqual(dfs_p.PathTo(1), [1,0,6,4,3])
        self.assertIsNone(dfs_p.PathTo(9))
        self.assertIsNone(dfs_p.PathTo(7))
        
     
    def test_bfs_path(self):
        bfs_p = BreadthFirstPath(self.g, 3)
        self.assertEqual(bfs_p.PathTo(0), [0,5,3]) 
        self.assertEqual(bfs_p.PathTo(4), [4,3])
        self.assertEqual(bfs_p.PathTo(1), [1,0,5,3])
        self.assertIsNone(bfs_p.PathTo(8)) 
        self.assertIsNone(bfs_p.PathTo(12))  
        
    def test_cc(self):
        cc = ConnectedComp(self.g)
        self.assertEqual(cc.count(), 3)
        self.assertTrue(cc.connected(0, 3))
        self.assertTrue(cc.connected(7,8))
        self.assertTrue(cc.connected(2, 5))
        self.assertTrue(cc.connected(9, 12))
        self.assertFalse(cc.connected(6, 10))
        self.assertFalse(cc.connected(1, 8))
        self.assertEqual(cc.id(3),0)
        self.assertEqual(cc.id(7),1)
        self.assertEqual(cc.id(12),2)
         
if __name__ == '__main__':
    unittest.main(exit=False)             
               
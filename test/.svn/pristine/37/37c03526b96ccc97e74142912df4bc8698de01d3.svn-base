#!/user/bin/env python

class TwoThreeST:
    
    class Node:
        def __init__(self, key, val, node1=None, node2=None):
            self.key = [key, None]
            self.val = [val, None]            
            self.child = [None] * 3
            self.parent = None  
            if node1:
                self.child[0] = node1
                node1.parent = self
            if node2:
                self.child[1] = node2
                node2.parent = self                                
            
        def isLeaf(self):
            return self.child[0] is None  
        
        def is2Node(self):
            return self.key[1] is None
        
        def whichChild(self):
            if not self.parent:
                return None
            return self.parent.child.index(self)
           
    def __init__(self):
        self.root = None
    
    def size(self):
        return self.__size(self.root)
    
    def __size(self, x):
        if not x:
            return 0
        else:
            return x.N
    
    def put(self, key, val):
        if not self.root:
            self.root = self.Node(key, val)
        else:
            self.__put(self.root, key, val)
    
    def __put(self, x, key, val):
        if  x.isLeaf():
            if x.is2Node():
                if x.key[0] == key:
                    x.val[0] = val
                    return
            else:
                if x.key[0] == key:
                    x.val[0] = val
                    return
                elif x.key[1] == key:
                    x.val[1] = val 
                    return               
            self.__insert(x, key, val, None, None)
        else:
            if x.is2Node():
                if key < x.key[0]:
                    self.__put(x.child[0], key, val)
                elif key > x.key[0]:
                    self.__put(x.child[1], key, val)
                else:
                    x.val[0] = val
            else:
                if key < x.key[0]:
                    self.__put(x.child[0], key, val)
                elif key == x.key[0]:
                    x.val[0] = val
                elif key > x.key[0] and key < x.key[1]:
                    self.__put(x.child[1], key, val)
                elif key == x.key[1]:
                    x.val[1] = val
                else:
                    self.__put(x.child[2], key, val)
                    
    def __insert(self, x, key, val, node1, node2):
        if x.is2Node():
            self.__insert2Node(x, key, val, node1, node2)
        else:
            """
            p = [x.parent, key, val, node1. node2]
            """
            p = self.__insert3Node(x, key, val, node1, node2)
            if not p[0]:
                """
                it must be root's parent
                """ 
                self.root = self.Node(p[1], p[2], p[3], p[4])
            else:
                self.__insert(p[0], p[1], p[2], p[3], p[4])
    
    def __insert2Node(self, x, key, val, node1, node2): 
        if key < x.key[0]:
            x.key[0], x.key[1] = key, x.key[0]
            x.val[0], x.val[1] = val, x.val[0]
            x.child[0] = node1
            x.child[1], x.child[2] = node2, x.child[1]
        else:
            x.key[1] = key
            x.val[1] = val
            x.child[1] = node1
            x.child[2] = node2
        if node1:
            node1.parent = x
        if node2:
            node2.parent = x
        
    def __insert3Node(self, x, key, val, node1, node2): 
        if key < x.key[0]:
            """
              b c
            /  \  \
            m1 m2 m3
            
            insert a < b:
                b
               /  \ 
              a    c
             / \   / \
             n1 n2 m2 m3
            """
            newNode1 = self.Node(key, val, node1, node2)
            newNode2 = self.Node(x.key[1], x.val[1], x.child[1], x.child[2])
            return x.parent, x.key[0], x.val[0], newNode1, newNode2
        elif key > x.key[0] and key < x.key[1]:
            """
            insert b < a < c:
                 a
                /  \
               b   c
              / \  / \
             m1 n1 n2 m3
            """
            newNode1 = self.Node(x.key[0], x.val[0], x.child[0], node1)
            newNode2 = self.Node(x.key[1], x.val[1], node2, x.child[2])
            return x.parent, key, val, newNode1, newNode2
        else:
            """
            insert a > c:
                 c
                /  \
               b   a
              / \  / \
             m1 m2 n1 n2 
            """
            newNode1 = self.Node(x.key[0], x.val[0], x.child[0], x.child[1])
            newNode2 = self.Node(key, val, node1, node2)
            return x.parent, x.key[1], x.val[1], newNode1, newNode2
    
    def get(self, key):
        x,t = self.__get(self.root, key)
        if not x:
            return None
        else:
            return x.val[t]
    
    def __get(self, x, key):
        if not x:
            return None,None
        if x.is2Node():
            if key < x.key[0]:
                return self.__get(x.child[0], key)
            elif key > x.key[0]:
                return self.__get(x.child[1], key)
            else:
                return x,0
        else:
            if key < x.key[0]:
                return self.__get(x.child[0], key)
            elif key == x.key[0]:
                return x,0
            elif key > x.key[0] and key < x.key[1]:
                return self.__get(x.child[1], key)
            elif key == x.key[1]:
                return x,1
            else:
                return self.__get(x.child[2], key)
        
    def delete(self, key):
        x,t = self.__get(self.root, key)
        if not x:
            return
        else:
            self.__delete(x, t)
            
    def __delete(self, x, t):
        if not x.isLeaf():
            leaf = self.__min(x.child[t+1])
            x.key[t],leaf.key[0] = leaf.key[0],x.key[t]
            x.val[t],leaf.val[0] = leaf.val[0],x.val[t]
            t = 0
        else:
            leaf = x
            
        if not leaf.is2Node():
            """
            If leaf node is 3 node, then just delete and return
            """
            leaf.key[t],leaf.key[1] = leaf.key[1],leaf.key[t]
            leaf.val[t],leaf.val[1] = leaf.val[1],leaf.val[t]
            leaf.key[1] = None
            leaf.val[1] = None            
        else:
            self.__deleteMerge(leaf, leaf.whichChild())           
    
       
                             
    def __deleteMerge(self, x, t): 
        if not x.parent:
            """
            This is root
            """
            self.root = x.child[0]
            if self.root:
                self.root.parent = None
            return 
        #print x.key ,x.parent.key
        
        if not x.parent.is2Node():
            brotherA = x.parent.child[(t+1)%3]
            brotherB = x.parent.child[(t+2)%3]
            if t == 0:
                self.__deleteMerge3NodeT0(x, brotherA, brotherB)
            elif t == 1:
                self.__deleteMerge3NodeT1(x, brotherA, brotherB)
            else:
                self.__deleteMerge3NodeT2(x, brotherA, brotherB)
        else:
            brotherA = x.parent.child[(t+1)%2]
            if t == 0:
                self.__deleteMerge2NodeT0(x, brotherA)
            else:
                self.__deleteMerge2NodeT1(x, brotherA)
    
    
    def __deleteMerge2NodeT1(self, x, brotherA):
        if not brotherA.is2Node():
            """
               a
              /  \
             b c  x
             / \ \  \
            t1 t2 t3  t4
                 c
                / \
               b   a
              /\   / \ 
             t1 t2 t3 t4                  
               
            """    
            x.key[0] = x.parent.key[0]
            x.val[0] = x.parent.val[0]
            x.child[1] = x.child[0]
            x.child[0] = brotherA.child[2]
            if brotherA.child[2]:
                brotherA.child[2].parent = x
            x.parent.key[0] = brotherA.key[1]
            x.parent.val[0] = brotherA.val[1]
            brotherA.key[1] = None  
            brotherA.val[1] = None
            brotherA.child[2] = None
        else:
            """
               a
               / \ 
              b  x  
            / \    \
            t1 t2   t3
            
                x  <- recursive
                /
               b a
              / \ \
             t1 t2 t3    
            """  
            brotherA.key[1] = x.parent.key[0]
            brotherA.val[1] = x.parent.val[0]
            brotherA.child[2] = x.child[0]
            if x.child[0]:
                x.child[0].parent = brotherA
            x.parent.child[1] = None
            self.__deleteMerge(x.parent, x.parent.whichChild())  
              
                
    def __deleteMerge2NodeT0(self, x, brotherA):       
        if not brotherA.is2Node():
            """
                a
               / \
              x   b c
              /   / \ \              
             t1  t2 t3 t4
                 b
                /  \
               a   c
              / \  / \
             t1 t2 t3 t4 
            """
            x.key[0] = x.parent.key[0]
            x.val[0] = x.parent.val[0]
            x.child[1] = brotherA.child[0]
            if brotherA.child[0]:
                brotherA.child[0].parent = x
            x.parent.key[0] = brotherA.key[0]
            x.parent.val[0] = brotherA.val[0]
            brotherA.key[0] = brotherA.key[1]
            brotherA.val[0] = brotherA.val[1]
            brotherA.key[1] = None
            brotherA.val[1] = None
            brotherA.child[0] = brotherA.child[1]
            brotherA.child[1] = brotherA.child[2]
            brotherA.child[2] = None
        else:
            """
                 a
                / \
               x   b
              /   / \               
             t1   t2 t3
                 x  <- recursive
                /
              a b
              / \ \
             t1 t2 t3  
            """
            x.key[0] = x.parent.key[0]
            x.val[0] = x.parent.val[0]
            x.key[1] = brotherA.key[0]
            x.val[1] = brotherA.val[0]
            x.child[1] = brotherA.child[0]
            x.child[2] = brotherA.child[1]
            if brotherA.child[0]:
                brotherA.child[0].parent = x
            if brotherA.child[1]:
                brotherA.child[1].parent = x 
            x.parent.child[1] = None
            self.__deleteMerge(x.parent, x.parent.whichChild())
                
            
            
                
    
    def __deleteMerge3NodeT2(self, x, brotherA, brotherB):
        if not brotherB.is2Node():
            """
                a  b
               /  \  \
              c   d e  x
                 / \ \  \
                t1 t2 t3 t4
                a  e
               /  \  \
              c   d   b
                 / \  / \
                t1 t2 t3 t4
            """
            x.key[0] = x.parent.key[1]
            x.val[0] = x.parent.val[1]
            x.child[1] = x.child[0]
            x.child[0] = brotherB.child[2]
            if brotherB.child[2]:
                brotherB.child[2].parent = x
            x.parent.key[1] = brotherB.key[1]
            x.parent.val[1] = brotherB.val[1]
            brotherB.key[1] = None
            brotherB.val[1]= None
            brotherB.child[2] = None
        elif not brotherA.is2Node():
            """
                a  b
               /   \   \
              c d    e   x
             / \ \   / \  \
            t1 t2 t3 t4 t5 t6
                d   e
               /   \   \    
              c    a     b
             / \   / \    / \
             t1 t2 t3 t4  t5 t6
            """
            x.key[0] = x.parent.key[1]
            x.val[0] = x.parent.val[1]
            x.child[1] = x.child[0]
            x.child[0] = brotherB.child[1]
            if brotherB.child[1]:
                brotherB.child[1].parent = x
            x.parent.key[1] = brotherB.key[0]
            x.parent.val[1] = brotherB.val[0]
            brotherB.key[0] = x.parent.key[0]
            brotherB.val[0] = x.parent.val[0]
            brotherB.child[1] = brotherB.child[0]
            brotherB.child[0] = brotherA.child[2]
            if brotherA.child[2]:
                brotherA.child[2].parent = brotherB
            x.parent.key[0] = brotherA.key[1]
            x.parent.val[0] = brotherA.val[1]
            brotherA.key[1] = None
            brotherA.val[1] = None
            brotherA.child[2] = None
        else:
            """
                a  b
                / \ \
               c  d  x
                  /\  \ 
                 t1 t2 t3
                 
                 a 
                / \
               c  d b
                  /  \ \
                  t1 t2 t3
            """
            brotherB.key[1] = x.parent.key[1]
            brotherB.val[1] = x.parent.val[1]
            brotherB.child[2] = x.child[0]
            if x.child[0]:
                x.child[0].parent = brotherB
            x.parent.key[1] = None
            x.parent.val[1] = None
            x.parent.child[2] = None
            
            
                
     
    def __deleteMerge3NodeT1(self, x, brotherA, brotherB):
        if not brotherA.is2Node():
             """
                  a  b
                 /  \   \
                c   x   d e
                   /    / \ \
                  t1   t2 t3 t4
                   c  d
                  /  \  \
                c    b   e
                    / \  / \
                   t1 t2  t3 t4
             """  
             x.key[0] = x.parent.key[1]
             x.val[0] = x.parent.val[1]
             x.child[1] = brotherA.child[0]
             if brotherA.child[0]:
                 brotherA.child[0].parent = x
             x.parent.key[1] = brotherA.key[0]
             x.parent.val[1] = brotherA.val[0]
             brotherA.key[0] = brotherA.key[1]
             brotherA.val[0] = brotherA.val[1]
             brotherA.key[1] = None
             brotherA.val[1] = None
             brotherA.child[0] = brotherA.child[1]
             brotherA.child[1] = brotherA.child[2]
             brotherA.child[2] = None
        elif not brotherB.is2Node():
             """
                  a  b
                 /  \   \
               c d   x    e
              / \  \  \   
             t1 t2 t3 t4 
                  d  b
                 /  \   \
               c    a    e
              / \  / \
             t1 t2 t3 t4                    
             """  
             x.key[0] = x.parent.key[0]
             x.val[0] = x.parent.val[0]
             x.child[1] = x.child[0]
             x.child[0] = brotherB.child[2]
             if brotherB.child[2]:
                 brotherB.child[2].parent = x
             x.parent.key[0] = brotherB.key[1]
             x.parent.val[0] = brotherB.val[1]
             brotherB.key[1] = None
             brotherB.val[1] = None
             brotherB.child[2] = None
        else:
             """
                  a b
                 /  \  \
                c   x   d
                    /   / \
                   t1   t2 t3
                  a 
                /  \  
                c   b d 
                   / \ \
                   t1 t2 t3    
             """
             x.key[0] = x.parent.key[1]
             x.val[0] = x.parent.val[1]
             x.key[1] = brotherA.key[0]
             x.val[1] = brotherA.val[0]  
             x.child[1] = brotherA.child[0]
             if brotherA.child[0]:
                 brotherA.child[0].parent = x
             x.child[2] = brotherA.child[1]
             if brotherA.child[1]:
                 brotherA.child[1].parent = x
             x.parent.key[1] = None
             x.parent.val[1] = None
             x.parent.child[2] = None           
                                 
                   
                    
    def __deleteMerge3NodeT0(self, x, brotherA, brotherB):
        if not brotherA.is2Node():
             """
                  a  b
                 /  \   \
                x   c d  e
               /    / \ \
               t1   t2 t3 t4
                   c  b
                  /  \  \
                a    d   e
               / \  / \
             t1 t2  t3 t4
             """
             x.key[0] = x.parent.key[0]
             x.val[0] = x.parent.val[0]
             x.child[1] = brotherA.child[0]
             if brotherA.child[0]:
                 brotherA.child[0].parent = x
             x.parent.key[0] = brotherA.key[0]
             x.parent.val[0] = brotherA.val[0]
             brotherA.key[0] = brotherA.key[1]
             brotherA.val[0] = brotherA.val[1]
             brotherA.key[1] = None
             brotherA.val[1] = None
             brotherA.child[0] = brotherA.child[1]
             brotherA.child[1] = brotherA.child[2]
             brotherA.child[2] = None
        elif not brotherB.is2Node():
             """
                  a  b
                 /  \   \
                x   c    d e
               /   / \   / \ \
               t1 t2 t3 t4 t5 t6
                   c  d
                  /  \  \
                a    b   e
               / \  / \  / \  
             t1 t2 t3 t4 t5 t6
             """
             x.key[0] = x.parent.key[0]
             x.val[0] = x.parent.val[0]
             x.child[1] = brotherA.child[0]
             if brotherA.child[0]:
                 brotherA.child[0].parent = x
             x.parent.key[0] = brotherA.key[0]
             x.parent.val[0] = brotherA.val[0]
             brotherA.key[0] = x.parent.key[1]
             brotherA.val[0] = x.parent.val[1]
             brotherA.child[0] = brotherA.child[1]
             brotherA.child[1] = brotherB.child[0]
             if brotherB.child[0]:
                 brotherB.child[0].parent = brotherA
             x.parent.key[1] = brotherB.key[0]
             x.parent.val[1] = brotherB.val[0]
             brotherB.key[0] = brotherB.key[1]
             brotherB.val[0] = brotherB.val[1]
             brotherB.key[1] = None
             brotherB.val[1] = None
             brotherB.child[0] = brotherB.child[1]
             brotherB.child[1] = brotherB.child[2]
             brotherB.child[2] = None            
        else:
             """
                  a b
                 /  \  \
                x   c   d
               /   / \
              t1  t2  t3
                   b
                  / \
                a c  d
                / \ \
               t1 t2 t3    
             """
             x.key[0] = x.parent.key[0]
             x.val[0] = x.parent.val[0]
             x.key[1] = brotherA.key[0]
             x.val[1] = brotherA.val[0]
             x.child[1] = brotherA.child[0]
             if brotherA.child[0]:
                 brotherA.child[0].parent = x
             x.child[2] = brotherA.child[1]
             if brotherA.child[1]:
                 brotherA.child[1].parent = x
             x.parent.key[0] = x.parent.key[1]
             x.parent.val[0] = x.parent.val[1]
             x.parent.key[1] = None
             x.parent.val[1] = None
             x.parent.child[1] = brotherB
             x.parent.child[2] = None                           
          
            
            
    def min(self):
        if not self.root:
            return None
        x = self.__min(self.root)
        return x.key[0]
    
    def __min(self, x):
        if x.isLeaf():
            return x
        else:
            return self.__min(x.child[0])
        
    def max(self):
        if not self.root:
            return None
        x = self.__max(self.root)
        if x.is2Node():
            return x.key[0]
        else:
            return x.key[1]
    
    def __max(self, x):
        if x.isLeaf():
            return x
        else:
            if x.is2Node():
                return self.__max(x.child[1])
            else:
                return self.__max(x.child[2]) 
        
        
            
    """
    PreOrder, dump tree
    """
    def preOrder(self):
        self.__preOrder(self.root)
    
    def __preOrder(self, x):
        if not x:
            return
        if x.is2Node():
            self.__preOrder(x.child[0])
            print '2Node:',x.key[0], x.val[0]
            self.__preOrder(x.child[1])
        else:
            self.__preOrder(x.child[0])
            print '3Node key0:',x.key[0], x.val[0]
            self.__preOrder(x.child[1])
            print '3Node key1:',x.key[1], x.val[1]
            self.__preOrder(x.child[2])
           
            
import unittest

class TestTwoThreeST(unittest.TestCase):
                
    def test_generic_operation(self):
        a = [x for x in 'TWOTHREESEARCHTREEEXAMPLE']
        b = range(len(a))
        st = TwoThreeST()
        for x,y in zip(a,b):
            st.put(x,y)
        st.preOrder()
        self.assertEqual(st.get('C'), 12)
        self.assertEqual(st.get('E'), 24)
        self.assertEqual(st.get('S'), 8)
        self.assertIsNone(st.get('B'))
        self.assertIsNone(st.get('Y'))
        self.assertEqual(st.min(), 'A')
        self.assertEqual(st.max(), 'X')
        
    def test_delete(self):
        a = [x for x in 'BDEFGHOPPJDKWZUMABCDEFGHIJKLLNOPRQSTUUUVVVWXYYZZ']
        b = range(len(a))
        st = TwoThreeST()
        for x,y in zip(a,b):
            st.put(x,y)
        st.preOrder()
        self.assertEqual(st.min(), 'A')
        self.assertEqual(st.max(), 'Z')
        st.delete('A')
        st.delete('Z')
        self.assertEqual(st.min(), 'B')
        self.assertEqual(st.max(), 'Y')
        st.delete('P')
        st.delete('M')
        st.delete('D')
        self.assertIsNone(st.get('P'))
        self.assertIsNone(st.get('M'))
        self.assertIsNone(st.get('D'))
        for i in a:
            st.delete(i) 
        self.assertIsNone(st.root)
               
        
if __name__ == '__main__':
    unittest.main(exit=False)         
        
        
 
 
  
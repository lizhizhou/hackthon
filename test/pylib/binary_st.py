#!/user/bin/env python

class BinaryST:
    
    class Node:        
        def __init__(self, key, val, N):
            self.key = key
            self.val = val
            self.left = None
            self.right = None 
            self.N = N     
        
            
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
        self.root = self.__put(self.root, key, val)
    
    def __put(self, x, key, val):
        if not x:
            return self.Node(key, val, 1)                
        if key > x.key:  
            #print x.key + '->' + key          
            x.right = self.__put(x.right, key, val)
        elif key < x.key:
            #print key + '<-' + x.key
            x.left = self.__put(x.left, key, val)
        else:
            #print x.key + '=' + key
            x.val = val            
        x.N = self.__size(x.left) + self.__size(x.right) + 1             
        return x
        
    def get(self, key): 
        return self.__get(self.root, key)  
    
    def __get(self, x, key):
        if not x:
            return None        
        if key > x.key:
            return self.__get(x.right, key)
        elif key < x.key:
            return self.__get(x.left, key)
        else:
            return x.val
        
    def delete(self, key):
        self.root = self.__delete(self.root, key)
        
    def __delete(self, x, key):
        if not x:
            return None        
        if key > x.key:
            x.right = self.__delete(x.right, key)
        elif key < x.key:
            x.left = self.__delete(x.left, key)
        else:
            """
            x <- to delete x 
             \
              x.right
              /      
              y <- min(x.right) <- swap x with y, then delete x
            """ 
            if not x.right:
                return x.left
            elif not x.left:
                return x.right
            else:                             
                y = self.__min(x.right)
                """
                bug fixed, must connect right before left
                """
                y.right = self.__deleteMin(x.right) 
                y.left = x.left              
                return y      
        return x
        
            
    def min(self):
        return self.__min(self.root)
    
    def __min(self, x):
        if not x:
            return None        
        if not x.left:
            return x        
        return self.__min(x.left)
    
    """
    delete minimun node on the tree and return root.    
    """   
    def deleteMin(self):
        self.root = self.__deleteMin(self.root) 
        
    def __deleteMin(self, x): 
        if not x:
            return None  
        if not x.left:
            return x.right        
        x.left = self.__deleteMin(x.left)
        return x
    
    def max(self):
        return self.__max(self.root)
    
    def __max(self, x):
        if not x:
            return None
        if not x.right:
            return x
        return self.__max(x.right)
    
    """
    delete maximum node on the tree and return root
    """
    def deleteMax(self):
        self.root = self.__deleteMax(self.root)
        
    def __deleteMax(self, x):
        if not x:
            return None
        if not x.right:
            return x.left
        x.right = self.__deleteMax(x.right)
        return x
    
    """
    return the largest key smaller than this key.
    """
    def floor(self, key):
        x = self.__floor(self.root, key)
        if not x:
            return None
        else:
            return x.key
        
    def __floor(self, x, key):
        if not x:
            return None
        if key == x.key:
            return x
        elif key < x.key:
            return self.__floor(x.left, key)
        else:
            t = self.__floor(x.right, key)
            if not t:
                return x
            else:
                return t
    """
    return the smallest key larger than this key    
    """
    def ceiling(self, key):
        x = self.__ceiling(self.root, key)
        if not x:
            return None
        else:
            return x.key
    
    def __ceiling(self, x, key):
        if not x:
            return None
        if key == x.key:
            return x
        elif key < x.key:
            t = self.__ceiling(x.left ,key)
            if not t:
                return x
            else:
                return t
        else:
            return self.__ceiling(x.right, key)
    
    """
    return the K th node on the tree (start from 0,1,2,3...)
    """
    def select(self, k):
        return self.__select(self.root, k).key
    
    def __select(self, x, k):
        if not x:
            return None
        t = self.__size(x.left)
        if t > k:
            return self.__select(x.left, k)
        elif t < k:
            return self.__select(x.right, k-t-1)
        else:
            return x
     
    """
    return rank for this key on the tree (start from 0,1,2,3..) 
    """   
    def rank(self, key):
        return self.__rank(self.root, key)
     
    def __rank(self, x, key):
        if not x:
            return 0
        if key > x.key:
            return 1 + self.__size(x.left) + self.__rank(x.right, key)
        elif key < x.key:
            return self.__rank(x.left, key)
        else:
            return self.__size(x.left)  
         
    """
    return preorder iteration of this tree
    """
    def keys(self):
        q = []
        self.__keys(self.root, self.min().key, self.max().key, q)
        return q
    
    def __keys(self,x ,lo, hi, q):
        if not x:
            return
        if lo < x.key:
            self.__keys(x.left, lo, hi, q)
        if lo <= x.key and hi >= x.key:
            q.append(x.key)
        if hi > x.key:
            self.__keys(x.right, lo, hi, q)
            
    """
    PreOrder, inOrder, PostOrder dump tree
    """
    def preOrder(self):
        self.__preOrder(self.root)
    
    def __preOrder(self, x):
        if not x:
            return
        self.__preOrder(x.left)
        print x.key, x.val
        self.__preOrder(x.right)
        
    def inOrder(self):
        self.__inOrder(self.root)
        
    def __inOrder(self, x):
        if not x:
            return
        print x.key, x.val
        self.__inOrder(x.left)
        self.__inOrder(x.right)
        
    def postOrder(self):
        self.__postOrder(self.root)
        
    def __postOrder(self, x):
        if not x:
            return
        self.__postOrder(x.left)
        self.__postOrder(x.right)
        print x.key, x.val
        
        
import unittest
      
class TestBinarySearchTree(unittest.TestCase):
    
    
    def test_generic_opration(self):
        a = [x for x in 'BINARYSEARCHTREEEXAMPLE']
        b = range(len(a))          
        bst = BinaryST()
        for x,y in zip(a,b):
            bst.put(x,y)
        #bst.preOrder()
        #bst.inOrder()        
        self.assertEqual(''.join(bst.keys()), 'ABCEHILMNPRSTXY')
        accept = [0,1,2,18,13,5,6,22,18,13,10,11,12,13,22,22,22,17,18,19,20,21,22]
        for x,y in zip(a,accept):
            self.assertEqual(bst.get(x),y) 
        bst.delete('A')
        #bst.preOrder()
        #bst.inOrder()
        self.assertEqual(''.join(bst.keys()), 'BCEHILMNPRSTXY')                
        bst.delete('Y')
        #bst.preOrder()
        #bst.inOrder()
        self.assertEqual(''.join(bst.keys()), 'BCEHILMNPRSTX')
        bst.delete('M')
        #bst.preOrder()
        #bst.inOrder()
        bst.delete('R')
        self.assertEqual(''.join(bst.keys()), 'BCEHILNPSTX')
        
    def test_maxmin(self):
        a = [x for x in 'BINARYSEARCHTREEEXAMPLE']
        b = range(len(a))          
        bst = BinaryST()
        for x,y in zip(a,b):
            bst.put(x,y) 
        self.assertEqual(bst.max().key, 'Y')
        self.assertEqual(bst.min().key, 'A')
        bst.deleteMax()
        self.assertEqual(bst.max().key, 'X')
        bst.deleteMin()
        self.assertEqual(bst.min().key, 'B')
        bst.deleteMax()
        bst.deleteMax()
        bst.deleteMin()
        bst.deleteMin()
        self.assertEqual(bst.max().key, 'S')
        self.assertEqual(bst.min().key, 'E')
        
    def test_ranking(self):
        a = [x for x in 'BINARYSEARCHTREEEXAMPLE']
        b = range(len(a))          
        bst = BinaryST()
        for x,y in zip(a,b):
            bst.put(x,y) 
        self.assertEqual(bst.rank('A'), 0)
        self.assertEqual(bst.rank('Y'), len(bst.keys())-1)
        #print bst.rank('F')
        #print bst.rank('Z')
        self.assertEqual(bst.rank('F'), bst.rank('E')+1)
        self.assertEqual(bst.rank('Z'), bst.rank('Y')+1)
        #print bst.rank('L')
        #print bst.rank('M')
        #print bst.rank('N')
        rL = bst.rank('L')
        rM = bst.rank('M')
        rN = bst.rank('N')
        self.assertEqual(bst.select(rL), 'L')
        self.assertEqual(bst.select(rM), 'M')
        self.assertEqual(bst.select(rN), 'N')
        bst.delete('L')
        bst.delete('M')
        bst.delete('N')   
        self.assertEqual(bst.select(rL), 'P')
        self.assertEqual(bst.select(rM), 'R')
        self.assertEqual(bst.select(rN), 'S')
                   
    def test_floor_ceiling(self):
        a = [x for x in 'BINARYSEARCHTREEEXAMPLE']
        b = range(len(a))          
        bst = BinaryST()
        for x,y in zip(a,b):
            bst.put(x,y) 
        self.assertEqual(bst.floor('A'), 'A')
        self.assertEqual(bst.floor('M'), 'M')
        self.assertEqual(bst.floor('Y'), 'Y')
        self.assertEqual(bst.ceiling('A'), 'A')
        self.assertEqual(bst.ceiling('N'), 'N')
        self.assertEqual(bst.ceiling('X'), 'X')
        self.assertEqual(bst.floor('G'), 'E')
        self.assertEqual(bst.ceiling('G'), 'H')
        self.assertEqual(bst.floor('Q'), 'P')
        self.assertEqual(bst.ceiling('Q'), 'R')                  
        
             
if __name__ == '__main__':
    unittest.main(exit=False)         
        
        
        
        
        
        
        
        
    
    
    
#!/user/bin/env python

class BST:
    
    class Node:        
        def __init__(self, key, val):
            self.key = key
            self.val = []
            self.val.append(val)
            self.left = None
            self.right = None 
            self.count = 1      
        
            
    def __init__(self):
        self.root = None
    
    def put(self, key, val):
        self.root = self.__put__(self.root, key, val)
    
    def __put__(self, x, key, val):
        if not x:
            return self.Node(key, val)        
        if key > x.key:
            x.right = self.__put__(x.right, key, val)
        elif key < x.key:
            x.left = self.__put__(x.left, key, val)
        else:
            x.val.append(val)
            x.count += 1       
        return x
        
    def get(self, key): 
        return self.__get__(self.root, key)  
    
    def __get__(self, x, key):
        if not x:
            return None        
        if key > x.key:
            return self.__get__(x.right, key)
        elif key < x.key:
            return self.__get__(x.left, key)
        else:
            return x.val
        
    def delete(self, key):
        self.root = self.__delete__(self.root, key)
        
    def __delete__(self, x, key):
        if not x:
            return None        
        if key > x.key:
            x.right = self.__delete__(x.right, key)
        elif key < x.key:
            x.left = self.__delete__(x.left, key)
        else:
            """
            x <- to delete x 
             \
              x.left
              /      
              y <- min(x.left) <- swap x with y, then delete x
            """
            if not x.right:
                return x.left
            elif not x.left:
                return x.right
            else:
                swap = x;
                x = min(x.right)
                x.left = swap.left
                x.right = self.__deleteMin__(swap.right)
                
        return x
        
            
    def min(self):
        return self.__min__(self.root)
    
    def __min__(self, x):
        if not x:
            return None        
        if not x.left:
            return x        
        return self.__min__(x.left)
        
    def __deleteMin__(self, x):   
        if not x.left:
            return x.right        
        x.left = self.__deleteMin__(x.left)
        return x
    
    def max(self):
        return self.__max__(self.root)
    
    def __max__(self, x):
        if not x:
            return None
        if not x.right:
            return x
        return self.__max__(x.right)

    def preOrder(self):
        p = []
        self.__preOrder__(self.root, p)
        return p
    
    def __preOrder__(self, x, p):
        if not x:
            return
        self.__preOrder__(x.left, p)
        p.append(x)
        self.__preOrder__(x.right, p)
        return        
        
        
        
        
        
          
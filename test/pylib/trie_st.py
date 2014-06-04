#!/user/bin/env python

class TrieST:
    R = 256
    
    class Node:
        R = 256
        
        def __init__(self):
            self.value = None
            self.next = [None for i in range(self.R)]
            
    def __init__(self):
        self.root = None
        
    def get(self, key):
        x = self.__get(self.root, key, 0)
        if not x:
            return None
        else:
            return x.value
        
    def __get(self, x, key, d):
        if not x:
            return None
        if d == len(key):
            return x
        return self.__get(x.next[ord(key[d])], key, d+1)
    
    def put(self, key, val):
        self.root = self.__put(self.root, key, val, 0)
        
    def __put(self, x, key, val, d):
        if not x:
            x = self.Node()
        if d == len(key):
            x.value = val
            return x
        c = ord(key[d])
        x.next[c] = self.__put(x.next[c], key, val, d+1)
        return x
    
    def keys(self):
        return self.keysWithPrefix('')
    
    
    def keysWithPrefix(self, pre):
        q = []
        x = self.__get(self.root, pre, 0)
        self.__collect(x, pre, q)
        return q
        
    def __collect(self, x, pre, q):
        if not x:
            return
        if x.value is not None:
            q.append(pre)
        for c in range(self.R):
            self.__collect(x.next[c], pre+chr(c), q) 
            
    def delete(self, key):
        self.root = self.__delete(self.root, key, 0)
        
    def __delete(self, x, key, d):
        if not x:
            return None
        if d == len(key):
            x.value = None
        else:
            c = ord(key[d])
            x.next[c] = self.__delete(x.next[c], key, d+1)
        if x.value is not None:
            return x
        for c in range(self.R):
            if x.next[c] is not None:
                return x
        else:
            return None

"""
TODO: Ternary search trie
"""       
    
import unittest

class TestTrieST(unittest.TestCase):
    
    def test_trieST(self):
        a = ['by','sea','sells','she','shells','shore','the']
        b = range(len(a))
        st = TrieST()
        for x,y in zip(a,b):
            st.put(x,y)
        print st.keys() 
        print st.get('sea')
        print st.get('shore')
        print st.get('shells') 
        print st.get('shel')
        print st.get('shell')  
        st.delete('the')
        st.delete('noone')
        st.delete('shell')
        print st.keys()
        st.delete('shells')
        print st.keys()
        
    
    
    
if __name__ == '__main__':
    unittest.main(exit=False) 
    
    
    
    
    
    
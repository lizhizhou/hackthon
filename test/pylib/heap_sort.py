#!/user/bin/env python

class MaxPriorityQueue:
    
    def __init__(self):
        self.__pq = [-1] #the first element is unused
    
    def isEmpty(self):
        return len(self.__pq) == 1
    
    def size(self):
        return len(self.__pq) - 1
    
    def insert(self, v):
        self.__pq.append(v)
        self.__swim(self.size())
        
    def __swim(self, n):
        while n > 1 and self.__pq[n/2] < self.__pq[n]:
            self.__pq[n/2],self.__pq[n] = self.__pq[n],self.__pq[n/2]
            n = n/2
    
    def __sink(self, n):
        N = self.size()
        while n*2 <= N:
            j = n*2
            if j < N and self.__pq[j] < self.__pq[j+1]:
                j += 1
            if self.__pq[n] >= self.__pq[j]:
                break
            self.__pq[n],self.__pq[j] = self.__pq[j],self.__pq[n]
            n = j
    
    def deleteMax(self):
        max = self.__pq[1]        
        self.__pq[1],self.__pq[-1] = self.__pq[-1],self.__pq[1]
        self.__pq.pop()
        self.__sink(1)       
        return max
    
class MinPriorityQueue:
    
    def __init__(self):
        self.__pq = [-1] #the first element is unused
    
    def isEmpty(self):
        return len(self.__pq) == 1
    
    def size(self):
        return len(self.__pq) - 1
    
    def insert(self, v):
        self.__pq.append(v)
        self.__swim(self.size())
        
    def __swim(self, n):
        while n > 1 and self.__pq[n/2] > self.__pq[n]:
            self.__pq[n/2],self.__pq[n] = self.__pq[n],self.__pq[n/2]
            n = n/2
    
    def __sink(self, n):
        N = self.size()
        while n*2 <= N:
            j = n*2
            if j < N and self.__pq[j] > self.__pq[j+1]:
                j += 1
            if self.__pq[n] <= self.__pq[j]:
                break
            self.__pq[n],self.__pq[j] = self.__pq[j],self.__pq[n]
            n = j
    
    def deleteMin(self):
        min = self.__pq[1]        
        self.__pq[1],self.__pq[-1] = self.__pq[-1],self.__pq[1]
        self.__pq.pop()
        self.__sink(1)       
        return min

"""
IndexMinPQ is not verified.
"""
class IndexMinPQ:
    
    def __init__(self, m):
        self.__pq = [-1] #the first element is unused
        self.__pos = [0 for i in range(m)]
    
    def isEmpty(self):
        return len(self.__pq) == 1
    
    def size(self):
        return len(self.__pq) - 1
    
    def contains(self, index):
        return self.__pos[index] != 0
    
    def change(self, index, w):
        n = self.__pos[index]
        v = self.__pq[n][1]
        self.__pq[n] = (index,w)
        if v < w:
            self.__sink(n)
        elif v > w:
            self.__swim(n)
        else:
            pass    
        
    
    def insert(self, index, v):
        self.__pq.append((index,v))
        self.__pos[index] = self.size()
        self.__swim(self.size())
        
    def __swim(self, n):
        while n > 1 and self.__pq[n/2][1] > self.__pq[n][1]:
            self.__pos[self.__pq[n/2][0]] = n
            self.__pos[self.__pq[n][0]] = n/2
            self.__pq[n/2],self.__pq[n] = self.__pq[n],self.__pq[n/2]
            n = n/2
    
    def __sink(self, n):
        N = self.size()
        while n*2 <= N:
            j = n*2
            if j < N and self.__pq[j][1] > self.__pq[j+1][1]:
                j += 1
            if self.__pq[n][1] <= self.__pq[j][1]:
                break
            self.__pos[self.__pq[j][0]] = n
            self.__pos[self.__pq[n][0]] = j
            self.__pq[n],self.__pq[j] = self.__pq[j],self.__pq[n]
            n = j
    
    def deleteMin(self):
        min = self.__pq[1]  
        self.__pos[self.__pq[1][0]] = self.size()  
        self.__pos[self.__pq[-1][0]] = 1    
        self.__pq[1],self.__pq[-1] = self.__pq[-1],self.__pq[1]
        self.__pq.pop()
        self.__sink(1)       
        return min
      

"""
remember this heapsort don't use the a[0]
"""
def heapSort(a):
    
    def sink(a, k, N):
        while k*2 <= N:
            j = k*2
            if j < N and a[j] < a[j+1]:
                j += 1
            if a[k] >= a[j]:
                break
            a[k],a[j] = a[j],a[k]
            k = j
    
    N = len(a)-1       
    for i in range(N/2, 0, -1):
        sink(a, i, N)
        
    while N > 1:
        a[1],a[N] = a[N],a[1]
        N -= 1
        sink(a,1,N)
    
    
import unittest

class TestHeapSort(unittest.TestCase):
    
    def test_index_minPQ(self):
        a = 'INDEXMINPQ'
        b = range(len(a))
        mpq = IndexMinPQ(len(a))
        for x,y in zip(a,b):
            mpq.insert(y, x)
        mpq.change(4, 'A')
        mpq.change(5, 'B')
        mpq.change(9, 'C')
        c = ''.join([mpq.deleteMin()[1] for i in range(len(a))])
        self.assertEqual(c, 'ABCDEIINNP')            
    
    def test_minPQ(self):
        mpq = MinPriorityQueue()
        for i in 'MINPRIORITYQUEUE':
            mpq.insert(i)
        b = ''.join([mpq.deleteMin() for i in range(mpq.size())])
        self.assertEqual(b, 'EEIIIMNOPQRRTUUY')
    
    def test_maxPQ(self):
        mpq = MaxPriorityQueue()
        for i in 'MAXPRIORITYQUEUE':
            mpq.insert(i)            
        b = ''.join([mpq.deleteMax() for i in range(mpq.size())])
        self.assertEqual(b, 'YXUUTRRQPOMIIEEA')  
        
    def test_heapSort(self):
        a = [''] # not used a[0]
        a.extend([i for i in 'MAXPRIORITYQUEUE'])        
        heapSort(a)
        b = ''.join(a[1:])
        self.assertEqual(b, 'AEEIIMOPQRRTUUXY')
        
if __name__ == '__main__':
    unittest.main(exit=False)   
    
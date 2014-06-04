#!/user/bin/env python

class LSDIndexSort:
    
    def __init__(self, a, W):
        N = len(a)
        R = 256
        aux = [None for i in range(N)]
        
        for d in reversed(range(W)):
            count = [0 for i in range(R+1)]
            for i in range(N):
                count[ord(a[i][d])+1] += 1
            for i in range(R):
                count[i+1] += count[i]
            for i in range(N):
                aux[count[ord(a[i][d])]] = a[i]
                count[ord(a[i][d])] += 1
            for i in range(N):
                a[i] = aux[i]


class MSDIndexSort:
    
    def charAt(self, s, d):
        if d < len(s):
            return ord(s[d])
        else:
            return -1
            
    
    def __init__(self, a):
        N = len(a)
        self.__aux = [None for i in range(N)]
        self.__sort(a, 0, N, 0)
        
    def __sort(self, a, lo, hi, d):
        R = 256
        count = [0 for i in range(R+2)]
        for i in range(lo, hi):            
            count[self.charAt(a[i], d)+2] += 1
        for i in range(R+1):
            count[i+1] += count[i]
        for i in range(lo,hi):
            self.__aux[count[self.charAt(a[i], d)+1]] = a[i]
            count[self.charAt(a[i], d)+1] += 1
        for i in range(lo,hi):
            a[i] = self.__aux[i-lo]
            
        for i in range(R+1):
            if count[i] < count[i+1]:
                self.__sort(a, lo+count[i], lo+count[i+1], d+1)
                
            
        
        
        
          
    
import unittest

class TestSringSort(unittest.TestCase):
    
    def test_MSD(self):
        a = ['she','sells','seashells','by','the','sea',
             'shore','the','shells','she','sells','are',
             'surely','seashell']
        msd = MSDIndexSort(a)
        print a
    
    def test_LSD(self):
        a = ['4PGC938','2IYE230','3CI0720','1ICK750',
             '10HV845','4JZY524','1ICK750','3CI0720',
             '10HV845','10HV845','2RLA629','3ATW723']
        lsd = LSDIndexSort(a, 6)
        print a    
            
            
if __name__ == '__main__':
    unittest.main(exit=False)     
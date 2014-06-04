#!/user/bin/env python

import sys

inputf = sys.argv[1]
outputf = sys.argv[2]

def revese_num(x):    
    a = [x%10]
    x = x/10
    while x != 0:
        a.append(x%10)
        x = x/10
    else:       
        y = 0 
        for i in a:
            y = y*10 + i
        else:
            return y
    

rsum = []
with open(inputf, 'r') as f:
    cases = int(f.readline().strip())
    #print cases
    for i in range(cases):
        num = f.readline().strip().split(' ')
        #print num
        rnum = []
        for n in num:            
            rnum.append(revese_num(int(n)))
        print rnum
        sum = rnum[0] + rnum[1]
        rsum.append(revese_num(sum))
        
        
print rsum            
with open(outputf, 'w') as f:
    for i in rsum:
        f.write('%s\n'%i)    
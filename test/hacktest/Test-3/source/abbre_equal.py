#!/user/bin/env python

import sys

inputf = sys.argv[1]
outputf = sys.argv[2]

def test_same(fL,fW,sL,sW):
    if fL != sL:
        return False
    else:
        for x,y in zip(fW,sW):
            if x[0] != y[0]:
                return False
        else:
            return True
         

result = []
with open(inputf, 'r') as f:
    cases = int(f.readline().strip())
    for i in range(cases):
        firstL = int(f.readline().strip())
        firstW = f.readline().strip().split(' ')
        secondL = int(f.readline().strip())
        secondW = f.readline().strip().split(' ')
        if test_same(firstL,firstW,secondL,secondW):
            result.append('SAME')
        else:
            result.append('DIFFERENT')
            
print result
with open(outputf, 'w') as f:
    for i in result:
        f.write('%s\n'%i) 
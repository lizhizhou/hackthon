#!/user/bin/env python

import sys

inputf = sys.argv[1]
outputf = sys.argv[2]

with open(outputf, 'w') as f:
    for i in range(10):
        f.write('Nonsense output %s\n'%i) 
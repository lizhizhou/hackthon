#!/user/bin/env python

import sys

for line in sys.stdin.readlines():
    if not line:
        break
    print sum(int(x) for x in line.split(' '))
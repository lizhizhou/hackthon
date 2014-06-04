#!/user/bin/env python

d = dict()
with open('input.txt', 'r') as f:
    for line in f:
        pa = line.strip().split(' ')[18:]
        #print pa
        p = ' '.join(pa)
        if not d.has_key(p):
            d[p] = 1
        else:
            d[p] += 1
            
print d
with open('output.txt', 'w') as f:
    for key,val in d.items():
        if val > 100:
            f.write('%s %s\n'%(key, val))
        

    
#!/user/bin/env python

n = 3

aux = []

s = 1
for i in range(n/2+1):
    x = s*i
    aux.append(x)
    s = -s

rever_aux = [aux[i-1] for i in range(len(aux)-1, 0, -1)]
aux.extend(rever_aux)

print aux

prepos = 0
count = 0
sss = -1
for a in aux:
    print 'swap %s to %s'%(prepos+n/2, a+n/2) 
    sss += 1   
    if prepos > a:
        step = 2
    else:
        step = -2
    if count > n/2:
        step = -step
    
        
    t = a
    while t != -a:
        print 'swap %s to %s'%(t+n/2, t+step+n/2)
        sss +=1
        t = t+step
    else:
        prepos = t
    count += 1    

print sss   

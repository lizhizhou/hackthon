#!/user/bin/env python

import bst

d = dict()
Tree = bst.BST()
with open('input.txt', 'r') as f:
    for line in f:
        pa = line.strip().split(' ')[18:]
        #print pa
        p = ' '.join(pa)
        Tree.put(p, None)
      
p = Tree.preOrder()
for i in p:
    print 'Key:%s, count:%s'%(i.key, i.count)

Tree = bst.BST()
for i in p:
    Tree.put(i.count, i.key)

with open('output.txt', 'w') as f:  
    p = Tree.preOrder()
    for i in p:
        print 'key:%s, val:%s'%(i.key, i.val)
        f.write('key:%s, val:%s\n'%(i.key, i.val))
    
    
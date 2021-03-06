#!/user/bin/env python


case = []
with open('input.txt', 'r') as f:
    for line in f:
        case.append([int(x) for x in line.strip().split(' ')])
        
print case        
        
def hanoi(disk): 
    if len(disk) == 0:
        return 0
    fn = [disk[0]]
    gn = [disk[0]*2 - 1]        
    for i in range(1,len(disk)):
        fn.append(fn[i-1]*2 + disk[i])
        if disk[i] == 1:
            gn.append(fn[i])
        else:
            gn.append(gn[i-1] + fn[i-1]*2 + disk[i]*2)
    return gn[-1]         
    
        
for cs in case:
    print hanoi(cs)   

        
       
        
    
    
    
    
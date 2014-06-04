#!/user/bin/env python

import sys

inputf = sys.argv[1]
outputf = sys.argv[2]

def decipher(text):
    cipher = 'A B C D E F G H I J K L M N O P Q R S T U V W X Y Z'.split(' ')
    plain = 'V W X Y Z A B C D E F G H I J K L M N O P Q R S T U'.split(' ')
    for i in range(len(text)):
        if text[i] in cipher:
            text[i] = plain[cipher.index(text[i])]
            

result = []
with open(inputf, 'r') as f:
    while True:
        line = f.readline().strip()
        if line == 'ENDOFINPUT':
            break;
        if line == 'START':
            text = f.readline().strip()
            a = [x for x in text]
            decipher(a)
            result.append(''.join(a))
            if f.readline().strip() != 'END':
                break;
            
print result
with open(outputf, 'w') as f:
    for i in result:
        f.write('%s\n'%i)    
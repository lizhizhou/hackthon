#!/user/bin/env python

import sys
import os

class Validator(object):
    VALID = 0
    ERR_FORMAT = -1
    ERR_ARGS = -2
    ERR_NONEXIST = -3
    ERR_OVERSIZE = -4    
    
    def __init__(self):
        self.output = None
        self.input = None
        self.err = False
        self.errstr = None 
    
    def validate(self, argv):
        if len(argv) < 3:
            self.err = self.ERR_ARGS
            self.errstr = 'number of arg < 3'
            return False 
        
        def ext_chk(x, y):
            import re
            ext = re.findall(r'.%s$'%y,x)
            if ext is None or len(ext) == 0:
                return False
            else:
                return True
        inputf = sys.argv[1]    
        if not ext_chk(inputf, 'txt'):
            self.err = self.ERR_FORMAT
            self.errstr = 'Invalid input file %s'%inputf
            return False 
        outputf = sys.argv[2]
        if not ext_chk(outputf, 'txt'):
            self.err = self.ERR_FORMAT
            self.errstr = 'Invalid output file %s'%outputf
            return False  
        
        """
        at this point, at least we have valid file name
        """
        self.output = outputf
        self.input = inputf
        if not os.path.exists(inputf):
            self.err = self.ERR_NONEXIST
            self.errstr = 'Input file not exists %s'%inputf
            return False 
        if os.path.getsize(inputf) >= 10000L:
            self.err = self.ERR_OVERSIZE
            self.errstr = 'Input file oversize %s'%inputf
            return False 
        return True

if __name__ == '__main__':    
    vd = Validator()
    try:         
        if not vd.validate(sys.argv):
            print vd.err,vd.errstr
            if vd.output:
                with open(vd.output, 'w') as f:
                    f.write('Invalid Input\n')
        #a = 1/0
    except Exception as e:
        print str(e)
        if vd.output:
            with open(vd.output, 'w') as f:
                f.write('Invalid Input\n') 
        
    
    
      
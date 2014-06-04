#!/user/bin/env python

import threading
import subprocess        

class Command(object):
    TIMEOUT = -2
    SUCCESS = 0
    ERROR = -1    
    
    def __init__(self, cmd):
        self.cmd = cmd
        self.process = None
        self.out = ''
        self.err = 0
        
    def run(self, timeout):
        def target():
            #print "subprocess started"
            self.process = subprocess.Popen('exec '+self.cmd, shell=True,stderr=subprocess.STDOUT, stdout=subprocess.PIPE)
            self.out, self.err = self.process.communicate()
            #print "subprocess finished"
            
        thread = threading.Thread(target=target)
        thread.start()
        
        thread.join(timeout)
        if thread.is_alive():
            #print "Running Timeout, terminating process"
            self.process.terminate()            
            thread.join()
            return self.TIMEOUT
        else:
            #print self.process.returncode
            if self.process.returncode:
                return self.ERROR
            else:
                return self.SUCCESS
            
    def output(self):
        return self.out
    
    def error(self):
        return self.err


def compile(src):
    pre,ext = src.split('.')
    if ext == 'py':
        return True, 'Skip Compile Python'
    elif ext == 'tcl':
        return True, 'Skip Compile TCL'
    elif ext == 'java':
        cmd = Command('javac %s'%src)
    elif ext == 'cpp':
        cmd = Command('g++ %s -o %s'%(src,pre))
    else:
        return False, 'Unknown file'
    ret = cmd.run(20)
    if ret == Command.ERROR:
        return False, cmd.output()
    elif ret == Command.TIMEOUT:
        return False, 'Compile timeout'
    else:
        return True, 'Compile succeeed'
                
def test(src, case, out):    
    pre,ext = src.split('.')
    if ext == 'py':    
        cmd = Command('python %s %s %s'%(src,case,out))
    elif ext == 'tcl':
        cmd = Command('tclsh %s %s %s'%(src,case, out))
    elif ext == 'java':
        cmd = Command('java %s %s %s'%(pre, case,out))
    elif ext == 'cpp':
        cmd = Command('./%s %s %s'%(pre, case,out))
    else:
        return False, 'Unknown file'
    
    if os.path.exists(out):
        os.remove(out)
    ret = cmd.run(15)
    if ret == Command.ERROR:
        return False, cmd.output()
    elif ret == Command.TIMEOUT:
        return False, 'Running timeout'
    else:
        if not os.path.exists(out):
            return False, 'Output not generated'
        else:
            with open(out, 'r') as f:
                import re
                fcontent = f.read()
                check = re.findall(r'(I|i)nvalid (I|i)nput',fcontent)
                if len(check) == 0:                    
                    return False, fcontent
                else:
                    return True, fcontent                   
    
    
def testcases(sources, cases, report): 
    result = ['FAILED', 'PASSED']   
    compiled = []
    print 'Test compile:'
    report.write('='*50)
    report.write('\n')
    report.write('Test compile:\n')
    for src in sources:
        ret,err = compile(src)
        print '%s.....%s'%(src, result[ret])
        report.write('-'*50)
        report.write('\n')
        report.write('%s.....%s\n'%(src, result[ret]))
        report.write('-'*50)
        report.write('\n')
        report.write('%s\n'%err)
        if ret:
            compiled.append(src)
            
    for case in cases:
        print 'Test %s:'%case
        report.write('='*50)
        report.write('\n')
        report.write('Test %s:\n'%case)
        for src in compiled:
            ret,err = test(src, case, 'output.txt')
            print '%s.....%s'%(src, result[ret])
            report.write('-'*50)
            report.write('\n')
            report.write('%s.....%s\n'%(src, result[ret]))
            report.write('-'*50)
            report.write('\n') 
            report.write('%s\n'%err)
          

import unittest
import os

def ext_chk(x, y):
    z = x.split('.')   
    if z is None or len(z) < 2:
        return False
    else:
        return z[1] in y 
            
class HackTest(unittest.TestCase):

    def test_1(self): 
        os.chdir('Test-1')
        sources = [f for f in os.listdir('source') if ext_chk(f, ['java','cpp','py','tcl'])]     
        cases = ['../input_ascii.txt','../input_number.txt','../input_lines.txt']
        print 'Test-1:'
        print 'Sources:%s'%sources
        print 'Cases:%s'%cases
        with open('test_report.txt', 'w') as report:
            os.chdir('source')
            testcases(sources,cases,report)
        os.chdir('../..')
            
        
    def test_2(self):
        os.chdir('Test-2')
        sources = [f for f in os.listdir('source') if ext_chk(f, ['java','cpp','py','tcl'])]     
        cases = ['../input_ascii.txt','../input_number.txt','../input_lines.txt']
        print 'Test-2:'
        print 'Sources:%s'%sources
        print 'Cases:%s'%cases
        with open('test_report.txt', 'w') as report:
            os.chdir('source')
            testcases(sources,cases,report)
        os.chdir('../..')
            
    def test_3(self):
        os.chdir('Test-3')
        sources = [f for f in os.listdir('source') if ext_chk(f, ['java','cpp','py','tcl'])]     
        cases = ['../input_ascii.txt','../input_number.txt','../input_lines.txt']
        print 'Test-3:'
        print 'Sources:%s'%sources
        print 'Cases:%s'%cases
        with open('test_report.txt', 'w') as report:
            os.chdir('source')
            testcases(sources,cases,report)
        os.chdir('../..')
        
    def test_4(self):
        os.chdir('Test-4')
        sources = [f for f in os.listdir('source') if ext_chk(f, ['java','cpp','py','tcl'])]     
        cases = ['../input_ascii.txt','../input_number.txt','../input_lines.txt']
        print 'Test-4:'
        print 'Sources:%s'%sources
        print 'Cases:%s'%cases
        with open('test_report.txt', 'w') as report:
            os.chdir('source')
            testcases(sources,cases,report)
        os.chdir('../..')
        
    def test_5(self):
        os.chdir('Test-5')
        sources = [f for f in os.listdir('source') if ext_chk(f, ['java','cpp','py','tcl'])]     
        cases = ['../input_ascii.txt','../input_number.txt','../input_lines.txt']
        print 'Test-5:'
        print 'Sources:%s'%sources
        print 'Cases:%s'%cases
        with open('test_report.txt', 'w') as report:
            os.chdir('source')
            testcases(sources,cases,report)
        os.chdir('../..')
            
if __name__ == '__main__':
    unittest.main(exit=False)
    
    


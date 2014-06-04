#!/user/bin/env python

total = 0

def update(check, column, row, b):
    for i in range(1,8):
        if column+i < 8:
            if row-i >= 0:
                if not b and check[column+i][row-i] == -1:
                    check[column+i][row-i] = column
                if b and check[column+i][row-i] == column:
                    check[column+i][row-i] = -1
            if row+i < 8:
                if not b and check[column+i][row+i] == -1:
                    check[column+i][row+i] = column
                if b and check[column+i][row+i] == column:
                    check[column+i][row+i] = -1
            if not b and check[column+i][row] == -1:
                check[column+i][row] = column
            if b and check[column+i][row] == column:
                check[column+i][row] = -1            
    #print check


def placeQueen(column, row, check):
    if column > 7:
        global total
        total += 1
        print row
        return
    for i in range(8):
        if check[column][i] == -1:
            row[column] = i
            update(check, column, i, False)
            placeQueen(column+1, row, check)
            update(check, column, i, True)




row = [-1 for i in range(8)]
check = [[-1 for i in range(8)] for i in range(8)]
  
placeQueen(0, row, check) 
print 'total:',total   



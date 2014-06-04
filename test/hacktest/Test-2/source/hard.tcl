#!/bin/sh
# the next line restarts using tclsh
#exec tclsh "$0" ${1+"$@"}
#  print.tcl
#  hack
#
#  Created by yunzshi on 14-3-4.
#  Copyright (c) 2014Äê yunzshi. All rights reserved.
proc transfer {input} {
    set new_val [expr [scan $input %c] - 5]
    if {$new_val < 65} {
        set tmp [format %c [expr ($new_val - 65) + 91]]
    } else {
        set tmp [format %c $new_val]
    }
    return $tmp
}
set pid_tmp [open [lindex $argv 0] r]
set data [read $pid_tmp]
if {[regexp {[a-z]} $data]} {
    puts "invalid input file \n all input should upercase"
    close $pid_tmp
    return
}
close $pid_tmp
set pid1 [open [lindex $argv 0] r]
set pid2 [open [lindex $argv 1] w]
set flag_s 0
set set_num 0
while {[gets $pid1 line] >=0} {
    set line [string trim $line]
    if {[string equal $line "ENDOFINPUT"]} {
        if {!$set_num} {
            puts "invalid input file\n 0 sets"
            close $pid1
            close $pid2
            return
        }
        break
    }
    if {[string equal $line "START"]} {
        set flag_s 1
        continue
    }
    if {[string equal $line "END"]} {
        if {!$flag_s} {
            puts "invalid input file\n pls use set with start flag"
            close $pid1
            close $pid2
            return
        }
        set flag_s 0
        incr set_num
        if {$set_num > 100} {
            puts "invalid input file\n exeed 100 sets"
            close $pid1
            close $pid2
            return
        }
        continue
    }
    for {set i 0} {$i < [string length $line]} {incr i} {
        set tmp [string index $line $i]
        if {[regexp {[A-Z]} $tmp]} {
            set tmp [transfer $tmp]
        }
        puts -nonewline $pid2 $tmp
        }
        puts -nonewline $pid2 "\n"
}
#    puts -nonewline $pid2 "\n"
close $pid1
close $pid2
#!/bin/sh

#  circus.tcl
#  hack
#
#  Created by yunzshi on 14-3-10.
#  Copyright (c) 2014年 yunzshi. All rights reserved.


#set a "(60,75)(65,150)"
#set a "(60,75)(a,b)"
# regexp {(\((\d+)\,(\d+)\))+} $a



set pid1 [open [lindex $argv 0] r]
set pid2 [open [lindex $argv 1] w]

set filesize [file size [lindex $argv 0]]

if {$filesize > 10240} {
    puts "Invalid input: file size big than 10k"
    puts $pid2 "Invalid input: file size big than 10k"
    close $pid2
    close $pid1
    exit
}
if {[catch {

gets $pid1 line

set str [string map {"(" "" "," " " ")" " "} $line]

set str [string trim $str]
foreach {hei wei} $str {

    if {(![regexp {\d+} $hei]) || (![regexp {\d+} $wei])} {

        puts "Invalid input: height and weight should be num between 0 and 1000"
        puts $pid2 "Invalid input: height and weight should be num between 0 and 1000"
        close $pid2
        close $pid1
        exit
    }

    lappend people "$hei $wei"
    if {$hei > 10000} {
        puts "Invalid input: height of person is big than 1000"
        puts $pid2 "Invalid input: height of person is big than 100"
        close $pid2
        close $pid1
        exit
    }

    if {$wei > 10000} {
        puts "Invalid input: weight of person is big than 1000"
        puts $pid2 "Invalid input: weight of person is big than 100"
        close $pid2
        close $pid1
        exit
    }

}

set num [llength $people]
if {$num > 100} {
    puts "Invalid input: max num of person is big than 100"
    puts $pid2 "Invalid input: max num of person is big than 100"
    close $pid2
    close $pid1
    return
}

set sort_hei [lsort -integer -index 0 $people]
set sort_wei [lsort -integer -index 1 $people]


set num [llength $sort_hei]

for {set i 0} {$i < [expr $num -1]} {incr i} {
    set p1 [lindex $sort_hei $i]
    set w1 [lindex $p1 1]
    set tmp [expr $i + 1]
    set p2 [lindex $sort_hei $tmp]
    set w2 [lindex $p2 1]


    if {$w1 >= $w2} {

        set sort_hei [lreplace $sort_hei $tmp $tmp]
    }

    set num [llength $sort_hei]
}




set num [llength $sort_wei]

for {set i 0} {$i < [expr $num -1]} {incr i} {
    set p1 [lindex $sort_wei $i]
    set h1 [lindex $p1 0]
    set tmp [expr $i + 1]
    set p2 [lindex $sort_wei $tmp]
    set h2 [lindex $p2 0]


    if {$h1 >= $h2} {

        set sort_wei [lreplace $sort_wei $tmp $tmp]
    }

    set num [llength $sort_wei]
}

set num_h [llength $sort_hei]
set num_w [llength $sort_wei]

if {$num_h >= $num_w} {
    puts -nonewline $pid2 $num_h
} else {
    puts -nonewline  $pid2 $num_w
}

close $pid1
close $pid2

}]} {
 puts "invalid input"
 puts $pid2 "invalid input"
 close $pid1
 close $pid2
}

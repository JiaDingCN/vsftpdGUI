#!/bin/bash
ip=$(ifconfig | grep "inet 192*")
echo $ip